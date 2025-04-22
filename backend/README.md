
# 📦 API de Validação de Entregas

## 📃 Visão Geral
API responsável por gerar, validar e concluir validações de entregas entre usuários e entregadores, prevenindo golpes de falsos entregadores.

## 📌 Endpoints

### 🔹 POST /api/validacao/gerar/{usuarioId}/{entregadorId}
Gera um código de validação associando um usuário e um entregador.

**Resposta de sucesso (201):**
```json
{
  "id": 1,
  "codigoVerificacao": "A1B2C3",
  "dataHora": "2025-04-16T14:35:00",
  "status": "PENDENTE",
  "usuario": { ... },
  "entregador": { ... }
}
```

**Erros:**
- 404 — Usuário ou entregador não encontrado.

---

### 🔹 GET /api/validacao/validar?codigo=XXXXXX&usuarioId=1&entregadorId=2
Valida se o código enviado confere com o usuário e o entregador.

**Resposta de sucesso (200):**
```
Código válido!
```

**Erro (404):**
```
Código inválido ou não localizado
```

---

### 🔹 PUT /api/validacao/concluir?codigo=XXXXXX&usuarioId=1&entregadorId=2
Conclui a validação e atualiza o status.

**Resposta de sucesso (200):**
```
Validação concluída com sucesso!
```

**Erro (400):**
```
Código inválido! Suspeita de golpe registrada.
```

## 📦 Models envolvidas:
- Usuario
- Entregador
- ValidacaoEntrega

## 📋 Tecnologias:
- Spring Boot
- JPA/Hibernate
- MySQL ou PostgreSQL (adaptável)
