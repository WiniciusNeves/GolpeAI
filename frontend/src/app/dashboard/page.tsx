/* Página inicial do dashboard – apenas atalhos minimalistas */
import Link from 'next/link'

export default function DashboardHome() {
  return (
    <main className="p-8 space-y-6">
      <h1 className="text-2xl font-bold">Dashboard</h1>

      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
        <DashboardCard href="/dashboard/user"        label="Usuários"       />
        <DashboardCard href="/dashboard/entregador"  label="Entregadores"   />
        <DashboardCard href="/dashboard/authcode"    label="Gerar Código"   />
      </div>
    </main>
  )
}

/* --- cartão reutilizável --- */
function DashboardCard({ href, label }: { href: string; label: string }) {
  return (
    <Link
      href={href}
      className="flex items-center justify-center h-32 rounded-xl bg-blue-50
                 text-blue-800 font-semibold shadow hover:bg-blue-100
                 transition-colors"
    >
      {label}
    </Link>
  )
}
