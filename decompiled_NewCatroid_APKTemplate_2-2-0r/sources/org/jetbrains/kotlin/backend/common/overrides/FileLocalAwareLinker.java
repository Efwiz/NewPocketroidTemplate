package org.jetbrains.kotlin.backend.common.overrides;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: FakeOverrides.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u001a\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/overrides/FileLocalAwareLinker;", "", "tryReferencingPropertyByLocalSignature", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "idSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "tryReferencingSimpleFunctionByLocalSignature", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FileLocalAwareLinker {
    IrPropertySymbol tryReferencingPropertyByLocalSignature(IrDeclaration parent, IdSignature idSignature);

    IrSimpleFunctionSymbol tryReferencingSimpleFunctionByLocalSignature(IrDeclaration parent, IdSignature idSignature);
}
