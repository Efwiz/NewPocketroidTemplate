package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: JsCollectClassIdentifiersLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0007H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsCollectClassIdentifiersLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsCollectClassIdentifiersLowering implements DeclarationTransformer {
    private final JsIrBackendContext context;

    public JsCollectClassIdentifiersLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        ClassId classId;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!(declaration instanceof IrClass) || (classId = AdditionalIrUtilsKt.getClassId((IrClass) declaration)) == null) {
            return null;
        }
        this.context.getClassToItsId().put(declaration, classId.toString());
        return null;
    }
}
