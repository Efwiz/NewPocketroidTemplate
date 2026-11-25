package org.jf.dexlib2.rewriter;

/* JADX INFO: loaded from: classes6.dex */
public class TypeRewriter implements Rewriter<String> {
    protected String rewriteUnwrappedType(String str) {
        return str;
    }

    @Override // org.jf.dexlib2.rewriter.Rewriter
    public String rewrite(String str) {
        if (str.length() > 0 && str.charAt(0) == '[') {
            int i = 0;
            while (str.charAt(i) == '[') {
                i++;
            }
            String strSubstring = str.substring(i);
            String strRewriteUnwrappedType = rewriteUnwrappedType(strSubstring);
            return strSubstring != strRewriteUnwrappedType ? new StringBuilder(strRewriteUnwrappedType.length() + i).append((CharSequence) str, 0, i).append(strRewriteUnwrappedType).toString() : str;
        }
        return rewriteUnwrappedType(str);
    }
}
