import apiRouter from './apiRouter'

export interface CodigoValidacao {
  id: number
  codigoVerificacao: string
  status: string
  usuario: { id: number, nome: string }
  entregador: { id: number, nome: string }
}

// Listar todos os códigos
export async function listarCodigos(): Promise<CodigoValidacao[]> {
  const res = await apiRouter.get('/validacao/todos')
  return res.data
}

// Gerar um novo código entre um usuário e um entregador
export async function gerarCodigo(usuarioId: number, entregadorId: number) {
  const res = await apiRouter.post(`/validacao/gerar/${usuarioId}/${entregadorId}`)
  return res.data
}

// Validar se um código é válido para o usuário e entregador
export async function validarCodigo(codigo: string, usuarioId: number, entregadorId: number) {
  const res = await apiRouter.get('/validacao/validar', {
    params: { codigo, usuarioId, entregadorId }
  })
  return res.data
}

// Concluir uma validação (quando o código for confirmado pelas duas partes)
export async function concluirCodigo(codigo: string, usuarioId: number, entregadorId: number) {
  const res = await apiRouter.put('/validacao/concluir', null, {
    params: { codigo, usuarioId, entregadorId }
  })
  return res.data
}

export async function excluirValidacao(id: number) {
  const res = await apiRouter.delete(`/validacao/excluir/${id}`)
  return res.data
}