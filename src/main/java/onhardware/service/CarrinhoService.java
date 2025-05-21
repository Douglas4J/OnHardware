package onhardware.service;

import onhardware.DTO.CarrinhoDTO;
import onhardware.DTO.ProdutoCarrinhoDTO;
import onhardware.exception.CarrinhoException;
import onhardware.exception.ProdutoCarrinhoException;
import onhardware.exception.ProdutoException;
import onhardware.model.Carrinho;
import onhardware.model.ProdutoCarrinho;
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
    private ProdutoCarrinhoService produtoCarrinhoService;

    @Autowired
    private ProdutoService produtoService;

    public Carrinho paraEntity(CarrinhoDTO carrinhoDTO) {
        Carrinho carrinho = Carrinho.builder()
                .idCarrinho(carrinhoDTO.getIdCarrinho())
                .totalProdutosNoCarrinho(carrinhoDTO.getTotalProdutosNoCarrinho())
                .build();

        List<ProdutoCarrinho> produtos = new ArrayList<>();
        for (ProdutoCarrinhoDTO dto : carrinhoDTO.getProdutos()) {
            ProdutoCarrinho produto = produtoCarrinhoService.paraEntity(dto,carrinho);
            produtos.add(produto);
        }
        carrinho.setProdutos(produtos);

        return carrinho;
    }

    public CarrinhoDTO paraDTO(Carrinho carrinho) {
        List<ProdutoCarrinhoDTO> produtosDTO = new ArrayList<>();
        for (ProdutoCarrinho produto : carrinho.getProdutos()) {
            ProdutoCarrinhoDTO produtoDTO = produtoCarrinhoService.paraDTO(produto);
            produtosDTO.add(produtoDTO);
        }

        return CarrinhoDTO.builder()
                .idCarrinho(carrinho.getIdCarrinho())
                .totalProdutosNoCarrinho(carrinho.getTotalProdutosNoCarrinho())
                .produtos(produtosDTO)
                .build();
    }

    public CarrinhoDTO cadastrarCarrinho(CarrinhoDTO carrinhoDTO) {
        Carrinho carrinho = paraEntity(carrinhoDTO);
        Carrinho carrinhoSalvo = carrinhoRepository.save(carrinho);

        return paraDTO(carrinhoSalvo);
    }

    public List<CarrinhoDTO> listarTodosCarrinhos() {
        List<Carrinho> carrinhos = carrinhoRepository.findAll();
        List<CarrinhoDTO> carrinhoDTOs = new ArrayList<>();

        for (Carrinho carrinho : carrinhos) {
            carrinhoDTOs.add(paraDTO(carrinho));
        }

        return carrinhoDTOs;
    }

    public CarrinhoDTO buscarCarrinhoPorId(Long id) {
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(id);
        if (optionalCarrinho.isEmpty()) {
            throw new CarrinhoException(id);
        }

        return paraDTO(optionalCarrinho.get());
    }

    public void deletarCarrinhoPorId(Long id) {
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(id);
        if (optionalCarrinho.isEmpty()) {
            throw new CarrinhoException(id);
        }

        carrinhoRepository.deleteById(id);
    }

    /*
    METOTODO - > adicionarProduto()

    - Acha o carrinho e o produto
    - Vê se o produto já está no carrinho
    - Se sim: soma a quantidade
    - Se não: cria um novo produto e coloca no carrinho
    - Salva tudo
    - Converte e devolve o carrinho atualizado
     */

    public CarrinhoDTO adicionarProduto(Long idCarrinho, Long idProduto, int quantidade) {
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

        // Verifica se o produto já existe no carrinho
        ProdutoCarrinho produtoExistente = buscarProdutoCarrinho(carrinho.getProdutos(), idProduto);

        if (produtoExistente != null) {
            int novaQuantidade = produtoExistente.getQuantidade() + quantidade;
            produtoExistente.setQuantidade(novaQuantidade);
            produtoExistente.setPrecoTotal(produto.getPrecoProduto().multiply(new BigDecimal(novaQuantidade)));

        } else {
            ProdutoCarrinho novoProduto = ProdutoCarrinho.builder()
                    .produto(produto)
                    .quantidade(quantidade)
                    .precoTotal(produto.getPrecoProduto().multiply(new BigDecimal(quantidade)))
                    .carrinho(carrinho)
                    .build();

            carrinho.getProdutos().add(novoProduto);
        }


        carrinho.setTotalProdutosNoCarrinho(calcularTotalProdutosNoCarrinho(carrinho.getProdutos()));

        Carrinho carrinhoAtualizado = carrinhoRepository.save(carrinho);

        return paraDTO(carrinhoAtualizado);
    }

    /*
    METODO -> removerProduto()

    - Busca o carrinho pelo ID
    - Busca o produto do carrinho pelo ID do produto
    - Se não encontrar o carrinho ou o produto, lança exceção
    - Remove o produto da lista de produtos do carrinho
    - Deleta o produto do banco de dados
    - Atualiza o total do carrinho somando os preços dos produtos restantes
    - Salva o carrinho atualizado
    - Converte e retorna o DTO do carrinho atualizado
    */

    public CarrinhoDTO removerProduto(Long idCarrinho, Long idProdutoCarrinho) {
        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findById(idCarrinho);
        if (optionalCarrinho.isEmpty()) {
            throw new CarrinhoException(idCarrinho);
        }
        Carrinho carrinho = optionalCarrinho.get();

        ProdutoCarrinho produtoParaRemover = buscarProdutoCarrinhoPorId(carrinho.getProdutos(), idProdutoCarrinho);
        if (produtoParaRemover == null) {
            throw new ProdutoCarrinhoException(idProdutoCarrinho);
        }

        carrinho.getProdutos().remove(produtoParaRemover);

        produtoCarrinhoService.deletarPorId(idProdutoCarrinho);

        carrinho.setTotalProdutosNoCarrinho(calcularTotalProdutosNoCarrinho(carrinho.getProdutos()));

        Carrinho carrinhoAtualizado = carrinhoRepository.save(carrinho);
        return paraDTO(carrinhoAtualizado);
    }

    public ProdutoCarrinho buscarProdutoCarrinho(List<ProdutoCarrinho> produtos, Long idProduto) {
        for (ProdutoCarrinho produto : produtos) {
            if (produto.getProduto().getIdProduto().equals(idProduto)) {
                return produto;
            }
        }
        return null;
    }

    public ProdutoCarrinho buscarProdutoCarrinhoPorId(List<ProdutoCarrinho> produtos, Long idProdutoCarrinho) {
        for (ProdutoCarrinho produto : produtos) {
            if (produto.getIdProdutoCarrinho().equals(idProdutoCarrinho)) {
                return produto;
            }
        }
        return null;
    }

    // USAR AO ATUALIZAR O CARRINHO
    private BigDecimal calcularTotalProdutosNoCarrinho(List<ProdutoCarrinho> produtos) {
        BigDecimal total = BigDecimal.ZERO;
        for (ProdutoCarrinho produto : produtos) {
            total = total.add(produto.getPrecoTotal());
        }
        return total;
    }
}
