package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirEnumEntriesSupport.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u001b\u0010\u0005\u001a\u00020\u00068DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0005\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirEnumEntriesSupport;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "isEnumEntriesAvailable", "", "()Z", "isEnumEntriesAvailable$delegate", "Lkotlin/Lazy;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "canSynthesizeEnumEntriesFor", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirEnumEntriesSupport implements FirSessionComponent {

    /* JADX INFO: renamed from: isEnumEntriesAvailable$delegate, reason: from kotlin metadata */
    private final Lazy isEnumEntriesAvailable;
    private final FirSession session;

    public FirEnumEntriesSupport(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        this.isEnumEntriesAvailable = LazyKt.lazy(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirEnumEntriesSupport.isEnumEntriesAvailable.2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(FirSymbolProviderKt.getRegularClassSymbolByClassId(FirSymbolProviderKt.getSymbolProvider(FirEnumEntriesSupport.this.getSession()), StandardClassIds.INSTANCE.getEnumEntries()) != null);
            }
        });
    }

    public final FirSession getSession() {
        return this.session;
    }

    protected final boolean isEnumEntriesAvailable() {
        return ((Boolean) this.isEnumEntriesAvailable.getValue()).booleanValue();
    }

    public boolean canSynthesizeEnumEntriesFor(FirClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return ClassMembersKt.getHasEnumEntries(klass) && isEnumEntriesAvailable();
    }
}
