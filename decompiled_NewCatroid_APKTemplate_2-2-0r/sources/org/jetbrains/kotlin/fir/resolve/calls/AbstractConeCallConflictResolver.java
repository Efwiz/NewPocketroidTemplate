package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.FirSamResolver;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceComponents;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.calls.results.FlatSignature;
import org.jetbrains.kotlin.resolve.calls.results.FlatSignatureKt;
import org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.results.SpecificityComparisonCallbacks;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;
import org.jetbrains.kotlin.resolve.calls.results.TypeWithConversion;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContextKt;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: AbstractConeCallConflictResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0083\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\f\b&\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ4\u0010\u0016\u001a\u00020\t2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\tH\u0004J\u001e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020$H\u0002J\u0016\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010 \u001a\u00020\u0019H\u0004J\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010 \u001a\u00020\u00192\u0006\u0010&\u001a\u00020'H\u0002J\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010 \u001a\u00020\u00192\u0006\u0010(\u001a\u00020)H\u0004J\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010 \u001a\u00020\u00192\u0006\u0010*\u001a\u00020+H\u0004J\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010 \u001a\u00020\u00192\u0006\u0010,\u001a\u00020-H\u0004J\f\u0010.\u001a\u00020/*\u000200H\u0002J\u001c\u00101\u001a\u00020\u001f*\u0002002\u0006\u00102\u001a\u0002032\u0006\u0010 \u001a\u00020\u0019H\u0002R\u0016\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\n\n\u0002\u0010\u000f\u0012\u0004\b\r\u0010\u000eR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractConeCallConflictResolver;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ConeCallConflictResolver;", "specificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "inferenceComponents", "Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "transformerComponents", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "considerMissingArgumentsInSignatures", "", "(Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;Z)V", "SpecificityComparisonWithNumerics", "org/jetbrains/kotlin/fir/resolve/calls/AbstractConeCallConflictResolver$SpecificityComparisonWithNumerics$1", "getSpecificityComparisonWithNumerics$annotations", "()V", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractConeCallConflictResolver$SpecificityComparisonWithNumerics$1;", "getInferenceComponents", "()Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "samResolver", "Lorg/jetbrains/kotlin/fir/resolve/FirSamResolver;", "getSamResolver", "()Lorg/jetbrains/kotlin/fir/resolve/FirSamResolver;", "compareCallsByUsedArguments", "call1", "Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "call2", "discriminateGenerics", "useOriginalSamTypes", "computeSignatureTypes", "", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeWithConversion;", "call", "called", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "createEmptyConstraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/results/SimpleConstraintSystem;", "createFlatSignature", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "variable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "argumentType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "toTypeWithConversion", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractConeCallConflictResolver extends ConeCallConflictResolver {
    private final AbstractConeCallConflictResolver$SpecificityComparisonWithNumerics$1 SpecificityComparisonWithNumerics;
    private final boolean considerMissingArgumentsInSignatures;
    private final InferenceComponents inferenceComponents;
    private final TypeSpecificityComparator specificityComparator;
    private final BodyResolveComponents transformerComponents;

    protected final InferenceComponents getInferenceComponents() {
        return this.inferenceComponents;
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [org.jetbrains.kotlin.fir.resolve.calls.AbstractConeCallConflictResolver$SpecificityComparisonWithNumerics$1] */
    public AbstractConeCallConflictResolver(TypeSpecificityComparator specificityComparator, InferenceComponents inferenceComponents, BodyResolveComponents transformerComponents, boolean z) {
        Intrinsics.checkNotNullParameter(specificityComparator, "specificityComparator");
        Intrinsics.checkNotNullParameter(inferenceComponents, "inferenceComponents");
        Intrinsics.checkNotNullParameter(transformerComponents, "transformerComponents");
        this.specificityComparator = specificityComparator;
        this.inferenceComponents = inferenceComponents;
        this.transformerComponents = transformerComponents;
        this.considerMissingArgumentsInSignatures = z;
        this.SpecificityComparisonWithNumerics = new SpecificityComparisonCallbacks() { // from class: org.jetbrains.kotlin.fir.resolve.calls.AbstractConeCallConflictResolver$SpecificityComparisonWithNumerics$1
            @Override // org.jetbrains.kotlin.resolve.calls.results.SpecificityComparisonCallbacks
            public boolean isNonSubtypeNotLessSpecific(KotlinTypeMarker specific, KotlinTypeMarker general) {
                ClassId classId;
                Intrinsics.checkNotNullParameter(specific, "specific");
                Intrinsics.checkNotNullParameter(general, "general");
                TypeSystemContextKt.requireOrDescribe(specific instanceof ConeKotlinType, specific);
                TypeSystemContextKt.requireOrDescribe(general instanceof ConeKotlinType, general);
                ClassId classId2 = ConeTypeUtilsKt.getClassId(ConeTypeUtilsKt.lowerBoundIfFlexible((ConeKotlinType) specific));
                if (classId2 == null || (classId = ConeTypeUtilsKt.getClassId(ConeTypeUtilsKt.upperBoundIfFlexible((ConeKotlinType) general))) == null) {
                    return false;
                }
                if (!isUnsigned(classId2) && isUnsigned(classId)) {
                    return true;
                }
                if (Intrinsics.areEqual(classId2, StandardClassIds.INSTANCE.getInt())) {
                    return Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getLong()) || Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getShort()) || Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getByte());
                }
                if (Intrinsics.areEqual(classId2, StandardClassIds.INSTANCE.getShort()) && Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getByte())) {
                    return true;
                }
                if (Intrinsics.areEqual(classId2, StandardClassIds.INSTANCE.getUInt())) {
                    return Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getULong()) || Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getUShort()) || Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getUByte());
                }
                if (Intrinsics.areEqual(classId2, StandardClassIds.INSTANCE.getUShort()) && Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getUByte())) {
                    return true;
                }
                return Intrinsics.areEqual(classId2, StandardClassIds.INSTANCE.getDouble()) && Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getFloat());
            }

            private final boolean isUnsigned(ClassId classId) {
                return StandardClassIds.INSTANCE.getUnsignedTypes().contains(classId);
            }
        };
    }

    private final FirSamResolver getSamResolver() {
        return this.transformerComponents.getSamResolver();
    }

    protected final boolean compareCallsByUsedArguments(FlatSignature<Candidate> call1, FlatSignature<Candidate> call2, boolean discriminateGenerics, boolean useOriginalSamTypes) {
        Intrinsics.checkNotNullParameter(call1, "call1");
        Intrinsics.checkNotNullParameter(call2, "call2");
        if (discriminateGenerics) {
            boolean isGeneric = call1.getIsGeneric();
            boolean isGeneric2 = call2.getIsGeneric();
            if (isGeneric && !isGeneric2) {
                return false;
            }
            if (!isGeneric && isGeneric2) {
                return true;
            }
            if (isGeneric && isGeneric2) {
                return false;
            }
        }
        if (!call1.getIsExpect() && call2.getIsExpect()) {
            return true;
        }
        if (call1.getIsExpect() && !call2.getIsExpect()) {
            return false;
        }
        if (call1.getContextReceiverCount() > call2.getContextReceiverCount()) {
            return true;
        }
        if (call1.getContextReceiverCount() < call2.getContextReceiverCount()) {
            return false;
        }
        return FlatSignatureKt.isSignatureNotLessSpecific(createEmptyConstraintSystem(), call1, call2, this.SpecificityComparisonWithNumerics, this.specificityComparator, useOriginalSamTypes);
    }

    protected final FlatSignature<Candidate> createFlatSignature(Candidate call) {
        Intrinsics.checkNotNullParameter(call, "call");
        FirDeclaration fir = call.getSymbol().getFir();
        if (fir instanceof FirSimpleFunction) {
            return createFlatSignature(call, (FirSimpleFunction) fir);
        }
        if (fir instanceof FirConstructor) {
            return createFlatSignature(call, (FirConstructor) fir);
        }
        if (fir instanceof FirVariable) {
            return createFlatSignature(call, (FirVariable) fir);
        }
        if (!(fir instanceof FirClass) && !(fir instanceof FirTypeAlias)) {
            KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Not supported: " + fir.getClass(), null);
            ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
            FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "declaration", fir);
            kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
            throw kotlinIllegalArgumentExceptionWithAttachments;
        }
        return createFlatSignature(call, (FirClassLikeDeclaration) fir);
    }

    protected final FlatSignature<Candidate> createFlatSignature(Candidate call, FirVariable variable) {
        ArrayList arrayListEmptyList;
        List<FirTypeParameter> typeParameters;
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(variable, "variable");
        boolean z = variable instanceof FirProperty;
        FirProperty firProperty = z ? (FirProperty) variable : null;
        if (firProperty == null || (typeParameters = firProperty.getTypeParameters()) == null) {
            arrayListEmptyList = null;
        } else {
            List<FirTypeParameter> list = typeParameters;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((FirTypeParameter) it.next()).getSymbol().getLookupTag());
            }
            arrayListEmptyList = arrayList;
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        List<TypeWithConversion> listComputeSignatureTypes = computeSignatureTypes(call, variable);
        boolean z2 = false;
        boolean z3 = variable.getReceiverParameter() != null;
        int size = variable.getContextReceivers().size();
        FirProperty firProperty2 = z ? (FirProperty) variable : null;
        if (firProperty2 != null && firProperty2.getStatus().isExpect()) {
            z2 = true;
        }
        return new FlatSignature<>(call, (Collection<? extends TypeParameterMarker>) arrayListEmptyList, z3, size, false, 0, z2, false, listComputeSignatureTypes);
    }

    protected final FlatSignature<Candidate> createFlatSignature(Candidate call, FirConstructor constructor) {
        boolean z;
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        List<FirTypeParameterRef> typeParameters = constructor.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirTypeParameterRef) it.next()).getSymbol().getLookupTag());
        }
        ArrayList arrayList2 = arrayList;
        List<TypeWithConversion> listComputeSignatureTypes = computeSignatureTypes(call, constructor);
        int size = constructor.getContextReceivers().size();
        List<FirValueParameter> valueParameters = constructor.getValueParameters();
        if ((valueParameters instanceof Collection) && valueParameters.isEmpty()) {
            z = false;
        } else {
            Iterator<T> it2 = valueParameters.iterator();
            while (it2.hasNext()) {
                if (((FirValueParameter) it2.next()).getIsVararg()) {
                    z = true;
                }
            }
            z = false;
        }
        return new FlatSignature<>(call, (Collection<? extends TypeParameterMarker>) arrayList2, false, size, z, call.getNumDefaults(), constructor.getStatus().isExpect(), false, listComputeSignatureTypes);
    }

    protected final FlatSignature<Candidate> createFlatSignature(Candidate call, FirSimpleFunction function) {
        boolean z;
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(function, "function");
        List<FirTypeParameter> typeParameters = function.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirTypeParameter) it.next()).getSymbol().getLookupTag());
        }
        ArrayList arrayList2 = arrayList;
        List<TypeWithConversion> listComputeSignatureTypes = computeSignatureTypes(call, function);
        boolean z2 = function.getReceiverParameter() != null;
        int size = function.getContextReceivers().size();
        List<FirValueParameter> valueParameters = function.getValueParameters();
        if ((valueParameters instanceof Collection) && valueParameters.isEmpty()) {
            z = false;
        } else {
            Iterator<T> it2 = valueParameters.iterator();
            while (it2.hasNext()) {
                if (((FirValueParameter) it2.next()).getIsVararg()) {
                    z = true;
                }
            }
            z = false;
        }
        return new FlatSignature<>(call, (Collection<? extends TypeParameterMarker>) arrayList2, z2, size, z, call.getNumDefaults(), function.getStatus().isExpect(), false, listComputeSignatureTypes);
    }

    private final ConeKotlinType argumentType(FirValueParameter firValueParameter) {
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef());
        if (!firValueParameter.getIsVararg()) {
            return coneType;
        }
        ConeKotlinType coneKotlinTypeArrayElementType = ArrayUtilsKt.arrayElementType(coneType);
        Intrinsics.checkNotNull(coneKotlinTypeArrayElementType);
        return coneKotlinTypeArrayElementType;
    }

    private final List<TypeWithConversion> computeSignatureTypes(Candidate call, FirCallableDeclaration called) {
        FirTypeRef typeRef;
        ConeKotlinType coneType;
        ConeKotlinType coneKotlinTypeFullyExpandedType;
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        FirSession session = this.inferenceComponents.getSession();
        List list = listCreateListBuilder;
        FirReceiverParameter receiverParameter = called.getReceiverParameter();
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list, (receiverParameter == null || (typeRef = receiverParameter.getTypeRef()) == null || (coneType = FirTypeUtilsKt.getConeType(typeRef)) == null || (coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(coneType, session)) == null) ? null : new TypeWithConversion(coneKotlinTypeFullyExpandedType, null, 2, null));
        ConeKotlinType resultingTypeForCallableReference = call.getResultingTypeForCallableReference();
        if (resultingTypeForCallableReference != null) {
            for (ConeTypeProjection coneTypeProjection : ArraysKt.dropLast(resultingTypeForCallableReference.getTypeArguments(), 1)) {
                Intrinsics.checkNotNull(coneTypeProjection, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
                list.add(new TypeWithConversion(TypeVariableTypeRemovingSubstitutorKt.removeTypeVariableTypes(TypeExpansionUtilsKt.fullyExpandedType((ConeKotlinType) coneTypeProjection, session), TypeComponentsKt.getTypeContext(session)), null, 2, null));
            }
        } else {
            Iterator<T> it = called.getContextReceivers().iterator();
            while (it.hasNext()) {
                list.add(new TypeWithConversion(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(((FirContextReceiver) it.next()).getTypeRef()), session), null, 2, null));
            }
            LinkedHashMap<FirExpression, FirValueParameter> argumentMapping = call.getArgumentMapping();
            if (argumentMapping != null) {
                Iterator<Map.Entry<FirExpression, FirValueParameter>> it2 = argumentMapping.entrySet().iterator();
                while (it2.hasNext()) {
                    list.add(toTypeWithConversion(it2.next().getValue(), session, call));
                }
            }
            if (this.considerMissingArgumentsInSignatures) {
                for (ResolutionDiagnostic resolutionDiagnostic : call.getDiagnostics()) {
                    TypeWithConversion typeWithConversion = !(resolutionDiagnostic instanceof NoValueForParameter) ? null : toTypeWithConversion(((NoValueForParameter) resolutionDiagnostic).getValueParameter(), session, call);
                    if (typeWithConversion != null) {
                        list.add(typeWithConversion);
                    }
                }
            }
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    private final TypeWithConversion toTypeWithConversion(FirValueParameter firValueParameter, FirSession firSession, Candidate candidate) {
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(argumentType(firValueParameter), firSession);
        if (!candidate.getUsesSAM()) {
            return new TypeWithConversion(coneKotlinTypeFullyExpandedType, null, 2, null);
        }
        ConeKotlinType functionTypeForPossibleSamType = getSamResolver().getFunctionTypeForPossibleSamType(coneKotlinTypeFullyExpandedType);
        if (functionTypeForPossibleSamType == null) {
            return new TypeWithConversion(coneKotlinTypeFullyExpandedType, null, 2, null);
        }
        return new TypeWithConversion(functionTypeForPossibleSamType, coneKotlinTypeFullyExpandedType);
    }

    private final FlatSignature<Candidate> createFlatSignature(Candidate call, FirClassLikeDeclaration klass) {
        ArrayList arrayListEmptyList;
        List<FirTypeParameterRef> typeParameters;
        FirClassLikeDeclaration firClassLikeDeclaration = klass instanceof FirTypeParameterRefsOwner ? klass : null;
        if (firClassLikeDeclaration == null || (typeParameters = firClassLikeDeclaration.getTypeParameters()) == null) {
            arrayListEmptyList = null;
        } else {
            List<FirTypeParameterRef> list = typeParameters;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((FirTypeParameterRef) it.next()).getSymbol().getLookupTag());
            }
            arrayListEmptyList = arrayList;
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        List list2 = arrayListEmptyList;
        List listEmptyList = CollectionsKt.emptyList();
        FirRegularClass firRegularClass = klass instanceof FirRegularClass ? (FirRegularClass) klass : null;
        return new FlatSignature<>(call, (Collection<? extends TypeParameterMarker>) list2, (List<? extends KotlinTypeMarker>) listEmptyList, false, 0, false, 0, firRegularClass != null && firRegularClass.getStatus().isExpect(), false);
    }

    private final SimpleConstraintSystem createEmptyConstraintSystem() {
        return new ConeSimpleConstraintSystemImpl(this.inferenceComponents.createConstraintSystem(), this.inferenceComponents.getSession());
    }
}
