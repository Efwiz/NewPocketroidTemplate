package com.reandroid.apkeditor;

import com.reandroid.jcommand.CommandStringResource;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.StringsUtil;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes10.dex */
public class ResourceStrings implements CommandStringResource {
    private static final String DEFAULT_CONFIG = "";
    public static final ResourceStrings INSTANCE = new ResourceStrings();
    private String config;
    private Properties defaultProperties;
    private boolean ignoreMissingResource;
    private Properties properties;

    private ResourceStrings() {
    }

    @Override // com.reandroid.jcommand.CommandStringResource
    public String getString(String str) {
        String property = getProperties().getProperty(str, null);
        return property == null ? getDefaultProperties().getProperty(str, str) : property;
    }

    public String getConfig() {
        String str = this.config;
        if (str != null) {
            return str;
        }
        String availableConfig = getAvailableConfig(Locale.getDefault());
        this.config = availableConfig;
        return availableConfig;
    }

    public void setConfig(Locale locale) {
        setConfig(getAvailableConfig(locale));
    }

    public void setConfig(String str) {
        if (ObjectsUtil.equals(this.config, str)) {
            return;
        }
        if (!hasConfig(str, null)) {
            str = null;
        }
        this.config = str;
        this.properties = null;
    }

    public void setIgnoreMissingResource(boolean z) {
        this.ignoreMissingResource = z;
    }

    private String getAvailableConfig(Locale locale) {
        if (locale != null) {
            String language = locale.getLanguage();
            String country = locale.getCountry();
            if (!hasConfig(language, country)) {
                country = null;
                if (!hasConfig(language, null)) {
                    language = null;
                }
            }
            return toConfig(language, country);
        }
        return "";
    }

    private boolean hasConfig(String str, String str2) {
        String resourcePath = toResourcePath(str, str2);
        boolean z = false;
        try {
            InputStream resourceAsStream = ResourceStrings.class.getResourceAsStream(resourcePath);
            if (resourceAsStream == null) {
                return false;
            }
            z = true;
            resourceAsStream.close();
            return true;
        } catch (Throwable unused) {
            return z;
        }
    }

    private String toResourcePath(String str, String str2) {
        return "/strings/strings" + toConfig(str, str2) + ".properties";
    }

    private String toConfig(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        if (!StringsUtil.isEmpty(str)) {
            if (str.charAt(0) != '-') {
                sb.append('-');
            }
            sb.append(str);
            if (!StringsUtil.isEmpty(str2)) {
                if (str2.charAt(0) != '-') {
                    sb.append('-');
                }
                sb.append(str2);
            }
        }
        if (sb.length() == 0) {
            return "";
        }
        return sb.toString();
    }

    public Properties getProperties() {
        Properties propertiesLoadProperties = this.properties;
        if (propertiesLoadProperties == null) {
            String config = getConfig();
            if ("".equals(config)) {
                propertiesLoadProperties = new Properties();
            } else {
                propertiesLoadProperties = loadProperties(config);
            }
            this.properties = propertiesLoadProperties;
        }
        return propertiesLoadProperties;
    }

    private Properties getDefaultProperties() {
        Properties properties = this.defaultProperties;
        if (properties != null) {
            return properties;
        }
        Properties propertiesLoadProperties = loadProperties("");
        this.defaultProperties = propertiesLoadProperties;
        initializeDefaultProperties(propertiesLoadProperties);
        return propertiesLoadProperties;
    }

    private void initializeDefaultProperties(Properties properties) {
        properties.put("title_app_name_and_version", APKEditor.getName() + " - " + APKEditor.getVersion());
        properties.put("title_app_repo", APKEditor.getRepo());
    }

    private Properties loadProperties(String str) {
        Properties properties = new Properties();
        String resourcePath = toResourcePath(str, null);
        InputStream resourceAsStream = ResourceStrings.class.getResourceAsStream(resourcePath);
        if (resourceAsStream == null) {
            if (this.ignoreMissingResource) {
                return properties;
            }
            throw new RuntimeException("Missing resource: '" + resourcePath + FormatHelper.QUOTE);
        }
        try {
            properties.load(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8));
            try {
                resourceAsStream.close();
            } catch (Throwable unused) {
            }
            return properties;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
