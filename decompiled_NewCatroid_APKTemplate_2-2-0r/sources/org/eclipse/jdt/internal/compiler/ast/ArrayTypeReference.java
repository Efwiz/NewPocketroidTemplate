package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import okhttp3.HttpUrl;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ArrayTypeReference extends SingleTypeReference {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$TypeReference$AnnotationPosition;
    private Annotation[][] annotationsOnDimensions;
    public int dimensions;
    public int extendedDimensions;
    public int originalSourceEnd;

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$TypeReference$AnnotationPosition() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$TypeReference$AnnotationPosition;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[TypeReference.AnnotationPosition.valuesCustom().length];
        try {
            iArr2[TypeReference.AnnotationPosition.ANY.ordinal()] = 3;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[TypeReference.AnnotationPosition.LEAF_TYPE.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[TypeReference.AnnotationPosition.MAIN_TYPE.ordinal()] = 1;
        } catch (NoSuchFieldError unused3) {
        }
        $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$TypeReference$AnnotationPosition = iArr2;
        return iArr2;
    }

    public ArrayTypeReference(char[] cArr, int i, long j) {
        super(cArr, j);
        this.originalSourceEnd = this.sourceEnd;
        this.dimensions = i;
        this.annotationsOnDimensions = null;
    }

    public ArrayTypeReference(char[] cArr, int i, Annotation[][] annotationArr, long j) {
        this(cArr, i, j);
        if (annotationArr != null) {
            this.bits |= 1048576;
        }
        this.annotationsOnDimensions = annotationArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public int dimensions() {
        return this.dimensions;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public int extraDimensions() {
        return this.extendedDimensions;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public Annotation[][] getAnnotationsOnDimensions(boolean z) {
        Annotation[][] annotationArr;
        int i;
        int i2;
        if (z || (annotationArr = this.annotationsOnDimensions) == null || annotationArr.length == 0 || (i = this.extendedDimensions) == 0 || i == (i2 = this.dimensions)) {
            return this.annotationsOnDimensions;
        }
        Annotation[][] annotationArr2 = new Annotation[i2][];
        int i3 = i2 - i;
        System.arraycopy(annotationArr, i3, annotationArr2, 0, i);
        System.arraycopy(this.annotationsOnDimensions, 0, annotationArr2, this.extendedDimensions, i3);
        return annotationArr2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public void setAnnotationsOnDimensions(Annotation[][] annotationArr) {
        this.annotationsOnDimensions = annotationArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[][] getParameterizedTypeName() {
        int i = this.dimensions;
        char[] cArr = new char[i * 2];
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 * 2;
            cArr[i3] = '[';
            cArr[i3 + 1] = ']';
        }
        return new char[][]{CharOperation.concat(this.token, cArr)};
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected TypeBinding getTypeBinding(Scope scope) {
        if (this.resolvedType != null) {
            return this.resolvedType;
        }
        if (this.dimensions > 255) {
            scope.problemReporter().tooManyDimensions(this);
        }
        return scope.createArrayType(scope.getType(this.token), this.dimensions);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        int i2;
        super.printExpression(i, stringBuffer);
        int i3 = 0;
        if ((this.bits & 16384) != 0) {
            while (true) {
                i2 = this.dimensions;
                if (i3 >= i2 - 1) {
                    break;
                }
                Annotation[][] annotationArr = this.annotationsOnDimensions;
                if (annotationArr != null && annotationArr[i3] != null) {
                    stringBuffer.append(StringUtil.SPACE);
                    printAnnotations(this.annotationsOnDimensions[i3], stringBuffer);
                    stringBuffer.append(StringUtil.SPACE);
                }
                stringBuffer.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                i3++;
            }
            Annotation[][] annotationArr2 = this.annotationsOnDimensions;
            if (annotationArr2 != null && annotationArr2[i2 - 1] != null) {
                stringBuffer.append(StringUtil.SPACE);
                printAnnotations(this.annotationsOnDimensions[this.dimensions - 1], stringBuffer);
                stringBuffer.append(StringUtil.SPACE);
            }
            stringBuffer.append("...");
        } else {
            while (i3 < this.dimensions) {
                Annotation[][] annotationArr3 = this.annotationsOnDimensions;
                if (annotationArr3 != null && annotationArr3[i3] != null) {
                    stringBuffer.append(" ");
                    printAnnotations(this.annotationsOnDimensions[i3], stringBuffer);
                    stringBuffer.append(" ");
                }
                stringBuffer.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                i3++;
            }
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            if (this.annotations != null) {
                Annotation[] annotationArr = this.annotations[0];
                int length = annotationArr == null ? 0 : annotationArr.length;
                for (int i = 0; i < length; i++) {
                    annotationArr[i].traverse(aSTVisitor, blockScope);
                }
            }
            Annotation[][] annotationArr2 = this.annotationsOnDimensions;
            if (annotationArr2 != null) {
                int length2 = annotationArr2.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    Annotation[] annotationArr3 = this.annotationsOnDimensions[i2];
                    if (annotationArr3 != null) {
                        for (Annotation annotation : annotationArr3) {
                            annotation.traverse(aSTVisitor, blockScope);
                        }
                    }
                }
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            if (this.annotations != null) {
                Annotation[] annotationArr = this.annotations[0];
                int length = annotationArr == null ? 0 : annotationArr.length;
                for (int i = 0; i < length; i++) {
                    annotationArr[i].traverse(aSTVisitor, classScope);
                }
            }
            Annotation[][] annotationArr2 = this.annotationsOnDimensions;
            if (annotationArr2 != null) {
                int length2 = annotationArr2.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    Annotation[] annotationArr3 = this.annotationsOnDimensions[i2];
                    if (annotationArr3 != null) {
                        for (Annotation annotation : annotationArr3) {
                            annotation.traverse(aSTVisitor, classScope);
                        }
                    }
                }
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected TypeBinding internalResolveType(Scope scope, int i) {
        return super.internalResolveType(scope, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public boolean hasNullTypeAnnotation(TypeReference.AnnotationPosition annotationPosition) {
        int i = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$TypeReference$AnnotationPosition()[annotationPosition.ordinal()];
        if (i == 1) {
            Annotation[][] annotationArr = this.annotationsOnDimensions;
            if (annotationArr != null && annotationArr.length > 0) {
                return containsNullAnnotation(annotationArr[0]);
            }
        } else {
            if (i == 2) {
                return super.hasNullTypeAnnotation(annotationPosition);
            }
            if (i == 3) {
                if (super.hasNullTypeAnnotation(annotationPosition)) {
                    return true;
                }
                if ((this.resolvedType == null || this.resolvedType.hasNullTypeAnnotations()) && this.annotationsOnDimensions != null) {
                    int i2 = 0;
                    while (true) {
                        Annotation[][] annotationArr2 = this.annotationsOnDimensions;
                        if (i2 >= annotationArr2.length) {
                            break;
                        }
                        if (containsNullAnnotation(annotationArr2[i2])) {
                            return true;
                        }
                        i2++;
                    }
                }
            }
        }
        return false;
    }
}
