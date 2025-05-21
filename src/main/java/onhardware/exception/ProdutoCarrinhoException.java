package onhardware.exception;

public class ProdutoCarrinhoException extends RuntimeException {
    public ProdutoCarrinhoException(Long id) {
        super("Produto no carrinho não encontrado");
    }
}
