package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;

/* JADX INFO: compiled from: FirEnumEntriesSupport.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirJvmEnumEntriesSupport;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirEnumEntriesSupport;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "canSynthesizeEnumEntriesFor", "", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmEnumEntriesSupport extends FirEnumEntriesSupport {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirJvmEnumEntriesSupport(FirSession session) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirEnumEntriesSupport
    public boolean canSynthesizeEnumEntriesFor(FirClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return isEnumEntriesAvailable();
    }
}
