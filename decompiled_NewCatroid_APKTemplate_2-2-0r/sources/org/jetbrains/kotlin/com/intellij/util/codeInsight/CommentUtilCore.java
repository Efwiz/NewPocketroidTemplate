package org.jetbrains.kotlin.com.intellij.util.codeInsight;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class CommentUtilCore {
    public static boolean isComment(PsiElement psiElement) {
        return psiElement != null && isComment(psiElement.getNode());
    }

    public static boolean isComment(ASTNode aSTNode) {
        if (aSTNode == null) {
            return false;
        }
        IElementType elementType = aSTNode.getElementType();
        ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(elementType.getLanguage());
        return parserDefinitionForLanguage != null && parserDefinitionForLanguage.getCommentTokens().contains(elementType);
    }
}
