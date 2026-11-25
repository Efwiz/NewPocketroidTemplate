package org.jetbrains.kotlin.fir.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirPredicateBasedProviderImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\u001a4\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0010\u0010\u0007\u001a\f\u0012\b\u0012\u00060\tj\u0002`\n0\b2\u0006\u0010\u000b\u001a\u00020\u0001\u001aX\u0010\f\u001a\u00020\u0001*\u0004\u0018\u00010\r2\u0006\u0010\u0003\u001a\u00020\u00042\u0010\u0010\u0007\u001a\f\u0012\b\u0012\u00060\tj\u0002`\n0\b2\u0006\u0010\u000b\u001a\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\u001a\b\u0002\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00120\u0011¨\u0006\u0013"}, d2 = {"markedWithMetaAnnotation", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "containingDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "metaAnnotations", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "includeItself", "markedWithMetaAnnotationImpl", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "visited", "", "resolvedCompilerAnnotations", "Lkotlin/Function1;", "", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirPredicateBasedProviderImplKt {
    public static final boolean markedWithMetaAnnotation(FirAnnotation firAnnotation, FirSession session, FirDeclaration containingDeclaration, Set<FqName> metaAnnotations, boolean z) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(metaAnnotations, "metaAnnotations");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(containingDeclaration.getSymbol(), FirResolvePhase.COMPILER_REQUIRED_ANNOTATIONS);
        FirTypeRef annotationTypeRef = firAnnotation.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeKotlinType)) {
            type = null;
        }
        return markedWithMetaAnnotationImpl$default(type != null ? TypeUtilsKt.toRegularClassSymbol(type, session) : null, session, metaAnnotations, z, new LinkedHashSet(), null, 16, null);
    }

    public static /* synthetic */ boolean markedWithMetaAnnotationImpl$default(FirRegularClassSymbol firRegularClassSymbol, FirSession firSession, Set set, boolean z, Set set2, Function1 function1, int i, Object obj) {
        if ((i & 16) != 0) {
            function1 = new PropertyReference1() { // from class: org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProviderImplKt.markedWithMetaAnnotationImpl.1
                @Override // kotlin.jvm.internal.CallableReference
                public String getSignature() {
                    return "getResolvedCompilerAnnotationsWithClassIds()Ljava/util/List;";
                }

                @Override // kotlin.reflect.KProperty1
                public Object get(Object obj2) {
                    return ((FirBasedSymbol) obj2).getResolvedCompilerAnnotationsWithClassIds();
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public String getName() {
                    return "resolvedCompilerAnnotationsWithClassIds";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinClass(FirBasedSymbol.class);
                }
            };
        }
        return markedWithMetaAnnotationImpl(firRegularClassSymbol, firSession, set, z, set2, function1);
    }

    public static final boolean markedWithMetaAnnotationImpl(FirRegularClassSymbol firRegularClassSymbol, FirSession session, Set<FqName> metaAnnotations, boolean z, Set<FirRegularClassSymbol> visited, Function1<? super FirRegularClassSymbol, ? extends List<? extends FirAnnotation>> resolvedCompilerAnnotations) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(metaAnnotations, "metaAnnotations");
        Intrinsics.checkNotNullParameter(visited, "visited");
        Intrinsics.checkNotNullParameter(resolvedCompilerAnnotations, "resolvedCompilerAnnotations");
        if (firRegularClassSymbol == null || !visited.add(firRegularClassSymbol)) {
            return false;
        }
        if (metaAnnotations.contains(firRegularClassSymbol.getClassId().asSingleFqName())) {
            return z;
        }
        List<? extends FirAnnotation> listInvoke = resolvedCompilerAnnotations.invoke(firRegularClassSymbol);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listInvoke.iterator();
        while (it.hasNext()) {
            FirTypeRef annotationTypeRef = ((FirAnnotation) it.next()).getAnnotationTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeKotlinType)) {
                type = null;
            }
            FirRegularClassSymbol regularClassSymbol = type != null ? TypeUtilsKt.toRegularClassSymbol(type, session) : null;
            if (regularClassSymbol != null) {
                arrayList.add(regularClassSymbol);
            }
        }
        ArrayList arrayList2 = arrayList;
        if ((arrayList2 instanceof Collection) && arrayList2.isEmpty()) {
            return false;
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            if (markedWithMetaAnnotationImpl((FirRegularClassSymbol) it2.next(), session, metaAnnotations, true, visited, resolvedCompilerAnnotations)) {
                return true;
            }
        }
        return false;
    }
}
