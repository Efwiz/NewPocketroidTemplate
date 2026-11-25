package org.jetbrains.kotlin.ir.backend.js.lower.inline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.UInt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.UStringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
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
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
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
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrUninitializedType;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.SimpleTypeRemapper;
import org.jetbrains.kotlin.ir.util.SymbolRemapper;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: SyntheticAccessorLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0003\u0011\u0012\u0013B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0014\u0010\r\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/inline/SyntheticAccessorLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "createAccessor", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "fileHash", "", "CallSiteTransformer", "CandidatesCollector", "FunctionCopier", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SyntheticAccessorLowering implements BodyLoweringPass {
    private final CommonBackendContext context;

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
    }

    public SyntheticAccessorLowering(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    /* JADX INFO: compiled from: SyntheticAccessorLowering.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\u00020\t2\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u0016J\f\u0010\u0017\u001a\u00020\u0018*\u00020\u0004H\u0002R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/inline/SyntheticAccessorLowering$CandidatesCollector;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitorVoid;", "candidates", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Ljava/util/Collection;)V", "getCandidates", "()Ljava/util/Collection;", "visitAnonymousInitializer", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitMemberAccess", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitSimpleFunction", "isTopLevelPrivate", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CandidatesCollector implements IrElementVisitorVoid {
        private final Collection<IrSimpleFunction> candidates;

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitAnonymousInitializer(IrAnonymousInitializer declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructor(IrConstructor declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitProperty(IrProperty declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
        }

        public CandidatesCollector(Collection<IrSimpleFunction> candidates) {
            Intrinsics.checkNotNullParameter(candidates, "candidates");
            this.candidates = candidates;
        }

        public final Collection<IrSimpleFunction> getCandidates() {
            return this.candidates;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitAnonymousInitializer */
        public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj) {
            visitAnonymousInitializer(irAnonymousInitializer, (Void) obj);
            return Unit.INSTANCE;
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
        public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement, Object obj) {
            visitElement(irElement, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitElement(IrElement irElement, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement, r2);
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

        private final boolean isTopLevelPrivate(IrSimpleFunction irSimpleFunction) {
            if (Intrinsics.areEqual(irSimpleFunction.getVisibility(), DescriptorVisibilities.PRIVATE)) {
                return irSimpleFunction.getParent() instanceof IrFile;
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        /* JADX INFO: renamed from: visitElement */
        public void mo6335visitElement(IrElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            IrVisitorsKt.acceptChildrenVoid(element, this);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSimpleFunction(IrSimpleFunction declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            if (declaration.getIsInline()) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, declaration);
            }
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitMemberAccess(IrMemberAccessExpression<?> expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, expression);
            IrSymbolOwner owner = expression.getSymbol().getOwner();
            IrSimpleFunction irSimpleFunction = owner instanceof IrSimpleFunction ? (IrSimpleFunction) owner : null;
            if (irSimpleFunction == null || irSimpleFunction.getIsInline() || !isTopLevelPrivate(irSimpleFunction)) {
                return;
            }
            this.candidates.add(irSimpleFunction);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: SyntheticAccessorLowering.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0002\u0006\t\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eR\u0010\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007R\u0010\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/inline/SyntheticAccessorLowering$FunctionCopier;", "", "fileHash", "", "(Ljava/lang/String;)V", "copier", "org/jetbrains/kotlin/ir/backend/js/lower/inline/SyntheticAccessorLowering$FunctionCopier$copier$1", "Lorg/jetbrains/kotlin/ir/backend/js/lower/inline/SyntheticAccessorLowering$FunctionCopier$copier$1;", "symbolRemapper", "org/jetbrains/kotlin/ir/backend/js/lower/inline/SyntheticAccessorLowering$FunctionCopier$symbolRemapper$1", "Lorg/jetbrains/kotlin/ir/backend/js/lower/inline/SyntheticAccessorLowering$FunctionCopier$symbolRemapper$1;", "typeRemapper", "Lorg/jetbrains/kotlin/ir/util/SimpleTypeRemapper;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "function", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class FunctionCopier {
        private final SyntheticAccessorLowering$FunctionCopier$copier$1 copier;
        private final SyntheticAccessorLowering$FunctionCopier$symbolRemapper$1 symbolRemapper;
        private final SimpleTypeRemapper typeRemapper;

        /* JADX WARN: Type inference failed for: r0v1, types: [org.jetbrains.kotlin.ir.backend.js.lower.inline.SyntheticAccessorLowering$FunctionCopier$symbolRemapper$1] */
        /* JADX WARN: Type inference failed for: r2v2, types: [org.jetbrains.kotlin.ir.backend.js.lower.inline.SyntheticAccessorLowering$FunctionCopier$copier$1] */
        public FunctionCopier(final String fileHash) {
            Intrinsics.checkNotNullParameter(fileHash, "fileHash");
            final ?? r0 = new DeepCopySymbolRemapper() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.inline.SyntheticAccessorLowering$FunctionCopier$symbolRemapper$1
                @Override // org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper, org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                public void visitSimpleFunction(IrSimpleFunction declaration) {
                    Intrinsics.checkNotNullParameter(declaration, "declaration");
                    HashMap<IrSimpleFunctionSymbol, IrSimpleFunctionSymbol> functions = getFunctions();
                    IrSymbol symbol = declaration.getSymbol();
                    if (symbol == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
                    }
                    functions.put((IrSimpleFunctionSymbol) symbol, new IrSimpleFunctionSymbolImpl(null, 1, null));
                    Iterator<T> it = declaration.getTypeParameters().iterator();
                    while (it.hasNext()) {
                        IrVisitorsKt.acceptVoid((IrTypeParameter) it.next(), this);
                    }
                    IrValueParameter extensionReceiverParameter = declaration.getExtensionReceiverParameter();
                    if (extensionReceiverParameter != null) {
                        IrVisitorsKt.acceptVoid(extensionReceiverParameter, this);
                    }
                    Iterator<T> it2 = declaration.getValueParameters().iterator();
                    while (it2.hasNext()) {
                        IrVisitorsKt.acceptVoid((IrValueParameter) it2.next(), this);
                    }
                }
            };
            this.symbolRemapper = r0;
            final SimpleTypeRemapper simpleTypeRemapper = new SimpleTypeRemapper((SymbolRemapper) r0);
            this.typeRemapper = simpleTypeRemapper;
            this.copier = new DeepCopyIrTreeWithSymbols(fileHash, this, r0, simpleTypeRemapper) { // from class: org.jetbrains.kotlin.ir.backend.js.lower.inline.SyntheticAccessorLowering$FunctionCopier$copier$1
                final /* synthetic */ String $fileHash;
                final /* synthetic */ SyntheticAccessorLowering.FunctionCopier this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(r0, simpleTypeRemapper);
                }

                @Override // org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                public IrSimpleFunction visitSimpleFunction(IrSimpleFunction declaration) {
                    Intrinsics.checkNotNullParameter(declaration, "declaration");
                    Name nameIdentifier = Name.identifier(declaration.getName().asString() + "$accessor$" + this.$fileHash);
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    IrFactory factory = declaration.getFactory();
                    int startOffset = declaration.getStartOffset();
                    int endOffset = declaration.getEndOffset();
                    IrDeclarationOrigin irDeclarationOriginMapDeclarationOrigin = mapDeclarationOrigin(declaration.getOrigin());
                    DescriptorVisibility INTERNAL = DescriptorVisibilities.INTERNAL;
                    Intrinsics.checkNotNullExpressionValue(INTERNAL, "INTERNAL");
                    IrSimpleFunction irSimpleFunctionCreateSimpleFunction = factory.createSimpleFunction(startOffset, endOffset, irDeclarationOriginMapDeclarationOrigin, nameIdentifier, INTERNAL, declaration.getIsInline(), declaration.getIsExpect(), IrUninitializedType.INSTANCE, declaration.getModality(), getDeclaredFunction(declaration.getSymbol()), declaration.getIsTailrec(), declaration.getIsSuspend(), declaration.getIsOperator(), declaration.getIsInfix(), declaration.getIsExternal(), declaration.getContainerSource(), declaration.getIsFakeOverride());
                    declaration.getOverriddenSymbols().isEmpty();
                    transformFunctionChildren(irSimpleFunctionCreateSimpleFunction, declaration);
                    return irSimpleFunctionCreateSimpleFunction;
                }

                /* JADX WARN: Code duplicated, block: B:11:0x0044  */
                private final void transformFunctionChildren(IrSimpleFunction irSimpleFunction, IrSimpleFunction irSimpleFunction2) {
                    IrValueParameter irValueParameter;
                    IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
                    copyTypeParametersFrom(irSimpleFunction3, irSimpleFunction2);
                    SimpleTypeRemapper simpleTypeRemapper2 = this.this$0.typeRemapper;
                    SyntheticAccessorLowering.FunctionCopier functionCopier = this.this$0;
                    simpleTypeRemapper2.enterScope(irSimpleFunction3);
                    irSimpleFunction2.getDispatchReceiverParameter();
                    IrValueParameter extensionReceiverParameter = irSimpleFunction2.getExtensionReceiverParameter();
                    if (extensionReceiverParameter == null) {
                        irValueParameter = null;
                    } else {
                        IrElement irElementTransform = extensionReceiverParameter.transform((IrElementTransformer<? super Object>) this, (Object) null);
                        if (irElementTransform != null) {
                            irValueParameter = (IrValueParameter) irElementTransform;
                            if (irValueParameter != null) {
                                irValueParameter.setParent(irSimpleFunction);
                            } else {
                                irValueParameter = null;
                            }
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
                        }
                    }
                    irSimpleFunction.setExtensionReceiverParameter(irValueParameter);
                    irSimpleFunction.setReturnType(functionCopier.typeRemapper.remapType(irSimpleFunction2.getReturnType()));
                    SyntheticAccessorLowering$FunctionCopier$copier$1 syntheticAccessorLowering$FunctionCopier$copier$1 = this;
                    List<IrValueParameter> valueParameters = irSimpleFunction2.getValueParameters();
                    ArrayList arrayList = new ArrayList(valueParameters.size());
                    Iterator<T> it = valueParameters.iterator();
                    while (it.hasNext()) {
                        IrElement irElementTransform2 = ((IrElement) it.next()).transform(syntheticAccessorLowering$FunctionCopier$copier$1, null);
                        if (irElementTransform2 != null) {
                            arrayList.add((IrValueParameter) irElementTransform2);
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
                        }
                    }
                    irSimpleFunction.setValueParameters(CollectionsKt.compactIfPossible(arrayList));
                    Iterator<T> it2 = irSimpleFunction.getValueParameters().iterator();
                    while (it2.hasNext()) {
                        ((IrValueParameter) it2.next()).setParent(irSimpleFunction);
                    }
                    Iterator<T> it3 = irSimpleFunction.getTypeParameters().iterator();
                    while (it3.hasNext()) {
                        ((IrTypeParameter) it3.next()).setParent(irSimpleFunction);
                    }
                    Unit unit = Unit.INSTANCE;
                    simpleTypeRemapper2.leaveScope();
                }
            };
        }

        public final IrSimpleFunction copy(IrSimpleFunction function) {
            Intrinsics.checkNotNullParameter(function, "function");
            IrVisitorsKt.acceptVoid(function, this.symbolRemapper);
            IrElement irElementTransform = function.transform(this.copier, null);
            Intrinsics.checkNotNull(irElementTransform, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
            return (IrSimpleFunction) irElementTransform;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction createAccessor(IrSimpleFunction irSimpleFunction, String str) {
        IrSimpleFunction irSimpleFunctionCopy = new FunctionCopier(str).copy(irSimpleFunction);
        IrCallImpl irCallImpl = new IrCallImpl(irSimpleFunction.getStartOffset(), irSimpleFunction.getEndOffset(), irSimpleFunctionCopy.getReturnType(), irSimpleFunction.getSymbol(), irSimpleFunction.getTypeParameters().size(), irSimpleFunction.getValueParameters().size(), null, null, 192, null);
        int i = 0;
        int i2 = 0;
        for (Object obj : irSimpleFunctionCopy.getTypeParameters()) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                kotlin.collections.CollectionsKt.throwIndexOverflow();
            }
            irCallImpl.putTypeArgument(i2, IrTypesKt.getDefaultType((IrTypeParameter) obj));
            i2 = i3;
        }
        for (Object obj2 : irSimpleFunctionCopy.getValueParameters()) {
            int i4 = i + 1;
            if (i < 0) {
                kotlin.collections.CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter = (IrValueParameter) obj2;
            irCallImpl.putValueArgument(i, new IrGetValueImpl(irSimpleFunction.getStartOffset(), irSimpleFunction.getEndOffset(), irValueParameter.getType(), irValueParameter.getSymbol(), null, 16, null));
            i = i4;
        }
        IrValueParameter extensionReceiverParameter = irSimpleFunctionCopy.getExtensionReceiverParameter();
        irCallImpl.setExtensionReceiver(extensionReceiverParameter != null ? new IrGetValueImpl(irSimpleFunction.getStartOffset(), irSimpleFunction.getEndOffset(), extensionReceiverParameter.getType(), extensionReceiverParameter.getSymbol(), null, 16, null) : null);
        irSimpleFunctionCopy.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), irSimpleFunction.getStartOffset(), irSimpleFunction.getEndOffset(), (List<? extends IrStatement>) kotlin.collections.CollectionsKt.listOf(new IrReturnImpl(irSimpleFunction.getStartOffset(), irSimpleFunction.getEndOffset(), this.context.getIrBuiltIns().getNothingType(), irSimpleFunctionCopy.getSymbol(), irCallImpl))));
        return irSimpleFunctionCopy;
    }

    /* JADX INFO: compiled from: SyntheticAccessorLowering.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u000bH\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/inline/SyntheticAccessorLowering$CallSiteTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "functionMap", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Ljava/util/Map;)V", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CallSiteTransformer extends IrElementTransformerVoid {
        private final Map<IrSimpleFunction, IrSimpleFunction> functionMap;

        /* JADX WARN: Multi-variable type inference failed */
        public CallSiteTransformer(Map<IrSimpleFunction, ? extends IrSimpleFunction> functionMap) {
            Intrinsics.checkNotNullParameter(functionMap, "functionMap");
            this.functionMap = functionMap;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitCall(IrCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            transformChildrenVoid(expression);
            IrSimpleFunction irSimpleFunction = this.functionMap.get(expression.getSymbol().getOwner());
            if (irSimpleFunction != null) {
                IrCallImpl irCallImpl = new IrCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irSimpleFunction.getSymbol(), expression.getTypeArgumentsCount(), expression.getValueArgumentsCount(), expression.getOrigin(), null, 128, null);
                IrExpressionsKt.copyTypeArgumentsFrom$default(irCallImpl, expression, 0, 2, null);
                irCallImpl.setExtensionReceiver(expression.getExtensionReceiver());
                int valueArgumentsCount = expression.getValueArgumentsCount();
                for (int i = 0; i < valueArgumentsCount; i++) {
                    irCallImpl.putValueArgument(i, expression.getValueArgument(i));
                }
                return irCallImpl;
            }
            return expression;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitFunctionReference(IrFunctionReference expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            transformChildrenVoid(expression);
            IrSimpleFunction irSimpleFunction = this.functionMap.get(expression.getSymbol().getOwner());
            if (irSimpleFunction != null) {
                IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irSimpleFunction.getSymbol(), expression.getTypeArgumentsCount(), expression.getValueArgumentsCount(), null, null, 192, null);
                IrExpressionsKt.copyTypeArgumentsFrom$default(irFunctionReferenceImpl, expression, 0, 2, null);
                irFunctionReferenceImpl.setExtensionReceiver(expression.getExtensionReceiver());
                int valueArgumentsCount = expression.getValueArgumentsCount();
                for (int i = 0; i < valueArgumentsCount; i++) {
                    irFunctionReferenceImpl.putValueArgument(i, expression.getValueArgument(i));
                }
                return irFunctionReferenceImpl;
            }
            return expression;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass, org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        ArrayList arrayList = new ArrayList();
        IrFile irFile2 = irFile;
        IrVisitorsKt.acceptChildrenVoid(irFile2, new CandidatesCollector(arrayList));
        if (arrayList.isEmpty()) {
            return;
        }
        final String strM4239toStringV7xB4Y4 = UStringsKt.m4239toStringV7xB4Y4(UInt.m2993constructorimpl(irFile.getFileEntry().getName().hashCode()), 36);
        ArrayList<IrSimpleFunction> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (final IrSimpleFunction irSimpleFunction : arrayList2) {
            arrayList3.add((IrSimpleFunction) this.context.getIrFactory().getStageController().restrictTo(irSimpleFunction, new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.inline.SyntheticAccessorLowering$lower$accessors$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrSimpleFunction invoke() {
                    return this.this$0.createAccessor(irSimpleFunction, strM4239toStringV7xB4Y4);
                }
            }));
        }
        ArrayList arrayList4 = arrayList3;
        ArrayList arrayList5 = arrayList4;
        IrElementTransformerVoidKt.transformChildrenVoid(irFile2, new CallSiteTransformer(MapsKt.toMap(kotlin.collections.CollectionsKt.zip(arrayList2, arrayList5))));
        Iterator it = arrayList5.iterator();
        while (it.hasNext()) {
            ((IrSimpleFunction) it.next()).setParent(irFile);
        }
        irFile.getDeclarations().addAll(arrayList4);
    }
}
