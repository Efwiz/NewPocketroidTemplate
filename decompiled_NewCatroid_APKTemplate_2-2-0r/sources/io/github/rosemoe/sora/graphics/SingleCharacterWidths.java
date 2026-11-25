package io.github.rosemoe.sora.graphics;

import android.util.SparseArray;
import io.github.rosemoe.sora.text.FunctionCharacters;
import java.util.Arrays;

/* JADX INFO: loaded from: classes11.dex */
public class SingleCharacterWidths {
    private boolean handleFunctionCharacters;
    private final int tabWidth;
    private final float[] cache = new float[65536];
    public final char[] buffer = new char[10];
    public final float[] widths = new float[10];
    public final SparseArray<Float> codePointWidths = new SparseArray<>();

    public static boolean isEmoji(char c) {
        return c == 55356 || c == 55357 || c == 55358;
    }

    public SingleCharacterWidths(int i) {
        this.tabWidth = i;
    }

    public void setHandleFunctionCharacters(boolean z) {
        this.handleFunctionCharacters = z;
    }

    public boolean isHandleFunctionCharacters() {
        return this.handleFunctionCharacters;
    }

    public void clearCache() {
        Arrays.fill(this.cache, 0.0f);
        this.codePointWidths.clear();
    }

    public float measureChar(char c, Paint paint) {
        int i;
        if (c == '\t') {
            i = this.tabWidth;
            c = ' ';
        } else {
            i = 1;
        }
        float fMeasureText = this.cache[c];
        if (fMeasureText == 0.0f) {
            char[] cArr = this.buffer;
            cArr[0] = c;
            fMeasureText = paint.measureText(cArr, 0, 1);
            this.cache[c] = fMeasureText;
        }
        return fMeasureText * i;
    }

    public float measureCodePoint(int i, Paint paint) {
        if (i <= 65535) {
            return measureChar((char) i, paint);
        }
        Float fValueOf = this.codePointWidths.get(i);
        if (fValueOf == null) {
            fValueOf = Float.valueOf(paint.measureText(this.buffer, 0, Character.toChars(i, this.buffer, 0)));
            this.codePointWidths.put(i, fValueOf);
        }
        return fValueOf.floatValue();
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0038  */
    /* JADX WARN: Code duplicated, block: B:19:0x0041  */
    public float measureText(char[] cArr, int i, int i2, Paint paint) {
        int iMin;
        float fMeasureChar = 0.0f;
        while (i < i2) {
            char c = cArr[i];
            if (isEmoji(c)) {
                if (i + 4 <= i2) {
                    paint.getTextWidths(cArr, i, 4, this.widths);
                    float[] fArr = this.widths;
                    float f = fArr[0];
                    if (f > 0.0f && fArr[1] == 0.0f && fArr[2] == 0.0f && fArr[3] == 0.0f) {
                        i += 3;
                        fMeasureChar += f;
                    } else {
                        iMin = Math.min(i2, i + 2) - i;
                        if (iMin >= 0) {
                            System.arraycopy(cArr, i, this.buffer, 0, iMin);
                        }
                        fMeasureChar += paint.measureText(this.buffer, 0, iMin);
                        i += iMin - 1;
                    }
                } else {
                    iMin = Math.min(i2, i + 2) - i;
                    if (iMin >= 0) {
                        System.arraycopy(cArr, i, this.buffer, 0, iMin);
                    }
                    fMeasureChar += paint.measureText(this.buffer, 0, iMin);
                    i += iMin - 1;
                }
            } else if (isHandleFunctionCharacters() && FunctionCharacters.isEditorFunctionChar(c)) {
                String nameForFunctionCharacter = FunctionCharacters.getNameForFunctionCharacter(c);
                for (int i3 = 0; i3 < nameForFunctionCharacter.length(); i3++) {
                    fMeasureChar += measureChar(nameForFunctionCharacter.charAt(i3), paint);
                }
            } else {
                fMeasureChar += measureChar(c, paint);
            }
            i++;
        }
        return fMeasureChar;
    }

    public float measureText(CharSequence charSequence, Paint paint) {
        return measureText(charSequence, 0, charSequence.length(), paint);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0039  */
    /* JADX WARN: Code duplicated, block: B:19:0x0043 A[LOOP:1: B:18:0x0041->B:19:0x0043, LOOP_END] */
    public float measureText(CharSequence charSequence, int i, int i2, Paint paint) {
        int iMin;
        int i3;
        float fMeasureChar = 0.0f;
        while (i < i2) {
            char cCharAt = charSequence.charAt(i);
            if (isEmoji(cCharAt)) {
                int i4 = i + 4;
                if (i4 <= i2) {
                    paint.getTextWidths(charSequence, i, i4, this.widths);
                    float[] fArr = this.widths;
                    float f = fArr[0];
                    if (f > 0.0f && fArr[1] == 0.0f && fArr[2] == 0.0f && fArr[3] == 0.0f) {
                        i += 3;
                        fMeasureChar += f;
                    } else {
                        iMin = Math.min(i2, i + 2) - i;
                        for (i3 = 0; i3 < iMin; i3++) {
                            this.buffer[i3] = charSequence.charAt(i + i3);
                        }
                        fMeasureChar += paint.measureText(this.buffer, 0, iMin);
                        i += iMin - 1;
                    }
                } else {
                    iMin = Math.min(i2, i + 2) - i;
                    while (i3 < iMin) {
                        this.buffer[i3] = charSequence.charAt(i + i3);
                    }
                    fMeasureChar += paint.measureText(this.buffer, 0, iMin);
                    i += iMin - 1;
                }
            } else if (isHandleFunctionCharacters() && FunctionCharacters.isEditorFunctionChar(cCharAt)) {
                String nameForFunctionCharacter = FunctionCharacters.getNameForFunctionCharacter(cCharAt);
                for (int i5 = 0; i5 < nameForFunctionCharacter.length(); i5++) {
                    fMeasureChar += measureChar(nameForFunctionCharacter.charAt(i5), paint);
                }
            } else {
                fMeasureChar += measureChar(cCharAt, paint);
            }
            i++;
        }
        return fMeasureChar;
    }
}
