package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;

/* JADX INFO: compiled from: FirCompanionGenerationProcessor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a!\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"runCompanionGenerationPhaseForLocalClass", "F", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirCompanionGenerationProcessorKt {
    public static final <F extends FirClassLikeDeclaration> F runCompanionGenerationPhaseForLocalClass(F f, FirSession session) {
        Intrinsics.checkNotNullParameter(f, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return (F) FirTransformerUtilKt.transformSingle(f, new FirCompanionGenerationTransformer(session), null);
    }
}
