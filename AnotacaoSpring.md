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
getters, setters, toString(), equals() e hashCode(). <br> </br>

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

- @Column(nullable = false): Campo obrigatório no banco de dados.

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

- BigDecimal: Tipo ideal para representar valores monetários com precisão.

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

  - Não pode ser alterado depois de criado (updatable = false).

---

# Classe Service - ProdutoService

```java
@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;
    
    // métodos...
}
```

🔹 @Service (Spring)

- Indica que a classe é um serviço, um componente da camada de negócio da aplicação. <br> </br>

- Permite que o Spring faça a injeção automática dessa classe onde for necessário.

🔹 @Autowired

- Injeta automaticamente o bean ProdutoRepository criado pelo Spring Data JPA para acessar o banco de dados. <br> </br>

- Permite usar o repositório para operações CRUD no banco.

### Cadastrar Produto

```java
public Produto cadastrarProduto(Produto produto) {
    return produtoRepository.save(produto);
}
```

- Salva um novo produto no banco de dados. <br> </br>

- Retorna o produto salvo, já com o ID gerado.

### Listar todos os produtos

```java
public List<Produto> listarTodosProdutos() {
    return produtoRepository.findAll();
}
```

- Busca e retorna todos os produtos cadastrados no banco.

### Deletar produto por ID

```java
public void deletarProdutoPorId(Long id) {
    Optional<Produto> optionalProduto = produtoRepository.findById(id);
    if (optionalProduto.isEmpty()) {
        throw new ProdutoException(id);
    }
    
    produtoRepository.deleteById(id);
}
```

- Verifica se o produto existe no banco pelo ID. <br> </br>

- Se não existir, lança uma exceção personalizada ProdutoException. <br> </br>

- Caso exista, deleta o produto.

### Buscar produto por ID

```java
public Produto buscarProdutoPorId(Long id) {
    Optional<Produto> optionalProduto = produtoRepository.findById(id);
    if (optionalProduto.isEmpty()) {
        throw new ProdutoException(id);
    }
    
    return optionalProduto.get();
}
```

- Busca um produto pelo seu ID. <br> </br>

- Se não encontrar, lança a exceção ProdutoException. <br> </br>

- Caso encontre, retorna o produto.

### Atualizar produto por ID

```java
public Produto atualizarProdutoPorId(Long id, Produto produtoAtualizado) {
    Optional<Produto> optionalProduto = produtoRepository.findById(id);
    if (optionalProduto.isEmpty()) {
        throw new ProdutoException(id);
    }
    
    Produto produto = optionalProduto.get();
    
    produto.setNomeProduto(produtoAtualizado.getNomeProduto());
    produto.setMarcaProduto(produtoAtualizado.getMarcaProduto());
    produto.setModeloProduto(produtoAtualizado.getModeloProduto());
    produto.setEspecificacaoProduto(produtoAtualizado.getEspecificacaoProduto());
    produto.setPrecoProduto(produtoAtualizado.getPrecoProduto());
    return produtoRepository.save(produto);
}
```

- Verifica se o produto a ser atualizado existe. <br> </br>

- Se não existir, lança exceção. <br> </br>

- Caso exista, atualiza os atributos do produto e salva a alteração no banco. <br> </br>

- Retorna o produto atualizado.

---

# Classe de Exceção - ProdutoException

```java
public class ProdutoException extends RuntimeException {
    public ProdutoException(Long id) {
        super("Produto com ID " + id + " não encontrado.");
    }
}
```

🔹 Extensão de RuntimeException

- A classe herda de RuntimeException, que é uma exceção não verificada (unchecked). <br> </br>

- Não é obrigatório tratar ou declarar essa exceção nos métodos, simplificando o código. <br> </br>

- Ideal para situações em que a falha indica erro de lógica, como tentar acessar um produto inexistente.

🔹 Construtor personalizado

- Recebe o id do produto que causou a exceção. <br> </br>

- Passa uma mensagem personalizada para a superclasse com o texto:
"Produto com ID " + id + " não encontrado." <br> </br>

- Isso facilita o entendimento do erro ao ser lançado e exibido no log ou para o usuário.