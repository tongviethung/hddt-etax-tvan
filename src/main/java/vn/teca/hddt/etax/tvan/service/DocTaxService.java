package vn.teca.hddt.etax.tvan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vn.teca.hddt.etax.tvan.exception.FatalException;
import vn.teca.hddt.etax.tvan.mapper.DocTaxMapper;
import vn.teca.hddt.etax.tvan.mapper.entity.DocTax;
import vn.teca.hddt.etax.tvan.model.DocTaxDto;

@Service
@Slf4j
public class DocTaxService {

	@Autowired
	private ResponseMessageBuilder responseMessageBuilder;

	@Autowired
	DocTaxMapper docTaxMapper;
	
	public void process(String payload) {
		// bóc thông tin header
		DocTaxDto docTaxDto;
		try {
			docTaxDto = responseMessageBuilder.extract2(payload);
		} catch (FatalException ex) {
			log.info("Exception {} - {}", ex, ex.getMessage());
			responseMessageBuilder.buildErrorResponse(ex);
			return;
		}
		// todo insert db
		DocTax entity = new DocTax();
		entity.setLicenseNumber(docTaxDto.getSoGiayPhepSX());
		entity.setLicenseDate(docTaxDto.getNgayGiayPhepSX());
		entity.setQuantity(docTaxDto.getSanLuong());
		entity.setDuration(docTaxDto.getThoiHan());
		
		docTaxMapper.insert(entity);
	}
}
