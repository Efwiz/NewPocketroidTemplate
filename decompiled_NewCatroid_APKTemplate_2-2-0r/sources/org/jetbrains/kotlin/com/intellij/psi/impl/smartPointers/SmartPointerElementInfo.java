package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
abstract class SmartPointerElementInfo {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "manager", "org/jetbrains/kotlin/com/intellij/psi/impl/smartPointers/SmartPointerElementInfo", "fastenBelt"));
    }

    void cleanup() {
    }

    abstract int elementHashCode();

    void fastenBelt(SmartPointerManagerImpl smartPointerManagerImpl) {
        if (smartPointerManagerImpl == null) {
            $$$reportNull$$$0(0);
        }
    }

    Document getDocumentToSynchronize() {
        return null;
    }

    abstract Segment getPsiRange(SmartPointerManagerImpl smartPointerManagerImpl);

    abstract Segment getRange(SmartPointerManagerImpl smartPointerManagerImpl);

    abstract VirtualFile getVirtualFile();

    abstract boolean pointsToTheSameElementAs(SmartPointerElementInfo smartPointerElementInfo, SmartPointerManagerImpl smartPointerManagerImpl);

    abstract PsiElement restoreElement(SmartPointerManagerImpl smartPointerManagerImpl);

    abstract PsiFile restoreFile(SmartPointerManagerImpl smartPointerManagerImpl);

    SmartPointerElementInfo() {
    }
}
