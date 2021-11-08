import {Injectable} from '@angular/core';
import {
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {EMPTY, Observable, throwError} from "rxjs";
import {NotificationService} from "./notification.service";
import {catchError} from "rxjs/operators";
import {RouterService} from "./router.service";

@Injectable({
  providedIn: 'root'
})
export class ErrorInterceptorService implements HttpInterceptor {


  constructor(private notificationService: NotificationService, private router: RouterService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(err => {

      if (err instanceof HttpErrorResponse && (err.status === 400 || err.status === 404)) {
        this.notificationService.errorSnackBar(err.error.message);
        this.router.navigateToUrl('/');
      }
      if (err instanceof HttpErrorResponse) {
        this.notificationService.errorSnackBar(err.error.message);
      }
      return EMPTY;
    }));
  }
}

export const errorInterceptorProviders = [
  {
    provide: HTTP_INTERCEPTORS,
    useClass: ErrorInterceptorService,
    multi: true
  }
]
