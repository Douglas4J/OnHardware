package onhardware.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import onhardware.DTO.AdicionarProdutoDTO;
import onhardware.DTO.CarrinhoDTO;
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
    public ResponseEntity<CarrinhoDTO> criarCarrinho() {
        CarrinhoDTO carrinhoCadastrado = carrinhoService.cadastrarCarrinho();
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoCadastrado);
        // POST 201 Created
    }

    @Operation(summary = "Listar todos os carrinhos")
    @GetMapping("/listar-carrinhos")
    public ResponseEntity<List<CarrinhoDTO>> listarCarrinhos() {
        return ResponseEntity.ok(carrinhoService.listarTodosCarrinhos());
        // GET 200 OK
    }

    @Operation(summary = "Buscar carrinho específico")
    @GetMapping("/buscar-carrinho/{id}")
    public ResponseEntity<CarrinhoDTO> buscarCarrinho(@PathVariable Long id) {
        return ResponseEntity.ok(carrinhoService.buscarCarrinhoPorId(id));
        // GET 200 OK
    }

    @Operation(summary = "Excluir carrinho")
    @DeleteMapping("/deletar-carrinho/{id}")
    public ResponseEntity<Void> deletarCarrinho(@PathVariable Long id) {
        carrinhoService.deletarCarrinhoPorId(id);
        return ResponseEntity.noContent().build();
        // 204 No Content
    }

    @Operation(summary = "Adicionar produtos a um carrinho")
    @PutMapping("/adicionar-produto-carrinho/{idCarrinho}/produtos")
    public ResponseEntity<CarrinhoDTO> adicionarProdutoAoCarrinho(@PathVariable Long idCarrinho, @Valid @RequestBody AdicionarProdutoDTO dto) {
        return ResponseEntity.ok(carrinhoService.adicionarProduto(idCarrinho, dto.getIdProduto(), dto.getQuantidade()));
        // 200 OK
    }

    @Operation(summary = "Deletar produto do carrinho")
    @PutMapping("/deletar-carrinho/{idCarrinho}/produtos/{idProdutoCarrinho}")
    public ResponseEntity<CarrinhoDTO> removerProdutoDoCarrinho(@PathVariable Long idCarrinho, @PathVariable Long idProdutoCarrinho) {
        return ResponseEntity.ok(carrinhoService.removerProduto(idCarrinho, idProdutoCarrinho));
        // 200 OK
    }

    @Operation(summary = "Finalizar a compra de um carrinho")
    @PutMapping("/finalizar-compra/{idCarrinho}")
    public ResponseEntity<CarrinhoDTO> finalizarCompra(@PathVariable("idCarrinho") Long idCarrinho) {
        return ResponseEntity.ok(carrinhoService.finalizarCompra(idCarrinho));
        // 200 OK
    }
}
