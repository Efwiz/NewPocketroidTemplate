package org.jetbrains.kotlin.psi2ir.generators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.ReferenceSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: DataClassMembersGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000g\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001:\u0001 J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J$\u0010\f\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u000f0\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0013\u0010\u0019\u001a\t\u0018\u00010\u001a¢\u0006\u0002\b\u001b*\u00020\u001cH\u0002J\u0012\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00140\u001e*\u00020\u001fH\u0014¨\u0006!"}, d2 = {"org/jetbrains/kotlin/psi2ir/generators/DataClassMembersGenerator$MyDataClassMethodGenerator$irDataClassMembersGenerator$1", "Lorg/jetbrains/kotlin/psi2ir/generators/DescriptorBasedDataClassMembersGenerator;", "declareSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "startOffset", "", "endOffset", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "generateSyntheticFunctionParameterDeclarations", "", "irFunction", "getHashCodeFunction", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "symbolResolve", "Lkotlin/Function1;", "getHashCodeFunctionInfo", "Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator$HashCodeFunctionInfo;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "findHashCodeFunctionOrNull", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "Lkotlin/jvm/JvmWildcard;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "typesOfTypeParameters", "", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "Psi2IrHashCodeFunctionInfo", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataClassMembersGenerator$MyDataClassMethodGenerator$irDataClassMembersGenerator$1 extends DescriptorBasedDataClassMembersGenerator {
    final /* synthetic */ DataClassMembersGenerator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    DataClassMembersGenerator$MyDataClassMethodGenerator$irDataClassMembersGenerator$1(DataClassMembersGenerator dataClassMembersGenerator, GeneratorContext generatorContext, SymbolTable symbolTable, IrClass irClass, FqName fqName, IrDeclarationOrigin irDeclarationOrigin, boolean z) {
        super(generatorContext, symbolTable, irClass, fqName, irDeclarationOrigin, false, z);
        this.this$0 = dataClassMembersGenerator;
    }

    @Override // org.jetbrains.kotlin.psi2ir.generators.DescriptorBasedDataClassMembersGenerator
    public IrFunction declareSimpleFunction(int startOffset, int endOffset, FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        return this.this$0.declareSimpleFunction(startOffset, endOffset, getOrigin(), functionDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator
    public void generateSyntheticFunctionParameterDeclarations(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        new FunctionGenerator(this.this$0.getDeclarationGenerator()).generateSyntheticFunctionParameterDeclarations(irFunction);
    }

    @Override // org.jetbrains.kotlin.psi2ir.generators.DescriptorBasedDataClassMembersGenerator
    public IrProperty getProperty(ValueParameterDescriptor parameter) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        DataClassMembersGenerator dataClassMembersGenerator = this.this$0;
        WritableSlice<ValueParameterDescriptor, PropertyDescriptor> VALUE_PARAMETER_AS_PROPERTY = BindingContext.VALUE_PARAMETER_AS_PROPERTY;
        Intrinsics.checkNotNullExpressionValue(VALUE_PARAMETER_AS_PROPERTY, "VALUE_PARAMETER_AS_PROPERTY");
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) GeneratorKt.getOrFail(dataClassMembersGenerator, VALUE_PARAMETER_AS_PROPERTY, parameter);
        Intrinsics.checkNotNull(propertyDescriptor);
        return getIrProperty(propertyDescriptor);
    }

    private final SimpleFunctionDescriptor findHashCodeFunctionOrNull(MemberScope memberScope) {
        Object next;
        Name nameIdentifier = Name.identifier("hashCode");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        Iterator<T> it = memberScope.getContributedFunctions(nameIdentifier, NoLookupLocation.FROM_BACKEND).iterator();
        while (it.hasNext()) {
            next = it.next();
            SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) next;
            if (simpleFunctionDescriptor.getValueParameters().isEmpty() && simpleFunctionDescriptor.getExtensionReceiverParameter() == null) {
                return (SimpleFunctionDescriptor) next;
            }
        }
        next = null;
        return (SimpleFunctionDescriptor) next;
    }

    private final FunctionDescriptor getHashCodeFunction(KotlinType type) {
        SimpleFunctionDescriptor simpleFunctionDescriptorFindHashCodeFunctionOrNull = findHashCodeFunctionOrNull(type.getMemberScope());
        if (simpleFunctionDescriptorFindHashCodeFunctionOrNull != null) {
            return simpleFunctionDescriptorFindHashCodeFunctionOrNull;
        }
        MemberScope unsubstitutedMemberScope = getContext().getIrBuiltIns().getAnyClass().getDescriptor().getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
        SimpleFunctionDescriptor simpleFunctionDescriptorFindHashCodeFunctionOrNull2 = findHashCodeFunctionOrNull(unsubstitutedMemberScope);
        Intrinsics.checkNotNull(simpleFunctionDescriptorFindHashCodeFunctionOrNull2);
        return simpleFunctionDescriptorFindHashCodeFunctionOrNull2;
    }

    private final IrSimpleFunctionSymbol getHashCodeFunction(KotlinType type, Function1<? super FunctionDescriptor, ? extends IrSimpleFunctionSymbol> symbolResolve) {
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        if (this$0 instanceof ClassDescriptor) {
            if (KotlinBuiltIns.isArrayOrPrimitiveArray((ClassDescriptor) this$0)) {
                return getContext().getIrBuiltIns().getDataClassArrayMemberHashCodeSymbol();
            }
            return symbolResolve.invoke(getHashCodeFunction(type));
        }
        if (this$0 instanceof TypeParameterDescriptor) {
            return getHashCodeFunction(TypeUtilsKt.getRepresentativeUpperBound((TypeParameterDescriptor) this$0), symbolResolve);
        }
        throw new AssertionError("Unexpected type: " + type);
    }

    /* JADX INFO: compiled from: DataClassMembersGenerator.kt */
    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\u008a\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0014\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"org/jetbrains/kotlin/psi2ir/generators/DataClassMembersGenerator$MyDataClassMethodGenerator$irDataClassMembersGenerator$1.Psi2IrHashCodeFunctionInfo", "Lorg/jetbrains/kotlin/ir/util/DataClassMembersGenerator$HashCodeFunctionInfo;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "substituted", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/psi2ir/generators/DataClassMembersGenerator$MyDataClassMethodGenerator$irDataClassMembersGenerator$1;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)V", "getSubstituted", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "commitSubstituted", "", "irMemberAccessExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class Psi2IrHashCodeFunctionInfo implements org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.HashCodeFunctionInfo {
        private final CallableDescriptor substituted;
        private final IrSimpleFunctionSymbol symbol;
        final /* synthetic */ DataClassMembersGenerator$MyDataClassMethodGenerator$irDataClassMembersGenerator$1 this$0;

        public Psi2IrHashCodeFunctionInfo(DataClassMembersGenerator$MyDataClassMethodGenerator$irDataClassMembersGenerator$1 dataClassMembersGenerator$MyDataClassMethodGenerator$irDataClassMembersGenerator$1, IrSimpleFunctionSymbol symbol, CallableDescriptor substituted) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(substituted, "substituted");
            this.this$0 = dataClassMembersGenerator$MyDataClassMethodGenerator$irDataClassMembersGenerator$1;
            this.symbol = symbol;
            this.substituted = substituted;
        }

        @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.HashCodeFunctionInfo
        public IrSimpleFunctionSymbol getSymbol() {
            return this.symbol;
        }

        public final CallableDescriptor getSubstituted() {
            return this.substituted;
        }

        @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.HashCodeFunctionInfo
        public void commitSubstituted(IrMemberAccessExpression<?> irMemberAccessExpression) {
            Intrinsics.checkNotNullParameter(irMemberAccessExpression, "irMemberAccessExpression");
            this.this$0.this$0.commitSubstituted(irMemberAccessExpression, this.substituted);
        }
    }

    @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator
    public org.jetbrains.kotlin.ir.util.DataClassMembersGenerator.HashCodeFunctionInfo getHashCodeFunctionInfo(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        IrSimpleFunctionSymbol hashCodeFunction = getHashCodeFunction(IrTypesKt.toKotlinType(type), new Function1<FunctionDescriptor, IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.psi2ir.generators.DataClassMembersGenerator$MyDataClassMethodGenerator$irDataClassMembersGenerator$1$getHashCodeFunctionInfo$symbol$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunctionSymbol invoke(FunctionDescriptor hashCodeDescriptor) {
                Intrinsics.checkNotNullParameter(hashCodeDescriptor, "hashCodeDescriptor");
                objectRef.element = hashCodeDescriptor;
                ReferenceSymbolTableExtension<ClassDescriptor, TypeAliasDescriptor, ScriptDescriptor, FunctionDescriptor, ClassConstructorDescriptor, PropertyDescriptor, ParameterDescriptor, TypeParameterDescriptor> descriptorExtension = this.getSymbolTable().getDescriptorExtension();
                FunctionDescriptor original = hashCodeDescriptor.getOriginal();
                Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                return descriptorExtension.referenceSimpleFunction(original);
            }
        });
        FunctionDescriptor descriptor = (CallableDescriptor) objectRef.element;
        if (descriptor == null) {
            descriptor = hashCodeFunction.getDescriptor();
        }
        return new Psi2IrHashCodeFunctionInfo(this, hashCodeFunction, descriptor);
    }

    @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator
    protected List<IrType> typesOfTypeParameters(IrConstructorSymbol irConstructorSymbol) {
        Intrinsics.checkNotNullParameter(irConstructorSymbol, "<this>");
        List<TypeParameterDescriptor> typeParameters = irConstructorSymbol.getDescriptor().getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        DataClassMembersGenerator dataClassMembersGenerator = this.this$0;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            SimpleType defaultType = ((TypeParameterDescriptor) it.next()).getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            arrayList.add(dataClassMembersGenerator.toIrType(defaultType));
        }
        return arrayList;
    }
}
