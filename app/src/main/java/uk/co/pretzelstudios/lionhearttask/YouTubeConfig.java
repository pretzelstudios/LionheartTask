package uk.co.pretzelstudios.lionhearttask;

public class YouTubeConfig {
    public YouTubeConfig() {
    }

    // this is my youtube player API key required to use the player. This has been restricted to my app. SHA-1 : 30:AF:A1:9D:3C:61:1F:64:A2:2D:68:00:AD:D8:68:39:B5:39:64:99
    private static  final String API_KEY = "AIzaSyChiz-PRDen-f5MIO2eP-3iVy2pxhLW9Ms";

    public static String getApiKey() {
        return API_KEY;
    }
}
