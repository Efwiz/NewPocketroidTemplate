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
import org.eclipse.jdt.internal.compiler.lookup.PackageBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ParameterizedQualifiedTypeReference extends ArrayQualifiedTypeReference {
    public TypeReference[][] typeArguments;
    ReferenceBinding[] typesPerToken;

    @Override // org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected TypeBinding getTypeBinding(Scope scope) {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public boolean isParameterizedTypeReference() {
        return true;
    }

    public ParameterizedQualifiedTypeReference(char[][] cArr, TypeReference[][] typeReferenceArr, int i, long[] jArr) {
        super(cArr, i, jArr);
        this.typeArguments = typeReferenceArr;
        for (TypeReference[] typeReferenceArr2 : typeReferenceArr) {
            if (typeReferenceArr2 != null) {
                for (TypeReference typeReference : typeReferenceArr2) {
                    if ((typeReference.bits & 1048576) != 0) {
                        this.bits |= 1048576;
                        return;
                    }
                }
            }
        }
    }

    public ParameterizedQualifiedTypeReference(char[][] cArr, TypeReference[][] typeReferenceArr, int i, Annotation[][] annotationArr, long[] jArr) {
        this(cArr, typeReferenceArr, i, jArr);
        setAnnotationsOnDimensions(annotationArr);
        if (annotationArr != null) {
            this.bits |= 1048576;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public void checkBounds(Scope scope) {
        if (this.resolvedType == null || !this.resolvedType.isValidBinding()) {
            return;
        }
        checkBounds((ReferenceBinding) this.resolvedType.leafComponentType(), scope, this.typeArguments.length - 1);
    }

    public void checkBounds(ReferenceBinding referenceBinding, Scope scope, int i) {
        int i2;
        ReferenceBinding referenceBinding2;
        if (i > 0 && (referenceBinding2 = this.typesPerToken[(i2 = i - 1)]) != null) {
            checkBounds(referenceBinding2, scope, i2);
        }
        if (referenceBinding.isParameterizedTypeWithActualArguments()) {
            ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding) referenceBinding;
            if (parameterizedTypeBinding.genericType().typeVariables() != null) {
                parameterizedTypeBinding.boundCheck(scope, this.typeArguments[i]);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeReference augmentTypeWithAdditionalDimensions(int i, Annotation[][] annotationArr, boolean z) {
        ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference = new ParameterizedQualifiedTypeReference(this.tokens, this.typeArguments, dimensions() + i, getMergedAnnotationsOnDimensions(i, annotationArr), this.sourcePositions);
        parameterizedQualifiedTypeReference.annotations = this.annotations;
        parameterizedQualifiedTypeReference.bits |= this.bits & 1048576;
        if (!z) {
            parameterizedQualifiedTypeReference.extendedDimensions = i;
        }
        return parameterizedQualifiedTypeReference;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public boolean hasNullTypeAnnotation(TypeReference.AnnotationPosition annotationPosition) {
        if (super.hasNullTypeAnnotation(annotationPosition)) {
            return true;
        }
        if (annotationPosition == TypeReference.AnnotationPosition.ANY && ((this.resolvedType == null || this.resolvedType.hasNullTypeAnnotations()) && this.typeArguments != null)) {
            int i = 0;
            while (true) {
                TypeReference[][] typeReferenceArr = this.typeArguments;
                if (i >= typeReferenceArr.length) {
                    break;
                }
                TypeReference[] typeReferenceArr2 = typeReferenceArr[i];
                if (typeReferenceArr2 != null) {
                    for (TypeReference typeReference : typeReferenceArr2) {
                        if (typeReference.hasNullTypeAnnotation(annotationPosition)) {
                            return true;
                        }
                    }
                }
                i++;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[][] getParameterizedTypeName() {
        int length = this.tokens.length;
        char[][] cArr = new char[length][];
        for (int i = 0; i < length; i++) {
            TypeReference[] typeReferenceArr = this.typeArguments[i];
            if (typeReferenceArr == null) {
                cArr[i] = this.tokens[i];
            } else {
                StringBuffer stringBuffer = new StringBuffer(5);
                stringBuffer.append(this.tokens[i]);
                stringBuffer.append('<');
                int length2 = typeReferenceArr.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    if (i2 > 0) {
                        stringBuffer.append(',');
                    }
                    stringBuffer.append(CharOperation.concatWith(typeReferenceArr[i2].getParameterizedTypeName(), '.'));
                }
                stringBuffer.append('>');
                int length3 = stringBuffer.length();
                char[] cArr2 = new char[length3];
                cArr[i] = cArr2;
                stringBuffer.getChars(0, length3, cArr2, 0);
            }
        }
        int i3 = this.dimensions;
        if (i3 > 0) {
            char[] cArr3 = new char[i3 * 2];
            for (int i4 = 0; i4 < i3; i4++) {
                int i5 = i4 * 2;
                cArr3[i5] = '[';
                cArr3[i5 + 1] = ']';
            }
            int i6 = length - 1;
            cArr[i6] = CharOperation.concat(cArr[i6], cArr3);
        }
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeReference[][] getTypeArguments() {
        return this.typeArguments;
    }

    private TypeBinding internalResolveType(Scope scope, boolean z, int i) {
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
        TypeBinding typeBindingInternalResolveLeafType = internalResolveLeafType(scope, z);
        createArrayType(scope);
        resolveAnnotations(scope, i);
        TypeReference[][] typeReferenceArr = this.typeArguments;
        if (typeReferenceArr != null) {
            checkIllegalNullAnnotations(scope, typeReferenceArr[typeReferenceArr.length - 1]);
        }
        return typeBindingInternalResolveLeafType == null ? typeBindingInternalResolveLeafType : this.resolvedType;
    }

    /* JADX WARN: Code duplicated, block: B:165:0x0261  */
    /* JADX WARN: Code duplicated, block: B:180:0x0264 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v4, types: [org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment] */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v2, types: [org.eclipse.jdt.internal.compiler.ast.TypeReference] */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1, types: [int] */
    /* JADX WARN: Type inference failed for: r5v25 */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding[]] */
    /* JADX WARN: Type inference failed for: r7v2 */
    private TypeBinding internalResolveLeafType(Scope scope, boolean z) {
        PackageBinding packageBinding;
        Object obj;
        ?? r15;
        ReferenceBinding referenceBindingEnclosingType;
        TypeBinding typeBindingResolveTypeArgument;
        boolean z2 = z;
        boolean z3 = false;
        boolean z4 = scope.kind == 3;
        Binding binding = scope.getPackage(this.tokens);
        ?? r7 = 0;
        if (binding != null && !binding.isValidBinding()) {
            this.resolvedType = (ReferenceBinding) binding;
            reportInvalidType(scope);
            int length = this.tokens.length;
            for (int i = 0; i < length; i++) {
                TypeReference[] typeReferenceArr = this.typeArguments[i];
                if (typeReferenceArr != null) {
                    for (TypeReference typeReference : typeReferenceArr) {
                        if (z4) {
                            typeReference.resolveType((ClassScope) scope);
                        } else {
                            typeReference.resolveType((BlockScope) scope, z2);
                        }
                    }
                }
            }
            return null;
        }
        PackageBinding packageBinding2 = binding == null ? null : (PackageBinding) binding;
        rejectAnnotationsOnPackageQualifiers(scope, packageBinding2);
        int length2 = this.tokens.length;
        this.typesPerToken = new ReferenceBinding[length2];
        int length3 = packageBinding2 == null ? 0 : packageBinding2.compoundName.length;
        ReferenceBinding referenceBindingCreateRawType = null;
        boolean z5 = true;
        while (length3 < length2) {
            findNextTypeBinding(length3, scope, packageBinding2);
            if (!this.resolvedType.isValidBinding()) {
                reportInvalidType(scope);
                while (length3 < length2) {
                    TypeReference[] typeReferenceArr2 = this.typeArguments[length3];
                    if (typeReferenceArr2 != null) {
                        int length4 = typeReferenceArr2.length;
                        for (?? r5 = z3; r5 < length4; r5++) {
                            TypeReference typeReference2 = typeReferenceArr2[r5];
                            if (z4) {
                                typeReference2.resolveType((ClassScope) scope);
                            } else {
                                typeReference2.resolveType((BlockScope) scope);
                            }
                        }
                    }
                    length3++;
                }
                return r7;
            }
            ReferenceBinding referenceBindingCreateParameterizedType = (ReferenceBinding) this.resolvedType;
            if (referenceBindingCreateRawType == null) {
                referenceBindingCreateRawType = referenceBindingCreateParameterizedType.enclosingType();
                if (referenceBindingCreateRawType != null) {
                    if (referenceBindingCreateParameterizedType.isStatic()) {
                        referenceBindingCreateRawType = (ReferenceBinding) scope.environment().convertToRawType(referenceBindingCreateRawType, z3);
                    } else {
                        referenceBindingCreateRawType = scope.environment().convertToParameterizedType(referenceBindingCreateRawType);
                    }
                }
            } else {
                if (this.annotations != null) {
                    rejectAnnotationsOnStaticMemberQualififer(scope, referenceBindingCreateParameterizedType, this.annotations[length3 - 1]);
                }
                if (z5 && referenceBindingCreateParameterizedType.isStatic() && (referenceBindingCreateRawType.isParameterizedTypeWithActualArguments() || referenceBindingCreateRawType.isGenericType())) {
                    scope.problemReporter().staticMemberOfParameterizedType(this, scope.environment().createParameterizedType((ReferenceBinding) referenceBindingCreateParameterizedType.erasure(), r7, referenceBindingCreateRawType), length3);
                    z5 = z3;
                }
                ReferenceBinding referenceBindingEnclosingType2 = referenceBindingCreateParameterizedType.enclosingType();
                if (referenceBindingEnclosingType2 != null && TypeBinding.notEquals(referenceBindingEnclosingType2.erasure(), referenceBindingCreateRawType.erasure())) {
                    referenceBindingCreateRawType = referenceBindingEnclosingType2;
                }
            }
            TypeReference[] typeReferenceArr3 = this.typeArguments[length3];
            if (typeReferenceArr3 != null) {
                if (z4) {
                    ClassScope classScope = (ClassScope) scope;
                    TypeReference typeReference3 = classScope.superTypeReference;
                    classScope.superTypeReference = r7;
                    r15 = typeReference3;
                } else {
                    r15 = r7;
                }
                int length5 = typeReferenceArr3.length;
                boolean z6 = (length5 == 0 && length3 == length2 + (-1) && (this.bits & 524288) != 0) ? true : z3;
                TypeBinding[] typeBindingArr = new TypeBinding[length5];
                ReferenceBinding referenceBinding = (ReferenceBinding) referenceBindingCreateParameterizedType.original();
                packageBinding = packageBinding2;
                int i2 = 0;
                boolean z7 = false;
                while (i2 < length5) {
                    TypeReference typeReference4 = typeReferenceArr3[i2];
                    if (z4) {
                        typeBindingResolveTypeArgument = typeReference4.resolveTypeArgument((ClassScope) scope, referenceBinding, i2);
                    } else {
                        typeBindingResolveTypeArgument = typeReference4.resolveTypeArgument((BlockScope) scope, referenceBinding, i2);
                    }
                    if (typeBindingResolveTypeArgument == null) {
                        z7 = true;
                    } else {
                        typeBindingArr[i2] = typeBindingResolveTypeArgument;
                    }
                    i2++;
                    z2 = z;
                    z6 = z6;
                }
                if (z7) {
                    return null;
                }
                if (z4) {
                    ClassScope classScope2 = (ClassScope) scope;
                    classScope2.superTypeReference = r15;
                    if (classScope2.detectHierarchyCycle(referenceBinding, this)) {
                        return null;
                    }
                }
                TypeVariableBinding[] typeVariableBindingArrTypeVariables = referenceBinding.typeVariables();
                if (typeVariableBindingArrTypeVariables == Binding.NO_TYPE_VARIABLES) {
                    if (scope.compilerOptions().originalSourceLevel >= ClassFileConstants.JDK1_5) {
                        scope.problemReporter().nonGenericTypeCannotBeParameterized(length3, this, referenceBindingCreateParameterizedType, typeBindingArr);
                        return null;
                    }
                    if (referenceBindingCreateRawType != null && referenceBindingCreateRawType.isParameterizedType()) {
                        referenceBindingCreateParameterizedType = scope.environment().createParameterizedType(referenceBinding, null, referenceBindingCreateRawType);
                    }
                    this.resolvedType = referenceBindingCreateParameterizedType;
                    return this.resolvedType;
                }
                if (length5 != typeVariableBindingArrTypeVariables.length && !z6) {
                    scope.problemReporter().incorrectArityForParameterizedType(this, referenceBindingCreateParameterizedType, typeBindingArr, length3);
                    return null;
                }
                if (z5 && !referenceBindingCreateParameterizedType.isStatic() && (referenceBindingEnclosingType = referenceBindingCreateParameterizedType.enclosingType()) != null && referenceBindingEnclosingType.isRawType()) {
                    scope.problemReporter().rawMemberTypeCannotBeParameterized(this, scope.environment().createRawType(referenceBinding, referenceBindingEnclosingType), typeBindingArr);
                    z5 = false;
                }
                ParameterizedTypeBinding parameterizedTypeBindingCreateParameterizedType = scope.environment().createParameterizedType(referenceBinding, typeBindingArr, referenceBindingCreateRawType);
                if (z6) {
                    parameterizedTypeBindingCreateParameterizedType.arguments = ParameterizedSingleTypeReference.DIAMOND_TYPE_ARGUMENTS;
                } else if (z2) {
                    parameterizedTypeBindingCreateParameterizedType.boundCheck(scope, typeReferenceArr3);
                } else {
                    scope.deferBoundCheck(this);
                }
                referenceBindingCreateRawType = parameterizedTypeBindingCreateParameterizedType;
            } else {
                packageBinding = packageBinding2;
                ReferenceBinding referenceBinding2 = (ReferenceBinding) referenceBindingCreateParameterizedType.original();
                if (z4 && ((ClassScope) scope).detectHierarchyCycle(referenceBinding2, this)) {
                    return null;
                }
                if (referenceBinding2.isGenericType()) {
                    if (z5 && referenceBindingCreateRawType != null && referenceBindingCreateRawType.isParameterizedType() && !referenceBinding2.isStatic()) {
                        scope.problemReporter().parameterizedMemberTypeMissingArguments(this, scope.environment().createParameterizedType(referenceBinding2, null, referenceBindingCreateRawType), length3);
                        z5 = false;
                    }
                    referenceBindingCreateRawType = scope.environment().createRawType(referenceBinding2, referenceBindingCreateRawType);
                } else {
                    if (referenceBindingCreateRawType == null || !referenceBindingCreateRawType.isParameterizedType()) {
                        obj = null;
                    } else {
                        obj = null;
                        referenceBindingCreateParameterizedType = scope.environment().createParameterizedType(referenceBinding2, null, referenceBindingCreateRawType);
                    }
                    referenceBindingCreateRawType = referenceBindingCreateParameterizedType;
                }
                if (isTypeUseDeprecated(referenceBindingCreateRawType, scope)) {
                    reportDeprecatedType(referenceBindingCreateRawType, scope, length3);
                }
                this.resolvedType = referenceBindingCreateRawType;
                this.typesPerToken[length3] = referenceBindingCreateRawType;
                recordResolution(scope.environment(), this.resolvedType);
                length3++;
                z2 = z;
                r7 = obj;
                packageBinding2 = packageBinding;
                z3 = false;
            }
            obj = null;
            if (isTypeUseDeprecated(referenceBindingCreateRawType, scope)) {
                reportDeprecatedType(referenceBindingCreateRawType, scope, length3);
            }
            this.resolvedType = referenceBindingCreateRawType;
            this.typesPerToken[length3] = referenceBindingCreateRawType;
            recordResolution(scope.environment(), this.resolvedType);
            length3++;
            z2 = z;
            r7 = obj;
            packageBinding2 = packageBinding;
            z3 = false;
        }
        return this.resolvedType;
    }

    private void createArrayType(Scope scope) {
        if (this.dimensions > 0) {
            if (this.dimensions > 255) {
                scope.problemReporter().tooManyDimensions(this);
            }
            this.resolvedType = scope.createArrayType(this.resolvedType, this.dimensions);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        int i2;
        int length = this.tokens.length;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            i2 = length - 1;
            if (i4 >= i2) {
                break;
            }
            if (this.annotations != null && this.annotations[i4] != null) {
                printAnnotations(this.annotations[i4], stringBuffer);
                stringBuffer.append(StringUtil.SPACE);
            }
            stringBuffer.append(this.tokens[i4]);
            TypeReference[] typeReferenceArr = this.typeArguments[i4];
            if (typeReferenceArr != null) {
                stringBuffer.append('<');
                int length2 = typeReferenceArr.length;
                if (length2 > 0) {
                    int i5 = length2 - 1;
                    for (int i6 = 0; i6 < i5; i6++) {
                        typeReferenceArr[i6].print(0, stringBuffer);
                        stringBuffer.append(", ");
                    }
                    typeReferenceArr[i5].print(0, stringBuffer);
                }
                stringBuffer.append('>');
            }
            stringBuffer.append('.');
            i4++;
        }
        if (this.annotations != null && this.annotations[i2] != null) {
            stringBuffer.append(" ");
            printAnnotations(this.annotations[i2], stringBuffer);
            stringBuffer.append(StringUtil.SPACE);
        }
        stringBuffer.append(this.tokens[i2]);
        TypeReference[] typeReferenceArr2 = this.typeArguments[i2];
        if (typeReferenceArr2 != null) {
            stringBuffer.append('<');
            int length3 = typeReferenceArr2.length;
            if (length3 > 0) {
                int i7 = length3 - 1;
                for (int i8 = 0; i8 < i7; i8++) {
                    typeReferenceArr2[i8].print(0, stringBuffer);
                    stringBuffer.append(", ");
                }
                typeReferenceArr2[i7].print(0, stringBuffer);
            }
            stringBuffer.append('>');
        }
        Annotation[][] annotationsOnDimensions = getAnnotationsOnDimensions();
        if ((this.bits & 16384) != 0) {
            while (i3 < this.dimensions - 1) {
                if (annotationsOnDimensions != null && annotationsOnDimensions[i3] != null) {
                    stringBuffer.append(" ");
                    printAnnotations(annotationsOnDimensions[i3], stringBuffer);
                    stringBuffer.append(" ");
                }
                stringBuffer.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                i3++;
            }
            if (annotationsOnDimensions != null && annotationsOnDimensions[this.dimensions - 1] != null) {
                stringBuffer.append(" ");
                printAnnotations(annotationsOnDimensions[this.dimensions - 1], stringBuffer);
                stringBuffer.append(" ");
            }
            stringBuffer.append("...");
        } else {
            while (i3 < this.dimensions) {
                if (annotationsOnDimensions != null && annotationsOnDimensions[i3] != null) {
                    stringBuffer.append(" ");
                    printAnnotations(annotationsOnDimensions[i3], stringBuffer);
                    stringBuffer.append(" ");
                }
                stringBuffer.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                i3++;
            }
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeBinding resolveType(BlockScope blockScope, boolean z, int i) {
        return internalResolveType(blockScope, z, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeBinding resolveType(ClassScope classScope, int i) {
        return internalResolveType(classScope, false, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
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
            Annotation[][] annotationsOnDimensions = getAnnotationsOnDimensions(true);
            if (annotationsOnDimensions != null) {
                int length3 = annotationsOnDimensions.length;
                for (int i3 = 0; i3 < length3; i3++) {
                    Annotation[] annotationArr = annotationsOnDimensions[i3];
                    int length4 = annotationArr == null ? 0 : annotationArr.length;
                    for (int i4 = 0; i4 < length4; i4++) {
                        annotationArr[i4].traverse(aSTVisitor, blockScope);
                    }
                }
            }
            int length5 = this.typeArguments.length;
            for (int i5 = 0; i5 < length5; i5++) {
                TypeReference[] typeReferenceArr = this.typeArguments[i5];
                if (typeReferenceArr != null) {
                    int length6 = typeReferenceArr.length;
                    for (int i6 = 0; i6 < length6; i6++) {
                        this.typeArguments[i5][i6].traverse(aSTVisitor, blockScope);
                    }
                }
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
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
            Annotation[][] annotationsOnDimensions = getAnnotationsOnDimensions(true);
            if (annotationsOnDimensions != null) {
                int length3 = annotationsOnDimensions.length;
                for (int i3 = 0; i3 < length3; i3++) {
                    Annotation[] annotationArr = annotationsOnDimensions[i3];
                    int length4 = annotationArr == null ? 0 : annotationArr.length;
                    for (int i4 = 0; i4 < length4; i4++) {
                        annotationArr[i4].traverse(aSTVisitor, classScope);
                    }
                }
            }
            int length5 = this.typeArguments.length;
            for (int i5 = 0; i5 < length5; i5++) {
                TypeReference[] typeReferenceArr = this.typeArguments[i5];
                if (typeReferenceArr != null) {
                    int length6 = typeReferenceArr.length;
                    for (int i6 = 0; i6 < length6; i6++) {
                        this.typeArguments[i5][i6].traverse(aSTVisitor, classScope);
                    }
                }
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
