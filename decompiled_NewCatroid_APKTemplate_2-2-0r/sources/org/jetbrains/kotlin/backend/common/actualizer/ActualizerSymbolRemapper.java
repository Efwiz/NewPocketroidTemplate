package org.jetbrains.kotlin.backend.common.actualizer;

import androidx.exifinterface.media.ExifInterface;
import java.util.Map;
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
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.util.SymbolRemapper;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ExpectActualLinker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\b\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\b\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\b\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\b\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\"2\u0006\u0010\b\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0016J\u0014\u0010$\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0016J\u0010\u0010%\u001a\u00020&2\u0006\u0010\b\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\nH\u0016J\u0010\u0010(\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\fH\u0016J\u0010\u0010)\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\u0010H\u0016J\u0010\u0010*\u001a\u00020+2\u0006\u0010\b\u001a\u00020+H\u0016J\u0010\u0010,\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\u0016H\u0016J\u0010\u0010-\u001a\u00020\u00182\u0006\u0010\b\u001a\u00020\u0018H\u0016J\u0010\u0010.\u001a\u00020/2\u0006\u0010\b\u001a\u00020/H\u0016J\u0010\u00100\u001a\u00020\u001a2\u0006\u0010\b\u001a\u00020\u001aH\u0016J\u0010\u00101\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\u0014H\u0016J\u0010\u00102\u001a\u00020\u001c2\u0006\u0010\b\u001a\u00020\u001cH\u0016J\u0010\u00103\u001a\u0002042\u0006\u0010\b\u001a\u000204H\u0016J\u0010\u00105\u001a\u00020\"2\u0006\u0010\b\u001a\u00020\"H\u0016J\u001e\u00106\u001a\u0002H7\"\n\b\u0000\u00107\u0018\u0001*\u00020\u0004*\u0002H7H\u0082\b¢\u0006\u0002\u00108R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/ActualizerSymbolRemapper;", "Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "expectActualMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Ljava/util/Map;)V", "getDeclaredClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", NativeSymbol.TYPE_NAME, TypeProxy.SilentConstruction.Appender.GET_DECLARED_CONSTRUCTOR_METHOD_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getDeclaredEnumEntry", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "getDeclaredExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/symbols/IrExternalPackageFragmentSymbol;", "getDeclaredField", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getDeclaredFile", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "getDeclaredFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getDeclaredLocalDelegatedProperty", "Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", "getDeclaredProperty", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getDeclaredScript", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "getDeclaredTypeAlias", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "getDeclaredTypeParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "getDeclaredValueParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "getDeclaredVariable", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "getReferencedClass", "getReferencedClassOrNull", "getReferencedClassifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getReferencedConstructor", "getReferencedEnumEntry", "getReferencedField", "getReferencedFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getReferencedLocalDelegatedProperty", "getReferencedProperty", "getReferencedReturnableBlock", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnableBlockSymbol;", "getReferencedScript", "getReferencedSimpleFunction", "getReferencedTypeAlias", "getReferencedValue", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "getReferencedVariable", "actualizeSymbol", ExifInterface.LATITUDE_SOUTH, "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ActualizerSymbolRemapper implements SymbolRemapper {
    private final Map<IrSymbol, IrSymbol> expectActualMap;

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

    /* JADX WARN: Multi-variable type inference failed */
    public ActualizerSymbolRemapper(Map<IrSymbol, ? extends IrSymbol> expectActualMap) {
        Intrinsics.checkNotNullParameter(expectActualMap, "expectActualMap");
        this.expectActualMap = expectActualMap;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrClassSymbol getReferencedClass(IrClassSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrClassSymbol irClassSymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irClassSymbol);
        if (!(irSymbol instanceof IrClassSymbol)) {
            irSymbol = null;
        }
        IrClassSymbol irClassSymbol2 = (IrClassSymbol) irSymbol;
        if (irClassSymbol2 != null) {
            irClassSymbol = irClassSymbol2;
        }
        return irClassSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrScriptSymbol getReferencedScript(IrScriptSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrScriptSymbol irScriptSymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irScriptSymbol);
        if (!(irSymbol instanceof IrScriptSymbol)) {
            irSymbol = null;
        }
        IrScriptSymbol irScriptSymbol2 = (IrScriptSymbol) irSymbol;
        if (irScriptSymbol2 != null) {
            irScriptSymbol = irScriptSymbol2;
        }
        return irScriptSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrClassSymbol getReferencedClassOrNull(IrClassSymbol symbol) {
        if (symbol == null) {
            return null;
        }
        IrClassSymbol irClassSymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irClassSymbol);
        IrClassSymbol irClassSymbol2 = (IrClassSymbol) (irSymbol instanceof IrClassSymbol ? irSymbol : null);
        if (irClassSymbol2 != null) {
            irClassSymbol = irClassSymbol2;
        }
        return irClassSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrEnumEntrySymbol getReferencedEnumEntry(IrEnumEntrySymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrEnumEntrySymbol irEnumEntrySymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irEnumEntrySymbol);
        if (!(irSymbol instanceof IrEnumEntrySymbol)) {
            irSymbol = null;
        }
        IrEnumEntrySymbol irEnumEntrySymbol2 = (IrEnumEntrySymbol) irSymbol;
        if (irEnumEntrySymbol2 != null) {
            irEnumEntrySymbol = irEnumEntrySymbol2;
        }
        return irEnumEntrySymbol;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrVariableSymbol getReferencedVariable(IrVariableSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrVariableSymbol irVariableSymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irVariableSymbol);
        if (!(irSymbol instanceof IrVariableSymbol)) {
            irSymbol = null;
        }
        IrVariableSymbol irVariableSymbol2 = (IrVariableSymbol) irSymbol;
        if (irVariableSymbol2 != null) {
            irVariableSymbol = irVariableSymbol2;
        }
        return irVariableSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrLocalDelegatedPropertySymbol getReferencedLocalDelegatedProperty(IrLocalDelegatedPropertySymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrLocalDelegatedPropertySymbol irLocalDelegatedPropertySymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irLocalDelegatedPropertySymbol);
        if (!(irSymbol instanceof IrLocalDelegatedPropertySymbol)) {
            irSymbol = null;
        }
        IrLocalDelegatedPropertySymbol irLocalDelegatedPropertySymbol2 = (IrLocalDelegatedPropertySymbol) irSymbol;
        if (irLocalDelegatedPropertySymbol2 != null) {
            irLocalDelegatedPropertySymbol = irLocalDelegatedPropertySymbol2;
        }
        return irLocalDelegatedPropertySymbol;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrFieldSymbol getReferencedField(IrFieldSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrFieldSymbol irFieldSymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irFieldSymbol);
        if (!(irSymbol instanceof IrFieldSymbol)) {
            irSymbol = null;
        }
        IrFieldSymbol irFieldSymbol2 = (IrFieldSymbol) irSymbol;
        if (irFieldSymbol2 != null) {
            irFieldSymbol = irFieldSymbol2;
        }
        return irFieldSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrConstructorSymbol getReferencedConstructor(IrConstructorSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrConstructorSymbol irConstructorSymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irConstructorSymbol);
        if (!(irSymbol instanceof IrConstructorSymbol)) {
            irSymbol = null;
        }
        IrConstructorSymbol irConstructorSymbol2 = (IrConstructorSymbol) irSymbol;
        if (irConstructorSymbol2 != null) {
            irConstructorSymbol = irConstructorSymbol2;
        }
        return irConstructorSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrValueSymbol getReferencedValue(IrValueSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrValueSymbol irValueSymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irValueSymbol);
        if (!(irSymbol instanceof IrValueSymbol)) {
            irSymbol = null;
        }
        IrValueSymbol irValueSymbol2 = (IrValueSymbol) irSymbol;
        if (irValueSymbol2 != null) {
            irValueSymbol = irValueSymbol2;
        }
        return irValueSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrFunctionSymbol getReferencedFunction(IrFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrFunctionSymbol irFunctionSymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irFunctionSymbol);
        if (!(irSymbol instanceof IrFunctionSymbol)) {
            irSymbol = null;
        }
        IrFunctionSymbol irFunctionSymbol2 = (IrFunctionSymbol) irSymbol;
        if (irFunctionSymbol2 != null) {
            irFunctionSymbol = irFunctionSymbol2;
        }
        return irFunctionSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrPropertySymbol getReferencedProperty(IrPropertySymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrPropertySymbol irPropertySymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irPropertySymbol);
        if (!(irSymbol instanceof IrPropertySymbol)) {
            irSymbol = null;
        }
        IrPropertySymbol irPropertySymbol2 = (IrPropertySymbol) irSymbol;
        if (irPropertySymbol2 != null) {
            irPropertySymbol = irPropertySymbol2;
        }
        return irPropertySymbol;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrSimpleFunctionSymbol getReferencedSimpleFunction(IrSimpleFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irSimpleFunctionSymbol);
        if (!(irSymbol instanceof IrSimpleFunctionSymbol)) {
            irSymbol = null;
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = (IrSimpleFunctionSymbol) irSymbol;
        if (irSimpleFunctionSymbol2 != null) {
            irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
        }
        return irSimpleFunctionSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrReturnableBlockSymbol getReferencedReturnableBlock(IrReturnableBlockSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrReturnableBlockSymbol irReturnableBlockSymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irReturnableBlockSymbol);
        if (!(irSymbol instanceof IrReturnableBlockSymbol)) {
            irSymbol = null;
        }
        IrReturnableBlockSymbol irReturnableBlockSymbol2 = (IrReturnableBlockSymbol) irSymbol;
        if (irReturnableBlockSymbol2 != null) {
            irReturnableBlockSymbol = irReturnableBlockSymbol2;
        }
        return irReturnableBlockSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrClassifierSymbol getReferencedClassifier(IrClassifierSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrClassifierSymbol irClassifierSymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irClassifierSymbol);
        if (!(irSymbol instanceof IrClassifierSymbol)) {
            irSymbol = null;
        }
        IrClassifierSymbol irClassifierSymbol2 = (IrClassifierSymbol) irSymbol;
        if (irClassifierSymbol2 != null) {
            irClassifierSymbol = irClassifierSymbol2;
        }
        return irClassifierSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrTypeAliasSymbol getReferencedTypeAlias(IrTypeAliasSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrTypeAliasSymbol irTypeAliasSymbol = symbol;
        IrSymbol irSymbol = this.expectActualMap.get(irTypeAliasSymbol);
        if (!(irSymbol instanceof IrTypeAliasSymbol)) {
            irSymbol = null;
        }
        IrTypeAliasSymbol irTypeAliasSymbol2 = (IrTypeAliasSymbol) irSymbol;
        if (irTypeAliasSymbol2 != null) {
            irTypeAliasSymbol = irTypeAliasSymbol2;
        }
        return irTypeAliasSymbol;
    }
}
