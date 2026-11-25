package org.jetbrains.kotlin.idea;

import javax.swing.Icon;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;

/* JADX INFO: loaded from: classes4.dex */
public abstract class KotlinIconProviderService {

    public static class CompilerKotlinFileIconProviderService extends KotlinIconProviderService {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/idea/KotlinIconProviderService$CompilerKotlinFileIconProviderService", "getLightVariableIcon"));
        }

        @Override // org.jetbrains.kotlin.idea.KotlinIconProviderService
        public Icon getBuiltInFileIcon() {
            return null;
        }

        @Override // org.jetbrains.kotlin.idea.KotlinIconProviderService
        public Icon getFileIcon() {
            return null;
        }

        @Override // org.jetbrains.kotlin.idea.KotlinIconProviderService
        public Icon getLightVariableIcon(PsiModifierListOwner psiModifierListOwner, int i) {
            if (psiModifierListOwner != null) {
                return null;
            }
            $$$reportNull$$$0(0);
            return null;
        }
    }

    public abstract Icon getBuiltInFileIcon();

    public abstract Icon getFileIcon();

    public abstract Icon getLightVariableIcon(PsiModifierListOwner psiModifierListOwner, int i);

    public static KotlinIconProviderService getInstance() {
        KotlinIconProviderService kotlinIconProviderService = (KotlinIconProviderService) ApplicationManager.getApplication().getService(KotlinIconProviderService.class);
        return kotlinIconProviderService != null ? kotlinIconProviderService : new CompilerKotlinFileIconProviderService();
    }
}
