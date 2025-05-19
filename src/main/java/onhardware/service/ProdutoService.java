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

    private Produto toEntity(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        if (produtoDTO.getIdProduto() != null) {
            produto.setIdProduto(produtoDTO.getIdProduto());
        }

        produto.setNomeProduto(produtoDTO.getNomeProduto());
        produto.setMarcaProduto(produtoDTO.getMarcaProduto());
        produto.setModeloProduto(produtoDTO.getModeloProduto());
        produto.setEspecificacaoProduto(produtoDTO.getEspecificacaoProduto());
        return produto;
    }

    private ProdutoDTO toDTO(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setIdProduto(produto.getIdProduto());
        dto.setNomeProduto(produto.getNomeProduto());
        dto.setMarcaProduto(produto.getMarcaProduto());
        dto.setModeloProduto(produto.getModeloProduto());
        dto.setEspecificacaoProduto(produto.getEspecificacaoProduto());
        dto.setPrecoProduto(produto.getPrecoProduto());
        return dto;
    }

    public ProdutoDTO cadastrarProduto(ProdutoDTO produtoDTO) {
        Produto produto = toEntity(produtoDTO);
        Produto produtoSalvo = produtoRepository.save(produto);
        return toDTO(produtoSalvo);
    }

    public List<ProdutoDTO> listarTodosProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        List<ProdutoDTO> produtoDTOs = new ArrayList<>();

        for (Produto produto : produtos) {
            produtoDTOs.add(toDTO(produto));
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
        return toDTO(optionalProduto.get());
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
        return toDTO(produtoSalvo);
    }
}
