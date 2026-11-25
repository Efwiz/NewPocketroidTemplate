package org.jetbrains.kotlin.fir.session;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.fir.FirSessionComponent;

/* JADX INFO: compiled from: SourcesToPathsMapper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u0006R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/SourcesToPathsMapper;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "sourcesToPath", "", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "", "getSourceFilePath", "sourceElement", "Lorg/jetbrains/kotlin/KtSourceElement;", "registerFileSource", "", "path", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourcesToPathsMapper implements FirSessionComponent {
    private final Map<LighterASTNode, String> sourcesToPath = new LinkedHashMap();

    public final void registerFileSource(KtSourceElement sourceElement, String path) {
        Intrinsics.checkNotNullParameter(sourceElement, "sourceElement");
        Intrinsics.checkNotNullParameter(path, "path");
        if (sourceElement instanceof KtPsiSourceElement) {
            return;
        }
        Map<LighterASTNode, String> map = this.sourcesToPath;
        LighterASTNode root = sourceElement.getTreeStructure().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        map.put(root, path);
    }

    public final String getSourceFilePath(KtSourceElement sourceElement) {
        VirtualFile virtualFile;
        Intrinsics.checkNotNullParameter(sourceElement, "sourceElement");
        PsiElement psi = KtSourceElementKt.getPsi(sourceElement);
        if (psi == null) {
            return this.sourcesToPath.get(sourceElement.getTreeStructure().getRoot());
        }
        PsiFile containingFile = psi.getContainingFile();
        if (containingFile == null || (virtualFile = containingFile.getVirtualFile()) == null) {
            return null;
        }
        return virtualFile.getPath();
    }
}
