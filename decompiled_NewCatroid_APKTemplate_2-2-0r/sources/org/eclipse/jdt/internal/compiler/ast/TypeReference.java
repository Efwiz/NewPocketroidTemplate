package org.eclipse.jdt.internal.compiler.ast;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.AnnotationContext;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.Substitution;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public abstract class TypeReference extends Expression {
    public static final TypeReference[] NO_TYPE_ARGUMENTS = new TypeReference[0];
    public Annotation[][] annotations = null;

    public enum AnnotationPosition {
        MAIN_TYPE,
        LEAF_TYPE,
        ANY;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static AnnotationPosition[] valuesCustom() {
            AnnotationPosition[] annotationPositionArrValuesCustom = values();
            int length = annotationPositionArrValuesCustom.length;
            AnnotationPosition[] annotationPositionArr = new AnnotationPosition[length];
            System.arraycopy(annotationPositionArrValuesCustom, 0, annotationPositionArr, 0, length);
            return annotationPositionArr;
        }
    }

    public void aboutToResolve(Scope scope) {
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        return flowInfo;
    }

    public abstract TypeReference augmentTypeWithAdditionalDimensions(int i, Annotation[][] annotationArr, boolean z);

    public void checkBounds(Scope scope) {
    }

    public int dimensions() {
        return 0;
    }

    public int extraDimensions() {
        return 0;
    }

    public int getAnnotatableLevels() {
        return 1;
    }

    public Annotation[][] getAnnotationsOnDimensions(boolean z) {
        return null;
    }

    public abstract char[] getLastToken();

    public TypeReference[][] getTypeArguments() {
        return null;
    }

    protected abstract TypeBinding getTypeBinding(Scope scope);

    public abstract char[][] getTypeName();

    public boolean isBaseTypeReference() {
        return false;
    }

    public boolean isParameterizedTypeReference() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public boolean isTypeReference() {
        return true;
    }

    public boolean isUnionType() {
        return false;
    }

    public boolean isWildcard() {
        return false;
    }

    public void setAnnotationsOnDimensions(Annotation[][] annotationArr) {
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public abstract void traverse(ASTVisitor aSTVisitor, BlockScope blockScope);

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public abstract void traverse(ASTVisitor aSTVisitor, ClassScope classScope);

    static class AnnotationCollector extends ASTVisitor {
        List annotationContexts;
        Annotation[][] annotationsOnDimensions;
        Wildcard currentWildcard;
        int dimensions;
        int info;
        int info2;
        LocalVariableBinding localVariable;
        int targetType;
        Expression typeReference;

        public AnnotationCollector(TypeParameter typeParameter, int i, int i2, List list) {
            this.info = 0;
            this.info2 = 0;
            this.annotationContexts = list;
            this.typeReference = typeParameter.type;
            this.targetType = i;
            this.info = i2;
        }

        public AnnotationCollector(LocalDeclaration localDeclaration, int i, LocalVariableBinding localVariableBinding, List list) {
            this.info = 0;
            this.info2 = 0;
            this.annotationContexts = list;
            this.typeReference = localDeclaration.type;
            this.targetType = i;
            this.localVariable = localVariableBinding;
        }

        public AnnotationCollector(LocalDeclaration localDeclaration, int i, int i2, List list) {
            this.info = 0;
            this.info2 = 0;
            this.annotationContexts = list;
            this.typeReference = localDeclaration.type;
            this.targetType = i;
            this.info = i2;
        }

        public AnnotationCollector(TypeReference typeReference, int i, List list) {
            this.info = 0;
            this.info2 = 0;
            this.annotationContexts = list;
            this.typeReference = typeReference;
            this.targetType = i;
        }

        public AnnotationCollector(Expression expression, int i, int i2, List list) {
            this.info2 = 0;
            this.annotationContexts = list;
            this.typeReference = expression;
            this.info = i2;
            this.targetType = i;
        }

        public AnnotationCollector(TypeReference typeReference, int i, int i2, int i3, List list) {
            this.annotationContexts = list;
            this.typeReference = typeReference;
            this.info = i2;
            this.targetType = i;
            this.info2 = i3;
        }

        public AnnotationCollector(TypeReference typeReference, int i, int i2, List list, Annotation[][] annotationArr, int i3) {
            this.info2 = 0;
            this.annotationContexts = list;
            this.typeReference = typeReference;
            this.info = i2;
            this.targetType = i;
            this.annotationsOnDimensions = annotationArr;
            this.dimensions = i3;
        }

        /* JADX WARN: Code duplicated, block: B:21:0x0043  */
        /* JADX WARN: Code duplicated, block: B:22:0x004c  */
        private boolean internalVisit(Annotation annotation) {
            AnnotationContext annotationContext;
            if (annotation.isRuntimeTypeInvisible()) {
                annotationContext = new AnnotationContext(annotation, this.typeReference, this.targetType, 2);
            } else {
                annotationContext = annotation.isRuntimeTypeVisible() ? new AnnotationContext(annotation, this.typeReference, this.targetType, 1) : null;
            }
            if (annotationContext != null) {
                annotationContext.wildcard = this.currentWildcard;
                int i = this.targetType;
                if (i == 0 || i == 1 || i == 22 || i == 23) {
                    annotationContext.info = this.info;
                } else {
                    switch (i) {
                        case 16:
                            annotationContext.info = this.info;
                            break;
                        case 17:
                        case 18:
                            annotationContext.info2 = this.info2;
                            annotationContext.info = this.info;
                            break;
                        default:
                            switch (i) {
                                case 64:
                                case 65:
                                    annotationContext.variableBinding = this.localVariable;
                                    break;
                                case 66:
                                case 67:
                                case 68:
                                case 69:
                                case 70:
                                    annotationContext.info = this.info;
                                    break;
                                case 71:
                                case 72:
                                case 73:
                                case 74:
                                case 75:
                                    annotationContext.info2 = this.info2;
                                    annotationContext.info = this.info;
                                    break;
                            }
                            break;
                    }
                }
                this.annotationContexts.add(annotationContext);
            }
            return true;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(MarkerAnnotation markerAnnotation, BlockScope blockScope) {
            return internalVisit(markerAnnotation);
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(NormalAnnotation normalAnnotation, BlockScope blockScope) {
            return internalVisit(normalAnnotation);
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(SingleMemberAnnotation singleMemberAnnotation, BlockScope blockScope) {
            return internalVisit(singleMemberAnnotation);
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(Wildcard wildcard, BlockScope blockScope) {
            this.currentWildcard = wildcard;
            return true;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(Argument argument, BlockScope blockScope) {
            if ((argument.bits & 536870912) == 0) {
                return true;
            }
            int i = this.localVariable.initializationCount;
            for (int i2 = 0; i2 < i; i2++) {
                int i3 = i2 << 1;
                if (this.localVariable.initializationPCs[i3] != this.localVariable.initializationPCs[i3 + 1]) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(Argument argument, ClassScope classScope) {
            if ((argument.bits & 536870912) == 0) {
                return true;
            }
            int i = this.localVariable.initializationCount;
            for (int i2 = 0; i2 < i; i2++) {
                int i3 = i2 << 1;
                if (this.localVariable.initializationPCs[i3] != this.localVariable.initializationPCs[i3 + 1]) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(LocalDeclaration localDeclaration, BlockScope blockScope) {
            int i = this.localVariable.initializationCount;
            for (int i2 = 0; i2 < i; i2++) {
                int i3 = i2 << 1;
                if (this.localVariable.initializationPCs[i3] != this.localVariable.initializationPCs[i3 + 1]) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public void endVisit(Wildcard wildcard, BlockScope blockScope) {
            this.currentWildcard = null;
        }
    }

    public static final TypeReference baseTypeReference(int i, int i2, Annotation[][] annotationArr) {
        if (i2 == 0) {
            switch (i) {
                case 2:
                    return new SingleTypeReference(TypeBinding.CHAR.simpleName, 0L);
                case 3:
                    return new SingleTypeReference(TypeBinding.BYTE.simpleName, 0L);
                case 4:
                    return new SingleTypeReference(TypeBinding.SHORT.simpleName, 0L);
                case 5:
                    return new SingleTypeReference(TypeBinding.BOOLEAN.simpleName, 0L);
                case 6:
                    return new SingleTypeReference(TypeBinding.VOID.simpleName, 0L);
                case 7:
                default:
                    return new SingleTypeReference(TypeBinding.LONG.simpleName, 0L);
                case 8:
                    return new SingleTypeReference(TypeBinding.DOUBLE.simpleName, 0L);
                case 9:
                    return new SingleTypeReference(TypeBinding.FLOAT.simpleName, 0L);
                case 10:
                    return new SingleTypeReference(TypeBinding.INT.simpleName, 0L);
            }
        }
        switch (i) {
            case 2:
                return new ArrayTypeReference(TypeBinding.CHAR.simpleName, i2, annotationArr, 0L);
            case 3:
                return new ArrayTypeReference(TypeBinding.BYTE.simpleName, i2, annotationArr, 0L);
            case 4:
                return new ArrayTypeReference(TypeBinding.SHORT.simpleName, i2, annotationArr, 0L);
            case 5:
                return new ArrayTypeReference(TypeBinding.BOOLEAN.simpleName, i2, annotationArr, 0L);
            case 6:
                return new ArrayTypeReference(TypeBinding.VOID.simpleName, i2, annotationArr, 0L);
            case 7:
            default:
                return new ArrayTypeReference(TypeBinding.LONG.simpleName, i2, annotationArr, 0L);
            case 8:
                return new ArrayTypeReference(TypeBinding.DOUBLE.simpleName, i2, annotationArr, 0L);
            case 9:
                return new ArrayTypeReference(TypeBinding.FLOAT.simpleName, i2, annotationArr, 0L);
            case 10:
                return new ArrayTypeReference(TypeBinding.INT.simpleName, i2, annotationArr, 0L);
        }
    }

    public static final TypeReference baseTypeReference(int i, int i2) {
        return baseTypeReference(i, i2, null);
    }

    protected Annotation[][] getMergedAnnotationsOnDimensions(int i, Annotation[][] annotationArr) {
        Annotation[][] annotationsOnDimensions = getAnnotationsOnDimensions(true);
        int iDimensions = dimensions();
        if (annotationsOnDimensions == null && annotationArr == null) {
            return null;
        }
        int i2 = i + iDimensions;
        Annotation[][] annotationArr2 = new Annotation[i2][];
        int i3 = 0;
        if (annotationsOnDimensions != null) {
            for (int i4 = 0; i4 < iDimensions; i4++) {
                annotationArr2[i4] = annotationsOnDimensions[i4];
            }
        }
        if (annotationArr != null) {
            while (iDimensions < i2) {
                annotationArr2[iDimensions] = annotationArr[i3];
                iDimensions++;
                i3++;
            }
        }
        return annotationArr2;
    }

    public AnnotationContext[] getAllAnnotationContexts(int i) {
        ArrayList arrayList = new ArrayList();
        traverse(new AnnotationCollector(this, i, arrayList), (BlockScope) null);
        return (AnnotationContext[]) arrayList.toArray(new AnnotationContext[arrayList.size()]);
    }

    public void getAllAnnotationContexts(int i, int i2, List list) {
        traverse(new AnnotationCollector(this, i, i2, list), (BlockScope) null);
    }

    public void getAllAnnotationContexts(int i, int i2, List list, Annotation[] annotationArr) {
        AnnotationCollector annotationCollector = new AnnotationCollector(this, i, i2, list);
        int length = annotationArr == null ? 0 : annotationArr.length;
        for (int i3 = 0; i3 < length; i3++) {
            annotationArr[i3].traverse(annotationCollector, (BlockScope) null);
        }
        traverse(annotationCollector, (BlockScope) null);
    }

    public void getAllAnnotationContexts(int i, int i2, List list, Annotation[][] annotationArr, int i3) {
        AnnotationCollector annotationCollector = new AnnotationCollector(this, i, i2, list, annotationArr, i3);
        traverse(annotationCollector, (BlockScope) null);
        if (annotationArr != null) {
            for (Annotation[] annotationArr2 : annotationArr) {
                if (annotationArr2 != null) {
                    for (Annotation annotation : annotationArr2) {
                        annotation.traverse(annotationCollector, (BlockScope) null);
                    }
                }
            }
        }
    }

    public void getAllAnnotationContexts(int i, int i2, int i3, List list) {
        traverse(new AnnotationCollector(this, i, i2, i3, list), (BlockScope) null);
    }

    public void getAllAnnotationContexts(int i, List list) {
        traverse(new AnnotationCollector(this, i, list), (BlockScope) null);
    }

    public Annotation[][] getAnnotationsOnDimensions() {
        return getAnnotationsOnDimensions(false);
    }

    public char[][] getParameterizedTypeName() {
        return getTypeName();
    }

    protected TypeBinding internalResolveType(Scope scope, int i) {
        TypeBinding typeBindingClosestMatch;
        this.constant = Constant.NotAConstant;
        if (this.resolvedType != null) {
            if (this.resolvedType.isValidBinding()) {
                return this.resolvedType;
            }
            int iProblemId = this.resolvedType.problemId();
            if ((iProblemId == 1 || iProblemId == 2 || iProblemId == 5) && (typeBindingClosestMatch = this.resolvedType.closestMatch()) != null) {
                return scope.environment().convertToRawType(typeBindingClosestMatch, false);
            }
            return null;
        }
        TypeBinding typeBinding = getTypeBinding(scope);
        this.resolvedType = typeBinding;
        if (typeBinding == null) {
            return null;
        }
        boolean zIsValidBinding = typeBinding.isValidBinding();
        if (!zIsValidBinding) {
            reportInvalidType(scope);
            int iProblemId2 = typeBinding.problemId();
            if ((iProblemId2 != 1 && iProblemId2 != 2 && iProblemId2 != 5) || (typeBinding = typeBinding.closestMatch()) == null) {
                return null;
            }
        }
        if (typeBinding.isArrayType() && ((ArrayBinding) typeBinding).leafComponentType == TypeBinding.VOID) {
            scope.problemReporter().cannotAllocateVoidArray(this);
            return null;
        }
        if (!(this instanceof QualifiedTypeReference) && isTypeUseDeprecated(typeBinding, scope)) {
            reportDeprecatedType(typeBinding, scope);
        }
        TypeBinding typeBindingConvertToRawType = scope.environment().convertToRawType(typeBinding, false);
        if (typeBindingConvertToRawType.leafComponentType().isRawType() && (this.bits & 1073741824) == 0 && scope.compilerOptions().getSeverity(CompilerOptions.RawTypeReference) != 256) {
            scope.problemReporter().rawTypeReference(this, typeBindingConvertToRawType);
        }
        if (!zIsValidBinding) {
            resolveAnnotations(scope, 0);
            return typeBindingConvertToRawType;
        }
        this.resolvedType = typeBindingConvertToRawType;
        resolveAnnotations(scope, i);
        return this.resolvedType;
    }

    public boolean isVarargs() {
        return (this.bits & 16384) != 0;
    }

    protected void reportDeprecatedType(TypeBinding typeBinding, Scope scope, int i) {
        scope.problemReporter().deprecatedType(typeBinding, this, i);
    }

    protected void reportDeprecatedType(TypeBinding typeBinding, Scope scope) {
        scope.problemReporter().deprecatedType(typeBinding, this, Integer.MAX_VALUE);
    }

    protected void reportInvalidType(Scope scope) {
        scope.problemReporter().invalidType(this, this.resolvedType);
    }

    public TypeBinding resolveSuperType(ClassScope classScope) {
        TypeBinding typeBindingResolveType = resolveType(classScope);
        if (typeBindingResolveType == null) {
            return null;
        }
        if (!typeBindingResolveType.isTypeVariable()) {
            return typeBindingResolveType;
        }
        if (this.resolvedType.isValidBinding()) {
            this.resolvedType = new ProblemReferenceBinding(getTypeName(), (ReferenceBinding) this.resolvedType, 9);
            reportInvalidType(classScope);
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public final TypeBinding resolveType(BlockScope blockScope) {
        return resolveType(blockScope, true);
    }

    public TypeBinding resolveType(BlockScope blockScope, boolean z) {
        return resolveType(blockScope, z, 0);
    }

    public TypeBinding resolveType(BlockScope blockScope, boolean z, int i) {
        return internalResolveType(blockScope, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(ClassScope classScope) {
        return resolveType(classScope, 0);
    }

    public TypeBinding resolveType(ClassScope classScope, int i) {
        return internalResolveType(classScope, i);
    }

    public TypeBinding resolveTypeArgument(BlockScope blockScope, ReferenceBinding referenceBinding, int i) {
        return resolveType(blockScope, true, 64);
    }

    public TypeBinding resolveTypeArgument(ClassScope classScope, ReferenceBinding referenceBinding, int i) {
        SourceTypeBinding sourceTypeBinding = classScope.referenceContext.binding;
        boolean z = false;
        try {
            if (sourceTypeBinding.isHierarchyBeingConnected()) {
                sourceTypeBinding.tagBits |= 524288;
                z = true;
            }
            return resolveType(classScope, 64);
        } finally {
            if (z) {
                sourceTypeBinding.tagBits = (-524289) & sourceTypeBinding.tagBits;
            }
        }
    }

    protected void resolveAnnotations(Scope scope, int i) {
        BlockScope blockScopeTypeAnnotationsResolutionScope;
        long[] jArr;
        Annotation[][] annotationsOnDimensions = getAnnotationsOnDimensions();
        if ((this.annotations != null || annotationsOnDimensions != null) && (blockScopeTypeAnnotationsResolutionScope = Scope.typeAnnotationsResolutionScope(scope)) != null) {
            int iDimensions = dimensions();
            if (this.annotations != null) {
                TypeBinding typeBindingResolveAnnotations = resolveAnnotations(blockScopeTypeAnnotationsResolutionScope, this.annotations, this.resolvedType.leafComponentType());
                if (iDimensions > 0) {
                    typeBindingResolveAnnotations = scope.environment().createArrayType(typeBindingResolveAnnotations, iDimensions);
                }
                this.resolvedType = typeBindingResolveAnnotations;
            }
            if (annotationsOnDimensions != null) {
                this.resolvedType = resolveAnnotations(blockScopeTypeAnnotationsResolutionScope, annotationsOnDimensions, this.resolvedType);
                if ((this.resolvedType instanceof ArrayBinding) && (jArr = ((ArrayBinding) this.resolvedType).nullTagBitsPerDimension) != null) {
                    for (int i2 = 0; i2 < iDimensions; i2++) {
                        if ((jArr[i2] & TagBits.AnnotationNullMASK) == TagBits.AnnotationNullMASK) {
                            scope.problemReporter().contradictoryNullAnnotations(annotationsOnDimensions[i2]);
                            jArr[i2] = 0;
                        }
                    }
                }
            }
        }
        if (!scope.compilerOptions().isAnnotationBasedNullAnalysisEnabled || this.resolvedType == null || (this.resolvedType.tagBits & TagBits.AnnotationNullMASK) != 0 || this.resolvedType.isTypeVariable() || this.resolvedType.isWildcard() || i == 0 || !scope.hasDefaultNullnessFor(i)) {
            return;
        }
        if (i == 256 && this.resolvedType.f626id == 1) {
            scope.problemReporter().implicitObjectBoundNoNullDefault(this);
        } else {
            LookupEnvironment lookupEnvironmentEnvironment = scope.environment();
            this.resolvedType = lookupEnvironmentEnvironment.createAnnotatedType(this.resolvedType, new AnnotationBinding[]{lookupEnvironmentEnvironment.getNonNullAnnotation()});
        }
    }

    protected void checkIllegalNullAnnotations(Scope scope, TypeReference[] typeReferenceArr) {
        if (!scope.environment().usesNullTypeAnnotations() || typeReferenceArr == null) {
            return;
        }
        for (TypeReference typeReference : typeReferenceArr) {
            if (typeReference.resolvedType != null) {
                typeReference.checkIllegalNullAnnotation(scope);
            }
        }
    }

    protected void checkNullConstraints(Scope scope, Substitution substitution, TypeBinding[] typeBindingArr, int i) {
        if (typeBindingArr != null && typeBindingArr.length > i) {
            TypeBinding typeBinding = typeBindingArr[i];
            if (typeBinding.hasNullTypeAnnotations() && NullAnnotationMatching.analyse(typeBinding, this.resolvedType, null, substitution, -1, null, NullAnnotationMatching.CheckMode.BOUND_CHECK).isAnyMismatch()) {
                scope.problemReporter().nullityMismatchTypeArgument(typeBinding, this.resolvedType, this);
            }
        }
        checkIllegalNullAnnotation(scope);
    }

    protected void checkIllegalNullAnnotation(Scope scope) {
        if (this.resolvedType.leafComponentType().isBaseType() && hasNullTypeAnnotation(AnnotationPosition.LEAF_TYPE)) {
            scope.problemReporter().illegalAnnotationForBaseType(this, this.annotations[0], this.resolvedType.tagBits & TagBits.AnnotationNullMASK);
        }
    }

    public Annotation findAnnotation(long j) {
        Annotation[] annotationArr;
        Annotation[][] annotationArr2 = this.annotations;
        if (annotationArr2 == null || (annotationArr = annotationArr2[annotationArr2.length - 1]) == null) {
            return null;
        }
        int i = j == 72057594037927936L ? 32 : 64;
        for (int i2 = 0; i2 < annotationArr.length; i2++) {
            Annotation annotation = annotationArr[i2];
            if (annotation != null && annotation.hasNullBit(i)) {
                return annotationArr[i2];
            }
        }
        return null;
    }

    public boolean hasNullTypeAnnotation(AnnotationPosition annotationPosition) {
        if (this.annotations != null) {
            if (annotationPosition == AnnotationPosition.MAIN_TYPE) {
                Annotation[][] annotationArr = this.annotations;
                return containsNullAnnotation(annotationArr[annotationArr.length - 1]);
            }
            for (Annotation[] annotationArr2 : this.annotations) {
                if (containsNullAnnotation(annotationArr2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsNullAnnotation(Annotation[] annotationArr) {
        if (annotationArr != null) {
            for (Annotation annotation : annotationArr) {
                if (annotation != null && annotation.hasNullBit(96)) {
                    return true;
                }
            }
        }
        return false;
    }

    public TypeReference[] getTypeReferences() {
        return new TypeReference[]{this};
    }
}
