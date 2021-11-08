import {Injectable} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {Location} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class RouterService {
  private previousUrl: string;
  private currentUrl: string;

  constructor(private router: Router, private location: Location) {
    this.currentUrl = this.router.url;
    router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.previousUrl = this.currentUrl;
        this.currentUrl = event.url;
      }
    });
  }

  public getPreviousUrl(): string {
    return this.previousUrl;
  }

  public getCurrentUrl(): string {
    return this.currentUrl;
  }

  public goToPrevious(): void {
    this.router.navigateByUrl(this.previousUrl);
  }

  public backByBrowserHistory() {
    this.location.back();
  }

  public navigateToUrl(url: string){
    this.router.navigateByUrl(url);
  }
}
