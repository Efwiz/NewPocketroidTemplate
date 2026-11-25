package org.jetbrains.kotlin.com.intellij.psi.tree;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public class IReparseableElementType extends ILazyParseableElementType implements IReparseableElementTypeBase {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 2:
            case 4:
                objArr[0] = "language";
                break;
            case 3:
            default:
                objArr[0] = "debugName";
                break;
            case 5:
            case 8:
                objArr[0] = "buffer";
                break;
            case 6:
            case 9:
                objArr[0] = "fileLanguage";
                break;
            case 7:
            case 10:
                objArr[0] = "project";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/tree/IReparseableElementType";
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = "isParsable";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Deprecated
    public boolean isParsable(CharSequence charSequence, Language language, Project project) {
        if (charSequence == null) {
            $$$reportNull$$$0(5);
        }
        if (language == null) {
            $$$reportNull$$$0(6);
        }
        if (project != null) {
            return false;
        }
        $$$reportNull$$$0(7);
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IReparseableElementType(String str) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IReparseableElementType(String str, Language language) {
        super(str, language);
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (language == null) {
            $$$reportNull$$$0(2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IReparseableElementType(String str, Language language, boolean z) {
        super(str, language, z);
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (language == null) {
            $$$reportNull$$$0(4);
        }
    }

    public boolean isParsable(ASTNode aSTNode, CharSequence charSequence, Language language, Project project) {
        if (charSequence == null) {
            $$$reportNull$$$0(8);
        }
        if (language == null) {
            $$$reportNull$$$0(9);
        }
        if (project == null) {
            $$$reportNull$$$0(10);
        }
        return isParsable(charSequence, language, project);
    }
}
