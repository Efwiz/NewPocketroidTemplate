package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface JavaCodeFragment extends PsiCodeFragment, PsiImportHolder {

    public interface ExceptionHandler {
        boolean isHandledException(PsiClassType psiClassType);
    }

    public interface VisibilityChecker {
        public static final VisibilityChecker EVERYTHING_VISIBLE = new VisibilityChecker() { // from class: org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment.VisibilityChecker.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment.VisibilityChecker
            public Visibility isDeclarationVisible(PsiElement psiElement, PsiElement psiElement2) {
                return Visibility.VISIBLE;
            }

            public String toString() {
                return "EVERYTHING_VISIBLE";
            }
        };
        public static final VisibilityChecker PROJECT_SCOPE_VISIBLE = new VisibilityChecker() { // from class: org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment.VisibilityChecker.2
            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaCodeFragment.VisibilityChecker
            public Visibility isDeclarationVisible(PsiElement psiElement, PsiElement psiElement2) {
                return psiElement.getManager().isInProject(psiElement) ? Visibility.VISIBLE : Visibility.NOT_VISIBLE;
            }

            public String toString() {
                return "PROJECT_SCOPE_VISIBLE";
            }
        };

        public enum Visibility {
            VISIBLE,
            NOT_VISIBLE,
            DEFAULT_VISIBILITY
        }

        Visibility isDeclarationVisible(PsiElement psiElement, PsiElement psiElement2);
    }

    void addImportsFromString(String str);

    ExceptionHandler getExceptionHandler();

    PsiType getSuperType();

    PsiType getThisType();

    VisibilityChecker getVisibilityChecker();

    String importsToString();

    void setExceptionHandler(ExceptionHandler exceptionHandler);

    void setSuperType(PsiType psiType);

    void setThisType(PsiType psiType);

    void setVisibilityChecker(VisibilityChecker visibilityChecker);
}
