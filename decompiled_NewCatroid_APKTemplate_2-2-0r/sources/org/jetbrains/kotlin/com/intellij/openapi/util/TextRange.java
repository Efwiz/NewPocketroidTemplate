package org.jetbrains.kotlin.com.intellij.openapi.util;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: loaded from: classes3.dex */
public class TextRange implements Segment, Serializable {
    private static final long serialVersionUID = -670091356599757430L;
    private final int myEndOffset;
    private final int myStartOffset;
    public static final TextRange EMPTY_RANGE = new TextRange(0, 0);
    public static final TextRange[] EMPTY_ARRAY = new TextRange[0];

    /* JADX WARN: Code duplicated, block: B:9:0x0014  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 5 && i != 7 && i != 17 && i != 23) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 5 && i != 7 && i != 17 && i != 23) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
                objArr[0] = "outer";
                break;
            case 3:
                objArr[0] = "inner";
                break;
            case 4:
            case 6:
                objArr[0] = "str";
                break;
            case 5:
            case 7:
            case 9:
            case 10:
            case 11:
            case 17:
            case 23:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/TextRange";
                break;
            case 8:
                objArr[0] = "subRange";
                break;
            case 12:
                objArr[0] = "segment";
                break;
            case 13:
                objArr[0] = "segment1";
                break;
            case 14:
                objArr[0] = "segment2";
                break;
            case 15:
                objArr[0] = "original";
                break;
            case 16:
                objArr[0] = "replacement";
                break;
            case 18:
            case 19:
            case 20:
            case 22:
                objArr[0] = "textRange";
                break;
            case 21:
            case 25:
            case 26:
            default:
                objArr[0] = "range";
                break;
            case 24:
                objArr[0] = "s";
                break;
            case 27:
            case 28:
                objArr[0] = CrashHianalyticsData.MESSAGE;
                break;
        }
        if (i == 5) {
            objArr[1] = "substring";
        } else if (i == 7) {
            objArr[1] = "subSequence";
        } else if (i == 17) {
            objArr[1] = SchemaSymbols.ATTVAL_REPLACE;
        } else if (i != 23) {
            switch (i) {
                case 9:
                    objArr[1] = "shiftRight";
                    break;
                case 10:
                    objArr[1] = "shiftLeft";
                    break;
                case 11:
                    objArr[1] = "grown";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/TextRange";
                    break;
            }
        } else {
            objArr[1] = SchemaSymbols.ATTVAL_UNION;
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "containsRange";
                break;
            case 4:
                objArr[2] = "substring";
                break;
            case 5:
            case 7:
            case 9:
            case 10:
            case 11:
            case 17:
            case 23:
                break;
            case 6:
                objArr[2] = "subSequence";
                break;
            case 8:
                objArr[2] = "cutOut";
                break;
            case 12:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            case 13:
            case 14:
                objArr[2] = "areSegmentsEqual";
                break;
            case 15:
            case 16:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 18:
            case 19:
                objArr[2] = "intersects";
                break;
            case 20:
                objArr[2] = "intersectsStrict";
                break;
            case 21:
                objArr[2] = "intersection";
                break;
            case 22:
                objArr[2] = SchemaSymbols.ATTVAL_UNION;
                break;
            case 24:
                objArr[2] = "allOf";
                break;
            case 25:
            case 26:
            case 27:
            case 28:
                objArr[2] = "assertProperRange";
                break;
            default:
                objArr[2] = "contains";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 7 && i != 17 && i != 23) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public static boolean isProperRange(int i, int i2) {
        return i <= i2 && i >= 0;
    }

    public TextRange(int i, int i2) {
        this(i, i2, true);
    }

    protected TextRange(int i, int i2, boolean z) {
        this.myStartOffset = i;
        this.myEndOffset = i2;
        if (z) {
            assertProperRange(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Segment
    public final int getStartOffset() {
        return this.myStartOffset;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Segment
    public final int getEndOffset() {
        return this.myEndOffset;
    }

    public final int getLength() {
        return this.myEndOffset - this.myStartOffset;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TextRange)) {
            return false;
        }
        TextRange textRange = (TextRange) obj;
        return this.myStartOffset == textRange.myStartOffset && this.myEndOffset == textRange.myEndOffset;
    }

    public int hashCode() {
        return this.myStartOffset + this.myEndOffset;
    }

    public boolean contains(TextRange textRange) {
        if (textRange == null) {
            $$$reportNull$$$0(0);
        }
        return contains((Segment) textRange);
    }

    public boolean contains(Segment segment) {
        if (segment == null) {
            $$$reportNull$$$0(1);
        }
        return containsRange(segment.getStartOffset(), segment.getEndOffset());
    }

    public boolean containsRange(int i, int i2) {
        return getStartOffset() <= i && i2 <= getEndOffset();
    }

    public static boolean containsRange(Segment segment, Segment segment2) {
        if (segment == null) {
            $$$reportNull$$$0(2);
        }
        if (segment2 == null) {
            $$$reportNull$$$0(3);
        }
        return segment.getStartOffset() <= segment2.getStartOffset() && segment2.getEndOffset() <= segment.getEndOffset();
    }

    public boolean containsOffset(int i) {
        return this.myStartOffset <= i && i <= this.myEndOffset;
    }

    public String toString() {
        return "(" + this.myStartOffset + "," + this.myEndOffset + Constant.AFTER_QUTO;
    }

    public boolean contains(int i) {
        return this.myStartOffset <= i && i < this.myEndOffset;
    }

    public String substring(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        String strSubstring = str.substring(this.myStartOffset, this.myEndOffset);
        if (strSubstring == null) {
            $$$reportNull$$$0(5);
        }
        return strSubstring;
    }

    public CharSequence subSequence(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(6);
        }
        CharSequence charSequenceSubSequence = charSequence.subSequence(this.myStartOffset, this.myEndOffset);
        if (charSequenceSubSequence == null) {
            $$$reportNull$$$0(7);
        }
        return charSequenceSubSequence;
    }

    public TextRange cutOut(TextRange textRange) {
        if (textRange == null) {
            $$$reportNull$$$0(8);
        }
        if (textRange.getStartOffset() > getLength()) {
            throw new IllegalArgumentException("SubRange: " + textRange + "; this=" + this);
        }
        if (textRange.getEndOffset() > getLength()) {
            throw new IllegalArgumentException("SubRange: " + textRange + "; this=" + this);
        }
        assertProperRange(textRange);
        return new TextRange(this.myStartOffset + textRange.getStartOffset(), Math.min(this.myEndOffset, this.myStartOffset + textRange.getEndOffset()));
    }

    public TextRange shiftRight(int i) {
        return i == 0 ? this : new TextRange(this.myStartOffset + i, this.myEndOffset + i);
    }

    public TextRange shiftLeft(int i) {
        return i == 0 ? this : new TextRange(this.myStartOffset - i, this.myEndOffset - i);
    }

    public TextRange grown(int i) {
        return i == 0 ? this : from(this.myStartOffset, getLength() + i);
    }

    public static TextRange from(int i, int i2) {
        return create(i, i2 + i);
    }

    public static TextRange create(int i, int i2) {
        return new TextRange(i, i2);
    }

    public static TextRange create(Segment segment) {
        if (segment == null) {
            $$$reportNull$$$0(12);
        }
        return create(segment.getStartOffset(), segment.getEndOffset());
    }

    public static boolean areSegmentsEqual(Segment segment, Segment segment2) {
        if (segment == null) {
            $$$reportNull$$$0(13);
        }
        if (segment2 == null) {
            $$$reportNull$$$0(14);
        }
        return segment.getStartOffset() == segment2.getStartOffset() && segment.getEndOffset() == segment2.getEndOffset();
    }

    public String replace(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        if (str2 == null) {
            $$$reportNull$$$0(16);
        }
        String str3 = str.substring(0, getStartOffset()) + str2 + str.substring(getEndOffset());
        if (str3 == null) {
            $$$reportNull$$$0(17);
        }
        return str3;
    }

    public boolean intersects(TextRange textRange) {
        if (textRange == null) {
            $$$reportNull$$$0(18);
        }
        return intersects((Segment) textRange);
    }

    public boolean intersects(Segment segment) {
        if (segment == null) {
            $$$reportNull$$$0(19);
        }
        return intersects(segment.getStartOffset(), segment.getEndOffset());
    }

    public boolean intersects(int i, int i2) {
        return Math.max(this.myStartOffset, i) <= Math.min(this.myEndOffset, i2);
    }

    public boolean intersectsStrict(TextRange textRange) {
        if (textRange == null) {
            $$$reportNull$$$0(20);
        }
        return intersectsStrict(textRange.getStartOffset(), textRange.getEndOffset());
    }

    public boolean intersectsStrict(int i, int i2) {
        return Math.max(this.myStartOffset, i) < Math.min(this.myEndOffset, i2);
    }

    public TextRange intersection(TextRange textRange) {
        if (textRange == null) {
            $$$reportNull$$$0(21);
        }
        if (equals(textRange)) {
            return this;
        }
        int iMax = Math.max(this.myStartOffset, textRange.getStartOffset());
        int iMin = Math.min(this.myEndOffset, textRange.getEndOffset());
        if (isProperRange(iMax, iMin)) {
            return new TextRange(iMax, iMin);
        }
        return null;
    }

    public boolean isEmpty() {
        return this.myStartOffset >= this.myEndOffset;
    }

    public TextRange union(TextRange textRange) {
        if (textRange == null) {
            $$$reportNull$$$0(22);
        }
        return equals(textRange) ? this : new TextRange(Math.min(this.myStartOffset, textRange.getStartOffset()), Math.max(this.myEndOffset, textRange.getEndOffset()));
    }

    public boolean equalsToRange(int i, int i2) {
        return i == this.myStartOffset && i2 == this.myEndOffset;
    }

    public static TextRange allOf(String str) {
        if (str == null) {
            $$$reportNull$$$0(24);
        }
        return new TextRange(0, str.length());
    }

    public static void assertProperRange(Segment segment) throws AssertionError {
        if (segment == null) {
            $$$reportNull$$$0(25);
        }
        assertProperRange(segment, "");
    }

    public static void assertProperRange(Segment segment, Object obj) throws AssertionError {
        if (segment == null) {
            $$$reportNull$$$0(26);
        }
        if (obj == null) {
            $$$reportNull$$$0(27);
        }
        assertProperRange(segment.getStartOffset(), segment.getEndOffset(), obj);
    }

    public static void assertProperRange(int i, int i2, Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(28);
        }
        if (!isProperRange(i, i2)) {
            throw new IllegalArgumentException("Invalid range specified: (" + i + ", " + i2 + "); " + obj);
        }
    }
}
