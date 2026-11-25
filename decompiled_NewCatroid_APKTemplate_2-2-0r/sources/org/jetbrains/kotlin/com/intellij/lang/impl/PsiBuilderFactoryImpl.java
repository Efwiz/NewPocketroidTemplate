package org.jetbrains.kotlin.com.intellij.lang.impl;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.LighterLazyParseableNode;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiBuilderFactoryImpl extends PsiBuilderFactory {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 17) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 17) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
            case 6:
            case 10:
                objArr[0] = "chameleon";
                break;
            case 2:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderFactoryImpl";
                break;
            case 3:
            case 5:
            case 9:
            default:
                objArr[0] = "project";
                break;
            case 7:
            case 11:
                objArr[0] = "lang";
                break;
            case 8:
            case 12:
            case 15:
                objArr[0] = "seq";
                break;
            case 13:
                objArr[0] = "parserDefinition";
                break;
            case 14:
                objArr[0] = "lexer";
                break;
            case 16:
                objArr[0] = "tokenType";
                break;
        }
        if (i == 2) {
            objArr[1] = "createBuilder";
        } else if (i != 17) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/impl/PsiBuilderFactoryImpl";
        } else {
            objArr[1] = "getParserDefinition";
        }
        if (i != 2) {
            if (i == 16) {
                objArr[2] = "getParserDefinition";
            } else if (i != 17) {
                objArr[2] = "createBuilder";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 17) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory
    public PsiBuilder createBuilder(Project project, ASTNode aSTNode) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        PsiBuilder psiBuilderCreateBuilder = createBuilder(project, (Lexer) null, aSTNode);
        if (psiBuilderCreateBuilder == null) {
            $$$reportNull$$$0(2);
        }
        return psiBuilderCreateBuilder;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory
    public PsiBuilder createBuilder(Project project, ASTNode aSTNode, Lexer lexer, Language language, CharSequence charSequence) {
        if (project == null) {
            $$$reportNull$$$0(5);
        }
        if (aSTNode == null) {
            $$$reportNull$$$0(6);
        }
        if (language == null) {
            $$$reportNull$$$0(7);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(8);
        }
        ParserDefinition parserDefinition = getParserDefinition(language, aSTNode.getElementType());
        if (lexer == null) {
            lexer = parserDefinition.createLexer(project);
        }
        return new PsiBuilderImpl(project, parserDefinition, lexer, aSTNode, charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory
    public PsiBuilder createBuilder(Project project, LighterLazyParseableNode lighterLazyParseableNode, Lexer lexer, Language language, CharSequence charSequence) {
        if (project == null) {
            $$$reportNull$$$0(9);
        }
        if (lighterLazyParseableNode == null) {
            $$$reportNull$$$0(10);
        }
        if (language == null) {
            $$$reportNull$$$0(11);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(12);
        }
        ParserDefinition parserDefinition = getParserDefinition(null, lighterLazyParseableNode.getTokenType());
        if (lexer == null) {
            lexer = parserDefinition.createLexer(project);
        }
        return new PsiBuilderImpl(project, parserDefinition, lexer, lighterLazyParseableNode, charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory
    public PsiBuilder createBuilder(ParserDefinition parserDefinition, Lexer lexer, CharSequence charSequence) {
        if (parserDefinition == null) {
            $$$reportNull$$$0(13);
        }
        if (lexer == null) {
            $$$reportNull$$$0(14);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(15);
        }
        return new PsiBuilderImpl(null, null, parserDefinition, lexer, null, charSequence, null, null);
    }

    private static ParserDefinition getParserDefinition(Language language, IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(16);
        }
        if (language == null) {
            language = iElementType.getLanguage();
        }
        ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(language);
        if (parserDefinitionForLanguage == null) {
            throw new AssertionError("ParserDefinition absent for language: '" + language.getID() + "' (" + language.getClass().getName() + "), for elementType: '" + iElementType.getDebugName() + "' (" + iElementType.getClass().getName() + Constant.AFTER_QUTO);
        }
        if (parserDefinitionForLanguage == null) {
            $$$reportNull$$$0(17);
        }
        return parserDefinitionForLanguage;
    }
}
