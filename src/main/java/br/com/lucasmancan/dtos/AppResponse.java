package br.com.lucasmancan.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Lazy
public class AppResponse {

    public static final AppResponse OOPS = new AppResponse(false, "Oops... Um erro interno aconteceu, verifique sua conexão com a internet ou entre em contado com o administrador!", null);
    private String message;
    private Boolean success;
    private Object data;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date timestamp;

    public AppResponse(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = new Date();
    }

}
