package org.jetbrains.kotlin.com.intellij.openapi.util;

import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: loaded from: classes3.dex */
public class ProperTextRange extends TextRange {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "subRange";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/ProperTextRange";
        } else if (i == 5) {
            objArr[0] = "textRange";
        } else if (i != 6) {
            objArr[0] = "range";
        } else {
            objArr[0] = "segment";
        }
        if (i == 2) {
            objArr[1] = "shiftRight";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/ProperTextRange";
        } else {
            objArr[1] = "grown";
        }
        switch (i) {
            case 1:
                objArr[2] = "cutOut";
                break;
            case 2:
            case 3:
                break;
            case 4:
                objArr[2] = "intersection";
                break;
            case 5:
                objArr[2] = SchemaSymbols.ATTVAL_UNION;
                break;
            case 6:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public ProperTextRange(int i, int i2) {
        super(i, i2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ProperTextRange(TextRange textRange) {
        this(textRange.getStartOffset(), textRange.getEndOffset());
        if (textRange == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.TextRange
    public ProperTextRange cutOut(TextRange textRange) {
        if (textRange == null) {
            $$$reportNull$$$0(1);
        }
        return new ProperTextRange(getStartOffset() + textRange.getStartOffset(), Math.min(getEndOffset(), getStartOffset() + textRange.getEndOffset()));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.TextRange
    public ProperTextRange shiftRight(int i) {
        return i == 0 ? this : new ProperTextRange(getStartOffset() + i, getEndOffset() + i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.TextRange
    public ProperTextRange grown(int i) {
        return i == 0 ? this : new ProperTextRange(getStartOffset(), getEndOffset() + i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.TextRange
    public ProperTextRange intersection(TextRange textRange) {
        if (textRange == null) {
            $$$reportNull$$$0(4);
        }
        assertProperRange(textRange);
        int iMax = Math.max(getStartOffset(), textRange.getStartOffset());
        int iMin = Math.min(getEndOffset(), textRange.getEndOffset());
        if (isProperRange(iMax, iMin)) {
            return new ProperTextRange(iMax, iMin);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.TextRange
    public ProperTextRange union(TextRange textRange) {
        if (textRange == null) {
            $$$reportNull$$$0(5);
        }
        assertProperRange(textRange);
        return new ProperTextRange(super.union(textRange));
    }

    public static ProperTextRange create(Segment segment) {
        if (segment == null) {
            $$$reportNull$$$0(6);
        }
        return new ProperTextRange(segment.getStartOffset(), segment.getEndOffset());
    }

    public static ProperTextRange create(int i, int i2) {
        return new ProperTextRange(i, i2);
    }
}
