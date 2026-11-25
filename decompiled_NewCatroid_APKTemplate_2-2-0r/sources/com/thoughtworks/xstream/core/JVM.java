package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.core.util.Base64Encoder;
import com.thoughtworks.xstream.core.util.CustomObjectOutputStream;
import com.thoughtworks.xstream.core.util.DependencyInjectionFactory;
import com.thoughtworks.xstream.core.util.PresortedMap;
import com.thoughtworks.xstream.core.util.PresortedSet;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.lang3.SystemProperties;
import org.catrobat.catroid.ui.SpriteActivity;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/JVM.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class JVM implements Caching {
    private static final float DEFAULT_JAVA_VERSION = 1.4f;
    private static final StringCodec base64Codec;
    private static final boolean canAllocateWithUnsafe;
    private static final boolean canCreateDerivedObjectOutputStream;
    private static final boolean canParseISO8601TimeZoneInDateFormat;
    private static final boolean canParseUTCDateFormat;
    private static final boolean canWriteWithUnsafe;
    static /* synthetic */ Class class$com$thoughtworks$xstream$converters$reflection$PureJavaReflectionProvider = null;
    static /* synthetic */ Class class$com$thoughtworks$xstream$core$JVM = null;
    static /* synthetic */ Class class$com$thoughtworks$xstream$core$JVM$Test = null;
    static /* synthetic */ Class class$java$lang$Class = null;
    static /* synthetic */ Class class$java$text$AttributedString = null;
    private static final boolean isAWTAvailable;
    private static final boolean isSQLAvailable;
    private static final boolean isSwingAvailable;
    private static final boolean optimizedTreeMapPutAll;
    private static final boolean optimizedTreeSetAddAll;
    private static final Class reflectionProviderType;
    private static final boolean reverseFieldOrder = false;
    private ReflectionProvider reflectionProvider;
    private static final String vendor = System.getProperty(SystemProperties.JAVA_VM_VENDOR);
    private static final float majorJavaVersion = getMajorJavaVersion();

    public static boolean reverseFieldDefinition() {
        return false;
    }

    @Override // com.thoughtworks.xstream.core.Caching
    public void flushCache() {
    }

    static {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        Class clsLoadClassForName;
        StringCodec base64Encoder = null;
        try {
            Class<?> cls = Class.forName("sun.misc.Unsafe");
            Field declaredField = cls.getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(null);
            Class<?>[] clsArr = new Class[1];
            Class<?> clsClass$ = class$java$lang$Class;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_LANG_CLASS);
                class$java$lang$Class = clsClass$;
            }
            clsArr[0] = clsClass$;
            Method declaredMethod = cls.getDeclaredMethod("allocateInstance", clsArr);
            declaredMethod.setAccessible(true);
            Class clsClass$2 = class$com$thoughtworks$xstream$core$JVM$Test;
            if (clsClass$2 == null) {
                clsClass$2 = class$("com.thoughtworks.xstream.core.JVM$Test");
                class$com$thoughtworks$xstream$core$JVM$Test = clsClass$2;
            }
            z = declaredMethod.invoke(obj, clsClass$2) != null;
        } catch (Error | Exception unused) {
        }
        canAllocateWithUnsafe = z;
        Class clsLoadClassForName2 = class$com$thoughtworks$xstream$converters$reflection$PureJavaReflectionProvider;
        if (clsLoadClassForName2 == null) {
            clsLoadClassForName2 = class$("com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider");
            class$com$thoughtworks$xstream$converters$reflection$PureJavaReflectionProvider = clsLoadClassForName2;
        }
        if (!canUseSunUnsafeReflectionProvider() || (clsLoadClassForName = loadClassForName("com.thoughtworks.xstream.converters.reflection.SunUnsafeReflectionProvider")) == null) {
            z2 = false;
        } else {
            try {
                ReflectionProvider reflectionProvider = (ReflectionProvider) DependencyInjectionFactory.newInstance(clsLoadClassForName, null);
                Class clsClass$3 = class$com$thoughtworks$xstream$core$JVM$Test;
                if (clsClass$3 == null) {
                    clsClass$3 = class$("com.thoughtworks.xstream.core.JVM$Test");
                    class$com$thoughtworks$xstream$core$JVM$Test = clsClass$3;
                }
                Test test = (Test) reflectionProvider.newInstance(clsClass$3);
                try {
                    Class clsClass$4 = class$com$thoughtworks$xstream$core$JVM$Test;
                    if (clsClass$4 == null) {
                        clsClass$4 = class$("com.thoughtworks.xstream.core.JVM$Test");
                        class$com$thoughtworks$xstream$core$JVM$Test = clsClass$4;
                    }
                    reflectionProvider.writeField(test, "o", "object", clsClass$4);
                    Character ch = new Character('c');
                    Class clsClass$5 = class$com$thoughtworks$xstream$core$JVM$Test;
                    if (clsClass$5 == null) {
                        clsClass$5 = class$("com.thoughtworks.xstream.core.JVM$Test");
                        class$com$thoughtworks$xstream$core$JVM$Test = clsClass$5;
                    }
                    reflectionProvider.writeField(test, "c", ch, clsClass$5);
                    Byte b = new Byte((byte) 1);
                    Class clsClass$6 = class$com$thoughtworks$xstream$core$JVM$Test;
                    if (clsClass$6 == null) {
                        clsClass$6 = class$("com.thoughtworks.xstream.core.JVM$Test");
                        class$com$thoughtworks$xstream$core$JVM$Test = clsClass$6;
                    }
                    reflectionProvider.writeField(test, JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, b, clsClass$6);
                    Short sh = new Short((short) 1);
                    Class clsClass$7 = class$com$thoughtworks$xstream$core$JVM$Test;
                    if (clsClass$7 == null) {
                        clsClass$7 = class$("com.thoughtworks.xstream.core.JVM$Test");
                        class$com$thoughtworks$xstream$core$JVM$Test = clsClass$7;
                    }
                    reflectionProvider.writeField(test, "s", sh, clsClass$7);
                    Integer num = new Integer(1);
                    Class clsClass$8 = class$com$thoughtworks$xstream$core$JVM$Test;
                    if (clsClass$8 == null) {
                        clsClass$8 = class$("com.thoughtworks.xstream.core.JVM$Test");
                        class$com$thoughtworks$xstream$core$JVM$Test = clsClass$8;
                    }
                    reflectionProvider.writeField(test, ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, num, clsClass$8);
                    Long l = new Long(1L);
                    Class clsClass$9 = class$com$thoughtworks$xstream$core$JVM$Test;
                    if (clsClass$9 == null) {
                        clsClass$9 = class$("com.thoughtworks.xstream.core.JVM$Test");
                        class$com$thoughtworks$xstream$core$JVM$Test = clsClass$9;
                    }
                    reflectionProvider.writeField(test, "l", l, clsClass$9);
                    Float f = new Float(1.0f);
                    Class clsClass$10 = class$com$thoughtworks$xstream$core$JVM$Test;
                    if (clsClass$10 == null) {
                        clsClass$10 = class$("com.thoughtworks.xstream.core.JVM$Test");
                        class$com$thoughtworks$xstream$core$JVM$Test = clsClass$10;
                    }
                    reflectionProvider.writeField(test, "f", f, clsClass$10);
                    Double d = new Double(1.0d);
                    Class clsClass$11 = class$com$thoughtworks$xstream$core$JVM$Test;
                    if (clsClass$11 == null) {
                        clsClass$11 = class$("com.thoughtworks.xstream.core.JVM$Test");
                        class$com$thoughtworks$xstream$core$JVM$Test = clsClass$11;
                    }
                    reflectionProvider.writeField(test, "d", d, clsClass$11);
                    Boolean bool = Boolean.TRUE;
                    Class clsClass$12 = class$com$thoughtworks$xstream$core$JVM$Test;
                    if (clsClass$12 == null) {
                        clsClass$12 = class$("com.thoughtworks.xstream.core.JVM$Test");
                        class$com$thoughtworks$xstream$core$JVM$Test = clsClass$12;
                    }
                    reflectionProvider.writeField(test, "bool", bool, clsClass$12);
                    z2 = true;
                } catch (ObjectAccessException | IncompatibleClassChangeError unused2) {
                    clsLoadClassForName = null;
                    z2 = false;
                }
                if (clsLoadClassForName == null) {
                    try {
                        clsLoadClassForName2 = loadClassForName("com.thoughtworks.xstream.converters.reflection.SunLimitedUnsafeReflectionProvider");
                    } catch (ObjectAccessException unused3) {
                    }
                } else {
                    clsLoadClassForName2 = clsLoadClassForName;
                }
            } catch (ObjectAccessException unused4) {
                z2 = false;
            }
        }
        reflectionProviderType = clsLoadClassForName2;
        canWriteWithUnsafe = z2;
        Comparator comparator = new Comparator() { // from class: com.thoughtworks.xstream.core.JVM.1
            @Override // java.util.Comparator
            public int compare(Object obj2, Object obj3) {
                throw new RuntimeException();
            }
        };
        PresortedMap presortedMap = new PresortedMap(comparator);
        presortedMap.put("one", null);
        presortedMap.put("two", null);
        try {
            new TreeMap(comparator).putAll(presortedMap);
            z3 = true;
        } catch (RuntimeException unused5) {
            z3 = false;
        }
        optimizedTreeMapPutAll = z3;
        PresortedSet presortedSet = new PresortedSet(comparator);
        presortedSet.addAll(presortedMap.keySet());
        try {
            new TreeSet(comparator).addAll(presortedSet);
            z4 = true;
        } catch (RuntimeException unused6) {
            z4 = false;
        }
        optimizedTreeSetAddAll = z4;
        try {
            new SimpleDateFormat(CompressorStreamFactory.Z).parse("UTC");
            z5 = true;
        } catch (ParseException unused7) {
            z5 = false;
        }
        canParseUTCDateFormat = z5;
        try {
            new SimpleDateFormat(SpriteActivity.EXTRA_X_TRANSFORM).parse("Z");
            z6 = true;
        } catch (IllegalArgumentException | ParseException unused8) {
            z6 = false;
        }
        canParseISO8601TimeZoneInDateFormat = z6;
        try {
            new CustomObjectOutputStream(null);
            z7 = true;
        } catch (IOException | RuntimeException unused9) {
            z7 = false;
        }
        canCreateDerivedObjectOutputStream = z7;
        isAWTAvailable = loadClassForName("java.awt.Color", false) != null;
        isSwingAvailable = loadClassForName("javax.swing.LookAndFeel", false) != null;
        isSQLAvailable = loadClassForName("java.sql.Date") != null;
        Class clsLoadClassForName3 = loadClassForName("com.thoughtworks.xstream.core.util.Base64JavaUtilCodec");
        if (clsLoadClassForName3 == null) {
            clsLoadClassForName3 = loadClassForName("com.thoughtworks.xstream.core.util.Base64JAXBCodec");
        }
        if (clsLoadClassForName3 != null) {
            try {
                base64Encoder = (StringCodec) clsLoadClassForName3.newInstance();
            } catch (Error | Exception unused10) {
            }
        }
        if (base64Encoder == null) {
            base64Encoder = new Base64Encoder();
        }
        base64Codec = base64Encoder;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/JVM$Test.class
      classes11.dex
     */
    static class Test {
        private byte b;
        private boolean bool;
        private char c;
        private double d;
        private float f;
        private int i;
        private long l;
        private Object o;
        private short s;

        Test() {
            throw new UnsupportedOperationException();
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private static final float getMajorJavaVersion() {
        try {
            if (isAndroid()) {
                return 1.5f;
            }
            return Float.parseFloat(System.getProperty(SystemProperties.JAVA_SPECIFICATION_VERSION));
        } catch (NumberFormatException unused) {
            return DEFAULT_JAVA_VERSION;
        }
    }

    public static boolean is14() {
        return isVersion(4);
    }

    public static boolean is15() {
        return isVersion(5);
    }

    public static boolean is16() {
        return isVersion(6);
    }

    public static boolean is17() {
        return isVersion(7);
    }

    public static boolean is18() {
        return isVersion(8);
    }

    public static boolean is19() {
        return majorJavaVersion >= 1.9f;
    }

    public static boolean is9() {
        return isVersion(9);
    }

    public static boolean isVersion(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("Java version range starts with at least 1.");
        }
        float f = majorJavaVersion;
        float f2 = i;
        if (f < 9.0f) {
            f2 = (f2 * 0.1f) + 1.0f;
        }
        return f >= f2;
    }

    private static boolean isIBM() {
        return vendor.indexOf("IBM") != -1;
    }

    private static boolean isAndroid() {
        return vendor.indexOf("Android") != -1;
    }

    public static Class loadClassForName(String str) {
        return loadClassForName(str, true);
    }

    public Class loadClass(String str) {
        return loadClassForName(str, true);
    }

    public static Class loadClassForName(String str, boolean z) {
        try {
            Class clsClass$ = class$com$thoughtworks$xstream$core$JVM;
            if (clsClass$ == null) {
                clsClass$ = class$("com.thoughtworks.xstream.core.JVM");
                class$com$thoughtworks$xstream$core$JVM = clsClass$;
            }
            return Class.forName(str, z, clsClass$.getClassLoader());
        } catch (ClassNotFoundException | LinkageError unused) {
            return null;
        }
    }

    public Class loadClass(String str, boolean z) {
        return loadClassForName(str, z);
    }

    public static ReflectionProvider newReflectionProvider() {
        return (ReflectionProvider) DependencyInjectionFactory.newInstance(reflectionProviderType, null);
    }

    public static ReflectionProvider newReflectionProvider(FieldDictionary fieldDictionary) {
        return (ReflectionProvider) DependencyInjectionFactory.newInstance(reflectionProviderType, new Object[]{fieldDictionary});
    }

    public static Class getStaxInputFactory() throws ClassNotFoundException {
        if (!isVersion(6)) {
            return null;
        }
        if (isIBM()) {
            return Class.forName("com.ibm.xml.xlxp.api.stax.XMLInputFactoryImpl");
        }
        return Class.forName("com.sun.xml.internal.stream.XMLInputFactoryImpl");
    }

    public static Class getStaxOutputFactory() throws ClassNotFoundException {
        if (!isVersion(6)) {
            return null;
        }
        if (isIBM()) {
            return Class.forName("com.ibm.xml.xlxp.api.stax.XMLOutputFactoryImpl");
        }
        return Class.forName("com.sun.xml.internal.stream.XMLOutputFactoryImpl");
    }

    public static StringCodec getBase64Codec() {
        return base64Codec;
    }

    public synchronized ReflectionProvider bestReflectionProvider() {
        if (this.reflectionProvider == null) {
            this.reflectionProvider = newReflectionProvider();
        }
        return this.reflectionProvider;
    }

    private static boolean canUseSunUnsafeReflectionProvider() {
        return canAllocateWithUnsafe;
    }

    private static boolean canUseSunLimitedUnsafeReflectionProvider() {
        return canWriteWithUnsafe;
    }

    public static boolean isAWTAvailable() {
        return isAWTAvailable;
    }

    public boolean supportsAWT() {
        return isAWTAvailable;
    }

    public static boolean isSwingAvailable() {
        return isSwingAvailable;
    }

    public boolean supportsSwing() {
        return isSwingAvailable;
    }

    public static boolean isSQLAvailable() {
        return isSQLAvailable;
    }

    public boolean supportsSQL() {
        return isSQLAvailable;
    }

    public static boolean hasOptimizedTreeSetAddAll() {
        return optimizedTreeSetAddAll;
    }

    public static boolean hasOptimizedTreeMapPutAll() {
        return optimizedTreeMapPutAll;
    }

    public static boolean canParseUTCDateFormat() {
        return canParseUTCDateFormat;
    }

    public static boolean canParseISO8601TimeZoneInDateFormat() {
        return canParseISO8601TimeZoneInDateFormat;
    }

    public static boolean canCreateDerivedObjectOutputStream() {
        return canCreateDerivedObjectOutputStream;
    }

    public static void main(String[] strArr) throws Throwable {
        boolean z;
        boolean z2;
        String message;
        Class clsClass$ = class$java$text$AttributedString;
        if (clsClass$ == null) {
            clsClass$ = class$("java.text.AttributedString");
            class$java$text$AttributedString = clsClass$;
        }
        Field[] declaredFields = clsClass$.getDeclaredFields();
        int i = 0;
        while (true) {
            if (i < declaredFields.length) {
                if (!declaredFields[i].getName().equals("text")) {
                    i++;
                } else if (i > 3) {
                    z = true;
                    break;
                }
            }
            z = false;
            break;
        }
        Class clsClass$2 = class$com$thoughtworks$xstream$core$JVM$Test;
        if (clsClass$2 == null) {
            clsClass$2 = class$("com.thoughtworks.xstream.core.JVM$Test");
            class$com$thoughtworks$xstream$core$JVM$Test = clsClass$2;
        }
        Field[] declaredFields2 = clsClass$2.getDeclaredFields();
        int i2 = 0;
        while (true) {
            if (i2 < declaredFields2.length) {
                if (!declaredFields2[i2].getName().equals("o")) {
                    i2++;
                } else if (i2 > 3) {
                    z2 = true;
                    break;
                }
            }
            z2 = false;
            break;
        }
        String message2 = null;
        try {
            message = getStaxInputFactory().getName();
        } catch (ClassNotFoundException e) {
            message = e.getMessage();
        } catch (NullPointerException unused) {
            message = null;
        }
        try {
            message2 = getStaxOutputFactory().getName();
        } catch (ClassNotFoundException e2) {
            message2 = e2.getMessage();
        } catch (NullPointerException unused2) {
        }
        System.out.println("XStream JVM diagnostics");
        System.out.println(new StringBuffer("java.specification.version: ").append(System.getProperty(SystemProperties.JAVA_SPECIFICATION_VERSION)).toString());
        System.out.println(new StringBuffer("java.specification.vendor: ").append(System.getProperty(SystemProperties.JAVA_SPECIFICATION_VENDOR)).toString());
        System.out.println(new StringBuffer("java.specification.name: ").append(System.getProperty(SystemProperties.JAVA_SPECIFICATION_NAME)).toString());
        System.out.println(new StringBuffer("java.vm.vendor: ").append(vendor).toString());
        System.out.println(new StringBuffer("java.vendor: ").append(System.getProperty(SystemProperties.JAVA_VENDOR)).toString());
        System.out.println(new StringBuffer("java.vm.name: ").append(System.getProperty(SystemProperties.JAVA_VM_NAME)).toString());
        System.out.println(new StringBuffer("Version: ").append(majorJavaVersion).toString());
        System.out.println(new StringBuffer("XStream support for enhanced Mode: ").append(canUseSunUnsafeReflectionProvider()).toString());
        System.out.println(new StringBuffer("XStream support for reduced Mode: ").append(canUseSunLimitedUnsafeReflectionProvider()).toString());
        System.out.println(new StringBuffer("Supports AWT: ").append(isAWTAvailable()).toString());
        System.out.println(new StringBuffer("Supports Swing: ").append(isSwingAvailable()).toString());
        System.out.println(new StringBuffer("Supports SQL: ").append(isSQLAvailable()).toString());
        System.out.println(new StringBuffer("Java Beans EventHandler present: ").append(loadClassForName("java.beans.EventHandler") != null).toString());
        System.out.println(new StringBuffer("Standard StAX XMLInputFactory: ").append(message).toString());
        System.out.println(new StringBuffer("Standard StAX XMLOutputFactory: ").append(message2).toString());
        System.out.println(new StringBuffer("Standard Base64 Codec: ").append(getBase64Codec().getClass().toString()).toString());
        System.out.println(new StringBuffer("Optimized TreeSet.addAll: ").append(hasOptimizedTreeSetAddAll()).toString());
        System.out.println(new StringBuffer("Optimized TreeMap.putAll: ").append(hasOptimizedTreeMapPutAll()).toString());
        System.out.println(new StringBuffer("Can parse UTC date format: ").append(canParseUTCDateFormat()).toString());
        System.out.println(new StringBuffer("Can create derive ObjectOutputStream: ").append(canCreateDerivedObjectOutputStream()).toString());
        System.out.println(new StringBuffer("Reverse field order detected for JDK: ").append(z).toString());
        System.out.println(new StringBuffer("Reverse field order detected (only if JVM class itself has been compiled): ").append(z2).toString());
    }
}
