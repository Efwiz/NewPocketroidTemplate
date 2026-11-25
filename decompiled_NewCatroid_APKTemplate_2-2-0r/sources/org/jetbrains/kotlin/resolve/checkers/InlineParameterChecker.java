package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: InlineParameterChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u00020\u0004*\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/InlineParameterChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "reportIncorrectInline", "Lorg/jetbrains/kotlin/psi/KtParameter;", "modifierToken", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineParameterChecker implements DeclarationChecker {
    public static final InlineParameterChecker INSTANCE = new InlineParameterChecker();

    private InlineParameterChecker() {
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0051  */
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KotlinType type;
        KtModifierList modifierList;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (declaration instanceof KtFunction) {
            boolean zHasModifier = declaration.hasModifier(KtTokens.INLINE_KEYWORD);
            for (KtParameter ktParameter : ((KtFunction) declaration).getValueParameters()) {
                VariableDescriptor variableDescriptor = (VariableDescriptor) context.getTrace().get(BindingContext.VALUE_PARAMETER, ktParameter);
                if (!zHasModifier) {
                    Intrinsics.checkNotNull(ktParameter);
                    KtModifierKeywordToken NOINLINE_KEYWORD = KtTokens.NOINLINE_KEYWORD;
                    Intrinsics.checkNotNullExpressionValue(NOINLINE_KEYWORD, "NOINLINE_KEYWORD");
                    reportIncorrectInline(ktParameter, NOINLINE_KEYWORD, context.getTrace());
                    KtModifierKeywordToken CROSSINLINE_KEYWORD = KtTokens.CROSSINLINE_KEYWORD;
                    Intrinsics.checkNotNullExpressionValue(CROSSINLINE_KEYWORD, "CROSSINLINE_KEYWORD");
                    reportIncorrectInline(ktParameter, CROSSINLINE_KEYWORD, context.getTrace());
                } else if (variableDescriptor != null) {
                    KotlinType type2 = variableDescriptor.getType();
                    Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                    if (!FunctionTypesKt.isBuiltinFunctionalType(type2)) {
                        Intrinsics.checkNotNull(ktParameter);
                        KtModifierKeywordToken NOINLINE_KEYWORD2 = KtTokens.NOINLINE_KEYWORD;
                        Intrinsics.checkNotNullExpressionValue(NOINLINE_KEYWORD2, "NOINLINE_KEYWORD");
                        reportIncorrectInline(ktParameter, NOINLINE_KEYWORD2, context.getTrace());
                        KtModifierKeywordToken CROSSINLINE_KEYWORD2 = KtTokens.CROSSINLINE_KEYWORD;
                        Intrinsics.checkNotNullExpressionValue(CROSSINLINE_KEYWORD2, "CROSSINLINE_KEYWORD");
                        reportIncorrectInline(ktParameter, CROSSINLINE_KEYWORD2, context.getTrace());
                    }
                }
                if (zHasModifier && !ktParameter.hasModifier(KtTokens.NOINLINE_KEYWORD) && !ktParameter.hasModifier(KtTokens.CROSSINLINE_KEYWORD) && variableDescriptor != null && (type = variableDescriptor.getType()) != null && FunctionTypesKt.isSuspendFunctionType(type)) {
                    if (declaration.hasModifier(KtTokens.SUSPEND_KEYWORD)) {
                        KtTypeReference ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference();
                        PsiElement modifier = (ktTypeReferenceMo7396getTypeReference == null || (modifierList = ktTypeReferenceMo7396getTypeReference.getModifierList()) == null) ? null : modifierList.getModifier(KtTokens.SUSPEND_KEYWORD);
                        if (modifier != null) {
                            context.getTrace().report(Errors.REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE.on(modifier));
                        }
                    } else {
                        context.getTrace().report(Errors.INLINE_SUSPEND_FUNCTION_TYPE_UNSUPPORTED.on(ktParameter));
                    }
                }
            }
        }
    }

    private final void reportIncorrectInline(KtParameter ktParameter, KtModifierKeywordToken ktModifierKeywordToken, DiagnosticSink diagnosticSink) {
        KtModifierList modifierList = ktParameter.getModifierList();
        PsiElement modifier = modifierList != null ? modifierList.getModifier(ktModifierKeywordToken) : null;
        if (modifier != null) {
            diagnosticSink.report(Errors.ILLEGAL_INLINE_PARAMETER_MODIFIER.on(modifier, ktModifierKeywordToken));
        }
    }
}
