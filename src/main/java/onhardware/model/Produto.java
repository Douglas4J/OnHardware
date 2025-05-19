package onhardware.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProduto;

    @Column(nullable = false)
    private String nomeProduto;

    @Column(nullable = false)
    private String marcaProduto;

    @Column(nullable = false)
    private String modeloProduto;

    @Column(nullable = false, length = 300)
    private String especificacaoProduto;

    @Column(nullable = false)
    private BigDecimal precoProduto;

    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataRegistroProduto;
}
