package org.jetbrains.kotlin.com.intellij.openapi.application.impl;

import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;

/* JADX INFO: loaded from: classes3.dex */
public final class AnyModalityState extends ModalityState {
    public static final AnyModalityState ANY = new AnyModalityState();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "anotherState", "org/jetbrains/kotlin/com/intellij/openapi/application/impl/AnyModalityState", "dominates"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState
    public boolean dominates(ModalityState modalityState) {
        if (modalityState == null) {
            $$$reportNull$$$0(0);
        }
        return false;
    }

    private AnyModalityState() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState
    public String toString() {
        return "ANY";
    }
}
