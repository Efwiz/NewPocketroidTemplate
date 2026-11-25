package org.jetbrains.kotlin.psi2ir.generators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTable;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: DataClassMembersGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\"\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r¢\u0006\u0002\u0010\u000fJH\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u001b2!\u0010\u001d\u001a\u001d\u0012\b\u0012\u00060\u001fR\u00020\u0001\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00170\u001e¢\u0006\u0002\b!H\u0082\bJ \u0010\"\u001a\u00020 2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u0019H&J\u0016\u0010$\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010%\u001a\u00020\u0015J\u0016\u0010&\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010'\u001a\u00020(J\u001c\u0010)\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00140+J\u001c\u0010,\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00140+J\u001c\u0010-\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00140+J\u000e\u0010.\u001a\u00020\u00152\u0006\u0010/\u001a\u00020\u0014J\u0010\u00100\u001a\u00020\u00152\u0006\u00101\u001a\u000202H&J\f\u00103\u001a\u00020\r*\u000204H\u0014J \u00105\u001a\u00020\u0017*\u00060\u001fR\u00020\u00012\u0006\u00101\u001a\u0002022\u0006\u00106\u001a\u000207H\u0002R\u0011\u0010\u000e\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/DescriptorBasedDataClassMembersGenerator;", "Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator;", "context", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "forbidDirectFieldAccess", "", "generateBodies", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;ZZ)V", "getGenerateBodies", "()Z", "irPropertiesByDescriptor", "", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "buildMember", "", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "startOffset", "", "endOffset", "body", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator$MemberFunctionBuilder;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lkotlin/ExtensionFunctionType;", "declareSimpleFunction", "functionDescriptor", "generateComponentFunction", "irProperty", "generateCopyFunction", "constructorSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "generateEqualsMethod", "properties", "", "generateHashCodeMethod", "generateToStringMethod", "getIrProperty", "property", "getProperty", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "hasDispatchReceiver", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "putDefault", "value", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
abstract class DescriptorBasedDataClassMembersGenerator extends org.jetbrains.kotlin.ir.util.DataClassMembersGenerator {
    private final boolean generateBodies;
    private final Map<PropertyDescriptor, IrProperty> irPropertiesByDescriptor;

    public abstract IrFunction declareSimpleFunction(int startOffset, int endOffset, FunctionDescriptor functionDescriptor);

    public abstract IrProperty getProperty(ValueParameterDescriptor parameter);

