import {MatSnackBar} from "@angular/material/snack-bar";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})

export class NotificationService {

  constructor(private snackbar: MatSnackBar) {
  }

  public errorSnackBar(message: string): void {
    this.snackbar.open(message, undefined, {
      panelClass: ['error-snackbar'],
      duration: 5000
    })
  }

  public successSnackBar(message: string): void {
    this.snackbar.open(message, undefined, {
      duration: 2000,
      panelClass: ['success-snackbar']
    })
  }
}
