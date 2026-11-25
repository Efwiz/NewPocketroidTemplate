package org.jetbrains.kotlin.fir.declarations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.resolve.SessionHolder;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValueForScript;
import org.jetbrains.kotlin.fir.scopes.impl.FirScriptDeclarationsScope;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: ScriptScopes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"collectTowerDataElementsForScript", "Lorg/jetbrains/kotlin/fir/declarations/TowerElementsForScript;", "Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;", "owner", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptScopesKt {
    public static final TowerElementsForScript collectTowerDataElementsForScript(SessionHolder sessionHolder, FirScript owner) {
        Intrinsics.checkNotNullParameter(sessionHolder, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(owner, FirResolvePhase.TYPES);
        List<FirContextReceiver> contextReceivers = owner.getContextReceivers();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceivers, 10));
        int i = 0;
        for (Iterator it = contextReceivers.iterator(); it.hasNext(); it = it) {
            Object next = it.next();
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            FirContextReceiver firContextReceiver = (FirContextReceiver) next;
            arrayList.add(new ImplicitReceiverValueForScript(owner.getSymbol(), FirTypeUtilsKt.getConeType(firContextReceiver.getTypeRef()), UtilsKt.getLabelName(firContextReceiver), sessionHolder.getSession(), sessionHolder.getScopeSession(), false, i, 32, null));
            i = i2;
        }
        return new TowerElementsForScript(arrayList, new FirScriptDeclarationsScope(sessionHolder.getSession(), owner));
    }
}
