# Anotações relacionadas ao projeto Spring

---

# Classe Modelo - Produto

```java
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "produtos")
public class Produto {
  // atributos...
}

```

🔹 @Data (do Lombok)
- Gera automaticamente:

- getters e setters

- toString()

- equals() e hashCode()

🔸 Objetivo: evitar código repetitivo (boilerplate) e manter a classe limpa e legível.

🔹 @Entity (do JPA)
- Indica que essa classe representa uma entidade do banco de dados.

- Cada instância será um registro (linha) na tabela correspondente.

🔹 @Table(name = "produtos")
- Define o nome da tabela no banco como produtos.

- Sem essa anotação, o nome da tabela seria produto por padrão (nome da classe).

🔹 @NoArgsConstructor (do Lombok)
- Gera automaticamente um construtor sem argumentos.

- Necessário para o JPA criar instâncias da entidade via reflexão.

🔹 @AllArgsConstructor (do Lombok)
- Gera automaticamente um construtor com todos os atributos como parâmetros.

🔹 @Builder (do Lombok)
- Permite criar objetos da classe usando o padrão de projeto Builder:

```java
Produto produto = Produto.builder()
.nomeProduto("Monitor LG")
.marcaProduto("LG")
.modeloProduto("UltraWide")
.precoProduto(new BigDecimal("1999.90"))
.build();
```

