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

    // Muitos itens podem estar relacionados a um mesmo produto.
    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    // Muitos itens pertencem a um único carrinho.
    @ManyToOne
    @JoinColumn(nullable = false)
    private Carrinho carrinho;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private BigDecimal precoTotal;
}

/*

OneToOne:	    Pessoa - Endereço	    / Um para um
OneToMany:	    Carrinho - Itens	    / Um para muitos (um lado)
ManyToOne:	    Item - Carrinho	        / Muitos para um (outro lado)
ManyToMany:	    Aluno - Turma	        / Muitos para muitos, com tabela intermediária

 */
