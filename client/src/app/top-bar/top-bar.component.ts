import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../service/authentication.service';
import {TopBarService} from '../service/top-bar.service';


@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit {
  constructor(
    public authentication: AuthenticationService,
    public topBarService: TopBarService
  ) { }
  ngOnInit(): void {
  }

}
