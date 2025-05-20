package onhardware.service;

import onhardware.DTO.ItemCarrinhoDTO;
import onhardware.model.Carrinho;
import onhardware.model.ItemCarrinho;
import onhardware.repository.ItemCarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemCarrinhoService {

    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @Autowired
    private ProdutoService produtoService;

    public ItemCarrinho paraEntity(ItemCarrinhoDTO itemCarrinhoDTO, Carrinho carrinho) {
        return ItemCarrinho.builder()
                .idItemCarrinho(itemCarrinhoDTO.getIdItemCarrinho())
                .produto(produtoService.paraEntity(itemCarrinhoDTO.getProdutoDTO()))
                .quantidade(itemCarrinhoDTO.getQuantidade())
                .precoTotal(itemCarrinhoDTO.getPrecoTotal())
                .carrinho(carrinho)
                .build();
    }

    public ItemCarrinhoDTO paraDTO(ItemCarrinho itemCarrinho) {
        return ItemCarrinhoDTO.builder()
                .idItemCarrinho(itemCarrinho.getIdItemCarrinho())
                .produtoDTO(produtoService.paraDTO(itemCarrinho.getProduto()))
                .quantidade(itemCarrinho.getQuantidade())
                .precoTotal(itemCarrinho.getPrecoTotal())
                .build();
    }

    public void deletarPorId(Long idItemCarrinho) {
        itemCarrinhoRepository.deleteById(idItemCarrinho);
    }

}
