package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrExternalPackageFragmentSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: SymbolRemapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u00012J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H&J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0004\u001a\u00020\u0006H&J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\nH&J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\fH&J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u000eH&J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0004\u001a\u00020\u0010H&J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0004\u001a\u00020\u0012H&J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0004\u001a\u00020\u0014H&J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u0016H&J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0004\u001a\u00020\u0018H&J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0004\u001a\u00020\u001aH&J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0004\u001a\u00020\u001cH&J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0004\u001a\u00020\u001eH&J\u0010\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H&J\u0014\u0010 \u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003H&J\u0010\u0010!\u001a\u00020\"2\u0006\u0010\u0004\u001a\u00020\"H&J\u0010\u0010#\u001a\u00020\u00062\u0006\u0010\u0004\u001a\u00020\u0006H&J\u0010\u0010$\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH&J\u0010\u0010%\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\fH&J\u0010\u0010&\u001a\u00020'2\u0006\u0010\u0004\u001a\u00020'H&J\u0010\u0010(\u001a\u00020\u00122\u0006\u0010\u0004\u001a\u00020\u0012H&J\u0010\u0010)\u001a\u00020\u00142\u0006\u0010\u0004\u001a\u00020\u0014H&J\u0010\u0010*\u001a\u00020+2\u0006\u0010\u0004\u001a\u00020+H&J\u0010\u0010,\u001a\u00020\u00162\u0006\u0010\u0004\u001a\u00020\u0016H&J\u0010\u0010-\u001a\u00020\u00102\u0006\u0010\u0004\u001a\u00020\u0010H&J\u0010\u0010.\u001a\u00020\u00182\u0006\u0010\u0004\u001a\u00020\u0018H&J\u0010\u0010/\u001a\u0002002\u0006\u0010\u0004\u001a\u000200H&J\u0010\u00101\u001a\u00020\u001e2\u0006\u0010\u0004\u001a\u00020\u001eH&¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "", "getDeclaredClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", NativeSymbol.TYPE_NAME, TypeProxy.SilentConstruction.Appender.GET_DECLARED_CONSTRUCTOR_METHOD_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getDeclaredEnumEntry", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "getDeclaredExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/symbols/IrExternalPackageFragmentSymbol;", "getDeclaredField", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getDeclaredFile", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "getDeclaredFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getDeclaredLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", "getDeclaredProperty", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getDeclaredScript", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "getDeclaredTypeAlias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "getDeclaredTypeParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "getDeclaredValueParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "getDeclaredVariable", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "getReferencedClass", "getReferencedClassOrNull", "getReferencedClassifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getReferencedConstructor", "getReferencedEnumEntry", "getReferencedField", "getReferencedFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getReferencedLocalDelegatedProperty", "getReferencedProperty", "getReferencedReturnableBlock", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnableBlockSymbol;", "getReferencedScript", "getReferencedSimpleFunction", "getReferencedTypeAlias", "getReferencedValue", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "getReferencedVariable", "Empty", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SymbolRemapper {

    /* JADX INFO: compiled from: SymbolRemapper.kt */
    @Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0005\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0005\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0005\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0005\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0005\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0005\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u0014\u0010!\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0016J\u0010\u0010\"\u001a\u00020#2\u0006\u0010\u0005\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020\u00072\u0006\u0010\u0005\u001a\u00020\u0007H\u0016J\u0010\u0010%\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\tH\u0016J\u0010\u0010&\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\rH\u0016J\u0010\u0010'\u001a\u00020(2\u0006\u0010\u0005\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020\u00132\u0006\u0010\u0005\u001a\u00020\u0013H\u0016J\u0010\u0010*\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0015H\u0016J\u0010\u0010+\u001a\u00020,2\u0006\u0010\u0005\u001a\u00020,H\u0016J\u0010\u0010-\u001a\u00020\u00172\u0006\u0010\u0005\u001a\u00020\u0017H\u0016J\u0010\u0010.\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u0011H\u0016J\u0010\u0010/\u001a\u00020\u00192\u0006\u0010\u0005\u001a\u00020\u0019H\u0016J\u0010\u00100\u001a\u0002012\u0006\u0010\u0005\u001a\u000201H\u0016J\u0010\u00102\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u001fH\u0016¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/SymbolRemapper$Empty;", "Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "()V", "getDeclaredClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", NativeSymbol.TYPE_NAME, TypeProxy.SilentConstruction.Appender.GET_DECLARED_CONSTRUCTOR_METHOD_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getDeclaredEnumEntry", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "getDeclaredExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/symbols/IrExternalPackageFragmentSymbol;", "getDeclaredField", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getDeclaredFile", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "getDeclaredFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getDeclaredLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", "getDeclaredProperty", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getDeclaredScript", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "getDeclaredTypeAlias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "getDeclaredTypeParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "getDeclaredValueParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "getDeclaredVariable", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "getReferencedClass", "getReferencedClassOrNull", "getReferencedClassifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getReferencedConstructor", "getReferencedEnumEntry", "getReferencedField", "getReferencedFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getReferencedLocalDelegatedProperty", "getReferencedProperty", "getReferencedReturnableBlock", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnableBlockSymbol;", "getReferencedScript", "getReferencedSimpleFunction", "getReferencedTypeAlias", "getReferencedValue", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "getReferencedVariable", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class Empty implements SymbolRemapper {
        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrClassSymbol getDeclaredClass(IrClassSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrConstructorSymbol getDeclaredConstructor(IrConstructorSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrEnumEntrySymbol getDeclaredEnumEntry(IrEnumEntrySymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrExternalPackageFragmentSymbol getDeclaredExternalPackageFragment(IrExternalPackageFragmentSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrFieldSymbol getDeclaredField(IrFieldSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrFileSymbol getDeclaredFile(IrFileSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrSimpleFunctionSymbol getDeclaredFunction(IrSimpleFunctionSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrLocalDelegatedPropertySymbol getDeclaredLocalDelegatedProperty(IrLocalDelegatedPropertySymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrPropertySymbol getDeclaredProperty(IrPropertySymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrScriptSymbol getDeclaredScript(IrScriptSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrTypeAliasSymbol getDeclaredTypeAlias(IrTypeAliasSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrTypeParameterSymbol getDeclaredTypeParameter(IrTypeParameterSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrValueParameterSymbol getDeclaredValueParameter(IrValueParameterSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrVariableSymbol getDeclaredVariable(IrVariableSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrClassSymbol getReferencedClass(IrClassSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrClassSymbol getReferencedClassOrNull(IrClassSymbol symbol) {
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrClassifierSymbol getReferencedClassifier(IrClassifierSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrConstructorSymbol getReferencedConstructor(IrConstructorSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrEnumEntrySymbol getReferencedEnumEntry(IrEnumEntrySymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrFieldSymbol getReferencedField(IrFieldSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrFunctionSymbol getReferencedFunction(IrFunctionSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrLocalDelegatedPropertySymbol getReferencedLocalDelegatedProperty(IrLocalDelegatedPropertySymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrPropertySymbol getReferencedProperty(IrPropertySymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrReturnableBlockSymbol getReferencedReturnableBlock(IrReturnableBlockSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrScriptSymbol getReferencedScript(IrScriptSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrSimpleFunctionSymbol getReferencedSimpleFunction(IrSimpleFunctionSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrTypeAliasSymbol getReferencedTypeAlias(IrTypeAliasSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrValueSymbol getReferencedValue(IrValueSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
        public IrVariableSymbol getReferencedVariable(IrVariableSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return symbol;
        }
    }

    IrClassSymbol getDeclaredClass(IrClassSymbol symbol);

    IrConstructorSymbol getDeclaredConstructor(IrConstructorSymbol symbol);

    IrEnumEntrySymbol getDeclaredEnumEntry(IrEnumEntrySymbol symbol);

    IrExternalPackageFragmentSymbol getDeclaredExternalPackageFragment(IrExternalPackageFragmentSymbol symbol);

    IrFieldSymbol getDeclaredField(IrFieldSymbol symbol);

    IrFileSymbol getDeclaredFile(IrFileSymbol symbol);

    IrSimpleFunctionSymbol getDeclaredFunction(IrSimpleFunctionSymbol symbol);

    IrLocalDelegatedPropertySymbol getDeclaredLocalDelegatedProperty(IrLocalDelegatedPropertySymbol symbol);

    IrPropertySymbol getDeclaredProperty(IrPropertySymbol symbol);

    IrScriptSymbol getDeclaredScript(IrScriptSymbol symbol);

    IrTypeAliasSymbol getDeclaredTypeAlias(IrTypeAliasSymbol symbol);

    IrTypeParameterSymbol getDeclaredTypeParameter(IrTypeParameterSymbol symbol);

    IrValueParameterSymbol getDeclaredValueParameter(IrValueParameterSymbol symbol);

    IrVariableSymbol getDeclaredVariable(IrVariableSymbol symbol);

    IrClassSymbol getReferencedClass(IrClassSymbol symbol);

    IrClassSymbol getReferencedClassOrNull(IrClassSymbol symbol);

    IrClassifierSymbol getReferencedClassifier(IrClassifierSymbol symbol);

    IrConstructorSymbol getReferencedConstructor(IrConstructorSymbol symbol);

    IrEnumEntrySymbol getReferencedEnumEntry(IrEnumEntrySymbol symbol);

    IrFieldSymbol getReferencedField(IrFieldSymbol symbol);

    IrFunctionSymbol getReferencedFunction(IrFunctionSymbol symbol);

    IrLocalDelegatedPropertySymbol getReferencedLocalDelegatedProperty(IrLocalDelegatedPropertySymbol symbol);

    IrPropertySymbol getReferencedProperty(IrPropertySymbol symbol);

    IrReturnableBlockSymbol getReferencedReturnableBlock(IrReturnableBlockSymbol symbol);

    IrScriptSymbol getReferencedScript(IrScriptSymbol symbol);

    IrSimpleFunctionSymbol getReferencedSimpleFunction(IrSimpleFunctionSymbol symbol);

    IrTypeAliasSymbol getReferencedTypeAlias(IrTypeAliasSymbol symbol);

    IrValueSymbol getReferencedValue(IrValueSymbol symbol);

    IrVariableSymbol getReferencedVariable(IrVariableSymbol symbol);
}
