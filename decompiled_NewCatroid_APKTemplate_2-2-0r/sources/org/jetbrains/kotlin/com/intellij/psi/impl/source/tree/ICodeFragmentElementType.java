package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ICodeFragmentElementType extends IFileElementType {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "debugName", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/ICodeFragmentElementType", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ICodeFragmentElementType(String str, Language language) {
        super(str, language);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ICodeFragmentElementType(String str, Language language, boolean z) {
        super(str, language, z);
        if (str == null) {
            $$$reportNull$$$0(1);
        }
    }
}
