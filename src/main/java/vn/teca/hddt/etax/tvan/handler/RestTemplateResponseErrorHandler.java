package vn.teca.hddt.etax.tvan.handler;

import com.google.common.io.CharStreams;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import vn.teca.hddt.etax.tvan.exception.ResourceNotFoundException;
import vn.teca.hddt.etax.tvan.exception.RestTemplateException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        String body = CharStreams.toString(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
        if (response.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
            // handle SERVER_ERROR
            throw new RestTemplateException(body);
        } else if (response.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            // handle CLIENT_ERROR
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResourceNotFoundException(body);
            } else {
                throw new RestTemplateException(body);
            }
        }
    }
}
