import { Routes } from '@angular/router';
import { MainLayout } from './layout/main-layout/main-layout';
import { Dashboard } from './features/dashboard/dashboard';
import { Categories } from './features/categories/categories';
import { Products } from './features/products/products';
import { Suppliers } from './features/suppliers/suppliers';
import { StockMovements } from './features/stock-movements/stock-movements';

export const routes: Routes = [
  {
    path: '',
    component: MainLayout,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: Dashboard },
      { path: 'categories', component: Categories },
      { path: 'products', component: Products },
      { path: 'suppliers', component: Suppliers },
      { path: 'stock-movements',component: StockMovements }
    ],
  },
  { path: '**', redirectTo: 'dashboard' },
];
