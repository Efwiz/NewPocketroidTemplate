package org.jetbrains.kotlin.ir.backend.jvm.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.backend.common.serialization.mangle.ir.IrBasedKotlinManglerImpl;
import org.jetbrains.kotlin.backend.common.serialization.mangle.ir.IrExportCheckerVisitor;
import org.jetbrains.kotlin.backend.common.serialization.mangle.ir.IrMangleComputer;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JvmMangler.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\f\rB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmIrMangler;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrBasedKotlinManglerImpl;", "()V", "getExportChecker", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinExportChecker;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "compatibleMode", "", "getMangleComputer", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinMangleComputer;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "JvmIrExportChecker", "JvmIrManglerComputer", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmIrMangler extends IrBasedKotlinManglerImpl {
    public static final JvmIrMangler INSTANCE = new JvmIrMangler();

    /* JADX INFO: compiled from: JvmMangler.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\f\u0010\u0005\u001a\u00020\u0003*\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmIrMangler$JvmIrExportChecker;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrExportCheckerVisitor;", "compatibleMode", "", "(Z)V", "isPlatformSpecificExported", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class JvmIrExportChecker extends IrExportCheckerVisitor {
        @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker
        public boolean isPlatformSpecificExported(IrDeclaration irDeclaration) {
            Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
            return false;
        }

        public JvmIrExportChecker(boolean z) {
            super(z);
        }
    }

    private JvmIrMangler() {
    }

    /* JADX INFO: compiled from: JvmMangler.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B!\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\u0010\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u0006H\u0016J\u001c\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\u0013\u001a\u00060\u0003j\u0002`\u0004H\u0014¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmIrMangler$JvmIrManglerComputer;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/ir/IrMangleComputer;", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "compatibleMode", "", "(Ljava/lang/StringBuilder;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;Z)V", "addReturnTypeSpecialCase", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newMode", "mangleTypePlatformSpecific", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "tBuilder", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class JvmIrManglerComputer extends IrMangleComputer {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
        public boolean addReturnTypeSpecialCase(IrFunction function) {
            Intrinsics.checkNotNullParameter(function, "function");
            return true;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public JvmIrManglerComputer(StringBuilder builder, MangleMode mode, boolean z) {
            super(builder, mode, z, false, 8, null);
            Intrinsics.checkNotNullParameter(builder, "builder");
            Intrinsics.checkNotNullParameter(mode, "mode");
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.ir.IrMangleComputer, org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer
        public IrMangleComputer copy(MangleMode newMode) {
            Intrinsics.checkNotNullParameter(newMode, "newMode");
            return new JvmIrManglerComputer(getBuilder(), newMode, getCompatibleMode());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
        public void mangleTypePlatformSpecific(IrType type, StringBuilder tBuilder) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(tBuilder, "tBuilder");
            FqName ENHANCED_NULLABILITY_ANNOTATION = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(ENHANCED_NULLABILITY_ANNOTATION, "ENHANCED_NULLABILITY_ANNOTATION");
            if (IrUtilsKt.hasAnnotation(type, ENHANCED_NULLABILITY_ANNOTATION)) {
                tBuilder.append(MangleConstant.ENHANCED_NULLABILITY_MARK);
            }
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler
    public KotlinExportChecker<IrDeclaration> getExportChecker(boolean compatibleMode) {
        return new JvmIrExportChecker(compatibleMode);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler
    public KotlinMangleComputer<IrDeclaration> getMangleComputer(MangleMode mode, boolean compatibleMode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        return new JvmIrManglerComputer(new StringBuilder(256), mode, compatibleMode);
    }
}
