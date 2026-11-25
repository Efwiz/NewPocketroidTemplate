package org.jetbrains.kotlin.com.intellij.model;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public class ModelBranchUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 7 || i == 8 || i == 9) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 7 || i == 8 || i == 9) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/model/ModelBranchUtil";
                break;
            case 5:
            default:
                objArr[0] = "context";
                break;
            case 6:
                objArr[0] = "file";
                break;
            case 10:
                objArr[0] = "left";
                break;
            case 11:
                objArr[0] = "right";
                break;
        }
        if (i == 2 || i == 3 || i == 4 || i == 7 || i == 8 || i == 9) {
            objArr[1] = "obtainCopyFromTheSameBranch";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/model/ModelBranchUtil";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
                break;
            case 5:
            case 6:
            default:
                objArr[2] = "obtainCopyFromTheSameBranch";
                break;
            case 10:
            case 11:
                objArr[2] = "checkSameBranch";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 7 && i != 8 && i != 9) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static void checkSameBranch(VirtualFile virtualFile, VirtualFile virtualFile2) {
        if (virtualFile == null) {
            $$$reportNull$$$0(10);
        }
        if (virtualFile2 == null) {
            $$$reportNull$$$0(11);
        }
        ModelBranch fileBranch = ModelBranch.getFileBranch(virtualFile);
        ModelBranch fileBranch2 = ModelBranch.getFileBranch(virtualFile2);
        if (fileBranch != fileBranch2) {
            throw new IllegalArgumentException("Files from different branches: " + fileBranch + " vs " + fileBranch2);
        }
    }
}
