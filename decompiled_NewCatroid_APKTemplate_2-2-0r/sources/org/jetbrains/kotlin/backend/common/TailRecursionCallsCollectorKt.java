package org.jetbrains.kotlin.backend.common;

import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
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
import org.jetbrains.kotlin.ir.expressions.IrStatementContainer;
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
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: TailRecursionCallsCollector.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¨\u0006\b"}, d2 = {"collectTailRecursionCalls", "Lorg/jetbrains/kotlin/backend/common/TailCalls;", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "followFunctionReference", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TailRecursionCallsCollectorKt {
    public static final TailCalls collectTailRecursionCalls(final IrFunction irFunction, final Function1<? super IrFunctionReference, Boolean> followFunctionReference) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(followFunctionReference, "followFunctionReference");
        IrSimpleFunction irSimpleFunction = irFunction instanceof IrSimpleFunction ? (IrSimpleFunction) irFunction : null;
        if (irSimpleFunction == null || !irSimpleFunction.getIsTailrec()) {
            return new TailCalls(SetsKt.emptySet(), false);
        }
        final boolean zIsUnit = IrTypePredicatesKt.isUnit(irFunction.getReturnType());
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        IrElementVisitor<Unit, VisitorState> irElementVisitor = new IrElementVisitor<Unit, VisitorState>() { // from class: org.jetbrains.kotlin.backend.common.TailRecursionCallsCollectorKt$collectTailRecursionCalls$visitor$1
            /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
            public void visitClass2(IrClass declaration, TailRecursionCallsCollectorKt.VisitorState data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                Intrinsics.checkNotNullParameter(data, "data");
            }

            /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
            public void visitFunction2(IrFunction declaration, TailRecursionCallsCollectorKt.VisitorState data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                Intrinsics.checkNotNullParameter(data, "data");
            }

            /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
            public void visitTry2(IrTry aTry, TailRecursionCallsCollectorKt.VisitorState data) {
                Intrinsics.checkNotNullParameter(aTry, "aTry");
                Intrinsics.checkNotNullParameter(data, "data");
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitAnonymousInitializer */
            public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitAnonymousInitializer2(irAnonymousInitializer, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
            public void visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlock */
            public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitBlock2(irBlock, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
            public void visitBlock2(IrBlock irBlock, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlockBody */
            public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitBlockBody2(irBlockBody, visitorState);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBody */
            public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitBody2(irBody, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
            public void visitBody2(IrBody irBody, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitBody(this, irBody, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBranch */
            public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitBranch2(irBranch, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
            public void visitBranch2(IrBranch irBranch, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreak */
            public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitBreak2(irBreak, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
            public void visitBreak2(IrBreak irBreak, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreakContinue */
            public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitBreakContinue2(irBreakContinue, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
            public void visitBreakContinue2(IrBreakContinue irBreakContinue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall */
            public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitCall2(irCall, visitorState);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitCallableReference2((IrCallableReference<?>) irCallableReference, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
            public void visitCallableReference2(IrCallableReference<?> irCallableReference, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCatch */
            public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitCatch2(irCatch, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
            public void visitCatch2(IrCatch irCatch, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClass */
            public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitClass2(irClass, visitorState);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClassReference */
            public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitClassReference2(irClassReference, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
            public void visitClassReference2(IrClassReference irClassReference, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitComposite */
            public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitComposite2(irComposite, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
            public void visitComposite2(IrComposite irComposite, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitConst2((IrConst<?>) irConst, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
            public void visitConst2(IrConst<?> irConst, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitConst(this, irConst, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantArray */
            public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitConstantArray2(irConstantArray, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
            public void visitConstantArray2(IrConstantArray irConstantArray, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantObject */
            public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitConstantObject2(irConstantObject, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
            public void visitConstantObject2(IrConstantObject irConstantObject, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantPrimitive */
            public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitConstantPrimitive2(irConstantPrimitive, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
            public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantValue */
            public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitConstantValue2(irConstantValue, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
            public void visitConstantValue2(IrConstantValue irConstantValue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructor */
            public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitConstructor2(irConstructor, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
            public void visitConstructor2(IrConstructor irConstructor, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructorCall */
            public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitConstructorCall2(irConstructorCall, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
            public void visitConstructorCall2(IrConstructorCall irConstructorCall, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContainerExpression */
            public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitContainerExpression2(irContainerExpression, visitorState);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContinue */
            public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitContinue2(irContinue, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
            public void visitContinue2(IrContinue irContinue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclaration */
            public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitDeclaration2(irDeclarationBase, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
            public void visitDeclaration2(IrDeclarationBase irDeclarationBase, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclarationReference */
            public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitDeclarationReference2(irDeclarationReference, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
            public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDelegatingConstructorCall */
            public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitDelegatingConstructorCall2(irDelegatingConstructorCall, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
            public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDoWhileLoop */
            public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitDoWhileLoop2(irDoWhileLoop, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
            public void visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicExpression */
            public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitDynamicExpression2(irDynamicExpression, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
            public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicMemberExpression */
            public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitDynamicMemberExpression2(irDynamicMemberExpression, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
            public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicOperatorExpression */
            public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitDynamicOperatorExpression2(irDynamicOperatorExpression, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
            public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElement */
            public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitElement2(irElement, visitorState);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElseBranch */
            public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitElseBranch2(irElseBranch, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
            public void visitElseBranch2(IrElseBranch irElseBranch, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumConstructorCall */
            public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitEnumConstructorCall2(irEnumConstructorCall, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
            public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumEntry */
            public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitEnumEntry2(irEnumEntry, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
            public void visitEnumEntry2(IrEnumEntry irEnumEntry, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorCallExpression */
            public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitErrorCallExpression2(irErrorCallExpression, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
            public void visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorDeclaration */
            public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitErrorDeclaration2(irErrorDeclaration, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
            public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorExpression */
            public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitErrorExpression2(irErrorExpression, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
            public void visitErrorExpression2(IrErrorExpression irErrorExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpression */
            public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitExpression2(irExpression, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
            public void visitExpression2(IrExpression irExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpressionBody */
            public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitExpressionBody2(irExpressionBody, visitorState);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExternalPackageFragment */
            public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitExternalPackageFragment2(irExternalPackageFragment, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
            public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitField */
            public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitField2(irField, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
            public void visitField2(IrField irField, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitField(this, irField, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFieldAccess */
            public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitFieldAccess2(irFieldAccessExpression, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
            public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFile */
            public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitFile2(irFile, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
            public void visitFile2(IrFile irFile, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitFile(this, irFile, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunction */
            public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction2, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitFunction2(irFunction2, visitorState);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionAccess */
            public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitFunctionAccess2(irFunctionAccessExpression, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
            public void visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionExpression */
            public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitFunctionExpression2(irFunctionExpression, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
            public void visitFunctionExpression2(IrFunctionExpression irFunctionExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionReference */
            public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitFunctionReference2(irFunctionReference, visitorState);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetClass */
            public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitGetClass2(irGetClass, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
            public void visitGetClass2(IrGetClass irGetClass, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetEnumValue */
            public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitGetEnumValue2(irGetEnumValue, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
            public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetField */
            public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitGetField2(irGetField, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
            public void visitGetField2(IrGetField irGetField, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetObjectValue */
            public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitGetObjectValue2(irGetObjectValue, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
            public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetValue */
            public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitGetValue2(irGetValue, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
            public void visitGetValue2(IrGetValue irGetValue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitInstanceInitializerCall */
            public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitInstanceInitializerCall2(irInstanceInitializerCall, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
            public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedProperty */
            public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitLocalDelegatedProperty2(irLocalDelegatedProperty, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
            public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
            public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
            public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLoop */
            public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitLoop2(irLoop, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
            public void visitLoop2(IrLoop irLoop, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
            public void visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitModuleFragment */
            public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitModuleFragment2(irModuleFragment, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
            public void visitModuleFragment2(IrModuleFragment irModuleFragment, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPackageFragment */
            public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitPackageFragment2(irPackageFragment, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
            public void visitPackageFragment2(IrPackageFragment irPackageFragment, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitProperty */
            public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitProperty2(irProperty, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
            public void visitProperty2(IrProperty irProperty, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPropertyReference */
            public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitPropertyReference2(irPropertyReference, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
            public void visitPropertyReference2(IrPropertyReference irPropertyReference, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitRawFunctionReference */
            public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitRawFunctionReference2(irRawFunctionReference, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
            public void visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitReturn */
            public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitReturn2(irReturn, visitorState);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitScript */
            public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitScript2(irScript, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
            public void visitScript2(IrScript irScript, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitScript(this, irScript, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetField */
            public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitSetField2(irSetField, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
            public void visitSetField2(IrSetField irSetField, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetValue */
            public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitSetValue2(irSetValue, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
            public void visitSetValue2(IrSetValue irSetValue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSimpleFunction */
            public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction2, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitSimpleFunction2(irSimpleFunction2, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
            public void visitSimpleFunction2(IrSimpleFunction irSimpleFunction2, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction2, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSingletonReference */
            public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitSingletonReference2(irGetSingletonValue, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
            public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSpreadElement */
            public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitSpreadElement2(irSpreadElement, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
            public void visitSpreadElement2(IrSpreadElement irSpreadElement, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitStringConcatenation */
            public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitStringConcatenation2(irStringConcatenation, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
            public void visitStringConcatenation2(IrStringConcatenation irStringConcatenation, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspendableExpression */
            public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitSuspendableExpression2(irSuspendableExpression, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
            public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspensionPoint */
            public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitSuspensionPoint2(irSuspensionPoint, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
            public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSyntheticBody */
            public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitSyntheticBody2(irSyntheticBody, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
            public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitThrow */
            public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitThrow2(irThrow, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
            public void visitThrow2(IrThrow irThrow, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTry */
            public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitTry2(irTry, visitorState);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeAlias */
            public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitTypeAlias2(irTypeAlias, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
            public void visitTypeAlias2(IrTypeAlias irTypeAlias, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeOperator */
            public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitTypeOperator2(irTypeOperatorCall, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
            public void visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeParameter */
            public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitTypeParameter2(irTypeParameter, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
            public void visitTypeParameter2(IrTypeParameter irTypeParameter, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueAccess */
            public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitValueAccess2(irValueAccessExpression, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
            public void visitValueAccess2(IrValueAccessExpression irValueAccessExpression, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueParameter */
            public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitValueParameter2(irValueParameter, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
            public void visitValueParameter2(IrValueParameter irValueParameter, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVararg */
            public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitVararg2(irVararg, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
            public void visitVararg2(IrVararg irVararg, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVariable */
            public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitVariable2(irVariable, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
            public void visitVariable2(IrVariable irVariable, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitVariable(this, irVariable, visitorState);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhen */
            public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitWhen2(irWhen, visitorState);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhileLoop */
            public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                visitWhileLoop2(irWhileLoop, visitorState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
            public void visitWhileLoop2(IrWhileLoop irWhileLoop, TailRecursionCallsCollectorKt.VisitorState visitorState) {
                IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, visitorState);
            }

            /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
            public void visitElement2(IrElement element, TailRecursionCallsCollectorKt.VisitorState data) {
                Intrinsics.checkNotNullParameter(element, "element");
                Intrinsics.checkNotNullParameter(data, "data");
                element.acceptChildren(this, new TailRecursionCallsCollectorKt.VisitorState(false, data.getInOtherFunction()));
            }

            /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
            public void visitReturn2(IrReturn expression, TailRecursionCallsCollectorKt.VisitorState data) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(data, "data");
                expression.getValue().accept(this, new TailRecursionCallsCollectorKt.VisitorState(Intrinsics.areEqual(expression.getReturnTargetSymbol(), ((IrSimpleFunction) irFunction).getSymbol()), data.getInOtherFunction()));
            }

            /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
            public void visitExpressionBody2(IrExpressionBody body, TailRecursionCallsCollectorKt.VisitorState data) {
                Intrinsics.checkNotNullParameter(body, "body");
                Intrinsics.checkNotNullParameter(data, "data");
                body.acceptChildren(this, data);
            }

            /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
            public void visitBlockBody2(IrBlockBody body, TailRecursionCallsCollectorKt.VisitorState data) {
                Intrinsics.checkNotNullParameter(body, "body");
                Intrinsics.checkNotNullParameter(data, "data");
                visitStatementContainer(body, data);
            }

            /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
            public void visitContainerExpression2(IrContainerExpression expression, TailRecursionCallsCollectorKt.VisitorState data) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(data, "data");
                visitStatementContainer(expression, data);
            }

            /* JADX WARN: Code duplicated, block: B:19:0x0061  */
            private final void visitStatementContainer(IrStatementContainer expression, TailRecursionCallsCollectorKt.VisitorState data) {
                boolean isTailExpression;
                List<IrStatement> statements = expression.getStatements();
                boolean z = zIsUnit;
                IrFunction irFunction2 = irFunction;
                int i = 0;
                for (Object obj : statements) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    IrStatement irStatement = (IrStatement) obj;
                    if (i == CollectionsKt.getLastIndex(expression.getStatements())) {
                        isTailExpression = data.getIsTailExpression();
                    } else if (z) {
                        IrStatement irStatement2 = expression.getStatements().get(i2);
                        if (irStatement2 instanceof IrReturn) {
                            IrReturn irReturn = (IrReturn) irStatement2;
                            if (Intrinsics.areEqual(irReturn.getReturnTargetSymbol(), ((IrSimpleFunction) irFunction2).getSymbol()) && isUnitRead(irReturn.getValue())) {
                                isTailExpression = true;
                            } else {
                                isTailExpression = false;
                            }
                        } else {
                            isTailExpression = false;
                        }
                    } else {
                        isTailExpression = false;
                    }
                    irStatement.accept(this, new TailRecursionCallsCollectorKt.VisitorState(isTailExpression, data.getInOtherFunction()));
                    i = i2;
                }
            }

            private final boolean isUnitRead(IrExpression irExpression) {
                return (irExpression instanceof IrGetObjectValue) && IrTypePredicatesKt.isClassWithFqName(((IrGetObjectValue) irExpression).getSymbol(), StandardNames.FqNames.unit);
            }

            /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
            public void visitWhen2(IrWhen expression, TailRecursionCallsCollectorKt.VisitorState data) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(data, "data");
                for (IrBranch irBranch : expression.getBranches()) {
                    TailRecursionCallsCollectorKt$collectTailRecursionCalls$visitor$1 tailRecursionCallsCollectorKt$collectTailRecursionCalls$visitor$1 = this;
                    irBranch.getCondition().accept(tailRecursionCallsCollectorKt$collectTailRecursionCalls$visitor$1, new TailRecursionCallsCollectorKt.VisitorState(false, data.getInOtherFunction()));
                    irBranch.getResult().accept(tailRecursionCallsCollectorKt$collectTailRecursionCalls$visitor$1, data);
                }
            }

            /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
            public void visitCall2(IrCall expression, TailRecursionCallsCollectorKt.VisitorState data) {
                IrExpression dispatchReceiver;
                IrType type;
                IrClassSymbol classOrNull;
                IrClass owner;
                ClassKind kind;
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(data, "data");
                expression.acceptChildren(this, new TailRecursionCallsCollectorKt.VisitorState(false, data.getInOtherFunction()));
                if (data.getIsTailExpression() && Intrinsics.areEqual(expression.getSymbol(), ((IrSimpleFunction) irFunction).getSymbol())) {
                    if (((IrSimpleFunction) irFunction).getOverriddenSymbols().isEmpty() || !IrUtilsKt.usesDefaultArguments(expression)) {
                        IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
                        if ((dispatchReceiverParameter == null || (type = dispatchReceiverParameter.getType()) == null || (classOrNull = IrTypesKt.getClassOrNull(type)) == null || (owner = classOrNull.getOwner()) == null || (kind = owner.getKind()) == null || !kind.isSingleton()) && (dispatchReceiver = expression.getDispatchReceiver()) != null) {
                            IrFunction irFunction2 = irFunction;
                            if (!(dispatchReceiver instanceof IrGetValue) || !Intrinsics.areEqual(((IrGetValue) dispatchReceiver).getSymbol().getOwner(), irFunction2.getDispatchReceiverParameter())) {
                                return;
                            }
                        }
                        if (data.getInOtherFunction()) {
                            booleanRef.element = true;
                        }
                        linkedHashSet.add(expression);
                    }
                }
            }

            /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
            public void visitFunctionReference2(IrFunctionReference expression, TailRecursionCallsCollectorKt.VisitorState data) {
                IrBody body;
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(data, "data");
                TailRecursionCallsCollectorKt$collectTailRecursionCalls$visitor$1 tailRecursionCallsCollectorKt$collectTailRecursionCalls$visitor$1 = this;
                expression.acceptChildren(tailRecursionCallsCollectorKt$collectTailRecursionCalls$visitor$1, new TailRecursionCallsCollectorKt.VisitorState(false, data.getInOtherFunction()));
                if (!followFunctionReference.invoke(expression).booleanValue() || (body = expression.getSymbol().getOwner().getBody()) == null) {
                    return;
                }
                body.accept(tailRecursionCallsCollectorKt$collectTailRecursionCalls$visitor$1, new TailRecursionCallsCollectorKt.VisitorState(false, true));
            }
        };
        IrBody body = irFunction.getBody();
        if (body != null) {
            body.accept(irElementVisitor, new VisitorState(true, false));
        }
        return new TailCalls(linkedHashSet, booleanRef.element);
    }

    /* JADX INFO: compiled from: TailRecursionCallsCollector.kt */
    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0007¨\u0006\b"}, d2 = {"org/jetbrains/kotlin/backend/common/TailRecursionCallsCollectorKt$collectTailRecursionCalls$VisitorState", "", "isTailExpression", "", "inOtherFunction", "(ZZ)V", "getInOtherFunction", "()Z", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class VisitorState {
        private final boolean inOtherFunction;
        private final boolean isTailExpression;

        public VisitorState(boolean z, boolean z2) {
            this.isTailExpression = z;
            this.inOtherFunction = z2;
        }

        public final boolean getInOtherFunction() {
            return this.inOtherFunction;
        }

        /* JADX INFO: renamed from: isTailExpression, reason: from getter */
        public final boolean getIsTailExpression() {
            return this.isTailExpression;
        }
    }
}
