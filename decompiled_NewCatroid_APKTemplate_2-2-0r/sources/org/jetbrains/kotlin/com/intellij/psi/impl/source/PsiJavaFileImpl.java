package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaFileType;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaParserDefinition;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJavaFileImpl extends PsiJavaFileBaseImpl {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaFileImpl", "getFileType"));
    }

    public PsiJavaFileImpl(FileViewProvider fileViewProvider) {
        super(JavaParserDefinition.JAVA_FILE, JavaParserDefinition.JAVA_FILE, fileViewProvider);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public FileType getFileType() {
        JavaFileType javaFileType = JavaFileType.INSTANCE;
        if (javaFileType == null) {
            $$$reportNull$$$0(0);
        }
        return javaFileType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaFileBaseImpl, org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public PsiJavaModule getModuleDeclaration() {
        PsiJavaFileStub psiJavaFileStub = (PsiJavaFileStub) getGreenStub();
        return psiJavaFileStub != null ? psiJavaFileStub.getModule() : (PsiJavaModule) PsiTreeUtil.getChildOfType(this, PsiJavaModule.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiJavaFile:" + getMyName();
    }
}
