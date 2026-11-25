package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: AnnotationImplementationTransformer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004H\u0016R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/AnnotationImplementationLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "transformer", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/lower/AnnotationImplementationTransformer;", "(Lkotlin/jvm/functions/Function1;)V", "getTransformer", "()Lkotlin/jvm/functions/Function1;", "lower", "", "irFile", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationImplementationLowering implements FileLoweringPass {
    private final Function1<IrFile, AnnotationImplementationTransformer> transformer;

    /* JADX WARN: Multi-variable type inference failed */
    public AnnotationImplementationLowering(Function1<? super IrFile, ? extends AnnotationImplementationTransformer> transformer) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        this.transformer = transformer;
    }

    public final Function1<IrFile, AnnotationImplementationTransformer> getTransformer() {
        return this.transformer;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        AnnotationImplementationTransformer annotationImplementationTransformerInvoke = this.transformer.invoke(irFile);
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, annotationImplementationTransformerInvoke);
        for (IrClass irClass : annotationImplementationTransformerInvoke.getImplementations$ir_backend_common().values()) {
            IrDeclarationParent parent = irClass.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer");
            ((IrDeclarationContainer) parent).getDeclarations().add(irClass);
        }
    }
}
