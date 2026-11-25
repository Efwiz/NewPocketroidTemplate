package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.PlainTextLanguage;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;

/* JADX INFO: loaded from: classes3.dex */
public final class PlainTextTokenTypes {
    public static final IElementType PLAIN_TEXT_FILE = new IFileElementType("PLAIN_TEXT_FILE", PlainTextLanguage.INSTANCE) { // from class: org.jetbrains.kotlin.com.intellij.psi.PlainTextTokenTypes.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "chameleon", "org/jetbrains/kotlin/com/intellij/psi/PlainTextTokenTypes$1", "parseContents"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
        public ASTNode parseContents(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            return ASTFactory.leaf(PlainTextTokenTypes.PLAIN_TEXT, aSTNode.getChars());
        }
    };
    public static final IElementType PLAIN_TEXT = new IElementType("PLAIN_TEXT", PlainTextLanguage.INSTANCE);

    private PlainTextTokenTypes() {
    }
}
