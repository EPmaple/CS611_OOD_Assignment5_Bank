package utility;

import java.util.HashMap;
import java.util.Map;

public class ColorPrint {
    // ANSI codes
    private static final Map<String, String> colors = new HashMap<>();
    static {
        colors.put("RESET", "\u001B[0m");
        colors.put("BLACK", "\u001B[30m");
        colors.put("RED", "\u001B[31m");
        colors.put("GREEN", "\u001B[32m");
        colors.put("YELLOW", "\u001B[33m");
        colors.put("BLUE", "\u001B[34m");
        colors.put("PURPLE", "\u001B[35m");
        colors.put("CYAN", "\u001B[36m");
        colors.put("WHITE", "\u001B[37m");
    }

    // Set color by name
    public static void setColor(String colorName) {
        String colorCode = colors.getOrDefault(colorName.toUpperCase(), colors.get("RESET"));
        System.out.print(colorCode);
    }

    // Reset color
    public static void resetColor() {
        System.out.print(colors.get("RESET"));
    }

    // Print colored text
    public static void print(String colorName, String text) {
        setColor(colorName);
        System.out.print(text);
        resetColor();
    }

    // Print colored text with formatting
    public static void print(String colorName, String format, Object... args) {
        setColor(colorName);
        System.out.printf(format, args);
        resetColor();
    }

    // Print line in color
    public static void println(String colorName, String text) {
        print(colorName, text);
        System.out.println();
    }
}
