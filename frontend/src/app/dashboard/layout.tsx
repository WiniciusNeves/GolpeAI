/* transformamos este layout em componente de cliente */
'use client'

import Link from 'next/link'
import { usePathname } from 'next/navigation'

export default function DashboardLayout({ children }: { children: React.ReactNode }) {
  const pathname = usePathname()          // rota atual para highlight

  const linkClass = (href: string) =>
    `block px-2 py-1 rounded hover:bg-blue-800/40 ${
      pathname === href ? 'bg-blue-800/60' : ''
    }`

  return (
    <section className="flex">
      {/* menu lateral */}
      <nav className="w-48 min-h-screen bg-slate-900 text-white p-4 space-y-3">
        <Link href="/dashboard"            className={linkClass('/dashboard')}>Início</Link>
        <Link href="/dashboard/user"       className={linkClass('/dashboard/user')}>Usuários</Link>
        <Link href="/dashboard/entregador" className={linkClass('/dashboard/entregador')}>Entregadores</Link>
        <Link href="/dashboard/authcode"   className={linkClass('/dashboard/authcode')}>Gerar Código</Link>
      </nav>

      {/* conteúdo */}
      <div className="flex-1">{children}</div>
    </section>
  )
}
