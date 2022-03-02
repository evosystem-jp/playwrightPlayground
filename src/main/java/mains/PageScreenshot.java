package mains;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import constants.Configurations;

/**
 * スクリーンショット作成テスト.
 *
 * @author evosystem
 */
public class PageScreenshot {

	/**
	 * 対象のURL.
	 */
	private static final String TARGET_URL = "https://evosystem.jp/";

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
							.setViewportSize(411, 731)
							.setDeviceScaleFactor(2.625)
							.setIsMobile(true)
							.setHasTouch(true)
							.setLocale("en-US")
							.setGeolocation(41.889938, 12.492507)
							.setPermissions(Arrays.asList("geolocation"));

					// ブラウザコンテキストを取得
					BrowserContext context = browser.newContext(newContextOptions);

					// ページを取得
					Page page = context.newPage();

					// 画面遷移
					page.navigate(TARGET_URL);

					// スクリーンショットを取得して保存
					page.screenshot(new Page.ScreenshotOptions()
							.setPath(Paths.get("screenshot/" + browserType.name() + ".png")));
				}
			}
		} finally {
			System.out.println("■done.");
		}
	}
}