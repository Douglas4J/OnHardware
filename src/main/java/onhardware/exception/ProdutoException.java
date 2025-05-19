package onhardware.exception;

public class ProdutoException extends RuntimeException {
    public ProdutoException(Long id) {
        super("Produto com ID " + id + " não encontrado.");
    }
}
