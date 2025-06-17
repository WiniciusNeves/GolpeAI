import apiRouter from './apiRouter'

export interface CodigoValidacao {
  id: number
  codigoVerificacao: string
  status: string
  usuario: {
    endereco: string
    email: string
    id: number
    nome: string
    telefone: string
  }
  entregador: {
    fotoUrl: string
    empresa: string
    placa: string
    email: string
    id: number
    nome: string
  }
}

// Listar todos os códigos
export async function listarCodigos(): Promise<CodigoValidacao[]> {
  const res = await apiRouter.get('validacao/')
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

// Buscar validação por ID (sem enviar parâmetros extras desnecessários)
export async function getValidacaoById(id: number | string) {
  const res = await apiRouter.get(`/validacao/${id}`)
  return res.data
}

// Concluir uma validação (quando o código for confirmado pelas duas partes)
export async function concluirCodigo(codigo: string, usuarioId: number, entregadorId: number) {
  const res = await apiRouter.put('/validacao/concluir', null, {
    params: { codigo, usuarioId, entregadorId }
  })
  return res.data
}

// Excluir uma validação pelo ID
export async function excluirValidacao(id: number) {
  const res = await apiRouter.delete(`/validacao/excluir/${id}`)
  return res.data
}

// Denunciar tentativa de golpe
export async function denunciarGolpe({
  codigo,
  usuarioId,
  entregadorId,
  motivo
}: {
  codigo: string,
  usuarioId: number,
  entregadorId: number,
  motivo: string
}) {
  const res = await apiRouter.post('/validacao/denunciar-golpe', {
    codigo,
    usuarioId,
    entregadorId,
    motivo
  });
  return res.data;
}
