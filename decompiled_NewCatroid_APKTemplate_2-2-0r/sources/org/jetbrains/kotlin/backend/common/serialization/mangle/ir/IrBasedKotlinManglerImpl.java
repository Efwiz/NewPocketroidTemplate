package org.jetbrains.kotlin.backend.common.serialization.mangle.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.backend.common.serialization.mangle.SpecialDeclarationType;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.util.KotlinMangler;

/* JADX INFO: compiled from: IrBasedKotlinManglerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u0014\u0010\b\u001a\u00020\t*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u0014\u0010\n\u001a\u00020\t*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrBasedKotlinManglerImpl;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/AbstractKotlinMangler;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;", "()V", "isExported", "", "compatibleMode", "mangleString", "", "signatureString", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrBasedKotlinManglerImpl extends AbstractKotlinMangler<IrDeclaration> implements KotlinMangler.IrMangler {
    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler, org.jetbrains.kotlin.ir.util.KotlinMangler.DescriptorMangler
    public String getManglerName() {
        return KotlinMangler.IrMangler.DefaultImpls.getManglerName(this);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public String mangleString(IrDeclaration irDeclaration, boolean z) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return getMangleComputer(MangleMode.FULL, z).computeMangle(irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public String signatureString(IrDeclaration irDeclaration, boolean z) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return getMangleComputer(MangleMode.SIGNATURE, z).computeMangle(irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public boolean isExported(IrDeclaration irDeclaration, boolean z) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return getExportChecker(z).check(irDeclaration, SpecialDeclarationType.REGULAR);
    }
}
