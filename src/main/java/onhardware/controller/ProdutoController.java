package onhardware.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import onhardware.DTO.ProdutoDTO;
import onhardware.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Produto", description = "Gerenciamento de produtos")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Cadastrar um novo produto")
    @PostMapping("/cadastrar-produto")
    public ResponseEntity<ProdutoDTO> criarProdutos(@Valid @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO produtoCadastrado = produtoService.cadastrarProduto(produtoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCadastrado);
        // POST 201 Created
    }

    @Operation(summary = "Listar todos os produtos")
    @GetMapping("/listar-produtos")
    public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarTodosProdutos());
        // GET 200 OK
    }

    @Operation(summary = "Buscar produto específico")
    @GetMapping("/buscar-produto/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarProdutoPorId(id));
        // GET 200 OK
    }

    @Operation(summary = "Editar produto")
    @PutMapping("/atualizar-produto/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.ok(produtoService.atualizarProdutoPorId(id, produtoDTO));
        // 200 OK
    }

    @Operation(summary = "Excluir produto")
    @DeleteMapping("/deletar-produto/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProdutoPorId(id);
        return ResponseEntity.noContent().build();
        // 204 No Content
    }
}
