package org.jetbrains.kotlin.backend.common.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;

/* JADX INFO: compiled from: LateinitLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\u000e\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/NullableFieldsForLateinitCreationLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "backendContext", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getBackendContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "withLocalDeclarations", "", "getWithLocalDeclarations", "()Z", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NullableFieldsForLateinitCreationLowering implements DeclarationTransformer {
    private final CommonBackendContext backendContext;

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public boolean getWithLocalDeclarations() {
        return true;
    }

    public NullableFieldsForLateinitCreationLowering(CommonBackendContext backendContext) {
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        this.backendContext = backendContext;
    }

    public final CommonBackendContext getBackendContext() {
        return this.backendContext;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        IrField irField;
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!(declaration instanceof IrField) || (correspondingPropertySymbol = (irField = (IrField) declaration).getCorrespondingPropertySymbol()) == null || (owner = correspondingPropertySymbol.getOwner()) == null || !LateinitLoweringKt.isRealLateinit(owner)) {
            return null;
        }
        IrField irFieldBuildOrGetNullableField = LateinitLoweringKt.buildOrGetNullableField(this.backendContext, irField);
        if (Intrinsics.areEqual(declaration, irFieldBuildOrGetNullableField) || Intrinsics.areEqual(declaration.getParent(), owner.getParent())) {
            return null;
        }
        return CollectionsKt.listOf(irFieldBuildOrGetNullableField);
    }
}
