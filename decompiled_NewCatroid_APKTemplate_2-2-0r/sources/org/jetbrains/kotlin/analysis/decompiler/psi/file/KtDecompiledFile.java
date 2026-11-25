package org.jetbrains.kotlin.analysis.decompiler.psi.file;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.analysis.decompiler.psi.KotlinDecompiledFileViewProvider;
import org.jetbrains.kotlin.analysis.decompiler.psi.text.DecompiledText;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.utils.concurrent.block.LockedClearableLazyValue;

/* JADX INFO: compiled from: KtDecompiledFile.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bJ\n\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtDecompiledFile;", "Lorg/jetbrains/kotlin/psi/KtFile;", Constants.EXTRA_PROVIDER, "Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinDecompiledFileViewProvider;", "buildDecompiledText", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/text/DecompiledText;", "(Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinDecompiledFileViewProvider;Lkotlin/jvm/functions/Function1;)V", "decompiledText", "Lorg/jetbrains/kotlin/utils/concurrent/block/LockedClearableLazyValue;", "getText", "", "onContentReload", "", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtDecompiledFile extends KtFile {
    private final LockedClearableLazyValue<DecompiledText> decompiledText;
    private final KotlinDecompiledFileViewProvider provider;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDecompiledFile(KotlinDecompiledFileViewProvider provider, final Function1<? super VirtualFile, DecompiledText> buildDecompiledText) {
        super(provider, true);
        Intrinsics.checkNotNullParameter(provider, "provider");
        Intrinsics.checkNotNullParameter(buildDecompiledText, "buildDecompiledText");
        this.provider = provider;
        this.decompiledText = new LockedClearableLazyValue<>(new Object(), new Function0<DecompiledText>() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.file.KtDecompiledFile$decompiledText$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final DecompiledText invoke() {
                Function1<VirtualFile, DecompiledText> function1 = buildDecompiledText;
                VirtualFile virtualFile = this.provider.getVirtualFile();
                Intrinsics.checkNotNullExpressionValue(virtualFile, "getVirtualFile(...)");
                return function1.invoke(virtualFile);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return this.decompiledText.get().getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileEx
    public void onContentReload() {
        super.onContentReload();
        this.provider.getContent().drop();
        this.decompiledText.drop();
    }
}
