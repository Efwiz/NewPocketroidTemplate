package org.jetbrains.kotlin.backend.common.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;

/* JADX INFO: compiled from: StripTypeAliasDeclarationsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/StripTypeAliasDeclarationsLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "()V", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StripTypeAliasDeclarationsLowering implements DeclarationTransformer {
    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrTypeAlias) {
            return CollectionsKt.emptyList();
        }
        return null;
    }
}
