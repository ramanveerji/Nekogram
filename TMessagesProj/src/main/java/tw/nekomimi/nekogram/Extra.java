package tw.nekomimi.nekogram;

import org.telegram.messenger.BuildConfig;

import java.util.List;

import tw.nekomimi.nekogram.helpers.UserHelper;
import tw.nekomimi.nekogram.helpers.remote.ConfigHelper;

public class Extra {
    // https://core.telegram.org/api/obtaining_api_id
    public static final int APP_ID = 0;
    public static final String APP_HASH = "";

    public static final String PLAYSTORE_APP_URL = "";

    public static String WS_USER_AGENT = "";
    public static String WS_CONN_HASH = "";
    public static String WS_DEFAULT_DOMAIN = "";

    public static String TWPIC_BOT_USERNAME = null;

    public static boolean FORCE_ANALYTICS = false;

    public static String TLV_URL = "";

    public static String SENTRY_DSN = "";

    public static boolean isDirectApp() {
        return "release".equals(BuildConfig.BUILD_TYPE) || "debug".equals(BuildConfig.BUILD_TYPE);
    }

    public static List<ConfigHelper.News> getDefaultNews() {
        return java.util.Collections.emptyList();
    }

    public static UserHelper.BotInfo getHelperBot() {
        return null;
    }

    public static UserHelper.UserInfoBot getUserInfoBot(boolean fallback) {
        if (fallback) {
            return new USInfoBot();
        } else {
            return new TGDBBot();
        }
    }

    private static class USInfoBot extends UserHelper.UserInfoBot {
        @Override
        public long getId() {
            return 189165596;
        }

        @Override
        public String getUsername() {
            return "usinfobot";
        }

        @Override
        public UserHelper.ParsedPeer parsePeer(String[] lines) {
            var peer = new UserHelper.ParsedPeer();
            for (var line : lines) {
                line = line.replaceAll("\\p{C}", "").trim();
                if (line.startsWith("\uD83D\uDC64")) {
                    var id = org.telegram.messenger.Utilities.parseLong(line.replace("\uD83D\uDC64", "").trim());
                    if (id > 0) {
                        peer.id = id;
                    }
                } else if (line.startsWith("\uD83D\uDC66\uD83C\uDFFB")) {
                    peer.first_name = line.replace("\uD83D\uDC66\uD83C\uDFFB", "").trim();
                } else if (line.startsWith("\uD83D\uDC6A")) {
                    peer.last_name = line.replace("\uD83D\uDC6A", "").trim();
                } else if (line.startsWith("\uD83C\uDF10")) {
                    peer.username = line.replace("\uD83C\uDF10", "").replace("@", "").trim();
                } else if (line.startsWith("\uD83D\uDC65")) {
                    var id = org.telegram.messenger.Utilities.parseLong(line.replace("\uD83D\uDC65", "").trim());
                    if (id < 0) {
                        if (id < -1000000000000L) {
                            peer.id = -1000000000000L - id;
                        } else {
                            peer.id = -id;
                        }
                    }
                } else if (line.startsWith("\uD83C\uDFF7")) {
                    peer.title = line.replace("\uD83C\uDFF7", "").trim();
                }
            }
            return peer;
        }
    }

    private static class TGDBBot extends UserHelper.UserInfoBot {
        @Override
        public long getId() {
            return 7424190611L;
        }

        @Override
        public String getUsername() {
            return "tgdb_search_bot";
        }

        @Override
        public UserHelper.ParsedPeer parsePeer(String[] lines) {
            var peer = new UserHelper.ParsedPeer();
            for (var line : lines) {
                line = line.replaceAll("\\p{C}", "").trim();
                if (line.startsWith("\uD83C\uDD94 ID:")) {
                    var id = org.telegram.messenger.Utilities.parseLong(line.replace("\uD83C\uDD94 ID:", "").trim());
                    if (id != 0) {
                        peer.id = id;
                    }
                } else if (line.startsWith("\uD83C\uDFF7 Title:")) {
                    var title = line.replace("\uD83C\uDFF7 Title:", "").trim();
                    peer.last_name = title;
                    peer.title = title;
                } else if (line.startsWith("\uD83D\uDCE7 Username:")) {
                    peer.username = line.replace("\uD83D\uDCE7 Username:", "").replace("@", "").trim();
                }
            }
            return peer;
        }
    }

    public static boolean isTrustedBot(long id) {
        // IDs for usinfobot, tgdb_search_bot, and official notification bots
        return id == 189165596 || id == 7424190611L || id == 6371744499L || id == 1190800416L;
    }
}