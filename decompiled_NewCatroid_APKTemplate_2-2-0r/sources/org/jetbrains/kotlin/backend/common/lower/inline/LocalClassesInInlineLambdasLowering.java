package org.jetbrains.kotlin.backend.common.lower.inline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.LowerKt;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering;
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
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: LocalClasses.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/inline/LocalClassesInInlineLambdasLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalClassesInInlineLambdasLowering implements BodyLoweringPass {
    private final CommonBackendContext context;

    public LocalClassesInInlineLambdasLowering(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass, org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        LowerKt.runOnFilePostfix$default(this, irFile, false, 2, null);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, final IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrElementTransformer<IrDeclarationParent> irElementTransformer = new IrElementTransformer<IrDeclarationParent>() { // from class: org.jetbrains.kotlin.backend.common.lower.inline.LocalClassesInInlineLambdasLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
            public IrStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
            public IrExpression visitBlock2(IrBlock irBlock, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBlock(this, irBlock, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitBlockBody2(IrBlockBody irBlockBody, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitBody2(IrBody irBody2, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBody(this, irBody2, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
            public IrBranch visitBranch2(IrBranch irBranch, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBranch(this, irBranch, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
            public IrExpression visitBreak2(IrBreak irBreak, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBreak(this, irBreak, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitBreakContinue2(IrBreakContinue irBreakContinue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ IrElement visitCallableReference(IrCallableReference irCallableReference, Object obj) {
                return visitCallableReference((IrCallableReference<?>) irCallableReference, (IrDeclarationParent) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
            public /* bridge */ /* synthetic */ IrElement visitCallableReference2(IrCallableReference irCallableReference, Object obj) {
                return visitCallableReference((IrCallableReference<?>) irCallableReference, (IrDeclarationParent) obj);
            }

            public IrElement visitCallableReference(IrCallableReference<?> irCallableReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
            public IrCatch visitCatch2(IrCatch irCatch, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitCatch(this, irCatch, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
            public IrStatement visitClass2(IrClass irClass, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitClass(this, irClass, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitClassReference2(IrClassReference irClassReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitClassReference(this, irClassReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
            public IrExpression visitComposite2(IrComposite irComposite, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitComposite(this, irComposite, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ IrElement visitConst(IrConst irConst, Object obj) {
                return visitConst((IrConst<?>) irConst, (IrDeclarationParent) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
            public /* bridge */ /* synthetic */ IrElement visitConst2(IrConst irConst, Object obj) {
                return visitConst((IrConst<?>) irConst, (IrDeclarationParent) obj);
            }

            public IrExpression visitConst(IrConst<?> irConst, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConst(this, irConst, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantArray2(IrConstantArray irConstantArray, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantObject2(IrConstantObject irConstantObject, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantValue2(IrConstantValue irConstantValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
            public IrStatement visitConstructor2(IrConstructor irConstructor, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstructor(this, irConstructor, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitConstructorCall2(IrConstructorCall irConstructorCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitConstructorCall(this, irConstructorCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitContainerExpression2(IrContainerExpression irContainerExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitContainerExpression(this, irContainerExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitContinue2(IrContinue irContinue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitContinue(this, irContinue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
            public IrElement visitElement2(IrElement irElement, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitElement(this, irElement, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
            public IrElseBranch visitElseBranch2(IrElseBranch irElseBranch, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
            public IrStatement visitEnumEntry2(IrEnumEntry irEnumEntry, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
            public IrStatement visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitErrorExpression2(IrErrorExpression irErrorExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitExpression2(IrExpression irExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitExpression(this, irExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitExpressionBody2(IrExpressionBody irExpressionBody, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
            public IrExternalPackageFragment visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
            public IrStatement visitField2(IrField irField, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitField(this, irField, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
            public IrExpression visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
            public IrFile visitFile2(IrFile irFile, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFile(this, irFile, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
            public IrStatement visitFunction2(IrFunction irFunction, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFunction(this, irFunction, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
            public IrElement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
            public IrElement visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
            public IrElement visitFunctionReference2(IrFunctionReference irFunctionReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetClass2(IrGetClass irGetClass, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetClass(this, irGetClass, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetField2(IrGetField irGetField, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetField(this, irGetField, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetValue2(IrGetValue irGetValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitGetValue(this, irGetValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
            public IrExpression visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
            public IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
            public IrElement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
            public IrExpression visitLoop2(IrLoop irLoop, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitLoop(this, irLoop, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrDeclarationParent) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
            public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (IrDeclarationParent) obj);
            }

            public IrElement visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
            public IrModuleFragment visitModuleFragment2(IrModuleFragment irModuleFragment, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitModuleFragment(this, irModuleFragment, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
            public IrElement visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
            public IrStatement visitProperty2(IrProperty irProperty, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitProperty(this, irProperty, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
            public IrElement visitPropertyReference2(IrPropertyReference irPropertyReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
            public IrExpression visitReturn2(IrReturn irReturn, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitReturn(this, irReturn, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
            public IrStatement visitScript2(IrScript irScript, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitScript(this, irScript, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSetField2(IrSetField irSetField, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSetField(this, irSetField, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSetValue2(IrSetValue irSetValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSetValue(this, irSetValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
            public IrStatement visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
            public IrSpreadElement visitSpreadElement2(IrSpreadElement irSpreadElement, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
            public IrExpression visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
            public IrExpression visitThrow2(IrThrow irThrow, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitThrow(this, irThrow, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
            public IrExpression visitTry2(IrTry irTry, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitTry(this, irTry, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
            public IrStatement visitTypeAlias2(IrTypeAlias irTypeAlias, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
            public IrExpression visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
            public IrStatement visitTypeParameter2(IrTypeParameter irTypeParameter, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
            public IrExpression visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
            public IrStatement visitValueParameter2(IrValueParameter irValueParameter, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
            public IrExpression visitVararg2(IrVararg irVararg, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitVararg(this, irVararg, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
            public IrStatement visitVariable2(IrVariable irVariable, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitVariable(this, irVariable, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
            public IrExpression visitWhen2(IrWhen irWhen, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitWhen(this, irWhen, irDeclarationParent);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
            public IrExpression visitWhileLoop2(IrWhileLoop irWhileLoop, IrDeclarationParent irDeclarationParent) {
                return IrElementTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, irDeclarationParent);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
            public IrStatement visitDeclaration2(IrDeclarationBase declaration, IrDeclarationParent data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                Intrinsics.checkNotNullParameter(data, "data");
                IrDeclarationParent irDeclarationParent = declaration instanceof IrDeclarationParent ? (IrDeclarationParent) declaration : null;
                if (irDeclarationParent != null) {
                    data = irDeclarationParent;
                }
                return IrElementTransformer.DefaultImpls.visitDeclaration(this, declaration, data);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitCall2(IrCall expression, IrDeclarationParent data) {
                IrSimpleFunction function;
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(data, "data");
                IrSimpleFunction owner = expression.getSymbol().getOwner();
                if (!owner.getIsInline()) {
                    return IrElementTransformer.DefaultImpls.visitCall(this, expression, data);
                }
                IrExpression extensionReceiver = expression.getExtensionReceiver();
                expression.setExtensionReceiver(extensionReceiver != null ? extensionReceiver.transform((IrElementTransformer<? super IrDeclarationParent>) this, data) : null);
                IrExpression dispatchReceiver = expression.getDispatchReceiver();
                expression.setDispatchReceiver(dispatchReceiver != null ? dispatchReceiver.transform((IrElementTransformer<? super IrDeclarationParent>) this, data) : null);
                ArrayList<IrFunction> arrayList = new ArrayList();
                int valueArgumentsCount = expression.getValueArgumentsCount();
                for (int i = 0; i < valueArgumentsCount; i++) {
                    IrExpression valueArgument = expression.getValueArgument(i);
                    IrFunctionExpression irFunctionExpression = valueArgument instanceof IrFunctionExpression ? (IrFunctionExpression) valueArgument : null;
                    if (irFunctionExpression == null || (function = irFunctionExpression.getFunction()) == null || !FunctionInliningKt.isInlineParameter$default(owner.getValueParameters().get(i), null, 1, null)) {
                        function = null;
                    }
                    if (function == null) {
                        expression.putValueArgument(i, valueArgument != null ? valueArgument.transform((IrElementTransformer<? super IrDeclarationParent>) this, data) : null);
                    } else {
                        arrayList.add(function);
                    }
                }
                final LinkedHashSet linkedHashSet = new LinkedHashSet();
                final LinkedHashSet linkedHashSet2 = new LinkedHashSet();
                final LinkedHashSet linkedHashSet3 = new LinkedHashSet();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    IrVisitorsKt.acceptChildrenVoid((IrFunction) it.next(), new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.inline.LocalClassesInInlineLambdasLowering$lower$1$visitCall$1
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
                        public /* bridge */ /* synthetic */ Object visitBody2(IrBody irBody2, Object obj) {
                            visitBody(irBody2, (Void) obj);
                            return Unit.INSTANCE;
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                        public void visitBody(IrBody irBody2) {
                            IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody2);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                        public void visitBody(IrBody irBody2, Void r2) {
                            IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody2, r2);
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
                        public /* bridge */ /* synthetic */ Object visitFunctionExpression2(IrFunctionExpression irFunctionExpression2, Object obj) {
                            visitFunctionExpression(irFunctionExpression2, (Void) obj);
                            return Unit.INSTANCE;
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                        public void visitFunctionExpression(IrFunctionExpression irFunctionExpression2, Void r2) {
                            IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression2, r2);
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
                            IrVisitorsKt.acceptChildrenVoid(element, this);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                        /* JADX INFO: renamed from: visitClass */
                        public void mo6112visitClass(IrClass declaration) {
                            Intrinsics.checkNotNullParameter(declaration, "declaration");
                            linkedHashSet.add(declaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                        public void visitFunctionExpression(IrFunctionExpression expression2) {
                            Intrinsics.checkNotNullParameter(expression2, "expression");
                            IrVisitorsKt.acceptChildrenVoid(expression2.getFunction(), this);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                        public void visitFunction(IrFunction declaration) {
                            Intrinsics.checkNotNullParameter(declaration, "declaration");
                            declaration.transformChildren(this, declaration);
                            linkedHashSet2.add(declaration);
                        }

                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
                        public void visitCall(IrCall expression2) {
                            Intrinsics.checkNotNullParameter(expression2, "expression");
                            IrSimpleFunction owner2 = expression2.getSymbol().getOwner();
                            if (!owner2.getIsInline()) {
                                IrVisitorsKt.acceptChildrenVoid(expression2, this);
                                return;
                            }
                            IrExpression extensionReceiver2 = expression2.getExtensionReceiver();
                            if (extensionReceiver2 != null) {
                                IrVisitorsKt.acceptVoid(extensionReceiver2, this);
                            }
                            IrExpression dispatchReceiver2 = expression2.getDispatchReceiver();
                            if (dispatchReceiver2 != null) {
                                IrVisitorsKt.acceptVoid(dispatchReceiver2, this);
                            }
                            IntRange intRangeUntil = RangesKt.until(0, expression2.getValueArgumentsCount());
                            Set<IrSimpleFunction> set = linkedHashSet3;
                            Iterator<Integer> it2 = intRangeUntil.iterator();
                            while (it2.hasNext()) {
                                int iNextInt = ((IntIterator) it2).nextInt();
                                IrExpression valueArgument2 = expression2.getValueArgument(iNextInt);
                                if (FunctionInliningKt.isInlineParameter$default(owner2.getValueParameters().get(iNextInt), null, 1, null) && valueArgument2 != null && FunctionInliningKt.isAdaptedFunctionReference(valueArgument2)) {
                                    Intrinsics.checkNotNull(valueArgument2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlock");
                                    IrStatement irStatement = ((IrBlock) valueArgument2).getStatements().get(0);
                                    Intrinsics.checkNotNull(irStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
                                    set.add((IrSimpleFunction) irStatement);
                                } else if (valueArgument2 != null) {
                                    IrVisitorsKt.acceptVoid(valueArgument2, this);
                                }
                            }
                        }
                    });
                }
                if (linkedHashSet.isEmpty() && linkedHashSet2.isEmpty()) {
                    return expression;
                }
                IrBlockImpl irBlockImpl = new IrBlockImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), null, 8, null);
                irBlockImpl.getStatements().add(expression);
                new LocalDeclarationsLowering(LocalClassesInInlineLambdasLowering.this.getContext(), null, null, false, false, false, null, 126, null).lower(irBlockImpl, container, data, linkedHashSet, linkedHashSet3);
                irBlockImpl.getStatements().addAll(0, linkedHashSet);
                for (IrFunction irFunction : arrayList) {
                    final LocalClassesInInlineLambdasLowering localClassesInInlineLambdasLowering = LocalClassesInInlineLambdasLowering.this;
                    IrElementTransformerVoidKt.transformChildrenVoid(irFunction, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.backend.common.lower.inline.LocalClassesInInlineLambdasLowering$lower$1$visitCall$2
                        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
                        public IrStatement visitClass(IrClass declaration) {
                            Intrinsics.checkNotNullParameter(declaration, "declaration");
                            return new IrCompositeImpl(declaration.getStartOffset(), declaration.getEndOffset(), localClassesInInlineLambdasLowering.getContext().getIrBuiltIns().getUnitType(), null, 8, null);
                        }
                    });
                }
                Iterator it2 = linkedHashSet.iterator();
                while (it2.hasNext()) {
                    IrUtilsKt.setDeclarationsParent((IrClass) it2.next(), data);
                }
                return irBlockImpl;
            }
        };
        IrDeclarationParent parent = container instanceof IrDeclarationParent ? (IrDeclarationParent) container : null;
        if (parent == null) {
            parent = container.getParent();
        }
        irBody.transformChildren(irElementTransformer, parent);
    }
}
