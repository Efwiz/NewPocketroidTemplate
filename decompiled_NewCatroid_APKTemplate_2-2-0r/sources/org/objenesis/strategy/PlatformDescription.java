package org.objenesis.strategy;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.apache.commons.lang3.SystemProperties;
import org.objenesis.ObjenesisException;

/* JADX INFO: loaded from: classes6.dex */
public final class PlatformDescription {
    public static final String DALVIK = "Dalvik";
    public static final String GNU = "GNU libgcj";
    public static final String HOTSPOT = "Java HotSpot";
    public static final String OPENJDK = "OpenJDK";
    public static final String PERC = "PERC";

    @Deprecated
    public static final String SUN = "Java HotSpot";
    public static final String SPECIFICATION_VERSION = System.getProperty(SystemProperties.JAVA_SPECIFICATION_VERSION);
    public static final String VM_VERSION = System.getProperty(SystemProperties.JAVA_RUNTIME_VERSION);
    public static final String VM_INFO = System.getProperty(SystemProperties.JAVA_VM_INFO);
    public static final String VENDOR_VERSION = System.getProperty(SystemProperties.JAVA_VM_VERSION);
    public static final String VENDOR = System.getProperty(SystemProperties.JAVA_VM_VENDOR);
    public static final String JVM_NAME = System.getProperty(SystemProperties.JAVA_VM_NAME);
    public static final int ANDROID_VERSION = getAndroidVersion();
    public static final boolean IS_ANDROID_OPENJDK = getIsAndroidOpenJDK();
    public static final String GAE_VERSION = getGaeRuntimeVersion();

    public static String describePlatform() {
        String str = "Java " + SPECIFICATION_VERSION + " (VM vendor name=\"" + VENDOR + "\", VM vendor version=" + VENDOR_VERSION + ", JVM name=\"" + JVM_NAME + "\", JVM version=" + VM_VERSION + ", JVM info=" + VM_INFO;
        int i = ANDROID_VERSION;
        if (i != 0) {
            str = str + ", API level=" + i;
        }
        return str + Constant.AFTER_QUTO;
    }

    public static boolean isThisJVM(String str) {
        return JVM_NAME.startsWith(str);
    }

    public static boolean isAndroidOpenJDK() {
        return IS_ANDROID_OPENJDK;
    }

    private static boolean getIsAndroidOpenJDK() {
        String property;
        return (getAndroidVersion() == 0 || (property = System.getProperty("java.boot.class.path")) == null || !property.toLowerCase().contains("core-oj.jar")) ? false : true;
    }

    public static boolean isAfterJigsaw() {
        return SPECIFICATION_VERSION.indexOf(46) < 0;
    }

    public static boolean isAfterJava11() {
        return isAfterJigsaw() && Integer.parseInt(SPECIFICATION_VERSION) >= 11;
    }

    public static boolean isGoogleAppEngine() {
        return GAE_VERSION != null;
    }

    private static String getGaeRuntimeVersion() {
        return System.getProperty("com.google.appengine.runtime.version");
    }

    private static int getAndroidVersion() {
        if (isThisJVM(DALVIK)) {
            return getAndroidVersion0();
        }
        return 0;
    }

    private static int getAndroidVersion0() {
        try {
            Class<?> cls = Class.forName("android.os.Build$VERSION");
            try {
                try {
                    return ((Integer) cls.getField("SDK_INT").get(null)).intValue();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } catch (NoSuchFieldException unused) {
                return getOldAndroidVersion(cls);
            }
        } catch (ClassNotFoundException e2) {
            throw new ObjenesisException(e2);
        }
    }

    private static int getOldAndroidVersion(Class<?> cls) {
        try {
            try {
                return Integer.parseInt((String) cls.getField("SDK").get(null));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (NoSuchFieldException e2) {
            throw new ObjenesisException(e2);
        }
    }

    private PlatformDescription() {
    }
}
