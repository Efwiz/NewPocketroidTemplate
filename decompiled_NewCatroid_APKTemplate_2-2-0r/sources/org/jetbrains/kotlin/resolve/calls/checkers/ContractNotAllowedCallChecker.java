package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.contracts.parsing.PsiContractsUtilsKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.scopes.HierarchicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;

/* JADX INFO: compiled from: ContractNotAllowedCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/ContractNotAllowedCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContractNotAllowedCallChecker implements CallChecker {
    public static final ContractNotAllowedCallChecker INSTANCE = new ContractNotAllowedCallChecker();

    private ContractNotAllowedCallChecker() {
    }

    /* JADX WARN: Code duplicated, block: B:44:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:46:0x00ad  */
    /* JADX WARN: Code duplicated, block: B:47:0x00b4 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:49:0x00b7  */
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        boolean z;
        boolean z2;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (reportOn instanceof KtElement) {
            CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
            FunctionDescriptor functionDescriptor = resultingDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) resultingDescriptor : null;
            if (functionDescriptor != null && PsiContractsUtilsKt.isContractCallDescriptor(functionDescriptor)) {
                boolean zSupportsFeature = context.getLanguageVersionSettings().supportsFeature(LanguageFeature.AllowContractsForNonOverridableMembers);
                KtElement callElement = resolvedCall.getCall().getCallElement();
                Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
                Ref.BooleanRef booleanRef = new Ref.BooleanRef();
                LexicalScope scope = context.getScope();
                DeclarationDescriptor ownerDescriptor = scope.getOwnerDescriptor();
                FunctionDescriptor functionDescriptor2 = ownerDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) ownerDescriptor : null;
                String str = "Contracts are allowed only for functions";
                if (functionDescriptor2 == null || (functionDescriptor2 instanceof PropertyAccessorDescriptor)) {
                    check$contractNotAllowed(booleanRef, context, reportOn, "Contracts are allowed only for functions");
                }
                DeclarationDescriptor declarationDescriptor = scope.getOwnerDescriptor().getContainingModule();
                boolean z3 = false;
                if (zSupportsFeature) {
                    while (true) {
                        if (declarationDescriptor instanceof PackageFragmentDescriptor) {
                            z = true;
                            break;
                        } else {
                            if (!(declarationDescriptor instanceof ClassDescriptor)) {
                                z = false;
                                break;
                            }
                            declarationDescriptor = ((ClassDescriptor) declarationDescriptor).getContainingModule();
                        }
                    }
                } else {
                    z = declarationDescriptor instanceof PackageFragmentDescriptor;
                }
                if (z && scope.getKind() == LexicalScopeKind.CODE_BLOCK) {
                    HierarchicalScope parent = scope.getParent();
                    LexicalScope lexicalScope = parent instanceof LexicalScope ? (LexicalScope) parent : null;
                    if ((lexicalScope != null ? lexicalScope.getKind() : null) == LexicalScopeKind.FUNCTION_INNER_SCOPE) {
                        z2 = true;
                    } else if (scope.getKind() == LexicalScopeKind.FUNCTION_INNER_SCOPE) {
                        check$contractNotAllowed(booleanRef, context, reportOn, "Contracts are allowed only in function body block");
                        z2 = false;
                    } else {
                        if (!zSupportsFeature) {
                            str = "Contracts are allowed only for top-level functions";
                        }
                        check$contractNotAllowed(booleanRef, context, reportOn, str);
                        z2 = true;
                    }
                } else if (scope.getKind() == LexicalScopeKind.FUNCTION_INNER_SCOPE) {
                    check$contractNotAllowed(booleanRef, context, reportOn, "Contracts are allowed only in function body block");
                    z2 = false;
                } else {
                    if (!zSupportsFeature) {
                        str = "Contracts are allowed only for top-level functions";
                    }
                    check$contractNotAllowed(booleanRef, context, reportOn, str);
                    z2 = true;
                }
                if (functionDescriptor2 != null && functionDescriptor2.isOperator()) {
                    check$contractNotAllowed(booleanRef, context, reportOn, "Contracts are not allowed for operator functions");
                }
                if (!zSupportsFeature) {
                    if (functionDescriptor2 != null && ModalityUtilsKt.isOverridable(functionDescriptor2)) {
                        check$contractNotAllowed(booleanRef, context, reportOn, "Contracts are not allowed for open functions");
                    }
                }
                if (zSupportsFeature) {
                    if (functionDescriptor2 != null && ModalityUtilsKt.isOverridableOrOverrides(functionDescriptor2)) {
                        z3 = true;
                    }
                    if (z3) {
                        check$contractNotAllowed(booleanRef, context, reportOn, "Contracts are not allowed for open or override functions");
                    }
                }
                if (!KtPsiUtilKt.isFirstStatement(callElement) && z2) {
                    check$contractNotAllowed(booleanRef, context, reportOn, "Contract should be the first statement");
                }
                if (booleanRef.element) {
                    context.getTrace().record(BindingContext.CONTRACT_NOT_ALLOWED, callElement, true);
                }
            }
        }
    }

    private static final void check$contractNotAllowed(Ref.BooleanRef booleanRef, CallCheckerContext callCheckerContext, PsiElement psiElement, String str) {
        booleanRef.element = true;
        callCheckerContext.getTrace().report(Errors.CONTRACT_NOT_ALLOWED.on(psiElement, str));
    }
}
