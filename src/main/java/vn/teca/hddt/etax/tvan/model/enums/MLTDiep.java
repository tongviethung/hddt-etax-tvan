package vn.teca.hddt.etax.tvan.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum MLTDiep {
    DANG_KY((short) 100),
    UY_NHIEM((short) 101),
    TIEP_NHAN_DANG_KY((short) 102),
    CHAP_NHAN_DANG_KY((short) 103),
    CHAP_NHAN_UY_NHIEM((short) 104),
    HET_HAN_SU_DUNG((short) 105),
    DE_NGHI_PHAT_SINH((short) 106),
    CAP_MA((short) 200),
    CAP_MA_PHAT_SINH((short) 201),
    KET_QUA_CAP_MA((short) 202),
    KHONG_MA((short) 203),
    KIEM_TRA_DU_LIEU((short) 204),
    // todo review code 205
    KET_QUA_DE_NGHI((short) 206),
    TB_SAI_SOT((short) 300),
    TB_TIEP_NHAN_SAI_SOT((short) 301),
    TB_RA_SOAT((short) 302),
    BANG_TONG_HOP((short) 400),
    UQ_CAP_MA((short) 500),
    UQ_DOI_SOAT((short) 501),
    UQ_KHONG_DU_DK((short) 503),
    UQ_KTDL((short) 504),
    PHAN_HOI_KY_THUAT((short) 999),
    PHAN_HOI_KY_THUAT_LOI((short) -1);

    private final short value;

    MLTDiep(short value) {
        this.value = value;
    }

    @JsonCreator
    public static MLTDiep fromValue(final short value) {
        return Stream.of(MLTDiep.values()).filter(targetEnum -> targetEnum.value == value).findFirst().orElse(null);
    }

    @JsonValue
    public short getValue() {
        return value;
    }
}
