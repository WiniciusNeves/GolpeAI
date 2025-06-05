import apiRouter from "./apiRouter";

export async function listarEntregadores() {
    const res = await apiRouter.get("/entregadores");
    return res.data;
}
export async function buscarEntregador(id : number) {
    const res = await apiRouter.get(`/entregadores/${id}`);
    return res.data;    
}

export async function cadastrarEmtregador( nome : string, email : string, placa : string, empresa : string, fotoUrl : string) {
    const res = await apiRouter.post("/entregadores", { nome, email, placa, empresa, fotoUrl });
    return res.data;
}

export async function atualizarEntregador(id : number, nome : string, email : string, placa : string, empresa : string, fotoUrl : string) {
    const res = await apiRouter.put(`/entregadores/${id}`, { nome, email, placa, empresa, fotoUrl });
    return res.data;
}

export async function excluirEntregador(id : number) {
    const res = await apiRouter.delete(`/entregadores/${id}`);
    return res.data;
}