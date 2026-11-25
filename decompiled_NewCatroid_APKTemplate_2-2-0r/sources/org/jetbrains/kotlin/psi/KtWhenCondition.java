package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;

/* JADX INFO: loaded from: classes6.dex */
public abstract class KtWhenCondition extends KtElementImpl {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "node", "org/jetbrains/kotlin/psi/KtWhenCondition", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtWhenCondition(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }
}
