package org.jetbrains.kotlin.backend.common.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;

/* JADX INFO: compiled from: ErrorExpressionLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ErrorDeclarationLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "()V", "transformErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "transformFlat", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ErrorDeclarationLowering implements DeclarationTransformer {
    public abstract IrDeclaration transformErrorDeclaration(IrErrorDeclaration declaration);

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrErrorDeclaration) {
            return CollectionsKt.listOf(transformErrorDeclaration((IrErrorDeclaration) declaration));
        }
        return null;
    }
}
