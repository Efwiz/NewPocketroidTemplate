package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: JvmPhases.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/FileCodegen;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "generateMultifileFacade", "", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Z)V", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FileCodegen implements FileLoweringPass {
    private final JvmBackendContext context;
    private final boolean generateMultifileFacade;

    public FileCodegen(JvmBackendContext context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.generateMultifileFacade = z;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        if ((irFile.getFileEntry() instanceof MultifileFacadeFileEntry) == this.generateMultifileFacade) {
            for (IrDeclaration irDeclaration : irFile.getDeclarations()) {
                if (!(irDeclaration instanceof IrClass)) {
                    throw new AssertionError("File-level declaration should be IrClass after JvmLower, got: " + RenderIrElementKt.render$default(irDeclaration, (DumpIrTreeOptions) null, 1, (Object) null));
                }
                ClassCodegen.Companion.getOrCreate$default(ClassCodegen.INSTANCE, (IrClass) irDeclaration, this.context, null, 4, null).generate();
            }
        }
    }
}
