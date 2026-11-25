package org.jetbrains.kotlin.ir.backend.js.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
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
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
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
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: Keeper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0018B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\nJ\u0018\u0010\u0010\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0003H\u0016J\u0018\u0010\u0013\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u0003H\u0016J\u0018\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u0003H\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/Keeper;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "Lorg/jetbrains/kotlin/ir/backend/js/utils/Keeper$KeepData;", "keep", "", "", "(Ljava/util/Set;)V", "keptDeclarations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "isInKeep", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "shouldKeep", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "data", "visitDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "KeepData", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Keeper implements IrElementVisitor<Unit, KeepData> {
    private final Set<String> keep;
    private final Set<IrDeclaration> keptDeclarations;

    public Keeper(Set<String> keep) {
        Intrinsics.checkNotNullParameter(keep, "keep");
        this.keep = keep;
        this.keptDeclarations = new LinkedHashSet();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer */
    public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, KeepData keepData) {
        visitAnonymousInitializer2(irAnonymousInitializer, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
    public void visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock */
    public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, KeepData keepData) {
        visitBlock2(irBlock, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
    public void visitBlock2(IrBlock irBlock, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody */
    public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, KeepData keepData) {
        visitBlockBody2(irBlockBody, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
    public void visitBlockBody2(IrBlockBody irBlockBody, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody */
    public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, KeepData keepData) {
        visitBody2(irBody, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
    public void visitBody2(IrBody irBody, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitBody(this, irBody, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch */
    public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, KeepData keepData) {
        visitBranch2(irBranch, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
    public void visitBranch2(IrBranch irBranch, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak */
    public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, KeepData keepData) {
        visitBreak2(irBreak, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
    public void visitBreak2(IrBreak irBreak, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue */
    public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, KeepData keepData) {
        visitBreakContinue2(irBreakContinue, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
    public void visitBreakContinue2(IrBreakContinue irBreakContinue, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall */
    public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, KeepData keepData) {
        visitCall2(irCall, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
    public void visitCall2(IrCall irCall, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitCall(this, irCall, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, KeepData keepData) {
        visitCallableReference2((IrCallableReference<?>) irCallableReference, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public void visitCallableReference2(IrCallableReference<?> irCallableReference, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch */
    public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, KeepData keepData) {
        visitCatch2(irCatch, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
    public void visitCatch2(IrCatch irCatch, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass */
    public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, KeepData keepData) {
        visitClass2(irClass, keepData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference */
    public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, KeepData keepData) {
        visitClassReference2(irClassReference, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
    public void visitClassReference2(IrClassReference irClassReference, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite */
    public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, KeepData keepData) {
        visitComposite2(irComposite, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
    public void visitComposite2(IrComposite irComposite, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, KeepData keepData) {
        visitConst2((IrConst<?>) irConst, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public void visitConst2(IrConst<?> irConst, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitConst(this, irConst, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray */
    public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, KeepData keepData) {
        visitConstantArray2(irConstantArray, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
    public void visitConstantArray2(IrConstantArray irConstantArray, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject */
    public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, KeepData keepData) {
        visitConstantObject2(irConstantObject, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
    public void visitConstantObject2(IrConstantObject irConstantObject, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive */
    public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, KeepData keepData) {
        visitConstantPrimitive2(irConstantPrimitive, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
    public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue */
    public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, KeepData keepData) {
        visitConstantValue2(irConstantValue, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
    public void visitConstantValue2(IrConstantValue irConstantValue, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor */
    public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, KeepData keepData) {
        visitConstructor2(irConstructor, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
    public void visitConstructor2(IrConstructor irConstructor, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, KeepData keepData) {
        visitConstructorCall2(irConstructorCall, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
    public void visitConstructorCall2(IrConstructorCall irConstructorCall, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression */
    public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, KeepData keepData) {
        visitContainerExpression2(irContainerExpression, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
    public void visitContainerExpression2(IrContainerExpression irContainerExpression, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue */
    public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, KeepData keepData) {
        visitContinue2(irContinue, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
    public void visitContinue2(IrContinue irContinue, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration */
    public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, KeepData keepData) {
        visitDeclaration2(irDeclarationBase, keepData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference */
    public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, KeepData keepData) {
        visitDeclarationReference2(irDeclarationReference, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
    public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, KeepData keepData) {
        visitDelegatingConstructorCall2(irDelegatingConstructorCall, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
    public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop */
    public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, KeepData keepData) {
        visitDoWhileLoop2(irDoWhileLoop, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
    public void visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, KeepData keepData) {
        visitDynamicExpression2(irDynamicExpression, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, KeepData keepData) {
        visitDynamicMemberExpression2(irDynamicMemberExpression, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, KeepData keepData) {
        visitDynamicOperatorExpression2(irDynamicOperatorExpression, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement */
    public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, KeepData keepData) {
        visitElement2(irElement, keepData);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch */
    public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, KeepData keepData) {
        visitElseBranch2(irElseBranch, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
    public void visitElseBranch2(IrElseBranch irElseBranch, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, KeepData keepData) {
        visitEnumConstructorCall2(irEnumConstructorCall, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
    public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry */
    public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, KeepData keepData) {
        visitEnumEntry2(irEnumEntry, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
    public void visitEnumEntry2(IrEnumEntry irEnumEntry, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression */
    public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, KeepData keepData) {
        visitErrorCallExpression2(irErrorCallExpression, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
    public void visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration */
    public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, KeepData keepData) {
        visitErrorDeclaration2(irErrorDeclaration, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
    public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression */
    public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, KeepData keepData) {
        visitErrorExpression2(irErrorExpression, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
    public void visitErrorExpression2(IrErrorExpression irErrorExpression, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression */
    public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, KeepData keepData) {
        visitExpression2(irExpression, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
    public void visitExpression2(IrExpression irExpression, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody */
    public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, KeepData keepData) {
        visitExpressionBody2(irExpressionBody, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
    public void visitExpressionBody2(IrExpressionBody irExpressionBody, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment */
    public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, KeepData keepData) {
        visitExternalPackageFragment2(irExternalPackageFragment, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
    public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField */
    public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, KeepData keepData) {
        visitField2(irField, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
    public void visitField2(IrField irField, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitField(this, irField, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess */
    public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, KeepData keepData) {
        visitFieldAccess2(irFieldAccessExpression, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
    public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile */
    public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, KeepData keepData) {
        visitFile2(irFile, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
    public void visitFile2(IrFile irFile, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitFile(this, irFile, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction */
    public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, KeepData keepData) {
        visitFunction2(irFunction, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
    public void visitFunction2(IrFunction irFunction, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess */
    public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, KeepData keepData) {
        visitFunctionAccess2(irFunctionAccessExpression, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
    public void visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression */
    public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, KeepData keepData) {
        visitFunctionExpression2(irFunctionExpression, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
    public void visitFunctionExpression2(IrFunctionExpression irFunctionExpression, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference */
    public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, KeepData keepData) {
        visitFunctionReference2(irFunctionReference, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
    public void visitFunctionReference2(IrFunctionReference irFunctionReference, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass */
    public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, KeepData keepData) {
        visitGetClass2(irGetClass, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
    public void visitGetClass2(IrGetClass irGetClass, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue */
    public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, KeepData keepData) {
        visitGetEnumValue2(irGetEnumValue, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
    public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField */
    public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, KeepData keepData) {
        visitGetField2(irGetField, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
    public void visitGetField2(IrGetField irGetField, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue */
    public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, KeepData keepData) {
        visitGetObjectValue2(irGetObjectValue, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
    public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue */
    public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, KeepData keepData) {
        visitGetValue2(irGetValue, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
    public void visitGetValue2(IrGetValue irGetValue, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall */
    public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, KeepData keepData) {
        visitInstanceInitializerCall2(irInstanceInitializerCall, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
    public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty */
    public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, KeepData keepData) {
        visitLocalDelegatedProperty2(irLocalDelegatedProperty, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
    public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
    public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, KeepData keepData) {
        visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
    public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop */
    public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, KeepData keepData) {
        visitLoop2(irLoop, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
    public void visitLoop2(IrLoop irLoop, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, KeepData keepData) {
        visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public void visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment */
    public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, KeepData keepData) {
        visitModuleFragment2(irModuleFragment, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
    public void visitModuleFragment2(IrModuleFragment irModuleFragment, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment */
    public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, KeepData keepData) {
        visitPackageFragment2(irPackageFragment, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
    public void visitPackageFragment2(IrPackageFragment irPackageFragment, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty */
    public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, KeepData keepData) {
        visitProperty2(irProperty, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
    public void visitProperty2(IrProperty irProperty, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference */
    public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, KeepData keepData) {
        visitPropertyReference2(irPropertyReference, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
    public void visitPropertyReference2(IrPropertyReference irPropertyReference, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference */
    public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, KeepData keepData) {
        visitRawFunctionReference2(irRawFunctionReference, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
    public void visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn */
    public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, KeepData keepData) {
        visitReturn2(irReturn, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
    public void visitReturn2(IrReturn irReturn, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript */
    public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, KeepData keepData) {
        visitScript2(irScript, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
    public void visitScript2(IrScript irScript, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitScript(this, irScript, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField */
    public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, KeepData keepData) {
        visitSetField2(irSetField, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
    public void visitSetField2(IrSetField irSetField, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue */
    public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, KeepData keepData) {
        visitSetValue2(irSetValue, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
    public void visitSetValue2(IrSetValue irSetValue, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction */
    public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, KeepData keepData) {
        visitSimpleFunction2(irSimpleFunction, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
    public void visitSimpleFunction2(IrSimpleFunction irSimpleFunction, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference */
    public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, KeepData keepData) {
        visitSingletonReference2(irGetSingletonValue, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
    public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement */
    public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, KeepData keepData) {
        visitSpreadElement2(irSpreadElement, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
    public void visitSpreadElement2(IrSpreadElement irSpreadElement, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation */
    public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, KeepData keepData) {
        visitStringConcatenation2(irStringConcatenation, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
    public void visitStringConcatenation2(IrStringConcatenation irStringConcatenation, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression */
    public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, KeepData keepData) {
        visitSuspendableExpression2(irSuspendableExpression, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
    public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint */
    public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, KeepData keepData) {
        visitSuspensionPoint2(irSuspensionPoint, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
    public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody */
    public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, KeepData keepData) {
        visitSyntheticBody2(irSyntheticBody, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
    public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow */
    public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, KeepData keepData) {
        visitThrow2(irThrow, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
    public void visitThrow2(IrThrow irThrow, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry */
    public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, KeepData keepData) {
        visitTry2(irTry, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
    public void visitTry2(IrTry irTry, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitTry(this, irTry, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias */
    public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, KeepData keepData) {
        visitTypeAlias2(irTypeAlias, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
    public void visitTypeAlias2(IrTypeAlias irTypeAlias, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator */
    public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, KeepData keepData) {
        visitTypeOperator2(irTypeOperatorCall, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
    public void visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter */
    public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, KeepData keepData) {
        visitTypeParameter2(irTypeParameter, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
    public void visitTypeParameter2(IrTypeParameter irTypeParameter, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess */
    public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, KeepData keepData) {
        visitValueAccess2(irValueAccessExpression, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
    public void visitValueAccess2(IrValueAccessExpression irValueAccessExpression, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter */
    public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, KeepData keepData) {
        visitValueParameter2(irValueParameter, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
    public void visitValueParameter2(IrValueParameter irValueParameter, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg */
    public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, KeepData keepData) {
        visitVararg2(irVararg, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
    public void visitVararg2(IrVararg irVararg, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable */
    public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, KeepData keepData) {
        visitVariable2(irVariable, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
    public void visitVariable2(IrVariable irVariable, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitVariable(this, irVariable, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen */
    public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, KeepData keepData) {
        visitWhen2(irWhen, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
    public void visitWhen2(IrWhen irWhen, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, keepData);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop */
    public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, KeepData keepData) {
        visitWhileLoop2(irWhileLoop, keepData);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
    public void visitWhileLoop2(IrWhileLoop irWhileLoop, KeepData keepData) {
        IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, keepData);
    }

    public final boolean shouldKeep(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!this.keptDeclarations.contains(declaration)) {
            if (declaration instanceof IrOverridableDeclaration) {
                List overriddenSymbols = ((IrOverridableDeclaration) declaration).getOverriddenSymbols();
                if (!(overriddenSymbols instanceof Collection) || !overriddenSymbols.isEmpty()) {
                    Iterator it = overriddenSymbols.iterator();
                    while (it.hasNext()) {
                        IrSymbolOwner owner = ((IrSymbol) it.next()).getOwner();
                        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                        if (shouldKeep((IrDeclaration) owner)) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
    public void visitElement2(IrElement element, KeepData data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        element.acceptChildren(this, data);
    }

    /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
    public void visitClass2(IrClass declaration, KeepData data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        boolean classShouldBeKept = data.getClassShouldBeKept();
        boolean classInKeep = data.getClassInKeep();
        data.setClassShouldBeKept(false);
        boolean z = data.getClassInKeep() || isInKeep(declaration);
        if (z) {
            this.keptDeclarations.add(declaration);
        }
        data.setClassInKeep(z);
        IrElementVisitor.DefaultImpls.visitClass(this, declaration, data);
        if (data.getClassShouldBeKept()) {
            this.keptDeclarations.add(declaration);
        }
        data.setClassShouldBeKept(classShouldBeKept);
        data.setClassInKeep(classInKeep);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
    public void visitDeclaration2(IrDeclarationBase declaration, KeepData data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        IrElementVisitor.DefaultImpls.visitDeclaration(this, declaration, data);
        if (this.keptDeclarations.contains(declaration)) {
            return;
        }
        if (((declaration instanceof IrDeclarationWithName) && isInKeep((IrDeclarationWithName) declaration)) || data.getClassInKeep()) {
            this.keptDeclarations.add(declaration);
            data.setClassShouldBeKept(true);
        }
    }

    private final boolean isInKeep(IrDeclarationWithName declaration) {
        Set<String> set = this.keep;
        FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(declaration);
        return CollectionsKt.contains(set, fqNameWhenAvailable != null ? fqNameWhenAvailable.asString() : null);
    }

    /* JADX INFO: compiled from: Keeper.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/Keeper$KeepData;", "", "classInKeep", "", "classShouldBeKept", "(ZZ)V", "getClassInKeep", "()Z", "setClassInKeep", "(Z)V", "getClassShouldBeKept", "setClassShouldBeKept", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class KeepData {
        private boolean classInKeep;
        private boolean classShouldBeKept;

        public KeepData(boolean z, boolean z2) {
            this.classInKeep = z;
            this.classShouldBeKept = z2;
        }

        public final boolean getClassInKeep() {
            return this.classInKeep;
        }

        public final boolean getClassShouldBeKept() {
            return this.classShouldBeKept;
        }

        public final void setClassInKeep(boolean z) {
            this.classInKeep = z;
        }

        public final void setClassShouldBeKept(boolean z) {
            this.classShouldBeKept = z;
        }
    }
}
