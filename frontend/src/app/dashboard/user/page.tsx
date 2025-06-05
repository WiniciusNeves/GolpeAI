'use client'

import { useEffect, useState } from 'react'
import { listarUsuarios, cadastrarUsuario, atualizarUsuario, excluirUsuario } from '@/services/userService'
import { PencilIcon, TrashIcon } from 'lucide-react'

type Usuario = {
  id: number
  nome: string
  email: string
  senha: string
  endereco: string
  telefone: string
}

export default function UsersPage() {
  const [users, setUsers] = useState<Usuario[]>([])
  const [nome, setNome] = useState<string>('')
  const [email, setEmail] = useState<string>('')
  const [senha, setSenha] = useState<string>('')
  const [endereco, setEndereco] = useState<string>('')
  const [telefone, setTelefone] = useState<string>('')
  const [editId, setEditId] = useState<number | null>(null)

  useEffect(() => {
    fetchUsuarios()
  }, [])

  async function fetchUsuarios() {
    try {
      const res = await listarUsuarios()
      setUsers(res.sort((a: Usuario, b: Usuario) => a.id - b.id))
    } catch (err) {
      alert('Erro ao listar usuários: ' + err)
    }
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    try {
      if (editId) {
        await atualizarUsuario(editId, email, senha, nome, endereco, telefone)
        alert('Usuário atualizado!')
      } else {
        await cadastrarUsuario(email, senha, nome, endereco, telefone)
        alert('Usuário criado!')
      }
      resetForm()
      await fetchUsuarios()
    } catch (err) {
      alert('Erro ao salvar usuário: ' + err)
    }
  }

  async function handleDelete(id: number) {
    if (confirm('Tem certeza que deseja excluir este usuário?')) {
      try {
        await excluirUsuario(id)
        alert('Usuário excluído com sucesso!')
        await fetchUsuarios()
      } catch (err) {
        alert('Erro ao excluir usuário: ' + err)
      }
    }
  }

  function handleEdit(user: Usuario) {
    setEditId(user.id)
    setNome(user.nome)
    setEmail(user.email)
    setSenha('') // Senha em branco para segurança
    setEndereco(user.endereco)
    setTelefone(user.telefone)
  }

  function resetForm() {
    setEditId(null)
    setNome('')
    setEmail('')
    setSenha('')
    setEndereco('')
    setTelefone('')
  }

  return (
    <main className="p-6 max-w-6xl mx-auto space-y-8">
      <h1 className="text-3xl font-semibold text-gray-800">Usuários</h1>

      {/* Formulário */}
      <form onSubmit={handleSubmit} className="grid gap-4 bg-white p-6 rounded-2xl shadow">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Input label="Nome" value={nome} onChange={setNome} />
          <Input label="E-mail" value={email} onChange={setEmail} />
          <Input label="Senha" type="password" value={senha} onChange={setSenha} />
          <Input label="Telefone" value={telefone} onChange={setTelefone} />
          <Input label="Endereço" value={endereco} onChange={setEndereco} className="md:col-span-2" />
        </div>
        <div className="flex justify-between gap-4">
          <button
            type="submit"
            className="flex-1 bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 rounded-lg transition"
          >
            {editId ? 'Atualizar' : 'Salvar'}
          </button>
          {editId && (
            <button
              type="button"
              onClick={resetForm}
              className="flex-1 bg-gray-300 hover:bg-gray-400 text-gray-800 font-medium py-2 rounded-lg transition"
            >
              Cancelar
            </button>
          )}
        </div>
      </form>

      {/* Tabela */}
      <div className="overflow-x-auto bg-white rounded-2xl shadow p-4">
        <table className="w-full text-left border-collapse">
          <thead className="bg-gray-100">
            <tr>
              <th className="p-3 font-medium text-gray-600">ID</th>
              <th className="p-3 font-medium text-gray-600">Nome</th>
              <th className="p-3 font-medium text-gray-600">E-mail</th>
              <th className="p-3 font-medium text-gray-600">Telefone</th>
              <th className="p-3 font-medium text-gray-600">Endereço</th>
              <th className="p-3 font-medium text-gray-600">Ações</th>
            </tr>
          </thead>
          <tbody>
            {users.map((u) => (
              <tr key={u.id} className="border-t hover:bg-gray-50">
                <td className="p-3">{u.id}</td>
                <td className="p-3">{u.nome}</td>
                <td className="p-3">{u.email}</td>
                <td className="p-3">{u.telefone}</td>
                <td className="p-3">{u.endereco}</td>
                <td className="p-3 space-x-2">
                  <button
                    onClick={() => handleEdit(u)}
                    className="text-blue-600 hover:underline"
                  >
                    <PencilIcon className="inline-block w-5 h-5" />
                  </button>
                  <button
                    onClick={() => handleDelete(u.id)}
                    className="text-red-600 hover:underline"
                  >
                    <TrashIcon className="inline-block w-5 h-5" />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </main>
  )
}

function Input({
  label,
  value,
  onChange,
  type = 'text',
  className = ''
}: {
  label: string
  value: string
  onChange: (v: string) => void
  type?: string
  className?: string
}) {
  return (
    <div className={`flex flex-col ${className}`}>
      <label className="text-sm font-medium text-gray-700 mb-1">{label}</label>
      <input
        type={type}
        value={value}
        onChange={(e) => onChange(e.target.value)}
        className="border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
      />
    </div>
  )
}


