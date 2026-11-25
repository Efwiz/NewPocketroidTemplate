package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.jvm.MultifileFacadesKt;
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
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: GenerateMultifileFacades.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u00012\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0002B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001a\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0004H\u0016J\u001a\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u000f\u001a\u0004\u0018\u00010\u0004H\u0016J\u001a\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00042\b\u0010\u000f\u001a\u0004\u0018\u00010\u0004H\u0016R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/UpdateFunctionCallSites;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "functionDelegates", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Ljava/util/Map;)V", "lower", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitCall", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "data", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitFunction", "declaration", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class UpdateFunctionCallSites implements FileLoweringPass, IrElementVisitor<Unit, IrFunction> {
    private final Map<IrSimpleFunction, IrSimpleFunction> functionDelegates;

    public UpdateFunctionCallSites(Map<IrSimpleFunction, IrSimpleFunction> functionDelegates) {
        Intrinsics.checkNotNullParameter(functionDelegates, "functionDelegates");
        this.functionDelegates = functionDelegates;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer */
    public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrFunction irFunction) {
        visitAnonymousInitializer2(irAnonymousInitializer, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
    public void visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock */
    public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, IrFunction irFunction) {
        visitBlock2(irBlock, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
    public void visitBlock2(IrBlock irBlock, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody */
    public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, IrFunction irFunction) {
        visitBlockBody2(irBlockBody, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
    public void visitBlockBody2(IrBlockBody irBlockBody, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody */
    public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, IrFunction irFunction) {
        visitBody2(irBody, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
    public void visitBody2(IrBody irBody, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitBody(this, irBody, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch */
    public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, IrFunction irFunction) {
        visitBranch2(irBranch, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
    public void visitBranch2(IrBranch irBranch, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak */
    public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, IrFunction irFunction) {
        visitBreak2(irBreak, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
    public void visitBreak2(IrBreak irBreak, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue */
    public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, IrFunction irFunction) {
        visitBreakContinue2(irBreakContinue, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
    public void visitBreakContinue2(IrBreakContinue irBreakContinue, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall */
    public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, IrFunction irFunction) {
        visitCall2(irCall, irFunction);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, IrFunction irFunction) {
        visitCallableReference2((IrCallableReference<?>) irCallableReference, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public void visitCallableReference2(IrCallableReference<?> irCallableReference, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch */
    public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, IrFunction irFunction) {
        visitCatch2(irCatch, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
    public void visitCatch2(IrCatch irCatch, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass */
    public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, IrFunction irFunction) {
        visitClass2(irClass, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
    public void visitClass2(IrClass irClass, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitClass(this, irClass, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference */
    public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, IrFunction irFunction) {
        visitClassReference2(irClassReference, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
    public void visitClassReference2(IrClassReference irClassReference, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite */
    public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, IrFunction irFunction) {
        visitComposite2(irComposite, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
    public void visitComposite2(IrComposite irComposite, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, IrFunction irFunction) {
        visitConst2((IrConst<?>) irConst, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public void visitConst2(IrConst<?> irConst, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitConst(this, irConst, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray */
    public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, IrFunction irFunction) {
        visitConstantArray2(irConstantArray, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
    public void visitConstantArray2(IrConstantArray irConstantArray, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject */
    public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, IrFunction irFunction) {
        visitConstantObject2(irConstantObject, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
    public void visitConstantObject2(IrConstantObject irConstantObject, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive */
    public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrFunction irFunction) {
        visitConstantPrimitive2(irConstantPrimitive, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
    public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue */
    public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, IrFunction irFunction) {
        visitConstantValue2(irConstantValue, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
    public void visitConstantValue2(IrConstantValue irConstantValue, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor */
    public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, IrFunction irFunction) {
        visitConstructor2(irConstructor, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
    public void visitConstructor2(IrConstructor irConstructor, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, IrFunction irFunction) {
        visitConstructorCall2(irConstructorCall, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
    public void visitConstructorCall2(IrConstructorCall irConstructorCall, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression */
    public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, IrFunction irFunction) {
        visitContainerExpression2(irContainerExpression, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
    public void visitContainerExpression2(IrContainerExpression irContainerExpression, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue */
    public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, IrFunction irFunction) {
        visitContinue2(irContinue, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
    public void visitContinue2(IrContinue irContinue, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration */
    public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, IrFunction irFunction) {
        visitDeclaration2(irDeclarationBase, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
    public void visitDeclaration2(IrDeclarationBase irDeclarationBase, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference */
    public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrFunction irFunction) {
        visitDeclarationReference2(irDeclarationReference, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
    public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrFunction irFunction) {
        visitDelegatingConstructorCall2(irDelegatingConstructorCall, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
    public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop */
    public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrFunction irFunction) {
        visitDoWhileLoop2(irDoWhileLoop, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
    public void visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrFunction irFunction) {
        visitDynamicExpression2(irDynamicExpression, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrFunction irFunction) {
        visitDynamicMemberExpression2(irDynamicMemberExpression, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrFunction irFunction) {
        visitDynamicOperatorExpression2(irDynamicOperatorExpression, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement */
    public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, IrFunction irFunction) {
        visitElement2(irElement, irFunction);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch */
    public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, IrFunction irFunction) {
        visitElseBranch2(irElseBranch, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
    public void visitElseBranch2(IrElseBranch irElseBranch, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrFunction irFunction) {
        visitEnumConstructorCall2(irEnumConstructorCall, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
    public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry */
    public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, IrFunction irFunction) {
        visitEnumEntry2(irEnumEntry, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
    public void visitEnumEntry2(IrEnumEntry irEnumEntry, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression */
    public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrFunction irFunction) {
        visitErrorCallExpression2(irErrorCallExpression, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
    public void visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration */
    public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrFunction irFunction) {
        visitErrorDeclaration2(irErrorDeclaration, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
    public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression */
    public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, IrFunction irFunction) {
        visitErrorExpression2(irErrorExpression, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
    public void visitErrorExpression2(IrErrorExpression irErrorExpression, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression */
    public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, IrFunction irFunction) {
        visitExpression2(irExpression, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
    public void visitExpression2(IrExpression irExpression, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody */
    public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, IrFunction irFunction) {
        visitExpressionBody2(irExpressionBody, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
    public void visitExpressionBody2(IrExpressionBody irExpressionBody, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment */
    public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrFunction irFunction) {
        visitExternalPackageFragment2(irExternalPackageFragment, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
    public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField */
    public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, IrFunction irFunction) {
        visitField2(irField, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
    public void visitField2(IrField irField, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitField(this, irField, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess */
    public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrFunction irFunction) {
        visitFieldAccess2(irFieldAccessExpression, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
    public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile */
    public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, IrFunction irFunction) {
        visitFile2(irFile, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
    public void visitFile2(IrFile irFile, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitFile(this, irFile, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction */
    public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, IrFunction irFunction2) {
        visitFunction2(irFunction, irFunction2);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess */
    public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrFunction irFunction) {
        visitFunctionAccess2(irFunctionAccessExpression, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
    public void visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression */
    public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrFunction irFunction) {
        visitFunctionExpression2(irFunctionExpression, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
    public void visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference */
    public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, IrFunction irFunction) {
        visitFunctionReference2(irFunctionReference, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
    public void visitFunctionReference2(IrFunctionReference irFunctionReference, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass */
    public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, IrFunction irFunction) {
        visitGetClass2(irGetClass, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
    public void visitGetClass2(IrGetClass irGetClass, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue */
    public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrFunction irFunction) {
        visitGetEnumValue2(irGetEnumValue, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
    public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField */
    public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, IrFunction irFunction) {
        visitGetField2(irGetField, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
    public void visitGetField2(IrGetField irGetField, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue */
    public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrFunction irFunction) {
        visitGetObjectValue2(irGetObjectValue, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
    public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue */
    public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, IrFunction irFunction) {
        visitGetValue2(irGetValue, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
    public void visitGetValue2(IrGetValue irGetValue, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall */
    public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrFunction irFunction) {
        visitInstanceInitializerCall2(irInstanceInitializerCall, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
    public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty */
    public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrFunction irFunction) {
        visitLocalDelegatedProperty2(irLocalDelegatedProperty, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
    public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
    public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrFunction irFunction) {
        visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
    public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop */
    public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, IrFunction irFunction) {
        visitLoop2(irLoop, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
    public void visitLoop2(IrLoop irLoop, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, IrFunction irFunction) {
        visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public void visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment */
    public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, IrFunction irFunction) {
        visitModuleFragment2(irModuleFragment, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
    public void visitModuleFragment2(IrModuleFragment irModuleFragment, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment */
    public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, IrFunction irFunction) {
        visitPackageFragment2(irPackageFragment, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
    public void visitPackageFragment2(IrPackageFragment irPackageFragment, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty */
    public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, IrFunction irFunction) {
        visitProperty2(irProperty, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
    public void visitProperty2(IrProperty irProperty, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference */
    public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, IrFunction irFunction) {
        visitPropertyReference2(irPropertyReference, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
    public void visitPropertyReference2(IrPropertyReference irPropertyReference, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference */
    public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrFunction irFunction) {
        visitRawFunctionReference2(irRawFunctionReference, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
    public void visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn */
    public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, IrFunction irFunction) {
        visitReturn2(irReturn, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
    public void visitReturn2(IrReturn irReturn, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript */
    public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, IrFunction irFunction) {
        visitScript2(irScript, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
    public void visitScript2(IrScript irScript, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitScript(this, irScript, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField */
    public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, IrFunction irFunction) {
        visitSetField2(irSetField, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
    public void visitSetField2(IrSetField irSetField, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue */
    public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, IrFunction irFunction) {
        visitSetValue2(irSetValue, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
    public void visitSetValue2(IrSetValue irSetValue, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction */
    public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrFunction irFunction) {
        visitSimpleFunction2(irSimpleFunction, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
    public void visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference */
    public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrFunction irFunction) {
        visitSingletonReference2(irGetSingletonValue, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
    public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement */
    public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, IrFunction irFunction) {
        visitSpreadElement2(irSpreadElement, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
    public void visitSpreadElement2(IrSpreadElement irSpreadElement, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation */
    public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrFunction irFunction) {
        visitStringConcatenation2(irStringConcatenation, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
    public void visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression */
    public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrFunction irFunction) {
        visitSuspendableExpression2(irSuspendableExpression, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
    public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint */
    public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrFunction irFunction) {
        visitSuspensionPoint2(irSuspensionPoint, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
    public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody */
    public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrFunction irFunction) {
        visitSyntheticBody2(irSyntheticBody, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
    public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow */
    public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, IrFunction irFunction) {
        visitThrow2(irThrow, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
    public void visitThrow2(IrThrow irThrow, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry */
    public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, IrFunction irFunction) {
        visitTry2(irTry, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
    public void visitTry2(IrTry irTry, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitTry(this, irTry, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias */
    public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, IrFunction irFunction) {
        visitTypeAlias2(irTypeAlias, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
    public void visitTypeAlias2(IrTypeAlias irTypeAlias, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator */
    public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrFunction irFunction) {
        visitTypeOperator2(irTypeOperatorCall, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
    public void visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter */
    public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, IrFunction irFunction) {
        visitTypeParameter2(irTypeParameter, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
    public void visitTypeParameter2(IrTypeParameter irTypeParameter, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess */
    public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrFunction irFunction) {
        visitValueAccess2(irValueAccessExpression, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
    public void visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter */
    public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, IrFunction irFunction) {
        visitValueParameter2(irValueParameter, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
    public void visitValueParameter2(IrValueParameter irValueParameter, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg */
    public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, IrFunction irFunction) {
        visitVararg2(irVararg, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
    public void visitVararg2(IrVararg irVararg, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable */
    public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, IrFunction irFunction) {
        visitVariable2(irVariable, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
    public void visitVariable2(IrVariable irVariable, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitVariable(this, irVariable, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen */
    public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, IrFunction irFunction) {
        visitWhen2(irWhen, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
    public void visitWhen2(IrWhen irWhen, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop */
    public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, IrFunction irFunction) {
        visitWhileLoop2(irWhileLoop, irFunction);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
    public void visitWhileLoop2(IrWhileLoop irWhileLoop, IrFunction irFunction) {
        IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, irFunction);
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        irFile.acceptChildren(this, null);
    }

    /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
    public void visitElement2(IrElement element, IrFunction data) {
        Intrinsics.checkNotNullParameter(element, "element");
        element.acceptChildren(this, data);
    }

    /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
    public void visitFunction2(IrFunction declaration, IrFunction data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrElementVisitor.DefaultImpls.visitFunction(this, declaration, declaration);
    }

    /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
    public void visitCall2(IrCall expression, IrFunction data) {
        IrSimpleFunction irSimpleFunction;
        Intrinsics.checkNotNullParameter(expression, "expression");
        expression.acceptChildren(this, data);
        if ((data == null || !MultifileFacadesKt.isMultifileBridge(data)) && (irSimpleFunction = this.functionDelegates.get(expression.getSymbol().getOwner())) != null) {
            expression.setSymbol(irSimpleFunction.getSymbol());
        }
    }
}
