package io.github.rosemoe.sora.graphics;

import android.graphics.Typeface;
import io.github.rosemoe.sora.text.ContentLine;
import io.github.rosemoe.sora.text.FunctionCharacters;

/* JADX INFO: loaded from: classes11.dex */
public class Paint extends android.graphics.Paint {
    private boolean renderFunctionCharacters;
    private float spaceWidth = measureText(" ");
    private float tabWidth = measureText("\t");
    private SingleCharacterWidths widths;

    public Paint(boolean z) {
        this.renderFunctionCharacters = z;
    }

    public void setRenderFunctionCharacters(boolean z) {
        this.renderFunctionCharacters = z;
        SingleCharacterWidths singleCharacterWidths = this.widths;
        if (singleCharacterWidths != null) {
            singleCharacterWidths.clearCache();
        }
    }

    public boolean isRenderFunctionCharacters() {
        return this.renderFunctionCharacters;
    }

    private void ensureCacheObject() {
        if (this.widths == null) {
            this.widths = new SingleCharacterWidths(1);
        }
    }

    public void onAttributeUpdate() {
        this.spaceWidth = measureText(" ");
        this.tabWidth = measureText("\t");
        SingleCharacterWidths singleCharacterWidths = this.widths;
        if (singleCharacterWidths != null) {
            singleCharacterWidths.clearCache();
        }
    }

    public float getSpaceWidth() {
        return this.spaceWidth;
    }

    public void setTypefaceWrapped(Typeface typeface) {
        super.setTypeface(typeface);
        onAttributeUpdate();
    }

    public void setTextSizeWrapped(float f) {
        super.setTextSize(f);
        onAttributeUpdate();
    }

    public void setFontFeatureSettingsWrapped(String str) {
        super.setFontFeatureSettings(str);
        onAttributeUpdate();
    }

