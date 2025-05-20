package onhardware.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ItemCarrinhoDTO {

    private Long idItemCarrinho;

    @NotNull(message = "O produto é obrigatório.")
    private ProdutoDTO produtoDTO;

    @Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
    private int quantidade;

    @NotNull(message = "O preço total do item é obrigatório.")
    private BigDecimal precoTotal;
}
