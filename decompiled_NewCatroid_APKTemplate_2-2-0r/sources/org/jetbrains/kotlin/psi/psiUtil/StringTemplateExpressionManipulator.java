package org.jetbrains.kotlin.psi.psiUtil;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.AbstractElementManipulator;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;

/* JADX INFO: compiled from: StringTemplateExpressionManipulator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0002H\u0016J\"\u0010\u0007\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/psi/psiUtil/StringTemplateExpressionManipulator;", "Lorg/jetbrains/kotlin/com/intellij/psi/AbstractElementManipulator;", "Lorg/jetbrains/kotlin/psi/KtStringTemplateExpression;", "()V", "getRangeInElement", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "handleContentChange", "range", "newContent", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StringTemplateExpressionManipulator extends AbstractElementManipulator<KtStringTemplateExpression> {
    @Override // org.jetbrains.kotlin.com.intellij.psi.ElementManipulator
    public KtStringTemplateExpression handleContentChange(KtStringTemplateExpression element, TextRange range, String newContent) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(range, "range");
        Intrinsics.checkNotNullParameter(newContent, "newContent");
        ASTNode node = element.getNode();
        Intrinsics.checkNotNullExpressionValue(node, "getNode(...)");
        if (node.getFirstChildNode().getTextLength() == 1) {
            newContent = StringUtil.escapeStringCharacters(newContent);
        }
        Intrinsics.checkNotNull(newContent);
        String text = node.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        StringBuilder sb = new StringBuilder();
        String strSubstring = text.substring(0, range.getStartOffset());
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        StringBuilder sbAppend = sb.append(strSubstring).append(newContent);
        String strSubstring2 = text.substring(range.getEndOffset());
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        String string = sbAppend.append(strSubstring2).toString();
        Project project = element.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        node.replaceAllChildrenToChildrenOf(new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createExpression(string).getNode());
        return (KtStringTemplateExpression) node.getPsi(KtStringTemplateExpression.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractElementManipulator, org.jetbrains.kotlin.com.intellij.psi.ElementManipulator
    public TextRange getRangeInElement(KtStringTemplateExpression element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return KtPsiUtilKt.getContentRange(element);
    }
}
