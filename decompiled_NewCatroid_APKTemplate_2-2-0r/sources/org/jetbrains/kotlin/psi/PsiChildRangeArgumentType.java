package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.kotlin.psi.psiUtil.PsiChildRange;

/* JADX INFO: compiled from: createByPattern.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\bÂ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/psi/PsiChildRangeArgumentType;", "Lorg/jetbrains/kotlin/psi/PsiElementPlaceholderArgumentType;", "Lorg/jetbrains/kotlin/psi/psiUtil/PsiChildRange;", "Lorg/jetbrains/kotlin/psi/KtElement;", "()V", "replacePlaceholderElement", "placeholder", "argument", "reformat", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PsiChildRangeArgumentType extends PsiElementPlaceholderArgumentType<PsiChildRange, KtElement> {
    public static final PsiChildRangeArgumentType INSTANCE = new PsiChildRangeArgumentType();

    private PsiChildRangeArgumentType() {
        super(PsiChildRange.class, KtElement.class);
    }

    @Override // org.jetbrains.kotlin.psi.PsiElementPlaceholderArgumentType
    public PsiChildRange replacePlaceholderElement(KtElement placeholder, PsiChildRange argument, boolean reformat) {
        Intrinsics.checkNotNullParameter(placeholder, "placeholder");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Project project = placeholder.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        if (!argument.isEmpty()) {
            PsiElement parent = placeholder.getLightParent();
            PsiElement first = argument.getFirst();
            Intrinsics.checkNotNull(first);
            PsiElement last = argument.getLast();
            Intrinsics.checkNotNull(last);
            PsiElement psiElementAddRangeBefore = parent.addRangeBefore(first, last, placeholder);
            PsiElement prevSibling = placeholder.getPrevSibling();
            placeholder.delete();
            if (reformat) {
                CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(project);
                codeStyleManager.reformatNewlyAddedElement(psiElementAddRangeBefore.getNode().getTreeParent(), psiElementAddRangeBefore.getNode());
                if (!Intrinsics.areEqual(prevSibling, psiElementAddRangeBefore)) {
                    codeStyleManager.reformatNewlyAddedElement(prevSibling.getNode().getTreeParent(), prevSibling.getNode());
                }
            }
            return new PsiChildRange(psiElementAddRangeBefore, prevSibling);
        }
        placeholder.delete();
        return PsiChildRange.INSTANCE.getEMPTY();
    }
}
