package org.jetbrains.kotlin.resolve;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderImpl;
import org.jetbrains.kotlin.resolve.calls.results.FlatSignature;
import org.jetbrains.kotlin.resolve.calls.results.FlatSignatureKt;
import org.jetbrains.kotlin.resolve.calls.results.FlatSignatureUtilsKt;
import org.jetbrains.kotlin.resolve.calls.results.OverloadabilitySpecificityCallbacks;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: ShadowedExtensionChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J \u0010\u0011\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J \u0010\u0014\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0016H\u0002J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0013H\u0002J$\u0010\u001c\u001a\u00020\u001b2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00130\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00130\u001eH\u0002J\f\u0010 \u001a\u00020\u001b*\u00020!H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/resolve/ShadowedExtensionChecker;", "", "typeSpecificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "trace", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "(Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;)V", "getTrace", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "getTypeSpecificityComparator", "()Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "checkDeclaration", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "checkShadowedExtensionFunction", "extensionFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "checkShadowedExtensionProperty", "extensionProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getInvokeOperatorShadowingExtensionFunction", SchemaSymbols.ATTVAL_EXTENSION, "member", "isExtensionFunctionShadowedByMemberFunction", "", "isSignatureNotLessSpecific", "extensionSignature", "Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;", "memberSignature", "isPublic", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ShadowedExtensionChecker {
    private final DiagnosticSink trace;
    private final TypeSpecificityComparator typeSpecificityComparator;

    public ShadowedExtensionChecker(TypeSpecificityComparator typeSpecificityComparator, DiagnosticSink trace) {
        Intrinsics.checkNotNullParameter(typeSpecificityComparator, "typeSpecificityComparator");
        Intrinsics.checkNotNullParameter(trace, "trace");
        this.typeSpecificityComparator = typeSpecificityComparator;
        this.trace = trace;
    }

    public final DiagnosticSink getTrace() {
        return this.trace;
    }

    public final TypeSpecificityComparator getTypeSpecificityComparator() {
        return this.typeSpecificityComparator;
    }

    public final void checkDeclaration(KtDeclaration declaration, DeclarationDescriptor descriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        KotlinType type;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (declaration.getName() == null || !(descriptor instanceof CallableMemberDescriptor) || AnnotationsForResolveUtilsKt.hasHidesMembersAnnotation((CallableDescriptor) descriptor) || (extensionReceiverParameter = ((CallableMemberDescriptor) descriptor).getExtensionReceiverParameter()) == null || (type = extensionReceiverParameter.getType()) == null || KotlinTypeKt.isError(type) || type.getIsMarkedNullable()) {
            return;
        }
        if (descriptor instanceof FunctionDescriptor) {
            checkShadowedExtensionFunction(declaration, (FunctionDescriptor) descriptor, this.trace);
        } else if (descriptor instanceof PropertyDescriptor) {
            checkShadowedExtensionProperty(declaration, (PropertyDescriptor) descriptor, this.trace);
        }
    }

    private final void checkShadowedExtensionFunction(KtDeclaration declaration, FunctionDescriptor extensionFunction, DiagnosticSink trace) {
        KotlinType type;
        MemberScope memberScope;
        FunctionDescriptor invokeOperatorShadowingExtensionFunction;
        ReceiverParameterDescriptor extensionReceiverParameter = extensionFunction.getExtensionReceiverParameter();
        if (extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null || (memberScope = type.getMemberScope()) == null) {
            return;
        }
        Name name2 = extensionFunction.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        for (SimpleFunctionDescriptor simpleFunctionDescriptor : memberScope.getContributedFunctions(name2, NoLookupLocation.WHEN_CHECK_DECLARATION_CONFLICTS)) {
            if (isPublic(simpleFunctionDescriptor) && isExtensionFunctionShadowedByMemberFunction(extensionFunction, simpleFunctionDescriptor)) {
                trace.report(Errors.EXTENSION_SHADOWED_BY_MEMBER.on(declaration, simpleFunctionDescriptor));
                return;
            }
        }
        Name name3 = extensionFunction.getShortName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        ClassifierDescriptor contributedClassifier = memberScope.mo7465getContributedClassifier(name3, NoLookupLocation.WHEN_CHECK_DECLARATION_CONFLICTS);
        if (contributedClassifier instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) contributedClassifier;
            if (classDescriptor.getIsInner() && isPublic((DeclarationDescriptorWithVisibility) contributedClassifier)) {
                for (ClassConstructorDescriptor classConstructorDescriptor : classDescriptor.getConstructors()) {
                    Intrinsics.checkNotNull(classConstructorDescriptor);
                    if (isPublic(classConstructorDescriptor) && isExtensionFunctionShadowedByMemberFunction(extensionFunction, classConstructorDescriptor)) {
                        trace.report(Errors.EXTENSION_FUNCTION_SHADOWED_BY_INNER_CLASS_CONSTRUCTOR.on(declaration, classConstructorDescriptor));
                        return;
                    }
                }
            }
        }
        Name name4 = extensionFunction.getShortName();
        Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
        for (PropertyDescriptor propertyDescriptor : memberScope.getContributedVariables(name4, NoLookupLocation.WHEN_CHECK_DECLARATION_CONFLICTS)) {
            if (isPublic(propertyDescriptor) && (invokeOperatorShadowingExtensionFunction = getInvokeOperatorShadowingExtensionFunction(extensionFunction, propertyDescriptor)) != null) {
                trace.report(Errors.EXTENSION_FUNCTION_SHADOWED_BY_MEMBER_PROPERTY_WITH_INVOKE.on(declaration, propertyDescriptor, invokeOperatorShadowingExtensionFunction));
                return;
            }
        }
    }

    private final boolean isPublic(DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility) {
        return Intrinsics.areEqual(declarationDescriptorWithVisibility.getVisibilityImpl().normalize(), DescriptorVisibilities.PUBLIC);
    }

    private final boolean isExtensionFunctionShadowedByMemberFunction(FunctionDescriptor extension, FunctionDescriptor member) {
        if (extension.getValueParameters().size() != member.getValueParameters().size()) {
            return false;
        }
        FunctionDescriptor functionDescriptor = extension;
        FunctionDescriptor functionDescriptor2 = member;
        if (DescriptorUtilsKt.varargParameterPosition(functionDescriptor) != DescriptorUtilsKt.varargParameterPosition(functionDescriptor2)) {
            return false;
        }
        if (extension.isOperator() && !member.isOperator()) {
            return false;
        }
        if (!extension.isInfix() || member.isInfix()) {
            return isSignatureNotLessSpecific(FlatSignatureUtilsKt.createForPossiblyShadowedExtension(FlatSignature.INSTANCE, functionDescriptor), FlatSignatureUtilsKt.createFromCallableDescriptor(FlatSignature.INSTANCE, functionDescriptor2));
        }
        return false;
    }

    private final FunctionDescriptor getInvokeOperatorShadowingExtensionFunction(FunctionDescriptor extension, PropertyDescriptor member) {
        Object next;
        Iterator<T> it = member.getType().getMemberScope().getContributedFunctions(OperatorNameConventions.INVOKE, NoLookupLocation.WHEN_CHECK_DECLARATION_CONFLICTS).iterator();
        while (it.hasNext()) {
            next = it.next();
            SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) next;
            if (isPublic(simpleFunctionDescriptor) && simpleFunctionDescriptor.isOperator() && isExtensionFunctionShadowedByMemberFunction(extension, simpleFunctionDescriptor)) {
                return (FunctionDescriptor) next;
            }
        }
        next = null;
        return (FunctionDescriptor) next;
    }

    private final boolean isSignatureNotLessSpecific(FlatSignature<? extends FunctionDescriptor> extensionSignature, FlatSignature<? extends FunctionDescriptor> memberSignature) {
        return FlatSignatureKt.isSignatureNotLessSpecific$default(ConstraintSystemBuilderImpl.INSTANCE.forSpecificity(), extensionSignature, memberSignature, OverloadabilitySpecificityCallbacks.INSTANCE, this.typeSpecificityComparator, false, 16, null);
    }

    private final void checkShadowedExtensionProperty(KtDeclaration declaration, PropertyDescriptor extensionProperty, DiagnosticSink trace) {
        KotlinType type;
        MemberScope memberScope;
        Object next;
        ReceiverParameterDescriptor extensionReceiverParameter = extensionProperty.getExtensionReceiverParameter();
        if (extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null || (memberScope = type.getMemberScope()) == null) {
            return;
        }
        Name name2 = extensionProperty.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        Iterator<T> it = memberScope.getContributedVariables(name2, NoLookupLocation.WHEN_CHECK_DECLARATION_CONFLICTS).iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) next;
            if (isPublic(propertyDescriptor) && !DescriptorUtilsKt.isExtension(propertyDescriptor)) {
                break;
            }
        }
        PropertyDescriptor propertyDescriptor2 = (PropertyDescriptor) next;
        if (propertyDescriptor2 != null) {
            trace.report(Errors.EXTENSION_SHADOWED_BY_MEMBER.on(declaration, propertyDescriptor2));
        }
    }
}
