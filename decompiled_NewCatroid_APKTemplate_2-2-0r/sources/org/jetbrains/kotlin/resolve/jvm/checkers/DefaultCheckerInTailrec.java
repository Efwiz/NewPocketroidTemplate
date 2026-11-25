package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.JvmConstantsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DefaultCheckerInTailrec.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/DefaultCheckerInTailrec;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultCheckerInTailrec implements DeclarationChecker {
    public static final DefaultCheckerInTailrec INSTANCE = new DefaultCheckerInTailrec();

    private DefaultCheckerInTailrec() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtExpression defaultValue;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((declaration instanceof KtNamedFunction) && (descriptor instanceof FunctionDescriptor)) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
            if (functionDescriptor.isTailrec() && !context.getLanguageVersionSettings().supportsFeature(LanguageFeature.ProperComputationOrderOfTailrecDefaultParameters)) {
                List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                ArrayList arrayList = new ArrayList();
                for (Object obj : valueParameters) {
                    if (((ValueParameterDescriptor) obj).declaresDefaultValue()) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                for (Object obj2 : arrayList) {
                    ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) obj2;
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(valueParameterDescriptor);
                    if ((psiElementDescriptorToDeclaration instanceof KtParameter) && (defaultValue = ((KtParameter) psiElementDescriptorToDeclaration).getDefaultValue()) != null) {
                        Intrinsics.checkNotNull(defaultValue);
                        BindingContext context2 = context.getTrace().getContext();
                        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                        ConstantValue<?> compileTimeConstant = JvmConstantsKt.getCompileTimeConstant(defaultValue, context2, false, context.getLanguageVersionSettings().supportsFeature(LanguageFeature.InlineConstVals));
                        if (compileTimeConstant != null) {
                            KotlinType type = compileTimeConstant.getType(DescriptorUtilsKt.getModule(descriptor));
                            if (KotlinBuiltIns.isPrimitiveTypeOrNullablePrimitiveType(type) || KotlinBuiltIns.isStringOrNullableString(type)) {
                            }
                        }
                    }
                    arrayList2.add(obj2);
                }
                if (arrayList2.size() > 1) {
                    context.getTrace().report(ErrorsJvm.TAILREC_WITH_DEFAULTS.on(declaration));
                }
            }
        }
    }
}
