package antlr;

import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes.dex */
class CSharpCharFormatter implements CharFormatter {
    CSharpCharFormatter() {
    }

    @Override // antlr.CharFormatter
    public String escapeChar(int i, boolean z) {
        if (i == 9) {
            return "\\t";
        }
        if (i == 10) {
            return "\\n";
        }
        if (i == 13) {
            return "\\r";
        }
        if (i == 34) {
            return z ? "\"" : "\\\"";
        }
        if (i == 39) {
            return z ? "\\'" : FormatHelper.QUOTE;
        }
        if (i == 92) {
            return "\\\\";
        }
        if (i >= 32 && i <= 126) {
            return String.valueOf((char) i);
        }
        if (i >= 0 && i <= 15) {
            return new StringBuffer("\\u000").append(Integer.toString(i, 16)).toString();
        }
        if (16 <= i && i <= 255) {
            return new StringBuffer("\\u00").append(Integer.toString(i, 16)).toString();
        }
        if (256 <= i && i <= 4095) {
            return new StringBuffer("\\u0").append(Integer.toString(i, 16)).toString();
        }
        return new StringBuffer("\\u").append(Integer.toString(i, 16)).toString();
    }

    @Override // antlr.CharFormatter
    public String escapeString(String str) {
        String str2 = new String();
        for (int i = 0; i < str.length(); i++) {
            str2 = new StringBuffer().append(str2).append(escapeChar(str.charAt(i), false)).toString();
        }
        return str2;
    }

    @Override // antlr.CharFormatter
    public String literalChar(int i) {
        return new StringBuffer(FormatHelper.QUOTE).append(escapeChar(i, true)).append(FormatHelper.QUOTE).toString();
    }

    @Override // antlr.CharFormatter
    public String literalString(String str) {
        return new StringBuffer("@\"\"\"").append(escapeString(str)).append("\"\"\"").toString();
    }
}
