package org.jetbrains.kotlin.backend.jvm.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.SpecialDeclarationType;
import org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureDescriptor;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: DisabledIdSignatureDescriptor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\tH\u0016J\u0012\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u00060\rR\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000fH\u0014¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/serialization/DisabledIdSignatureDescriptor;", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureDescriptor;", "()V", "composeAnonInitSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "composeEnumEntrySignature", "composeFieldSignature", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "composeSignature", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "createSignatureBuilder", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureDescriptor$DescriptorBasedSignatureBuilder;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/SpecialDeclarationType;", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DisabledIdSignatureDescriptor extends IdSignatureDescriptor {
    public static final DisabledIdSignatureDescriptor INSTANCE = new DisabledIdSignatureDescriptor();

    @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureDescriptor, org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public IdSignature composeAnonInitSignature(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureDescriptor, org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public IdSignature composeEnumEntrySignature(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureDescriptor, org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public IdSignature composeFieldSignature(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureDescriptor, org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public IdSignature composeSignature(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    private DisabledIdSignatureDescriptor() {
        super(DisabledDescriptorMangler.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureDescriptor
    protected IdSignatureDescriptor.DescriptorBasedSignatureBuilder createSignatureBuilder(SpecialDeclarationType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        throw new IllegalStateException("Should not be called".toString());
    }
}
