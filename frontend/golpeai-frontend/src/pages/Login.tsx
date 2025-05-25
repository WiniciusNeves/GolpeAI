import { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");

  async function autenticar(e: React.FormEvent) {
    e.preventDefault();
    setErro("");                     // limpa erro anterior
    try {
      const { data } = await api.post("/usuarios/login", { email, senha });
      localStorage.setItem("usuario", JSON.stringify(data)); // guarda sessão
      navigate("/dashboard");        // redireciona (criaremos depois)
    } catch {
      setErro("E-mail ou senha inválidos");
    }
  }

  return (
    <div className="flex items-center justify-center min-h-screen bg-slate-900">
      <form
        onSubmit={autenticar}
        className="w-80 space-y-4 bg-slate-800 p-8 rounded-2xl shadow-xl"
      >
        <h1 className="text-xl font-bold text-center text-slate-100">
          Acessar conta
        </h1>

        <label className="block">
          <span className="text-sm text-slate-200">E-mail</span>
          <input
            type="email"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="w-full mt-1 p-2 rounded bg-slate-700 text-slate-100"
          />
        </label>

        <label className="block">
          <span className="text-sm text-slate-200">Senha</span>
          <input
            type="password"
            required
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
            className="w-full mt-1 p-2 rounded bg-slate-700 text-slate-100"
          />
        </label>

        {erro && <p className="text-red-400 text-sm">{erro}</p>}

        <button
          type="submit"
          className="w-full py-2 rounded bg-emerald-500 hover:bg-emerald-600 text-white font-semibold"
        >
          Entrar
        </button>
      </form>
    </div>
  );
}
