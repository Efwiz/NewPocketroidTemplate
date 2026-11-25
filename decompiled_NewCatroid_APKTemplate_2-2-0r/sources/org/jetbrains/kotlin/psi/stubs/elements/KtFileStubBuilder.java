package org.jetbrains.kotlin.psi.stubs.elements;

import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.stubs.DefaultStubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.stubs.KotlinFileStub;

/* JADX INFO: loaded from: classes6.dex */
public class KtFileStubBuilder extends DefaultStubBuilder {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/psi/stubs/elements/KtFileStubBuilder";
        } else {
            objArr[0] = "file";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "createStubForFile";
        } else {
            objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtFileStubBuilder";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "createStubForFile";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.DefaultStubBuilder
    protected StubElement createStubForFile(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        if (!(psiFile instanceof KtFile)) {
            StubElement stubElementCreateStubForFile = super.createStubForFile(psiFile);
            if (stubElementCreateStubForFile == null) {
                $$$reportNull$$$0(1);
            }
            return stubElementCreateStubForFile;
        }
        KotlinFileStub kotlinFileStubCreateFileStub = StubIndexService.getInstance().createFileStub((KtFile) psiFile);
        if (kotlinFileStubCreateFileStub == null) {
            $$$reportNull$$$0(2);
        }
        return kotlinFileStubCreateFileStub;
    }
}
