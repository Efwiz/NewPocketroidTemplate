package org.apache.xmlcommons;

/* JADX INFO: loaded from: classes13.dex */
public class Version {
    public static String getProduct() {
        return "XmlCommonsExternal";
    }

    public static String getVersion() {
        return new StringBuffer().append(getProduct()).append(" ").append(getVersionNum()).toString();
    }

    public static String getVersionNum() {
        return "1.4.01";
    }

    public static void main(String[] strArr) {
        System.out.println(getVersion());
    }
}
