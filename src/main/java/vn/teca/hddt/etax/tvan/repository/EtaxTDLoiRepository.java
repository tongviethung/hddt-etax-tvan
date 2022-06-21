package vn.teca.hddt.etax.tvan.repository;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import vn.teca.hddt.etax.tvan.model.EtaxTDLoi;

@Dao
public interface EtaxTDLoiRepository {
    @Insert
    void save(EtaxTDLoi entity);
}
