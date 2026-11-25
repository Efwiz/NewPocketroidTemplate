package org.jetbrains.kotlin.backend.wasm.dce;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.backend.wasm.ir2wasm.DeclarationGeneratorKt;
import org.jetbrains.kotlin.backend.wasm.ir2wasm.TypeTransformerKt;
import org.jetbrains.kotlin.backend.wasm.utils.AnnotationsKt;
import org.jetbrains.kotlin.backend.wasm.utils.WasmArrayInfo;
import org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrResolveUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.TypeTranformerKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.InlineClassesKt;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: WasmUsefulDeclarationProcessor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0014J\u0010\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u001eH\u0014J\u0010\u0010\u001f\u001a\u00020\u00162\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u0011H\u0014J\f\u0010#\u001a\u00020\u0016*\u00020\u0014H\u0002J\u001c\u0010$\u001a\u00020\u0016*\u00020%2\u0006\u0010&\u001a\u00020\u00142\u0006\u0010'\u001a\u00020\u0007H\u0002J\u001c\u0010(\u001a\u00020\u0016*\u00020%2\u0006\u0010&\u001a\u00020\u00142\u0006\u0010'\u001a\u00020\u0007H\u0002J\u0014\u0010)\u001a\u00020\u0016*\u00020*2\u0006\u0010&\u001a\u00020\u0014H\u0002J\u000e\u0010+\u001a\u0004\u0018\u00010%*\u00020%H\u0002R\u0018\u0010\t\u001a\u00060\nR\u00020\u0001X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/dce/WasmUsefulDeclarationProcessor;", "Lorg/jetbrains/kotlin/ir/backend/js/dce/UsefulDeclarationProcessor;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "printReachabilityInfo", "", "dumpReachabilityInfoToFile", "", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;ZLjava/lang/String;)V", "bodyVisitor", "Lorg/jetbrains/kotlin/ir/backend/js/dce/UsefulDeclarationProcessor$BodyVisitorBase;", "getBodyVisitor", "()Lorg/jetbrains/kotlin/ir/backend/js/dce/UsefulDeclarationProcessor$BodyVisitorBase;", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "kotlinClosureToJsClosureConvertFunToKotlinClosureCallFun", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "isExported", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "processClass", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "processConstructor", "irConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "processField", "irField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "processIrFunction", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "processSimpleFunction", "enqueueParentClass", "enqueueRuntimeClassOrAny", "Lorg/jetbrains/kotlin/ir/types/IrType;", TypedValues.TransitionType.S_FROM, CompilerOptions.INFO, "enqueueType", "enqueueValueParameterType", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "getInlinedValueTypeIfAny", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmUsefulDeclarationProcessor extends UsefulDeclarationProcessor {
    private final UsefulDeclarationProcessor.BodyVisitorBase bodyVisitor;
    private final WasmBackendContext context;
    private final Map<IrSimpleFunction, IrSimpleFunction> kotlinClosureToJsClosureConvertFunToKotlinClosureCallFun;

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    public WasmBackendContext getContext() {
        return this.context;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WasmUsefulDeclarationProcessor(WasmBackendContext context, boolean z, String str) {
        super(z, false, str);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        Set<Map.Entry<String, IrSimpleFunction>> setEntrySet = getContext().getKotlinClosureToJsConverters().entrySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Pair pair = TuplesKt.to((IrSimpleFunction) entry.getValue(), getContext().getClosureCallExports().get((String) entry.getKey()));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        this.kotlinClosureToJsClosureConvertFunToKotlinClosureCallFun = linkedHashMap;
        this.bodyVisitor = new UsefulDeclarationProcessor.BodyVisitorBase() { // from class: org.jetbrains.kotlin.backend.wasm.dce.WasmUsefulDeclarationProcessor$bodyVisitor$1
            {
                super();
            }

            @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor.BodyVisitorBase, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall */
            public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, IrDeclaration irDeclaration) {
                visitCall2(irCall, irDeclaration);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor.BodyVisitorBase, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, IrDeclaration irDeclaration) {
                visitConst2((IrConst<?>) irConst, irDeclaration);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor.BodyVisitorBase, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetField */
            public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, IrDeclaration irDeclaration) {
                visitGetField2(irGetField, irDeclaration);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor.BodyVisitorBase, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetField */
            public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, IrDeclaration irDeclaration) {
                visitSetField2(irSetField, irDeclaration);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor.BodyVisitorBase, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVararg */
            public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, IrDeclaration irDeclaration) {
                visitVararg2(irVararg, irDeclaration);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor.BodyVisitorBase, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVariable */
            public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, IrDeclaration irDeclaration) {
                visitVariable2(irVariable, irDeclaration);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor.BodyVisitorBase
            /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
            public void visitConst2(IrConst<?> expression, IrDeclaration data) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(data, "data");
                IrConstKind<?> kind = expression.getKind();
                if (kind instanceof IrConstKind.Null) {
                    this.this$0.enqueueType(expression.getType(), data, "expression type");
                } else if (kind instanceof IrConstKind.String) {
                    WasmUsefulDeclarationProcessor wasmUsefulDeclarationProcessor = this.this$0;
                    UsefulDeclarationProcessor.enqueue$default(wasmUsefulDeclarationProcessor, wasmUsefulDeclarationProcessor.getContext().getWasmSymbols().getStringGetLiteral().getOwner(), data, "String literal intrinsic getter stringGetLiteral", false, 4, null);
                }
            }

            @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor.BodyVisitorBase
            /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
            public void visitVariable2(IrVariable declaration, IrDeclaration data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                Intrinsics.checkNotNullParameter(data, "data");
                this.this$0.enqueueType(declaration.getType(), data, "local variable type");
                super.visitVariable(declaration, data);
            }

            @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor.BodyVisitorBase
            /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
            public void visitVararg2(IrVararg expression, IrDeclaration data) {
                IrConstructor next;
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(data, "data");
                IrClass irClass = IrTypesKt.getClass(expression.getType());
                Intrinsics.checkNotNull(irClass);
                Iterator<IrConstructor> it2 = IrUtilsKt.getConstructors(irClass).iterator();
                do {
                    if (!it2.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it2.next();
                } while (!AnnotationsKt.hasWasmPrimitiveConstructorAnnotation(next));
                IrConstructor irConstructor = next;
                if (irConstructor != null) {
                    UsefulDeclarationProcessor.enqueue$default(this.this$0, irConstructor, data, "implicit vararg constructor", false, 4, null);
                }
                super.visitVararg(expression, data);
            }

            @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor.BodyVisitorBase
            /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
            public void visitSetField2(IrSetField expression, IrDeclaration data) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(data, "data");
                if (IrJsUtilsKt.isObjectInstanceField(expression.getSymbol().getOwner())) {
                    return;
                }
                super.visitSetField(expression, data);
            }

            @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor.BodyVisitorBase
            /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
            public void visitGetField2(IrGetField expression, IrDeclaration data) {
                IrConstructor primaryConstructor;
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(data, "data");
                IrField owner = expression.getSymbol().getOwner();
                if (IrJsUtilsKt.isObjectInstanceField(owner) && (primaryConstructor = IrUtilsKt.getPrimaryConstructor(IrTypesKt.getClassOrFail(owner.getType()).getOwner())) != null) {
                    UsefulDeclarationProcessor.enqueue$default(this.this$0, primaryConstructor, owner, "object lazy initialization", false, 4, null);
                }
                super.visitGetField(expression, data);
            }

            private final boolean tryToProcessIntrinsicCall(IrDeclaration from, IrCall call) {
                IrClass inlinedClass;
                IrSimpleFunctionSymbol symbol = call.getSymbol();
                if (Intrinsics.areEqual(symbol, this.this$0.getContext().getWasmSymbols().getUnboxIntrinsic())) {
                    IrType typeArgument = call.getTypeArgument(0);
                    if (typeArgument != null && !IrTypePredicatesKt.isNothing(typeArgument) && !IrTypePredicatesKt.isNullableNothing(typeArgument)) {
                        IrType typeArgument2 = call.getTypeArgument(1);
                        IrField inlineClassBackingField = (typeArgument2 == null || (inlinedClass = this.this$0.getContext().getInlineClassesUtils().getInlinedClass(typeArgument2)) == null) ? null : InlineClassesKt.getInlineClassBackingField(inlinedClass);
                        if (inlineClassBackingField != null) {
                            UsefulDeclarationProcessor.enqueue$default(this.this$0, inlineClassBackingField, from, "backing inline class field for unboxIntrinsic", false, 4, null);
                        }
                    }
                } else {
                    if (!(Intrinsics.areEqual(symbol, this.this$0.getContext().getWasmSymbols().getWasmTypeId()) ? true : Intrinsics.areEqual(symbol, this.this$0.getContext().getWasmSymbols().getRefCastNull()) ? true : Intrinsics.areEqual(symbol, this.this$0.getContext().getWasmSymbols().getRefTest()) ? true : Intrinsics.areEqual(symbol, this.this$0.getContext().getWasmSymbols().getBoxIntrinsic()) ? true : Intrinsics.areEqual(symbol, this.this$0.getContext().getWasmSymbols().getWasmArrayCopy()))) {
                        return false;
                    }
                    IrType typeArgument3 = call.getTypeArgument(0);
                    if (typeArgument3 != null) {
                        this.this$0.enqueueRuntimeClassOrAny(typeArgument3, from, "intrinsic " + call.getSymbol().getOwner().getName());
                    }
                }
                return true;
            }

            @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor.BodyVisitorBase
            /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
            public void visitCall2(IrCall expression, IrDeclaration data) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(data, "data");
                super.visitCall(expression, data);
                IrSimpleFunction realOverrideTarget = IrResolveUtilsKt.getRealOverrideTarget(expression.getSymbol().getOwner());
                if (tryToProcessIntrinsicCall(data, expression)) {
                    return;
                }
                IrSimpleFunction irSimpleFunction = realOverrideTarget;
                if (!AnnotationsKt.hasWasmNoOpCastAnnotation(irSimpleFunction) && AnnotationsKt.getWasmOpAnnotation(irSimpleFunction) == null) {
                    boolean z2 = expression.getSuperQualifierSymbol() != null;
                    if ((realOverrideTarget instanceof IrSimpleFunction) && IrUtilsKt.isOverridable(realOverrideTarget) && !z2) {
                        IrSimpleFunction irSimpleFunction2 = realOverrideTarget;
                        IrClass parentAsClass = IrUtilsKt.getParentAsClass(irSimpleFunction2);
                        if (IrUtilsKt.isInterface(parentAsClass)) {
                            UsefulDeclarationProcessor.enqueue$default(this.this$0, parentAsClass, data, "receiver class", false, 4, null);
                        }
                        UsefulDeclarationProcessor.enqueue$default(this.this$0, irSimpleFunction2, data, "method call", false, 4, null);
                    }
                }
            }
        };
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    protected UsefulDeclarationProcessor.BodyVisitorBase getBodyVisitor() {
        return this.bodyVisitor;
    }

    private final IrType getInlinedValueTypeIfAny(IrType irType) {
        if (!(Intrinsics.areEqual(irType, getContext().getIrBuiltIns().getBooleanType()) ? true : Intrinsics.areEqual(irType, getContext().getIrBuiltIns().getByteType()) ? true : Intrinsics.areEqual(irType, getContext().getIrBuiltIns().getShortType()) ? true : Intrinsics.areEqual(irType, getContext().getIrBuiltIns().getCharType()) ? true : Intrinsics.areEqual(irType, getContext().getIrBuiltIns().getIntType()) ? true : Intrinsics.areEqual(irType, getContext().getIrBuiltIns().getLongType()) ? true : Intrinsics.areEqual(irType, getContext().getIrBuiltIns().getFloatType()) ? true : Intrinsics.areEqual(irType, getContext().getIrBuiltIns().getDoubleType()) ? true : Intrinsics.areEqual(irType, getContext().getIrBuiltIns().getNothingType()) ? true : Intrinsics.areEqual(irType, getContext().getWasmSymbols().getVoidType())) && !TypeTransformerKt.isBuiltInWasmRefType(irType)) {
            IrClass erasedUpperBound = TypeTranformerKt.getErasedUpperBound(irType);
            if (!(erasedUpperBound != null && erasedUpperBound.getIsExternal())) {
                IrClass inlinedClass = getContext().getInlineClassesUtils().getInlinedClass(irType);
                return inlinedClass == null ? irType : getInlinedValueTypeIfAny(getContext().getInlineClassesUtils().getInlineClassUnderlyingType(inlinedClass));
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void enqueueRuntimeClassOrAny(IrType irType, IrDeclaration irDeclaration, String str) {
        enqueue(TypeTransformerKt.getRuntimeClass(irType, getContext().getIrBuiltIns()), irDeclaration, str, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void enqueueType(IrType irType, IrDeclaration irDeclaration, String str) {
        IrType inlinedValueTypeIfAny = getInlinedValueTypeIfAny(irType);
        if (inlinedValueTypeIfAny != null) {
            enqueueRuntimeClassOrAny(inlinedValueTypeIfAny, irDeclaration, str);
        }
    }

    private final void enqueueParentClass(IrDeclaration irDeclaration) {
        IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(irDeclaration);
        if (parentClassOrNull != null) {
            enqueue(parentClassOrNull, irDeclaration, "parent class", false);
        }
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    protected void processField(IrField irField) {
        Intrinsics.checkNotNullParameter(irField, "irField");
        super.processField(irField);
        IrField irField2 = irField;
        enqueueParentClass(irField2);
        enqueueType(irField.getType(), irField2, "field types");
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    protected void processClass(IrClass irClass) {
        Object next;
        IrType type;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        super.processClass(irClass);
        WasmArrayInfo wasmArrayAnnotation = AnnotationsKt.getWasmArrayAnnotation(irClass);
        if (wasmArrayAnnotation != null && (type = wasmArrayAnnotation.getType()) != null) {
            enqueueType(type, irClass, "array type for wasm array annotated");
        }
        if (getContext().getInlineClassesUtils().isClassInlineLike(irClass)) {
            Iterator<T> it = irClass.getDeclarations().iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!(next instanceof IrConstructor));
            IrConstructor irConstructor = (IrConstructor) next;
            if (irConstructor != null) {
                IrConstructor irConstructor2 = irConstructor.getIsPrimary() ? irConstructor : null;
                if (irConstructor2 != null) {
                    UsefulDeclarationProcessor.enqueue$default(this, irConstructor2, irClass, "inline class primary ctor", false, 4, null);
                }
            }
        }
    }

    private final void enqueueValueParameterType(IrValueParameter irValueParameter, IrDeclaration irDeclaration) {
        if (getContext().getInlineClassesUtils().shouldValueParameterBeBoxed(irValueParameter)) {
            enqueueRuntimeClassOrAny(irValueParameter.getType(), irDeclaration, "function ValueParameterType");
        } else {
            enqueueType(irValueParameter.getType(), irDeclaration, "function ValueParameterType");
        }
    }

    private final void processIrFunction(IrFunction irFunction) {
        IrFunction irFunction2 = irFunction;
        if (IrFakeOverrideUtilsKt.isFakeOverride(irFunction2)) {
            return;
        }
        IrFunction irFunction3 = irFunction;
        if (AnnotationsKt.hasWasmNoOpCastAnnotation(irFunction3) || AnnotationsKt.getWasmOpAnnotation(irFunction3) != null) {
            return;
        }
        Iterator<T> it = DeclarationGeneratorKt.getEffectiveValueParameters(irFunction).iterator();
        while (it.hasNext()) {
            enqueueValueParameterType((IrValueParameter) it.next(), irFunction2);
        }
        enqueueType(irFunction.getReturnType(), irFunction2, "function return type");
        IrSimpleFunction irSimpleFunction = this.kotlinClosureToJsClosureConvertFunToKotlinClosureCallFun.get(irFunction);
        if (irSimpleFunction != null) {
            enqueue(irSimpleFunction, irFunction2, "kotlin closure to JS closure conversion", false);
        }
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    protected void processSimpleFunction(IrSimpleFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        super.processSimpleFunction(irFunction);
        enqueueParentClass(irFunction);
        processIrFunction(irFunction);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    protected void processConstructor(IrConstructor irConstructor) {
        Intrinsics.checkNotNullParameter(irConstructor, "irConstructor");
        super.processConstructor(irConstructor);
        IrClass constructedClass = AdditionalIrUtilsKt.getConstructedClass(irConstructor);
        if (!getContext().getInlineClassesUtils().isClassInlineLike(constructedClass)) {
            processIrFunction(irConstructor);
        }
        if (AnnotationsKt.hasWasmPrimitiveConstructorAnnotation(irConstructor)) {
            for (IrDeclaration irDeclaration : constructedClass.getDeclarations()) {
                if (irDeclaration instanceof IrField) {
                    UsefulDeclarationProcessor.enqueue$default(this, irDeclaration, constructedClass, "preserve all fields for primitive constructors", false, 4, null);
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessor
    protected boolean isExported(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return (declaration instanceof IrFunction) && DeclarationGeneratorKt.isExported((IrFunction) declaration);
    }
}
