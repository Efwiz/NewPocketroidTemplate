package org.jetbrains.kotlin.com.intellij.openapi.editor.impl;

import java.util.Arrays;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.LineIterator;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.LineTokenizer;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.text.MergingCharSequence;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteArrayList;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntArrayList;

/* JADX INFO: loaded from: classes3.dex */
public final class LineSet {
    private final byte[] myFlags;
    private final int myLength;
    private final int[] myStarts;

    /* JADX WARN: Code duplicated, block: B:11:0x0013  */
    /* JADX WARN: Code duplicated, block: B:6:0x0009  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3) {
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3) {
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
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
            case 1:
                objArr[0] = "prevText";
                break;
            case 2:
            case 4:
                objArr[0] = "replacement";
                break;
            case 3:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/LineSet";
                break;
            case 5:
                objArr[0] = "indices";
                break;
            default:
                objArr[0] = "text";
                break;
        }
        if (i != 3) {
            switch (i) {
                case 6:
                case 7:
                    objArr[1] = "setModified";
                    break;
                case 8:
                case 9:
                    objArr[1] = "clearModificationFlags";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/impl/LineSet";
                    break;
            }
        } else {
            objArr[1] = ConfigConstants.CONFIG_KEY_UPDATE;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = ConfigConstants.CONFIG_KEY_UPDATE;
                break;
            case 3:
            case 6:
            case 7:
            case 8:
            case 9:
                break;
            case 4:
                objArr[2] = "isSingleLineChange";
                break;
            case 5:
                objArr[2] = "setModified";
                break;
            default:
                objArr[2] = "createLineSet";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3) {
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    private LineSet(int[] iArr, byte[] bArr, int i) {
        this.myStarts = iArr;
        this.myFlags = bArr;
        this.myLength = i;
    }

    public static LineSet createLineSet(CharSequence charSequence) {
        return createLineSet(charSequence, false);
    }

    private static LineSet createLineSet(CharSequence charSequence, boolean z) {
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
        IntArrayList intArrayList = new IntArrayList();
        ByteArrayList byteArrayList = new ByteArrayList();
        LineTokenizer lineTokenizer = new LineTokenizer(charSequence);
        while (!lineTokenizer.atEnd()) {
            intArrayList.add(lineTokenizer.getOffset());
            byteArrayList.add((byte) (lineTokenizer.getLineSeparatorLength() | (z ? 4 : 0)));
            lineTokenizer.advance();
        }
        return new LineSet(intArrayList.toIntArray(), byteArrayList.toByteArray(), charSequence.length());
    }

    LineSet update(CharSequence charSequence, int i, int i2, CharSequence charSequence2, boolean z) {
        LineSet lineSetGenericUpdate;
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(2);
        }
        if (this.myLength == 0) {
            return createLineSet(charSequence2, !z);
        }
        MergingCharSequence mergingCharSequenceReplaceSubSequence = StringUtil.replaceSubSequence(charSequence, i, i2, charSequence2);
        if (hasChar(charSequence, i - 1, '\r') && (hasChar(charSequence, i, '\n') || hasChar(mergingCharSequenceReplaceSubSequence, i, '\n'))) {
            i--;
            charSequence2 = new MergingCharSequence("\r", charSequence2);
        }
        if (hasChar(charSequence, i2, '\n') && (hasChar(charSequence, i2 - 1, '\r') || hasChar(mergingCharSequenceReplaceSubSequence, (charSequence2.length() + i) - 1, '\r'))) {
            i2++;
            charSequence2 = new MergingCharSequence(charSequence2, "\n");
        }
        if (isSingleLineChange(i, i2, charSequence2)) {
            lineSetGenericUpdate = updateInsideOneLine(findLineIndex(i), charSequence2.length() - (i2 - i));
        } else {
            lineSetGenericUpdate = genericUpdate(i, i2, charSequence2);
        }
        if (z) {
            lineSetGenericUpdate = lineSetGenericUpdate.clearModificationFlags();
        }
        if (lineSetGenericUpdate == null) {
            $$$reportNull$$$0(3);
        }
        return lineSetGenericUpdate;
    }

    private static boolean hasChar(CharSequence charSequence, int i, char c) {
        return i >= 0 && i < charSequence.length() && charSequence.charAt(i) == c;
    }

    private boolean isSingleLineChange(int i, int i2, CharSequence charSequence) {
        int iFindLineIndex;
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        return ((i == 0 && i2 == this.myLength && charSequence.length() == 0) || (iFindLineIndex = findLineIndex(i)) != findLineIndex(i2) || CharArrayUtil.containLineBreaks(charSequence) || isLastEmptyLine(iFindLineIndex)) ? false : true;
    }

    private LineSet updateInsideOneLine(int i, int i2) {
        int[] iArr = (int[]) this.myStarts.clone();
        for (int i3 = i + 1; i3 < iArr.length; i3++) {
            iArr[i3] = iArr[i3] + i2;
        }
        byte[] bArr = (byte[]) this.myFlags.clone();
        bArr[i] = (byte) (bArr[i] | 4);
        return new LineSet(iArr, bArr, this.myLength + i2);
    }

    private LineSet genericUpdate(int i, int i2, CharSequence charSequence) {
        int iFindLineIndex = findLineIndex(i);
        int iFindLineIndex2 = findLineIndex(i2);
        LineSet lineSetCreateLineSet = createLineSet(charSequence, true);
        int i3 = lineSetCreateLineSet.myLength - (i2 - i);
        int lineStart = getLineStart(iFindLineIndex);
        int i4 = (i - lineStart > 0 || lineSetCreateLineSet.myStarts.length > 0 || i2 < this.myLength) ? 1 : 0;
        int i5 = (i2 >= this.myLength || lineSetCreateLineSet.myLength <= 0 || lineSetCreateLineSet.getSeparatorLength(lineSetCreateLineSet.myStarts.length - 1) <= 0) ? 0 : 1;
        int iMax = iFindLineIndex + i4 + Math.max(lineSetCreateLineSet.myStarts.length - 1, 0) + i5 + Math.max((this.myStarts.length - iFindLineIndex2) - 1, 0);
        int[] iArrNewIntArray = ArrayUtil.newIntArray(iMax);
        byte[] bArrNewByteArray = ArrayUtil.newByteArray(iMax);
        if (iFindLineIndex > 0) {
            System.arraycopy(this.myStarts, 0, iArrNewIntArray, 0, iFindLineIndex);
            System.arraycopy(this.myFlags, 0, bArrNewByteArray, 0, iFindLineIndex);
        }
        if (i4 != 0) {
            iArrNewIntArray[iFindLineIndex] = lineStart;
            bArrNewByteArray[iFindLineIndex] = lineSetCreateLineSet.myStarts.length > 0 ? lineSetCreateLineSet.myFlags[0] : (byte) 4;
            iFindLineIndex++;
        }
        int iShiftData = lineSetCreateLineSet.shiftData(iArrNewIntArray, bArrNewByteArray, 1, iFindLineIndex, lineSetCreateLineSet.myStarts.length - 1, i);
        if (i2 < this.myLength) {
            if (i5 != 0) {
                iArrNewIntArray[iShiftData] = i2 + i3;
                bArrNewByteArray[iShiftData] = (byte) (this.myFlags[iFindLineIndex2] | 4);
                iShiftData++;
            } else if (iShiftData > 0) {
                bArrNewByteArray[iShiftData - 1] = (byte) (this.myFlags[iFindLineIndex2] | 4);
            }
        }
        int i6 = iFindLineIndex2 + 1;
        shiftData(iArrNewIntArray, bArrNewByteArray, i6, iShiftData, this.myStarts.length - i6, i3);
        return new LineSet(iArrNewIntArray, bArrNewByteArray, this.myLength + i3);
    }

    private int shiftData(int[] iArr, byte[] bArr, int i, int i2, int i3, int i4) {
        if (i3 < 0) {
            return i2;
        }
        System.arraycopy(this.myFlags, i, bArr, i2, i3);
        for (int i5 = 0; i5 < i3; i5++) {
            iArr[i2 + i5] = this.myStarts[i + i5] + i4;
        }
        return i2 + i3;
    }

    public int findLineIndex(int i) {
        int i2;
        if (i < 0 || i > (i2 = this.myLength)) {
            throw new IndexOutOfBoundsException("Wrong offset: " + i + ". Should be in range: [0, " + this.myLength + "]");
        }
        if (i2 == 0) {
            return 0;
        }
        if (i == i2) {
            return getLineCount() - 1;
        }
        int iBinarySearch = Arrays.binarySearch(this.myStarts, i);
        return iBinarySearch >= 0 ? iBinarySearch : (-iBinarySearch) - 2;
    }

    public LineIterator createIterator() {
        return new LineIteratorImpl(this);
    }

    public int getLineStart(int i) {
        checkLineIndex(i);
        return isLastEmptyLine(i) ? this.myLength : this.myStarts[i];
    }

    private boolean isLastEmptyLine(int i) {
        return i == this.myFlags.length && hasEol(i - 1);
    }

    private boolean hasEol(int i) {
        return i >= 0 && getSeparatorLengthUnsafe(i) > 0;
    }

    public int getLineEnd(int i) {
        checkLineIndex(i);
        int[] iArr = this.myStarts;
        return i >= iArr.length + (-1) ? this.myLength : iArr[i + 1];
    }

    private void checkLineIndex(int i) {
        if (i < 0 || i >= getLineCount()) {
            throw new IndexOutOfBoundsException("Wrong line: " + i + ". Available lines count: " + getLineCount());
        }
    }

    LineSet clearModificationFlags(int i, int i2) {
        if (i > i2) {
            throw new IllegalArgumentException("endLine < startLine: " + i2 + " < " + i + "; lineCount: " + getLineCount());
        }
        checkLineIndex(i);
        int i3 = i2 - 1;
        checkLineIndex(i3);
        if (isLastEmptyLine(i3)) {
            i2--;
        }
        if (i >= i2) {
            return this;
        }
        byte[] bArr = (byte[]) this.myFlags.clone();
        while (i < i2) {
            bArr[i] = (byte) (bArr[i] & (-5));
            i++;
        }
        return new LineSet(this.myStarts, bArr, this.myLength);
    }

    LineSet clearModificationFlags() {
        LineSet lineSetClearModificationFlags = getLineCount() == 0 ? this : clearModificationFlags(0, getLineCount());
        if (lineSetClearModificationFlags == null) {
            $$$reportNull$$$0(9);
        }
        return lineSetClearModificationFlags;
    }

    int getSeparatorLength(int i) {
        checkLineIndex(i);
        return getSeparatorLengthUnsafe(i);
    }

    private int getSeparatorLengthUnsafe(int i) {
        byte[] bArr = this.myFlags;
        if (i < bArr.length) {
            return bArr[i] & 3;
        }
        return 0;
    }

    public int getLineCount() {
        int[] iArr = this.myStarts;
        return iArr.length + (isLastEmptyLine(iArr.length) ? 1 : 0);
    }

    public int getLength() {
        return this.myLength;
    }
}
