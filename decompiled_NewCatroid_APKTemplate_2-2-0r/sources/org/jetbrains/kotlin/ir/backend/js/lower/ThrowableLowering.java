package org.jetbrains.kotlin.ir.backend.js.lower;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
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
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ThrowableLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\u001a\u001bB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\f\u0010\u0017\u001a\u00020\u0018*\u00020\u0019H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ThrowableLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "extendThrowableFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "getExtendThrowableFunction", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "newThrowableFunction", "throwableConstructors", "", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "undefinedValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "extractThrowableArguments", "Lorg/jetbrains/kotlin/ir/backend/js/lower/ThrowableLowering$ThrowableArguments;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "ThrowableArguments", "Transformer", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ThrowableLowering implements BodyLoweringPass {
    private final JsIrBackendContext context;
    private final IrSimpleFunctionSymbol extendThrowableFunction;
    private final IrSimpleFunctionSymbol newThrowableFunction;
    private final List<IrConstructorSymbol> throwableConstructors;

    public ThrowableLowering(JsIrBackendContext context, IrSimpleFunctionSymbol extendThrowableFunction) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(extendThrowableFunction, "extendThrowableFunction");
        this.context = context;
        this.extendThrowableFunction = extendThrowableFunction;
        this.throwableConstructors = context.getThrowableConstructors();
        this.newThrowableFunction = context.getNewThrowableSymbol();
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    public final IrSimpleFunctionSymbol getExtendThrowableFunction() {
        return this.extendThrowableFunction;
    }

    private final IrExpression undefinedValue() {
        return IrJsUtilsKt.getVoid(this.context);
    }

    /* JADX INFO: compiled from: ThrowableLowering.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ThrowableLowering$ThrowableArguments;", "", CrashHianalyticsData.MESSAGE, "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "cause", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getCause", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getMessage", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ThrowableArguments {
        private final IrExpression cause;
        private final IrExpression message;

        public static /* synthetic */ ThrowableArguments copy$default(ThrowableArguments throwableArguments, IrExpression irExpression, IrExpression irExpression2, int i, Object obj) {
            if ((i & 1) != 0) {
                irExpression = throwableArguments.message;
            }
            if ((i & 2) != 0) {
                irExpression2 = throwableArguments.cause;
            }
            return throwableArguments.copy(irExpression, irExpression2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrExpression getMessage() {
            return this.message;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final IrExpression getCause() {
            return this.cause;
        }

        public final ThrowableArguments copy(IrExpression message, IrExpression cause) {
            Intrinsics.checkNotNullParameter(message, "message");
            Intrinsics.checkNotNullParameter(cause, "cause");
            return new ThrowableArguments(message, cause);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ThrowableArguments)) {
                return false;
            }
            ThrowableArguments throwableArguments = (ThrowableArguments) other;
            return Intrinsics.areEqual(this.message, throwableArguments.message) && Intrinsics.areEqual(this.cause, throwableArguments.cause);
        }

        public int hashCode() {
            return (this.message.hashCode() * 31) + this.cause.hashCode();
        }

        public String toString() {
            return "ThrowableArguments(message=" + this.message + ", cause=" + this.cause + ')';
        }

        public ThrowableArguments(IrExpression message, IrExpression cause) {
            Intrinsics.checkNotNullParameter(message, "message");
            Intrinsics.checkNotNullParameter(cause, "cause");
            this.message = message;
            this.cause = cause;
        }

        public final IrExpression getMessage() {
            return this.message;
        }

        public final IrExpression getCause() {
            return this.cause;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrElement parentClassOrNull = IrUtilsKt.getParentClassOrNull(container);
        Transformer transformer = new Transformer();
        if (parentClassOrNull == null) {
            parentClassOrNull = IrUtilsKt.getFile(container);
        }
        irBody.transformChildren(transformer, parentClassOrNull);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ThrowableArguments extractThrowableArguments(IrFunctionAccessExpression irFunctionAccessExpression) {
        int valueArgumentsCount = irFunctionAccessExpression.getValueArgumentsCount();
        if (valueArgumentsCount == 0) {
            return new ThrowableArguments(undefinedValue(), undefinedValue());
        }
        if (valueArgumentsCount == 2) {
            IrExpression valueArgument = irFunctionAccessExpression.getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument);
            IrExpression valueArgument2 = irFunctionAccessExpression.getValueArgument(1);
            Intrinsics.checkNotNull(valueArgument2);
            return new ThrowableArguments(valueArgument, valueArgument2);
        }
        IrExpression valueArgument3 = irFunctionAccessExpression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument3);
        IrValueParameter irValueParameter = irFunctionAccessExpression.getSymbol().getOwner().getValueParameters().get(0);
        if (IrTypePredicatesKt.isNullableString(irValueParameter.getType())) {
            return new ThrowableArguments(valueArgument3, undefinedValue());
        }
        IrTypeUtilsKt.isThrowable(IrTypesKt.makeNotNull(irValueParameter.getType()));
        return new ThrowableArguments(undefinedValue(), valueArgument3);
    }

    /* JADX INFO: compiled from: ThrowableLowering.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0002H\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u0002H\u0016J\u0018\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ThrowableLowering$Transformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/ThrowableLowering;)V", "anyConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "data", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class Transformer implements IrElementTransformer<IrDeclarationParent> {
        private final IrConstructorSymbol anyConstructor;

        public Transformer() {
            this.anyConstructor = (IrConstructorSymbol) SequencesKt.first(IrUtilsKt.getConstructors(ThrowableLowering.this.getContext().getIrBuiltIns().getAnyClass()));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
        public IrStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
        public IrExpression visitBlock2(IrBlock irBlock, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitBlock(this, irBlock, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
        public IrBody visitBlockBody2(IrBlockBody irBlockBody, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
        public IrBody visitBody2(IrBody irBody, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitBody(this, irBody, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
        public IrBranch visitBranch2(IrBranch irBranch, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitBranch(this, irBranch, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
        public IrExpression visitBreak2(IrBreak irBreak, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitBreak(this, irBreak, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitBreakContinue2(IrBreakContinue irBreakContinue, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
        public IrElement visitCall2(IrCall irCall, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitCall(this, irCall, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ IrElement visitCallableReference(IrCallableReference irCallableReference, Object obj) {
            return visitCallableReference((IrCallableReference<?>) irCallableReference, (IrDeclarationParent) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
        public /* bridge */ /* synthetic */ IrElement visitCallableReference2(IrCallableReference irCallableReference, Object obj) {
            return visitCallableReference((IrCallableReference<?>) irCallableReference, (IrDeclarationParent) obj);
        }

        public IrElement visitCallableReference(IrCallableReference<?> irCallableReference, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
        public IrCatch visitCatch2(IrCatch irCatch, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitCatch(this, irCatch, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
        public IrExpression visitClassReference2(IrClassReference irClassReference, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitClassReference(this, irClassReference, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
        public IrExpression visitComposite2(IrComposite irComposite, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitComposite(this, irComposite, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ IrElement visitConst(IrConst irConst, Object obj) {
            return visitConst((IrConst<?>) irConst, (IrDeclarationParent) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
        public /* bridge */ /* synthetic */ IrElement visitConst2(IrConst irConst, Object obj) {
            return visitConst((IrConst<?>) irConst, (IrDeclarationParent) obj);
        }

        public IrExpression visitConst(IrConst<?> irConst, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitConst(this, irConst, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
        public IrConstantValue visitConstantArray2(IrConstantArray irConstantArray, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
        public IrConstantValue visitConstantObject2(IrConstantObject irConstantObject, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
        public IrConstantValue visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
        public IrConstantValue visitConstantValue2(IrConstantValue irConstantValue, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
        public IrStatement visitConstructor2(IrConstructor irConstructor, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitConstructor(this, irConstructor, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitContainerExpression2(IrContainerExpression irContainerExpression, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitContainerExpression(this, irContainerExpression, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitContinue2(IrContinue irContinue, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitContinue(this, irContinue, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
        public IrStatement visitDeclaration2(IrDeclarationBase irDeclarationBase, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitDeclaration(this, irDeclarationBase, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
        public IrElement visitElement2(IrElement irElement, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitElement(this, irElement, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
        public IrElseBranch visitElseBranch2(IrElseBranch irElseBranch, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
        public IrElement visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
        public IrStatement visitEnumEntry2(IrEnumEntry irEnumEntry, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
        public IrStatement visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitErrorExpression2(IrErrorExpression irErrorExpression, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitExpression2(IrExpression irExpression, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitExpression(this, irExpression, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
        public IrBody visitExpressionBody2(IrExpressionBody irExpressionBody, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
        public IrExternalPackageFragment visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
        public IrStatement visitField2(IrField irField, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitField(this, irField, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
        public IrExpression visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
        public IrFile visitFile2(IrFile irFile, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitFile(this, irFile, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
        public IrStatement visitFunction2(IrFunction irFunction, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitFunction(this, irFunction, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
        public IrElement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
        public IrElement visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
        public IrElement visitFunctionReference2(IrFunctionReference irFunctionReference, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetClass2(IrGetClass irGetClass, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitGetClass(this, irGetClass, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetField2(IrGetField irGetField, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitGetField(this, irGetField, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitGetValue2(IrGetValue irGetValue, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitGetValue(this, irGetValue, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
        public IrExpression visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
        public IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
        public IrElement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
        public IrExpression visitLoop2(IrLoop irLoop, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitLoop(this, irLoop, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
            return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrDeclarationParent) obj);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
        public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
            return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrDeclarationParent) obj);
        }

        public IrElement visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
        public IrModuleFragment visitModuleFragment2(IrModuleFragment irModuleFragment, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitModuleFragment(this, irModuleFragment, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
        public IrElement visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
        public IrStatement visitProperty2(IrProperty irProperty, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitProperty(this, irProperty, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
        public IrElement visitPropertyReference2(IrPropertyReference irPropertyReference, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
        public IrExpression visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
        public IrExpression visitReturn2(IrReturn irReturn, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitReturn(this, irReturn, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
        public IrStatement visitScript2(IrScript irScript, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitScript(this, irScript, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSetField2(IrSetField irSetField, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitSetField(this, irSetField, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSetValue2(IrSetValue irSetValue, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitSetValue(this, irSetValue, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
        public IrStatement visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
        public IrSpreadElement visitSpreadElement2(IrSpreadElement irSpreadElement, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
        public IrExpression visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
        public IrExpression visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
        public IrBody visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
        public IrExpression visitThrow2(IrThrow irThrow, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitThrow(this, irThrow, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
        public IrExpression visitTry2(IrTry irTry, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitTry(this, irTry, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
        public IrStatement visitTypeAlias2(IrTypeAlias irTypeAlias, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
        public IrExpression visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
        public IrStatement visitTypeParameter2(IrTypeParameter irTypeParameter, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
        public IrExpression visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
        public IrStatement visitValueParameter2(IrValueParameter irValueParameter, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
        public IrExpression visitVararg2(IrVararg irVararg, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitVararg(this, irVararg, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
        public IrStatement visitVariable2(IrVariable irVariable, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitVariable(this, irVariable, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
        public IrExpression visitWhen2(IrWhen irWhen, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitWhen(this, irWhen, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
        public IrExpression visitWhileLoop2(IrWhileLoop irWhileLoop, IrDeclarationParent irDeclarationParent) {
            return IrElementTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, irDeclarationParent);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
        public IrStatement visitClass2(IrClass declaration, IrDeclarationParent data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitClass(this, declaration, declaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
        public IrExpression visitConstructorCall2(IrConstructorCall expression, IrDeclarationParent data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            expression.transformChildren(this, data);
            if (!ThrowableLowering.this.throwableConstructors.contains(expression.getSymbol())) {
                return expression;
            }
            ThrowableArguments throwableArgumentsExtractThrowableArguments = ThrowableLowering.this.extractThrowableArguments(expression);
            IrExpression message = throwableArgumentsExtractThrowableArguments.getMessage();
            IrExpression cause = throwableArgumentsExtractThrowableArguments.getCause();
            IrCallImpl irCallImpl = new IrCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), ThrowableLowering.this.newThrowableFunction, 0, 2, null, null, 192, null);
            irCallImpl.putValueArgument(0, message);
            irCallImpl.putValueArgument(1, cause);
            return irCallImpl;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
        public IrExpression visitDelegatingConstructorCall2(IrDelegatingConstructorCall expression, IrDeclarationParent data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            expression.transformChildren(this, data);
            if (!ThrowableLowering.this.throwableConstructors.contains(expression.getSymbol())) {
                return expression;
            }
            ThrowableArguments throwableArgumentsExtractThrowableArguments = ThrowableLowering.this.extractThrowableArguments(expression);
            IrExpression message = throwableArgumentsExtractThrowableArguments.getMessage();
            IrExpression cause = throwableArgumentsExtractThrowableArguments.getCause();
            int startOffset = expression.getStartOffset();
            int endOffset = expression.getEndOffset();
            IrValueParameter thisReceiver = ((IrClass) data).getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver);
            IrGetValueImpl irGetValueImpl = new IrGetValueImpl(startOffset, endOffset, thisReceiver.getSymbol(), null, 8, null);
            IrCallImpl irCallImpl = new IrCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), ThrowableLowering.this.getExtendThrowableFunction(), 0, 3, null, null, 192, null);
            irCallImpl.putValueArgument(0, irGetValueImpl);
            irCallImpl.putValueArgument(1, message);
            irCallImpl.putValueArgument(2, cause);
            if (!ThrowableLowering.this.getContext().getEs6mode()) {
                return irCallImpl;
            }
            return JsIrBuilder.INSTANCE.buildComposite(ThrowableLowering.this.getContext().getIrBuiltIns().getUnitType(), CollectionsKt.listOf((Object[]) new IrFunctionAccessExpression[]{new IrDelegatingConstructorCallImpl(expression.getStartOffset(), expression.getEndOffset(), ThrowableLowering.this.getContext().getIrBuiltIns().getAnyType(), this.anyConstructor, 0, 0), irCallImpl}));
        }
    }
}
