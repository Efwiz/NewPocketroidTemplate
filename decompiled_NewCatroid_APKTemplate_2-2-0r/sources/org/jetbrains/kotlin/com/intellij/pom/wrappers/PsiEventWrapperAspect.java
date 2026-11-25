package org.jetbrains.kotlin.com.intellij.pom.wrappers;

import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.pom.PomModelAspect;
import org.jetbrains.kotlin.com.intellij.pom.core.impl.PomModelImpl;
import org.jetbrains.kotlin.com.intellij.pom.event.PomModelEvent;
import org.jetbrains.kotlin.com.intellij.pom.tree.TreeAspect;
import org.jetbrains.kotlin.com.intellij.pom.tree.events.impl.TreeChangeEventImpl;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiEventWrapperAspect implements PomModelAspect {
    private final TreeAspect myTreeAspect;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "treeAspect";
        } else {
            objArr[0] = "event";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/pom/wrappers/PsiEventWrapperAspect";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = ConfigConstants.CONFIG_KEY_UPDATE;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public void update(PomModelEvent pomModelEvent) {
        if (pomModelEvent == null) {
            $$$reportNull$$$0(1);
        }
        TreeChangeEventImpl treeChangeEventImpl = (TreeChangeEventImpl) pomModelEvent.getChangeSet(this.myTreeAspect);
        if (treeChangeEventImpl == null) {
            return;
        }
        PsiFile psiFile = (PsiFile) treeChangeEventImpl.getRootElement().getPsi();
        ((PsiDocumentManagerBase) PsiDocumentManager.getInstance(psiFile.getProject())).getSynchronizer().processEvents(treeChangeEventImpl, psiFile);
        if (PomModelImpl.shouldFirePhysicalPsiEvents(psiFile)) {
            treeChangeEventImpl.fireEvents();
        } else {
            ((PsiManagerImpl) psiFile.getManager()).afterChange(false);
        }
    }
}
