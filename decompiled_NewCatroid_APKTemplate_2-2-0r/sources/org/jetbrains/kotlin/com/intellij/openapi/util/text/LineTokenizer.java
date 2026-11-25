package org.jetbrains.kotlin.com.intellij.openapi.util.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;

/* JADX INFO: loaded from: classes3.dex */
public final class LineTokenizer {
    private boolean atEnd;
    private int myLength;
    private int myLineSeparatorLength;
    private int myOffset;
    private final CharSequence myText;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                i2 = 3;
                break;
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
                objArr[0] = "chars";
                break;
            case 7:
                objArr[0] = "tokenizer";
                break;
            case 9:
            case 10:
                objArr[0] = "text";
                break;
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/LineTokenizer";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[1] = "tokenizeIntoList";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/LineTokenizer";
                break;
            default:
                objArr[1] = "tokenize";
                break;
        }
        switch (i) {
            case 3:
                objArr[2] = "calcLineCount";
                break;
            case 4:
            case 5:
            case 6:
            case 8:
                objArr[2] = "tokenize";
                break;
            case 7:
                objArr[2] = "stringEndsWithSeparator";
                break;
            case 9:
            case 10:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                throw new IllegalArgumentException(str2);
            default:
                throw new IllegalStateException(str2);
        }
    }

    public static String[] tokenize(CharSequence charSequence, boolean z, boolean z2) {
        List<String> list = tokenizeIntoList(charSequence, z, z2);
        String[] stringArray = list.isEmpty() ? ArrayUtilRt.EMPTY_STRING_ARRAY : ArrayUtilRt.toStringArray(list);
        if (stringArray == null) {
            $$$reportNull$$$0(0);
        }
        return stringArray;
    }

    public static List<String> tokenizeIntoList(CharSequence charSequence, boolean z, boolean z2) {
        String string;
        if (charSequence == null || charSequence.length() == 0) {
            List<String> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(1);
            }
            return listEmptyList;
        }
        LineTokenizer lineTokenizer = new LineTokenizer(charSequence);
        ArrayList arrayList = new ArrayList();
        while (!lineTokenizer.atEnd()) {
            int offset = lineTokenizer.getOffset();
            if (z) {
                string = charSequence.subSequence(offset, lineTokenizer.getLength() + offset + lineTokenizer.getLineSeparatorLength()).toString();
            } else {
                string = charSequence.subSequence(offset, lineTokenizer.getLength() + offset).toString();
            }
            arrayList.add(string);
            lineTokenizer.advance();
        }
        if (!z2 && stringEndsWithSeparator(lineTokenizer)) {
            arrayList.add("");
        }
        return arrayList;
    }

    private static boolean stringEndsWithSeparator(LineTokenizer lineTokenizer) {
        if (lineTokenizer == null) {
            $$$reportNull$$$0(7);
        }
        return lineTokenizer.getLineSeparatorLength() > 0;
    }

    public LineTokenizer(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(9);
        }
        this.myText = charSequence;
        this.myOffset = 0;
        advance();
    }

    public boolean atEnd() {
        return this.atEnd;
    }

    public int getOffset() {
        return this.myOffset;
    }

    public int getLength() {
        return this.myLength;
    }

    public int getLineSeparatorLength() {
        return this.myLineSeparatorLength;
    }

    public void advance() {
        char cCharAt;
        int i = this.myOffset + this.myLength + this.myLineSeparatorLength;
        int length = this.myText.length();
        if (i >= length) {
            this.atEnd = true;
            return;
        }
        while (i < length && (cCharAt = this.myText.charAt(i)) != '\r' && cCharAt != '\n') {
            i++;
        }
        int i2 = this.myOffset + this.myLength + this.myLineSeparatorLength;
        this.myOffset = i2;
        this.myLength = i - i2;
        this.myLineSeparatorLength = 0;
        if (i == length) {
            return;
        }
        char cCharAt2 = this.myText.charAt(i);
        if (cCharAt2 == '\r' || cCharAt2 == '\n') {
            this.myLineSeparatorLength = 1;
        }
        int i3 = i + 1;
        if (i3 == length) {
            return;
        }
        char cCharAt3 = this.myText.charAt(i3);
        if (cCharAt2 == '\r' && cCharAt3 == '\n') {
            this.myLineSeparatorLength = 2;
        }
    }
}
