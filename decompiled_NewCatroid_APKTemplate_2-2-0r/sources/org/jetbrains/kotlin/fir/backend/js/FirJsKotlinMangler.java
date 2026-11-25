package org.jetbrains.kotlin.fir.backend.js;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.fir.backend.FirExportCheckerVisitor;
import org.jetbrains.kotlin.fir.backend.FirMangleComputer;
import org.jetbrains.kotlin.fir.backend.FirMangler;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;

/* JADX INFO: compiled from: FirJsKotlinMangler.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\fB\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/js/FirJsKotlinMangler;", "Lorg/jetbrains/kotlin/fir/backend/FirMangler;", "()V", "getExportChecker", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinExportChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "compatibleMode", "", "getMangleComputer", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinMangleComputer;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "JsFirExportChecker", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsKotlinMangler extends FirMangler {

    /* JADX INFO: compiled from: FirJsKotlinMangler.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\f\u0010\u0003\u001a\u00020\u0004*\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/js/FirJsKotlinMangler$JsFirExportChecker;", "Lorg/jetbrains/kotlin/fir/backend/FirExportCheckerVisitor;", "()V", "isPlatformSpecificExported", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class JsFirExportChecker extends FirExportCheckerVisitor {
        @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker
        public boolean isPlatformSpecificExported(FirDeclaration firDeclaration) {
            Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
            return false;
        }
    }

    @Override // org.jetbrains.kotlin.fir.backend.FirMangler, org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler
    public KotlinExportChecker<FirDeclaration> getExportChecker(boolean compatibleMode) {
        return new JsFirExportChecker();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler
    public KotlinMangleComputer<FirDeclaration> getMangleComputer(MangleMode mode, boolean compatibleMode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        return new FirMangleComputer(new StringBuilder(256), mode);
    }
}
