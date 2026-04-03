package tw.nekomimi.nekogram;

import org.telegram.messenger.BuildConfig;

import java.util.List;

import tw.nekomimi.nekogram.helpers.UserHelper;
import tw.nekomimi.nekogram.helpers.remote.ConfigHelper;

public class Extra {

    // Injected via Gradle (from GitHub Secrets)
    public static final int APP_ID = Integer.parseInt(System.getProperty("TELEGRAM_API_ID", "0"));
    public static final String APP_HASH = System.getProperty("TELEGRAM_API_HASH", "");

    public static final String PLAYSTORE_APP_URL = "";

    // Disable WebSocket tracking / custom routing
    public static String WS_USER_AGENT = "";
    public static String WS_CONN_HASH = "";
    public static String WS_DEFAULT_DOMAIN = "";

    // Disable bot integrations
    public static String TWPIC_BOT_USERNAME = null;

    // Disable analytics completely
    public static boolean FORCE_ANALYTICS = false;

    // Disable remote config / telemetry endpoints
    public static String TLV_URL = "";

    // Disable Sentry (crash reporting)
    public static String SENTRY_DSN = "";

    public static boolean isDirectApp() {
        return true; // always allow (avoids restrictions)
    }

    // Disable remote news fetching
    public static List<ConfigHelper.News> getDefaultNews() {
        return null;
    }

    // Disable helper bot
    public static UserHelper.BotInfo getHelperBot() {
        return null;
    }

    // Disable user info bot
    public static UserHelper.UserInfoBot getUserInfoBot(boolean fallback) {
        return null;
    }

    // No trusted bots
    public static boolean isTrustedBot(long id) {
        return false;
    }
}