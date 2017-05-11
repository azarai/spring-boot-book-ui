import { AngularSpringbootBookPage } from './app.po';

describe('angular-springboot-book App', () => {
  let page: AngularSpringbootBookPage;

  beforeEach(() => {
    page = new AngularSpringbootBookPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
