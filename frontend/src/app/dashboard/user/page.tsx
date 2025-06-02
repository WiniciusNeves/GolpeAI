/* 'use client' habilita hooks e estado no componente */
'use client'

import { useEffect, useState } from 'react'
import api from '@/services/apiRouter'

/** Definição rápida do objeto que o backend devolve */
type Usuario = {
  id: number
  nome: string
  email: string
  senha: string
  endereco: string
  telefone: string
}

export default function UsersPage() {
  /* lista vinda do backend */
  const [users, setUsers] = useState<Usuario[]>([])

  /* estados do formulário */
  const [nome, setNome]         = useState('')
  const [email, setEmail]       = useState('')
  const [senha, setSenha]       = useState('')
  const [endereco, setEndereco] = useState('')
  const [telefone, setTelefone] = useState('')

  /* 1. Carrega a lista quando a página abre */
  useEffect(() => {
    api.get<Usuario[]>('usuarios')
       .then(r => setUsers(r.data))
       .catch(err => alert('Erro ao listar usuários: ' + err))
  }, [])

  /* 2. Envia novo usuário ao backend */
  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    try {
      await api.post('usuarios', { nome, email, senha, endereco, telefone })
      alert('Usuário criado!')

      /* limpa o formulário */
      setNome(''); setEmail(''); setSenha(''); setEndereco(''); setTelefone('')

      /* recarrega a lista */
      const r = await api.get<Usuario[]>('usuarios')
      setUsers(r.data)
    } catch (err) {
      alert('Falha ao criar usuário: ' + err)
    }
  }

  return (
    <main className="p-6 space-y-8">
      <h1 className="text-2xl font-bold">Usuários</h1>

      {/* ---------- Formulário ---------- */}
      <form onSubmit={handleSubmit} className="space-y-3 max-w-md bg-white shadow rounded-xl p-6">
        <input  className="input" placeholder="Nome"      value={nome}      onChange={e => setNome(e.target.value)} />
        <input  className="input" placeholder="E-mail"    value={email}     onChange={e => setEmail(e.target.value)} />
        <input  className="input" placeholder="Senha"     type="password" value={senha} onChange={e => setSenha(e.target.value)} />
        <input  className="input" placeholder="Endereço"  value={endereco}  onChange={e => setEndereco(e.target.value)} />
        <input  className="input" placeholder="Telefone"  value={telefone}  onChange={e => setTelefone(e.target.value)} />
        <button className="btn-primary w-full">Salvar</button>
      </form>

      {/* ---------- Tabela ---------- */}
      <div className="overflow-x-auto">
        <table className="w-full text-left border border-slate-200">
          <thead className="bg-slate-100 sticky top-0">
            <tr>
              <th>ID</th><th>Nome</th><th>E-mail</th><th>Telefone</th><th>Endereço</th>
            </tr>
          </thead>
          <tbody className="[&>tr:nth-child(even)]:bg-slate-50">
            {users.map(u => (
              <tr key={u.id} className="border-t">
                <td>{u.id}</td>
                <td>{u.nome}</td>
                <td>{u.email}</td>
                <td>{u.telefone}</td>
                <td>{u.endereco}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </main>
  )
}
