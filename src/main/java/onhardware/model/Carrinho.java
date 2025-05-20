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
    Representa a relação 1:N entre Carrinho e ItemCarrinho.
    mappedBy = "carrinho": a associação é controlada pela entidade ItemCarrinho.
    cascade = ALL: se o carrinho for salvo/removido, os itens também serão.
    orphanRemoval = true: se um item for removido da lista, será deletado do banco.
    fetch = LAZY: os itens só são carregados quando acessados (melhora o desempenho).
     */
    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemCarrinho> itens = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal totalItensNoCarrinho = BigDecimal.ZERO;

    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataRegistroCarrinho;
}

/*

OneToOne:	    Pessoa - Endereço	    / Um para um
OneToMany:	    Carrinho - Itens	    / Um para muitos (um lado)
ManyToOne:	    Item - Carrinho	        / Muitos para um (outro lado)
ManyToMany:	    Aluno - Turma	        / Muitos para muitos, com tabela intermediária

 */
