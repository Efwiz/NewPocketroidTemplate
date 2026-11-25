package org.apache.xml.serialize;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import org.apache.xerces.util.EncodingMap;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: loaded from: classes13.dex */
public class EncodingInfo {
    String ianaName;
    String javaName;
    int lastPrintable;
    private Object[] fArgsForMethod = null;
    Object fCharsetEncoder = null;
    Object fCharToByteConverter = null;
    boolean fHaveTriedCToB = false;
    boolean fHaveTriedCharsetEncoder = false;

    static class CharToByteConverterMethods {
        private static java.lang.reflect.Method fgCanConvertMethod = null;
        private static boolean fgConvertersAvailable = false;
        private static java.lang.reflect.Method fgGetConverterMethod;

        static {
            try {
                Class<?> cls = Class.forName("sun.io.CharToByteConverter");
                fgGetConverterMethod = cls.getMethod("getConverter", String.class);
                fgCanConvertMethod = cls.getMethod("canConvert", Character.TYPE);
                fgConvertersAvailable = true;
            } catch (Exception unused) {
                fgGetConverterMethod = null;
                fgCanConvertMethod = null;
                fgConvertersAvailable = false;
            }
        }

        private CharToByteConverterMethods() {
        }
    }

    static class CharsetMethods {
        private static java.lang.reflect.Method fgCharsetCanEncodeMethod = null;
        private static java.lang.reflect.Method fgCharsetEncoderCanEncodeMethod = null;
        private static java.lang.reflect.Method fgCharsetForNameMethod = null;
        private static java.lang.reflect.Method fgCharsetNewEncoderMethod = null;
        private static boolean fgNIOCharsetAvailable = false;

        static {
            try {
                Class<?> cls = Class.forName(CommonClassNames.JAVA_NIO_CHARSET_CHARSET);
                Class<?> cls2 = Class.forName("java.nio.charset.CharsetEncoder");
                fgCharsetForNameMethod = cls.getMethod("forName", String.class);
                fgCharsetCanEncodeMethod = cls.getMethod("canEncode", new Class[0]);
                fgCharsetNewEncoderMethod = cls.getMethod("newEncoder", new Class[0]);
                fgCharsetEncoderCanEncodeMethod = cls2.getMethod("canEncode", Character.TYPE);
                fgNIOCharsetAvailable = true;
            } catch (Exception unused) {
                fgCharsetForNameMethod = null;
                fgCharsetCanEncodeMethod = null;
                fgCharsetEncoderCanEncodeMethod = null;
                fgCharsetNewEncoderMethod = null;
                fgNIOCharsetAvailable = false;
            }
        }

        private CharsetMethods() {
        }
    }

    public EncodingInfo(String str, String str2, int i) {
        this.ianaName = str;
        this.javaName = EncodingMap.getIANA2JavaMapping(str);
        this.lastPrintable = i;
    }

    private boolean isPrintable0(char c) {
        if (this.fCharsetEncoder == null && CharsetMethods.fgNIOCharsetAvailable && !this.fHaveTriedCharsetEncoder) {
            if (this.fArgsForMethod == null) {
                this.fArgsForMethod = new Object[1];
            }
            try {
                this.fArgsForMethod[0] = this.javaName;
                Object objInvoke = CharsetMethods.fgCharsetForNameMethod.invoke(null, this.fArgsForMethod);
                if (((Boolean) CharsetMethods.fgCharsetCanEncodeMethod.invoke(objInvoke, null)).booleanValue()) {
                    this.fCharsetEncoder = CharsetMethods.fgCharsetNewEncoderMethod.invoke(objInvoke, null);
                } else {
                    this.fHaveTriedCharsetEncoder = true;
                }
            } catch (Exception unused) {
                this.fHaveTriedCharsetEncoder = true;
            }
        }
        if (this.fCharsetEncoder != null) {
            try {
                this.fArgsForMethod[0] = new Character(c);
                return ((Boolean) CharsetMethods.fgCharsetEncoderCanEncodeMethod.invoke(this.fCharsetEncoder, this.fArgsForMethod)).booleanValue();
            } catch (Exception unused2) {
                this.fCharsetEncoder = null;
                this.fHaveTriedCharsetEncoder = false;
            }
        }
        if (this.fCharToByteConverter == null) {
            if (!this.fHaveTriedCToB && CharToByteConverterMethods.fgConvertersAvailable) {
                if (this.fArgsForMethod == null) {
                    this.fArgsForMethod = new Object[1];
                }
                try {
                    this.fArgsForMethod[0] = this.javaName;
                    this.fCharToByteConverter = CharToByteConverterMethods.fgGetConverterMethod.invoke(null, this.fArgsForMethod);
                } catch (Exception unused3) {
                    this.fHaveTriedCToB = true;
                }
            }
            return false;
        }
        try {
            this.fArgsForMethod[0] = new Character(c);
            return ((Boolean) CharToByteConverterMethods.fgCanConvertMethod.invoke(this.fCharToByteConverter, this.fArgsForMethod)).booleanValue();
        } catch (Exception unused4) {
            this.fCharToByteConverter = null;
            this.fHaveTriedCToB = false;
            return false;
        }
    }

    public static void testJavaEncodingName(String str) throws UnsupportedEncodingException {
        new String("valid".getBytes(), str);
    }

    public String getIANAName() {
        return this.ianaName;
    }

    public Writer getWriter(OutputStream outputStream) throws UnsupportedEncodingException {
        if (this.javaName != null) {
            return new OutputStreamWriter(outputStream, this.javaName);
        }
        String iANA2JavaMapping = EncodingMap.getIANA2JavaMapping(this.ianaName);
        this.javaName = iANA2JavaMapping;
        return iANA2JavaMapping == null ? new OutputStreamWriter(outputStream, "UTF8") : new OutputStreamWriter(outputStream, this.javaName);
    }

    public boolean isPrintable(char c) {
        if (c <= this.lastPrintable) {
            return true;
        }
        return isPrintable0(c);
    }
}
