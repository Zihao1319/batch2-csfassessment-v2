import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Archive } from '../models/archive';
import { UploadService } from '../services/upload.service';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css'],
})
export class View0Component implements OnInit {
  data!: any
  res$!: Subscription;

  constructor(private uploadSvc: UploadService) {}

  ngOnInit(): void {
    this.data = this.uploadSvc.getAllUploads;
  }
}
