package onhardware.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProduto;

    @NotBlank(message = "O nome do produto é obrigatório.")
    @Column(nullable = false)
    private String nomeProduto;

    @NotBlank(message = "A marca do produto é obrigatória.")
    @Column(nullable = false)
    private String marcaProduto;

    @NotBlank(message = "O modelo do produto é obrigatório.")
    @Column(nullable = false)
    private String modeloProduto;

    @NotBlank(message = "As especificações do produto são obrigatórias.")
    @Size(max = 300, message = "As especificações deve ter no máximo 300 caracteres.")
    @Column(nullable = false)
    private String especificacaoProduto;

    @NotNull(message = "O preço do produto é obrigatório.")
    @Positive(message = "O preço do produto deve ser maior que zero.")
    @Column(nullable = false)
    private BigDecimal precoProduto;

    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataRegistroProduto;

}
