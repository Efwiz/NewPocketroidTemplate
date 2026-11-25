package org.jetbrains.kotlin.com.intellij.extapi.psi;

import java.util.ArrayList;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiFileBase extends PsiFileImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Language myLanguage;
    private final ParserDefinition myParserDefinition;

    /* JADX WARN: Code duplicated, block: B:20:0x002f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 6) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "language";
        } else if (i == 2) {
            objArr[0] = "baseLanguage";
        } else if (i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/extapi/psi/PsiFileBase";
        } else if (i == 5) {
            objArr[0] = "visitor";
        } else if (i != 6) {
            objArr[0] = "viewProvider";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/extapi/psi/PsiFileBase";
        }
        if (i == 4) {
            objArr[1] = "getLanguage";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/extapi/psi/PsiFileBase";
        } else {
            objArr[1] = "getParserDefinition";
        }
        if (i == 2 || i == 3) {
            objArr[2] = "findLanguage";
        } else if (i != 4) {
            if (i == 5) {
                objArr[2] = "accept";
            } else if (i != 6) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected PsiFileBase(FileViewProvider fileViewProvider, Language language) {
        super(fileViewProvider);
        if (fileViewProvider == null) {
            $$$reportNull$$$0(0);
        }
        if (language == null) {
            $$$reportNull$$$0(1);
        }
        Language languageFindLanguage = findLanguage(language, fileViewProvider);
        this.myLanguage = languageFindLanguage;
        ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(languageFindLanguage);
        if (parserDefinitionForLanguage == null) {
            throw new RuntimeException("PsiFileBase: language.getParserDefinition() returned null for: " + languageFindLanguage);
        }
        this.myParserDefinition = parserDefinitionForLanguage;
        IFileElementType fileNodeType = parserDefinitionForLanguage.getFileNodeType();
        init(fileNodeType, fileNodeType);
    }

    private static Language findLanguage(Language language, FileViewProvider fileViewProvider) {
        if (language == null) {
            $$$reportNull$$$0(2);
        }
        if (fileViewProvider == null) {
            $$$reportNull$$$0(3);
        }
        Set<Language> languages = fileViewProvider.getLanguages();
        Language language2 = null;
        for (Language language3 : languages) {
            if (language3.equals(language)) {
                return language;
            }
            if (language2 == null && language3.isKindOf(language)) {
                language2 = language3;
            }
        }
        if (language2 != null) {
            return language2;
        }
        throw new AssertionError("Language " + language + " doesn't participate in view provider " + fileViewProvider + ": " + new ArrayList(languages));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final Language getLanguage() {
        Language language = this.myLanguage;
        if (language == null) {
            $$$reportNull$$$0(4);
        }
        return language;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(5);
        }
        psiElementVisitor.visitFile(this);
    }

    public ParserDefinition getParserDefinition() {
        ParserDefinition parserDefinition = this.myParserDefinition;
        if (parserDefinition == null) {
            $$$reportNull$$$0(6);
        }
        return parserDefinition;
    }
}
