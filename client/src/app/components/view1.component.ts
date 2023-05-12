import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UploadService } from '../services/upload.service';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css'],
})
export class View1Component implements OnInit {
  @ViewChild('files') filesElem!: ElementRef;

  form!: FormGroup;
  disabled = false;
  bundleId!: string;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private uploadSvc: UploadService
  ) {}

  ngOnInit(): void {
    this.form = this.createForm();
  }

  createForm() {
    return this.fb.group({
      name: this.fb.control('', [Validators.required]),
      title: this.fb.control('', [Validators.required]),
      comments: this.fb.control('', [Validators.required]),
      file: this.fb.control('', [Validators.required]),
    });
  }

  async upload() {
    await this.uploadSvc.uploadFiles(this.form, this.filesElem).subscribe({
      next: (id) => {
        console.log(id);
        this.bundleId = id;
        this.router.navigate(['/list', this.bundleId]);
      },
      error: (error) => {
        console.log(error);
        alert(error);
      },
      complete: () => {},
    });
  }

  back() {
    this.router.navigate(['/']);
  }
}
