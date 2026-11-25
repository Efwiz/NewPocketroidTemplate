package org.jetbrains.kotlin.backend.common.lower;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ArrayConstructorLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ArrayConstructorReferenceLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ArrayConstructorReferenceTransformer", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayConstructorReferenceLowering implements BodyLoweringPass {
    private final CommonBackendContext context;

    public ArrayConstructorReferenceLowering(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new ArrayConstructorReferenceTransformer(this.context, container));
    }

    /* JADX INFO: compiled from: ArrayConstructorLowering.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ArrayConstructorReferenceLowering$ArrayConstructorReferenceTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;)V", "getContainer", "()Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "createFunctionReferenceWrapper", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "target", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ArrayConstructorReferenceTransformer extends IrElementTransformerVoid {
        private final IrSymbolOwner container;
        private final CommonBackendContext context;

        public final CommonBackendContext getContext() {
            return this.context;
        }

        public final IrSymbolOwner getContainer() {
            return this.container;
        }

        public ArrayConstructorReferenceTransformer(CommonBackendContext context, IrSymbolOwner container) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(container, "container");
            this.context = context;
            this.container = container;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitFunctionReference(IrFunctionReference expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            transformChildrenVoid(expression);
            IrFunction owner = expression.getSymbol().getOwner();
            if (!(owner instanceof IrConstructor)) {
                return expression;
            }
            IrConstructor irConstructor = (IrConstructor) owner;
            if (ArrayConstructorTransformer.INSTANCE.arrayInlineToSizeConstructor$ir_backend_common(this.context, irConstructor) == null) {
                return expression;
            }
            IrCompositeImpl irCompositeImpl = new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), expression.getOrigin());
            IrSimpleFunction irSimpleFunctionCreateFunctionReferenceWrapper = createFunctionReferenceWrapper(expression, irConstructor);
            irCompositeImpl.getStatements().add(irSimpleFunctionCreateFunctionReferenceWrapper);
            irCompositeImpl.getStatements().add(new IrFunctionReferenceImpl(irCompositeImpl.getStartOffset(), irCompositeImpl.getEndOffset(), irCompositeImpl.getType(), irSimpleFunctionCreateFunctionReferenceWrapper.getSymbol(), 0, expression.getValueArgumentsCount(), irConstructor.getSymbol(), irCompositeImpl.getOrigin()));
            return irCompositeImpl;
        }

        /* JADX WARN: Code duplicated, block: B:12:0x0099  */
        private final IrSimpleFunction createFunctionReferenceWrapper(IrFunctionReference expression, IrConstructor target) {
            Map mapEmptyMap;
            IrType type = expression.getType();
            Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            List<IrTypeArgument> arguments = ((IrSimpleType) type).getArguments();
            arguments.size();
            IrTypeArgument irTypeArgument = arguments.get(2);
            Intrinsics.checkNotNull(irTypeArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrTypeProjection");
            IrType type2 = ((IrTypeProjection) irTypeArgument).getType();
            Intrinsics.checkNotNull(type2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) ((IrSimpleType) type2).getArguments());
            IrTypeProjection irTypeProjection = objSingleOrNull instanceof IrTypeProjection ? (IrTypeProjection) objSingleOrNull : null;
            IrType type3 = irTypeProjection != null ? irTypeProjection.getType() : null;
            IrFactory irFactory = this.context.getIrFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setStartOffset(expression.getStartOffset());
            irFunctionBuilder.setEndOffset(expression.getEndOffset());
            irFunctionBuilder.setOrigin(ArrayConstructorWrapper.INSTANCE);
            Name nameSpecial = Name.special("<array_inline_constructor_wrapper>");
            Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
            irFunctionBuilder.setName(nameSpecial);
            DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
            Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
            irFunctionBuilder.setVisibility(LOCAL);
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
            IrTypeParameter irTypeParameter = (IrTypeParameter) CollectionsKt.singleOrNull((List) target.getTypeParameters());
            if (irTypeParameter != null) {
                IrTypeParameterSymbol symbol = irTypeParameter.getSymbol();
                Intrinsics.checkNotNull(type3);
                mapEmptyMap = MapsKt.mapOf(TuplesKt.to(symbol, type3));
                if (mapEmptyMap == null) {
                    mapEmptyMap = MapsKt.emptyMap();
                }
            } else {
                mapEmptyMap = MapsKt.emptyMap();
            }
            IrUtilsKt.copyValueParametersFrom(irSimpleFunctionBuildFunction, target, mapEmptyMap);
            irSimpleFunctionBuildFunction.setReturnType(type2);
            IrSymbolOwner irSymbolOwner = this.container;
            Intrinsics.checkNotNull(irSymbolOwner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationParent");
            irSimpleFunctionBuildFunction.setParent((IrDeclarationParent) irSymbolOwner);
            IrBlockBody irBlockBodyCreateBlockBody = this.context.getIrFactory().createBlockBody(expression.getStartOffset(), expression.getEndOffset());
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
            List<IrStatement> statements = irBlockBodyCreateBlockBody.getStatements();
            DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
            IrConstructorCall irConstructorCallIrCall$default = ExpressionHelpersKt.irCall$default(declarationIrBuilder, target.getSymbol(), type2, (IrClass) null, 4, (Object) null);
            if (irConstructorCallIrCall$default.getTypeArgumentsCount() != 0) {
                irConstructorCallIrCall$default.getTypeArgumentsCount();
                irConstructorCallIrCall$default.putTypeArgument(0, type3);
            }
            irConstructorCallIrCall$default.putValueArgument(0, ExpressionHelpersKt.irGet(declarationIrBuilder, irSimpleFunctionBuildFunction.getValueParameters().get(0)));
            irConstructorCallIrCall$default.putValueArgument(1, ExpressionHelpersKt.irGet(declarationIrBuilder, irSimpleFunctionBuildFunction.getValueParameters().get(1)));
            Unit unit = Unit.INSTANCE;
            statements.add(ExpressionHelpersKt.irReturn(declarationIrBuilder, irConstructorCallIrCall$default));
            irSimpleFunctionBuildFunction.setBody(irBlockBodyCreateBlockBody);
            return irSimpleFunctionBuildFunction;
        }
    }
}
