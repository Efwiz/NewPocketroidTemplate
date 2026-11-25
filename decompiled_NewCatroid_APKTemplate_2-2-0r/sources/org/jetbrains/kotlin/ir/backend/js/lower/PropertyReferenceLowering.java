package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.Implementation;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: PropertyReferenceLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00102\u00020\u0001:\u0002\u0010\u0011B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/PropertyReferenceLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "jsClassSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "localDelegateBuilderSymbol", "referenceBuilderSymbol", "throwISE", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Companion", "PropertyReferenceTransformer", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PropertyReferenceLowering implements BodyLoweringPass {
    private final JsIrBackendContext context;
    private final IrSimpleFunctionSymbol jsClassSymbol;
    private final IrSimpleFunctionSymbol localDelegateBuilderSymbol;
    private final IrSimpleFunctionSymbol referenceBuilderSymbol;
    private final IrSimpleFunctionSymbol throwISE;

    public PropertyReferenceLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.referenceBuilderSymbol = context.getKpropertyBuilder();
        this.localDelegateBuilderSymbol = context.getKlocalDelegateBuilder();
        this.jsClassSymbol = context.getIntrinsics().getJsClass();
        this.throwISE = context.getIr().getSymbols().getThrowISE();
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrDeclarationParent parent = container instanceof IrDeclarationParent ? (IrDeclarationParent) container : null;
        if (parent == null) {
            parent = container.getParent();
        }
        List<IrDeclaration> listProcess = new PropertyReferenceTransformer(this, parent).process(irBody);
        if (listProcess.isEmpty()) {
            return;
        }
        IrFile file = IrUtilsKt.getFile(container);
        Iterator<T> it = listProcess.iterator();
        while (it.hasNext()) {
            ((IrDeclaration) it.next()).setParent(file);
        }
        file.getDeclarations().addAll(listProcess);
    }

    /* JADX INFO: compiled from: PropertyReferenceLowering.kt */
    @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J.\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002J\u0010\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J&\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002J\u0010\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J&\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002J\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00162\u0006\u0010\u001f\u001a\u00020 J\u0010\u0010!\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\"\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u0014H\u0016J\f\u0010#\u001a\u00020\u000f*\u00020\u000fH\u0002J\f\u0010$\u001a\u00020\u000f*\u00020\u0014H\u0002R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/PropertyReferenceLowering$PropertyReferenceTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "currentParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/PropertyReferenceLowering;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "getCurrentParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setCurrentParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "newDeclarations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getNewDeclarations", "()Ljava/util/List;", "buildAccessorLambda", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "reference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "boundValueParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "buildFactoryFunction", "buildGetterLambda", "buildLocalDelegateLambda", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "buildSetterLambda", "process", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "visitLocalDelegatedPropertyReference", "visitPropertyReference", "getJsTypeConstructor", "nameExpression", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class PropertyReferenceTransformer extends IrElementTransformerVoid {
        private IrDeclarationParent currentParent;
        private final List<IrDeclaration> newDeclarations;
        final /* synthetic */ PropertyReferenceLowering this$0;

        public PropertyReferenceTransformer(PropertyReferenceLowering propertyReferenceLowering, IrDeclarationParent currentParent) {
            Intrinsics.checkNotNullParameter(currentParent, "currentParent");
            this.this$0 = propertyReferenceLowering;
            this.currentParent = currentParent;
            this.newDeclarations = new ArrayList();
        }

        public final IrDeclarationParent getCurrentParent() {
            return this.currentParent;
        }

        public final void setCurrentParent(IrDeclarationParent irDeclarationParent) {
            Intrinsics.checkNotNullParameter(irDeclarationParent, "<set-?>");
            this.currentParent = irDeclarationParent;
        }

        public final List<IrDeclaration> getNewDeclarations() {
            return this.newDeclarations;
        }

        public final List<IrDeclaration> process(IrBody irBody) {
            Intrinsics.checkNotNullParameter(irBody, "irBody");
            IrElementTransformerVoidKt.transformChildrenVoid(irBody, this);
            return this.newDeclarations;
        }

        private final IrSimpleFunction buildFactoryFunction(IrPropertyReference reference) {
            IrProperty owner = reference.getSymbol().getOwner();
            IrFactory irFactory = this.this$0.context.getIrFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setStartOffset(reference.getStartOffset());
            irFunctionBuilder.setEndOffset(reference.getEndOffset());
            irFunctionBuilder.setReturnType(reference.getType());
            Name nameIdentifier = Name.identifier(owner.getName().asString() + "$factory");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFunctionBuilder.setName(nameIdentifier);
            irFunctionBuilder.setOrigin(Companion.PROPERTY_REFERENCE_FACTORY.INSTANCE);
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
            List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new IrExpression[]{reference.getDispatchReceiver(), reference.getExtensionReceiver()});
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOfNotNull, 10));
            int i = 0;
            for (Object obj : listListOfNotNull) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                irValueParameterBuilder.setType(((IrExpression) obj).getType());
                irValueParameterBuilder.setIndex(i);
                Name nameIdentifier2 = Name.identifier("$b" + i);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
                irValueParameterBuilder.setName(nameIdentifier2);
                IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
                arrayList.add(DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder, irSimpleFunction));
                i = i2;
            }
            ArrayList arrayList2 = arrayList;
            irSimpleFunctionBuildFunction.setValueParameters(arrayList2);
            IrType type = reference.getType();
            Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            int size = ((IrSimpleType) type).getArguments().size() - 1;
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.this$0.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
            PropertyReferenceLowering propertyReferenceLowering = this.this$0;
            IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder2, propertyReferenceLowering.referenceBuilderSymbol);
            irCall.putValueArgument(0, nameExpression(reference));
            irCall.putValueArgument(1, ExpressionHelpersKt.irInt$default(irBlockBodyBuilder2, size, null, 2, null));
            irCall.putValueArgument(2, getJsTypeConstructor(reference));
            irCall.putValueArgument(3, buildGetterLambda(irSimpleFunctionBuildFunction, reference, arrayList2));
            irCall.putValueArgument(4, buildSetterLambda(irSimpleFunctionBuildFunction, reference, arrayList2));
            Unit unit = Unit.INSTANCE;
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, irCall));
            irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
            this.newDeclarations.add(irSimpleFunctionBuildFunction);
            return irSimpleFunctionBuildFunction;
        }

        private final IrExpression buildGetterLambda(IrSimpleFunction factory, IrPropertyReference reference, List<? extends IrValueParameter> boundValueParameters) {
            IrSimpleFunction owner;
            IrSimpleFunctionSymbol getter = reference.getGetter();
            if (getter == null || (owner = getter.getOwner()) == null) {
                CompilationExceptionKt.compilationException("Getter expected", reference);
                throw null;
            }
            return buildAccessorLambda(factory, owner, reference, boundValueParameters);
        }

        private final IrExpression buildSetterLambda(IrSimpleFunction factory, IrPropertyReference reference, List<? extends IrValueParameter> boundValueParameters) {
            IrSimpleFunction owner;
            PropertyReferenceLowering propertyReferenceLowering = this.this$0;
            IrSimpleFunctionSymbol setter = reference.getSetter();
            if (setter == null || (owner = setter.getOwner()) == null) {
                return IrConstImpl.INSTANCE.constNull(reference.getStartOffset(), reference.getEndOffset(), propertyReferenceLowering.context.getIrBuiltIns().getNothingNType());
            }
            return buildAccessorLambda(factory, owner, reference, boundValueParameters);
        }

        private final IrExpression buildAccessorLambda(IrSimpleFunction factory, IrSimpleFunction accessor, IrPropertyReference reference, List<? extends IrValueParameter> boundValueParameters) {
            String str;
            int i;
            IrGetValueImpl irGetValueImplIrGet;
            IrGetValueImpl irGetValueImplIrGet2;
            IrSimpleFunctionSymbol symbol = accessor.getSymbol();
            Object obj = null;
            if (Intrinsics.areEqual(symbol, reference.getGetter())) {
                str = "get";
            } else {
                if (!Intrinsics.areEqual(symbol, reference.getSetter())) {
                    CompilationExceptionKt.compilationException("Unexpected accessor", (IrDeclaration) accessor);
                    throw null;
                }
                str = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX;
            }
            IrType type = reference.getType();
            Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull((IrSimpleType) type);
            if (classOrNull == null) {
                CompilationExceptionKt.compilationException("Simple type expected", reference);
                throw null;
            }
            List<IrDeclaration> declarations = classOrNull.getOwner().getDeclarations();
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : declarations) {
                if (obj2 instanceof IrSimpleFunction) {
                    arrayList.add(obj2);
                }
            }
            Iterator it = arrayList.iterator();
            boolean z = false;
            while (true) {
                int i2 = 1;
                if (!it.hasNext()) {
                    if (z) {
                        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) obj;
                        IrFactory irFactory = this.this$0.context.getIrFactory();
                        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                        irFunctionBuilder.setStartOffset(reference.getStartOffset());
                        irFunctionBuilder.setEndOffset(reference.getEndOffset());
                        irFunctionBuilder.setReturnType(irSimpleFunction.getReturnType());
                        irFunctionBuilder.setName(irSimpleFunction.getName());
                        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
                        irSimpleFunctionBuildFunction.setParent(factory);
                        List<IrValueParameter> valueParameters = irSimpleFunction.getValueParameters();
                        ArrayList arrayList2 = new ArrayList(valueParameters.size());
                        Iterator<T> it2 = valueParameters.iterator();
                        while (it2.hasNext()) {
                            arrayList2.add(IrUtilsKt.copyTo$default((IrValueParameter) it2.next(), irSimpleFunctionBuildFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null));
                        }
                        List<? extends IrValueParameter> listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2);
                        irSimpleFunctionBuildFunction.setValueParameters(listCompactIfPossible);
                        listCompactIfPossible.size();
                        boundValueParameters.size();
                        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.this$0.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
                        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), declarationIrBuilderCreateIrBuilder$default.getStartOffset(), declarationIrBuilderCreateIrBuilder$default.getEndOffset());
                        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder2, accessor.getSymbol());
                        if (accessor.getDispatchReceiverParameter() != null) {
                            if (reference.getDispatchReceiver() != null) {
                                irGetValueImplIrGet2 = ExpressionHelpersKt.irGet(irBlockBodyBuilder2, boundValueParameters.get(0));
                                i = 0;
                            } else {
                                irGetValueImplIrGet2 = ExpressionHelpersKt.irGet(irBlockBodyBuilder2, listCompactIfPossible.get(0));
                                i = 1;
                                i2 = 0;
                            }
                            irCall.setDispatchReceiver(irGetValueImplIrGet2);
                        } else {
                            i2 = 0;
                            i = 0;
                        }
                        if (accessor.getExtensionReceiverParameter() != null) {
                            if (reference.getExtensionReceiver() != null) {
                                irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBodyBuilder2, boundValueParameters.get(i2));
                            } else {
                                IrGetValueImpl irGetValueImplIrGet3 = ExpressionHelpersKt.irGet(irBlockBodyBuilder2, listCompactIfPossible.get(i));
                                i++;
                                irGetValueImplIrGet = irGetValueImplIrGet3;
                            }
                            irCall.setExtensionReceiver(irGetValueImplIrGet);
                        }
                        if (i < listCompactIfPossible.size()) {
                            irCall.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, listCompactIfPossible.get(i)));
                        }
                        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, irCall));
                        irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
                        return new IrFunctionExpressionImpl(reference.getStartOffset(), reference.getEndOffset(), this.this$0.context.getIrBuiltIns().getAnyType(), irSimpleFunctionBuildFunction, IrStatementOrigin.LAMBDA.INSTANCE);
                    }
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
                Object next = it.next();
                if (Intrinsics.areEqual(((IrSimpleFunction) next).getName().asString(), str)) {
                    if (z) {
                        throw new IllegalArgumentException("Collection contains more than one matching element.");
                    }
                    obj = next;
                    z = true;
                }
            }
        }

        private final IrExpression nameExpression(IrPropertyReference irPropertyReference) {
            String strAsString = irPropertyReference.getSymbol().getOwner().getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return IrConstImpl.INSTANCE.string(irPropertyReference.getStartOffset(), irPropertyReference.getEndOffset(), this.this$0.context.getIrBuiltIns().getStringType(), strAsString);
        }

        private final IrExpression getJsTypeConstructor(IrExpression irExpression) {
            IrCallImpl irCallImpl = new IrCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), this.this$0.jsClassSymbol.getOwner().getReturnType(), this.this$0.jsClassSymbol, 1, 0, null, null, 192, null);
            irCallImpl.putTypeArgument(0, irExpression.getType());
            return irCallImpl;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitPropertyReference(IrPropertyReference expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
            IrSimpleFunction irSimpleFunctionBuildFactoryFunction = buildFactoryFunction(expression);
            expression.getValueArgumentsCount();
            IrCallImpl irCallImpl = new IrCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irSimpleFunctionBuildFactoryFunction.getSymbol(), expression.getTypeArgumentsCount(), irSimpleFunctionBuildFactoryFunction.getValueParameters().size(), null, null, 192, null);
            int typeArgumentsCount = irCallImpl.getTypeArgumentsCount();
            int i = 0;
            for (int i2 = 0; i2 < typeArgumentsCount; i2++) {
                irCallImpl.putTypeArgument(i2, expression.getTypeArgument(i2));
            }
            IrExpression dispatchReceiver = expression.getDispatchReceiver();
            if (dispatchReceiver != null) {
                irCallImpl.putValueArgument(0, dispatchReceiver);
                i = 1;
            }
            IrExpression extensionReceiver = expression.getExtensionReceiver();
            if (extensionReceiver != null) {
                irCallImpl.putValueArgument(i, extensionReceiver);
            }
            return irCallImpl;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
            IrCallImpl irCallImpl = new IrCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), this.this$0.localDelegateBuilderSymbol, expression.getTypeArgumentsCount(), 4, null, null, 192, null);
            String strAsString = expression.getSymbol().getOwner().getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            boolean z = expression.getSetter() != null;
            PropertyReferenceLowering propertyReferenceLowering = this.this$0;
            irCallImpl.putValueArgument(0, IrConstImpl.INSTANCE.string(expression.getStartOffset(), expression.getEndOffset(), propertyReferenceLowering.context.getIrBuiltIns().getStringType(), strAsString));
            irCallImpl.putValueArgument(1, getJsTypeConstructor(expression));
            irCallImpl.putValueArgument(2, IrConstImpl.INSTANCE.m7206boolean(expression.getStartOffset(), expression.getEndOffset(), propertyReferenceLowering.context.getIrBuiltIns().getBooleanType(), z));
            irCallImpl.putValueArgument(3, buildLocalDelegateLambda(expression));
            return irCallImpl;
        }

        private final IrExpression buildLocalDelegateLambda(IrLocalDelegatedPropertyReference expression) {
            IrVariable owner = expression.getDelegate().getOwner();
            IrFactory irFactory = this.this$0.context.getIrFactory();
            PropertyReferenceLowering propertyReferenceLowering = this.this$0;
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setStartOffset(expression.getStartOffset());
            irFunctionBuilder.setEndOffset(expression.getEndOffset());
            irFunctionBuilder.setReturnType(propertyReferenceLowering.context.getIrBuiltIns().getNothingType());
            Name nameIdentifier = Name.identifier(owner.getName() + "$stub");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFunctionBuilder.setName(nameIdentifier);
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
            irSimpleFunctionBuildFunction.setParent(this.currentParent);
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.this$0.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
            PropertyReferenceLowering propertyReferenceLowering2 = this.this$0;
            DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
            IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), declarationIrBuilder.getStartOffset(), declarationIrBuilder.getEndOffset());
            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder2, propertyReferenceLowering2.throwISE)));
            irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
            return new IrFunctionExpressionImpl(expression.getStartOffset(), expression.getEndOffset(), this.this$0.context.getIrBuiltIns().getAnyType(), irSimpleFunctionBuildFunction, IrStatementOrigin.LAMBDA.INSTANCE);
        }
    }
}
