"use client"

import { useState, useEffect } from "react"
import { Search, Plus, Edit, Trash2, ChevronLeft, ChevronRight, Filter } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from "@/components/ui/dropdown-menu"
import { Label } from "@/components/ui/label"
import { useToast } from "@/hooks/use-toast"

const API_BASE = process.env.NEXT_PUBLIC_API_BASE_URL!

// 상품 타입 정의
interface Product {
  id: number
  name: string
  description: string
  price: number
  shippingFee: number
}

export default function ProductsPage() {
  const [products, setProducts] = useState<Product[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [page, setPage] = useState(0)
  const [size, setSize] = useState(10)
  const [totalPages, setTotalPages] = useState(0)
  const [searchTerm, setSearchTerm] = useState("")
  const [isCreateDialogOpen, setIsCreateDialogOpen] = useState(false)
  const [isEditDialogOpen, setIsEditDialogOpen] = useState(false)
  const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false)
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null)

  // 폼 상태
  const [formData, setFormData] = useState({
    name: "",
    description: "",
    price: 0,
    shippingFee: 0,
  })

  const { toast } = useToast()

  // 상품 목록 불러오기 (인증 헤더 포함)
  const fetchProducts = async () => {
    setIsLoading(true)
    try {
      const token = localStorage.getItem("token")
      const response = await fetch(
          `${API_BASE}/products?page=${page}&size=${size}`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              ...(token && { Authorization: `Bearer ${token}` }),
            },
          }
      )
      if (!response.ok) {
        throw new Error("상품 목록을 불러오는데 실패했습니다")
      }
      const data = await response.json()
      setProducts(data.content || [])
      setTotalPages(data.totalPages || 1)
    } catch {
      toast({
        title: "오류 발생",
        description: "상품 목록을 불러오는데 실패했습니다",
        variant: "destructive",
      })
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    fetchProducts()
  }, [page, size])

  // 상품 생성 (인증 헤더 포함)
  const handleCreateProduct = async () => {
    try {
      const token = localStorage.getItem("token")
      const response = await fetch(`${API_BASE}/products`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          ...(token && { Authorization: `Bearer ${token}` }),
        },
        body: JSON.stringify(formData),
      })
      if (!response.ok) {
        throw new Error("상품 생성에 실패했습니다")
      }
      toast({
        title: "상품 생성 성공",
        description: "새 상품이 성공적으로 생성되었습니다",
      })
      setIsCreateDialogOpen(false)
      fetchProducts()
      setFormData({ name: "", description: "", price: 0, shippingFee: 0 })
    } catch {
      toast({
        title: "오류 발생",
        description: "상품 생성에 실패했습니다",
        variant: "destructive",
      })
    }
  }

  // 상품 수정 (인증 헤더 포함)
  const handleUpdateProduct = async () => {
    if (!selectedProduct) return
    try {
      const token = localStorage.getItem("token")
      const response = await fetch(
          `${API_BASE}/products/${selectedProduct.id}`,
          {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
              ...(token && { Authorization: `Bearer ${token}` }),
            },
            body: JSON.stringify(formData),
          }
      )
      if (!response.ok) {
        throw new Error("상품 수정에 실패했습니다")
      }
      toast({
        title: "상품 수정 성공",
        description: "상품이 성공적으로 수정되었습니다",
      })
      setIsEditDialogOpen(false)
      fetchProducts()
    } catch {
      toast({
        title: "오류 발생",
        description: "상품 수정에 실패했습니다",
        variant: "destructive",
      })
    }
  }

  // 상품 삭제 (인증 헤더 포함)
  const handleDeleteProduct = async () => {
    if (!selectedProduct) return
    try {
      const token = localStorage.getItem("token")
      const response = await fetch(
          `${API_BASE}/products/${selectedProduct.id}`,
          {
            method: "DELETE",
            headers: {
              "Content-Type": "application/json",
              ...(token && { Authorization: `Bearer ${token}` }),
            },
          }
      )
      if (!response.ok) {
        throw new Error("상품 삭제에 실패했습니다")
      }
      toast({
        title: "상품 삭제 성공",
        description: "상품이 성공적으로 삭제되었습니다",
      })
      setIsDeleteDialogOpen(false)
      fetchProducts()
    } catch {
      toast({
        title: "오류 발생",
        description: "상품 삭제에 실패했습니다",
        variant: "destructive",
      })
    }
  }

  // 수정 다이얼로그 열기
  const openEditDialog = (product: Product) => {
    setSelectedProduct(product)
    setFormData({
      name: product.name,
      description: product.description,
      price: product.price,
      shippingFee: product.shippingFee,
    })
    setIsEditDialogOpen(true)
  }

  // 삭제 다이얼로그 열기
  const openDeleteDialog = (product: Product) => {
    setSelectedProduct(product)
    setIsDeleteDialogOpen(true)
  }

  // 필터링된 상품 목록
  const filteredProducts = products.filter(
      (product) =>
          product.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
          product.description.toLowerCase().includes(searchTerm.toLowerCase())
  )

  return (
      <div className="flex-1 space-y-4 p-4 md:p-8 pt-6">
        <div className="flex items-center justify-between">
          <h2 className="text-3xl font-bold tracking-tight">상품 관리</h2>
          <Dialog open={isCreateDialogOpen} onOpenChange={setIsCreateDialogOpen}>
            <DialogTrigger asChild>
              <Button>
                <Plus className="mr-2 h-4 w-4" />
                상품 추가
              </Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-[500px]">
              <DialogHeader>
                <DialogTitle>새 상품 추가</DialogTitle>
                <DialogDescription>새로운 상품 정보를 입력하세요. 모든 필드는 필수입니다.</DialogDescription>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                <div className="grid gap-2">
                  <Label htmlFor="name">상품명</Label>
                  <Input
                      id="name"
                      value={formData.name}
                      onChange={(e) =>
                          setFormData({ ...formData, name: e.target.value })
                      }
                      placeholder="상품명을 입력하세요"
                  />
                </div>
                <div className="grid gap-2">
                  <Label htmlFor="description">상품 설명</Label>
                  <Input
                      id="description"
                      value={formData.description}
                      onChange={(e) =>
                          setFormData({ ...formData, description: e.target.value })
                      }
                      placeholder="상품 설명을 입력하세요"
                  />
                </div>
                <div className="grid grid-cols-2 gap-4">
                  <div className="grid gap-2">
                    <Label htmlFor="price">가격 (원)</Label>
                    <Input
                        id="price"
                        type="number"
                        value={formData.price}
                        onChange={(e) =>
                            setFormData({ ...formData, price: Number(e.target.value) })
                        }
                        placeholder="가격을 입력하세요"
                    />
                  </div>
                  <div className="grid gap-2">
                    <Label htmlFor="shippingFee">배송비 (원)</Label>
                    <Input
                        id="shippingFee"
                        type="number"
                        value={formData.shippingFee}
                        onChange={(e) =>
                            setFormData({
                              ...formData,
                              shippingFee: Number(e.target.value),
                            })
                        }
                        placeholder="배송비를 입력하세요"
                    />
                  </div>
                </div>
              </div>
              <DialogFooter>
                <Button variant="outline" onClick={() => setIsCreateDialogOpen(false)}>
                  취소
                </Button>
                <Button onClick={handleCreateProduct}>추가</Button>
              </DialogFooter>
            </DialogContent>
          </Dialog>
        </div>

        <div className="flex items-center gap-2">
          <div className="relative flex-1 max-w-sm">
            <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
            <Input
                type="search"
                placeholder="상품명 또는 설명으로 검색..."
                className="pl-8"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="outline" size="icon">
                <Filter className="h-4 w-4" />
                <span className="sr-only">필터</span>
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              <DropdownMenuItem onClick={() => setSize(5)}>5개씩 보기</DropdownMenuItem>
              <DropdownMenuItem onClick={() => setSize(10)}>
                10개씩 보기
              </DropdownMenuItem>
              <DropdownMenuItem onClick={() => setSize(20)}>
                20개씩 보기
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>

        <div className="rounded-md border">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[80px]">ID</TableHead>
                <TableHead>상품명</TableHead>
                <TableHead className="hidden md:table-cell">설명</TableHead>
                <TableHead>가격</TableHead>
                <TableHead className="hidden md:table-cell">배송비</TableHead>
                <TableHead className="text-right">관리</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {isLoading ? (
                  <TableRow>
                    <TableCell colSpan={6} className="h-24 text-center">
                      <div className="flex justify-center">
                        <div className="h-6 w-6 animate-spin rounded-full border-2 border-primary border-t-transparent"></div>
                      </div>
                    </TableCell>
                  </TableRow>
              ) : filteredProducts.length === 0 ? (
                  <TableRow>
                    <TableCell colSpan={6} className="h-24 text-center">
                      상품이 없습니다
                    </TableCell>
                  </TableRow>
              ) : (
                  filteredProducts.map((product) => (
                      <TableRow key={product.id}>
                        <TableCell className="font-medium">{product.id}</TableCell>
                        <TableCell>{product.name}</TableCell>
                        <TableCell className="hidden md:table-cell">
                          {product.description}
                        </TableCell>
                        <TableCell>{product.price.toLocaleString()}원</TableCell>
                        <TableCell className="hidden md:table-cell">
                          {product.shippingFee.toLocaleString()}원
                        </TableCell>
                        <TableCell className="text-right">
                          <div className="flex justify-end gap-2">
                            <Button
                                variant="ghost"
                                size="icon"
                                onClick={() => openEditDialog(product)}
                            >
                              <Edit className="h-4 w-4" />
                              <span className="sr-only">수정</span>
                            </Button>
                            <Button
                                variant="ghost"
                                size="icon"
                                onClick={() => openDeleteDialog(product)}
                            >
                              <Trash2 className="h-4 w-4" />
                              <span className="sr-only">삭제</span>
                            </Button>
                          </div>
                        </TableCell>
                      </TableRow>
                  ))
              )}
            </TableBody>
          </Table>
        </div>

        {/* 페이지네이션 */}
        <div className="flex items-center justify-end space-x-2 py-4">
          <Button
              variant="outline"
              size="sm"
              onClick={() => setPage(Math.max(0, page - 1))}
              disabled={page === 0}
          >
            <ChevronLeft className="h-4 w-4" />
            이전
          </Button>
          <div className="flex items-center gap-1">
            {Array.from({ length: totalPages }, (_, i) => (
                <Button
                    key={i}
                    variant={page === i ? "default" : "outline"}
                    size="sm"
                    onClick={() => setPage(i)}
                    className="w-8 h-8 p-0"
                >
                  {i + 1}
                </Button>
            ))}
          </div>
          <Button
              variant="outline"
              size="sm"
              onClick={() => setPage(Math.min(totalPages - 1, page + 1))}
              disabled={page === totalPages - 1}
          >
            다음
            <ChevronRight className="h-4 w-4" />
          </Button>
        </div>

        {/* 수정 다이얼로그 */}
        <Dialog open={isEditDialogOpen} onOpenChange={setIsEditDialogOpen}>
          <DialogContent className="sm:max-w-[500px]">
            <DialogHeader>
              <DialogTitle>상품 수정</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid gap-2">
                <Label htmlFor="edit-name">상품명</Label>
                <Input
                    id="edit-name"
                    value={formData.name}
                    onChange={(e) =>
                        setFormData({ ...formData, name: e.target.value })
                    }
                />
              </div>
              <div className="grid gap-2">
                <Label htmlFor="edit-description">상품 설명</Label>
                <Input
                    id="edit-description"
                    value={formData.description}
                    onChange={(e) =>
                        setFormData({ ...formData, description: e.target.value })
                    }
                />
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div className="grid gap-2">
                  <Label htmlFor="edit-price">가격 (원)</Label>
                  <Input
                      id="edit-price"
                      type="number"
                      value={formData.price}
                      onChange={(e) =>
                          setFormData({
                            ...formData,
                            price: Number(e.target.value),
                          })
                      }
                  />
                </div>
                <div className="grid gap-2">
                  <Label htmlFor="edit-shippingFee">배송비 (원)</Label>
                  <Input
                      id="edit-shippingFee"
                      type="number"
                      value={formData.shippingFee}
                      onChange={(e) =>
                          setFormData({
                            ...formData,
                            shippingFee: Number(e.target.value),
                          })
                      }
                  />
                </div>
              </div>
            </div>
            <DialogFooter>
              <Button variant="outline" onClick={() => setIsEditDialogOpen(false)}>
                취소
              </Button>
              <Button onClick={handleUpdateProduct}>저장</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>

        {/* 삭제 다이얼로그 */}
        <Dialog open={isDeleteDialogOpen} onOpenChange={setIsDeleteDialogOpen}>
          <DialogContent className="sm:max-w-[425px]">
            <DialogHeader>
              <DialogTitle>상품 삭제</DialogTitle>
            </DialogHeader>
            <DialogFooter>
              <Button variant="outline" onClick={() => setIsDeleteDialogOpen(false)}>
                취소
              </Button>
              <Button variant="destructive" onClick={handleDeleteProduct}>
                삭제
              </Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </div>
  )
}
