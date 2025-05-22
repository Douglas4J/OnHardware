package onhardware.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "ID do carrinho (gerado automaticamente)")
    private Long idCarrinho;

    @NotNull(message = "A lista de produtos é obrigatória.")
    @Schema(description = "Lista de produtos no carrinho (inicialmente vazia)", example = "[]")
    private List<@Valid ProdutoCarrinhoDTO> produtos;

    @NotNull(message = "O total do carrinho é obrigatório.")
    @Schema(description = "Valor total do carrinho (inicialmente zero)", example = "0.00")
    private BigDecimal valorTotalCarrinho;

    private LocalDateTime dataCompraFinalizada;

    private boolean finalizado;
}
