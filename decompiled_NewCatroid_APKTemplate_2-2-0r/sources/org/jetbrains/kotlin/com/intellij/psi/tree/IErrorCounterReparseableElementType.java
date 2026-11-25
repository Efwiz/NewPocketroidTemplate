package org.jetbrains.kotlin.com.intellij.psi.tree;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public abstract class IErrorCounterReparseableElementType extends IReparseableElementType {
    public static final int FATAL_ERROR = Integer.MIN_VALUE;
    public static final int NO_ERRORS = 0;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "fileLanguage";
        } else if (i != 2) {
            objArr[0] = "buffer";
        } else {
            objArr[0] = "project";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/tree/IErrorCounterReparseableElementType";
        objArr[2] = "isParsable";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract int getErrorsCount(CharSequence charSequence, Language language, Project project);

    public IErrorCounterReparseableElementType(String str, Language language) {
        super(str, language);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IReparseableElementType
    public boolean isParsable(CharSequence charSequence, Language language, Project project) {
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
        if (language == null) {
            $$$reportNull$$$0(1);
        }
        if (project == null) {
            $$$reportNull$$$0(2);
        }
        return getErrorsCount(charSequence, language, project) == 0;
    }
}
