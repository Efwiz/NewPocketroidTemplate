package org.jetbrains.kotlin.fir.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.fir.FirFunctionTypeParameter;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationArgumentMappingBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirAnnotationBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTagWithFixedSymbol;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.SymbolInternals;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLikeLookupTagImpl;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLookupTagWithFixedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeCapturedType;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableType;
import org.jetbrains.kotlin.fir.types.CustomAnnotationTypeAttribute;
import org.jetbrains.kotlin.fir.types.CustomAnnotationTypeAttributeKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.ConstantValueKind;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.util.WeakPair;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: LookupTagUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006\u001a\u001c\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u0004\u001a$\u0010\u000b\u001a\u0004\u0018\u00010\b*\b\u0012\u0004\u0012\u00020\t0\f2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u001e\u0010\u000b\u001a\u0004\u0018\u00010\b*\u00020\u000e2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u0018\u0010\u000f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\b\u001a\u0018\u0010\u000f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0012*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0013\u001a\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u0015*\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007\u001a\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u0017*\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0018\u0010\u0018\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0006*\u00020\b2\u0006\u0010\u0019\u001a\u00020\u0004\u001a\u0018\u0010\u0018\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0012*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0004\u001a\u0014\u0010\u001a\u001a\u00020\t*\u00020\t2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002\u001a\u0012\u0010\u001a\u001a\u00020\t*\u00020\t2\u0006\u0010\u001b\u001a\u00020\t\u001a\u001a\u0010\u001d\u001a\u00020\t*\u00020\t2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006 "}, d2 = {"bindSymbolToLookupTag", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/ConeClassLikeLookupTagImpl;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "findClassRepresentation", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "dispatchReceiverParameterType", "findClassRepresentationThatIsSubtypeOf", "", "supertype", "Lorg/jetbrains/kotlin/fir/symbols/ConeTypeParameterLookupTag;", "getSymbolByLookupTag", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassifierLookupTag;", "toFirRegularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "toFirRegularClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "toSymbol", "useSiteSession", "withCombinedAttributesFrom", "other", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", "withParameterNameAnnotation", "parameter", "Lorg/jetbrains/kotlin/fir/FirFunctionTypeParameter;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LookupTagUtilsKt {
    public static final FirClassifierSymbol<?> toSymbol(ConeClassifierLookupTag coneClassifierLookupTag, FirSession useSiteSession) {
        Intrinsics.checkNotNullParameter(coneClassifierLookupTag, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        if (coneClassifierLookupTag instanceof ConeClassLikeLookupTag) {
            return toSymbol((ConeClassLikeLookupTag) coneClassifierLookupTag, useSiteSession);
        }
        if (coneClassifierLookupTag instanceof ConeClassifierLookupTagWithFixedSymbol) {
            return ((ConeClassifierLookupTagWithFixedSymbol) coneClassifierLookupTag).getSymbol();
        }
        return null;
    }

    public static final FirClassLikeSymbol<?> toSymbol(ConeClassLikeLookupTag coneClassLikeLookupTag, FirSession useSiteSession) {
        WeakPair<FirSession, FirClassLikeSymbol<?>> boundSymbol;
        Intrinsics.checkNotNullParameter(coneClassLikeLookupTag, "<this>");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        if (coneClassLikeLookupTag instanceof ConeClassLookupTagWithFixedSymbol) {
            return ((ConeClassLookupTagWithFixedSymbol) coneClassLikeLookupTag).getSymbol();
        }
        boolean z = coneClassLikeLookupTag instanceof ConeClassLikeLookupTagImpl;
        ConeClassLikeLookupTagImpl coneClassLikeLookupTagImpl = z ? (ConeClassLikeLookupTagImpl) coneClassLikeLookupTag : null;
        if (coneClassLikeLookupTagImpl != null && (boundSymbol = coneClassLikeLookupTagImpl.getBoundSymbol()) != null) {
            if (boundSymbol.getFirst() != useSiteSession) {
                boundSymbol = null;
            }
            if (boundSymbol != null) {
                return boundSymbol.getSecond();
            }
        }
        FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(useSiteSession).getClassLikeSymbolByClassId(coneClassLikeLookupTag.getClassId());
        ConeClassLikeLookupTagImpl coneClassLikeLookupTagImpl2 = z ? (ConeClassLikeLookupTagImpl) coneClassLikeLookupTag : null;
        if (coneClassLikeLookupTagImpl2 != null) {
            bindSymbolToLookupTag(coneClassLikeLookupTagImpl2, useSiteSession, classLikeSymbolByClassId);
        }
        return classLikeSymbolByClassId;
    }

    public static final FirRegularClassSymbol toFirRegularClassSymbol(ConeClassLikeLookupTag coneClassLikeLookupTag, FirSession session) {
        Intrinsics.checkNotNullParameter(coneClassLikeLookupTag, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirClassLikeSymbol<?> symbol = toSymbol(coneClassLikeLookupTag, session);
        if (symbol instanceof FirRegularClassSymbol) {
            return (FirRegularClassSymbol) symbol;
        }
        return null;
    }

    public static final void bindSymbolToLookupTag(ConeClassLikeLookupTagImpl coneClassLikeLookupTagImpl, FirSession session, FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(coneClassLikeLookupTagImpl, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        coneClassLikeLookupTagImpl.setBoundSymbol(new WeakPair<>(session, firClassLikeSymbol));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SymbolInternals
    public static final FirRegularClass toFirRegularClass(ConeClassLikeLookupTag coneClassLikeLookupTag, FirSession session) {
        Intrinsics.checkNotNullParameter(coneClassLikeLookupTag, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirRegularClassSymbol firRegularClassSymbol = toFirRegularClassSymbol(coneClassLikeLookupTag, session);
        if (firRegularClassSymbol != null) {
            return (FirRegularClass) firRegularClassSymbol.getFir();
        }
        return null;
    }

    public static final FirClassifierSymbol<?> getSymbolByLookupTag(FirSymbolProvider firSymbolProvider, ConeClassifierLookupTag lookupTag) {
        Intrinsics.checkNotNullParameter(firSymbolProvider, "<this>");
        Intrinsics.checkNotNullParameter(lookupTag, "lookupTag");
        return toSymbol(lookupTag, firSymbolProvider.getSession());
    }

    public static final FirClassLikeSymbol<?> getSymbolByLookupTag(FirSymbolProvider firSymbolProvider, ConeClassLikeLookupTag lookupTag) {
        Intrinsics.checkNotNullParameter(firSymbolProvider, "<this>");
        Intrinsics.checkNotNullParameter(lookupTag, "lookupTag");
        return toSymbol(lookupTag, firSymbolProvider.getSession());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ConeKotlinType withParameterNameAnnotation(ConeKotlinType coneKotlinType, FirFunctionTypeParameter parameter, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(session, "session");
        Name name2 = parameter.getName();
        if (name2 == null || Intrinsics.areEqual(name2, SpecialNames.NO_NAME_PROVIDED) || Intrinsics.areEqual(name2, SpecialNames.UNDERSCORE_FOR_UNUSED_VAR) || !FirAnnotationUtilsKt.getAnnotationsByClassId(CustomAnnotationTypeAttributeKt.getCustomAnnotations(coneKotlinType.getAttributes()), StandardNames.FqNames.parameterNameClassId, session).isEmpty()) {
            return coneKotlinType;
        }
        KtSourceElement source = parameter.getSource();
        List list = null;
        Object[] objArr = 0;
        KtSourceElement ktSourceElementFakeElement$default = source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ParameterNameAnnotationCall.INSTANCE, 0, 0, 6, null) : null;
        FirAnnotationBuilder firAnnotationBuilder = new FirAnnotationBuilder();
        firAnnotationBuilder.setSource(ktSourceElementFakeElement$default);
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setSource(ktSourceElementFakeElement$default);
        firResolvedTypeRefBuilder.setType(new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(StandardNames.FqNames.parameterNameClassId), new ConeTypeProjection[0], false, null, 8, null));
        firAnnotationBuilder.setAnnotationTypeRef(firResolvedTypeRefBuilder.mo6809build());
        FirAnnotationArgumentMappingBuilder firAnnotationArgumentMappingBuilder = new FirAnnotationArgumentMappingBuilder();
        Map<Name, FirExpression> mapping = firAnnotationArgumentMappingBuilder.getMapping();
        Name parameterNameName = StandardClassIds.Annotations.ParameterNames.INSTANCE.getParameterNameName();
        ConstantValueKind.String string = ConstantValueKind.String.INSTANCE;
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        mapping.put(parameterNameName, FirConstExpressionBuilderKt.buildConstExpression$default(ktSourceElementFakeElement$default, string, strAsString, null, true, 8, null));
        firAnnotationBuilder.setArgumentMapping(firAnnotationArgumentMappingBuilder.build());
        return withCombinedAttributesFrom(coneKotlinType, ConeAttributes.INSTANCE.create(CollectionsKt.listOf(new CustomAnnotationTypeAttribute(CollectionsKt.listOf(firAnnotationBuilder.mo6809build()), list, 2, objArr == true ? 1 : 0))));
    }

    public static final ConeKotlinType withCombinedAttributesFrom(ConeKotlinType coneKotlinType, ConeKotlinType other) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return withCombinedAttributesFrom(coneKotlinType, other.getAttributes());
    }

    private static final ConeKotlinType withCombinedAttributesFrom(ConeKotlinType coneKotlinType, ConeAttributes coneAttributes) {
        return coneAttributes.isEmpty() ? coneKotlinType : TypeUtilsKt.withAttributes(coneKotlinType, coneKotlinType.getAttributes().add(coneAttributes));
    }

    public static final ConeClassLikeLookupTag findClassRepresentation(ConeKotlinType coneKotlinType, ConeKotlinType dispatchReceiverParameterType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(dispatchReceiverParameterType, "dispatchReceiverParameterType");
        Intrinsics.checkNotNullParameter(session, "session");
        if (coneKotlinType instanceof ConeClassLikeType) {
            return TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) coneKotlinType, session, null, 2, null).getLookupTag();
        }
        if (coneKotlinType instanceof ConeDynamicType) {
            return findClassRepresentation(((ConeDynamicType) coneKotlinType).getUpperBound(), dispatchReceiverParameterType, session);
        }
        if (coneKotlinType instanceof ConeFlexibleType) {
            return findClassRepresentation(((ConeFlexibleType) coneKotlinType).getLowerBound(), dispatchReceiverParameterType, session);
        }
        if (coneKotlinType instanceof ConeCapturedType) {
            List<ConeKotlinType> supertypes = ((ConeCapturedType) coneKotlinType).getConstructor().getSupertypes();
            if (supertypes == null) {
                supertypes = CollectionsKt.emptyList();
            }
            return findClassRepresentationThatIsSubtypeOf(supertypes, dispatchReceiverParameterType, session);
        }
        if (coneKotlinType instanceof ConeDefinitelyNotNullType) {
            return findClassRepresentation(((ConeDefinitelyNotNullType) coneKotlinType).getOriginal(), dispatchReceiverParameterType, session);
        }
        if (coneKotlinType instanceof ConeIntegerLiteralType) {
            return findClassRepresentationThatIsSubtypeOf(((ConeIntegerLiteralType) coneKotlinType).getPossibleTypes(), dispatchReceiverParameterType, session);
        }
        if (coneKotlinType instanceof ConeIntersectionType) {
            return findClassRepresentationThatIsSubtypeOf(((ConeIntersectionType) coneKotlinType).getIntersectedTypes(), dispatchReceiverParameterType, session);
        }
        if (coneKotlinType instanceof ConeTypeParameterType) {
            return findClassRepresentationThatIsSubtypeOf(((ConeTypeParameterType) coneKotlinType).getLookupTag(), dispatchReceiverParameterType, session);
        }
        if (coneKotlinType instanceof ConeTypeVariableType) {
            TypeParameterMarker originalTypeParameter = ((ConeTypeVariableType) coneKotlinType).getLookupTag().getOriginalTypeParameter();
            ConeTypeParameterLookupTag coneTypeParameterLookupTag = originalTypeParameter instanceof ConeTypeParameterLookupTag ? (ConeTypeParameterLookupTag) originalTypeParameter : null;
            if (coneTypeParameterLookupTag != null) {
                return findClassRepresentationThatIsSubtypeOf(coneTypeParameterLookupTag, dispatchReceiverParameterType, session);
            }
            return null;
        }
        if (!(coneKotlinType instanceof ConeStubType)) {
            if (coneKotlinType instanceof ConeLookupTagBasedType) {
                return null;
            }
            throw new NoWhenBranchMatchedException();
        }
        TypeParameterMarker originalTypeParameter2 = ((ConeStubType) coneKotlinType).getConstructor().getVariable().getTypeConstructor().getOriginalTypeParameter();
        ConeTypeParameterLookupTag coneTypeParameterLookupTag2 = originalTypeParameter2 instanceof ConeTypeParameterLookupTag ? (ConeTypeParameterLookupTag) originalTypeParameter2 : null;
        if (coneTypeParameterLookupTag2 != null) {
            return findClassRepresentationThatIsSubtypeOf(coneTypeParameterLookupTag2, dispatchReceiverParameterType, session);
        }
        return null;
    }

    private static final ConeClassLikeLookupTag findClassRepresentationThatIsSubtypeOf(ConeTypeParameterLookupTag coneTypeParameterLookupTag, ConeKotlinType coneKotlinType, FirSession firSession) {
        List<FirResolvedTypeRef> resolvedBounds = coneTypeParameterLookupTag.getTypeParameterSymbol().getResolvedBounds();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedBounds, 10));
        Iterator<T> it = resolvedBounds.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it.next()));
        }
        return findClassRepresentationThatIsSubtypeOf(arrayList, coneKotlinType, firSession);
    }

    private static final ConeClassLikeLookupTag findClassRepresentationThatIsSubtypeOf(Collection<? extends ConeKotlinType> collection, ConeKotlinType coneKotlinType, FirSession firSession) {
        Object next;
        Iterator<T> it = collection.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!TypeUtilsKt.isSubtypeOf$default((ConeKotlinType) next, coneKotlinType, firSession, false, 4, null));
        ConeKotlinType coneKotlinType2 = (ConeKotlinType) next;
        if (coneKotlinType2 != null) {
            return findClassRepresentation(coneKotlinType2, coneKotlinType, firSession);
        }
        return null;
    }
}
