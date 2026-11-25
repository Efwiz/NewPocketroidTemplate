package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.checker.ClassicTypeCheckerStateKt;
import org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.checker.KotlinTypePreparator;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.DynamicTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: OverridingUtilTypeSystemContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001BQ\u0012\u0014\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u001c\b\u0002\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0018\u00010\f¢\u0006\u0002\u0010\u000fJ\u0018\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0016J\u0018\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u000eH\u0016R\"\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001f\u0010\u0002\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverridingUtilTypeSystemContext;", "Lorg/jetbrains/kotlin/types/checker/ClassicTypeSystemContext;", "matchingTypeConstructors", "", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "equalityAxioms", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeChecker$TypeConstructorEquality;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "kotlinTypePreparator", "Lorg/jetbrains/kotlin/types/checker/KotlinTypePreparator;", "customSubtype", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/types/KotlinType;", "", "(Ljava/util/Map;Lorg/jetbrains/kotlin/types/checker/KotlinTypeChecker$TypeConstructorEquality;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;Lorg/jetbrains/kotlin/types/checker/KotlinTypePreparator;Lkotlin/jvm/functions/Function2;)V", "getMatchingTypeConstructors", "()Ljava/util/Map;", "areEqualTypeConstructors", "c1", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "c2", "areEqualTypeConstructorsByAxioms", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "newTypeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "errorTypesEqualToAnything", "stubTypesEqualToAnything", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OverridingUtilTypeSystemContext implements ClassicTypeSystemContext {
    private final Function2<KotlinType, KotlinType, Boolean> customSubtype;
    private final KotlinTypeChecker.TypeConstructorEquality equalityAxioms;
    private final KotlinTypePreparator kotlinTypePreparator;
    private final KotlinTypeRefiner kotlinTypeRefiner;
    private final Map<TypeConstructor, TypeConstructor> matchingTypeConstructors;

    /* JADX WARN: Multi-variable type inference failed */
    public OverridingUtilTypeSystemContext(Map<TypeConstructor, ? extends TypeConstructor> map, KotlinTypeChecker.TypeConstructorEquality equalityAxioms, KotlinTypeRefiner kotlinTypeRefiner, KotlinTypePreparator kotlinTypePreparator, Function2<? super KotlinType, ? super KotlinType, Boolean> function2) {
        Intrinsics.checkNotNullParameter(equalityAxioms, "equalityAxioms");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        Intrinsics.checkNotNullParameter(kotlinTypePreparator, "kotlinTypePreparator");
        this.matchingTypeConstructors = map;
        this.equalityAxioms = equalityAxioms;
        this.kotlinTypeRefiner = kotlinTypeRefiner;
        this.kotlinTypePreparator = kotlinTypePreparator;
        this.customSubtype = function2;
    }

    public /* synthetic */ OverridingUtilTypeSystemContext(Map map, KotlinTypeChecker.TypeConstructorEquality typeConstructorEquality, KotlinTypeRefiner kotlinTypeRefiner, KotlinTypePreparator kotlinTypePreparator, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(map, typeConstructorEquality, kotlinTypeRefiner, kotlinTypePreparator, (i & 16) != 0 ? null : function2);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker anyType() {
        return ClassicTypeSystemContext.DefaultImpls.anyType(this);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.argumentsCount(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public SimpleTypeMarker arrayType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.arrayType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.asArgumentList(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.asCapturedType(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.asDefinitelyNotNullType(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.asDynamicType(this, flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.asFlexibleType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.asSimpleType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.asTypeArgument(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean canHaveUndefinedNullability(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.canHaveUndefinedNullability(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker captureFromArguments(SimpleTypeMarker simpleTypeMarker, CaptureStatus captureStatus) {
        return ClassicTypeSystemContext.DefaultImpls.captureFromArguments(this, simpleTypeMarker, captureStatus);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker captureFromExpression(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.captureFromExpression(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.captureStatus(this, capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean contains(KotlinTypeMarker kotlinTypeMarker, Function1<? super KotlinTypeMarker, Boolean> function1) {
        return ClassicTypeSystemContext.DefaultImpls.contains(this, kotlinTypeMarker, function1);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker convertToNonRaw(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.convertToNonRaw(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public CapturedTypeMarker createCapturedType(TypeArgumentMarker typeArgumentMarker, List<? extends KotlinTypeMarker> list, KotlinTypeMarker kotlinTypeMarker, CaptureStatus captureStatus) {
        return ClassicTypeSystemContext.DefaultImpls.createCapturedType(this, typeArgumentMarker, list, kotlinTypeMarker, captureStatus);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker createEmptySubstitutor() {
        return ClassicTypeSystemContext.DefaultImpls.createEmptySubstitutor(this);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public SimpleTypeMarker createErrorType(String str) {
        return ClassicTypeSystemContext.DefaultImpls.createErrorType(this, str);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public KotlinTypeMarker createFlexibleType(SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
        return ClassicTypeSystemContext.DefaultImpls.createFlexibleType(this, simpleTypeMarker, simpleTypeMarker2);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public SimpleTypeMarker createSimpleType(TypeConstructorMarker typeConstructorMarker, List<? extends TypeArgumentMarker> list, boolean z, boolean z2, List<? extends AnnotationMarker> list2) {
        return ClassicTypeSystemContext.DefaultImpls.createSimpleType(this, typeConstructorMarker, list, z, z2, list2);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public TypeArgumentMarker createStarProjection(TypeParameterMarker typeParameterMarker) {
        return ClassicTypeSystemContext.DefaultImpls.createStarProjection(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public StubTypeMarker createStubTypeForBuilderInference(TypeVariableMarker typeVariableMarker) {
        return ClassicTypeSystemContext.DefaultImpls.createStubTypeForBuilderInference(this, typeVariableMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public StubTypeMarker createStubTypeForTypeVariablesInSubtyping(TypeVariableMarker typeVariableMarker) {
        return ClassicTypeSystemContext.DefaultImpls.createStubTypeForTypeVariablesInSubtyping(this, typeVariableMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeSubstitutorMarker createSubstitutorForSuperTypes(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.createSubstitutorForSuperTypes(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public TypeArgumentMarker createTypeArgument(KotlinTypeMarker kotlinTypeMarker, TypeVariance typeVariance) {
        return ClassicTypeSystemContext.DefaultImpls.createTypeArgument(this, kotlinTypeMarker, typeVariance);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker createTypeWithAlternativeForIntersectionResult(KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2) {
        return ClassicTypeSystemContext.DefaultImpls.createTypeWithAlternativeForIntersectionResult(this, kotlinTypeMarker, kotlinTypeMarker2);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public KotlinTypeMarker createUninferredType(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.createUninferredType(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker defaultType(TypeVariableMarker typeVariableMarker) {
        return ClassicTypeSystemContext.DefaultImpls.defaultType(this, typeVariableMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker eraseContainingTypeParameters(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.eraseContainingTypeParameters(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public List<KotlinTypeMarker> extractArgumentsForFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.extractArgumentsForFunctionTypeOrSubtype(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(List<? extends SimpleTypeMarker> list) {
        return ClassicTypeSystemContext.DefaultImpls.findCommonIntegerLiteralTypesSuperType(this, list);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeConstructorMarker freshTypeConstructor(TypeVariableMarker typeVariableMarker) {
        return ClassicTypeSystemContext.DefaultImpls.freshTypeConstructor(this, typeVariableMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public FunctionTypeKind functionTypeKind(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.functionTypeKind(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public Object getAnnotationFirstArgumentValue(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
        return ClassicTypeSystemContext.DefaultImpls.getAnnotationFirstArgumentValue(this, kotlinTypeMarker, fqName);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker getApproximatedIntegerLiteralType(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getApproximatedIntegerLiteralType(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
        return ClassicTypeSystemContext.DefaultImpls.getArgument(this, kotlinTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getArguments(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getAttributes(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext
    public KotlinBuiltIns getBuiltIns() {
        return ClassicTypeSystemContext.DefaultImpls.getBuiltIns(this);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getClassFqNameUnsafe(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getCustomAttributes(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker getFunctionTypeFromSupertypes(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getFunctionTypeFromSupertypes(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public Name getName(TypeParameterMarker typeParameterMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getName(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeConstructorMarker getNonReflectFunctionTypeConstructor(int i, FunctionTypeKind functionTypeKind) {
        return ClassicTypeSystemContext.DefaultImpls.getNonReflectFunctionTypeConstructor(this, i, functionTypeKind);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeVariableTypeConstructorMarker getOriginalTypeVariable(StubTypeMarker stubTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getOriginalTypeVariable(this, stubTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
        return ClassicTypeSystemContext.DefaultImpls.getParameter(this, typeConstructorMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeParameterMarker> getParameters(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getParameters(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public PrimitiveType getPrimitiveArrayType(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getPrimitiveArrayType(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public PrimitiveType getPrimitiveType(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getPrimitiveType(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeConstructorMarker getReflectFunctionTypeConstructor(int i, FunctionTypeKind functionTypeKind) {
        return ClassicTypeSystemContext.DefaultImpls.getReflectFunctionTypeConstructor(this, i, functionTypeKind);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker typeParameterMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getRepresentativeUpperBound(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getSubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getSubstitutedUnderlyingType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getType(this, typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker getTypeConstructor(TypeParameterMarker typeParameterMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getTypeConstructor(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getTypeParameter(this, typeVariableTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getTypeParameterClassifier(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getUnsubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getUnsubstitutedUnderlyingType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
        return ClassicTypeSystemContext.DefaultImpls.getUpperBound(this, typeParameterMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getUpperBounds(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public List<Pair<Name, SimpleTypeMarker>> getValueClassProperties(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getValueClassProperties(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getVariance(this, typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
        return ClassicTypeSystemContext.DefaultImpls.getVariance(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean hasAnnotation(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
        return ClassicTypeSystemContext.DefaultImpls.hasAnnotation(this, kotlinTypeMarker, fqName);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.hasCustomAttributes(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean hasExactAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.hasExactAnnotation(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean hasNoInferAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.hasNoInferAnnotation(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.hasRecursiveBounds(this, typeParameterMarker, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemOptimizationContext
    public boolean identicalArguments(SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
        return ClassicTypeSystemContext.DefaultImpls.identicalArguments(this, simpleTypeMarker, simpleTypeMarker2);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> list) {
        return ClassicTypeSystemContext.DefaultImpls.intersectTypes(this, list);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    /* JADX INFO: renamed from: intersectTypes */
    public SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> list) {
        return ClassicTypeSystemContext.DefaultImpls.m7476intersectTypes((ClassicTypeSystemContext) this, list);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isAnonymous(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isAnyConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isArrayOrNullableArray(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isArrayOrNullableArray(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isBuiltinFunctionTypeOrSubtype(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isBuiltinFunctionTypeOrSubtype(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isCapturedTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isCapturedTypeConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isClassTypeConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isCommonFinalClassConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isContainedInInvariantOrContravariantPositions(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isContainedInInvariantOrContravariantPositions(this, typeVariableTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isDenotable(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isError(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isError(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean isExtensionFunction(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isExtensionFunction(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isExtensionFunctionType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isExtensionFunctionType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isFinalClassConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isFinalClassOrEnumEntryOrAnnotationClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isFinalClassOrEnumEntryOrAnnotationClassConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isFunctionOrKFunctionWithAnySuspendability(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isFunctionOrKFunctionWithAnySuspendability(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInlineClass(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isInlineClass(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInnerClass(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isInnerClass(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isIntegerConstantOperatorTypeConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isIntegerLiteralConstantTypeConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isIntegerLiteralTypeConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isInterface(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInterfaceOrAnnotationClass(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isInterfaceOrAnnotationClass(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isIntersection(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isK2() {
        return ClassicTypeSystemContext.DefaultImpls.isK2(this);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isLocalType(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isMarkedNullable(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isMultiFieldValueClass(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isMultiFieldValueClass(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNotNullTypeParameter(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isNotNullTypeParameter(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isNothingConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isNullableType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isOldCapturedType(this, capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isPrimitiveType(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isProjectionNotNull(this, capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isRawType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isReified(TypeParameterMarker typeParameterMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isReified(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isSignedOrUnsignedNumberType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isSignedOrUnsignedNumberType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isSingleClassifierType(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isSpecial(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isSpecial(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isStarProjection(this, typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isStubType(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isStubTypeForBuilderInference(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isStubTypeForVariableInSubtyping(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isTypeParameterTypeConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isTypeVariable(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isTypeVariable(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isTypeVariableType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isUnderKotlinPackage(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isUnderKotlinPackage(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isUninferredParameter(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isUnit(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isUnit(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean isUnitTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.isUnitTypeConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.lowerBound(this, flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.lowerType(this, capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.makeDefinitelyNotNullOrNotNull(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.makeSimpleTypeDefinitelyNotNullOrNotNull(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker nothingType() {
        return ClassicTypeSystemContext.DefaultImpls.nothingType(this);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker nullableAnyType() {
        return ClassicTypeSystemContext.DefaultImpls.nullableAnyType(this);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public SimpleTypeMarker nullableNothingType() {
        return ClassicTypeSystemContext.DefaultImpls.nullableNothingType(this);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.original(this, definitelyNotNullTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.parametersCount(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.possibleIntegerTypes(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.projection(this, capturedTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker removeAnnotations(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.removeAnnotations(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker removeExactAnnotation(KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.removeExactAnnotation(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, List<? extends TypeArgumentMarker> list) {
        return ClassicTypeSystemContext.DefaultImpls.replaceArguments(this, simpleTypeMarker, list);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public SimpleTypeMarker replaceArguments(SimpleTypeMarker simpleTypeMarker, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> function1) {
        return ClassicTypeSystemContext.DefaultImpls.replaceArguments(this, simpleTypeMarker, function1);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public KotlinTypeMarker replaceCustomAttributes(KotlinTypeMarker kotlinTypeMarker, List<? extends AnnotationMarker> list) {
        return ClassicTypeSystemContext.DefaultImpls.replaceCustomAttributes(this, kotlinTypeMarker, list);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.replaceType(this, typeArgumentMarker, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker kotlinTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.safeSubstitute(this, typeSubstitutorMarker, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker singleBestRepresentative(Collection<? extends KotlinTypeMarker> collection) {
        return ClassicTypeSystemContext.DefaultImpls.singleBestRepresentative(this, collection);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.substitutionSupertypePolicy(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.supertypes(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public SimpleTypeMarker toErrorType(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.toErrorType(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.typeConstructor((ClassicTypeSystemContext) this, capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.typeConstructor(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeArgumentMarker typeConstructorProjection(CapturedTypeMarker capturedTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.typeConstructorProjection(this, capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public int typeDepth(SimpleTypeMarker simpleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.typeDepth(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public TypeParameterMarker typeParameter(CapturedTypeMarker capturedTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.typeParameter(this, capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
        return ClassicTypeSystemContext.DefaultImpls.typeSubstitutorByTypeConstructor(this, map);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public List<AnnotationMarker> unionTypeAttributes(List<? extends KotlinTypeMarker> list) {
        return ClassicTypeSystemContext.DefaultImpls.unionTypeAttributes(this, list);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
        return ClassicTypeSystemContext.DefaultImpls.unwrapStubTypeVariableConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.upperBound(this, flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public int upperBoundCount(TypeParameterMarker typeParameterMarker) {
        return ClassicTypeSystemContext.DefaultImpls.upperBoundCount(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public boolean useRefinedBoundsForTypeVariableInFlexiblePosition() {
        return ClassicTypeSystemContext.DefaultImpls.useRefinedBoundsForTypeVariableInFlexiblePosition(this);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext
    public KotlinTypeMarker withNotNullProjection(CapturedTypeMarker capturedTypeMarker) {
        return ClassicTypeSystemContext.DefaultImpls.withNotNullProjection(this, capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        return ClassicTypeSystemContext.DefaultImpls.withNullability(this, kotlinTypeMarker, z);
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
        return ClassicTypeSystemContext.DefaultImpls.withNullability((ClassicTypeSystemContext) this, simpleTypeMarker, z);
    }

    public final Map<TypeConstructor, TypeConstructor> getMatchingTypeConstructors() {
        return this.matchingTypeConstructors;
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
        Intrinsics.checkNotNullParameter(c1, "c1");
        Intrinsics.checkNotNullParameter(c2, "c2");
        if (!(c1 instanceof TypeConstructor)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (c2 instanceof TypeConstructor) {
            return ClassicTypeSystemContext.DefaultImpls.areEqualTypeConstructors(this, c1, c2) || areEqualTypeConstructorsByAxioms((TypeConstructor) c1, (TypeConstructor) c2);
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    @Override // org.jetbrains.kotlin.types.checker.ClassicTypeSystemContext, org.jetbrains.kotlin.types.model.TypeCheckerProviderContext
    public TypeCheckerState newTypeCheckerState(boolean errorTypesEqualToAnything, boolean stubTypesEqualToAnything) {
        if (this.customSubtype == null) {
            return ClassicTypeCheckerStateKt.createClassicTypeCheckerState(errorTypesEqualToAnything, stubTypesEqualToAnything, this, this.kotlinTypePreparator, this.kotlinTypeRefiner);
        }
        return new TypeCheckerState(errorTypesEqualToAnything, stubTypesEqualToAnything, this, this.kotlinTypePreparator, this.kotlinTypeRefiner) { // from class: org.jetbrains.kotlin.resolve.OverridingUtilTypeSystemContext.newTypeCheckerState.1
            final /* synthetic */ OverridingUtilTypeSystemContext this$0;

            {
                this.this$0 = this;
                OverridingUtilTypeSystemContext overridingUtilTypeSystemContext = this;
                KotlinTypePreparator kotlinTypePreparator = kotlinTypePreparator;
                KotlinTypeRefiner kotlinTypeRefiner = kotlinTypeRefiner;
            }

            @Override // org.jetbrains.kotlin.types.TypeCheckerState
            public boolean customIsSubtypeOf(KotlinTypeMarker subType, KotlinTypeMarker superType) {
                Intrinsics.checkNotNullParameter(subType, "subType");
                Intrinsics.checkNotNullParameter(superType, "superType");
                if (!(subType instanceof KotlinType)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                if (superType instanceof KotlinType) {
                    return ((Boolean) this.this$0.customSubtype.invoke(subType, superType)).booleanValue();
                }
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
        };
    }

    private final boolean areEqualTypeConstructorsByAxioms(TypeConstructor a, TypeConstructor b) {
        if (this.equalityAxioms.equals(a, b)) {
            return true;
        }
        Map<TypeConstructor, TypeConstructor> map = this.matchingTypeConstructors;
        if (map == null) {
            return false;
        }
        TypeConstructor typeConstructor = map.get(a);
        TypeConstructor typeConstructor2 = this.matchingTypeConstructors.get(b);
        if (typeConstructor == null || !Intrinsics.areEqual(typeConstructor, b)) {
            return typeConstructor2 != null && Intrinsics.areEqual(typeConstructor2, a);
        }
        return true;
    }
}
