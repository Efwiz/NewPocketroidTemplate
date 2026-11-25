package org.jetbrains.kotlin.fir.symbols.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.mpp.RegularClassSymbolMarker;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirClassLikeSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u00008F¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "(Lorg/jetbrains/kotlin/name/ClassId;)V", "companionObjectSymbol", "getCompanionObjectSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "resolvedContextReceivers", "", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "getResolvedContextReceivers", "()Ljava/util/List;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirRegularClassSymbol extends FirClassSymbol<FirRegularClass> implements RegularClassSymbolMarker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirRegularClassSymbol(ClassId classId) {
        super(classId, null);
        Intrinsics.checkNotNullParameter(classId, "classId");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final FirRegularClassSymbol getCompanionObjectSymbol() {
        return ((FirRegularClass) getFir()).getCompanionObjectSymbol();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final List<FirContextReceiver> getResolvedContextReceivers() {
        if (((FirRegularClass) getFir()).getContextReceivers().isEmpty()) {
            return CollectionsKt.emptyList();
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(this, FirResolvePhase.TYPES);
        return ((FirRegularClass) getFir()).getContextReceivers();
    }
}
