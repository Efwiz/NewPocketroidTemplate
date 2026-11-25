package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;

/* JADX INFO: compiled from: TypeComponents.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/TypeComponents;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "typeApproximator", "Lorg/jetbrains/kotlin/fir/types/ConeTypeApproximator;", "getTypeApproximator", "()Lorg/jetbrains/kotlin/fir/types/ConeTypeApproximator;", "typeContext", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "getTypeContext", "()Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeComponents implements FirSessionComponent {
    private final FirSession session;
    private final ConeTypeApproximator typeApproximator;
    private final ConeInferenceContext typeContext;

    public TypeComponents(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        ConeInferenceContext coneInferenceContext = new ConeInferenceContext() { // from class: org.jetbrains.kotlin.fir.types.TypeComponents$typeContext$1
            @Override // org.jetbrains.kotlin.fir.types.ConeTypeContext
            /* JADX INFO: renamed from: getSession */
            public FirSession get$session() {
                return this.this$0.getSession();
            }
        };
        this.typeContext = coneInferenceContext;
        this.typeApproximator = new ConeTypeApproximator(coneInferenceContext, FirLanguageSettingsComponentKt.getLanguageVersionSettings(session));
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final ConeInferenceContext getTypeContext() {
        return this.typeContext;
    }

    public final ConeTypeApproximator getTypeApproximator() {
        return this.typeApproximator;
    }
}
