package org.jetbrains.kotlin.cfg;

import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiElementFilter;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: UnreachableCode.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\b\bf\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bR\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/UnreachableCode;", "", PathTree.NAME_elements, "", "Lorg/jetbrains/kotlin/psi/KtElement;", "getElements", "()Ljava/util/Set;", "reachableElements", "getReachableElements", "unreachableElements", "getUnreachableElements", "Companion", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface UnreachableCode {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    Set<KtElement> getElements();

    Set<KtElement> getReachableElements();

    Set<KtElement> getUnreachableElements();

    /* JADX INFO: compiled from: UnreachableCode.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\tJ.\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0004*\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u0002J\u001a\u0010\r\u001a\u00020\u000e*\u00020\u00072\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u0002J\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004*\b\u0012\u0004\u0012\u00020\f0\u0004H\u0002J&\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u0004*\b\u0012\u0004\u0012\u00020\f0\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/cfg/UnreachableCode$Companion;", "", "()V", "getUnreachableTextRanges", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "reachableElements", "", "unreachableElements", "getLeavesOrReachableChildren", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "hasChildrenInSet", "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "mergeAdjacentTextRanges", "removeReachableElementsWithMeaninglessSiblings", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final List<TextRange> getUnreachableTextRanges(KtElement element, Set<? extends KtElement> reachableElements, Set<? extends KtElement> unreachableElements) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(reachableElements, "reachableElements");
            Intrinsics.checkNotNullParameter(unreachableElements, "unreachableElements");
            if (hasChildrenInSet(element, reachableElements)) {
                List<TextRange> listMergeAdjacentTextRanges = mergeAdjacentTextRanges(removeReachableElementsWithMeaninglessSiblings(getLeavesOrReachableChildren(element, reachableElements, unreachableElements), reachableElements));
                if (!listMergeAdjacentTextRanges.isEmpty()) {
                    return listMergeAdjacentTextRanges;
                }
                int endOffset = element.getTextRange().getEndOffset();
                return CollectionsKt.listOf(new TextRange(endOffset, endOffset));
            }
            TextRange textRange = element.getTextRange();
            Intrinsics.checkNotNull(textRange);
            return CollectionsKt.listOf(textRange);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean hasChildrenInSet(final KtElement ktElement, Set<? extends KtElement> set) {
            PsiElement[] psiElementArrCollectElements = PsiTreeUtil.collectElements(ktElement, new PsiElementFilter() { // from class: org.jetbrains.kotlin.cfg.UnreachableCode$Companion$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.psi.util.PsiElementFilter
                public final boolean isAccepted(PsiElement psiElement) {
                    return UnreachableCode.Companion.hasChildrenInSet$lambda$2(ktElement, psiElement);
                }
            });
            Intrinsics.checkNotNullExpressionValue(psiElementArrCollectElements, "collectElements(...)");
            for (PsiElement psiElement : psiElementArrCollectElements) {
                if (CollectionsKt.contains(set, psiElement)) {
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean hasChildrenInSet$lambda$2(KtElement this_hasChildrenInSet, PsiElement it) {
            Intrinsics.checkNotNullParameter(this_hasChildrenInSet, "$this_hasChildrenInSet");
            Intrinsics.checkNotNullParameter(it, "it");
            return !Intrinsics.areEqual(it, this_hasChildrenInSet);
        }

        private final List<PsiElement> getLeavesOrReachableChildren(KtElement ktElement, final Set<? extends KtElement> set, final Set<? extends KtElement> set2) {
            final ArrayList arrayList = new ArrayList();
            ktElement.acceptChildren(new PsiElementVisitor() { // from class: org.jetbrains.kotlin.cfg.UnreachableCode$Companion$getLeavesOrReachableChildren$1
                @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
                public void visitElement(PsiElement element) {
                    Intrinsics.checkNotNullParameter(element, "element");
                    if (!(element instanceof KtElement) || !set.contains(element) || UnreachableCode.Companion.$$INSTANCE.hasChildrenInSet((KtElement) element, set2)) {
                        PsiElement[] children = element.getChildren();
                        Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
                        if (children.length != 0) {
                            element.acceptChildren(this);
                            return;
                        }
                    }
                    arrayList.add(element);
                }
            });
            return arrayList;
        }

        private static final boolean removeReachableElementsWithMeaninglessSiblings$isMeaningless(PsiElement psiElement) {
            if (!(psiElement instanceof PsiWhiteSpace)) {
                ASTNode node = psiElement.getNode();
                if (!Intrinsics.areEqual(node != null ? node.getElementType() : null, KtTokens.COMMA) && !(psiElement instanceof PsiComment)) {
                    return false;
                }
            }
            return true;
        }

        private final List<PsiElement> removeReachableElementsWithMeaninglessSiblings(List<? extends PsiElement> list, Set<? extends KtElement> set) {
            HashSet hashSet = new HashSet();
            int i = 0;
            for (PsiElement psiElement : list) {
                int i2 = i + 1;
                if (CollectionsKt.contains(set, psiElement)) {
                    hashSet.add(psiElement);
                    removeReachableElementsWithMeaninglessSiblings$collectSiblingsIfMeaningless(list, hashSet, i, -1);
                    removeReachableElementsWithMeaninglessSiblings$collectSiblingsIfMeaningless(list, hashSet, i, 1);
                }
                i = i2;
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (!hashSet.contains((PsiElement) obj)) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }

        private static final void removeReachableElementsWithMeaninglessSiblings$collectSiblingsIfMeaningless(List<? extends PsiElement> list, HashSet<PsiElement> hashSet, int i, int i2) {
            int i3 = i + i2;
            if (i3 < 0 || i3 >= list.size()) {
                return;
            }
            PsiElement psiElement = list.get(i3);
            if (removeReachableElementsWithMeaninglessSiblings$isMeaningless(psiElement)) {
                hashSet.add(psiElement);
                removeReachableElementsWithMeaninglessSiblings$collectSiblingsIfMeaningless(list, hashSet, i3, i2);
            }
        }

        private final List<TextRange> mergeAdjacentTextRanges(List<? extends PsiElement> list) {
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = list.iterator();
            TextRange textRangeUnion = null;
            while (it.hasNext()) {
                TextRange textRange = ((PsiElement) it.next()).getTextRange();
                Intrinsics.checkNotNull(textRange);
                if (textRangeUnion != null) {
                    if (textRangeUnion.getEndOffset() == textRange.getStartOffset()) {
                        textRangeUnion = textRangeUnion.union(textRange);
                    } else {
                        arrayList.add(textRangeUnion);
                    }
                }
                textRangeUnion = textRange;
            }
            if (textRangeUnion != null) {
                arrayList.add(textRangeUnion);
            }
            return arrayList;
        }
    }
}
