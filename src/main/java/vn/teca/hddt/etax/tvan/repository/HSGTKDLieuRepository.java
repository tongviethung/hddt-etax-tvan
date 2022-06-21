package vn.teca.hddt.etax.tvan.repository;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import vn.teca.hddt.etax.tvan.model.HSGTKDLieu;

@Dao
public interface HSGTKDLieuRepository {
    @Insert
    void save(HSGTKDLieu entity);
}
