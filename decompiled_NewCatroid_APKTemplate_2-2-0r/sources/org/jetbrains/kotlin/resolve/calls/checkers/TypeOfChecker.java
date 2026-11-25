package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: TypeOfChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/TypeOfChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "isTypeOf", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeOfChecker implements CallChecker {
    public static final TypeOfChecker INSTANCE = new TypeOfChecker();

    private TypeOfChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
        if (isTypeOf(resultingDescriptor)) {
            Map<TypeParameterDescriptor, KotlinType> typeArguments = resolvedCall.getTypeArguments();
            Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
            Iterator<Map.Entry<TypeParameterDescriptor, KotlinType>> it = typeArguments.entrySet().iterator();
            while (it.hasNext()) {
                KotlinType value = it.next().getValue();
                Intrinsics.checkNotNull(value);
                if (TypeUtilsKt.contains(value, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.checkers.TypeOfChecker.check.1
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(UnwrappedType type) {
                        Intrinsics.checkNotNullParameter(type, "type");
                        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
                        return Boolean.valueOf((this$0 instanceof TypeParameterDescriptor) && !((TypeParameterDescriptor) this$0).isReified());
                    }
                })) {
                    context.getTrace().report(Errors.UNSUPPORTED.on(reportOn, "'typeOf' with non-reified type parameters is not supported"));
                }
            }
        }
    }

    public final boolean isTypeOf(CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (Intrinsics.areEqual(descriptor.getShortName().asString(), "typeOf") && descriptor.getValueParameters().isEmpty()) {
            DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
            PackageFragmentDescriptor packageFragmentDescriptor = containingDeclaration instanceof PackageFragmentDescriptor ? (PackageFragmentDescriptor) containingDeclaration : null;
            if (Intrinsics.areEqual(packageFragmentDescriptor != null ? packageFragmentDescriptor.getFqName() : null, StandardNames.KOTLIN_REFLECT_FQ_NAME)) {
                return true;
            }
        }
        return false;
    }
}
