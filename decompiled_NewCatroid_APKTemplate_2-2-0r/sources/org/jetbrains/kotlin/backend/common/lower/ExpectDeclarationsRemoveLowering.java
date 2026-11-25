package org.jetbrains.kotlin.backend.common.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;

/* JADX INFO: compiled from: ExpectDeclarationsRemoveLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\tH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ExpectDeclarationsRemoveLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "(Lorg/jetbrains/kotlin/backend/common/BackendContext;)V", "remover", "Lorg/jetbrains/kotlin/backend/common/lower/ExpectDeclarationRemover;", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpectDeclarationsRemoveLowering implements DeclarationTransformer {
    private final ExpectDeclarationRemover remover;

    public ExpectDeclarationsRemoveLowering(BackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.remover = new ExpectDeclarationRemover(context.getIr().getSymbols().getSymbolTable(), true);
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return this.remover.transformFlat(declaration);
    }
}
