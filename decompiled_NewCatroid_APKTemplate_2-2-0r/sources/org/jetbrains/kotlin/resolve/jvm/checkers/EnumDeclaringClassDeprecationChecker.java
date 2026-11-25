package org.jetbrains.kotlin.resolve.jvm.checkers;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: EnumDeclaringClassDeprecationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/EnumDeclaringClassDeprecationChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumDeclaringClassDeprecationChecker implements CallChecker {
    public static final EnumDeclaringClassDeprecationChecker INSTANCE = new EnumDeclaringClassDeprecationChecker();

    private EnumDeclaringClassDeprecationChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (resultingDescriptor instanceof PropertyDescriptor) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) resultingDescriptor;
            if (propertyDescriptor.getKind() != CallableMemberDescriptor.Kind.SYNTHESIZED) {
                return;
            }
            ReceiverParameterDescriptor extensionReceiverParameter = propertyDescriptor.getExtensionReceiverParameter();
            ReceiverValue value = extensionReceiverParameter != null ? extensionReceiverParameter.getValue() : null;
            ExtensionReceiver extensionReceiver = value instanceof ExtensionReceiver ? (ExtensionReceiver) value : null;
            if (extensionReceiver != null && Intrinsics.areEqual(propertyDescriptor.getShortName().asString(), "declaringClass")) {
                TypeConstructor constructor = extensionReceiver.getType().getConstructor();
                if (!Intrinsics.areEqual(DescriptorUtilsKt.getClassId(constructor.getThis$0()), StandardClassIds.INSTANCE.getEnum())) {
                    Collection<KotlinType> collectionMo7438getSupertypes = constructor.mo7438getSupertypes();
                    Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
                    Collection<KotlinType> collection = collectionMo7438getSupertypes;
                    if (collection.isEmpty()) {
                        return;
                    }
                    Iterator<T> it = collection.iterator();
                    while (it.hasNext()) {
                        if (Intrinsics.areEqual(DescriptorUtilsKt.getClassId(((KotlinType) it.next()).getConstructor().getThis$0()), StandardClassIds.INSTANCE.getEnum())) {
                        }
                    }
                    return;
                }
                context.getTrace().report(ErrorsJvm.ENUM_DECLARING_CLASS_DEPRECATED.on(context.getLanguageVersionSettings(), reportOn));
            }
        }
    }
}
