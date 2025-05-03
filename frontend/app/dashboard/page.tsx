import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Package, Tag, ShoppingCart, TrendingUp } from "lucide-react"

export default function DashboardPage() {
  return (
    <div className="flex-1 space-y-4 p-4 md:p-8 pt-6">
      <div className="flex items-center justify-between">
        <h2 className="text-3xl font-bold tracking-tight">대시보드</h2>
      </div>
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">총 상품</CardTitle>
            <Package className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">120</div>
            <p className="text-xs text-muted-foreground">지난달 대비 +10%</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">총 옵션</CardTitle>
            <Tag className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">350</div>
            <p className="text-xs text-muted-foreground">지난달 대비 +5%</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">총 주문</CardTitle>
            <ShoppingCart className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">1,203</div>
            <p className="text-xs text-muted-foreground">지난달 대비 +12%</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">매출</CardTitle>
            <TrendingUp className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">₩24,500,000</div>
            <p className="text-xs text-muted-foreground">지난달 대비 +18%</p>
          </CardContent>
        </Card>
      </div>
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-7">
        <Card className="col-span-4">
          <CardHeader>
            <CardTitle>최근 추가된 상품</CardTitle>
            <CardDescription>최근 추가된 상품 목록입니다.</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {[
                { name: "프리미엄 티셔츠", price: "₩35,000", date: "2023-05-01" },
                { name: "캐주얼 청바지", price: "₩45,000", date: "2023-04-28" },
                { name: "여름용 샌들", price: "₩28,000", date: "2023-04-25" },
                { name: "가벼운 재킷", price: "₩65,000", date: "2023-04-22" },
                { name: "스포츠 양말 세트", price: "₩12,000", date: "2023-04-20" },
              ].map((product, index) => (
                <div key={index} className="flex items-center justify-between">
                  <div>
                    <p className="font-medium">{product.name}</p>
                    <p className="text-sm text-muted-foreground">{product.date}</p>
                  </div>
                  <div className="font-medium">{product.price}</div>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
        <Card className="col-span-3">
          <CardHeader>
            <CardTitle>최근 추가된 옵션</CardTitle>
            <CardDescription>최근 추가된 상품 옵션 목록입니다.</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {[
                { name: "사이즈 - S/M/L/XL", product: "프리미엄 티셔츠", date: "2023-05-01" },
                { name: "색상 - 블랙/화이트/네이비", product: "프리미엄 티셔츠", date: "2023-05-01" },
                { name: "사이즈 - 28/30/32/34", product: "캐주얼 청바지", date: "2023-04-28" },
                { name: "워싱 - 라이트/미디엄/다크", product: "캐주얼 청바지", date: "2023-04-28" },
                { name: "사이즈 - 230/240/250/260", product: "여름용 샌들", date: "2023-04-25" },
              ].map((option, index) => (
                <div key={index} className="flex items-center justify-between">
                  <div>
                    <p className="font-medium">{option.name}</p>
                    <p className="text-sm text-muted-foreground">{option.product}</p>
                  </div>
                  <div className="text-sm text-muted-foreground">{option.date}</div>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}
