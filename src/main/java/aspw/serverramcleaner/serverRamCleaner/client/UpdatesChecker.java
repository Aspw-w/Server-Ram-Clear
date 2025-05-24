package aspw.serverramcleaner.serverRamCleaner.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class UpdatesChecker {

    public static boolean isLatestClient = false;

    public static void updateCheck() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url("https://nattogreatapi.pages.dev/ServerRamCleaner/updts/latest.txt")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                isLatestClient = responseBody.equals(ServerRamCleanerMod.modVersion);
            }
        } catch (IOException ignored) {
        }
    }
}
