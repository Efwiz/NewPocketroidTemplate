package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableTypeConstructor;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tower.NewResolvedCallImpl;
import org.jetbrains.kotlin.resolve.calls.tower.SimplePSIKotlinCallArgument;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;

/* JADX INFO: compiled from: NullableVarargArgumentCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/NullableVarargArgumentCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NullableVarargArgumentCallChecker implements CallChecker {
    public static final NullableVarargArgumentCallChecker INSTANCE = new NullableVarargArgumentCallChecker();

    private NullableVarargArgumentCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        UnwrappedType stableType;
        KotlinTypeInfo kotlinTypeInfo;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.NewInference)) {
            if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
                check(((VariableAsFunctionResolvedCall) resolvedCall).getFunctionCall(), reportOn, context);
                return;
            }
            if (resolvedCall instanceof NewResolvedCallImpl) {
                Iterator<ResolvedCallArgument> it = ((NewResolvedCallImpl) resolvedCall).getArgumentMappingByOriginal().values().iterator();
                while (it.hasNext()) {
                    for (KotlinCallArgument kotlinCallArgument : it.next().getArguments()) {
                        if (kotlinCallArgument.isSpread() && (kotlinCallArgument instanceof SimplePSIKotlinCallArgument)) {
                            SimplePSIKotlinCallArgument simplePSIKotlinCallArgument = (SimplePSIKotlinCallArgument) kotlinCallArgument;
                            LeafPsiElement spreadElement = simplePSIKotlinCallArgument.getValueArgument().getSpreadElement();
                            if (spreadElement != null) {
                                ReceiverValueWithSmartCastInfo receiver = simplePSIKotlinCallArgument.getReceiver();
                                if (!(receiver instanceof ReceiverValueWithSmartCastInfo)) {
                                    receiver = null;
                                }
                                if (receiver != null) {
                                    if (!(ArgumentsUtilsKt.getStableType(receiver).getConstructor() instanceof TypeVariableTypeConstructor) || (kotlinTypeInfo = (KotlinTypeInfo) context.getTrace().getContext().get(BindingContext.EXPRESSION_TYPE_INFO, simplePSIKotlinCallArgument.getValueArgument().getArgumentExpression())) == null || (stableType = kotlinTypeInfo.getType()) == null) {
                                        stableType = ArgumentsUtilsKt.getStableType(receiver);
                                    }
                                    if (!(stableType instanceof FlexibleType) && TypeUtils.isNullableType(stableType)) {
                                        context.getTrace().report(Errors.SPREAD_OF_NULLABLE.on(spreadElement));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
