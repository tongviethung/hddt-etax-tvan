package vn.teca.hddt.etax.tvan.service;

import com.datastax.oss.driver.api.core.DriverTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import vn.teca.hddt.etax.tvan.model.*;
import vn.teca.hddt.etax.tvan.repository.InventoryMapper;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataService {

    private final InventoryMapper inventoryMapper;
    private final InventoryMapper originalInventoryMapper;

    @Retryable(
            maxAttempts = 5,
            backoff = @Backoff(delay = 3000),
            value = {DriverTimeoutException.class}
    )
    public void saveEtaxTDiep(EtaxTDiep entity) {
        inventoryMapper.etaxtdiepRepository().save(entity);
    }

    @Retryable(
            maxAttempts = 5,
            backoff = @Backoff(delay = 3000),
            value = {DriverTimeoutException.class}
    )
    public void saveTDKHLe(UUID hsgoc, String mloi, String tloi) {
        EtaxTDKHLe entity = new EtaxTDKHLe();
        entity.setId(UUID.randomUUID());
        entity.setHsgoc(hsgoc);
        entity.setMloi(mloi);
        entity.setTloi(tloi);
        entity.setNtao(Instant.now());
        inventoryMapper.tdkhleRepository().save(entity);
    }

    @Retryable(
            maxAttempts = 5,
            backoff = @Backoff(delay = 3000),
            value = {DriverTimeoutException.class}
    )
    public void saveTDLoi(String msgid, UUID hsgoc, String mloi, String tloi) {
        EtaxTDLoi entity = new EtaxTDLoi();
        entity.setMsgid(msgid);
        entity.setId(UUID.randomUUID());
        entity.setHsgoc(hsgoc);
        entity.setMloi(mloi);
        entity.setTloi(tloi);
        entity.setNtao(Instant.now());
        inventoryMapper.tdloiRepository().save(entity);
    }

    @Retryable(
            maxAttempts = 5,
            backoff = @Backoff(delay = 3000),
            value = {DriverTimeoutException.class}
    )
    public UUID saveHSGTDiep(String data) {
        HSGEtaxTDiep entity = new HSGEtaxTDiep();
        entity.setId(UUID.randomUUID());
        entity.setHsgoc(data);
        entity.setNtao(Instant.now());
        originalInventoryMapper.hsgtdiepRepository().save(entity);
        return entity.getId();
    }

    @Retryable(
            maxAttempts = 5,
            backoff = @Backoff(delay = 3000),
            value = {DriverTimeoutException.class}
    )
    public UUID saveHSGTKDLieu(String data) {
        HSGTKDLieu entity = new HSGTKDLieu();
        entity.setId(UUID.randomUUID());
        entity.setHsgoc(data);
        entity.setNtao(Instant.now());
        originalInventoryMapper.hsgtkdlieuRepository().save(entity);
        return entity.getId();
    }

    @Retryable(
            maxAttempts = 5,
            backoff = @Backoff(delay = 3000),
            value = {DriverTimeoutException.class}
    )
    public void saveTKDLieu(TKhaiDL entity) {
        inventoryMapper.tkhaiDLRepository().save(entity);
        inventoryMapper.tkhaiCTietRepository().saveList(entity.getTkdlctiets());
    }
}
