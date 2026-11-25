package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.MfvcNodeKt;
import org.jetbrains.kotlin.backend.jvm.RootMfvcNode;
import org.jetbrains.kotlin.backend.jvm.ir.IrArrayBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FunctionNVarargBridgeLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0016J\u001c\u0010'\u001a\u00020\u0010*\u00020\"2\u0006\u0010(\u001a\u00020\u00102\u0006\u0010)\u001a\u00020\u0010H\u0002J\f\u0010*\u001a\u00020$*\u00020$H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0017\u001a\u00020\u0018*\u00020\u00198BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u001a¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/FunctionNVarargBridgeLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "FUNCTIONS_PACKAGE_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "REFLECT_PACKAGE_FQ_NAME", "arrayGetFun", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getArrayGetFun", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "arrayGetFun$delegate", "Lkotlin/Lazy;", "arraySizePropertyGetter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getArraySizePropertyGetter", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "arraySizePropertyGetter$delegate", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "functionNInvokeFun", "isFunctionType", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Z", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitClassNew", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "addBridge", "invoke", "superFunction", "transformVoid", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FunctionNVarargBridgeLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final FqName FUNCTIONS_PACKAGE_FQ_NAME;
    private final FqName REFLECT_PACKAGE_FQ_NAME;

    /* JADX INFO: renamed from: arrayGetFun$delegate, reason: from kotlin metadata */
    private final Lazy arrayGetFun;

    /* JADX INFO: renamed from: arraySizePropertyGetter$delegate, reason: from kotlin metadata */
    private final Lazy arraySizePropertyGetter;
    private final JvmBackendContext context;
    private final IrSimpleFunctionSymbol functionNInvokeFun;

    public final JvmBackendContext getContext() {
        return this.context;
    }

    public FunctionNVarargBridgeLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = null;
        boolean z = false;
        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol2 : IrUtilsKt.getFunctions(context.getIr().getSymbols().getFunctionN())) {
            if (Intrinsics.areEqual(irSimpleFunctionSymbol2.getOwner().getName().toString(), "invoke")) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
            }
        }
        if (z) {
            this.functionNInvokeFun = irSimpleFunctionSymbol;
            this.arraySizePropertyGetter = LazyKt.lazy(new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.FunctionNVarargBridgeLowering$arraySizePropertyGetter$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleFunction invoke() {
                    IrProperty irProperty = null;
                    boolean z2 = false;
                    for (IrProperty irProperty2 : IrUtilsKt.getProperties(this.this$0.getContext().getIrBuiltIns().getArrayClass().getOwner())) {
                        if (Intrinsics.areEqual(irProperty2.getName().toString(), "size")) {
                            if (z2) {
                                throw new IllegalArgumentException("Sequence contains more than one matching element.");
                            }
                            z2 = true;
                            irProperty = irProperty2;
                        }
                    }
                    if (z2) {
                        IrSimpleFunction getter = irProperty.getGetter();
                        Intrinsics.checkNotNull(getter);
                        return getter;
                    }
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
            });
            this.arrayGetFun = LazyKt.lazy(new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.FunctionNVarargBridgeLowering$arrayGetFun$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleFunctionSymbol invoke() {
                    IrSimpleFunctionSymbol irSimpleFunctionSymbol3 = null;
                    boolean z2 = false;
                    for (IrSimpleFunctionSymbol irSimpleFunctionSymbol4 : IrUtilsKt.getFunctions(this.this$0.getContext().getIrBuiltIns().getArrayClass())) {
                        if (Intrinsics.areEqual(irSimpleFunctionSymbol4.getOwner().getName().toString(), "get")) {
                            if (z2) {
                                throw new IllegalArgumentException("Sequence contains more than one matching element.");
                            }
                            z2 = true;
                            irSimpleFunctionSymbol3 = irSimpleFunctionSymbol4;
                        }
                    }
                    if (!z2) {
                        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                    }
                    return irSimpleFunctionSymbol3;
                }
            });
            FqName fqNameChild = StandardNames.BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("jvm")).child(Name.identifier("functions"));
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            this.FUNCTIONS_PACKAGE_FQ_NAME = fqNameChild;
            FqName fqNameChild2 = StandardNames.BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("reflect"));
            Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
            this.REFLECT_PACKAGE_FQ_NAME = fqNameChild2;
            return;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionAccess(IrFunctionAccessExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (expression.getValueArgumentsCount() < 23 || ((!IrTypeUtilsKt.isFunctionOrKFunction(IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(expression.getSymbol().getOwner()))) && !IrTypeUtilsKt.isSuspendFunctionOrKFunction(IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(expression.getSymbol().getOwner())))) || !Intrinsics.areEqual(expression.getSymbol().getOwner().getName().asString(), "invoke"))) {
            return super.visitFunctionAccess(expression);
        }
        JvmBackendContext jvmBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(jvmBackendContext, currentScope);
        LowerUtilsKt.at(jvmIrBuilderCreateJvmIrBuilder, expression);
        JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder;
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilder, this.functionNInvokeFun);
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        irCall.setDispatchReceiver(ExpressionHelpersKt.irImplicitCast(jvmIrBuilder, transformVoid(dispatchReceiver), IrTypesKt.getDefaultType(this.context.getIr().getSymbols().getFunctionN())));
        IrArrayBuilder irArrayBuilder = new IrArrayBuilder(jvmIrBuilderCreateJvmIrBuilder, IrTypesKt.typeWith(jvmIrBuilderCreateJvmIrBuilder.getIrSymbols().getArray(), jvmIrBuilderCreateJvmIrBuilder.getContext().getIrBuiltIns().getAnyNType()));
        Iterator<Integer> it = RangesKt.until(0, expression.getValueArgumentsCount()).iterator();
        while (it.hasNext()) {
            IrExpression valueArgument = expression.getValueArgument(((IntIterator) it).nextInt());
            Intrinsics.checkNotNull(valueArgument);
            irArrayBuilder.unaryPlus(transformVoid(valueArgument));
        }
        Unit unit = Unit.INSTANCE;
        irCall.putValueArgument(0, irArrayBuilder.build());
        return irCall;
    }

    private final IrExpression transformVoid(IrExpression irExpression) {
        return irExpression.transform((IrElementTransformer<? super Object>) this, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public IrStatement visitClassNew(IrClass declaration) {
        int leavesCount;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        List<IrType> superTypes = declaration.getSuperTypes();
        ArrayList arrayList = new ArrayList();
        for (Object obj : superTypes) {
            if (obj instanceof IrSimpleType) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            IrSimpleType irSimpleType = (IrSimpleType) obj2;
            if (isFunctionType(irSimpleType) && irSimpleType.getArguments().size() > 23) {
                arrayList2.add(obj2);
            }
        }
        ArrayList<IrSimpleType> arrayList3 = arrayList2;
        if (arrayList3.isEmpty()) {
            return super.visitClassNew(declaration);
        }
        IrElementTransformerVoidKt.transformChildrenVoid(declaration, this);
        for (IrSimpleType irSimpleType2 : arrayList3) {
            declaration.setSuperTypes(CollectionsKt.minus(declaration.getSuperTypes(), irSimpleType2));
            List<IrType> superTypes2 = declaration.getSuperTypes();
            IrClassSymbol functionN = this.context.getIr().getSymbols().getFunctionN();
            Object objLast = CollectionsKt.last((List<? extends Object>) irSimpleType2.getArguments());
            Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrTypeProjection");
            declaration.setSuperTypes(CollectionsKt.plus((Collection<? extends IrSimpleType>) superTypes2, IrTypesKt.typeWith(functionN, ((IrTypeProjection) objLast).getType())));
            IrSimpleFunction irSimpleFunction = null;
            Object[] objArr = false;
            for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(declaration)) {
                IrSimpleFunction irSimpleFunction3 = irSimpleFunction2;
                List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction3.getOverriddenSymbols();
                if (!(overriddenSymbols instanceof Collection) || !overriddenSymbols.isEmpty()) {
                    Iterator<T> it = overriddenSymbols.iterator();
                    while (it.hasNext()) {
                        if (Intrinsics.areEqual(((IrSimpleFunctionSymbol) it.next()).getOwner().getName().asString(), "invoke")) {
                            int size = irSimpleFunction3.getValueParameters().size();
                            int i = 0;
                            for (IrTypeArgument irTypeArgument : irSimpleType2.getArguments()) {
                                IrType typeOrNull = IrTypesKt.getTypeOrNull(irTypeArgument);
                                if (typeOrNull == null || !MfvcNodeKt.needsMfvcFlattening(typeOrNull)) {
                                    leavesCount = 1;
                                } else {
                                    Function1<IrClass, RootMfvcNode> getRootMfvcNode = this.context.getMultiFieldValueClassReplacements().getGetRootMfvcNode();
                                    IrType typeOrNull2 = IrTypesKt.getTypeOrNull(irTypeArgument);
                                    Intrinsics.checkNotNull(typeOrNull2);
                                    leavesCount = getRootMfvcNode.invoke(JvmIrTypeUtilsKt.getErasedUpperBound(typeOrNull2)).getLeavesCount();
                                }
                                i += leavesCount;
                            }
                            if (size != i - (!irSimpleFunction3.getIsSuspend() ? 1 : 0)) {
                                break;
                            }
                            if (objArr != true) {
                                objArr = true;
                                irSimpleFunction = irSimpleFunction2;
                                break;
                            }
                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                        }
                    }
                }
            }
            if (objArr != false) {
                IrSimpleFunction irSimpleFunction4 = irSimpleFunction;
                irSimpleFunction4.setOverriddenSymbols(CollectionsKt.emptyList());
                addBridge(declaration, irSimpleFunction4, this.functionNInvokeFun.getOwner());
            } else {
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
        }
        return declaration;
    }

    private final boolean isFunctionType(IrType irType) {
        IrClass owner;
        FqName packageFqName;
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        if (classOrNull == null || (owner = classOrNull.getOwner()) == null) {
            return false;
        }
        String strAsString = owner.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        IrDeclarationParent parent = owner.getParent();
        IrPackageFragment irPackageFragment = parent instanceof IrPackageFragment ? (IrPackageFragment) parent : null;
        if (irPackageFragment == null || (packageFqName = irPackageFragment.getPackageFqName()) == null) {
            return false;
        }
        if (StringsKt.startsWith$default(strAsString, "Function", false, 2, (Object) null)) {
            return Intrinsics.areEqual(packageFqName, StandardNames.BUILT_INS_PACKAGE_FQ_NAME) || Intrinsics.areEqual(packageFqName, this.FUNCTIONS_PACKAGE_FQ_NAME);
        }
        if (StringsKt.startsWith$default(strAsString, StandardNames.K_FUNCTION_PREFIX, false, 2, (Object) null)) {
            return Intrinsics.areEqual(packageFqName, this.REFLECT_PACKAGE_FQ_NAME);
        }
        return false;
    }

    private final IrSimpleFunction getArraySizePropertyGetter() {
        return (IrSimpleFunction) this.arraySizePropertyGetter.getValue();
    }

    private final IrSimpleFunctionSymbol getArrayGetFun() {
        return (IrSimpleFunctionSymbol) this.arrayGetFun.getValue();
    }

    private final IrSimpleFunction addBridge(IrClass irClass, IrSimpleFunction irSimpleFunction, IrSimpleFunction irSimpleFunction2) {
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setName(irSimpleFunction2.getName());
        irFunctionBuilder.setReturnType(this.context.getIrBuiltIns().getAnyNType());
        irFunctionBuilder.setModality(Modality.FINAL);
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        irFunctionBuilder.setVisibility(PUBLIC);
        irFunctionBuilder.setOrigin(IrDeclarationOrigin.BRIDGE.INSTANCE);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
        IrClass irClass2 = irClass;
        irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setParent(irClass2);
        irSimpleFunctionBuildFunction.setOverriddenSymbols(CollectionsKt.plus((Collection<? extends IrSimpleFunctionSymbol>) irSimpleFunctionBuildFunction.getOverriddenSymbols(), irSimpleFunction2.getSymbol()));
        IrValueParameter thisReceiver = irClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        IrSimpleFunction irSimpleFunction3 = irSimpleFunctionBuildFunction;
        irSimpleFunctionBuildFunction.setDispatchReceiverParameter(IrUtilsKt.copyTo$default(thisReceiver, irSimpleFunction3, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
        IrType irType = null;
        irSimpleFunctionBuildFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunctionBuildFunction.getValueParameters(), IrUtilsKt.copyTo$default((IrValueParameter) CollectionsKt.single((List) irSimpleFunction2.getValueParameters()), irSimpleFunction3, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null)));
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), irSimpleFunctionBuildFunction.getStartOffset(), irSimpleFunctionBuildFunction.getEndOffset());
        int size = irSimpleFunction.getValueParameters().size();
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder2, getArraySizePropertyGetter());
        irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBodyBuilder2, (IrValueDeclaration) CollectionsKt.single((List) irSimpleFunctionBuildFunction.getValueParameters())));
        Unit unit = Unit.INSTANCE;
        IrExpression irExpressionIrNotEquals = ExpressionHelpersKt.irNotEquals(irBlockBodyBuilder2, irFunctionAccessExpressionIrCall, ExpressionHelpersKt.irInt$default(irBlockBodyBuilder2, size, null, 2, null));
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder2, irBlockBodyBuilder.getContext().getIrBuiltIns().getIllegalArgumentExceptionSymbol());
        irCall.putValueArgument(0, ExpressionHelpersKt.irString(irBlockBodyBuilder2, "Expected " + size + " arguments"));
        Unit unit2 = Unit.INSTANCE;
        irBlockBodyBuilder.unaryPlus(LowerUtilsKt.irIfThen(irBlockBodyBuilder2, irExpressionIrNotEquals, irCall));
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(irBlockBodyBuilder2, irSimpleFunction);
        IrValueParameter dispatchReceiverParameter = irSimpleFunctionBuildFunction.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        irFunctionAccessExpressionIrCall2.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBodyBuilder2, dispatchReceiverParameter));
        Iterator<IrValueParameter> it = irSimpleFunction.getValueParameters().iterator();
        while (it.hasNext()) {
            int index = it.next().getIndex();
            irFunctionAccessExpressionIrCall2.putValueArgument(index, ExpressionHelpersKt.irImplicitCast(irBlockBodyBuilder2, ExpressionHelpersKt.irCallOp(irBlockBodyBuilder2, getArrayGetFun(), irBlockBodyBuilder.getContext().getIrBuiltIns().getAnyNType(), ExpressionHelpersKt.irGet(irBlockBodyBuilder2, (IrValueDeclaration) CollectionsKt.single((List) irSimpleFunctionBuildFunction.getValueParameters())), (24 & 8) != 0 ? null : ExpressionHelpersKt.irInt$default(irBlockBodyBuilder2, index, irType, 2, irType), (24 & 16) != 0 ? null : null), irSimpleFunction.getValueParameters().get(index).getType()));
            irType = null;
        }
        Unit unit3 = Unit.INSTANCE;
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, irFunctionAccessExpressionIrCall2));
        irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
        return irSimpleFunctionBuildFunction;
    }
}
