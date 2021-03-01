import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TopBarService {
  private topBarName: BehaviorSubject<string> = new BehaviorSubject<string>('');
  constructor() { }
  setTopBarName(name: string): void {
    this.topBarName.next(name);
  }
  public get getTopBarName(): string {
    return this.topBarName.value;
  }
}
