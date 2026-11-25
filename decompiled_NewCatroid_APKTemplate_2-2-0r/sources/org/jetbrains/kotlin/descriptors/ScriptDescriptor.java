package org.jetbrains.kotlin.descriptors;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface ScriptDescriptor extends ClassDescriptor {
    ValueParameterDescriptor getEarlierScriptsConstructorParameter();

    List<ValueParameterDescriptor> getExplicitConstructorParameters();

    List<ClassDescriptor> getImplicitReceivers();

    List<ValueParameterDescriptor> getImplicitReceiversParameters();

    int getPriority();

    PropertyDescriptor getResultValue();

    List<PropertyDescriptor> getScriptProvidedProperties();

    List<ValueParameterDescriptor> getScriptProvidedPropertiesParameters();

    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor */
    ClassConstructorDescriptor mo6347getUnsubstitutedPrimaryConstructor();

    boolean isReplScript();
}
