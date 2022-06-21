package vn.teca.hddt.etax.tvan.repository;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import vn.teca.hddt.etax.tvan.model.TimeLog;

@Dao
public interface TimeLogRepository {
    @Insert
    void save(TimeLog timeLog);
}
