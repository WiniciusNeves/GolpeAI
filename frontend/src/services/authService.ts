import apiRouter from "./apiRouter";

export const Login = async (email: string, senha: string) => {
    try {
        const response = await apiRouter.post("auth/login", { email, senha });
        const token = response.data.token;
        localStorage.setItem("token", token);
        return response.data;
    } catch (error) {
        if (error instanceof Error && error.name === "AxiosError" && error.message === "Network Error") {
            console.error("Erro de rede ao fazer login:", error);
            throw new Error("Erro de rede ao fazer login. Verifique sua conex o com a internet e tente novamente.");
        }
        console.error("Erro ao fazer login:", error);
        throw error;
    }
};

export const Register = async (email: string, senha: string, nome: string) => {
    try {
        const response = await apiRouter.post("auth/register", { email, senha, nome });
        
        return response.data;
    } catch (error) {
        if (error instanceof Error && error.name === "AxiosError" && error.message === "Network Error") {
            console.error("Erro de rede ao registrar usuário:", error);
            throw new Error("Erro de rede ao registrar usuário. Verifique sua conex o com a internet e tente novamente.");
        }
        console.error("Erro ao registrar usuário:", error);
        throw error;
    }
};

