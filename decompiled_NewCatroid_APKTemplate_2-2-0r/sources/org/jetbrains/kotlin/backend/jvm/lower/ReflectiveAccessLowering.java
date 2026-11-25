package org.jetbrains.kotlin.backend.jvm.lower;

import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmReflectSymbols;
import org.jetbrains.kotlin.backend.jvm.JvmSymbols;
import org.jetbrains.kotlin.backend.jvm.ir.IrArrayBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocatorKt;
import org.jetbrains.kotlin.backend.jvm.ir.IrInlineScopeResolver;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStatementOriginKt;
import org.jetbrains.kotlin.ir.expressions.impl.IrClassReferenceImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.StubGeneratorExtensions;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ReflectiveAccess.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¼\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\f\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001c\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u001bH\u0002J\u001e\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u001f\u0012\u0006\u0012\u0004\u0018\u00010 0\u001e2\u0006\u0010!\u001a\u00020\bH\u0002J\u0018\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020\b2\u0006\u0010$\u001a\u00020\tH\u0002J\u0010\u0010%\u001a\u00020 2\u0006\u0010!\u001a\u00020&H\u0002J\u0010\u0010'\u001a\u00020 2\u0006\u0010!\u001a\u00020\bH\u0002J\u0010\u0010(\u001a\u00020 2\u0006\u0010!\u001a\u00020\bH\u0002J\u0010\u0010)\u001a\u00020 2\u0006\u0010!\u001a\u00020*H\u0002J\u0010\u0010+\u001a\u00020 2\u0006\u0010,\u001a\u00020-H\u0002J2\u0010+\u001a\u00020 2\u0006\u0010.\u001a\u00020\u001f2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u001f2\b\u00102\u001a\u0004\u0018\u00010 2\u0006\u00103\u001a\u000204H\u0002J\u0010\u00105\u001a\u00020 2\u0006\u00106\u001a\u000207H\u0002J:\u00105\u001a\u00020 2\u0006\u0010.\u001a\u00020\u001f2\u0006\u0010/\u001a\u0002002\u0006\u00108\u001a\u00020 2\u0006\u00109\u001a\u00020\u001f2\b\u00102\u001a\u0004\u0018\u00010 2\u0006\u00103\u001a\u000204H\u0002J\u0010\u0010:\u001a\u00020 2\u0006\u0010!\u001a\u00020\bH\u0002JN\u0010:\u001a\u00020 2\u0006\u0010.\u001a\u00020\u001f2\u0006\u0010;\u001a\u0002002\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001f0=2\b\u0010>\u001a\u0004\u0018\u00010 2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020 0=2\u0006\u0010@\u001a\u00020\u001f2\u0006\u00103\u001a\u000204H\u0002J\u0010\u0010A\u001a\u00020 2\u0006\u0010!\u001a\u00020\bH\u0002J\u0010\u0010B\u001a\u00020 2\u0006\u00106\u001a\u000207H\u0002J\u0010\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020FH\u0016J\u0010\u0010G\u001a\u00020D2\u0006\u0010#\u001a\u00020\bH\u0002J\u0010\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020KH\u0002J\u0010\u0010L\u001a\u00020 2\u0006\u0010#\u001a\u00020\bH\u0016J\u0010\u0010M\u001a\u00020 2\u0006\u0010#\u001a\u00020*H\u0016J\u0010\u0010N\u001a\u00020 2\u0006\u0010#\u001a\u00020-H\u0016J\u0010\u0010O\u001a\u00020 2\u0006\u0010#\u001a\u00020&H\u0016J\u0010\u0010P\u001a\u00020 2\u0006\u0010#\u001a\u000207H\u0016J\"\u0010Q\u001a\u00020 *\u00020R2\u0006\u0010S\u001a\u00020 2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020 0=H\u0002J\u0014\u0010T\u001a\u00020 *\u00020R2\u0006\u0010S\u001a\u00020 H\u0002J\u001c\u0010U\u001a\u00020 *\u00020R2\u0006\u0010V\u001a\u00020 2\u0006\u0010>\u001a\u00020 H\u0002J$\u0010W\u001a\u00020 *\u00020R2\u0006\u0010V\u001a\u00020 2\u0006\u0010>\u001a\u00020 2\u0006\u00108\u001a\u00020 H\u0002J\u0014\u0010X\u001a\u00020 *\u00020R2\u0006\u0010Y\u001a\u00020 H\u0002J\"\u0010Z\u001a\u00020 *\u00020R2\u0006\u0010.\u001a\u00020 2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001f0=H\u0002J\u001c\u0010[\u001a\u00020 *\u00020R2\u0006\u0010.\u001a\u00020 2\u0006\u0010/\u001a\u000200H\u0002J*\u0010\\\u001a\u00020 *\u00020R2\u0006\u0010.\u001a\u00020 2\u0006\u0010;\u001a\u0002002\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001f0=H\u0002J\u0012\u0010]\u001a\b\u0012\u0004\u0012\u00020 0=*\u00020^H\u0002J\"\u0010_\u001a\u00020 *\u00020R2\u0006\u0010`\u001a\u00020\u001f2\f\u0010a\u001a\b\u0012\u0004\u0012\u00020 0=H\u0002J\u0016\u0010b\u001a\u00020I*\u0002042\b\b\u0002\u0010c\u001a\u00020IH\u0002J\u0014\u0010d\u001a\u00020 *\u00020R2\u0006\u0010e\u001a\u00020\u001fH\u0002J*\u0010f\u001a\u00020 *\u00020R2\u0006\u0010g\u001a\u00020 2\u0006\u0010>\u001a\u00020 2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020 0=H\u0002J\u0014\u0010h\u001a\u00020 *\u00020R2\u0006\u0010g\u001a\u00020 H\u0002J\u0012\u0010i\u001a\b\u0012\u0004\u0012\u00020\u001f0=*\u00020^H\u0002R\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006j"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ReflectiveAccessLowering;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "callsOnCompanionObjects", "", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getCallsOnCompanionObjects", "()Ljava/util/Map;", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "inlineScopeResolver", "Lorg/jetbrains/kotlin/backend/jvm/ir/IrInlineScopeResolver;", "getInlineScopeResolver", "()Lorg/jetbrains/kotlin/backend/jvm/ir/IrInlineScopeResolver;", "setInlineScopeResolver", "(Lorg/jetbrains/kotlin/backend/jvm/ir/IrInlineScopeResolver;)V", "reflectSymbols", "Lorg/jetbrains/kotlin/backend/jvm/JvmReflectSymbols;", "symbols", "Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;", "createBuilder", "Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "startOffset", "", "endOffset", "fieldLocationAndReceiver", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "call", "generateInvokeSpecialForCall", "expression", "superQualifier", "generateReflectiveAccessForCompanion", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "generateReflectiveAccessForGetter", "generateReflectiveAccessForSetter", "generateReflectiveConstructorInvocation", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "generateReflectiveFieldGet", "getField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "declaringClass", "fieldName", "", "fieldType", "instance", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "generateReflectiveFieldSet", "setField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "value", ModuleXmlParser.TYPE, "generateReflectiveMethodInvocation", "methodName", "parameterTypes", "", "receiver", "arguments", "returnType", "generateReflectiveStaticCall", "generateThrowIllegalAccessException", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "recordCompanionObjectAsDispatchReceiver", "shouldUseAccessor", "", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitCall", "visitConstructorCall", "visitGetField", "visitGetObjectValue", "visitSetField", "constructorNewInstance", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "constructorSetAccessible", "fieldGet", "fieldObject", "fieldSet", "fieldSetAccessible", "field", TypeProxy.SilentConstruction.Appender.GET_DECLARED_CONSTRUCTOR_METHOD_NAME, "getDeclaredField", "getDeclaredMethod", "getValueArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "irVararg", "elementType", CompoundEntry.NAME_values, "isAccessible", "withSuper", "javaClassObject", "klass", "methodInvoke", "method", "methodSetAccessible", "valueParameterTypes", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReflectiveAccessLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final Map<IrCall, IrClassSymbol> callsOnCompanionObjects;
    private final JvmBackendContext context;
    public IrInlineScopeResolver inlineScopeResolver;
    private final JvmReflectSymbols reflectSymbols;
    private final JvmSymbols symbols;

    public final JvmBackendContext getContext() {
        return this.context;
    }

    public ReflectiveAccessLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.callsOnCompanionObjects = new LinkedHashMap();
        JvmSymbols symbols = context.getIr().getSymbols();
        this.symbols = symbols;
        this.reflectSymbols = symbols.getJavaLangReflectSymbols();
    }

    public final IrInlineScopeResolver getInlineScopeResolver() {
        IrInlineScopeResolver irInlineScopeResolver = this.inlineScopeResolver;
        if (irInlineScopeResolver != null) {
            return irInlineScopeResolver;
        }
        Intrinsics.throwUninitializedPropertyAccessException("inlineScopeResolver");
        return null;
    }

    public final void setInlineScopeResolver(IrInlineScopeResolver irInlineScopeResolver) {
        Intrinsics.checkNotNullParameter(irInlineScopeResolver, "<set-?>");
        this.inlineScopeResolver = irInlineScopeResolver;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        setInlineScopeResolver(IrInlineReferenceLocatorKt.findInlineCallSites(irFile, this.context));
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, this);
    }

    static /* synthetic */ boolean isAccessible$default(ReflectiveAccessLowering reflectiveAccessLowering, IrSymbol irSymbol, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return reflectiveAccessLowering.isAccessible(irSymbol, z);
    }

    private final boolean isAccessible(IrSymbol irSymbol, boolean z) {
        return SyntheticAccessorLowering.INSTANCE.isAccessible(irSymbol, this.context, getCurrentScope(), getInlineScopeResolver(), z, null, true);
    }

    public final Map<IrCall, IrClassSymbol> getCallsOnCompanionObjects() {
        return this.callsOnCompanionObjects;
    }

    private final void recordCompanionObjectAsDispatchReceiver(IrCall expression) {
        IrClassSymbol classOrNull;
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        IrGetField irGetField = dispatchReceiver instanceof IrGetField ? (IrGetField) dispatchReceiver : null;
        if (irGetField == null) {
            return;
        }
        IrType type = irGetField.getSymbol().getOwner().getType();
        IrSimpleType irSimpleType = type instanceof IrSimpleType ? (IrSimpleType) type : null;
        if (irSimpleType == null || (classOrNull = IrTypesKt.getClassOrNull(irSimpleType)) == null || !classOrNull.getOwner().getIsCompanion()) {
            return;
        }
        this.callsOnCompanionObjects.put(expression, classOrNull);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        recordCompanionObjectAsDispatchReceiver(expression);
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        IrClassSymbol superQualifierSymbol = expression.getSuperQualifierSymbol();
        if (isAccessible(expression.getSymbol(), superQualifierSymbol != null)) {
            return expression;
        }
        if (Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.GET_PROPERTY.INSTANCE)) {
            return generateReflectiveAccessForGetter(expression);
        }
        IrStatementOrigin origin = expression.getOrigin();
        if (origin != null && IrStatementOriginKt.isAssignmentOperator(origin)) {
            return generateReflectiveAccessForSetter(expression);
        }
        if (expression.getDispatchReceiver() == null && expression.getExtensionReceiver() == null) {
            return generateReflectiveStaticCall(expression);
        }
        if (superQualifierSymbol != null) {
            return generateInvokeSpecialForCall(expression, superQualifierSymbol);
        }
        return generateReflectiveMethodInvocation(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitGetField(IrGetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        if (isAccessible$default(this, expression.getSymbol(), false, 1, null)) {
            return expression;
        }
        return generateReflectiveFieldGet(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitSetField(IrSetField expression) {
        IrProperty owner;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        IrFieldSymbol symbol = expression.getSymbol();
        if (isAccessible$default(this, symbol, false, 1, null)) {
            return expression;
        }
        IrPropertySymbol correspondingPropertySymbol = symbol.getOwner().getCorrespondingPropertySymbol();
        if ((correspondingPropertySymbol != null && (owner = correspondingPropertySymbol.getOwner()) != null && owner.getIsConst()) || (IrUtilsKt.isFromJava(symbol.getOwner()) && symbol.getOwner().getIsFinal())) {
            return generateThrowIllegalAccessException(expression);
        }
        return generateReflectiveFieldSet(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitConstructorCall(IrConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        if (isAccessible$default(this, expression.getSymbol(), false, 1, null)) {
            return expression;
        }
        return generateReflectiveConstructorInvocation(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitGetObjectValue(IrGetObjectValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        if (isAccessible$default(this, expression.getSymbol(), false, 1, null)) {
            return expression;
        }
        return generateReflectiveAccessForCompanion(expression);
    }

    private final IrExpression javaClassObject(IrBuilderWithScope irBuilderWithScope, IrType irType) {
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBuilderWithScope, this.symbols.getKClassJavaPropertyGetter());
        irFunctionAccessExpressionIrCall.setExtensionReceiver(new IrClassReferenceImpl(irFunctionAccessExpressionIrCall.getStartOffset(), irFunctionAccessExpressionIrCall.getEndOffset(), IrTypesKt.getStarProjectedType(irBuilderWithScope.getContext().getIrBuiltIns().getKClassClass()), irBuilderWithScope.getContext().getIrBuiltIns().getKClassClass(), irType));
        return irFunctionAccessExpressionIrCall;
    }

    private final IrExpression getDeclaredField(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, String str) {
        IrCall irCall = ExpressionHelpersKt.irCall(irBuilderWithScope, this.reflectSymbols.getGetDeclaredField());
        irCall.setDispatchReceiver(irExpression);
        irCall.putValueArgument(0, ExpressionHelpersKt.irString(irBuilderWithScope, str));
        return irCall;
    }

    private final IrExpression fieldSetAccessible(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression) {
        IrCall irCall = ExpressionHelpersKt.irCall(irBuilderWithScope, this.reflectSymbols.getJavaLangReflectFieldSetAccessible());
        irCall.setDispatchReceiver(irExpression);
        irCall.putValueArgument(0, ExpressionHelpersKt.irTrue(irBuilderWithScope));
        return irCall;
    }

    private final IrExpression fieldSet(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, IrExpression irExpression2, IrExpression irExpression3) {
        IrCall irCall = ExpressionHelpersKt.irCall(irBuilderWithScope, this.reflectSymbols.getJavaLangReflectFieldSet());
        irCall.setDispatchReceiver(irExpression);
        irCall.putValueArgument(0, irExpression2);
        irCall.putValueArgument(1, irExpression3);
        return irCall;
    }

    private final IrExpression fieldGet(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, IrExpression irExpression2) {
        IrCall irCall = ExpressionHelpersKt.irCall(irBuilderWithScope, this.reflectSymbols.getJavaLangReflectFieldGet());
        irCall.setDispatchReceiver(irExpression);
        irCall.putValueArgument(0, irExpression2);
        return irCall;
    }

    static /* synthetic */ JvmIrBuilder createBuilder$default(ReflectiveAccessLowering reflectiveAccessLowering, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = -1;
        }
        if ((i3 & 2) != 0) {
            i2 = -1;
        }
        return reflectiveAccessLowering.createBuilder(i, i2);
    }

    private final JvmIrBuilder createBuilder(int startOffset, int endOffset) {
        JvmBackendContext jvmBackendContext = this.context;
        ScopeWithIr currentScope = getCurrentScope();
        Intrinsics.checkNotNull(currentScope);
        return JvmIrBuilderKt.createJvmIrBuilder(jvmBackendContext, currentScope, startOffset, endOffset);
    }

    private final IrExpression irVararg(IrBuilderWithScope irBuilderWithScope, IrType irType, List<? extends IrExpression> list) {
        IrArrayBuilder irArrayBuilder = new IrArrayBuilder(createBuilder$default(this, 0, 0, 3, null), IrTypesKt.typeWith(irBuilderWithScope.getContext().getIrBuiltIns().getArrayClass(), irType));
        Iterator<? extends IrExpression> it = list.iterator();
        while (it.hasNext()) {
            irArrayBuilder.unaryPlus(it.next());
        }
        return irArrayBuilder.build();
    }

    private final IrExpression getDeclaredMethod(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, String str, List<? extends IrType> list) {
        IrCall irCall = ExpressionHelpersKt.irCall(irBuilderWithScope, this.reflectSymbols.getGetDeclaredMethod());
        irCall.setDispatchReceiver(irExpression);
        irCall.putValueArgument(0, ExpressionHelpersKt.irString(irBuilderWithScope, str));
        IrType defaultType = IrTypesKt.getDefaultType(this.symbols.getJavaLangClass());
        List<? extends IrType> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(javaClassObject(irBuilderWithScope, (IrType) it.next()));
        }
        irCall.putValueArgument(1, irVararg(irBuilderWithScope, defaultType, arrayList));
        return irCall;
    }

    private final IrExpression methodSetAccessible(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression) {
        IrCall irCall = ExpressionHelpersKt.irCall(irBuilderWithScope, this.reflectSymbols.getJavaLangReflectMethodSetAccessible());
        irCall.setDispatchReceiver(irExpression);
        irCall.putValueArgument(0, ExpressionHelpersKt.irTrue(irBuilderWithScope));
        return irCall;
    }

    private final IrExpression methodInvoke(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, IrExpression irExpression2, List<? extends IrExpression> list) {
        IrCall irCall = ExpressionHelpersKt.irCall(irBuilderWithScope, this.reflectSymbols.getJavaLangReflectMethodInvoke());
        irCall.setDispatchReceiver(irExpression);
        irCall.putValueArgument(0, irExpression2);
        irCall.putValueArgument(1, irVararg(irBuilderWithScope, irBuilderWithScope.getContext().getIrBuiltIns().getAnyNType(), list));
        return irCall;
    }

    private final IrExpression getDeclaredConstructor(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, List<? extends IrType> list) {
        IrCall irCall = ExpressionHelpersKt.irCall(irBuilderWithScope, this.reflectSymbols.getGetDeclaredConstructor());
        irCall.setDispatchReceiver(irExpression);
        IrType defaultType = IrTypesKt.getDefaultType(this.symbols.getJavaLangClass());
        List<? extends IrType> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(javaClassObject(irBuilderWithScope, (IrType) it.next()));
        }
        irCall.putValueArgument(0, irVararg(irBuilderWithScope, defaultType, arrayList));
        return irCall;
    }

    private final IrExpression constructorSetAccessible(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression) {
        IrCall irCall = ExpressionHelpersKt.irCall(irBuilderWithScope, this.reflectSymbols.getJavaLangReflectConstructorSetAccessible());
        irCall.setDispatchReceiver(irExpression);
        irCall.putValueArgument(0, ExpressionHelpersKt.irTrue(irBuilderWithScope));
        return irCall;
    }

    private final IrExpression constructorNewInstance(IrBuilderWithScope irBuilderWithScope, IrExpression irExpression, List<? extends IrExpression> list) {
        IrCall irCall = ExpressionHelpersKt.irCall(irBuilderWithScope, this.reflectSymbols.getJavaLangReflectConstructorNewInstance());
        irCall.setDispatchReceiver(irExpression);
        irCall.putValueArgument(0, irVararg(irBuilderWithScope, irBuilderWithScope.getContext().getIrBuiltIns().getAnyNType(), list));
        return irCall;
    }

    private final IrExpression generateReflectiveMethodInvocation(IrType declaringClass, String methodName, List<? extends IrType> parameterTypes, IrExpression receiver, List<? extends IrExpression> arguments, IrType returnType, IrSymbol symbol) {
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.context, symbol, 0, 0, 6, null);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder$default.getContext(), jvmIrBuilderCreateJvmIrBuilder$default.getScope(), jvmIrBuilderCreateJvmIrBuilder$default.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder$default.getEndOffset(), null, returnType, false, 64, null);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder, getDeclaredMethod(irBlockBuilder2, javaClassObject(irBlockBuilder2, declaringClass), methodName, parameterTypes), "method", false, (IrDeclarationOrigin) null, IrTypesKt.getDefaultType(this.reflectSymbols.getJavaLangReflectMethod()), 12, (Object) null);
        irBlockBuilder.unaryPlus(methodSetAccessible(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableCreateTmpVariable$default)));
        irBlockBuilder.unaryPlus(methodInvoke(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableCreateTmpVariable$default), receiver == null ? ExpressionHelpersKt.irNull(irBlockBuilder2) : receiver, arguments));
        return irBlockBuilder.getIrBlockBody();
    }

    private final List<IrExpression> getValueArguments(IrFunctionAccessExpression irFunctionAccessExpression) {
        IntRange intRangeUntil = RangesKt.until(0, irFunctionAccessExpression.getValueArgumentsCount());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
        Iterator<Integer> it = intRangeUntil.iterator();
        while (it.hasNext()) {
            IrExpression valueArgument = irFunctionAccessExpression.getValueArgument(((IntIterator) it).nextInt());
            Intrinsics.checkNotNull(valueArgument);
            arrayList.add(valueArgument);
        }
        return arrayList;
    }

    private final List<IrType> valueParameterTypes(IrFunctionAccessExpression irFunctionAccessExpression) {
        List<IrValueParameter> valueParameters = irFunctionAccessExpression.getSymbol().getOwner().getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrValueParameter) it.next()).getType());
        }
        return arrayList;
    }

    private final IrExpression generateReflectiveMethodInvocation(IrCall call) {
        IrSimpleType type;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (call.getExtensionReceiver() != null) {
            IrValueParameter extensionReceiverParameter = call.getSymbol().getOwner().getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                arrayList.add(extensionReceiverParameter.getType());
            }
            IrExpression extensionReceiver = call.getExtensionReceiver();
            if (extensionReceiver != null) {
                arrayList2.add(extensionReceiver);
            }
        } else if (call.getDispatchReceiver() != null && Intrinsics.areEqual(call.getSymbol().getOwner().getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE)) {
            IrValueParameter dispatchReceiverParameter = call.getSymbol().getOwner().getDispatchReceiverParameter();
            if (dispatchReceiverParameter != null) {
                arrayList.add(dispatchReceiverParameter.getType());
            }
            IrExpression dispatchReceiver = call.getDispatchReceiver();
            if (dispatchReceiver != null) {
                arrayList2.add(dispatchReceiver);
            }
        }
        IrCall irCall = call;
        arrayList.addAll(valueParameterTypes(irCall));
        arrayList2.addAll(getValueArguments(irCall));
        IrClassSymbol superQualifierSymbol = call.getSuperQualifierSymbol();
        if (superQualifierSymbol == null || (type = IrTypesKt.getDefaultType(superQualifierSymbol)) == null) {
            IrExpression dispatchReceiver2 = call.getDispatchReceiver();
            type = dispatchReceiver2 != null ? dispatchReceiver2.getType() : IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(call.getSymbol().getOwner()));
        }
        IrType irType = type;
        String strAsString = call.getSymbol().getOwner().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return generateReflectiveMethodInvocation(irType, strAsString, arrayList, call.getDispatchReceiver(), arrayList2, call.getType(), call.getSymbol());
    }

    private final IrExpression generateReflectiveStaticCall(IrCall call) {
        call.getDispatchReceiver();
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(call.getSymbol().getOwner()));
        String strAsString = call.getSymbol().getOwner().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        IrCall irCall = call;
        return generateReflectiveMethodInvocation(defaultType, strAsString, valueParameterTypes(irCall), null, getValueArguments(irCall), call.getType(), call.getSymbol());
    }

    private final IrExpression generateReflectiveConstructorInvocation(IrConstructorCall call) {
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.context, call.getSymbol(), 0, 0, 6, null);
        IrType type = call.getType();
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder$default.getContext(), jvmIrBuilderCreateJvmIrBuilder$default.getScope(), jvmIrBuilderCreateJvmIrBuilder$default.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder$default.getEndOffset(), null, type, false, 64, null);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrConstructorCall irConstructorCall = call;
        IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder, getDeclaredConstructor(irBlockBuilder2, javaClassObject(irBlockBuilder2, IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(call.getSymbol().getOwner()))), valueParameterTypes(irConstructorCall)), JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, false, (IrDeclarationOrigin) null, IrTypesKt.getDefaultType(this.reflectSymbols.getJavaLangReflectConstructor()), 12, (Object) null);
        irBlockBuilder.unaryPlus(constructorSetAccessible(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableCreateTmpVariable$default)));
        irBlockBuilder.unaryPlus(constructorNewInstance(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableCreateTmpVariable$default), getValueArguments(irConstructorCall)));
        return irBlockBuilder.getIrBlockBody();
    }

    private final IrExpression generateReflectiveFieldGet(IrType declaringClass, String fieldName, IrType fieldType, IrExpression instance, IrSymbol symbol) {
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.context, symbol, 0, 0, 6, null);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder$default.getContext(), jvmIrBuilderCreateJvmIrBuilder$default.getScope(), jvmIrBuilderCreateJvmIrBuilder$default.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder$default.getEndOffset(), null, fieldType, false, 64, null);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrBlockBuilder irBlockBuilder3 = irBlockBuilder;
        IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder2, javaClassObject(irBlockBuilder3, declaringClass), "klass", false, (IrDeclarationOrigin) null, this.symbols.getKClassJavaPropertyGetter().getReturnType(), 12, (Object) null);
        IrVariable irVariableCreateTmpVariable$default2 = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder2, getDeclaredField(irBlockBuilder3, ExpressionHelpersKt.irGet(irBlockBuilder3, irVariableCreateTmpVariable$default), fieldName), "field", false, (IrDeclarationOrigin) null, IrTypesKt.getDefaultType(this.reflectSymbols.getJavaLangReflectField()), 12, (Object) null);
        irBlockBuilder.unaryPlus(fieldSetAccessible(irBlockBuilder3, ExpressionHelpersKt.irGet(irBlockBuilder3, irVariableCreateTmpVariable$default2)));
        irBlockBuilder.unaryPlus(fieldGet(irBlockBuilder3, ExpressionHelpersKt.irGet(irBlockBuilder3, irVariableCreateTmpVariable$default2), instance == null ? ExpressionHelpersKt.irGet(irBlockBuilder3, irVariableCreateTmpVariable$default) : instance));
        return irBlockBuilder.getIrBlockBody();
    }

    private final IrExpression generateReflectiveFieldGet(IrGetField getField) {
        IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(getField.getSymbol().getOwner());
        Intrinsics.checkNotNull(parentClassOrNull);
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(parentClassOrNull);
        String strAsString = getField.getSymbol().getOwner().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return generateReflectiveFieldGet(defaultType, strAsString, getField.getType(), getField.getReceiver(), getField.getSymbol());
    }

    private final IrExpression generateReflectiveFieldSet(IrType declaringClass, String fieldName, IrExpression value, IrType type, IrExpression instance, IrSymbol symbol) {
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.context, symbol, 0, 0, 6, null);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder$default.getContext(), jvmIrBuilderCreateJvmIrBuilder$default.getScope(), jvmIrBuilderCreateJvmIrBuilder$default.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder$default.getEndOffset(), null, type, false, 64, null);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder, getDeclaredField(irBlockBuilder2, javaClassObject(irBlockBuilder2, declaringClass), fieldName), "field", false, (IrDeclarationOrigin) null, IrTypesKt.getDefaultType(this.reflectSymbols.getJavaLangReflectField()), 12, (Object) null);
        irBlockBuilder.unaryPlus(fieldSetAccessible(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableCreateTmpVariable$default)));
        irBlockBuilder.unaryPlus(fieldSet(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableCreateTmpVariable$default), instance == null ? ExpressionHelpersKt.irNull(irBlockBuilder2) : instance, value));
        return irBlockBuilder.getIrBlockBody();
    }

    private final IrExpression generateReflectiveFieldSet(IrSetField setField) {
        IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(setField.getSymbol().getOwner());
        Intrinsics.checkNotNull(parentClassOrNull);
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(parentClassOrNull);
        String strAsString = setField.getSymbol().getOwner().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return generateReflectiveFieldSet(defaultType, strAsString, setField.getValue(), setField.getType(), setField.getReceiver(), setField.getSymbol());
    }

    private final boolean shouldUseAccessor(IrSimpleFunction accessor) {
        Object generatorExtensions = this.context.getGeneratorExtensions();
        Intrinsics.checkNotNull(generatorExtensions, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.util.StubGeneratorExtensions");
        return ((StubGeneratorExtensions) generatorExtensions).isAccessorWithExplicitImplementation(accessor);
    }

    private final Pair<IrType, IrExpression> fieldLocationAndReceiver(IrCall call) {
        IrClassSymbol irClassSymbol = this.callsOnCompanionObjects.get(call);
        if (irClassSymbol != null) {
            IrClass parentAsClass = IrUtilsKt.getParentAsClass(irClassSymbol.getOwner());
            if (!JvmIrUtilsKt.isJvmInterface(parentAsClass)) {
                return TuplesKt.to(IrUtilsKt.getDefaultType(parentAsClass), null);
            }
        }
        IrExpression dispatchReceiver = call.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        IrType type = dispatchReceiver.getType();
        IrExpression dispatchReceiver2 = call.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver2);
        return TuplesKt.to(type, dispatchReceiver2);
    }

    private final IrExpression generateReflectiveAccessForGetter(IrCall call) {
        List<? extends IrType> listEmptyList;
        IrSimpleFunction owner = call.getSymbol().getOwner();
        IrPropertySymbol correspondingPropertySymbol = owner.getCorrespondingPropertySymbol();
        Intrinsics.checkNotNull(correspondingPropertySymbol);
        IrProperty owner2 = correspondingPropertySymbol.getOwner();
        if (shouldUseAccessor(owner)) {
            IrSimpleType defaultType = IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(owner));
            String strAsString = owner2.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            String str = JvmAbi.getterName(strAsString);
            IrValueParameter extensionReceiverParameter = owner.getExtensionReceiverParameter();
            if (extensionReceiverParameter == null || (listEmptyList = CollectionsKt.listOf(extensionReceiverParameter.getType())) == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            return generateReflectiveMethodInvocation(defaultType, str, listEmptyList, call.getDispatchReceiver(), CollectionsKt.listOfNotNull(call.getExtensionReceiver()), owner.getReturnType(), call.getSymbol());
        }
        Pair<IrType, IrExpression> pairFieldLocationAndReceiver = fieldLocationAndReceiver(call);
        IrType irTypeComponent1 = pairFieldLocationAndReceiver.component1();
        IrExpression irExpressionComponent2 = pairFieldLocationAndReceiver.component2();
        String strAsString2 = owner2.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        return generateReflectiveFieldGet(irTypeComponent1, strAsString2, owner.getReturnType(), irExpressionComponent2, call.getSymbol());
    }

    private final IrExpression generateReflectiveAccessForSetter(IrCall call) {
        IrSimpleFunction owner = call.getSymbol().getOwner();
        IrPropertySymbol correspondingPropertySymbol = owner.getCorrespondingPropertySymbol();
        Intrinsics.checkNotNull(correspondingPropertySymbol);
        IrProperty owner2 = correspondingPropertySymbol.getOwner();
        if (shouldUseAccessor(owner)) {
            IrSimpleType defaultType = IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(owner));
            String strAsString = owner2.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            String str = JvmAbi.setterName(strAsString);
            ArrayList arrayList = new ArrayList();
            IrValueParameter extensionReceiverParameter = owner.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                arrayList.add(extensionReceiverParameter.getType());
            }
            IrCall irCall = call;
            arrayList.addAll(valueParameterTypes(irCall));
            Unit unit = Unit.INSTANCE;
            IrExpression dispatchReceiver = call.getDispatchReceiver();
            ArrayList arrayList2 = new ArrayList();
            IrExpression extensionReceiver = call.getExtensionReceiver();
            if (extensionReceiver != null) {
                arrayList2.add(extensionReceiver);
            }
            arrayList2.addAll(getValueArguments(irCall));
            Unit unit2 = Unit.INSTANCE;
            return generateReflectiveMethodInvocation(defaultType, str, arrayList, dispatchReceiver, arrayList2, owner.getReturnType(), call.getSymbol());
        }
        Pair<IrType, IrExpression> pairFieldLocationAndReceiver = fieldLocationAndReceiver(call);
        IrType irTypeComponent1 = pairFieldLocationAndReceiver.component1();
        IrExpression irExpressionComponent2 = pairFieldLocationAndReceiver.component2();
        String strAsString2 = owner2.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        IrExpression valueArgument = call.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        return generateReflectiveFieldSet(irTypeComponent1, strAsString2, valueArgument, call.getType(), irExpressionComponent2, call.getSymbol());
    }

    private final IrExpression generateThrowIllegalAccessException(IrSetField setField) {
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.context, setField.getSymbol(), 0, 0, 6, null);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilderCreateJvmIrBuilder$default.getContext(), jvmIrBuilderCreateJvmIrBuilder$default.getScope(), jvmIrBuilderCreateJvmIrBuilder$default.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder$default.getEndOffset(), null, null, false, 64, null);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, this.symbols.getThrowIllegalAccessException());
        irCall.putValueArgument(0, ExpressionHelpersKt.irString(irBlockBuilder2, "Can not set final field"));
        irBlockBuilder.unaryPlus(irCall);
        return irBlockBuilder.getIrBlockBody();
    }

    private final IrExpression generateInvokeSpecialForCall(IrCall expression, IrClassSymbol superQualifier) {
        JvmMethodSignature jvmMethodSignatureMapSignatureSkipGeneric = this.context.getDefaultMethodSignatureMapper().mapSignatureSkipGeneric(expression.getSymbol().getOwner());
        IrClass owner = superQualifier.getOwner();
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.context, expression.getSymbol(), 0, 0, 6, null);
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilderCreateJvmIrBuilder$default, this.symbols.getJvmDebuggerInvokeSpecialIntrinsic());
        irCall.setDispatchReceiver(expression.getDispatchReceiver());
        irCall.setType(expression.getSymbol().getOwner().getReturnType());
        irCall.putValueArgument(0, ExpressionHelpersKt.irString(jvmIrBuilderCreateJvmIrBuilder$default, new StringBuilder().append(AdditionalIrUtilsKt.getPackageFqName(owner)).append('/').append(owner.getName()).toString()));
        String name2 = jvmMethodSignatureMapSignatureSkipGeneric.getAsmMethod().getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        irCall.putValueArgument(1, ExpressionHelpersKt.irString(jvmIrBuilderCreateJvmIrBuilder$default, name2));
        String descriptor = jvmMethodSignatureMapSignatureSkipGeneric.getAsmMethod().getDescriptor();
        Intrinsics.checkNotNullExpressionValue(descriptor, "getDescriptor(...)");
        irCall.putValueArgument(2, ExpressionHelpersKt.irString(jvmIrBuilderCreateJvmIrBuilder$default, descriptor));
        irCall.putValueArgument(3, ExpressionHelpersKt.irFalse(jvmIrBuilderCreateJvmIrBuilder$default));
        return irCall;
    }

    private final IrExpression generateReflectiveAccessForCompanion(IrGetObjectValue call) {
        return generateReflectiveFieldGet(IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(call.getSymbol().getOwner())), "Companion", call.getType(), null, call.getSymbol());
    }
}
