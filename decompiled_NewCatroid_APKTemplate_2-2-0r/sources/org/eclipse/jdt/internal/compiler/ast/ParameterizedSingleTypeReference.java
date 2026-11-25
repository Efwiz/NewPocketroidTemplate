package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import okhttp3.HttpUrl;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ParameterizedSingleTypeReference extends ArrayTypeReference {
    public static final TypeBinding[] DIAMOND_TYPE_ARGUMENTS = new TypeBinding[0];
    public TypeReference[] typeArguments;

    @Override // org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference, org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected TypeBinding getTypeBinding(Scope scope) {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public boolean isParameterizedTypeReference() {
        return true;
    }

    public ParameterizedSingleTypeReference(char[] cArr, TypeReference[] typeReferenceArr, int i, long j) {
        super(cArr, i, j);
        this.originalSourceEnd = this.sourceEnd;
        this.typeArguments = typeReferenceArr;
        for (TypeReference typeReference : typeReferenceArr) {
            if ((typeReference.bits & 1048576) != 0) {
                this.bits |= 1048576;
                return;
            }
        }
    }

    public ParameterizedSingleTypeReference(char[] cArr, TypeReference[] typeReferenceArr, int i, Annotation[][] annotationArr, long j) {
        this(cArr, typeReferenceArr, i, j);
        setAnnotationsOnDimensions(annotationArr);
        if (annotationArr != null) {
            this.bits |= 1048576;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public void checkBounds(Scope scope) {
        if (this.resolvedType != null && (this.resolvedType.leafComponentType() instanceof ParameterizedTypeBinding)) {
            ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding) this.resolvedType.leafComponentType();
            if (parameterizedTypeBinding.arguments != null) {
                parameterizedTypeBinding.boundCheck(scope, this.typeArguments);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeReference augmentTypeWithAdditionalDimensions(int i, Annotation[][] annotationArr, boolean z) {
        ParameterizedSingleTypeReference parameterizedSingleTypeReference = new ParameterizedSingleTypeReference(this.token, this.typeArguments, dimensions() + i, getMergedAnnotationsOnDimensions(i, annotationArr), ((long) this.sourceEnd) + (((long) this.sourceStart) << 32));
        parameterizedSingleTypeReference.annotations = this.annotations;
        parameterizedSingleTypeReference.bits |= this.bits & 1048576;
        if (!z) {
            parameterizedSingleTypeReference.extendedDimensions = i;
        }
        return parameterizedSingleTypeReference;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[][] getParameterizedTypeName() {
        StringBuffer stringBuffer = new StringBuffer(5);
        stringBuffer.append(this.token).append('<');
        int length = this.typeArguments.length;
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                stringBuffer.append(',');
            }
            stringBuffer.append(CharOperation.concatWith(this.typeArguments[i].getParameterizedTypeName(), '.'));
        }
        stringBuffer.append('>');
        int length2 = stringBuffer.length();
        char[] cArrConcat = new char[length2];
        stringBuffer.getChars(0, length2, cArrConcat, 0);
        int i2 = this.dimensions;
        if (i2 > 0) {
            char[] cArr = new char[i2 * 2];
            for (int i3 = 0; i3 < i2; i3++) {
                int i4 = i3 * 2;
                cArr[i4] = '[';
                cArr[i4 + 1] = ']';
            }
            cArrConcat = CharOperation.concat(cArrConcat, cArr);
        }
        return new char[][]{cArrConcat};
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeReference[][] getTypeArguments() {
        return new TypeReference[][]{this.typeArguments};
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference
    public boolean hasNullTypeAnnotation(TypeReference.AnnotationPosition annotationPosition) {
        if (super.hasNullTypeAnnotation(annotationPosition)) {
            return true;
        }
        if (annotationPosition == TypeReference.AnnotationPosition.ANY && ((this.resolvedType == null || this.resolvedType.hasNullTypeAnnotations()) && this.typeArguments != null)) {
            int i = 0;
            while (true) {
                TypeReference[] typeReferenceArr = this.typeArguments;
                if (i >= typeReferenceArr.length) {
                    break;
                }
                if (typeReferenceArr[i].hasNullTypeAnnotation(annotationPosition)) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    private TypeBinding internalResolveType(Scope scope, ReferenceBinding referenceBinding, boolean z, int i) {
        this.constant = Constant.NotAConstant;
        if ((this.bits & 262144) != 0 && this.resolvedType != null) {
            if (this.resolvedType.isValidBinding()) {
                return this.resolvedType;
            }
            int iProblemId = this.resolvedType.problemId();
            if (iProblemId == 1 || iProblemId == 2 || iProblemId == 5) {
                return this.resolvedType.closestMatch();
            }
            return null;
        }
        this.bits |= 262144;
        TypeBinding typeBindingInternalResolveLeafType = internalResolveLeafType(scope, referenceBinding, z);
        if (typeBindingInternalResolveLeafType == null) {
            this.resolvedType = createArrayType(scope, this.resolvedType);
            resolveAnnotations(scope, 0);
            return null;
        }
        TypeBinding typeBindingCreateArrayType = createArrayType(scope, typeBindingInternalResolveLeafType);
        if (!this.resolvedType.isValidBinding() && this.resolvedType.dimensions() == typeBindingCreateArrayType.dimensions()) {
            resolveAnnotations(scope, 0);
            return typeBindingCreateArrayType;
        }
        this.resolvedType = typeBindingCreateArrayType;
        resolveAnnotations(scope, i);
        return this.resolvedType;
    }

    private TypeBinding internalResolveLeafType(Scope scope, ReferenceBinding referenceBinding, boolean z) {
        ReferenceBinding referenceBindingEnclosingType;
        ReferenceBinding referenceBindingCreateParameterizedType;
        TypeReference typeReference;
        ReferenceBinding referenceBindingEnclosingType2;
        TypeBinding typeBindingResolveTypeArgument;
        int i = 1;
        int i2 = 0;
        TypeBinding typeBinding = null;
        if (referenceBinding == null) {
            this.resolvedType = scope.getType(this.token);
            if (this.resolvedType.isValidBinding()) {
                referenceBindingCreateParameterizedType = (ReferenceBinding) this.resolvedType;
            } else {
                reportInvalidType(scope);
                int iProblemId = this.resolvedType.problemId();
                if (iProblemId == 1 || iProblemId == 2 || iProblemId == 5) {
                    TypeBinding typeBindingClosestMatch = this.resolvedType.closestMatch();
                    if (typeBindingClosestMatch instanceof ReferenceBinding) {
                        referenceBindingCreateParameterizedType = (ReferenceBinding) typeBindingClosestMatch;
                    }
                }
                boolean z2 = scope.kind == 3;
                int length = this.typeArguments.length;
                while (i2 < length) {
                    TypeReference typeReference2 = this.typeArguments[i2];
                    if (z2) {
                        typeReference2.resolveType((ClassScope) scope);
                    } else {
                        typeReference2.resolveType((BlockScope) scope, z);
                    }
                    i2++;
                }
                return null;
            }
            referenceBindingEnclosingType = referenceBindingCreateParameterizedType.enclosingType();
            if (referenceBindingEnclosingType != null) {
                if (referenceBindingCreateParameterizedType.isStatic()) {
                    referenceBindingEnclosingType = (ReferenceBinding) scope.environment().convertToRawType(referenceBindingEnclosingType, false);
                } else {
                    referenceBindingEnclosingType = scope.environment().convertToParameterizedType(referenceBindingEnclosingType);
                }
                referenceBindingCreateParameterizedType = scope.environment().createParameterizedType((ReferenceBinding) referenceBindingCreateParameterizedType.erasure(), null, referenceBindingEnclosingType);
            }
        } else {
            ReferenceBinding memberType = scope.getMemberType(this.token, referenceBinding);
            this.resolvedType = memberType;
            if (!this.resolvedType.isValidBinding()) {
                scope.problemReporter().invalidEnclosingType(this, memberType, referenceBinding);
                return null;
            }
            if (isTypeUseDeprecated(memberType, scope)) {
                scope.problemReporter().deprecatedType(memberType, this);
            }
            ReferenceBinding referenceBindingEnclosingType3 = memberType.enclosingType();
            if (referenceBindingEnclosingType3 == null || !TypeBinding.notEquals(referenceBindingEnclosingType3.erasure(), referenceBinding.erasure())) {
                referenceBindingEnclosingType = referenceBinding;
                referenceBindingCreateParameterizedType = memberType;
            } else {
                referenceBindingCreateParameterizedType = memberType;
                referenceBindingEnclosingType = referenceBindingEnclosingType3;
            }
        }
        boolean z3 = scope.kind == 3;
        if (z3) {
            ClassScope classScope = (ClassScope) scope;
            typeReference = classScope.superTypeReference;
            classScope.superTypeReference = null;
        } else {
            typeReference = null;
        }
        boolean z4 = (this.bits & 524288) != 0;
        int length2 = this.typeArguments.length;
        TypeBinding[] typeBindingArr = new TypeBinding[length2];
        ReferenceBinding referenceBinding2 = (ReferenceBinding) referenceBindingCreateParameterizedType.original();
        int i3 = 0;
        boolean z5 = false;
        while (i3 < length2) {
            TypeReference typeReference3 = this.typeArguments[i3];
            if (z3) {
                typeBindingResolveTypeArgument = typeReference3.resolveTypeArgument((ClassScope) scope, referenceBinding2, i3);
            } else {
                typeBindingResolveTypeArgument = typeReference3.resolveTypeArgument((BlockScope) scope, referenceBinding2, i3);
            }
            this.bits = (typeReference3.bits & 1048576) | this.bits;
            if (typeBindingResolveTypeArgument == null) {
                z5 = true;
            } else {
                typeBindingArr[i3] = typeBindingResolveTypeArgument;
            }
            i3++;
            i = 1;
            i2 = 0;
            typeBinding = null;
        }
        if (z5) {
            return typeBinding;
        }
        if (z3) {
            ClassScope classScope2 = (ClassScope) scope;
            classScope2.superTypeReference = typeReference;
            if (classScope2.detectHierarchyCycle(referenceBinding2, this)) {
                return typeBinding;
            }
        }
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = referenceBinding2.typeVariables();
        if (typeVariableBindingArrTypeVariables == Binding.NO_TYPE_VARIABLES) {
            if (scope.compilerOptions().originalSourceLevel < ClassFileConstants.JDK1_5) {
                i = i2;
            }
            if ((referenceBinding2.tagBits & 128) == 0 && i != 0) {
                this.resolvedType = referenceBindingCreateParameterizedType;
                scope.problemReporter().nonGenericTypeCannotBeParameterized(i2, this, referenceBindingCreateParameterizedType, typeBindingArr);
                return typeBinding;
            }
            if (i == 0) {
                if (!this.resolvedType.isValidBinding()) {
                    return referenceBindingCreateParameterizedType;
                }
                this.resolvedType = referenceBindingCreateParameterizedType;
                return referenceBindingCreateParameterizedType;
            }
        } else if (length2 != typeVariableBindingArrTypeVariables.length) {
            if (!z4) {
                scope.problemReporter().incorrectArityForParameterizedType(this, referenceBindingCreateParameterizedType, typeBindingArr);
                return typeBinding;
            }
        } else if (!referenceBindingCreateParameterizedType.isStatic() && (referenceBindingEnclosingType2 = referenceBindingCreateParameterizedType.enclosingType()) != null && referenceBindingEnclosingType2.isRawType()) {
            scope.problemReporter().rawMemberTypeCannotBeParameterized(this, scope.environment().createRawType(referenceBinding2, referenceBindingEnclosingType2), typeBindingArr);
            return typeBinding;
        }
        ParameterizedTypeBinding parameterizedTypeBindingCreateParameterizedType = scope.environment().createParameterizedType(referenceBinding2, typeBindingArr, referenceBindingEnclosingType);
        if (z4) {
            parameterizedTypeBindingCreateParameterizedType.arguments = DIAMOND_TYPE_ARGUMENTS;
        } else if (z) {
            parameterizedTypeBindingCreateParameterizedType.boundCheck(scope, this.typeArguments);
        } else {
            scope.deferBoundCheck(this);
        }
        if (isTypeUseDeprecated(parameterizedTypeBindingCreateParameterizedType, scope)) {
            reportDeprecatedType(parameterizedTypeBindingCreateParameterizedType, scope);
        }
        checkIllegalNullAnnotations(scope, this.typeArguments);
        if (!this.resolvedType.isValidBinding()) {
            return parameterizedTypeBindingCreateParameterizedType;
        }
        this.resolvedType = parameterizedTypeBindingCreateParameterizedType;
        return parameterizedTypeBindingCreateParameterizedType;
    }

    private TypeBinding createArrayType(Scope scope, TypeBinding typeBinding) {
        if (this.dimensions <= 0) {
            return typeBinding;
        }
        if (this.dimensions > 255) {
            scope.problemReporter().tooManyDimensions(this);
        }
        return scope.createArrayType(typeBinding, this.dimensions);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference, org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        int i2 = 0;
        if (this.annotations != null && this.annotations[0] != null) {
            printAnnotations(this.annotations[0], stringBuffer);
            stringBuffer.append(StringUtil.SPACE);
        }
        stringBuffer.append(this.token);
        stringBuffer.append("<");
        int length = this.typeArguments.length;
        if (length > 0) {
            int i3 = length - 1;
            for (int i4 = 0; i4 < i3; i4++) {
                this.typeArguments[i4].print(0, stringBuffer);
                stringBuffer.append(", ");
            }
            this.typeArguments[i3].print(0, stringBuffer);
        }
        stringBuffer.append(">");
        Annotation[][] annotationsOnDimensions = getAnnotationsOnDimensions();
        if ((this.bits & 16384) != 0) {
            while (i2 < this.dimensions - 1) {
                if (annotationsOnDimensions != null && annotationsOnDimensions[i2] != null) {
                    stringBuffer.append(" ");
                    printAnnotations(annotationsOnDimensions[i2], stringBuffer);
                    stringBuffer.append(" ");
                }
                stringBuffer.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                i2++;
            }
            if (annotationsOnDimensions != null && annotationsOnDimensions[this.dimensions - 1] != null) {
                stringBuffer.append(" ");
                printAnnotations(annotationsOnDimensions[this.dimensions - 1], stringBuffer);
                stringBuffer.append(" ");
            }
            stringBuffer.append("...");
        } else {
            while (i2 < this.dimensions) {
                if (annotationsOnDimensions != null && annotationsOnDimensions[i2] != null) {
                    stringBuffer.append(" ");
                    printAnnotations(annotationsOnDimensions[i2], stringBuffer);
                    stringBuffer.append(" ");
                }
                stringBuffer.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                i2++;
            }
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeBinding resolveType(BlockScope blockScope, boolean z, int i) {
        return internalResolveType(blockScope, null, z, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeBinding resolveType(ClassScope classScope, int i) {
        return internalResolveType(classScope, null, false, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.SingleTypeReference
    public TypeBinding resolveTypeEnclosing(BlockScope blockScope, ReferenceBinding referenceBinding) {
        return internalResolveType(blockScope, referenceBinding, true, 0);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference, org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            if (this.annotations != null) {
                Annotation[] annotationArr = this.annotations[0];
                int length = annotationArr == null ? 0 : annotationArr.length;
                for (int i = 0; i < length; i++) {
                    annotationArr[i].traverse(aSTVisitor, blockScope);
                }
            }
            Annotation[][] annotationsOnDimensions = getAnnotationsOnDimensions(true);
            if (annotationsOnDimensions != null) {
                for (Annotation[] annotationArr2 : annotationsOnDimensions) {
                    if (annotationArr2 != null) {
                        for (Annotation annotation : annotationArr2) {
                            annotation.traverse(aSTVisitor, blockScope);
                        }
                    }
                }
            }
            int length2 = this.typeArguments.length;
            for (int i2 = 0; i2 < length2; i2++) {
                this.typeArguments[i2].traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference, org.eclipse.jdt.internal.compiler.ast.SingleTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            if (this.annotations != null) {
                Annotation[] annotationArr = this.annotations[0];
                int length = annotationArr == null ? 0 : annotationArr.length;
                for (int i = 0; i < length; i++) {
                    annotationArr[i].traverse(aSTVisitor, classScope);
                }
            }
            Annotation[][] annotationsOnDimensions = getAnnotationsOnDimensions(true);
            if (annotationsOnDimensions != null) {
                for (Annotation[] annotationArr2 : annotationsOnDimensions) {
                    for (Annotation annotation : annotationArr2) {
                        annotation.traverse(aSTVisitor, classScope);
                    }
                }
            }
            int length2 = this.typeArguments.length;
            for (int i2 = 0; i2 < length2; i2++) {
                this.typeArguments[i2].traverse(aSTVisitor, classScope);
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
