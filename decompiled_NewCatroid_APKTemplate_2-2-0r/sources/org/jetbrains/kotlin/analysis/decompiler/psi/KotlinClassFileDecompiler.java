package org.jetbrains.kotlin.analysis.decompiler.psi;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.psi.file.KtClsFile;
import org.jetbrains.kotlin.analysis.decompiler.psi.file.KtDecompiledFile;
import org.jetbrains.kotlin.analysis.decompiler.stub.file.ClsClassFinder;
import org.jetbrains.kotlin.analysis.decompiler.stub.file.ClsKotlinBinaryClassCache;
import org.jetbrains.kotlin.analysis.decompiler.stub.file.KotlinClsStubBuilder;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers;

/* JADX INFO: compiled from: KotlinClassFileDecompiler.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J \u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0016J\b\u0010\u000e\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinClassFileDecompiler;", "Lorg/jetbrains/kotlin/com/intellij/psi/compiled/ClassFileDecompilers$Full;", "()V", "stubBuilder", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinClsStubBuilder;", "accepts", "", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "createFileViewProvider", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinDecompiledFileViewProvider;", "manager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "physical", "getStubBuilder", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinClassFileDecompiler extends ClassFileDecompilers.Full {
    private final KotlinClsStubBuilder stubBuilder = new KotlinClsStubBuilder();

    @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers.Decompiler
    public boolean accepts(VirtualFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return ClsKotlinBinaryClassCache.isKotlinJvmCompiledFile$default(ClsKotlinBinaryClassCache.INSTANCE.getInstance(), file, null, 2, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers.Full
    public KotlinClsStubBuilder getStubBuilder() {
        return this.stubBuilder;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers.Full
    public KotlinDecompiledFileViewProvider createFileViewProvider(VirtualFile file, PsiManager manager, boolean physical) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(manager, "manager");
        return new KotlinDecompiledFileViewProvider(manager, file, physical, new Function1<KotlinDecompiledFileViewProvider, KtDecompiledFile>() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.KotlinClassFileDecompiler.createFileViewProvider.1
            @Override // kotlin.jvm.functions.Function1
            public final KtDecompiledFile invoke(KotlinDecompiledFileViewProvider provider) {
                Intrinsics.checkNotNullParameter(provider, "provider");
                VirtualFile virtualFile = provider.getVirtualFile();
                Intrinsics.checkNotNullExpressionValue(virtualFile, "getVirtualFile(...)");
                if (!ClsClassFinder.isKotlinInternalCompiledFile$default(ClsClassFinder.INSTANCE, virtualFile, null, 2, null) || ClsClassFinder.isMultifileClassPartFile$default(ClsClassFinder.INSTANCE, virtualFile, null, 2, null)) {
                    return new KtClsFile(provider);
                }
                return null;
            }
        });
    }
}
