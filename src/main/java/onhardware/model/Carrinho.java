package onhardware.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "carrinhos")
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrinho;

    /*
    Representa a relação 1:N entre Carrinho e ProdutoCarrinho.
    mappedBy = "carrinho": a associação é controlada pela entidade ProdutoCarrinho.
    cascade = ALL: se o carrinho for salvo/removido, os produtos também serão.
    orphanRemoval = true: se um produto for removido da lista, será deletado do banco.
    fetch = LAZY: os produtos só são carregados quando acessados (melhora o desempenho).
     */
    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProdutoCarrinho> produtos = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal totalProdutosNoCarrinho = BigDecimal.ZERO;

    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataRegistroCarrinho;
}
