package antlr;

import org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil;

/* JADX INFO: loaded from: classes.dex */
class CppCharFormatter implements CharFormatter {
    CppCharFormatter() {
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
            return "\\\"";
        }
        if (i == 39) {
            return "\\'";
        }
        if (i == 92) {
            return "\\\\";
        }
        if (i >= 32 && i <= 126) {
            return String.valueOf((char) i);
        }
        if (i > 255) {
            String string = Integer.toString(i, 16);
            while (string.length() < 4) {
                string = new StringBuffer("0").append(string).toString();
            }
            return new StringBuffer("\\u").append(string).toString();
        }
        return new StringBuffer("\\").append(Integer.toString(i, 8)).toString();
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
        String string = new StringBuffer(PsiLiteralUtil.HEX_PREFIX).append(Integer.toString(i, 16)).toString();
        return (i < 0 || i > 126) ? string : new StringBuffer().append(string).append(" /* '").append(escapeChar(i, true)).append("' */ ").toString();
    }

    @Override // antlr.CharFormatter
    public String literalString(String str) {
        return new StringBuffer("\"").append(escapeString(str)).append("\"").toString();
    }
}
