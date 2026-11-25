package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.LocalClassesNavigationInfo;
import org.jetbrains.kotlin.fir.scopes.FirCompositeScope;
import org.jetbrains.kotlin.fir.scopes.FirScope;

/* JADX INFO: compiled from: FirStatusResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a?\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"runStatusResolveForLocalClass", "F", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "scopesForLocalClass", "", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "localClassesNavigationInfo", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/LocalClassesNavigationInfo;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Ljava/util/List;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/LocalClassesNavigationInfo;)Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirStatusResolveTransformerKt {
    public static final <F extends FirClassLikeDeclaration> F runStatusResolveForLocalClass(F f, FirSession session, ScopeSession scopeSession, List<? extends FirScope> scopesForLocalClass, LocalClassesNavigationInfo localClassesNavigationInfo) {
        Intrinsics.checkNotNullParameter(f, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(scopesForLocalClass, "scopesForLocalClass");
        Intrinsics.checkNotNullParameter(localClassesNavigationInfo, "localClassesNavigationInfo");
        return (F) f.transform(new FirStatusResolveTransformer(session, scopeSession, new StatusComputationSession(), localClassesNavigationInfo.getParentForClass(), new FirCompositeScope(scopesForLocalClass)), null);
    }
}
