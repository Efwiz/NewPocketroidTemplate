package org.eclipse.jgit.lib;

import com.huawei.hms.framework.common.ContainerUtils;
import org.eclipse.jgit.util.StringUtils;

/* JADX INFO: loaded from: classes13.dex */
class ConfigLine {
    String includedFrom;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    String f653name;
    String prefix;
    String section;
    String subsection;
    String suffix;
    String value;

    ConfigLine() {
    }

    ConfigLine forValue(String str) {
        ConfigLine configLine = new ConfigLine();
        configLine.prefix = this.prefix;
        configLine.section = this.section;
        configLine.subsection = this.subsection;
        configLine.f653name = this.f653name;
        configLine.value = str;
        configLine.suffix = this.suffix;
        configLine.includedFrom = this.includedFrom;
        return configLine;
    }

    boolean match(String str, String str2, String str3) {
        return eqIgnoreCase(this.section, str) && eqSameCase(this.subsection, str2) && eqIgnoreCase(this.f653name, str3);
    }

    boolean match(String str, String str2) {
        return eqIgnoreCase(this.section, str) && eqSameCase(this.subsection, str2);
    }

    private static boolean eqIgnoreCase(String str, String str2) {
        if (str == null && str2 == null) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        return StringUtils.equalsIgnoreCase(str, str2);
    }

    private static boolean eqSameCase(String str, String str2) {
        if (str == null && str2 == null) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        return str.equals(str2);
    }

    public String toString() {
        if (this.section == null) {
            return "<empty>";
        }
        StringBuilder sb = new StringBuilder(this.section);
        if (this.subsection != null) {
            sb.append(".").append(this.subsection);
        }
        if (this.f653name != null) {
            sb.append(".").append(this.f653name);
        }
        if (this.value != null) {
            sb.append(ContainerUtils.KEY_VALUE_DELIMITER).append(this.value);
        }
        return sb.toString();
    }
}
