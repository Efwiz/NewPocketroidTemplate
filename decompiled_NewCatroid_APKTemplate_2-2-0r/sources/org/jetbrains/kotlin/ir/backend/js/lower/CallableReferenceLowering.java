package org.jetbrains.kotlin.ir.backend.js.lower;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import net.bytebuddy.asm.Advice;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.LowerKt;
import org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.LoweredStatementOrigins;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrElementBuilderKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrPropertyBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: CallableReferenceLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00112\u00020\u0001:\u0003\u0010\u0011\u0012B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/CallableReferenceLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "nothingType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "stringType", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "CallableReferenceBuilder", "Companion", "ReferenceTransformer", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceLowering implements BodyLoweringPass {
    private static final Name BOUND_RECEIVER_NAME;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final CommonBackendContext context;
    private final IrType nothingType;
    private final IrType stringType;

    public CallableReferenceLowering(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.nothingType = context.getIrBuiltIns().getNothingType();
        this.stringType = context.getIrBuiltIns().getStringType();
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass, org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        LowerKt.runOnFilePostfix(this, irFile, true);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrDeclarationParent parent = container instanceof IrDeclarationParent ? (IrDeclarationParent) container : null;
        if (parent == null) {
            parent = container.getParent();
        }
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new ReferenceTransformer(this, parent));
    }

    /* JADX INFO: compiled from: CallableReferenceLowering.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\t\u001a\u00020\nH\u0002J$\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u000eH\u0016J\u0010\u0010\u0014\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/CallableReferenceLowering$ReferenceTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/CallableReferenceLowering;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "buildFunctionReference", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "buildLambdaReference", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "visitBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "body", "visitFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitFunctionReference", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class ReferenceTransformer extends IrElementTransformerVoid {
        private final IrDeclarationParent container;
        final /* synthetic */ CallableReferenceLowering this$0;

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrBody visitBody(IrBody body) {
            Intrinsics.checkNotNullParameter(body, "body");
            return body;
        }

        public ReferenceTransformer(CallableReferenceLowering callableReferenceLowering, IrDeclarationParent container) {
            Intrinsics.checkNotNullParameter(container, "container");
            this.this$0 = callableReferenceLowering;
            this.container = container;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitFunctionExpression(IrFunctionExpression expression) {
            IrConstructorCallImpl irConstructorCallImpl;
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
            IrSimpleFunction function = expression.getFunction();
            Pair<IrClass, IrConstructor> pairBuildLambdaReference = buildLambdaReference(function, expression);
            IrClass irClassComponent1 = pairBuildLambdaReference.component1();
            IrConstructor irConstructorComponent2 = pairBuildLambdaReference.component2();
            irClassComponent1.setParent(this.container);
            CallableReferenceLowering callableReferenceLowering = this.this$0;
            IrConstructorCallImpl irConstructorCallImpl2 = new IrConstructorCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irConstructorComponent2.getSymbol(), 0, 0, function.getIsSuspend() ? 1 : 0, JsStatementOrigins.CALLABLE_REFERENCE_CREATE.INSTANCE, null, 256, null);
            if (function.getIsSuspend()) {
                IrConstImpl irConstImplConstNull = IrConstImpl.INSTANCE.constNull(irConstructorCallImpl2.getStartOffset(), irConstructorCallImpl2.getEndOffset(), callableReferenceLowering.context.getIrBuiltIns().getNothingNType());
                irConstructorCallImpl = irConstructorCallImpl2;
                irConstructorCallImpl.putValueArgument(0, irConstImplConstNull);
            } else {
                irConstructorCallImpl = irConstructorCallImpl2;
            }
            return new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), expression.getOrigin(), CollectionsKt.listOf((Object[]) new IrElement[]{irClassComponent1, irConstructorCallImpl}));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitFunctionReference(IrFunctionReference expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
            Pair<IrClass, IrConstructor> pairBuildFunctionReference = buildFunctionReference(expression);
            IrClass irClassComponent1 = pairBuildFunctionReference.component1();
            IrConstructor irConstructorComponent2 = pairBuildFunctionReference.component2();
            irClassComponent1.setParent(this.container);
            IrExpression dispatchReceiver = expression.getDispatchReceiver();
            if (dispatchReceiver == null) {
                dispatchReceiver = expression.getExtensionReceiver();
            }
            IrConstructorCallImpl irConstructorCallImpl = new IrConstructorCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irConstructorComponent2.getSymbol(), 0, 0, dispatchReceiver != null ? 1 : 0, JsStatementOrigins.CALLABLE_REFERENCE_CREATE.INSTANCE, null, 256, null);
            if (dispatchReceiver != null) {
                irConstructorCallImpl.putValueArgument(0, dispatchReceiver);
            }
            return new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), expression.getOrigin(), CollectionsKt.listOf((Object[]) new IrElement[]{irClassComponent1, irConstructorCallImpl}));
        }

        private final Pair<IrClass, IrConstructor> buildFunctionReference(IrFunctionReference expression) {
            IrFunction owner;
            IrFunction owner2 = expression.getSymbol().getOwner();
            IrFunctionSymbol reflectionTarget = expression.getReflectionTarget();
            if (reflectionTarget == null || (owner = reflectionTarget.getOwner()) == null) {
                owner = owner2;
            }
            return new CallableReferenceBuilder(this.this$0, owner2, expression, owner).build();
        }

        private final Pair<IrClass, IrConstructor> buildLambdaReference(IrSimpleFunction function, IrFunctionExpression expression) {
            return new CallableReferenceBuilder(this.this$0, function, expression, null).build();
        }
    }

    /* JADX INFO: compiled from: CallableReferenceLowering.kt */
    @Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00180\u0017J\b\u0010\u0019\u001a\u00020\u0012H\u0002J\u0010\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u0012H\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001b\u001a\u00020\u0012H\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001b\u001a\u00020\u0012H\u0002J\u000e\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#J\b\u0010$\u001a\u00020%H\u0002J\f\u0010&\u001a\u00020'*\u00020\u001dH\u0002J\u0018\u0010(\u001a\u00020\u001f*\u00060)j\u0002`*2\u0006\u0010\"\u001a\u00020+H\u0002J\f\u0010,\u001a\u00020-*\u00020\u0018H\u0002J\f\u0010.\u001a\u00020\u001f*\u00020\u001dH\u0002J\f\u0010/\u001a\u00020\u001f*\u00020\u001dH\u0002J\f\u00100\u001a\u00020\u001f*\u00020\u0012H\u0002R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/CallableReferenceLowering$CallableReferenceBuilder;", "", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "reference", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "reflectionTarget", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/CallableReferenceLowering;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)V", "boundReceiverField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "isKReference", "", "isLambda", "()Z", "isSuspendLambda", "referenceType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "secondFunctionInterface", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "superClass", "Lorg/jetbrains/kotlin/ir/types/IrType;", "superFunctionInterface", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "buildReferenceClass", "createConstructor", "clazz", "createInvokeMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "createNameProperty", "", "getValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "d", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "makeContextDependentName", "Lorg/jetbrains/kotlin/name/Name;", "buildInvoke", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "collectNamesForLambda", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "countContextTypeParameters", "", "createFunctionReferenceInvokeMethod", "createLambdaInvokeMethod", "createReceiverField", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class CallableReferenceBuilder {
        private IrField boundReceiverField;
        private final IrFunction function;
        private final boolean isKReference;
        private final boolean isSuspendLambda;
        private final IrExpression reference;
        private final IrSimpleType referenceType;
        private final IrFunction reflectionTarget;
        private final IrClass secondFunctionInterface;
        private final IrType superClass;
        private final IrClass superFunctionInterface;
        final /* synthetic */ CallableReferenceLowering this$0;

        public CallableReferenceBuilder(CallableReferenceLowering callableReferenceLowering, IrFunction function, IrExpression reference, IrFunction irFunction) {
            IrClass owner;
            Intrinsics.checkNotNullParameter(function, "function");
            Intrinsics.checkNotNullParameter(reference, "reference");
            this.this$0 = callableReferenceLowering;
            this.function = function;
            this.reference = reference;
            this.reflectionTarget = irFunction;
            boolean z = isLambda() && AdditionalIrUtilsKt.isSuspend(function);
            this.isSuspendLambda = z;
            this.superClass = z ? IrUtilsKt.getDefaultType(callableReferenceLowering.context.getIr().getSymbols().getCoroutineImpl().getOwner()) : callableReferenceLowering.context.getIrBuiltIns().getAnyType();
            IrType type = reference.getType();
            Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            IrSimpleType irSimpleType = (IrSimpleType) type;
            this.referenceType = irSimpleType;
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irSimpleType);
            IrClass owner2 = null;
            if (classOrNull == null || (owner = classOrNull.getOwner()) == null) {
                CompilationExceptionKt.compilationException("Expected functional type", reference);
                throw null;
            }
            this.superFunctionInterface = owner;
            boolean z2 = owner.getName().getIdentifier().charAt(0) == 'K';
            this.isKReference = z2;
            if (z2) {
                int size = irSimpleType.getArguments().size() - 1;
                owner2 = AdditionalIrUtilsKt.isSuspend(function) ? callableReferenceLowering.context.getIr().getSymbols().suspendFunctionN(size).getOwner() : callableReferenceLowering.context.getIr().getSymbols().functionN(size).getOwner();
            }
            this.secondFunctionInterface = owner2;
        }

        private final boolean isLambda() {
            return this.reflectionTarget == null;
        }

        private final void collectNamesForLambda(StringBuilder sb, IrDeclarationWithName irDeclarationWithName) {
            IrDeclarationParent parent = irDeclarationWithName.getParent();
            if (parent instanceof IrPackageFragment) {
                sb.append(irDeclarationWithName.getName().asString());
                return;
            }
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName");
            collectNamesForLambda(sb, (IrDeclarationWithName) parent);
            if (irDeclarationWithName instanceof IrAnonymousInitializer) {
                return;
            }
            if (collectNamesForLambda$isLambdaFun(irDeclarationWithName)) {
                sb.append('$');
                if ((irDeclarationWithName instanceof IrSimpleFunction) && ((IrSimpleFunction) irDeclarationWithName).getIsSuspend()) {
                    sb.append(Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL);
                }
                sb.append("lambda");
                return;
            }
            if (Intrinsics.areEqual(irDeclarationWithName.getName(), SpecialNames.NO_NAME_PROVIDED)) {
                sb.append("$o");
            } else {
                sb.append('$');
                sb.append(irDeclarationWithName.getName().asString());
            }
        }

        private static final boolean collectNamesForLambda$isLambdaFun(IrDeclaration irDeclaration) {
            return Intrinsics.areEqual(irDeclaration.getOrigin(), IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA.INSTANCE);
        }

        private final Name makeContextDependentName() {
            StringBuilder sb = new StringBuilder();
            collectNamesForLambda(sb, this.function);
            if (!isLambda()) {
                sb.append("$ref");
            }
            Name nameIdentifier = Name.identifier(sb.toString());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            return nameIdentifier;
        }

        private final IrClass buildReferenceClass() {
            IrClassSymbol symbol;
            IrFactory irFactory = this.this$0.context.getIrFactory();
            IrClassBuilder irClassBuilder = new IrClassBuilder();
            IrElementBuilderKt.setSourceRange(irClassBuilder, this.reference);
            DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
            Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
            irClassBuilder.setVisibility(LOCAL);
            irClassBuilder.setOrigin((this.isKReference || !isLambda()) ? Companion.FUNCTION_REFERENCE_IMPL.INSTANCE : Companion.LAMBDA_IMPL.INSTANCE);
            irClassBuilder.setName(makeContextDependentName());
            IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
            IrType[] irTypeArr = new IrType[3];
            irTypeArr[0] = this.superClass;
            irTypeArr[1] = this.referenceType;
            IrClass irClass = this.secondFunctionInterface;
            irTypeArr[2] = (irClass == null || (symbol = irClass.getSymbol()) == null) ? null : IrTypesKt.typeWithArguments(symbol, this.referenceType.getArguments());
            irClassBuildClass.setSuperTypes(CollectionsKt.listOfNotNull((Object[]) irTypeArr));
            IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
            createReceiverField(irClassBuildClass);
            return irClassBuildClass;
        }

        private final void createReceiverField(IrClass irClass) {
            if (isLambda()) {
                return;
            }
            IrExpression irExpression = this.reference;
            Intrinsics.checkNotNull(irExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionReference");
            IrFunctionReference irFunctionReference = (IrFunctionReference) irExpression;
            IrExpression dispatchReceiver = irFunctionReference.getDispatchReceiver();
            if (dispatchReceiver == null) {
                dispatchReceiver = irFunctionReference.getExtensionReceiver();
            }
            if (dispatchReceiver != null) {
                this.boundReceiverField = DeclarationBuildersKt.addField$default(irClass, CallableReferenceLowering.INSTANCE.getBOUND_RECEIVER_NAME(), dispatchReceiver.getType(), (DescriptorVisibility) null, 4, (Object) null);
            }
        }

        private final IrSimpleFunction createInvokeMethod(IrClass clazz) {
            IrSimpleFunction invokeFun;
            IrSimpleFunction invokeFun2 = IrUtilsKt.getInvokeFun(this.superFunctionInterface);
            Intrinsics.checkNotNull(invokeFun2);
            IrFactory factory = clazz.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            IrElementBuilderKt.setSourceRange(irFunctionBuilder, isLambda() ? this.function : this.reference);
            irFunctionBuilder.setName(invokeFun2.getName());
            irFunctionBuilder.setReturnType(this.function.getReturnType());
            irFunctionBuilder.setSuspend(invokeFun2.getIsSuspend());
            irFunctionBuilder.setOperator(invokeFun2.getIsOperator());
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
            IrClass irClass = clazz;
            irClass.getDeclarations().add(irSimpleFunctionBuildFunction);
            irSimpleFunctionBuildFunction.setParent(irClass);
            IrClass irClass2 = this.secondFunctionInterface;
            if (irClass2 != null) {
                invokeFun = IrUtilsKt.getInvokeFun(irClass2);
                Intrinsics.checkNotNull(invokeFun);
            } else {
                invokeFun = null;
            }
            IrSimpleFunctionSymbol[] irSimpleFunctionSymbolArr = new IrSimpleFunctionSymbol[2];
            irSimpleFunctionSymbolArr[0] = invokeFun2.getSymbol();
            irSimpleFunctionSymbolArr[1] = invokeFun != null ? invokeFun.getSymbol() : null;
            irSimpleFunctionBuildFunction.setOverriddenSymbols(CollectionsKt.listOfNotNull((Object[]) irSimpleFunctionSymbolArr));
            irSimpleFunctionBuildFunction.setDispatchReceiverParameter(DeclarationBuildersKt.buildReceiverParameter(irSimpleFunctionBuildFunction, clazz.getOrigin(), IrUtilsKt.getDefaultType(clazz), irSimpleFunctionBuildFunction.getStartOffset(), irSimpleFunctionBuildFunction.getEndOffset()));
            if (isLambda()) {
                createLambdaInvokeMethod(irSimpleFunctionBuildFunction);
            } else {
                createFunctionReferenceInvokeMethod(irSimpleFunctionBuildFunction);
            }
            return irSimpleFunctionBuildFunction;
        }

        private final void createLambdaInvokeMethod(IrSimpleFunction irSimpleFunction) {
            irSimpleFunction.setAnnotations(this.function.getAnnotations());
            Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(IrUtilsKt.getExplicitParameters(this.function));
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
            for (IndexedValue indexedValue : iterableWithIndex) {
                int index = indexedValue.getIndex();
                IrValueParameter irValueParameter = (IrValueParameter) indexedValue.component2();
                Pair pair = TuplesKt.to(irValueParameter, IrUtilsKt.copyTo$default(irValueParameter, irSimpleFunction, null, index, 0, 0, null, null, null, null, null, false, false, false, 8186, null));
                linkedHashMap.put(pair.getFirst(), pair.getSecond());
            }
            irSimpleFunction.setValueParameters(CollectionsKt.toList(linkedHashMap.values()));
            irSimpleFunction.setBody(IrInlineUtilsKt.moveBodyTo(this.function, irSimpleFunction, linkedHashMap));
        }

        public final IrGetValue getValue(IrValueDeclaration d) {
            Intrinsics.checkNotNullParameter(d, "d");
            return new IrGetValueImpl(-1, -1, d.getType(), d.getSymbol(), JsStatementOrigins.CALLABLE_REFERENCE_INVOKE.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private static final int countContextTypeParameters$countImpl(IrDeclarationParent irDeclarationParent) {
            List<IrTypeParameter> typeParameters;
            int size = 0;
            if (irDeclarationParent instanceof IrClass) {
                IrClass irClass = (IrClass) irDeclarationParent;
                return (irClass.getIsInner() ? countContextTypeParameters$countImpl(irClass.getParent()) : 0) + irClass.getTypeParameters().size();
            }
            if (irDeclarationParent instanceof IrFunction) {
                IrFunction irFunction = (IrFunction) irDeclarationParent;
                return irFunction.getTypeParameters().size() + countContextTypeParameters$countImpl(irFunction.getParent());
            }
            if (!(irDeclarationParent instanceof IrProperty)) {
                if (irDeclarationParent instanceof IrDeclaration) {
                    return countContextTypeParameters$countImpl(((IrDeclaration) irDeclarationParent).getParent());
                }
                return 0;
            }
            IrProperty irProperty = (IrProperty) irDeclarationParent;
            IrSimpleFunction getter = irProperty.getGetter();
            if (getter == null) {
                getter = irProperty.getSetter();
            }
            if (getter != null && (typeParameters = getter.getTypeParameters()) != null) {
                size = typeParameters.size();
            }
            return size + countContextTypeParameters$countImpl(((IrProperty) irDeclarationParent).getParent());
        }

        private final int countContextTypeParameters(IrConstructor irConstructor) {
            return countContextTypeParameters$countImpl(irConstructor.getParent());
        }

        private final IrFunctionAccessExpression buildInvoke(IrSimpleFunction irSimpleFunction) {
            IrCallImpl irCallImpl;
            int i;
            IrFunction irFunction = this.function;
            if (irFunction instanceof IrConstructor) {
                IrConstructor irConstructor = (IrConstructor) irFunction;
                irCallImpl = new IrConstructorCallImpl(-1, -1, IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(irFunction)), irConstructor.getSymbol(), countContextTypeParameters(irConstructor), irFunction.getTypeParameters().size(), irFunction.getValueParameters().size(), JsStatementOrigins.CALLABLE_REFERENCE_INVOKE.INSTANCE, null, 256, null);
            } else if (irFunction instanceof IrSimpleFunction) {
                irCallImpl = new IrCallImpl(-1, -1, irFunction.getReturnType(), ((IrSimpleFunction) irFunction).getSymbol(), irFunction.getTypeParameters().size(), irFunction.getValueParameters().size(), JsStatementOrigins.CALLABLE_REFERENCE_INVOKE.INSTANCE, null, 128, null);
            } else {
                CompilationExceptionKt.compilationException("unknown function kind", (IrDeclaration) irFunction);
                throw null;
            }
            IrExpression irExpression = this.reference;
            Intrinsics.checkNotNull(irExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionReference");
            IrFunctionReference irFunctionReference = (IrFunctionReference) irExpression;
            IrExpression dispatchReceiver = irFunctionReference.getDispatchReceiver();
            if (dispatchReceiver == null) {
                dispatchReceiver = irFunctionReference.getExtensionReceiver();
            }
            int i2 = 1;
            boolean z = dispatchReceiver != null;
            IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
            if (dispatchReceiverParameter == null) {
                dispatchReceiverParameter = irFunction.getExtensionReceiverParameter();
            }
            boolean z2 = dispatchReceiverParameter != null;
            irCallImpl.setDispatchReceiver(irFunctionReference.getDispatchReceiver());
            irCallImpl.setExtensionReceiver(irFunctionReference.getExtensionReceiver());
            List<IrValueParameter> valueParameters = irSimpleFunction.getValueParameters();
            int typeArgumentsCount = irFunctionReference.getTypeArgumentsCount();
            for (int i3 = 0; i3 < typeArgumentsCount; i3++) {
                irCallImpl.putTypeArgument(i3, irFunctionReference.getTypeArgument(i3));
            }
            if (!z2) {
                i2 = 0;
            } else if (!z) {
                if (irFunction.getDispatchReceiverParameter() != null) {
                    irCallImpl.setDispatchReceiver(getValue(valueParameters.get(0)));
                } else {
                    i2 = 0;
                }
                if (irFunction.getExtensionReceiverParameter() != null) {
                    i = i2 + 1;
                    irCallImpl.setExtensionReceiver(getValue(valueParameters.get(i2)));
                    i2 = i;
                }
            } else {
                IrField irField = this.boundReceiverField;
                if (irField != null) {
                    IrValueParameter dispatchReceiverParameter2 = irSimpleFunction.getDispatchReceiverParameter();
                    Intrinsics.checkNotNull(dispatchReceiverParameter2);
                    IrGetFieldImpl irGetFieldImpl = new IrGetFieldImpl(-1, -1, irField.getSymbol(), irField.getType(), getValue(dispatchReceiverParameter2), JsStatementOrigins.CALLABLE_REFERENCE_INVOKE.INSTANCE, null, 64, null);
                    if (irFunctionReference.getDispatchReceiver() != null) {
                        irCallImpl.setDispatchReceiver(irGetFieldImpl);
                    }
                    if (irFunctionReference.getExtensionReceiver() != null) {
                        irCallImpl.setExtensionReceiver(irGetFieldImpl);
                    }
                }
                if (irFunction.getDispatchReceiverParameter() == null || irFunctionReference.getDispatchReceiver() != null) {
                    i2 = 0;
                } else {
                    irCallImpl.setDispatchReceiver(getValue(valueParameters.get(0)));
                }
                if (irFunction.getExtensionReceiverParameter() != null && irFunctionReference.getExtensionReceiver() == null) {
                    i = i2 + 1;
                    irCallImpl.setExtensionReceiver(getValue(valueParameters.get(i2)));
                    i2 = i;
                }
            }
            int valueArgumentsCount = irFunctionReference.getValueArgumentsCount();
            for (int i4 = 0; i4 < valueArgumentsCount; i4++) {
                IrExpression valueArgument = irFunctionReference.getValueArgument(i4);
                if (valueArgument == null) {
                    int i5 = i2 + 1;
                    IrGetValue value = getValue(valueParameters.get(i2));
                    i2 = i5;
                    valueArgument = value;
                }
                irCallImpl.putValueArgument(i4, valueArgument);
            }
            if (i2 == valueParameters.size()) {
                return irCallImpl;
            }
            throw new IllegalStateException("Unused parameters are left".toString());
        }

        private final void createFunctionReferenceInvokeMethod(IrSimpleFunction irSimpleFunction) {
            IrType type = this.reference.getType();
            Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            List<IrTypeArgument> arguments = ((IrSimpleType) type).getArguments();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
            for (IrTypeArgument irTypeArgument : arguments) {
                Intrinsics.checkNotNull(irTypeArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrTypeProjection");
                arrayList.add(((IrTypeProjection) irTypeArgument).getType());
            }
            List listDropLast = CollectionsKt.dropLast(arrayList, 1);
            ArrayList arrayList2 = new ArrayList(listDropLast.size());
            int i = 0;
            for (Object obj : listDropLast) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                Name nameIdentifier = Name.identifier("p" + i);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irValueParameterBuilder.setName(nameIdentifier);
                irValueParameterBuilder.setType((IrType) obj);
                irValueParameterBuilder.setIndex(i);
                IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
                arrayList2.add(DeclarationBuildersKt.buildValueParameter(irSimpleFunction2.getFactory(), irValueParameterBuilder, irSimpleFunction2));
                i = i2;
            }
            irSimpleFunction.setValueParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2));
            irSimpleFunction.setBody(IrFactoryHelpersKt.createBlockBody(irSimpleFunction.getFactory(), -1, -1, (List<? extends IrStatement>) CollectionsKt.listOf(new IrReturnImpl(-1, -1, this.this$0.nothingType, irSimpleFunction.getSymbol(), buildInvoke(irSimpleFunction)))));
        }

        private final void createNameProperty(IrClass clazz) {
            if (this.isKReference) {
                List<IrDeclaration> declarations = this.superFunctionInterface.getDeclarations();
                ArrayList arrayList = new ArrayList();
                for (Object obj : declarations) {
                    if (obj instanceof IrProperty) {
                        arrayList.add(obj);
                    }
                }
                boolean z = false;
                Object obj2 = null;
                for (Object obj3 : arrayList) {
                    if (Intrinsics.areEqual(((IrProperty) obj3).getName(), StandardNames.NAME)) {
                        if (z) {
                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                        }
                        z = true;
                        obj2 = obj3;
                    }
                }
                if (z) {
                    IrProperty irProperty = (IrProperty) obj2;
                    IrSimpleFunction getter = irProperty.getGetter();
                    if (getter == null) {
                        CompilationExceptionKt.compilationException("Expected getter for KFunction.name property", (IrDeclaration) irProperty);
                        throw null;
                    }
                    IrFactory factory = clazz.getFactory();
                    IrPropertyBuilder irPropertyBuilder = new IrPropertyBuilder();
                    irPropertyBuilder.setVisibility(irProperty.getVisibility());
                    irPropertyBuilder.setName(irProperty.getName());
                    irPropertyBuilder.setOrigin(Companion.GENERATED_MEMBER_IN_CALLABLE_REFERENCE.INSTANCE);
                    IrProperty irPropertyBuildProperty = DeclarationBuildersKt.buildProperty(factory, irPropertyBuilder);
                    clazz.getDeclarations().add(irPropertyBuildProperty);
                    irPropertyBuildProperty.setParent(clazz);
                    CallableReferenceLowering callableReferenceLowering = this.this$0;
                    IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                    Name nameSpecial = Name.special("<get-" + irPropertyBuildProperty.getName() + '>');
                    Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
                    irFunctionBuilder.setName(nameSpecial);
                    irFunctionBuilder.setReturnType(callableReferenceLowering.stringType);
                    IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irPropertyBuildProperty.getFactory(), irFunctionBuilder);
                    irPropertyBuildProperty.setGetter(irSimpleFunctionBuildFunction);
                    irSimpleFunctionBuildFunction.setCorrespondingPropertySymbol(irPropertyBuildProperty.getSymbol());
                    irSimpleFunctionBuildFunction.setParent(irPropertyBuildProperty.getParent());
                    irSimpleFunctionBuildFunction.setOverriddenSymbols(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(irSimpleFunctionBuildFunction.getOverriddenSymbols(), getter.getSymbol()));
                    IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                    irValueParameterBuilder.setName(SpecialNames.THIS);
                    irValueParameterBuilder.setType(IrUtilsKt.getDefaultType(clazz));
                    IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
                    irSimpleFunctionBuildFunction.setDispatchReceiverParameter(DeclarationBuildersKt.buildValueParameter(irSimpleFunction.getFactory(), irValueParameterBuilder, irSimpleFunction));
                    IrFactory irFactory = this.this$0.context.getIrFactory();
                    IrType irType = this.this$0.nothingType;
                    IrSimpleFunctionSymbol symbol = irSimpleFunctionBuildFunction.getSymbol();
                    IrConstImpl.Companion companion = IrConstImpl.INSTANCE;
                    IrType irType2 = this.this$0.stringType;
                    IrFunction irFunction = this.reflectionTarget;
                    Intrinsics.checkNotNull(irFunction);
                    String strAsString = irFunction.getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    irSimpleFunctionBuildFunction.setBody(IrFactoryHelpersKt.createBlockBody(irFactory, -1, -1, (List<? extends IrStatement>) CollectionsKt.listOf(new IrReturnImpl(-1, -1, irType, symbol, companion.string(-1, -1, irType2, strAsString)))));
                    this.this$0.context.getMapping().getReflectedNameAccessor().set(clazz, irSimpleFunctionBuildFunction);
                    return;
                }
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
        }

        public final Pair<IrClass, IrConstructor> build() {
            IrClass irClassBuildReferenceClass = buildReferenceClass();
            IrConstructor irConstructorCreateConstructor = createConstructor(irClassBuildReferenceClass);
            createInvokeMethod(irClassBuildReferenceClass);
            createNameProperty(irClassBuildReferenceClass);
            return new Pair<>(irClassBuildReferenceClass, irConstructorCreateConstructor);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v16, types: [T, java.lang.Object, org.jetbrains.kotlin.ir.declarations.IrValueParameter] */
        private final IrConstructor createConstructor(IrClass clazz) {
            IrFactory factory = clazz.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setOrigin(Companion.GENERATED_MEMBER_IN_CALLABLE_REFERENCE.INSTANCE);
            irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(clazz));
            irFunctionBuilder.setPrimary(true);
            irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(clazz));
            IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
            clazz.getDeclarations().add(irConstructorBuildConstructor);
            irConstructorBuildConstructor.setParent(clazz);
            CallableReferenceLowering callableReferenceLowering = this.this$0;
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(this.superClass);
            Intrinsics.checkNotNull(classOrNull);
            IrValueParameter irValueParameter = null;
            Object obj = null;
            boolean z = false;
            for (Object obj2 : classOrNull.getOwner().getDeclarations()) {
                IrDeclaration irDeclaration = (IrDeclaration) obj2;
                if ((irDeclaration instanceof IrConstructor) && ((IrConstructor) irDeclaration).getIsPrimary()) {
                    if (z) {
                        throw new IllegalArgumentException("Collection contains more than one matching element.");
                    }
                    z = true;
                    obj = obj2;
                }
            }
            if (z) {
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrConstructor");
                IrConstructor irConstructor = (IrConstructor) obj;
                IrField irField = this.boundReceiverField;
                if (irField != null) {
                    IrConstructor irConstructor2 = irConstructorBuildConstructor;
                    IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                    irValueParameterBuilder.setName(CallableReferenceLowering.INSTANCE.getBOUND_RECEIVER_NAME());
                    irValueParameterBuilder.setType(irField.getType());
                    irValueParameterBuilder.setIndex(0);
                    if (irValueParameterBuilder.getIndex() == -1) {
                        irValueParameterBuilder.setIndex(irConstructor2.getValueParameters().size());
                    }
                    IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irConstructor2.getFactory(), irValueParameterBuilder, irConstructor2);
                    irConstructor2.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irConstructor2.getValueParameters(), irValueParameterBuildValueParameter));
                    irValueParameter = irValueParameterBuildValueParameter;
                }
                Ref.ObjectRef objectRef = new Ref.ObjectRef();
                if (this.isSuspendLambda) {
                    IrValueParameter irValueParameter2 = (IrValueParameter) CollectionsKt.single((List) irConstructor.getValueParameters());
                    IrConstructor irConstructor3 = irConstructorBuildConstructor;
                    IrValueParameterBuilder irValueParameterBuilder2 = new IrValueParameterBuilder();
                    irValueParameterBuilder2.setName(irValueParameter2.getName());
                    irValueParameterBuilder2.setType(irValueParameter2.getType());
                    irValueParameterBuilder2.setIndex(irValueParameter == null ? 0 : 1);
                    if (irValueParameterBuilder2.getIndex() == -1) {
                        irValueParameterBuilder2.setIndex(irConstructor3.getValueParameters().size());
                    }
                    ?? BuildValueParameter = DeclarationBuildersKt.buildValueParameter(irConstructor3.getFactory(), irValueParameterBuilder2, irConstructor3);
                    irConstructor3.setValueParameters(CollectionsKt.plus((Collection<? extends ??>) irConstructor3.getValueParameters(), BuildValueParameter));
                    objectRef.element = BuildValueParameter;
                }
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(callableReferenceLowering.context, irConstructorBuildConstructor.getSymbol(), 0, 0, 6, (Object) null);
                IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), irConstructorBuildConstructor.getStartOffset(), irConstructorBuildConstructor.getEndOffset());
                IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                IrDelegatingConstructorCall irDelegatingConstructorCall = ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder2, irConstructor);
                IrValueParameter irValueParameter3 = (IrValueParameter) objectRef.element;
                if (irValueParameter3 != null) {
                    irDelegatingConstructorCall.putValueArgument(0, getValue(irValueParameter3));
                }
                irBlockBodyBuilder.unaryPlus(irDelegatingConstructorCall);
                if (irValueParameter != null) {
                    IrValueParameter thisReceiver = clazz.getThisReceiver();
                    Intrinsics.checkNotNull(thisReceiver);
                    IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBodyBuilder2, thisReceiver);
                    IrField irField2 = this.boundReceiverField;
                    Intrinsics.checkNotNull(irField2);
                    irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irSetField(irBlockBodyBuilder2, irGetValueImplIrGet, irField2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameter), LoweredStatementOrigins.STATEMENT_ORIGIN_INITIALIZER_OF_FIELD_FOR_CAPTURED_VALUE.INSTANCE));
                }
                irBlockBodyBuilder.unaryPlus(new IrInstanceInitializerCallImpl(irBlockBodyBuilder.getStartOffset(), irBlockBodyBuilder.getEndOffset(), clazz.getSymbol(), irBlockBodyBuilder.getContext().getIrBuiltIns().getUnitType()));
                irConstructorBuildConstructor.setBody(irBlockBodyBuilder.getIrBlockBody());
                return irConstructorBuildConstructor;
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
    }

    /* JADX INFO: compiled from: CallableReferenceLowering.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0003\u0007\b\tB\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/CallableReferenceLowering$Companion;", "", "()V", "BOUND_RECEIVER_NAME", "Lorg/jetbrains/kotlin/name/Name;", "getBOUND_RECEIVER_NAME", "()Lorg/jetbrains/kotlin/name/Name;", "FUNCTION_REFERENCE_IMPL", "GENERATED_MEMBER_IN_CALLABLE_REFERENCE", "LAMBDA_IMPL", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: CallableReferenceLowering.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/CallableReferenceLowering$Companion$LAMBDA_IMPL;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class LAMBDA_IMPL extends IrDeclarationOriginImpl {
            public static final LAMBDA_IMPL INSTANCE = new LAMBDA_IMPL();

            private LAMBDA_IMPL() {
                super("LAMBDA_IMPL", false, 2, null);
            }
        }

        private Companion() {
        }

        /* JADX INFO: compiled from: CallableReferenceLowering.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/CallableReferenceLowering$Companion$FUNCTION_REFERENCE_IMPL;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class FUNCTION_REFERENCE_IMPL extends IrDeclarationOriginImpl {
            public static final FUNCTION_REFERENCE_IMPL INSTANCE = new FUNCTION_REFERENCE_IMPL();

            private FUNCTION_REFERENCE_IMPL() {
                super("FUNCTION_REFERENCE_IMPL", false, 2, null);
            }
        }

        /* JADX INFO: compiled from: CallableReferenceLowering.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/CallableReferenceLowering$Companion$GENERATED_MEMBER_IN_CALLABLE_REFERENCE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class GENERATED_MEMBER_IN_CALLABLE_REFERENCE extends IrDeclarationOriginImpl {
            public static final GENERATED_MEMBER_IN_CALLABLE_REFERENCE INSTANCE = new GENERATED_MEMBER_IN_CALLABLE_REFERENCE();

            private GENERATED_MEMBER_IN_CALLABLE_REFERENCE() {
                super("GENERATED_MEMBER_IN_CALLABLE_REFERENCE", false, 2, null);
            }
        }

        public final Name getBOUND_RECEIVER_NAME() {
            return CallableReferenceLowering.BOUND_RECEIVER_NAME;
        }
    }

    static {
        Name nameIdentifier = Name.identifier("$boundThis");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        BOUND_RECEIVER_NAME = nameIdentifier;
    }
}
