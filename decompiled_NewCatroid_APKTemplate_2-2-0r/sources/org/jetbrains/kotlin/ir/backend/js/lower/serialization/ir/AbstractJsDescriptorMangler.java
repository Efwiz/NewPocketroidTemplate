package org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor.DescriptorBasedKotlinManglerImpl;
import org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor.DescriptorExportCheckerVisitor;
import org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor.DescriptorMangleComputer;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;

/* JADX INFO: compiled from: JsMangler.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 \f2\u00020\u0001:\u0002\f\rB\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/AbstractJsDescriptorMangler;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/descriptor/DescriptorBasedKotlinManglerImpl;", "()V", "getExportChecker", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinExportChecker;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "compatibleMode", "", "getMangleComputer", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinMangleComputer;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "Companion", "JsDescriptorExportChecker", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractJsDescriptorMangler extends DescriptorBasedKotlinManglerImpl {
    private static final JsDescriptorExportChecker exportChecker = new JsDescriptorExportChecker();

    /* JADX INFO: compiled from: JsMangler.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\f\u0010\u0003\u001a\u00020\u0004*\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/AbstractJsDescriptorMangler$JsDescriptorExportChecker;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/descriptor/DescriptorExportCheckerVisitor;", "()V", "isPlatformSpecificExported", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class JsDescriptorExportChecker extends DescriptorExportCheckerVisitor {
        @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker
        public boolean isPlatformSpecificExported(DeclarationDescriptor declarationDescriptor) {
            Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
            return false;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler
    public KotlinExportChecker<DeclarationDescriptor> getExportChecker(boolean compatibleMode) {
        return exportChecker;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler
    public KotlinMangleComputer<DeclarationDescriptor> getMangleComputer(MangleMode mode, boolean compatibleMode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        return new DescriptorMangleComputer(new StringBuilder(256), mode);
    }
}
