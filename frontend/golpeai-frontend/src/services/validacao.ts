import api from "./api";

export interface CodigoEntrega {
  id: number;
  codigoVerificacao: string;
  entregador: { id: number; nome: string };
  status: string;
  dataHora: string;
}

export async function listarCodigos(usuarioId: number) {
  const { data } = await api.get<CodigoEntrega[]>(`/validacao/usuario/${usuarioId}`);
  return data;
}

export async function gerarCodigo(usuarioId: number, entregadorId: number) {
  const { data } = await api.post<CodigoEntrega>(
    `/validacao/gerar/${usuarioId}/${entregadorId}`
  );
  return data;
}
