"use client";

import "@/styles/auth.css";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { Login, Register } from "../services/authService";
import Cookies from "js-cookie";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export default function AuthPage() {
  const router = useRouter();

  const [loginEmail, setLoginEmail] = useState("");
  const [loginPassword, setLoginPassword] = useState("");
  const [registerNome, setRegisterNome] = useState("");
  const [registerEmail, setRegisterEmail] = useState("");
  const [registerPassword, setRegisterPassword] = useState("");

  useEffect(() => {
    const token = Cookies.get("token");
    if (token) {
      router.replace("/dashboard");
    }
  }, [router]);

  useEffect(() => {
    const container = document.getElementById("container");
    const registerBtn = document.getElementById("register");
    const loginBtn = document.getElementById("login");

    const handleRegisterClick = () => {
      container?.classList.add("active");
      toast.dismiss();
    };

    const handleLoginClick = () => {
      container?.classList.remove("active");
      toast.dismiss();
    };

    registerBtn?.addEventListener("click", handleRegisterClick);
    loginBtn?.addEventListener("click", handleLoginClick);

    return () => {
      registerBtn?.removeEventListener("click", handleRegisterClick);
      loginBtn?.removeEventListener("click", handleLoginClick);
    };
  }, []);

  const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    toast.dismiss();

    if (!loginEmail || !loginPassword) {
      toast.warn("Preencha email e senha para entrar.", {
        position: "top-right",
        autoClose: 3000,
      });
      return;
    }

    try {
      const response = await Login(loginEmail, loginPassword);

      if (response && response.token) {
        localStorage.setItem("auth_user", response.nome);
        localStorage.setItem("token", response.token);
        Cookies.set("token", response.token);

        toast.success("Login realizado com sucesso!", {
          position: "top-right",
          autoClose: 2000,
          onClose: () => router.push("/dashboard"),
        });
      } else {
        toast.error("Email ou senha inválidos. Por favor, tente novamente.", {
          position: "top-right",
          autoClose: 3000,
        });
      }
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || err.message || "Erro ao fazer login. Tente novamente mais tarde.";
      toast.error(errorMessage, {
        position: "top-right",
        autoClose: 3000,
      });
    }
  };

  const handleRegister = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    toast.dismiss();

    if (!registerEmail || !registerPassword || !registerNome) {
      toast.warn("Por favor, preencha todos os campos para o cadastro.", {
        position: "top-right",
        autoClose: 3000,
      });
      return;
    }

    try {
      await Register(registerEmail, registerPassword, registerNome);

      setRegisterEmail("");
      setRegisterPassword("");
      setRegisterNome("");

      toast.success("Cadastro realizado com sucesso! Faça login para continuar.", {
        position: "top-right",
        autoClose: 3000,
      });

      document.getElementById("login")?.click();
    } catch (err: any) {
      const errorMessage =
        err.response?.data?.message || err.message || "Erro ao registrar usuário. Tente novamente mais tarde.";
      toast.error(errorMessage, {
        position: "top-right",
        autoClose: 3000,
      });
    }
  };

  return (
    <div className="container" id="container">
      {/* Formulário de Cadastro */}
      <div className="form-container sign-up">
        <form onSubmit={handleRegister}>
          <h1 className="text-2xl">Criar Conta</h1>
          <span>Informe seus dados para criar uma conta</span>
          <input
            type="text"
            placeholder="Nome Completo"
            value={registerNome}
            onChange={(e) => setRegisterNome(e.target.value)}
          />
          <input
            type="email"
            placeholder="Email"
            value={registerEmail}
            onChange={(e) => setRegisterEmail(e.target.value)}
          />
          <input
            type="password"
            placeholder="Senha"
            value={registerPassword}
            onChange={(e) => setRegisterPassword(e.target.value)}
          />
          <button type="submit">Cadastrar</button>
        </form>
      </div>

      {/* Formulário de Login */}
      <div className="form-container sign-in">
        <form onSubmit={handleLogin}>
          <h1 className="text-2xl">Entrar</h1>
          <span>Informe seu email e senha para entrar</span>
          <input
            type="email"
            placeholder="Email"
            value={loginEmail}
            onChange={(e) => setLoginEmail(e.target.value)}
          />
          <input
            type="password"
            placeholder="Senha"
            value={loginPassword}
            onChange={(e) => setLoginPassword(e.target.value)}
          />
          <button type="submit">Entrar</button>
        </form>
      </div>

      {/* Alternância de painel */}
      <div className="toggle-container">
        <div className="toggle">
          <div className="toggle-panel toggle-left">
            <h1 className="text-2xl">Bem-vindo de Volta!</h1>
            <p>Insira seus dados pessoais para usar todos os recursos do site</p>
            <button id="login">
              Entrar
            </button>
          </div>
          <div className="toggle-panel toggle-right">
            <h1 className="text-2xl">Olá, Amigo!</h1>
            <p>Registre-se com seus dados pessoais para usar todos os recursos do site</p>
            <button id="register">
              Cadastrar
            </button>
          </div>
        </div>
      </div>

      <ToastContainer />
    </div>
  );
}

