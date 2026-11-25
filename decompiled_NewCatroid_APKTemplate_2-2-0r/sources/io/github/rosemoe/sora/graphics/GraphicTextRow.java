package io.github.rosemoe.sora.graphics;

import android.util.Log;
import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.lang.styling.TextStyle;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentLine;
import io.github.rosemoe.sora.text.bidi.Directions;
import io.github.rosemoe.sora.text.bidi.TextBidi;
import io.github.rosemoe.sora.util.IntPair;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class GraphicTextRow {
    private static final GraphicTextRow[] sCached = new GraphicTextRow[5];
    private Directions directions;
    private Paint paint;
    private boolean quickMeasureMode;
    private List<Integer> softBreaks;
    private List<Span> spans;
    private int tabWidth;
    private ContentLine text;
    private int textEnd;
    private int textStart;
    private boolean useCache = true;
    private final Directions tmpDirections = new Directions(new long[]{IntPair.pack(0, 0)}, 0);

    private GraphicTextRow() {
    }

    public static GraphicTextRow obtain(boolean z) {
        GraphicTextRow[] graphicTextRowArr;
        GraphicTextRow graphicTextRow;
        GraphicTextRow[] graphicTextRowArr2 = sCached;
        synchronized (graphicTextRowArr2) {
            int length = graphicTextRowArr2.length;
            do {
                length--;
                if (length >= 0) {
                    graphicTextRowArr = sCached;
                    graphicTextRow = graphicTextRowArr[length];
                } else {
                    GraphicTextRow graphicTextRow2 = new GraphicTextRow();
                    graphicTextRow2.quickMeasureMode = z;
                    return graphicTextRow2;
                }
            } while (graphicTextRow == null);
            graphicTextRowArr[length] = null;
            graphicTextRow.quickMeasureMode = z;
            return graphicTextRow;
        }
    }

    public static void recycle(GraphicTextRow graphicTextRow) {
        graphicTextRow.text = null;
        graphicTextRow.spans = null;
        graphicTextRow.paint = null;
        int i = 0;
        graphicTextRow.tabWidth = 0;
        graphicTextRow.textEnd = 0;
        graphicTextRow.textStart = 0;
        graphicTextRow.useCache = true;
        graphicTextRow.softBreaks = null;
        graphicTextRow.directions = null;
        synchronized (sCached) {
            while (true) {
                GraphicTextRow[] graphicTextRowArr = sCached;
                if (i >= graphicTextRowArr.length) {
                    break;
                }
                if (graphicTextRowArr[i] == null) {
                    graphicTextRowArr[i] = graphicTextRow;
                    break;
                }
                i++;
            }
        }
    }

    public void recycle() {
        recycle(this);
    }

    public void set(Content content, int i, int i2, int i3, int i4, List<Span> list, Paint paint) {
        this.paint = paint;
        this.text = content.getLine(i);
        this.directions = content.getLineDirections(i);
        this.tabWidth = i4;
        this.textStart = i2;
        this.textEnd = i3;
        this.spans = list;
        this.tmpDirections.setLength(this.text.length());
    }

    public void set(ContentLine contentLine, Directions directions, int i, int i2, int i3, List<Span> list, Paint paint) {
        this.paint = paint;
        this.text = contentLine;
        this.directions = directions;
        this.tabWidth = i3;
        this.textStart = i;
        this.textEnd = i2;
        this.spans = list;
        this.tmpDirections.setLength(contentLine.length());
    }

    public void setSoftBreaks(List<Integer> list) {
        this.softBreaks = list;
    }

    public void disableCache() {
        this.useCache = false;
    }

    public void buildMeasureCache() {
        if (this.text.widthCache == null || this.text.widthCache.length < this.textEnd + 4) {
            ContentLine contentLine = this.text;
            contentLine.widthCache = new float[Math.max(90, contentLine.length() + 16)];
        }
        measureTextInternal(this.textStart, this.textEnd, this.text.widthCache);
        float[] fArr = this.text.widthCache;
        float f = fArr[0];
        fArr[0] = 0.0f;
        int i = 1;
        while (i <= this.textEnd) {
            float f2 = fArr[i];
            fArr[i] = fArr[i - 1] + f;
            i++;
            f = f2;
        }
    }

    public long findOffsetByAdvance(int i, float f) {
        boolean z;
        long j;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        float f2;
        if (this.text.widthCache != null && this.useCache) {
            float[] fArr = this.text.widthCache;
            int i7 = this.textEnd;
            float f3 = fArr[i];
            int i8 = i;
            int i9 = i7;
            while (i8 <= i9) {
                int i10 = (i8 + i9) / 2;
                if (i10 >= i && i10 < i7) {
                    float f4 = fArr[i10] - f3;
                    if (f4 > f) {
                        i9 = i10 - 1;
                    } else if (f4 < f) {
                        i8 = i10 + 1;
                    }
                }
                i8 = i10;
                break;
            }
            if (fArr[i8] - f3 > f) {
                i8--;
            }
            int iMax = Math.max(i, Math.min(i7, i8));
            return CharPosDesc.make(iMax, fArr[iMax] - f3);
        }
        TextRegionIterator textRegionIterator = new TextRegionIterator(this.textEnd, this.spans, this.softBreaks);
        char[] cArr = this.text.value;
        float spaceWidth = this.paint.getSpaceWidth() * this.tabWidth;
        boolean z2 = true;
        int i11 = i;
        float f5 = 0.0f;
        long j2 = 0;
        while (textRegionIterator.hasNextRegion() && f5 < f) {
            if (z2) {
                textRegionIterator.requireStartOffset(i);
                z = false;
            } else {
                textRegionIterator.nextRegion();
                z = z2;
            }
            int startIndex = textRegionIterator.getStartIndex();
            int iMin = Math.min(this.textEnd, textRegionIterator.getEndIndex());
            long styleBits = textRegionIterator.getSpan().getStyleBits();
            if (styleBits != j2) {
                if (TextStyle.isBold(styleBits) != TextStyle.isBold(j2)) {
                    this.paint.setFakeBoldText(TextStyle.isBold(styleBits));
                }
                if (TextStyle.isItalics(styleBits) != TextStyle.isItalics(j2)) {
                    this.paint.setTextSkewX(TextStyle.isItalics(styleBits) ? -0.2f : 0.0f);
                }
                j = styleBits;
            } else {
                j = j2;
            }
            float fMeasureText = f5;
            int i12 = startIndex;
            int i13 = i12;
            while (true) {
                if (i13 >= iMin) {
                    i2 = i12;
                    i3 = iMin;
                    i4 = -1;
                    i5 = -1;
                    break;
                }
                if (cArr[i13] != '\t') {
                    i6 = i13;
                    i3 = iMin;
                } else if (i12 != i13) {
                    int iFindOffsetByRunAdvance = this.paint.findOffsetByRunAdvance(this.text, i12, i13, f - fMeasureText, this.useCache, this.quickMeasureMode);
                    i4 = -1;
                    i2 = i12;
                    i6 = i13;
                    i3 = iMin;
                    fMeasureText += this.paint.measureTextRunAdvance(cArr, i12, iFindOffsetByRunAdvance, startIndex, iMin, this.quickMeasureMode);
                    if (iFindOffsetByRunAdvance < i6) {
                        i5 = iFindOffsetByRunAdvance;
                        break;
                    }
                    f2 = fMeasureText + spaceWidth;
                    if (f2 > f) {
                        i5 = i6;
                        break;
                    }
                    i12 = i6 + 1;
                    fMeasureText = f2;
                } else {
                    i2 = i12;
                    i6 = i13;
                    i3 = iMin;
                    i4 = -1;
                    f2 = fMeasureText + spaceWidth;
                    if (f2 > f) {
                        i5 = i6;
                        break;
                    }
                    i12 = i6 + 1;
                    fMeasureText = f2;
                }
                i13 = i6 + 1;
                iMin = i3;
            }
            if (i5 == i4) {
                int iFindOffsetByRunAdvance2 = this.paint.findOffsetByRunAdvance(this.text, i2, i3, f - fMeasureText, this.useCache, this.quickMeasureMode);
                fMeasureText += measureText(i2, iFindOffsetByRunAdvance2);
                i11 = iFindOffsetByRunAdvance2;
            } else {
                i11 = i5;
            }
            f5 = fMeasureText;
            if (i11 < i3 || i3 == this.textEnd) {
                j2 = j;
                break;
            }
            z2 = z;
            j2 = j;
        }
        if (j2 != 0) {
            this.paint.setFakeBoldText(false);
            this.paint.setTextSkewX(0.0f);
        }
        return CharPosDesc.make(i11, f5);
    }

    public float measureText(int i, int i2) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("negative start position");
        }
        if (i >= i2) {
            if (i == i2) {
                return 0.0f;
            }
            Log.w("GraphicTextRow", "start > end. if this is caused by editor, please provide feedback", new Throwable());
            return 0.0f;
        }
        float[] fArr = this.text.widthCache;
        if (fArr != null && this.useCache && i2 < fArr.length) {
            return fArr[i2] - fArr[i];
        }
        return measureTextInternal(i, i2, null);
    }

    private float measureTextInternal(int i, int i2, float[] fArr) {
        long j;
        boolean zIsFakeBoldText = this.paint.isFakeBoldText();
        float textSkewX = this.paint.getTextSkewX();
        int iMax = Math.max(i, this.textStart);
        int iMin = Math.min(i2, this.textEnd);
        TextRegionIterator textRegionIterator = new TextRegionIterator(iMin, this.spans, this.softBreaks);
        long j2 = 0;
        boolean z = true;
        float fMeasureTextInner = 0.0f;
        while (textRegionIterator.hasNextRegion()) {
            if (z) {
                textRegionIterator.requireStartOffset(iMax);
                z = false;
            } else {
                textRegionIterator.nextRegion();
            }
            boolean z2 = z;
            int startIndex = textRegionIterator.getStartIndex();
            int iMin2 = Math.min(iMin, textRegionIterator.getEndIndex());
            if (startIndex > iMin2 || (startIndex == iMin2 && iMin2 >= iMin)) {
                break;
            }
            long styleBits = textRegionIterator.getSpan().getStyleBits();
            if (styleBits != j2) {
                if (TextStyle.isBold(styleBits) != TextStyle.isBold(j2)) {
                    this.paint.setFakeBoldText(TextStyle.isBold(styleBits));
                }
                if (TextStyle.isItalics(styleBits) != TextStyle.isItalics(j2)) {
                    this.paint.setTextSkewX(TextStyle.isItalics(styleBits) ? -0.2f : 0.0f);
                }
                j = styleBits;
            } else {
                j = j2;
            }
            fMeasureTextInner += measureTextInner(startIndex, iMin2, Math.min(startIndex, textRegionIterator.getSpanStart()), Math.min(this.textEnd, Math.max(iMin2, textRegionIterator.getSpanEnd())), fArr);
            if (iMin2 >= iMin) {
                break;
            }
            z = z2;
            j2 = j;
        }
        this.paint.setFakeBoldText(zIsFakeBoldText);
        this.paint.setTextSkewX(textSkewX);
        return fMeasureTextInner;
    }

    private float measureTextInner(int i, int i2, int i3, int i4, float[] fArr) {
        int i5 = i;
        if (i5 >= i2) {
            return 0.0f;
        }
        Directions directions = this.directions;
        if (directions == null) {
            directions = this.text.mayNeedBidi() ? TextBidi.getDirections(this.text) : this.tmpDirections;
        }
        Directions directions2 = directions;
        int i6 = 0;
        float fMyGetTextRunAdvances = 0.0f;
        for (int i7 = 0; i7 < directions2.getRunCount(); i7++) {
            int iMax = Math.max(i5, directions2.getRunStart(i7));
            int iMin = Math.min(i2, directions2.getRunEnd(i7));
            if (iMin > iMax) {
                fMyGetTextRunAdvances += this.paint.myGetTextRunAdvances(this.text.value, iMax, iMin - iMax, i3, i4 - i3, directions2.isRunRtl(i7), fArr, fArr == null ? 0 : iMax, this.quickMeasureMode);
            }
            if (directions2.getRunStart(i7) >= i2) {
                break;
            }
        }
        float spaceWidth = this.paint.getSpaceWidth() * this.tabWidth;
        while (i5 < i2) {
            if (this.text.charAt(i5) == '\t') {
                i6++;
                if (fArr != null) {
                    fArr[i5] = spaceWidth;
                }
            }
            i5++;
        }
        return fMyGetTextRunAdvances + ((i6 == 0 ? 0.0f : spaceWidth - this.paint.measureText("\t")) * i6);
    }
}
