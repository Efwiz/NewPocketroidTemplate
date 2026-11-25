package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public interface ParserDefinition {

    public enum SpaceRequirements {
        MAY,
        MUST,
        MUST_NOT,
        MUST_LINE_BREAK
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 3 : 2];
        if (i == 3) {
            objArr[0] = "originalSpaceNode";
        } else if (i != 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/ParserDefinition";
        } else {
            objArr[0] = "newWhiteSpaceSequence";
        }
        if (i == 1) {
            objArr[1] = "spaceExistenceTypeBetweenTokens";
        } else if (i == 2) {
            objArr[1] = "spaceExistanceTypeBetweenTokens";
        } else if (i == 3 || i == 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/ParserDefinition";
        } else {
            objArr[1] = "getWhitespaceTokens";
        }
        if (i == 3 || i == 4) {
            objArr[2] = "reparseSpace";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    PsiElement createElement(ASTNode aSTNode);

    PsiFile createFile(FileViewProvider fileViewProvider);

    Lexer createLexer(Project project);

    PsiParser createParser(Project project);

    TokenSet getCommentTokens();

    IFileElementType getFileNodeType();

    TokenSet getStringLiteralElements();

    default ASTNode reparseSpace(ASTNode aSTNode, CharSequence charSequence) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        if (charSequence != null) {
            return null;
        }
        $$$reportNull$$$0(4);
        return null;
    }

    default TokenSet getWhitespaceTokens() {
        TokenSet tokenSet = TokenSet.WHITE_SPACE;
        if (tokenSet == null) {
            $$$reportNull$$$0(0);
        }
        return tokenSet;
    }

    default SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode aSTNode, ASTNode aSTNode2) {
        SpaceRequirements spaceRequirementsSpaceExistanceTypeBetweenTokens = spaceExistanceTypeBetweenTokens(aSTNode, aSTNode2);
        if (spaceRequirementsSpaceExistanceTypeBetweenTokens == null) {
            $$$reportNull$$$0(1);
        }
        return spaceRequirementsSpaceExistanceTypeBetweenTokens;
    }

    @Deprecated
    default SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode aSTNode, ASTNode aSTNode2) {
        SpaceRequirements spaceRequirements = SpaceRequirements.MAY;
        if (spaceRequirements == null) {
            $$$reportNull$$$0(2);
        }
        return spaceRequirements;
    }
}
