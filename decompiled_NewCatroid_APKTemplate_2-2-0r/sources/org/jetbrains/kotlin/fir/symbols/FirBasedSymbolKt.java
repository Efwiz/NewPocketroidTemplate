package org.jetbrains.kotlin.fir.symbols;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirBasedSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0014\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0007\u001a\u0018\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0002\u001a\u001e\u0010\u0007\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\t0\b2\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0007\u001a\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b*\u00020\u00062\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0007\u001a\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\b*\u00020\u00062\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0007\u001a\u001e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\b*\u00020\u00062\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0007\u001a\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b*\u00020\u00062\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0007¨\u0006\u000f"}, d2 = {"resolveAnnotationsWithClassIds", "", "anchorElement", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "isDefinitelyEmpty", "", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "resolveAnnotationsWithArguments", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "resolvedAnnotationClassIds", "Lorg/jetbrains/kotlin/name/ClassId;", "resolvedAnnotationsWithArguments", "resolvedAnnotationsWithClassIds", "resolvedCompilerRequiredAnnotations", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirBasedSymbolKt {
    @SymbolInternals
    public static final List<FirAnnotation> resolvedCompilerRequiredAnnotations(FirAnnotationContainer firAnnotationContainer, FirBasedSymbol<?> anchorElement) {
        Intrinsics.checkNotNullParameter(firAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(anchorElement, "anchorElement");
        if (firAnnotationContainer.getAnnotations().isEmpty()) {
            return CollectionsKt.emptyList();
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(anchorElement, FirResolvePhase.COMPILER_REQUIRED_ANNOTATIONS);
        return firAnnotationContainer.getAnnotations();
    }

    @SymbolInternals
    public static final List<FirAnnotation> resolvedAnnotationsWithArguments(FirAnnotationContainer firAnnotationContainer, FirBasedSymbol<?> anchorElement) {
        Intrinsics.checkNotNullParameter(firAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(anchorElement, "anchorElement");
        if (isDefinitelyEmpty(firAnnotationContainer, anchorElement)) {
            return CollectionsKt.emptyList();
        }
        resolveAnnotationsWithArguments(firAnnotationContainer.getAnnotations(), anchorElement);
        return firAnnotationContainer.getAnnotations();
    }

    @SymbolInternals
    public static final void resolveAnnotationsWithArguments(List<? extends FirAnnotation> list, FirBasedSymbol<?> anchorElement) {
        FirResolvePhase firResolvePhase;
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(anchorElement, "anchorElement");
        int size = list.size();
        for (int i = 0; i < size; i++) {
            FirAnnotationContainer firAnnotationContainer = (FirAnnotation) list.get(i);
            if ((firAnnotationContainer instanceof FirAnnotationCall) && !((FirCall) firAnnotationContainer).getArgumentList().getArguments().isEmpty()) {
                firResolvePhase = FirResolvePhase.ANNOTATIONS_ARGUMENTS_MAPPING;
                FirLazyDeclarationResolverKt.lazyResolveToPhase(anchorElement, firResolvePhase);
            }
        }
        firResolvePhase = FirResolvePhase.TYPES;
        FirLazyDeclarationResolverKt.lazyResolveToPhase(anchorElement, firResolvePhase);
    }

    private static final boolean isDefinitelyEmpty(FirAnnotationContainer firAnnotationContainer, FirBasedSymbol<?> firBasedSymbol) {
        if (!firAnnotationContainer.getAnnotations().isEmpty()) {
            return false;
        }
        if (!(firBasedSymbol instanceof FirBackingFieldSymbol)) {
            return true;
        }
        List<FirAnnotation> annotations = ((FirBackingFieldSymbol) firBasedSymbol).getPropertySymbol().getAnnotations();
        if (!(annotations instanceof Collection) || !annotations.isEmpty()) {
            Iterator<T> it = annotations.iterator();
            while (it.hasNext()) {
                if (((FirAnnotation) it.next()).getUseSiteTarget() == null) {
                    return false;
                }
            }
        }
        return true;
    }

    @SymbolInternals
    public static final List<FirAnnotation> resolvedAnnotationsWithClassIds(FirAnnotationContainer firAnnotationContainer, FirBasedSymbol<?> anchorElement) {
        Intrinsics.checkNotNullParameter(firAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(anchorElement, "anchorElement");
        if (isDefinitelyEmpty(firAnnotationContainer, anchorElement)) {
            return CollectionsKt.emptyList();
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(anchorElement, FirResolvePhase.TYPES);
        return firAnnotationContainer.getAnnotations();
    }

    @SymbolInternals
    public static final void resolveAnnotationsWithClassIds(FirBasedSymbol<?> anchorElement) {
        Intrinsics.checkNotNullParameter(anchorElement, "anchorElement");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(anchorElement, FirResolvePhase.TYPES);
    }

    @SymbolInternals
    public static final List<ClassId> resolvedAnnotationClassIds(FirAnnotationContainer firAnnotationContainer, FirBasedSymbol<?> anchorElement) {
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(firAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(anchorElement, "anchorElement");
        List<FirAnnotation> listResolvedAnnotationsWithClassIds = resolvedAnnotationsWithClassIds(firAnnotationContainer, anchorElement);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listResolvedAnnotationsWithClassIds.iterator();
        while (it.hasNext()) {
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(((FirAnnotation) it.next()).getAnnotationTypeRef());
            ClassId classId = null;
            ConeClassLikeType coneClassLikeType = coneType instanceof ConeClassLikeType ? (ConeClassLikeType) coneType : null;
            if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null) {
                classId = lookupTag.getClassId();
            }
            if (classId != null) {
                arrayList.add(classId);
            }
        }
        return arrayList;
    }
}
