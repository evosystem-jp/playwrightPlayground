package mains;

import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import constants.Configurations;

/**
 * プロキシサーバークローリングテスト.
 *
 * @author evosystem
 */
public class FreeProxyCrawlExample {

	/**
	 * メイン.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("■start.");

		// Playwrightを作成
		try (Playwright playwright = Playwright.create()) {
			// 使用するブラウザ種別一覧を取得
			List<BrowserType> browserTypes = Configurations.getUseBrowserTypeList(playwright);

			// 全てのブラウザ種別に対して実行
			for (BrowserType browserType : browserTypes) {
				System.out.println("■browserType: " + browserType.name());

				// ブラウザ起動オプションを設定
				BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
						.setHeadless(Configurations.USE_HEADLESS_MODE);

				// ブラウザを起動
				try (Browser browser = browserType.launch(launchOptions)) {
					System.out.println("■launched: " + browserType.name());

					// ブラウザコンテキストオプションを設定
					Browser.NewContextOptions newContextOptions = new Browser.NewContextOptions()
							.setUserAgent(Configurations.USE_UA)
							.setIsMobile(false);

					// ブラウザコンテキストを取得
					BrowserContext context = browser.newContext(newContextOptions);

					// ページを取得
					Page page = context.newPage();
					page.onDialog(dialog -> {
						dialog.dismiss();
					});

					// 画面遷移
					page.navigate("https://free-proxy-list.net/");

					Locator trListLocator = page.locator("#list table tbody tr");
					for (Locator trLocator : trListLocator.all()) {
						String ip = trLocator.locator("td:nth-child(1)").textContent();
						String port = trLocator.locator("td:nth-child(2)").textContent();
						String code = trLocator.locator("td:nth-child(3)").textContent();
						String country = trLocator.locator("td:nth-child(4)").textContent();
						String anonymity = trLocator.locator("td:nth-child(5)").textContent();
						String google = trLocator.locator("td:nth-child(6)").textContent();
						String https = trLocator.locator("td:nth-child(7)").textContent();
						String lastChecked = trLocator.locator("td:nth-child(8)").textContent();
						System.out.println("ip : " + ip + ", port : " + port);
					}
				}
			}
		} finally {
			System.out.println("■done.");
		}
	}
}