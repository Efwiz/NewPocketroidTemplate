package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.bytebuddy.implementation.MethodDelegation;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.ir.ValueRemapper;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrStatementContainer;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrRawFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: SecondaryCtorLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0018B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002J \u0010\u0010\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002J(\u0010\u0011\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000eH\u0002J\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00142\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u00142\u0006\u0010\u0017\u001a\u00020\u0016H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/SecondaryConstructorLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "generateFactoryBody", "", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "stub", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "generateInitBody", "generateStubsBody", "factory", "transformConstructor", "", "transformFlat", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "ThisUsageReplaceTransformer", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SecondaryConstructorLowering implements DeclarationTransformer {
    private final JsIrBackendContext context;

    public SecondaryConstructorLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!this.context.getEs6mode() && (declaration instanceof IrConstructor)) {
            IrConstructor irConstructor = (IrConstructor) declaration;
            if (!irConstructor.getIsPrimary()) {
                IrClass parentAsClass = IrUtilsKt.getParentAsClass(declaration);
                if (this.context.getInlineClassesUtils().isClassInlineLike(parentAsClass)) {
                    return null;
                }
                return transformConstructor(irConstructor, parentAsClass);
            }
        }
        return null;
    }

    private final List<IrSimpleFunction> transformConstructor(IrConstructor constructor, IrClass irClass) {
        IrSimpleFunction irSimpleFunctionBuildConstructorDelegate = SecondaryCtorLoweringKt.buildConstructorDelegate(this.context, constructor, irClass);
        IrSimpleFunction irSimpleFunctionBuildConstructorFactory = SecondaryCtorLoweringKt.buildConstructorFactory(this.context, constructor, irClass);
        generateStubsBody(constructor, irClass, irSimpleFunctionBuildConstructorDelegate, irSimpleFunctionBuildConstructorFactory);
        return CollectionsKt.listOf((Object[]) new IrSimpleFunction[]{irSimpleFunctionBuildConstructorDelegate, irSimpleFunctionBuildConstructorFactory});
    }

    private final void generateStubsBody(IrConstructor constructor, IrClass irClass, IrSimpleFunction delegate, IrSimpleFunction factory) {
        generateInitBody(constructor, irClass, delegate);
        generateFactoryBody(constructor, irClass, factory, delegate);
    }

    private final void generateFactoryBody(final IrConstructor constructor, final IrClass irClass, final IrSimpleFunction stub, final IrSimpleFunction delegate) {
        stub.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), -1, -1, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.SecondaryConstructorLowering.generateFactoryBody.1
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
                IrSimpleType defaultType = IrUtilsKt.getDefaultType(irClass);
                IrSimpleType irSimpleType = defaultType;
                IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.getContext().getIntrinsics().getJsObjectCreateSymbol(), irSimpleType, CollectionsKt.listOf(defaultType), null, null, 24, null);
                IrCall irCallBuildCall$default2 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, delegate.getSymbol(), irSimpleType, null, null, null, 28, null);
                IrSimpleFunction irSimpleFunction = stub;
                IrConstructor irConstructor = constructor;
                int size = irSimpleFunction.getTypeParameters().size();
                for (int i = 0; i < size; i++) {
                    irCallBuildCall$default2.putTypeArgument(i, SecondaryCtorLoweringKt.toIrType(irSimpleFunction.getTypeParameters().get(i)));
                }
                int size2 = irSimpleFunction.getValueParameters().size();
                for (int i2 = 0; i2 < size2; i2++) {
                    irCallBuildCall$default2.putValueArgument(i2, JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irSimpleFunction.getValueParameters().get(i2).getSymbol(), null, 2, null));
                }
                irCallBuildCall$default2.putValueArgument(irConstructor.getValueParameters().size(), irCallBuildCall$default);
                if (!IrUtilsKt.isSubclassOf(irClass, this.getContext().getIrBuiltIns().getThrowableClass().getOwner())) {
                    createBlockBody.getStatements().add(JsIrBuilder.INSTANCE.buildReturn(stub.getSymbol(), irCallBuildCall$default2, this.getContext().getIrBuiltIns().getNothingType()));
                    return;
                }
                IrVariable irVariableBuildVar = JsIrBuilder.INSTANCE.buildVar(irCallBuildCall$default2.getType(), stub, (180 & 4) != 0 ? Constants.TMP_DIRECTORY_NAME : null, (180 & 8) != 0 ? false : false, (180 & 16) != 0 ? false : false, (180 & 32) != 0 ? false : false, (180 & 64) != 0 ? null : irCallBuildCall$default2, (180 & 128) != 0 ? JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE : null);
                createBlockBody.getStatements().add(irVariableBuildVar);
                List<IrStatement> statements = createBlockBody.getStatements();
                IrCall irCallBuildCall$default3 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.getContext().getIntrinsics().getCaptureStack(), null, null, null, null, 30, null);
                SecondaryConstructorLowering secondaryConstructorLowering = this;
                IrSimpleFunction irSimpleFunction2 = stub;
                irCallBuildCall$default3.putValueArgument(0, JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableBuildVar.getSymbol(), null, 2, null));
                irCallBuildCall$default3.putValueArgument(1, new IrRawFunctionReferenceImpl(-1, -1, secondaryConstructorLowering.getContext().getIrBuiltIns().getAnyType(), irSimpleFunction2.getSymbol()));
                statements.add(irCallBuildCall$default3);
                createBlockBody.getStatements().add(JsIrBuilder.INSTANCE.buildReturn(stub.getSymbol(), JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableBuildVar.getSymbol(), null, 2, null), this.getContext().getIrBuiltIns().getNothingType()));
            }
        }));
    }

    private final void generateInitBody(final IrConstructor constructor, IrClass irClass, final IrSimpleFunction delegate) {
        final IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.last((List) delegate.getValueParameters());
        IrValueParameter thisReceiver = irClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        final IrBody body = constructor.getBody();
        final List listPlus = CollectionsKt.plus((Collection<? extends IrValueParameter>) constructor.getValueParameters(), thisReceiver);
        if (body != null) {
            delegate.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), -1, -1, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.SecondaryConstructorLowering.generateInitBody.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
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
                    IrSimpleFunction irSimpleFunction = delegate;
                    DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
                    IrVisitorsKt.acceptVoid(irBody, deepCopySymbolRemapper);
                    DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                    IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irBody.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irSimpleFunction);
                    if (irElementPatchDeclarationParents != null) {
                        IrElement irElement = (IrBody) irElementPatchDeclarationParents;
                        Intrinsics.checkNotNull(irElement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrStatementContainer");
                        CollectionsKt.addAll(statements, ((IrStatementContainer) irElement).getStatements());
                        createBlockBody.getStatements().add(JsIrBuilder.INSTANCE.buildReturn(delegate.getSymbol(), JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irValueParameter.getSymbol(), null, 2, null), this.getContext().getIrBuiltIns().getNothingType()));
                        IrBlockBody irBlockBody = createBlockBody;
                        IrConstructorSymbol symbol = constructor.getSymbol();
                        IrSimpleFunctionSymbol symbol2 = delegate.getSymbol();
                        List<Pair> listZip = CollectionsKt.zip(listPlus, delegate.getValueParameters());
                        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listZip, 10)), 16));
                        for (Pair pair : listZip) {
                            Pair pair2 = TuplesKt.to(((IrValueParameter) pair.component1()).getSymbol(), ((IrValueParameter) pair.component2()).getSymbol());
                            linkedHashMap.put(pair2.getFirst(), pair2.getSecond());
                        }
                        IrElementTransformerVoidKt.transformChildrenVoid(irBlockBody, new ThisUsageReplaceTransformer(symbol, symbol2, linkedHashMap));
                        return;
                    }
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBody");
                }
            }));
        }
    }

    /* JADX INFO: compiled from: SecondaryCtorLowering.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/SecondaryConstructorLowering$ThisUsageReplaceTransformer;", "Lorg/jetbrains/kotlin/backend/common/ir/ValueRemapper;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "function", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "symbolMapping", "", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;Ljava/util/Map;)V", "getConstructor", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getFunction", "()Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "newThisSymbol", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ThisUsageReplaceTransformer extends ValueRemapper {
        private final IrConstructorSymbol constructor;
        private final IrFunctionSymbol function;
        private final IrValueSymbol newThisSymbol;

        public final IrConstructorSymbol getConstructor() {
            return this.constructor;
        }

        public final IrFunctionSymbol getFunction() {
            return this.function;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ThisUsageReplaceTransformer(IrConstructorSymbol constructor, IrFunctionSymbol function, Map<IrValueSymbol, ? extends IrValueSymbol> symbolMapping) {
            super(symbolMapping);
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Intrinsics.checkNotNullParameter(function, "function");
            Intrinsics.checkNotNullParameter(symbolMapping, "symbolMapping");
            this.constructor = constructor;
            this.function = function;
            this.newThisSymbol = (IrValueSymbol) CollectionsKt.last(symbolMapping.values());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitReturn(IrReturn expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            if (!Intrinsics.areEqual(expression.getReturnTargetSymbol(), this.constructor)) {
                return expression;
            }
            return new IrReturnImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), this.function, new IrGetValueImpl(expression.getStartOffset(), expression.getEndOffset(), this.newThisSymbol.getOwner().getType(), this.newThisSymbol, null, 16, null));
        }
    }
}
