package org.jetbrains.kotlin.util;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ControlFlowException;
import org.jetbrains.kotlin.com.intellij.openapi.project.IndexNotReadyException;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;

/* JADX INFO: compiled from: AnalysisExceptions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a \u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u0006H\u0002\u001a%\u0010\u000e\u001a\u0002H\u000f\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u0011H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a\u0014\u0010\u0013\u001a\u00020\u0014*\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0002\u001a\"\u0010\u0015\u001a\u00020\u0014*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00160\u0019H\u0002\u001a@\u0010\u001a\u001a\u00020\u0006*\u00020\u00062\b\u0010\u001b\u001a\u0004\u0018\u00010\u00012\b\u0010\f\u001a\u0004\u0018\u00010\u000b2 \u0010\u001c\u001a\u001c\u0012\u0004\u0012\u00020\u001e\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001e\u0018\u00010\u001f0\u001d\u001a\u0014\u0010 \u001a\u00020\u0006*\u00020\u00062\b\u0010!\u001a\u0004\u0018\u00010\u000b\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\""}, d2 = {"asQuote", "", "", "getAsQuote", "(Ljava/lang/CharSequence;)Ljava/lang/String;", "classNameAndMessage", "", "getClassNameAndMessage", "(Ljava/lang/Throwable;)Ljava/lang/String;", "reportFileMismatch", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "fileSource", "cause", "withSourceCodeAnalysisExceptionUnwrapping", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isDefinitelyNotInsideFile", "", "isInside", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "other", Constants.TYPE_TREE, "Lorg/jetbrains/kotlin/com/intellij/util/diff/FlyweightCapableTreeStructure;", "wrapIntoFileAnalysisExceptionIfNeeded", "filePath", "linesMapping", "Lkotlin/Function1;", "", "Lkotlin/Pair;", "wrapIntoSourceCodeAnalysisExceptionIfNeeded", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "frontend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AnalysisExceptionsKt {
    public static final String getClassNameAndMessage(Throwable th) {
        Intrinsics.checkNotNullParameter(th, "<this>");
        return Reflection.getOrCreateKotlinClass(th.getClass()).getQualifiedName() + ": " + th.getMessage();
    }

    public static final Throwable wrapIntoSourceCodeAnalysisExceptionIfNeeded(Throwable th, KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(th, "<this>");
        if ((th instanceof SourceCodeAnalysisException) || (th instanceof IndexNotReadyException) || (th instanceof ControlFlowException) || (th instanceof VirtualMachineError)) {
            return th;
        }
        return (ktSourceElement != null ? ktSourceElement.getKind() : null) instanceof KtRealSourceElementKind ? new SourceCodeAnalysisException(ktSourceElement, th) : th;
    }

    public static final Throwable wrapIntoFileAnalysisExceptionIfNeeded(Throwable th, String str, KtSourceElement ktSourceElement, Function1<? super Integer, Pair<Integer, Integer>> linesMapping) {
        Intrinsics.checkNotNullParameter(th, "<this>");
        Intrinsics.checkNotNullParameter(linesMapping, "linesMapping");
        if (str == null || ktSourceElement == null) {
            if (th instanceof SourceCodeAnalysisException) {
                throw new IllegalStateException("Sourceless FirFile contains a FirElement with a real source element".toString());
            }
            return th;
        }
        if (!(th instanceof SourceCodeAnalysisException)) {
            return ((th instanceof IndexNotReadyException) || (th instanceof FileAnalysisException) || (th instanceof ControlFlowException) || (th instanceof VirtualMachineError)) ? th : new FileAnalysisException(str, th, null, 4, null);
        }
        SourceCodeAnalysisException sourceCodeAnalysisException = (SourceCodeAnalysisException) th;
        if (Intrinsics.areEqual(ktSourceElement, sourceCodeAnalysisException.getSource())) {
            return new FileAnalysisException(str, sourceCodeAnalysisException.getCause(), null, 4, null);
        }
        return isDefinitelyNotInsideFile(sourceCodeAnalysisException.getSource(), ktSourceElement) ? reportFileMismatch(sourceCodeAnalysisException.getSource(), ktSourceElement, sourceCodeAnalysisException.getCause()) : new FileAnalysisException(str, sourceCodeAnalysisException.getCause(), linesMapping.invoke(Integer.valueOf(sourceCodeAnalysisException.getSource().getStartOffset())));
    }

    private static final boolean isDefinitelyNotInsideFile(KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2) {
        PsiElement psi = KtSourceElementKt.getPsi(ktSourceElement);
        PsiElement psi2 = KtSourceElementKt.getPsi(ktSourceElement2);
        if (psi != null && psi2 != null) {
            if (!Intrinsics.areEqual(psi.getContainingFile(), psi2)) {
                return true;
            }
        } else if (!isInside(ktSourceElement.getLighterASTNode(), ktSourceElement2.getLighterASTNode(), ktSourceElement.getTreeStructure())) {
            return true;
        }
        return false;
    }

    private static final Throwable reportFileMismatch(KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2, Throwable th) {
        String string;
        PsiElement psi = KtSourceElementKt.getPsi(ktSourceElement);
        PsiElement psi2 = KtSourceElementKt.getPsi(ktSourceElement2);
        StringBuilder sb = new StringBuilder("This:\n\n");
        CharSequence text = KtSourceElementKt.getText(ktSourceElement);
        String string2 = sb.append(text != null ? getAsQuote(text) : null).append("\n\n...is not present in").toString();
        if (psi != null && psi2 != null) {
            String path = psi.getContainingFile().getVirtualFile().getPath();
            Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
            String path2 = psi2.getContainingFile().getVirtualFile().getPath();
            Intrinsics.checkNotNullExpressionValue(path2, "getPath(...)");
            string = path2 + ", but rather in " + path;
        } else {
            StringBuilder sb2 = new StringBuilder("...");
            CharSequence text2 = KtSourceElementKt.getText(ktSourceElement2);
            string = sb2.append(text2 != null ? getAsQuote(text2) : null).toString();
        }
        return new IllegalStateException("KtSourceElement inside a SourceCodeAnalysisException was matched against the wrong FirFile source. " + string2 + string, th);
    }

    private static final String getAsQuote(CharSequence charSequence) {
        return CollectionsKt.joinToString$default(StringsKt.split$default(charSequence, new String[]{"\n"}, false, 0, 6, (Object) null), "\n", null, null, 0, null, new Function1<String, CharSequence>() { // from class: org.jetbrains.kotlin.util.AnalysisExceptionsKt$asQuote$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "> " + it;
            }
        }, 30, null);
    }

    private static final boolean isInside(LighterASTNode lighterASTNode, LighterASTNode lighterASTNode2, final FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure) {
        Iterator it = SequencesKt.generateSequence(flyweightCapableTreeStructure.getParent(lighterASTNode), new Function1<LighterASTNode, LighterASTNode>() { // from class: org.jetbrains.kotlin.util.AnalysisExceptionsKt.isInside.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final LighterASTNode invoke(LighterASTNode it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return flyweightCapableTreeStructure.getParent(it2);
            }
        }).iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual((LighterASTNode) it.next(), lighterASTNode2)) {
                return true;
            }
        }
        return false;
    }

    public static final <R> R withSourceCodeAnalysisExceptionUnwrapping(Function0<? extends R> block) throws Throwable {
        Intrinsics.checkNotNullParameter(block, "block");
        try {
            return block.invoke();
        } catch (Throwable th) {
            if (th instanceof SourceCodeAnalysisException) {
                throw th.getCause();
            }
            throw th;
        }
    }
}
