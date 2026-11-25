package org.jetbrains.kotlin.com.intellij.lang.java;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LanguageUtil;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.lang.PsiParser;
import org.jetbrains.kotlin.com.intellij.lang.java.lexer.JavaDocLexer;
import org.jetbrains.kotlin.com.intellij.lang.java.lexer.JavaLexer;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.LanguageLevelProjectExtension;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.JavaDocTokenType;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public class JavaParserDefinition implements ParserDefinition {
    public static final IStubFileElementType JAVA_FILE = new JavaFileElementType();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 6:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                i2 = 2;
                break;
            case 6:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/java/JavaParserDefinition";
                break;
            case 6:
                objArr[0] = "viewProvider";
                break;
            default:
                objArr[0] = FirebaseAnalytics.Param.LEVEL;
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "getFileNodeType";
                break;
            case 3:
                objArr[1] = "getCommentTokens";
                break;
            case 4:
                objArr[1] = "getStringLiteralElements";
                break;
            case 5:
                objArr[1] = "createElement";
                break;
            case 6:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/java/JavaParserDefinition";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[1] = "spaceExistenceTypeBetweenTokens";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "createDocLexer";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                break;
            case 6:
                objArr[2] = "createFile";
                break;
            default:
                objArr[2] = "createLexer";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                throw new IllegalStateException(str2);
            case 6:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public Lexer createLexer(Project project) {
        return createLexer(project != null ? LanguageLevelProjectExtension.getInstance(project).getLanguageLevel() : LanguageLevel.HIGHEST);
    }

    public static Lexer createLexer(LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(0);
        }
        return new JavaLexer(languageLevel);
    }

    public static Lexer createDocLexer(LanguageLevel languageLevel) {
        if (languageLevel == null) {
            $$$reportNull$$$0(1);
        }
        return new JavaDocLexer(languageLevel);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public IFileElementType getFileNodeType() {
        IStubFileElementType iStubFileElementType = JAVA_FILE;
        if (iStubFileElementType == null) {
            $$$reportNull$$$0(2);
        }
        return iStubFileElementType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public TokenSet getCommentTokens() {
        TokenSet tokenSet = ElementType.JAVA_COMMENT_BIT_SET;
        if (tokenSet == null) {
            $$$reportNull$$$0(3);
        }
        return tokenSet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public TokenSet getStringLiteralElements() {
        TokenSet tokenSetCreate = TokenSet.create(JavaElementType.LITERAL_EXPRESSION);
        if (tokenSetCreate == null) {
            $$$reportNull$$$0(4);
        }
        return tokenSetCreate;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public PsiParser createParser(Project project) {
        throw new UnsupportedOperationException("Should not be called directly");
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public PsiElement createElement(ASTNode aSTNode) {
        IElementType elementType = aSTNode.getElementType();
        if (elementType instanceof JavaStubElementType) {
            PsiElement psiElementCreatePsi = ((JavaStubElementType) elementType).createPsi(aSTNode);
            if (psiElementCreatePsi == null) {
                $$$reportNull$$$0(5);
            }
            return psiElementCreatePsi;
        }
        throw new IllegalArgumentException("Not a Java node: " + aSTNode + " (" + elementType + ", " + elementType.getLanguage() + Constant.AFTER_QUTO);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public PsiFile createFile(FileViewProvider fileViewProvider) {
        if (fileViewProvider == null) {
            $$$reportNull$$$0(6);
        }
        return new PsiJavaFileImpl(fileViewProvider);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ParserDefinition
    public ParserDefinition.SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode2.getElementType() == JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN || aSTNode.getElementType() == JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN) {
            ParserDefinition.SpaceRequirements spaceRequirements = ParserDefinition.SpaceRequirements.MUST_NOT;
            if (spaceRequirements == null) {
                $$$reportNull$$$0(7);
            }
            return spaceRequirements;
        }
        PsiFile containingFile = aSTNode.getTreeParent().getPsi().getContainingFile();
        ParserDefinition.SpaceRequirements spaceRequirementsCanStickTokensTogetherByLexer = LanguageUtil.canStickTokensTogetherByLexer(aSTNode, aSTNode2, createLexer(containingFile instanceof PsiJavaFile ? ((PsiJavaFile) containingFile).getLanguageLevel() : LanguageLevel.HIGHEST));
        if (aSTNode.getElementType() == JavaTokenType.END_OF_LINE_COMMENT) {
            ParserDefinition.SpaceRequirements spaceRequirements2 = ParserDefinition.SpaceRequirements.MUST_LINE_BREAK;
            if (spaceRequirements2 == null) {
                $$$reportNull$$$0(8);
            }
            return spaceRequirements2;
        }
        if (aSTNode.getElementType() == JavaDocTokenType.DOC_COMMENT_DATA) {
            String text = aSTNode.getText();
            if (text.length() > 0 && Character.isWhitespace(text.charAt(text.length() - 1))) {
                ParserDefinition.SpaceRequirements spaceRequirements3 = ParserDefinition.SpaceRequirements.MAY;
                if (spaceRequirements3 == null) {
                    $$$reportNull$$$0(9);
                }
                return spaceRequirements3;
            }
        }
        if (aSTNode2.getElementType() == JavaDocTokenType.DOC_COMMENT_DATA) {
            String text2 = aSTNode2.getText();
            if (text2.length() > 0 && Character.isWhitespace(text2.charAt(0))) {
                ParserDefinition.SpaceRequirements spaceRequirements4 = ParserDefinition.SpaceRequirements.MAY;
                if (spaceRequirements4 == null) {
                    $$$reportNull$$$0(10);
                }
                return spaceRequirements4;
            }
        } else if (aSTNode2.getElementType() == JavaDocTokenType.DOC_INLINE_TAG_END) {
            ParserDefinition.SpaceRequirements spaceRequirements5 = ParserDefinition.SpaceRequirements.MAY;
            if (spaceRequirements5 == null) {
                $$$reportNull$$$0(11);
            }
            return spaceRequirements5;
        }
        if (spaceRequirementsCanStickTokensTogetherByLexer == null) {
            $$$reportNull$$$0(12);
        }
        return spaceRequirementsCanStickTokensTogetherByLexer;
    }
}
