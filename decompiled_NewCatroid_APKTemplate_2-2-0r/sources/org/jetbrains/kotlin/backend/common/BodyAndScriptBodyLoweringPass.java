package org.jetbrains.kotlin.backend.common;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrFile;

/* JADX INFO: compiled from: Lower.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/BodyAndScriptBodyLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "lowerScriptBody", "irDeclarationContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface BodyAndScriptBodyLoweringPass extends BodyLoweringPass {
    void lowerScriptBody(IrDeclarationContainer irDeclarationContainer, IrDeclaration container);

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass, org.jetbrains.kotlin.backend.common.FileLoweringPass
    default void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        LowerKt.runOnFilePostfix(this, irFile);
    }
}
