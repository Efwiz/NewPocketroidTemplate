package org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.backend.common.serialization.mangle.ir.IrBasedKotlinManglerImpl;
import org.jetbrains.kotlin.backend.common.serialization.mangle.ir.IrExportCheckerVisitor;
import org.jetbrains.kotlin.backend.common.serialization.mangle.ir.IrMangleComputer;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;

/* JADX INFO: compiled from: JsMangler.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001\fB\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/AbstractJsManglerIr;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrBasedKotlinManglerImpl;", "()V", "getExportChecker", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinExportChecker;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "compatibleMode", "", "getMangleComputer", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinMangleComputer;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "JsIrExportChecker", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractJsManglerIr extends IrBasedKotlinManglerImpl {

    /* JADX INFO: compiled from: JsMangler.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\f\u0010\u0005\u001a\u00020\u0003*\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/AbstractJsManglerIr$JsIrExportChecker;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrExportCheckerVisitor;", "compatibleMode", "", "(Z)V", "isPlatformSpecificExported", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class JsIrExportChecker extends IrExportCheckerVisitor {
        @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker
        public boolean isPlatformSpecificExported(IrDeclaration irDeclaration) {
            Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
            return false;
        }

        public JsIrExportChecker(boolean z) {
            super(z);
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler
    public KotlinExportChecker<IrDeclaration> getExportChecker(boolean compatibleMode) {
        return new JsIrExportChecker(compatibleMode);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler
    public KotlinMangleComputer<IrDeclaration> getMangleComputer(MangleMode mode, boolean compatibleMode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        return new IrMangleComputer(new StringBuilder(256), mode, compatibleMode, false, 8, null);
    }
}
