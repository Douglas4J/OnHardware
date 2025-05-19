package onhardware.service;

import onhardware.exception.ProdutoException;
import onhardware.model.Produto;
import onhardware.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto cadastrarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public void deletarProdutoPorId(Long id) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isEmpty()) {
            throw new ProdutoException(id);
        }

        produtoRepository.deleteById(id);
    }

    public Produto buscarProdutoPorId(Long id) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isEmpty()) {
            throw new ProdutoException(id);
        }
        return optionalProduto.get();
    }

    public Produto atualizarProdutoPorId(Long id, Produto produtoAtualizado) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isEmpty()) {
            throw new ProdutoException(id);
        }
        Produto produto = optionalProduto.get();

        produto.setNomeProduto(produtoAtualizado.getNomeProduto());
        produto.setMarcaProduto(produtoAtualizado.getMarcaProduto());
        produto.setModeloProduto(produtoAtualizado.getModeloProduto());
        produto.setEspecificacaoProduto(produtoAtualizado.getEspecificacaoProduto());
        produto.setPrecoProduto(produtoAtualizado.getPrecoProduto());
        return produtoRepository.save(produto);
    }
}
