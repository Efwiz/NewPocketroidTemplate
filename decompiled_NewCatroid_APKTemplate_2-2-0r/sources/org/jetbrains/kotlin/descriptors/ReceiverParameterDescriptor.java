package org.jetbrains.kotlin.descriptors;

import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: loaded from: classes4.dex */
public interface ReceiverParameterDescriptor extends ParameterDescriptor {
    /* JADX INFO: renamed from: copy */
    ReceiverParameterDescriptor mo7203copy(DeclarationDescriptor declarationDescriptor);

    ReceiverValue getValue();

    @Override // org.jetbrains.kotlin.descriptors.Substitutable
    ReceiverParameterDescriptor substitute(TypeSubstitutor typeSubstitutor);
}
