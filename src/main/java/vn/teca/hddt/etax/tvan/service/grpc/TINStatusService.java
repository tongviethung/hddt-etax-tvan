package vn.teca.hddt.etax.tvan.service.grpc;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.teca.hddt.category.grpc.TINStatus;
import vn.teca.hddt.category.grpc.TINStatusRequest;
import vn.teca.hddt.category.grpc.TINStatusResponse;
import vn.teca.hddt.category.grpc.TINStatusServiceGrpc;

import java.util.Optional;

@Service
@Slf4j
public class TINStatusService {
    @GrpcClient("hddt-category")
    private TINStatusServiceGrpc.TINStatusServiceBlockingStub stub;

    public Optional<TINStatus> findByTIN(String tin) throws Exception {
        try {
            TINStatusResponse response = stub.findByTIN(TINStatusRequest.newBuilder().setTin(tin).build());
            if (response.getStatusCode() != HttpStatus.OK.value()) {
                throw new Exception(response.getStatusText());
            }
            return response.getTinStatusesList().stream().findFirst();
        } catch (Exception ex) {
            log.error("Lỗi gọi service {}", TINStatusServiceGrpc.SERVICE_NAME, ex);
            throw new Exception(ex);
        }
    }
}
