package org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.util.KotlinMangler;

/* JADX INFO: compiled from: DescriptorBasedKotlinManglerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0014\u0010\u0013\u001a\u00020\u000e*\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0014\u0010\u0014\u001a\u00020\b*\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0014\u0010\u0015\u001a\u00020\b*\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/descriptor/Ir2DescriptorManglerAdapter;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/AbstractKotlinMangler;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/descriptor/DescriptorBasedKotlinManglerImpl;", "(Lorg/jetbrains/kotlin/backend/common/serialization/mangle/descriptor/DescriptorBasedKotlinManglerImpl;)V", "manglerName", "", "getManglerName", "()Ljava/lang/String;", "getExportChecker", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinExportChecker;", "compatibleMode", "", "getMangleComputer", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinMangleComputer;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "isExported", "mangleString", "signatureString", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Ir2DescriptorManglerAdapter extends AbstractKotlinMangler<IrDeclaration> implements KotlinMangler.IrMangler {
    private final DescriptorBasedKotlinManglerImpl delegate;

    public Ir2DescriptorManglerAdapter(DescriptorBasedKotlinManglerImpl delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler, org.jetbrains.kotlin.ir.util.KotlinMangler.DescriptorMangler
    public String getManglerName() {
        return this.delegate.getManglerName();
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public boolean isExported(IrDeclaration irDeclaration, boolean z) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return this.delegate.isExported(irDeclaration.getDescriptor(), z);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public String mangleString(IrDeclaration irDeclaration, boolean z) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        if (irDeclaration instanceof IrEnumEntry) {
            return this.delegate.mangleEnumEntryString(((IrEnumEntry) irDeclaration).getDescriptor(), z);
        }
        return irDeclaration instanceof IrField ? this.delegate.mangleFieldString(((IrField) irDeclaration).getDescriptor(), z) : this.delegate.mangleString(irDeclaration.getDescriptor(), z);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public String signatureString(IrDeclaration irDeclaration, boolean z) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return this.delegate.signatureString(irDeclaration.getDescriptor(), z);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler
    public KotlinMangleComputer<IrDeclaration> getMangleComputer(MangleMode mode, boolean compatibleMode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        throw new IllegalStateException("Should not have been reached".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler
    public KotlinExportChecker<IrDeclaration> getExportChecker(boolean compatibleMode) {
        throw new IllegalStateException("Should not be called".toString());
    }
}
