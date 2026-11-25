package org.jetbrains.kotlin.fir.analysis;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.psi.psiUtil.PsiChildRange;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: PsiElementFinderByType.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B+\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\fJ\u001a\u0010\u000e\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0006H\u0002R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/PsiElementFinderByType;", "", SpecTypePair.NAME_types, "", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "index", "", ConfigConstants.CONFIG_KEY_DEPTH, "reverse", "", "(Ljava/util/Collection;IIZ)V", "find", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "root", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "currentDepth", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiElementFinderByType {
    private final int depth;
    private int index;
    private final boolean reverse;
    private final Collection<IElementType> types;

    /* JADX WARN: Multi-variable type inference failed */
    public PsiElementFinderByType(Collection<? extends IElementType> types, int i, int i2, boolean z) {
        Intrinsics.checkNotNullParameter(types, "types");
        this.types = types;
        this.index = i;
        this.depth = i2;
        this.reverse = z;
    }

    public final PsiElement find(PsiElement root) {
        Intrinsics.checkNotNullParameter(root, "root");
        return visitElement(root, 0);
    }

    private final PsiElement visitElement(PsiElement element, int currentDepth) {
        if (currentDepth != 0 && this.types.contains(element.getNode().getElementType())) {
            int i = this.index;
            if (i == 0) {
                return element;
            }
            this.index = i - 1;
        }
        if (currentDepth == this.depth) {
            return null;
        }
        boolean z = this.reverse;
        PsiChildRange allChildren = PsiUtilsKt.getAllChildren(element);
        Iterator<PsiElement> it = z ? CollectionsKt.asReversed(SequencesKt.toList(allChildren)).iterator() : allChildren.iterator();
        while (it.hasNext()) {
            PsiElement psiElementVisitElement = visitElement(it.next(), currentDepth + 1);
            if (psiElementVisitElement != null) {
                return psiElementVisitElement;
            }
        }
        return null;
    }
}
