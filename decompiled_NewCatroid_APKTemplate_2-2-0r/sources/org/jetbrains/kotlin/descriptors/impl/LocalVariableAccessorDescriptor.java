package org.jetbrains.kotlin.descriptors.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: LocalVariableAccessorDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u00012\u00020\u0002:\u0002\u0010\u0011B\u0017\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u0082\u0001\u0002\u0012\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableAccessorDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;", "Lorg/jetbrains/kotlin/descriptors/VariableAccessorDescriptor;", "correspondingVariable", "Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableDescriptor;", "isGetter", "", "(Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableDescriptor;Z)V", "getCorrespondingVariable", "()Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableDescriptor;", "createValueParameter", "Lorg/jetbrains/kotlin/descriptors/impl/ValueParameterDescriptorImpl;", "name", "Lorg/jetbrains/kotlin/name/Name;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "Getter", "Setter", "Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableAccessorDescriptor$Getter;", "Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableAccessorDescriptor$Setter;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LocalVariableAccessorDescriptor extends SimpleFunctionDescriptorImpl implements VariableAccessorDescriptor {
    private final LocalVariableDescriptor correspondingVariable;

    public /* synthetic */ LocalVariableAccessorDescriptor(LocalVariableDescriptor localVariableDescriptor, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(localVariableDescriptor, z);
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor
    public /* bridge */ /* synthetic */ VariableDescriptorWithAccessors getCorrespondingVariable() {
        return this.correspondingVariable;
    }

    @Override // org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor
    public final LocalVariableDescriptor getCorrespondingVariable() {
        return this.correspondingVariable;
    }

    private LocalVariableAccessorDescriptor(LocalVariableDescriptor localVariableDescriptor, boolean z) {
        List listListOf;
        super(localVariableDescriptor.getDeclarationDescriptor(), null, Annotations.INSTANCE.getEMPTY(), Name.special((z ? "<get-" : "<set-") + localVariableDescriptor.getShortName() + '>'), CallableMemberDescriptor.Kind.SYNTHESIZED, SourceElement.NO_SOURCE);
        this.correspondingVariable = localVariableDescriptor;
        if (z) {
            listListOf = CollectionsKt.emptyList();
        } else {
            Name nameIdentifier = Name.identifier("value");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            KotlinType type = localVariableDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            listListOf = CollectionsKt.listOf(createValueParameter(nameIdentifier, type));
        }
        List list = listListOf;
        KotlinType type2 = z ? localVariableDescriptor.getType() : DescriptorUtilsKt.getBuiltIns(localVariableDescriptor).getUnitType();
        Intrinsics.checkNotNull(type2);
        initialize((ReceiverParameterDescriptor) null, (ReceiverParameterDescriptor) null, CollectionsKt.emptyList(), CollectionsKt.emptyList(), list, type2, Modality.FINAL, DescriptorVisibilities.LOCAL);
    }

    /* JADX INFO: compiled from: LocalVariableAccessorDescriptor.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableAccessorDescriptor$Getter;", "Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableAccessorDescriptor;", "correspondingVariable", "Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableDescriptor;)V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Getter extends LocalVariableAccessorDescriptor {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Getter(LocalVariableDescriptor correspondingVariable) {
            super(correspondingVariable, true, null);
            Intrinsics.checkNotNullParameter(correspondingVariable, "correspondingVariable");
        }
    }

    /* JADX INFO: compiled from: LocalVariableAccessorDescriptor.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableAccessorDescriptor$Setter;", "Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableAccessorDescriptor;", "correspondingVariable", "Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableDescriptor;)V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Setter extends LocalVariableAccessorDescriptor {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Setter(LocalVariableDescriptor correspondingVariable) {
            super(correspondingVariable, false, null);
            Intrinsics.checkNotNullParameter(correspondingVariable, "correspondingVariable");
        }
    }

    private final ValueParameterDescriptorImpl createValueParameter(Name name2, KotlinType type) {
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        return new ValueParameterDescriptorImpl(this, null, 0, empty, name2, type, false, false, false, null, NO_SOURCE);
    }
}
