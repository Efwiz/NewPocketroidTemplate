package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiParserFacade {
    PsiComment createBlockCommentFromText(Language language, String str) throws IncorrectOperationException;

    PsiComment createLineCommentFromText(Language language, String str) throws IncorrectOperationException;

    PsiComment createLineCommentFromText(LanguageFileType languageFileType, String str) throws IncorrectOperationException;

    PsiComment createLineOrBlockCommentFromText(Language language, String str) throws IncorrectOperationException;

    PsiElement createWhiteSpaceFromText(String str) throws IncorrectOperationException;

    public static final class SERVICE {
        private SERVICE() {
        }

        public static PsiParserFacade getInstance(Project project) {
            return (PsiParserFacade) project.getService(PsiParserFacade.class);
        }
    }
}
