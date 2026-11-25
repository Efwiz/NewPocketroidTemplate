package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.module.Module;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaCompilerConfigurationProxy {
    private static final ExtensionPointName<JavaCompilerConfigurationProxy> EP_NAME = new ExtensionPointName<>("org.jetbrains.kotlin.com.intellij.javaCompilerConfigurationProxy");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 3) {
            objArr[0] = "module";
        } else if (i != 4) {
            objArr[0] = "project";
        } else {
            objArr[0] = "options";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/JavaCompilerConfigurationProxy";
        if (i == 2 || i == 3 || i == 4) {
            objArr[2] = "setAdditionalOptions";
        } else {
            objArr[2] = "getAdditionalOptions";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected abstract List<String> getAdditionalOptionsImpl(Project project, Module module);

    protected abstract void setAdditionalOptionsImpl(Project project, Module module, List<String> list);

    public static List<String> getAdditionalOptions(Project project, Module module) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (module == null) {
            $$$reportNull$$$0(1);
        }
        JavaCompilerConfigurationProxy[] extensions = EP_NAME.getExtensions();
        return extensions.length == 0 ? Collections.emptyList() : extensions[0].getAdditionalOptionsImpl(project, module);
    }

    public static void setAdditionalOptions(Project project, Module module, List<String> list) {
        if (project == null) {
            $$$reportNull$$$0(2);
        }
        if (module == null) {
            $$$reportNull$$$0(3);
        }
        if (list == null) {
            $$$reportNull$$$0(4);
        }
        JavaCompilerConfigurationProxy[] extensions = EP_NAME.getExtensions();
        if (extensions.length == 0) {
            return;
        }
        extensions[0].setAdditionalOptionsImpl(project, module, list);
    }
}
