package org.jetbrains.kotlin.com.intellij.psi.compiled;

import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.components.Service;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.BinaryFileTypeDecompilers;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;

/* JADX INFO: loaded from: classes3.dex */
@Service
public final class ClassFileDecompilers {
    public final ExtensionPointName<Decompiler> EP_NAME;

    public interface Decompiler {
        boolean accepts(VirtualFile virtualFile);
    }

    public static abstract class Full implements Decompiler {
        public abstract FileViewProvider createFileViewProvider(VirtualFile virtualFile, PsiManager psiManager, boolean z);

        public abstract ClsStubBuilder getStubBuilder();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "file";
        } else {
            objArr[0] = "decompilerClass";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/compiled/ClassFileDecompilers";
        objArr[2] = "find";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static abstract class Light implements Decompiler {
        public abstract CharSequence getText(VirtualFile virtualFile) throws CannotDecompileException;

        public static class CannotDecompileException extends RuntimeException {
            public CannotDecompileException(String str, Throwable th) {
                super(str, th);
            }
        }
    }

    public static ClassFileDecompilers getInstance() {
        return (ClassFileDecompilers) ApplicationManager.getApplication().getService(ClassFileDecompilers.class);
    }

    private ClassFileDecompilers() {
        ExtensionPointName<Decompiler> extensionPointName = new ExtensionPointName<>("org.jetbrains.kotlin.com.intellij.psi.classFileDecompiler");
        this.EP_NAME = extensionPointName;
        extensionPointName.addChangeListener(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BinaryFileTypeDecompilers.getInstance().m6486xaf102b9c();
            }
        }, null);
    }

    static /* synthetic */ boolean lambda$find$1(Class cls, VirtualFile virtualFile, Decompiler decompiler) {
        return cls.isInstance(decompiler) && decompiler.accepts(virtualFile);
    }

    public <D extends Decompiler> D find(final VirtualFile virtualFile, final Class<D> cls) {
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
        return (D) this.EP_NAME.findFirstSafe(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ClassFileDecompilers.lambda$find$1(cls, virtualFile, (ClassFileDecompilers.Decompiler) obj);
            }
        });
    }
}
