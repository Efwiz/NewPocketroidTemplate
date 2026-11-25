package org.jetbrains.kotlin.backend.common.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrAnonymousInitializerSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrLocalDelegatedPropertySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;

/* JADX INFO: compiled from: IrSymbolDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0000¨\u0006\n"}, d2 = {"referenceDeserializedSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "fileSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "symbolKind", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "idSig", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrSymbolDeserializerKt {

    /* JADX INFO: compiled from: IrSymbolDeserializer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BinarySymbolData.SymbolKind.values().length];
            try {
                iArr[BinarySymbolData.SymbolKind.ANONYMOUS_INIT_SYMBOL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.CLASS_SYMBOL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.CONSTRUCTOR_SYMBOL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.TYPE_PARAMETER_SYMBOL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.ENUM_ENTRY_SYMBOL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.STANDALONE_FIELD_SYMBOL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.FIELD_SYMBOL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.FUNCTION_SYMBOL.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.TYPEALIAS_SYMBOL.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.PROPERTY_SYMBOL.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.VARIABLE_SYMBOL.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.VALUE_PARAMETER_SYMBOL.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.RECEIVER_PARAMETER_SYMBOL.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.LOCAL_DELEGATED_PROPERTY_SYMBOL.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.FILE_SYMBOL.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final IrSymbol referenceDeserializedSymbol(ReferenceSymbolTable symbolTable, IrFileSymbol irFileSymbol, BinarySymbolData.SymbolKind symbolKind, IdSignature idSig) {
        IrAnonymousInitializerSymbolImpl irAnonymousInitializerSymbolImpl;
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(symbolKind, "symbolKind");
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        switch (WhenMappings.$EnumSwitchMapping$0[symbolKind.ordinal()]) {
            case 1:
                irAnonymousInitializerSymbolImpl = new IrAnonymousInitializerSymbolImpl(null, 1, null);
                break;
            case 2:
                irAnonymousInitializerSymbolImpl = symbolTable.referenceClass(idSig);
                break;
            case 3:
                irAnonymousInitializerSymbolImpl = symbolTable.referenceConstructor(idSig);
                break;
            case 4:
                irAnonymousInitializerSymbolImpl = symbolTable.referenceTypeParameter(idSig);
                break;
            case 5:
                irAnonymousInitializerSymbolImpl = symbolTable.referenceEnumEntry(idSig);
                break;
            case 6:
                irAnonymousInitializerSymbolImpl = symbolTable.referenceField(idSig);
                break;
            case 7:
                irAnonymousInitializerSymbolImpl = symbolTable.referenceField(idSig);
                break;
            case 8:
                irAnonymousInitializerSymbolImpl = symbolTable.referenceSimpleFunction(idSig);
                break;
            case 9:
                irAnonymousInitializerSymbolImpl = symbolTable.referenceTypeAlias(idSig);
                break;
            case 10:
                irAnonymousInitializerSymbolImpl = symbolTable.referenceProperty(idSig);
                break;
            case 11:
                irAnonymousInitializerSymbolImpl = new IrVariableSymbolImpl(null, 1, null);
                break;
            case 12:
                irAnonymousInitializerSymbolImpl = new IrValueParameterSymbolImpl(null, 1, null);
                break;
            case 13:
                irAnonymousInitializerSymbolImpl = new IrValueParameterSymbolImpl(null, 1, null);
                break;
            case 14:
                irAnonymousInitializerSymbolImpl = new IrLocalDelegatedPropertySymbolImpl(null, 1, null);
                break;
            case 15:
                if (irFileSymbol == null) {
                    throw new IllegalStateException("File symbol is not provided".toString());
                }
                irAnonymousInitializerSymbolImpl = irFileSymbol;
                break;
                break;
            default:
                throw new IllegalStateException(("Unexpected classifier symbol kind: " + symbolKind + " for signature " + idSig).toString());
        }
        return irAnonymousInitializerSymbolImpl;
    }
}
