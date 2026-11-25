package org.jetbrains.kotlin.resolve.calls.inference;

import kotlin.Metadata;

/* JADX INFO: compiled from: ConstraintSystemBuilder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0004H&¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemTransaction;", "", "()V", "closeTransaction", "", "rollbackTransaction", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConstraintSystemTransaction {
    public abstract void closeTransaction();

    public abstract void rollbackTransaction();
}
