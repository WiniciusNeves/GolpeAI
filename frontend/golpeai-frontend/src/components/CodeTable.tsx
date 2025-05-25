import type { CodigoEntrega } from "../services/validacao";

interface Props {
  codigos: CodigoEntrega[];
}

export default function CodeTable({ codigos }: Props) {
  if (!codigos.length)
    return <p className="text-slate-400 text-center">Nenhum código gerado.</p>;

  return (
    <table className="w-full text-sm text-slate-200">
      <thead>
        <tr className="bg-slate-700 text-left">
          <th className="p-2">Código</th>
          <th className="p-2">Entregador</th>
          <th className="p-2">Status</th>
          <th className="p-2">Data/Hora</th>
        </tr>
      </thead>
      <tbody>
        {codigos.map((c) => (
          <tr key={c.id} className="odd:bg-slate-800">
            <td className="p-2 font-mono">{c.codigoVerificacao}</td>
            <td className="p-2">{c.entregador?.nome}</td>
            <td className="p-2">{c.status}</td>
            <td className="p-2">{new Date(c.dataHora).toLocaleString()}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
