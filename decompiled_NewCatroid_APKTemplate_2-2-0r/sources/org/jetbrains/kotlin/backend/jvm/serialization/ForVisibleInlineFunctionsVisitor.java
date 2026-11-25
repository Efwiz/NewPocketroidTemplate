package org.jetbrains.kotlin.backend.jvm.serialization;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
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
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.synthetic.SyntheticExtensionsUtilsKt;

/* JADX INFO: compiled from: JvmIrSerializerSession.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u001a\u0012\u0004\u0012\u00020\u0002\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0005J$\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u0003H\u0016J$\u0010\n\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u000b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u0003H\u0016J$\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u0003H\u0016J$\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00102\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u0003H\u0016J$\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00122\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u0003H\u0016¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/serialization/ForVisibleInlineFunctionsVisitor;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "()V", "visitClass", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "data", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ForVisibleInlineFunctionsVisitor implements IrElementVisitor<Unit, Function1<? super IrDeclaration, ? extends Unit>> {
    public static final ForVisibleInlineFunctionsVisitor INSTANCE = new ForVisibleInlineFunctionsVisitor();

    /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
    public void visitDeclaration2(IrDeclarationBase declaration, Function1<? super IrDeclaration, Unit> data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
    }

    private ForVisibleInlineFunctionsVisitor() {
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer */
    public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitAnonymousInitializer2(irAnonymousInitializer, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
    public void visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock */
    public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitBlock2(irBlock, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
    public void visitBlock2(IrBlock irBlock, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody */
    public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitBlockBody2(irBlockBody, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
    public void visitBlockBody2(IrBlockBody irBlockBody, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody */
    public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitBody2(irBody, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
    public void visitBody2(IrBody irBody, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitBody(this, irBody, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch */
    public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitBranch2(irBranch, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
    public void visitBranch2(IrBranch irBranch, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak */
    public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitBreak2(irBreak, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
    public void visitBreak2(IrBreak irBreak, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue */
    public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitBreakContinue2(irBreakContinue, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
    public void visitBreakContinue2(IrBreakContinue irBreakContinue, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall */
    public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitCall2(irCall, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
    public void visitCall2(IrCall irCall, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitCall(this, irCall, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitCallableReference2((IrCallableReference<?>) irCallableReference, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public void visitCallableReference2(IrCallableReference<?> irCallableReference, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch */
    public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitCatch2(irCatch, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
    public void visitCatch2(IrCatch irCatch, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass */
    public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitClass2(irClass, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference */
    public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitClassReference2(irClassReference, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
    public void visitClassReference2(IrClassReference irClassReference, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite */
    public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitComposite2(irComposite, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
    public void visitComposite2(IrComposite irComposite, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitConst2((IrConst<?>) irConst, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public void visitConst2(IrConst<?> irConst, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitConst(this, irConst, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray */
    public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitConstantArray2(irConstantArray, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
    public void visitConstantArray2(IrConstantArray irConstantArray, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject */
    public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitConstantObject2(irConstantObject, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
    public void visitConstantObject2(IrConstantObject irConstantObject, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive */
    public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitConstantPrimitive2(irConstantPrimitive, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
    public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue */
    public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitConstantValue2(irConstantValue, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
    public void visitConstantValue2(IrConstantValue irConstantValue, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor */
    public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitConstructor2(irConstructor, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
    public void visitConstructor2(IrConstructor irConstructor, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitConstructorCall2(irConstructorCall, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
    public void visitConstructorCall2(IrConstructorCall irConstructorCall, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression */
    public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitContainerExpression2(irContainerExpression, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
    public void visitContainerExpression2(IrContainerExpression irContainerExpression, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue */
    public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitContinue2(irContinue, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
    public void visitContinue2(IrContinue irContinue, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration */
    public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitDeclaration2(irDeclarationBase, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference */
    public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitDeclarationReference2(irDeclarationReference, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
    public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitDelegatingConstructorCall2(irDelegatingConstructorCall, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
    public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop */
    public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitDoWhileLoop2(irDoWhileLoop, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
    public void visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitDynamicExpression2(irDynamicExpression, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitDynamicMemberExpression2(irDynamicMemberExpression, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitDynamicOperatorExpression2(irDynamicOperatorExpression, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement */
    public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitElement2(irElement, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch */
    public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitElseBranch2(irElseBranch, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
    public void visitElseBranch2(IrElseBranch irElseBranch, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitEnumConstructorCall2(irEnumConstructorCall, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
    public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry */
    public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitEnumEntry2(irEnumEntry, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
    public void visitEnumEntry2(IrEnumEntry irEnumEntry, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression */
    public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitErrorCallExpression2(irErrorCallExpression, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
    public void visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration */
    public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitErrorDeclaration2(irErrorDeclaration, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
    public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression */
    public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitErrorExpression2(irErrorExpression, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
    public void visitErrorExpression2(IrErrorExpression irErrorExpression, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression */
    public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitExpression2(irExpression, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
    public void visitExpression2(IrExpression irExpression, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody */
    public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitExpressionBody2(irExpressionBody, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
    public void visitExpressionBody2(IrExpressionBody irExpressionBody, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment */
    public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitExternalPackageFragment2(irExternalPackageFragment, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
    public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField */
    public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitField2(irField, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
    public void visitField2(IrField irField, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitField(this, irField, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess */
    public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitFieldAccess2(irFieldAccessExpression, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
    public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile */
    public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitFile2(irFile, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
    public void visitFile2(IrFile irFile, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitFile(this, irFile, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction */
    public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitFunction2(irFunction, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
    public void visitFunction2(IrFunction irFunction, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess */
    public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitFunctionAccess2(irFunctionAccessExpression, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
    public void visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression */
    public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitFunctionExpression2(irFunctionExpression, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
    public void visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference */
    public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitFunctionReference2(irFunctionReference, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
    public void visitFunctionReference2(IrFunctionReference irFunctionReference, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass */
    public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitGetClass2(irGetClass, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
    public void visitGetClass2(IrGetClass irGetClass, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue */
    public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitGetEnumValue2(irGetEnumValue, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
    public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField */
    public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitGetField2(irGetField, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
    public void visitGetField2(IrGetField irGetField, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue */
    public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitGetObjectValue2(irGetObjectValue, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
    public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue */
    public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitGetValue2(irGetValue, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
    public void visitGetValue2(IrGetValue irGetValue, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall */
    public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitInstanceInitializerCall2(irInstanceInitializerCall, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
    public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty */
    public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitLocalDelegatedProperty2(irLocalDelegatedProperty, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
    public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
    public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
    public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop */
    public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitLoop2(irLoop, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
    public void visitLoop2(IrLoop irLoop, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public void visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment */
    public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitModuleFragment2(irModuleFragment, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
    public void visitModuleFragment2(IrModuleFragment irModuleFragment, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment */
    public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitPackageFragment2(irPackageFragment, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
    public void visitPackageFragment2(IrPackageFragment irPackageFragment, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty */
    public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitProperty2(irProperty, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference */
    public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitPropertyReference2(irPropertyReference, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
    public void visitPropertyReference2(IrPropertyReference irPropertyReference, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference */
    public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitRawFunctionReference2(irRawFunctionReference, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
    public void visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn */
    public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitReturn2(irReturn, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
    public void visitReturn2(IrReturn irReturn, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript */
    public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitScript2(irScript, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
    public void visitScript2(IrScript irScript, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitScript(this, irScript, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField */
    public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitSetField2(irSetField, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
    public void visitSetField2(IrSetField irSetField, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue */
    public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitSetValue2(irSetValue, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
    public void visitSetValue2(IrSetValue irSetValue, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction */
    public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitSimpleFunction2(irSimpleFunction, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference */
    public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitSingletonReference2(irGetSingletonValue, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
    public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement */
    public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitSpreadElement2(irSpreadElement, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
    public void visitSpreadElement2(IrSpreadElement irSpreadElement, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation */
    public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitStringConcatenation2(irStringConcatenation, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
    public void visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression */
    public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitSuspendableExpression2(irSuspendableExpression, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
    public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint */
    public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitSuspensionPoint2(irSuspensionPoint, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
    public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody */
    public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitSyntheticBody2(irSyntheticBody, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
    public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow */
    public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitThrow2(irThrow, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
    public void visitThrow2(IrThrow irThrow, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry */
    public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitTry2(irTry, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
    public void visitTry2(IrTry irTry, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitTry(this, irTry, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias */
    public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitTypeAlias2(irTypeAlias, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
    public void visitTypeAlias2(IrTypeAlias irTypeAlias, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator */
    public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitTypeOperator2(irTypeOperatorCall, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
    public void visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter */
    public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitTypeParameter2(irTypeParameter, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
    public void visitTypeParameter2(IrTypeParameter irTypeParameter, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess */
    public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitValueAccess2(irValueAccessExpression, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
    public void visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter */
    public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitValueParameter2(irValueParameter, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
    public void visitValueParameter2(IrValueParameter irValueParameter, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg */
    public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitVararg2(irVararg, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
    public void visitVararg2(IrVararg irVararg, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable */
    public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitVariable2(irVariable, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
    public void visitVariable2(IrVariable irVariable, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitVariable(this, irVariable, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen */
    public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitWhen2(irWhen, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
    public void visitWhen2(IrWhen irWhen, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, function1);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop */
    public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, Function1<? super IrDeclaration, ? extends Unit> function1) {
        visitWhileLoop2(irWhileLoop, (Function1<? super IrDeclaration, Unit>) function1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
    public void visitWhileLoop2(IrWhileLoop irWhileLoop, Function1<? super IrDeclaration, Unit> function1) {
        IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, function1);
    }

    /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
    public void visitElement2(IrElement element, Function1<? super IrDeclaration, Unit> data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        throw new IllegalStateException("Visitor only for nonlocal declarations".toString());
    }

    /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
    public void visitClass2(IrClass declaration, Function1<? super IrDeclaration, Unit> data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        if (SyntheticExtensionsUtilsKt.isVisibleOutside(declaration.getVisibility())) {
            Iterator<IrDeclaration> it = declaration.getDeclarations().iterator();
            while (it.hasNext()) {
                it.next().accept(this, data);
            }
        }
    }

    /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
    public void visitProperty2(IrProperty declaration, Function1<? super IrDeclaration, Unit> data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        if (SyntheticExtensionsUtilsKt.isVisibleOutside(declaration.getVisibility())) {
            IrSimpleFunction getter = declaration.getGetter();
            if (getter != null) {
                getter.accept(this, data);
            }
            IrSimpleFunction setter = declaration.getSetter();
            if (setter != null) {
                setter.accept(this, data);
            }
        }
    }

    /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
    public void visitSimpleFunction2(IrSimpleFunction declaration, Function1<? super IrDeclaration, Unit> data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        if (SyntheticExtensionsUtilsKt.isVisibleOutside(declaration.getVisibility()) && declaration.getIsInline() && !declaration.getIsFakeOverride()) {
            data.invoke(declaration);
        }
    }
}
