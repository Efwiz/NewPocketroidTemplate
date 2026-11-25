package org.jetbrains.kotlin.fir.java.scopes;

import com.reandroid.apk.ApkUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.java.JavaTypeConversionKt;
import org.jetbrains.kotlin.fir.java.JavaTypeParameterStack;
import org.jetbrains.kotlin.fir.java.enhancement.JavaTypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.scopes.impl.FirAbstractOverrideChecker;
import org.jetbrains.kotlin.fir.scopes.jvm.SignatureUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeRawType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeContext;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: JavaOverrideChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\b\u0006\u0018\u00002\u00020\u0001B/\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0014J\u0016\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015J \u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u000fH\u0002J \u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u001b\u001a\u00020\u000fH\u0002J \u0010\u001c\u001a\u00020\n2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\u0006\u0010\u001b\u001a\u00020\u000fH\u0002J\u0018\u0010#\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u0015H\u0016J\u0018\u0010$\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020%H\u0016J\u001e\u0010&\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\u001e0'*\b\u0012\u0004\u0012\u00020*0)H\u0002J\u001a\u0010+\u001a\u00020,*\u00020\u00112\f\u0010-\u001a\b\u0012\u0004\u0012\u00020*0.H\u0002J\u001a\u0010+\u001a\u00020,*\u00020\u001e2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020*0.H\u0002J\u001a\u0010+\u001a\u00020,*\u00020!2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020*0.H\u0002J\u0014\u0010/\u001a\u00020\n*\u00020\u00152\u0006\u00100\u001a\u00020\u001eH\u0002J\u0014\u00101\u001a\u00020\n*\u00020\u001e2\u0006\u00102\u001a\u00020\nH\u0002J\f\u00103\u001a\u00020\n*\u00020\u0011H\u0002J\f\u00103\u001a\u00020\n*\u00020\u001eH\u0002J\u000e\u00103\u001a\u00020\n*\u0004\u0018\u00010!H\u0002R\u0016\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/scopes/JavaOverrideChecker;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirAbstractOverrideChecker;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "javaTypeParameterStack", "Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;", "baseScopes", "", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "considerReturnTypeKinds", "", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;Ljava/util/List;Z)V", "context", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "buildTypeParametersSubstitutorIfCompatible", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "overrideCandidate", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "baseDeclaration", "doesReturnTypesHaveSameKind", "candidate", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", ApkUtil.DEF_MODULE_NAME, "isEqualArrayElementTypeProjections", "candidateTypeProjection", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "baseTypeProjection", "substitutor", "isEqualTypes", "candidateType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "baseType", "candidateTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "baseTypeRef", "isOverriddenFunction", "isOverriddenProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "buildErasure", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "extractTypeParametersTo", "", "result", "", "hasPrimitiveReturnTypeInJvm", "returnType", "isPrimitiveInJava", "isReturnType", "isTypeParameterDependent", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaOverrideChecker extends FirAbstractOverrideChecker {
    private final List<FirTypeScope> baseScopes;
    private final boolean considerReturnTypeKinds;
    private final ConeTypeContext context;
    private final JavaTypeParameterStack javaTypeParameterStack;
    private final FirSession session;

    /* JADX WARN: Multi-variable type inference failed */
    public JavaOverrideChecker(FirSession session, JavaTypeParameterStack javaTypeParameterStack, List<? extends FirTypeScope> list, boolean z) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(javaTypeParameterStack, "javaTypeParameterStack");
        this.session = session;
        this.javaTypeParameterStack = javaTypeParameterStack;
        this.baseScopes = list;
        this.considerReturnTypeKinds = z;
        this.context = TypeComponentsKt.getTypeContext(session);
    }

    private final boolean isEqualTypes(ConeKotlinType candidateType, ConeKotlinType baseType, ConeSubstitutor substitutor) {
        if (candidateType instanceof ConeRawType) {
            return Intrinsics.areEqual(SignatureUtilsKt.computeJvmDescriptorRepresentation$default(candidateType, null, 1, null), SignatureUtilsKt.computeJvmDescriptorRepresentation$default(baseType, null, 1, null));
        }
        if (candidateType instanceof ConeFlexibleType) {
            return isEqualTypes(((ConeFlexibleType) candidateType).getLowerBound(), baseType, substitutor);
        }
        if (baseType instanceof ConeFlexibleType) {
            return isEqualTypes(candidateType, ((ConeFlexibleType) baseType).getLowerBound(), substitutor);
        }
        if ((candidateType instanceof ConeClassLikeType) && (baseType instanceof ConeClassLikeType)) {
            ClassId classId = TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) candidateType, this.session, null, 2, null).getLookupTag().getClassId();
            ClassId onlyToMutable = JavaTypeUtilsKt.readOnlyToMutable(classId);
            if (onlyToMutable != null) {
                classId = onlyToMutable;
            }
            ClassId classId2 = TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) baseType, this.session, null, 2, null).getLookupTag().getClassId();
            ClassId onlyToMutable2 = JavaTypeUtilsKt.readOnlyToMutable(classId2);
            if (onlyToMutable2 != null) {
                classId2 = onlyToMutable2;
            }
            if (!Intrinsics.areEqual(classId, classId2)) {
                return false;
            }
            if (!Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getArray())) {
                return true;
            }
            int length = candidateType.getTypeArguments().length;
            int length2 = baseType.getTypeArguments().length;
            return isEqualArrayElementTypeProjections((ConeTypeProjection) ArraysKt.single(candidateType.getTypeArguments()), (ConeTypeProjection) ArraysKt.single(baseType.getTypeArguments()), substitutor);
        }
        ConeTypeContext coneTypeContext = this.context;
        return coneTypeContext.areEqualTypeConstructors(coneTypeContext.typeConstructor(substitutor.substituteOrSelf(candidateType)), coneTypeContext.typeConstructor(substitutor.substituteOrSelf(baseType)));
    }

    private final boolean isEqualTypes(FirTypeRef candidateTypeRef, FirTypeRef baseTypeRef, ConeSubstitutor substitutor) {
        ConeKotlinType coneKotlinTypeProbablyFlexible$default = JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible$default(candidateTypeRef, this.session, this.javaTypeParameterStack, null, 4, null);
        ConeKotlinType coneKotlinTypeProbablyFlexible$default2 = JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible$default(baseTypeRef, this.session, this.javaTypeParameterStack, null, 4, null);
        if (isPrimitiveInJava(coneKotlinTypeProbablyFlexible$default, false) != isPrimitiveInJava(coneKotlinTypeProbablyFlexible$default2, false)) {
            return false;
        }
        return isEqualTypes(coneKotlinTypeProbablyFlexible$default, coneKotlinTypeProbablyFlexible$default2, substitutor);
    }

    public final boolean doesReturnTypesHaveSameKind(FirSimpleFunction candidate, FirSimpleFunction base) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(base, "base");
        FirTypeRef returnTypeRef = candidate.getReturnTypeRef();
        FirTypeRef returnTypeRef2 = base.getReturnTypeRef();
        ConeKotlinType coneKotlinTypeProbablyFlexible$default = JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible$default(returnTypeRef, this.session, this.javaTypeParameterStack, null, 4, null);
        ConeKotlinType coneKotlinTypeProbablyFlexible$default2 = JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible$default(returnTypeRef2, this.session, this.javaTypeParameterStack, null, 4, null);
        boolean zHasPrimitiveReturnTypeInJvm = hasPrimitiveReturnTypeInJvm(candidate, coneKotlinTypeProbablyFlexible$default);
        if (zHasPrimitiveReturnTypeInJvm != hasPrimitiveReturnTypeInJvm(base, coneKotlinTypeProbablyFlexible$default2)) {
            return false;
        }
        if (!zHasPrimitiveReturnTypeInJvm) {
            return true;
        }
        ConeClassLikeType coneClassLikeType = coneKotlinTypeProbablyFlexible$default instanceof ConeClassLikeType ? (ConeClassLikeType) coneKotlinTypeProbablyFlexible$default : null;
        ConeClassLikeLookupTag lookupTag = coneClassLikeType != null ? coneClassLikeType.getLookupTag() : null;
        ConeClassLikeType coneClassLikeType2 = coneKotlinTypeProbablyFlexible$default2 instanceof ConeClassLikeType ? (ConeClassLikeType) coneKotlinTypeProbablyFlexible$default2 : null;
        return Intrinsics.areEqual(lookupTag, coneClassLikeType2 != null ? coneClassLikeType2.getLookupTag() : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isPrimitiveInJava(ConeKotlinType coneKotlinType, boolean z) {
        if (this.context.isNullableType(coneKotlinType) || coneKotlinType.getAttributes().contains(CompilerConeAttributes.EnhancedNullability.INSTANCE)) {
            return false;
        }
        return ConeBuiltinTypeUtilsKt.isPrimitiveOrNullablePrimitive(coneKotlinType) || (z && ConeBuiltinTypeUtilsKt.isUnit(coneKotlinType));
    }

    private final boolean hasPrimitiveReturnTypeInJvm(FirSimpleFunction firSimpleFunction, ConeKotlinType coneKotlinType) {
        if (!isPrimitiveInJava(coneKotlinType, true)) {
            return false;
        }
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        List<FirTypeScope> list = this.baseScopes;
        if (list != null) {
            FirTypeScopeKt.processOverriddenFunctions(list, firSimpleFunction.getSymbol(), new Function1<FirNamedFunctionSymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaOverrideChecker.hasPrimitiveReturnTypeInJvm.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                public final ProcessorAction invoke(FirNamedFunctionSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (!JavaOverrideChecker.this.isPrimitiveInJava(JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible$default(((FirSimpleFunction) it.getFir()).getReturnTypeRef(), JavaOverrideChecker.this.session, JavaOverrideChecker.this.javaTypeParameterStack, null, 4, null), true)) {
                        booleanRef.element = true;
                        return ProcessorAction.STOP;
                    }
                    return ProcessorAction.NEXT;
                }
            });
        }
        return !booleanRef.element;
    }

    private final boolean isEqualArrayElementTypeProjections(ConeTypeProjection candidateTypeProjection, ConeTypeProjection baseTypeProjection, ConeSubstitutor substitutor) {
        if ((candidateTypeProjection instanceof ConeKotlinTypeProjection) && (baseTypeProjection instanceof ConeKotlinTypeProjection)) {
            return isEqualTypes(((ConeKotlinTypeProjection) candidateTypeProjection).getType(), ((ConeKotlinTypeProjection) baseTypeProjection).getType(), substitutor);
        }
        return (candidateTypeProjection instanceof ConeStarProjection) && (baseTypeProjection instanceof ConeStarProjection);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Map<FirTypeParameterSymbol, ConeKotlinType> buildErasure(Collection<? extends FirTypeParameterRef> collection) {
        Collection<? extends FirTypeParameterRef> collection2 = collection;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(collection2, 10)), 16));
        for (FirTypeParameterRef firTypeParameterRef : collection2) {
            FirTypeParameterSymbol symbol = firTypeParameterRef.getSymbol();
            FirTypeRef firTypeRef = (FirTypeRef) CollectionsKt.firstOrNull((List) ((FirTypeParameter) symbol.getFir()).getBounds());
            if (firTypeRef != null) {
                Pair pair = TuplesKt.to(symbol, JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible$default(firTypeRef, this.session, this.javaTypeParameterStack, null, 4, null));
                linkedHashMap.put(pair.getFirst(), pair.getSecond());
            } else {
                KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Bound element is not found", null);
                ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "typeParameterRef", firTypeParameterRef);
                FirTypeParameter firTypeParameter = (FirTypeParameter) firTypeParameterRef.getSymbol().getFir();
                FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "typeParameter", firTypeParameter);
                FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "containingDeclaration", firTypeParameter.getContainingDeclarationSymbol().getFir());
                kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                throw kotlinIllegalArgumentExceptionWithAttachments;
            }
        }
        return linkedHashMap;
    }

    private final boolean isTypeParameterDependent(FirTypeRef firTypeRef) {
        return (firTypeRef instanceof FirResolvedTypeRef) && isTypeParameterDependent(((FirResolvedTypeRef) firTypeRef).getType());
    }

    private final boolean isTypeParameterDependent(ConeKotlinType coneKotlinType) {
        if (coneKotlinType instanceof ConeFlexibleType) {
            return isTypeParameterDependent(((ConeFlexibleType) coneKotlinType).getLowerBound());
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            return isTypeParameterDependent(((ConeDefinitelyNotNullType) coneKotlinType).getOriginal());
        }
        if (!(coneKotlinType instanceof ConeTypeParameterType)) {
            if (!(coneKotlinType instanceof ConeClassLikeType)) {
                return false;
            }
            for (ConeTypeProjection coneTypeProjection : coneKotlinType.getTypeArguments()) {
                if (!(coneTypeProjection instanceof ConeKotlinTypeProjection) || !isTypeParameterDependent(((ConeKotlinTypeProjection) coneTypeProjection).getType())) {
                }
            }
            return false;
        }
        return true;
    }

    private final boolean isTypeParameterDependent(FirCallableDeclaration firCallableDeclaration) {
        if (firCallableDeclaration.getTypeParameters().isEmpty() && !isTypeParameterDependent(firCallableDeclaration.getReturnTypeRef())) {
            FirReceiverParameter receiverParameter = firCallableDeclaration.getReceiverParameter();
            if (!isTypeParameterDependent(receiverParameter != null ? receiverParameter.getTypeRef() : null)) {
                if (firCallableDeclaration instanceof FirSimpleFunction) {
                    List<FirValueParameter> valueParameters = ((FirSimpleFunction) firCallableDeclaration).getValueParameters();
                    if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
                        Iterator<T> it = valueParameters.iterator();
                        while (it.hasNext()) {
                            if (isTypeParameterDependent(((FirValueParameter) it.next()).getReturnTypeRef())) {
                            }
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }

    private final void extractTypeParametersTo(FirTypeRef firTypeRef, Collection<FirTypeParameterRef> collection) {
        if (firTypeRef instanceof FirResolvedTypeRef) {
            extractTypeParametersTo(((FirResolvedTypeRef) firTypeRef).getType(), collection);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void extractTypeParametersTo(ConeKotlinType coneKotlinType, Collection<FirTypeParameterRef> collection) {
        if (coneKotlinType instanceof ConeFlexibleType) {
            extractTypeParametersTo(((ConeFlexibleType) coneKotlinType).getLowerBound(), collection);
            return;
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            extractTypeParametersTo(((ConeDefinitelyNotNullType) coneKotlinType).getOriginal(), collection);
            return;
        }
        if (coneKotlinType instanceof ConeTypeParameterType) {
            collection.add(((ConeTypeParameterType) coneKotlinType).getLookupTag().getTypeParameterSymbol().getFir());
            return;
        }
        if (coneKotlinType instanceof ConeClassLikeType) {
            for (ConeTypeProjection coneTypeProjection : coneKotlinType.getTypeArguments()) {
                if (coneTypeProjection instanceof ConeKotlinTypeProjection) {
                    extractTypeParametersTo(((ConeKotlinTypeProjection) coneTypeProjection).getType(), collection);
                }
            }
        }
    }

    private final void extractTypeParametersTo(FirCallableDeclaration firCallableDeclaration, Collection<FirTypeParameterRef> collection) {
        FirTypeRef typeRef;
        CollectionsKt.addAll(collection, firCallableDeclaration.getTypeParameters());
        extractTypeParametersTo(firCallableDeclaration.getReturnTypeRef(), collection);
        FirReceiverParameter receiverParameter = firCallableDeclaration.getReceiverParameter();
        if (receiverParameter != null && (typeRef = receiverParameter.getTypeRef()) != null) {
            extractTypeParametersTo(typeRef, collection);
        }
        if (firCallableDeclaration instanceof FirSimpleFunction) {
            Iterator<T> it = ((FirSimpleFunction) firCallableDeclaration).getValueParameters().iterator();
            while (it.hasNext()) {
                extractTypeParametersTo(((FirValueParameter) it.next()).getReturnTypeRef(), collection);
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirAbstractOverrideChecker
    protected ConeSubstitutor buildTypeParametersSubstitutorIfCompatible(FirCallableDeclaration overrideCandidate, FirCallableDeclaration baseDeclaration) {
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(overrideCandidate, FirResolvePhase.TYPES);
        FirLazyDeclarationResolverKt.lazyResolveToPhase(baseDeclaration, FirResolvePhase.TYPES);
        if (!isTypeParameterDependent(overrideCandidate) && !isTypeParameterDependent(baseDeclaration)) {
            return ConeSubstitutor.Empty.INSTANCE;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        extractTypeParametersTo(overrideCandidate, linkedHashSet);
        extractTypeParametersTo(baseDeclaration, linkedHashSet);
        return SubstitutorsKt.substitutorByMap(buildErasure(linkedHashSet), this.session);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirOverrideChecker
    public boolean isOverriddenFunction(FirSimpleFunction overrideCandidate, FirSimpleFunction baseDeclaration) {
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        if (overrideCandidate.getStatus().isStatic() != baseDeclaration.getStatus().isStatic()) {
            return false;
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(overrideCandidate, FirResolvePhase.TYPES);
        FirLazyDeclarationResolverKt.lazyResolveToPhase(baseDeclaration, FirResolvePhase.TYPES);
        FirReceiverParameter receiverParameter = baseDeclaration.getReceiverParameter();
        List listListOfNotNull = CollectionsKt.listOfNotNull(receiverParameter != null ? receiverParameter.getTypeRef() : null);
        List<FirValueParameter> valueParameters = baseDeclaration.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirValueParameter) it.next()).getReturnTypeRef());
        }
        List listPlus = CollectionsKt.plus((Collection) listListOfNotNull, (Iterable) arrayList);
        if (overrideCandidate.getValueParameters().size() != listPlus.size()) {
            return false;
        }
        ConeSubstitutor coneSubstitutorBuildTypeParametersSubstitutorIfCompatible = buildTypeParametersSubstitutorIfCompatible(overrideCandidate, baseDeclaration);
        List<Pair> listZip = CollectionsKt.zip(overrideCandidate.getValueParameters(), listPlus);
        if (!(listZip instanceof Collection) || !listZip.isEmpty()) {
            for (Pair pair : listZip) {
                if (!isEqualTypes(((FirValueParameter) pair.component1()).getReturnTypeRef(), (FirTypeRef) pair.component2(), coneSubstitutorBuildTypeParametersSubstitutorIfCompatible)) {
                    return false;
                }
            }
        }
        return !this.considerReturnTypeKinds || doesReturnTypesHaveSameKind(overrideCandidate, baseDeclaration);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirOverrideChecker
    public boolean isOverriddenProperty(FirCallableDeclaration overrideCandidate, FirProperty baseDeclaration) {
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        if (baseDeclaration.getStatus().getModality() == Modality.FINAL) {
            return false;
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(overrideCandidate, FirResolvePhase.TYPES);
        FirLazyDeclarationResolverKt.lazyResolveToPhase(baseDeclaration, FirResolvePhase.TYPES);
        FirReceiverParameter receiverParameter = baseDeclaration.getReceiverParameter();
        FirTypeRef typeRef = receiverParameter != null ? receiverParameter.getTypeRef() : null;
        if (overrideCandidate instanceof FirSimpleFunction) {
            if (typeRef == null) {
                return ((FirSimpleFunction) overrideCandidate).getValueParameters().isEmpty();
            }
            FirSimpleFunction firSimpleFunction = (FirSimpleFunction) overrideCandidate;
            if (firSimpleFunction.getValueParameters().size() != 1) {
                return false;
            }
            return isEqualTypes(typeRef, ((FirValueParameter) CollectionsKt.single((List) firSimpleFunction.getValueParameters())).getReturnTypeRef(), ConeSubstitutor.Empty.INSTANCE);
        }
        if (!(overrideCandidate instanceof FirProperty)) {
            return false;
        }
        FirReceiverParameter receiverParameter2 = overrideCandidate.getReceiverParameter();
        FirTypeRef typeRef2 = receiverParameter2 != null ? receiverParameter2.getTypeRef() : null;
        if (typeRef == null) {
            return typeRef2 == null;
        }
        if (typeRef2 == null) {
            return false;
        }
        return isEqualTypes(typeRef, typeRef2, ConeSubstitutor.Empty.INSTANCE);
    }
}
