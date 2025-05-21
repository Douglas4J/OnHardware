package onhardware.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoCarrinhoDTO {

    @JsonIgnore
    private Long idProdutoCarrinho;

    @JsonProperty("produto")
    @NotNull(message = "O produto é obrigatório.")
    private ProdutoDTO produtoDTO;

    @Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
    private int quantidade;

    @NotNull(message = "O preço total do produto é obrigatório.")
    private BigDecimal precoTotal;
}
