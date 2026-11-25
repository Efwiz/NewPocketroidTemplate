package org.jetbrains.kotlin.scripting.resolve;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptProvidedPropertyDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/ScriptProvidedPropertyDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/PropertyDescriptorImpl;", "name", "Lorg/jetbrains/kotlin/name/Name;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "receiver", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "isVar", "", "script", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;ZLorg/jetbrains/kotlin/descriptors/ScriptDescriptor;)V", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptProvidedPropertyDescriptor extends PropertyDescriptorImpl {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScriptProvidedPropertyDescriptor(Name name2, KotlinType type, ReceiverParameterDescriptor receiverParameterDescriptor, boolean z, ScriptDescriptor script) {
        super(script, null, Annotations.INSTANCE.getEMPTY(), Modality.FINAL, DescriptorVisibilities.PUBLIC, z, name2, CallableMemberDescriptor.Kind.SYNTHESIZED, SourceElement.NO_SOURCE, false, false, false, false, false, false);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(script, "script");
        setType(type, CollectionsKt.emptyList(), receiverParameterDescriptor, null, CollectionsKt.emptyList());
        initialize(null, null, null, null);
    }
}
