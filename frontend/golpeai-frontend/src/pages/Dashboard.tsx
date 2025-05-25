import { useEffect, useState } from "react";
import { listarCodigos, gerarCodigo } from "../services/validacao";
import type { CodigoEntrega } from "../services/validacao";
import CodeTable from "../components/CodeTable";

export default function Dashboard() {
  const usuario = JSON.parse(localStorage.getItem("usuario")!);
  const [codigos, setCodigos] = useState<CodigoEntrega[]>([]);
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState("");

  const carregar = async () => {
    try {
      setLoading(true);
      setCodigos(await listarCodigos(usuario.id));
    } catch {
      setErro("Falha ao carregar códigos");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregar();
  }, []);

  const gerar = async () => {
    const entregadorId = prompt("ID do entregador:");
    if (!entregadorId) return;
    try {
      await gerarCodigo(usuario.id, Number(entregadorId));
      await carregar();
      alert("Código gerado com sucesso!");
    } catch {
      alert("Erro ao gerar código.");
    }
  };

  return (
    <div className="min-h-screen bg-slate-900 text-slate-100 p-6">
      <h1 className="text-2xl font-bold mb-4">Meus códigos de entrega</h1>

      <button
        onClick={gerar}
        className="mb-4 px-4 py-2 bg-emerald-500 hover:bg-emerald-600 rounded font-semibold"
      >
        + Gerar código
      </button>

      {loading ? (
        <p>Carregando…</p>
      ) : erro ? (
        <p className="text-red-400">{erro}</p>
      ) : (
        <CodeTable codigos={codigos} />
      )}
    </div>
  );
}
