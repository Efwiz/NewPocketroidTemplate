package org.jetbrains.kotlin.analysis.decompiler.psi;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.analysis.decompiler.psi.file.KtDecompiledFile;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.SingleRootFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.utils.concurrent.block.LockedClearableLazyValue;

/* JADX INFO: compiled from: KotlinDecompiledFileViewProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0014\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0000\u0012\u0006\u0012\u0004\u0018\u00010\n0\t¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0005H\u0016J\"\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u000eH\u0016R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0000\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinDecompiledFileViewProvider;", "Lorg/jetbrains/kotlin/com/intellij/psi/SingleRootFileViewProvider;", "manager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "physical", "", "factory", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtDecompiledFile;", "(Lcom/intellij/psi/PsiManager;Lcom/intellij/openapi/vfs/VirtualFile;ZLkotlin/jvm/functions/Function1;)V", FirebaseAnalytics.Param.CONTENT, "Lorg/jetbrains/kotlin/utils/concurrent/block/LockedClearableLazyValue;", "", "getContent", "()Lorg/jetbrains/kotlin/utils/concurrent/block/LockedClearableLazyValue;", "createCopy", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "createFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "fileType", "Lorg/jetbrains/kotlin/com/intellij/openapi/fileTypes/FileType;", "getContents", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinDecompiledFileViewProvider extends SingleRootFileViewProvider {
    private final LockedClearableLazyValue<String> content;
    private final Function1<KotlinDecompiledFileViewProvider, KtDecompiledFile> factory;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public KotlinDecompiledFileViewProvider(PsiManager manager, VirtualFile file, boolean z, Function1<? super KotlinDecompiledFileViewProvider, ? extends KtDecompiledFile> factory) {
        super(manager, file, z, KotlinLanguage.INSTANCE);
        Intrinsics.checkNotNullParameter(manager, "manager");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.factory = factory;
        this.content = new LockedClearableLazyValue<>(new Object(), new KotlinDecompiledFileViewProvider$content$1(this, manager, file));
    }

    public final LockedClearableLazyValue<String> getContent() {
        return this.content;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider
    protected PsiFile createFile(Project project, VirtualFile file, FileType fileType) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        return this.factory.invoke(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.SingleRootFileViewProvider, org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public KotlinDecompiledFileViewProvider createCopy(VirtualFile copy) {
        Intrinsics.checkNotNullParameter(copy, "copy");
        PsiManagerEx manager = getManager();
        Intrinsics.checkNotNullExpressionValue(manager, "getManager(...)");
        return new KotlinDecompiledFileViewProvider(manager, copy, false, this.factory);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider, org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public String getContents() {
        return this.content.get();
    }
}
