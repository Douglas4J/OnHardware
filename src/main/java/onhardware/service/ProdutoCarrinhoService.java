package onhardware.service;

import onhardware.DTO.ProdutoCarrinhoDTO;
import onhardware.model.Carrinho;
import onhardware.model.ProdutoCarrinho;
import onhardware.repository.ProdutoCarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoCarrinhoService {

    @Autowired
    private ProdutoCarrinhoRepository produtoCarrinhoRepository;

    @Autowired
    private ProdutoService produtoService;

    public ProdutoCarrinho paraEntity(ProdutoCarrinhoDTO produtoCarrinhoDTO, Carrinho carrinho) {
        return ProdutoCarrinho.builder()
                .idProdutoCarrinho(produtoCarrinhoDTO.getIdProdutoCarrinho())
                .produto(produtoService.paraEntity(produtoCarrinhoDTO.getProdutoDTO()))
                .quantidade(produtoCarrinhoDTO.getQuantidade())
                .precoTotal(produtoCarrinhoDTO.getPrecoTotal())
                .carrinho(carrinho)
                .build();
    }

    public ProdutoCarrinhoDTO paraDTO(ProdutoCarrinho produtoCarrinho) {
        return ProdutoCarrinhoDTO.builder()
                .idProdutoCarrinho(produtoCarrinho.getIdProdutoCarrinho())
                .produtoDTO(produtoService.paraDTO(produtoCarrinho.getProduto()))
                .quantidade(produtoCarrinho.getQuantidade())
                .precoTotal(produtoCarrinho.getPrecoTotal())
                .build();
    }

    public void deletarPorId(Long idProdutoCarrinho) {
        produtoCarrinhoRepository.deleteById(idProdutoCarrinho);
    }
}
