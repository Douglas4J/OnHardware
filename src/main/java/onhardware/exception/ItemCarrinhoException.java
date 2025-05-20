package onhardware.exception;

public class ItemCarrinhoException extends RuntimeException {
    public ItemCarrinhoException(Long id) {
        super("Item não encontrado");
    }
}
