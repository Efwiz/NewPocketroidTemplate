package org.eclipse.jdt.internal.compiler.parser.diagnose;

import java.lang.reflect.Array;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Initializer;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;

/* JADX INFO: loaded from: classes14.dex */
public class RangeUtil {
    public static final int IGNORE = 2;
    public static final int LBRACE_MISSING = 1;
    public static final int NO_FLAG = 0;

    static class RangeResult {
        private static final int INITIAL_SIZE = 10;
        int pos = 0;
        int[] intervalStarts = new int[10];
        int[] intervalEnds = new int[10];
        int[] intervalFlags = new int[10];

        private int compare(int i, int i2) {
            return i - i2;
        }

        RangeResult() {
        }

        void addInterval(int i, int i2) {
            addInterval(i, i2, 0);
        }

        void addInterval(int i, int i2, int i3) {
            int i4 = this.pos;
            int[] iArr = this.intervalStarts;
            if (i4 >= iArr.length) {
                int[] iArr2 = new int[i4 * 2];
                this.intervalStarts = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, i4);
                int[] iArr3 = this.intervalEnds;
                int i5 = this.pos;
                int[] iArr4 = new int[i5 * 2];
                this.intervalEnds = iArr4;
                System.arraycopy(iArr3, 0, iArr4, 0, i5);
                int[] iArr5 = this.intervalFlags;
                int i6 = this.pos;
                int[] iArr6 = new int[i6 * 2];
                this.intervalFlags = iArr6;
                System.arraycopy(iArr5, 0, iArr6, 0, i6);
            }
            int[] iArr7 = this.intervalStarts;
            int i7 = this.pos;
            iArr7[i7] = i;
            this.intervalEnds[i7] = i2;
            this.intervalFlags[i7] = i3;
            this.pos = i7 + 1;
        }

        int[][] getRanges() {
            int i = this.pos;
            int[] iArr = new int[i];
            int[] iArr2 = new int[i];
            int[] iArr3 = new int[i];
            System.arraycopy(this.intervalStarts, 0, iArr, 0, i);
            System.arraycopy(this.intervalEnds, 0, iArr2, 0, this.pos);
            System.arraycopy(this.intervalFlags, 0, iArr3, 0, this.pos);
            if (i > 1) {
                quickSort(iArr, iArr2, iArr3, 0, i - 1);
            }
            return new int[][]{iArr, iArr2, iArr3};
        }

        private void quickSort(int[] iArr, int[] iArr2, int[] iArr3, int i, int i2) {
            int i3;
            int i4;
            int i5 = iArr[((i2 - i) / 2) + i];
            int i6 = i;
            int i7 = i2;
            while (true) {
                if (compare(iArr[i6], i5) >= 0) {
                    while (compare(i5, iArr[i7]) < 0) {
                        i7--;
                    }
                    if (i6 <= i7) {
                        int i8 = iArr[i6];
                        iArr[i6] = iArr[i7];
                        iArr[i7] = i8;
                        int i9 = iArr2[i6];
                        iArr2[i6] = iArr2[i7];
                        iArr2[i7] = i9;
                        int i10 = iArr3[i6];
                        iArr3[i6] = iArr3[i7];
                        iArr3[i7] = i10;
                        i6++;
                        i7--;
                    }
                    i3 = i6;
                    i4 = i7;
                    if (i3 > i4) {
                        break;
                    }
                    i7 = i4;
                    i6 = i3;
                } else {
                    i6++;
                }
            }
            if (i < i4) {
                quickSort(iArr, iArr2, iArr3, i, i4);
            }
            if (i3 < i2) {
                quickSort(iArr, iArr2, iArr3, i3, i2);
            }
        }
    }

    public static boolean containsErrorInSignature(AbstractMethodDeclaration abstractMethodDeclaration) {
        return abstractMethodDeclaration.sourceEnd + 1 == abstractMethodDeclaration.bodyStart || abstractMethodDeclaration.bodyEnd == abstractMethodDeclaration.declarationSourceEnd;
    }

    public static int[][] computeDietRange(TypeDeclaration[] typeDeclarationArr) {
        if (typeDeclarationArr == null || typeDeclarationArr.length == 0) {
            return (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 3, 0);
        }
        RangeResult rangeResult = new RangeResult();
        computeDietRange0(typeDeclarationArr, rangeResult);
        return rangeResult.getRanges();
    }

    private static void computeDietRange0(TypeDeclaration[] typeDeclarationArr, RangeResult rangeResult) {
        for (int i = 0; i < typeDeclarationArr.length; i++) {
            TypeDeclaration[] typeDeclarationArr2 = typeDeclarationArr[i].memberTypes;
            if (typeDeclarationArr2 != null && typeDeclarationArr2.length > 0) {
                computeDietRange0(typeDeclarationArr[i].memberTypes, rangeResult);
            }
            AbstractMethodDeclaration[] abstractMethodDeclarationArr = typeDeclarationArr[i].methods;
            if (abstractMethodDeclarationArr != null) {
                for (AbstractMethodDeclaration abstractMethodDeclaration : abstractMethodDeclarationArr) {
                    if (containsIgnoredBody(abstractMethodDeclaration)) {
                        if (containsErrorInSignature(abstractMethodDeclaration)) {
                            abstractMethodDeclaration.bits |= 32;
                            rangeResult.addInterval(abstractMethodDeclaration.declarationSourceStart, abstractMethodDeclaration.declarationSourceEnd, 2);
                        } else {
                            rangeResult.addInterval(abstractMethodDeclaration.bodyStart, abstractMethodDeclaration.bodyEnd, abstractMethodDeclaration.sourceEnd + 1 != abstractMethodDeclaration.bodyStart ? 0 : 1);
                        }
                    }
                }
            }
            FieldDeclaration[] fieldDeclarationArr = typeDeclarationArr[i].fields;
            if (fieldDeclarationArr != null) {
                for (FieldDeclaration fieldDeclaration : fieldDeclarationArr) {
                    if (fieldDeclaration instanceof Initializer) {
                        Initializer initializer = (Initializer) fieldDeclaration;
                        if (initializer.declarationSourceEnd == initializer.bodyEnd && initializer.declarationSourceStart != initializer.declarationSourceEnd) {
                            initializer.bits |= 32;
                            rangeResult.addInterval(initializer.declarationSourceStart, initializer.declarationSourceEnd, 2);
                        } else {
                            rangeResult.addInterval(initializer.bodyStart, initializer.bodyEnd);
                        }
                    }
                }
            }
        }
    }

    public static boolean containsIgnoredBody(AbstractMethodDeclaration abstractMethodDeclaration) {
        return (abstractMethodDeclaration.isDefaultConstructor() || abstractMethodDeclaration.isClinit() || (abstractMethodDeclaration.modifiers & 16777216) != 0) ? false : true;
    }
}
