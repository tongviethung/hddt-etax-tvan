package vn.teca.hddt.etax.tvan.repository;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface InventoryMapper {
    @DaoFactory
    TimeLogRepository timeLogRepository();

    @DaoFactory
    TKhaiCTietRepository tkhaiCTietRepository();

    @DaoFactory
    TKhaiDLRepository tkhaiDLRepository();

    @DaoFactory
    HSGTKDLieuRepository hsgtkdlieuRepository();

    @DaoFactory
    HSGEtaxTDiepRepository hsgtdiepRepository();

    @DaoFactory
    EtaxTDKHLeRepository tdkhleRepository();

    @DaoFactory
    EtaxTDLoiRepository tdloiRepository();

    @DaoFactory
    EtaxTDiepRepository etaxtdiepRepository();
}