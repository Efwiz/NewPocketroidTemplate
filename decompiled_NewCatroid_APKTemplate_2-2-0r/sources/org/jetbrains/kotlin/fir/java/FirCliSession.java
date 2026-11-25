package org.jetbrains.kotlin.fir.java;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.PrivateSessionConstructor;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirCliSession.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/FirCliSession;", "Lorg/jetbrains/kotlin/fir/FirSession;", "sessionProvider", "Lorg/jetbrains/kotlin/fir/java/FirProjectSessionProvider;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/FirSession$Kind;", "(Lorg/jetbrains/kotlin/fir/java/FirProjectSessionProvider;Lorg/jetbrains/kotlin/fir/FirSession$Kind;)V", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCliSession extends FirSession {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @PrivateSessionConstructor
    public FirCliSession(FirProjectSessionProvider sessionProvider, FirSession.Kind kind) {
        super(sessionProvider, kind);
        Intrinsics.checkNotNullParameter(sessionProvider, "sessionProvider");
        Intrinsics.checkNotNullParameter(kind, "kind");
    }
}
