/* 'use client' habilita hooks e estado no componente */
'use client'
import { useEffect, useState } from 'react'
import api from '@/services/apiRouter'

type Entregador = { id: number; nome: string; cpf: string; placa: string }

export default function EntregadoresPage() {
  const [entregadores, setEntregadores] = useState<Entregador[]>([])
  const [nome,  setNome]  = useState('')
  const [cpf,   setCpf]   = useState('')
  const [placa, setPlaca] = useState('')

  // Carrega lista ao abrir a página
  useEffect(() => {
    api.get<Entregador[]>('entregadores')
       .then(r => setEntregadores(r.data))
       .catch(err => alert('Erro ao listar entregadores: ' + err))
  }, [])

  // Envia novo entregador
  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    try {
      await api.post('entregadores', { nome, cpf, placa })
      alert('Entregador cadastrado!')
      setNome(''); setCpf(''); setPlaca('')

      const r = await api.get<Entregador[]>('entregadores')
      setEntregadores(r.data)
    } catch (err) {
      alert('Falha ao criar entregador: ' + err)
    }
  }

  return (
    <main className="p-6 space-y-8">
      <h1 className="text-2xl font-bold">Entregadores</h1>

      {/* Formulário */}
      <form onSubmit={handleSubmit} className="space-y-2 max-w-md">
        <input className="input" placeholder="Nome"  value={nome}  onChange={e => setNome(e.target.value)} />
        <input className="input" placeholder="CPF"   value={cpf}   onChange={e => setCpf(e.target.value)} />
        <input className="input" placeholder="Placa" value={placa} onChange={e => setPlaca(e.target.value)} />
        <button className="btn-primary">Salvar</button>
      </form>

      {/* Tabela */}
      <table className="w-full text-left border mt-6">
        <thead><tr><th>ID</th><th>Nome</th><th>CPF</th><th>Placa</th></tr></thead>
        <tbody>
          {entregadores.map(e => (
            <tr key={e.id} className="border-t">
              <td>{e.id}</td><td>{e.nome}</td><td>{e.cpf}</td><td>{e.placa}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </main>
  )
}
