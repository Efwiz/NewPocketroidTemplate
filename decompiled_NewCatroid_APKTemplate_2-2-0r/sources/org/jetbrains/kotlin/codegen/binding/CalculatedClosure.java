package org.jetbrains.kotlin.codegen.binding;

import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.codegen.context.EnclosedValueDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public interface CalculatedClosure {
    Map<DeclarationDescriptor, EnclosedValueDescriptor> getCaptureVariables();

    ClassDescriptor getCapturedOuterClassDescriptor();

    String getCapturedReceiverFieldName(BindingContext bindingContext, LanguageVersionSettings languageVersionSettings);

    KotlinType getCapturedReceiverFromOuterContext();

    ClassDescriptor getClosureClass();

    CallableDescriptor getEnclosingCallableDescriptorWithReceiver();

    List<Pair<String, Type>> getRecordedFields();

    boolean isSuspend();

    boolean isSuspendLambda();
}
