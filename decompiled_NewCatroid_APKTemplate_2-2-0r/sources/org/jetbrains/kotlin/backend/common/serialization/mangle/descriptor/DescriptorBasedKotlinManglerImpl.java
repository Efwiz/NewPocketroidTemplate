package org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.backend.common.serialization.mangle.SpecialDeclarationType;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.ir.util.KotlinMangler;

/* JADX INFO: compiled from: DescriptorBasedKotlinManglerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0002J\u0014\u0010\f\u001a\u00020\n*\u00020\u00022\u0006\u0010\t\u001a\u00020\nH\u0016J\u0014\u0010\r\u001a\u00020\u0006*\u00020\u000e2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0014\u0010\u000f\u001a\u00020\u0006*\u00020\u00102\u0006\u0010\t\u001a\u00020\nH\u0016J\u0014\u0010\u0011\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\t\u001a\u00020\nH\u0016J\u0014\u0010\u0012\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/descriptor/DescriptorBasedKotlinManglerImpl;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/AbstractKotlinMangler;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;", "()V", "withMode", "", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "compatibleMode", "", "descriptor", "isExported", "mangleEnumEntryString", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "mangleFieldString", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "mangleString", "signatureString", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DescriptorBasedKotlinManglerImpl extends AbstractKotlinMangler<DeclarationDescriptor> implements KotlinMangler.DescriptorMangler {
    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler, org.jetbrains.kotlin.ir.util.KotlinMangler.DescriptorMangler
    public String getManglerName() {
        return KotlinMangler.DescriptorMangler.DefaultImpls.getManglerName(this);
    }

    private final String withMode(MangleMode mode, boolean compatibleMode, DeclarationDescriptor descriptor) {
        return getMangleComputer(mode, compatibleMode).computeMangle(descriptor);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler.DescriptorMangler
    public String mangleEnumEntryString(ClassDescriptor classDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        return withMode(MangleMode.FQNAME, z, classDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler.DescriptorMangler
    public String mangleFieldString(PropertyDescriptor propertyDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
        return mangleString((DeclarationDescriptor) propertyDescriptor, z);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public String mangleString(DeclarationDescriptor declarationDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return withMode(MangleMode.FULL, z, declarationDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public String signatureString(DeclarationDescriptor declarationDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return withMode(MangleMode.SIGNATURE, z, declarationDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public boolean isExported(DeclarationDescriptor declarationDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return getExportChecker(z).check(declarationDescriptor, SpecialDeclarationType.REGULAR);
    }
}
