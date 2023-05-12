import { ThisReceiver } from '@angular/compiler';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Archive } from '../models/archive';
import { UploadService } from '../services/upload.service';

@Component({
  selector: 'app-view2',
  templateUrl: './view2.component.html',
  styleUrls: ['./view2.component.css'],
})
export class View2Component implements OnInit, OnDestroy {
  params$!: Subscription;
  bundleId!: string;
  archive!: Archive;
  notFound = false;
  urls!: string[];

  constructor(
    private activatedRoute: ActivatedRoute,
    private uploadSvc: UploadService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.params$ = this.activatedRoute.params.subscribe({
      next: async (params) => {
        this.bundleId = params['bundleId'];
        console.log(this.bundleId);

        await this.uploadSvc.getOneUpload(this.bundleId).subscribe({
          next: (data) => {
            this.archive = data;
            this.urls = this.getUrlsArray(data.urls);
          },
          error: (error) => {
            console.log(error);
            alert(error);
          },
          complete: () => {},
        });
      },
    });
  }

  getUrlsArray(urls: any): string[] {
    const urlsString = urls.slice(1, -1);
    return urlsString.split(',').map((url: string) => url.trim());
  }

  ngOnDestroy(): void {
    this.params$.unsubscribe();
  }

  back() {
    this.router.navigate(['/']);
  }
}
