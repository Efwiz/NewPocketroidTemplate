package org.jetbrains.kotlin.ir.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: DataClassMembersGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJH\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00142!\u0010\u0016\u001a\u001d\u0012\b\u0012\u00060\u0018R\u00020\u0001\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00100\u0017¢\u0006\u0002\b\u0019H\u0082\bJ\u0016\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u001fJ\u001c\u0010 \u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001c0\"J\u001c\u0010#\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001c0\"J\u001c\u0010$\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001c0\"J\u0012\u0010%\u001a\u00020\u001c2\b\u0010&\u001a\u0004\u0018\u00010'H&J\f\u0010(\u001a\u00020\r*\u00020)H\u0014J\u0012\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\"*\u00020\u001fH\u0014¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IrBasedDataClassMembersGenerator;", "Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator;", "context", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "forbidDirectFieldAccess", "", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Z)V", "buildMember", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "startOffset", "", "endOffset", "body", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator$MemberFunctionBuilder;", "Lkotlin/ExtensionFunctionType;", "generateComponentFunction", "irProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "generateCopyFunction", "constructorSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "generateEqualsMethod", "properties", "", "generateHashCodeMethod", "generateToStringMethod", "getProperty", "irValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "hasDispatchReceiver", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "typesOfTypeParameters", "Lorg/jetbrains/kotlin/ir/types/IrType;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrBasedDataClassMembersGenerator extends DataClassMembersGenerator {
    public abstract IrProperty getProperty(IrValueParameter irValueParameter);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBasedDataClassMembersGenerator(IrGeneratorContext context, ReferenceSymbolTable symbolTable, IrClass irClass, FqName fqName, IrDeclarationOrigin origin, boolean z) {
        super(context, symbolTable, irClass, fqName, origin, z);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(origin, "origin");
    }

    @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator
    protected boolean hasDispatchReceiver(IrSimpleFunctionSymbol irSimpleFunctionSymbol) {
        Intrinsics.checkNotNullParameter(irSimpleFunctionSymbol, "<this>");
        return irSimpleFunctionSymbol.getOwner().getDispatchReceiverParameter() != null;
    }

    @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator
    protected List<IrType> typesOfTypeParameters(IrConstructorSymbol irConstructorSymbol) {
        Intrinsics.checkNotNullParameter(irConstructorSymbol, "<this>");
        List listPlus = CollectionsKt.plus((Collection) AdditionalIrUtilsKt.getConstructedClass(irConstructorSymbol.getOwner()).getTypeParameters(), (Iterable) irConstructorSymbol.getOwner().getTypeParameters());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPlus, 10));
        Iterator it = listPlus.iterator();
        while (it.hasNext()) {
            arrayList.add(IrTypesKt.getDefaultType((IrTypeParameter) it.next()));
        }
        return arrayList;
    }

    public final void generateComponentFunction(IrFunction irFunction, IrProperty irProperty) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(irProperty, "irProperty");
        IrBasedDataClassMembersGenerator irBasedDataClassMembersGenerator = this;
        DataClassMembersGenerator.MemberFunctionBuilder memberFunctionBuilder = new DataClassMembersGenerator.MemberFunctionBuilder(irBasedDataClassMembersGenerator, -2, -2, irFunction);
        DataClassMembersGenerator dataClassMembersGenerator = memberFunctionBuilder.this$0;
        IrFunction irFunction2 = memberFunctionBuilder.getIrFunction();
        ReferenceSymbolTable symbolTable = dataClassMembersGenerator.getSymbolTable();
        symbolTable.enterScope(irFunction2);
        IrFunction irFunction3 = memberFunctionBuilder.getIrFunction();
        IrFunction irFunction4 = irFunction3;
        ReferenceSymbolTable symbolTable2 = irBasedDataClassMembersGenerator.getSymbolTable();
        symbolTable2.enterScope(irFunction4);
        generateSyntheticFunctionParameterDeclarations(irFunction3);
        memberFunctionBuilder.generateComponentFunction(irProperty);
        Unit unit = Unit.INSTANCE;
        symbolTable2.leaveScope(irFunction4);
        memberFunctionBuilder.getIrFunction().setBody(memberFunctionBuilder.getIrBlockBody());
        Unit unit2 = Unit.INSTANCE;
        symbolTable.leaveScope(irFunction2);
    }

    public final void generateCopyFunction(IrFunction irFunction, IrConstructorSymbol constructorSymbol) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(constructorSymbol, "constructorSymbol");
        IrBasedDataClassMembersGenerator irBasedDataClassMembersGenerator = this;
        DataClassMembersGenerator.MemberFunctionBuilder memberFunctionBuilder = new DataClassMembersGenerator.MemberFunctionBuilder(irBasedDataClassMembersGenerator, -2, -2, irFunction);
        DataClassMembersGenerator dataClassMembersGenerator = memberFunctionBuilder.this$0;
        IrFunction irFunction2 = memberFunctionBuilder.getIrFunction();
        ReferenceSymbolTable symbolTable = dataClassMembersGenerator.getSymbolTable();
        symbolTable.enterScope(irFunction2);
        IrFunction irFunction3 = memberFunctionBuilder.getIrFunction();
        IrFunction irFunction4 = irFunction3;
        ReferenceSymbolTable symbolTable2 = irBasedDataClassMembersGenerator.getSymbolTable();
        symbolTable2.enterScope(irFunction4);
        generateSyntheticFunctionParameterDeclarations(irFunction3);
        for (IrValueParameter irValueParameter : irFunction.getValueParameters()) {
            irValueParameter.setDefaultValue(ExpressionHelpersKt.irExprBody(memberFunctionBuilder, memberFunctionBuilder.irGetProperty(memberFunctionBuilder.irThis(), getProperty(irValueParameter))));
        }
        memberFunctionBuilder.generateCopyFunction(constructorSymbol);
        Unit unit = Unit.INSTANCE;
        symbolTable2.leaveScope(irFunction4);
        memberFunctionBuilder.getIrFunction().setBody(memberFunctionBuilder.getIrBlockBody());
        Unit unit2 = Unit.INSTANCE;
        symbolTable.leaveScope(irFunction2);
    }

    public final void generateEqualsMethod(IrFunction irFunction, List<? extends IrProperty> properties) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(properties, "properties");
        IrBasedDataClassMembersGenerator irBasedDataClassMembersGenerator = this;
        DataClassMembersGenerator.MemberFunctionBuilder memberFunctionBuilder = new DataClassMembersGenerator.MemberFunctionBuilder(irBasedDataClassMembersGenerator, -2, -2, irFunction);
        DataClassMembersGenerator dataClassMembersGenerator = memberFunctionBuilder.this$0;
        IrFunction irFunction2 = memberFunctionBuilder.getIrFunction();
        ReferenceSymbolTable symbolTable = dataClassMembersGenerator.getSymbolTable();
        symbolTable.enterScope(irFunction2);
        IrFunction irFunction3 = memberFunctionBuilder.getIrFunction();
        IrFunction irFunction4 = irFunction3;
        ReferenceSymbolTable symbolTable2 = irBasedDataClassMembersGenerator.getSymbolTable();
        symbolTable2.enterScope(irFunction4);
        generateSyntheticFunctionParameterDeclarations(irFunction3);
        memberFunctionBuilder.generateEqualsMethodBody(properties);
        Unit unit = Unit.INSTANCE;
        symbolTable2.leaveScope(irFunction4);
        memberFunctionBuilder.getIrFunction().setBody(memberFunctionBuilder.getIrBlockBody());
        Unit unit2 = Unit.INSTANCE;
        symbolTable.leaveScope(irFunction2);
    }

    public final void generateHashCodeMethod(IrFunction irFunction, List<? extends IrProperty> properties) {
        FqName fqName;
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(properties, "properties");
        IrBasedDataClassMembersGenerator irBasedDataClassMembersGenerator = this;
        DataClassMembersGenerator.MemberFunctionBuilder memberFunctionBuilder = new DataClassMembersGenerator.MemberFunctionBuilder(irBasedDataClassMembersGenerator, -2, -2, irFunction);
        DataClassMembersGenerator dataClassMembersGenerator = memberFunctionBuilder.this$0;
        IrFunction irFunction2 = memberFunctionBuilder.getIrFunction();
        ReferenceSymbolTable symbolTable = dataClassMembersGenerator.getSymbolTable();
        symbolTable.enterScope(irFunction2);
        IrFunction irFunction3 = memberFunctionBuilder.getIrFunction();
        IrFunction irFunction4 = irFunction3;
        ReferenceSymbolTable symbolTable2 = irBasedDataClassMembersGenerator.getSymbolTable();
        symbolTable2.enterScope(irFunction4);
        generateSyntheticFunctionParameterDeclarations(irFunction3);
        int iHashCode = 0;
        if (getIrClass().getKind() == ClassKind.OBJECT && getIrClass().getIsData() && (fqName = getFqName()) != null) {
            iHashCode = fqName.hashCode();
        }
        memberFunctionBuilder.generateHashCodeMethodBody(properties, iHashCode);
        Unit unit = Unit.INSTANCE;
        symbolTable2.leaveScope(irFunction4);
        memberFunctionBuilder.getIrFunction().setBody(memberFunctionBuilder.getIrBlockBody());
        Unit unit2 = Unit.INSTANCE;
        symbolTable.leaveScope(irFunction2);
    }

    public final void generateToStringMethod(IrFunction irFunction, List<? extends IrProperty> properties) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(properties, "properties");
        IrBasedDataClassMembersGenerator irBasedDataClassMembersGenerator = this;
        DataClassMembersGenerator.MemberFunctionBuilder memberFunctionBuilder = new DataClassMembersGenerator.MemberFunctionBuilder(irBasedDataClassMembersGenerator, -2, -2, irFunction);
        DataClassMembersGenerator dataClassMembersGenerator = memberFunctionBuilder.this$0;
        IrFunction irFunction2 = memberFunctionBuilder.getIrFunction();
        ReferenceSymbolTable symbolTable = dataClassMembersGenerator.getSymbolTable();
        symbolTable.enterScope(irFunction2);
        IrFunction irFunction3 = memberFunctionBuilder.getIrFunction();
        IrFunction irFunction4 = irFunction3;
        ReferenceSymbolTable symbolTable2 = irBasedDataClassMembersGenerator.getSymbolTable();
        symbolTable2.enterScope(irFunction4);
        generateSyntheticFunctionParameterDeclarations(irFunction3);
        memberFunctionBuilder.generateToStringMethodBody(properties);
        Unit unit = Unit.INSTANCE;
        symbolTable2.leaveScope(irFunction4);
        memberFunctionBuilder.getIrFunction().setBody(memberFunctionBuilder.getIrBlockBody());
        Unit unit2 = Unit.INSTANCE;
        symbolTable.leaveScope(irFunction2);
    }
}
