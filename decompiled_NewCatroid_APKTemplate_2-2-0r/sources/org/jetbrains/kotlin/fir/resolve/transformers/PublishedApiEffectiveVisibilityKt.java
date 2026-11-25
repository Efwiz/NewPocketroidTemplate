package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataRegistry;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLikeLookupTagImpl;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: publishedApiEffectiveVisibility.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001aL\u0010\u0015\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00022\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u00022\b\u0010\u001e\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020!\u001aV\u0010\u0015\u001a\u0004\u0018\u00010\u00022\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00022\f\u0010%\u001a\b\u0012\u0002\b\u0003\u0018\u00010&2\u0006\u0010\u001d\u001a\u00020\u00022\b\u0010\u001e\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020!\u001a$\u0010'\u001a\u00020(*\u00020)2\u0006\u0010*\u001a\u00020\u00172\b\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010 \u001a\u00020!\u001a*\u0010'\u001a\u00020(*\u00020)2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#2\b\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010 \u001a\u00020!\u001a\u0012\u0010'\u001a\u00020(*\u00020)2\u0006\u0010 \u001a\u00020!\"C\u0010\u0003\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001*\u00020\u00042\u0010\u0010\u0000\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u00018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\"3\u0010\u000b\u001a\u0004\u0018\u00010\u0002*\u00020\u00042\b\u0010\u0000\u001a\u0004\u0018\u00010\u00028F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0010\u0010\n\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\"\u0017\u0010\u0011\u001a\u0004\u0018\u00010\u0002*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\r\"\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0002*\u0006\u0012\u0002\b\u00030\u00138Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0014¨\u0006-"}, d2 = {"<set-?>", "Lkotlin/Lazy;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "lazyPublishedApiEffectiveVisibility", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getLazyPublishedApiEffectiveVisibility", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Lkotlin/Lazy;", "setLazyPublishedApiEffectiveVisibility", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lkotlin/Lazy;)V", "lazyPublishedApiEffectiveVisibility$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "nonLazyPublishedApiEffectiveVisibility", "getNonLazyPublishedApiEffectiveVisibility", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "setNonLazyPublishedApiEffectiveVisibility", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;)V", "nonLazyPublishedApiEffectiveVisibility$delegate", "publishedApiEffectiveVisibility", "getPublishedApiEffectiveVisibility", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "computePublishedApiEffectiveVisibility", "hasPublishedApiAnnotation", "", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "selfEffectiveVisibility", "containingClassLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "parentEffectiveVisibility", "parentPublishedEffectiveVisibility", "forClass", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "containingClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "setLazyPublishedVisibility", "", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "hasPublishedApi", "parentProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PublishedApiEffectiveVisibilityKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(PublishedApiEffectiveVisibilityKt.class, "semantics"), "nonLazyPublishedApiEffectiveVisibility", "getNonLazyPublishedApiEffectiveVisibility(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(PublishedApiEffectiveVisibilityKt.class, "semantics"), "lazyPublishedApiEffectiveVisibility", "getLazyPublishedApiEffectiveVisibility(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Lkotlin/Lazy;"))};
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor nonLazyPublishedApiEffectiveVisibility$delegate = FirDeclarationDataRegistry.INSTANCE.data(PublishedApiEffectiveVisibilityKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor lazyPublishedApiEffectiveVisibility$delegate = FirDeclarationDataRegistry.INSTANCE.data(LazyPublishedApiEffectiveVisibilityKey.INSTANCE);

    public static final EffectiveVisibility getNonLazyPublishedApiEffectiveVisibility(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return (EffectiveVisibility) nonLazyPublishedApiEffectiveVisibility$delegate.getValue(firDeclaration, $$delegatedProperties[0]);
    }

    public static final void setNonLazyPublishedApiEffectiveVisibility(FirDeclaration firDeclaration, EffectiveVisibility effectiveVisibility) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        nonLazyPublishedApiEffectiveVisibility$delegate.setValue(firDeclaration, $$delegatedProperties[0], effectiveVisibility);
    }

    public static final Lazy<EffectiveVisibility> getLazyPublishedApiEffectiveVisibility(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return (Lazy) lazyPublishedApiEffectiveVisibility$delegate.getValue(firDeclaration, $$delegatedProperties[1]);
    }

    public static final void setLazyPublishedApiEffectiveVisibility(FirDeclaration firDeclaration, Lazy<? extends EffectiveVisibility> lazy) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        lazyPublishedApiEffectiveVisibility$delegate.setValue(firDeclaration, $$delegatedProperties[1], lazy);
    }

    public static final EffectiveVisibility getPublishedApiEffectiveVisibility(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        EffectiveVisibility nonLazyPublishedApiEffectiveVisibility = getNonLazyPublishedApiEffectiveVisibility(firDeclaration);
        if (nonLazyPublishedApiEffectiveVisibility != null) {
            return nonLazyPublishedApiEffectiveVisibility;
        }
        Lazy<EffectiveVisibility> lazyPublishedApiEffectiveVisibility = getLazyPublishedApiEffectiveVisibility(firDeclaration);
        if (lazyPublishedApiEffectiveVisibility != null) {
            return lazyPublishedApiEffectiveVisibility.getValue();
        }
        return null;
    }

    public static final EffectiveVisibility getPublishedApiEffectiveVisibility(FirBasedSymbol<?> firBasedSymbol) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firBasedSymbol, FirResolvePhase.STATUS);
        return getPublishedApiEffectiveVisibility(firBasedSymbol.getFir());
    }

    public static final EffectiveVisibility computePublishedApiEffectiveVisibility(List<? extends FirAnnotation> annotations, Visibility visibility, EffectiveVisibility selfEffectiveVisibility, FirClassLikeSymbol<?> firClassLikeSymbol, EffectiveVisibility parentEffectiveVisibility, EffectiveVisibility effectiveVisibility, boolean z, FirSession session) {
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(selfEffectiveVisibility, "selfEffectiveVisibility");
        Intrinsics.checkNotNullParameter(parentEffectiveVisibility, "parentEffectiveVisibility");
        Intrinsics.checkNotNullParameter(session, "session");
        List<? extends FirAnnotation> list = annotations;
        boolean z2 = false;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                FirTypeRef typeRef = ((FirAnnotation) it.next()).getTypeRef();
                FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
                ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                if (!(type instanceof ConeClassLikeType)) {
                    type = null;
                }
                ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
                if (Intrinsics.areEqual((coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) ? null : lookupTag.getClassId(), StandardClassIds.Annotations.INSTANCE.getPublishedApi())) {
                    z2 = true;
                    break;
                }
            }
        }
        return computePublishedApiEffectiveVisibility(z2, visibility, selfEffectiveVisibility, firClassLikeSymbol != null ? firClassLikeSymbol.getLookupTag() : null, parentEffectiveVisibility, effectiveVisibility, z, session);
    }

    public static final EffectiveVisibility computePublishedApiEffectiveVisibility(boolean z, Visibility visibility, EffectiveVisibility selfEffectiveVisibility, ConeClassLikeLookupTag coneClassLikeLookupTag, EffectiveVisibility parentEffectiveVisibility, EffectiveVisibility effectiveVisibility, boolean z2, FirSession session) {
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(selfEffectiveVisibility, "selfEffectiveVisibility");
        Intrinsics.checkNotNullParameter(parentEffectiveVisibility, "parentEffectiveVisibility");
        Intrinsics.checkNotNullParameter(session, "session");
        EffectiveVisibility effectiveVisibility2 = z ? EffectiveVisibilityUtilsKt.toEffectiveVisibility(visibility, coneClassLikeLookupTag, z2, true) : null;
        if (effectiveVisibility2 == null && effectiveVisibility == null) {
            return null;
        }
        if (effectiveVisibility != null) {
            parentEffectiveVisibility = effectiveVisibility;
        }
        if (effectiveVisibility2 != null) {
            selfEffectiveVisibility = effectiveVisibility2;
        }
        return parentEffectiveVisibility.lowerBound(selfEffectiveVisibility, TypeComponentsKt.getTypeContext(session));
    }

    public static final void setLazyPublishedVisibility(FirMemberDeclaration firMemberDeclaration, FirSession session) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        setLazyPublishedVisibility(firMemberDeclaration, firMemberDeclaration.getAnnotations(), (FirProperty) null, session);
    }

    public static final void setLazyPublishedVisibility(FirMemberDeclaration firMemberDeclaration, List<? extends FirAnnotation> annotations, FirProperty firProperty, FirSession session) {
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(session, "session");
        List<? extends FirAnnotation> list = annotations;
        boolean z = false;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                FirTypeRef annotationTypeRef = ((FirAnnotation) it.next()).getAnnotationTypeRef();
                ClassId classId = null;
                FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
                ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                ConeClassLikeType coneClassLikeType = type instanceof ConeClassLikeType ? (ConeClassLikeType) type : null;
                if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null) {
                    classId = lookupTag.getClassId();
                }
                if (Intrinsics.areEqual(classId, StandardClassIds.Annotations.INSTANCE.getPublishedApi())) {
                    z = true;
                    break;
                }
            }
        }
        setLazyPublishedVisibility(firMemberDeclaration, z, firProperty, session);
    }

    public static final void setLazyPublishedVisibility(final FirMemberDeclaration firMemberDeclaration, boolean z, final FirProperty firProperty, final FirSession session) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (z) {
            setLazyPublishedApiEffectiveVisibility(firMemberDeclaration, LazyKt.lazy(new Function0<EffectiveVisibility>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.PublishedApiEffectiveVisibilityKt.setLazyPublishedVisibility.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Code duplicated, block: B:30:0x0084 A[DONT_INVERT] */
                /* JADX WARN: Code duplicated, block: B:31:0x0086  */
                /* JADX WARN: Code duplicated, block: B:32:0x008f  */
                /* JADX WARN: Type inference failed for: r0v21, types: [org.jetbrains.kotlin.fir.declarations.FirDeclaration] */
                @Override // kotlin.jvm.functions.Function0
                public final EffectiveVisibility invoke() {
                    ClassId classId;
                    EffectiveVisibility.Public effectiveVisibility;
                    EffectiveVisibility effectiveVisibility2;
                    EffectiveVisibility publishedApiEffectiveVisibility;
                    FirProperty firProperty2 = firProperty;
                    EffectiveVisibility publishedApiEffectiveVisibility2 = null;
                    if (firProperty2 != null) {
                        classId = firProperty2.getSymbol().getCallableId().getClassId();
                    } else {
                        FirMemberDeclaration firMemberDeclaration2 = firMemberDeclaration;
                        if (firMemberDeclaration2 instanceof FirClassLikeDeclaration) {
                            classId = FirDeclarationUtilKt.getClassId((FirClassLikeDeclaration) firMemberDeclaration2).getParentClassId();
                        } else {
                            classId = firMemberDeclaration2 instanceof FirCallableDeclaration ? ((FirCallableDeclaration) firMemberDeclaration2).getSymbol().getCallableId().getClassId() : null;
                        }
                    }
                    ConeClassLikeLookupTagImpl lookupTag = classId != null ? TypeConstructionUtilsKt.toLookupTag(classId) : null;
                    FirDeclarationStatus status = firMemberDeclaration.getStatus();
                    Intrinsics.checkNotNull(status, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus");
                    FirResolvedDeclarationStatus firResolvedDeclarationStatus = (FirResolvedDeclarationStatus) status;
                    FirClassLikeSymbol<?> symbol = lookupTag != null ? LookupTagUtilsKt.toSymbol((ConeClassLikeLookupTag) lookupTag, session) : null;
                    Visibility visibility = firResolvedDeclarationStatus.getVisibility();
                    EffectiveVisibility effectiveVisibility3 = firResolvedDeclarationStatus.getEffectiveVisibility();
                    ConeClassLikeLookupTagImpl coneClassLikeLookupTagImpl = lookupTag;
                    FirProperty firProperty3 = firProperty;
                    if (firProperty3 != null) {
                        FirDeclarationStatus status2 = firProperty3.getStatus();
                        FirResolvedDeclarationStatus firResolvedDeclarationStatus2 = status2 instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status2 : null;
                        if (firResolvedDeclarationStatus2 == null || (effectiveVisibility = firResolvedDeclarationStatus2.getEffectiveVisibility()) == null) {
                            effectiveVisibility = EffectiveVisibility.Local.INSTANCE;
                        }
                        if (effectiveVisibility == null) {
                            if (symbol != null) {
                                effectiveVisibility = EffectiveVisibility.Public.INSTANCE;
                            } else {
                                effectiveVisibility = symbol.getResolvedStatus().getEffectiveVisibility();
                            }
                        }
                    } else if (symbol != null) {
                        effectiveVisibility = EffectiveVisibility.Public.INSTANCE;
                    } else {
                        effectiveVisibility = symbol.getResolvedStatus().getEffectiveVisibility();
                    }
                    EffectiveVisibility effectiveVisibility4 = effectiveVisibility;
                    FirProperty firProperty4 = firProperty;
                    if (firProperty4 == null || (publishedApiEffectiveVisibility = PublishedApiEffectiveVisibilityKt.getPublishedApiEffectiveVisibility(firProperty4)) == null) {
                        if (symbol != null) {
                            FirClassLikeSymbol<?> firClassLikeSymbol = symbol;
                            FirLazyDeclarationResolverKt.lazyResolveToPhase(firClassLikeSymbol, FirResolvePhase.STATUS);
                            publishedApiEffectiveVisibility2 = PublishedApiEffectiveVisibilityKt.getPublishedApiEffectiveVisibility((FirDeclaration) firClassLikeSymbol.getFir());
                        }
                        effectiveVisibility2 = publishedApiEffectiveVisibility2;
                    } else {
                        effectiveVisibility2 = publishedApiEffectiveVisibility;
                    }
                    return PublishedApiEffectiveVisibilityKt.computePublishedApiEffectiveVisibility(true, visibility, effectiveVisibility3, (ConeClassLikeLookupTag) coneClassLikeLookupTagImpl, effectiveVisibility4, effectiveVisibility2, firMemberDeclaration instanceof FirClass, session);
                }
            }));
        }
    }
}
