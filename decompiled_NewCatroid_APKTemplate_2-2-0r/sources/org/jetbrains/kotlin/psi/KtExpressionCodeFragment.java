package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;

/* JADX INFO: compiled from: KtExpressionCodeFragment.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\u000f\u0010\f\u001a\t\u0018\u00010\r¢\u0006\u0002\b\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtExpressionCodeFragment;", "Lorg/jetbrains/kotlin/psi/KtCodeFragment;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "name", "", "text", "", "imports", "context", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lcom/intellij/openapi/project/Project;Ljava/lang/String;Ljava/lang/CharSequence;Ljava/lang/String;Lcom/intellij/psi/PsiElement;)V", "getContentElement", "Lorg/jetbrains/kotlin/psi/KtExpression;", "Lorg/jetbrains/annotations/Nullable;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtExpressionCodeFragment extends KtCodeFragment {
    /* JADX WARN: Illegal instructions before constructor call */
    public KtExpressionCodeFragment(Project project, String name2, CharSequence text, String str, PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(text, "text");
        IFileElementType EXPRESSION_CODE_FRAGMENT = KtNodeTypes.EXPRESSION_CODE_FRAGMENT;
        Intrinsics.checkNotNullExpressionValue(EXPRESSION_CODE_FRAGMENT, "EXPRESSION_CODE_FRAGMENT");
        super(project, name2, text, str, EXPRESSION_CODE_FRAGMENT, psiElement);
    }

    @Override // org.jetbrains.kotlin.psi.KtCodeFragment
    public KtExpression getContentElement() {
        return (KtExpression) findChildByClass(KtExpression.class);
    }
}
