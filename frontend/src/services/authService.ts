import apiRouter from "./apiRouter";

export const Login = async (email: string, senha: string) => {
    try {
        const response = await apiRouter.post("auth/login", { email, senha });
        const token = response.data.token;
        localStorage.setItem("token", token);
        return response.data;
    } catch (error) {
        if (
            error instanceof Error &&
            error.name === "AxiosError" &&
            typeof (error as any).response !== "undefined" &&
            (error as any).response.status === 401
        ) {
            console.error("Erro ao fazer login:", error);
            throw new Error("Erro ao fazer login. Verifique se o email e a senha est o corretos. Caso n o tenha um cadastro, por favor, cadastre-se.");
        }
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
            console.error("Erro de rede ao registrar usu rio:", error);
            throw new Error("Erro de rede ao registrar usu rio. Verifique sua conex o com a internet e tente novamente.");
        }
        console.error("Erro ao registrar usu rio:", error);
        throw error;
    }
};

