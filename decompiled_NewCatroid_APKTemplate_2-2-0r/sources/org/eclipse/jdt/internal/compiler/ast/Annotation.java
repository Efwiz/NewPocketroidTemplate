package org.eclipse.jdt.internal.compiler.ast;

import java.util.Stack;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.env.EnumConstantSignature;
import org.eclipse.jdt.internal.compiler.impl.BooleanConstant;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.impl.IrritantSet;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.ElementValuePair;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.PackageBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;

/* JADX INFO: loaded from: classes14.dex */
public abstract class Annotation extends Expression {
    public int declarationSourceEnd;
    public Binding recipient;
    public TypeReference type;
    static final MemberValuePair[] NoValuePairs = new MemberValuePair[0];
    static final int[] TYPE_PATH_ELEMENT_ARRAY = new int[2];
    static final int[] TYPE_PATH_INNER_TYPE = {1, 0};
    static final int[] TYPE_PATH_ANNOTATION_ON_WILDCARD_BOUND = {2, 0};
    Annotation persistibleAnnotation = this;
    protected AnnotationBinding compilerAnnotation = null;

    public abstract MemberValuePair[] memberValuePairs();

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public abstract void traverse(ASTVisitor aSTVisitor, BlockScope blockScope);

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public abstract void traverse(ASTVisitor aSTVisitor, ClassScope classScope);

    /* JADX INFO: renamed from: org.eclipse.jdt.internal.compiler.ast.Annotation$1LocationCollector, reason: invalid class name */
    class C1LocationCollector extends ASTVisitor {
        Annotation searchedAnnotation;
        boolean continueSearch = true;
        Stack typePathEntries = new Stack();

        public C1LocationCollector(Annotation annotation) {
            this.searchedAnnotation = annotation;
        }

        private int[] computeNestingDepth(TypeReference typeReference) {
            TypeBinding typeBindingLeafComponentType = typeReference.resolvedType == null ? null : typeReference.resolvedType.leafComponentType();
            int annotatableLevels = typeReference.getAnnotatableLevels();
            int[] iArr = new int[annotatableLevels];
            if (typeBindingLeafComponentType != null && typeBindingLeafComponentType.isNestedType()) {
                int i = 0;
                for (TypeBinding typeBindingEnclosingType = typeBindingLeafComponentType; typeBindingEnclosingType != null; typeBindingEnclosingType = typeBindingEnclosingType.enclosingType()) {
                    i += !typeBindingEnclosingType.isStatic() ? 1 : 0;
                }
                for (int i2 = annotatableLevels - 1; typeBindingLeafComponentType != null && i2 >= 0; i2--) {
                    iArr[i2] = i;
                    i -= !typeBindingLeafComponentType.isStatic() ? 1 : 0;
                    typeBindingLeafComponentType = typeBindingLeafComponentType.enclosingType();
                }
            }
            return iArr;
        }

        private void inspectAnnotations(Annotation[] annotationArr) {
            int length = annotationArr == null ? 0 : annotationArr.length;
            for (int i = 0; this.continueSearch && i < length; i++) {
                if (annotationArr[i] == this.searchedAnnotation) {
                    this.continueSearch = false;
                }
            }
        }

        private void inspectArrayDimensions(Annotation[][] annotationArr, int i) {
            for (int i2 = 0; this.continueSearch && i2 < i; i2++) {
                inspectAnnotations(annotationArr == null ? null : annotationArr[i2]);
                if (!this.continueSearch) {
                    return;
                }
                this.typePathEntries.push(Annotation.TYPE_PATH_ELEMENT_ARRAY);
            }
        }

        private void inspectTypeArguments(TypeReference[] typeReferenceArr) {
            int length = typeReferenceArr == null ? 0 : typeReferenceArr.length;
            for (int i = 0; this.continueSearch && i < length; i++) {
                int size = this.typePathEntries.size();
                this.typePathEntries.add(new int[]{3, i});
                typeReferenceArr[i].traverse(this, (BlockScope) null);
                if (!this.continueSearch) {
                    return;
                }
                this.typePathEntries.setSize(size);
            }
        }

