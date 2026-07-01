export interface StockMovement {
  id: number;
  type: 'IN' | 'OUT';
  quatity: number;
  reason: string;
  movementData: string;
  productId: number;
  productName: string;
}