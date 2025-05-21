package onhardware.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdicionarProdutoDTO {

    @NotNull(message = "O ID do produto é obrigatório.")
    private Long idProduto;

    @Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
    private int quantidade;
}
