package org.jetbrains.kotlin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
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
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
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
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: KtDiagnosticReporterWithImplicitIrBasedContext.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0003J\u001c\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00052\u0006\u0010\n\u001a\u00020\u0002H\u0002J\"\u0010\u000b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0014J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0010\u001a\u00020\u0002H\u0014R&\u0010\u0004\u001a\u001a\u0012\u0004\u0012\u00020\u0002\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/IrBasedSuppressCache;", "Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache;", "Lorg/jetbrains/kotlin/ir/IrElement;", "()V", "annotatedAncestorsPerRoot", "", "annotationKeys", "", "", "ensureRootProcessed", "rootElement", "getClosestAnnotatedAncestorElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "excludeSelf", "", "getSuppressingStrings", "annotated", "AnnotatedTreeVisitor", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrBasedSuppressCache extends AbstractKotlinSuppressCache<IrElement> {
    private final Map<IrElement, Map<IrElement, IrElement>> annotatedAncestorsPerRoot = new LinkedHashMap();
    private final Map<IrElement, Set<String>> annotationKeys = new LinkedHashMap();

    private final synchronized Map<IrElement, IrElement> ensureRootProcessed(IrElement rootElement) {
        Map<IrElement, IrElement> annotatedAncestors;
        Map<IrElement, Map<IrElement, IrElement>> map = this.annotatedAncestorsPerRoot;
        annotatedAncestors = map.get(rootElement);
        if (annotatedAncestors == null) {
            AnnotatedTreeVisitor annotatedTreeVisitor = new AnnotatedTreeVisitor();
            rootElement.accept(annotatedTreeVisitor, new Stack());
            annotatedAncestors = annotatedTreeVisitor.getAnnotatedAncestors();
            map.put(rootElement, annotatedAncestors);
        }
        return annotatedAncestors;
    }

    /* JADX INFO: compiled from: KtDiagnosticReporterWithImplicitIrBasedContext.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0005J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004H\u0002J\u001e\u0010\r\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u00042\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0016R\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/IrBasedSuppressCache$AnnotatedTreeVisitor;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "Ljava/util/Stack;", "Lorg/jetbrains/kotlin/ir/IrElement;", "(Lorg/jetbrains/kotlin/IrBasedSuppressCache;)V", "annotatedAncestors", "", "getAnnotatedAncestors", "()Ljava/util/Map;", "collectSuppressAnnotationKeys", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "visitElement", "data", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class AnnotatedTreeVisitor implements IrElementVisitor<Unit, Stack<IrElement>> {
        private final Map<IrElement, IrElement> annotatedAncestors = new LinkedHashMap();

        public AnnotatedTreeVisitor() {
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitAnonymousInitializer */
        public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Stack<IrElement> stack) {
            visitAnonymousInitializer2(irAnonymousInitializer, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
        public void visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlock */
        public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, Stack<IrElement> stack) {
            visitBlock2(irBlock, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
        public void visitBlock2(IrBlock irBlock, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlockBody */
        public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, Stack<IrElement> stack) {
            visitBlockBody2(irBlockBody, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
        public void visitBlockBody2(IrBlockBody irBlockBody, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBody */
        public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, Stack<IrElement> stack) {
            visitBody2(irBody, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
        public void visitBody2(IrBody irBody, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitBody(this, irBody, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBranch */
        public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, Stack<IrElement> stack) {
            visitBranch2(irBranch, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
        public void visitBranch2(IrBranch irBranch, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreak */
        public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, Stack<IrElement> stack) {
            visitBreak2(irBreak, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
        public void visitBreak2(IrBreak irBreak, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreakContinue */
        public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, Stack<IrElement> stack) {
            visitBreakContinue2(irBreakContinue, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
        public void visitBreakContinue2(IrBreakContinue irBreakContinue, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCall */
        public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, Stack<IrElement> stack) {
            visitCall2(irCall, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
        public void visitCall2(IrCall irCall, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitCall(this, irCall, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, Stack<IrElement> stack) {
            visitCallableReference2((IrCallableReference<?>) irCallableReference, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
        public void visitCallableReference2(IrCallableReference<?> irCallableReference, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCatch */
        public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, Stack<IrElement> stack) {
            visitCatch2(irCatch, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
        public void visitCatch2(IrCatch irCatch, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClass */
        public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, Stack<IrElement> stack) {
            visitClass2(irClass, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
        public void visitClass2(IrClass irClass, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitClass(this, irClass, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClassReference */
        public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, Stack<IrElement> stack) {
            visitClassReference2(irClassReference, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
        public void visitClassReference2(IrClassReference irClassReference, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitComposite */
        public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, Stack<IrElement> stack) {
            visitComposite2(irComposite, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
        public void visitComposite2(IrComposite irComposite, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, Stack<IrElement> stack) {
            visitConst2((IrConst<?>) irConst, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
        public void visitConst2(IrConst<?> irConst, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitConst(this, irConst, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantArray */
        public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, Stack<IrElement> stack) {
            visitConstantArray2(irConstantArray, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
        public void visitConstantArray2(IrConstantArray irConstantArray, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantObject */
        public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, Stack<IrElement> stack) {
            visitConstantObject2(irConstantObject, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
        public void visitConstantObject2(IrConstantObject irConstantObject, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantPrimitive */
        public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Stack<IrElement> stack) {
            visitConstantPrimitive2(irConstantPrimitive, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
        public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantValue */
        public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, Stack<IrElement> stack) {
            visitConstantValue2(irConstantValue, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
        public void visitConstantValue2(IrConstantValue irConstantValue, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructor */
        public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, Stack<IrElement> stack) {
            visitConstructor2(irConstructor, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
        public void visitConstructor2(IrConstructor irConstructor, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, Stack<IrElement> stack) {
            visitConstructorCall2(irConstructorCall, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
        public void visitConstructorCall2(IrConstructorCall irConstructorCall, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContainerExpression */
        public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, Stack<IrElement> stack) {
            visitContainerExpression2(irContainerExpression, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
        public void visitContainerExpression2(IrContainerExpression irContainerExpression, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContinue */
        public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, Stack<IrElement> stack) {
            visitContinue2(irContinue, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
        public void visitContinue2(IrContinue irContinue, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclaration */
        public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, Stack<IrElement> stack) {
            visitDeclaration2(irDeclarationBase, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
        public void visitDeclaration2(IrDeclarationBase irDeclarationBase, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclarationReference */
        public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Stack<IrElement> stack) {
            visitDeclarationReference2(irDeclarationReference, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
        public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDelegatingConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Stack<IrElement> stack) {
            visitDelegatingConstructorCall2(irDelegatingConstructorCall, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
        public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDoWhileLoop */
        public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Stack<IrElement> stack) {
            visitDoWhileLoop2(irDoWhileLoop, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
        public void visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Stack<IrElement> stack) {
            visitDynamicExpression2(irDynamicExpression, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
        public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicMemberExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Stack<IrElement> stack) {
            visitDynamicMemberExpression2(irDynamicMemberExpression, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
        public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicOperatorExpression */
        public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Stack<IrElement> stack) {
            visitDynamicOperatorExpression2(irDynamicOperatorExpression, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
        public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElement */
        public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, Stack<IrElement> stack) {
            visitElement2(irElement, stack);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElseBranch */
        public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, Stack<IrElement> stack) {
            visitElseBranch2(irElseBranch, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
        public void visitElseBranch2(IrElseBranch irElseBranch, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumConstructorCall */
        public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Stack<IrElement> stack) {
            visitEnumConstructorCall2(irEnumConstructorCall, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
        public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumEntry */
        public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, Stack<IrElement> stack) {
            visitEnumEntry2(irEnumEntry, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
        public void visitEnumEntry2(IrEnumEntry irEnumEntry, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorCallExpression */
        public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Stack<IrElement> stack) {
            visitErrorCallExpression2(irErrorCallExpression, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
        public void visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorDeclaration */
        public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Stack<IrElement> stack) {
            visitErrorDeclaration2(irErrorDeclaration, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
        public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorExpression */
        public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, Stack<IrElement> stack) {
            visitErrorExpression2(irErrorExpression, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
        public void visitErrorExpression2(IrErrorExpression irErrorExpression, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpression */
        public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, Stack<IrElement> stack) {
            visitExpression2(irExpression, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
        public void visitExpression2(IrExpression irExpression, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpressionBody */
        public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, Stack<IrElement> stack) {
            visitExpressionBody2(irExpressionBody, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
        public void visitExpressionBody2(IrExpressionBody irExpressionBody, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExternalPackageFragment */
        public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Stack<IrElement> stack) {
            visitExternalPackageFragment2(irExternalPackageFragment, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
        public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitField */
        public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, Stack<IrElement> stack) {
            visitField2(irField, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
        public void visitField2(IrField irField, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitField(this, irField, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFieldAccess */
        public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Stack<IrElement> stack) {
            visitFieldAccess2(irFieldAccessExpression, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
        public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFile */
        public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, Stack<IrElement> stack) {
            visitFile2(irFile, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
        public void visitFile2(IrFile irFile, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitFile(this, irFile, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunction */
        public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, Stack<IrElement> stack) {
            visitFunction2(irFunction, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
        public void visitFunction2(IrFunction irFunction, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionAccess */
        public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Stack<IrElement> stack) {
            visitFunctionAccess2(irFunctionAccessExpression, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
        public void visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionExpression */
        public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Stack<IrElement> stack) {
            visitFunctionExpression2(irFunctionExpression, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
        public void visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionReference */
        public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, Stack<IrElement> stack) {
            visitFunctionReference2(irFunctionReference, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
        public void visitFunctionReference2(IrFunctionReference irFunctionReference, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetClass */
        public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, Stack<IrElement> stack) {
            visitGetClass2(irGetClass, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
        public void visitGetClass2(IrGetClass irGetClass, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetEnumValue */
        public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Stack<IrElement> stack) {
            visitGetEnumValue2(irGetEnumValue, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
        public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetField */
        public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, Stack<IrElement> stack) {
            visitGetField2(irGetField, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
        public void visitGetField2(IrGetField irGetField, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetObjectValue */
        public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Stack<IrElement> stack) {
            visitGetObjectValue2(irGetObjectValue, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
        public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetValue */
        public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, Stack<IrElement> stack) {
            visitGetValue2(irGetValue, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
        public void visitGetValue2(IrGetValue irGetValue, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitInstanceInitializerCall */
        public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Stack<IrElement> stack) {
            visitInstanceInitializerCall2(irInstanceInitializerCall, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
        public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedProperty */
        public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Stack<IrElement> stack) {
            visitLocalDelegatedProperty2(irLocalDelegatedProperty, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
        public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
        public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Stack<IrElement> stack) {
            visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
        public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLoop */
        public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, Stack<IrElement> stack) {
            visitLoop2(irLoop, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
        public void visitLoop2(IrLoop irLoop, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Stack<IrElement> stack) {
            visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
        public void visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitModuleFragment */
        public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, Stack<IrElement> stack) {
            visitModuleFragment2(irModuleFragment, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
        public void visitModuleFragment2(IrModuleFragment irModuleFragment, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPackageFragment */
        public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, Stack<IrElement> stack) {
            visitPackageFragment2(irPackageFragment, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
        public void visitPackageFragment2(IrPackageFragment irPackageFragment, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitProperty */
        public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, Stack<IrElement> stack) {
            visitProperty2(irProperty, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
        public void visitProperty2(IrProperty irProperty, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPropertyReference */
        public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, Stack<IrElement> stack) {
            visitPropertyReference2(irPropertyReference, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
        public void visitPropertyReference2(IrPropertyReference irPropertyReference, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitRawFunctionReference */
        public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Stack<IrElement> stack) {
            visitRawFunctionReference2(irRawFunctionReference, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
        public void visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitReturn */
        public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, Stack<IrElement> stack) {
            visitReturn2(irReturn, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
        public void visitReturn2(IrReturn irReturn, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitScript */
        public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, Stack<IrElement> stack) {
            visitScript2(irScript, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
        public void visitScript2(IrScript irScript, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitScript(this, irScript, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetField */
        public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, Stack<IrElement> stack) {
            visitSetField2(irSetField, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
        public void visitSetField2(IrSetField irSetField, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetValue */
        public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, Stack<IrElement> stack) {
            visitSetValue2(irSetValue, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
        public void visitSetValue2(IrSetValue irSetValue, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSimpleFunction */
        public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Stack<IrElement> stack) {
            visitSimpleFunction2(irSimpleFunction, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
        public void visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSingletonReference */
        public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Stack<IrElement> stack) {
            visitSingletonReference2(irGetSingletonValue, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
        public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSpreadElement */
        public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, Stack<IrElement> stack) {
            visitSpreadElement2(irSpreadElement, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
        public void visitSpreadElement2(IrSpreadElement irSpreadElement, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitStringConcatenation */
        public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Stack<IrElement> stack) {
            visitStringConcatenation2(irStringConcatenation, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
        public void visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspendableExpression */
        public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Stack<IrElement> stack) {
            visitSuspendableExpression2(irSuspendableExpression, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
        public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspensionPoint */
        public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Stack<IrElement> stack) {
            visitSuspensionPoint2(irSuspensionPoint, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
        public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSyntheticBody */
        public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Stack<IrElement> stack) {
            visitSyntheticBody2(irSyntheticBody, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
        public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitThrow */
        public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, Stack<IrElement> stack) {
            visitThrow2(irThrow, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
        public void visitThrow2(IrThrow irThrow, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTry */
        public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, Stack<IrElement> stack) {
            visitTry2(irTry, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
        public void visitTry2(IrTry irTry, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitTry(this, irTry, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeAlias */
        public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, Stack<IrElement> stack) {
            visitTypeAlias2(irTypeAlias, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
        public void visitTypeAlias2(IrTypeAlias irTypeAlias, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeOperator */
        public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Stack<IrElement> stack) {
            visitTypeOperator2(irTypeOperatorCall, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
        public void visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeParameter */
        public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, Stack<IrElement> stack) {
            visitTypeParameter2(irTypeParameter, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
        public void visitTypeParameter2(IrTypeParameter irTypeParameter, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueAccess */
        public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Stack<IrElement> stack) {
            visitValueAccess2(irValueAccessExpression, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
        public void visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueParameter */
        public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, Stack<IrElement> stack) {
            visitValueParameter2(irValueParameter, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
        public void visitValueParameter2(IrValueParameter irValueParameter, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVararg */
        public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, Stack<IrElement> stack) {
            visitVararg2(irVararg, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
        public void visitVararg2(IrVararg irVararg, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVariable */
        public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, Stack<IrElement> stack) {
            visitVariable2(irVariable, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
        public void visitVariable2(IrVariable irVariable, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitVariable(this, irVariable, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhen */
        public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, Stack<IrElement> stack) {
            visitWhen2(irWhen, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
        public void visitWhen2(IrWhen irWhen, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, stack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhileLoop */
        public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, Stack<IrElement> stack) {
            visitWhileLoop2(irWhileLoop, stack);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
        public void visitWhileLoop2(IrWhileLoop irWhileLoop, Stack<IrElement> stack) {
            IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, stack);
        }

        public final Map<IrElement, IrElement> getAnnotatedAncestors() {
            return this.annotatedAncestors;
        }

        /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
        public void visitElement2(IrElement element, Stack<IrElement> data) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(data, "data");
            if (!data.isEmpty()) {
                Map<IrElement, IrElement> map = this.annotatedAncestors;
                IrElement irElementPeek = data.peek();
                Intrinsics.checkNotNullExpressionValue(irElementPeek, "peek(...)");
                map.put(element, irElementPeek);
            }
            boolean zCollectSuppressAnnotationKeys = collectSuppressAnnotationKeys(element);
            if (zCollectSuppressAnnotationKeys) {
                data.push(element);
            }
            element.acceptChildren(this, data);
            if (zCollectSuppressAnnotationKeys) {
                data.pop();
            }
        }

        private final boolean collectSuppressAnnotationKeys(IrElement element) {
            List<IrConstructorCall> annotations;
            IrClass owner;
            ArrayList arrayList = null;
            IrAnnotationContainer irAnnotationContainer = element instanceof IrAnnotationContainer ? (IrAnnotationContainer) element : null;
            if (irAnnotationContainer != null && (annotations = irAnnotationContainer.getAnnotations()) != null) {
                ArrayList<IrConstructorCall> arrayList2 = new ArrayList();
                for (Object obj : annotations) {
                    IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(((IrConstructorCall) obj).getType());
                    if (classOrNull != null && (owner = classOrNull.getOwner()) != null && AdditionalIrUtilsKt.hasEqualFqName(owner, KtDiagnosticReporterWithImplicitIrBasedContextKt.SUPPRESS)) {
                        arrayList2.add(obj);
                    }
                }
                ArrayList arrayList3 = new ArrayList();
                for (IrConstructorCall irConstructorCall : arrayList2) {
                    List listCreateListBuilder = CollectionsKt.createListBuilder();
                    int valueArgumentsCount = irConstructorCall.getValueArgumentsCount();
                    for (int i = 0; i < valueArgumentsCount; i++) {
                        IrExpression valueArgument = irConstructorCall.getValueArgument(i);
                        if (valueArgument instanceof IrConst) {
                            collectSuppressAnnotationKeys$lambda$4$lambda$3$addIfStringConst(listCreateListBuilder, (IrConst) valueArgument);
                        } else if (!(valueArgument instanceof IrConstantArray)) {
                            if (valueArgument instanceof IrVararg) {
                                List<IrVarargElement> elements = ((IrVararg) valueArgument).getElements();
                                ArrayList arrayList4 = new ArrayList();
                                for (Object obj2 : elements) {
                                    if (obj2 instanceof IrConst) {
                                        arrayList4.add(obj2);
                                    }
                                }
                                Iterator it = arrayList4.iterator();
                                while (it.hasNext()) {
                                    collectSuppressAnnotationKeys$lambda$4$lambda$3$addIfStringConst(listCreateListBuilder, (IrConst) it.next());
                                }
                            }
                        } else {
                            List<IrConstantValue> elements2 = ((IrConstantArray) valueArgument).getElements();
                            ArrayList arrayList5 = new ArrayList();
                            for (Object obj3 : elements2) {
                                if (obj3 instanceof IrConstantPrimitive) {
                                    arrayList5.add(obj3);
                                }
                            }
                            Iterator it2 = arrayList5.iterator();
                            while (it2.hasNext()) {
                                collectSuppressAnnotationKeys$lambda$4$lambda$3$addIfStringConst(listCreateListBuilder, ((IrConstantPrimitive) it2.next()).getValue());
                            }
                        }
                    }
                    CollectionsKt.addAll(arrayList3, CollectionsKt.build(listCreateListBuilder));
                }
                ArrayList arrayList6 = arrayList3;
                if (arrayList6.isEmpty()) {
                    arrayList6 = null;
                }
                if (arrayList6 != null) {
                    IrBasedSuppressCache.this.annotationKeys.put(element, CollectionsKt.toSet(arrayList6));
                    arrayList = arrayList6;
                }
            }
            return arrayList != null;
        }

        private static final void collectSuppressAnnotationKeys$lambda$4$lambda$3$addIfStringConst(List<String> list, IrConst<?> irConst) {
            if (Intrinsics.areEqual(irConst.getKind(), IrConstKind.String.INSTANCE)) {
                Object value = irConst.getValue();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.String");
                String lowerCase = ((String) value).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                list.add(lowerCase);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache
    public IrElement getClosestAnnotatedAncestorElement(IrElement element, IrElement rootElement, boolean excludeSelf) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(rootElement, "rootElement");
        return (excludeSelf || !this.annotationKeys.containsKey(element)) ? ensureRootProcessed(rootElement).get(element) : element;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache
    public Set<String> getSuppressingStrings(IrElement annotated) {
        Intrinsics.checkNotNullParameter(annotated, "annotated");
        Set<String> set = this.annotationKeys.get(annotated);
        return set == null ? SetsKt.emptySet() : set;
    }
}
