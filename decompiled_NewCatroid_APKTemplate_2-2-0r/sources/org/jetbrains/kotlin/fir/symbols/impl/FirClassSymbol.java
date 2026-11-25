package org.jetbrains.kotlin.fir.symbols.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirClassLikeSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u000f\b\u0004\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0018\u001a\u00020\u0011H\u0016R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001b\u0010\u000b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r0\f8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\f8F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000fR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\f8F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u000f\u0082\u0001\u0002\u0019\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "C", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "(Lorg/jetbrains/kotlin/name/ClassId;)V", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getClassKind", "()Lorg/jetbrains/kotlin/descriptors/ClassKind;", "declarationSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getDeclarationSymbols", "()Ljava/util/List;", "lookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "resolvedSuperTypeRefs", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "getResolvedSuperTypeRefs", "resolvedSuperTypes", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getResolvedSuperTypes", "toLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousObjectSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirClassSymbol<C extends FirClass> extends FirClassLikeSymbol<C> {
    private final ConeClassLikeLookupTag lookupTag;

    public /* synthetic */ FirClassSymbol(ClassId classId, DefaultConstructorMarker defaultConstructorMarker) {
        this(classId);
    }

    private FirClassSymbol(ClassId classId) {
        super(classId, null);
        this.lookupTag = classId.isLocal() ? new ConeClassLookupTagWithFixedSymbol(classId, this) : TypeConstructionUtilsKt.toLookupTag(classId);
    }

    @Override // org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol
    /* JADX INFO: renamed from: toLookupTag, reason: from getter */
    public ConeClassLikeLookupTag getLookupTag() {
        return this.lookupTag;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final List<FirResolvedTypeRef> getResolvedSuperTypeRefs() {
        FirLazyDeclarationResolverKt.lazyResolveToPhase(this, FirResolvePhase.SUPER_TYPES);
        List superTypeRefs = ((FirClass) getFir()).getSuperTypeRefs();
        Intrinsics.checkNotNull(superTypeRefs, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.fir.types.FirResolvedTypeRef>");
        return superTypeRefs;
    }

    public final List<ConeKotlinType> getResolvedSuperTypes() {
        List<FirResolvedTypeRef> resolvedSuperTypeRefs = getResolvedSuperTypeRefs();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedSuperTypeRefs, 10));
        Iterator<T> it = resolvedSuperTypeRefs.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it.next()));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final List<FirBasedSymbol<?>> getDeclarationSymbols() {
        List<FirDeclaration> declarations = ((FirClass) getFir()).getDeclarations();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(declarations, 10));
        Iterator<T> it = declarations.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirDeclaration) it.next()).getSymbol());
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final ClassKind getClassKind() {
        return ((FirClass) getFir()).getClassKind();
    }
}
