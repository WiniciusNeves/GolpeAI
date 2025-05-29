// src/app/page.tsx
"use client";

import "@/styles/auth.css";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { Login, Register } from "../services/auth";
import Cookies from "js-cookie";

export default function AuthPage() {
  const router = useRouter();

  // Login state
  const [loginEmail, setLoginEmail] = useState("");
  const [loginPassword, setLoginPassword] = useState("");

  // Register state
  const [registerNome, setRegisterNome] = useState("");
  const [registerEmail, setRegisterEmail] = useState("");
  const [registerPassword, setRegisterPassword] = useState("");

  // Messages
  const [error, setError] = useState<string | null>(null);
  const [message, setMessage] = useState<string | null>(null);

  useEffect(() => {
    const container = document.getElementById("container");
    const registerBtn = document.getElementById("register");
    const loginBtn = document.getElementById("login");

    const handleRegisterClick = () => {
      container?.classList.add("active");
      setError(null);
      setMessage(null);
    };

    const handleLoginClick = () => {
      container?.classList.remove("active");
      setError(null);
      setMessage(null);
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
    setError(null);
    setMessage(null);

    try {
      const response = await Login(loginEmail, loginPassword);

      if (response?.token) {
        localStorage.setItem("auth_user", response.nome);
        localStorage.setItem("token", response.token);
        Cookies.set("token", response.token); // Cookie para controle de sessão

        router.push("/dashboard");
      } else {
        setError("Email ou senha inválidos.");
      }
    } catch (err: any) {
      setError(err.message || "Erro ao fazer login.");
    }
  };

  const handleRegister = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setMessage(null);

    if (!registerEmail || !registerPassword || !registerNome) {
      setError("Por favor, preencha todos os campos.");
      return;
    }

    try {
      await Register(registerEmail, registerPassword, registerNome);

      setRegisterEmail("");
      setRegisterPassword("");
      setRegisterNome("");

      setMessage("Cadastro realizado com sucesso! Faça login para continuar.");
      document.getElementById("login")?.click(); // Ativa aba de login
    } catch (err: any) {
      setError(err.message || "Erro ao registrar usuário.");
    }
  };

  return (
    <div className="container" id="container">
      {/* Cadastro */}
      <div className="form-container sign-up">
        <form onSubmit={handleRegister}>
          <h1>Criar Conta</h1>
          <span>ou use seu email para se registrar</span>
          <input
            type="text"
            placeholder="Nome"
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
          {error && <p style={{ color: "red" }}>{error}</p>}
          {message && <p style={{ color: "green" }}>{message}</p>}
        </form>
      </div>

      {/* Login */}
      <div className="form-container sign-in">
        <form onSubmit={handleLogin}>
          <h1>Entrar</h1>
          <span>ou use seu email e senha</span>
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
          <a href="#">Esqueceu sua senha?</a>
          <button type="submit">Entrar</button>
          {error && <p style={{ color: "red" }}>{error}</p>}
          {message && <p style={{ color: "green" }}>{message}</p>}
        </form>
      </div>

      {/* Alternância de painel */}
      <div className="toggle-container">
        <div className="toggle">
          <div className="toggle-panel toggle-left">
            <h1>Bem-vindo de Volta!</h1>
            <p>Insira seus dados pessoais para usar todos os recursos do site</p>
            <button className="hidden" id="login">Entrar</button>
          </div>
          <div className="toggle-panel toggle-right">
            <h1>Olá, Amigo!</h1>
            <p>Registre-se com seus dados pessoais para usar todos os recursos do site</p>
            <button className="hidden bg-blue-600" id="register">Cadastrar</button>
          </div>
        </div>
      </div>
    </div>
  );
}
