package org.jetbrains.kotlin.fir.backend.generators;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContextBase;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrBasedDataClassMembersGenerator;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.FqName;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: DataClassMembersGenerator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000E\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001:\u0001\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003H\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016R\u0015\u0010\u0002\u001a\u00020\u0003*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0017"}, d2 = {"org/jetbrains/kotlin/fir/backend/generators/DataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1", "Lorg/jetbrains/kotlin/ir/util/IrBasedDataClassMembersGenerator;", "erasedUpperBound", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getErasedUpperBound", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "generateSyntheticFunctionParameterDeclarations", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getHashCodeFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "klass", "getHashCodeFunctionInfo", "Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator$HashCodeFunctionInfo;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "getProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "irValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Fir2IrHashCodeFunctionInfo", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1 extends IrBasedDataClassMembersGenerator {
    @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator
    public void generateSyntheticFunctionParameterDeclarations(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
    }

    DataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1(IrGeneratorContextBase irGeneratorContextBase, SymbolTable symbolTable, IrClass irClass, FqName fqName, IrDeclarationOrigin irDeclarationOrigin) {
        super(irGeneratorContextBase, symbolTable, irClass, fqName, irDeclarationOrigin, false);
    }

    @Override // org.jetbrains.kotlin.ir.util.IrBasedDataClassMembersGenerator
    public IrProperty getProperty(IrValueParameter irValueParameter) {
        if (irValueParameter != null) {
            boolean z = false;
            IrProperty irProperty = null;
            for (IrProperty irProperty2 : IrUtilsKt.getProperties(getIrClass())) {
                IrProperty irProperty3 = irProperty2;
                if (Intrinsics.areEqual(irProperty3.getName(), irValueParameter.getName())) {
                    IrField backingField = irProperty3.getBackingField();
                    if (!Intrinsics.areEqual(backingField != null ? backingField.getType() : null, irValueParameter.getType())) {
                        continue;
                    } else {
                        if (z) {
                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                        }
                        z = true;
                        irProperty = irProperty2;
                    }
                }
            }
            if (!z) {
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
            IrProperty irProperty4 = irProperty;
            if (irProperty4 != null) {
                return irProperty4;
            }
        }
        throw new IllegalStateException(("Property for parameter " + irValueParameter).toString());
    }

    /* JADX INFO: compiled from: DataClassMembersGenerator.kt */
    @Metadata(d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\u008a\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"org/jetbrains/kotlin/fir/backend/generators/DataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1.Fir2IrHashCodeFunctionInfo", "Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator$HashCodeFunctionInfo;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "(Lorg/jetbrains/kotlin/fir/backend/generators/DataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;)V", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "commitSubstituted", "", "irMemberAccessExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class Fir2IrHashCodeFunctionInfo implements org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.HashCodeFunctionInfo {
        private final IrSimpleFunctionSymbol symbol;
        final /* synthetic */ DataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1 this$0;

        @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.HashCodeFunctionInfo
        public void commitSubstituted(IrMemberAccessExpression<?> irMemberAccessExpression) {
            Intrinsics.checkNotNullParameter(irMemberAccessExpression, "irMemberAccessExpression");
        }

        public Fir2IrHashCodeFunctionInfo(DataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1 dataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1, IrSimpleFunctionSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            this.this$0 = dataClassMembersGenerator$MyDataClassMethodsGenerator$irDataClassMembersGenerator$1;
            this.symbol = symbol;
        }

        @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.HashCodeFunctionInfo
        public IrSimpleFunctionSymbol getSymbol() {
            return this.symbol;
        }
    }

    private final IrSimpleFunctionSymbol getHashCodeFunction(IrClass klass) {
        IrSimpleFunctionSymbol symbol;
        Iterator<IrSimpleFunction> it = IrUtilsKt.getFunctions(klass).iterator();
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = null;
        boolean z = false;
        IrSimpleFunction irSimpleFunction = null;
        boolean z2 = false;
        while (true) {
            if (!it.hasNext()) {
                if (!z2) {
                    break;
                }
                break;
            }
            IrSimpleFunction next = it.next();
            IrSimpleFunction irSimpleFunction2 = next;
            if (Intrinsics.areEqual(irSimpleFunction2.getName().asString(), "hashCode") && irSimpleFunction2.getValueParameters().isEmpty() && irSimpleFunction2.getExtensionReceiverParameter() == null) {
                if (!z2) {
                    irSimpleFunction = next;
                    z2 = true;
                }
            }
            irSimpleFunction = null;
            break;
        }
        IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
        if (irSimpleFunction3 != null && (symbol = irSimpleFunction3.getSymbol()) != null) {
            return symbol;
        }
        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol2 : IrUtilsKt.getFunctions(getContext().getIrBuiltIns().getAnyClass())) {
            if (Intrinsics.areEqual(irSimpleFunctionSymbol2.getOwner().getName().asString(), "hashCode")) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
                z = true;
            }
        }
        if (!z) {
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        return irSimpleFunctionSymbol;
    }

    public final IrClass getErasedUpperBound(IrTypeParameter irTypeParameter) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(irTypeParameter, "<this>");
        Iterator<IrType> it = irTypeParameter.getSuperTypes().iterator();
        while (it.hasNext()) {
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(it.next());
            if (classOrNull != null && (owner = classOrNull.getOwner()) != null && !IrUtilsKt.isInterface(owner) && !IrUtilsKt.isAnnotationClass(owner)) {
                return owner;
            }
        }
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull((IrType) CollectionsKt.first((List) irTypeParameter.getSuperTypes()));
        IrSymbolOwner owner2 = classifierOrNull != null ? classifierOrNull.getOwner() : null;
        if (owner2 instanceof IrClass) {
            return (IrClass) owner2;
        }
        if (owner2 instanceof IrTypeParameter) {
            return getErasedUpperBound((IrTypeParameter) owner2);
        }
        throw new IllegalStateException(("unknown supertype kind " + owner2).toString());
    }

    @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator
    public org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.HashCodeFunctionInfo getHashCodeFunctionInfo(IrType type) {
        IrSimpleFunctionSymbol hashCodeFunction;
        Intrinsics.checkNotNullParameter(type, "type");
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(type);
        if (isArrayOrPrimitiveArray(classifierOrNull)) {
            hashCodeFunction = getContext().getIrBuiltIns().getDataClassArrayMemberHashCodeSymbol();
        } else if (classifierOrNull instanceof IrClassSymbol) {
            hashCodeFunction = getHashCodeFunction(((IrClassSymbol) classifierOrNull).getOwner());
        } else {
            if (!(classifierOrNull instanceof IrTypeParameterSymbol)) {
                throw new IllegalStateException(("Unknown classifier kind " + classifierOrNull).toString());
            }
            hashCodeFunction = getHashCodeFunction(getErasedUpperBound(((IrTypeParameterSymbol) classifierOrNull).getOwner()));
        }
        return new Fir2IrHashCodeFunctionInfo(this, hashCodeFunction);
    }
}
