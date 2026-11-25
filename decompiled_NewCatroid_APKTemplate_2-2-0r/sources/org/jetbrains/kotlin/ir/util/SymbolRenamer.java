package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrExternalPackageFragmentSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: DeepCopyIrTreeWithSymbols.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0019J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0018H\u0016¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/SymbolRenamer;", "", "getClassName", "Lorg/jetbrains/kotlin/name/Name;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getEnumEntryName", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "getExternalPackageFragmentName", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/ir/symbols/IrExternalPackageFragmentSymbol;", "getFieldName", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getFileName", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "getFunctionName", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getTypeAliasName", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "getTypeParameterName", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "getValueParameterName", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "getVariableName", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "DEFAULT", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SymbolRenamer {
    Name getClassName(IrClassSymbol symbol);

    Name getEnumEntryName(IrEnumEntrySymbol symbol);

    FqName getExternalPackageFragmentName(IrExternalPackageFragmentSymbol symbol);

    Name getFieldName(IrFieldSymbol symbol);

    FqName getFileName(IrFileSymbol symbol);

    Name getFunctionName(IrSimpleFunctionSymbol symbol);

    Name getTypeAliasName(IrTypeAliasSymbol symbol);

    Name getTypeParameterName(IrTypeParameterSymbol symbol);

    Name getValueParameterName(IrValueParameterSymbol symbol);

    Name getVariableName(IrVariableSymbol symbol);

    /* JADX INFO: compiled from: DeepCopyIrTreeWithSymbols.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static Name getClassName(SymbolRenamer symbolRenamer, IrClassSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol.getOwner().getName();
        }

        public static Name getFunctionName(SymbolRenamer symbolRenamer, IrSimpleFunctionSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol.getOwner().getName();
        }

        public static Name getFieldName(SymbolRenamer symbolRenamer, IrFieldSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol.getOwner().getName();
        }

        public static FqName getFileName(SymbolRenamer symbolRenamer, IrFileSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol.getOwner().getPackageFqName();
        }

        public static FqName getExternalPackageFragmentName(SymbolRenamer symbolRenamer, IrExternalPackageFragmentSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol.getOwner().getPackageFqName();
        }

        public static Name getEnumEntryName(SymbolRenamer symbolRenamer, IrEnumEntrySymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol.getOwner().getName();
        }

        public static Name getVariableName(SymbolRenamer symbolRenamer, IrVariableSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol.getOwner().getName();
        }

        public static Name getTypeParameterName(SymbolRenamer symbolRenamer, IrTypeParameterSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol.getOwner().getName();
        }

        public static Name getValueParameterName(SymbolRenamer symbolRenamer, IrValueParameterSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol.getOwner().getName();
        }

        public static Name getTypeAliasName(SymbolRenamer symbolRenamer, IrTypeAliasSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol.getOwner().getName();
        }
    }

    /* JADX INFO: compiled from: DeepCopyIrTreeWithSymbols.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/SymbolRenamer$DEFAULT;", "Lorg/jetbrains/kotlin/ir/util/SymbolRenamer;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DEFAULT implements SymbolRenamer {
        public static final DEFAULT INSTANCE = new DEFAULT();

        private DEFAULT() {
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRenamer
        public Name getClassName(IrClassSymbol irClassSymbol) {
            return DefaultImpls.getClassName(this, irClassSymbol);
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRenamer
        public Name getEnumEntryName(IrEnumEntrySymbol irEnumEntrySymbol) {
            return DefaultImpls.getEnumEntryName(this, irEnumEntrySymbol);
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRenamer
        public FqName getExternalPackageFragmentName(IrExternalPackageFragmentSymbol irExternalPackageFragmentSymbol) {
            return DefaultImpls.getExternalPackageFragmentName(this, irExternalPackageFragmentSymbol);
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRenamer
        public Name getFieldName(IrFieldSymbol irFieldSymbol) {
            return DefaultImpls.getFieldName(this, irFieldSymbol);
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRenamer
        public FqName getFileName(IrFileSymbol irFileSymbol) {
            return DefaultImpls.getFileName(this, irFileSymbol);
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRenamer
        public Name getFunctionName(IrSimpleFunctionSymbol irSimpleFunctionSymbol) {
            return DefaultImpls.getFunctionName(this, irSimpleFunctionSymbol);
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRenamer
        public Name getTypeAliasName(IrTypeAliasSymbol irTypeAliasSymbol) {
            return DefaultImpls.getTypeAliasName(this, irTypeAliasSymbol);
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRenamer
        public Name getTypeParameterName(IrTypeParameterSymbol irTypeParameterSymbol) {
            return DefaultImpls.getTypeParameterName(this, irTypeParameterSymbol);
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRenamer
        public Name getValueParameterName(IrValueParameterSymbol irValueParameterSymbol) {
            return DefaultImpls.getValueParameterName(this, irValueParameterSymbol);
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRenamer
        public Name getVariableName(IrVariableSymbol irVariableSymbol) {
            return DefaultImpls.getVariableName(this, irVariableSymbol);
        }
    }
}
