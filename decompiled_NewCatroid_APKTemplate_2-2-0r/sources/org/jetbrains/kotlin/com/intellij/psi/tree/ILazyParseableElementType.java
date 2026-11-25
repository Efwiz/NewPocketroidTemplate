package org.jetbrains.kotlin.com.intellij.psi.tree;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public class ILazyParseableElementType extends IElementType implements ILazyParseableElementTypeBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Key<Language> LANGUAGE_KEY = Key.create("LANGUAGE_KEY");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 7 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 7 ? 3 : 2];
        if (i == 3 || i == 4) {
            objArr[0] = "chameleon";
        } else if (i == 5 || i == 6) {
            objArr[0] = "psi";
        } else if (i != 7) {
            objArr[0] = "debugName";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/tree/ILazyParseableElementType";
        }
        if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/tree/ILazyParseableElementType";
        } else {
            objArr[1] = "getLanguageForParser";
        }
        if (i == 3) {
            objArr[2] = "parseContents";
        } else if (i == 4 || i == 5) {
            objArr[2] = "doParseContents";
        } else if (i == 6) {
            objArr[2] = "getLanguageForParser";
        } else if (i != 7) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 7) {
            throw new IllegalStateException(str2);
        }
    }

    public ASTNode createNode(CharSequence charSequence) {
        return null;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ILazyParseableElementType(String str) {
        this(str, null);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ILazyParseableElementType(String str, Language language) {
        super(str, language);
        if (str == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ILazyParseableElementType(String str, Language language, boolean z) {
        super(str, language, z);
        if (str == null) {
            $$$reportNull$$$0(2);
        }
    }

    public ASTNode parseContents(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        return doParseContents(aSTNode, aSTNode.getTreeParent().getPsi());
    }

    protected ASTNode doParseContents(ASTNode aSTNode, PsiElement psiElement) {
        if (aSTNode == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        Project project = psiElement.getProject();
        Language languageForParser = getLanguageForParser(psiElement);
        return LanguageParserDefinitions.INSTANCE.forLanguage(languageForParser).createParser(project).parse(this, PsiBuilderFactory.getInstance().createBuilder(project, aSTNode, (Lexer) null, languageForParser, aSTNode.getChars())).getFirstChildNode();
    }

    protected Language getLanguageForParser(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        Language language = getLanguage();
        if (language == null) {
            $$$reportNull$$$0(7);
        }
        return language;
    }
}
