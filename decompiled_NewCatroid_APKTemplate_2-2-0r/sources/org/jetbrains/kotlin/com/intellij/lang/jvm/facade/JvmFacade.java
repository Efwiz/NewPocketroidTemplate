package org.jetbrains.kotlin.com.intellij.lang.jvm.facade;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public interface JvmFacade {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/jvm/facade/JvmFacade";
        } else if (i == 2) {
            objArr[0] = "qualifiedName";
        } else if (i != 3) {
            objArr[0] = "project";
        } else {
            objArr[0] = "scope";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/jvm/facade/JvmFacade";
        } else {
            objArr[1] = "getInstance";
        }
        if (i != 1) {
            if (i == 2 || i == 3) {
                objArr[2] = "findClass";
            } else {
                objArr[2] = "getInstance";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    static JvmFacade getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        JvmFacade jvmFacade = (JvmFacade) project.getService(JvmFacade.class);
        if (jvmFacade == null) {
            $$$reportNull$$$0(1);
        }
        return jvmFacade;
    }
}
