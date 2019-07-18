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


    public static final AppResponse OOPS = new AppResponse("Oops... some error occurred!");
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime timestamp;

    public AppResponse(String message) {
        this.timestamp = LocalDateTime.now();
    }

}
