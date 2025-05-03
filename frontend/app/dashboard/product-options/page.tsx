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
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { useToast } from "@/hooks/use-toast"

const API_BASE = process.env.NEXT_PUBLIC_API_BASE_URL!

interface ProductOption {
  id: number
  productId: number
  productName: string
  name: string
  additionalPrice: number
  type: string
  value: string[]
}

export default function ProductOptionsPage() {
  const [options, setOptions] = useState<ProductOption[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [page, setPage] = useState(0)
  const [size, setSize] = useState(10)
  const [totalPages, setTotalPages] = useState(0)
  const [searchTerm, setSearchTerm] = useState("")
  const [isCreateDialogOpen, setIsCreateDialogOpen] = useState(false)
  const [isEditDialogOpen, setIsEditDialogOpen] = useState(false)
  const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false)
  const [selectedOption, setSelectedOption] = useState<ProductOption | null>(null)
  const [products, setProducts] = useState<{ id: number; name: string }[]>([])
  const [formData, setFormData] = useState({
    productId: 0,
    name: "",
    additionalPrice: 0,
    type: "SIZE",
    value: "",
  })
  const { toast } = useToast()

  const fetchProducts = async () => {
    try {
      const token = localStorage.getItem("token")
      const response = await fetch(`${API_BASE}/products?size=100`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          ...(token && { Authorization: `Bearer ${token}` }),
        },
      })
      if (!response.ok) {
        throw new Error("상품 목록을 불러오는데 실패했습니다")
      }
      const data = await response.json()
      setProducts(data.content || [])
    } catch {
      toast({
        title: "오류 발생",
        description: "상품 목록을 불러오는데 실패했습니다",
        variant: "destructive",
      })
      setProducts([])
    }
  }

  const fetchOptions = async () => {
    setIsLoading(true)
    try {
      const token = localStorage.getItem("token")
      const response = await fetch(
          `${API_BASE}/product-options?page=${page}&size=${size}`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              ...(token && { Authorization: `Bearer ${token}` }),
            },
          }
      )
      if (!response.ok) {
        throw new Error("상품 옵션 목록을 불러오는데 실패했습니다")
      }
      const data = await response.json()
      setOptions(data.content || [])
      setTotalPages(data.totalPages || 0)
    } catch {
      toast({
        title: "오류 발생",
        description: "상품 옵션 목록을 불러오는데 실패했습니다",
        variant: "destructive",
      })
      setOptions([])
      setTotalPages(0)
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    fetchProducts()
    fetchOptions()
  }, [page, size])

  const handleCreateOption = async () => {
    try {
      const token = localStorage.getItem("token")
      const valueArray = formData.value.split(",").map((item) => item.trim())
      const response = await fetch(`${API_BASE}/product-options`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          ...(token && { Authorization: `Bearer ${token}` }),
        },
        body: JSON.stringify({
          productId: formData.productId,
          name: formData.name,
          additionalPrice: formData.additionalPrice,
          type: formData.type,
          value: valueArray,
        }),
      })
      if (!response.ok) {
        throw new Error("상품 옵션 생성에 실패했습니다")
      }
      toast({
        title: "상품 옵션 생성 성공",
        description: "상품 옵션이 생성되었습니다",
      })
      setIsCreateDialogOpen(false)
      fetchOptions()
      setFormData({
        productId: 0,
        name: "",
        additionalPrice: 0,
        type: "SIZE",
        value: "",
      })
    } catch {
      toast({
        title: "오류 발생",
        description: "상품 옵션 생성에 실패했습니다",
        variant: "destructive",
      })
    }
  }

  const handleUpdateOption = async () => {
    if (!selectedOption) return
    try {
      const token = localStorage.getItem("token")
      const valueArray = formData.value.split(",").map((item) => item.trim())
      const response = await fetch(
          `${API_BASE}/product-options/${selectedOption.id}`,
          {
            method: "PUT",
            headers: {
              "Content-Type": "application/json",
              ...(token && { Authorization: `Bearer ${token}` }),
            },
            body: JSON.stringify({
              name: formData.name,
              additionalPrice: formData.additionalPrice,
              type: formData.type,
              value: valueArray,
            }),
          }
      )
      if (!response.ok) {
        throw new Error("상품 옵션 수정에 실패했습니다")
      }
      toast({
        title: "상품 옵션 수정 성공",
        description: "상품 옵션이 수정되었습니다",
      })
      setIsEditDialogOpen(false)
      fetchOptions()
    } catch {
      toast({
        title: "오류 발생",
        description: "상품 옵션 수정에 실패했습니다",
        variant: "destructive",
      })
    }
  }

  const handleDeleteOption = async () => {
    if (!selectedOption) return
    try {
      const token = localStorage.getItem("token")
      const response = await fetch(
          `${API_BASE}/product-options/${selectedOption.id}`,
          {
            method: "DELETE",
            headers: {
              "Content-Type": "application/json",
              ...(token && { Authorization: `Bearer ${token}` }),
            },
          }
      )
      if (!response.ok) {
        throw new Error("상품 옵션 삭제에 실패했습니다")
      }
      toast({
        title: "상품 옵션 삭제 성공",
        description: "상품 옵션이 삭제되었습니다",
      })
      setIsDeleteDialogOpen(false)
      fetchOptions()
    } catch {
      toast({
        title: "오류 발생",
        description: "상품 옵션 삭제에 실패했습니다",
        variant: "destructive",
      })
    }
  }

  const openEditDialog = (option: ProductOption) => {
    setSelectedOption(option)
    setFormData({
      productId: option.productId,
      name: option.name,
      additionalPrice: option.additionalPrice,
      type: option.type,
      value: option.value.join(", "),
    })
    setIsEditDialogOpen(true)
  }

  const openDeleteDialog = (option: ProductOption) => {
    setSelectedOption(option)
    setIsDeleteDialogOpen(true)
  }

  const filteredOptions = options.filter(
      (option) =>
          option.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
          option.productName.toLowerCase().includes(searchTerm.toLowerCase()) ||
          option.value.some((v) => v.toLowerCase().includes(searchTerm.toLowerCase()))
  )

  return (
      <div className="flex-1 space-y-4 p-4 md:p-8 pt-6">
        <div className="flex items-center justify-between">
          <h2 className="text-3xl font-bold tracking-tight">상품 옵션 관리</h2>
          <Dialog open={isCreateDialogOpen} onOpenChange={setIsCreateDialogOpen}>
            <DialogTrigger asChild>
              <Button>
                <Plus className="mr-2 h-4 w-4" />
                옵션 추가
              </Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-[500px]">
              <DialogHeader>
                <DialogTitle>새 상품 옵션 추가</DialogTitle>
                <DialogDescription>새 상품 옵션 정보를 입력하세요</DialogDescription>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                <div className="grid gap-2">
                  <Label htmlFor="productId">상품</Label>
                  <Select
                      value={formData.productId.toString()}
                      onValueChange={(value) =>
                          setFormData({ ...formData, productId: Number(value) })
                      }
                  >
                    <SelectTrigger>
                      <SelectValue placeholder="상품을 선택하세요" />
                    </SelectTrigger>
                    <SelectContent>
                      {products.map((product) => (
                          <SelectItem key={product.id} value={product.id.toString()}>
                            {product.name}
                          </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
                <div className="grid gap-2">
                  <Label htmlFor="name">옵션명</Label>
                  <Input
                      id="name"
                      value={formData.name}
                      onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                  />
                </div>
                <div className="grid gap-2">
                  <Label htmlFor="type">옵션 타입</Label>
                  <Select
                      value={formData.type}
                      onValueChange={(value) => setFormData({ ...formData, type: value })}
                  >
                    <SelectTrigger>
                      <SelectValue placeholder="옵션 타입을 선택하세요" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="SIZE">사이즈</SelectItem>
                      <SelectItem value="COLOR">색상</SelectItem>
                      <SelectItem value="STYLE">스타일</SelectItem>
                      <SelectItem value="MATERIAL">소재</SelectItem>
                      <SelectItem value="OTHER">기타</SelectItem>
                    </SelectContent>
                  </Select>
                </div>
                <div className="grid gap-2">
                  <Label htmlFor="additionalPrice">추가 가격 (원)</Label>
                  <Input
                      id="additionalPrice"
                      type="number"
                      value={formData.additionalPrice}
                      onChange={(e) =>
                          setFormData({ ...formData, additionalPrice: Number(e.target.value) })
                      }
                  />
                </div>
                <div className="grid gap-2">
                  <Label htmlFor="value">옵션 값 (쉼표로 구분)</Label>
                  <Input
                      id="value"
                      value={formData.value}
                      onChange={(e) => setFormData({ ...formData, value: e.target.value })}
                  />
                </div>
              </div>
              <DialogFooter>
                <Button variant="outline" onClick={() => setIsCreateDialogOpen(false)}>
                  취소
                </Button>
                <Button onClick={handleCreateOption}>추가</Button>
              </DialogFooter>
            </DialogContent>
          </Dialog>
        </div>

        <div className="flex items-center gap-2">
          <div className="relative flex-1 max-w-sm">
            <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
            <Input
                type="search"
                placeholder="옵션명, 상품명 또는 옵션 값으로 검색..."
                className="pl-8"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="outline" size="icon">
                <Filter className="h-4 w-4" />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              <DropdownMenuItem onClick={() => setSize(5)}>5개씩 보기</DropdownMenuItem>
              <DropdownMenuItem onClick={() => setSize(10)}>10개씩 보기</DropdownMenuItem>
              <DropdownMenuItem onClick={() => setSize(20)}>20개씩 보기</DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>

        <div className="rounded-md border">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[80px]">ID</TableHead>
                <TableHead>상품명</TableHead>
                <TableHead>옵션명</TableHead>
                <TableHead>타입</TableHead>
                <TableHead className="hidden md:table-cell">옵션 값</TableHead>
                <TableHead>추가 가격</TableHead>
                <TableHead className="text-right">관리</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {isLoading ? (
                  <TableRow>
                    <TableCell colSpan={7} className="h-24 text-center">
                      <div className="flex justify-center">
                        <div className="h-6 w-6 animate-spin rounded-full border-2 border-primary border-t-transparent"></div>
                      </div>
                    </TableCell>
                  </TableRow>
              ) : filteredOptions.length === 0 ? (
                  <TableRow>
                    <TableCell colSpan={7} className="h-24 text-center">
                      상품 옵션이 없습니다
                    </TableCell>
                  </TableRow>
              ) : (
                  filteredOptions.map((option) => (
                      <TableRow key={option.id}>
                        <TableCell className="font-medium">{option.id}</TableCell>
                        <TableCell>{option.productName}</TableCell>
                        <TableCell>{option.name}</TableCell>
                        <TableCell>{option.type}</TableCell>
                        <TableCell className="hidden md:table-cell">
                          <div className="flex flex-wrap gap-1">
                            {option.value.map((val, idx) => (
                                <span
                                    key={idx}
                                    className="inline-flex items-center rounded-full border px-2.5 py-0.5 text-xs font-semibold"
                                >
                          {val}
                        </span>
                            ))}
                          </div>
                        </TableCell>
                        <TableCell>{option.additionalPrice.toLocaleString()}원</TableCell>
                        <TableCell className="text-right">
                          <div className="flex justify-end gap-2">
                            <Button variant="ghost" size="icon" onClick={() => openEditDialog(option)}>
                              <Edit className="h-4 w-4" />
                            </Button>
                            <Button variant="ghost" size="icon" onClick={() => openDeleteDialog(option)}>
                              <Trash2 className="h-4 w-4" />
                            </Button>
                          </div>
                        </TableCell>
                      </TableRow>
                  ))
              )}
            </TableBody>
          </Table>
        </div>

        <div className="flex items-center justify-end space-x-2 py-4">
          <Button variant="outline" size="sm" onClick={() => setPage(Math.max(0, page - 1))} disabled={page === 0}>
            <ChevronLeft className="h-4 w-4" />
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
          <Button variant="outline" size="sm" onClick={() => setPage(Math.min(totalPages - 1, page + 1))} disabled={page === totalPages - 1}>
            <ChevronRight className="h-4 w-4" />
          </Button>
        </div>

        <Dialog open={isEditDialogOpen} onOpenChange={setIsEditDialogOpen}>
          <DialogContent className="sm:max-w-[500px]">
            <DialogHeader>
              <DialogTitle>상품 옵션 수정</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid gap-2">
                <Label htmlFor="edit-name">옵션명</Label>
                <Input
                    id="edit-name"
                    value={formData.name}
                    onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                />
              </div>
              <div className="grid gap-2">
                <Label htmlFor="edit-type">옵션 타입</Label>
                <Select value={formData.type} onValueChange={(value) => setFormData({ ...formData, type: value })}>
                  <SelectTrigger>
                    <SelectValue />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="SIZE">사이즈</SelectItem>
                    <SelectItem value="COLOR">색상</SelectItem>
                    <SelectItem value="STYLE">스타일</SelectItem>
                    <SelectItem value="MATERIAL">소재</SelectItem>
                    <SelectItem value="OTHER">기타</SelectItem>
                  </SelectContent>
                </Select>
              </div>
              <div className="grid gap-2">
                <Label htmlFor="edit-additionalPrice">추가 가격 (원)</Label>
                <Input
                    id="edit-additionalPrice"
                    type="number"
                    value={formData.additionalPrice}
                    onChange={(e) => setFormData({ ...formData, additionalPrice: Number(e.target.value) })}
                />
              </div>
              <div className="grid gap-2">
                <Label htmlFor="edit-value">옵션 값 (쉼표로 구분)</Label>
                <Input
                    id="edit-value"
                    value={formData.value}
                    onChange={(e) => setFormData({ ...formData, value: e.target.value })}
                />
              </div>
            </div>
            <DialogFooter>
              <Button variant="outline" onClick={() => setIsEditDialogOpen(false)}>
                취소
              </Button>
              <Button onClick={handleUpdateOption}>저장</Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>

        <Dialog open={isDeleteDialogOpen} onOpenChange={setIsDeleteDialogOpen}>
          <DialogContent className="sm:max-w-[425px]">
            <DialogHeader>
              <DialogTitle>상품 옵션 삭제</DialogTitle>
            </DialogHeader>
            <DialogFooter>
              <Button variant="outline" onClick={() => setIsDeleteDialogOpen(false)}>
                취소
              </Button>
              <Button variant="destructive" onClick={handleDeleteOption}>
                삭제
              </Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </div>
  )
}
