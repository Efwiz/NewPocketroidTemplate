package org.jetbrains.kotlin.com.intellij.pom;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public final class PomManager {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "project";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/pom/PomManager";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/pom/PomManager";
        } else {
            objArr[1] = "getModel";
        }
        if (i != 1) {
            objArr[2] = "getModel";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public static PomModel getModel(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        PomModel pomModel = (PomModel) project.getService(PomModel.class);
        if (pomModel == null) {
            $$$reportNull$$$0(1);
        }
        return pomModel;
    }
}
