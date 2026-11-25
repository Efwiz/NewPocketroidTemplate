package org.jetbrains.kotlin.backend.common.lower;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.inline.FunctionInliningKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
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
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: SharedVariablesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/SharedVariablesLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "(Lorg/jetbrains/kotlin/backend/common/BackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/BackendContext;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "SharedVariablesTransformer", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SharedVariablesLowering implements BodyLoweringPass {
    private final BackendContext context;

    public SharedVariablesLowering(BackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final BackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        new SharedVariablesTransformer(this, irBody, container).lowerSharedVariables();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: SharedVariablesLowering.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0006\u0010\u0011\u001a\u00020\u0010J\b\u0010\u0012\u001a\u00020\u0010H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001e\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00020\r`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/SharedVariablesLowering$SharedVariablesTransformer;", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "irDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/backend/common/lower/SharedVariablesLowering;Lorg/jetbrains/kotlin/ir/expressions/IrBody;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)V", "getIrBody", "()Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "getIrDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "sharedVariables", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "Lkotlin/collections/HashSet;", "collectSharedVariables", "", "lowerSharedVariables", "rewriteSharedVariables", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class SharedVariablesTransformer {
        private final IrBody irBody;
        private final IrDeclaration irDeclaration;
        private final HashSet<IrVariable> sharedVariables;
        final /* synthetic */ SharedVariablesLowering this$0;

        public SharedVariablesTransformer(SharedVariablesLowering sharedVariablesLowering, IrBody irBody, IrDeclaration irDeclaration) {
            Intrinsics.checkNotNullParameter(irBody, "irBody");
            Intrinsics.checkNotNullParameter(irDeclaration, "irDeclaration");
            this.this$0 = sharedVariablesLowering;
            this.irBody = irBody;
            this.irDeclaration = irDeclaration;
            this.sharedVariables = new HashSet<>();
        }

        public final IrBody getIrBody() {
            return this.irBody;
        }

        public final IrDeclaration getIrDeclaration() {
            return this.irDeclaration;
        }

        public final void lowerSharedVariables() {
            collectSharedVariables();
            if (this.sharedVariables.isEmpty()) {
                return;
            }
            rewriteSharedVariables();
        }

        private final void collectSharedVariables() {
            final LinkedHashMap linkedHashMap = new LinkedHashMap();
            IrBody irBody = this.irBody;
            IrElementVisitor<Unit, IrDeclarationParent> irElementVisitor = new IrElementVisitor<Unit, IrDeclarationParent>() { // from class: org.jetbrains.kotlin.backend.common.lower.SharedVariablesLowering$SharedVariablesTransformer$collectSharedVariables$1
                private final HashSet<IrVariable> relevantVars = new HashSet<>();
                private final HashSet<IrVariable> relevantVals = new HashSet<>();

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitAnonymousInitializer */
                public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrDeclarationParent irDeclarationParent) {
                    visitAnonymousInitializer2(irAnonymousInitializer, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
                public void visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBlock */
                public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, IrDeclarationParent irDeclarationParent) {
                    visitBlock2(irBlock, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
                public void visitBlock2(IrBlock irBlock, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBlockBody */
                public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, IrDeclarationParent irDeclarationParent) {
                    visitBlockBody2(irBlockBody, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
                public void visitBlockBody2(IrBlockBody irBlockBody, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBody */
                public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody2, IrDeclarationParent irDeclarationParent) {
                    visitBody2(irBody2, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
                public void visitBody2(IrBody irBody2, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitBody(this, irBody2, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBranch */
                public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, IrDeclarationParent irDeclarationParent) {
                    visitBranch2(irBranch, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
                public void visitBranch2(IrBranch irBranch, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBreak */
                public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, IrDeclarationParent irDeclarationParent) {
                    visitBreak2(irBreak, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
                public void visitBreak2(IrBreak irBreak, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitBreakContinue */
                public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, IrDeclarationParent irDeclarationParent) {
                    visitBreakContinue2(irBreakContinue, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
                public void visitBreakContinue2(IrBreakContinue irBreakContinue, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitCall */
                public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, IrDeclarationParent irDeclarationParent) {
                    visitCall2(irCall, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, IrDeclarationParent irDeclarationParent) {
                    visitCallableReference2((IrCallableReference<?>) irCallableReference, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
                public void visitCallableReference2(IrCallableReference<?> irCallableReference, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitCatch */
                public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, IrDeclarationParent irDeclarationParent) {
                    visitCatch2(irCatch, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
                public void visitCatch2(IrCatch irCatch, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitClass */
                public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, IrDeclarationParent irDeclarationParent) {
                    visitClass2(irClass, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
                public void visitClass2(IrClass irClass, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitClass(this, irClass, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitClassReference */
                public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, IrDeclarationParent irDeclarationParent) {
                    visitClassReference2(irClassReference, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
                public void visitClassReference2(IrClassReference irClassReference, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitComposite */
                public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, IrDeclarationParent irDeclarationParent) {
                    visitComposite2(irComposite, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
                public void visitComposite2(IrComposite irComposite, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, IrDeclarationParent irDeclarationParent) {
                    visitConst2((IrConst<?>) irConst, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
                public void visitConst2(IrConst<?> irConst, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitConst(this, irConst, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantArray */
                public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, IrDeclarationParent irDeclarationParent) {
                    visitConstantArray2(irConstantArray, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
                public void visitConstantArray2(IrConstantArray irConstantArray, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantObject */
                public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, IrDeclarationParent irDeclarationParent) {
                    visitConstantObject2(irConstantObject, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
                public void visitConstantObject2(IrConstantObject irConstantObject, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantPrimitive */
                public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclarationParent irDeclarationParent) {
                    visitConstantPrimitive2(irConstantPrimitive, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
                public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstantValue */
                public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, IrDeclarationParent irDeclarationParent) {
                    visitConstantValue2(irConstantValue, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
                public void visitConstantValue2(IrConstantValue irConstantValue, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstructor */
                public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, IrDeclarationParent irDeclarationParent) {
                    visitConstructor2(irConstructor, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
                public void visitConstructor2(IrConstructor irConstructor, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitConstructorCall */
                public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, IrDeclarationParent irDeclarationParent) {
                    visitConstructorCall2(irConstructorCall, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
                public void visitConstructorCall2(IrConstructorCall irConstructorCall, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitContainerExpression */
                public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, IrDeclarationParent irDeclarationParent) {
                    visitContainerExpression2(irContainerExpression, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
                public void visitContainerExpression2(IrContainerExpression irContainerExpression, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitContinue */
                public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, IrDeclarationParent irDeclarationParent) {
                    visitContinue2(irContinue, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
                public void visitContinue2(IrContinue irContinue, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDeclaration */
                public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, IrDeclarationParent irDeclarationParent) {
                    visitDeclaration2(irDeclarationBase, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDeclarationReference */
                public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclarationParent irDeclarationParent) {
                    visitDeclarationReference2(irDeclarationReference, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
                public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDelegatingConstructorCall */
                public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrDeclarationParent irDeclarationParent) {
                    visitDelegatingConstructorCall2(irDelegatingConstructorCall, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
                public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDoWhileLoop */
                public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrDeclarationParent irDeclarationParent) {
                    visitDoWhileLoop2(irDoWhileLoop, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
                public void visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDynamicExpression */
                public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclarationParent irDeclarationParent) {
                    visitDynamicExpression2(irDynamicExpression, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
                public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDynamicMemberExpression */
                public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrDeclarationParent irDeclarationParent) {
                    visitDynamicMemberExpression2(irDynamicMemberExpression, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
                public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitDynamicOperatorExpression */
                public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrDeclarationParent irDeclarationParent) {
                    visitDynamicOperatorExpression2(irDynamicOperatorExpression, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
                public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitElement */
                public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, IrDeclarationParent irDeclarationParent) {
                    visitElement2(irElement, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitElseBranch */
                public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, IrDeclarationParent irDeclarationParent) {
                    visitElseBranch2(irElseBranch, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
                public void visitElseBranch2(IrElseBranch irElseBranch, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitEnumConstructorCall */
                public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrDeclarationParent irDeclarationParent) {
                    visitEnumConstructorCall2(irEnumConstructorCall, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
                public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitEnumEntry */
                public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, IrDeclarationParent irDeclarationParent) {
                    visitEnumEntry2(irEnumEntry, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
                public void visitEnumEntry2(IrEnumEntry irEnumEntry, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitErrorCallExpression */
                public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrDeclarationParent irDeclarationParent) {
                    visitErrorCallExpression2(irErrorCallExpression, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
                public void visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitErrorDeclaration */
                public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrDeclarationParent irDeclarationParent) {
                    visitErrorDeclaration2(irErrorDeclaration, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
                public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitErrorExpression */
                public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, IrDeclarationParent irDeclarationParent) {
                    visitErrorExpression2(irErrorExpression, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
                public void visitErrorExpression2(IrErrorExpression irErrorExpression, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitExpression */
                public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, IrDeclarationParent irDeclarationParent) {
                    visitExpression2(irExpression, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
                public void visitExpression2(IrExpression irExpression, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitExpressionBody */
                public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, IrDeclarationParent irDeclarationParent) {
                    visitExpressionBody2(irExpressionBody, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
                public void visitExpressionBody2(IrExpressionBody irExpressionBody, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitExternalPackageFragment */
                public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclarationParent irDeclarationParent) {
                    visitExternalPackageFragment2(irExternalPackageFragment, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
                public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitField */
                public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, IrDeclarationParent irDeclarationParent) {
                    visitField2(irField, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
                public void visitField2(IrField irField, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitField(this, irField, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFieldAccess */
                public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrDeclarationParent irDeclarationParent) {
                    visitFieldAccess2(irFieldAccessExpression, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
                public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFile */
                public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, IrDeclarationParent irDeclarationParent) {
                    visitFile2(irFile, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
                public void visitFile2(IrFile irFile, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitFile(this, irFile, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunction */
                public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, IrDeclarationParent irDeclarationParent) {
                    visitFunction2(irFunction, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
                public void visitFunction2(IrFunction irFunction, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunctionAccess */
                public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrDeclarationParent irDeclarationParent) {
                    visitFunctionAccess2(irFunctionAccessExpression, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
                public void visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunctionExpression */
                public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrDeclarationParent irDeclarationParent) {
                    visitFunctionExpression2(irFunctionExpression, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
                public void visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitFunctionReference */
                public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, IrDeclarationParent irDeclarationParent) {
                    visitFunctionReference2(irFunctionReference, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
                public void visitFunctionReference2(IrFunctionReference irFunctionReference, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetClass */
                public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, IrDeclarationParent irDeclarationParent) {
                    visitGetClass2(irGetClass, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
                public void visitGetClass2(IrGetClass irGetClass, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetEnumValue */
                public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrDeclarationParent irDeclarationParent) {
                    visitGetEnumValue2(irGetEnumValue, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
                public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetField */
                public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, IrDeclarationParent irDeclarationParent) {
                    visitGetField2(irGetField, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
                public void visitGetField2(IrGetField irGetField, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetObjectValue */
                public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrDeclarationParent irDeclarationParent) {
                    visitGetObjectValue2(irGetObjectValue, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
                public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitGetValue */
                public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, IrDeclarationParent irDeclarationParent) {
                    visitGetValue2(irGetValue, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
                public void visitGetValue2(IrGetValue irGetValue, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitInstanceInitializerCall */
                public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrDeclarationParent irDeclarationParent) {
                    visitInstanceInitializerCall2(irInstanceInitializerCall, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
                public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitLocalDelegatedProperty */
                public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrDeclarationParent irDeclarationParent) {
                    visitLocalDelegatedProperty2(irLocalDelegatedProperty, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
                public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
                public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrDeclarationParent irDeclarationParent) {
                    visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
                public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitLoop */
                public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, IrDeclarationParent irDeclarationParent) {
                    visitLoop2(irLoop, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
                public void visitLoop2(IrLoop irLoop, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, IrDeclarationParent irDeclarationParent) {
                    visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
                public void visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitModuleFragment */
                public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, IrDeclarationParent irDeclarationParent) {
                    visitModuleFragment2(irModuleFragment, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
                public void visitModuleFragment2(IrModuleFragment irModuleFragment, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitPackageFragment */
                public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclarationParent irDeclarationParent) {
                    visitPackageFragment2(irPackageFragment, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
                public void visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitProperty */
                public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, IrDeclarationParent irDeclarationParent) {
                    visitProperty2(irProperty, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
                public void visitProperty2(IrProperty irProperty, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitPropertyReference */
                public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, IrDeclarationParent irDeclarationParent) {
                    visitPropertyReference2(irPropertyReference, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
                public void visitPropertyReference2(IrPropertyReference irPropertyReference, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitRawFunctionReference */
                public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrDeclarationParent irDeclarationParent) {
                    visitRawFunctionReference2(irRawFunctionReference, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
                public void visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitReturn */
                public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, IrDeclarationParent irDeclarationParent) {
                    visitReturn2(irReturn, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
                public void visitReturn2(IrReturn irReturn, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitScript */
                public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, IrDeclarationParent irDeclarationParent) {
                    visitScript2(irScript, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
                public void visitScript2(IrScript irScript, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitScript(this, irScript, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSetField */
                public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, IrDeclarationParent irDeclarationParent) {
                    visitSetField2(irSetField, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
                public void visitSetField2(IrSetField irSetField, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSetValue */
                public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, IrDeclarationParent irDeclarationParent) {
                    visitSetValue2(irSetValue, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSimpleFunction */
                public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclarationParent irDeclarationParent) {
                    visitSimpleFunction2(irSimpleFunction, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
                public void visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSingletonReference */
                public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclarationParent irDeclarationParent) {
                    visitSingletonReference2(irGetSingletonValue, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
                public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSpreadElement */
                public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, IrDeclarationParent irDeclarationParent) {
                    visitSpreadElement2(irSpreadElement, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
                public void visitSpreadElement2(IrSpreadElement irSpreadElement, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitStringConcatenation */
                public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrDeclarationParent irDeclarationParent) {
                    visitStringConcatenation2(irStringConcatenation, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
                public void visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSuspendableExpression */
                public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclarationParent irDeclarationParent) {
                    visitSuspendableExpression2(irSuspendableExpression, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
                public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSuspensionPoint */
                public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclarationParent irDeclarationParent) {
                    visitSuspensionPoint2(irSuspensionPoint, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
                public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitSyntheticBody */
                public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrDeclarationParent irDeclarationParent) {
                    visitSyntheticBody2(irSyntheticBody, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
                public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitThrow */
                public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, IrDeclarationParent irDeclarationParent) {
                    visitThrow2(irThrow, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
                public void visitThrow2(IrThrow irThrow, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTry */
                public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, IrDeclarationParent irDeclarationParent) {
                    visitTry2(irTry, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
                public void visitTry2(IrTry irTry, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitTry(this, irTry, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTypeAlias */
                public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, IrDeclarationParent irDeclarationParent) {
                    visitTypeAlias2(irTypeAlias, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
                public void visitTypeAlias2(IrTypeAlias irTypeAlias, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTypeOperator */
                public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrDeclarationParent irDeclarationParent) {
                    visitTypeOperator2(irTypeOperatorCall, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
                public void visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitTypeParameter */
                public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, IrDeclarationParent irDeclarationParent) {
                    visitTypeParameter2(irTypeParameter, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
                public void visitTypeParameter2(IrTypeParameter irTypeParameter, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitValueAccess */
                public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrDeclarationParent irDeclarationParent) {
                    visitValueAccess2(irValueAccessExpression, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitValueParameter */
                public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, IrDeclarationParent irDeclarationParent) {
                    visitValueParameter2(irValueParameter, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
                public void visitValueParameter2(IrValueParameter irValueParameter, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitVararg */
                public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, IrDeclarationParent irDeclarationParent) {
                    visitVararg2(irVararg, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
                public void visitVararg2(IrVararg irVararg, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitVariable */
                public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, IrDeclarationParent irDeclarationParent) {
                    visitVariable2(irVariable, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitWhen */
                public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, IrDeclarationParent irDeclarationParent) {
                    visitWhen2(irWhen, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
                public void visitWhen2(IrWhen irWhen, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, irDeclarationParent);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
                /* JADX INFO: renamed from: visitWhileLoop */
                public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, IrDeclarationParent irDeclarationParent) {
                    visitWhileLoop2(irWhileLoop, irDeclarationParent);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
                public void visitWhileLoop2(IrWhileLoop irWhileLoop, IrDeclarationParent irDeclarationParent) {
                    IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, irDeclarationParent);
                }

                public final HashSet<IrVariable> getRelevantVars() {
                    return this.relevantVars;
                }

                public final HashSet<IrVariable> getRelevantVals() {
                    return this.relevantVals;
                }

                /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
                public void visitElement2(IrElement element, IrDeclarationParent data) {
                    Intrinsics.checkNotNullParameter(element, "element");
                    element.acceptChildren(this, data);
                }

                /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
                public void visitCall2(IrCall expression, IrDeclarationParent data) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    IrSimpleFunction owner = expression.getSymbol().getOwner();
                    if (!owner.getIsInline()) {
                        IrElementVisitor.DefaultImpls.visitCall(this, expression, data);
                        return;
                    }
                    IrExpression dispatchReceiver = expression.getDispatchReceiver();
                    if (dispatchReceiver != null) {
                        dispatchReceiver.accept(this, data);
                    }
                    IrExpression extensionReceiver = expression.getExtensionReceiver();
                    if (extensionReceiver != null) {
                        extensionReceiver.accept(this, data);
                    }
                    for (IrValueParameter irValueParameter : owner.getValueParameters()) {
                        IrExpression valueArgument = expression.getValueArgument(irValueParameter.getIndex());
                        if (valueArgument != null) {
                            if (FunctionInliningKt.isInlineParameter$default(irValueParameter, null, 1, null) && !irValueParameter.getIsCrossinline() && (valueArgument instanceof IrFunctionExpression)) {
                                Map<IrFunction, IrDeclarationParent> map = linkedHashMap;
                                IrFunctionExpression irFunctionExpression = (IrFunctionExpression) valueArgument;
                                IrSimpleFunction function = irFunctionExpression.getFunction();
                                Intrinsics.checkNotNull(data);
                                map.put(function, data);
                                irFunctionExpression.getFunction().acceptChildren(this, data);
                                linkedHashMap.remove(irFunctionExpression.getFunction());
                            } else {
                                valueArgument.accept(this, data);
                            }
                        }
                    }
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
                public void visitDeclaration2(IrDeclarationBase declaration, IrDeclarationParent data) {
                    Intrinsics.checkNotNullParameter(declaration, "declaration");
                    IrDeclarationParent irDeclarationParent = declaration instanceof IrDeclarationParent ? (IrDeclarationParent) declaration : null;
                    if (irDeclarationParent != null) {
                        data = irDeclarationParent;
                    }
                    IrElementVisitor.DefaultImpls.visitDeclaration(this, declaration, data);
                }

                /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
                public void visitVariable2(IrVariable declaration, IrDeclarationParent data) {
                    Intrinsics.checkNotNullParameter(declaration, "declaration");
                    declaration.acceptChildren(this, data);
                    if (declaration.getIsVar()) {
                        this.relevantVars.add(declaration);
                    } else if (declaration.getInitializer() == null) {
                        this.relevantVals.add(declaration);
                    }
                }

                /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
                public void visitValueAccess2(IrValueAccessExpression expression, IrDeclarationParent data) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    expression.acceptChildren(this, data);
                    IrValueDeclaration owner = expression.getSymbol().getOwner();
                    if (CollectionsKt.contains(this.relevantVars, owner)) {
                        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrVariable");
                        if (Intrinsics.areEqual(getRealParent((IrVariable) owner), data)) {
                            return;
                        }
                        this.sharedVariables.add(owner);
                    }
                }

                /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
                public void visitSetValue2(IrSetValue expression, IrDeclarationParent data) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    IrElementVisitor.DefaultImpls.visitSetValue(this, expression, data);
                    IrValueDeclaration owner = expression.getSymbol().getOwner();
                    if (owner instanceof IrVariable) {
                        IrVariable irVariable = (IrVariable) owner;
                        if (irVariable.getInitializer() == null && !Intrinsics.areEqual(getRealParent(irVariable), data) && this.relevantVals.contains(owner)) {
                            this.sharedVariables.add(owner);
                        }
                    }
                }

                private final IrDeclarationParent getRealParent(IrVariable variable) {
                    IrDeclarationParent parent = variable.getParent();
                    IrDeclarationParent irDeclarationParent = linkedHashMap.get(parent);
                    return irDeclarationParent == null ? parent : irDeclarationParent;
                }
            };
            IrDeclaration irDeclaration = this.irDeclaration;
            IrDeclarationParent parent = irDeclaration instanceof IrDeclarationParent ? (IrDeclarationParent) irDeclaration : null;
            if (parent == null) {
                parent = irDeclaration.getParent();
            }
            irBody.accept(irElementVisitor, parent);
        }

        private final void rewriteSharedVariables() {
            final HashMap map = new HashMap();
            IrBody irBody = this.irBody;
            final SharedVariablesLowering sharedVariablesLowering = this.this$0;
            IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.SharedVariablesLowering$SharedVariablesTransformer$rewriteSharedVariables$1
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrStatement visitVariable(IrVariable declaration) {
                    Intrinsics.checkNotNullParameter(declaration, "declaration");
                    IrElementTransformerVoidKt.transformChildrenVoid(declaration, this);
                    if (!this.this$0.sharedVariables.contains(declaration)) {
                        return declaration;
                    }
                    IrVariable irVariableDeclareSharedVariable = sharedVariablesLowering.getContext().getSharedVariablesManager().declareSharedVariable(declaration);
                    irVariableDeclareSharedVariable.setParent(declaration.getParent());
                    map.put(declaration.getSymbol(), irVariableDeclareSharedVariable.getSymbol());
                    return sharedVariablesLowering.getContext().getSharedVariablesManager().defineSharedValue(declaration, irVariableDeclareSharedVariable);
                }
            });
            IrBody irBody2 = this.irBody;
            final SharedVariablesLowering sharedVariablesLowering2 = this.this$0;
            IrElementTransformerVoidKt.transformChildrenVoid(irBody2, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.SharedVariablesLowering$SharedVariablesTransformer$rewriteSharedVariables$2
                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitGetValue(IrGetValue expression) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                    IrVariableSymbol transformedSymbol = getTransformedSymbol(expression.getSymbol());
                    return transformedSymbol == null ? expression : sharedVariablesLowering2.getContext().getSharedVariablesManager().getSharedValue(transformedSymbol, expression);
                }

                @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrExpression visitSetValue(IrSetValue expression) {
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                    IrVariableSymbol transformedSymbol = getTransformedSymbol(expression.getSymbol());
                    return transformedSymbol == null ? expression : sharedVariablesLowering2.getContext().getSharedVariablesManager().setSharedValue(transformedSymbol, expression);
                }

                private final IrVariableSymbol getTransformedSymbol(IrValueSymbol oldSymbol) {
                    HashMap<IrValueSymbol, IrVariableSymbol> map2 = map;
                    SharedVariablesLowering.SharedVariablesTransformer sharedVariablesTransformer = this;
                    IrVariableSymbol irVariableSymbol = map2.get(oldSymbol);
                    if (irVariableSymbol == null) {
                        CollectionsKt.contains(sharedVariablesTransformer.sharedVariables, oldSymbol.getOwner());
                        irVariableSymbol = null;
                    }
                    return irVariableSymbol;
                }
            });
        }
    }
}
