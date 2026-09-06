import apiRouter from "./apiRouter";

export async function listarUsuarios() {
    const res = await apiRouter.get("usuarios");
    return res.data;
}

export async function cadastrarUsuario(email: string, nome: string, endereco: string, telefone: string) {
    const res = await apiRouter.post("usuarios/", { email, nome, endereco, telefone });
    return res.data;

}

export async function atualizarUsuario(id: number, email: string, nome: string, endereco: string, telefone: string) {
    const res = await apiRouter.put(`usuarios/${id}`, { email, nome, endereco, telefone });
    return res.data;
}

export async function excluirUsuario(id: number) {
    const res = await apiRouter.delete(`usuarios/${id}`);
    return res.data;
}


