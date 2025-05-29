// middleware.ts
import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

export function middleware(request: NextRequest) {
  const token = request.cookies.get("token")?.value;

  const isProtectedRoute = request.nextUrl.pathname.startsWith("/dashboard");

  if (!token && isProtectedRoute) {
    return NextResponse.redirect(new URL("/", request.url)); // redireciona para login
  }

  if (token && request.nextUrl.pathname === "/") {
    return NextResponse.redirect(new URL("/dashboard", request.url)); // se logado e tentar voltar para login
  }

  return NextResponse.next();
}

export const config = {
  matcher: ["/dashboard/:path*"], // protege somente dashboard e subrotas
};
