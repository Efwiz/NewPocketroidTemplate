package org.jetbrains.kotlin.backend.common.lower.loops;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.ArrayIndicesHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.CharSequenceIndicesHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.CollectionIndicesHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.DownToHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.RangeToHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.RangeUntilHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.StepHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.handlers.UntilHandler;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
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
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: HeaderInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b \u0018\u00002\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B%\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001b\u001a\u00020\u00032\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016J\u001c\u0010 \u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001b\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\f\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e0\rX¤\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R&\u0010\u0012\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e0\rX¤\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0011R&\u0010\u0015\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00160\u000e0\rX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoBuilder;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "scopeOwnerSymbol", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "allowUnsignedBounds", "", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lkotlin/jvm/functions/Function0;Z)V", "callHandlers", "", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoHandler;", "", "getCallHandlers", "()Ljava/util/List;", "expressionHandlers", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getExpressionHandlers", "progressionHandlers", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;", "getProgressionHandlers", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "visitCall", "expression", "data", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitExpression", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class HeaderInfoBuilder implements IrElementVisitor<HeaderInfo, IrCall> {
    private final boolean allowUnsignedBounds;
    private final List<HeaderInfoHandler<IrCall, ProgressionType>> progressionHandlers;
    private final Function0<IrSymbol> scopeOwnerSymbol;
    private final Symbols symbols;

    protected abstract List<HeaderInfoHandler> getCallHandlers();

    protected abstract List<HeaderInfoHandler> getExpressionHandlers();

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
    public HeaderInfo visitElement2(IrElement element, IrCall data) {
        Intrinsics.checkNotNullParameter(element, "element");
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public HeaderInfoBuilder(CommonBackendContext context, Function0<? extends IrSymbol> scopeOwnerSymbol, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scopeOwnerSymbol, "scopeOwnerSymbol");
        this.scopeOwnerSymbol = scopeOwnerSymbol;
        this.allowUnsignedBounds = z;
        this.symbols = context.getIr().getSymbols();
        this.progressionHandlers = CollectionsKt.listOf((Object[]) new HeaderInfoHandler[]{new CollectionIndicesHandler(context), new ArrayIndicesHandler(context), new CharSequenceIndicesHandler(context), new UntilHandler(context), new RangeUntilHandler(context), new DownToHandler(context), new RangeToHandler(context), new StepHandler(context, this)});
    }

    public /* synthetic */ HeaderInfoBuilder(CommonBackendContext commonBackendContext, Function0 function0, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(commonBackendContext, function0, (i & 4) != 0 ? false : z);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
    public HeaderInfo visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
    public HeaderInfo visitBlock2(IrBlock irBlock, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
    public HeaderInfo visitBlockBody2(IrBlockBody irBlockBody, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
    public HeaderInfo visitBody2(IrBody irBody, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitBody(this, irBody, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
    public HeaderInfo visitBranch2(IrBranch irBranch, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
    public HeaderInfo visitBreak2(IrBreak irBreak, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
    public HeaderInfo visitBreakContinue2(IrBreakContinue irBreakContinue, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ HeaderInfo visitCallableReference(IrCallableReference irCallableReference, IrCall irCall) {
        return visitCallableReference2((IrCallableReference<?>) irCallableReference, irCall);
    }

    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public HeaderInfo visitCallableReference2(IrCallableReference<?> irCallableReference, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
    public HeaderInfo visitCatch2(IrCatch irCatch, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
    public HeaderInfo visitClass2(IrClass irClass, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitClass(this, irClass, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
    public HeaderInfo visitClassReference2(IrClassReference irClassReference, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
    public HeaderInfo visitComposite2(IrComposite irComposite, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ HeaderInfo visitConst(IrConst irConst, IrCall irCall) {
        return visitConst2((IrConst<?>) irConst, irCall);
    }

    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public HeaderInfo visitConst2(IrConst<?> irConst, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitConst(this, irConst, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
    public HeaderInfo visitConstantArray2(IrConstantArray irConstantArray, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
    public HeaderInfo visitConstantObject2(IrConstantObject irConstantObject, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
    public HeaderInfo visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
    public HeaderInfo visitConstantValue2(IrConstantValue irConstantValue, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
    public HeaderInfo visitConstructor2(IrConstructor irConstructor, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
    public HeaderInfo visitConstructorCall2(IrConstructorCall irConstructorCall, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
    public HeaderInfo visitContainerExpression2(IrContainerExpression irContainerExpression, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
    public HeaderInfo visitContinue2(IrContinue irContinue, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
    public HeaderInfo visitDeclaration2(IrDeclarationBase irDeclarationBase, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
    public HeaderInfo visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
    public HeaderInfo visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
    public HeaderInfo visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
    public HeaderInfo visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
    public HeaderInfo visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
    public HeaderInfo visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
    public HeaderInfo visitElseBranch2(IrElseBranch irElseBranch, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
    public HeaderInfo visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
    public HeaderInfo visitEnumEntry2(IrEnumEntry irEnumEntry, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
    public HeaderInfo visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
    public HeaderInfo visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
    public HeaderInfo visitErrorExpression2(IrErrorExpression irErrorExpression, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
    public HeaderInfo visitExpressionBody2(IrExpressionBody irExpressionBody, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
    public HeaderInfo visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
    public HeaderInfo visitField2(IrField irField, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitField(this, irField, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
    public HeaderInfo visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
    public HeaderInfo visitFile2(IrFile irFile, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitFile(this, irFile, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
    public HeaderInfo visitFunction2(IrFunction irFunction, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
    public HeaderInfo visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
    public HeaderInfo visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
    public HeaderInfo visitFunctionReference2(IrFunctionReference irFunctionReference, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
    public HeaderInfo visitGetClass2(IrGetClass irGetClass, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
    public HeaderInfo visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
    public HeaderInfo visitGetField2(IrGetField irGetField, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
    public HeaderInfo visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
    public HeaderInfo visitGetValue2(IrGetValue irGetValue, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
    public HeaderInfo visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
    public HeaderInfo visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
    public HeaderInfo visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
    public HeaderInfo visitLoop2(IrLoop irLoop, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ HeaderInfo visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, IrCall irCall) {
        return visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, irCall);
    }

    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public HeaderInfo visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
    public HeaderInfo visitModuleFragment2(IrModuleFragment irModuleFragment, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
    public HeaderInfo visitPackageFragment2(IrPackageFragment irPackageFragment, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
    public HeaderInfo visitProperty2(IrProperty irProperty, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
    public HeaderInfo visitPropertyReference2(IrPropertyReference irPropertyReference, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
    public HeaderInfo visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
    public HeaderInfo visitReturn2(IrReturn irReturn, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
    public HeaderInfo visitScript2(IrScript irScript, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitScript(this, irScript, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
    public HeaderInfo visitSetField2(IrSetField irSetField, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
    public HeaderInfo visitSetValue2(IrSetValue irSetValue, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
    public HeaderInfo visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
    public HeaderInfo visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
    public HeaderInfo visitSpreadElement2(IrSpreadElement irSpreadElement, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
    public HeaderInfo visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
    public HeaderInfo visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
    public HeaderInfo visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
    public HeaderInfo visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
    public HeaderInfo visitThrow2(IrThrow irThrow, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
    public HeaderInfo visitTry2(IrTry irTry, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitTry(this, irTry, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
    public HeaderInfo visitTypeAlias2(IrTypeAlias irTypeAlias, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
    public HeaderInfo visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
    public HeaderInfo visitTypeParameter2(IrTypeParameter irTypeParameter, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
    public HeaderInfo visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
    public HeaderInfo visitValueParameter2(IrValueParameter irValueParameter, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
    public HeaderInfo visitVararg2(IrVararg irVararg, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
    public HeaderInfo visitVariable2(IrVariable irVariable, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitVariable(this, irVariable, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
    public HeaderInfo visitWhen2(IrWhen irWhen, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, irCall);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
    public HeaderInfo visitWhileLoop2(IrWhileLoop irWhileLoop, IrCall irCall) {
        return (HeaderInfo) IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, irCall);
    }

    protected List<HeaderInfoHandler<IrCall, ProgressionType>> getProgressionHandlers() {
        return this.progressionHandlers;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
    public HeaderInfo visitCall2(IrCall expression, IrCall data) {
        HeaderInfo headerInfo;
        HeaderInfo headerInfoHandle;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Iterator<T> it = getCallHandlers().iterator();
        do {
            headerInfo = null;
            if (!it.hasNext()) {
                headerInfoHandle = null;
                break;
            }
            headerInfoHandle = ((HeaderInfoHandler) it.next()).handle(expression, data, null, this.scopeOwnerSymbol.invoke());
        } while (headerInfoHandle == null);
        if (headerInfoHandle != null) {
            return headerInfoHandle;
        }
        ProgressionType progressionTypeFromIrType = ProgressionType.INSTANCE.fromIrType(expression.getType(), this.symbols, this.allowUnsignedBounds);
        if (progressionTypeFromIrType != null) {
            Iterator<T> it2 = getProgressionHandlers().iterator();
            while (it2.hasNext()) {
                HeaderInfo headerInfoHandle2 = ((HeaderInfoHandler) it2.next()).handle(expression, data, progressionTypeFromIrType, this.scopeOwnerSymbol.invoke());
                if (headerInfoHandle2 != null) {
                    headerInfo = headerInfoHandle2;
                    break;
                }
            }
        }
        return headerInfo == null ? (HeaderInfo) IrElementVisitor.DefaultImpls.visitCall(this, expression, data) : headerInfo;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
    public HeaderInfo visitExpression2(IrExpression expression, IrCall data) {
        HeaderInfo headerInfoHandle;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Iterator<T> it = getExpressionHandlers().iterator();
        do {
            headerInfoHandle = null;
            if (!it.hasNext()) {
                break;
            }
            headerInfoHandle = ((HeaderInfoHandler) it.next()).handle(expression, data, null, this.scopeOwnerSymbol.invoke());
        } while (headerInfoHandle == null);
        return headerInfoHandle == null ? (HeaderInfo) IrElementVisitor.DefaultImpls.visitExpression(this, expression, data) : headerInfoHandle;
    }
}
