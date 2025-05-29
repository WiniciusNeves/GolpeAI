// src/app/dashboard/page.tsx
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export default function DashboardPage() {
  const cookieStore = cookies();
  const token = cookieStore.get("token");

  if (!token) {
    redirect("/");
  }

  return <div>Bem-vindo à área protegida!</div>;
}
