package vn.teca.hddt.etax.tvan.mapper;

import java.util.List;

import vn.teca.hddt.etax.tvan.mapper.entity.DocTax;

public interface DocTaxMapper {

    int insert(DocTax row);

    DocTax selectByPrimaryKey(Long id);

    List<DocTax> selectAll();

    int updateByPrimaryKey(DocTax row);
}