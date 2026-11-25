package org.jetbrains.kotlin.ir.descriptors;

import com.badlogic.gdx.physics.bullet.collision.ContactCallbackEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeBuilder;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl;
import org.jetbrains.kotlin.ir.util.ConstantValueGenerator;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.DescriptorToIrUtilKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.SmartList;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrDescriptorBasedFunctionFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001SB?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0016\b\u0002\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\t\u0012\b\b\u0002\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ@\u0010-\u001a\u00020\u00162\u0006\u0010.\u001a\u00020\u00102\u0006\u0010/\u001a\u00020\r2\u0006\u00100\u001a\u00020\r2\u0006\u00101\u001a\u00020\u00152\u0006\u00102\u001a\u00020\u00102\u0006\u00103\u001a\u00020\u000b2\u0006\u00104\u001a\u000205H\u0002J\u0010\u00106\u001a\u0002072\u0006\u00104\u001a\u000205H\u0002J\u0010\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u0015H\u0016J;\u0010;\u001a\u00020\u00162\u0006\u0010:\u001a\u00020\u00152)\u0010<\u001a%\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00160\t\u0012\u0004\u0012\u00020\u00160=¢\u0006\u0002\b>H\u0016J\u0010\u0010?\u001a\u0002092\u0006\u0010:\u001a\u00020\u0015H\u0016J;\u0010@\u001a\u00020\u00162\u0006\u0010:\u001a\u00020\u00152)\u0010<\u001a%\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00160\t\u0012\u0004\u0012\u00020\u00160=¢\u0006\u0002\b>H\u0016J\u0010\u0010A\u001a\u0002092\u0006\u0010:\u001a\u00020\u0015H\u0016J;\u0010B\u001a\u00020\u00162\u0006\u0010:\u001a\u00020\u00152)\u0010<\u001a%\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00160\t\u0012\u0004\u0012\u00020\u00160=¢\u0006\u0002\b>H\u0016J\u0010\u0010C\u001a\u0002092\u0006\u0010:\u001a\u00020\u0015H\u0016J;\u0010D\u001a\u00020\u00162\u0006\u0010:\u001a\u00020\u00152)\u0010<\u001a%\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00160\t\u0012\u0004\u0012\u00020\u00160=¢\u0006\u0002\b>H\u0016J\u0010\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020HH\u0002J\f\u0010I\u001a\u00020J*\u00020\u0016H\u0002J$\u0010K\u001a\u00020J*\u00020\u00162\u0006\u0010/\u001a\u00020\r2\u0006\u00100\u001a\u00020\r2\u0006\u00104\u001a\u000205H\u0002J\u001c\u0010L\u001a\u00020M*\u00020N2\u0006\u00101\u001a\u00020\u00152\u0006\u00104\u001a\u000205H\u0002J\u0014\u0010O\u001a\u000207*\u00020P2\u0006\u0010Q\u001a\u00020RH\u0002R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\u00020\u001a8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0012R\u001a\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010!\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b\"\u0010#R\u001b\u0010&\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b(\u0010%\u001a\u0004\b'\u0010#R\u001b\u0010)\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b+\u0010%\u001a\u0004\b*\u0010#R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010,\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006T"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrDescriptorBasedFunctionFactory;", "Lorg/jetbrains/kotlin/ir/descriptors/IrAbstractDescriptorBasedFunctionFactory;", "irBuiltIns", "Lorg/jetbrains/kotlin/psi2ir/descriptors/IrBuiltInsOverDescriptors;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "getPackageFragment", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "referenceFunctionsWhenKFunctionAreReferenced", "", "(Lorg/jetbrains/kotlin/psi2ir/descriptors/IrBuiltInsOverDescriptors;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/ir/util/TypeTranslator;Lkotlin/jvm/functions/Function1;Z)V", "functionClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getFunctionClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "functionNMap", "", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getGetPackageFragment", "()Lkotlin/jvm/functions/Function1;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "kFunctionClass", "getKFunctionClass", "kFunctionNMap", "kSuspendFunctionNMap", "kotlinCoroutinesPackageFragment", "getKotlinCoroutinesPackageFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "kotlinCoroutinesPackageFragment$delegate", "Lkotlin/Lazy;", "kotlinPackageFragment", "getKotlinPackageFragment", "kotlinPackageFragment$delegate", "kotlinReflectPackageFragment", "getKotlinReflectPackageFragment", "kotlinReflectPackageFragment$delegate", "suspendFunctionNMap", "createFunctionClass", NativeSymbol.TYPE_NAME, "isK", "isSuspend", "n", "baseClass", "packageFragment", "descriptorFactory", "Lorg/jetbrains/kotlin/ir/descriptors/IrDescriptorBasedFunctionFactory$FunctionDescriptorFactory;", "createThisReceiver", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "functionClassDescriptor", "Lorg/jetbrains/kotlin/builtins/functions/FunctionClassDescriptor;", "arity", "functionN", "declarator", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "kFunctionClassDescriptor", "kFunctionN", "kSuspendFunctionClassDescriptor", "kSuspendFunctionN", "suspendFunctionClassDescriptor", "suspendFunctionN", "toIrType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "wrapped", "Lorg/jetbrains/kotlin/types/KotlinType;", "addFakeOverrides", "", "createMembers", "createTypeParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "createValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "FunctionDescriptorFactory", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrDescriptorBasedFunctionFactory extends IrAbstractDescriptorBasedFunctionFactory {
    private final IrClassSymbol functionClass;
    private final Map<Integer, IrClass> functionNMap;
    private final Function1<PackageFragmentDescriptor, IrPackageFragment> getPackageFragment;
    private final IrBuiltInsOverDescriptors irBuiltIns;
    private final IrClassSymbol kFunctionClass;
    private final Map<Integer, IrClass> kFunctionNMap;
    private final Map<Integer, IrClass> kSuspendFunctionNMap;

    /* JADX INFO: renamed from: kotlinCoroutinesPackageFragment$delegate, reason: from kotlin metadata */
    private final Lazy kotlinCoroutinesPackageFragment;

    /* JADX INFO: renamed from: kotlinPackageFragment$delegate, reason: from kotlin metadata */
    private final Lazy kotlinPackageFragment;

    /* JADX INFO: renamed from: kotlinReflectPackageFragment$delegate, reason: from kotlin metadata */
    private final Lazy kotlinReflectPackageFragment;
    private final boolean referenceFunctionsWhenKFunctionAreReferenced;
    private final Map<Integer, IrClass> suspendFunctionNMap;
    private final SymbolTable symbolTable;
    private final TypeTranslator typeTranslator;

    public /* synthetic */ IrDescriptorBasedFunctionFactory(IrBuiltInsOverDescriptors irBuiltInsOverDescriptors, SymbolTable symbolTable, TypeTranslator typeTranslator, Function1 function1, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irBuiltInsOverDescriptors, symbolTable, typeTranslator, (i & 8) != 0 ? null : function1, (i & 16) != 0 ? false : z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public IrDescriptorBasedFunctionFactory(IrBuiltInsOverDescriptors irBuiltIns, SymbolTable symbolTable, TypeTranslator typeTranslator, Function1<? super PackageFragmentDescriptor, ? extends IrPackageFragment> function1, boolean z) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(typeTranslator, "typeTranslator");
        this.irBuiltIns = irBuiltIns;
        this.symbolTable = symbolTable;
        this.typeTranslator = typeTranslator;
        this.referenceFunctionsWhenKFunctionAreReferenced = z;
        this.getPackageFragment = function1 == null ? new IrDescriptorBasedFunctionFactory$getPackageFragment$1(symbolTable.getDescriptorExtension()) : function1;
        this.functionNMap = new LinkedHashMap();
        this.kFunctionNMap = new LinkedHashMap();
        this.suspendFunctionNMap = new LinkedHashMap();
        this.kSuspendFunctionNMap = new LinkedHashMap();
        DescriptorSymbolTableExtension descriptorExtension = symbolTable.getDescriptorExtension();
        ClassDescriptor builtInClassByFqName = irBuiltIns.getBuiltIns().getBuiltInClassByFqName(new FqName("kotlin.Function"));
        Intrinsics.checkNotNullExpressionValue(builtInClassByFqName, "getBuiltInClassByFqName(...)");
        this.functionClass = descriptorExtension.referenceClass(builtInClassByFqName);
        DescriptorSymbolTableExtension descriptorExtension2 = symbolTable.getDescriptorExtension();
        ClassDescriptor builtInClassByFqName2 = irBuiltIns.getBuiltIns().getBuiltInClassByFqName(new FqName("kotlin.reflect.KFunction"));
        Intrinsics.checkNotNullExpressionValue(builtInClassByFqName2, "getBuiltInClassByFqName(...)");
        this.kFunctionClass = descriptorExtension2.referenceClass(builtInClassByFqName2);
        this.kotlinPackageFragment = LazyKt.lazy(new Function0<IrPackageFragment>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory$kotlinPackageFragment$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrPackageFragment invoke() {
                ClassDescriptor function = this.this$0.irBuiltIns.getBuiltIns().getFunction(0);
                Function1<PackageFragmentDescriptor, IrPackageFragment> getPackageFragment = this.this$0.getGetPackageFragment();
                DeclarationDescriptor containingModule = function.getContainingModule();
                Intrinsics.checkNotNull(containingModule, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor");
                return getPackageFragment.invoke((PackageFragmentDescriptor) containingModule);
            }
        });
        this.kotlinCoroutinesPackageFragment = LazyKt.lazy(new Function0<IrPackageFragment>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory$kotlinCoroutinesPackageFragment$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrPackageFragment invoke() {
                ClassDescriptor suspendFunction = this.this$0.irBuiltIns.getBuiltIns().getSuspendFunction(0);
                Function1<PackageFragmentDescriptor, IrPackageFragment> getPackageFragment = this.this$0.getGetPackageFragment();
                DeclarationDescriptor containingModule = suspendFunction.getContainingModule();
                Intrinsics.checkNotNull(containingModule, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor");
                return getPackageFragment.invoke((PackageFragmentDescriptor) containingModule);
            }
        });
        this.kotlinReflectPackageFragment = LazyKt.lazy(new Function0<IrPackageFragment>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory$kotlinReflectPackageFragment$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrPackageFragment invoke() {
                ClassDescriptor descriptor = this.this$0.irBuiltIns.getKPropertyClass().getDescriptor();
                Function1<PackageFragmentDescriptor, IrPackageFragment> getPackageFragment = this.this$0.getGetPackageFragment();
                DeclarationDescriptor containingModule = descriptor.getContainingModule();
                Intrinsics.checkNotNull(containingModule, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor");
                return getPackageFragment.invoke((PackageFragmentDescriptor) containingModule);
            }
        });
    }

    public final Function1<PackageFragmentDescriptor, IrPackageFragment> getGetPackageFragment() {
        return this.getPackageFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrFactory getIrFactory() {
        return this.symbolTable.getIrFactory();
    }

    public final IrClassSymbol getFunctionClass() {
        return this.functionClass;
    }

    public final IrClassSymbol getKFunctionClass() {
        return this.kFunctionClass;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory
    public FunctionClassDescriptor functionClassDescriptor(int arity) {
        ClassDescriptor function = this.irBuiltIns.getBuiltIns().getFunction(arity);
        Intrinsics.checkNotNull(function, "null cannot be cast to non-null type org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor");
        return (FunctionClassDescriptor) function;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory
    public FunctionClassDescriptor suspendFunctionClassDescriptor(int arity) {
        ClassDescriptor suspendFunction = this.irBuiltIns.getBuiltIns().getSuspendFunction(arity);
        Intrinsics.checkNotNull(suspendFunction, "null cannot be cast to non-null type org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor");
        return (FunctionClassDescriptor) suspendFunction;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory
    public FunctionClassDescriptor kFunctionClassDescriptor(int arity) {
        ClassDescriptor builtInClassByFqName = this.irBuiltIns.getBuiltIns().getBuiltInClassByFqName(IrDescriptorBasedFunctionFactoryKt.reflectFunctionClassFqn(IrDescriptorBasedFunctionFactoryKt.reflectionFunctionClassName(false, arity)));
        Intrinsics.checkNotNull(builtInClassByFqName, "null cannot be cast to non-null type org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor");
        return (FunctionClassDescriptor) builtInClassByFqName;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory
    public FunctionClassDescriptor kSuspendFunctionClassDescriptor(int arity) {
        ClassDescriptor builtInClassByFqName = this.irBuiltIns.getBuiltIns().getBuiltInClassByFqName(IrDescriptorBasedFunctionFactoryKt.reflectFunctionClassFqn(IrDescriptorBasedFunctionFactoryKt.reflectionFunctionClassName(true, arity)));
        Intrinsics.checkNotNull(builtInClassByFqName, "null cannot be cast to non-null type org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor");
        return (FunctionClassDescriptor) builtInClassByFqName;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory
    public IrClass functionN(final int arity, Function2<? super SymbolTable, ? super Function1<? super IrClassSymbol, ? extends IrClass>, ? extends IrClass> declarator) {
        Intrinsics.checkNotNullParameter(declarator, "declarator");
        Map<Integer, IrClass> map = this.functionNMap;
        Integer numValueOf = Integer.valueOf(arity);
        IrClass irClassInvoke = map.get(numValueOf);
        if (irClassInvoke == null) {
            irClassInvoke = declarator.invoke(this.symbolTable, new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory$functionN$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrClass invoke(IrClassSymbol symbol) {
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    IrDescriptorBasedFunctionFactory.FunctionDescriptorFactory.RealDescriptorFactory realDescriptorFactory = new IrDescriptorBasedFunctionFactory.FunctionDescriptorFactory.RealDescriptorFactory(symbol.getDescriptor(), this.this$0.symbolTable);
                    IrDescriptorBasedFunctionFactory irDescriptorBasedFunctionFactory = this.this$0;
                    return irDescriptorBasedFunctionFactory.createFunctionClass(symbol, false, false, arity, irDescriptorBasedFunctionFactory.irBuiltIns.getFunctionClass(), this.this$0.getKotlinPackageFragment(), realDescriptorFactory);
                }
            });
            map.put(numValueOf, irClassInvoke);
        }
        return irClassInvoke;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory
    public IrClass suspendFunctionN(final int arity, Function2<? super SymbolTable, ? super Function1<? super IrClassSymbol, ? extends IrClass>, ? extends IrClass> declarator) {
        Intrinsics.checkNotNullParameter(declarator, "declarator");
        Map<Integer, IrClass> map = this.suspendFunctionNMap;
        Integer numValueOf = Integer.valueOf(arity);
        IrClass irClassInvoke = map.get(numValueOf);
        if (irClassInvoke == null) {
            irClassInvoke = declarator.invoke(this.symbolTable, new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory$suspendFunctionN$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrClass invoke(IrClassSymbol symbol) {
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    ClassDescriptor descriptor = symbol.getDescriptor();
                    Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor");
                    IrDescriptorBasedFunctionFactory.FunctionDescriptorFactory.RealDescriptorFactory realDescriptorFactory = new IrDescriptorBasedFunctionFactory.FunctionDescriptorFactory.RealDescriptorFactory((FunctionClassDescriptor) descriptor, this.this$0.symbolTable);
                    IrDescriptorBasedFunctionFactory irDescriptorBasedFunctionFactory = this.this$0;
                    return irDescriptorBasedFunctionFactory.createFunctionClass(symbol, false, true, arity, irDescriptorBasedFunctionFactory.irBuiltIns.getFunctionClass(), this.this$0.getKotlinCoroutinesPackageFragment(), realDescriptorFactory);
                }
            });
            map.put(numValueOf, irClassInvoke);
        }
        return irClassInvoke;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory
    public IrClass kFunctionN(final int arity, Function2<? super SymbolTable, ? super Function1<? super IrClassSymbol, ? extends IrClass>, ? extends IrClass> declarator) {
        Intrinsics.checkNotNullParameter(declarator, "declarator");
        if (this.referenceFunctionsWhenKFunctionAreReferenced) {
            functionN(arity);
        }
        Map<Integer, IrClass> map = this.kFunctionNMap;
        Integer numValueOf = Integer.valueOf(arity);
        IrClass irClassInvoke = map.get(numValueOf);
        if (irClassInvoke == null) {
            irClassInvoke = declarator.invoke(this.symbolTable, new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory$kFunctionN$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrClass invoke(IrClassSymbol symbol) {
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    ClassDescriptor descriptor = symbol.getDescriptor();
                    Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor");
                    IrDescriptorBasedFunctionFactory.FunctionDescriptorFactory.RealDescriptorFactory realDescriptorFactory = new IrDescriptorBasedFunctionFactory.FunctionDescriptorFactory.RealDescriptorFactory((FunctionClassDescriptor) descriptor, this.this$0.symbolTable);
                    IrDescriptorBasedFunctionFactory irDescriptorBasedFunctionFactory = this.this$0;
                    return irDescriptorBasedFunctionFactory.createFunctionClass(symbol, true, false, arity, irDescriptorBasedFunctionFactory.irBuiltIns.getKFunctionClass(), this.this$0.getKotlinReflectPackageFragment(), realDescriptorFactory);
                }
            });
            map.put(numValueOf, irClassInvoke);
        }
        return irClassInvoke;
    }

    @Override // org.jetbrains.kotlin.ir.descriptors.IrAbstractDescriptorBasedFunctionFactory
    public IrClass kSuspendFunctionN(final int arity, Function2<? super SymbolTable, ? super Function1<? super IrClassSymbol, ? extends IrClass>, ? extends IrClass> declarator) {
        Intrinsics.checkNotNullParameter(declarator, "declarator");
        if (this.referenceFunctionsWhenKFunctionAreReferenced) {
            suspendFunctionN(arity);
        }
        Map<Integer, IrClass> map = this.kSuspendFunctionNMap;
        Integer numValueOf = Integer.valueOf(arity);
        IrClass irClassInvoke = map.get(numValueOf);
        if (irClassInvoke == null) {
            irClassInvoke = declarator.invoke(this.symbolTable, new Function1<IrClassSymbol, IrClass>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory$kSuspendFunctionN$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrClass invoke(IrClassSymbol symbol) {
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    ClassDescriptor descriptor = symbol.getDescriptor();
                    Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor");
                    IrDescriptorBasedFunctionFactory.FunctionDescriptorFactory.RealDescriptorFactory realDescriptorFactory = new IrDescriptorBasedFunctionFactory.FunctionDescriptorFactory.RealDescriptorFactory((FunctionClassDescriptor) descriptor, this.this$0.symbolTable);
                    IrDescriptorBasedFunctionFactory irDescriptorBasedFunctionFactory = this.this$0;
                    return irDescriptorBasedFunctionFactory.createFunctionClass(symbol, true, true, arity, irDescriptorBasedFunctionFactory.irBuiltIns.getKFunctionClass(), this.this$0.getKotlinReflectPackageFragment(), realDescriptorFactory);
                }
            });
            map.put(numValueOf, irClassInvoke);
        }
        return irClassInvoke;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: IrDescriptorBasedFunctionFactory.kt */
    @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0001\u0018B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH&J$\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000f0\u000eH&J$\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00140\u000eH&J\u0014\u0010\u0015\u001a\u00020\u0016*\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u0013H&R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0001\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrDescriptorBasedFunctionFactory$FunctionDescriptorFactory;", "", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "(Lorg/jetbrains/kotlin/ir/util/SymbolTable;)V", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "classReceiverParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "memberDescriptor", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "name", "", "factory", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "typeParameterDescriptor", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "index", "", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "valueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "RealDescriptorFactory", "Lorg/jetbrains/kotlin/ir/descriptors/IrDescriptorBasedFunctionFactory$FunctionDescriptorFactory$RealDescriptorFactory;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static abstract class FunctionDescriptorFactory {
        private final SymbolTable symbolTable;

        public /* synthetic */ FunctionDescriptorFactory(SymbolTable symbolTable, DefaultConstructorMarker defaultConstructorMarker) {
            this(symbolTable);
        }

        public abstract ReceiverParameterDescriptor classReceiverParameterDescriptor();

        public abstract IrSimpleFunctionSymbol memberDescriptor(String name2, Function1<? super IrSimpleFunctionSymbol, ? extends IrSimpleFunction> factory);

        public abstract IrTypeParameterSymbol typeParameterDescriptor(int index, Function1<? super IrTypeParameterSymbol, ? extends IrTypeParameter> factory);

        public abstract ValueParameterDescriptor valueParameterDescriptor(FunctionDescriptor functionDescriptor, int i);

        private FunctionDescriptorFactory(SymbolTable symbolTable) {
            this.symbolTable = symbolTable;
        }

        protected final SymbolTable getSymbolTable() {
            return this.symbolTable;
        }

        /* JADX INFO: compiled from: IrDescriptorBasedFunctionFactory.kt */
        @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J$\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016J$\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00140\u000eH\u0016J\u0014\u0010\u0015\u001a\u00020\u0016*\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/ir/descriptors/IrDescriptorBasedFunctionFactory$FunctionDescriptorFactory$RealDescriptorFactory;", "Lorg/jetbrains/kotlin/ir/descriptors/IrDescriptorBasedFunctionFactory$FunctionDescriptorFactory;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/ir/util/SymbolTable;)V", "classReceiverParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "memberDescriptor", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "name", "", "factory", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "typeParameterDescriptor", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "index", "", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "valueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class RealDescriptorFactory extends FunctionDescriptorFactory {
            private final ClassDescriptor classDescriptor;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public RealDescriptorFactory(ClassDescriptor classDescriptor, SymbolTable symbolTable) {
                super(symbolTable, null);
                Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
                Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
                this.classDescriptor = classDescriptor;
            }

            @Override // org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory.FunctionDescriptorFactory
            public IrSimpleFunctionSymbol memberDescriptor(String name2, Function1<? super IrSimpleFunctionSymbol, ? extends IrSimpleFunction> factory) {
                FunctionDescriptor functionDescriptor;
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(factory, "factory");
                MemberScope unsubstitutedMemberScope = this.classDescriptor.getUnsubstitutedMemberScope();
                if (name2.charAt(0) == '<') {
                    Name nameIdentifier = Name.identifier(StringsKt.dropLast(StringsKt.drop(name2, 5), 1));
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    List<PropertyAccessorDescriptor> accessors = ((PropertyDescriptor) CollectionsKt.single(unsubstitutedMemberScope.getContributedVariables(nameIdentifier, NoLookupLocation.FROM_BACKEND))).getAccessors();
                    Intrinsics.checkNotNullExpressionValue(accessors, "getAccessors(...)");
                    for (Object obj : accessors) {
                        if (Intrinsics.areEqual(((PropertyAccessorDescriptor) obj).getShortName().asString(), name2)) {
                            functionDescriptor = (FunctionDescriptor) obj;
                        }
                    }
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
                Name nameIdentifier2 = Name.identifier(name2);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
                functionDescriptor = (FunctionDescriptor) CollectionsKt.first(unsubstitutedMemberScope.getContributedFunctions(nameIdentifier2, NoLookupLocation.FROM_BACKEND));
                DescriptorSymbolTableExtension descriptorExtension = getSymbolTable().getDescriptorExtension();
                Intrinsics.checkNotNull(functionDescriptor);
                return descriptorExtension.declareSimpleFunction(functionDescriptor, factory).getSymbol();
            }

            @Override // org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory.FunctionDescriptorFactory
            public ValueParameterDescriptor valueParameterDescriptor(FunctionDescriptor functionDescriptor, int i) {
                Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
                functionDescriptor.getContainingModule();
                ValueParameterDescriptor valueParameterDescriptor = functionDescriptor.getValueParameters().get(i);
                Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "get(...)");
                return valueParameterDescriptor;
            }

            @Override // org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory.FunctionDescriptorFactory
            public IrTypeParameterSymbol typeParameterDescriptor(int index, Function1<? super IrTypeParameterSymbol, ? extends IrTypeParameter> factory) {
                Intrinsics.checkNotNullParameter(factory, "factory");
                TypeParameterDescriptor typeParameterDescriptor = this.classDescriptor.getDeclaredTypeParameters().get(index);
                DescriptorSymbolTableExtension descriptorExtension = getSymbolTable().getDescriptorExtension();
                Intrinsics.checkNotNull(typeParameterDescriptor);
                return descriptorExtension.declareGlobalTypeParameter(typeParameterDescriptor, factory).getSymbol();
            }

            @Override // org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory.FunctionDescriptorFactory
            public ReceiverParameterDescriptor classReceiverParameterDescriptor() {
                ReceiverParameterDescriptor thisAsReceiverParameter = this.classDescriptor.getThisAsReceiverParameter();
                Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter, "getThisAsReceiverParameter(...)");
                return thisAsReceiverParameter;
            }
        }
    }

    private final IrTypeParameter createTypeParameters(IrTypeParametersContainer irTypeParametersContainer, int i, FunctionDescriptorFactory functionDescriptorFactory) {
        final Ref.IntRef intRef = new Ref.IntRef();
        int i2 = i + 1;
        ArrayList arrayList = new ArrayList(i2);
        for (int i3 = 1; i3 < i2; i3++) {
            final Name nameIdentifier = Name.identifier("P" + i3);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            IrTypeParameter owner = functionDescriptorFactory.typeParameterDescriptor(intRef.element, new Function1<IrTypeParameterSymbol, IrTypeParameter>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory$createTypeParameters$pSymbol$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrTypeParameter invoke(IrTypeParameterSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrFactory irFactory = this.this$0.getIrFactory();
                    IrDeclarationOriginImpl classOrigin = IrAbstractDescriptorBasedFunctionFactory.INSTANCE.getClassOrigin();
                    Name name2 = nameIdentifier;
                    Variance variance = Variance.IN_VARIANCE;
                    int i4 = intRef.element;
                    intRef.element = i4 + 1;
                    return irFactory.createTypeParameter(-2, -2, (IrDeclarationOrigin) classOrigin, name2, it, variance, i4, false);
                }
            }).getOwner();
            owner.setSuperTypes(CollectionsKt.plus((Collection<? extends IrType>) owner.getSuperTypes(), this.irBuiltIns.getAnyNType()));
            owner.setParent(irTypeParametersContainer);
            arrayList.add(owner);
        }
        IrTypeParameter owner2 = functionDescriptorFactory.typeParameterDescriptor(intRef.element, new Function1<IrTypeParameterSymbol, IrTypeParameter>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory$createTypeParameters$rSymbol$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrTypeParameter invoke(IrTypeParameterSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrFactory irFactory = this.this$0.getIrFactory();
                IrDeclarationOriginImpl classOrigin = IrAbstractDescriptorBasedFunctionFactory.INSTANCE.getClassOrigin();
                Name nameIdentifier2 = Name.identifier("R");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
                return irFactory.createTypeParameter(-2, -2, (IrDeclarationOrigin) classOrigin, nameIdentifier2, it, Variance.OUT_VARIANCE, intRef.element, false);
            }
        }).getOwner();
        owner2.setSuperTypes(CollectionsKt.plus((Collection<? extends IrType>) owner2.getSuperTypes(), this.irBuiltIns.getAnyNType()));
        owner2.setParent(irTypeParametersContainer);
        arrayList.add(owner2);
        irTypeParametersContainer.setTypeParameters(arrayList);
        return owner2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrPackageFragment getKotlinPackageFragment() {
        return (IrPackageFragment) this.kotlinPackageFragment.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrPackageFragment getKotlinCoroutinesPackageFragment() {
        return (IrPackageFragment) this.kotlinCoroutinesPackageFragment.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrPackageFragment getKotlinReflectPackageFragment() {
        return (IrPackageFragment) this.kotlinReflectPackageFragment.getValue();
    }

    private final IrValueParameter createThisReceiver(FunctionDescriptorFactory descriptorFactory) {
        ReceiverParameterDescriptor receiverParameterDescriptorClassReceiverParameterDescriptor = descriptorFactory.classReceiverParameterDescriptor();
        IrFactory irFactory = getIrFactory();
        IrDeclarationOriginImpl classOrigin = IrAbstractDescriptorBasedFunctionFactory.INSTANCE.getClassOrigin();
        Name name2 = SpecialNames.THIS;
        TypeTranslator typeTranslator = this.typeTranslator;
        KotlinType type = receiverParameterDescriptorClassReceiverParameterDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return irFactory.createValueParameter(-2, -2, (IrDeclarationOrigin) classOrigin, name2, typeTranslator.translateType(type), false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(receiverParameterDescriptorClassReceiverParameterDescriptor), -1, (IrType) null, false, false, false);
    }

    private final void createMembers(final IrClass irClass, boolean z, final boolean z2, FunctionDescriptorFactory functionDescriptorFactory) {
        if (!z) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbolMemberDescriptor = functionDescriptorFactory.memberDescriptor("invoke", new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory$createMembers$invokeSymbol$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrSimpleFunction invoke(IrSimpleFunctionSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    IrSimpleTypeBuilder irSimpleTypeBuilder = new IrSimpleTypeBuilder();
                    irSimpleTypeBuilder.setClassifier(((IrTypeParameter) CollectionsKt.last((List) irClass.getTypeParameters())).getSymbol());
                    IrSimpleTypeImpl irSimpleTypeImplBuildSimpleType = IrSimpleTypeImplKt.buildSimpleType(irSimpleTypeBuilder);
                    IrFactory irFactory = this.this$0.getIrFactory();
                    IrDeclarationOriginImpl memberOrigin = IrAbstractDescriptorBasedFunctionFactory.INSTANCE.getMemberOrigin();
                    Name nameIdentifier = Name.identifier("invoke");
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
                    Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
                    return IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, -2, -2, memberOrigin, nameIdentifier, PUBLIC, false, false, irSimpleTypeImplBuildSimpleType, Modality.ABSTRACT, it, false, z2, true, false, false, null, false, 32768, null);
                }
            });
            IrSimpleFunction owner = irSimpleFunctionSymbolMemberDescriptor.getOwner();
            IrValueParameter irValueParameterCreateThisReceiver = createThisReceiver(functionDescriptorFactory);
            IrSimpleFunction irSimpleFunction = owner;
            irValueParameterCreateThisReceiver.setParent(irSimpleFunction);
            owner.setDispatchReceiverParameter(irValueParameterCreateThisReceiver);
            IrSimpleTypeBuilder irSimpleTypeBuilder = new IrSimpleTypeBuilder();
            int size = irClass.getTypeParameters().size();
            for (int i = 1; i < size; i++) {
                int i2 = i - 1;
                IrTypeParameter irTypeParameter = irClass.getTypeParameters().get(i2);
                IrValueParameterSymbolImpl irValueParameterSymbolImpl = new IrValueParameterSymbolImpl(functionDescriptorFactory.valueParameterDescriptor(irSimpleFunctionSymbolMemberDescriptor.getDescriptor(), i2));
                irSimpleTypeBuilder.setClassifier(irTypeParameter.getSymbol());
                IrSimpleTypeImpl irSimpleTypeImplBuildSimpleType = IrSimpleTypeImplKt.buildSimpleType(irSimpleTypeBuilder);
                IrFactory irFactory = getIrFactory();
                IrDeclarationOriginImpl memberOrigin = IrAbstractDescriptorBasedFunctionFactory.INSTANCE.getMemberOrigin();
                Name nameIdentifier = Name.identifier("p" + i);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                IrValueParameter irValueParameterCreateValueParameter = irFactory.createValueParameter(-2, -2, (IrDeclarationOrigin) memberOrigin, nameIdentifier, (IrType) irSimpleTypeImplBuildSimpleType, false, (IrValueParameterSymbol) irValueParameterSymbolImpl, i2, (IrType) null, false, false, false);
                irValueParameterCreateValueParameter.setParent(irSimpleFunction);
                owner.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) owner.getValueParameters(), irValueParameterCreateValueParameter));
            }
            owner.setParent(irClass);
            irClass.getDeclarations().add(owner);
        }
        addFakeOverrides(irClass);
    }

    private final IrType toIrType(KotlinType wrapped) {
        IrTypeArgument irTypeArgumentMakeTypeProjection;
        UnwrappedType unwrappedTypeUnwrap = wrapped.unwrap();
        IrSimpleTypeBuilder irSimpleTypeBuilder = new IrSimpleTypeBuilder();
        SymbolTable symbolTable = this.symbolTable;
        ClassifierDescriptor this$0 = unwrappedTypeUnwrap.getConstructor().getThis$0();
        if (this$0 != null) {
            Intrinsics.checkNotNull(this$0);
            irSimpleTypeBuilder.setClassifier(IrUtilsKt.referenceClassifier(symbolTable, this$0));
            irSimpleTypeBuilder.setNullability(SimpleTypeNullability.INSTANCE.fromHasQuestionMark(unwrappedTypeUnwrap.getIsMarkedNullable()));
            List<TypeProjection> arguments = unwrappedTypeUnwrap.getArguments();
            ArrayList arrayList = new ArrayList(arguments.size());
            for (TypeProjection typeProjection : arguments) {
                if (typeProjection.isStarProjection()) {
                    irTypeArgumentMakeTypeProjection = IrStarProjectionImpl.INSTANCE;
                } else {
                    KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                    Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                    IrType irType = toIrType(nullableAnyType);
                    Variance projectionKind = typeProjection.getProjectionKind();
                    Intrinsics.checkNotNullExpressionValue(projectionKind, "getProjectionKind(...)");
                    irTypeArgumentMakeTypeProjection = IrSimpleTypeImplKt.makeTypeProjection(irType, projectionKind);
                }
                arrayList.add(irTypeArgumentMakeTypeProjection);
            }
            irSimpleTypeBuilder.setArguments(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
            return IrSimpleTypeImplKt.buildSimpleType(irSimpleTypeBuilder);
        }
        throw new IllegalStateException(("No classifier for type " + unwrappedTypeUnwrap).toString());
    }

    private final IrValueParameter createValueParameter(IrFunction irFunction, ParameterDescriptor parameterDescriptor) {
        KotlinType varargElementType;
        IrFactory irFactory = getIrFactory();
        IrDeclarationOriginImpl memberOrigin = IrAbstractDescriptorBasedFunctionFactory.INSTANCE.getMemberOrigin();
        Name name2 = parameterDescriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        KotlinType type = parameterDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        IrType irType = toIrType(type);
        IrValueParameterSymbolImpl irValueParameterSymbolImpl = new IrValueParameterSymbolImpl(parameterDescriptor);
        int indexOrMinusOne = DescriptorToIrUtilKt.getIndexOrMinusOne(parameterDescriptor);
        ValueParameterDescriptor valueParameterDescriptor = parameterDescriptor instanceof ValueParameterDescriptor ? (ValueParameterDescriptor) parameterDescriptor : null;
        IrValueParameter irValueParameterCreateValueParameter = irFactory.createValueParameter(-2, -2, (IrDeclarationOrigin) memberOrigin, name2, irType, false, (IrValueParameterSymbol) irValueParameterSymbolImpl, indexOrMinusOne, (valueParameterDescriptor == null || (varargElementType = valueParameterDescriptor.getVarargElementType()) == null) ? null : toIrType(varargElementType), DescriptorToIrUtilKt.isCrossinline(parameterDescriptor), DescriptorToIrUtilKt.isNoinline(parameterDescriptor), false);
        irValueParameterCreateValueParameter.setParent(irFunction);
        return irValueParameterCreateValueParameter;
    }

    private final void addFakeOverrides(IrClass irClass) {
        SmartList smartListEmptyList;
        MemberScope unsubstitutedMemberScope = irClass.getDescriptor().getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
        Collection contributedDescriptors$default = ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, DescriptorKindFilter.CALLABLES, null, 2, null);
        if (contributedDescriptors$default.isEmpty()) {
            smartListEmptyList = CollectionsKt.emptyList();
        } else {
            SmartList smartList = new SmartList();
            for (Object obj : contributedDescriptors$default) {
                if (obj instanceof CallableMemberDescriptor) {
                    CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) obj;
                    if (callableMemberDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE && callableMemberDescriptor.mo6753getDispatchReceiverParameter() != null && !DescriptorVisibilities.isPrivate(callableMemberDescriptor.getVisibility()) && !Intrinsics.areEqual(callableMemberDescriptor.getVisibility(), DescriptorVisibilities.INVISIBLE_FAKE)) {
                        smartList.add(obj);
                    }
                }
            }
            smartListEmptyList = smartList;
        }
        List<IrDeclaration> declarations = irClass.getDeclarations();
        Iterable iterable = smartListEmptyList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(addFakeOverrides$createFakeOverride(this, irClass, (CallableMemberDescriptor) it.next()));
        }
        CollectionsKt.addAll(declarations, arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunction addFakeOverrides$createFakeOverrideFunction(final IrDescriptorBasedFunctionFactory irDescriptorBasedFunctionFactory, IrClass irClass, final FunctionDescriptor functionDescriptor, IrPropertySymbol irPropertySymbol) {
        final IrType irType;
        KotlinType returnType = functionDescriptor.getReturnType();
        if (returnType == null || (irType = irDescriptorBasedFunctionFactory.toIrType(returnType)) == null) {
            throw new IllegalStateException(("No return type for " + functionDescriptor).toString());
        }
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunction = irDescriptorBasedFunctionFactory.symbolTable.getDescriptorExtension().declareSimpleFunction(functionDescriptor, new Function1<IrSimpleFunctionSymbol, IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory$addFakeOverrides$createFakeOverrideFunction$newFunction$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunction invoke(IrSimpleFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                FunctionDescriptor functionDescriptor2 = functionDescriptor;
                IrDescriptorBasedFunctionFactory irDescriptorBasedFunctionFactory2 = irDescriptorBasedFunctionFactory;
                IrType irType2 = irType;
                IrFactory irFactory = irDescriptorBasedFunctionFactory2.getIrFactory();
                IrDeclarationOriginImpl memberOrigin = IrAbstractDescriptorBasedFunctionFactory.INSTANCE.getMemberOrigin();
                Name name2 = functionDescriptor2.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                DescriptorVisibility visibility = functionDescriptor2.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                boolean zIsInline = functionDescriptor2.isInline();
                boolean zIsExpect = functionDescriptor2.get$isExpect();
                Modality modality = functionDescriptor2.getModality();
                Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
                return IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, -2, -2, memberOrigin, name2, visibility, zIsInline, zIsExpect, irType2, modality, it, functionDescriptor2.isTailrec(), functionDescriptor2.isSuspend(), functionDescriptor2.isOperator(), functionDescriptor2.isInfix(), DescriptorUtilsKt.isEffectivelyExternal(functionDescriptor2), null, true, 32768, null);
            }
        });
        irSimpleFunctionDeclareSimpleFunction.setParent(irClass);
        Collection<? extends FunctionDescriptor> overriddenDescriptors = functionDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        ArrayList arrayList = new ArrayList(overriddenDescriptors.size());
        for (FunctionDescriptor functionDescriptor2 : overriddenDescriptors) {
            DescriptorSymbolTableExtension descriptorExtension = irDescriptorBasedFunctionFactory.symbolTable.getDescriptorExtension();
            FunctionDescriptor original = functionDescriptor2.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
            arrayList.add(descriptorExtension.referenceSimpleFunction(original));
        }
        irSimpleFunctionDeclareSimpleFunction.setOverriddenSymbols(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
        ReceiverParameterDescriptor dispatchReceiverParameter = functionDescriptor.mo6753getDispatchReceiverParameter();
        irSimpleFunctionDeclareSimpleFunction.setDispatchReceiverParameter(dispatchReceiverParameter != null ? irDescriptorBasedFunctionFactory.createValueParameter(irSimpleFunctionDeclareSimpleFunction, dispatchReceiverParameter) : null);
        ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
        irSimpleFunctionDeclareSimpleFunction.setExtensionReceiverParameter(extensionReceiverParameter != null ? irDescriptorBasedFunctionFactory.createValueParameter(irSimpleFunctionDeclareSimpleFunction, extensionReceiverParameter) : null);
        irSimpleFunctionDeclareSimpleFunction.setContextReceiverParametersCount(functionDescriptor.getContextReceiverParameters().size());
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        ArrayList arrayList2 = new ArrayList(list.size());
        for (ValueParameterDescriptor valueParameterDescriptor : list) {
            Intrinsics.checkNotNull(valueParameterDescriptor);
            arrayList2.add(irDescriptorBasedFunctionFactory.createValueParameter(irSimpleFunctionDeclareSimpleFunction, valueParameterDescriptor));
        }
        irSimpleFunctionDeclareSimpleFunction.setValueParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2));
        irSimpleFunctionDeclareSimpleFunction.setCorrespondingPropertySymbol(irPropertySymbol);
        Annotations annotations = functionDescriptor.getAnnotations();
        ConstantValueGenerator constantValueGenerator = irDescriptorBasedFunctionFactory.typeTranslator.getConstantValueGenerator();
        ArrayList arrayList3 = new ArrayList();
        Iterator<AnnotationDescriptor> it = annotations.iterator();
        while (it.hasNext()) {
            IrConstructorCall irConstructorCallGenerateAnnotationConstructorCall$default = ConstantValueGenerator.generateAnnotationConstructorCall$default(constantValueGenerator, it.next(), null, 2, null);
            if (irConstructorCallGenerateAnnotationConstructorCall$default != null) {
                arrayList3.add(irConstructorCallGenerateAnnotationConstructorCall$default);
            }
        }
        irSimpleFunctionDeclareSimpleFunction.setAnnotations(arrayList3);
        return irSimpleFunctionDeclareSimpleFunction;
    }

    private static final IrProperty addFakeOverrides$createFakeOverrideProperty(final IrDescriptorBasedFunctionFactory irDescriptorBasedFunctionFactory, final IrClass irClass, final PropertyDescriptor propertyDescriptor) {
        return irDescriptorBasedFunctionFactory.symbolTable.getDescriptorExtension().declareProperty(propertyDescriptor, new Function1<IrPropertySymbol, IrProperty>() { // from class: org.jetbrains.kotlin.ir.descriptors.IrDescriptorBasedFunctionFactory$addFakeOverrides$createFakeOverrideProperty$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrProperty invoke(IrPropertySymbol it) {
                IrSimpleFunction irSimpleFunctionAddFakeOverrides$createFakeOverrideFunction;
                IrSimpleFunction irSimpleFunctionAddFakeOverrides$createFakeOverrideFunction2;
                Intrinsics.checkNotNullParameter(it, "it");
                IrFactory irFactory = this.this$0.getIrFactory();
                IrDeclarationOriginImpl memberOrigin = IrAbstractDescriptorBasedFunctionFactory.INSTANCE.getMemberOrigin();
                Name name2 = propertyDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                DescriptorVisibility visibility = propertyDescriptor.getVisibility();
                Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
                Modality modality = propertyDescriptor.getModality();
                Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
                IrProperty irPropertyCreateProperty$default = IrFactory.DefaultImpls.createProperty$default(irFactory, -2, -2, (IrDeclarationOrigin) memberOrigin, name2, visibility, modality, it, propertyDescriptor.isVar(), propertyDescriptor.isConst(), propertyDescriptor.isLateInit(), propertyDescriptor.getIsDelegated(), DescriptorUtilsKt.isEffectivelyExternal(propertyDescriptor), (DeserializedContainerSource) null, propertyDescriptor.get$isExpect(), false, ContactCallbackEvent.ON_STARTED_FILTERED_MANIFOLD, (Object) null);
                IrClass irClass2 = irClass;
                PropertyDescriptor propertyDescriptor2 = propertyDescriptor;
                IrDescriptorBasedFunctionFactory irDescriptorBasedFunctionFactory2 = this.this$0;
                irPropertyCreateProperty$default.setParent(irClass2);
                PropertyGetterDescriptor getter = propertyDescriptor2.getGetter();
                if (getter != null) {
                    Intrinsics.checkNotNull(getter);
                    irSimpleFunctionAddFakeOverrides$createFakeOverrideFunction = IrDescriptorBasedFunctionFactory.addFakeOverrides$createFakeOverrideFunction(irDescriptorBasedFunctionFactory2, irClass2, getter, irPropertyCreateProperty$default.getSymbol());
                } else {
                    irSimpleFunctionAddFakeOverrides$createFakeOverrideFunction = null;
                }
                irPropertyCreateProperty$default.setGetter(irSimpleFunctionAddFakeOverrides$createFakeOverrideFunction);
                PropertySetterDescriptor setter = propertyDescriptor2.getSetter();
                if (setter != null) {
                    Intrinsics.checkNotNull(setter);
                    irSimpleFunctionAddFakeOverrides$createFakeOverrideFunction2 = IrDescriptorBasedFunctionFactory.addFakeOverrides$createFakeOverrideFunction(irDescriptorBasedFunctionFactory2, irClass2, setter, irPropertyCreateProperty$default.getSymbol());
                } else {
                    irSimpleFunctionAddFakeOverrides$createFakeOverrideFunction2 = null;
                }
                irPropertyCreateProperty$default.setSetter(irSimpleFunctionAddFakeOverrides$createFakeOverrideFunction2);
                Annotations annotations = propertyDescriptor2.getAnnotations();
                ConstantValueGenerator constantValueGenerator = irDescriptorBasedFunctionFactory2.typeTranslator.getConstantValueGenerator();
                ArrayList arrayList = new ArrayList();
                Iterator<AnnotationDescriptor> it2 = annotations.iterator();
                while (it2.hasNext()) {
                    IrConstructorCall irConstructorCallGenerateAnnotationConstructorCall$default = ConstantValueGenerator.generateAnnotationConstructorCall$default(constantValueGenerator, it2.next(), null, 2, null);
                    if (irConstructorCallGenerateAnnotationConstructorCall$default != null) {
                        arrayList.add(irConstructorCallGenerateAnnotationConstructorCall$default);
                    }
                }
                irPropertyCreateProperty$default.setAnnotations(arrayList);
                return irPropertyCreateProperty$default;
            }
        });
    }

    private static final IrDeclaration addFakeOverrides$createFakeOverride(IrDescriptorBasedFunctionFactory irDescriptorBasedFunctionFactory, IrClass irClass, CallableMemberDescriptor callableMemberDescriptor) {
        if (callableMemberDescriptor instanceof FunctionDescriptor) {
            return addFakeOverrides$createFakeOverrideFunction(irDescriptorBasedFunctionFactory, irClass, (FunctionDescriptor) callableMemberDescriptor, null);
        }
        if (callableMemberDescriptor instanceof PropertyDescriptor) {
            return addFakeOverrides$createFakeOverrideProperty(irDescriptorBasedFunctionFactory, irClass, (PropertyDescriptor) callableMemberDescriptor);
        }
        throw new IllegalStateException(("Unexpected member " + callableMemberDescriptor).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClass createFunctionClass(IrClassSymbol symbol, boolean isK, boolean isSuspend, int n, IrClassSymbol baseClass, IrPackageFragment packageFragment, FunctionDescriptorFactory descriptorFactory) {
        String strFunctionClassName$ir_psi2ir = IrAbstractDescriptorBasedFunctionFactory.INSTANCE.functionClassName$ir_psi2ir(isK, isSuspend, n);
        if (symbol.isBound()) {
            return symbol.getOwner();
        }
        IrFactory irFactory = getIrFactory();
        IrDeclarationOriginImpl classOrigin = IrAbstractDescriptorBasedFunctionFactory.INSTANCE.getClassOrigin();
        Name nameIdentifier = Name.identifier(strFunctionClassName$ir_psi2ir);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        IrClass irClassCreateClass$default = IrFactory.DefaultImpls.createClass$default(irFactory, -2, -2, (IrDeclarationOrigin) classOrigin, nameIdentifier, PUBLIC, symbol, ClassKind.INTERFACE, Modality.ABSTRACT, false, false, false, false, false, false, false, (SourceElement) null, 65280, (Object) null);
        IrTypeParameter irTypeParameterCreateTypeParameters = createTypeParameters(irClassCreateClass$default, n, descriptorFactory);
        IrValueParameter irValueParameterCreateThisReceiver = createThisReceiver(descriptorFactory);
        irValueParameterCreateThisReceiver.setParent(irClassCreateClass$default);
        irClassCreateClass$default.setThisReceiver(irValueParameterCreateThisReceiver);
        IrSimpleTypeBuilder irSimpleTypeBuilder = new IrSimpleTypeBuilder();
        irSimpleTypeBuilder.setClassifier(baseClass);
        IrSimpleTypeBuilder irSimpleTypeBuilder2 = new IrSimpleTypeBuilder();
        irSimpleTypeBuilder2.setClassifier(irTypeParameterCreateTypeParameters.getSymbol());
        irSimpleTypeBuilder.setArguments(CollectionsKt.listOf(IrSimpleTypeImplKt.buildTypeProjection(irSimpleTypeBuilder2)));
        irClassCreateClass$default.setSuperTypes(CollectionsKt.listOf(IrSimpleTypeImplKt.buildSimpleType(irSimpleTypeBuilder)));
        irClassCreateClass$default.setParent(packageFragment);
        packageFragment.getDeclarations().add(irClassCreateClass$default);
        createMembers(irClassCreateClass$default, isK, isSuspend, descriptorFactory);
        return irClassCreateClass$default;
    }
}
