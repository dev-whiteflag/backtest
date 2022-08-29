import { Component } from '@angular/core';

export interface CotacaoDia {
  data: string;
  valorCompra: number;
  valorVenda: number;
}

const ELEMENT_DATA: CotacaoDia[] = [
  {data: '29/08/22', valorCompra: 1.0079, valorVenda: 1.0079},
];

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  displayedColumns: string[] = ['data', 'valorCompra', 'valorVenda'];
  dataSource = ELEMENT_DATA;
}
