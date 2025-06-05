'use client'
import { useEffect, useState } from 'react'
import { listarUsuarios } from '@/services/userService'
import { listarEntregadores } from '@/services/entregadorService'
import { gerarCodigo } from '@/services/validacaoService'

type Usuario = { id: number; nome: string }
type Entregador = { id: number; nome: string }

export default function AuthCodePage() {
  const [users, setUsers] = useState<Usuario[]>([])
  const [ents, setEnts] = useState<Entregador[]>([])
  const [userId, setUserId] = useState<number>()
  const [entId, setEntId] = useState<number>()
  const [codigo, setCodigo] = useState<string>('')

  useEffect(() => {
    async function carregarDados() {
      try {
        const [usuarios, entregadores] = await Promise.all([
          listarUsuarios(),
          listarEntregadores()
        ])
        setUsers(usuarios)
        setEnts(entregadores)
      } catch (err) {
        alert('Erro ao carregar dados: ' + err)
      }
    }

    carregarDados()
  }, [])

  async function gerar() {
    if (!userId || !entId) {
      alert('Selecione usuário e entregador')
      return
    }

    try {
      const resposta = await gerarCodigo(userId, entId)
      setCodigo(resposta.codigoVerificacao)
    } catch (err) {
      alert('Erro ao gerar código: ' + err)
    }
  }

  return (
    <main className="">
      <div className="w-full max-w-md bg-white rounded-2xl shadow-lg p-8 space-y-6">
        <h1 className="text-3xl font-bold text-center text-gray-800">Gerar Código</h1>

        {/* Select Usuário */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Usuário</label>
          <select
            className="w-full px-4 py-2 border border-gray-300 rounded-xl shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            onChange={e => setUserId(Number(e.target.value))}
            defaultValue=""
          >
            <option value="" disabled>Selecione um usuário</option>
            {users.map(u => (
              <option key={u.id} value={u.id}>{u.nome}</option>
            ))}
          </select>
        </div>

        {/* Select Motoboy */}
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Motoboy</label>
          <select
            className="w-full px-4 py-2 border border-gray-300 rounded-xl shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            onChange={e => setEntId(Number(e.target.value))}
            defaultValue=""
          >
            <option value="" disabled>Selecione um motoboy</option>
            {ents.map(e => (
              <option key={e.id} value={e.id}>{e.nome}</option>
            ))}
          </select>
        </div>

        {/* Botão */}
        <button
          onClick={gerar}
          className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 rounded-xl transition duration-200 shadow-md"
        >
          Gerar Código
        </button>

        {/* Código */}
        {codigo && (
          <div className="text-center mt-4">
            <p className="text-sm text-gray-600">Código de verificação:</p>
            <p className="text-2xl font-mono font-bold text-green-600">{codigo}</p>
          </div>
        )}
      </div>
    </main>
  )
}
