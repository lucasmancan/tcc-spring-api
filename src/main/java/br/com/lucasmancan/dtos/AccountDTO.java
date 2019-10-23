package br.com.lucasmancan.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private String name;

}
