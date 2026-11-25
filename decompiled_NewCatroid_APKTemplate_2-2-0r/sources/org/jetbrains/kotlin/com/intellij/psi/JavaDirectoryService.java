package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Map;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaDirectoryService {
    public abstract void checkCreateClass(PsiDirectory psiDirectory, String str) throws IncorrectOperationException;

    public abstract PsiClass createAnnotationType(PsiDirectory psiDirectory, String str) throws IncorrectOperationException;

    public abstract PsiClass createClass(PsiDirectory psiDirectory, String str) throws IncorrectOperationException;

    public abstract PsiClass createClass(PsiDirectory psiDirectory, String str, String str2) throws IncorrectOperationException;

    public abstract PsiClass createClass(PsiDirectory psiDirectory, String str, String str2, boolean z) throws IncorrectOperationException;

    public abstract PsiClass createClass(PsiDirectory psiDirectory, String str, String str2, boolean z, Map<String, String> map) throws IncorrectOperationException;

    public abstract PsiClass createEnum(PsiDirectory psiDirectory, String str) throws IncorrectOperationException;

    public abstract PsiClass createInterface(PsiDirectory psiDirectory, String str) throws IncorrectOperationException;

    public abstract PsiClass createRecord(PsiDirectory psiDirectory, String str) throws IncorrectOperationException;

    public abstract PsiClass[] getClasses(PsiDirectory psiDirectory);

    public abstract LanguageLevel getLanguageLevel(PsiDirectory psiDirectory);

    public abstract PsiPackage getPackage(PsiDirectory psiDirectory);

    public abstract PsiPackage getPackageInSources(PsiDirectory psiDirectory);

    public abstract boolean isSourceRoot(PsiDirectory psiDirectory);

    public static JavaDirectoryService getInstance() {
        return (JavaDirectoryService) ApplicationManager.getApplication().getService(JavaDirectoryService.class);
    }
}
