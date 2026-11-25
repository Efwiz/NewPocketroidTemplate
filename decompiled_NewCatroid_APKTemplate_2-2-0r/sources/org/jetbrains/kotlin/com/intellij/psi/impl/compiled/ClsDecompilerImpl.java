package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.ClassFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers;
import org.jetbrains.kotlin.com.intellij.psi.compiled.ClsStubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.util.cls.ClsFormatException;
import org.jetbrains.kotlin.com.intellij.util.indexing.FileContent;

/* JADX INFO: loaded from: classes3.dex */
public class ClsDecompilerImpl extends ClassFileDecompilers.Full {
    private static final Logger LOG = Logger.getInstance((Class<?>) ClsDecompilerImpl.class);
    private final ClsStubBuilder myStubBuilder = new MyClsStubBuilder();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsDecompilerImpl";
        } else if (i != 3) {
            objArr[0] = "file";
        } else {
            objArr[0] = "manager";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsDecompilerImpl";
        } else {
            objArr[1] = "getStubBuilder";
        }
        if (i != 1) {
            if (i == 2 || i == 3) {
                objArr[2] = "createFileViewProvider";
            } else {
                objArr[2] = "accepts";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers.Decompiler
    public boolean accepts(VirtualFile virtualFile) {
        if (virtualFile != null) {
            return true;
        }
        $$$reportNull$$$0(0);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers.Full
    /* JADX INFO: renamed from: getStubBuilder */
    public ClsStubBuilder getMetadataStubBuilder() {
        ClsStubBuilder clsStubBuilder = this.myStubBuilder;
        if (clsStubBuilder == null) {
            $$$reportNull$$$0(1);
        }
        return clsStubBuilder;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers.Full
    public FileViewProvider createFileViewProvider(VirtualFile virtualFile, PsiManager psiManager, boolean z) {
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(3);
        }
        return new ClassFileViewProvider(psiManager, virtualFile, z);
    }

    private static class MyClsStubBuilder extends ClsStubBuilder {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "fileContent", "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsDecompilerImpl$MyClsStubBuilder", "buildFileStub"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClsStubBuilder
        public int getStubVersion() {
            return 53;
        }

        private MyClsStubBuilder() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClsStubBuilder
        public PsiFileStub<?> buildFileStub(FileContent fileContent) throws ClsFormatException {
            if (fileContent == null) {
                $$$reportNull$$$0(0);
            }
            PsiJavaFileStub psiJavaFileStubBuildFileStub = ClsFileImpl.buildFileStub(fileContent.getFile(), fileContent.getContent());
            if (psiJavaFileStubBuildFileStub == null && fileContent.getFileName().indexOf(36) < 0) {
                ClsDecompilerImpl.LOG.info("No stub built for the file " + fileContent);
            }
            return psiJavaFileStubBuildFileStub;
        }
    }
}
