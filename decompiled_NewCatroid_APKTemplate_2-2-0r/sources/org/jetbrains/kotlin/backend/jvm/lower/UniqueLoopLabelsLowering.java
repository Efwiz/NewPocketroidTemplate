package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
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
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: UniqueLoopLabelsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/UniqueLoopLabelsLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class UniqueLoopLabelsLowering implements FileLoweringPass {
    private final JvmBackendContext context;

    public UniqueLoopLabelsLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        irFile.accept(new IrElementVisitor<Unit, String>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.UniqueLoopLabelsLowering.lower.1
            private int counter;

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitAnonymousInitializer */
            public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, String str) {
                visitAnonymousInitializer2(irAnonymousInitializer, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
            public void visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, String str) {
                IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlock */
            public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, String str) {
                visitBlock2(irBlock, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
            public void visitBlock2(IrBlock irBlock, String str) {
                IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlockBody */
            public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, String str) {
                visitBlockBody2(irBlockBody, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
            public void visitBlockBody2(IrBlockBody irBlockBody, String str) {
                IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBody */
            public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, String str) {
                visitBody2(irBody, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
            public void visitBody2(IrBody irBody, String str) {
                IrElementVisitor.DefaultImpls.visitBody(this, irBody, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBranch */
            public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, String str) {
                visitBranch2(irBranch, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
            public void visitBranch2(IrBranch irBranch, String str) {
                IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreak */
            public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, String str) {
                visitBreak2(irBreak, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
            public void visitBreak2(IrBreak irBreak, String str) {
                IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreakContinue */
            public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, String str) {
                visitBreakContinue2(irBreakContinue, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
            public void visitBreakContinue2(IrBreakContinue irBreakContinue, String str) {
                IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall */
            public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, String str) {
                visitCall2(irCall, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
            public void visitCall2(IrCall irCall, String str) {
                IrElementVisitor.DefaultImpls.visitCall(this, irCall, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, String str) {
                visitCallableReference2((IrCallableReference<?>) irCallableReference, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
            public void visitCallableReference2(IrCallableReference<?> irCallableReference, String str) {
                IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCatch */
            public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, String str) {
                visitCatch2(irCatch, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
            public void visitCatch2(IrCatch irCatch, String str) {
                IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClass */
            public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, String str) {
                visitClass2(irClass, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
            public void visitClass2(IrClass irClass, String str) {
                IrElementVisitor.DefaultImpls.visitClass(this, irClass, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClassReference */
            public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, String str) {
                visitClassReference2(irClassReference, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
            public void visitClassReference2(IrClassReference irClassReference, String str) {
                IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitComposite */
            public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, String str) {
                visitComposite2(irComposite, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
            public void visitComposite2(IrComposite irComposite, String str) {
                IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, String str) {
                visitConst2((IrConst<?>) irConst, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
            public void visitConst2(IrConst<?> irConst, String str) {
                IrElementVisitor.DefaultImpls.visitConst(this, irConst, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantArray */
            public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, String str) {
                visitConstantArray2(irConstantArray, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
            public void visitConstantArray2(IrConstantArray irConstantArray, String str) {
                IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantObject */
            public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, String str) {
                visitConstantObject2(irConstantObject, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
            public void visitConstantObject2(IrConstantObject irConstantObject, String str) {
                IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantPrimitive */
            public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, String str) {
                visitConstantPrimitive2(irConstantPrimitive, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
            public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, String str) {
                IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantValue */
            public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, String str) {
                visitConstantValue2(irConstantValue, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
            public void visitConstantValue2(IrConstantValue irConstantValue, String str) {
                IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructor */
            public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, String str) {
                visitConstructor2(irConstructor, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
            public void visitConstructor2(IrConstructor irConstructor, String str) {
                IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructorCall */
            public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, String str) {
                visitConstructorCall2(irConstructorCall, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
            public void visitConstructorCall2(IrConstructorCall irConstructorCall, String str) {
                IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContainerExpression */
            public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, String str) {
                visitContainerExpression2(irContainerExpression, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
            public void visitContainerExpression2(IrContainerExpression irContainerExpression, String str) {
                IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContinue */
            public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, String str) {
                visitContinue2(irContinue, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
            public void visitContinue2(IrContinue irContinue, String str) {
                IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclaration */
            public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, String str) {
                visitDeclaration2(irDeclarationBase, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
            public void visitDeclaration2(IrDeclarationBase irDeclarationBase, String str) {
                IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclarationReference */
            public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, String str) {
                visitDeclarationReference2(irDeclarationReference, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
            public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, String str) {
                IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDelegatingConstructorCall */
            public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, String str) {
                visitDelegatingConstructorCall2(irDelegatingConstructorCall, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
            public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, String str) {
                IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDoWhileLoop */
            public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, String str) {
                visitDoWhileLoop2(irDoWhileLoop, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
            public void visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, String str) {
                IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicExpression */
            public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, String str) {
                visitDynamicExpression2(irDynamicExpression, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
            public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, String str) {
                IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicMemberExpression */
            public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, String str) {
                visitDynamicMemberExpression2(irDynamicMemberExpression, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
            public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, String str) {
                IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicOperatorExpression */
            public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, String str) {
                visitDynamicOperatorExpression2(irDynamicOperatorExpression, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
            public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, String str) {
                IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElement */
            public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, String str) {
                visitElement2(irElement, str);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElseBranch */
            public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, String str) {
                visitElseBranch2(irElseBranch, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
            public void visitElseBranch2(IrElseBranch irElseBranch, String str) {
                IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumConstructorCall */
            public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, String str) {
                visitEnumConstructorCall2(irEnumConstructorCall, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
            public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, String str) {
                IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumEntry */
            public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, String str) {
                visitEnumEntry2(irEnumEntry, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
            public void visitEnumEntry2(IrEnumEntry irEnumEntry, String str) {
                IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorCallExpression */
            public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, String str) {
                visitErrorCallExpression2(irErrorCallExpression, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
            public void visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, String str) {
                IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorDeclaration */
            public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, String str) {
                visitErrorDeclaration2(irErrorDeclaration, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
            public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, String str) {
                IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorExpression */
            public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, String str) {
                visitErrorExpression2(irErrorExpression, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
            public void visitErrorExpression2(IrErrorExpression irErrorExpression, String str) {
                IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpression */
            public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, String str) {
                visitExpression2(irExpression, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
            public void visitExpression2(IrExpression irExpression, String str) {
                IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpressionBody */
            public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, String str) {
                visitExpressionBody2(irExpressionBody, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
            public void visitExpressionBody2(IrExpressionBody irExpressionBody, String str) {
                IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExternalPackageFragment */
            public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, String str) {
                visitExternalPackageFragment2(irExternalPackageFragment, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
            public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, String str) {
                IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitField */
            public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, String str) {
                visitField2(irField, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
            public void visitField2(IrField irField, String str) {
                IrElementVisitor.DefaultImpls.visitField(this, irField, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFieldAccess */
            public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, String str) {
                visitFieldAccess2(irFieldAccessExpression, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
            public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, String str) {
                IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFile */
            public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile2, String str) {
                visitFile2(irFile2, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
            public void visitFile2(IrFile irFile2, String str) {
                IrElementVisitor.DefaultImpls.visitFile(this, irFile2, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunction */
            public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, String str) {
                visitFunction2(irFunction, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
            public void visitFunction2(IrFunction irFunction, String str) {
                IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionAccess */
            public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, String str) {
                visitFunctionAccess2(irFunctionAccessExpression, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
            public void visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, String str) {
                IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionExpression */
            public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, String str) {
                visitFunctionExpression2(irFunctionExpression, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
            public void visitFunctionExpression2(IrFunctionExpression irFunctionExpression, String str) {
                IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionReference */
            public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, String str) {
                visitFunctionReference2(irFunctionReference, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
            public void visitFunctionReference2(IrFunctionReference irFunctionReference, String str) {
                IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetClass */
            public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, String str) {
                visitGetClass2(irGetClass, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
            public void visitGetClass2(IrGetClass irGetClass, String str) {
                IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetEnumValue */
            public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, String str) {
                visitGetEnumValue2(irGetEnumValue, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
            public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, String str) {
                IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetField */
            public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, String str) {
                visitGetField2(irGetField, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
            public void visitGetField2(IrGetField irGetField, String str) {
                IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetObjectValue */
            public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, String str) {
                visitGetObjectValue2(irGetObjectValue, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
            public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, String str) {
                IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetValue */
            public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, String str) {
                visitGetValue2(irGetValue, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
            public void visitGetValue2(IrGetValue irGetValue, String str) {
                IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitInstanceInitializerCall */
            public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, String str) {
                visitInstanceInitializerCall2(irInstanceInitializerCall, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
            public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, String str) {
                IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedProperty */
            public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, String str) {
                visitLocalDelegatedProperty2(irLocalDelegatedProperty, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
            public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, String str) {
                IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
            public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, String str) {
                visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
            public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, String str) {
                IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLoop */
            public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, String str) {
                visitLoop2(irLoop, str);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, String str) {
                visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
            public void visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, String str) {
                IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitModuleFragment */
            public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, String str) {
                visitModuleFragment2(irModuleFragment, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
            public void visitModuleFragment2(IrModuleFragment irModuleFragment, String str) {
                IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPackageFragment */
            public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, String str) {
                visitPackageFragment2(irPackageFragment, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
            public void visitPackageFragment2(IrPackageFragment irPackageFragment, String str) {
                IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitProperty */
            public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, String str) {
                visitProperty2(irProperty, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
            public void visitProperty2(IrProperty irProperty, String str) {
                IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPropertyReference */
            public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, String str) {
                visitPropertyReference2(irPropertyReference, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
            public void visitPropertyReference2(IrPropertyReference irPropertyReference, String str) {
                IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitRawFunctionReference */
            public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, String str) {
                visitRawFunctionReference2(irRawFunctionReference, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
            public void visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, String str) {
                IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitReturn */
            public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, String str) {
                visitReturn2(irReturn, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
            public void visitReturn2(IrReturn irReturn, String str) {
                IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitScript */
            public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, String str) {
                visitScript2(irScript, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
            public void visitScript2(IrScript irScript, String str) {
                IrElementVisitor.DefaultImpls.visitScript(this, irScript, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetField */
            public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, String str) {
                visitSetField2(irSetField, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
            public void visitSetField2(IrSetField irSetField, String str) {
                IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetValue */
            public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, String str) {
                visitSetValue2(irSetValue, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
            public void visitSetValue2(IrSetValue irSetValue, String str) {
                IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSimpleFunction */
            public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, String str) {
                visitSimpleFunction2(irSimpleFunction, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
            public void visitSimpleFunction2(IrSimpleFunction irSimpleFunction, String str) {
                IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSingletonReference */
            public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, String str) {
                visitSingletonReference2(irGetSingletonValue, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
            public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, String str) {
                IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSpreadElement */
            public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, String str) {
                visitSpreadElement2(irSpreadElement, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
            public void visitSpreadElement2(IrSpreadElement irSpreadElement, String str) {
                IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitStringConcatenation */
            public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, String str) {
                visitStringConcatenation2(irStringConcatenation, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
            public void visitStringConcatenation2(IrStringConcatenation irStringConcatenation, String str) {
                IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspendableExpression */
            public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, String str) {
                visitSuspendableExpression2(irSuspendableExpression, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
            public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, String str) {
                IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspensionPoint */
            public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, String str) {
                visitSuspensionPoint2(irSuspensionPoint, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
            public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, String str) {
                IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSyntheticBody */
            public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, String str) {
                visitSyntheticBody2(irSyntheticBody, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
            public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, String str) {
                IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitThrow */
            public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, String str) {
                visitThrow2(irThrow, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
            public void visitThrow2(IrThrow irThrow, String str) {
                IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTry */
            public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, String str) {
                visitTry2(irTry, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
            public void visitTry2(IrTry irTry, String str) {
                IrElementVisitor.DefaultImpls.visitTry(this, irTry, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeAlias */
            public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, String str) {
                visitTypeAlias2(irTypeAlias, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
            public void visitTypeAlias2(IrTypeAlias irTypeAlias, String str) {
                IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeOperator */
            public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, String str) {
                visitTypeOperator2(irTypeOperatorCall, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
            public void visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, String str) {
                IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeParameter */
            public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, String str) {
                visitTypeParameter2(irTypeParameter, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
            public void visitTypeParameter2(IrTypeParameter irTypeParameter, String str) {
                IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueAccess */
            public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, String str) {
                visitValueAccess2(irValueAccessExpression, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
            public void visitValueAccess2(IrValueAccessExpression irValueAccessExpression, String str) {
                IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueParameter */
            public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, String str) {
                visitValueParameter2(irValueParameter, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
            public void visitValueParameter2(IrValueParameter irValueParameter, String str) {
                IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVararg */
            public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, String str) {
                visitVararg2(irVararg, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
            public void visitVararg2(IrVararg irVararg, String str) {
                IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVariable */
            public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, String str) {
                visitVariable2(irVariable, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
            public void visitVariable2(IrVariable irVariable, String str) {
                IrElementVisitor.DefaultImpls.visitVariable(this, irVariable, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhen */
            public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, String str) {
                visitWhen2(irWhen, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
            public void visitWhen2(IrWhen irWhen, String str) {
                IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, str);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhileLoop */
            public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, String str) {
                visitWhileLoop2(irWhileLoop, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
            public void visitWhileLoop2(IrWhileLoop irWhileLoop, String str) {
                IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, str);
            }

            /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
            public void visitElement2(IrElement element, String data) {
                Intrinsics.checkNotNullParameter(element, "element");
                Intrinsics.checkNotNullParameter(data, "data");
                AnonymousClass1 anonymousClass1 = this;
                if (element instanceof IrDeclarationWithName) {
                    data = data + ((IrDeclarationWithName) element).getName() + '$';
                }
                element.acceptChildren(anonymousClass1, data);
            }

            /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
            public void visitLoop2(IrLoop loop, String data) {
                Intrinsics.checkNotNullParameter(loop, "loop");
                Intrinsics.checkNotNullParameter(data, "data");
                StringBuilder sbAppend = new StringBuilder().append(data);
                int i = this.counter + 1;
                this.counter = i;
                loop.setLabel(sbAppend.append(i).toString());
                IrElementVisitor.DefaultImpls.visitLoop(this, loop, data);
            }
        }, "");
    }
}
