package org.jetbrains.kotlin.org.jline.reader.impl;

import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: loaded from: classes4.dex */
public class ReaderUtils {
    public static boolean isSet(LineReader lineReader, LineReader.Option option) {
        return lineReader != null && lineReader.isSet(option);
    }

    public static String getString(LineReader lineReader, String str, String str2) {
        Object variable = lineReader != null ? lineReader.getVariable(str) : null;
        return variable != null ? variable.toString() : str2;
    }

    public static boolean getBoolean(LineReader lineReader, String str, boolean z) {
        Object variable = lineReader != null ? lineReader.getVariable(str) : null;
        if (variable instanceof Boolean) {
            return ((Boolean) variable).booleanValue();
        }
        if (variable == null) {
            return z;
        }
        String string = variable.toString();
        return string.isEmpty() || string.equalsIgnoreCase("on") || string.equalsIgnoreCase(SchemaSymbols.ATTVAL_TRUE_1) || string.equalsIgnoreCase("true");
    }

    public static int getInt(LineReader lineReader, String str, int i) {
        Object variable = lineReader != null ? lineReader.getVariable(str) : null;
        if (variable instanceof Number) {
            return ((Number) variable).intValue();
        }
        if (variable == null) {
            return i;
        }
        try {
            return Integer.parseInt(variable.toString());
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    public static long getLong(LineReader lineReader, String str, long j) {
        Object variable = lineReader != null ? lineReader.getVariable(str) : null;
        if (variable instanceof Number) {
            return ((Number) variable).longValue();
        }
        if (variable == null) {
            return j;
        }
        try {
            return Long.parseLong(variable.toString());
        } catch (NumberFormatException unused) {
            return 0L;
        }
    }
}
