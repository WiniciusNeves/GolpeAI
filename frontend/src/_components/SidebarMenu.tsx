'use client'

import Link from 'next/link'
import { usePathname } from 'next/navigation'

export default function SidebarMenu() {
  const pathname = usePathname()

  const linkClass = (href: string) =>
    `block px-4 py-2 rounded hover:bg-blue-800/50 transition ${
      pathname === href ? 'bg-blue-800 text-white' : 'text-gray-100'
    }`

  return (
    <aside className="fixed left-0 top-0 w-72 h-full bg-slate-900 shadow-lg p-4 rounded-r-2xl z-50">
      <nav className="space-y-2 text-sm font-medium">
        <Link href="/dashboard" className={linkClass('/dashboard')}>Início</Link>
        <Link href="/dashboard/user" className={linkClass('/dashboard/user')}>Usuários</Link>
        <Link href="/dashboard/entregador" className={linkClass('/dashboard/entregador')}>Entregadores</Link>
        <Link href="/dashboard/authcode" className={linkClass('/dashboard/authcode')}>Gerar Código</Link>
      </nav>
    </aside>
  )
}
