package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IdSignatureComposer;
import org.jetbrains.kotlin.ir.util.KotlinMangler;

/* JADX INFO: compiled from: DescriptorSignatureComposerStub.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\rH\u0016J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\u000fH\u0016J\u001e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\u0015H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/DescriptorSignatureComposerStub;", "Lorg/jetbrains/kotlin/ir/util/IdSignatureComposer;", "mangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;", "(Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;)V", "getMangler", "()Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;", "composeAnonInitSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "composeEnumEntrySignature", "composeFieldSignature", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "composeSignature", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "withFileSignature", "", "fileSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "body", "Lkotlin/Function0;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DescriptorSignatureComposerStub implements IdSignatureComposer {
    private final KotlinMangler.DescriptorMangler mangler;

    @Override // org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public IdSignature composeAnonInitSignature(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public IdSignature composeEnumEntrySignature(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public IdSignature composeFieldSignature(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public IdSignature composeSignature(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    public DescriptorSignatureComposerStub(KotlinMangler.DescriptorMangler mangler) {
        Intrinsics.checkNotNullParameter(mangler, "mangler");
        this.mangler = mangler;
    }

    @Override // org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public KotlinMangler.DescriptorMangler getMangler() {
        return this.mangler;
    }

    @Override // org.jetbrains.kotlin.ir.util.IdSignatureComposer
    public void withFileSignature(IdSignature.FileSignature fileSignature, Function0<Unit> body) {
        Intrinsics.checkNotNullParameter(fileSignature, "fileSignature");
        Intrinsics.checkNotNullParameter(body, "body");
        body.invoke();
    }
}
