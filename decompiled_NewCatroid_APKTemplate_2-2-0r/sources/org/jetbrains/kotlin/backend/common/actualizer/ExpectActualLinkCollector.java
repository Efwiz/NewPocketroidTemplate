package org.jetbrains.kotlin.backend.common.actualizer;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtDiagnosticReporterWithImplicitIrBasedContext;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
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
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.calls.mpp.AbstractExpectActualCompatibilityChecker;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;

/* JADX INFO: compiled from: ExpectActualCollector.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u001cB\u0005¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0003H\u0002J&\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\u0006\u0010\n\u001a\u00020\u0003H\u0002J\u0018\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0003H\u0016J\u0018\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u0003H\u0016J\u0018\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u0003H\u0016J\u0018\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00192\u0006\u0010\u0012\u001a\u00020\u0003H\u0016J\u0018\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u0003H\u0016¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/ExpectActualLinkCollector;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "Lorg/jetbrains/kotlin/backend/common/actualizer/ExpectActualLinkCollector$MatchingContext;", "()V", "matchExpectCallable", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "context", "matchExpectDeclaration", "expectSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "actualSymbols", "", "visitClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "data", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "MatchingContext", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ExpectActualLinkCollector implements IrElementVisitor<Unit, MatchingContext> {
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer */
    public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, MatchingContext matchingContext) {
        visitAnonymousInitializer2(irAnonymousInitializer, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
    public void visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock */
    public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, MatchingContext matchingContext) {
        visitBlock2(irBlock, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
    public void visitBlock2(IrBlock irBlock, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody */
    public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, MatchingContext matchingContext) {
        visitBlockBody2(irBlockBody, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
    public void visitBlockBody2(IrBlockBody irBlockBody, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody */
    public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, MatchingContext matchingContext) {
        visitBody2(irBody, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
    public void visitBody2(IrBody irBody, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitBody(this, irBody, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch */
    public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, MatchingContext matchingContext) {
        visitBranch2(irBranch, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
    public void visitBranch2(IrBranch irBranch, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak */
    public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, MatchingContext matchingContext) {
        visitBreak2(irBreak, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
    public void visitBreak2(IrBreak irBreak, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue */
    public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, MatchingContext matchingContext) {
        visitBreakContinue2(irBreakContinue, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
    public void visitBreakContinue2(IrBreakContinue irBreakContinue, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall */
    public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, MatchingContext matchingContext) {
        visitCall2(irCall, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
    public void visitCall2(IrCall irCall, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitCall(this, irCall, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, MatchingContext matchingContext) {
        visitCallableReference2((IrCallableReference<?>) irCallableReference, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public void visitCallableReference2(IrCallableReference<?> irCallableReference, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch */
    public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, MatchingContext matchingContext) {
        visitCatch2(irCatch, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
    public void visitCatch2(IrCatch irCatch, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass */
    public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, MatchingContext matchingContext) {
        visitClass2(irClass, matchingContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference */
    public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, MatchingContext matchingContext) {
        visitClassReference2(irClassReference, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
    public void visitClassReference2(IrClassReference irClassReference, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite */
    public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, MatchingContext matchingContext) {
        visitComposite2(irComposite, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
    public void visitComposite2(IrComposite irComposite, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, MatchingContext matchingContext) {
        visitConst2((IrConst<?>) irConst, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public void visitConst2(IrConst<?> irConst, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitConst(this, irConst, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray */
    public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, MatchingContext matchingContext) {
        visitConstantArray2(irConstantArray, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
    public void visitConstantArray2(IrConstantArray irConstantArray, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject */
    public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, MatchingContext matchingContext) {
        visitConstantObject2(irConstantObject, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
    public void visitConstantObject2(IrConstantObject irConstantObject, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive */
    public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, MatchingContext matchingContext) {
        visitConstantPrimitive2(irConstantPrimitive, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
    public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue */
    public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, MatchingContext matchingContext) {
        visitConstantValue2(irConstantValue, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
    public void visitConstantValue2(IrConstantValue irConstantValue, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor */
    public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, MatchingContext matchingContext) {
        visitConstructor2(irConstructor, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
    public void visitConstructor2(IrConstructor irConstructor, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, MatchingContext matchingContext) {
        visitConstructorCall2(irConstructorCall, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
    public void visitConstructorCall2(IrConstructorCall irConstructorCall, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression */
    public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, MatchingContext matchingContext) {
        visitContainerExpression2(irContainerExpression, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
    public void visitContainerExpression2(IrContainerExpression irContainerExpression, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue */
    public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, MatchingContext matchingContext) {
        visitContinue2(irContinue, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
    public void visitContinue2(IrContinue irContinue, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration */
    public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, MatchingContext matchingContext) {
        visitDeclaration2(irDeclarationBase, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
    public void visitDeclaration2(IrDeclarationBase irDeclarationBase, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference */
    public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, MatchingContext matchingContext) {
        visitDeclarationReference2(irDeclarationReference, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
    public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, MatchingContext matchingContext) {
        visitDelegatingConstructorCall2(irDelegatingConstructorCall, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
    public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop */
    public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, MatchingContext matchingContext) {
        visitDoWhileLoop2(irDoWhileLoop, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
    public void visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, MatchingContext matchingContext) {
        visitDynamicExpression2(irDynamicExpression, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, MatchingContext matchingContext) {
        visitDynamicMemberExpression2(irDynamicMemberExpression, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, MatchingContext matchingContext) {
        visitDynamicOperatorExpression2(irDynamicOperatorExpression, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement */
    public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, MatchingContext matchingContext) {
        visitElement2(irElement, matchingContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch */
    public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, MatchingContext matchingContext) {
        visitElseBranch2(irElseBranch, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
    public void visitElseBranch2(IrElseBranch irElseBranch, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, MatchingContext matchingContext) {
        visitEnumConstructorCall2(irEnumConstructorCall, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
    public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry */
    public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, MatchingContext matchingContext) {
        visitEnumEntry2(irEnumEntry, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
    public void visitEnumEntry2(IrEnumEntry irEnumEntry, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression */
    public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, MatchingContext matchingContext) {
        visitErrorCallExpression2(irErrorCallExpression, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
    public void visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration */
    public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, MatchingContext matchingContext) {
        visitErrorDeclaration2(irErrorDeclaration, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
    public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression */
    public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, MatchingContext matchingContext) {
        visitErrorExpression2(irErrorExpression, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
    public void visitErrorExpression2(IrErrorExpression irErrorExpression, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression */
    public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, MatchingContext matchingContext) {
        visitExpression2(irExpression, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
    public void visitExpression2(IrExpression irExpression, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody */
    public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, MatchingContext matchingContext) {
        visitExpressionBody2(irExpressionBody, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
    public void visitExpressionBody2(IrExpressionBody irExpressionBody, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment */
    public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, MatchingContext matchingContext) {
        visitExternalPackageFragment2(irExternalPackageFragment, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
    public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField */
    public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, MatchingContext matchingContext) {
        visitField2(irField, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
    public void visitField2(IrField irField, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitField(this, irField, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess */
    public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, MatchingContext matchingContext) {
        visitFieldAccess2(irFieldAccessExpression, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
    public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile */
    public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, MatchingContext matchingContext) {
        visitFile2(irFile, matchingContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction */
    public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, MatchingContext matchingContext) {
        visitFunction2(irFunction, matchingContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess */
    public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, MatchingContext matchingContext) {
        visitFunctionAccess2(irFunctionAccessExpression, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
    public void visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression */
    public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, MatchingContext matchingContext) {
        visitFunctionExpression2(irFunctionExpression, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
    public void visitFunctionExpression2(IrFunctionExpression irFunctionExpression, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference */
    public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, MatchingContext matchingContext) {
        visitFunctionReference2(irFunctionReference, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
    public void visitFunctionReference2(IrFunctionReference irFunctionReference, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass */
    public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, MatchingContext matchingContext) {
        visitGetClass2(irGetClass, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
    public void visitGetClass2(IrGetClass irGetClass, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue */
    public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, MatchingContext matchingContext) {
        visitGetEnumValue2(irGetEnumValue, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
    public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField */
    public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, MatchingContext matchingContext) {
        visitGetField2(irGetField, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
    public void visitGetField2(IrGetField irGetField, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue */
    public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, MatchingContext matchingContext) {
        visitGetObjectValue2(irGetObjectValue, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
    public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue */
    public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, MatchingContext matchingContext) {
        visitGetValue2(irGetValue, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
    public void visitGetValue2(IrGetValue irGetValue, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall */
    public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, MatchingContext matchingContext) {
        visitInstanceInitializerCall2(irInstanceInitializerCall, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
    public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty */
    public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, MatchingContext matchingContext) {
        visitLocalDelegatedProperty2(irLocalDelegatedProperty, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
    public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
    public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, MatchingContext matchingContext) {
        visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
    public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop */
    public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, MatchingContext matchingContext) {
        visitLoop2(irLoop, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
    public void visitLoop2(IrLoop irLoop, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, MatchingContext matchingContext) {
        visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public void visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment */
    public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, MatchingContext matchingContext) {
        visitModuleFragment2(irModuleFragment, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
    public void visitModuleFragment2(IrModuleFragment irModuleFragment, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment */
    public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, MatchingContext matchingContext) {
        visitPackageFragment2(irPackageFragment, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
    public void visitPackageFragment2(IrPackageFragment irPackageFragment, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty */
    public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, MatchingContext matchingContext) {
        visitProperty2(irProperty, matchingContext);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference */
    public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, MatchingContext matchingContext) {
        visitPropertyReference2(irPropertyReference, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
    public void visitPropertyReference2(IrPropertyReference irPropertyReference, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference */
    public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, MatchingContext matchingContext) {
        visitRawFunctionReference2(irRawFunctionReference, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
    public void visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn */
    public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, MatchingContext matchingContext) {
        visitReturn2(irReturn, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
    public void visitReturn2(IrReturn irReturn, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript */
    public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, MatchingContext matchingContext) {
        visitScript2(irScript, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
    public void visitScript2(IrScript irScript, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitScript(this, irScript, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField */
    public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, MatchingContext matchingContext) {
        visitSetField2(irSetField, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
    public void visitSetField2(IrSetField irSetField, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue */
    public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, MatchingContext matchingContext) {
        visitSetValue2(irSetValue, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
    public void visitSetValue2(IrSetValue irSetValue, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction */
    public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, MatchingContext matchingContext) {
        visitSimpleFunction2(irSimpleFunction, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
    public void visitSimpleFunction2(IrSimpleFunction irSimpleFunction, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference */
    public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, MatchingContext matchingContext) {
        visitSingletonReference2(irGetSingletonValue, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
    public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement */
    public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, MatchingContext matchingContext) {
        visitSpreadElement2(irSpreadElement, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
    public void visitSpreadElement2(IrSpreadElement irSpreadElement, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation */
    public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, MatchingContext matchingContext) {
        visitStringConcatenation2(irStringConcatenation, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
    public void visitStringConcatenation2(IrStringConcatenation irStringConcatenation, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression */
    public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, MatchingContext matchingContext) {
        visitSuspendableExpression2(irSuspendableExpression, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
    public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint */
    public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, MatchingContext matchingContext) {
        visitSuspensionPoint2(irSuspensionPoint, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
    public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody */
    public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, MatchingContext matchingContext) {
        visitSyntheticBody2(irSyntheticBody, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
    public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow */
    public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, MatchingContext matchingContext) {
        visitThrow2(irThrow, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
    public void visitThrow2(IrThrow irThrow, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry */
    public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, MatchingContext matchingContext) {
        visitTry2(irTry, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
    public void visitTry2(IrTry irTry, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitTry(this, irTry, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias */
    public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, MatchingContext matchingContext) {
        visitTypeAlias2(irTypeAlias, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
    public void visitTypeAlias2(IrTypeAlias irTypeAlias, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator */
    public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, MatchingContext matchingContext) {
        visitTypeOperator2(irTypeOperatorCall, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
    public void visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter */
    public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, MatchingContext matchingContext) {
        visitTypeParameter2(irTypeParameter, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
    public void visitTypeParameter2(IrTypeParameter irTypeParameter, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess */
    public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, MatchingContext matchingContext) {
        visitValueAccess2(irValueAccessExpression, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
    public void visitValueAccess2(IrValueAccessExpression irValueAccessExpression, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter */
    public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, MatchingContext matchingContext) {
        visitValueParameter2(irValueParameter, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
    public void visitValueParameter2(IrValueParameter irValueParameter, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg */
    public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, MatchingContext matchingContext) {
        visitVararg2(irVararg, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
    public void visitVararg2(IrVararg irVararg, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable */
    public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, MatchingContext matchingContext) {
        visitVariable2(irVariable, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
    public void visitVariable2(IrVariable irVariable, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitVariable(this, irVariable, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen */
    public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, MatchingContext matchingContext) {
        visitWhen2(irWhen, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
    public void visitWhen2(IrWhen irWhen, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, matchingContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop */
    public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, MatchingContext matchingContext) {
        visitWhileLoop2(irWhileLoop, matchingContext);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
    public void visitWhileLoop2(IrWhileLoop irWhileLoop, MatchingContext matchingContext) {
        IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, matchingContext);
    }

    /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
    public void visitFile2(IrFile declaration, MatchingContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        IrElementVisitor.DefaultImpls.visitFile(this, declaration, data.withNewCurrentFile(declaration));
    }

    /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
    public void visitFunction2(IrFunction declaration, MatchingContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        if (declaration.getIsExpect()) {
            matchExpectCallable(declaration, AdditionalIrUtilsKt.getCallableId(declaration), data);
        }
    }

    /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
    public void visitProperty2(IrProperty declaration, MatchingContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        if (declaration.getIsExpect()) {
            matchExpectCallable(declaration, AdditionalIrUtilsKt.getCallableId(declaration), data);
        }
    }

    private final void matchExpectCallable(IrDeclarationWithName declaration, CallableId callableId, MatchingContext context) {
        IrSymbol symbol = declaration.getSymbol();
        List<IrSymbol> listEmptyList = context.getClassActualizationInfo().getActualTopLevels().get(callableId);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        matchExpectDeclaration(symbol, listEmptyList, context);
    }

    /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
    public void visitClass2(IrClass declaration, MatchingContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        if (declaration.getIsExpect()) {
            ClassId classIdOrFail = AdditionalIrUtilsKt.getClassIdOrFail(declaration);
            matchExpectDeclaration(declaration.getSymbol(), CollectionsKt.listOfNotNull(data.getClassActualizationInfo().getActualWithoutExpansion(classIdOrFail)), data);
        }
    }

    private final void matchExpectDeclaration(IrSymbol expectSymbol, List<? extends IrSymbol> actualSymbols, MatchingContext context) {
        AbstractExpectActualCompatibilityChecker.INSTANCE.matchSingleExpectTopLevelDeclarationAgainstPotentialActuals(expectSymbol, actualSymbols, context);
    }

    /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
    public void visitElement2(IrElement element, MatchingContext data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        element.acceptChildren(this, data);
    }

    /* JADX INFO: compiled from: ExpectActualCollector.kt */
    @Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fJ\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0006H\u0016J\u0018\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eH\u0016J.\u0010 \u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020!2\u001c\u0010\"\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030$\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0%0#H\u0016J\u000e\u0010&\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\u000eJ\u0014\u0010(\u001a\u00020\u0019*\u00020\n2\u0006\u0010\u001b\u001a\u00020\u0006H\u0002R\u0014\u0010\u000b\u001a\u00020\fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0012\u001a\u0004\u0018\u00010\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/ExpectActualLinkCollector$MatchingContext;", "Lorg/jetbrains/kotlin/backend/common/actualizer/IrExpectActualMatchingContext;", "typeSystemContext", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "diagnosticsReporter", "Lorg/jetbrains/kotlin/KtDiagnosticReporterWithImplicitIrBasedContext;", "expectActualTracker", "Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "classActualizationInfo", "Lorg/jetbrains/kotlin/backend/common/actualizer/ClassActualizationInfo;", "currentExpectFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;Ljava/util/Map;Lorg/jetbrains/kotlin/KtDiagnosticReporterWithImplicitIrBasedContext;Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;Lorg/jetbrains/kotlin/backend/common/actualizer/ClassActualizationInfo;Lorg/jetbrains/kotlin/ir/declarations/IrFile;)V", "getClassActualizationInfo$ir_backend_common", "()Lorg/jetbrains/kotlin/backend/common/actualizer/ClassActualizationInfo;", "currentExpectIoFile", "Ljava/io/File;", "getCurrentExpectIoFile", "()Ljava/io/File;", "currentExpectIoFile$delegate", "Lkotlin/Lazy;", "onMatchedCallables", "", "expectSymbol", "actualSymbol", "onMatchedClasses", "expectClassSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "actualClassSymbol", "onMismatchedMembersFromClassScope", "Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;", "actualSymbolsByIncompatibility", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "", "withNewCurrentFile", "newCurrentFile", "reportWithCurrentFile", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MatchingContext extends IrExpectActualMatchingContext {
        private final ClassActualizationInfo classActualizationInfo;
        private final IrFile currentExpectFile;

        /* JADX INFO: renamed from: currentExpectIoFile$delegate, reason: from kotlin metadata */
        private final Lazy currentExpectIoFile;
        private final Map<IrSymbol, IrSymbol> destination;
        private final KtDiagnosticReporterWithImplicitIrBasedContext diagnosticsReporter;
        private final ExpectActualTracker expectActualTracker;

        /* JADX INFO: renamed from: getClassActualizationInfo$ir_backend_common, reason: from getter */
        public final ClassActualizationInfo getClassActualizationInfo() {
            return this.classActualizationInfo;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MatchingContext(IrTypeSystemContext typeSystemContext, Map<IrSymbol, IrSymbol> destination, KtDiagnosticReporterWithImplicitIrBasedContext diagnosticsReporter, ExpectActualTracker expectActualTracker, ClassActualizationInfo classActualizationInfo, IrFile irFile) {
            super(typeSystemContext, classActualizationInfo.getActualClasses());
            Intrinsics.checkNotNullParameter(typeSystemContext, "typeSystemContext");
            Intrinsics.checkNotNullParameter(destination, "destination");
            Intrinsics.checkNotNullParameter(diagnosticsReporter, "diagnosticsReporter");
            Intrinsics.checkNotNullParameter(classActualizationInfo, "classActualizationInfo");
            this.destination = destination;
            this.diagnosticsReporter = diagnosticsReporter;
            this.expectActualTracker = expectActualTracker;
            this.classActualizationInfo = classActualizationInfo;
            this.currentExpectFile = irFile;
            this.currentExpectIoFile = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<File>() { // from class: org.jetbrains.kotlin.backend.common.actualizer.ExpectActualLinkCollector$MatchingContext$currentExpectIoFile$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final File invoke() {
                    IrFile irFile2 = this.this$0.currentExpectFile;
                    if (irFile2 != null) {
                        return ExpectActualCollectorKt.toIoFile(irFile2);
                    }
                    return null;
                }
            });
        }

        private final File getCurrentExpectIoFile() {
            return (File) this.currentExpectIoFile.getValue();
        }

        public final MatchingContext withNewCurrentFile(IrFile newCurrentFile) {
            Intrinsics.checkNotNullParameter(newCurrentFile, "newCurrentFile");
            return new MatchingContext(getTypeContext(), this.destination, this.diagnosticsReporter, this.expectActualTracker, this.classActualizationInfo, newCurrentFile);
        }

        @Override // org.jetbrains.kotlin.backend.common.actualizer.IrExpectActualMatchingContext
        public void onMatchedClasses(IrClassSymbol expectClassSymbol, IrClassSymbol actualClassSymbol) {
            Intrinsics.checkNotNullParameter(expectClassSymbol, "expectClassSymbol");
            Intrinsics.checkNotNullParameter(actualClassSymbol, "actualClassSymbol");
            this.destination.put(expectClassSymbol, actualClassSymbol);
            ExpectActualTracker expectActualTracker = this.expectActualTracker;
            if (expectActualTracker != null) {
                reportWithCurrentFile(expectActualTracker, actualClassSymbol);
            }
            IrActualizerUtilsKt.recordActualForExpectDeclaration(expectClassSymbol, actualClassSymbol, this.destination);
        }

        @Override // org.jetbrains.kotlin.backend.common.actualizer.IrExpectActualMatchingContext
        public void onMatchedCallables(IrSymbol expectSymbol, IrSymbol actualSymbol) {
            Intrinsics.checkNotNullParameter(expectSymbol, "expectSymbol");
            Intrinsics.checkNotNullParameter(actualSymbol, "actualSymbol");
            ExpectActualTracker expectActualTracker = this.expectActualTracker;
            if (expectActualTracker != null) {
                reportWithCurrentFile(expectActualTracker, actualSymbol);
            }
            IrActualizerUtilsKt.recordActualForExpectDeclaration(expectSymbol, actualSymbol, this.destination);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
        public void onMismatchedMembersFromClassScope(DeclarationSymbolMarker expectSymbol, Map<ExpectActualCompatibility.Incompatible<?>, ? extends List<? extends DeclarationSymbolMarker>> actualSymbolsByIncompatibility) {
            Intrinsics.checkNotNullParameter(expectSymbol, "expectSymbol");
            Intrinsics.checkNotNullParameter(actualSymbolsByIncompatibility, "actualSymbolsByIncompatibility");
            if (!(expectSymbol instanceof IrSymbol)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (actualSymbolsByIncompatibility.isEmpty()) {
                IrSymbol irSymbol = (IrSymbol) expectSymbol;
                if (!IrActualizerUtilsKt.containsOptionalExpectation(irSymbol.getOwner())) {
                    IrActualizerUtilsKt.reportMissingActual(this.diagnosticsReporter, irSymbol);
                }
            }
            for (Map.Entry<ExpectActualCompatibility.Incompatible<?>, ? extends List<? extends DeclarationSymbolMarker>> entry : actualSymbolsByIncompatibility.entrySet()) {
                ExpectActualCompatibility.Incompatible<?> key = entry.getKey();
                for (DeclarationSymbolMarker declarationSymbolMarker : entry.getValue()) {
                    if (!(declarationSymbolMarker instanceof IrSymbol)) {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                    IrActualizerUtilsKt.reportIncompatibleExpectActual(this.diagnosticsReporter, (IrSymbol) expectSymbol, (IrSymbol) declarationSymbolMarker, key);
                }
            }
        }

        private final void reportWithCurrentFile(ExpectActualTracker expectActualTracker, IrSymbol irSymbol) {
            if (this.currentExpectFile != null) {
                IrFile irFile = this.classActualizationInfo.getActualSymbolsToFile().get(irSymbol);
                File ioFile = irFile != null ? ExpectActualCollectorKt.toIoFile(irFile) : null;
                if (ioFile != null) {
                    File currentExpectIoFile = getCurrentExpectIoFile();
                    Intrinsics.checkNotNull(currentExpectIoFile);
                    expectActualTracker.report(currentExpectIoFile, ioFile);
                }
            }
        }
    }
}
