package org.jetbrains.kotlin.com.intellij.psi;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.ClassVisitor;

/* JADX INFO: loaded from: classes3.dex */
public class ClassFileViewProvider extends SingleRootFileViewProvider {
    private static final Key<Boolean> IS_INNER_CLASS = Key.create("java.is.inner.class.key");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
                objArr[0] = "file";
                break;
            case 2:
            default:
                objArr[0] = "manager";
                break;
            case 4:
                objArr[0] = "project";
                break;
            case 6:
                objArr[0] = "fileType";
                break;
            case 9:
                objArr[0] = FirebaseAnalytics.Param.CONTENT;
                break;
            case 10:
                objArr[0] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
                break;
            case 11:
            case 12:
                objArr[0] = "language";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/ClassFileViewProvider";
        switch (i) {
            case 4:
            case 5:
            case 6:
                objArr[2] = "createFile";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "isInnerClass";
                break;
            case 10:
                objArr[2] = "createCopy";
                break;
            case 11:
                objArr[2] = "findElementAt";
                break;
            case 12:
                objArr[2] = "findReferenceAt";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ClassFileViewProvider(PsiManager psiManager, VirtualFile virtualFile) {
        this(psiManager, virtualFile, true);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassFileViewProvider(PsiManager psiManager, VirtualFile virtualFile, boolean z) {
        super(psiManager, virtualFile, z, JavaLanguage.INSTANCE);
        if (psiManager == null) {
            $$$reportNull$$$0(2);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(3);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider
    protected PsiFile createFile(Project project, VirtualFile virtualFile, FileType fileType) {
        if (project == null) {
            $$$reportNull$$$0(4);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(5);
        }
        if (fileType == null) {
            $$$reportNull$$$0(6);
        }
        FileIndexFacade fileIndexFacade = (FileIndexFacade) project.getService(FileIndexFacade.class);
        if (!fileIndexFacade.isInLibraryClasses(virtualFile) && fileIndexFacade.isInSource(virtualFile)) {
            return new PsiBinaryFileImpl((PsiManagerImpl) getManager(), this);
        }
        try {
            if (isInnerClass(virtualFile)) {
                return null;
            }
            return new ClsFileImpl(this);
        } catch (Exception e) {
            Logger.getInstance((Class<?>) ClassFileViewProvider.class).debug(virtualFile.getPath(), e);
            return null;
        }
    }

    public static boolean isInnerClass(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(7);
        }
        return detectInnerClass(virtualFile, null);
    }

    public static boolean isInnerClass(VirtualFile virtualFile, byte[] bArr) {
        if (virtualFile == null) {
            $$$reportNull$$$0(8);
        }
        if (bArr == null) {
            $$$reportNull$$$0(9);
        }
        return detectInnerClass(virtualFile, bArr);
    }

    private static boolean detectInnerClass(VirtualFile virtualFile, byte[] bArr) {
        String nameWithoutExtension = virtualFile.getNameWithoutExtension();
        if (nameWithoutExtension.lastIndexOf(36, nameWithoutExtension.length() - 2) <= 0) {
            return false;
        }
        Key<Boolean> key = IS_INNER_CLASS;
        Boolean bool = key.get(virtualFile);
        if (bool != null) {
            return bool.booleanValue();
        }
        if (bArr == null) {
            try {
                bArr = virtualFile.contentsToByteArray(false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        ClassReader classReader = new ClassReader(bArr);
        final Ref refCreate = Ref.create(Boolean.FALSE);
        final String className = classReader.getClassName();
        classReader.accept(new ClassVisitor(589824) { // from class: org.jetbrains.kotlin.com.intellij.psi.ClassFileViewProvider.1
            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visitOuterClass(String str, String str2, String str3) {
                refCreate.set(Boolean.TRUE);
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visitInnerClass(String str, String str2, String str3, int i) {
                if (className.equals(str)) {
                    refCreate.set(Boolean.TRUE);
                }
            }
        }, ClsFileImpl.EMPTY_ATTRIBUTES, 7);
        Boolean bool2 = (Boolean) refCreate.get();
        key.set(virtualFile, bool2);
        return bool2.booleanValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SingleRootFileViewProvider, org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public SingleRootFileViewProvider createCopy(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(10);
        }
        return new ClassFileViewProvider(getManager(), virtualFile, false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SingleRootFileViewProvider, org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public PsiElement findElementAt(int i) {
        return findElementAt(i, getBaseLanguage());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider, org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public PsiElement findElementAt(int i, Language language) {
        if (language == null) {
            $$$reportNull$$$0(11);
        }
        PsiFile psi = getPsi(language);
        if (psi instanceof PsiCompiledFile) {
            psi = ((PsiCompiledFile) psi).getDecompiledPsiFile();
        }
        return findElementAt(psi, i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SingleRootFileViewProvider, org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public PsiReference findReferenceAt(int i) {
        return findReferenceAt(i, getBaseLanguage());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider, org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public PsiReference findReferenceAt(int i, Language language) {
        if (language == null) {
            $$$reportNull$$$0(12);
        }
        PsiFile psi = getPsi(language);
        if (psi instanceof PsiCompiledFile) {
            psi = ((PsiCompiledFile) psi).getDecompiledPsiFile();
        }
        return findReferenceAt(psi, i);
    }
}
