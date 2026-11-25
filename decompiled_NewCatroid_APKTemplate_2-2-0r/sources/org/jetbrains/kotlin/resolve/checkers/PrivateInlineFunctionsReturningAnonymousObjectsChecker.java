package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: compiled from: PrivateInlineFunctionsReturningAnonymousObjectsChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\nH\u0002J \u0010\u0010\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/PrivateInlineFunctionsReturningAnonymousObjectsChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "checkTypeAndArguments", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrivateInlineFunctionsReturningAnonymousObjectsChecker implements DeclarationChecker {
    public static final PrivateInlineFunctionsReturningAnonymousObjectsChecker INSTANCE = new PrivateInlineFunctionsReturningAnonymousObjectsChecker();

    private PrivateInlineFunctionsReturningAnonymousObjectsChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        PsiElement nameIdentifier;
        KotlinType returnType;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof SimpleFunctionDescriptor) {
            SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) descriptor;
            if (simpleFunctionDescriptor.isInline() && DescriptorVisibilities.isPrivate(simpleFunctionDescriptor.getVisibilityImpl()) && (declaration instanceof KtNamedFunction) && (nameIdentifier = ((KtNamedFunction) declaration).mo7392getNameIdentifier()) != null && (returnType = simpleFunctionDescriptor.getReturnType()) != null) {
                checkTypeAndArguments(returnType, nameIdentifier, context);
            }
        }
    }

    private final void checkTypeAndArguments(KotlinType type, PsiElement reportOn, DeclarationCheckerContext context) {
        checkType(type, reportOn, context);
        for (TypeProjection typeProjection : type.getArguments()) {
            if (!typeProjection.isStarProjection()) {
                KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                checkTypeAndArguments(nullableAnyType, reportOn, context);
            }
        }
    }

    private final void checkType(KotlinType type, PsiElement reportOn, DeclarationCheckerContext context) {
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        if (this$0 != null && DescriptorUtils.isAnonymousObject(this$0)) {
            context.getTrace().report(Errors.PRIVATE_INLINE_FUNCTIONS_RETURNING_ANONYMOUS_OBJECTS.on(reportOn));
        }
    }
}
