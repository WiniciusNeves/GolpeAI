'use client'

import Link from 'next/link'
import { useEffect, useState } from 'react'
import {
  listarCodigos,
  concluirCodigo,
  excluirValidacao,
  CodigoValidacao
} from '../../services/validacaoService'

import { Eye, CheckCircle, Trash2 } from 'lucide-react'

export default function DashboardHome() {
  const [codigos, setCodigos] = useState<CodigoValidacao[]>([])
  const [loading, setLoading] = useState(true)
  const [concluindoId, setConcluindoId] = useState<number | null>(null)
  const [excluindoId, setExcluindoId] = useState<number | null>(null)

  const fetchCodigos = async () => {
    setLoading(true)
    try {
      const dados = await listarCodigos()
      setCodigos(dados)
    } catch (error) {
      console.error('Erro ao buscar códigos:', error)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchCodigos()
  }, [])

  const handleConcluir = async (codigo: CodigoValidacao) => {
    setConcluindoId(codigo.id)
    try {
      await concluirCodigo(
        codigo.codigoVerificacao,
        codigo.usuario.id,
        codigo.entregador.id
      )
      await fetchCodigos()
    } catch (err) {
      console.error('Erro ao concluir código', err)
    } finally {
      setConcluindoId(null)
    }
  }

  const handleExcluir = async (id: number) => {
    setExcluindoId(id)
    try {
      await excluirValidacao(id)
      await fetchCodigos()
    } catch (err) {
      console.error('Erro ao excluir código', err)
    } finally {
      setExcluindoId(null)
    }
  }

  return (
    <div className="space-y-8">
      <h1 className="text-3xl font-bold text-gray-800">Dashboard</h1>

      <div className="bg-white rounded-2xl shadow p-6">
        <h2 className="text-xl font-semibold mb-4">Códigos Abertos</h2>

        {loading ? (
          <p>Carregando...</p>
        ) : (
          <table className="w-full table-auto text-left border-collapse">
            <thead>
              <tr className="text-gray-600 border-b">
                <th className="py-2">Código</th>
                <th className="py-2">Usuário</th>
                <th className="py-2">Motoboy</th>
                <th className="py-2">Status</th>
                <th className="py-2">Ações</th>
              </tr>
            </thead>
            <tbody>
              {codigos.map((codigo) => (
                <tr key={codigo.id} className="border-b hover:bg-gray-50">
                  <td className="py-2">{codigo.codigoVerificacao}</td>
                  <td className="py-2">{codigo.usuario?.nome || '-'}</td>
                  <td className="py-2">{codigo.entregador?.nome || '-'}</td>
                  <td className="py-2">{codigo.status}</td>
                  <td className="py-2 flex gap-2 flex-wrap">
                    <Link
                      href={`/dashboard/validacao/${codigo.id}`}
                      className="flex items-center gap-1 text-blue-600 hover:underline"
                    >
                      <Eye className="w-4 h-4" />
                      Verificar
                    </Link>

                    {codigo.status === 'PENDENTE' && (
                      <button
                        onClick={() => handleConcluir(codigo)}
                        disabled={concluindoId === codigo.id}
                        className="flex items-center gap-1 text-green-600 hover:underline disabled:opacity-50"
                      >
                        <CheckCircle className="w-4 h-4" />
                        {concluindoId === codigo.id ? 'Concluindo...' : 'Concluir'}
                      </button>
                    )}

                    <button
                      onClick={() => handleExcluir(codigo.id)}
                      disabled={excluindoId === codigo.id}
                      className="flex items-center gap-1 text-red-600 hover:underline disabled:opacity-50"
                    >
                      <Trash2 className="w-4 h-4" />
                      {excluindoId === codigo.id ? 'Excluindo...' : 'Excluir'}
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  )
}
