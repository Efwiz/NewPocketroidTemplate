package org.jetbrains.kotlin.com.intellij.psi.presentation.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.icons.AllIcons;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProvider;
import org.jetbrains.kotlin.com.intellij.openapi.module.Module;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightJavaModule;

/* JADX INFO: loaded from: classes3.dex */
public class JavaModulePresentationProvider implements ItemPresentationProvider<PsiJavaModule> {
    private static final Pattern JAR_NAME = Pattern.compile(".+/([^/]+\\.jar)!/.*");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "item", "org/jetbrains/kotlin/com/intellij/psi/presentation/java/JavaModulePresentationProvider", "getPresentation"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProvider
    public ItemPresentation getPresentation(final PsiJavaModule psiJavaModule) {
        if (psiJavaModule == null) {
            $$$reportNull$$$0(0);
        }
        return new ItemPresentation() { // from class: org.jetbrains.kotlin.com.intellij.psi.presentation.java.JavaModulePresentationProvider.1
            public String getPresentableText() {
                return psiJavaModule.getName();
            }

            @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
            public String getLocationString() {
                Module moduleForFile;
                VirtualFile moduleVirtualFile = PsiImplUtil.getModuleVirtualFile(psiJavaModule);
                FileIndexFacade fileIndexFacade = FileIndexFacade.getInstance(psiJavaModule.getProject());
                if (fileIndexFacade.isInLibraryClasses(moduleVirtualFile)) {
                    Matcher matcher = JavaModulePresentationProvider.JAR_NAME.matcher(moduleVirtualFile.getPath());
                    if (matcher.find()) {
                        return matcher.group(1);
                    }
                    return null;
                }
                if (!fileIndexFacade.isInSource(moduleVirtualFile) || (moduleForFile = fileIndexFacade.getModuleForFile(moduleVirtualFile)) == null) {
                    return null;
                }
                return "[" + moduleForFile.getName() + ']';
            }

            @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
            public Icon getIcon(boolean z) {
                return psiJavaModule instanceof LightJavaModule ? AllIcons.FileTypes.Archive : AllIcons.Nodes.JavaModule;
            }
        };
    }
}
