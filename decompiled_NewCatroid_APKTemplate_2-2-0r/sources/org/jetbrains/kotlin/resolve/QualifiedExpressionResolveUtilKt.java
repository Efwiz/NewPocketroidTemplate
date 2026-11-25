package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForTypeAliasObject;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ClassifierQualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.PackageQualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.Qualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.TypeParameterQualifier;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;

/* JADX INFO: compiled from: QualifiedExpressionResolveUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0016\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006\u001a\"\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u000e\u0010\t\u001a\u0004\u0018\u00010\u0001*\u00020\nH\u0002¨\u0006\u000b"}, d2 = {"resolveQualifierAsReceiverInExpression", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "qualifier", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/Qualifier;", "selector", "context", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "resolveQualifierAsStandaloneExpression", "resolveQualifierReferenceTarget", "getCallableReceiverDescriptorRetainingTypeAliasReference", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class QualifiedExpressionResolveUtilKt {
    public static final DeclarationDescriptor resolveQualifierAsReceiverInExpression(Qualifier qualifier, DeclarationDescriptor declarationDescriptor, ExpressionTypingContext context) {
        Intrinsics.checkNotNullParameter(qualifier, "qualifier");
        Intrinsics.checkNotNullParameter(context, "context");
        DeclarationDescriptor declarationDescriptorResolveQualifierReferenceTarget = resolveQualifierReferenceTarget(qualifier, declarationDescriptor, context);
        if (declarationDescriptorResolveQualifierReferenceTarget instanceof TypeParameterDescriptor) {
            context.trace.report(Errors.TYPE_PARAMETER_ON_LHS_OF_DOT.on(qualifier.getReferenceExpression(), (TypeParameterDescriptor) declarationDescriptorResolveQualifierReferenceTarget));
        }
        return declarationDescriptorResolveQualifierReferenceTarget;
    }

    public static final DeclarationDescriptor resolveQualifierAsStandaloneExpression(Qualifier qualifier, ExpressionTypingContext context) {
        Intrinsics.checkNotNullParameter(qualifier, "qualifier");
        Intrinsics.checkNotNullParameter(context, "context");
        DeclarationDescriptor declarationDescriptorResolveQualifierReferenceTarget = resolveQualifierReferenceTarget(qualifier, null, context);
        if (declarationDescriptorResolveQualifierReferenceTarget instanceof TypeAliasDescriptor) {
            ClassDescriptor classDescriptor = ((TypeAliasDescriptor) declarationDescriptorResolveQualifierReferenceTarget).getClassDescriptor();
            if (classDescriptor != null && !classDescriptor.getKind().isSingleton()) {
                context.trace.report(Errors.NO_COMPANION_OBJECT.on(qualifier.getReferenceExpression(), (ClassifierDescriptor) declarationDescriptorResolveQualifierReferenceTarget));
            }
        } else if (declarationDescriptorResolveQualifierReferenceTarget instanceof TypeParameterDescriptor) {
            context.trace.report(Errors.TYPE_PARAMETER_IS_NOT_AN_EXPRESSION.on(qualifier.getReferenceExpression(), (TypeParameterDescriptor) declarationDescriptorResolveQualifierReferenceTarget));
        } else if (declarationDescriptorResolveQualifierReferenceTarget instanceof ClassDescriptor) {
            if (!((ClassDescriptor) declarationDescriptorResolveQualifierReferenceTarget).getKind().isSingleton()) {
                context.trace.report(Errors.NO_COMPANION_OBJECT.on(qualifier.getReferenceExpression(), (ClassifierDescriptor) declarationDescriptorResolveQualifierReferenceTarget));
            }
        } else if (declarationDescriptorResolveQualifierReferenceTarget instanceof PackageViewDescriptor) {
            context.trace.report(Errors.EXPRESSION_EXPECTED_PACKAGE_FOUND.on(qualifier.getReferenceExpression()));
        }
        return declarationDescriptorResolveQualifierReferenceTarget;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x006e  */
    private static final DeclarationDescriptor resolveQualifierReferenceTarget(Qualifier qualifier, DeclarationDescriptor declarationDescriptor, ExpressionTypingContext expressionTypingContext) {
        DeclarationDescriptor containingModule;
        boolean z;
        if (qualifier instanceof TypeParameterQualifier) {
            return ((TypeParameterQualifier) qualifier).getDescriptor();
        }
        if (declarationDescriptor instanceof ConstructorDescriptor) {
            containingModule = ((ConstructorDescriptor) declarationDescriptor).getContainingModule().getContainingModule();
        } else {
            containingModule = declarationDescriptor != null ? declarationDescriptor.getContainingModule() : null;
        }
        if ((qualifier instanceof PackageQualifier) && ((containingModule instanceof PackageFragmentDescriptor) || (containingModule instanceof PackageViewDescriptor))) {
            PackageQualifier packageQualifier = (PackageQualifier) qualifier;
            if (Intrinsics.areEqual(DescriptorUtils.getFqName(packageQualifier.getDescriptor()), DescriptorUtils.getFqName(containingModule))) {
                return packageQualifier.getDescriptor();
            }
        }
        if (qualifier instanceof ClassifierQualifier) {
            ClassifierDescriptorWithTypeParameters descriptor = ((ClassifierQualifier) qualifier).getDescriptor();
            if (declarationDescriptor instanceof CallableDescriptor) {
                CallableDescriptor callableDescriptor = (CallableDescriptor) declarationDescriptor;
                if (callableDescriptor.mo6753getDispatchReceiverParameter() == null && callableDescriptor.getExtensionReceiverParameter() == null) {
                    z = false;
                } else {
                    z = true;
                }
            } else {
                z = false;
            }
            DeclarationDescriptor callableReceiverDescriptorRetainingTypeAliasReference = getCallableReceiverDescriptorRetainingTypeAliasReference(descriptor);
            if (z && callableReceiverDescriptorRetainingTypeAliasReference != null) {
                ClassDescriptor classValueTypeDescriptor = DescriptorUtilsKt.getClassValueTypeDescriptor(descriptor);
                Intrinsics.checkNotNull(classValueTypeDescriptor);
                expressionTypingContext.trace.record(BindingContext.REFERENCE_TARGET, qualifier.getReferenceExpression(), callableReceiverDescriptorRetainingTypeAliasReference);
                expressionTypingContext.trace.recordType(QualifierKt.getExpression(qualifier), classValueTypeDescriptor.getDefaultType());
                if (DescriptorUtilsKt.getHasCompanionObject(descriptor)) {
                    expressionTypingContext.trace.record(BindingContext.SHORT_REFERENCE_TO_COMPANION_OBJECT, qualifier.getReferenceExpression(), descriptor);
                }
                return classValueTypeDescriptor;
            }
        }
        return qualifier.getDescriptor();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [org.jetbrains.kotlin.descriptors.ClassifierDescriptor] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v4, types: [org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForTypeAliasObject] */
    private static final DeclarationDescriptor getCallableReceiverDescriptorRetainingTypeAliasReference(ClassifierDescriptor classifierDescriptor) {
        if (classifierDescriptor instanceof ClassDescriptor) {
            return DescriptorUtilsKt.getClassValueDescriptor((ClassDescriptor) classifierDescriptor);
        }
        if (!(classifierDescriptor instanceof TypeAliasDescriptor)) {
            return null;
        }
        TypeAliasDescriptor typeAliasDescriptor = (TypeAliasDescriptor) classifierDescriptor;
        ClassDescriptor classDescriptor = typeAliasDescriptor.getClassDescriptor();
        if ((classDescriptor != null ? DescriptorUtilsKt.getClassValueDescriptor(classDescriptor) : null) != null) {
            classifierDescriptor = new FakeCallableDescriptorForTypeAliasObject(typeAliasDescriptor);
        }
        return (DeclarationDescriptor) classifierDescriptor;
    }
}
