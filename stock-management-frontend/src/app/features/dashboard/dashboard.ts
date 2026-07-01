import { DashboardService } from './../../core/services/dashboard.service';
import { Component, OnInit, signal } from '@angular/core';
import { Dashboard as DashboardModel } from '../../core/models/dashboard.model';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  imports: [CurrencyPipe],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard implements OnInit {
  dashboard = signal<DashboardModel | null>(null);
  loading = signal(true);
  error = signal<string | null>(null);

  constructor(private dashboardService: DashboardService) { }
  
  ngOnInit(): void {
    this.dashboardService.getDashboard().subscribe({
      next: (data) => {
        this.dashboard.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Impossible de charger de dashboard');
        this.loading.set(false);
      }
    })
  }
}
