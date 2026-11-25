package org.jetbrains.kotlin.resolve.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: compiled from: ValueClassDeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ReservedMembersAndConstructsForValueClass;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReservedMembersAndConstructsForValueClass implements DeclarationChecker {
    private static final Set<String> boxAndUnboxNames = SetsKt.setOf((Object[]) new String[]{"box", "unbox"});
    private static final Set<String> equalsAndHashCodeNames = SetsKt.setOf((Object[]) new String[]{Namer.EQUALS_METHOD_NAME, "hashCode"});

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        PsiElement lBrace;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        DeclarationDescriptor declarationDescriptor = descriptor.getContainingModule();
        if (declarationDescriptor != null && InlineClassesUtilsKt.isValueClass(declarationDescriptor) && (descriptor instanceof FunctionDescriptor)) {
            if (descriptor instanceof SimpleFunctionDescriptor) {
                KtFunction ktFunction = declaration instanceof KtFunction ? (KtFunction) declaration : null;
                if (ktFunction == null) {
                    return;
                }
                SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) descriptor;
                String strAsString = simpleFunctionDescriptor.getShortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                if (boxAndUnboxNames.contains(strAsString) || (equalsAndHashCodeNames.contains(strAsString) && !context.getLanguageVersionSettings().supportsFeature(LanguageFeature.CustomEqualsInValueClasses))) {
                    PsiElement nameIdentifier = ktFunction.mo7392getNameIdentifier();
                    if (nameIdentifier == null) {
                        return;
                    }
                    context.getTrace().report(Errors.RESERVED_MEMBER_INSIDE_VALUE_CLASS.on(nameIdentifier, strAsString));
                    return;
                }
                if (DescriptorUtilKt.isTypedEqualsInValueClass((FunctionDescriptor) descriptor)) {
                    List<TypeParameterDescriptor> typeParameters = simpleFunctionDescriptor.getTypeParameters();
                    Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
                    if (!typeParameters.isEmpty()) {
                        context.getTrace().report(Errors.TYPE_PARAMETERS_NOT_ALLOWED.on(declaration));
                    }
                    List<ValueParameterDescriptor> valueParameters = simpleFunctionDescriptor.getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                    ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.first((List) valueParameters);
                    KotlinType type = valueParameterDescriptor != null ? valueParameterDescriptor.getType() : null;
                    if (type != null) {
                        List<TypeProjection> arguments = type.getArguments();
                        if ((arguments instanceof Collection) && arguments.isEmpty()) {
                            return;
                        }
                        Iterator<T> it = arguments.iterator();
                        while (it.hasNext()) {
                            if (!((TypeProjection) it.next()).isStarProjection()) {
                                BindingTrace trace = context.getTrace();
                                DiagnosticFactory0<KtTypeReference> diagnosticFactory0 = Errors.TYPE_ARGUMENT_ON_TYPED_VALUE_CLASS_EQUALS;
                                KtTypeReference ktTypeReferenceMo7396getTypeReference = ((KtFunction) declaration).getValueParameters().get(0).mo7396getTypeReference();
                                Intrinsics.checkNotNull(ktTypeReferenceMo7396getTypeReference);
                                trace.report(diagnosticFactory0.on(ktTypeReferenceMo7396getTypeReference));
                                return;
                            }
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            if (!(descriptor instanceof ConstructorDescriptor) || context.getLanguageVersionSettings().supportsFeature(LanguageFeature.ValueClassesSecondaryConstructorWithBody)) {
                return;
            }
            KtSecondaryConstructor ktSecondaryConstructor = declaration instanceof KtSecondaryConstructor ? (KtSecondaryConstructor) declaration : null;
            if (ktSecondaryConstructor == null) {
                return;
            }
            KtBlockExpression bodyExpression = ktSecondaryConstructor.getBodyExpression();
            if (!ktSecondaryConstructor.hasBlockBody() || bodyExpression == null || (lBrace = bodyExpression.getLBrace()) == null) {
                return;
            }
            context.getTrace().report(Errors.UNSUPPORTED_FEATURE.on(lBrace, TuplesKt.to(LanguageFeature.ValueClassesSecondaryConstructorWithBody, context.getLanguageVersionSettings())));
        }
    }
}
