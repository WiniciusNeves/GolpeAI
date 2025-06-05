'use client'
import { useEffect, useState } from 'react'
import api from '@/services/apiRouter'
import { PencilIcon, TrashIcon } from 'lucide-react'

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

  const [nome, setNome] = useState('')
  const [email, setEmail] = useState('')
  const [placa, setPlaca] = useState('')
  const [fotoUrl, setFotoUrl] = useState('')
  const [empresa, setEmpresa] = useState('')

  const [editId, setEditId] = useState<number | null>(null)

  useEffect(() => {
    fetchEntregadores()
  }, [])

  async function fetchEntregadores() {
    try {
      const r = await api.get<Entregador[]>('entregadores')
      setEntregadores(r.data)
    } catch (err) {
      alert('Erro ao listar entregadores: ' + err)
    }
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    try {
      if (editId !== null) {
        await api.put(`entregadores/${editId}`, { nome, email, placa, fotoUrl, empresa })
        alert('Entregador atualizado!')
      } else {
        await api.post('entregadores', { nome, email, placa, fotoUrl, empresa })
        alert('Entregador cadastrado!')
      }
      clearForm()
      fetchEntregadores()
    } catch (err) {
      alert('Erro ao salvar entregador: ' + err)
    }
  }

  function handleEdit(e: Entregador) {
    setEditId(e.id)
    setNome(e.nome)
    setEmail(e.email)
    setPlaca(e.placa)
    setFotoUrl(e.fotoUrl)
    setEmpresa(e.empresa)
  }

  async function handleDelete(id: number) {
    if (confirm('Tem certeza que deseja excluir?')) {
      try {
        await api.delete(`entregadores/${id}`)
        fetchEntregadores()
      } catch (err) {
        alert('Erro ao excluir: ' + err)
      }
    }
  }

  function clearForm() {
    setEditId(null)
    setNome('')
    setEmail('')
    setPlaca('')
    setFotoUrl('')
    setEmpresa('')
  }

  return (
    <main className="p-6 max-w-6xl mx-auto space-y-8">
      <h1 className="text-3xl font-semibold text-gray-800">Entregadores</h1>

      {/* Formulário */}
      <form onSubmit={handleSubmit} className="grid gap-4 bg-white p-6 rounded-2xl shadow">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Input label="Nome" value={nome} onChange={setNome} />
          <Input label="E-mail" value={email} onChange={setEmail} />
          <Input label="Placa" value={placa} onChange={setPlaca} />
          <Input label="Empresa" value={empresa} onChange={setEmpresa} />
          <Input label="Foto URL" value={fotoUrl} onChange={setFotoUrl} className="md:col-span-2" />
        </div>
        <div className="flex gap-2">
          <button type="submit" className="bg-blue-600 hover:bg-blue-700 text-white font-medium px-4 py-2 rounded-lg transition">
            {editId !== null ? 'Atualizar' : 'Salvar'}
          </button>
          {editId !== null && (
            <button type="button" onClick={clearForm} className="bg-gray-400 hover:bg-gray-500 text-white font-medium px-4 py-2 rounded-lg transition">
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
              <th className="p-3 font-medium text-gray-600">Placa</th>
              <th className="p-3 font-medium text-gray-600">Empresa</th>
              <th className="p-3 font-medium text-gray-600">Foto</th>
              <th className="p-3 font-medium text-gray-600">Ações</th>
            </tr>
          </thead>
          <tbody>
            {entregadores.map((e) => (
              <tr key={e.id} className="border-t hover:bg-gray-50">
                <td className="p-3">{e.id}</td>
                <td className="p-3">{e.nome}</td>
                <td className="p-3">{e.email}</td>
                <td className="p-3">{e.placa}</td>
                <td className="p-3">{e.empresa}</td>
                <td className="p-3">
                  {e.fotoUrl && (
                    <img src={e.fotoUrl} alt={e.nome} className="h-10 w-10 rounded-full object-cover" />
                  )}
                </td>
                <td className="p-3 space-x-2">
                  <button onClick={() => handleEdit(e)} className="text-blue-600 hover:underline">
                    <PencilIcon className="inline-block w-5 h-5" />
                  </button>
                  <button onClick={() => handleDelete(e.id)} className="text-red-600 hover:underline">
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

/** Componente de input reutilizável */
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

