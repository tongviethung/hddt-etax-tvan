package vn.teca.hddt.etax.tvan.repository;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import vn.teca.hddt.etax.tvan.model.EtaxTDiep;

@Dao
public interface EtaxTDiepRepository {
    @Insert
    void save(EtaxTDiep entity);
}
