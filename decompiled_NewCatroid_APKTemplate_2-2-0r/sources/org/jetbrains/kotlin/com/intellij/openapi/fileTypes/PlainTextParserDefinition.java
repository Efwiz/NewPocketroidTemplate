package org.jetbrains.kotlin.com.intellij.openapi.fileTypes;

import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.lang.PsiParser;
import org.jetbrains.kotlin.com.intellij.lexer.EmptyLexer;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PlainTextTokenTypes;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiPlainTextFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;

/* JADX INFO: loaded from: classes3.dex */
public class PlainTextParserDefinition implements ParserDefinition {
    private static final IFileElementType PLAIN_FILE_ELEMENT_TYPE = new IFileElementType(PlainTextFileType.INSTANCE.getLanguage()) { // from class: org.jetbrains.kotlin.com.intellij.openapi.fileTypes.PlainTextParserDefinition.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "chameleon", "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/PlainTextParserDefinition$1", "parseContents"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
        public ASTNode parseContents(ASTNode aSTNode) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            return ASTFactory.leaf(PlainTextTokenTypes.PLAIN_TEXT, aSTNode.getChars());
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 5 ? 2 : 3];
        if (i != 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/PlainTextParserDefinition";
        } else {
            objArr[0] = "viewProvider";
        }
        switch (i) {
            case 1:
                objArr[1] = "getWhitespaceTokens";
                break;
            case 2:
                objArr[1] = "getCommentTokens";
                break;
            case 3:
                objArr[1] = "getStringLiteralElements";
                break;
            case 4:
                objArr[1] = "createElement";
                break;
            case 5:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/PlainTextParserDefinition";
                break;
            case 6:
                objArr[1] = "spaceExistenceTypeBetweenTokens";
                break;
            default:
                objArr[1] = "getFileNodeType";
                break;
        }
        if (i == 5) {
            objArr[2] = "createFile";
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public Lexer createLexer(Project project) {
        return new EmptyLexer();
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public PsiParser createParser(Project project) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public IFileElementType getFileNodeType() {
        IFileElementType iFileElementType = PLAIN_FILE_ELEMENT_TYPE;
        if (iFileElementType == null) {
            $$$reportNull$$$0(0);
        }
        return iFileElementType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public TokenSet getWhitespaceTokens() {
        TokenSet tokenSet = TokenSet.EMPTY;
        if (tokenSet == null) {
            $$$reportNull$$$0(1);
        }
        return tokenSet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public TokenSet getCommentTokens() {
        TokenSet tokenSet = TokenSet.EMPTY;
        if (tokenSet == null) {
            $$$reportNull$$$0(2);
        }
        return tokenSet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public TokenSet getStringLiteralElements() {
        TokenSet tokenSet = TokenSet.EMPTY;
        if (tokenSet == null) {
            $$$reportNull$$$0(3);
        }
        return tokenSet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public PsiElement createElement(ASTNode aSTNode) {
        PsiElement psiElement = PsiUtilCore.NULL_PSI_ELEMENT;
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        return psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public PsiFile createFile(FileViewProvider fileViewProvider) {
        if (fileViewProvider == null) {
            $$$reportNull$$$0(5);
        }
        return new PsiPlainTextFileImpl(fileViewProvider);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public ParserDefinition.SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode aSTNode, ASTNode aSTNode2) {
        ParserDefinition.SpaceRequirements spaceRequirements = ParserDefinition.SpaceRequirements.MAY;
        if (spaceRequirements == null) {
            $$$reportNull$$$0(6);
        }
        return spaceRequirements;
    }
}
