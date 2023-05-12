import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { View1Component } from './components/view1.component';
import { View0Component } from './components/view0.component';
import { View2Component } from './components/view2.component';
import { MaterialModule } from 'src/material.module';
import { UploadService } from './services/upload.service';

@NgModule({
  declarations: [AppComponent, View1Component, View0Component, View2Component],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MaterialModule,
  ],
  providers: [UploadService],
  bootstrap: [AppComponent],
})
export class AppModule {}
