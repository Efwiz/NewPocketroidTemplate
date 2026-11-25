package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.PsiDiagnosticUtils;
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;

/* JADX INFO: compiled from: AnalyzingUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0015B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u001a\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u0007J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u000e\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/AnalyzingUtils;", "", "()V", "WRITE_DEBUG_TRACE_NAMES", "", "checkForSyntacticErrors", "", "root", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "formDebugNameForBindingTrace", "", "debugName", "resolutionSubjectForMessage", "getSyntaxErrorRanges", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiErrorElement;", "throwExceptionOnErrors", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "diagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "PsiErrorElementVisitor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnalyzingUtils {
    public static final AnalyzingUtils INSTANCE = new AnalyzingUtils();

    /* JADX INFO: compiled from: AnalyzingUtils.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/AnalyzingUtils$PsiErrorElementVisitor;", "Lorg/jetbrains/kotlin/psi/KtTreeVisitorVoid;", "()V", "visitErrorElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiErrorElement;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class PsiErrorElementVisitor extends KtTreeVisitorVoid {
        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
        public abstract void visitErrorElement(PsiErrorElement element);
    }

    private AnalyzingUtils() {
    }

    @JvmStatic
    public static final void checkForSyntacticErrors(PsiElement root) {
        Intrinsics.checkNotNullParameter(root, "root");
        root.acceptChildren(new PsiErrorElementVisitor() { // from class: org.jetbrains.kotlin.resolve.AnalyzingUtils.checkForSyntacticErrors.1
            @Override // org.jetbrains.kotlin.resolve.AnalyzingUtils.PsiErrorElementVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
            public void visitErrorElement(PsiErrorElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                throw new IllegalArgumentException(element.getErrorDescription() + "; looking at " + element.getNode().getElementType() + " '" + element.getText() + PsiDiagnosticUtils.atLocation(element));
            }
        });
    }

    @JvmStatic
    public static final List<PsiErrorElement> getSyntaxErrorRanges(PsiElement root) {
        Intrinsics.checkNotNullParameter(root, "root");
        final ArrayList arrayList = new ArrayList();
        root.acceptChildren(new PsiErrorElementVisitor() { // from class: org.jetbrains.kotlin.resolve.AnalyzingUtils.getSyntaxErrorRanges.1
            @Override // org.jetbrains.kotlin.resolve.AnalyzingUtils.PsiErrorElementVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
            public void visitErrorElement(PsiErrorElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                arrayList.add(element);
            }
        });
        return arrayList;
    }

    public final void throwExceptionOnErrors(BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Diagnostics diagnostics = bindingContext.getDiagnostics();
        Intrinsics.checkNotNullExpressionValue(diagnostics, "getDiagnostics(...)");
        throwExceptionOnErrors(diagnostics);
    }

    public final void throwExceptionOnErrors(Diagnostics diagnostics) {
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        Iterator<Diagnostic> it = diagnostics.iterator();
        while (it.hasNext()) {
            DiagnosticSink.THROW_EXCEPTION.report(it.next());
        }
    }

    @JvmStatic
    public static final String formDebugNameForBindingTrace(String debugName, Object resolutionSubjectForMessage) {
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        return "";
    }
}