    @Override // android.graphics.Paint
    public void setLetterSpacing(float f) {
        super.setLetterSpacing(f);
        onAttributeUpdate();
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0039  */
    /* JADX WARN: Code duplicated, block: B:23:0x0054  */
    /* JADX WARN: Code duplicated, block: B:25:0x0058  */
    /* JADX WARN: Code duplicated, block: B:26:0x005b  */
    /* JADX WARN: Code duplicated, block: B:28:0x0063  */
    /* JADX WARN: Code duplicated, block: B:48:0x0067 A[SYNTHETIC] */
    public float myGetTextRunAdvances(char[] cArr, int i, int i2, int i3, int i4, boolean z, float[] fArr, int i5, boolean z2) {
        float fMeasureText;
        float fMeasureChar;
        int i6;
        int i7 = 0;
        if (z2) {
            ensureCacheObject();
            float f = 0.0f;
            while (i7 < i2) {
                int i8 = i7 + i;
                char c = cArr[i8];
                if (Character.isHighSurrogate(c) && (i6 = i7 + 1) < i2) {
                    int i9 = i8 + 1;
                    if (Character.isLowSurrogate(cArr[i9])) {
                        fMeasureChar = this.widths.measureCodePoint(Character.toCodePoint(c, cArr[i9]), this);
                        if (fArr != null) {
                            int i10 = i7 + i5;
                            fArr[i10] = fMeasureChar;
                            fArr[i10 + 1] = 0.0f;
                        }
                        i7 = i6;
                    } else if (this.renderFunctionCharacters) {
                        if (c == '\t') {
                            fMeasureChar = this.tabWidth;
                        } else {
                            fMeasureChar = this.widths.measureChar(c, this);
                        }
                        if (fArr != null) {
                            fArr[i5 + i7] = fMeasureChar;
                        }
                    } else {
                        if (c == '\t') {
                            fMeasureChar = this.tabWidth;
                        } else {
                            fMeasureChar = this.widths.measureChar(c, this);
                        }
                        if (fArr != null) {
                            fArr[i5 + i7] = fMeasureChar;
                        }
                    }
                } else if (this.renderFunctionCharacters || !FunctionCharacters.isEditorFunctionChar(c)) {
                    if (c == '\t') {
                        fMeasureChar = this.tabWidth;
                    } else {
                        fMeasureChar = this.widths.measureChar(c, this);
                    }
                    if (fArr != null) {
                        fArr[i5 + i7] = fMeasureChar;
                    }
                } else {
                    fMeasureChar = this.widths.measureText(FunctionCharacters.getNameForFunctionCharacter(c), this);
                    if (fArr != null) {
                        fArr[i5 + i7] = fMeasureChar;
                    }
                }
                f += fMeasureChar;
                i7++;
            }
            return f;
        }
        float textRunAdvances = getTextRunAdvances(cArr, i, i2, i3, i4, z, fArr, i5);
        if (this.renderFunctionCharacters) {
            while (i7 < i2) {
                char c2 = cArr[i + i7];
                if (FunctionCharacters.isEditorFunctionChar(c2)) {
                    float fMeasureText2 = measureText(FunctionCharacters.getNameForFunctionCharacter(c2));
                    if (fArr != null) {
                        int i11 = i5 + i7;
                        fMeasureText = textRunAdvances - fArr[i11];
                        fArr[i11] = fMeasureText2;
                    } else {
                        fMeasureText = textRunAdvances - measureText(Character.toString(c2));
                    }
                    textRunAdvances = fMeasureText + fMeasureText2;
                }
                i7++;
            }
        }
        return textRunAdvances;
    }

    public float measureTextRunAdvance(char[] cArr, int i, int i2, int i3, int i4, boolean z) {
        return myGetTextRunAdvances(cArr, i, i2 - i, i3, i4 - i3, false, null, 0, z);
    }

    public int findOffsetByRunAdvance(ContentLine contentLine, int i, int i2, float f, boolean z, boolean z2) {
        float fMeasureChar;
        int i3;
        float fMeasureTextRunAdvance = 0.0f;
        if (contentLine.widthCache != null && z) {
            float[] fArr = contentLine.widthCache;
            int i4 = i;
            while (i4 < i2 && fMeasureTextRunAdvance < f) {
                int i5 = i4 + 1;
                fMeasureTextRunAdvance += fArr[i5] - fArr[i4];
                i4 = i5;
            }
            if (fMeasureTextRunAdvance > f) {
                i4--;
            }
            return Math.max(i4, i);
        }
        if (z2) {
            ensureCacheObject();
            int i6 = i;
            while (i6 < i2) {
                char c = contentLine.value[i6];
                if (Character.isHighSurrogate(c) && (i3 = i6 + 1) < i2 && Character.isLowSurrogate(contentLine.value[i3])) {
                    fMeasureChar = this.widths.measureCodePoint(Character.toCodePoint(c, contentLine.value[i3]), this);
                } else {
                    if (this.renderFunctionCharacters && FunctionCharacters.isEditorFunctionChar(c)) {
                        fMeasureChar = this.widths.measureText(FunctionCharacters.getNameForFunctionCharacter(c), this);
                    } else {
                        fMeasureChar = c == '\t' ? this.tabWidth : this.widths.measureChar(c, this);
                    }
                    i3 = i6;
                }
                fMeasureTextRunAdvance += fMeasureChar;
                if (fMeasureTextRunAdvance > f) {
                    return Math.max(i, i6 - 1);
                }
                i6 = i3 + 1;
            }
            return i2;
        }
        if (!this.renderFunctionCharacters) {
            return breakTextImpl(contentLine, i, i2, f);
        }
        int i7 = i;
        while (i < i2) {
            char c2 = contentLine.value[i];
            if (FunctionCharacters.isEditorFunctionChar(c2)) {
                int iBreakTextImpl = i7 == i ? i : breakTextImpl(contentLine, i7, i, f - fMeasureTextRunAdvance);
                if (iBreakTextImpl < i) {
                    return iBreakTextImpl;
                }
                fMeasureTextRunAdvance = fMeasureTextRunAdvance + measureTextRunAdvance(contentLine.value, i7, i, i7, i, false) + measureText(FunctionCharacters.getNameForFunctionCharacter(c2));
                if (fMeasureTextRunAdvance >= f) {
                    return i;
                }
                i7 = i + 1;
            }
            i++;
        }
        return i7 < i2 ? breakTextImpl(contentLine, i7, i2, f - fMeasureTextRunAdvance) : i2;
    }

    private int breakTextImpl(ContentLine contentLine, int i, int i2, float f) {
        return getOffsetForAdvance(contentLine.value, i, i2, i, i2, false, f);
    }
}
