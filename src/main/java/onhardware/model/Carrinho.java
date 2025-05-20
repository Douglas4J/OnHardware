package onhardware.model;

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

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemCarrinho> itens = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal totalCarrinho = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataRegistroCarrinho;
}
