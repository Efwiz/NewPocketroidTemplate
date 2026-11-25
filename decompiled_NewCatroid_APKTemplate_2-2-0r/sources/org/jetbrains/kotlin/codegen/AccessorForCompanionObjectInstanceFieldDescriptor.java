package org.jetbrains.kotlin.codegen;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: AccessorForCompanionObjectInstanceFieldDescriptor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J<\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/codegen/AccessorForCompanionObjectInstanceFieldDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;", "companionObjectDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/name/Name;)V", "getCompanionObjectDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "createSubstitutedCopy", "Lorg/jetbrains/kotlin/descriptors/impl/FunctionDescriptorImpl;", "newOwner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "original", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor$Kind;", "newName", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AccessorForCompanionObjectInstanceFieldDescriptor extends SimpleFunctionDescriptorImpl {
    private final ClassDescriptor companionObjectDescriptor;

    public final ClassDescriptor getCompanionObjectDescriptor() {
        return this.companionObjectDescriptor;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AccessorForCompanionObjectInstanceFieldDescriptor(ClassDescriptor companionObjectDescriptor, Name name2) {
        super(companionObjectDescriptor.getContainingDeclaration(), null, Annotations.INSTANCE.getEMPTY(), name2, CallableMemberDescriptor.Kind.DECLARATION, SourceElement.NO_SOURCE);
        Intrinsics.checkNotNullParameter(companionObjectDescriptor, "companionObjectDescriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.companionObjectDescriptor = companionObjectDescriptor;
        initialize((ReceiverParameterDescriptor) null, (ReceiverParameterDescriptor) null, CollectionsKt.emptyList(), CollectionsKt.emptyList(), CollectionsKt.emptyList(), (KotlinType) companionObjectDescriptor.getDefaultType(), Modality.FINAL, DescriptorVisibilities.LOCAL);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl, org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl
    protected FunctionDescriptorImpl createSubstitutedCopy(DeclarationDescriptor newOwner, FunctionDescriptor original, CallableMemberDescriptor.Kind kind, Name newName, Annotations annotations, SourceElement source) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(source, "source");
        throw new UnsupportedOperationException("Accessor for companion object " + this.companionObjectDescriptor + " should not be substituted");
    }
}
