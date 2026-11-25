package org.jetbrains.kotlin.resolve.calls.inference;

import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: loaded from: classes6.dex */
public interface ConstraintSystemCompleter {
    void completeConstraintSystem(ConstraintSystem.Builder builder, ResolvedCall<?> resolvedCall);
}
