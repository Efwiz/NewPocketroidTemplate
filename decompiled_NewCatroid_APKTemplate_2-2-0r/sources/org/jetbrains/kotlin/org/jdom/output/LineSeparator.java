package org.jetbrains.kotlin.org.jdom.output;

import org.apache.commons.lang3.SystemProperties;
import org.jetbrains.kotlin.org.jdom.internal.SystemProperty;

/* JADX INFO: loaded from: classes4.dex */
public enum LineSeparator {
    CRNL("\r\n"),
    NL("\n"),
    CR("\r"),
    DOS("\r\n"),
    UNIX("\n"),
    SYSTEM(SystemProperty.get(SystemProperties.LINE_SEPARATOR, "\r\n")),
    NONE(null),
    DEFAULT(getDefaultLineSeparator());

    private final String value;

    private static String getDefaultLineSeparator() {
        String str = SystemProperty.get("org.jetbrains.kotlin.org.jdom.output.LineSeparator", "DEFAULT");
        if ("DEFAULT".equals(str)) {
            return "\r\n";
        }
        if ("SYSTEM".equals(str)) {
            return System.getProperty(SystemProperties.LINE_SEPARATOR);
        }
        if ("CRNL".equals(str)) {
            return "\r\n";
        }
        if ("NL".equals(str)) {
            return "\n";
        }
        if ("CR".equals(str)) {
            return "\r";
        }
        if ("DOS".equals(str)) {
            return "\r\n";
        }
        if ("UNIX".equals(str)) {
            return "\n";
        }
        if ("NONE".equals(str)) {
            return null;
        }
        return str;
    }

    LineSeparator(String str) {
        this.value = str;
    }

    public String value() {
        return this.value;
    }
}
