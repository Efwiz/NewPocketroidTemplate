package org.jetbrains.kotlin.psi.psiUtil;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: psiUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\t\u0010\u0003\u001a\u00020\u0004H\u0096\u0002J\t\u0010\u0005\u001a\u00020\u0002H\u0096\u0002¨\u0006\u0006"}, d2 = {"org/jetbrains/kotlin/psi/psiUtil/PsiUtilsKt$siblings$1$iterator$1", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "hasNext", "", ES6Iterator.NEXT_METHOD, "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiUtilsKt$siblings$1$iterator$1 implements Iterator<PsiElement>, KMappedMarker {
    final /* synthetic */ boolean $forward;
    final /* synthetic */ Ref.ObjectRef<PsiElement> $next;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    PsiUtilsKt$siblings$1$iterator$1(boolean z, Ref.ObjectRef<PsiElement> objectRef, boolean z2) {
        this.$next = objectRef;
        this.$forward = z2;
        if (z) {
            return;
        }
        next();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.$next.element != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Iterator
    public PsiElement next() {
        PsiElement psiElement = this.$next.element;
        if (psiElement == null) {
            throw new NoSuchElementException();
        }
        this.$next.element = this.$forward ? psiElement.getNextSibling() : psiElement.getPrevSibling();
        return psiElement;
    }
}
