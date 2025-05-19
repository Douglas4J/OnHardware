package onhardware.service;

import onhardware.DTO.ProdutoDTO;
import onhardware.exception.ProdutoException;
import onhardware.model.Produto;
import onhardware.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    private Produto paraEntity(ProdutoDTO produtoDTO) {
        return Produto.builder()
                .idProduto(produtoDTO.getIdProduto())
                .nomeProduto(produtoDTO.getNomeProduto())
                .marcaProduto(produtoDTO.getMarcaProduto())
                .modeloProduto(produtoDTO.getModeloProduto())
                .especificacaoProduto(produtoDTO.getEspecificacaoProduto())
                .precoProduto(produtoDTO.getPrecoProduto())
                .build();
    }

    private ProdutoDTO paraDTO(Produto produto) {
        return ProdutoDTO.builder()
                .idProduto(produto.getIdProduto())
                .nomeProduto(produto.getNomeProduto())
                .marcaProduto(produto.getMarcaProduto())
                .modeloProduto(produto.getModeloProduto())
                .especificacaoProduto(produto.getEspecificacaoProduto())
                .precoProduto(produto.getPrecoProduto())
                .build();
    }

    public ProdutoDTO cadastrarProduto(ProdutoDTO produtoDTO) {
        Produto produto = paraEntity(produtoDTO);
        Produto produtoSalvo = produtoRepository.save(produto);

        return paraDTO(produtoSalvo);
    }

    public List<ProdutoDTO> listarTodosProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        List<ProdutoDTO> produtoDTOs = new ArrayList<>();

        for (Produto produto : produtos) {
            produtoDTOs.add(paraDTO(produto));
        }

        return produtoDTOs;
    }

    public void deletarProdutoPorId(Long id) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isEmpty()) {
            throw new ProdutoException(id);
        }

        produtoRepository.deleteById(id);
    }

    public ProdutoDTO buscarProdutoPorId(Long id) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isEmpty()) {
            throw new ProdutoException(id);
        }

        return paraDTO(optionalProduto.get());
    }

    public ProdutoDTO atualizarProdutoPorId(Long id, ProdutoDTO produtoDTO) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isEmpty()) {
            throw new ProdutoException(id);
        }

        Produto produto = optionalProduto.get();
        produto.setNomeProduto(produtoDTO.getNomeProduto());
        produto.setMarcaProduto(produtoDTO.getMarcaProduto());
        produto.setModeloProduto(produtoDTO.getModeloProduto());
        produto.setEspecificacaoProduto(produtoDTO.getEspecificacaoProduto());
        produto.setPrecoProduto(produtoDTO.getPrecoProduto());
        Produto produtoSalvo = produtoRepository.save(produto);

        return paraDTO(produtoSalvo);
    }
}
