package onhardware.service;

import onhardware.DTO.CarrinhoDTO;
import onhardware.DTO.ItemCarrinhoDTO;
import onhardware.DTO.ProdutoDTO;
import onhardware.exception.CarrinhoException;
import onhardware.exception.ItemCarrinhoException;
import onhardware.exception.ProdutoException;
import onhardware.model.Carrinho;
import onhardware.model.ItemCarrinho;
import onhardware.model.Produto;
import onhardware.repository.CarrinhoRepository;
import onhardware.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemCarrinhoService itemCarrinhoService;

    @Autowired
    private ProdutoService produtoService;

    public Carrinho paraEntity(CarrinhoDTO carrinhoDTO) {
        Carrinho carrinho = Carrinho.builder()
                .idCarrinho(carrinhoDTO.getIdCarrinho())
                .totalItensNoCarrinho(carrinhoDTO.getTotalItensNoCarrinho())
                .build();

        List<ItemCarrinho> itens = new ArrayList<>();
        for (ItemCarrinhoDTO dto : carrinhoDTO.getItens()) {
            ItemCarrinho item = itemCarrinhoService.paraEntity(dto,carrinho);
            itens.add(item);
        }
        carrinho.setItens(itens);

        return carrinho;
    }

    public CarrinhoDTO paraDTO(Carrinho carrinho) {
        List<ItemCarrinhoDTO> itensDTO = new ArrayList<>();
        for (ItemCarrinho item : carrinho.getItens()) {
            ItemCarrinhoDTO itemDTO =itemCarrinhoService.paraDTO(item);
            itensDTO.add(itemDTO);
        }

        return CarrinhoDTO.builder()
                .idCarrinho(carrinho.getIdCarrinho())
                .totalItensNoCarrinho(carrinho.getTotalItensNoCarrinho())
                .itens(itensDTO)
                .build();
    }

    public CarrinhoDTO cadastrarCarrinho(CarrinhoDTO carrinhoDTO) {
        Carrinho carrinho = paraEntity(carrinhoDTO);
        Carrinho carrinhoSalvo = carrinhoRepository.save(carrinho);

        return paraDTO(carrinhoSalvo);
    }

    /*
    METOTODO - > adicionarItem()

    - Acha o carrinho e o produto
    - Vê se o produto já está no carrinho
    - Se sim: soma a quantidade
    - Se não: cria um novo item e coloca no carrinho
    - Salva tudo
    - Converte e devolve o carrinho atualizado
     */

    public CarrinhoDTO adicionarItem(Long idCarrinho, Long idProduto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        // Buscar carrinho
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(idCarrinho);
        if (optionalCarrinho.isEmpty()) {
            throw new CarrinhoException(idCarrinho);
        }
        Carrinho carrinho = optionalCarrinho.get();

        // Buscar produto
        Optional<Produto> optionalProduto = produtoRepository.findById(idProduto);
        if (optionalProduto.isEmpty()) {
            throw new ProdutoException(idProduto);
        }
        Produto produto = optionalProduto.get();

        // Verifica se o item já existe no carrinho
        ItemCarrinho itemExistente = buscarItemCarrinho(carrinho.getItens(), idProduto);

        if (itemExistente != null) {
            int novaQuantidade = itemExistente.getQuantidade() + quantidade;
            itemExistente.setQuantidade(novaQuantidade);
            itemExistente.setPrecoTotal(produto.getPrecoProduto().multiply(new BigDecimal(novaQuantidade)));

        } else {
            ItemCarrinho novoItem = ItemCarrinho.builder()
                    .produto(produto)
                    .quantidade(quantidade)
                    .precoTotal(produto.getPrecoProduto().multiply(new BigDecimal(quantidade)))
                    .carrinho(carrinho)
                    .build();

            carrinho.getItens().add(novoItem);
        }


        carrinho.setTotalItensNoCarrinho(calcularTotalItensNoCarrinho(carrinho.getItens()));

        Carrinho carrinhoAtualizado = carrinhoRepository.save(carrinho);

        // Retorna o DTO atualizado
        return paraDTO(carrinhoAtualizado);
    }

    /*
    METODO -> removerItem()

    - Busca o carrinho pelo ID
    - Busca o item do carrinho pelo ID do item
    - Se não encontrar o carrinho ou o item, lança exceção
    - Remove o item da lista de itens do carrinho
    - Deleta o item do banco de dados
    - Atualiza o total do carrinho somando os preços dos itens restantes
    - Salva o carrinho atualizado
    - Converte e retorna o DTO do carrinho atualizado
    */

    public CarrinhoDTO removerItem(Long idCarrinho, Long idItemCarrinho) {
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(idCarrinho);
        if (optionalCarrinho.isEmpty()) {
            throw new CarrinhoException(idCarrinho);
        }
        Carrinho carrinho = optionalCarrinho.get();

        ItemCarrinho itemParaRemover = buscarItemCarrinhoPorId(carrinho.getItens(), idItemCarrinho);
        if (itemParaRemover == null) {
            throw new ItemCarrinhoException(idItemCarrinho);
        }

        carrinho.getItens().remove(itemParaRemover);

        itemCarrinhoService.deletarPorId(idItemCarrinho);

        carrinho.setTotalItensNoCarrinho(calcularTotalItensNoCarrinho(carrinho.getItens()));

        Carrinho carrinhoAtualizado = carrinhoRepository.save(carrinho);
        return paraDTO(carrinhoAtualizado);
    }

    public ItemCarrinho buscarItemCarrinho(List<ItemCarrinho> itens, Long idProduto) {
        for (ItemCarrinho item : itens) {
            if (item.getProduto().getIdProduto().equals(idProduto)) {
                return item;
            }
        }
        return null;
    }

    public ItemCarrinho buscarItemCarrinhoPorId(List<ItemCarrinho> itens, Long idItemCarrinho) {
        for (ItemCarrinho item : itens) {
            if (item.getIdItemCarrinho().equals(idItemCarrinho)) {
                return item;
            }
        }
        return null;
    }

    // USAR AO ATUALIZAR O CARRINHO
    private BigDecimal calcularTotalItensNoCarrinho(List<ItemCarrinho> itens) {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemCarrinho item : itens) {
            total = total.add(item.getPrecoTotal());
        }
        return total;
    }


}
