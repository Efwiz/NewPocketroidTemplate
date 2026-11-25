package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: PolymorphicSignatureCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/PolymorphicSignatureCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "polymorphicSignatureFqName", "Lorg/jetbrains/kotlin/name/FqName;", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PolymorphicSignatureCallChecker implements CallChecker {
    public static final PolymorphicSignatureCallChecker INSTANCE = new PolymorphicSignatureCallChecker();
    public static final FqName polymorphicSignatureFqName = new FqName(CommonClassNames.JAVA_LANG_INVOKE_MH_POLYMORPHIC);

    private PolymorphicSignatureCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        List<ResolvedValueArgument> valueArgumentsByIndex;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.PolymorphicSignature) && resolvedCall.getResultingDescriptor().getAnnotations().hasAnnotation(polymorphicSignatureFqName) && (valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex()) != null) {
            for (ResolvedValueArgument resolvedValueArgument : valueArgumentsByIndex) {
                if (resolvedValueArgument instanceof VarargValueArgument) {
                    Iterator<ValueArgument> it = ((VarargValueArgument) resolvedValueArgument).getArguments().iterator();
                    while (it.hasNext()) {
                        LeafPsiElement spreadElement = it.next().getSpreadElement();
                        if (spreadElement != null) {
                            context.getTrace().report(ErrorsJvm.SPREAD_ON_SIGNATURE_POLYMORPHIC_CALL.on(context.getLanguageVersionSettings(), spreadElement));
                        }
                    }
                }
            }
        }
    }
}
