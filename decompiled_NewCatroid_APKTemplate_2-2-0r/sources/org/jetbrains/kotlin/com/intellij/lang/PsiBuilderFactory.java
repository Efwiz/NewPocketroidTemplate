package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiBuilderFactory {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "chameleon";
        } else if (i != 2) {
            objArr[0] = "project";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/PsiBuilderFactory";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/PsiBuilderFactory";
        } else {
            objArr[1] = "createBuilder";
        }
        if (i != 2) {
            objArr[2] = "createBuilder";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract PsiBuilder createBuilder(ParserDefinition parserDefinition, Lexer lexer, CharSequence charSequence);

    public abstract PsiBuilder createBuilder(Project project, ASTNode aSTNode);

    public abstract PsiBuilder createBuilder(Project project, ASTNode aSTNode, Lexer lexer, Language language, CharSequence charSequence);

    public abstract PsiBuilder createBuilder(Project project, LighterLazyParseableNode lighterLazyParseableNode, Lexer lexer, Language language, CharSequence charSequence);

    public static PsiBuilderFactory getInstance() {
        return (PsiBuilderFactory) ApplicationManager.getApplication().getService(PsiBuilderFactory.class);
    }

    public PsiBuilder createBuilder(Project project, Lexer lexer, ASTNode aSTNode) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        PsiBuilder psiBuilderCreateBuilder = createBuilder(project, aSTNode, lexer, aSTNode.getElementType().getLanguage(), aSTNode.getChars());
        if (psiBuilderCreateBuilder == null) {
            $$$reportNull$$$0(2);
        }
        return psiBuilderCreateBuilder;
    }
}
