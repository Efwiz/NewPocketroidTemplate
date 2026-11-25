package org.jetbrains.kotlin.backend.common.lower.optimizations;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.BitSetUtilKt;
import org.jetbrains.kotlin.backend.common.ir.IrUtilsKt;
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
import org.jetbrains.kotlin.ir.declarations.IrReturnTarget;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
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
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
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
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: LivenessAnalysis.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u000eB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u00042\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\f0\u000bJ\u0012\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u0006*\u00020\u0005H\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/optimizations/LivenessAnalysis;", "", "()V", "run", "", "Lorg/jetbrains/kotlin/ir/IrElement;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "filter", "Lkotlin/Function1;", "", "getImmediateChildren", "LivenessAnalysisVisitor", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LivenessAnalysis {
    public static final LivenessAnalysis INSTANCE = new LivenessAnalysis();

    private LivenessAnalysis() {
    }

    public final Map<IrElement, List<IrVariable>> run(IrBody body, Function1<? super IrElement, Boolean> filter) {
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(filter, "filter");
        return new LivenessAnalysisVisitor(filter).run(body);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<IrElement> getImmediateChildren(IrElement irElement) {
        final ArrayList arrayList = new ArrayList();
        IrVisitorsKt.acceptChildrenVoid(irElement, new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.optimizations.LivenessAnalysis.getImmediateChildren.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitAnonymousInitializer */
            public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj) {
                visitAnonymousInitializer(irAnonymousInitializer, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer) {
                IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlock */
            public /* bridge */ /* synthetic */ Object visitBlock2(IrBlock irBlock, Object obj) {
                visitBlock(irBlock, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlock(IrBlock irBlock) {
                IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlock(IrBlock irBlock, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlockBody */
            public /* bridge */ /* synthetic */ Object visitBlockBody2(IrBlockBody irBlockBody, Object obj) {
                visitBlockBody(irBlockBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlockBody(IrBlockBody irBlockBody) {
                IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlockBody(IrBlockBody irBlockBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBody */
            public /* bridge */ /* synthetic */ Object visitBody2(IrBody irBody, Object obj) {
                visitBody(irBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBody(IrBody irBody) {
                IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBody(IrBody irBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBranch */
            public /* bridge */ /* synthetic */ Object visitBranch2(IrBranch irBranch, Object obj) {
                visitBranch(irBranch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBranch(IrBranch irBranch) {
                IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBranch(IrBranch irBranch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreak */
            public /* bridge */ /* synthetic */ Object visitBreak2(IrBreak irBreak, Object obj) {
                visitBreak(irBreak, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreak(IrBreak irBreak) {
                IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreak(IrBreak irBreak, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreakContinue */
            public /* bridge */ /* synthetic */ Object visitBreakContinue2(IrBreakContinue irBreakContinue, Object obj) {
                visitBreakContinue(irBreakContinue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreakContinue(IrBreakContinue irBreakContinue) {
                IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreakContinue(IrBreakContinue irBreakContinue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall */
            public /* bridge */ /* synthetic */ Object visitCall2(IrCall irCall, Object obj) {
                visitCall(irCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCall(IrCall irCall) {
                IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCall(IrCall irCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
                visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCallableReference(IrCallableReference<?> irCallableReference) {
                IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCatch */
            public /* bridge */ /* synthetic */ Object visitCatch2(IrCatch irCatch, Object obj) {
                visitCatch(irCatch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCatch(IrCatch irCatch) {
                IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCatch(IrCatch irCatch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClass */
            public /* bridge */ /* synthetic */ Object visitClass2(IrClass irClass, Object obj) {
                visitClass(irClass, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            /* JADX INFO: renamed from: visitClass */
            public void mo6112visitClass(IrClass irClass) {
                IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClass(IrClass irClass, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClassReference */
            public /* bridge */ /* synthetic */ Object visitClassReference2(IrClassReference irClassReference, Object obj) {
                visitClassReference(irClassReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClassReference(IrClassReference irClassReference) {
                IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClassReference(IrClassReference irClassReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitComposite */
            public /* bridge */ /* synthetic */ Object visitComposite2(IrComposite irComposite, Object obj) {
                visitComposite(irComposite, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitComposite(IrComposite irComposite) {
                IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitComposite(IrComposite irComposite, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
                visitConst((IrConst<?>) irConst, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConst(IrConst<?> irConst) {
                IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConst(IrConst<?> irConst, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantArray */
            public /* bridge */ /* synthetic */ Object visitConstantArray2(IrConstantArray irConstantArray, Object obj) {
                visitConstantArray(irConstantArray, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantArray(IrConstantArray irConstantArray) {
                IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantArray(IrConstantArray irConstantArray, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantObject */
            public /* bridge */ /* synthetic */ Object visitConstantObject2(IrConstantObject irConstantObject, Object obj) {
                visitConstantObject(irConstantObject, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantObject(IrConstantObject irConstantObject) {
                IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantObject(IrConstantObject irConstantObject, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantPrimitive */
            public /* bridge */ /* synthetic */ Object visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Object obj) {
                visitConstantPrimitive(irConstantPrimitive, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive) {
                IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantValue */
            public /* bridge */ /* synthetic */ Object visitConstantValue2(IrConstantValue irConstantValue, Object obj) {
                visitConstantValue(irConstantValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantValue(IrConstantValue irConstantValue) {
                IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantValue(IrConstantValue irConstantValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructor */
            public /* bridge */ /* synthetic */ Object visitConstructor2(IrConstructor irConstructor, Object obj) {
                visitConstructor(irConstructor, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructor(IrConstructor irConstructor) {
                IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructor(IrConstructor irConstructor, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructorCall */
            public /* bridge */ /* synthetic */ Object visitConstructorCall2(IrConstructorCall irConstructorCall, Object obj) {
                visitConstructorCall(irConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructorCall(IrConstructorCall irConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructorCall(IrConstructorCall irConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContainerExpression */
            public /* bridge */ /* synthetic */ Object visitContainerExpression2(IrContainerExpression irContainerExpression, Object obj) {
                visitContainerExpression(irContainerExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContainerExpression(IrContainerExpression irContainerExpression) {
                IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContainerExpression(IrContainerExpression irContainerExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContinue */
            public /* bridge */ /* synthetic */ Object visitContinue2(IrContinue irContinue, Object obj) {
                visitContinue(irContinue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContinue(IrContinue irContinue) {
                IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContinue(IrContinue irContinue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclaration */
            public /* bridge */ /* synthetic */ Object visitDeclaration2(IrDeclarationBase irDeclarationBase, Object obj) {
                visitDeclaration(irDeclarationBase, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclaration(IrDeclarationBase irDeclarationBase) {
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclaration(IrDeclarationBase irDeclarationBase, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclarationReference */
            public /* bridge */ /* synthetic */ Object visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Object obj) {
                visitDeclarationReference(irDeclarationReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclarationReference(IrDeclarationReference irDeclarationReference) {
                IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclarationReference(IrDeclarationReference irDeclarationReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDelegatingConstructorCall */
            public /* bridge */ /* synthetic */ Object visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Object obj) {
                visitDelegatingConstructorCall(irDelegatingConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDoWhileLoop */
            public /* bridge */ /* synthetic */ Object visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Object obj) {
                visitDoWhileLoop(irDoWhileLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop) {
                IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Object obj) {
                visitDynamicExpression(irDynamicExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicExpression(IrDynamicExpression irDynamicExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicExpression(IrDynamicExpression irDynamicExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicMemberExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Object obj) {
                visitDynamicMemberExpression(irDynamicMemberExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicOperatorExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Object obj) {
                visitDynamicOperatorExpression(irDynamicOperatorExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElement */
            public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement2, Object obj) {
                visitElement(irElement2, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElement(IrElement irElement2, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement2, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElseBranch */
            public /* bridge */ /* synthetic */ Object visitElseBranch2(IrElseBranch irElseBranch, Object obj) {
                visitElseBranch(irElseBranch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElseBranch(IrElseBranch irElseBranch) {
                IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElseBranch(IrElseBranch irElseBranch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumConstructorCall */
            public /* bridge */ /* synthetic */ Object visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Object obj) {
                visitEnumConstructorCall(irEnumConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumEntry */
            public /* bridge */ /* synthetic */ Object visitEnumEntry2(IrEnumEntry irEnumEntry, Object obj) {
                visitEnumEntry(irEnumEntry, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumEntry(IrEnumEntry irEnumEntry) {
                IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumEntry(IrEnumEntry irEnumEntry, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorCallExpression */
            public /* bridge */ /* synthetic */ Object visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Object obj) {
                visitErrorCallExpression(irErrorCallExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression) {
                IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorDeclaration */
            public /* bridge */ /* synthetic */ Object visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Object obj) {
                visitErrorDeclaration(irErrorDeclaration, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration) {
                IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorExpression */
            public /* bridge */ /* synthetic */ Object visitErrorExpression2(IrErrorExpression irErrorExpression, Object obj) {
                visitErrorExpression(irErrorExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorExpression(IrErrorExpression irErrorExpression) {
                IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorExpression(IrErrorExpression irErrorExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpression */
            public /* bridge */ /* synthetic */ Object visitExpression2(IrExpression irExpression, Object obj) {
                visitExpression(irExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpression(IrExpression irExpression) {
                IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpression(IrExpression irExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpressionBody */
            public /* bridge */ /* synthetic */ Object visitExpressionBody2(IrExpressionBody irExpressionBody, Object obj) {
                visitExpressionBody(irExpressionBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpressionBody(IrExpressionBody irExpressionBody) {
                IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpressionBody(IrExpressionBody irExpressionBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExternalPackageFragment */
            public /* bridge */ /* synthetic */ Object visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Object obj) {
                visitExternalPackageFragment(irExternalPackageFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment) {
                IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitField */
            public /* bridge */ /* synthetic */ Object visitField2(IrField irField, Object obj) {
                visitField(irField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitField(IrField irField) {
                IrElementVisitorVoid.DefaultImpls.visitField(this, irField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitField(IrField irField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitField(this, irField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFieldAccess */
            public /* bridge */ /* synthetic */ Object visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Object obj) {
                visitFieldAccess(irFieldAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFile */
            public /* bridge */ /* synthetic */ Object visitFile2(IrFile irFile, Object obj) {
                visitFile(irFile, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFile(IrFile irFile) {
                IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFile(IrFile irFile, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunction */
            public /* bridge */ /* synthetic */ Object visitFunction2(IrFunction irFunction, Object obj) {
                visitFunction(irFunction, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunction(IrFunction irFunction) {
                IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunction(IrFunction irFunction, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionAccess */
            public /* bridge */ /* synthetic */ Object visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Object obj) {
                visitFunctionAccess(irFunctionAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionExpression */
            public /* bridge */ /* synthetic */ Object visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Object obj) {
                visitFunctionExpression(irFunctionExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionExpression(IrFunctionExpression irFunctionExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionExpression(IrFunctionExpression irFunctionExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionReference */
            public /* bridge */ /* synthetic */ Object visitFunctionReference2(IrFunctionReference irFunctionReference, Object obj) {
                visitFunctionReference(irFunctionReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionReference(IrFunctionReference irFunctionReference) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionReference(IrFunctionReference irFunctionReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetClass */
            public /* bridge */ /* synthetic */ Object visitGetClass2(IrGetClass irGetClass, Object obj) {
                visitGetClass(irGetClass, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetClass(IrGetClass irGetClass) {
                IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetClass(IrGetClass irGetClass, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetEnumValue */
            public /* bridge */ /* synthetic */ Object visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Object obj) {
                visitGetEnumValue(irGetEnumValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetEnumValue(IrGetEnumValue irGetEnumValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetEnumValue(IrGetEnumValue irGetEnumValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetField */
            public /* bridge */ /* synthetic */ Object visitGetField2(IrGetField irGetField, Object obj) {
                visitGetField(irGetField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetField(IrGetField irGetField) {
                IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetField(IrGetField irGetField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetObjectValue */
            public /* bridge */ /* synthetic */ Object visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Object obj) {
                visitGetObjectValue(irGetObjectValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetObjectValue(IrGetObjectValue irGetObjectValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetObjectValue(IrGetObjectValue irGetObjectValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetValue */
            public /* bridge */ /* synthetic */ Object visitGetValue2(IrGetValue irGetValue, Object obj) {
                visitGetValue(irGetValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetValue(IrGetValue irGetValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetValue(IrGetValue irGetValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitInstanceInitializerCall */
            public /* bridge */ /* synthetic */ Object visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Object obj) {
                visitInstanceInitializerCall(irInstanceInitializerCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall) {
                IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedProperty */
            public /* bridge */ /* synthetic */ Object visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Object obj) {
                visitLocalDelegatedProperty(irLocalDelegatedProperty, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
            public /* bridge */ /* synthetic */ Object visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Object obj) {
                visitLocalDelegatedPropertyReference(irLocalDelegatedPropertyReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLoop */
            public /* bridge */ /* synthetic */ Object visitLoop2(IrLoop irLoop, Object obj) {
                visitLoop(irLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLoop(IrLoop irLoop) {
                IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLoop(IrLoop irLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitModuleFragment */
            public /* bridge */ /* synthetic */ Object visitModuleFragment2(IrModuleFragment irModuleFragment, Object obj) {
                visitModuleFragment(irModuleFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitModuleFragment(IrModuleFragment irModuleFragment) {
                IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitModuleFragment(IrModuleFragment irModuleFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPackageFragment */
            public /* bridge */ /* synthetic */ Object visitPackageFragment2(IrPackageFragment irPackageFragment, Object obj) {
                visitPackageFragment(irPackageFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPackageFragment(IrPackageFragment irPackageFragment) {
                IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPackageFragment(IrPackageFragment irPackageFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitProperty */
            public /* bridge */ /* synthetic */ Object visitProperty2(IrProperty irProperty, Object obj) {
                visitProperty(irProperty, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitProperty(IrProperty irProperty) {
                IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitProperty(IrProperty irProperty, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPropertyReference */
            public /* bridge */ /* synthetic */ Object visitPropertyReference2(IrPropertyReference irPropertyReference, Object obj) {
                visitPropertyReference(irPropertyReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPropertyReference(IrPropertyReference irPropertyReference) {
                IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPropertyReference(IrPropertyReference irPropertyReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitRawFunctionReference */
            public /* bridge */ /* synthetic */ Object visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Object obj) {
                visitRawFunctionReference(irRawFunctionReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference) {
                IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitReturn */
            public /* bridge */ /* synthetic */ Object visitReturn2(IrReturn irReturn, Object obj) {
                visitReturn(irReturn, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitReturn(IrReturn irReturn) {
                IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitReturn(IrReturn irReturn, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitScript */
            public /* bridge */ /* synthetic */ Object visitScript2(IrScript irScript, Object obj) {
                visitScript(irScript, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitScript(IrScript irScript) {
                IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitScript(IrScript irScript, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetField */
            public /* bridge */ /* synthetic */ Object visitSetField2(IrSetField irSetField, Object obj) {
                visitSetField(irSetField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetField(IrSetField irSetField) {
                IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetField(IrSetField irSetField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetValue */
            public /* bridge */ /* synthetic */ Object visitSetValue2(IrSetValue irSetValue, Object obj) {
                visitSetValue(irSetValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetValue(IrSetValue irSetValue) {
                IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetValue(IrSetValue irSetValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSimpleFunction */
            public /* bridge */ /* synthetic */ Object visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Object obj) {
                visitSimpleFunction(irSimpleFunction, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSimpleFunction(IrSimpleFunction irSimpleFunction) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSimpleFunction(IrSimpleFunction irSimpleFunction, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSingletonReference */
            public /* bridge */ /* synthetic */ Object visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Object obj) {
                visitSingletonReference(irGetSingletonValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue) {
                IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSpreadElement */
            public /* bridge */ /* synthetic */ Object visitSpreadElement2(IrSpreadElement irSpreadElement, Object obj) {
                visitSpreadElement(irSpreadElement, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSpreadElement(IrSpreadElement irSpreadElement) {
                IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSpreadElement(IrSpreadElement irSpreadElement, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitStringConcatenation */
            public /* bridge */ /* synthetic */ Object visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Object obj) {
                visitStringConcatenation(irStringConcatenation, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitStringConcatenation(IrStringConcatenation irStringConcatenation) {
                IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitStringConcatenation(IrStringConcatenation irStringConcatenation, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspendableExpression */
            public /* bridge */ /* synthetic */ Object visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Object obj) {
                visitSuspendableExpression(irSuspendableExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression) {
                IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspensionPoint */
            public /* bridge */ /* synthetic */ Object visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Object obj) {
                visitSuspensionPoint(irSuspensionPoint, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint) {
                IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSyntheticBody */
            public /* bridge */ /* synthetic */ Object visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Object obj) {
                visitSyntheticBody(irSyntheticBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSyntheticBody(IrSyntheticBody irSyntheticBody) {
                IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSyntheticBody(IrSyntheticBody irSyntheticBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitThrow */
            public /* bridge */ /* synthetic */ Object visitThrow2(IrThrow irThrow, Object obj) {
                visitThrow(irThrow, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitThrow(IrThrow irThrow) {
                IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitThrow(IrThrow irThrow, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTry */
            public /* bridge */ /* synthetic */ Object visitTry2(IrTry irTry, Object obj) {
                visitTry(irTry, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTry(IrTry irTry) {
                IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTry(IrTry irTry, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeAlias */
            public /* bridge */ /* synthetic */ Object visitTypeAlias2(IrTypeAlias irTypeAlias, Object obj) {
                visitTypeAlias(irTypeAlias, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeAlias(IrTypeAlias irTypeAlias) {
                IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeAlias(IrTypeAlias irTypeAlias, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeOperator */
            public /* bridge */ /* synthetic */ Object visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Object obj) {
                visitTypeOperator(irTypeOperatorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall) {
                IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeParameter */
            public /* bridge */ /* synthetic */ Object visitTypeParameter2(IrTypeParameter irTypeParameter, Object obj) {
                visitTypeParameter(irTypeParameter, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeParameter(IrTypeParameter irTypeParameter) {
                IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeParameter(IrTypeParameter irTypeParameter, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueAccess */
            public /* bridge */ /* synthetic */ Object visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Object obj) {
                visitValueAccess(irValueAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueAccess(IrValueAccessExpression irValueAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueAccess(IrValueAccessExpression irValueAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueParameter */
            public /* bridge */ /* synthetic */ Object visitValueParameter2(IrValueParameter irValueParameter, Object obj) {
                visitValueParameter(irValueParameter, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueParameter(IrValueParameter irValueParameter) {
                IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueParameter(IrValueParameter irValueParameter, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVararg */
            public /* bridge */ /* synthetic */ Object visitVararg2(IrVararg irVararg, Object obj) {
                visitVararg(irVararg, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVararg(IrVararg irVararg) {
                IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVararg(IrVararg irVararg, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVariable */
            public /* bridge */ /* synthetic */ Object visitVariable2(IrVariable irVariable, Object obj) {
                visitVariable(irVariable, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVariable(IrVariable irVariable) {
                IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVariable(IrVariable irVariable, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhen */
            public /* bridge */ /* synthetic */ Object visitWhen2(IrWhen irWhen, Object obj) {
                visitWhen(irWhen, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhen(IrWhen irWhen) {
                IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhen(IrWhen irWhen, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhileLoop */
            public /* bridge */ /* synthetic */ Object visitWhileLoop2(IrWhileLoop irWhileLoop, Object obj) {
                visitWhileLoop(irWhileLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhileLoop(IrWhileLoop irWhileLoop) {
                IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhileLoop(IrWhileLoop irWhileLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            /* JADX INFO: renamed from: visitElement */
            public void mo6335visitElement(IrElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                arrayList.add(element);
            }
        });
        return arrayList;
    }

    /* JADX INFO: compiled from: LivenessAnalysis.kt */
    @Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010%\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001B\u0019\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0013H\u0002J\u0018\u0010\u0019\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u0002H\u0002J \u0010\u001c\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u001e0\u001d2\u0006\u0010\u001f\u001a\u00020 J6\u0010!\u001a\u00020\u0002\"\b\b\u0000\u0010\"*\u00020\u00052\u0006\u0010#\u001a\u0002H\"2\u0006\u0010$\u001a\u00020\u00022\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00020&H\u0082\b¢\u0006\u0002\u0010'J\u0018\u0010(\u001a\u00020\u00022\u0006\u0010)\u001a\u00020*2\u0006\u0010\u001b\u001a\u00020\u0002H\u0016J\u0018\u0010+\u001a\u00020\u00022\u0006\u0010,\u001a\u00020-2\u0006\u0010\u001b\u001a\u00020\u0002H\u0016J\u0018\u0010.\u001a\u00020\u00022\u0006\u0010)\u001a\u00020/2\u0006\u0010\u001b\u001a\u00020\u0002H\u0016J\u0018\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u0010\u001b\u001a\u00020\u0002H\u0016J\u0018\u00104\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u0002052\u0006\u0010\u001b\u001a\u00020\u0002H\u0016J\u0018\u00106\u001a\u00020\u00022\u0006\u0010#\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u0002H\u0016J\u0018\u00107\u001a\u00020\u00022\u0006\u0010,\u001a\u0002082\u0006\u0010\u001b\u001a\u00020\u0002H\u0016J\u0018\u00109\u001a\u00020\u00022\u0006\u0010,\u001a\u00020:2\u0006\u0010\u001b\u001a\u00020\u0002H\u0016J\u0018\u0010;\u001a\u00020\u00022\u0006\u0010,\u001a\u00020<2\u0006\u0010\u001b\u001a\u00020\u0002H\u0016J\u0018\u0010=\u001a\u00020\u00022\u0006\u0010,\u001a\u00020>2\u0006\u0010\u001b\u001a\u00020\u0002H\u0016J\u0018\u0010?\u001a\u00020\u00022\u0006\u0010@\u001a\u00020A2\u0006\u0010\u001b\u001a\u00020\u0002H\u0016J\u0018\u0010B\u001a\u00020\u00022\u0006\u00102\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u0002H\u0016J\u0018\u0010C\u001a\u00020\u00022\u0006\u0010,\u001a\u00020D2\u0006\u0010\u001b\u001a\u00020\u0002H\u0016J\u0018\u0010E\u001a\u00020\u00022\u0006\u0010\u001a\u001a\u00020F2\u0006\u0010\u001b\u001a\u00020\u0002H\u0016J\f\u0010G\u001a\u00020H*\u00020\u0002H\u0002R\u000e\u0010\b\u001a\u00020\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00130\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006I"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/optimizations/LivenessAnalysis$LivenessAnalysisVisitor;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "Ljava/util/BitSet;", "filter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/IrElement;", "", "(Lkotlin/jvm/functions/Function1;)V", "catchesLV", "getFilter", "()Lkotlin/jvm/functions/Function1;", "filteredElementEndsLV", "", "loopEndsLV", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "loopStartsLV", "returnableBlockEndsLV", "Lorg/jetbrains/kotlin/ir/expressions/IrReturnableBlock;", "variableIds", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "", "variables", "", "getVariableId", "variable", "handleLoop", "loop", "data", "run", "", "", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "saveAndCompute", ExifInterface.GPS_DIRECTION_TRUE, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "liveVariables", "compute", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/ir/IrElement;Ljava/util/BitSet;Lkotlin/jvm/functions/Function0;)Ljava/util/BitSet;", "visitBreak", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "visitContainerExpression", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "visitDeclaration", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationBase;", "visitDoWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "visitElement", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitVariable", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", ConfigConstants.CONFIG_KEY_FORMAT, "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class LivenessAnalysisVisitor implements IrElementVisitor<BitSet, BitSet> {
        private BitSet catchesLV;
        private final Function1<IrElement, Boolean> filter;
        private final Map<IrElement, BitSet> filteredElementEndsLV;
        private final Map<IrLoop, BitSet> loopEndsLV;
        private final Map<IrLoop, BitSet> loopStartsLV;
        private final Map<IrReturnableBlock, BitSet> returnableBlockEndsLV;
        private final Map<IrVariable, Integer> variableIds;
        private final List<IrVariable> variables;

        /* JADX WARN: Multi-variable type inference failed */
        public LivenessAnalysisVisitor(Function1<? super IrElement, Boolean> filter) {
            Intrinsics.checkNotNullParameter(filter, "filter");
            this.filter = filter;
            this.variables = new ArrayList();
            this.variableIds = new LinkedHashMap();
            this.filteredElementEndsLV = new LinkedHashMap();
            this.returnableBlockEndsLV = new LinkedHashMap();
            this.loopEndsLV = new LinkedHashMap();
            this.loopStartsLV = new LinkedHashMap();
            this.catchesLV = new BitSet();
        }

        public final Function1<IrElement, Boolean> getFilter() {
            return this.filter;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
        public BitSet visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
        public BitSet visitBlock2(IrBlock irBlock, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
        public BitSet visitBlockBody2(IrBlockBody irBlockBody, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
        public BitSet visitBody2(IrBody irBody, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitBody(this, irBody, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
        public BitSet visitBranch2(IrBranch irBranch, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
        public BitSet visitBreakContinue2(IrBreakContinue irBreakContinue, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
        public BitSet visitCall2(IrCall irCall, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitCall(this, irCall, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ BitSet visitCallableReference(IrCallableReference irCallableReference, BitSet bitSet) {
            return visitCallableReference2((IrCallableReference<?>) irCallableReference, bitSet);
        }

        /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
        public BitSet visitCallableReference2(IrCallableReference<?> irCallableReference, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
        public BitSet visitCatch2(IrCatch irCatch, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
        public BitSet visitClass2(IrClass irClass, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitClass(this, irClass, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
        public BitSet visitClassReference2(IrClassReference irClassReference, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
        public BitSet visitComposite2(IrComposite irComposite, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ BitSet visitConst(IrConst irConst, BitSet bitSet) {
            return visitConst2((IrConst<?>) irConst, bitSet);
        }

        /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
        public BitSet visitConst2(IrConst<?> irConst, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitConst(this, irConst, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
        public BitSet visitConstantArray2(IrConstantArray irConstantArray, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
        public BitSet visitConstantObject2(IrConstantObject irConstantObject, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
        public BitSet visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
        public BitSet visitConstantValue2(IrConstantValue irConstantValue, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
        public BitSet visitConstructor2(IrConstructor irConstructor, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
        public BitSet visitConstructorCall2(IrConstructorCall irConstructorCall, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
        public BitSet visitDeclarationReference2(IrDeclarationReference irDeclarationReference, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
        public BitSet visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
        public BitSet visitDynamicExpression2(IrDynamicExpression irDynamicExpression, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
        public BitSet visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
        public BitSet visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
        public BitSet visitElseBranch2(IrElseBranch irElseBranch, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
        public BitSet visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
        public BitSet visitEnumEntry2(IrEnumEntry irEnumEntry, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
        public BitSet visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
        public BitSet visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
        public BitSet visitErrorExpression2(IrErrorExpression irErrorExpression, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
        public BitSet visitExpression2(IrExpression irExpression, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
        public BitSet visitExpressionBody2(IrExpressionBody irExpressionBody, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
        public BitSet visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
        public BitSet visitField2(IrField irField, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitField(this, irField, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
        public BitSet visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
        public BitSet visitFile2(IrFile irFile, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitFile(this, irFile, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
        public BitSet visitFunction2(IrFunction irFunction, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
        public BitSet visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
        public BitSet visitFunctionExpression2(IrFunctionExpression irFunctionExpression, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
        public BitSet visitFunctionReference2(IrFunctionReference irFunctionReference, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
        public BitSet visitGetClass2(IrGetClass irGetClass, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
        public BitSet visitGetEnumValue2(IrGetEnumValue irGetEnumValue, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
        public BitSet visitGetField2(IrGetField irGetField, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
        public BitSet visitGetObjectValue2(IrGetObjectValue irGetObjectValue, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
        public BitSet visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
        public BitSet visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
        public BitSet visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
        public BitSet visitLoop2(IrLoop irLoop, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ BitSet visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, BitSet bitSet) {
            return visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, bitSet);
        }

        /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
        public BitSet visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
        public BitSet visitModuleFragment2(IrModuleFragment irModuleFragment, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
        public BitSet visitPackageFragment2(IrPackageFragment irPackageFragment, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
        public BitSet visitProperty2(IrProperty irProperty, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
        public BitSet visitPropertyReference2(IrPropertyReference irPropertyReference, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
        public BitSet visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
        public BitSet visitScript2(IrScript irScript, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitScript(this, irScript, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
        public BitSet visitSetField2(IrSetField irSetField, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
        public BitSet visitSimpleFunction2(IrSimpleFunction irSimpleFunction, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
        public BitSet visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
        public BitSet visitSpreadElement2(IrSpreadElement irSpreadElement, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
        public BitSet visitStringConcatenation2(IrStringConcatenation irStringConcatenation, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
        public BitSet visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
        public BitSet visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
        public BitSet visitSyntheticBody2(IrSyntheticBody irSyntheticBody, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
        public BitSet visitTypeAlias2(IrTypeAlias irTypeAlias, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
        public BitSet visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
        public BitSet visitTypeParameter2(IrTypeParameter irTypeParameter, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
        public BitSet visitValueAccess2(IrValueAccessExpression irValueAccessExpression, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
        public BitSet visitValueParameter2(IrValueParameter irValueParameter, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
        public BitSet visitVararg2(IrVararg irVararg, BitSet bitSet) {
            return (BitSet) IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, bitSet);
        }

        public final Map<IrElement, List<IrVariable>> run(IrBody body) {
            Intrinsics.checkNotNullParameter(body, "body");
            body.accept(this, new BitSet());
            Map<IrElement, BitSet> map = this.filteredElementEndsLV;
            LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
            Iterator<T> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                BitSet bitSet = (BitSet) entry.getValue();
                List listCreateListBuilder = CollectionsKt.createListBuilder();
                int iNextSetBit = -1;
                while (true) {
                    iNextSetBit = bitSet.nextSetBit(iNextSetBit + 1);
                    if (iNextSetBit >= 0) {
                        listCreateListBuilder.add(this.variables.get(iNextSetBit));
                    }
                }
                linkedHashMap.put(key, CollectionsKt.build(listCreateListBuilder));
            }
            return linkedHashMap;
        }

        private final int getVariableId(IrVariable variable) {
            Map<IrVariable, Integer> map = this.variableIds;
            Integer numValueOf = map.get(variable);
            if (numValueOf == null) {
                this.variables.add(variable);
                numValueOf = Integer.valueOf(CollectionsKt.getLastIndex(this.variables));
                map.put(variable, numValueOf);
            }
            return numValueOf.intValue();
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
        public Void visitDeclaration2(IrDeclarationBase declaration, BitSet data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            throw new IllegalStateException("Local declarations should've been popped out by this point".toString());
        }

        private final BitSet handleLoop(IrLoop loop, BitSet data) {
            this.loopEndsLV.put(loop, data);
            LivenessAnalysisVisitor livenessAnalysisVisitor = this;
            BitSet bitSet = (BitSet) loop.getCondition().accept(livenessAnalysisVisitor, data);
            IrExpression body = loop.getBody();
            if (body == null) {
                return bitSet;
            }
            BitSet bitSet2 = new BitSet();
            while (true) {
                this.loopStartsLV.put(loop, bitSet);
                BitSet bitSet3 = (BitSet) body.accept(livenessAnalysisVisitor, bitSet);
                bitSet2.or(bitSet3);
                BitSet bitSet4 = (BitSet) loop.getCondition().accept(livenessAnalysisVisitor, bitSet3);
                if (Intrinsics.areEqual(bitSet4, bitSet)) {
                    return bitSet2;
                }
                bitSet = bitSet4;
            }
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
        public BitSet visitElement2(IrElement element, BitSet data) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(data, "data");
            if (this.filter.invoke(element).booleanValue()) {
                Map<IrElement, BitSet> map = this.filteredElementEndsLV;
                BitSet bitSet = map.get(element);
                if (bitSet == null) {
                    bitSet = new BitSet();
                    map.put(element, bitSet);
                }
                BitSet bitSet2 = bitSet;
                bitSet2.or(data);
                bitSet2.or(this.catchesLV);
            }
            List immediateChildren = LivenessAnalysis.INSTANCE.getImmediateChildren(element);
            int size = immediateChildren.size();
            while (true) {
                size--;
                if (-1 >= size) {
                    return data;
                }
                data = (BitSet) ((IrElement) immediateChildren.get(size)).accept(this, data);
            }
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
        public BitSet visitGetValue2(IrGetValue expression, BitSet data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            IrGetValue irGetValue = expression;
            if (this.filter.invoke(irGetValue).booleanValue()) {
                Map<IrElement, BitSet> map = this.filteredElementEndsLV;
                BitSet bitSet = map.get(irGetValue);
                if (bitSet == null) {
                    bitSet = new BitSet();
                    map.put(irGetValue, bitSet);
                }
                BitSet bitSet2 = bitSet;
                bitSet2.or(data);
                bitSet2.or(this.catchesLV);
            }
            IrValueDeclaration owner = expression.getSymbol().getOwner();
            IrVariable irVariable = owner instanceof IrVariable ? (IrVariable) owner : null;
            return irVariable == null ? data : LivenessAnalysisKt.withBit(data, getVariableId(irVariable));
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
        public BitSet visitVariable2(IrVariable declaration, BitSet data) {
            BitSet bitSet;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            IrVariable irVariable = declaration;
            if (this.filter.invoke(irVariable).booleanValue()) {
                Map<IrElement, BitSet> map = this.filteredElementEndsLV;
                BitSet bitSet2 = map.get(irVariable);
                if (bitSet2 == null) {
                    bitSet2 = new BitSet();
                    map.put(irVariable, bitSet2);
                }
                BitSet bitSet3 = bitSet2;
                bitSet3.or(data);
                bitSet3.or(this.catchesLV);
            }
            int variableId = getVariableId(declaration);
            BitSet bitSetWithOutBit = LivenessAnalysisKt.withOutBit(data, variableId);
            IrExpression initializer = declaration.getInitializer();
            if (initializer != null && (bitSet = (BitSet) initializer.accept(this, bitSetWithOutBit)) != null) {
                bitSetWithOutBit = bitSet;
            }
            if (bitSetWithOutBit.get(variableId)) {
                throw new IllegalArgumentException(("Use of uninitialized variable " + RenderIrElementKt.render$default(irVariable, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            return bitSetWithOutBit;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
        public BitSet visitSetValue2(IrSetValue expression, BitSet data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            IrSetValue irSetValue = expression;
            if (this.filter.invoke(irSetValue).booleanValue()) {
                Map<IrElement, BitSet> map = this.filteredElementEndsLV;
                BitSet bitSet = map.get(irSetValue);
                if (bitSet == null) {
                    bitSet = new BitSet();
                    map.put(irSetValue, bitSet);
                }
                BitSet bitSet2 = bitSet;
                bitSet2.or(data);
                bitSet2.or(this.catchesLV);
            }
            IrValueDeclaration owner = expression.getSymbol().getOwner();
            IrVariable irVariable = owner instanceof IrVariable ? (IrVariable) owner : null;
            if (irVariable == null) {
                throw new IllegalStateException(("Unexpected parameter rewrite: " + RenderIrElementKt.render$default(irSetValue, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            return (BitSet) expression.getValue().accept(this, LivenessAnalysisKt.withOutBit(data, getVariableId(irVariable)));
        }

        /* JADX WARN: Code duplicated, block: B:19:0x007d  */
        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
        public BitSet visitReturn2(IrReturn expression, BitSet data) {
            BitSet bitSet;
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            IrReturn irReturn = expression;
            if (this.filter.invoke(irReturn).booleanValue()) {
                Map<IrElement, BitSet> map = this.filteredElementEndsLV;
                BitSet bitSet2 = map.get(irReturn);
                if (bitSet2 == null) {
                    bitSet2 = new BitSet();
                    map.put(irReturn, bitSet2);
                }
                BitSet bitSet3 = bitSet2;
                bitSet3.or(data);
                bitSet3.or(this.catchesLV);
            }
            IrReturnTarget owner = expression.getReturnTargetSymbol().getOwner();
            IrReturnableBlock irReturnableBlock = owner instanceof IrReturnableBlock ? (IrReturnableBlock) owner : null;
            if (irReturnableBlock != null) {
                bitSet = this.returnableBlockEndsLV.get(irReturnableBlock);
                if (bitSet == null) {
                    throw new IllegalStateException(("Unknown return target " + RenderIrElementKt.render$default(expression.getReturnTargetSymbol().getOwner(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                }
                if (bitSet == null) {
                    bitSet = new BitSet();
                }
            } else {
                bitSet = new BitSet();
            }
            return (BitSet) expression.getValue().accept(this, bitSet);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
        public BitSet visitContainerExpression2(IrContainerExpression expression, BitSet data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            IrContainerExpression irContainerExpression = expression;
            if (this.filter.invoke(irContainerExpression).booleanValue()) {
                Map<IrElement, BitSet> map = this.filteredElementEndsLV;
                BitSet bitSet = map.get(irContainerExpression);
                if (bitSet == null) {
                    bitSet = new BitSet();
                    map.put(irContainerExpression, bitSet);
                }
                BitSet bitSet2 = bitSet;
                bitSet2.or(data);
                bitSet2.or(this.catchesLV);
            }
            IrReturnableBlock irReturnableBlock = expression instanceof IrReturnableBlock ? (IrReturnableBlock) expression : null;
            if (irReturnableBlock != null) {
                this.returnableBlockEndsLV.put(irReturnableBlock, data);
            }
            return visitElement2((IrElement) irContainerExpression, data);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
        public BitSet visitWhen2(IrWhen expression, BitSet data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            IrWhen irWhen = expression;
            if (this.filter.invoke(irWhen).booleanValue()) {
                Map<IrElement, BitSet> map = this.filteredElementEndsLV;
                BitSet bitSet = map.get(irWhen);
                if (bitSet == null) {
                    bitSet = new BitSet();
                    map.put(irWhen, bitSet);
                }
                BitSet bitSet2 = bitSet;
                bitSet2.or(data);
                bitSet2.or(this.catchesLV);
            }
            BitSet bitSet3 = IrUtilsKt.isUnconditional((IrBranch) CollectionsKt.last((List) expression.getBranches())) ? new BitSet() : data;
            int size = expression.getBranches().size();
            while (true) {
                size--;
                if (-1 >= size) {
                    return bitSet3;
                }
                IrBranch irBranch = expression.getBranches().get(size);
                BitSet bitSetCopy = BitSetUtilKt.copy(bitSet3);
                LivenessAnalysisVisitor livenessAnalysisVisitor = this;
                bitSetCopy.or((BitSet) irBranch.getResult().accept(livenessAnalysisVisitor, data));
                bitSet3 = (BitSet) irBranch.getCondition().accept(livenessAnalysisVisitor, bitSetCopy);
            }
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
        public BitSet visitThrow2(IrThrow expression, BitSet data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            IrThrow irThrow = expression;
            if (this.filter.invoke(irThrow).booleanValue()) {
                Map<IrElement, BitSet> map = this.filteredElementEndsLV;
                BitSet bitSet = map.get(irThrow);
                if (bitSet == null) {
                    bitSet = new BitSet();
                    map.put(irThrow, bitSet);
                }
                BitSet bitSet2 = bitSet;
                bitSet2.or(data);
                bitSet2.or(this.catchesLV);
            }
            return (BitSet) expression.getValue().accept(this, this.catchesLV);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
        public BitSet visitTry2(IrTry aTry, BitSet data) {
            Intrinsics.checkNotNullParameter(aTry, "aTry");
            Intrinsics.checkNotNullParameter(data, "data");
            IrTry irTry = aTry;
            if (this.filter.invoke(irTry).booleanValue()) {
                Map<IrElement, BitSet> map = this.filteredElementEndsLV;
                BitSet bitSet = map.get(irTry);
                if (bitSet == null) {
                    bitSet = new BitSet();
                    map.put(irTry, bitSet);
                }
                BitSet bitSet2 = bitSet;
                bitSet2.or(data);
                bitSet2.or(this.catchesLV);
            }
            if (aTry.getFinallyExpression() != null) {
                throw new IllegalArgumentException("All finally clauses should've been lowered".toString());
            }
            BitSet bitSet3 = new BitSet();
            Iterator<IrCatch> it = aTry.getCatches().iterator();
            while (it.hasNext()) {
                bitSet3.or((BitSet) it.next().accept(this, data));
            }
            BitSet bitSet4 = this.catchesLV;
            this.catchesLV = bitSet3;
            bitSet3.or((BitSet) aTry.getTryResult().accept(this, data));
            this.catchesLV = bitSet4;
            return bitSet3;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
        public BitSet visitBreak2(IrBreak jump, BitSet data) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            Intrinsics.checkNotNullParameter(data, "data");
            IrBreak irBreak = jump;
            if (this.filter.invoke(irBreak).booleanValue()) {
                Map<IrElement, BitSet> map = this.filteredElementEndsLV;
                BitSet bitSet = map.get(irBreak);
                if (bitSet == null) {
                    bitSet = new BitSet();
                    map.put(irBreak, bitSet);
                }
                BitSet bitSet2 = bitSet;
                bitSet2.or(data);
                bitSet2.or(this.catchesLV);
            }
            BitSet bitSet3 = this.loopEndsLV.get(jump.getLoop());
            if (bitSet3 != null) {
                return bitSet3;
            }
            throw new IllegalStateException(("Break from an unknown loop " + RenderIrElementKt.render$default(jump.getLoop(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
        public BitSet visitContinue2(IrContinue jump, BitSet data) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            Intrinsics.checkNotNullParameter(data, "data");
            IrContinue irContinue = jump;
            if (this.filter.invoke(irContinue).booleanValue()) {
                Map<IrElement, BitSet> map = this.filteredElementEndsLV;
                BitSet bitSet = map.get(irContinue);
                if (bitSet == null) {
                    bitSet = new BitSet();
                    map.put(irContinue, bitSet);
                }
                BitSet bitSet2 = bitSet;
                bitSet2.or(data);
                bitSet2.or(this.catchesLV);
            }
            BitSet bitSet3 = this.loopStartsLV.get(jump.getLoop());
            if (bitSet3 != null) {
                return bitSet3;
            }
            throw new IllegalStateException(("Continue to an unknown loop " + RenderIrElementKt.render$default(jump.getLoop(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
        public BitSet visitDoWhileLoop2(IrDoWhileLoop loop, BitSet data) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            Intrinsics.checkNotNullParameter(data, "data");
            IrDoWhileLoop irDoWhileLoop = loop;
            if (this.filter.invoke(irDoWhileLoop).booleanValue()) {
                Map<IrElement, BitSet> map = this.filteredElementEndsLV;
                BitSet bitSet = map.get(irDoWhileLoop);
                if (bitSet == null) {
                    bitSet = new BitSet();
                    map.put(irDoWhileLoop, bitSet);
                }
                BitSet bitSet2 = bitSet;
                bitSet2.or(data);
                bitSet2.or(this.catchesLV);
            }
            return handleLoop(loop, data);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
        public BitSet visitWhileLoop2(IrWhileLoop loop, BitSet data) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            Intrinsics.checkNotNullParameter(data, "data");
            IrWhileLoop irWhileLoop = loop;
            if (this.filter.invoke(irWhileLoop).booleanValue()) {
                Map<IrElement, BitSet> map = this.filteredElementEndsLV;
                BitSet bitSet = map.get(irWhileLoop);
                if (bitSet == null) {
                    bitSet = new BitSet();
                    map.put(irWhileLoop, bitSet);
                }
                BitSet bitSet2 = bitSet;
                bitSet2.or(data);
                bitSet2.or(this.catchesLV);
            }
            BitSet bitSetCopy = BitSetUtilKt.copy(data);
            bitSetCopy.or(handleLoop(loop, data));
            return (BitSet) loop.getCondition().accept(this, bitSetCopy);
        }
    }
}
