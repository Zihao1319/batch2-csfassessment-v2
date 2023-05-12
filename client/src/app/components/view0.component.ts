import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { lastValueFrom, Subscription } from 'rxjs';
import { Archive } from '../models/archive';
import { UploadService } from '../services/upload.service';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css'],
})
export class View0Component implements OnInit {
  data!: any;
  res$!: Subscription;

  constructor(private uploadSvc: UploadService, private router: Router) {}

  ngOnInit(): void {
    this.uploadSvc.getAllUploads().then((res) => {
      this.data = res;
      console.log(this.data);
    });
  }

  next() {
    this.router.navigate(['/comment']);
  }
}
