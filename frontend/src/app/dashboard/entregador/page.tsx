/* 'use client' habilita hooks e estado no componente */
'use client'
import { useEffect, useState } from 'react'
import api from '@/services/apiRouter'

type Entregador = {
  id: number
  nome: string
  email: string
  placa: string
  fotoUrl: string
  empresa: string
}

export default function EntregadoresPage() {
  const [entregadores, setEntregadores] = useState<Entregador[]>([])

  /* estados controlados do formulário */
  const [nome,     setNome]     = useState('')
  const [email,    setEmail]    = useState('')
  const [placa,    setPlaca]    = useState('')
  const [fotoUrl,  setFotoUrl]  = useState('')
  const [empresa,  setEmpresa]  = useState('')

  /* carrega lista ao abrir a página */
  useEffect(() => {
    api.get<Entregador[]>('entregadores')
       .then(r => setEntregadores(r.data))
       .catch(err => alert('Erro ao listar entregadores: ' + err))
  }, [])

  /* envia novo entregador */
  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    try {
      await api.post('entregadores', { nome, email, placa, fotoUrl, empresa })
      alert('Entregador cadastrado!')

      /* limpa formulário */
      setNome(''); setEmail(''); setPlaca(''); setFotoUrl(''); setEmpresa('')

      /* atualiza lista */
      const r = await api.get<Entregador[]>('entregadores')
      setEntregadores(r.data)
    } catch (err) {
      alert('Falha ao criar entregador: ' + err)
    }
  }

  return (
    <main className="p-6 space-y-8">
      <h1 className="text-2xl font-bold">Entregadores</h1>

      {/* formulário de cadastro */}
      <form onSubmit={handleSubmit} className="space-y-2 max-w-md">
        <input className="input" placeholder="Nome"     value={nome}     onChange={e => setNome(e.target.value)} />
        <input className="input" placeholder="E-mail"   value={email}    onChange={e => setEmail(e.target.value)} />
        <input className="input" placeholder="Placa"    value={placa}    onChange={e => setPlaca(e.target.value)} />
        <input className="input" placeholder="Foto URL" value={fotoUrl}  onChange={e => setFotoUrl(e.target.value)} />
        <input className="input" placeholder="Empresa"  value={empresa}  onChange={e => setEmpresa(e.target.value)} />

        <button className="btn-primary">Salvar</button>
      </form>

      {/* tabela de listagem */}
      <table className="w-full text-left border mt-6">
        <thead>
          <tr>
            <th>ID</th><th>Nome</th><th>E-mail</th><th>Placa</th><th>Empresa</th><th>Foto</th>
          </tr>
        </thead>
        <tbody>
          {entregadores.map(e => (
            <tr key={e.id} className="border-t">
              <td>{e.id}</td>
              <td>{e.nome}</td>
              <td>{e.email}</td>
              <td>{e.placa}</td>
              <td>{e.empresa}</td>
              <td>
                {e.fotoUrl && (
                  <img src={e.fotoUrl} alt={e.nome} className="h-10 w-10 rounded-full object-cover" />
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </main>
  )
}
