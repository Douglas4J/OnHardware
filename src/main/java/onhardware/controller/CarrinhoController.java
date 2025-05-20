package onhardware.controller;

import jakarta.validation.Valid;
import onhardware.DTO.CarrinhoDTO;
import onhardware.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{idCarrinho}/itens/{idProduto}")
    public ResponseEntity<CarrinhoDTO> adicionarItemAoCarrinho(@PathVariable Long idCarrinho,
                                                               @PathVariable Long idProduto,
                                                               @RequestParam int quantidade) {

        CarrinhoDTO carrinhoAtualizado = carrinhoService.adicionarItem(idCarrinho, idProduto, quantidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoAtualizado);
    }

    @DeleteMapping("/{idCarrinho}/itens/{idItemCarrinho}")
    public ResponseEntity<CarrinhoDTO> removerItemDoCarrinho(@PathVariable Long idCarrinho,
                                                             @PathVariable Long idItemCarrinho) {
        CarrinhoDTO carrinhoAtualizado = carrinhoService.removerItem(idCarrinho, idItemCarrinho);
        return ResponseEntity.ok(carrinhoAtualizado);
    }

}
