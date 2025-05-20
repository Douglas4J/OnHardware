package onhardware.exception;

public class CarrinhoException extends RuntimeException {
    public CarrinhoException(Long id) {
        super("Carrinho não encontrado.");
    }
}
