package onhardware.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "produtos_carrinho")
public class ProdutoCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProdutoCarrinho;

    // Muitos produtos podem estar relacionados a um mesmo produto.
    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    // Muitos produtos pertencem a um único carrinho.
    @ManyToOne
    @JoinColumn(nullable = false)
    private Carrinho carrinho;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private BigDecimal precoTotal;
}
