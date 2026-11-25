package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.utils.TypeTranformerKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: TypeTransformer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0005\u001a\u00020\u0006*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"isBuiltInWasmRefType", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "isExternalType", "getRuntimeClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeTransformerKt {
    public static final boolean isBuiltInWasmRefType(IrType type) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(type, "type");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(type);
        return Intrinsics.areEqual((classOrNull == null || (owner = classOrNull.getOwner()) == null) ? null : AdditionalIrUtilsKt.getPackageFqName(owner), new FqName("kotlin.wasm.internal.reftypes"));
    }

    public static final boolean isExternalType(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        IrClass erasedUpperBound = TypeTranformerKt.getErasedUpperBound(type);
        if (erasedUpperBound != null) {
            return erasedUpperBound.getIsExternal();
        }
        return false;
    }

    public static final IrClass getRuntimeClass(IrType irType, IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        IrClass erasedUpperBound = TypeTranformerKt.getErasedUpperBound(irType);
        if (erasedUpperBound != null) {
            if (IrUtilsKt.isInterface(erasedUpperBound)) {
                erasedUpperBound = null;
            }
            if (erasedUpperBound != null) {
                return erasedUpperBound;
            }
        }
        return irBuiltIns.getAnyClass().getOwner();
    }
}
