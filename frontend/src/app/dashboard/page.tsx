/* 'use client' habilita hooks e estado no componente */
'use client'
import { useEffect, useState } from 'react'
import api from '@/services/apiRouter'

type Usuario = { id: number; nome: string; email: string }

export default function UsersPage() {
  const [users, setUsers] = useState<Usuario[]>([])
  const [nome, setNome]   = useState('')
  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('')

  /** Carrega usuários assim que a página abrir */
  useEffect(() => {
    api.get<Usuario[]>('usuarios')
       .then(r => setUsers(r.data))
       .catch(err => alert('Erro ao listar usuários: ' + err))
  }, [])

  /** Envia novo usuário para o backend */
  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    try {
      await api.post('usuarios', { nome, email, senha })
      alert('Usuário criado!')
      setNome(''); setEmail(''); setSenha('')
      /** Atualiza lista na tela */
      const r = await api.get<Usuario[]>('usuarios')
      setUsers(r.data)
    } catch (err) {
      alert('Falha ao criar usuário: ' + err)
    }
  }

  return (
    <main className="p-6 space-y-8">
      <h1 className="text-2xl font-bold">Usuários</h1>

      {/* Formulário simples */}
      <form onSubmit={handleSubmit} className="space-y-2 max-w-md">
        <input className="input" placeholder="Nome"  value={nome}  onChange={e => setNome(e.target.value)} />
        <input className="input" placeholder="E-mail" value={email} onChange={e => setEmail(e.target.value)} />
        <input className="input" placeholder="Senha" type="password" value={senha} onChange={e => setSenha(e.target.value)} />
        <button className="btn-primary">Salvar</button>
      </form>

      {/* Tabela de usuários */}
      <table className="w-full text-left border mt-6">
        <thead><tr><th>ID</th><th>Nome</th><th>E-mail</th></tr></thead>
        <tbody>
          {users.map(u => (
            <tr key={u.id} className="border-t">
              <td>{u.id}</td><td>{u.nome}</td><td>{u.email}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </main>
  )
}
