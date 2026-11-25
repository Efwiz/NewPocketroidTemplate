package org.eclipse.jdt.internal.compiler.classfmt;

import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;
import org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation;
import org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker;

/* JADX INFO: loaded from: classes14.dex */
public class TypeAnnotationWalker implements ITypeAnnotationWalker {
    protected final long matches;
    protected final int pathPtr;
    protected final IBinaryTypeAnnotation[] typeAnnotations;

    public TypeAnnotationWalker(IBinaryTypeAnnotation[] iBinaryTypeAnnotationArr) {
        this(iBinaryTypeAnnotationArr, (-1) >>> (64 - iBinaryTypeAnnotationArr.length));
    }

    TypeAnnotationWalker(IBinaryTypeAnnotation[] iBinaryTypeAnnotationArr, long j) {
        this(iBinaryTypeAnnotationArr, j, 0);
    }

    protected TypeAnnotationWalker(IBinaryTypeAnnotation[] iBinaryTypeAnnotationArr, long j, int i) {
        this.typeAnnotations = iBinaryTypeAnnotationArr;
        this.matches = j;
        this.pathPtr = i;
    }

    protected ITypeAnnotationWalker restrict(long j, int i) {
        IBinaryTypeAnnotation[] iBinaryTypeAnnotationArr;
        if (this.matches == j && this.pathPtr == i) {
            return this;
        }
        if (j == 0 || (iBinaryTypeAnnotationArr = this.typeAnnotations) == null || iBinaryTypeAnnotationArr.length == 0) {
            return EMPTY_ANNOTATION_WALKER;
        }
        return new TypeAnnotationWalker(iBinaryTypeAnnotationArr, j, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toField() {
        return toTarget(19);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toMethodReturn() {
        return toTarget(20);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toReceiver() {
        return toTarget(21);
    }

    protected ITypeAnnotationWalker toTarget(int i) {
        long j = this.matches;
        if (j == 0) {
            return EMPTY_ANNOTATION_WALKER;
        }
        int length = this.typeAnnotations.length;
        long j2 = 1;
        int i2 = 0;
        while (i2 < length) {
            if (this.typeAnnotations[i2].getTargetType() != i) {
                j &= ~j2;
            }
            i2++;
            j2 <<= 1;
        }
        return restrict(j, 0);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toTypeParameter(boolean z, int i) {
        long j = this.matches;
        if (j == 0) {
            return EMPTY_ANNOTATION_WALKER;
        }
        boolean z2 = !z;
        int length = this.typeAnnotations.length;
        long j2 = 1;
        int i2 = 0;
        while (i2 < length) {
            IBinaryTypeAnnotation iBinaryTypeAnnotation = this.typeAnnotations[i2];
            if (iBinaryTypeAnnotation.getTargetType() != z2 || iBinaryTypeAnnotation.getTypeParameterIndex() != i) {
                j &= ~j2;
            }
            i2++;
            j2 <<= 1;
        }
        return restrict(j, 0);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toTypeParameterBounds(boolean z, int i) {
        long j = this.matches;
        if (j == 0) {
            return EMPTY_ANNOTATION_WALKER;
        }
        int length = this.typeAnnotations.length;
        int i2 = z ? 17 : 18;
        long j2 = 1;
        int i3 = 0;
        while (i3 < length) {
            IBinaryTypeAnnotation iBinaryTypeAnnotation = this.typeAnnotations[i3];
            if (iBinaryTypeAnnotation.getTargetType() != i2 || ((short) iBinaryTypeAnnotation.getTypeParameterIndex()) != i) {
                j &= ~j2;
            }
            i3++;
            j2 <<= 1;
        }
        return restrict(j, 0);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toTypeBound(short s) {
        long j = this.matches;
        if (j == 0) {
            return EMPTY_ANNOTATION_WALKER;
        }
        int length = this.typeAnnotations.length;
        long j2 = 1;
        int i = 0;
        while (i < length) {
            if (((short) this.typeAnnotations[i].getBoundIndex()) != s) {
                j &= ~j2;
            }
            i++;
            j2 <<= 1;
        }
        return restrict(j, 0);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toSupertype(short s, char[] cArr) {
        long j = this.matches;
        if (j == 0) {
            return EMPTY_ANNOTATION_WALKER;
        }
        int length = this.typeAnnotations.length;
        long j2 = 1;
        int i = 0;
        while (i < length) {
            IBinaryTypeAnnotation iBinaryTypeAnnotation = this.typeAnnotations[i];
            if (iBinaryTypeAnnotation.getTargetType() != 16 || ((short) iBinaryTypeAnnotation.getSupertypeIndex()) != s) {
                j &= ~j2;
            }
            i++;
            j2 <<= 1;
        }
        return restrict(j, 0);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toMethodParameter(short s) {
        long j = this.matches;
        if (j == 0) {
            return EMPTY_ANNOTATION_WALKER;
        }
        int length = this.typeAnnotations.length;
        long j2 = 1;
        int i = 0;
        while (i < length) {
            IBinaryTypeAnnotation iBinaryTypeAnnotation = this.typeAnnotations[i];
            if (iBinaryTypeAnnotation.getTargetType() != 22 || ((short) iBinaryTypeAnnotation.getMethodFormalParameterIndex()) != s) {
                j &= ~j2;
            }
            i++;
            j2 <<= 1;
        }
        return restrict(j, 0);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toThrows(int i) {
        long j = this.matches;
        if (j == 0) {
            return EMPTY_ANNOTATION_WALKER;
        }
        int length = this.typeAnnotations.length;
        long j2 = 1;
        int i2 = 0;
        while (i2 < length) {
            IBinaryTypeAnnotation iBinaryTypeAnnotation = this.typeAnnotations[i2];
            if (iBinaryTypeAnnotation.getTargetType() != 23 || iBinaryTypeAnnotation.getThrowsTypeIndex() != i) {
                j &= ~j2;
            }
            i2++;
            j2 <<= 1;
        }
        return restrict(j, 0);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toTypeArgument(int i) {
        long j = this.matches;
        if (j == 0) {
            return EMPTY_ANNOTATION_WALKER;
        }
        int length = this.typeAnnotations.length;
        long j2 = 1;
        int i2 = 0;
        while (i2 < length) {
            int[] typePath = this.typeAnnotations[i2].getTypePath();
            int i3 = this.pathPtr;
            if (i3 >= typePath.length || typePath[i3] != 3 || typePath[i3 + 1] != i) {
                j &= ~j2;
            }
            i2++;
            j2 <<= 1;
        }
        return restrict(j, this.pathPtr + 2);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toWildcardBound() {
        long j = this.matches;
        if (j == 0) {
            return EMPTY_ANNOTATION_WALKER;
        }
        int length = this.typeAnnotations.length;
        long j2 = 1;
        int i = 0;
        while (i < length) {
            int[] typePath = this.typeAnnotations[i].getTypePath();
            int i2 = this.pathPtr;
            if (i2 >= typePath.length || typePath[i2] != 2) {
                j &= ~j2;
            }
            i++;
            j2 <<= 1;
        }
        return restrict(j, this.pathPtr + 2);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toNextArrayDimension() {
        return toNextDetail(0);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toNextNestedType() {
        return toNextDetail(1);
    }

    protected ITypeAnnotationWalker toNextDetail(int i) {
        long j = this.matches;
        if (j == 0) {
            return EMPTY_ANNOTATION_WALKER;
        }
        int length = this.typeAnnotations.length;
        long j2 = 1;
        int i2 = 0;
        while (i2 < length) {
            int[] typePath = this.typeAnnotations[i2].getTypePath();
            int i3 = this.pathPtr;
            if (i3 >= typePath.length || typePath[i3] != i) {
                j &= ~j2;
            }
            i2++;
            j2 <<= 1;
        }
        return restrict(j, this.pathPtr + 2);
    }

    @Override // org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public IBinaryAnnotation[] getAnnotationsAtCursor(int i) {
        int length = this.typeAnnotations.length;
        IBinaryAnnotation[] iBinaryAnnotationArr = new IBinaryAnnotation[length];
        long j = 1;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            if ((this.matches & j) != 0) {
                IBinaryTypeAnnotation iBinaryTypeAnnotation = this.typeAnnotations[i2];
                if (iBinaryTypeAnnotation.getTypePath().length <= this.pathPtr) {
                    iBinaryAnnotationArr[i3] = iBinaryTypeAnnotation.getAnnotation();
                    i3++;
                }
            }
            i2++;
            j <<= 1;
        }
        if (i3 == 0) {
            return NO_ANNOTATIONS;
        }
        if (i3 >= length) {
            return iBinaryAnnotationArr;
        }
        IBinaryAnnotation[] iBinaryAnnotationArr2 = new IBinaryAnnotation[i3];
        System.arraycopy(iBinaryAnnotationArr, 0, iBinaryAnnotationArr2, 0, i3);
        return iBinaryAnnotationArr2;
    }
}