### Chave Primária
    
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long idProduto;
```

- @Id: Define que o campo é a chave primária.

- @GeneratedValue(strategy = GenerationType.IDENTITY):
O valor do ID será gerado automaticamente pelo banco de dados (auto-incremento).

### Campos Obrigatórios

```java
@Column(nullable = false)
private String nomeProduto;
```

- @Column(nullable = false): Campo obrigatório no banco de dados.

### Especificações do Produto

```java
@Column(nullable = false, length = 300)
private String especificacaoProduto;
```

- Obrigatório e com limite de até 300 caracteres.

### Proço do Produto

```java
@Column(nullable = false)
private BigDecimal precoProduto;
```

- Valor obrigatório e representado com BigDecimal para precisão financeira.

### Data de Registro do Produto

```java
@CreationTimestamp
@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
@Column(nullable = false, updatable = false)
private LocalDateTime dataRegistroProduto;
```

- @CreationTimestamp: O valor será preenchido automaticamente com a data e hora da criação do registro (Hibernate).

- @JsonFormat(...): Define o formato da data ao converter para JSON (ex: "19/05/2025 14:23:00"). 

- @Column(nullable = false, updatable = false):

  - Campo obrigatório no banco (nullable = false).

  - Não pode ser alterado depois de criado (updatable = false).

---

# Classe DTO - ProdutoDTO

### Finalidade

- A classe ProdutoDTO é usada para transferência de dados entre a aplicação e os consumidores da API (como controladores REST), separando a entidade Produto do que é exposto ou recebido externamente.

### Anotações

🔹 @Data (Lombok)
- Gera automaticamente getters, setters, toString(), equals() e hashCode().

🔹 @Builder (do Lombok)
- Permite criar objetos da classe usando o padrão de projeto Builder:

### Validações

@NotBlank
- Garante que o valor não seja nulo, vazio ou apenas espaços em branco.

- Utilizado nos campos de texto (nomeProduto, marcaProduto, modeloProduto, especificacaoProduto).

@Size(max = 300)
- Limita o tamanho máximo da string especificacaoProduto a 300 caracteres.

@NotNull
- Garante que o campo precoProduto não seja nulo.

@Positive
- Garante que o preço seja maior que zero, evitando valores negativos ou zerados.

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

- Indica que a classe é um serviço, um componente da camada de negócio da aplicação.

- Permite que o Spring faça a injeção automática dessa classe onde for necessário.

🔹 @Autowired

- Injeta automaticamente o bean ProdutoRepository criado pelo Spring Data JPA para acessar o banco de dados.

- Permite usar o repositório para operações CRUD no banco.

### Conversão entre DTO e Entidade

- A classe ProdutoService utiliza dois métodos auxiliares privados para realizar a conversão entre ProdutoDTO (usado na comunicação com a API) e a entidade Produto (persistida no banco de dados):

```java
private Produto paraEntity(ProdutoDTO produtoDTO) {
    return Produto.builder()
          .idProduto(produtoDTO.getIdProduto())
          .nomeProduto(produtoDTO.getNomeProduto())
          .marcaProduto(produtoDTO.getMarcaProduto())
          .modeloProduto(produtoDTO.getModeloProduto())
          .especificacaoProduto(produtoDTO.getEspecificacaoProduto())
          .precoProduto(produtoDTO.getPrecoProduto())
          .build();
}
```
- Converter um objeto ProdutoDTO em uma entidade Produto.
- Transforma os dados recebidos da API (ProdutoDTO) em um objeto pronto para persistência com o builder. <br> </br> <br>

```java
private ProdutoDTO paraDTO(Produto produto) {
    return ProdutoDTO.builder()
          .idProduto(produto.getIdProduto())
          .nomeProduto(produto.getNomeProduto())
          .marcaProduto(produto.getMarcaProduto())
          .modeloProduto(produto.getModeloProduto())
          .especificacaoProduto(produto.getEspecificacaoProduto())
          .precoProduto(produto.getPrecoProduto())
          .build();
}
```
- Converter uma entidade Produto em um objeto ProdutoDTO.
- Transforma os dados persistidos da entidade em um DTO para ser exposto via API.

### Cadastrar Produto

```java
public ProdutoDTO cadastrarProduto(ProdutoDTO produtoDTO) {
    Produto produto = paraEntity(produtoDTO);
    Produto produtoSalvo = produtoRepository.save(produto);
    return paraDTO(produtoSalvo);
}
```

- Converte o DTO em entidade e salva no banco de dados.

- Retorna o produto salvo convertido novamente em DTO.

### Listar todos os produtos

```java
public List<ProdutoDTO> listarTodosProdutos() {
    List<Produto> produtos = produtoRepository.findAll();
    List<ProdutoDTO> produtoDTOs = new ArrayList<>();
    
    for (Produto produto : produtos) {
        produtoDTOs.add(paraDTO(produto));
    }
    
    return produtoDTOs;
}
```

- Busca todos os produtos no banco de dados.

- Converte cada produto da lista para DTO.

- Retorna a lista de DTOs.

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

- Verifica se o produto existe no banco pelo ID.

- Se não existir, lança uma exceção personalizada ProdutoException.

- Caso exista, deleta o produto.

### Buscar produto por ID

```java
public ProdutoDTO buscarProdutoPorId(Long id) {
    Optional<Produto> optionalProduto = produtoRepository.findById(id);
    if (optionalProduto.isEmpty()) {
        throw new ProdutoException(id);
    }
    
    return paraDTO(optionalProduto.get());
}
```

- Verifica se o produto existe pelo ID.

- Se não existir, lança ProdutoException.

- Se existir, converte e retorna o produto em forma de DTO.

### Atualizar produto por ID

```java
public ProdutoDTO atualizarProdutoPorId(Long id, ProdutoDTO produtoDTO) {
    Optional<Produto> optionalProduto = produtoRepository.findById(id);
    if (optionalProduto.isEmpty()) {
        throw new ProdutoException(id);
    }
    
    Produto produto = optionalProduto.get();
    produto.setNomeProduto(produtoDTO.getNomeProduto());
    produto.setMarcaProduto(produtoDTO.getMarcaProduto());
    produto.setModeloProduto(produtoDTO.getModeloProduto());
    produto.setEspecificacaoProduto(produtoDTO.getEspecificacaoProduto());
    produto.setPrecoProduto(produtoDTO.getPrecoProduto());
    Produto produtoSalvo = produtoRepository.save(produto);
    
    return paraDTO(produtoSalvo);
}
```

- Verifica se o produto existe pelo ID.

- Se não existir, lança ProdutoException.

- Se existir: 

  - Atualiza os dados do produto com base no DTO recebido.

  - Salva a alteração no banco. 

  - Retorna o produto atualizado como DTO.

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

- A classe herda de RuntimeException, que é uma exceção não verificada (unchecked).

- Não é obrigatório tratar ou declarar essa exceção nos métodos, simplificando o código.

- Ideal para situações em que a falha indica erro de lógica, como tentar acessar um produto inexistente.

🔹 Construtor personalizado

- Recebe o id do produto que causou a exceção.

- Passa uma mensagem personalizada para a superclasse com o texto:
"Produto com ID " + id + " não encontrado."

- Isso facilita o entendimento do erro ao ser lançado e exibido no log ou para o usuário.