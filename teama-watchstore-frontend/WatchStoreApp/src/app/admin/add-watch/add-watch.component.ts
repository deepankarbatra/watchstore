import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import imageDto from 'src/app/interfaces/imageDto';
import watchDto from 'src/app/interfaces/watchDto';
import watchUpdateDto from 'src/app/interfaces/watchUpdateDto';
import { WatchService } from '../services/watch-service.service';
@Component({
  selector: 'app-add-watch',
  templateUrl: './add-watch.component.html',
  styleUrls: ['./add-watch.component.css']
})
export class AddWatchComponent {
  watchForm!: FormGroup;
  actionButton: string = "Save"
  actionLine: string = "Add Watch";
  watchImages:imageDto[] =[];
  isUpdate=true;
  watch: watchDto = {
    modelNumber: '',
    watchName: '',
    watchBrand: '',
    watchType: '',
    stockQuantity: 0,
    price: 0,
    availableStatus: false,
    imagePathList: [],
  }
  watchUpdate: any = {
    modelNumber:'',
    watchName: '',
    watchBrand: '',
    watchType: '',
    stockQuantity: 0,
    price: 0,
    availableStatus: false,
    imagePathList:[],
  }

  constructor(private formBuilder: FormBuilder, private dialogRef: MatDialogRef<AddWatchComponent>, public watchService: WatchService, private snackBar: MatSnackBar,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public editData: watchUpdateDto,
  ) {

  }
  ngOnInit(): void {
    this.watchForm = this.formBuilder.group({
      modelNumber: ['', Validators.required],
      watchName: ['', Validators.required],
      watchBrand: ['', Validators.required],
      watchType: ['', Validators.required],
      watchQuantity: ['', Validators.required],
      watchPrice: ['', Validators.required],
      watchStatus: ['', Validators.required],
      watchImage: ['', Validators.required]
    })
    if (this.editData) {
      this.actionButton = "Update";
      this.actionLine = "Update Watch";
      this.watchForm.controls['modelNumber'].setValue(this.editData.modelNumber);
      this.watchForm.controls['modelNumber'].disable();
      this.watchForm.controls['watchName'].setValue(this.editData.watchName);
      this.watchForm.controls['watchBrand'].setValue(this.editData.watchBrand);
      this.watchForm.controls['watchType'].setValue(this.editData.watchType);
      this.watchForm.controls['watchQuantity'].setValue(this.editData.stockQuantity);
      this.watchForm.controls['watchPrice'].setValue(this.editData.price);
      const imagePaths: string[] = [];
      for (let image of this.editData.images) {
        const imagePath = image.imagePath;
        imagePaths.push(imagePath);
      }
      this.watchForm.controls['watchImage'].setValue(imagePaths)
      this.isUpdate=true;
      this.watchImages=this.editData.images;
      if (this.editData.availableStatus) {
        this.watchForm.controls['watchStatus'].setValue("True");
      }
      else {
        this.watchForm.controls['watchStatus'].setValue("False");
      }
    }
  }

  handleFileInput(event: any) {
    const files: File[] = event.target.files;
    const imagePaths: string[] = [];
    for (let i = 0; i < files.length; i++) {
      const imagePath = files[i].name;
      imagePaths.push("assets/" + imagePath);
    }
    this.watchForm.get('watchImage')?.setValue(imagePaths);
  }
  addWatch() {
    if (!this.editData) {
      if (this.watchForm.valid) {
        this.formDtoConversion();
        this.watchService.addWatch(this.watch).subscribe({
          next: (response: any) => {
            setTimeout(() => {
              this.showMessage('Watch Added Successfully', 3000);
            }, 1500);
            this.dialogRef.close('save');
          },
          error: err => {
            this.showMessage('Failed to Add Watch. Please try again!', 3000);
          }
        });
      }
    } else {
      this.updateWatch();
    }

  }
  updateWatch() {
    if (this.watchForm.valid) {
      this.updateFormDtoConversion();
      this.watchService.updateWatch(this.editData.modelNumber, this.watchUpdate).subscribe({
        next: (response: any) => {
          setTimeout(() => {
            this.showMessage('Watch Update Successfully', 3000);
          }, 1500);
          this.dialogRef.close('update');
        },
        error: err => {
          this.showMessage('Failed to Update Watch. Please try again!', 3000);
        }
      })

    }



  }
  private formDtoConversion() {
    this.watch.modelNumber = this.watchForm.value.modelNumber;
    this.watch.watchBrand = this.watchForm.value.watchBrand.trim();
    this.watch.watchName = this.watchForm.value.watchName;
    this.watch.watchType = this.watchForm.value.watchType;
    this.watch.stockQuantity = this.watchForm.value.watchQuantity;
    this.watch.price = this.watchForm.value.watchPrice;
    this.watch.availableStatus = this.watchForm.value.watchStatus as boolean;
    this.watch.imagePathList = this.watchForm.value.watchImage;

  }

  private updateFormDtoConversion() {
    this.watchUpdate.watchBrand = this.watchForm.value.watchBrand.trim();
    this.watchUpdate.watchName = this.watchForm.value.watchName;
    this.watchUpdate.watchType = this.watchForm.value.watchType;
    this.watchUpdate.stockQuantity = this.watchForm.value.watchQuantity;
    this.watchUpdate.price = this.watchForm.value.watchPrice;
    this.watchUpdate.availableStatus = this.watchForm.value.watchStatus as boolean;
    for(let image of this.watchForm.value.watchImage)
    {
      this.watchUpdate.imagePathList.push(image);
    }
  
  }

  private showMessage(message: string, duration: number): void {
    this.snackBar.open(message, 'Dismiss', {
      duration: duration,
    });
  }



}
