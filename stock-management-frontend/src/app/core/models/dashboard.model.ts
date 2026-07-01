import { StockMovement } from "./stock-movement.model";

export interface Dashboard {
  totalProducts: number;
  totalCategories: number;
  totalSuppliers: number;
  lowStockProducts: number;
  totalStockValue: number;
  recentMovement: StockMovement[]
}