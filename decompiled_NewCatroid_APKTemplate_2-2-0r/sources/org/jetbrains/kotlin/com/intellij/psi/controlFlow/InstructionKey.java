package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import java.util.Arrays;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;

/* JADX INFO: loaded from: classes3.dex */
final class InstructionKey implements Comparable<InstructionKey> {
    private final int[] myCallStack;
    private final int myOffset;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/InstructionKey";
        } else if (i != 2) {
            objArr[0] = "callStack";
        } else {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/InstructionKey";
        } else {
            objArr[1] = "getCallStack";
        }
        if (i != 1) {
            if (i != 2) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "compareTo";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    private InstructionKey(int i, int[] iArr) {
        if (iArr == null) {
            $$$reportNull$$$0(0);
        }
        this.myOffset = i;
        this.myCallStack = iArr;
    }

    static InstructionKey create(int i) {
        return new InstructionKey(i, ArrayUtilRt.EMPTY_INT_ARRAY);
    }

    InstructionKey next(int i) {
        return new InstructionKey(i, this.myCallStack);
    }

    InstructionKey push(int i, int i2) {
        int[] iArr = this.myCallStack;
        if (iArr.length > 100) {
            throw new OverflowException(this.myOffset);
        }
        return new InstructionKey(i, ArrayUtil.append(iArr, i2));
    }

    InstructionKey pop(int i) {
        int[] iArr = this.myCallStack;
        int i2 = iArr[iArr.length - 1];
        int[] iArrRealloc = ArrayUtil.realloc(iArr, iArr.length - 1);
        if (i == 0) {
            i = i2;
        }
        return new InstructionKey(i, iArrRealloc);
    }

    int getOffset() {
        return this.myOffset;
    }

    int[] getCallStack() {
        int[] iArr = this.myCallStack;
        if (iArr == null) {
            $$$reportNull$$$0(1);
        }
        return iArr;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InstructionKey instructionKey = (InstructionKey) obj;
        return this.myOffset == instructionKey.myOffset && Arrays.equals(this.myCallStack, instructionKey.myCallStack);
    }

    public int hashCode() {
        return (this.myOffset * 31) + Arrays.hashCode(this.myCallStack);
    }

    public String toString() {
        if (this.myCallStack.length == 0) {
            return String.valueOf(this.myOffset);
        }
        StringBuilder sb = new StringBuilder();
        for (int i : this.myCallStack) {
            if (sb.length() != 0) {
                sb.append(',');
            }
            sb.append(i);
        }
        return this.myOffset + "[" + ((Object) sb) + "]";
    }

    @Override // java.lang.Comparable
    public int compareTo(InstructionKey instructionKey) {
        if (instructionKey == null) {
            $$$reportNull$$$0(2);
        }
        int i = this.myOffset - instructionKey.myOffset;
        if (i != 0) {
            return i;
        }
        int iMin = Math.min(this.myCallStack.length, instructionKey.myCallStack.length);
        for (int i2 = 0; i2 < iMin; i2++) {
            int i3 = this.myCallStack[i2] - instructionKey.myCallStack[i2];
            if (i3 != 0) {
                return i3;
            }
        }
        return this.myCallStack.length - instructionKey.myCallStack.length;
    }

    static class OverflowException extends RuntimeException {
        OverflowException(int i) {
            super("Instruction key overflow at offset " + i);
        }
    }
}
