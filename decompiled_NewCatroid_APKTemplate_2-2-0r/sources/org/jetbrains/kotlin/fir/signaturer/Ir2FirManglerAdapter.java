package org.jetbrains.kotlin.fir.signaturer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.fir.backend.FirMangler;
import org.jetbrains.kotlin.fir.backend.FirMetadataSource;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.util.KotlinMangler;

/* JADX INFO: compiled from: FirMangler.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0016J\f\u0010\u0013\u001a\u00020\u0014*\u00020\u0002H\u0002J\u0014\u0010\u0015\u001a\u00020\u000e*\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0014\u0010\u0016\u001a\u00020\b*\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0014\u0010\u0017\u001a\u00020\b*\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/signaturer/Ir2FirManglerAdapter;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/AbstractKotlinMangler;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/backend/FirMangler;", "(Lorg/jetbrains/kotlin/fir/backend/FirMangler;)V", "manglerName", "", "getManglerName", "()Ljava/lang/String;", "getExportChecker", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinExportChecker;", "compatibleMode", "", "getMangleComputer", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinMangleComputer;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "fir", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "isExported", "mangleString", "signatureString", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Ir2FirManglerAdapter extends AbstractKotlinMangler<IrDeclaration> implements KotlinMangler.IrMangler {
    private final FirMangler delegate;

    public Ir2FirManglerAdapter(FirMangler delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler, org.jetbrains.kotlin.ir.util.KotlinMangler.DescriptorMangler
    public String getManglerName() {
        return this.delegate.getManglerName();
    }

    private final FirDeclaration fir(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNull(irDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner");
        MetadataSource metadata = ((IrMetadataSourceOwner) irDeclaration).getMetadata();
        Intrinsics.checkNotNull(metadata, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.backend.FirMetadataSource");
        FirDeclaration fir = ((FirMetadataSource) metadata).getFir();
        Intrinsics.checkNotNull(fir);
        return fir;
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public boolean isExported(IrDeclaration irDeclaration, boolean z) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return this.delegate.isExported(fir(irDeclaration), z);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public String mangleString(IrDeclaration irDeclaration, boolean z) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return this.delegate.mangleString(fir(irDeclaration), z);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public String signatureString(IrDeclaration irDeclaration, boolean z) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return this.delegate.signatureString(fir(irDeclaration), z);
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
