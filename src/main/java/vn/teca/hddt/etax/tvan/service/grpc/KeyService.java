package vn.teca.hddt.etax.tvan.service.grpc;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.teca.hddt.category.grpc.KeyRequest;
import vn.teca.hddt.category.grpc.KeyResponse;
import vn.teca.hddt.category.grpc.KeyServiceGrpc;

@Service
@Slf4j
public class KeyService {
    private static final String BUCKET = "ETAX";
    @GrpcClient("hddt-key")
    private KeyServiceGrpc.KeyServiceBlockingStub stub;

    public boolean validate(String value) throws Exception {
        try {
            KeyResponse response = stub.validate(
                    KeyRequest.newBuilder()
                            .setKey(BUCKET)
                            .setValue(value)
                            .build());
            if (response.getStatusCode() != HttpStatus.OK.value()) {
                throw new Exception(response.getStatusText());
            }
            return response.getStatus();
        } catch (Exception ex) {
            log.error("Lỗi gọi service {}", KeyServiceGrpc.SERVICE_NAME, ex);
            throw new Exception(ex);
        }
    }
}
