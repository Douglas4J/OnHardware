package onhardware.controller;

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

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @PostMapping
    public ResponseEntity<CarrinhoDTO> criarCarrinho(@Valid @RequestBody CarrinhoDTO carrinhoDTO) {
        CarrinhoDTO carrinhoCadastrado = carrinhoService.cadastrarCarrinho(carrinhoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<CarrinhoDTO>> listarCarrinhos() {
        return ResponseEntity.ok(carrinhoService.listarTodosCarrinhos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarrinhoDTO> buscarCarrinho(@PathVariable Long id) {
        return ResponseEntity.ok(carrinhoService.buscarCarrinhoPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCarrinho(@PathVariable Long id) {
        carrinhoService.deletarCarrinhoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idCarrinho}/produtos")
    public ResponseEntity<CarrinhoDTO> adicionarProdutoAoCarrinho(@PathVariable Long idCarrinho,
                                                                  @Valid @RequestBody AdicionarProdutoDTO dto) {

        CarrinhoDTO carrinhoAtualizado = carrinhoService.adicionarProduto(idCarrinho, dto.getIdProduto(), dto.getQuantidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoAtualizado);
    }

    @DeleteMapping("/{idCarrinho}/produtos/{idProdutoCarrinho}")
    public ResponseEntity<CarrinhoDTO> removerProdutoDoCarrinho(@PathVariable Long idCarrinho,
                                                             @PathVariable Long idProdutoCarrinho) {
        CarrinhoDTO carrinhoAtualizado = carrinhoService.removerProduto(idCarrinho, idProdutoCarrinho);
        return ResponseEntity.ok(carrinhoAtualizado);
    }

    @PostMapping("/{idCarrinho}/finalizar-compra")
    public ResponseEntity<CarrinhoDTO> finalizarCompra(@PathVariable("idCarrinho") Long idCarrinho,
                                                       @RequestBody @Valid FinalizarCompraDTO finalizarCompraDTO) {

        if (!finalizarCompraDTO.isConfirmacao()) {
            return ResponseEntity.badRequest().build();
        }

        CarrinhoDTO carrinhoFinalizado = carrinhoService.finalizarCompra(idCarrinho);
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoFinalizado);
    }

}
