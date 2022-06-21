package vn.teca.hddt.etax.tvan.repository;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Insert;
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider;
import vn.teca.hddt.etax.tvan.model.TKhaiCTiet;
import vn.teca.hddt.etax.tvan.provider.TKhaiCTietProvider;

import java.util.List;

@Dao
public interface TKhaiCTietRepository {
    @Insert
    void save(TKhaiCTiet entity);

    @QueryProvider(providerClass = TKhaiCTietProvider.class, entityHelpers = TKhaiCTiet.class)
    void saveList(List<TKhaiCTiet> items);
}
