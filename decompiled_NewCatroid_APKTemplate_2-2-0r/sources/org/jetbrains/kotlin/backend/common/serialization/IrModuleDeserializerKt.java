package org.jetbrains.kotlin.backend.common.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrModuleDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\n\u0010\u0007\u001a\u00020\u0006*\u00020\u0001¨\u0006\b"}, d2 = {"deserializeIrSymbolOrFail", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "idSig", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "symbolKind", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", Namer.METADATA_CLASS_KIND, "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrModuleDeserializerKt {
    public static final BinarySymbolData.SymbolKind kind(IrSymbol irSymbol) {
        Intrinsics.checkNotNullParameter(irSymbol, "<this>");
        if (irSymbol instanceof IrClassSymbol) {
            return BinarySymbolData.SymbolKind.CLASS_SYMBOL;
        }
        if (irSymbol instanceof IrConstructorSymbol) {
            return BinarySymbolData.SymbolKind.CONSTRUCTOR_SYMBOL;
        }
        if (irSymbol instanceof IrSimpleFunctionSymbol) {
            return BinarySymbolData.SymbolKind.FUNCTION_SYMBOL;
        }
        if (irSymbol instanceof IrPropertySymbol) {
            return BinarySymbolData.SymbolKind.PROPERTY_SYMBOL;
        }
        if (irSymbol instanceof IrFieldSymbol) {
            return BinarySymbolData.SymbolKind.FIELD_SYMBOL;
        }
        if (irSymbol instanceof IrEnumEntrySymbol) {
            return BinarySymbolData.SymbolKind.ENUM_ENTRY_SYMBOL;
        }
        if (irSymbol instanceof IrTypeAliasSymbol) {
            return BinarySymbolData.SymbolKind.TYPEALIAS_SYMBOL;
        }
        if (irSymbol instanceof IrTypeParameterSymbol) {
            return BinarySymbolData.SymbolKind.TYPE_PARAMETER_SYMBOL;
        }
        throw new IllegalStateException(("Unexpected symbol kind " + irSymbol).toString());
    }

    public static final IrSymbol deserializeIrSymbolOrFail(IrModuleDeserializer irModuleDeserializer, IdSignature idSig, BinarySymbolData.SymbolKind symbolKind) {
        Intrinsics.checkNotNullParameter(irModuleDeserializer, "<this>");
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        Intrinsics.checkNotNullParameter(symbolKind, "symbolKind");
        IrSymbol irSymbolMo6122tryDeserializeIrSymbol = irModuleDeserializer.mo6122tryDeserializeIrSymbol(idSig, symbolKind);
        if (irSymbolMo6122tryDeserializeIrSymbol != null) {
            return irSymbolMo6122tryDeserializeIrSymbol;
        }
        irModuleDeserializer.deserializedSymbolNotFound(idSig);
        throw null;
    }
}
