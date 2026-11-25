package org.jetbrains.kotlin.fir.scopes;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ImportingScopes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/ListStorageFirScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "result", "", "(Ljava/util/List;)V", "getResult", "()Ljava/util/List;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ListStorageFirScope extends FirScope {
    private final List<FirScope> result;

    /* JADX WARN: Multi-variable type inference failed */
    public ListStorageFirScope(List<? extends FirScope> result) {
        Intrinsics.checkNotNullParameter(result, "result");
        this.result = result;
    }

    public final List<FirScope> getResult() {
        return this.result;
    }
}
