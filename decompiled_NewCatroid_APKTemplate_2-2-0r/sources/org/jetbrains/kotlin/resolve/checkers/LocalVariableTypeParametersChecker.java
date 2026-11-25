package org.jetbrains.kotlin.resolve.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterList;

/* JADX INFO: compiled from: LocalVariableTypeParametersChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/LocalVariableTypeParametersChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalVariableTypeParametersChecker implements DeclarationChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        DiagnosticFactory0<KtTypeParameterList> diagnosticFactory0;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((declaration instanceof KtProperty) && (descriptor instanceof LocalVariableDescriptor)) {
            KtProperty ktProperty = (KtProperty) declaration;
            List<KtTypeParameter> typeParameters = ktProperty.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            KtTypeParameterList typeParameterList = ktProperty.mo7395getTypeParameterList();
            if (typeParameters.isEmpty() || typeParameterList == null) {
                return;
            }
            if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.ProhibitTypeParametersForLocalVariables)) {
                diagnosticFactory0 = Errors.LOCAL_VARIABLE_WITH_TYPE_PARAMETERS;
            } else {
                diagnosticFactory0 = Errors.LOCAL_VARIABLE_WITH_TYPE_PARAMETERS_WARNING;
            }
            context.getTrace().report(diagnosticFactory0.on(typeParameterList));
        }
    }
}
