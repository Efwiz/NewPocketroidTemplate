package org.jetbrains.kotlin.com.google.gwt.dev.js.rhino;

import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: loaded from: classes3.dex */
final class BinaryDigitReader {
    int digit;
    int digitPos;
    String digits;
    int end;
    int lgBase = 0;
    int start;

    BinaryDigitReader(int i, String str, int i2, int i3) {
        while (i != 1) {
            this.lgBase++;
            i >>= 1;
        }
        this.digitPos = 0;
        this.digits = str;
        this.start = i2;
        this.end = i3;
    }

    int getNextBinaryDigit() {
        if (this.digitPos == 0) {
            int i = this.start;
            if (i == this.end) {
                return -1;
            }
            String str = this.digits;
            this.start = i + 1;
            char cCharAt = str.charAt(i);
            if ('0' <= cCharAt && cCharAt <= '9') {
                this.digit = cCharAt - ExternalAnnotationProvider.NULLABLE;
            } else if ('a' <= cCharAt && cCharAt <= 'z') {
                this.digit = cCharAt - 'W';
            } else {
                this.digit = cCharAt - '7';
            }
            this.digitPos = this.lgBase;
        }
        int i2 = this.digit;
        int i3 = this.digitPos - 1;
        this.digitPos = i3;
        return (i2 >> i3) & 1;
    }
}
