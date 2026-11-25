package org.jetbrains.kotlin.backend.common.lower;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.backend.common.MappingsKt;
import org.jetbrains.kotlin.ir.IrElementsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextKt;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: InlineClassDeclarationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0011H\u0002J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0011H\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\f\u0010\u001a\u001a\u00020\u001b*\u00020\u0011H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/InlineClassLowering;", "", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "inlineClassDeclarationLowering", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "getInlineClassDeclarationLowering", "()Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "inlineClassUsageLowering", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "getInlineClassUsageLowering", "()Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "transformedFunction", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "createStaticBodilessMethod", "function", "getOrCreateStaticMethod", "isClassInlineLike", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "toInlineClassImplementationName", "Lorg/jetbrains/kotlin/name/Name;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineClassLowering {
    private final CommonBackendContext context;
    private final DeclarationTransformer inlineClassDeclarationLowering;
    private final BodyLoweringPass inlineClassUsageLowering;
    private final Mapping.Delegate<IrFunction, IrSimpleFunction> transformedFunction;

    public InlineClassLowering(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.transformedFunction = context.getMapping().getInlineClassMemberToStatic();
        this.inlineClassDeclarationLowering = new DeclarationTransformer() { // from class: org.jetbrains.kotlin.backend.common.lower.InlineClassLowering$inlineClassDeclarationLowering$1
            @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
            public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                IrDeclarationParent parent = declaration.getParent();
                IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
                if (irClass == null || !this.this$0.isClassInlineLike(irClass)) {
                    return null;
                }
                if (declaration instanceof IrConstructor) {
                    return transformConstructor((IrConstructor) declaration);
                }
                if (declaration instanceof IrSimpleFunction) {
                    return transformMethodFlat((IrSimpleFunction) declaration);
                }
                return null;
            }

            private final List<IrDeclaration> transformConstructor(IrConstructor irConstructor) {
                if (!irConstructor.getIsPrimary()) {
                    IrSimpleFunction orCreateStaticMethod = this.this$0.getOrCreateStaticMethod(irConstructor);
                    transformConstructorBody(irConstructor, orCreateStaticMethod);
                    return CollectionsKt.listOf(orCreateStaticMethod);
                }
                return transformPrimaryConstructor(irConstructor);
            }

            private final List<IrDeclaration> transformMethodFlat(IrSimpleFunction function) {
                IrSimpleFunction irSimpleFunction = function;
                if (IrDeclarationsKt.isStaticMethodOfClass(irSimpleFunction) || !AdditionalIrUtilsKt.isReal(irSimpleFunction)) {
                    return null;
                }
                IrSimpleFunction orCreateStaticMethod = this.this$0.getOrCreateStaticMethod(irSimpleFunction);
                transformMethodBodyFlat(function, orCreateStaticMethod);
                function.setBody(delegateToStaticMethod(function, orCreateStaticMethod));
                return function.getOverriddenSymbols().isEmpty() ? CollectionsKt.listOf(orCreateStaticMethod) : CollectionsKt.listOf((Object[]) new IrSimpleFunction[]{function, orCreateStaticMethod});
            }

            private final List<IrDeclaration> transformPrimaryConstructor(IrConstructor irConstructor) {
                IrBlockBody irBlockBodyCreateBlockBody;
                IrClass parentAsClass = IrUtilsKt.getParentAsClass(irConstructor);
                IrSimpleType defaultType = IrUtilsKt.getDefaultType(parentAsClass);
                IrSimpleFunction orCreateStaticMethod = this.this$0.getOrCreateStaticMethod(irConstructor);
                orCreateStaticMethod.setReturnType(defaultType);
                final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
                IrBody body = irConstructor.getBody();
                if (body != null) {
                    InlineClassLowering inlineClassLowering = this.this$0;
                    irBlockBodyCreateBlockBody = IrFactoryHelpersKt.createBlockBody(inlineClassLowering.getContext().getIrFactory(), -1, -1, new InlineClassLowering$inlineClassDeclarationLowering$1$transformPrimaryConstructor$1$1(irConstructor, inlineClassLowering, orCreateStaticMethod, body, objectRef, parentAsClass, objectRef2));
                } else {
                    irBlockBodyCreateBlockBody = null;
                }
                orCreateStaticMethod.setBody(irBlockBodyCreateBlockBody);
                if (irConstructor.getBody() != null) {
                    irConstructor.setBody(IrFactoryHelpersKt.createBlockBody(this.this$0.getContext().getIrFactory(), -1, -1, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.backend.common.lower.InlineClassLowering$inlineClassDeclarationLowering$1$transformPrimaryConstructor$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                            invoke2(irBlockBody);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(IrBlockBody createBlockBody) {
                            Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                            IrDelegatingConstructorCall irDelegatingConstructorCall = objectRef.element;
                            if (irDelegatingConstructorCall != null) {
                                createBlockBody.getStatements().add(irDelegatingConstructorCall);
                            }
                            IrSetField irSetField = objectRef2.element;
                            if (irSetField != null) {
                                createBlockBody.getStatements().add(irSetField);
                            }
                        }
                    }));
                }
                return CollectionsKt.listOf((Object[]) new IrFunction[]{irConstructor, orCreateStaticMethod});
            }

            private final void transformConstructorBody(final IrConstructor irConstructor, final IrSimpleFunction staticMethod) {
                IrBlockBody irBlockBodyCreateBlockBody;
                if (irConstructor.getIsPrimary()) {
                    return;
                }
                final IrClass parentAsClass = IrUtilsKt.getParentAsClass(irConstructor);
                final IrBody body = irConstructor.getBody();
                if (body != null) {
                    final InlineClassLowering inlineClassLowering = this.this$0;
                    irBlockBodyCreateBlockBody = IrFactoryHelpersKt.createBlockBody(inlineClassLowering.getContext().getIrFactory(), -1, -1, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.backend.common.lower.InlineClassLowering$inlineClassDeclarationLowering$1$transformConstructorBody$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                            invoke2(irBlockBody);
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(IrBlockBody createBlockBody) {
                            IrVariable irVariable;
                            Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                            List<IrStatement> statements = createBlockBody.getStatements();
                            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(inlineClassLowering.getContext(), staticMethod.getSymbol(), 0, 0, 6, (Object) null);
                            final IrSimpleFunction irSimpleFunction = staticMethod;
                            IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
                            IrBody irBody = body;
                            final IrConstructor irConstructor2 = irConstructor;
                            final IrClass irClass = parentAsClass;
                            final IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), irSimpleFunction2.getStartOffset(), irSimpleFunction2.getEndOffset());
                            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                            List<IrValueParameter> valueParameters = irSimpleFunction.getValueParameters();
                            final LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(valueParameters, 10)), 16));
                            for (Object obj : valueParameters) {
                                linkedHashMap.put(irConstructor2.getValueParameters().get(((IrValueParameter) obj).getIndex()).getSymbol(), obj);
                            }
                            Intrinsics.checkNotNull(irBody, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
                            Iterator<T> it = ((IrBlockBody) irBody).getStatements().iterator();
                            while (it.hasNext()) {
                                irBlockBodyBuilder.unaryPlus(IrElementsKt.transformStatement((IrStatement) it.next(), new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.InlineClassLowering$inlineClassDeclarationLowering$1$transformConstructorBody$1$1$1$1$1
                                    /* JADX WARN: Type inference failed for: r2v9, types: [T, org.jetbrains.kotlin.ir.declarations.IrVariable] */
                                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                                    public IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
                                        IrVariable irVariable2;
                                        Intrinsics.checkNotNullParameter(expression, "expression");
                                        transformChildrenVoid(expression);
                                        expression.setType(IrUtilsKt.getDefaultType(irClass));
                                        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                                        Ref.ObjectRef<IrVariable> objectRef2 = objectRef;
                                        IrClass irClass2 = irClass;
                                        IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
                                        IrDelegatingConstructorCall irDelegatingConstructorCall = expression;
                                        IrType type = irDelegatingConstructorCall.getType();
                                        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBlockBodyBuilder2.getContext(), irBlockBodyBuilder2.getScope(), irDelegatingConstructorCall.getStartOffset(), irDelegatingConstructorCall.getEndOffset(), null, type, false, 64, null);
                                        objectRef2.element = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder, (IrExpression) irDelegatingConstructorCall, (String) null, false, (IrDeclarationOrigin) null, (IrType) IrUtilsKt.getDefaultType(irClass2), 14, (Object) null);
                                        if (objectRef2.element == null) {
                                            Intrinsics.throwUninitializedPropertyAccessException("thisVar");
                                            irVariable2 = null;
                                        } else {
                                            irVariable2 = objectRef2.element;
                                        }
                                        irVariable2.setParent(irSimpleFunction3);
                                        return irBlockBuilder.getIrBlockBody();
                                    }

                                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                                    public IrExpression visitGetValue(IrGetValue expression) {
                                        Intrinsics.checkNotNullParameter(expression, "expression");
                                        transformChildrenVoid(expression);
                                        IrValueSymbol symbol = expression.getSymbol();
                                        IrValueParameter thisReceiver = irClass.getThisReceiver();
                                        IrVariable irVariable2 = null;
                                        if (Intrinsics.areEqual(symbol, thisReceiver != null ? thisReceiver.getSymbol() : null)) {
                                            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                                            if (objectRef.element == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("thisVar");
                                            } else {
                                                irVariable2 = objectRef.element;
                                            }
                                            return ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irVariable2);
                                        }
                                        IrValueParameter irValueParameter = linkedHashMap.get(expression.getSymbol());
                                        return irValueParameter != null ? ExpressionHelpersKt.irGet(irBlockBodyBuilder, irValueParameter) : expression;
                                    }

                                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                                    public IrExpression visitSetValue(IrSetValue expression) {
                                        Intrinsics.checkNotNullParameter(expression, "expression");
                                        transformChildrenVoid(expression);
                                        IrValueParameter irValueParameter = linkedHashMap.get(expression.getSymbol());
                                        if (irValueParameter == null) {
                                            return expression;
                                        }
                                        return ExpressionHelpersKt.irSet$default(irBlockBodyBuilder, irValueParameter.getSymbol(), expression.getValue(), (IrStatementOrigin) null, 4, (Object) null);
                                    }

                                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                                    public IrStatement visitDeclaration(IrDeclarationBase declaration) {
                                        Intrinsics.checkNotNullParameter(declaration, "declaration");
                                        IrElementTransformerVoidKt.transformChildrenVoid(declaration, this);
                                        if (Intrinsics.areEqual(declaration.getParent(), irConstructor2)) {
                                            declaration.setParent(irSimpleFunction);
                                        }
                                        return declaration;
                                    }

                                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                                    public IrExpression visitReturn(IrReturn expression) {
                                        IrVariable irVariable2;
                                        Intrinsics.checkNotNullParameter(expression, "expression");
                                        transformChildrenVoid(expression);
                                        if (Intrinsics.areEqual(expression.getReturnTargetSymbol(), irConstructor2.getSymbol())) {
                                            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                                            IrBlockBodyBuilder irBlockBodyBuilder3 = irBlockBodyBuilder2;
                                            IrBlockBodyBuilder irBlockBodyBuilder4 = irBlockBodyBuilder2;
                                            int startOffset = expression.getStartOffset();
                                            int endOffset = expression.getEndOffset();
                                            Ref.ObjectRef<IrVariable> objectRef2 = objectRef;
                                            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBlockBodyBuilder4.getContext(), irBlockBodyBuilder4.getScope(), startOffset, endOffset, null, null, false, 64, null);
                                            irBlockBuilder.unaryPlus(expression.getValue());
                                            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                                            if (objectRef2.element == null) {
                                                Intrinsics.throwUninitializedPropertyAccessException("thisVar");
                                                irVariable2 = null;
                                            } else {
                                                irVariable2 = objectRef2.element;
                                            }
                                            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable2));
                                            return ExpressionHelpersKt.irReturn(irBlockBodyBuilder3, irBlockBuilder.getIrBlockBody());
                                        }
                                        return expression;
                                    }
                                }));
                            }
                            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                            if (objectRef.element == 0) {
                                Intrinsics.throwUninitializedPropertyAccessException("thisVar");
                                irVariable = null;
                            } else {
                                irVariable = (IrVariable) objectRef.element;
                            }
                            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irVariable)));
                            CollectionsKt.addAll(statements, irBlockBodyBuilder.getIrBlockBody().getStatements());
                        }
                    });
                } else {
                    irBlockBodyCreateBlockBody = null;
                }
                staticMethod.setBody(irBlockBodyCreateBlockBody);
            }

            private final void transformMethodBodyFlat(final IrSimpleFunction function, final IrSimpleFunction staticMethod) {
                IrBlockBody irBlockBodyCreateBlockBody;
                IrSimpleFunction irSimpleFunction = function;
                if (IrDeclarationsKt.isStaticMethodOfClass(irSimpleFunction) || !AdditionalIrUtilsKt.isReal(irSimpleFunction)) {
                    return;
                }
                final IrBody body = function.getBody();
                if (body != null) {
                    final InlineClassLowering inlineClassLowering = this.this$0;
                    irBlockBodyCreateBlockBody = IrFactoryHelpersKt.createBlockBody(inlineClassLowering.getContext().getIrFactory(), -1, -1, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.backend.common.lower.InlineClassLowering$inlineClassDeclarationLowering$1$transformMethodBodyFlat$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                            invoke2(irBlockBody);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(IrBlockBody createBlockBody) {
                            Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                            List<IrStatement> statements = createBlockBody.getStatements();
                            IrBody irBody = body;
                            Intrinsics.checkNotNull(irBody, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
                            statements.addAll(((IrBlockBody) irBody).getStatements());
                            final IrSimpleFunction irSimpleFunction2 = function;
                            final IrSimpleFunction irSimpleFunction3 = staticMethod;
                            final InlineClassLowering inlineClassLowering2 = inlineClassLowering;
                            IrElementTransformerVoidKt.transformChildrenVoid(createBlockBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.InlineClassLowering$inlineClassDeclarationLowering$1$transformMethodBodyFlat$1$1.1
                                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                                public IrStatement visitDeclaration(IrDeclarationBase declaration) {
                                    Intrinsics.checkNotNullParameter(declaration, "declaration");
                                    IrElementTransformerVoidKt.transformChildrenVoid(declaration, this);
                                    if (Intrinsics.areEqual(declaration.getParent(), irSimpleFunction2)) {
                                        declaration.setParent(irSimpleFunction3);
                                    }
                                    return declaration;
                                }

                                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                                public IrExpression visitGetValue(IrGetValue expression) {
                                    IrValueParameter irValueParameter;
                                    Intrinsics.checkNotNullParameter(expression, "expression");
                                    IrValueDeclaration owner = expression.getSymbol().getOwner();
                                    IrValueParameter irValueParameter2 = owner instanceof IrValueParameter ? (IrValueParameter) owner : null;
                                    if (irValueParameter2 == null) {
                                        return super.visitGetValue(expression);
                                    }
                                    DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(inlineClassLowering2.getContext(), irSimpleFunction3.getSymbol(), 0, 0, 6, (Object) null);
                                    if (Intrinsics.areEqual(irValueParameter2, irSimpleFunction2.getDispatchReceiverParameter()) ? true : Intrinsics.areEqual(irValueParameter2, IrUtilsKt.getParentAsClass(irSimpleFunction2).getThisReceiver())) {
                                        irValueParameter = irSimpleFunction3.getValueParameters().get(0);
                                    } else if (Intrinsics.areEqual(irValueParameter2, irSimpleFunction2.getExtensionReceiverParameter())) {
                                        irValueParameter = irSimpleFunction3.getValueParameters().get(1);
                                    } else if (irSimpleFunction2.getValueParameters().contains(irValueParameter2)) {
                                        irValueParameter = irSimpleFunction3.getValueParameters().get(irValueParameter2.getIndex() + (irSimpleFunction2.getExtensionReceiverParameter() != null ? 2 : 1));
                                    } else {
                                        return expression;
                                    }
                                    return ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, irValueParameter);
                                }

                                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                                public IrExpression visitSetValue(IrSetValue expression) {
                                    Intrinsics.checkNotNullParameter(expression, "expression");
                                    IrValueDeclaration owner = expression.getSymbol().getOwner();
                                    IrValueParameter irValueParameter = owner instanceof IrValueParameter ? (IrValueParameter) owner : null;
                                    if (irValueParameter == null) {
                                        return super.visitSetValue(expression);
                                    }
                                    transformChildrenVoid(expression);
                                    DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(inlineClassLowering2.getContext(), irSimpleFunction3.getSymbol(), 0, 0, 6, (Object) null);
                                    if (irSimpleFunction2.getValueParameters().contains(irValueParameter)) {
                                        return ExpressionHelpersKt.irSet$default(declarationIrBuilderCreateIrBuilder$default, irSimpleFunction3.getValueParameters().get(irValueParameter.getIndex() + (irSimpleFunction2.getExtensionReceiverParameter() != null ? 2 : 1)).getSymbol(), expression.getValue(), (IrStatementOrigin) null, 4, (Object) null);
                                    }
                                    return expression;
                                }
                            });
                        }
                    });
                } else {
                    irBlockBodyCreateBlockBody = null;
                }
                staticMethod.setBody(irBlockBodyCreateBlockBody);
            }

            private final IrBlockBody delegateToStaticMethod(final IrSimpleFunction function, final IrSimpleFunction staticMethod) {
                IrFactory irFactory = this.this$0.getContext().getIrFactory();
                final InlineClassLowering inlineClassLowering = this.this$0;
                return IrFactoryHelpersKt.createBlockBody(irFactory, -1, -1, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.backend.common.lower.InlineClassLowering$inlineClassDeclarationLowering$1$delegateToStaticMethod$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                        invoke2(irBlockBody);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(IrBlockBody createBlockBody) {
                        Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                        List<IrStatement> statements = createBlockBody.getStatements();
                        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(inlineClassLowering.getContext(), function.getSymbol(), 0, 0, 6, (Object) null);
                        IrSimpleFunction irSimpleFunction = staticMethod;
                        IrSimpleFunction irSimpleFunction2 = function;
                        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
                        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder2, irSimpleFunction);
                        IrValueParameter dispatchReceiverParameter = irSimpleFunction2.getDispatchReceiverParameter();
                        Intrinsics.checkNotNull(dispatchReceiverParameter);
                        int i = 0;
                        Iterator it = CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull((Object[]) new IrValueParameter[]{dispatchReceiverParameter, irSimpleFunction2.getExtensionReceiverParameter()}), (Iterable) irSimpleFunction2.getValueParameters()).iterator();
                        int i2 = 0;
                        while (it.hasNext()) {
                            irFunctionAccessExpressionIrCall.putValueArgument(i2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, (IrValueParameter) it.next()));
                            i2++;
                        }
                        Iterator it2 = CollectionsKt.plus((Collection) IrTypeSystemContextKt.extractTypeParameters(IrUtilsKt.getParentAsClass(irSimpleFunction2)), (Iterable) irSimpleFunction2.getTypeParameters()).iterator();
                        while (it2.hasNext()) {
                            irFunctionAccessExpressionIrCall.putTypeArgument(i, new IrSimpleTypeImpl(((IrTypeParameter) it2.next()).getSymbol(), SimpleTypeNullability.NOT_SPECIFIED, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null));
                            i++;
                        }
                        Unit unit = Unit.INSTANCE;
                        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, irFunctionAccessExpressionIrCall));
                        CollectionsKt.addAll(statements, irBlockBodyBuilder.getIrBlockBody().getStatements());
                    }
                });
            }
        };
        this.inlineClassUsageLowering = new BodyLoweringPass() { // from class: org.jetbrains.kotlin.backend.common.lower.InlineClassLowering$inlineClassUsageLowering$1
            @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
            public void lower(IrBody irBody, IrDeclaration container) {
                Intrinsics.checkNotNullParameter(irBody, "irBody");
                Intrinsics.checkNotNullParameter(container, "container");
                final InlineClassLowering inlineClassLowering = this.this$0;
                IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.InlineClassLowering$inlineClassUsageLowering$1$lower$1
                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                    public IrExpression visitConstructorCall(IrConstructorCall expression) {
                        Intrinsics.checkNotNullParameter(expression, "expression");
                        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                        IrConstructor owner = expression.getSymbol().getOwner();
                        if (!inlineClassLowering.isClassInlineLike(IrUtilsKt.getParentAsClass(owner))) {
                            return expression;
                        }
                        return IrUtilsKt.irCall(expression, inlineClassLowering.getOrCreateStaticMethod(owner), (56 & 4) != 0 ? false : false, (56 & 8) != 0 ? false : false, (56 & 16) != 0 ? null : null, (56 & 32) != 0 ? null : null);
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                    public IrExpression visitCall(IrCall expression) {
                        Intrinsics.checkNotNullParameter(expression, "expression");
                        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                        IrSimpleFunction owner = expression.getSymbol().getOwner();
                        if (owner.getParent() instanceof IrClass) {
                            IrSimpleFunction irSimpleFunction = owner;
                            if (!IrDeclarationsKt.isStaticMethodOfClass(irSimpleFunction) && inlineClassLowering.isClassInlineLike(IrUtilsKt.getParentAsClass(owner)) && AdditionalIrUtilsKt.isReal(irSimpleFunction)) {
                                return IrUtilsKt.irCall(expression, inlineClassLowering.getOrCreateStaticMethod(irSimpleFunction), (56 & 4) != 0 ? false : true, (56 & 8) != 0 ? false : false, (56 & 16) != 0 ? null : null, (56 & 32) != 0 ? null : null);
                            }
                        }
                        return expression;
                    }

                    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                    public IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
                        Intrinsics.checkNotNullParameter(expression, "expression");
                        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                        IrConstructor owner = expression.getSymbol().getOwner();
                        if (inlineClassLowering.isClassInlineLike(IrUtilsKt.getParentAsClass(owner))) {
                            return IrUtilsKt.irCall(expression, inlineClassLowering.getOrCreateStaticMethod(owner), (56 & 4) != 0 ? false : false, (56 & 8) != 0 ? false : false, (56 & 16) != 0 ? null : null, (56 & 32) != 0 ? null : null);
                        }
                        return expression;
                    }
                });
            }
        };
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isClassInlineLike(IrClass irClass) {
        return this.context.getInlineClassesUtils().isClassInlineLike(irClass);
    }

    public final DeclarationTransformer getInlineClassDeclarationLowering() {
        return this.inlineClassDeclarationLowering;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction getOrCreateStaticMethod(final IrFunction function) {
        return (IrSimpleFunction) MappingsKt.getOrPut(this.transformedFunction, function, new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.backend.common.lower.InlineClassLowering.getOrCreateStaticMethod.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                return InlineClassLowering.this.createStaticBodilessMethod(function);
            }
        });
    }

    public final BodyLoweringPass getInlineClassUsageLowering() {
        return this.inlineClassUsageLowering;
    }

    private final Name toInlineClassImplementationName(IrFunction irFunction) {
        String str = IrUtilsKt.getParentAsClass(irFunction).getName().asString() + JvmNames.MULTIFILE_PART_NAME_DELIMITER + irFunction.getName().asString() + JvmAbi.IMPL_SUFFIX_FOR_INLINE_CLASS_MEMBERS;
        if (irFunction.getName().isSpecial()) {
            Name nameSpecial = Name.special("<" + str + '>');
            Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
            return nameSpecial;
        }
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction createStaticBodilessMethod(IrFunction function) {
        return IrUtilsKt.createStaticFunctionWithReceivers$default(this.context.getIrFactory(), function.getParent(), toInlineClassImplementationName(function), function, null, null, null, null, false, false, IrTypeSystemContextKt.extractTypeParameters(IrUtilsKt.getParentAsClass(function)), new AnonymousClass1(this.context), 504, null);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.common.lower.InlineClassLowering$createStaticBodilessMethod$1, reason: invalid class name */
    /* JADX INFO: compiled from: InlineClassDeclarationLowering.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function3<IrFunction, IrFunction, Map<IrValueParameter, ? extends IrValueParameter>, Unit> {
        AnonymousClass1(Object obj) {
            super(3, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "remapMultiFieldValueClassStructure(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Ljava/util/Map;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "remapMultiFieldValueClassStructure";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(CommonBackendContext.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(IrFunction irFunction, IrFunction irFunction2, Map<IrValueParameter, ? extends IrValueParameter> map) {
            invoke2(irFunction, irFunction2, map);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(IrFunction p0, IrFunction p1, Map<IrValueParameter, ? extends IrValueParameter> map) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            ((CommonBackendContext) this.receiver).remapMultiFieldValueClassStructure(p0, p1, map);
        }
    }
}
