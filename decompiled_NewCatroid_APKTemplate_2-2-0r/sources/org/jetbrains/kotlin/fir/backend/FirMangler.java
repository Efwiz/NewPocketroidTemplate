package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.backend.common.serialization.mangle.SpecialDeclarationType;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;

/* JADX INFO: compiled from: FirMangler.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\r\u001a\u00020\u0005*\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\u000e\u001a\u00020\u0005*\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/FirMangler;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/AbstractKotlinMangler;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "()V", "manglerName", "", "getManglerName", "()Ljava/lang/String;", "getExportChecker", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinExportChecker;", "compatibleMode", "", "isExported", "mangleString", "signatureString", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirMangler extends AbstractKotlinMangler<FirDeclaration> {
    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public boolean isExported(FirDeclaration firDeclaration, boolean z) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return true;
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public String mangleString(FirDeclaration firDeclaration, boolean z) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return getMangleComputer(MangleMode.FULL, z).computeMangle(firDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler
    public String signatureString(FirDeclaration firDeclaration, boolean z) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return getMangleComputer(MangleMode.SIGNATURE, z).computeMangle(firDeclaration);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler
    public KotlinExportChecker<FirDeclaration> getExportChecker(boolean compatibleMode) {
        return new KotlinExportChecker<FirDeclaration>() { // from class: org.jetbrains.kotlin.fir.backend.FirMangler.getExportChecker.1
            @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker
            public boolean check(FirDeclaration declaration, SpecialDeclarationType type) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                Intrinsics.checkNotNullParameter(type, "type");
                return true;
            }

            @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker
            public boolean isPlatformSpecificExported(FirDeclaration firDeclaration) {
                Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
                return true;
            }
        };
    }

    @Override // org.jetbrains.kotlin.ir.util.KotlinMangler, org.jetbrains.kotlin.ir.util.KotlinMangler.DescriptorMangler
    public String getManglerName() {
        return "Fir";
    }
}
