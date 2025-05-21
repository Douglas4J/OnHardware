package onhardware.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import onhardware.DTO.AdicionarProdutoDTO;
import onhardware.DTO.CarrinhoDTO;
import onhardware.DTO.FinalizarCompraDTO;
import onhardware.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Carrinho", description = "Operações com carrinho de compras")
@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Operation(summary = "Criar novo carrinho")
    @PostMapping("/criar-carrinho")
    public ResponseEntity<CarrinhoDTO> criarCarrinho(@Valid @RequestBody CarrinhoDTO carrinhoDTO) {
        CarrinhoDTO carrinhoCadastrado = carrinhoService.cadastrarCarrinho(carrinhoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoCadastrado);
    }

    @Operation(summary = "Listar todos os carrinhos")
    @GetMapping("/listar-carrinhos")
    public ResponseEntity<List<CarrinhoDTO>> listarCarrinhos() {
        return ResponseEntity.ok(carrinhoService.listarTodosCarrinhos());
    }

    @Operation(summary = "Buscar carrinho específico")
    @GetMapping("/buscar-carrinho/{id}")
    public ResponseEntity<CarrinhoDTO> buscarCarrinho(@PathVariable Long id) {
        return ResponseEntity.ok(carrinhoService.buscarCarrinhoPorId(id));
    }

    @Operation(summary = "Excluir carrinho")
    @DeleteMapping("/deletar-carrinho/{id}")
    public ResponseEntity<Void> deletarCarrinho(@PathVariable Long id) {
        carrinhoService.deletarCarrinhoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adicionar produtos a um carrinho")
    @PostMapping("/adicionar-produto-carrinho/{idCarrinho}/produtos")
    public ResponseEntity<CarrinhoDTO> adicionarProdutoAoCarrinho(@PathVariable Long idCarrinho,
                                                                  @Valid @RequestBody AdicionarProdutoDTO dto) {

        CarrinhoDTO carrinhoAtualizado = carrinhoService.adicionarProduto(idCarrinho, dto.getIdProduto(), dto.getQuantidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoAtualizado);
    }

    @Operation(summary = "Deletar produto do carrinho")
    @DeleteMapping("/deletar-carrinho/{idCarrinho}/produtos/{idProdutoCarrinho}")
    public ResponseEntity<CarrinhoDTO> removerProdutoDoCarrinho(@PathVariable Long idCarrinho,
                                                             @PathVariable Long idProdutoCarrinho) {
        CarrinhoDTO carrinhoAtualizado = carrinhoService.removerProduto(idCarrinho, idProdutoCarrinho);
        return ResponseEntity.ok(carrinhoAtualizado);
    }

    @Operation(summary = "Finalizar a compra de um carrinho")
    @PostMapping("/finalizar-compra/{idCarrinho}")
    public ResponseEntity<CarrinhoDTO> finalizarCompra(@PathVariable("idCarrinho") Long idCarrinho,
                                                       @RequestBody @Valid FinalizarCompraDTO finalizarCompraDTO) {

        if (!finalizarCompraDTO.isConfirmacao()) {
            return ResponseEntity.badRequest().build();
        }

        CarrinhoDTO carrinhoFinalizado = carrinhoService.finalizarCompra(idCarrinho);
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoFinalizado);
    }

}