    public final boolean getGenerateBodies() {
        return this.generateBodies;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DescriptorBasedDataClassMembersGenerator(IrGeneratorContext context, ReferenceSymbolTable symbolTable, IrClass irClass, FqName fqName, IrDeclarationOrigin origin, boolean z, boolean z2) {
        super(context, symbolTable, irClass, fqName, origin, z);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(origin, "origin");
        this.generateBodies = z2;
        Sequence<IrProperty> properties = IrUtilsKt.getProperties(irClass);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (IrProperty irProperty : properties) {
            linkedHashMap.put(irProperty.getDescriptor(), irProperty);
        }
        this.irPropertiesByDescriptor = linkedHashMap;
    }

    public final IrProperty getIrProperty(PropertyDescriptor property) {
        Intrinsics.checkNotNullParameter(property, "property");
        IrProperty irProperty = this.irPropertiesByDescriptor.get(property);
        if (irProperty != null) {
            return irProperty;
        }
        throw new IllegalStateException(("Class: " + getIrClass().getDescriptor() + ": unexpected property descriptor: " + property).toString());
    }

    private final void putDefault(org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.MemberFunctionBuilder memberFunctionBuilder, ValueParameterDescriptor valueParameterDescriptor, IrExpression irExpression) {
        IrDeclarationsKt.putDefault(memberFunctionBuilder.getIrFunction(), valueParameterDescriptor, ExpressionHelpersKt.irExprBody(memberFunctionBuilder, irExpression));
    }

    @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator
    protected boolean hasDispatchReceiver(IrSimpleFunctionSymbol irSimpleFunctionSymbol) {
        Intrinsics.checkNotNullParameter(irSimpleFunctionSymbol, "<this>");
        return irSimpleFunctionSymbol.getDescriptor().mo6753getDispatchReceiverParameter() != null;
    }

    public final void generateEqualsMethod(FunctionDescriptor function, List<? extends PropertyDescriptor> properties) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(properties, "properties");
        DescriptorBasedDataClassMembersGenerator descriptorBasedDataClassMembersGenerator = this;
        org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.MemberFunctionBuilder memberFunctionBuilder = new org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.MemberFunctionBuilder(descriptorBasedDataClassMembersGenerator, -2, -2, declareSimpleFunction(-2, -2, function));
        org.jetbrains.kotlin.ir.util.DataClassMembersGenerator dataClassMembersGenerator = memberFunctionBuilder.this$0;
        IrFunction irFunction = memberFunctionBuilder.getIrFunction();
        ReferenceSymbolTable symbolTable = dataClassMembersGenerator.getSymbolTable();
        symbolTable.enterScope(irFunction);
        IrFunction irFunction2 = memberFunctionBuilder.getIrFunction();
        IrFunction irFunction3 = irFunction2;
        ReferenceSymbolTable symbolTable2 = descriptorBasedDataClassMembersGenerator.getSymbolTable();
        symbolTable2.enterScope(irFunction3);
        irFunction2.setParent(getIrClass());
        generateSyntheticFunctionParameterDeclarations(irFunction2);
        List<? extends PropertyDescriptor> list = properties;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(getIrProperty((PropertyDescriptor) it.next()));
        }
        memberFunctionBuilder.generateEqualsMethodBody(arrayList);
        Unit unit = Unit.INSTANCE;
        symbolTable2.leaveScope(irFunction3);
        memberFunctionBuilder.getIrFunction().setBody(memberFunctionBuilder.getIrBlockBody());
        Unit unit2 = Unit.INSTANCE;
        symbolTable.leaveScope(irFunction);
        memberFunctionBuilder.this$0.getIrClass().getDeclarations().add(memberFunctionBuilder.getIrFunction());
        memberFunctionBuilder.getIrFunction();
    }

    public final void generateComponentFunction(FunctionDescriptor function, IrProperty irProperty) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(irProperty, "irProperty");
        DescriptorBasedDataClassMembersGenerator descriptorBasedDataClassMembersGenerator = this;
        org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.MemberFunctionBuilder memberFunctionBuilder = new org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.MemberFunctionBuilder(descriptorBasedDataClassMembersGenerator, -2, -2, declareSimpleFunction(-2, -2, function));
        org.jetbrains.kotlin.ir.util.DataClassMembersGenerator dataClassMembersGenerator = memberFunctionBuilder.this$0;
        IrFunction irFunction = memberFunctionBuilder.getIrFunction();
        ReferenceSymbolTable symbolTable = dataClassMembersGenerator.getSymbolTable();
        symbolTable.enterScope(irFunction);
        IrFunction irFunction2 = memberFunctionBuilder.getIrFunction();
        IrFunction irFunction3 = irFunction2;
        ReferenceSymbolTable symbolTable2 = descriptorBasedDataClassMembersGenerator.getSymbolTable();
        symbolTable2.enterScope(irFunction3);
        irFunction2.setParent(getIrClass());
        generateSyntheticFunctionParameterDeclarations(irFunction2);
        memberFunctionBuilder.generateComponentFunction(irProperty);
        Unit unit = Unit.INSTANCE;
        symbolTable2.leaveScope(irFunction3);
        memberFunctionBuilder.getIrFunction().setBody(memberFunctionBuilder.getIrBlockBody());
        Unit unit2 = Unit.INSTANCE;
        symbolTable.leaveScope(irFunction);
        memberFunctionBuilder.this$0.getIrClass().getDeclarations().add(memberFunctionBuilder.getIrFunction());
        memberFunctionBuilder.getIrFunction();
    }

    public final void generateCopyFunction(FunctionDescriptor function, IrConstructorSymbol constructorSymbol) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(constructorSymbol, "constructorSymbol");
        DescriptorBasedDataClassMembersGenerator descriptorBasedDataClassMembersGenerator = this;
        org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.MemberFunctionBuilder memberFunctionBuilder = new org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.MemberFunctionBuilder(descriptorBasedDataClassMembersGenerator, -2, -2, declareSimpleFunction(-2, -2, function));
        org.jetbrains.kotlin.ir.util.DataClassMembersGenerator dataClassMembersGenerator = memberFunctionBuilder.this$0;
        IrFunction irFunction = memberFunctionBuilder.getIrFunction();
        ReferenceSymbolTable symbolTable = dataClassMembersGenerator.getSymbolTable();
        symbolTable.enterScope(irFunction);
        IrFunction irFunction2 = memberFunctionBuilder.getIrFunction();
        IrFunction irFunction3 = irFunction2;
        ReferenceSymbolTable symbolTable2 = descriptorBasedDataClassMembersGenerator.getSymbolTable();
        symbolTable2.enterScope(irFunction3);
        irFunction2.setParent(getIrClass());
        generateSyntheticFunctionParameterDeclarations(irFunction2);
        if (this.generateBodies) {
            List<ValueParameterDescriptor> valueParameters = function.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            for (ValueParameterDescriptor valueParameterDescriptor : valueParameters) {
                Intrinsics.checkNotNull(valueParameterDescriptor);
                putDefault(memberFunctionBuilder, valueParameterDescriptor, memberFunctionBuilder.irGetProperty(memberFunctionBuilder.irThis(), getProperty(valueParameterDescriptor)));
            }
            memberFunctionBuilder.generateCopyFunction(constructorSymbol);
        }
        Unit unit = Unit.INSTANCE;
        symbolTable2.leaveScope(irFunction3);
        memberFunctionBuilder.getIrFunction().setBody(memberFunctionBuilder.getIrBlockBody());
        Unit unit2 = Unit.INSTANCE;
        symbolTable.leaveScope(irFunction);
        memberFunctionBuilder.this$0.getIrClass().getDeclarations().add(memberFunctionBuilder.getIrFunction());
        memberFunctionBuilder.getIrFunction();
    }

    public final void generateHashCodeMethod(FunctionDescriptor function, List<? extends PropertyDescriptor> properties) {
        FqName fqName;
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(properties, "properties");
        DescriptorBasedDataClassMembersGenerator descriptorBasedDataClassMembersGenerator = this;
        org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.MemberFunctionBuilder memberFunctionBuilder = new org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.MemberFunctionBuilder(descriptorBasedDataClassMembersGenerator, -2, -2, declareSimpleFunction(-2, -2, function));
        org.jetbrains.kotlin.ir.util.DataClassMembersGenerator dataClassMembersGenerator = memberFunctionBuilder.this$0;
        IrFunction irFunction = memberFunctionBuilder.getIrFunction();
        ReferenceSymbolTable symbolTable = dataClassMembersGenerator.getSymbolTable();
        symbolTable.enterScope(irFunction);
        IrFunction irFunction2 = memberFunctionBuilder.getIrFunction();
        IrFunction irFunction3 = irFunction2;
        ReferenceSymbolTable symbolTable2 = descriptorBasedDataClassMembersGenerator.getSymbolTable();
        symbolTable2.enterScope(irFunction3);
        irFunction2.setParent(getIrClass());
        generateSyntheticFunctionParameterDeclarations(irFunction2);
        List<? extends PropertyDescriptor> list = properties;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(getIrProperty((PropertyDescriptor) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        int iHashCode = 0;
        if (getIrClass().getKind() == ClassKind.OBJECT && getIrClass().getIsData() && (fqName = getFqName()) != null) {
            iHashCode = fqName.hashCode();
        }
        memberFunctionBuilder.generateHashCodeMethodBody(arrayList2, iHashCode);
        Unit unit = Unit.INSTANCE;
        symbolTable2.leaveScope(irFunction3);
        memberFunctionBuilder.getIrFunction().setBody(memberFunctionBuilder.getIrBlockBody());
        Unit unit2 = Unit.INSTANCE;
        symbolTable.leaveScope(irFunction);
        memberFunctionBuilder.this$0.getIrClass().getDeclarations().add(memberFunctionBuilder.getIrFunction());
        memberFunctionBuilder.getIrFunction();
    }

    public final void generateToStringMethod(FunctionDescriptor function, List<? extends PropertyDescriptor> properties) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(properties, "properties");
        DescriptorBasedDataClassMembersGenerator descriptorBasedDataClassMembersGenerator = this;
        org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.MemberFunctionBuilder memberFunctionBuilder = new org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.MemberFunctionBuilder(descriptorBasedDataClassMembersGenerator, -2, -2, declareSimpleFunction(-2, -2, function));
        org.jetbrains.kotlin.ir.util.DataClassMembersGenerator dataClassMembersGenerator = memberFunctionBuilder.this$0;
        IrFunction irFunction = memberFunctionBuilder.getIrFunction();
        ReferenceSymbolTable symbolTable = dataClassMembersGenerator.getSymbolTable();
        symbolTable.enterScope(irFunction);
        IrFunction irFunction2 = memberFunctionBuilder.getIrFunction();
        IrFunction irFunction3 = irFunction2;
        ReferenceSymbolTable symbolTable2 = descriptorBasedDataClassMembersGenerator.getSymbolTable();
        symbolTable2.enterScope(irFunction3);
        irFunction2.setParent(getIrClass());
        generateSyntheticFunctionParameterDeclarations(irFunction2);
        List<? extends PropertyDescriptor> list = properties;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(getIrProperty((PropertyDescriptor) it.next()));
        }
        memberFunctionBuilder.generateToStringMethodBody(arrayList);
        Unit unit = Unit.INSTANCE;
        symbolTable2.leaveScope(irFunction3);
        memberFunctionBuilder.getIrFunction().setBody(memberFunctionBuilder.getIrBlockBody());
        Unit unit2 = Unit.INSTANCE;
        symbolTable.leaveScope(irFunction);
        memberFunctionBuilder.this$0.getIrClass().getDeclarations().add(memberFunctionBuilder.getIrFunction());
        memberFunctionBuilder.getIrFunction();
    }
}
