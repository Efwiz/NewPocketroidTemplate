package org.jetbrains.kotlin.resolve.checkers;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtTypeParameterList;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImplKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: FunInterfaceDeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/FunInterfaceDeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkSingleAbstractMember", "abstractMember", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "funInterfaceKeyword", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunInterfaceDeclarationChecker implements DeclarationChecker {
    /* JADX WARN: Code duplicated, block: B:27:0x006d  */
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        PsiElement funKeyword;
        PsiElement valOrVarKeyword;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((declaration instanceof KtClass) && (descriptor instanceof ClassDescriptor)) {
            ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
            if (classDescriptor.isFun() && (funKeyword = ((KtClass) declaration).getFunKeyword()) != null) {
                List<CallableMemberDescriptor> abstractMembers = SamConversionResolverImplKt.getAbstractMembers(classDescriptor);
                for (CallableMemberDescriptor callableMemberDescriptor : abstractMembers) {
                    if (callableMemberDescriptor instanceof PropertyDescriptor) {
                        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) callableMemberDescriptor;
                        boolean zAreEqual = Intrinsics.areEqual(propertyDescriptor.getContainingModule(), descriptor);
                        if (zAreEqual) {
                            SourceElement source = propertyDescriptor.getSourceElement();
                            Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
                            PsiElement psi = PsiSourceElementKt.getPsi(source);
                            KtProperty ktProperty = psi instanceof KtProperty ? (KtProperty) psi : null;
                            if (ktProperty == null || (valOrVarKeyword = ktProperty.getValOrVarKeyword()) == null) {
                                valOrVarKeyword = funKeyword;
                            }
                        } else {
                            valOrVarKeyword = funKeyword;
                        }
                        context.getTrace().report(Errors.FUN_INTERFACE_CANNOT_HAVE_ABSTRACT_PROPERTIES.on(valOrVarKeyword));
                        if (!zAreEqual) {
                            return;
                        }
                    }
                }
                ArrayList arrayList = new ArrayList();
                for (Object obj : abstractMembers) {
                    if (obj instanceof FunctionDescriptor) {
                        arrayList.add(obj);
                    }
                }
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) CollectionsKt.singleOrNull((List) arrayList);
                if (functionDescriptor == null) {
                    context.getTrace().report(Errors.FUN_INTERFACE_WRONG_COUNT_OF_ABSTRACT_MEMBERS.on(funKeyword));
                } else {
                    checkSingleAbstractMember(functionDescriptor, funKeyword, context);
                }
            }
        }
    }

    private final void checkSingleAbstractMember(FunctionDescriptor abstractMember, PsiElement funInterfaceKeyword, DeclarationCheckerContext context) {
        PsiElement funKeyword;
        KtTypeParameterList typeParameterList;
        SourceElement source = abstractMember.getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        PsiElement psi = PsiSourceElementKt.getPsi(source);
        KtNamedFunction ktNamedFunction = psi instanceof KtNamedFunction ? (KtNamedFunction) psi : null;
        List<TypeParameterDescriptor> typeParameters = abstractMember.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        if (!typeParameters.isEmpty()) {
            if (ktNamedFunction == null || (typeParameterList = ktNamedFunction.mo7395getTypeParameterList()) == null) {
                funKeyword = ktNamedFunction != null ? ktNamedFunction.getFunKeyword() : null;
                if (funKeyword != null) {
                    funInterfaceKeyword = funKeyword;
                }
            } else {
                funInterfaceKeyword = typeParameterList;
            }
            Intrinsics.checkNotNull(funInterfaceKeyword);
            context.getTrace().report(Errors.FUN_INTERFACE_ABSTRACT_METHOD_WITH_TYPE_PARAMETERS.on(funInterfaceKeyword));
            return;
        }
        for (ValueParameterDescriptor valueParameterDescriptor : abstractMember.getValueParameters()) {
            Intrinsics.checkNotNull(valueParameterDescriptor);
            if (ArgumentsUtilsKt.hasDefaultValue(valueParameterDescriptor)) {
                SourceElement source2 = valueParameterDescriptor.getSourceElement();
                Intrinsics.checkNotNullExpressionValue(source2, "getSource(...)");
                PsiElement psi2 = PsiSourceElementKt.getPsi(source2);
                if (psi2 == null) {
                    funKeyword = ktNamedFunction != null ? ktNamedFunction.getFunKeyword() : null;
                    if (funKeyword != null) {
                        funInterfaceKeyword = funKeyword;
                    }
                } else {
                    funInterfaceKeyword = psi2;
                }
                context.getTrace().report(Errors.FUN_INTERFACE_ABSTRACT_METHOD_WITH_DEFAULT_VALUE.on(funInterfaceKeyword));
                return;
            }
        }
    }
}
