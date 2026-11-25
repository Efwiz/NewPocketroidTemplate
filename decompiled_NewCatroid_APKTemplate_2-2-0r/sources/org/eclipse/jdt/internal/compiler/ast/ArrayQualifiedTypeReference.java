package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import okhttp3.HttpUrl;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilation;

/* JADX INFO: loaded from: classes14.dex */
public class ArrayQualifiedTypeReference extends QualifiedTypeReference {
    private Annotation[][] annotationsOnDimensions;
    int dimensions;
    public int extendedDimensions;

    public ArrayQualifiedTypeReference(char[][] cArr, int i, long[] jArr) {
        super(cArr, jArr);
        this.dimensions = i;
        this.annotationsOnDimensions = null;
    }

    public ArrayQualifiedTypeReference(char[][] cArr, int i, Annotation[][] annotationArr, long[] jArr) {
        this(cArr, i, jArr);
        this.annotationsOnDimensions = annotationArr;
        if (annotationArr != null) {
            this.bits |= 1048576;
        }
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
        int length = this.tokens.length;
        char[][] cArr2 = new char[length][];
        int i4 = length - 1;
        System.arraycopy(this.tokens, 0, cArr2, 0, i4);
        cArr2[i4] = CharOperation.concat(this.tokens[i4], cArr);
        return cArr2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected TypeBinding getTypeBinding(Scope scope) {
        if (this.resolvedType != null) {
            return this.resolvedType;
        }
        if (this.dimensions > 255) {
            scope.problemReporter().tooManyDimensions(this);
        }
        LookupEnvironment lookupEnvironmentEnvironment = scope.environment();
        try {
            try {
                lookupEnvironmentEnvironment.missingClassFileLocation = this;
                TypeBinding typeBinding = super.getTypeBinding(scope);
                if (typeBinding == null) {
                    lookupEnvironmentEnvironment.missingClassFileLocation = null;
                    return null;
                }
                ArrayBinding arrayBindingCreateArrayType = scope.createArrayType(typeBinding, this.dimensions);
                this.resolvedType = arrayBindingCreateArrayType;
                lookupEnvironmentEnvironment.missingClassFileLocation = null;
                return arrayBindingCreateArrayType;
            } catch (AbortCompilation e) {
                e.updateContext(this, scope.referenceCompilationUnit().compilationResult);
                throw e;
            }
        } catch (Throwable th) {
            lookupEnvironmentEnvironment.missingClassFileLocation = null;
            throw th;
        }
        lookupEnvironmentEnvironment.missingClassFileLocation = null;
        throw th;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected TypeBinding internalResolveType(Scope scope, int i) {
        return super.internalResolveType(scope, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
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

    @Override // org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            if (this.annotations != null) {
                int length = this.annotations.length;
                for (int i = 0; i < length; i++) {
                    int length2 = this.annotations[i] == null ? 0 : this.annotations[i].length;
                    for (int i2 = 0; i2 < length2; i2++) {
                        this.annotations[i][i2].traverse(aSTVisitor, blockScope);
                    }
                }
            }
            Annotation[][] annotationArr = this.annotationsOnDimensions;
            if (annotationArr != null) {
                int length3 = annotationArr.length;
                for (int i3 = 0; i3 < length3; i3++) {
                    Annotation[] annotationArr2 = this.annotationsOnDimensions[i3];
                    int length4 = annotationArr2 == null ? 0 : annotationArr2.length;
                    for (int i4 = 0; i4 < length4; i4++) {
                        annotationArr2[i4].traverse(aSTVisitor, blockScope);
                    }
                }
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            if (this.annotations != null) {
                int length = this.annotations.length;
                for (int i = 0; i < length; i++) {
                    int length2 = this.annotations[i] == null ? 0 : this.annotations[i].length;
                    for (int i2 = 0; i2 < length2; i2++) {
                        this.annotations[i][i2].traverse(aSTVisitor, classScope);
                    }
                }
            }
            Annotation[][] annotationArr = this.annotationsOnDimensions;
            if (annotationArr != null) {
                int length3 = annotationArr.length;
                for (int i3 = 0; i3 < length3; i3++) {
                    Annotation[] annotationArr2 = this.annotationsOnDimensions[i3];
                    int length4 = annotationArr2 == null ? 0 : annotationArr2.length;
                    for (int i4 = 0; i4 < length4; i4++) {
                        annotationArr2[i4].traverse(aSTVisitor, classScope);
                    }
                }
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
