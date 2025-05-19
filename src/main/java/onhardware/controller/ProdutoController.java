package onhardware.controller;


import jakarta.validation.Valid;
import onhardware.DTO.ProdutoDTO;
import onhardware.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoDTO> criarProdutos(@Valid @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO produtoCadastrado = produtoService.cadastrarProduto(produtoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarTodosProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarProdutoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.ok(produtoService.atualizarProdutoPorId(id, produtoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProdutoPorId(id);
        return ResponseEntity.noContent().build();
    }

}

/*
200 OK — é o mais comum para atualizar um recurso existente e retornar a representação atualizada.

201 Created — usado somente se a atualização criou um recurso novo (por exemplo, se o recurso com aquele id não existia e foi criado durante o PUT).

204 No Content — também usado quando a atualização ocorreu com sucesso, mas o servidor não retorna conteúdo no corpo.
*/