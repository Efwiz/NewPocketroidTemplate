package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/GlyphLayout.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class GlyphLayout implements Pool.Poolable {
    private static final float epsilon = 1.0E-4f;
    public int glyphCount;
    public float height;
    public float width;
    private static final Pool<GlyphRun> glyphRunPool = Pools.get(GlyphRun.class);
    private static final IntArray colorStack = new IntArray(4);
    public final Array<GlyphRun> runs = new Array<>(1);
    public final IntArray colors = new IntArray(2);

    public GlyphLayout() {
    }

    public GlyphLayout(BitmapFont bitmapFont, CharSequence charSequence) {
        setText(bitmapFont, charSequence);
    }

    public GlyphLayout(BitmapFont bitmapFont, CharSequence charSequence, Color color, float f, int i, boolean z) {
        setText(bitmapFont, charSequence, color, f, i, z);
    }

    public GlyphLayout(BitmapFont bitmapFont, CharSequence charSequence, int i, int i2, Color color, float f, int i3, boolean z, String str) {
        setText(bitmapFont, charSequence, i, i2, color, f, i3, z, str);
    }

    public void setText(BitmapFont bitmapFont, CharSequence charSequence) {
        setText(bitmapFont, charSequence, 0, charSequence.length(), bitmapFont.getColor(), 0.0f, 8, false, null);
    }

    public void setText(BitmapFont bitmapFont, CharSequence charSequence, Color color, float f, int i, boolean z) {
        setText(bitmapFont, charSequence, 0, charSequence.length(), color, f, i, z, null);
    }

    /* JADX WARN: Code duplicated, block: B:101:0x01ec  */
    /* JADX WARN: Code duplicated, block: B:103:0x01f0  */
    /* JADX WARN: Code duplicated, block: B:104:0x01f7  */
    /* JADX WARN: Code duplicated, block: B:119:0x01bf A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:65:0x0133  */
    /* JADX WARN: Code duplicated, block: B:97:0x01c3  */
    /* JADX WARN: Code duplicated, block: B:99:0x01e4 A[EDGE_INSN: B:99:0x01e4->B:100:0x01ea BREAK  A[LOOP:1: B:73:0x0154->B:98:0x01e0], PHI: r2 r22
      0x01e4: PHI (r2v6 com.badlogic.gdx.graphics.g2d.GlyphLayout$GlyphRun) = 
      (r2v5 com.badlogic.gdx.graphics.g2d.GlyphLayout$GlyphRun)
      (r2v5 com.badlogic.gdx.graphics.g2d.GlyphLayout$GlyphRun)
      (r2v7 com.badlogic.gdx.graphics.g2d.GlyphLayout$GlyphRun)
      (r2v5 com.badlogic.gdx.graphics.g2d.GlyphLayout$GlyphRun)
     binds: [B:66:0x0138, B:68:0x013e, B:118:0x01e4, B:71:0x0144] A[DONT_GENERATE, DONT_INLINE]
      0x01e4: PHI (r22v1 float) = (r22v0 float), (r22v0 float), (r22v2 float), (r22v0 float) binds: [B:66:0x0138, B:68:0x013e, B:118:0x01e4, B:71:0x0144] A[DONT_GENERATE, DONT_INLINE]] */
    public void setText(BitmapFont bitmapFont, CharSequence charSequence, int i, int i2, Color color, float f, int i3, boolean z, String str) {
        int i4;
        int iPeek;
        int i5;
        boolean z2;
        GlyphRun glyphRun;
        float f2;
        GlyphRun glyphRunWrap;
        int i6;
        float fFirst;
        CharSequence charSequence2 = charSequence;
        int i7 = i2;
        reset();
        BitmapFont.BitmapFontData bitmapFontData = bitmapFont.data;
        int i8 = i;
        if (i8 == i7) {
            this.height = bitmapFontData.capHeight;
            return;
        }
        float fMax = z ? Math.max(f, bitmapFontData.spaceXadvance * 3.0f) : f;
        boolean z3 = z || str != null;
        int intBits = color.toIntBits();
        this.colors.add(0, intBits);
        boolean z4 = bitmapFontData.markupEnabled;
        if (z4) {
            colorStack.add(intBits);
        }
        float f3 = bitmapFontData.down;
        int i9 = i8;
        int i10 = intBits;
        boolean z5 = false;
        BitmapFont.Glyph glyphPeek = null;
        GlyphRun glyphRun2 = null;
        float f4 = 0.0f;
        loop0: while (true) {
            if (i8 != i7) {
                int i11 = i8 + 1;
                char cCharAt = charSequence2.charAt(i8);
                int i12 = intBits;
                if (cCharAt != '\n') {
                    if (cCharAt == '[' && z4) {
                        int colorMarkup = parseColorMarkup(charSequence2, i11, i7);
                        if (colorMarkup >= 0) {
                            i11 += colorMarkup + 1;
                            if (i11 == i7) {
                                iPeek = i12;
                                i4 = i11;
                                z5 = true;
                                z2 = false;
                            } else {
                                iPeek = colorStack.peek();
                                z2 = false;
                            }
                            i5 = i8;
                        } else {
                            if (colorMarkup == -2) {
                                i8 += 2;
                            }
                            intBits = i12;
                        }
                    }
                    i8 = i11;
                    intBits = i12;
                } else {
                    iPeek = i12;
                    z2 = true;
                }
                i4 = i11;
                i5 = i8;
            } else {
                if (i9 == i7) {
                    break;
                }
                i4 = i8;
                iPeek = intBits;
                i5 = i7;
                z5 = true;
                z2 = false;
            }
            Pool<GlyphRun> pool = glyphRunPool;
            GlyphRun glyphRunObtain = pool.obtain();
            int i13 = iPeek;
            glyphRunObtain.x = 0.0f;
            glyphRunObtain.y = f4;
            GlyphRun glyphRun3 = glyphRunObtain;
            int i14 = i10;
            int i15 = i9;
            float f5 = f4;
            float f6 = f3;
            bitmapFontData.getGlyphs(glyphRun3, charSequence, i9, i5, glyphPeek);
            this.glyphCount += glyphRun3.glyphs.size;
            int i16 = 2;
            if (i13 != i14) {
                IntArray intArray = this.colors;
                int i17 = intArray.get(intArray.size - 2);
                int i18 = this.glyphCount;
                if (i17 == i18) {
                    IntArray intArray2 = this.colors;
                    intArray2.set(intArray2.size - 1, i13);
                } else {
                    this.colors.add(i18);
                    this.colors.add(i13);
                }
                i10 = i13;
            } else {
                i10 = i14;
            }
            if (glyphRun3.glyphs.size == 0) {
                pool.free(glyphRun3);
                glyphRun = glyphRun2;
                if (glyphRun == null) {
                    glyphRun2 = glyphRun;
                }
                if (z2) {
                    if (i5 == i15) {
                        f5 += f6 * bitmapFontData.blankLineScale;
                    } else {
                        f5 += f6;
                    }
                    glyphPeek = null;
                    glyphRun2 = null;
                }
                charSequence2 = charSequence;
                i7 = i2;
                intBits = i13;
                i8 = i4;
                i9 = i8;
                z5 = z5;
                f4 = f5;
                f3 = f6;
            } else {
                glyphRun = glyphRun2;
                if (glyphRun == null) {
                    this.runs.add(glyphRun3);
                } else {
                    glyphRun.appendRun(glyphRun3);
                    pool.free(glyphRun3);
                }
                if (!z2 || z5) {
                    setLastGlyphXAdvance(bitmapFontData, glyphRun3);
                    glyphPeek = null;
                } else {
                    glyphPeek = glyphRun3.glyphs.peek();
                }
                if (z3 && glyphRun3.glyphs.size != 0 && (z2 || z5)) {
                    float fFirst2 = glyphRun3.xAdvances.first() + glyphRun3.xAdvances.get(1);
                    while (true) {
                        if (i16 >= glyphRun3.xAdvances.size) {
                            glyphRun2 = glyphRun3;
                            break;
                        }
                        int i19 = i16 - 1;
                        if ((getGlyphWidth(glyphRun3.glyphs.get(i19), bitmapFontData) + fFirst2) - 1.0E-4f <= fMax) {
                            fFirst = fFirst2 + glyphRun3.xAdvances.items[i16];
                            i6 = 1;
                        } else {
                            if (str != null) {
                                truncate(bitmapFontData, glyphRun3, fMax, str);
                                f4 = f5;
                                break loop0;
                            }
                            int wrapIndex = bitmapFontData.getWrapIndex(glyphRun3.glyphs, i16);
                            if (wrapIndex == 0) {
                                f2 = 0.0f;
                                if (glyphRun3.x != 0.0f) {
                                }
                                glyphRunWrap = wrap(bitmapFontData, glyphRun3, i19);
                                if (glyphRunWrap == null) {
                                    glyphRun2 = glyphRunWrap;
                                    break;
                                }
                                this.runs.add(glyphRunWrap);
                                float f7 = f5 + f6;
                                glyphRunWrap.x = f2;
                                glyphRunWrap.y = f7;
                                i6 = 1;
                                fFirst = glyphRunWrap.xAdvances.first() + glyphRunWrap.xAdvances.get(1);
                                f5 = f7;
                                glyphRun3 = glyphRunWrap;
                                i16 = 1;
                            } else {
                                f2 = 0.0f;
                            }
                            if (wrapIndex < glyphRun3.glyphs.size) {
                                i19 = wrapIndex;
                            }
                            glyphRunWrap = wrap(bitmapFontData, glyphRun3, i19);
                            if (glyphRunWrap == null) {
                                glyphRun2 = glyphRunWrap;
                                break;
                            }
                            this.runs.add(glyphRunWrap);
                            float f8 = f5 + f6;
                            glyphRunWrap.x = f2;
                            glyphRunWrap.y = f8;
                            i6 = 1;
                            fFirst = glyphRunWrap.xAdvances.first() + glyphRunWrap.xAdvances.get(1);
                            f5 = f8;
                            glyphRun3 = glyphRunWrap;
                            i16 = 1;
                        }
                        i16 += i6;
                        fFirst2 = fFirst;
                    }
                } else {
                    glyphRun2 = glyphRun3;
                    break;
                }
                if (z2) {
                    if (i5 == i15) {
                        f5 += f6 * bitmapFontData.blankLineScale;
                    } else {
                        f5 += f6;
                    }
                    glyphPeek = null;
                    glyphRun2 = null;
                }
                charSequence2 = charSequence;
                i7 = i2;
                intBits = i13;
                i8 = i4;
                i9 = i8;
                z5 = z5;
                f4 = f5;
                f3 = f6;
            }
            glyphRun3 = glyphRun;
            if (!z2) {
                setLastGlyphXAdvance(bitmapFontData, glyphRun3);
                glyphPeek = null;
            } else {
                setLastGlyphXAdvance(bitmapFontData, glyphRun3);
                glyphPeek = null;
            }
            if (z3) {
                glyphRun2 = glyphRun3;
                break;
            } else {
                glyphRun2 = glyphRun3;
                break;
            }
            if (z2) {
                if (i5 == i15) {
                    f5 += f6 * bitmapFontData.blankLineScale;
                } else {
                    f5 += f6;
                }
                glyphPeek = null;
                glyphRun2 = null;
            }
            charSequence2 = charSequence;
            i7 = i2;
            intBits = i13;
            i8 = i4;
            i9 = i8;
            z5 = z5;
            f4 = f5;
            f3 = f6;
        }
        this.height = bitmapFontData.capHeight + Math.abs(f4);
        calculateWidths(bitmapFontData);
        alignRuns(fMax, i3);
        if (z4) {
            colorStack.clear();
        }
    }

    private void calculateWidths(BitmapFont.BitmapFontData bitmapFontData) {
        GlyphRun[] glyphRunArr = this.runs.items;
        int i = this.runs.size;
        float fMax = 0.0f;
        for (int i2 = 0; i2 < i; i2++) {
            GlyphRun glyphRun = glyphRunArr[i2];
            float[] fArr = glyphRun.xAdvances.items;
            float f = glyphRun.x + fArr[0];
            BitmapFont.Glyph[] glyphArr = glyphRun.glyphs.items;
            int i3 = glyphRun.glyphs.size;
            float fMax2 = 0.0f;
            int i4 = 0;
            while (i4 < i3) {
                fMax2 = Math.max(fMax2, getGlyphWidth(glyphArr[i4], bitmapFontData) + f);
                i4++;
                f += fArr[i4];
            }
            glyphRun.width = Math.max(f, fMax2) - glyphRun.x;
            fMax = Math.max(fMax, glyphRun.x + glyphRun.width);
        }
        this.width = fMax;
    }

    private void alignRuns(float f, int i) {
        if ((i & 8) == 0) {
            boolean z = (i & 1) != 0;
            GlyphRun[] glyphRunArr = this.runs.items;
            int i2 = this.runs.size;
            for (int i3 = 0; i3 < i2; i3++) {
                GlyphRun glyphRun = glyphRunArr[i3];
                float f2 = glyphRun.x;
                float f3 = f - glyphRun.width;
                if (z) {
                    f3 *= 0.5f;
                }
                glyphRun.x = f2 + f3;
            }
        }
    }

    private void truncate(BitmapFont.BitmapFontData bitmapFontData, GlyphRun glyphRun, float f, String str) {
        int i = glyphRun.glyphs.size;
        GlyphRun glyphRunObtain = glyphRunPool.obtain();
        bitmapFontData.getGlyphs(glyphRunObtain, str, 0, str.length(), null);
        float f2 = 0.0f;
        if (glyphRunObtain.xAdvances.size > 0) {
            setLastGlyphXAdvance(bitmapFontData, glyphRunObtain);
            float[] fArr = glyphRunObtain.xAdvances.items;
            int i2 = glyphRunObtain.xAdvances.size;
            for (int i3 = 1; i3 < i2; i3++) {
                f2 += fArr[i3];
            }
        }
        float f3 = f - f2;
        float f4 = glyphRun.x;
        float[] fArr2 = glyphRun.xAdvances.items;
        int i4 = 0;
        while (i4 < glyphRun.xAdvances.size) {
            f4 += fArr2[i4];
            if (f4 > f3) {
                break;
            } else {
                i4++;
            }
        }
        if (i4 > 1) {
            glyphRun.glyphs.truncate(i4 - 1);
            glyphRun.xAdvances.truncate(i4);
            setLastGlyphXAdvance(bitmapFontData, glyphRun);
            if (glyphRunObtain.xAdvances.size > 0) {
                glyphRun.xAdvances.addAll(glyphRunObtain.xAdvances, 1, glyphRunObtain.xAdvances.size - 1);
            }
        } else {
            glyphRun.glyphs.clear();
            glyphRun.xAdvances.clear();
            glyphRun.xAdvances.addAll(glyphRunObtain.xAdvances);
        }
        int i5 = i - glyphRun.glyphs.size;
        if (i5 > 0) {
            this.glyphCount -= i5;
            if (bitmapFontData.markupEnabled) {
                while (this.colors.size > 2) {
                    IntArray intArray = this.colors;
                    if (intArray.get(intArray.size - 2) < this.glyphCount) {
                        break;
                    }
                    this.colors.size -= 2;
                }
            }
        }
        glyphRun.glyphs.addAll(glyphRunObtain.glyphs);
        this.glyphCount += str.length();
        glyphRunPool.free(glyphRunObtain);
    }

    private GlyphRun wrap(BitmapFont.BitmapFontData bitmapFontData, GlyphRun glyphRun, int i) {
        GlyphRun glyphRunObtain;
        Array<BitmapFont.Glyph> array = glyphRun.glyphs;
        int i2 = glyphRun.glyphs.size;
        FloatArray floatArray = glyphRun.xAdvances;
        int i3 = i;
        while (i3 > 0 && bitmapFontData.isWhitespace((char) array.get(i3 - 1).f94id)) {
            i3--;
        }
        while (i < i2 && bitmapFontData.isWhitespace((char) array.get(i).f94id)) {
            i++;
        }
        if (i < i2) {
            glyphRunObtain = glyphRunPool.obtain();
            Array<BitmapFont.Glyph> array2 = glyphRunObtain.glyphs;
            array2.addAll(array, 0, i3);
            array.removeRange(0, i - 1);
            glyphRun.glyphs = array2;
            glyphRunObtain.glyphs = array;
            FloatArray floatArray2 = glyphRunObtain.xAdvances;
            floatArray2.addAll(floatArray, 0, i3 + 1);
            floatArray.removeRange(1, i);
            floatArray.items[0] = getLineOffset(array, bitmapFontData);
            glyphRun.xAdvances = floatArray2;
            glyphRunObtain.xAdvances = floatArray;
            int i4 = glyphRun.glyphs.size;
            int i5 = glyphRunObtain.glyphs.size;
            int i6 = (i2 - i4) - i5;
            this.glyphCount -= i6;
            if (bitmapFontData.markupEnabled && i6 > 0) {
                int i7 = this.glyphCount - i5;
                for (int i8 = this.colors.size - 2; i8 >= 2; i8 -= 2) {
                    int i9 = this.colors.get(i8);
                    if (i9 <= i7) {
                        break;
                    }
                    this.colors.set(i8, i9 - i6);
                }
            }
        } else {
            array.truncate(i3);
            floatArray.truncate(i3 + 1);
            int i10 = i - i3;
            if (i10 > 0) {
                this.glyphCount -= i10;
                if (bitmapFontData.markupEnabled) {
                    IntArray intArray = this.colors;
                    if (intArray.get(intArray.size - 2) > this.glyphCount) {
                        int iPeek = this.colors.peek();
                        while (true) {
                            IntArray intArray2 = this.colors;
                            if (intArray2.get(intArray2.size - 2) <= this.glyphCount) {
                                break;
                            }
                            this.colors.size -= 2;
                        }
                        IntArray intArray3 = this.colors;
                        intArray3.set(intArray3.size - 2, this.glyphCount);
                        IntArray intArray4 = this.colors;
                        intArray4.set(intArray4.size - 1, iPeek);
                    }
                }
            }
            glyphRunObtain = null;
        }
        if (i3 == 0) {
            glyphRunPool.free(glyphRun);
            this.runs.pop();
        } else {
            setLastGlyphXAdvance(bitmapFontData, glyphRun);
        }
        return glyphRunObtain;
    }

    private void setLastGlyphXAdvance(BitmapFont.BitmapFontData bitmapFontData, GlyphRun glyphRun) {
        BitmapFont.Glyph glyphPeek = glyphRun.glyphs.peek();
        if (glyphPeek.fixedWidth) {
            return;
        }
        glyphRun.xAdvances.items[glyphRun.xAdvances.size - 1] = getGlyphWidth(glyphPeek, bitmapFontData);
    }

    private float getGlyphWidth(BitmapFont.Glyph glyph, BitmapFont.BitmapFontData bitmapFontData) {
        int i;
        if (glyph.fixedWidth) {
            i = glyph.xadvance;
        } else {
            i = glyph.xoffset + glyph.width;
        }
        return (i * bitmapFontData.scaleX) - bitmapFontData.padRight;
    }

    private float getLineOffset(Array<BitmapFont.Glyph> array, BitmapFont.BitmapFontData bitmapFontData) {
        BitmapFont.Glyph glyphFirst = array.first();
        return (glyphFirst.fixedWidth ? 0.0f : (-glyphFirst.xoffset) * bitmapFontData.scaleX) - bitmapFontData.padLeft;
    }

    private int parseColorMarkup(CharSequence charSequence, int i, int i2) {
        if (i == i2) {
            return -1;
        }
        char cCharAt = charSequence.charAt(i);
        int i3 = 0;
        if (cCharAt != '#') {
            if (cCharAt == '[') {
                return -2;
            }
            if (cCharAt == ']') {
                IntArray intArray = colorStack;
                if (intArray.size > 1) {
                    intArray.pop();
                }
                return 0;
            }
            for (int i4 = i + 1; i4 < i2; i4++) {
                if (charSequence.charAt(i4) == ']') {
                    Color color = Colors.get(charSequence.subSequence(i, i4).toString());
                    if (color == null) {
                        return -1;
                    }
                    colorStack.add(color.toIntBits());
                    return i4 - i;
                }
            }
            return -1;
        }
        for (int i5 = i + 1; i5 < i2; i5++) {
            char cCharAt2 = charSequence.charAt(i5);
            if (cCharAt2 == ']') {
                if (i5 < i + 2 || i5 > i + 9) {
                    break;
                    break;
                }
                int i6 = i5 - i;
                if (i6 < 8) {
                    i3 = (i3 << ((9 - i6) << 2)) | 255;
                }
                colorStack.add(Integer.reverseBytes(i3));
                return i6;
            }
            int i7 = (i3 << 4) + cCharAt2;
            if (cCharAt2 >= '0' && cCharAt2 <= '9') {
                i3 = i7 - 48;
            } else if (cCharAt2 >= 'A' && cCharAt2 <= 'F') {
                i3 = i7 - 55;
            } else {
                if (cCharAt2 < 'a' || cCharAt2 > 'f') {
                    break;
                }
                i3 = i7 - 87;
            }
        }
        return -1;
    }

    @Override // com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        glyphRunPool.freeAll(this.runs);
        this.runs.clear();
        this.colors.clear();
        this.glyphCount = 0;
        this.width = 0.0f;
        this.height = 0.0f;
    }

    public String toString() {
        if (this.runs.size == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append(this.width);
        sb.append('x');
        sb.append(this.height);
        sb.append('\n');
        int i = this.runs.size;
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(this.runs.get(i2).toString());
            sb.append('\n');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g2d/GlyphLayout$GlyphRun.class
      classes8.dex
     */
    public static class GlyphRun implements Pool.Poolable {
        public float width;
        public float x;
        public float y;
        public Array<BitmapFont.Glyph> glyphs = new Array<>();
        public FloatArray xAdvances = new FloatArray();

        void appendRun(GlyphRun glyphRun) {
            this.glyphs.addAll(glyphRun.glyphs);
            if (this.xAdvances.notEmpty()) {
                this.xAdvances.size--;
            }
            this.xAdvances.addAll(glyphRun.xAdvances);
        }

        @Override // com.badlogic.gdx.utils.Pool.Poolable
        public void reset() {
            this.glyphs.clear();
            this.xAdvances.clear();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(this.glyphs.size + 32);
            Array<BitmapFont.Glyph> array = this.glyphs;
            int i = array.size;
            for (int i2 = 0; i2 < i; i2++) {
                sb.append((char) array.get(i2).f94id);
            }
            sb.append(", ");
            sb.append(this.x);
            sb.append(", ");
            sb.append(this.y);
            sb.append(", ");
            sb.append(this.width);
            return sb.toString();
        }
    }
}
