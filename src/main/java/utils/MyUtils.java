package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtils {
    private static Pattern cityCodePattern = Pattern.compile("[a-z]-[a-z]*[0-9]*-[a-z]+");
    private static Pattern siteCodePattern = Pattern.compile("p-oi[0-9]+-[a-z]+");
    private static Pattern numberPattern = Pattern.compile("[0-9]+");

    public static String extractCityCode(String content) {
        return find(cityCodePattern, content);
    }

    public static String extractId(String content) {
        return find(siteCodePattern, content);
    }


    private static String find(Pattern pattern, String content) {
        if (content == null) return "0.0";

        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group();
        }

        return "0.0";
    }

    public static double getScore(String str) {
        String numStr = find(numberPattern, str);
        if (numStr == null) return 0;
        int number = Integer.parseInt(numStr);
        return 5.0 * number / 100.0;
    }
}
