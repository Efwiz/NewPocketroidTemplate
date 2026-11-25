package org.jetbrains.kotlin.analysis.decompiler.psi;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: KotlinDecompiledFileViewProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00070\u0001¢\u0006\u0002\b\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/NlsSafe;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class KotlinDecompiledFileViewProvider$content$1 extends Lambda implements Function0<String> {
    final /* synthetic */ VirtualFile $file;
    final /* synthetic */ PsiManager $manager;
    final /* synthetic */ KotlinDecompiledFileViewProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    KotlinDecompiledFileViewProvider$content$1(KotlinDecompiledFileViewProvider kotlinDecompiledFileViewProvider, PsiManager psiManager, VirtualFile virtualFile) {
        super(0);
        this.this$0 = kotlinDecompiledFileViewProvider;
        this.$manager = psiManager;
        this.$file = virtualFile;
    }

    @Override // kotlin.jvm.functions.Function0
    public final String invoke() throws Throwable {
        KotlinDecompiledFileViewProvider kotlinDecompiledFileViewProvider = this.this$0;
        Project project = this.$manager.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        VirtualFile virtualFile = this.$file;
        KotlinFileType INSTANCE = KotlinFileType.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE, "INSTANCE");
        final PsiFile psiFileCreateFile = kotlinDecompiledFileViewProvider.createFile(project, virtualFile, INSTANCE);
        String text = psiFileCreateFile != null ? psiFileCreateFile.getText() : null;
        if (text == null) {
            text = "";
        }
        DebugUtil.performPsiModification("Invalidating throw-away copy of file that was used for getting text", new ThrowableRunnable() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.KotlinDecompiledFileViewProvider$content$1$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
            public final void run() {
                KotlinDecompiledFileViewProvider$content$1.invoke$lambda$0(psiFileCreateFile);
            }
        });
        return text;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(PsiFile psiFile) {
        PsiFileImpl psiFileImpl = psiFile instanceof PsiFileImpl ? (PsiFileImpl) psiFile : null;
        if (psiFileImpl != null) {
            psiFileImpl.markInvalidated();
        }
    }
}
