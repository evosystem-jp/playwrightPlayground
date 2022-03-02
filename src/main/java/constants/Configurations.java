package constants;

import java.util.Arrays;
import java.util.List;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

/**
 * 環境設定.
 *
 * @author evosystem
 */
public interface Configurations {

	/**
	 * 使用するブラウザ種別一覧を取得.
	 */
	static List<BrowserType> getUseBrowserTypeList(Playwright playwright) {
		return Arrays.asList(playwright.chromium(),
				playwright.webkit(),
				playwright.firefox());
	}

	/**
	 * ブラウザをヘッドレスモードで使用するか.
	 */
	boolean USE_HEADLESS_MODE = false;

	/**
	 * 使用するユーザーエージェント.
	 */
	String USE_UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.92 Safari/537.36";
}