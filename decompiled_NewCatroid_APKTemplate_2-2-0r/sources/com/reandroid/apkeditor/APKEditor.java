package com.reandroid.apkeditor;

import com.beust.jcommander.Parameters;
import com.reandroid.arsc.ARSCLib;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/* JADX INFO: loaded from: classes10.dex */
public class APKEditor {
    public static final String PATH_properties = "/apkeditor.properties";
    private static Properties sProperties;

    public static String getARSCLibInfo() {
        return ARSCLib.getName() + Parameters.DEFAULT_OPTION_PREFIXES + ARSCLib.getVersion();
    }

    public static String getDescription() {
        return getProperties().getProperty("app.description", "---");
    }

    public static String getRepo() {
        return getProperties().getProperty("app.repo", "https://github.com/REAndroid");
    }

    public static String getName() {
        return getProperties().getProperty("app.name", "---");
    }

    public static String getVersion() {
        return getProperties().getProperty("app.version", "---");
    }

    private static Properties getProperties() {
        Properties properties = sProperties;
        if (properties != null) {
            return properties;
        }
        Properties properties2 = new Properties();
        sProperties = properties2;
        try {
            properties2.load(APKEditor.class.getResourceAsStream(PATH_properties));
        } catch (IOException e) {
            sProperties.put("app.description", e.getMessage());
        }
        return sProperties;
    }

    public static String getJarName() {
        File file = new File(APKEditor.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        if (file.isFile()) {
            return file.getName();
        }
        return getName() + ".jar";
    }

    public static boolean isExperimental() {
        return getVersion().contains("beta");
    }
}
