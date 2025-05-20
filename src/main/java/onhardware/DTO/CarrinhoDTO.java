package onhardware.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoDTO {

    private Long idCarrinho;

    @NotNull(message = "A lista de itens é obrigatória.")
    private List<@Valid ItemCarrinhoDTO> itens;

    @NotNull(message = "O total do carrinho é obrigatório.")
    private BigDecimal totalItensNoCarrinho;
}
