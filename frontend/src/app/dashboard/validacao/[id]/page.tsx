'use client'

import { useEffect, useState } from 'react'
import { useParams, useRouter } from 'next/navigation'
import {
  getValidacaoById,
  concluirCodigo,
  CodigoValidacao,
  denunciarGolpe,
} from '@/services/validacaoService'
import { CheckCircle, AlertTriangle, ArrowLeft } from 'lucide-react' // importei o ícone de seta

export default function VerificacaoPage() {
  const { id } = useParams()
  const router = useRouter()

  const [validacao, setValidacao] = useState<CodigoValidacao | null>(null)
  const [loading, setLoading] = useState(true)
  const [concluindo, setConcluindo] = useState(false)
  const [denunciando, setDenunciando] = useState(false)
  const [fotoZoom, setFotoZoom] = useState<string | null>(null)

  const abrirFotoZoom = (url: string) => setFotoZoom(url)
  const fecharFotoZoom = () => setFotoZoom(null)

  useEffect(() => {
    const fetchValidacao = async () => {
      setLoading(true)
      try {
        const dados = await getValidacaoById(Number(id))
        setValidacao(dados)
      } catch (err) {
        console.error('Erro ao carregar validação:', err)
      } finally {
        setLoading(false)
      }
    }

    if (id) fetchValidacao()
  }, [id])

  const handleConcluir = async () => {
    if (!validacao) return

    setConcluindo(true)
    try {
      await concluirCodigo(
        validacao.codigoVerificacao,
        validacao.usuario.id,
        validacao.entregador.id
      )
      alert('Entrega concluída com sucesso!')
      router.push('/dashboard')
    } catch (err) {
      console.error('Erro ao concluir entrega:', err)
      alert('Erro ao concluir entrega.')
    } finally {
      setConcluindo(false)
    }
  }

  const handleDenunciarGolpe = async () => {
    if (!validacao) return

    if (validacao.status === 'DENUNCIADO') {
      alert('Este código já foi denunciado e não pode ser denunciado novamente.')
      return
    }

    setDenunciando(true)
    try {
      await denunciarGolpe({
        codigo: validacao.codigoVerificacao,
        usuarioId: validacao.usuario.id,
        entregadorId: validacao.entregador.id,
        motivo: 'Foto, placa ou dados não conferem com o esperado.',
      })

      alert('Tentativa de golpe denunciada! Nosso suporte irá verificar.')
      router.push('/dashboard')
    } catch (err) {
      console.error('Erro ao denunciar golpe:', err)
      alert('Erro ao denunciar golpe.')
    } finally {
      setDenunciando(false)
    }
  }

  if (loading) return <p>Carregando...</p>
  if (!validacao) return <p>Validação não encontrada.</p>

  return (
    <div className="max-w-3xl mx-auto p-6 space-y-6">
      {/* Botão Voltar */}
      <button
        onClick={() => router.back()}
        className="inline-flex items-center gap-2 text-gray-600 hover:text-gray-900 transition-colors"
      >
        <ArrowLeft className="w-5 h-5" />
        Voltar
      </button>

      <h1 className="text-3xl font-bold text-gray-800">Verificação do Código</h1>

      <div className="bg-white p-6 rounded-2xl shadow space-y-4">
        <div>
          <strong className="block text-gray-600">Código de Verificação:</strong>
          <span className="text-lg">{validacao.codigoVerificacao}</span>
        </div>

        <div>
          <strong className="block text-gray-600">Status:</strong>
          <span
            className={`inline-block px-2 py-1 rounded-full text-sm font-semibold ${
              validacao.status === 'PENDENTE'
                ? 'bg-yellow-100 text-yellow-800'
                : validacao.status === 'CONCLUIDO'
                ? 'bg-green-100 text-green-800'
                : 'bg-red-100 text-red-800'
            }`}
          >
            {validacao.status}
          </span>
        </div>

        <div>
          <strong className="block text-gray-600 mb-1">Usuário:</strong>
          <p>{validacao.usuario.nome}</p>
          <p>{validacao.usuario.email}</p>
          <p>{validacao.usuario.endereco}</p>
        </div>

        <div>
          <strong className="block text-gray-600 mb-1">Entregador:</strong>
          <p>{validacao.entregador.nome}</p>
          <p>{validacao.entregador.email}</p>
          <p>{validacao.entregador.placa}</p>
          <p>{validacao.entregador.empresa}</p>
          {validacao.entregador.fotoUrl && (
            <img
              src={validacao.entregador.fotoUrl}
              alt="Foto do entregador"
              onClick={() => abrirFotoZoom(validacao.entregador.fotoUrl)}
              className="w-32 h-32 object-cover rounded-xl mt-2 cursor-zoom-in transition-transform hover:scale-105"
            />
          )}
        </div>

        {validacao.status === 'PENDENTE' && (
          <>
            <button
              onClick={handleConcluir}
              disabled={concluindo}
              className="mt-4 inline-flex items-center gap-2 px-4 py-2 bg-green-600 text-white rounded-xl hover:bg-green-700 disabled:opacity-50"
            >
              <CheckCircle className="w-5 h-5" />
              {concluindo ? 'Concluindo...' : 'Concluir Entrega'}
            </button>

            <button
              onClick={handleDenunciarGolpe}
              disabled={denunciando}
              className="mt-2 inline-flex items-center gap-2 px-4 py-2 bg-red-600 text-white rounded-xl hover:bg-red-700 disabled:opacity-50"
            >
              <AlertTriangle className="mr-1 w-5 h-5" />
              {denunciando ? 'Denunciando...' : 'Denunciar Tentativa de Golpe'}
            </button>
          </>
        )}
      </div>

      {fotoZoom && (
        <div
          onClick={fecharFotoZoom}
          className="fixed inset-0 bg-black bg-opacity-80 flex items-center justify-center z-50"
        >
          <img
            src={fotoZoom}
            alt="Foto ampliada"
            className="max-w-full max-h-full object-contain cursor-zoom-out"
          />
        </div>
      )}
    </div>
  )
}
