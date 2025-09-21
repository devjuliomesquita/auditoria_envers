# Hibernate Envers | Guia Rápido
---

## 📌 1. Conceito

* O **Hibernate Envers** é um módulo do Hibernate que permite **auditar entidades** (registrar histórico de alterações).
* Ele mantém automaticamente versões de registros em tabelas auxiliares (`*_AUD`).
* É útil para **auditoria, rastreamento, histórico e versionamento** de dados.

---

## 📌 2. Dependência

No Maven/Gradle você precisa incluir o módulo:

```xml
<dependency>
  <groupId>org.hibernate.orm</groupId>
  <artifactId>hibernate-envers</artifactId>
</dependency>
```

---

## 📌 3. Anotações principais

### @Audited

* Marca uma entidade ou atributo para ser auditado.
* Exemplo:

```java
@Entity
@Table(name = "clientes")
@Audited
public class Cliente {
    @Id
    private Long id;
    private String nome;
}
```
* Está anotação pode ser aplicada em:

    * **Classe** → toda a entidade é auditada.
    * **Campo** → apenas o campo específico é auditado.

* Essas são as configurções mais comuns que podem ser utilizadas com `@Audited`:

    * `withModifiedFlag` (boolean) → cria colunas booleanas indicando se o campo foi modificado.
    * `targetAuditMode` (enum) → define se entidades relacionadas também são auditadas (`NOT_AUDITED`, `AUDITED`, `DEFAULT`).
    * `auditParents` (array) → define entidades pais que devem ser auditadas junto.

* Exemplo nos atributos:

```java
@Audited(withModifiedFlag = true)
private String email;
```

### @NotAudited

* Exclui um campo da auditoria. Está anotação serve para evitar que dados sensíveis sejam armazenados
no histórico. 

```java
@NotAudited
private String senha;
```

### @AuditTable

* Permite customizar o nome da tabela de auditoria. Está anotação é útil para seguir padrões de nomenclatura específicos.
É possível passar 3 paramêtros: `value` (nome da tabela), `schema` (esquema) e `catalog` (catálogo).

```java
@Entity
@Table(name = "clientes")
@Audited
@AuditTable(name="clientes_aud", schema="auditoria")
public class Cliente { ... }
```

### @AuditOverride / @AuditOverrides

* Usado em heranças ou embutidos (para sobrescrever comportamento de auditoria).

```java
@AuditOverride(forClass = Endereco.class, name = "cidade", isAudited = false)
```

---

## 📌 4. Estrutura das tabelas geradas

Para cada entidade auditada, o Envers cria:

* Uma tabela `*_AUD` (com as colunas da entidade + metadados).
* Uma tabela `REVINFO` (ou outra customizada) que registra:

    * `REV` → número da revisão.
    * `REVTSTMP` → timestamp.
    * Outros campos que você pode adicionar (usuário, IP, etc).

---

## 📌 5. Customização da revisão

### Revisão padrão

Criada automaticamente (`REVINFO`).

### Revisão customizada

Você pode criar sua própria entidade de revisão:

```java
@Entity
@RevisionEntity(MyRevisionListener.class)
public class CustomRevisionEntity extends DefaultRevisionEntity {
    private String username;
}
```

E capturar informações dinâmicas via Listener:

```java
public class MyRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        CustomRevisionEntity rev = (CustomRevisionEntity) revisionEntity;
        rev.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
```

---

## 📌 6. API para consultas (AuditReader)

Com `AuditReader` você pode:

```java
AuditReader reader = AuditReaderFactory.get(entityManager);

// Buscar revisões de uma entidade
List<Number> revisoes = reader.getRevisions(Cliente.class, clienteId);

// Recuperar entidade em uma revisão específica
Cliente c = reader.find(Cliente.class, clienteId, revisao);

// Buscar todas as versões de uma entidade
List<Object[]> historico = reader.createQuery()
    .forRevisionsOfEntity(Cliente.class, false, true)
    .add(AuditEntity.id().eq(clienteId))
    .addOrder(AuditEntity.revisionNumber().desc())
    .getResultList();
```

---

## 📌 7. Configurações importantes (hibernate.properties ou application.yml)

* **Habilitar auditoria** (normalmente automático ao incluir Envers).
* Algumas configs úteis:

```yaml
spring:
  jpa:
    properties:
      org.hibernate.envers.audit_table_suffix: "_HIST"
      org.hibernate.envers.revision_field_name: "rev_id"
      org.hibernate.envers.revision_type_field_name: "rev_type"
      org.hibernate.envers.store_data_at_delete: true
```

🔹 **Mais comuns:**

* `audit_table_suffix` → define sufixo para tabelas de auditoria.
* `store_data_at_delete` → guarda o estado completo no delete.
* `modified_flag_suffix` → gera colunas booleanas indicando quais campos foram alterados.

---

## 📌 8. Boas práticas

* Não auditar colunas sensíveis (senhas, tokens).
* Usar **entidade de revisão customizada** para guardar usuário/IP.
* Evitar auditar entidades com alto volume de mudanças sem real necessidade.
* Criar índices em colunas mais acessadas nas tabelas de auditoria (`id`, `rev`).

---

👉 Resumindo:
O **mínimo** para usar o Envers é **@Audited**, mas em projetos reais quase sempre se usa:

* **@NotAudited** (para dados sensíveis).
* **@AuditTable** (para nome customizado).
* **Entidade de revisão customizada** (para usuário/IP).
* **AuditReader** (para consultas).

---

Quer que eu monte um **documento estilo guia rápido (cheat sheet)** com exemplos prontos de cada anotação/configuração, tipo uma tabela comparativa?
