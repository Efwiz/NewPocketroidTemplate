package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers;
import org.jetbrains.kotlin.com.intellij.psi.stubs.BinaryFileStubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.Stub;
import org.jetbrains.kotlin.com.intellij.util.cls.ClsFormatException;
import org.jetbrains.kotlin.com.intellij.util.indexing.FileContent;

/* JADX INFO: loaded from: classes3.dex */
public class ClassFileStubBuilder implements BinaryFileStubBuilder.CompositeBinaryFileStubBuilder<ClassFileDecompilers.Full> {
    private static final Logger LOG = Logger.getInstance((Class<?>) ClassFileStubBuilder.class);
    public static final int STUB_VERSION = 26;

    /* JADX WARN: Code duplicated, block: B:18:0x0026  */
    /* JADX WARN: Code duplicated, block: B:19:0x002b  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClassFileStubBuilder";
        } else if (i == 2) {
            objArr[0] = "fileContent";
        } else if (i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClassFileStubBuilder";
        } else if (i != 4) {
            objArr[0] = "file";
        } else {
            objArr[0] = "fileContent";
        }
        if (i == 1) {
            objArr[1] = "getAllSubBuilders";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClassFileStubBuilder";
        } else {
            objArr[1] = "getSubBuilderVersion";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "getSubBuilder";
            } else if (i != 3) {
                if (i != 4) {
                    objArr[2] = "acceptsFile";
                } else {
                    objArr[2] = "buildStubTree";
                }
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.BinaryFileStubBuilder
    public boolean acceptsFile(VirtualFile virtualFile) {
        if (virtualFile != null) {
            return true;
        }
        $$$reportNull$$$0(0);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.BinaryFileStubBuilder
    public int getStubVersion() {
        return 26;
    }

    static /* synthetic */ boolean lambda$getAllSubBuilders$0(ClassFileDecompilers.Decompiler decompiler) {
        return decompiler instanceof ClassFileDecompilers.Full;
    }

    static /* synthetic */ ClassFileDecompilers.Full lambda$getAllSubBuilders$1(ClassFileDecompilers.Decompiler decompiler) {
        return (ClassFileDecompilers.Full) decompiler;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.BinaryFileStubBuilder.CompositeBinaryFileStubBuilder
    public Stream<ClassFileDecompilers.Full> getAllSubBuilders() {
        Stream map = ClassFileDecompilers.getInstance().EP_NAME.getExtensionList().stream().filter(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClassFileStubBuilder$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ClassFileStubBuilder.lambda$getAllSubBuilders$0((ClassFileDecompilers.Decompiler) obj);
            }
        }).map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClassFileStubBuilder$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ClassFileStubBuilder.lambda$getAllSubBuilders$1((ClassFileDecompilers.Decompiler) obj);
            }
        });
        if (map == null) {
            $$$reportNull$$$0(1);
        }
        return map;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.BinaryFileStubBuilder.CompositeBinaryFileStubBuilder
    public ClassFileDecompilers.Full getSubBuilder(final FileContent fileContent) {
        if (fileContent == null) {
            $$$reportNull$$$0(2);
        }
        return (ClassFileDecompilers.Full) fileContent.getFile().computeWithPreloadedContentHint(fileContent.getContent(), new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClassFileStubBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return ClassFileStubBuilder.lambda$getSubBuilder$2(fileContent);
            }
        });
    }

    static /* synthetic */ ClassFileDecompilers.Full lambda$getSubBuilder$2(FileContent fileContent) {
        return (ClassFileDecompilers.Full) ClassFileDecompilers.getInstance().find(fileContent.getFile(), ClassFileDecompilers.Full.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.BinaryFileStubBuilder.CompositeBinaryFileStubBuilder
    public String getSubBuilderVersion(ClassFileDecompilers.Full full) {
        if (full == null) {
            return "default";
        }
        String str = full.getClass().getName() + ":" + full.getStubBuilder().getStubVersion();
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.BinaryFileStubBuilder.CompositeBinaryFileStubBuilder
    public Stub buildStubTree(final FileContent fileContent, final ClassFileDecompilers.Full full) {
        if (fileContent == null) {
            $$$reportNull$$$0(4);
        }
        if (full == null) {
            return null;
        }
        return (Stub) fileContent.getFile().computeWithPreloadedContentHint(fileContent.getContent(), new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClassFileStubBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return ClassFileStubBuilder.lambda$buildStubTree$3(fileContent, full);
            }
        });
    }

    static /* synthetic */ PsiFileStub lambda$buildStubTree$3(FileContent fileContent, ClassFileDecompilers.Full full) {
        VirtualFile file = fileContent.getFile();
        try {
            return full.getStubBuilder().buildFileStub(fileContent);
        } catch (ClsFormatException e) {
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug(file.getPath(), e);
                return null;
            }
            logger.info(file.getPath() + ": " + e.getMessage());
            return null;
        }
    }
}
