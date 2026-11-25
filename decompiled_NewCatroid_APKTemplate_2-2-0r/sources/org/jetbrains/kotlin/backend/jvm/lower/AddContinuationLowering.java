package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrCoroutineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.MethodSignatureMapper;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformerKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrDynamicExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: AddContinuationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0002J*\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\r\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u001e\u0010\u0018\u001a\u00020\u0019*\u00020\u000f2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J6\u0010\u001c\u001a\u00020\u0007*\u00020\u000f2\u0006\u0010\r\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001f\u001a\u00020\u0016H\u0002J\f\u0010 \u001a\u00020!*\u00020!H\u0002¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/AddContinuationLowering;", "Lorg/jetbrains/kotlin/backend/jvm/lower/SuspendLoweringUtils;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "addContinuationObjectAndContinuationParameterToSuspendFunctions", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "addContinuationParameterToSuspendCalls", "createStaticSuspendImpl", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "irFunction", "generateContinuationClassForNamedFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "dispatchReceiverParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "attributeContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "capturesCrossinline", "", "lower", "addConstructorForNamedFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "capturedThisField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "addInvokeSuspendForNamedFunction", "resultField", "labelField", "isStaticSuspendImpl", "toSuspendImplementationName", "Lorg/jetbrains/kotlin/name/Name;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class AddContinuationLowering extends SuspendLoweringUtils implements FileLoweringPass {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AddContinuationLowering(JvmBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        addContinuationObjectAndContinuationParameterToSuspendFunctions(irFile);
        addContinuationParameterToSuspendCalls(irFile);
    }

    private final void addContinuationParameterToSuspendCalls(IrFile irFile) {
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.jvm.lower.AddContinuationLowering.addContinuationParameterToSuspendCalls.1
            private final List<IrFunction> functionStack = new ArrayList();

            public final List<IrFunction> getFunctionStack() {
                return this.functionStack;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrStatement visitFunction(IrFunction declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                UtilsKt.push(this.functionStack, declaration);
                IrStatement irStatementVisitFunction = super.visitFunction(declaration);
                UtilsKt.pop(this.functionStack);
                return irStatementVisitFunction;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitFunctionReference(IrFunctionReference expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrExpression irExpressionVisitFunctionReference = super.visitFunctionReference(expression);
                Intrinsics.checkNotNull(irExpressionVisitFunctionReference, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionReference");
                return AddContinuationLoweringKt.retargetToSuspendView((IrFunctionReference) irExpressionVisitFunctionReference, AddContinuationLowering.this.getContext(), null, new Function2<IrFunctionReference, IrSimpleFunctionSymbol, IrFunctionReference>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.AddContinuationLowering$addContinuationParameterToSuspendCalls$1$visitFunctionReference$1
                    @Override // kotlin.jvm.functions.Function2
                    public final IrFunctionReference invoke(IrFunctionReference retargetToSuspendView, IrSimpleFunctionSymbol it) {
                        Intrinsics.checkNotNullParameter(retargetToSuspendView, "$this$retargetToSuspendView");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return IrFunctionReferenceImpl.INSTANCE.fromSymbolOwner(retargetToSuspendView.getStartOffset(), retargetToSuspendView.getEndOffset(), retargetToSuspendView.getType(), it, retargetToSuspendView.getTypeArgumentsCount(), retargetToSuspendView.getReflectionTarget(), retargetToSuspendView.getOrigin());
                    }
                });
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrExpression irExpressionVisitCall = super.visitCall(expression);
                Intrinsics.checkNotNull(irExpressionVisitCall, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrCall");
                IrCall irCall = (IrCall) irExpressionVisitCall;
                IrCall irCall2 = irCall;
                JvmBackendContext context = AddContinuationLowering.this.getContext();
                IrFunction irFunction = (IrFunction) UtilsKt.peek(this.functionStack);
                return irFunction == null ? irCall : AddContinuationLoweringKt.retargetToSuspendView(irCall2, context, irFunction, new Function2<IrCall, IrSimpleFunctionSymbol, IrCall>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.AddContinuationLowering$addContinuationParameterToSuspendCalls$1$visitCall$1
                    @Override // kotlin.jvm.functions.Function2
                    public final IrCall invoke(IrCall retargetToSuspendView, IrSimpleFunctionSymbol it) {
                        Intrinsics.checkNotNullParameter(retargetToSuspendView, "$this$retargetToSuspendView");
                        Intrinsics.checkNotNullParameter(it, "it");
                        return IrCallImpl.INSTANCE.fromSymbolOwner(retargetToSuspendView.getStartOffset(), retargetToSuspendView.getEndOffset(), retargetToSuspendView.getType(), it, (240 & 16) != 0 ? it.getOwner().getTypeParameters().size() : 0, (240 & 32) != 0 ? it.getOwner().getValueParameters().size() : 0, (240 & 64) != 0 ? null : retargetToSuspendView.getOrigin(), (240 & 128) != 0 ? null : retargetToSuspendView.getSuperQualifierSymbol());
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClass generateContinuationClassForNamedFunction(IrFunction irFunction, IrValueParameter dispatchReceiverParameter, IrAttributeContainer attributeContainer, boolean capturesCrossinline) {
        DescriptorVisibility PACKAGE_VISIBILITY;
        IrField irFieldBuildField;
        IrFactory irFactory = getContext().getIrFactory();
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        Name nameSpecial = Name.special("<Continuation>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        irClassBuilder.setName(nameSpecial);
        irClassBuilder.setOrigin(JvmLoweredDeclarationOrigin.CONTINUATION_CLASS.INSTANCE);
        if (capturesCrossinline) {
            PACKAGE_VISIBILITY = DescriptorVisibilities.PUBLIC;
            Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY, "PUBLIC");
        } else {
            PACKAGE_VISIBILITY = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
            Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY, "PACKAGE_VISIBILITY");
        }
        irClassBuilder.setVisibility(PACKAGE_VISIBILITY);
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
        IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
        irClassBuildClass.setSuperTypes(CollectionsKt.plus((Collection<? extends IrSimpleType>) irClassBuildClass.getSuperTypes(), IrUtilsKt.getDefaultType(getContext().getIr().getSymbols().getContinuationImplClass().getOwner())));
        irClassBuildClass.setParent(irFunction);
        IrUtilsKt.copyTypeParametersFrom$default(irClassBuildClass, irFunction, null, null, 6, null);
        IrFactory factory = irClassBuildClass.getFactory();
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        irFieldBuilder.setOrigin(JvmLoweredDeclarationOrigin.CONTINUATION_CLASS_RESULT_FIELD.INSTANCE);
        Name nameIdentifier = Name.identifier("result");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFieldBuilder.setName(nameIdentifier);
        irFieldBuilder.setType(getContext().getIr().getSymbols().getResultOfAnyType());
        DescriptorVisibility PACKAGE_VISIBILITY2 = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
        Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY2, "PACKAGE_VISIBILITY");
        irFieldBuilder.setVisibility(PACKAGE_VISIBILITY2);
        IrField irFieldBuildField2 = DeclarationBuildersKt.buildField(factory, irFieldBuilder);
        IrClass irClass = irClassBuildClass;
        irFieldBuildField2.setParent(irClass);
        irClassBuildClass.getDeclarations().add(irFieldBuildField2);
        if (dispatchReceiverParameter != null) {
            IrFactory factory2 = irClassBuildClass.getFactory();
            IrFieldBuilder irFieldBuilder2 = new IrFieldBuilder();
            Name nameIdentifier2 = Name.identifier(AsmUtil.CAPTURED_THIS_FIELD);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
            irFieldBuilder2.setName(nameIdentifier2);
            irFieldBuilder2.setType(dispatchReceiverParameter.getType());
            irFieldBuilder2.setOrigin(IrDeclarationOrigin.FIELD_FOR_OUTER_THIS.INSTANCE);
            DescriptorVisibility PACKAGE_VISIBILITY3 = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
            Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY3, "PACKAGE_VISIBILITY");
            irFieldBuilder2.setVisibility(PACKAGE_VISIBILITY3);
            irFieldBuilder2.setFinal(true);
            irFieldBuildField = DeclarationBuildersKt.buildField(factory2, irFieldBuilder2);
            irFieldBuildField.setParent(irClass);
            irClassBuildClass.getDeclarations().add(irFieldBuildField);
        } else {
            irFieldBuildField = null;
        }
        IrField irField = irFieldBuildField;
        IrType intType = getContext().getIrBuiltIns().getIntType();
        DescriptorVisibility PACKAGE_VISIBILITY4 = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
        Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY4, "PACKAGE_VISIBILITY");
        IrField irFieldAddField = DeclarationBuildersKt.addField(irClassBuildClass, CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, intType, PACKAGE_VISIBILITY4);
        addConstructorForNamedFunction(irClassBuildClass, irField, capturesCrossinline);
        addInvokeSuspendForNamedFunction(irClassBuildClass, irFunction, irFieldBuildField2, irFieldAddField, irField, Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.SUSPEND_IMPL_STATIC_FUNCTION.INSTANCE));
        IrDeclarationsKt.copyAttributes(irClassBuildClass, attributeContainer);
        return irClassBuildClass;
    }

    private final void addInvokeSuspendForNamedFunction(final IrClass irClass, final IrFunction irFunction, final IrField irField, final IrField irField2, final IrField irField3, final boolean z) {
        final JvmBackendContext context = getContext();
        IrSimpleFunction irSimpleFunction = null;
        boolean z2 = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(getContext().getIr().getSymbols().getContinuationImplClass().getOwner())) {
            if (Intrinsics.areEqual(irSimpleFunction2.getName(), Name.identifier(CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME))) {
                if (z2) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z2 = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (z2) {
            addFunctionOverride(irClass, irSimpleFunction, irFunction.getStartOffset(), irFunction.getEndOffset(), new Function2<IrBlockBodyBuilder, IrFunction, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.AddContinuationLowering.addInvokeSuspendForNamedFunction.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(IrBlockBodyBuilder irBlockBodyBuilder, IrFunction irFunction2) {
                    invoke2(irBlockBodyBuilder, irFunction2);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(IrBlockBodyBuilder addFunctionOverride, IrFunction function) {
                    IrGetFieldImpl irGetFieldImplIrGetField$default;
                    Intrinsics.checkNotNullParameter(addFunctionOverride, "$this$addFunctionOverride");
                    Intrinsics.checkNotNullParameter(function, "function");
                    IrBlockBodyBuilder irBlockBodyBuilder = addFunctionOverride;
                    IrValueParameter dispatchReceiverParameter = function.getDispatchReceiverParameter();
                    Intrinsics.checkNotNull(dispatchReceiverParameter);
                    addFunctionOverride.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder, ExpressionHelpersKt.irGet(irBlockBodyBuilder, dispatchReceiverParameter), irField, ExpressionHelpersKt.irGet(irBlockBodyBuilder, function.getValueParameters().get(0)), null, 8, null));
                    IrValueParameter dispatchReceiverParameter2 = function.getDispatchReceiverParameter();
                    Intrinsics.checkNotNull(dispatchReceiverParameter2);
                    IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBodyBuilder, dispatchReceiverParameter2);
                    IrField irField4 = irField2;
                    boolean z3 = false;
                    IrSimpleFunctionSymbol irSimpleFunctionSymbol = null;
                    for (IrSimpleFunctionSymbol irSimpleFunctionSymbol2 : IrUtilsKt.getFunctions(addFunctionOverride.getContext().getIrBuiltIns().getIntClass())) {
                        if (Intrinsics.areEqual(irSimpleFunctionSymbol2.getOwner().getName(), OperatorNameConventions.OR)) {
                            if (z3) {
                                throw new IllegalArgumentException("Sequence contains more than one matching element.");
                            }
                            irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
                            z3 = true;
                        }
                    }
                    if (z3) {
                        IrType intType = addFunctionOverride.getContext().getIrBuiltIns().getIntType();
                        IrValueParameter dispatchReceiverParameter3 = function.getDispatchReceiverParameter();
                        Intrinsics.checkNotNull(dispatchReceiverParameter3);
                        addFunctionOverride.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder, irGetValueImplIrGet, irField4, ExpressionHelpersKt.irCallOp(irBlockBodyBuilder, irSimpleFunctionSymbol, intType, ExpressionHelpersKt.irGetField$default(irBlockBodyBuilder, ExpressionHelpersKt.irGet(irBlockBodyBuilder, dispatchReceiverParameter3), irField2, null, 4, null), (24 & 8) != 0 ? null : ExpressionHelpersKt.irInt$default(irBlockBodyBuilder, Integer.MIN_VALUE, null, 2, null), (24 & 16) != 0 ? null : null), null, 8, null));
                        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder, irFunction);
                        IrFunction irFunction2 = irFunction;
                        IrClass irClass2 = irClass;
                        IrField irField5 = irField3;
                        JvmBackendContext jvmBackendContext = context;
                        boolean z4 = z;
                        int size = irFunction2.getTypeParameters().size();
                        for (int i = 0; i < size; i++) {
                            irFunctionAccessExpressionIrCall.putTypeArgument(i, IrTypesKt.getDefaultType(irClass2.getTypeParameters().get(i)));
                        }
                        if (irField5 != null) {
                            IrValueParameter dispatchReceiverParameter4 = function.getDispatchReceiverParameter();
                            Intrinsics.checkNotNull(dispatchReceiverParameter4);
                            irGetFieldImplIrGetField$default = ExpressionHelpersKt.irGetField$default(irBlockBodyBuilder, ExpressionHelpersKt.irGet(irBlockBodyBuilder, dispatchReceiverParameter4), irField5, null, 4, null);
                        } else {
                            irGetFieldImplIrGetField$default = null;
                        }
                        if (irFunction2.getDispatchReceiverParameter() != null) {
                            irFunctionAccessExpressionIrCall.setDispatchReceiver(irGetFieldImplIrGetField$default);
                        }
                        IrValueParameter extensionReceiverParameter = irFunction2.getExtensionReceiverParameter();
                        if (extensionReceiverParameter != null) {
                            irFunctionAccessExpressionIrCall.setExtensionReceiver(JvmIrTypeUtilsKt.defaultValue(extensionReceiverParameter.getType(), -1, -1, jvmBackendContext));
                        }
                        Iterator it = CollectionsKt.dropLast(irFunction2.getValueParameters(), 1).iterator();
                        int i2 = 0;
                        while (it.hasNext()) {
                            irFunctionAccessExpressionIrCall.putValueArgument(i2, JvmIrTypeUtilsKt.defaultValue(((IrValueParameter) it.next()).getType(), -1, -1, jvmBackendContext));
                            i2++;
                        }
                        int size2 = irFunction2.getValueParameters().size() - 1;
                        IrValueParameter dispatchReceiverParameter5 = function.getDispatchReceiverParameter();
                        Intrinsics.checkNotNull(dispatchReceiverParameter5);
                        irFunctionAccessExpressionIrCall.putValueArgument(size2, new IrGetValueImpl(-1, -1, dispatchReceiverParameter5.getSymbol(), null, 8, null));
                        if (z4) {
                            irFunctionAccessExpressionIrCall.putValueArgument(0, irGetFieldImplIrGetField$default);
                        }
                        Unit unit = Unit.INSTANCE;
                        addFunctionOverride.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder, irFunctionAccessExpressionIrCall));
                        return;
                    }
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
            });
            return;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private final Name toSuspendImplementationName(Name name2) {
        Name nameGuessByFirstCharacter = Name.guessByFirstCharacter(name2.asString() + CoroutineCodegenUtilKt.SUSPEND_IMPL_NAME_SUFFIX);
        Intrinsics.checkNotNullExpressionValue(nameGuessByFirstCharacter, "guessByFirstCharacter(...)");
        return nameGuessByFirstCharacter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction createStaticSuspendImpl(final IrSimpleFunction irFunction) {
        DescriptorVisibility descriptorVisibility;
        IrFactory irFactory = getContext().getIrFactory();
        IrDeclarationParent parent = irFunction.getParent();
        Name suspendImplementationName = toSuspendImplementationName(irFunction.getName());
        IrSimpleFunction irSimpleFunction = irFunction;
        JvmLoweredDeclarationOrigin.SUSPEND_IMPL_STATIC_FUNCTION suspend_impl_static_function = JvmLoweredDeclarationOrigin.SUSPEND_IMPL_STATIC_FUNCTION.INSTANCE;
        Modality modality = Modality.OPEN;
        IrSimpleFunction irSimpleFunction2 = irFunction;
        if (JvmIrUtilsKt.isJvmInterface(IrUtilsKt.getParentAsClass(irSimpleFunction2))) {
            descriptorVisibility = DescriptorVisibilities.PUBLIC;
        } else {
            descriptorVisibility = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
        }
        DescriptorVisibility descriptorVisibility2 = descriptorVisibility;
        Intrinsics.checkNotNull(descriptorVisibility2);
        IrSimpleFunction irSimpleFunctionCreateStaticFunctionWithReceivers$default = IrUtilsKt.createStaticFunctionWithReceivers$default(irFactory, parent, suspendImplementationName, irSimpleFunction, null, suspend_impl_static_function, modality, descriptorVisibility2, false, false, IrTypeSystemContextKt.extractTypeParameters(IrUtilsKt.getParentAsClass(irSimpleFunction2)), new AddContinuationLowering$createStaticSuspendImpl$static$1(getContext()), 8, null);
        IrSimpleFunction irSimpleFunction3 = irSimpleFunctionCreateStaticFunctionWithReceivers$default;
        irSimpleFunctionCreateStaticFunctionWithReceivers$default.setBody(IrInlineUtilsKt.moveBodyTo(irSimpleFunction, irSimpleFunction3));
        int i = 0;
        if (IrUtilsKt.getParentAsClass(irSimpleFunction2).getIsInner()) {
            final IrValueParameter irValueParameter = irSimpleFunctionCreateStaticFunctionWithReceivers$default.getValueParameters().get(0);
            Intrinsics.areEqual(irValueParameter.getOrigin(), IrDeclarationOrigin.MOVED_DISPATCH_RECEIVER.INSTANCE);
            IrBody body = irSimpleFunctionCreateStaticFunctionWithReceivers$default.getBody();
            Intrinsics.checkNotNull(body);
            IrElementTransformerVoidKt.transformChildrenVoid(body, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.jvm.lower.AddContinuationLowering.createStaticSuspendImpl.2
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitGetValue(IrGetValue expression) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    IrValueDeclaration owner = expression.getSymbol().getOwner();
                    if ((owner instanceof IrValueParameter) && isInstanceReceiverOfOuterClass((IrValueParameter) owner)) {
                        IrGetFieldImpl irGetValueImpl = new IrGetValueImpl(expression.getStartOffset(), expression.getEndOffset(), irValueParameter.getSymbol(), null, 8, null);
                        for (IrClass parentAsClass = IrUtilsKt.getParentAsClass(irFunction); !Intrinsics.areEqual(parentAsClass, owner.getParent()); parentAsClass = IrUtilsKt.getParentAsClass(parentAsClass)) {
                            IrField outerThisField = this.getContext().getInnerClassesSupport().getOuterThisField(parentAsClass);
                            irGetValueImpl = new IrGetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), outerThisField.getSymbol(), outerThisField.getType(), irGetValueImpl, null, null, 96, null);
                        }
                        return irGetValueImpl;
                    }
                    return super.visitGetValue(expression);
                }

                private final boolean isInstanceReceiverOfOuterClass(IrValueParameter param) {
                    if (!Intrinsics.areEqual(param.getOrigin(), IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE) || !(param.getParent() instanceof IrClass)) {
                        return false;
                    }
                    for (IrDeclarationParent parent2 = IrUtilsKt.getParentAsClass(irFunction).getParent(); parent2 instanceof IrClass; parent2 = ((IrClass) parent2).getParent()) {
                        if (Intrinsics.areEqual(parent2, param.getParent())) {
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
        IrDeclarationsKt.copyAttributes(irSimpleFunctionCreateStaticFunctionWithReceivers$default, irFunction);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(getContext(), irFunction.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder2, irSimpleFunction3);
        int size = irFunction.getTypeParameters().size();
        for (int i2 = 0; i2 < size; i2++) {
            irFunctionAccessExpressionIrCall.putTypeArgument(i2, irBlockBodyBuilder.getContext().getIrBuiltIns().getAnyNType());
        }
        if (irFunction.getDispatchReceiverParameter() != null) {
            IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            irFunctionAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, dispatchReceiverParameter));
            i = 1;
        }
        if (irFunction.getExtensionReceiverParameter() != null) {
            IrValueParameter extensionReceiverParameter = irFunction.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            irFunctionAccessExpressionIrCall.putValueArgument(i, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, extensionReceiverParameter));
            i++;
        }
        Iterator<IrValueParameter> it = irFunction.getValueParameters().iterator();
        while (it.hasNext()) {
            irFunctionAccessExpressionIrCall.putValueArgument(i, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, it.next()));
            i++;
        }
        Unit unit = Unit.INSTANCE;
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, irFunctionAccessExpressionIrCall));
        irFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
        return irSimpleFunctionCreateStaticFunctionWithReceivers$default;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.jvm.lower.AddContinuationLowering$addContinuationObjectAndContinuationParameterToSuspendFunctions$1, reason: invalid class name */
    /* JADX INFO: compiled from: AddContinuationLowering.kt */
    @Metadata(d1 = {"\u0000-\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\f\u0010\u000b\u001a\u00020\f*\u00020\u0006H\u0002¨\u0006\r"}, d2 = {"org/jetbrains/kotlin/backend/jvm/lower/AddContinuationLowering$addContinuationObjectAndContinuationParameterToSuspendFunctions$1", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "transformToView", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "isCapturingCrossinline", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 extends IrElementTransformerVoid {
        AnonymousClass1() {
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrStatement visitClass(IrClass declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            TransformKt.transformDeclarationsFlat(declaration, new Function1<IrDeclaration, List<? extends IrDeclaration>>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.AddContinuationLowering$addContinuationObjectAndContinuationParameterToSuspendFunctions$1$visitClass$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final List<IrDeclaration> invoke(IrDeclaration it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it instanceof IrSimpleFunction) {
                        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) it;
                        if (irSimpleFunction.getIsSuspend()) {
                            return this.this$0.transformToView(irSimpleFunction);
                        }
                    }
                    it.accept(this.this$0, null);
                    return null;
                }
            });
            return declaration;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<IrFunction> transformToView(IrSimpleFunction function) {
            DescriptorVisibility PRIVATE;
            IrSimpleFunction irSimpleFunctionCreateStaticSuspendImpl;
            function.accept(this, null);
            boolean zIsCapturingCrossinline = isCapturingCrossinline(function);
            IrSimpleFunction irSimpleFunctionSuspendFunctionViewOrStub = AddContinuationLoweringKt.suspendFunctionViewOrStub(function, AddContinuationLowering.this.getContext());
            IrSimpleFunction irSimpleFunction = irSimpleFunctionSuspendFunctionViewOrStub;
            IrValueParameter irValueParameterContinuationParameter = JvmIrCoroutineUtilsKt.continuationParameter(irSimpleFunction);
            IrSimpleFunction irSimpleFunction2 = function;
            List<IrValueParameter> explicitParameters = IrUtilsKt.getExplicitParameters(irSimpleFunction2);
            List<IrValueParameter> explicitParameters2 = IrUtilsKt.getExplicitParameters(irSimpleFunction);
            ArrayList arrayList = new ArrayList();
            for (Object obj : explicitParameters2) {
                if (!Intrinsics.areEqual((IrValueParameter) obj, irValueParameterContinuationParameter)) {
                    arrayList.add(obj);
                }
            }
            irSimpleFunctionSuspendFunctionViewOrStub.setBody(IrInlineUtilsKt.moveBodyTo(irSimpleFunction2, irSimpleFunction, MapsKt.toMap(CollectionsKt.zip(explicitParameters, arrayList))));
            List<IrFunction> listMutableListOf = CollectionsKt.mutableListOf(irSimpleFunctionSuspendFunctionViewOrStub);
            if (function.getBody() != null && JvmIrCoroutineUtilsKt.hasContinuation(irSimpleFunction2)) {
                IrSimpleFunction irSimpleFunction3 = function;
                if (Intrinsics.areEqual(IrUtilsKt.getParentAsClass(irSimpleFunction3).getOrigin(), JvmLoweredDeclarationOrigin.LAMBDA_IMPL.INSTANCE) || Intrinsics.areEqual(IrUtilsKt.getParentAsClass(irSimpleFunction3).getOrigin(), JvmLoweredDeclarationOrigin.FUNCTION_REFERENCE_IMPL.INSTANCE)) {
                    JvmBackendContext context = AddContinuationLowering.this.getContext();
                    IrAttributeContainer attributeOwnerId = function.getAttributeOwnerId();
                    StringBuilder sb = new StringBuilder();
                    Type localClassType = AddContinuationLowering.this.getContext().getLocalClassType(IrUtilsKt.getParentAsClass(irSimpleFunction3));
                    Intrinsics.checkNotNull(localClassType);
                    Type objectType = Type.getObjectType(sb.append(localClassType.getInternalName()).append('$').append(function.getName()).append("$1").toString());
                    Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
                    context.putLocalClassType(attributeOwnerId, objectType);
                }
                if (zIsCapturingCrossinline || function.getIsInline()) {
                    List<IrFunction> list = listMutableListOf;
                    IrFactory irFactory = AddContinuationLowering.this.getContext().getIrFactory();
                    AddContinuationLowering addContinuationLowering = AddContinuationLowering.this;
                    IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                    irFunctionBuilder.setContainerSource(irSimpleFunctionSuspendFunctionViewOrStub.getContainerSource());
                    Name nameIdentifier = Name.identifier(MethodSignatureMapper.mapFunctionName$default(addContinuationLowering.getContext().getDefaultMethodSignatureMapper(), irSimpleFunction, false, 2, null) + CoroutineTransformerKt.FOR_INLINE_SUFFIX);
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    irFunctionBuilder.setName(nameIdentifier);
                    irFunctionBuilder.setReturnType(irSimpleFunctionSuspendFunctionViewOrStub.getReturnType());
                    irFunctionBuilder.setModality(irSimpleFunctionSuspendFunctionViewOrStub.getModality());
                    irFunctionBuilder.setSuspend(irSimpleFunctionSuspendFunctionViewOrStub.getIsSuspend());
                    irFunctionBuilder.setInline(irSimpleFunctionSuspendFunctionViewOrStub.getIsInline());
                    if (irSimpleFunctionSuspendFunctionViewOrStub.getIsInline()) {
                        PRIVATE = DescriptorVisibilities.PRIVATE;
                        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
                    } else {
                        PRIVATE = irSimpleFunctionSuspendFunctionViewOrStub.getVisibility();
                    }
                    irFunctionBuilder.setVisibility(PRIVATE);
                    irFunctionBuilder.setOrigin(irSimpleFunctionSuspendFunctionViewOrStub.getIsInline() ? JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE.INSTANCE : JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE_CAPTURES_CROSSINLINE.INSTANCE);
                    IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
                    AddContinuationLowering addContinuationLowering2 = AddContinuationLowering.this;
                    IrUtilsKt.copyAnnotationsFrom(irSimpleFunctionBuildFunction, irSimpleFunctionSuspendFunctionViewOrStub);
                    IrSimpleFunction irSimpleFunction4 = irSimpleFunctionBuildFunction;
                    IrUtilsKt.copyParameterDeclarationsFrom(irSimpleFunction4, irSimpleFunction);
                    addContinuationLowering2.getContext().remapMultiFieldValueClassStructure(irSimpleFunction, irSimpleFunction4, null);
                    IrDeclarationsKt.copyAttributes(irSimpleFunctionBuildFunction, irSimpleFunctionSuspendFunctionViewOrStub);
                    addContinuationLowering2.generateErrorForInlineBody(irSimpleFunctionBuildFunction);
                    list.add(irSimpleFunctionBuildFunction);
                }
                if (IrUtilsKt.isOverridable(function)) {
                    irSimpleFunctionCreateStaticSuspendImpl = AddContinuationLowering.this.createStaticSuspendImpl(irSimpleFunctionSuspendFunctionViewOrStub);
                    listMutableListOf.add(irSimpleFunctionCreateStaticSuspendImpl);
                } else {
                    irSimpleFunctionCreateStaticSuspendImpl = irSimpleFunctionSuspendFunctionViewOrStub;
                }
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(AddContinuationLowering.this.getContext(), irSimpleFunctionCreateStaticSuspendImpl.getSymbol(), 0, 0, 6, (Object) null);
                AddContinuationLowering addContinuationLowering3 = AddContinuationLowering.this;
                IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
                IrValueParameter dispatchReceiverParameter = irSimpleFunctionSuspendFunctionViewOrStub.getDispatchReceiverParameter();
                Intrinsics.checkNotNull(function, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrAttributeContainer");
                irBlockBodyBuilder.unaryPlus(addContinuationLowering3.generateContinuationClassForNamedFunction(irSimpleFunctionCreateStaticSuspendImpl, dispatchReceiverParameter, function, zIsCapturingCrossinline));
                if (irSimpleFunctionCreateStaticSuspendImpl.getBody() instanceof IrExpressionBody) {
                    IrBody body = irSimpleFunctionCreateStaticSuspendImpl.getBody();
                    Intrinsics.checkNotNull(body);
                    IrStatement irStatement = IrUtilsKt.getStatements(body).get(0);
                    Intrinsics.checkNotNull(irStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                    irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder, (IrExpression) irStatement));
                } else {
                    IrBody body2 = irSimpleFunctionCreateStaticSuspendImpl.getBody();
                    Intrinsics.checkNotNull(body2);
                    Iterator<IrStatement> it = IrUtilsKt.getStatements(body2).iterator();
                    while (it.hasNext()) {
                        irBlockBodyBuilder.unaryPlus(it.next());
                    }
                }
                irSimpleFunctionCreateStaticSuspendImpl.setBody(irBlockBodyBuilder.getIrBlockBody());
            }
            return listMutableListOf;
        }

        private final boolean isCapturingCrossinline(IrSimpleFunction irSimpleFunction) {
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            IrSimpleFunction originalBeforeInline = irSimpleFunction.getOriginalBeforeInline();
            if (originalBeforeInline == null) {
                originalBeforeInline = irSimpleFunction;
            }
            IrVisitorsKt.acceptVoid(originalBeforeInline, new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.backend.jvm.lower.AddContinuationLowering$addContinuationObjectAndContinuationParameterToSuspendFunctions$1$isCapturingCrossinline$1
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                /* JADX INFO: renamed from: visitClass */
                public void mo6112visitClass(IrClass declaration) {
                    Intrinsics.checkNotNullParameter(declaration, "declaration");
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitAnonymousInitializer */
                public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj) {
                    visitAnonymousInitializer(irAnonymousInitializer, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer) {
                    IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBlock */
                public /* bridge */ /* synthetic */ Object visitBlock2(IrBlock irBlock, Object obj) {
                    visitBlock(irBlock, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBlock(IrBlock irBlock) {
                    IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBlock(IrBlock irBlock, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBlockBody */
                public /* bridge */ /* synthetic */ Object visitBlockBody2(IrBlockBody irBlockBody, Object obj) {
                    visitBlockBody(irBlockBody, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBlockBody(IrBlockBody irBlockBody) {
                    IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBlockBody(IrBlockBody irBlockBody, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBody */
                public /* bridge */ /* synthetic */ Object visitBody2(IrBody irBody, Object obj) {
                    visitBody(irBody, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBody(IrBody irBody) {
                    IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBody(IrBody irBody, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBranch */
                public /* bridge */ /* synthetic */ Object visitBranch2(IrBranch irBranch, Object obj) {
                    visitBranch(irBranch, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBranch(IrBranch irBranch) {
                    IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBranch(IrBranch irBranch, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBreak */
                public /* bridge */ /* synthetic */ Object visitBreak2(IrBreak irBreak, Object obj) {
                    visitBreak(irBreak, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBreak(IrBreak irBreak) {
                    IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBreak(IrBreak irBreak, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBreakContinue */
                public /* bridge */ /* synthetic */ Object visitBreakContinue2(IrBreakContinue irBreakContinue, Object obj) {
                    visitBreakContinue(irBreakContinue, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBreakContinue(IrBreakContinue irBreakContinue) {
                    IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitBreakContinue(IrBreakContinue irBreakContinue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitCall */
                public /* bridge */ /* synthetic */ Object visitCall2(IrCall irCall, Object obj) {
                    visitCall(irCall, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitCall(IrCall irCall) {
                    IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitCall(IrCall irCall, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
                    visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitCallableReference(IrCallableReference<?> irCallableReference) {
                    IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitCatch */
                public /* bridge */ /* synthetic */ Object visitCatch2(IrCatch irCatch, Object obj) {
                    visitCatch(irCatch, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitCatch(IrCatch irCatch) {
                    IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitCatch(IrCatch irCatch, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitClass */
                public /* bridge */ /* synthetic */ Object visitClass2(IrClass irClass, Object obj) {
                    visitClass(irClass, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitClass(IrClass irClass, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitClassReference */
                public /* bridge */ /* synthetic */ Object visitClassReference2(IrClassReference irClassReference, Object obj) {
                    visitClassReference(irClassReference, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitClassReference(IrClassReference irClassReference) {
                    IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitClassReference(IrClassReference irClassReference, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitComposite */
                public /* bridge */ /* synthetic */ Object visitComposite2(IrComposite irComposite, Object obj) {
                    visitComposite(irComposite, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitComposite(IrComposite irComposite) {
                    IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitComposite(IrComposite irComposite, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
                    visitConst((IrConst<?>) irConst, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConst(IrConst<?> irConst) {
                    IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConst(IrConst<?> irConst, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantArray */
                public /* bridge */ /* synthetic */ Object visitConstantArray2(IrConstantArray irConstantArray, Object obj) {
                    visitConstantArray(irConstantArray, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantArray(IrConstantArray irConstantArray) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantArray(IrConstantArray irConstantArray, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantObject */
                public /* bridge */ /* synthetic */ Object visitConstantObject2(IrConstantObject irConstantObject, Object obj) {
                    visitConstantObject(irConstantObject, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantObject(IrConstantObject irConstantObject) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantObject(IrConstantObject irConstantObject, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantPrimitive */
                public /* bridge */ /* synthetic */ Object visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Object obj) {
                    visitConstantPrimitive(irConstantPrimitive, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantValue */
                public /* bridge */ /* synthetic */ Object visitConstantValue2(IrConstantValue irConstantValue, Object obj) {
                    visitConstantValue(irConstantValue, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantValue(IrConstantValue irConstantValue) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstantValue(IrConstantValue irConstantValue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstructor */
                public /* bridge */ /* synthetic */ Object visitConstructor2(IrConstructor irConstructor, Object obj) {
                    visitConstructor(irConstructor, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstructor(IrConstructor irConstructor) {
                    IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstructor(IrConstructor irConstructor, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstructorCall */
                public /* bridge */ /* synthetic */ Object visitConstructorCall2(IrConstructorCall irConstructorCall, Object obj) {
                    visitConstructorCall(irConstructorCall, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstructorCall(IrConstructorCall irConstructorCall) {
                    IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitConstructorCall(IrConstructorCall irConstructorCall, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitContainerExpression */
                public /* bridge */ /* synthetic */ Object visitContainerExpression2(IrContainerExpression irContainerExpression, Object obj) {
                    visitContainerExpression(irContainerExpression, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitContainerExpression(IrContainerExpression irContainerExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitContainerExpression(IrContainerExpression irContainerExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitContinue */
                public /* bridge */ /* synthetic */ Object visitContinue2(IrContinue irContinue, Object obj) {
                    visitContinue(irContinue, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitContinue(IrContinue irContinue) {
                    IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitContinue(IrContinue irContinue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDeclaration */
                public /* bridge */ /* synthetic */ Object visitDeclaration2(IrDeclarationBase irDeclarationBase, Object obj) {
                    visitDeclaration(irDeclarationBase, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDeclaration(IrDeclarationBase irDeclarationBase) {
                    IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDeclaration(IrDeclarationBase irDeclarationBase, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDeclarationReference */
                public /* bridge */ /* synthetic */ Object visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Object obj) {
                    visitDeclarationReference(irDeclarationReference, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDeclarationReference(IrDeclarationReference irDeclarationReference) {
                    IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDeclarationReference(IrDeclarationReference irDeclarationReference, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDelegatingConstructorCall */
                public /* bridge */ /* synthetic */ Object visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Object obj) {
                    visitDelegatingConstructorCall(irDelegatingConstructorCall, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall) {
                    IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDoWhileLoop */
                public /* bridge */ /* synthetic */ Object visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Object obj) {
                    visitDoWhileLoop(irDoWhileLoop, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop) {
                    IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDynamicExpression */
                public /* bridge */ /* synthetic */ Object visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Object obj) {
                    visitDynamicExpression(irDynamicExpression, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDynamicExpression(IrDynamicExpression irDynamicExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDynamicExpression(IrDynamicExpression irDynamicExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDynamicMemberExpression */
                public /* bridge */ /* synthetic */ Object visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Object obj) {
                    visitDynamicMemberExpression(irDynamicMemberExpression, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDynamicOperatorExpression */
                public /* bridge */ /* synthetic */ Object visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Object obj) {
                    visitDynamicOperatorExpression(irDynamicOperatorExpression, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitElement */
                public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement, Object obj) {
                    visitElement(irElement, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitElement(IrElement irElement, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitElseBranch */
                public /* bridge */ /* synthetic */ Object visitElseBranch2(IrElseBranch irElseBranch, Object obj) {
                    visitElseBranch(irElseBranch, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitElseBranch(IrElseBranch irElseBranch) {
                    IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitElseBranch(IrElseBranch irElseBranch, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitEnumConstructorCall */
                public /* bridge */ /* synthetic */ Object visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Object obj) {
                    visitEnumConstructorCall(irEnumConstructorCall, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall) {
                    IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitEnumEntry */
                public /* bridge */ /* synthetic */ Object visitEnumEntry2(IrEnumEntry irEnumEntry, Object obj) {
                    visitEnumEntry(irEnumEntry, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitEnumEntry(IrEnumEntry irEnumEntry) {
                    IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitEnumEntry(IrEnumEntry irEnumEntry, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitErrorCallExpression */
                public /* bridge */ /* synthetic */ Object visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Object obj) {
                    visitErrorCallExpression(irErrorCallExpression, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitErrorDeclaration */
                public /* bridge */ /* synthetic */ Object visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Object obj) {
                    visitErrorDeclaration(irErrorDeclaration, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration) {
                    IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitErrorExpression */
                public /* bridge */ /* synthetic */ Object visitErrorExpression2(IrErrorExpression irErrorExpression, Object obj) {
                    visitErrorExpression(irErrorExpression, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitErrorExpression(IrErrorExpression irErrorExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitErrorExpression(IrErrorExpression irErrorExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitExpression */
                public /* bridge */ /* synthetic */ Object visitExpression2(IrExpression irExpression, Object obj) {
                    visitExpression(irExpression, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitExpression(IrExpression irExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitExpression(IrExpression irExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitExpressionBody */
                public /* bridge */ /* synthetic */ Object visitExpressionBody2(IrExpressionBody irExpressionBody, Object obj) {
                    visitExpressionBody(irExpressionBody, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitExpressionBody(IrExpressionBody irExpressionBody) {
                    IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitExpressionBody(IrExpressionBody irExpressionBody, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitExternalPackageFragment */
                public /* bridge */ /* synthetic */ Object visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Object obj) {
                    visitExternalPackageFragment(irExternalPackageFragment, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment) {
                    IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitField */
                public /* bridge */ /* synthetic */ Object visitField2(IrField irField, Object obj) {
                    visitField(irField, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitField(IrField irField) {
                    IrElementVisitorVoid.DefaultImpls.visitField(this, irField);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitField(IrField irField, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitField(this, irField, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFieldAccess */
                public /* bridge */ /* synthetic */ Object visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Object obj) {
                    visitFieldAccess(irFieldAccessExpression, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFile */
                public /* bridge */ /* synthetic */ Object visitFile2(IrFile irFile, Object obj) {
                    visitFile(irFile, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFile(IrFile irFile) {
                    IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFile(IrFile irFile, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunction */
                public /* bridge */ /* synthetic */ Object visitFunction2(IrFunction irFunction, Object obj) {
                    visitFunction(irFunction, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunction(IrFunction irFunction) {
                    IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunction(IrFunction irFunction, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunctionAccess */
                public /* bridge */ /* synthetic */ Object visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Object obj) {
                    visitFunctionAccess(irFunctionAccessExpression, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunctionExpression */
                public /* bridge */ /* synthetic */ Object visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Object obj) {
                    visitFunctionExpression(irFunctionExpression, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunctionExpression(IrFunctionExpression irFunctionExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunctionExpression(IrFunctionExpression irFunctionExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunctionReference */
                public /* bridge */ /* synthetic */ Object visitFunctionReference2(IrFunctionReference irFunctionReference, Object obj) {
                    visitFunctionReference(irFunctionReference, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunctionReference(IrFunctionReference irFunctionReference) {
                    IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFunctionReference(IrFunctionReference irFunctionReference, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetClass */
                public /* bridge */ /* synthetic */ Object visitGetClass2(IrGetClass irGetClass, Object obj) {
                    visitGetClass(irGetClass, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetClass(IrGetClass irGetClass) {
                    IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetClass(IrGetClass irGetClass, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetEnumValue */
                public /* bridge */ /* synthetic */ Object visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Object obj) {
                    visitGetEnumValue(irGetEnumValue, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetEnumValue(IrGetEnumValue irGetEnumValue) {
                    IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetEnumValue(IrGetEnumValue irGetEnumValue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetField */
                public /* bridge */ /* synthetic */ Object visitGetField2(IrGetField irGetField, Object obj) {
                    visitGetField(irGetField, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetField(IrGetField irGetField) {
                    IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetField(IrGetField irGetField, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetObjectValue */
                public /* bridge */ /* synthetic */ Object visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Object obj) {
                    visitGetObjectValue(irGetObjectValue, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetObjectValue(IrGetObjectValue irGetObjectValue) {
                    IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetObjectValue(IrGetObjectValue irGetObjectValue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetValue */
                public /* bridge */ /* synthetic */ Object visitGetValue2(IrGetValue irGetValue, Object obj) {
                    visitGetValue(irGetValue, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetValue(IrGetValue irGetValue) {
                    IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitGetValue(IrGetValue irGetValue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitInstanceInitializerCall */
                public /* bridge */ /* synthetic */ Object visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Object obj) {
                    visitInstanceInitializerCall(irInstanceInitializerCall, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall) {
                    IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitLocalDelegatedProperty */
                public /* bridge */ /* synthetic */ Object visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Object obj) {
                    visitLocalDelegatedProperty(irLocalDelegatedProperty, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty) {
                    IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
                public /* bridge */ /* synthetic */ Object visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Object obj) {
                    visitLocalDelegatedPropertyReference(irLocalDelegatedPropertyReference, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
                    IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitLoop */
                public /* bridge */ /* synthetic */ Object visitLoop2(IrLoop irLoop, Object obj) {
                    visitLoop(irLoop, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitLoop(IrLoop irLoop) {
                    IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitLoop(IrLoop irLoop, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                    visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitModuleFragment */
                public /* bridge */ /* synthetic */ Object visitModuleFragment2(IrModuleFragment irModuleFragment, Object obj) {
                    visitModuleFragment(irModuleFragment, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitModuleFragment(IrModuleFragment irModuleFragment) {
                    IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitModuleFragment(IrModuleFragment irModuleFragment, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitPackageFragment */
                public /* bridge */ /* synthetic */ Object visitPackageFragment2(IrPackageFragment irPackageFragment, Object obj) {
                    visitPackageFragment(irPackageFragment, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitPackageFragment(IrPackageFragment irPackageFragment) {
                    IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitPackageFragment(IrPackageFragment irPackageFragment, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitProperty */
                public /* bridge */ /* synthetic */ Object visitProperty2(IrProperty irProperty, Object obj) {
                    visitProperty(irProperty, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitProperty(IrProperty irProperty) {
                    IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitProperty(IrProperty irProperty, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitPropertyReference */
                public /* bridge */ /* synthetic */ Object visitPropertyReference2(IrPropertyReference irPropertyReference, Object obj) {
                    visitPropertyReference(irPropertyReference, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitPropertyReference(IrPropertyReference irPropertyReference) {
                    IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitPropertyReference(IrPropertyReference irPropertyReference, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitRawFunctionReference */
                public /* bridge */ /* synthetic */ Object visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Object obj) {
                    visitRawFunctionReference(irRawFunctionReference, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference) {
                    IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitReturn */
                public /* bridge */ /* synthetic */ Object visitReturn2(IrReturn irReturn, Object obj) {
                    visitReturn(irReturn, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitReturn(IrReturn irReturn) {
                    IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitReturn(IrReturn irReturn, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitScript */
                public /* bridge */ /* synthetic */ Object visitScript2(IrScript irScript, Object obj) {
                    visitScript(irScript, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitScript(IrScript irScript) {
                    IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitScript(IrScript irScript, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSetField */
                public /* bridge */ /* synthetic */ Object visitSetField2(IrSetField irSetField, Object obj) {
                    visitSetField(irSetField, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSetField(IrSetField irSetField) {
                    IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSetField(IrSetField irSetField, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSetValue */
                public /* bridge */ /* synthetic */ Object visitSetValue2(IrSetValue irSetValue, Object obj) {
                    visitSetValue(irSetValue, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSetValue(IrSetValue irSetValue) {
                    IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSetValue(IrSetValue irSetValue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSimpleFunction */
                public /* bridge */ /* synthetic */ Object visitSimpleFunction2(IrSimpleFunction irSimpleFunction2, Object obj) {
                    visitSimpleFunction(irSimpleFunction2, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSimpleFunction(IrSimpleFunction irSimpleFunction2) {
                    IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSimpleFunction(IrSimpleFunction irSimpleFunction2, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction2, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSingletonReference */
                public /* bridge */ /* synthetic */ Object visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Object obj) {
                    visitSingletonReference(irGetSingletonValue, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue) {
                    IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSpreadElement */
                public /* bridge */ /* synthetic */ Object visitSpreadElement2(IrSpreadElement irSpreadElement, Object obj) {
                    visitSpreadElement(irSpreadElement, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSpreadElement(IrSpreadElement irSpreadElement) {
                    IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSpreadElement(IrSpreadElement irSpreadElement, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitStringConcatenation */
                public /* bridge */ /* synthetic */ Object visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Object obj) {
                    visitStringConcatenation(irStringConcatenation, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitStringConcatenation(IrStringConcatenation irStringConcatenation) {
                    IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitStringConcatenation(IrStringConcatenation irStringConcatenation, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSuspendableExpression */
                public /* bridge */ /* synthetic */ Object visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Object obj) {
                    visitSuspendableExpression(irSuspendableExpression, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSuspensionPoint */
                public /* bridge */ /* synthetic */ Object visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Object obj) {
                    visitSuspensionPoint(irSuspensionPoint, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint) {
                    IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSyntheticBody */
                public /* bridge */ /* synthetic */ Object visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Object obj) {
                    visitSyntheticBody(irSyntheticBody, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSyntheticBody(IrSyntheticBody irSyntheticBody) {
                    IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSyntheticBody(IrSyntheticBody irSyntheticBody, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitThrow */
                public /* bridge */ /* synthetic */ Object visitThrow2(IrThrow irThrow, Object obj) {
                    visitThrow(irThrow, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitThrow(IrThrow irThrow) {
                    IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitThrow(IrThrow irThrow, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTry */
                public /* bridge */ /* synthetic */ Object visitTry2(IrTry irTry, Object obj) {
                    visitTry(irTry, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTry(IrTry irTry) {
                    IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTry(IrTry irTry, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTypeAlias */
                public /* bridge */ /* synthetic */ Object visitTypeAlias2(IrTypeAlias irTypeAlias, Object obj) {
                    visitTypeAlias(irTypeAlias, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTypeAlias(IrTypeAlias irTypeAlias) {
                    IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTypeAlias(IrTypeAlias irTypeAlias, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTypeOperator */
                public /* bridge */ /* synthetic */ Object visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Object obj) {
                    visitTypeOperator(irTypeOperatorCall, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall) {
                    IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTypeParameter */
                public /* bridge */ /* synthetic */ Object visitTypeParameter2(IrTypeParameter irTypeParameter, Object obj) {
                    visitTypeParameter(irTypeParameter, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTypeParameter(IrTypeParameter irTypeParameter) {
                    IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitTypeParameter(IrTypeParameter irTypeParameter, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitValueAccess */
                public /* bridge */ /* synthetic */ Object visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Object obj) {
                    visitValueAccess(irValueAccessExpression, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitValueAccess(IrValueAccessExpression irValueAccessExpression) {
                    IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitValueAccess(IrValueAccessExpression irValueAccessExpression, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitValueParameter */
                public /* bridge */ /* synthetic */ Object visitValueParameter2(IrValueParameter irValueParameter, Object obj) {
                    visitValueParameter(irValueParameter, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitValueParameter(IrValueParameter irValueParameter) {
                    IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitValueParameter(IrValueParameter irValueParameter, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitVararg */
                public /* bridge */ /* synthetic */ Object visitVararg2(IrVararg irVararg, Object obj) {
                    visitVararg(irVararg, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitVararg(IrVararg irVararg) {
                    IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitVararg(IrVararg irVararg, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitVariable */
                public /* bridge */ /* synthetic */ Object visitVariable2(IrVariable irVariable, Object obj) {
                    visitVariable(irVariable, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitVariable(IrVariable irVariable) {
                    IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitVariable(IrVariable irVariable, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitWhen */
                public /* bridge */ /* synthetic */ Object visitWhen2(IrWhen irWhen, Object obj) {
                    visitWhen(irWhen, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitWhen(IrWhen irWhen) {
                    IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitWhen(IrWhen irWhen, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitWhileLoop */
                public /* bridge */ /* synthetic */ Object visitWhileLoop2(IrWhileLoop irWhileLoop, Object obj) {
                    visitWhileLoop(irWhileLoop, (Void) obj);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitWhileLoop(IrWhileLoop irWhileLoop) {
                    IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitWhileLoop(IrWhileLoop irWhileLoop, Void r2) {
                    IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                /* JADX INFO: renamed from: visitElement */
                public void mo6335visitElement(IrElement element) {
                    Intrinsics.checkNotNullParameter(element, "element");
                    IrVisitorsKt.acceptChildrenVoid(element, this);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitFieldAccess(IrFieldAccessExpression expression) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    if (Intrinsics.areEqual(expression.getSymbol().getOwner().getOrigin(), LocalDeclarationsLowering.DECLARATION_ORIGIN_FIELD_FOR_CROSSINLINE_CAPTURED_VALUE.INSTANCE)) {
                        booleanRef.element = true;
                    } else {
                        IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, expression);
                    }
                }
            });
            return booleanRef.element;
        }
    }

    private final void addContinuationObjectAndContinuationParameterToSuspendFunctions(IrFile irFile) {
        irFile.accept(new AnonymousClass1(), null);
    }

    private final IrConstructor addConstructorForNamedFunction(IrClass irClass, IrField irField, boolean z) {
        DescriptorVisibility descriptorVisibility;
        String str;
        IrValueParameter irValueParameterAddValueParameter$default;
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setPrimary(true);
        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClass));
        if (z) {
            descriptorVisibility = DescriptorVisibilities.PUBLIC;
            str = "PUBLIC";
        } else {
            descriptorVisibility = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
            str = "PACKAGE_VISIBILITY";
        }
        Intrinsics.checkNotNullExpressionValue(descriptorVisibility, str);
        irFunctionBuilder.setVisibility(descriptorVisibility);
        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClass));
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
        irClass.getDeclarations().add(irConstructorBuildConstructor);
        irConstructorBuildConstructor.setParent(irClass);
        IrConstructor irConstructor = null;
        if (irField != null) {
            String strAsString = irField.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            irValueParameterAddValueParameter$default = DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor, strAsString, irField.getType(), (IrDeclarationOrigin) null, 4, (Object) null);
        } else {
            irValueParameterAddValueParameter$default = null;
        }
        IrValueParameter irValueParameterAddCompletionValueParameter = addCompletionValueParameter(irConstructorBuildConstructor);
        boolean z2 = false;
        for (IrConstructor irConstructor2 : IrUtilsKt.getConstructors(getContext().getIr().getSymbols().getContinuationImplClass().getOwner())) {
            if (irConstructor2.getValueParameters().size() == 1) {
                if (z2) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z2 = true;
                irConstructor = irConstructor2;
            }
        }
        if (z2) {
            IrConstructor irConstructor3 = irConstructor;
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(getContext(), irConstructorBuildConstructor.getSymbol(), 0, 0, 6, (Object) null);
            IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
            if (irField != null) {
                IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                IrValueParameter thisReceiver = irClass.getThisReceiver();
                Intrinsics.checkNotNull(thisReceiver);
                IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBodyBuilder2, thisReceiver);
                Intrinsics.checkNotNull(irValueParameterAddValueParameter$default);
                irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder2, irGetValueImplIrGet, irField, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameterAddValueParameter$default), null, 8, null));
            }
            IrBlockBodyBuilder irBlockBodyBuilder3 = irBlockBodyBuilder;
            IrDelegatingConstructorCall irDelegatingConstructorCall = ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder3, irConstructor3);
            irDelegatingConstructorCall.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBodyBuilder3, irValueParameterAddCompletionValueParameter));
            irBlockBodyBuilder.unaryPlus(irDelegatingConstructorCall);
            irConstructorBuildConstructor.setBody(irBlockBodyBuilder.getIrBlockBody());
            return irConstructorBuildConstructor;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }
}
