package org.jetbrains.kotlin.fir.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;

/* JADX INFO: compiled from: SupertypeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH&¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/SupertypeSupplier;", "", "()V", "expansionForTypeAlias", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "forClass", "", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Default", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SupertypeSupplier {
    public abstract ConeClassLikeType expansionForTypeAlias(FirTypeAlias typeAlias, FirSession useSiteSession);

    public abstract List<ConeClassLikeType> forClass(FirClass firClass, FirSession useSiteSession);

    /* JADX INFO: compiled from: SupertypeUtils.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/SupertypeSupplier$Default;", "Lorg/jetbrains/kotlin/fir/resolve/SupertypeSupplier;", "()V", "expansionForTypeAlias", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "forClass", "", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default extends SupertypeSupplier {
        public static final Default INSTANCE = new Default();

        private Default() {
        }

        @Override // org.jetbrains.kotlin.fir.resolve.SupertypeSupplier
        public List<ConeClassLikeType> forClass(FirClass firClass, FirSession useSiteSession) {
            Intrinsics.checkNotNullParameter(firClass, "firClass");
            Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
            if (!firClass.getSymbol().getClassId().isLocal()) {
                FirLazyDeclarationResolverKt.lazyResolveToPhase(firClass, FirResolvePhase.SUPER_TYPES);
            }
            return FirDeclarationUtilKt.getSuperConeTypes(firClass);
        }

        @Override // org.jetbrains.kotlin.fir.resolve.SupertypeSupplier
        public ConeClassLikeType expansionForTypeAlias(FirTypeAlias typeAlias, FirSession useSiteSession) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
            FirLazyDeclarationResolverKt.lazyResolveToPhase(typeAlias, FirResolvePhase.SUPER_TYPES);
            return FirDeclarationUtilKt.getExpandedConeType(typeAlias);
        }
    }
}
