package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmIrSerializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;

/* JADX INFO: compiled from: SerializeIrPhase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/SerializeIrPhase;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SerializeIrPhase implements FileLoweringPass {
    private final JvmBackendContext context;

    public SerializeIrPhase(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        JvmIrSerializer irSerializer = this.context.getIrSerializer();
        if (irSerializer != null) {
            MetadataSource metadata = irFile.getMetadata();
            MetadataSource.File file = metadata instanceof MetadataSource.File ? (MetadataSource.File) metadata : null;
            if (file != null) {
                file.setSerializedIr(irSerializer.serializeIrFile(irFile));
            }
            List<IrDeclaration> declarations = irFile.getDeclarations();
            ArrayList<IrClass> arrayList = new ArrayList();
            for (Object obj : declarations) {
                if (obj instanceof IrClass) {
                    arrayList.add(obj);
                }
            }
            for (IrClass irClass : arrayList) {
                MetadataSource metadata2 = irClass.getMetadata();
                MetadataSource.Class r2 = metadata2 instanceof MetadataSource.Class ? (MetadataSource.Class) metadata2 : null;
                if (r2 != null) {
                    r2.setSerializedIr(irSerializer.serializeTopLevelIrClass(irClass));
                }
            }
        }
    }
}
