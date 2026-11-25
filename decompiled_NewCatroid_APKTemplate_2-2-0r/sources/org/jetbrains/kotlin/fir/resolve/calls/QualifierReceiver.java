package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.scopes.FirScope;

/* JADX INFO: compiled from: QualifierReceiver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\n\u0010\t\u001a\u0004\u0018\u00010\bH&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/QualifierReceiver;", "", "explicitReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;)V", "getExplicitReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "callableScope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "classifierScope", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class QualifierReceiver {
    private final FirResolvedQualifier explicitReceiver;

    public abstract FirScope callableScope();

    public abstract FirScope classifierScope();

    public QualifierReceiver(FirResolvedQualifier explicitReceiver) {
        Intrinsics.checkNotNullParameter(explicitReceiver, "explicitReceiver");
        this.explicitReceiver = explicitReceiver;
    }

    public final FirResolvedQualifier getExplicitReceiver() {
        return this.explicitReceiver;
    }
}
