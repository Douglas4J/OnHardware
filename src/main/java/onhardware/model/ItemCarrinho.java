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
@Table(name = "itens_carrinho")
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemCarrinho;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Carrinho carrinho;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private BigDecimal precoTotal;
}
