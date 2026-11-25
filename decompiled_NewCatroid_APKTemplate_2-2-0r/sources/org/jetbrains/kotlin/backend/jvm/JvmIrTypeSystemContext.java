package org.jetbrains.kotlin.backend.jvm;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.ir.IrJvmFlexibleType;
import org.jetbrains.kotlin.backend.jvm.ir.IrJvmFlexibleTypeKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: JvmIrTypeSystemContext.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\tH\u0016J\f\u0010\n\u001a\u00020\u000b*\u00020\tH\u0016J\f\u0010\f\u001a\u00020\r*\u00020\bH\u0016J\f\u0010\u000e\u001a\u00020\r*\u00020\bH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmIrTypeSystemContext;", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;)V", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "asFlexibleType", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "isMarkedNullable", "", "lowerBound", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "upperBound", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmIrTypeSystemContext implements IrTypeSystemContext {
    private final IrBuiltIns irBuiltIns;

    public JvmIrTypeSystemContext(IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        this.irBuiltIns = irBuiltIns;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public IrSimpleType anyType() {
        return IrTypeSystemContext.DefaultImpls.anyType(this);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean areEqualTypeConstructors(TypeConstructorMarker typeConstructorMarker, TypeConstructorMarker typeConstructorMarker2) {
        return IrTypeSystemContext.DefaultImpls.areEqualTypeConstructors(this, typeConstructorMarker, typeConstructorMarker2);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.argumentsCount(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public SimpleTypeMarker arrayType(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.arrayType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrSimpleType asArgumentList(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.asArgumentList(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.asCapturedType(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.asDefinitelyNotNullType(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrDynamicType asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.asDynamicType(this, flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.asSimpleType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrTypeArgument asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.asTypeArgument(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean canHaveUndefinedNullability(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.canHaveUndefinedNullability(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker captureFromArguments(SimpleTypeMarker simpleTypeMarker, CaptureStatus captureStatus) {
        return IrTypeSystemContext.DefaultImpls.captureFromArguments(this, simpleTypeMarker, captureStatus);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker captureFromExpression(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.captureFromExpression(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.captureStatus(this, capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker createEmptySubstitutor() {
        return IrTypeSystemContext.DefaultImpls.createEmptySubstitutor(this);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public SimpleTypeMarker createErrorType(String str) {
        return IrTypeSystemContext.DefaultImpls.createErrorType(this, str);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public KotlinTypeMarker createFlexibleType(SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
        return IrTypeSystemContext.DefaultImpls.createFlexibleType(this, simpleTypeMarker, simpleTypeMarker2);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public SimpleTypeMarker createSimpleType(TypeConstructorMarker typeConstructorMarker, List<? extends TypeArgumentMarker> list, boolean z, boolean z2, List<? extends AnnotationMarker> list2) {
        return IrTypeSystemContext.DefaultImpls.createSimpleType(this, typeConstructorMarker, list, z, z2, list2);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public IrStarProjectionImpl createStarProjection(TypeParameterMarker typeParameterMarker) {
        return IrTypeSystemContext.DefaultImpls.createStarProjection(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public TypeArgumentMarker createTypeArgument(KotlinTypeMarker kotlinTypeMarker, TypeVariance typeVariance) {
        return IrTypeSystemContext.DefaultImpls.createTypeArgument(this, kotlinTypeMarker, typeVariance);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemTypeFactoryContext
    public KotlinTypeMarker createUninferredType(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.createUninferredType(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(List<? extends SimpleTypeMarker> list) {
        return IrTypeSystemContext.DefaultImpls.findCommonIntegerLiteralTypesSuperType(this, list);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public Object getAnnotationFirstArgumentValue(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
        return IrTypeSystemContext.DefaultImpls.getAnnotationFirstArgumentValue(this, kotlinTypeMarker, fqName);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
        return IrTypeSystemContext.DefaultImpls.getArgument(this, kotlinTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.getArguments(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.getAttributes(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.getClassFqNameUnsafe(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.getCustomAttributes(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext
    public IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public Name getName(TypeParameterMarker typeParameterMarker) {
        return IrTypeSystemContext.DefaultImpls.getName(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrTypeParameterSymbol getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
        return IrTypeSystemContext.DefaultImpls.getParameter(this, typeConstructorMarker, i);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<IrTypeParameterSymbol> getParameters(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.getParameters(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public PrimitiveType getPrimitiveArrayType(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.getPrimitiveArrayType(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public PrimitiveType getPrimitiveType(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.getPrimitiveType(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker typeParameterMarker) {
        return IrTypeSystemContext.DefaultImpls.getRepresentativeUpperBound(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getSubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.getSubstitutedUnderlyingType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrType getType(TypeArgumentMarker typeArgumentMarker) {
        return IrTypeSystemContext.DefaultImpls.getType(this, typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public IrTypeParameterSymbol getTypeConstructor(TypeParameterMarker typeParameterMarker) {
        return IrTypeSystemContext.DefaultImpls.getTypeConstructor(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.getTypeParameter(this, typeVariableTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.getTypeParameterClassifier(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public KotlinTypeMarker getUnsubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.getUnsubstitutedUnderlyingType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
        return IrTypeSystemContext.DefaultImpls.getUpperBound(this, typeParameterMarker, i);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
        return IrTypeSystemContext.DefaultImpls.getUpperBounds(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public List<Pair<Name, SimpleTypeMarker>> getValueClassProperties(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.getValueClassProperties(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
        return IrTypeSystemContext.DefaultImpls.getVariance(this, typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
        return IrTypeSystemContext.DefaultImpls.getVariance(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean hasAnnotation(KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
        return IrTypeSystemContext.DefaultImpls.hasAnnotation(this, kotlinTypeMarker, fqName);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.hasCustomAttributes(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.hasRecursiveBounds(this, typeParameterMarker, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> list) {
        return IrTypeSystemContext.DefaultImpls.intersectTypes(this, list);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    /* JADX INFO: renamed from: intersectTypes */
    public SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> list) {
        return IrTypeSystemContext.DefaultImpls.m7220intersectTypes((IrTypeSystemContext) this, list);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isAnonymous(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isAnyConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isArrayOrNullableArray(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isArrayOrNullableArray(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isClassTypeConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isCommonFinalClassConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isDenotable(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isError(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isError(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public boolean isExtensionFunction(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isExtensionFunction(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isFinalClassOrEnumEntryOrAnnotationClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isFinalClassOrEnumEntryOrAnnotationClassConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInlineClass(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isInlineClass(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInnerClass(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isInnerClass(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isIntegerConstantOperatorTypeConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isIntegerLiteralConstantTypeConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isIntegerLiteralTypeConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isInterface(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isInterfaceOrAnnotationClass(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isInterfaceOrAnnotationClass(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isIntersection(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isLocalType(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isMarkedNullable((IrTypeSystemContext) this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isMultiFieldValueClass(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isMultiFieldValueClass(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isNothingConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isNullableType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isOldCapturedType(this, capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isPrimitiveType(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isProjectionNotNull(this, capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isRawType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isReified(TypeParameterMarker typeParameterMarker) {
        return IrTypeSystemContext.DefaultImpls.isReified(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isSingleClassifierType(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
        return IrTypeSystemContext.DefaultImpls.isStarProjection(this, typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isStubType(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isStubTypeForBuilderInference(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isStubTypeForVariableInSubtyping(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isTypeParameterTypeConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isTypeVariableType(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    public boolean isUnderKotlinPackage(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.isUnderKotlinPackage(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.isUninferredParameter(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.lowerType(this, capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.makeDefinitelyNotNullOrNotNull(this, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.makeSimpleTypeDefinitelyNotNullOrNotNull(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeCheckerProviderContext
    public TypeCheckerState newTypeCheckerState(boolean z, boolean z2) {
        return IrTypeSystemContext.DefaultImpls.newTypeCheckerState(this, z, z2);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public IrSimpleType nothingType() {
        return IrTypeSystemContext.DefaultImpls.nothingType(this);
    }

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public IrSimpleType nullableAnyType() {
        return IrTypeSystemContext.DefaultImpls.nullableAnyType(this);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    public IrSimpleType nullableNothingType() {
        return IrTypeSystemContext.DefaultImpls.nullableNothingType(this);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.original(this, definitelyNotNullTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.parametersCount(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Set<IrType> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.possibleIntegerTypes(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.projection(this, capturedTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public KotlinTypeMarker replaceCustomAttributes(KotlinTypeMarker kotlinTypeMarker, List<? extends AnnotationMarker> list) {
        return IrTypeSystemContext.DefaultImpls.replaceCustomAttributes(this, kotlinTypeMarker, list);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.replaceType(this, typeArgumentMarker, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker kotlinTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.safeSubstitute(this, typeSubstitutorMarker, kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.substitutionSupertypePolicy(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.supertypes(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public SimpleTypeMarker toErrorType(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.toErrorType(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.typeConstructor((IrTypeSystemContext) this, capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.typeConstructor(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public int typeDepth(SimpleTypeMarker simpleTypeMarker) {
        return IrTypeSystemContext.DefaultImpls.typeDepth(this, simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
        return IrTypeSystemContext.DefaultImpls.typeSubstitutorByTypeConstructor(this, map);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext
    public List<AnnotationMarker> unionTypeAttributes(List<? extends KotlinTypeMarker> list) {
        return IrTypeSystemContext.DefaultImpls.unionTypeAttributes(this, list);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
        return IrTypeSystemContext.DefaultImpls.unwrapStubTypeVariableConstructor(this, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public int upperBoundCount(TypeParameterMarker typeParameterMarker) {
        return IrTypeSystemContext.DefaultImpls.upperBoundCount(this, typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        return IrTypeSystemContext.DefaultImpls.withNullability(this, kotlinTypeMarker, z);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
        return IrTypeSystemContext.DefaultImpls.withNullability((IrTypeSystemContext) this, simpleTypeMarker, z);
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return IrJvmFlexibleTypeKt.asJvmFlexibleType((IrType) kotlinTypeMarker, getIrBuiltIns());
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        if (flexibleTypeMarker instanceof IrJvmFlexibleType) {
            return ((IrJvmFlexibleType) flexibleTypeMarker).getUpperBound();
        }
        throw new IllegalStateException(("Unexpected flexible type " + flexibleTypeMarker.getClass().getSimpleName() + ": " + flexibleTypeMarker).toString());
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        if (flexibleTypeMarker instanceof IrJvmFlexibleType) {
            return ((IrJvmFlexibleType) flexibleTypeMarker).getLowerBound();
        }
        throw new IllegalStateException(("Unexpected flexible type " + flexibleTypeMarker.getClass().getSimpleName() + ": " + flexibleTypeMarker).toString());
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeSystemContext, org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return (kotlinTypeMarker instanceof IrSimpleType) && !IrJvmFlexibleTypeKt.isWithFlexibleNullability((IrType) kotlinTypeMarker) && IrTypePredicatesKt.isMarkedNullable((IrSimpleType) kotlinTypeMarker);
    }
}
