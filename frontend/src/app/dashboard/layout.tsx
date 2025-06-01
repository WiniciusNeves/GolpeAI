export default function DashboardLayout({ children }: { children: React.ReactNode }) {
  return (
    <section className="flex">
      <nav className="w-48 bg-gray-100 min-h-screen p-4 space-y-2">
        <a href="/dashboard"            className="block">Início</a>
        <a href="/dashboard/users"      className="block">Usuários</a>
        <a href="/dashboard/motoboys"   className="block">Motoboys</a>
        <a href="/dashboard/authcode"   className="block">Gerar Código</a>
      </nav>
      <div className="flex-1">{children}</div>
    </section>
  )
}
