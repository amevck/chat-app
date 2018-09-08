import { AppPage } from './app.po';

describe('angular-quick-start-app App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should display home', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Home');
  });
});