        public boolean visit(TypeReference typeReference, BlockScope blockScope) {
            if (this.continueSearch) {
                inspectArrayDimensions(typeReference.getAnnotationsOnDimensions(), typeReference.dimensions());
                if (this.continueSearch) {
                    int[] iArrComputeNestingDepth = computeNestingDepth(typeReference);
                    Annotation[][] annotationArr = typeReference.annotations;
                    TypeReference[][] typeArguments = typeReference.getTypeArguments();
                    int annotatableLevels = typeReference.getAnnotatableLevels();
                    int size = this.typePathEntries.size();
                    while (true) {
                        annotatableLevels--;
                        if (!this.continueSearch || annotatableLevels < 0) {
                            break;
                        }
                        this.typePathEntries.setSize(size);
                        int i = iArrComputeNestingDepth[annotatableLevels];
                        for (int i2 = 0; i2 < i; i2++) {
                            this.typePathEntries.add(Annotation.TYPE_PATH_INNER_TYPE);
                        }
                        if (annotationArr != null) {
                            inspectAnnotations(annotationArr[annotatableLevels]);
                        }
                        if (this.continueSearch && typeArguments != null) {
                            inspectTypeArguments(typeArguments[annotatableLevels]);
                        }
                    }
                }
            }
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(SingleTypeReference singleTypeReference, BlockScope blockScope) {
            return visit((TypeReference) singleTypeReference, blockScope);
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(ArrayTypeReference arrayTypeReference, BlockScope blockScope) {
            return visit((TypeReference) arrayTypeReference, blockScope);
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(ParameterizedSingleTypeReference parameterizedSingleTypeReference, BlockScope blockScope) {
            return visit((TypeReference) parameterizedSingleTypeReference, blockScope);
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(QualifiedTypeReference qualifiedTypeReference, BlockScope blockScope) {
            return visit((TypeReference) qualifiedTypeReference, blockScope);
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(ArrayQualifiedTypeReference arrayQualifiedTypeReference, BlockScope blockScope) {
            return visit((TypeReference) arrayQualifiedTypeReference, blockScope);
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference, BlockScope blockScope) {
            return visit((TypeReference) parameterizedQualifiedTypeReference, blockScope);
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(Wildcard wildcard, BlockScope blockScope) {
            TypeReference typeReference;
            visit((TypeReference) wildcard, blockScope);
            if (!this.continueSearch || (typeReference = wildcard.bound) == null) {
                return false;
            }
            int size = this.typePathEntries.size();
            this.typePathEntries.push(Annotation.TYPE_PATH_ANNOTATION_ON_WILDCARD_BOUND);
            typeReference.traverse(this, blockScope);
            if (!this.continueSearch) {
                return false;
            }
            this.typePathEntries.setSize(size);
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(ArrayAllocationExpression arrayAllocationExpression, BlockScope blockScope) {
            if (!this.continueSearch) {
                return false;
            }
            inspectArrayDimensions(arrayAllocationExpression.getAnnotationsOnDimensions(), arrayAllocationExpression.dimensions.length);
            if (this.continueSearch) {
                arrayAllocationExpression.type.traverse(this, blockScope);
            }
            if (this.continueSearch) {
                throw new IllegalStateException();
            }
            return false;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("search location for ").append(this.searchedAnnotation).append("\ncurrent type_path entries : ");
            int size = this.typePathEntries.size();
            for (int i = 0; i < size; i++) {
                int[] iArr = (int[]) this.typePathEntries.get(i);
                stringBuffer.append('(').append(iArr[0]).append(',').append(iArr[1]).append(')');
            }
            return String.valueOf(stringBuffer);
        }
    }

    public static int[] getLocations(Expression expression, Annotation annotation) {
        if (expression == null) {
            return null;
        }
        C1LocationCollector c1LocationCollector = new C1LocationCollector(annotation);
        expression.traverse(c1LocationCollector, (BlockScope) null);
        if (c1LocationCollector.typePathEntries.isEmpty()) {
            return null;
        }
        int size = c1LocationCollector.typePathEntries.size();
        int[] iArr = new int[size * 2];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int[] iArr2 = (int[]) c1LocationCollector.typePathEntries.get(i2);
            int i3 = i + 1;
            iArr[i] = iArr2[0];
            i += 2;
            iArr[i3] = iArr2[1];
        }
        return iArr;
    }

    public static long getRetentionPolicy(char[] cArr) {
        if (cArr == null || cArr.length == 0) {
            return 0L;
        }
        char c = cArr[0];
        if (c == 'C') {
            return CharOperation.equals(cArr, TypeConstants.UPPER_CLASS) ? 35184372088832L : 0L;
        }
        if (c != 'R') {
            return (c == 'S' && CharOperation.equals(cArr, TypeConstants.UPPER_SOURCE)) ? 17592186044416L : 0L;
        }
        return CharOperation.equals(cArr, TypeConstants.UPPER_RUNTIME) ? 52776558133248L : 0L;
    }

    public static long getTargetElementType(char[] cArr) {
        if (cArr == null || cArr.length == 0) {
            return 0L;
        }
        char c = cArr[0];
        if (c == 'A') {
            return CharOperation.equals(cArr, TypeConstants.UPPER_ANNOTATION_TYPE) ? 4398046511104L : 0L;
        }
        if (c == 'C') {
            return CharOperation.equals(cArr, TypeConstants.UPPER_CONSTRUCTOR) ? 1099511627776L : 0L;
        }
        if (c == 'F') {
            return CharOperation.equals(cArr, TypeConstants.UPPER_FIELD) ? 137438953472L : 0L;
        }
        if (c == 'P') {
            if (CharOperation.equals(cArr, TypeConstants.UPPER_PARAMETER)) {
                return 549755813888L;
            }
            return CharOperation.equals(cArr, TypeConstants.UPPER_PACKAGE) ? 8796093022208L : 0L;
        }
        if (c != 'T') {
            if (c != 'L') {
                return (c == 'M' && CharOperation.equals(cArr, TypeConstants.UPPER_METHOD)) ? 274877906944L : 0L;
            }
            return CharOperation.equals(cArr, TypeConstants.UPPER_LOCAL_VARIABLE) ? 2199023255552L : 0L;
        }
        if (CharOperation.equals(cArr, TypeConstants.TYPE)) {
            return 68719476736L;
        }
        if (CharOperation.equals(cArr, TypeConstants.TYPE_USE_TARGET)) {
            return 9007199254740992L;
        }
        return CharOperation.equals(cArr, TypeConstants.TYPE_PARAMETER_TARGET) ? 18014398509481984L : 0L;
    }

    public ElementValuePair[] computeElementValuePairs() {
        return Binding.NO_ELEMENT_VALUE_PAIRS;
    }

    private long detectStandardAnnotation(Scope scope, ReferenceBinding referenceBinding, MemberValuePair memberValuePair) {
        long retentionPolicy;
        long jNullLocationBitsFromAnnotationValue;
        FieldBinding fieldBinding;
        FieldBinding fieldBinding2;
        FieldBinding fieldBinding3;
        int i = referenceBinding.f626id;
        if (i == 60) {
            retentionPolicy = 2251799813685248L;
        } else if (i == 61) {
            retentionPolicy = 4503599627370496L;
        } else if (i == 77) {
            retentionPolicy = 576460752303423488L;
        } else if (i != 90) {
            retentionPolicy = 0;
            switch (i) {
                case 44:
                    retentionPolicy = 70368744177664L;
                    break;
                case 45:
                    retentionPolicy = 140737488355328L;
                    break;
                case 46:
                    retentionPolicy = 281474976710656L;
                    break;
                case 47:
                    retentionPolicy = 562949953421312L;
                    break;
                case 48:
                    if (memberValuePair != null) {
                        Expression expression = memberValuePair.value;
                        if ((expression.bits & 3) == 1 && (expression instanceof Reference) && (fieldBinding = ((Reference) expression).fieldBinding()) != null && fieldBinding.declaringClass.f626id == 51) {
                            retentionPolicy = getRetentionPolicy(fieldBinding.f628name);
                        }
                    }
                    break;
                case 49:
                    retentionPolicy = 1125899906842624L;
                    break;
                case 50:
                    long j = 34359738368L;
                    if (memberValuePair != null) {
                        Expression expression2 = memberValuePair.value;
                        if (expression2 instanceof ArrayInitializer) {
                            Expression[] expressionArr = ((ArrayInitializer) expression2).expressions;
                            if (expressionArr != null) {
                                for (Expression expression3 : expressionArr) {
                                    if ((expression3.bits & 3) == 1 && (fieldBinding3 = ((Reference) expression3).fieldBinding()) != null && fieldBinding3.declaringClass.f626id == 52) {
                                        long targetElementType = getTargetElementType(fieldBinding3.f628name);
                                        if ((j & targetElementType) != 0) {
                                            scope.problemReporter().duplicateTargetInTargetAnnotation(referenceBinding, (NameReference) expression3);
                                        } else {
                                            j |= targetElementType;
                                        }
                                    }
                                }
                            }
                        } else if ((expression2.bits & 3) == 1 && (fieldBinding2 = ((Reference) expression2).fieldBinding()) != null && fieldBinding2.declaringClass.f626id == 52) {
                            retentionPolicy = getTargetElementType(fieldBinding2.f628name) | 34359738368L;
                            break;
                        }
                        retentionPolicy = j;
                    } else {
                        retentionPolicy = j;
                    }
                    break;
            }
        } else {
            retentionPolicy = 1152921504606846976L;
        }
        if (referenceBinding.hasNullBit(64)) {
            jNullLocationBitsFromAnnotationValue = 36028797018963968L;
        } else if (referenceBinding.hasNullBit(32)) {
            jNullLocationBitsFromAnnotationValue = 72057594037927936L;
        } else {
            if (!referenceBinding.hasNullBit(128)) {
                return retentionPolicy;
            }
            Object defaultValue = null;
            if (memberValuePair != null) {
                if (memberValuePair.compilerElementPair != null) {
                    defaultValue = memberValuePair.compilerElementPair.value;
                }
            } else {
                MethodBinding[] methodBindingArrMethods = referenceBinding.methods();
                if (methodBindingArrMethods == null || methodBindingArrMethods.length != 1) {
                    retentionPolicy |= 144115188075855872L;
                } else {
                    defaultValue = methodBindingArrMethods[0].getDefaultValue();
                }
            }
            if (defaultValue instanceof BooleanConstant) {
                return retentionPolicy | (((BooleanConstant) defaultValue).booleanValue() ? 144115188075855872L : 288230376151711744L);
            }
            if (defaultValue == null) {
                return retentionPolicy;
            }
            jNullLocationBitsFromAnnotationValue = nullLocationBitsFromAnnotationValue(defaultValue);
        }
        return retentionPolicy | jNullLocationBitsFromAnnotationValue;
    }

    public static int nullLocationBitsFromAnnotationValue(Object obj) {
        if (obj instanceof Object[]) {
            Object[] objArr = (Object[]) obj;
            if (objArr.length == 0) {
                return 2;
            }
            int iEvaluateDefaultNullnessLocation = 0;
            for (Object obj2 : objArr) {
                iEvaluateDefaultNullnessLocation |= evaluateDefaultNullnessLocation(obj2);
            }
            return iEvaluateDefaultNullnessLocation;
        }
        return evaluateDefaultNullnessLocation(obj);
    }

    private static int evaluateDefaultNullnessLocation(Object obj) {
        char[] enumConstantName;
        if (obj instanceof FieldBinding) {
            enumConstantName = ((FieldBinding) obj).f628name;
        } else if (obj instanceof EnumConstantSignature) {
            enumConstantName = ((EnumConstantSignature) obj).getEnumConstantName();
        } else if (obj instanceof ElementValuePair.UnresolvedEnumConstant) {
            enumConstantName = ((ElementValuePair.UnresolvedEnumConstant) obj).getEnumConstantName();
        } else {
            if (obj instanceof BooleanConstant) {
                return ((BooleanConstant) obj).booleanValue() ? 1 : 2;
            }
            enumConstantName = null;
        }
        if (enumConstantName == null) {
            return 0;
        }
        int length = enumConstantName.length;
        if (length == 5) {
            return CharOperation.equals(enumConstantName, TypeConstants.DEFAULT_LOCATION__FIELD) ? 32 : 0;
        }
        if (length == 13) {
            return CharOperation.equals(enumConstantName, TypeConstants.DEFAULT_LOCATION__TYPE_ARGUMENT) ? 64 : 0;
        }
        if (length == 14) {
            if (CharOperation.equals(enumConstantName, TypeConstants.DEFAULT_LOCATION__TYPE_PARAMETER)) {
                return 128;
            }
            return CharOperation.equals(enumConstantName, TypeConstants.DEFAULT_LOCATION__ARRAY_CONTENTS) ? 512 : 0;
        }
        switch (length) {
            case 9:
                return CharOperation.equals(enumConstantName, TypeConstants.DEFAULT_LOCATION__PARAMETER) ? 8 : 0;
            case 10:
                return CharOperation.equals(enumConstantName, TypeConstants.DEFAULT_LOCATION__TYPE_BOUND) ? 256 : 0;
            case 11:
                return CharOperation.equals(enumConstantName, TypeConstants.DEFAULT_LOCATION__RETURN_TYPE) ? 16 : 0;
            default:
                return 0;
        }
    }

    static String getRetentionName(long j) {
        if ((j & 52776558133248L) == 52776558133248L) {
            return new String(UPPER_RUNTIME);
        }
        if ((j & 17592186044416L) != 0) {
            return new String(UPPER_SOURCE);
        }
        return new String(TypeConstants.UPPER_CLASS);
    }

    private static long getAnnotationRetention(ReferenceBinding referenceBinding) {
        long annotationTagBits = referenceBinding.getAnnotationTagBits() & 52776558133248L;
        if (annotationTagBits != 0) {
            return annotationTagBits;
        }
        return 35184372088832L;
    }

    public void checkRepeatableMetaAnnotation(BlockScope blockScope) {
        ReferenceBinding referenceBinding = (ReferenceBinding) this.recipient;
        MemberValuePair[] memberValuePairArrMemberValuePairs = memberValuePairs();
        if (memberValuePairArrMemberValuePairs == null || memberValuePairArrMemberValuePairs.length != 1) {
            return;
        }
        Object obj = memberValuePairArrMemberValuePairs[0].compilerElementPair.value;
        if (obj instanceof ReferenceBinding) {
            ReferenceBinding referenceBinding2 = (ReferenceBinding) obj;
            if (referenceBinding2.isAnnotationType()) {
                referenceBinding.setContainerAnnotationType(referenceBinding2);
                checkContainerAnnotationType(memberValuePairArrMemberValuePairs[0], blockScope, referenceBinding2, referenceBinding, false);
            }
        }
    }

    public static void checkContainerAnnotationType(ASTNode aSTNode, BlockScope blockScope, ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2, boolean z) {
        boolean z2 = false;
        for (MethodBinding methodBinding : referenceBinding.methods()) {
            if (CharOperation.equals(methodBinding.selector, TypeConstants.VALUE)) {
                if (!methodBinding.returnType.isArrayType() || methodBinding.returnType.dimensions() != 1 || !TypeBinding.equalsEquals(((ArrayBinding) methodBinding.returnType).elementsType(), referenceBinding2)) {
                    referenceBinding2.tagAsHavingDefectiveContainerType();
                    blockScope.problemReporter().containerAnnotationTypeHasWrongValueType(aSTNode, referenceBinding, referenceBinding2, methodBinding.returnType);
                }
                z2 = true;
            } else if ((methodBinding.modifiers & 131072) == 0) {
                referenceBinding2.tagAsHavingDefectiveContainerType();
                blockScope.problemReporter().containerAnnotationTypeHasNonDefaultMembers(aSTNode, referenceBinding, methodBinding.selector);
            }
        }
        if (!z2) {
            referenceBinding2.tagAsHavingDefectiveContainerType();
            blockScope.problemReporter().containerAnnotationTypeMustHaveValue(aSTNode, referenceBinding);
        }
        if (z) {
            checkContainingAnnotationTargetAtUse((Annotation) aSTNode, blockScope, referenceBinding, referenceBinding2);
        } else {
            checkContainerAnnotationTypeTarget(aSTNode, blockScope, referenceBinding, referenceBinding2);
        }
        long annotationRetention = getAnnotationRetention(referenceBinding2);
        long annotationRetention2 = getAnnotationRetention(referenceBinding);
        if (annotationRetention2 < annotationRetention) {
            referenceBinding2.tagAsHavingDefectiveContainerType();
            blockScope.problemReporter().containerAnnotationTypeHasShorterRetention(aSTNode, referenceBinding2, getRetentionName(annotationRetention), referenceBinding, getRetentionName(annotationRetention2));
        }
        if ((referenceBinding2.getAnnotationTagBits() & 140737488355328L) != 0 && (referenceBinding.getAnnotationTagBits() & 140737488355328L) == 0) {
            referenceBinding2.tagAsHavingDefectiveContainerType();
            blockScope.problemReporter().repeatableAnnotationTypeIsDocumented(aSTNode, referenceBinding2, referenceBinding);
        }
        if ((referenceBinding2.getAnnotationTagBits() & 281474976710656L) == 0 || (referenceBinding.getAnnotationTagBits() & 281474976710656L) != 0) {
            return;
        }
        referenceBinding2.tagAsHavingDefectiveContainerType();
        blockScope.problemReporter().repeatableAnnotationTypeIsInherited(aSTNode, referenceBinding2, referenceBinding);
    }

    /* JADX WARN: Type inference failed for: r4v6, types: [org.eclipse.jdt.internal.compiler.ast.Annotation$1MissingTargetBuilder] */
    private static void checkContainerAnnotationTypeTarget(ASTNode aSTNode, Scope scope, ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2) {
        long annotationTagBits = referenceBinding2.getAnnotationTagBits();
        long j = annotationTagBits & TagBits.AnnotationTargetMASK;
        long j2 = TagBits.SE7AnnotationTargetMASK;
        if (j == 0) {
            annotationTagBits = 17523466567680L;
        }
        long annotationTagBits2 = referenceBinding.getAnnotationTagBits();
        if ((annotationTagBits2 & TagBits.AnnotationTargetMASK) != 0) {
            j2 = annotationTagBits2;
        }
        final long j3 = annotationTagBits & TagBits.AnnotationTargetMASK;
        final long j4 = TagBits.AnnotationTargetMASK & j2;
        if (((~j3) & j4) != 0) {
            ?? r4 = new Object() { // from class: org.eclipse.jdt.internal.compiler.ast.Annotation.1MissingTargetBuilder
                StringBuffer targetBuffer = new StringBuffer();

                void check(long j5, char[] cArr) {
                    long j6 = j4 & j5;
                    long j7 = j3;
                    if ((j6 & (~j7)) != 0) {
                        if (j5 != 68719476736L || (9007199254740992L & j7) == 0) {
                            add(cArr);
                        }
                    }
                }

                void checkAnnotationType(char[] cArr) {
                    if ((j4 & 4398046511104L) == 0 || (j3 & 4466765987840L) != 0) {
                        return;
                    }
                    add(cArr);
                }

                private void add(char[] cArr) {
                    if (this.targetBuffer.length() != 0) {
                        this.targetBuffer.append(", ");
                    }
                    this.targetBuffer.append(cArr);
                }

                public String toString() {
                    return this.targetBuffer.toString();
                }

                public boolean hasError() {
                    return this.targetBuffer.length() != 0;
                }
            };
            r4.check(68719476736L, TypeConstants.TYPE);
            r4.check(137438953472L, TypeConstants.UPPER_FIELD);
            r4.check(274877906944L, TypeConstants.UPPER_METHOD);
            r4.check(549755813888L, TypeConstants.UPPER_PARAMETER);
            r4.check(1099511627776L, TypeConstants.UPPER_CONSTRUCTOR);
            r4.check(2199023255552L, TypeConstants.UPPER_LOCAL_VARIABLE);
            r4.checkAnnotationType(TypeConstants.UPPER_ANNOTATION_TYPE);
            r4.check(8796093022208L, TypeConstants.UPPER_PACKAGE);
            r4.check(18014398509481984L, TypeConstants.TYPE_PARAMETER_TARGET);
            r4.check(9007199254740992L, TypeConstants.TYPE_USE_TARGET);
            if (r4.hasError()) {
                referenceBinding2.tagAsHavingDefectiveContainerType();
                scope.problemReporter().repeatableAnnotationTypeTargetMismatch(aSTNode, referenceBinding2, referenceBinding, r4.toString());
            }
        }
    }

    public static void checkContainingAnnotationTargetAtUse(Annotation annotation, BlockScope blockScope, TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (typeBinding2.isValidBinding() && !isAnnotationTargetAllowed(annotation, blockScope, typeBinding, annotation.recipient.kind())) {
            blockScope.problemReporter().disallowedTargetForContainerAnnotation(annotation, typeBinding);
        }
    }

    public AnnotationBinding getCompilerAnnotation() {
        return this.compilerAnnotation;
    }

    public boolean isRuntimeInvisible() {
        TypeBinding typeBinding = this.resolvedType;
        if (typeBinding == null) {
            return false;
        }
        long annotationTagBits = typeBinding.getAnnotationTagBits();
        if ((27021597764222976L & annotationTagBits) != 0 && (TagBits.SE7AnnotationTargetMASK & annotationTagBits) == 0) {
            return false;
        }
        long j = annotationTagBits & 52776558133248L;
        return j == 0 || j == 35184372088832L;
    }

    public boolean isRuntimeTypeInvisible() {
        TypeBinding typeBinding = this.resolvedType;
        if (typeBinding == null) {
            return false;
        }
        long annotationTagBits = typeBinding.getAnnotationTagBits();
        if ((TagBits.AnnotationTargetMASK & annotationTagBits) == 0 || (27021597764222976L & annotationTagBits) == 0) {
            return false;
        }
        long j = annotationTagBits & 52776558133248L;
        return j == 0 || j == 35184372088832L;
    }

    public boolean isRuntimeTypeVisible() {
        TypeBinding typeBinding = this.resolvedType;
        if (typeBinding == null) {
            return false;
        }
        long annotationTagBits = typeBinding.getAnnotationTagBits();
        if ((TagBits.AnnotationTargetMASK & annotationTagBits) == 0 || (27021597764222976L & annotationTagBits) == 0) {
            return false;
        }
        long j = annotationTagBits & 52776558133248L;
        return j != 0 && j == 52776558133248L;
    }

    public boolean isRuntimeVisible() {
        TypeBinding typeBinding = this.resolvedType;
        if (typeBinding == null) {
            return false;
        }
        long annotationTagBits = typeBinding.getAnnotationTagBits();
        if ((27021597764222976L & annotationTagBits) != 0 && (TagBits.SE7AnnotationTargetMASK & annotationTagBits) == 0) {
            return false;
        }
        long j = annotationTagBits & 52776558133248L;
        return j != 0 && j == 52776558133248L;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        stringBuffer.append('@');
        this.type.printExpression(0, stringBuffer);
        return stringBuffer;
    }

    public void recordSuppressWarnings(Scope scope, int i, int i2, boolean z) {
        IrritantSet irritantSet;
        MemberValuePair[] memberValuePairArrMemberValuePairs = memberValuePairs();
        int length = memberValuePairArrMemberValuePairs.length;
        int i3 = 0;
        while (true) {
            irritantSet = null;
            if (i3 >= length) {
                break;
            }
            MemberValuePair memberValuePair = memberValuePairArrMemberValuePairs[i3];
            if (CharOperation.equals(memberValuePair.f618name, TypeConstants.VALUE)) {
                Expression expression = memberValuePair.value;
                if (expression instanceof ArrayInitializer) {
                    Expression[] expressionArr = ((ArrayInitializer) expression).expressions;
                    if (expressionArr == null) {
                        break;
                    }
                    int length2 = expressionArr.length;
                    for (int i4 = 0; i4 < length2; i4++) {
                        Constant constant = expressionArr[i4].constant;
                        if (constant != Constant.NotAConstant && constant.typeID() == 11) {
                            IrritantSet irritantSetWarningTokenToIrritants = CompilerOptions.warningTokenToIrritants(constant.stringValue());
                            if (irritantSetWarningTokenToIrritants == null) {
                                scope.problemReporter().unhandledWarningToken(expressionArr[i4]);
                            } else if (irritantSet == null) {
                                irritantSet = new IrritantSet(irritantSetWarningTokenToIrritants);
                            } else if (irritantSet.set(irritantSetWarningTokenToIrritants) == null) {
                                scope.problemReporter().unusedWarningToken(expressionArr[i4]);
                            }
                        }
                    }
                    break;
                }
                Constant constant2 = expression.constant;
                if (constant2 == Constant.NotAConstant || constant2.typeID() != 11) {
                    break;
                    break;
                }
                IrritantSet irritantSetWarningTokenToIrritants2 = CompilerOptions.warningTokenToIrritants(constant2.stringValue());
                if (irritantSetWarningTokenToIrritants2 != null) {
                    irritantSet = new IrritantSet(irritantSetWarningTokenToIrritants2);
                    break;
                } else {
                    scope.problemReporter().unhandledWarningToken(expression);
                    break;
                }
            }
            i3++;
        }
        IrritantSet irritantSet2 = irritantSet;
        if (!z || irritantSet2 == null) {
            return;
        }
        scope.referenceCompilationUnit().recordSuppressWarnings(irritantSet2, this, i, i2, scope.referenceContext());
    }

    /* JADX WARN: Code duplicated, block: B:130:0x02a6  */
    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) {
        int i;
        MethodBinding[] methodBindingArr;
        TypeBinding typeBinding;
        MethodBinding[] methodBindingArr2;
        TypeBinding typeBinding2;
        if (this.compilerAnnotation != null) {
            return this.resolvedType;
        }
        this.constant = Constant.NotAConstant;
        TypeBinding typeBindingResolveType = this.type.resolveType(blockScope);
        TypeBinding typeBinding3 = null;
        if (typeBindingResolveType == null) {
            return null;
        }
        this.resolvedType = typeBindingResolveType;
        if (!typeBindingResolveType.isAnnotationType() && typeBindingResolveType.isValidBinding()) {
            blockScope.problemReporter().notAnnotationType(typeBindingResolveType, this.type);
            return null;
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) this.resolvedType;
        MethodBinding[] methodBindingArrMethods = referenceBinding.methods();
        MemberValuePair[] memberValuePairArrMemberValuePairs = memberValuePairs();
        int length = memberValuePairArrMemberValuePairs.length;
        int i2 = 0;
        if (length > 0) {
            MemberValuePair[] memberValuePairArr = new MemberValuePair[length];
            System.arraycopy(memberValuePairArrMemberValuePairs, 0, memberValuePairArr, 0, length);
            memberValuePairArrMemberValuePairs = memberValuePairArr;
        }
        int length2 = methodBindingArrMethods.length;
        MemberValuePair memberValuePair = null;
        int i3 = 0;
        while (i3 < length2) {
            MethodBinding methodBinding = methodBindingArrMethods[i3];
            char[] cArr = methodBinding.selector;
            int i4 = i2;
            int i5 = i4;
            while (true) {
                if (i4 < length) {
                    MemberValuePair memberValuePair2 = memberValuePairArrMemberValuePairs[i4];
                    if (memberValuePair2 == null) {
                        methodBindingArr = methodBindingArrMethods;
                        typeBinding = typeBinding3;
                    } else {
                        char[] cArr2 = memberValuePair2.f618name;
                        if (CharOperation.equals(cArr2, cArr)) {
                            MemberValuePair memberValuePair3 = (memberValuePair == null && CharOperation.equals(cArr2, TypeConstants.VALUE)) ? memberValuePair2 : memberValuePair;
                            memberValuePair2.binding = methodBinding;
                            memberValuePair2.resolveTypeExpecting(blockScope, methodBinding.returnType);
                            memberValuePairArrMemberValuePairs[i4] = typeBinding3;
                            int i6 = i4 + 1;
                            boolean z = false;
                            while (i6 < length) {
                                MemberValuePair memberValuePair4 = memberValuePairArrMemberValuePairs[i6];
                                if (memberValuePair4 == null) {
                                    methodBindingArr2 = methodBindingArrMethods;
                                } else {
                                    methodBindingArr2 = methodBindingArrMethods;
                                    if (CharOperation.equals(memberValuePair4.f618name, cArr)) {
                                        blockScope.problemReporter().duplicateAnnotationValue(referenceBinding, memberValuePair4);
                                        memberValuePair4.binding = methodBinding;
                                        memberValuePair4.resolveTypeExpecting(blockScope, methodBinding.returnType);
                                        typeBinding2 = null;
                                        memberValuePairArrMemberValuePairs[i6] = null;
                                        z = true;
                                    }
                                    i6++;
                                    typeBinding3 = typeBinding2;
                                    methodBindingArrMethods = methodBindingArr2;
                                }
                                typeBinding2 = null;
                                i6++;
                                typeBinding3 = typeBinding2;
                                methodBindingArrMethods = methodBindingArr2;
                            }
                            if (z) {
                                blockScope.problemReporter().duplicateAnnotationValue(referenceBinding, memberValuePair2);
                                memberValuePair = memberValuePair3;
                                break;
                            }
                            methodBindingArr = methodBindingArrMethods;
                            typeBinding = typeBinding3;
                            memberValuePair = memberValuePair3;
                            i5 = 1;
                        } else {
                            methodBindingArr = methodBindingArrMethods;
                            typeBinding = typeBinding3;
                        }
                    }
                    i4++;
                    typeBinding3 = typeBinding;
                    methodBindingArrMethods = methodBindingArr;
                } else {
                    if (i5 != 0 || (methodBinding.modifiers & 131072) != 0 || (this.bits & 32) != 0 || !referenceBinding.isValidBinding()) {
                        break;
                    }
                    blockScope.problemReporter().missingValueForAnnotationMember(this, cArr);
                    break;
                }
            }
            i3++;
            i2 = 0;
        }
        for (int i7 = i2; i7 < length; i7++) {
            if (memberValuePairArrMemberValuePairs[i7] != null) {
                if (referenceBinding.isValidBinding()) {
                    blockScope.problemReporter().undefinedAnnotationValue(referenceBinding, memberValuePairArrMemberValuePairs[i7]);
                }
                memberValuePairArrMemberValuePairs[i7].resolveTypeExpecting(blockScope, typeBinding3);
            }
        }
        this.compilerAnnotation = blockScope.environment().createAnnotation((ReferenceBinding) this.resolvedType, computeElementValuePairs());
        long jDetectStandardAnnotation = detectStandardAnnotation(blockScope, referenceBinding, memberValuePair);
        int i8 = (int) (1018 & jDetectStandardAnnotation);
        long j = (-1019) & jDetectStandardAnnotation;
        blockScope.referenceCompilationUnit().recordSuppressWarnings(IrritantSet.NLS, null, this.sourceStart, this.declarationSourceEnd, blockScope.referenceContext());
        Binding binding = this.recipient;
        if (binding != null) {
            int iKind = binding.kind();
            if (j == 0 && i8 == 0) {
                i = 4;
            } else {
                if (iKind == 1) {
                    FieldBinding fieldBinding = (FieldBinding) this.recipient;
                    fieldBinding.tagBits = j | fieldBinding.tagBits;
                    if ((jDetectStandardAnnotation & 1125899906842624L) != 0) {
                        FieldDeclaration fieldDeclarationDeclarationOf = ((SourceTypeBinding) fieldBinding.declaringClass).scope.referenceContext.declarationOf(fieldBinding);
                        recordSuppressWarnings(blockScope, fieldDeclarationDeclarationOf.declarationSourceStart, fieldDeclarationDeclarationOf.declarationSourceEnd, blockScope.compilerOptions().suppressWarnings);
                    }
                    if ((fieldBinding.tagBits & TagBits.AnnotationNullMASK) == TagBits.AnnotationNullMASK) {
                        blockScope.problemReporter().contradictoryNullAnnotations(this);
                        fieldBinding.tagBits &= -108086391056891905L;
                    }
                } else if (iKind != 2) {
                    if (iKind != 4) {
                        if (iKind == 8) {
                            MethodBinding methodBinding2 = (MethodBinding) this.recipient;
                            methodBinding2.tagBits = j | methodBinding2.tagBits;
                            if ((jDetectStandardAnnotation & 1125899906842624L) != 0) {
                                AbstractMethodDeclaration abstractMethodDeclarationDeclarationOf = ((SourceTypeBinding) methodBinding2.declaringClass).scope.referenceContext.declarationOf(methodBinding2);
                                recordSuppressWarnings(blockScope, abstractMethodDeclarationDeclarationOf.declarationSourceStart, abstractMethodDeclarationDeclarationOf.declarationSourceEnd, blockScope.compilerOptions().suppressWarnings);
                            }
                            long j2 = methodBinding2.tagBits & TagBits.AnnotationNullMASK;
                            if (j2 == TagBits.AnnotationNullMASK) {
                                blockScope.problemReporter().contradictoryNullAnnotations(this);
                                methodBinding2.tagBits &= -108086391056891905L;
                            }
                            if (j2 != 0 && methodBinding2.isConstructor()) {
                                if (blockScope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_8) {
                                    blockScope.problemReporter().nullAnnotationUnsupportedLocation(this);
                                }
                                methodBinding2.tagBits &= -108086391056891905L;
                            }
                            methodBinding2.defaultNullness = i8 | methodBinding2.defaultNullness;
                        } else if (iKind == 16) {
                            PackageBinding packageBinding = (PackageBinding) this.recipient;
                            packageBinding.tagBits = j | packageBinding.tagBits;
                        } else if (iKind != 2052) {
                        }
                        i = 4;
                    }
                    SourceTypeBinding sourceTypeBinding = (SourceTypeBinding) this.recipient;
                    if ((jDetectStandardAnnotation & 1152921504606846976L) == 0 || sourceTypeBinding.isAnnotationType()) {
                        sourceTypeBinding.tagBits = j | sourceTypeBinding.tagBits;
                    }
                    if ((jDetectStandardAnnotation & 1125899906842624L) != 0) {
                        TypeDeclaration typeDeclaration = sourceTypeBinding.scope.referenceContext;
                        if (blockScope.referenceCompilationUnit().types[i2] != typeDeclaration) {
                            i2 = typeDeclaration.declarationSourceStart;
                        }
                        recordSuppressWarnings(blockScope, i2, typeDeclaration.declarationSourceEnd, blockScope.compilerOptions().suppressWarnings);
                    }
                    sourceTypeBinding.defaultNullness = i8 | sourceTypeBinding.defaultNullness;
                } else {
                    LocalVariableBinding localVariableBinding = (LocalVariableBinding) this.recipient;
                    localVariableBinding.tagBits = j | localVariableBinding.tagBits;
                    if ((localVariableBinding.tagBits & TagBits.AnnotationNullMASK) == TagBits.AnnotationNullMASK) {
                        blockScope.problemReporter().contradictoryNullAnnotations(this);
                        localVariableBinding.tagBits &= -108086391056891905L;
                    }
                    if ((jDetectStandardAnnotation & 1125899906842624L) != 0) {
                        LocalDeclaration localDeclaration = localVariableBinding.declaration;
                        recordSuppressWarnings(blockScope, localDeclaration.declarationSourceStart, localDeclaration.declarationSourceEnd, blockScope.compilerOptions().suppressWarnings);
                    }
                }
                i = 4;
            }
            checkAnnotationTarget(this, blockScope, referenceBinding, (iKind == i && CharOperation.equals(((SourceTypeBinding) this.recipient).sourceName, TypeConstants.PACKAGE_INFO_NAME)) ? 16 : iKind, this.recipient, jDetectStandardAnnotation & TagBits.AnnotationNullMASK);
        }
        return this.resolvedType;
    }

    private static boolean isAnnotationTargetAllowed(Binding binding, BlockScope blockScope, TypeBinding typeBinding, int i, long j) {
        if (i == 1) {
            if ((137438953472L & j) != 0) {
                return true;
            }
            if ((j & 9007199254740992L) == 0) {
                return false;
            }
            FieldBinding fieldBinding = (FieldBinding) binding;
            return isTypeUseCompatible(((SourceTypeBinding) fieldBinding.declaringClass).scope.referenceContext.declarationOf(fieldBinding).type, blockScope);
        }
        if (i != 2) {
            if (i != 4) {
                if (i == 8) {
                    MethodBinding methodBinding = (MethodBinding) binding;
                    if (methodBinding.isConstructor()) {
                        return (9008298766368768L & j) != 0;
                    }
                    if ((274877906944L & j) != 0) {
                        return true;
                    }
                    return (j & 9007199254740992L) != 0 && isTypeUseCompatible(((MethodDeclaration) ((SourceTypeBinding) methodBinding.declaringClass).scope.referenceContext.declarationOf(methodBinding)).returnType, blockScope);
                }
                if (i == 16) {
                    if ((j & 8796093022208L) != 0) {
                        return true;
                    }
                    return blockScope.compilerOptions().sourceLevel <= ClassFileConstants.JDK1_6 && CharOperation.equals(((SourceTypeBinding) binding).sourceName, TypeConstants.PACKAGE_INFO_NAME);
                }
                if (i != 2052) {
                    if (i == 4100) {
                        return (27021597764222976L & j) != 0;
                    }
                    if (i != 16388) {
                        return false;
                    }
                    return (j & 9007199254740992L) != 0 || blockScope.compilerOptions().sourceLevel < ClassFileConstants.JDK1_8;
                }
            }
            ReferenceBinding referenceBinding = (ReferenceBinding) binding;
            if (referenceBinding.isAnnotationType()) {
                return (9011666020728832L & j) != 0;
            }
            if ((9007267974217728L & j) != 0) {
                return true;
            }
            return (j & 8796093022208L) != 0 && CharOperation.equals(referenceBinding.sourceName, TypeConstants.PACKAGE_INFO_NAME);
        }
        LocalVariableBinding localVariableBinding = (LocalVariableBinding) binding;
        if ((localVariableBinding.tagBits & 1024) != 0) {
            if ((549755813888L & j) != 0) {
                return true;
            }
            return (j & 9007199254740992L) != 0 && isTypeUseCompatible(localVariableBinding.declaration.type, blockScope);
        }
        if ((typeBinding.tagBits & 2199023255552L) != 0) {
            return true;
        }
        return (j & 9007199254740992L) != 0 && isTypeUseCompatible(localVariableBinding.declaration.type, blockScope);
    }

    public static boolean isAnnotationTargetAllowed(BlockScope blockScope, TypeBinding typeBinding, Binding binding) {
        long annotationTagBits = typeBinding.getAnnotationTagBits();
        if ((TagBits.AnnotationTargetMASK & annotationTagBits) == 0) {
            return true;
        }
        return isAnnotationTargetAllowed(binding, blockScope, typeBinding, binding.kind(), annotationTagBits);
    }

    static boolean isAnnotationTargetAllowed(Annotation annotation, BlockScope blockScope, TypeBinding typeBinding, int i) {
        long annotationTagBits = typeBinding.getAnnotationTagBits();
        if ((TagBits.AnnotationTargetMASK & annotationTagBits) == 0) {
            if (i == 4100 || i == 16388) {
                blockScope.problemReporter().explitAnnotationTargetRequired(annotation);
            }
            return true;
        }
        if ((TagBits.SE7AnnotationTargetMASK & annotationTagBits) == 0 && (27021597764222976L & annotationTagBits) != 0 && blockScope.compilerOptions().sourceLevel < ClassFileConstants.JDK1_8 && (i == 1 || i == 2 || i == 4 || i == 8 || i == 16 || i == 2052)) {
            blockScope.problemReporter().invalidUsageOfTypeAnnotations(annotation);
        }
        return isAnnotationTargetAllowed(annotation.recipient, blockScope, typeBinding, i, annotationTagBits);
    }

    static void checkAnnotationTarget(Annotation annotation, BlockScope blockScope, ReferenceBinding referenceBinding, int i, Binding binding, long j) {
        if (referenceBinding.isValidBinding() && !isAnnotationTargetAllowed(annotation, blockScope, referenceBinding, i)) {
            blockScope.problemReporter().disallowedTargetForAnnotation(annotation);
            if (binding instanceof TypeBinding) {
                ((TypeBinding) binding).tagBits &= ~j;
            }
        }
    }

    public static void checkForInstancesOfRepeatableWithRepeatingContainerAnnotation(BlockScope blockScope, ReferenceBinding referenceBinding, Annotation[] annotationArr) {
        MethodBinding[] methods = referenceBinding.getMethods(TypeConstants.VALUE);
        if (methods.length != 1) {
            return;
        }
        TypeBinding typeBinding = methods[0].returnType;
        if (typeBinding.isArrayType() && typeBinding.dimensions() == 1) {
            TypeBinding typeBindingElementsType = ((ArrayBinding) typeBinding).elementsType();
            if (typeBindingElementsType.isRepeatableAnnotationType()) {
                for (Annotation annotation : annotationArr) {
                    if (TypeBinding.equalsEquals(typeBindingElementsType, annotation.resolvedType)) {
                        blockScope.problemReporter().repeatableAnnotationWithRepeatingContainer(annotation, referenceBinding);
                        return;
                    }
                }
            }
        }
    }

    public static boolean isTypeUseCompatible(TypeReference typeReference, Scope scope) {
        return typeReference == null || (typeReference instanceof SingleTypeReference) || !(scope.getPackage(typeReference.getTypeName()) instanceof PackageBinding);
    }

    public static void isTypeUseCompatible(TypeReference typeReference, Scope scope, Annotation[] annotationArr) {
        if (annotationArr == null || typeReference == null || typeReference.getAnnotatableLevels() == 1 || scope.environment().globalOptions.sourceLevel < ClassFileConstants.JDK1_8) {
            return;
        }
        TypeBinding typeBindingLeafComponentType = typeReference.resolvedType == null ? null : typeReference.resolvedType.leafComponentType();
        if (typeBindingLeafComponentType == null || !typeBindingLeafComponentType.isNestedType()) {
            return;
        }
        for (Annotation annotation : annotationArr) {
            long annotationTagBits = annotation.resolvedType.getAnnotationTagBits();
            if ((9007199254740992L & annotationTagBits) != 0 && (annotationTagBits & TagBits.SE7AnnotationTargetMASK) == 0) {
                for (ReferenceBinding referenceBindingEnclosingType = (ReferenceBinding) typeBindingLeafComponentType; referenceBindingEnclosingType.isNestedType(); referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType()) {
                    if (referenceBindingEnclosingType.isStatic()) {
                        QualifiedTypeReference.rejectAnnotationsOnStaticMemberQualififer(scope, referenceBindingEnclosingType, new Annotation[]{annotation});
                        break;
                    } else {
                        if (annotation.hasNullBit(96)) {
                            scope.problemReporter().nullAnnotationUnsupportedLocation(annotation);
                            break;
                        }
                    }
                }
            }
        }
    }

    public boolean hasNullBit(int i) {
        return (this.resolvedType instanceof ReferenceBinding) && ((ReferenceBinding) this.resolvedType).hasNullBit(i);
    }

    public Annotation getPersistibleAnnotation() {
        return this.persistibleAnnotation;
    }

    public void setPersistibleAnnotation(ContainerAnnotation containerAnnotation) {
        this.persistibleAnnotation = containerAnnotation;
    }
}
