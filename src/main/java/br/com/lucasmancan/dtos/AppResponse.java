package br.com.lucasmancan.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Lazy
public class AppResponse {


    public static final AppResponse OOPS = new AppResponse("Oops... Um erro interno aconteceu, verifique sua conexão com a internet ou entre em contado com o administrador!", null);
    private String message;
    private Object data;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime timestamp;

    public AppResponse(String message, Object data) {
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

}
