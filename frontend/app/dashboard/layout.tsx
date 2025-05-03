"use client"

import type React from "react"

import { useState, useEffect } from "react"
import Link from "next/link"
import { usePathname, useRouter } from "next/navigation"
import { LayoutDashboard, Package, Tag, LogOut, Menu, X, User } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet"
import { useToast } from "@/hooks/use-toast"
import { useMobile } from "@/hooks/use-mobile"

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode
}) {
  const pathname = usePathname()
  const router = useRouter()
  const { toast } = useToast()
  const isMobile = useMobile()
  const [isSheetOpen, setIsSheetOpen] = useState(false)
  const [isClient, setIsClient] = useState(false)

  useEffect(() => {
    setIsClient(true)

    // 로그인 상태 확인 (실제 구현에서는 토큰 유효성 검증 필요)
    const token = localStorage.getItem("token")
    if (!token) {
      router.push("/login")
    }
  }, [router])

  const handleLogout = () => {
    localStorage.removeItem("token")
    toast({
      title: "로그아웃 성공",
      description: "로그인 페이지로 이동합니다",
    })
    router.push("/login")
  }

  const navigation = [
    {
      name: "대시보드",
      href: "/dashboard",
      icon: LayoutDashboard,
      current: pathname === "/dashboard",
    },
    {
      name: "상품 관리",
      href: "/dashboard/products",
      icon: Package,
      current: pathname === "/dashboard/products",
    },
    {
      name: "상품 옵션 관리",
      href: "/dashboard/product-options",
      icon: Tag,
      current: pathname === "/dashboard/product-options",
    },
  ]

  if (!isClient) {
    return null
  }

  return (
    <div className="flex min-h-screen flex-col">
      {/* 모바일 헤더 */}
      <header className="sticky top-0 z-30 flex h-14 items-center gap-4 border-b bg-background px-4 sm:static lg:hidden">
        <Sheet open={isSheetOpen} onOpenChange={setIsSheetOpen}>
          <SheetTrigger asChild>
            <Button variant="outline" size="icon" className="lg:hidden">
              <Menu className="h-5 w-5" />
              <span className="sr-only">메뉴 열기</span>
            </Button>
          </SheetTrigger>
          <SheetContent side="left" className="w-64 p-0">
            <div className="flex h-14 items-center border-b px-4">
              <Link
                href="/dashboard"
                className="flex items-center gap-2 font-semibold"
                onClick={() => setIsSheetOpen(false)}
              >
                <Package className="h-6 w-6" />
                <span>관리자 대시보드</span>
              </Link>
              <Button variant="ghost" size="icon" className="ml-auto" onClick={() => setIsSheetOpen(false)}>
                <X className="h-5 w-5" />
                <span className="sr-only">메뉴 닫기</span>
              </Button>
            </div>
            <nav className="grid gap-1 p-2">
              {navigation.map((item) => (
                <Link
                  key={item.name}
                  href={item.href}
                  onClick={() => setIsSheetOpen(false)}
                  className={`flex items-center gap-3 rounded-md px-3 py-2 text-sm font-medium ${
                    item.current ? "bg-muted text-primary" : "text-muted-foreground hover:bg-muted hover:text-primary"
                  }`}
                >
                  <item.icon className="h-4 w-4" />
                  {item.name}
                </Link>
              ))}
              <Button
                variant="ghost"
                className="flex w-full items-center justify-start gap-3 rounded-md px-3 py-2 text-sm font-medium text-muted-foreground hover:bg-muted hover:text-primary"
                onClick={handleLogout}
              >
                <LogOut className="h-4 w-4" />
                로그아웃
              </Button>
            </nav>
          </SheetContent>
        </Sheet>
        <div className="flex items-center gap-2">
          <Package className="h-6 w-6" />
          <span className="font-semibold">관리자 대시보드</span>
        </div>
        <div className="ml-auto flex items-center gap-2">
          <Button variant="ghost" size="icon" onClick={handleLogout}>
            <LogOut className="h-5 w-5" />
            <span className="sr-only">로그아웃</span>
          </Button>
        </div>
      </header>

      <div className="flex flex-1">
        {/* 데스크톱 사이드바 */}
        <aside className="hidden w-64 flex-col border-r bg-muted/40 lg:flex">
          <div className="flex h-14 items-center border-b px-4">
            <Link href="/dashboard" className="flex items-center gap-2 font-semibold">
              <Package className="h-6 w-6" />
              <span>관리자 대시보드</span>
            </Link>
          </div>
          <nav className="flex-1 overflow-auto p-2">
            {navigation.map((item) => (
              <Link
                key={item.name}
                href={item.href}
                className={`flex items-center gap-3 rounded-md px-3 py-2 text-sm font-medium ${
                  item.current ? "bg-muted text-primary" : "text-muted-foreground hover:bg-muted hover:text-primary"
                }`}
              >
                <item.icon className="h-4 w-4" />
                {item.name}
              </Link>
            ))}
          </nav>
          <div className="border-t p-4">
            <div className="flex items-center gap-3 rounded-md px-3 py-2">
              <div className="flex items-center gap-3">
                <div className="flex h-8 w-8 items-center justify-center rounded-full bg-primary/10 text-primary">
                  <User className="h-4 w-4" />
                </div>
                <div>
                  <p className="text-sm font-medium">관리자</p>
                  <p className="text-xs text-muted-foreground">admin@example.com</p>
                </div>
              </div>
              <Button variant="ghost" size="icon" className="ml-auto" onClick={handleLogout}>
                <LogOut className="h-4 w-4" />
                <span className="sr-only">로그아웃</span>
              </Button>
            </div>
          </div>
        </aside>

        {/* 메인 콘텐츠 */}
        <main className="flex-1 overflow-auto">{children}</main>
      </div>
    </div>
  )
}
