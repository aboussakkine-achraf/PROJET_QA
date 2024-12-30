import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Article } from '../model/article.model';
@Component({
  selector: 'app-dialog-content-article-details',
  templateUrl: './dialog-content-article-details.component.html',
  styleUrls: ['./dialog-content-article-details.component.css']
})
export class DialogContentArticleDetailsComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public article:Article) { }

  ngOnInit(): void {
  }

}
