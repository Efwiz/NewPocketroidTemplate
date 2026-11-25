package org.jetbrains.kotlin.psi.psiUtil;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: PsiChildRange.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00182\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0018B\u0019\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0005J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0002HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0002HÆ\u0003J!\u0010\u000e\u001a\u00020\u00002\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0002HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\t2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0015H\u0096\u0002J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/psi/psiUtil/PsiChildRange;", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "first", "last", "(Lcom/intellij/psi/PsiElement;Lcom/intellij/psi/PsiElement;)V", "getFirst", "()Lcom/intellij/psi/PsiElement;", "isEmpty", "", "()Z", "getLast", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "iterator", "", "toString", "", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class PsiChildRange implements Sequence<PsiElement> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final PsiChildRange EMPTY = new PsiChildRange(null, null);
    private final PsiElement first;
    private final PsiElement last;

    public static /* synthetic */ PsiChildRange copy$default(PsiChildRange psiChildRange, PsiElement psiElement, PsiElement psiElement2, int i, Object obj) {
        if ((i & 1) != 0) {
            psiElement = psiChildRange.first;
        }
        if ((i & 2) != 0) {
            psiElement2 = psiChildRange.last;
        }
        return psiChildRange.copy(psiElement, psiElement2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final PsiElement getFirst() {
        return this.first;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final PsiElement getLast() {
        return this.last;
    }

    public final PsiChildRange copy(PsiElement first, PsiElement last) {
        return new PsiChildRange(first, last);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PsiChildRange)) {
            return false;
        }
        PsiChildRange psiChildRange = (PsiChildRange) other;
        return Intrinsics.areEqual(this.first, psiChildRange.first) && Intrinsics.areEqual(this.last, psiChildRange.last);
    }

    public int hashCode() {
        PsiElement psiElement = this.first;
        int iHashCode = (psiElement == null ? 0 : psiElement.hashCode()) * 31;
        PsiElement psiElement2 = this.last;
        return iHashCode + (psiElement2 != null ? psiElement2.hashCode() : 0);
    }

    public String toString() {
        return "PsiChildRange(first=" + this.first + ", last=" + this.last + ')';
    }

    public PsiChildRange(PsiElement psiElement, PsiElement psiElement2) {
        this.first = psiElement;
        this.last = psiElement2;
        if (psiElement == null) {
            return;
        }
        PsiElement parent = psiElement.getParent();
        Intrinsics.checkNotNull(psiElement2);
        Intrinsics.areEqual(parent, psiElement2.getParent());
    }

    public final PsiElement getFirst() {
        return this.first;
    }

    public final PsiElement getLast() {
        return this.last;
    }

    public final boolean isEmpty() {
        return this.first == null;
    }

    @Override // kotlin.sequences.Sequence
    public Iterator<PsiElement> iterator() {
        Sequence sequenceTakeWhile;
        if (this.first == null) {
            sequenceTakeWhile = SequencesKt.emptySequence();
        } else {
            PsiElement psiElement = this.last;
            Intrinsics.checkNotNull(psiElement);
            final PsiElement nextSibling = psiElement.getNextSibling();
            sequenceTakeWhile = SequencesKt.takeWhile(PsiUtilsKt.siblings$default(this.first, false, false, 3, null), new Function1<PsiElement, Boolean>() { // from class: org.jetbrains.kotlin.psi.psiUtil.PsiChildRange$iterator$sequence$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(PsiElement it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(!Intrinsics.areEqual(it, nextSibling));
                }
            });
        }
        return sequenceTakeWhile.iterator();
    }

    /* JADX INFO: compiled from: PsiChildRange.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/psi/psiUtil/PsiChildRange$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/psi/psiUtil/PsiChildRange;", "getEMPTY", "()Lorg/jetbrains/kotlin/psi/psiUtil/PsiChildRange;", "singleElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final PsiChildRange getEMPTY() {
            return PsiChildRange.EMPTY;
        }

        public final PsiChildRange singleElement(PsiElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            return new PsiChildRange(element, element);
        }
    }
}
