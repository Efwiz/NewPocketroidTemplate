package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IReparseableLeafElementType;

/* JADX INFO: loaded from: classes3.dex */
class WhiteSpaceTokenType extends IElementType implements IReparseableLeafElementType<ASTNode> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "leaf";
        } else {
            objArr[0] = "newText";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/WhiteSpaceTokenType";
        objArr[2] = "reparseLeaf";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    WhiteSpaceTokenType() {
        super("WHITE_SPACE", Language.ANY);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IReparseableLeafElementType
    public ASTNode reparseLeaf(ASTNode aSTNode, CharSequence charSequence) {
        ParserDefinition parserDefinitionForLanguage;
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        Language language = aSTNode.getPsi().getLanguage();
        if (language == Language.ANY || (parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(language)) == null) {
            return null;
        }
        for (int i = 0; i < charSequence.length(); i++) {
            if (!Character.isWhitespace(charSequence.charAt(i))) {
                return null;
            }
        }
        return parserDefinitionForLanguage.reparseSpace(aSTNode, charSequence);
    }
}
