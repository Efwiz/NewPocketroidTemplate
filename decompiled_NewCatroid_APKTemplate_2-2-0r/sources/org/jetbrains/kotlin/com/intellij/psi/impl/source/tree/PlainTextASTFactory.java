package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.psi.PlainTextTokenTypes;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PlainTextASTFactory extends ASTFactory {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else {
            objArr[0] = "text";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/PlainTextASTFactory";
        objArr[2] = "createLeaf";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTFactory
    public LeafElement createLeaf(IElementType iElementType, CharSequence charSequence) {
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        if (iElementType == PlainTextTokenTypes.PLAIN_TEXT) {
            return new PsiPlainTextImpl(charSequence);
        }
        return null;
    }
}
