package vn.teca.hddt.etax.tvan.xml.validator;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.StringUtils;
import vn.teca.hddt.etax.tvan.model.enums.MLTDiep;
import vn.teca.hddt.etax.tvan.util.RegexCommon;
import vn.teca.hddt.etax.tvan.xml.TDiepXml;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Only validate with message sent to TVAN
 */
public class TDiepConstraintValidator implements ConstraintValidator<TDiepConstraint, TDiepXml> {
    @Override
    public boolean isValid(TDiepXml entity, ConstraintValidatorContext context) {
        if (entity == null) return true;

        // ignored validate message sent from core
        if (entity.getTtchung().getMngui().equals("TCT")) {
            return true;
        }

        Multimap<String, String> errorMap = ArrayListMultimap.create();

        // validate mngui
        if (!RegexCommon.validSenderCode(entity.getTtchung().getMngui())) {
            errorMap.put("ttchung.mngui", "Giá trị không hợp lệ");
        }

        // validate mnnhan => da bo qua truong hop gui tu core
        if (entity.getTtchung().getMnnhan() != null) {
            if (!"TCT".equals(entity.getTtchung().getMnnhan())) {
                errorMap.put("ttchung.mnnhan", "Giá trị không hợp lệ");
            }
        }

        // validate mtdiep
        if (entity.getTtchung().getMtdiep() != null && (!entity.getTtchung().getMtdiep().startsWith(entity.getTtchung().getMngui()) ||
                !RegexCommon.validMessageCode(entity.getTtchung().getMtdiep()))
        ) {
            errorMap.put("ttchung.mtdiep", "Giá trị không hợp lệ");
        }

        if (StringUtils.isNotBlank(entity.getTtchung().getPban()) && !entity.getTtchung().getPban().equals("2.0.0")) {
            errorMap.put("ttchung.pban", "Phiên bản không hợp lệ");
        }

        // validate so luong, mst
        if (entity.getTtchung().getMltdiep() != null) {
            Set<Short> quantityWhitelists = new HashSet<>(Arrays.asList(
                    MLTDiep.PHAN_HOI_KY_THUAT.getValue(),
                    MLTDiep.PHAN_HOI_KY_THUAT_LOI.getValue(),
                    MLTDiep.UQ_DOI_SOAT.getValue()
            ));

            Set<Short> tinWhitelists = new HashSet<>(Arrays.asList(
                    MLTDiep.PHAN_HOI_KY_THUAT.getValue(),
                    MLTDiep.PHAN_HOI_KY_THUAT_LOI.getValue(),
                    MLTDiep.UQ_CAP_MA.getValue(),
                    MLTDiep.UQ_KHONG_DU_DK.getValue(),
                    MLTDiep.UQ_KTDL.getValue(),
                    MLTDiep.UQ_DOI_SOAT.getValue()
            ));

            short mltdiep = entity.getTtchung().getMltdiep().shortValue();
            if (!quantityWhitelists.contains(mltdiep) && Objects.isNull(entity.getTtchung().getSluong())) {
                errorMap.put("ttchung.sluong", "Không được bỏ trống");
            }

            if (!tinWhitelists.contains(mltdiep) && Objects.isNull(entity.getTtchung().getMst())) {
                errorMap.put("ttchung.mst", "Không được bỏ trống");
            }
        }

        if (!errorMap.isEmpty()) {
            context.disableDefaultConstraintViolation();
            errorMap.asMap().forEach((key, messages) ->
                    messages.forEach(msg ->
                            context.buildConstraintViolationWithTemplate(msg)
                                    .addPropertyNode(key)
                                    .addConstraintViolation()));
            return false;
        }

        return true;
    }
}