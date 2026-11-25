package org.jetbrains.kotlin.psi.findDocComment;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.kdoc.psi.api.KDoc;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationModifierList;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: FindDocComment.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"findDocComment", "Lorg/jetbrains/kotlin/kdoc/psi/api/KDoc;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FindDocCommentKt {
    public static final KDoc findDocComment(KtDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        PsiFile containingFile = declaration.getContainingFile();
        if ((containingFile instanceof KtFile) && ((KtFile) containingFile).getIsCompiled()) {
            return null;
        }
        Object objFirstOrNull = SequencesKt.firstOrNull(SequencesKt.dropWhile(SequencesKt.flatMap(PsiUtilsKt.getAllChildren(declaration), new Function1<PsiElement, Sequence<? extends PsiElement>>() { // from class: org.jetbrains.kotlin.psi.findDocComment.FindDocCommentKt.findDocComment.1
            @Override // kotlin.jvm.functions.Function1
            public final Sequence<PsiElement> invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (!(it instanceof KtDeclarationModifierList)) {
                    return SequencesKt.sequenceOf(it);
                }
                PsiElement[] children = ((KtDeclarationModifierList) it).getChildren();
                Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
                return ArraysKt.asSequence(children);
            }
        }), new Function1<PsiElement, Boolean>() { // from class: org.jetbrains.kotlin.psi.findDocComment.FindDocCommentKt.findDocComment.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(PsiElement psiElement) {
                return Boolean.valueOf(!(psiElement instanceof KDoc));
            }
        }));
        if (objFirstOrNull instanceof KDoc) {
            return (KDoc) objFirstOrNull;
        }
        return null;
    }
}
