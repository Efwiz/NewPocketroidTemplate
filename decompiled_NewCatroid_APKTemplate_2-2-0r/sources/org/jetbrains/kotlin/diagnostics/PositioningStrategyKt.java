package org.jetbrains.kotlin.diagnostics;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: PositioningStrategy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0010\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0003\u001a\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\f\u001a\u00020\r\u001a\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000f\u001a\u00020\n\u001a\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0003\u001a\u000e\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0013"}, d2 = {"getEndOffset", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getStartOffset", "hasSyntaxErrors", "", "psiElement", "markElement", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "markNode", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "markRange", "range", TypedValues.TransitionType.S_FROM, "to", "markSingleElement", "frontend.common-psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PositioningStrategyKt {
    public static final List<TextRange> markElement(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return CollectionsKt.listOf(new TextRange(getStartOffset(element), getEndOffset(element)));
    }

    public static final TextRange markSingleElement(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return new TextRange(getStartOffset(element), getEndOffset(element));
    }

    public static final List<TextRange> markNode(ASTNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        PsiElement psi = node.getPsi();
        Intrinsics.checkNotNullExpressionValue(psi, "getPsi(...)");
        return markElement(psi);
    }

    public static final List<TextRange> markRange(TextRange range) {
        Intrinsics.checkNotNullParameter(range, "range");
        return CollectionsKt.listOf(range);
    }

    public static final List<TextRange> markRange(PsiElement from, PsiElement to) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        return markRange(new TextRange(getStartOffset(from), getEndOffset(to)));
    }

    private static final int getStartOffset(PsiElement psiElement) {
        PsiElement firstChild = psiElement.getFirstChild();
        if (firstChild != null) {
            while (true) {
                if (!(firstChild instanceof PsiComment) && !(firstChild instanceof PsiWhiteSpace)) {
                    break;
                }
                firstChild = firstChild.getNextSibling();
            }
            if (firstChild != null) {
                return getStartOffset(firstChild);
            }
        }
        return PsiUtilsKt.getStartOffset(psiElement);
    }

    private static final int getEndOffset(PsiElement psiElement) {
        PsiElement lastChild = psiElement.getLastChild();
        if (lastChild != null) {
            while (true) {
                if (!(lastChild instanceof PsiComment) && !(lastChild instanceof PsiWhiteSpace)) {
                    break;
                }
                lastChild = lastChild.getPrevSibling();
            }
            if (lastChild != null) {
                return getEndOffset(lastChild);
            }
        }
        return PsiUtilsKt.getEndOffset(psiElement);
    }

    public static final boolean hasSyntaxErrors(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        if (psiElement instanceof PsiErrorElement) {
            return true;
        }
        PsiElement[] children = psiElement.getChildren();
        Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
        if (!(children.length == 0)) {
            Object objLast = ArraysKt.last(children);
            Intrinsics.checkNotNullExpressionValue(objLast, "last(...)");
            if (hasSyntaxErrors((PsiElement) objLast)) {
                return true;
            }
        }
        return false;
    }
}
