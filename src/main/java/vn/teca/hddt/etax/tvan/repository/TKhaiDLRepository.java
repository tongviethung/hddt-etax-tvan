package vn.teca.hddt.etax.tvan.repository;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import vn.teca.hddt.etax.tvan.model.TKhaiDL;

@Dao
public interface TKhaiDLRepository {
    @Insert
    void save(TKhaiDL entity);
}
