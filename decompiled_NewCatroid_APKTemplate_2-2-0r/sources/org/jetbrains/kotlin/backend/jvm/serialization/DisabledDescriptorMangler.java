package org.jetbrains.kotlin.backend.jvm.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.ir.util.KotlinMangler;

/* JADX INFO: compiled from: DisabledIdSignatureDescriptor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\b\u001a\u00020\t*\u00020\n2\u0006\u0010\u000b\u001a\u00020\tH\u0016J\u0014\u0010\f\u001a\u00020\u0005*\u00020\r2\u0006\u0010\u000b\u001a\u00020\tH\u0016J\u0014\u0010\u000e\u001a\u00020\u0005*\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\tH\u0016J\u0014\u0010\u0010\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u000b\u001a\u00020\tH\u0016J\u0014\u0010\u0011\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u000b\u001a\u00020\tH\u0016R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/serialization/DisabledDescriptorMangler;", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;", "()V", "hashMangle", "", "", "getHashMangle", "(Ljava/lang/String;)J", "isExported", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "compatibleMode", "mangleEnumEntryString", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "mangleFieldString", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "mangleString", "signatureString", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DisabledDescriptorMangler implements KotlinMangler.DescriptorMangler {
    public static final DisabledDescriptorMangler INSTANCE = new DisabledDescriptorMangler();

    private DisabledDescriptorMangler() {
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler.DescriptorMangler
    public String getManglerName() {
        return KotlinMangler.DescriptorMangler.DefaultImpls.getManglerName(this);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public long hashedMangle(DeclarationDescriptor declarationDescriptor, boolean z) {
        return KotlinMangler.DescriptorMangler.DefaultImpls.hashedMangle(this, declarationDescriptor, z);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public boolean isPlatformSpecificExport(DeclarationDescriptor declarationDescriptor) {
        return KotlinMangler.DescriptorMangler.DefaultImpls.isPlatformSpecificExport(this, declarationDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public long signatureMangle(DeclarationDescriptor declarationDescriptor, boolean z) {
        return KotlinMangler.DescriptorMangler.DefaultImpls.signatureMangle(this, declarationDescriptor, z);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public long getHashMangle(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        throw new IllegalStateException("Should not be called".toString());
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public boolean isExported(DeclarationDescriptor declarationDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        throw new IllegalStateException("Should not be called".toString());
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public String mangleString(DeclarationDescriptor declarationDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        throw new IllegalStateException("Should not be called".toString());
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public String signatureString(DeclarationDescriptor declarationDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        throw new IllegalStateException("Should not be called".toString());
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler.DescriptorMangler
    public String mangleEnumEntryString(ClassDescriptor classDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        throw new IllegalStateException("Should not be called".toString());
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler.DescriptorMangler
    public String mangleFieldString(PropertyDescriptor propertyDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
        throw new IllegalStateException("Should not be called".toString());
    }
}
