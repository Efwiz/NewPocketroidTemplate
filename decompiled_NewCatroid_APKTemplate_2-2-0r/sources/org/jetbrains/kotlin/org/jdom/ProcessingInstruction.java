package org.jetbrains.kotlin.org.jdom;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.kotlin.org.jdom.output.XMLOutputter2;

/* JADX INFO: loaded from: classes4.dex */
public class ProcessingInstruction extends Content {
    private static final long serialVersionUID = 200;
    protected transient Map<String, String> mapData;
    protected String rawData;
    protected String target;

    protected ProcessingInstruction() {
        super(Content.CType.ProcessingInstruction);
        this.mapData = null;
    }

    public String getTarget() {
        return this.target;
    }

    public String getData() {
        return this.rawData;
    }

    private Map<String, String> parseData(String str) {
        String strSubstring;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String strTrim = str.trim();
        while (true) {
            String strTrim2 = "";
            if (strTrim.trim().equals("")) {
                return linkedHashMap;
            }
            char cCharAt = strTrim.charAt(0);
            int i = 0;
            int i2 = 1;
            while (true) {
                if (i2 >= strTrim.length()) {
                    strSubstring = "";
                    break;
                }
                char cCharAt2 = strTrim.charAt(i2);
                if (cCharAt2 == '=') {
                    strTrim2 = strTrim.substring(i, i2).trim();
                    int[] iArrExtractQuotedString = extractQuotedString(strTrim.substring(i2 + 1));
                    if (iArrExtractQuotedString == null) {
                        return Collections.emptyMap();
                    }
                    strSubstring = strTrim.substring(iArrExtractQuotedString[0] + i2 + 1, iArrExtractQuotedString[1] + i2 + 1);
                    i2 += iArrExtractQuotedString[1] + 1;
                    break;
                }
                if (Character.isWhitespace(cCharAt) && !Character.isWhitespace(cCharAt2)) {
                    i = i2;
                }
                i2++;
                cCharAt = cCharAt2;
            }
            strTrim = strTrim.substring(i2);
            if (strTrim2.length() > 0) {
                linkedHashMap.put(strTrim2, strSubstring);
            }
        }
    }

    private static int[] extractQuotedString(String str) {
        boolean z = false;
        int i = 0;
        char c = '\"';
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\"' || cCharAt == '\'') {
                if (!z) {
                    c = cCharAt;
                    i = i2 + 1;
                    z = true;
                } else if (c == cCharAt) {
                    return new int[]{i, i2};
                }
            }
        }
        return null;
    }

    public String toString() {
        return "[ProcessingInstruction: " + new XMLOutputter2().outputString(this) + "]";
    }

    @Override // org.jetbrains.kotlin.org.jdom.Content, org.jetbrains.kotlin.org.jdom.CloneBase
    public ProcessingInstruction clone() {
        ProcessingInstruction processingInstruction = (ProcessingInstruction) super.clone();
        processingInstruction.mapData = parseData(this.rawData);
        return processingInstruction;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.org.jdom.Content
    public ProcessingInstruction setParent(Parent parent) {
        return (ProcessingInstruction) super.setParent(parent);
    }
}
