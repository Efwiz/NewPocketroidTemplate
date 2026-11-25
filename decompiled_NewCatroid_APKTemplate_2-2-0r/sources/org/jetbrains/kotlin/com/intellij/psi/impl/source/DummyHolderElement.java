package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;

/* JADX INFO: loaded from: classes3.dex */
public class DummyHolderElement extends FileElement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "text", "org/jetbrains/kotlin/com/intellij/psi/impl/source/DummyHolderElement", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DummyHolderElement(CharSequence charSequence) {
        super(TokenType.DUMMY_HOLDER, charSequence);
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
    }
}
