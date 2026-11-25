package org.jf.dexlib2.analysis.reflection.util;

import androidx.exifinterface.media.ExifInterface;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes6.dex */
public class ReflectionUtils {
    private static String getPrimitive(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("boolean")) {
            return "Z";
        }
        if (str.equals("int")) {
            return "I";
        }
        if (str.equals("long")) {
            return "J";
        }
        if (str.equals("double")) {
            return "D";
        }
        if (str.equals(PsiKeyword.VOID)) {
            return ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
        }
        if (str.equals("float")) {
            return "F";
        }
        if (str.equals(PsiKeyword.CHAR)) {
            return "C";
        }
        if (str.equals("short")) {
            return ExifInterface.LATITUDE_SOUTH;
        }
        if (str.equals("byte")) {
            return "B";
        }
        return null;
    }

    private static String getJava(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("Z")) {
            return "boolean";
        }
        if (str.equals("I")) {
            return "int";
        }
        if (str.equals("J")) {
            return "long";
        }
        if (str.equals("D")) {
            return "double";
        }
        if (str.equals(ExifInterface.GPS_MEASUREMENT_INTERRUPTED)) {
            return PsiKeyword.VOID;
        }
        if (str.equals("F")) {
            return "float";
        }
        if (str.equals("C")) {
            return PsiKeyword.CHAR;
        }
        if (str.equals(ExifInterface.LATITUDE_SOUTH)) {
            return "short";
        }
        if (str.equals("B")) {
            return "byte";
        }
        return null;
    }

    public static String javaToDexName(String str) {
        if (str.charAt(0) == '[') {
            return str.replace('.', '/');
        }
        String primitive = getPrimitive(str);
        return primitive != null ? primitive : "L" + str.replace('.', '/') + ';';
    }

    public static String dexToJavaName(String str) {
        if (str.charAt(0) == '[') {
            return str.replace('/', '.');
        }
        String java2 = getJava(str);
        return java2 != null ? java2 : str.replace('/', '.').substring(1, str.length() - 1);
    }
}
