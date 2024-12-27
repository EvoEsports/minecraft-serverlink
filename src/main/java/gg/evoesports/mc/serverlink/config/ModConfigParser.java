package gg.evoesports.mc.serverlink.config;

import gg.evoesports.mc.serverlink.Serverlink;

public class ModConfigParser {
    public static String getString(String name) {
        return System.getenv(makeName(name));
    }

    public static int getInt(String name) {
        return Integer.parseInt(System.getenv(makeName(name)));
    }

    public static boolean getBoolean(String name) {
        return Boolean.parseBoolean(System.getenv(makeName(name)));
    }

    public static String[] getStringArray(String name) {
        return System.getenv(makeName(name)).split(",");
    }

    public static float getFloat(String name) {
        return Float.parseFloat(System.getenv(makeName(name)));
    }

    public static double getDouble(String name) {
        return Double.parseDouble(System.getenv(makeName(name)));
    }

    private static String makeName(String name) {
        return (Serverlink.MOD_ID + "_" + name).toUpperCase();
    }
}
