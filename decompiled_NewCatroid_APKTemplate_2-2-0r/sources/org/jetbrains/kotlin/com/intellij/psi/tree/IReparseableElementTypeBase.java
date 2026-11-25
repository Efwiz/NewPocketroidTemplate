package org.jetbrains.kotlin.com.intellij.psi.tree;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public interface IReparseableElementTypeBase extends ILazyParseableElementTypeBase {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 5:
                objArr[0] = "fileLanguage";
                break;
            case 2:
            case 6:
                objArr[0] = "project";
                break;
            case 3:
                objArr[0] = "currentNode";
                break;
            case 4:
                objArr[0] = "newText";
                break;
            case 7:
                objArr[0] = "oldNode";
                break;
            case 8:
                objArr[0] = "newNode";
                break;
            default:
                objArr[0] = "buffer";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/tree/IReparseableElementTypeBase";
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[2] = "isReparseable";
                break;
            case 7:
            case 8:
                objArr[2] = "isValidReparse";
                break;
            default:
                objArr[2] = "isParsable";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Deprecated
    default boolean isParsable(ASTNode aSTNode, CharSequence charSequence, Language language, Project project) {
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
        if (language == null) {
            $$$reportNull$$$0(1);
        }
        if (project == null) {
            $$$reportNull$$$0(2);
        }
        return false;
    }

    default boolean isValidReparse(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(7);
        }
        if (aSTNode2 != null) {
            return true;
        }
        $$$reportNull$$$0(8);
        return true;
    }

    default boolean isReparseable(ASTNode aSTNode, CharSequence charSequence, Language language, Project project) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        if (language == null) {
            $$$reportNull$$$0(5);
        }
        if (project == null) {
            $$$reportNull$$$0(6);
        }
        return isParsable(aSTNode.getTreeParent(), charSequence, language, project);
    }
}
