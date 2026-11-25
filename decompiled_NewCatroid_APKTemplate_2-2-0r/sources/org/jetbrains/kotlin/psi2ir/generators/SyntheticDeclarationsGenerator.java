package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: SyntheticDeclarationsGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ð\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000 R2\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001:\u0001RB\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u001aH\u0002J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u001dH\u0002J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020 2\u0006\u0010\u0015\u001a\u00020!H\u0002J\u0018\u0010\"\u001a\u00020#2\u0006\u0010\u0013\u001a\u00020$2\u0006\u0010\u0015\u001a\u00020%H\u0002J\u0018\u0010&\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020#H\u0002J\u0018\u0010*\u001a\u00020+2\u0006\u0010\u0013\u001a\u00020,2\u0006\u0010\u0015\u001a\u00020-H\u0002J\u001a\u0010.\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u00100\u001a\u00020\u00022\u0006\u00101\u001a\u0002022\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u00103\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020 2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u00104\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u0002052\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u00106\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u0002072\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u00108\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u0002092\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010:\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020$2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010;\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020<2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010=\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020>2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010?\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020@2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010A\u001a\u00020\u00022\u0006\u0010B\u001a\u00020C2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010D\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020,2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010E\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020F2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010G\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020H2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010I\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020J2\b\u0010/\u001a\u0004\u0018\u00010\u0003H\u0016J\u0016\u0010K\u001a\u0004\u0018\u00010\u000b*\u00020\t2\u0006\u0010L\u001a\u00020\nH\u0002J#\u0010M\u001a\u0002HN\"\b\b\u0000\u0010N*\u00020O*\u0002HN2\u0006\u0010P\u001a\u00020\u0003H\u0002¢\u0006\u0002\u0010QR'\u0010\u0007\u001a\u001b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\b¢\u0006\u0002\b\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006S"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/SyntheticDeclarationsGenerator;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "(Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;)V", "defaultFactoryReference", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "Lkotlin/ExtensionFunctionType;", "generator", "Lorg/jetbrains/kotlin/psi2ir/generators/StandaloneDeclarationGenerator;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "createClassStub", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "createConstructorStub", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "createEnumEntruStub", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "createFunctionStub", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "createPropertyStub", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "declareAccessor", "accessorDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "property", "declareTypeAliasStub", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "visitClassDescriptor", "data", "visitConstructorDescriptor", "constructorDescriptor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "visitFunctionDescriptor", "visitModuleDeclaration", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "visitPackageFragmentDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "visitPackageViewDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "visitPropertyDescriptor", "visitPropertyGetterDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "visitPropertySetterDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "visitReceiverParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "visitScriptDescriptor", "scriptDescriptor", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "visitTypeAliasDescriptor", "visitTypeParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "visitValueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "visitVariableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "defaultArgumentFactory", "parameter", "insertDeclaration", "D", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declarationContainer", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Companion", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SyntheticDeclarationsGenerator implements DeclarationDescriptorVisitor<Unit, IrDeclarationContainer> {
    private final Function2<IrFunction, IrValueParameter, IrExpressionBody> defaultFactoryReference;
    private final StandaloneDeclarationGenerator generator;
    private final SymbolTable symbolTable;

    public SyntheticDeclarationsGenerator(GeneratorContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.generator = new StandaloneDeclarationGenerator(context);
        this.symbolTable = context.getSymbolTable();
        this.defaultFactoryReference = new Function2<IrFunction, IrValueParameter, IrExpressionBody>() { // from class: org.jetbrains.kotlin.psi2ir.generators.SyntheticDeclarationsGenerator$defaultFactoryReference$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final IrExpressionBody invoke(IrFunction irFunction, IrValueParameter it) {
                Intrinsics.checkNotNullParameter(irFunction, "$this$null");
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.defaultArgumentFactory(irFunction, it);
            }
        };
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitClassDescriptor(ClassDescriptor classDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitClassDescriptor2(classDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitConstructorDescriptor(ConstructorDescriptor constructorDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitConstructorDescriptor2(constructorDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitFunctionDescriptor(FunctionDescriptor functionDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitFunctionDescriptor2(functionDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitModuleDeclaration(ModuleDescriptor moduleDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitModuleDeclaration2(moduleDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitPackageFragmentDescriptor(PackageFragmentDescriptor packageFragmentDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitPackageFragmentDescriptor2(packageFragmentDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitPackageViewDescriptor(PackageViewDescriptor packageViewDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitPackageViewDescriptor2(packageViewDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitPropertyDescriptor(PropertyDescriptor propertyDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitPropertyDescriptor2(propertyDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitPropertyGetterDescriptor(PropertyGetterDescriptor propertyGetterDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitPropertyGetterDescriptor2(propertyGetterDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitPropertySetterDescriptor(PropertySetterDescriptor propertySetterDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitPropertySetterDescriptor2(propertySetterDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitReceiverParameterDescriptor(ReceiverParameterDescriptor receiverParameterDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitReceiverParameterDescriptor2(receiverParameterDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitScriptDescriptor(ScriptDescriptor scriptDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitScriptDescriptor2(scriptDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitTypeAliasDescriptor(TypeAliasDescriptor typeAliasDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitTypeAliasDescriptor2(typeAliasDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitTypeParameterDescriptor(TypeParameterDescriptor typeParameterDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitTypeParameterDescriptor2(typeParameterDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitValueParameterDescriptor(ValueParameterDescriptor valueParameterDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitValueParameterDescriptor2(valueParameterDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor
    public /* bridge */ /* synthetic */ Unit visitVariableDescriptor(VariableDescriptor variableDescriptor, IrDeclarationContainer irDeclarationContainer) {
        visitVariableDescriptor2(variableDescriptor, irDeclarationContainer);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <D extends IrDeclaration> D insertDeclaration(D d, IrDeclarationContainer irDeclarationContainer) {
        d.setParent(irDeclarationContainer);
        irDeclarationContainer.getDeclarations().add(d);
        return d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpressionBody defaultArgumentFactory(IrFunction irFunction, IrValueParameter irValueParameter) {
        ParameterDescriptor descriptor = irValueParameter.getDescriptor();
        Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ValueParameterDescriptor");
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) descriptor;
        if (!valueParameterDescriptor.declaresDefaultValue()) {
            return null;
        }
        return IrFactoryHelpersKt.createExpressionBody(irFunction.getFactory(), new IrErrorExpressionImpl(irFunction.getStartOffset(), irFunction.getEndOffset(), irValueParameter.getType(), "Default Argument Value stub for " + valueParameterDescriptor.getShortName() + '|' + valueParameterDescriptor.getIndex()));
    }

    /* JADX INFO: renamed from: visitPackageFragmentDescriptor, reason: avoid collision after fix types in other method */
    public void visitPackageFragmentDescriptor2(PackageFragmentDescriptor descriptor, IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        throw new IllegalStateException(("Unexpected declaration descriptor " + descriptor).toString());
    }

    /* JADX INFO: renamed from: visitPackageViewDescriptor, reason: avoid collision after fix types in other method */
    public void visitPackageViewDescriptor2(PackageViewDescriptor descriptor, IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        throw new IllegalStateException(("Unexpected declaration descriptor " + descriptor).toString());
    }

    /* JADX INFO: renamed from: visitVariableDescriptor, reason: avoid collision after fix types in other method */
    public void visitVariableDescriptor2(VariableDescriptor descriptor, IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        throw new IllegalStateException(("Unexpected declaration descriptor " + descriptor).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction createFunctionStub(FunctionDescriptor descriptor, IrSimpleFunctionSymbol symbol) {
        return this.generator.generateSimpleFunction(-2, -2, IrDeclarationOrigin.DEFINED.INSTANCE, descriptor, symbol, this.defaultFactoryReference);
    }

    /* JADX INFO: renamed from: visitFunctionDescriptor, reason: avoid collision after fix types in other method */
    public void visitFunctionDescriptor2(final FunctionDescriptor descriptor, final IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (data == null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (Intrinsics.areEqual(descriptor.getVisibility(), DescriptorVisibilities.INVISIBLE_FAKE) || descriptor.getKind() == CallableMemberDescriptor.Kind.DELEGATION) {
            return;
        }
        this.symbolTable.getDescriptorExtension().declareSimpleFunctionIfNotExists(descriptor, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.psi2ir.generators.SyntheticDeclarationsGenerator.visitFunctionDescriptor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrSimpleFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                SyntheticDeclarationsGenerator syntheticDeclarationsGenerator = SyntheticDeclarationsGenerator.this;
                return (IrSimpleFunction) syntheticDeclarationsGenerator.insertDeclaration(syntheticDeclarationsGenerator.createFunctionStub(descriptor, it), data);
            }
        });
    }

    /* JADX INFO: renamed from: visitTypeParameterDescriptor, reason: avoid collision after fix types in other method */
    public void visitTypeParameterDescriptor2(TypeParameterDescriptor descriptor, IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        throw new IllegalStateException(("Unexpected declaration descriptor " + descriptor).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClass createClassStub(ClassDescriptor descriptor, IrClassSymbol symbol) {
        DescriptorUtils.isEnumEntry(descriptor);
        return this.generator.generateClass(-2, -2, IrDeclarationOrigin.DEFINED.INSTANCE, descriptor, symbol);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrEnumEntry createEnumEntruStub(ClassDescriptor descriptor, IrEnumEntrySymbol symbol) {
        DescriptorUtils.isEnumEntry(descriptor);
        return this.generator.generateEnumEntry(-2, -2, IrDeclarationOrigin.DEFINED.INSTANCE, descriptor, symbol);
    }

    /* JADX INFO: renamed from: visitClassDescriptor, reason: avoid collision after fix types in other method */
    public void visitClassDescriptor2(final ClassDescriptor descriptor, final IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (data == null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (DescriptorUtils.isEnumEntry(descriptor)) {
            this.symbolTable.getDescriptorExtension().declareEnumEntryIfNotExists(descriptor, new Function1<IrEnumEntrySymbol, IrEnumEntry>() { // from class: org.jetbrains.kotlin.psi2ir.generators.SyntheticDeclarationsGenerator.visitClassDescriptor.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrEnumEntry invoke(IrEnumEntrySymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    SyntheticDeclarationsGenerator syntheticDeclarationsGenerator = SyntheticDeclarationsGenerator.this;
                    return (IrEnumEntry) syntheticDeclarationsGenerator.insertDeclaration(syntheticDeclarationsGenerator.createEnumEntruStub(descriptor, it), data);
                }
            });
        } else {
            this.symbolTable.getDescriptorExtension().declareClassIfNotExists(descriptor, new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.psi2ir.generators.SyntheticDeclarationsGenerator.visitClassDescriptor.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrClass invoke(IrClassSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    SyntheticDeclarationsGenerator syntheticDeclarationsGenerator = SyntheticDeclarationsGenerator.this;
                    return (IrClass) syntheticDeclarationsGenerator.insertDeclaration(syntheticDeclarationsGenerator.createClassStub(descriptor, it), data);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrTypeAlias declareTypeAliasStub(TypeAliasDescriptor descriptor, IrTypeAliasSymbol symbol) {
        return this.generator.generateTypeAlias(-2, -2, IrDeclarationOrigin.DEFINED.INSTANCE, descriptor, symbol);
    }

    /* JADX INFO: renamed from: visitTypeAliasDescriptor, reason: avoid collision after fix types in other method */
    public void visitTypeAliasDescriptor2(final TypeAliasDescriptor descriptor, final IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (data == null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        this.symbolTable.getDescriptorExtension().declareTypeAliasIfNotExists(descriptor, new Function1<IrTypeAliasSymbol, IrTypeAlias>() { // from class: org.jetbrains.kotlin.psi2ir.generators.SyntheticDeclarationsGenerator.visitTypeAliasDescriptor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrTypeAlias invoke(IrTypeAliasSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                SyntheticDeclarationsGenerator syntheticDeclarationsGenerator = SyntheticDeclarationsGenerator.this;
                return (IrTypeAlias) syntheticDeclarationsGenerator.insertDeclaration(syntheticDeclarationsGenerator.declareTypeAliasStub(descriptor, it), data);
            }
        });
    }

    /* JADX INFO: renamed from: visitModuleDeclaration, reason: avoid collision after fix types in other method */
    public void visitModuleDeclaration2(ModuleDescriptor descriptor, IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        throw new IllegalStateException(("Unreachable execution " + descriptor).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstructor createConstructorStub(ClassConstructorDescriptor descriptor, IrConstructorSymbol symbol) {
        return this.generator.generateConstructor(-2, -2, IrDeclarationOrigin.DEFINED.INSTANCE, descriptor, symbol, this.defaultFactoryReference);
    }

    /* JADX INFO: renamed from: visitConstructorDescriptor, reason: avoid collision after fix types in other method */
    public void visitConstructorDescriptor2(final ConstructorDescriptor constructorDescriptor, final IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(constructorDescriptor, "constructorDescriptor");
        if (data == null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        boolean z = constructorDescriptor instanceof ClassConstructorDescriptor;
        this.symbolTable.getDescriptorExtension().declareConstructorIfNotExists((ClassConstructorDescriptor) constructorDescriptor, new Function1<IrConstructorSymbol, IrConstructor>() { // from class: org.jetbrains.kotlin.psi2ir.generators.SyntheticDeclarationsGenerator.visitConstructorDescriptor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrConstructor invoke(IrConstructorSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                SyntheticDeclarationsGenerator syntheticDeclarationsGenerator = SyntheticDeclarationsGenerator.this;
                return (IrConstructor) syntheticDeclarationsGenerator.insertDeclaration(syntheticDeclarationsGenerator.createConstructorStub((ClassConstructorDescriptor) constructorDescriptor, it), data);
            }
        });
    }

    /* JADX INFO: renamed from: visitScriptDescriptor, reason: avoid collision after fix types in other method */
    public void visitScriptDescriptor2(ScriptDescriptor scriptDescriptor, IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(scriptDescriptor, "scriptDescriptor");
        this.symbolTable.getDescriptorExtension().referenceScript(scriptDescriptor).isBound();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrProperty createPropertyStub(PropertyDescriptor descriptor, IrPropertySymbol symbol) {
        return this.generator.generateProperty(-2, -2, IrDeclarationOrigin.DEFINED.INSTANCE, descriptor, symbol);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction declareAccessor(final PropertyAccessorDescriptor accessorDescriptor, final IrProperty property) {
        return this.symbolTable.getDescriptorExtension().declareSimpleFunctionIfNotExists(accessorDescriptor, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.psi2ir.generators.SyntheticDeclarationsGenerator.declareAccessor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrSimpleFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrSimpleFunction irSimpleFunctionCreateFunctionStub = SyntheticDeclarationsGenerator.this.createFunctionStub(accessorDescriptor, it);
                IrProperty irProperty = property;
                irSimpleFunctionCreateFunctionStub.setParent(irProperty.getParent());
                irSimpleFunctionCreateFunctionStub.setCorrespondingPropertySymbol(irProperty.getSymbol());
                return irSimpleFunctionCreateFunctionStub;
            }
        });
    }

    /* JADX INFO: renamed from: visitPropertyDescriptor, reason: avoid collision after fix types in other method */
    public void visitPropertyDescriptor2(final PropertyDescriptor descriptor, final IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (data == null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (Intrinsics.areEqual(descriptor.getVisibility(), DescriptorVisibilities.INVISIBLE_FAKE)) {
            return;
        }
        this.symbolTable.getDescriptorExtension().declarePropertyIfNotExists(descriptor, new Function1<IrPropertySymbol, IrProperty>() { // from class: org.jetbrains.kotlin.psi2ir.generators.SyntheticDeclarationsGenerator.visitPropertyDescriptor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrProperty invoke(IrPropertySymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                SyntheticDeclarationsGenerator syntheticDeclarationsGenerator = SyntheticDeclarationsGenerator.this;
                IrDeclaration irDeclarationInsertDeclaration = syntheticDeclarationsGenerator.insertDeclaration(syntheticDeclarationsGenerator.createPropertyStub(descriptor, it), data);
                PropertyDescriptor propertyDescriptor = descriptor;
                SyntheticDeclarationsGenerator syntheticDeclarationsGenerator2 = SyntheticDeclarationsGenerator.this;
                IrProperty irProperty = (IrProperty) irDeclarationInsertDeclaration;
                PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
                if (getter != null) {
                    Intrinsics.checkNotNull(getter);
                    irProperty.setGetter(syntheticDeclarationsGenerator2.declareAccessor(getter, irProperty));
                }
                PropertySetterDescriptor setter = propertyDescriptor.getSetter();
                if (setter != null) {
                    Intrinsics.checkNotNull(setter);
                    irProperty.setSetter(syntheticDeclarationsGenerator2.declareAccessor(setter, irProperty));
                }
                return irProperty;
            }
        });
    }

    /* JADX INFO: renamed from: visitValueParameterDescriptor, reason: avoid collision after fix types in other method */
    public void visitValueParameterDescriptor2(ValueParameterDescriptor descriptor, IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        throw new IllegalStateException(("Unreachable execution " + descriptor).toString());
    }

    /* JADX INFO: renamed from: visitPropertyGetterDescriptor, reason: avoid collision after fix types in other method */
    public void visitPropertyGetterDescriptor2(PropertyGetterDescriptor descriptor, IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        throw new IllegalStateException(("Unreachable execution " + descriptor).toString());
    }

    /* JADX INFO: renamed from: visitPropertySetterDescriptor, reason: avoid collision after fix types in other method */
    public void visitPropertySetterDescriptor2(PropertySetterDescriptor descriptor, IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        throw new IllegalStateException(("Unreachable execution " + descriptor).toString());
    }

    /* JADX INFO: renamed from: visitReceiverParameterDescriptor, reason: avoid collision after fix types in other method */
    public void visitReceiverParameterDescriptor2(ReceiverParameterDescriptor descriptor, IrDeclarationContainer data) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        throw new IllegalStateException(("Unreachable execution " + descriptor).toString());
    }
}
