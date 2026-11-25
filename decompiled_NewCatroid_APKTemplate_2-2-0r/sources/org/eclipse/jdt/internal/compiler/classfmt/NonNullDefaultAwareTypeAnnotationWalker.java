package org.eclipse.jdt.internal.compiler.classfmt;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;
import org.eclipse.jdt.internal.compiler.env.IBinaryElementValuePair;
import org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation;
import org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;

/* JADX INFO: loaded from: classes14.dex */
public class NonNullDefaultAwareTypeAnnotationWalker extends TypeAnnotationWalker {
    private boolean atDefaultLocation;
    private boolean atTypeBound;
    private int defaultNullness;
    LookupEnvironment environment;
    private boolean isEmpty;
    private boolean nextIsDefaultLocation;
    private boolean nextIsTypeBound;
    IBinaryAnnotation nonNullAnnotation;

    public NonNullDefaultAwareTypeAnnotationWalker(IBinaryTypeAnnotation[] iBinaryTypeAnnotationArr, int i, LookupEnvironment lookupEnvironment) {
        super(iBinaryTypeAnnotationArr);
        this.nonNullAnnotation = getNonNullAnnotation(lookupEnvironment);
        this.defaultNullness = i;
        this.environment = lookupEnvironment;
    }

    public NonNullDefaultAwareTypeAnnotationWalker(int i, LookupEnvironment lookupEnvironment) {
        this(i, getNonNullAnnotation(lookupEnvironment), false, false, lookupEnvironment);
    }

    NonNullDefaultAwareTypeAnnotationWalker(IBinaryTypeAnnotation[] iBinaryTypeAnnotationArr, long j, int i, int i2, IBinaryAnnotation iBinaryAnnotation, boolean z, boolean z2, LookupEnvironment lookupEnvironment) {
        super(iBinaryTypeAnnotationArr, j, i);
        this.defaultNullness = i2;
        this.nonNullAnnotation = iBinaryAnnotation;
        this.atDefaultLocation = z;
        this.atTypeBound = z2;
        this.environment = lookupEnvironment;
    }

    NonNullDefaultAwareTypeAnnotationWalker(int i, IBinaryAnnotation iBinaryAnnotation, boolean z, boolean z2, LookupEnvironment lookupEnvironment) {
        super(null, 0L, 0);
        this.nonNullAnnotation = iBinaryAnnotation;
        this.defaultNullness = i;
        this.atDefaultLocation = z;
        this.atTypeBound = z2;
        this.isEmpty = true;
        this.environment = lookupEnvironment;
    }

    private static IBinaryAnnotation getNonNullAnnotation(LookupEnvironment lookupEnvironment) {
        final char[] cArrConcat = CharOperation.concat('L', CharOperation.concatWith(lookupEnvironment.getNonNullAnnotationName(), '/'), ';');
        return new IBinaryAnnotation() { // from class: org.eclipse.jdt.internal.compiler.classfmt.NonNullDefaultAwareTypeAnnotationWalker.1
            @Override // org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation
            public IBinaryElementValuePair[] getElementValuePairs() {
                return null;
            }

            @Override // org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation
            public char[] getTypeName() {
                return cArrConcat;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jdt.internal.compiler.classfmt.TypeAnnotationWalker
    public TypeAnnotationWalker restrict(long j, int i) {
        try {
            if (this.matches == j && this.pathPtr == i && this.atDefaultLocation == this.nextIsDefaultLocation && this.atTypeBound == this.nextIsTypeBound) {
                return this;
            }
            if (j != 0 && this.typeAnnotations != null && this.typeAnnotations.length != 0) {
                return new NonNullDefaultAwareTypeAnnotationWalker(this.typeAnnotations, j, i, this.defaultNullness, this.nonNullAnnotation, this.nextIsDefaultLocation, this.nextIsTypeBound, this.environment);
            }
            return new NonNullDefaultAwareTypeAnnotationWalker(this.defaultNullness, this.nonNullAnnotation, this.nextIsDefaultLocation, this.nextIsTypeBound, this.environment);
        } finally {
            this.nextIsDefaultLocation = false;
            this.nextIsTypeBound = false;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.TypeAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toSupertype(short s, char[] cArr) {
        return this.isEmpty ? restrict(this.matches, this.pathPtr) : super.toSupertype(s, cArr);
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.TypeAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toMethodParameter(short s) {
        return this.isEmpty ? restrict(this.matches, this.pathPtr) : super.toMethodParameter(s);
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.TypeAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toField() {
        return this.isEmpty ? restrict(this.matches, this.pathPtr) : super.toField();
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.TypeAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toMethodReturn() {
        return this.isEmpty ? restrict(this.matches, this.pathPtr) : super.toMethodReturn();
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.TypeAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toTypeBound(short s) {
        this.nextIsDefaultLocation = (this.defaultNullness & 256) != 0;
        this.nextIsTypeBound = true;
        return this.isEmpty ? restrict(this.matches, this.pathPtr) : super.toTypeBound(s);
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.TypeAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toWildcardBound() {
        this.nextIsDefaultLocation = (this.defaultNullness & 256) != 0;
        this.nextIsTypeBound = true;
        return this.isEmpty ? restrict(this.matches, this.pathPtr) : super.toWildcardBound();
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.TypeAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toTypeParameterBounds(boolean z, int i) {
        this.nextIsDefaultLocation = (this.defaultNullness & 256) != 0;
        this.nextIsTypeBound = true;
        return this.isEmpty ? restrict(this.matches, this.pathPtr) : super.toTypeParameterBounds(z, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.TypeAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toTypeArgument(int i) {
        this.nextIsDefaultLocation = (this.defaultNullness & 64) != 0;
        this.nextIsTypeBound = false;
        return this.isEmpty ? restrict(this.matches, this.pathPtr) : super.toTypeArgument(i);
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.TypeAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public ITypeAnnotationWalker toTypeParameter(boolean z, int i) {
        this.nextIsDefaultLocation = (this.defaultNullness & 128) != 0;
        this.nextIsTypeBound = false;
        return this.isEmpty ? restrict(this.matches, this.pathPtr) : super.toTypeParameter(z, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.TypeAnnotationWalker
    protected ITypeAnnotationWalker toNextDetail(int i) {
        return this.isEmpty ? restrict(this.matches, this.pathPtr) : super.toNextDetail(i);
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.TypeAnnotationWalker, org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker
    public IBinaryAnnotation[] getAnnotationsAtCursor(int i) {
        IBinaryAnnotation[] annotationsAtCursor = this.isEmpty ? NO_ANNOTATIONS : super.getAnnotationsAtCursor(i);
        if (!this.atDefaultLocation || i == -1) {
            return annotationsAtCursor;
        }
        if (this.atTypeBound && i == 1) {
            return annotationsAtCursor;
        }
        if (annotationsAtCursor == null || annotationsAtCursor.length == 0) {
            return new IBinaryAnnotation[]{this.nonNullAnnotation};
        }
        if (this.environment.containsNullTypeAnnotation(annotationsAtCursor)) {
            return annotationsAtCursor;
        }
        int length = annotationsAtCursor.length;
        IBinaryAnnotation[] iBinaryAnnotationArr = new IBinaryAnnotation[length + 1];
        System.arraycopy(annotationsAtCursor, 0, iBinaryAnnotationArr, 0, length);
        iBinaryAnnotationArr[length] = this.nonNullAnnotation;
        return iBinaryAnnotationArr;
    }
}
