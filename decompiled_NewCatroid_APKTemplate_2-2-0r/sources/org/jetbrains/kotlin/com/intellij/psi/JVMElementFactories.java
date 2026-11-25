package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageExtension;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public final class JVMElementFactories extends LanguageExtension<JVMElementFactoryProvider> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final JVMElementFactories INSTANCE = new JVMElementFactories();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 1 || i == 3) {
            objArr[0] = "project";
        } else if (i != 4) {
            objArr[0] = "language";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/JVMElementFactories";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/JVMElementFactories";
        } else {
            objArr[1] = "requireFactory";
        }
        if (i == 2 || i == 3) {
            objArr[2] = "requireFactory";
        } else if (i != 4) {
            objArr[2] = "getFactory";
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    private JVMElementFactories() {
        super("org.jetbrains.kotlin.com.intellij.generation.topLevelFactory");
    }

    public static JVMElementFactory getFactory(Language language, Project project) {
        if (language == null) {
            $$$reportNull$$$0(0);
        }
        if (project == null) {
            $$$reportNull$$$0(1);
        }
        JVMElementFactoryProvider jVMElementFactoryProviderForLanguage = INSTANCE.forLanguage(language);
        if (jVMElementFactoryProviderForLanguage != null) {
            return jVMElementFactoryProviderForLanguage.getFactory(project);
        }
        return null;
    }

    public static JVMElementFactory requireFactory(Language language, Project project) {
        if (language == null) {
            $$$reportNull$$$0(2);
        }
        if (project == null) {
            $$$reportNull$$$0(3);
        }
        JVMElementFactory factory = getFactory(language, project);
        if (factory == null) {
            $$$reportNull$$$0(4);
        }
        return factory;
    }
}
