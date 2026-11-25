package org.jetbrains.kotlin.ir.interpreter.transformer;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.constant.ErrorValue;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
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
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreter;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterConfiguration;
import org.jetbrains.kotlin.ir.interpreter.IrTreeBuildUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.checker.EvaluationMode;
import org.jetbrains.kotlin.ir.interpreter.checker.IrInterpreterChecker;
import org.jetbrains.kotlin.ir.interpreter.checker.IrInterpreterCheckerData;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrConstTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b \u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001#B\u0081\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0010\u0012\u001e\u0010\u0014\u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0010\u0012\u0006\u0010\u0015\u001a\u00020\u0016¢\u0006\u0002\u0010\u0017J\u0016\u0010\u001a\u001a\u00020\u0016*\u00020\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u001dH\u0004J\u0014\u0010\u001e\u001a\u00020\u001b*\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\u0016H\u0004J\u0014\u0010 \u001a\u00020\u001b*\u00020\u001b2\u0006\u0010!\u001a\u00020\u001bH\u0002J\u0014\u0010\"\u001a\u00020\u001b*\u00020\u001b2\u0006\u0010!\u001a\u00020\u001bH\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u0014\u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstTransformer$Data;", "interpreter", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "mode", "Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;", "checker", "Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterChecker;", "evaluatedConstTracker", "Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "inlineConstTracker", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "onWarning", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "", "onError", "suppressExceptions", "", "(Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;Lorg/jetbrains/kotlin/ir/declarations/IrFile;Lorg/jetbrains/kotlin/ir/interpreter/checker/EvaluationMode;Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterChecker;Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;Lkotlin/jvm/functions/Function3;Lkotlin/jvm/functions/Function3;Z)V", "getInterpreter", "()Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;", "canBeInterpreted", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "configuration", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterConfiguration;", "interpret", "failAsError", "reportIfError", "original", "warningIfError", "Data", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrConstTransformer implements IrElementTransformer<Data> {
    private final IrInterpreterChecker checker;
    private final EvaluatedConstTracker evaluatedConstTracker;
    private final InlineConstTracker inlineConstTracker;
    private final IrInterpreter interpreter;
    private final IrFile irFile;
    private final EvaluationMode mode;
    private final Function3<IrFile, IrElement, IrErrorExpression, Unit> onError;
    private final Function3<IrFile, IrElement, IrErrorExpression, Unit> onWarning;
    private final boolean suppressExceptions;

    /* JADX INFO: compiled from: IrConstTransformer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EvaluationMode.values().length];
            try {
                iArr[EvaluationMode.ONLY_INTRINSIC_CONST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public IrConstTransformer(IrInterpreter interpreter, IrFile irFile, EvaluationMode mode, IrInterpreterChecker checker, EvaluatedConstTracker evaluatedConstTracker, InlineConstTracker inlineConstTracker, Function3<? super IrFile, ? super IrElement, ? super IrErrorExpression, Unit> onWarning, Function3<? super IrFile, ? super IrElement, ? super IrErrorExpression, Unit> onError, boolean z) {
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(checker, "checker");
        Intrinsics.checkNotNullParameter(onWarning, "onWarning");
        Intrinsics.checkNotNullParameter(onError, "onError");
        this.interpreter = interpreter;
        this.irFile = irFile;
        this.mode = mode;
        this.checker = checker;
        this.evaluatedConstTracker = evaluatedConstTracker;
        this.inlineConstTracker = inlineConstTracker;
        this.onWarning = onWarning;
        this.onError = onError;
        this.suppressExceptions = z;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
    public IrStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Data data) {
        return IrElementTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBlock2(IrBlock irBlock, Data data) {
        return IrElementTransformer.DefaultImpls.visitBlock(this, irBlock, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitBlockBody2(IrBlockBody irBlockBody, Data data) {
        return IrElementTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitBody2(IrBody irBody, Data data) {
        return IrElementTransformer.DefaultImpls.visitBody(this, irBody, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
    public IrBranch visitBranch2(IrBranch irBranch, Data data) {
        return IrElementTransformer.DefaultImpls.visitBranch(this, irBranch, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBreak2(IrBreak irBreak, Data data) {
        return IrElementTransformer.DefaultImpls.visitBreak(this, irBreak, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBreakContinue2(IrBreakContinue irBreakContinue, Data data) {
        return IrElementTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitCall2(IrCall irCall, Data data) {
        return IrElementTransformer.DefaultImpls.visitCall(this, irCall, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitCallableReference(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (Data) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitCallableReference2(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (Data) obj);
    }

    public IrElement visitCallableReference(IrCallableReference<?> irCallableReference, Data data) {
        return IrElementTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
    public IrCatch visitCatch2(IrCatch irCatch, Data data) {
        return IrElementTransformer.DefaultImpls.visitCatch(this, irCatch, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
    public IrStatement visitClass2(IrClass irClass, Data data) {
        return IrElementTransformer.DefaultImpls.visitClass(this, irClass, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitClassReference2(IrClassReference irClassReference, Data data) {
        return IrElementTransformer.DefaultImpls.visitClassReference(this, irClassReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
    public IrExpression visitComposite2(IrComposite irComposite, Data data) {
        return IrElementTransformer.DefaultImpls.visitComposite(this, irComposite, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitConst(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (Data) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitConst2(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (Data) obj);
    }

    public IrExpression visitConst(IrConst<?> irConst, Data data) {
        return IrElementTransformer.DefaultImpls.visitConst(this, irConst, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantArray2(IrConstantArray irConstantArray, Data data) {
        return IrElementTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantObject2(IrConstantObject irConstantObject, Data data) {
        return IrElementTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Data data) {
        return IrElementTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantValue2(IrConstantValue irConstantValue, Data data) {
        return IrElementTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
    public IrStatement visitConstructor2(IrConstructor irConstructor, Data data) {
        return IrElementTransformer.DefaultImpls.visitConstructor(this, irConstructor, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitConstructorCall2(IrConstructorCall irConstructorCall, Data data) {
        return IrElementTransformer.DefaultImpls.visitConstructorCall(this, irConstructorCall, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitContainerExpression2(IrContainerExpression irContainerExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitContainerExpression(this, irContainerExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitContinue2(IrContinue irContinue, Data data) {
        return IrElementTransformer.DefaultImpls.visitContinue(this, irContinue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
    public IrStatement visitDeclaration2(IrDeclarationBase irDeclarationBase, Data data) {
        return IrElementTransformer.DefaultImpls.visitDeclaration(this, irDeclarationBase, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Data data) {
        return IrElementTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Data data) {
        return IrElementTransformer.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Data data) {
        return IrElementTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
    public IrElement visitElement2(IrElement irElement, Data data) {
        return IrElementTransformer.DefaultImpls.visitElement(this, irElement, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
    public IrElseBranch visitElseBranch2(IrElseBranch irElseBranch, Data data) {
        return IrElementTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Data data) {
        return IrElementTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
    public IrStatement visitEnumEntry2(IrEnumEntry irEnumEntry, Data data) {
        return IrElementTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
    public IrStatement visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Data data) {
        return IrElementTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitErrorExpression2(IrErrorExpression irErrorExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitExpression2(IrExpression irExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitExpression(this, irExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitExpressionBody2(IrExpressionBody irExpressionBody, Data data) {
        return IrElementTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
    public IrExternalPackageFragment visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Data data) {
        return IrElementTransformer.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
    public IrStatement visitField2(IrField irField, Data data) {
        return IrElementTransformer.DefaultImpls.visitField(this, irField, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
    public IrExpression visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
    public IrFile visitFile2(IrFile irFile, Data data) {
        return IrElementTransformer.DefaultImpls.visitFile(this, irFile, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
    public IrStatement visitFunction2(IrFunction irFunction, Data data) {
        return IrElementTransformer.DefaultImpls.visitFunction(this, irFunction, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionReference2(IrFunctionReference irFunctionReference, Data data) {
        return IrElementTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetClass2(IrGetClass irGetClass, Data data) {
        return IrElementTransformer.DefaultImpls.visitGetClass(this, irGetClass, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Data data) {
        return IrElementTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetField2(IrGetField irGetField, Data data) {
        return IrElementTransformer.DefaultImpls.visitGetField(this, irGetField, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Data data) {
        return IrElementTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetValue2(IrGetValue irGetValue, Data data) {
        return IrElementTransformer.DefaultImpls.visitGetValue(this, irGetValue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
    public IrExpression visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Data data) {
        return IrElementTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
    public IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Data data) {
        return IrElementTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Data data) {
        return IrElementTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitLoop2(IrLoop irLoop, Data data) {
        return IrElementTransformer.DefaultImpls.visitLoop(this, irLoop, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Data) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Data) obj);
    }

    public IrElement visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
    public IrModuleFragment visitModuleFragment2(IrModuleFragment irModuleFragment, Data data) {
        return IrElementTransformer.DefaultImpls.visitModuleFragment(this, irModuleFragment, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
    public IrElement visitPackageFragment2(IrPackageFragment irPackageFragment, Data data) {
        return IrElementTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
    public IrStatement visitProperty2(IrProperty irProperty, Data data) {
        return IrElementTransformer.DefaultImpls.visitProperty(this, irProperty, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitPropertyReference2(IrPropertyReference irPropertyReference, Data data) {
        return IrElementTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Data data) {
        return IrElementTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
    public IrExpression visitReturn2(IrReturn irReturn, Data data) {
        return IrElementTransformer.DefaultImpls.visitReturn(this, irReturn, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
    public IrStatement visitScript2(IrScript irScript, Data data) {
        return IrElementTransformer.DefaultImpls.visitScript(this, irScript, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSetField2(IrSetField irSetField, Data data) {
        return IrElementTransformer.DefaultImpls.visitSetField(this, irSetField, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSetValue2(IrSetValue irSetValue, Data data) {
        return IrElementTransformer.DefaultImpls.visitSetValue(this, irSetValue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
    public IrStatement visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Data data) {
        return IrElementTransformer.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Data data) {
        return IrElementTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
    public IrSpreadElement visitSpreadElement2(IrSpreadElement irSpreadElement, Data data) {
        return IrElementTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
    public IrExpression visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Data data) {
        return IrElementTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Data data) {
        return IrElementTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Data data) {
        return IrElementTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
    public IrExpression visitThrow2(IrThrow irThrow, Data data) {
        return IrElementTransformer.DefaultImpls.visitThrow(this, irThrow, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
    public IrExpression visitTry2(IrTry irTry, Data data) {
        return IrElementTransformer.DefaultImpls.visitTry(this, irTry, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
    public IrStatement visitTypeAlias2(IrTypeAlias irTypeAlias, Data data) {
        return IrElementTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
    public IrExpression visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Data data) {
        return IrElementTransformer.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
    public IrStatement visitTypeParameter2(IrTypeParameter irTypeParameter, Data data) {
        return IrElementTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
    public IrExpression visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Data data) {
        return IrElementTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
    public IrStatement visitValueParameter2(IrValueParameter irValueParameter, Data data) {
        return IrElementTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
    public IrExpression visitVararg2(IrVararg irVararg, Data data) {
        return IrElementTransformer.DefaultImpls.visitVararg(this, irVararg, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
    public IrStatement visitVariable2(IrVariable irVariable, Data data) {
        return IrElementTransformer.DefaultImpls.visitVariable(this, irVariable, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
    public IrExpression visitWhen2(IrWhen irWhen, Data data) {
        return IrElementTransformer.DefaultImpls.visitWhen(this, irWhen, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitWhileLoop2(IrWhileLoop irWhileLoop, Data data) {
        return IrElementTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, data);
    }

    protected final IrInterpreter getInterpreter() {
        return this.interpreter;
    }

    /* JADX INFO: compiled from: IrConstTransformer.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000b\u001a\u00020\fHÖ\u0001J\t\u0010\r\u001a\u00020\u000eHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/transformer/IrConstTransformer$Data;", "", "inAnnotation", "", "(Z)V", "getInAnnotation", "()Z", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Data {
        private final boolean inAnnotation;

        public Data() {
            this(false, 1, null);
        }

        public static /* synthetic */ Data copy$default(Data data, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                z = data.inAnnotation;
            }
            return data.copy(z);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final boolean getInAnnotation() {
            return this.inAnnotation;
        }

        public final Data copy(boolean inAnnotation) {
            return new Data(inAnnotation);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Data) && this.inAnnotation == ((Data) other).inAnnotation;
        }

        public int hashCode() {
            return Boolean.hashCode(this.inAnnotation);
        }

        public String toString() {
            return "Data(inAnnotation=" + this.inAnnotation + ')';
        }

        public Data(boolean z) {
            this.inAnnotation = z;
        }

        public /* synthetic */ Data(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? false : z);
        }

        public final boolean getInAnnotation() {
            return this.inAnnotation;
        }
    }

    private final IrExpression warningIfError(IrExpression irExpression, IrExpression irExpression2) {
        if (!(irExpression instanceof IrErrorExpression)) {
            return irExpression;
        }
        this.onWarning.invoke(this.irFile, irExpression2, irExpression);
        return irExpression2;
    }

    private final IrExpression reportIfError(IrExpression irExpression, IrExpression irExpression2) {
        if (!(irExpression instanceof IrErrorExpression)) {
            return irExpression;
        }
        this.onError.invoke(this.irFile, irExpression2, irExpression);
        return WhenMappings.$EnumSwitchMapping$0[this.mode.ordinal()] == 1 ? IrConstImpl.INSTANCE.constNull(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType()) : irExpression2;
    }

    public static /* synthetic */ boolean canBeInterpreted$default(IrConstTransformer irConstTransformer, IrExpression irExpression, IrInterpreterConfiguration irInterpreterConfiguration, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: canBeInterpreted");
        }
        if ((i & 1) != 0) {
            irInterpreterConfiguration = irConstTransformer.interpreter.getEnvironment().getConfiguration();
        }
        return irConstTransformer.canBeInterpreted(irExpression, irInterpreterConfiguration);
    }

    protected final boolean canBeInterpreted(IrExpression irExpression, IrInterpreterConfiguration configuration) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        try {
            return ((Boolean) irExpression.accept(this.checker, new IrInterpreterCheckerData(this.irFile, this.mode, this.interpreter.getIrBuiltIns(), configuration))).booleanValue();
        } catch (Throwable th) {
            if (this.suppressExceptions) {
                return false;
            }
            throw new AssertionError("Error occurred while optimizing an expression:\n" + DumpIrTreeKt.dump$default(irExpression, null, 1, null), th);
        }
    }

    protected final IrExpression interpret(IrExpression irExpression, boolean z) {
        IrProperty property;
        InlineConstTracker inlineConstTracker;
        ErrorValue constantValue;
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        IrField backingField = null;
        try {
            IrExpression irExpressionInterpret = this.interpreter.interpret(irExpression, this.irFile);
            EvaluatedConstTracker evaluatedConstTracker = this.evaluatedConstTracker;
            if (evaluatedConstTracker != null) {
                int startOffset = irExpressionInterpret.getStartOffset();
                int endOffset = irExpressionInterpret.getEndOffset();
                String nameWithPackage = IrDeclarationsKt.getNameWithPackage(this.irFile);
                if (irExpressionInterpret instanceof IrErrorExpression) {
                    constantValue = ErrorValue.INSTANCE.create(((IrErrorExpression) irExpressionInterpret).getDescription());
                } else {
                    Intrinsics.checkNotNull(irExpressionInterpret, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<*>");
                    constantValue = IrTreeBuildUtilsKt.toConstantValue((IrConst) irExpressionInterpret);
                }
                evaluatedConstTracker.save(startOffset, endOffset, nameWithPackage, constantValue);
            }
            if (irExpressionInterpret instanceof IrConst) {
                if (irExpression instanceof IrGetField) {
                    backingField = ((IrGetField) irExpression).getSymbol().getOwner();
                } else if ((irExpression instanceof IrCall) && (property = UtilsKt.getProperty(((IrCall) irExpression).getSymbol().getOwner())) != null) {
                    backingField = property.getBackingField();
                }
                if (backingField != null && (inlineConstTracker = this.inlineConstTracker) != null) {
                    IrConstTransformerKt.reportOnIr(inlineConstTracker, this.irFile, backingField, (IrConst) irExpressionInterpret);
                }
            }
            return z ? reportIfError(irExpressionInterpret, irExpression) : warningIfError(irExpressionInterpret, irExpression);
        } catch (Throwable th) {
            if (this.suppressExceptions) {
                return irExpression;
            }
            throw new AssertionError("Error occurred while optimizing an expression:\n" + DumpIrTreeKt.dump$default(irExpression, null, 1, null), th);
        }
    }
}
