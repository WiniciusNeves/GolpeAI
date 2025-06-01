'use client'
import { useEffect, useState } from 'react'
import api from '@/services/apiRouter'

type Usuario   = { id:number; nome:string }
type Entregador = { id:number; nome:string }

export default function AuthCodePage() {
  const [users, setUsers] = useState<Usuario[]>([])
  const [ents , setEnts ] = useState<Entregador[]>([])
  const [userId, setUserId] = useState<number>()
  const [entId , setEntId ] = useState<number>()
  const [codigo, setCodigo] = useState<string>('')

  useEffect(() => {
    api.get<Usuario[]>('usuarios').then(r => setUsers(r.data))
    api.get<Entregador[]>('entregadores').then(r => setEnts(r.data))
  }, [])

  async function gerar() {
    if (!userId || !entId) { alert('Selecione usuário e entregador'); return }
    try {
      const r = await api.post<{codigoVerificacao:string}>(`validacao/gerar/${userId}/${entId}`)
      setCodigo(r.data.codigoVerificacao)
    } catch (err) { alert('Erro: ' + err) }
  }

  return (
    <main className="p-6 space-y-4">
      <h1 className="text-2xl font-bold">Gerar Código</h1>

      {/* selects */}
      <select className="input" onChange={e => setUserId(Number(e.target.value))} defaultValue="">
        <option value="" disabled>Usuário</option>
        {users.map(u => <option key={u.id} value={u.id}>{u.nome}</option>)}
      </select>

      <select className="input" onChange={e => setEntId(Number(e.target.value))} defaultValue="">
        <option value="" disabled>Motoboy</option>
        {ents.map(e => <option key={e.id} value={e.id}>{e.nome}</option>)}
      </select>

      <button className="btn-primary" onClick={gerar}>Gerar</button>

      {codigo && (
        <p className="mt-4 text-xl font-mono">
          Código: <span className="font-bold">{codigo}</span>
        </p>
      )}
    </main>
  )
}
