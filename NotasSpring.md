# Anotações relacionadas ao projeto Spring

---

# Classe Modelo - Produto

```java
@Data
@Entity
public class Produto {
    
}
```

🔹 @Data (Lombok)
- Gera automaticamente os métodos:
getters, setters, toString(), equals() e hashCode().

- Evita repetição de código padrão (boilerplate).

🔹 @Entity (JPA)
- Indica que essa classe é uma entidade JPA, ou seja, será mapeada para uma tabela no banco de dados.

### Chave Primária
    
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long idProduto; 
```

- @Id: Define que o campo é a chave primária. <br> </br>

- @GeneratedValue(strategy = GenerationType.IDENTITY):
O valor do ID será gerado automaticamente pelo banco de dados (auto-incremento).

### Atributos com Validação

```java
@NotBlank(message = "O nome do produto é obrigatório.")
@Column(nullable = false)
private String nomeProduto;
```

- @NotBlank: Valor não pode ser nulo, vazio ou espaços em branco. <br> </br>

- @Column(nullable = false): Campo obrigatório no banco de dados. <br> </br>

### Especificações do Produto

```java
@NotBlank(message = "As especificações do produto são obrigatórias.")
@Size(max = 300, message = "As especificações deve ter no máximo 300 caracteres.")
@Column(nullable = false)
private String especificacaoProduto;
```

- @Size(max = 300): Limita o tamanho máximo a 300 caracteres para proteger o banco e melhorar a usabilidade.

### Proço do Produto

```java
@NotNull(message = "O preço do produto é obrigatório.")
@Positive(message = "O preço do produto deve ser maior que zero.")
@Column(nullable = false)
private BigDecimal precoProduto;
```

- @NotNull: Garante que o campo não pode ser nulo. <br> </br>

- @Positive: O valor deve ser maior que zero. <br> </br>

- BigDecimal: Tipo ideal para representar valores monetários com precisão. <br> </br>

### Data de Registro do Produto

```java
@CreationTimestamp
@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
@Column(nullable = false, updatable = false)
private LocalDateTime dataRegistroProduto;
```

- @CreationTimestamp: O valor será preenchido automaticamente com a data e hora da criação do registro (Hibernate). <br> </br>

- @JsonFormat(...): Define o formato da data ao converter para JSON (ex: "19/05/2025 14:23:00"). <br> </br>

- @Column(nullable = false, updatable = false): <br> </br>

  - Campo obrigatório no banco (nullable = false). <br> </br>

  - Não pode ser alterado depois de criado (updatable = false). <br> </br>