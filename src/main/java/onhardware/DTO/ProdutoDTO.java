package onhardware.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProdutoDTO {

    private Long idProduto;

    @NotBlank(message = "O nome do produto é obrigatório.")
    private String nomeProduto;

    @NotBlank(message = "A marca do produto é obrigatória.")
    private String marcaProduto;

    @NotBlank(message = "O modelo do produto é obrigatório.")
    private String modeloProduto;

    @NotBlank(message = "As especificações do produto são obrigatórias.")
    @Size(max = 300, message = "As especificações deve ter no máximo 300 caracteres.")
    private String especificacaoProduto;

    @NotNull(message = "O preço do produto é obrigatório.")
    @Positive(message = "O preço do produto deve ser maior que zero.")
    private BigDecimal precoProduto;
}
