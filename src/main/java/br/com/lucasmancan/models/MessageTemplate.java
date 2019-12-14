package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages_templates")
public class MessageTemplate {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String value;

    private Boolean isHtml;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name ="admin_id")
    private AppUser admin;
//
//    private Boolean active;

    @Column(name="created_at")

    private LocalDateTime createdAt;

    @Column(name="updated_at")


    private LocalDateTime updatedAt;

}
