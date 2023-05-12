import { HttpClient, HttpParams } from '@angular/common/http';
import { ElementRef, Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UploadService {
  imageFiles: any = [];
  constructor(private httpClient: HttpClient) {}

  private IMAGE_POST_API: string =
    'automatic-yak-production.up.railway.app/api/upload';

  private IMAGE_GET_API: string =
    'automatic-yak-production.up.railway.app/api/search';

  uploadFiles(form: FormGroup, imageFileElem: ElementRef) {
    console.log('upload in progress');
    const formData = new FormData();
    formData.set('name', form.get('name')?.value);
    formData.set('title', form.get('title')?.value);
    formData.set('comments', form.get('comments')?.value);

    const images = imageFileElem.nativeElement.files;

    // const zip = imageFileElem.nativeElement.files[0];
    // console.log(zip);

    // formData.append('file', zip);

    // for (var i = 0; i < images.length; i++) {
    //   this.imageFiles.push(images[i]);
    // }

    // formData.append('files', this.imageFiles);

    for (var i = 0; i < images.length; i++) {
      console.log(images[i]);
      formData.append('files', images[i]);
    }

    return lastValueFrom(
      this.httpClient.post<any>(this.IMAGE_POST_API, formData)
    );
  }

  getOneUpload(id: string) {
    console.log('get one upload triggered');
    return this.httpClient.get<any>(this.IMAGE_GET_API + '/' + id);
  }

  getAllUploads() {
    return lastValueFrom(this.httpClient.get<any>(this.IMAGE_GET_API + '/all'));
  }
}
