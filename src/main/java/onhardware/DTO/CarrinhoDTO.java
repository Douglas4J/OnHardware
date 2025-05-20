package onhardware.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CarrinhoDTO {

    private Long idCarrinho;

    @NotNull(message = "A lista de itens é obrigatória.")
    @Size(min = 1, message = "O carrinho deve conter pelo menos um item.")
    private List<@Valid ItemCarrinhoDTO> itens;

    @NotNull(message = "O total do carrinho é obrigatório.")
    private BigDecimal totalCarrinho;
}
