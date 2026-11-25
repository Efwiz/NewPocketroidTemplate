package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.BinaryFileDecompiler;
import org.jetbrains.kotlin.com.intellij.openapi.project.DefaultProjectFactory;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers;

/* JADX INFO: loaded from: classes3.dex */
public class ClassFileDecompiler implements BinaryFileDecompiler {
    private static final Logger LOG = Logger.getInstance((Class<?>) ClassFileDecompiler.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClassFileDecompiler";
        } else {
            objArr[0] = "file";
        }
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            objArr[1] = "decompile";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClassFileDecompiler";
        }
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            objArr[2] = "decompile";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.BinaryFileDecompiler
    public CharSequence decompile(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        ClassFileDecompilers.Decompiler decompilerFind = ClassFileDecompilers.getInstance().find(virtualFile, ClassFileDecompilers.Decompiler.class);
        if (decompilerFind instanceof ClsDecompilerImpl) {
            CharSequence charSequenceDecompile = ClsFileImpl.decompile(virtualFile);
            if (charSequenceDecompile == null) {
                $$$reportNull$$$0(1);
            }
            return charSequenceDecompile;
        }
        if (decompilerFind instanceof ClassFileDecompilers.Full) {
            CharSequence contents = ((ClassFileDecompilers.Full) decompilerFind).createFileViewProvider(virtualFile, PsiManager.getInstance(DefaultProjectFactory.getInstance().getDefaultProject()), true).getContents();
            if (contents == null) {
                $$$reportNull$$$0(2);
            }
            return contents;
        }
        if (decompilerFind instanceof ClassFileDecompilers.Light) {
            try {
                CharSequence text = ((ClassFileDecompilers.Light) decompilerFind).getText(virtualFile);
                if (text == null) {
                    $$$reportNull$$$0(3);
                }
                return text;
            } catch (ClassFileDecompilers.Light.CannotDecompileException e) {
                LOG.warn("decompiler: " + decompilerFind.getClass(), e);
                CharSequence charSequenceDecompile2 = ClsFileImpl.decompile(virtualFile);
                if (charSequenceDecompile2 == null) {
                    $$$reportNull$$$0(4);
                }
                return charSequenceDecompile2;
            }
        }
        throw new IllegalStateException(decompilerFind.getClass().getName() + " should be on of " + ClassFileDecompilers.Full.class.getName() + " or " + ClassFileDecompilers.Light.class.getName());
    }
}
