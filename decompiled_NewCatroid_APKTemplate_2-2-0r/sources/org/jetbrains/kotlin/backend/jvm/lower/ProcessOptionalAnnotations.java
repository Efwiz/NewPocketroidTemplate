package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.resolve.multiplatform.OptionalAnnotationUtil;

/* JADX INFO: compiled from: ProcessOptionalAnnotations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\f\u0010\t\u001a\u00020\u0006*\u00020\nH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ProcessOptionalAnnotations;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "registerOptionalAnnotations", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProcessOptionalAnnotations implements FileLoweringPass {
    private final JvmBackendContext context;

    public ProcessOptionalAnnotations(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        for (IrDeclaration irDeclaration : irFile.getDeclarations()) {
            if (irDeclaration instanceof IrClass) {
                IrClass irClass = (IrClass) irDeclaration;
                if (JvmIrUtilsKt.isOptionalAnnotationClass(irClass)) {
                    registerOptionalAnnotations(irClass);
                }
            }
        }
    }

    private final void registerOptionalAnnotations(IrClass irClass) {
        ClassDescriptor descriptor;
        MetadataSource metadata = irClass.getMetadata();
        DescriptorMetadataSource.Class r0 = metadata instanceof DescriptorMetadataSource.Class ? (DescriptorMetadataSource.Class) metadata : null;
        if (r0 == null || (descriptor = r0.getDescriptor()) == null || !OptionalAnnotationUtil.shouldGenerateExpectClass(descriptor)) {
            return;
        }
        this.context.getState().getFactory().getPackagePartRegistry().getOptionalAnnotations().add(descriptor);
        for (IrDeclaration irDeclaration : irClass.getDeclarations()) {
            if (irDeclaration instanceof IrClass) {
                IrClass irClass2 = (IrClass) irDeclaration;
                if (JvmIrUtilsKt.isOptionalAnnotationClass(irClass2)) {
                    registerOptionalAnnotations(irClass2);
                }
            }
        }
    }
}
