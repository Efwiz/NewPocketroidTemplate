package org.jetbrains.kotlin.ir.interpreter;

import androidx.exifinterface.media.ExifInterface;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.description.type.TypeDescription;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.interpreter.exceptions.ExceptionUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.proxy.ProxyKt;
import org.jetbrains.kotlin.ir.interpreter.stack.CallStack;
import org.jetbrains.kotlin.ir.interpreter.state.Common;
import org.jetbrains.kotlin.ir.interpreter.state.Primitive;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateKt;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeBuilder;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.DescriptorsRemapper;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000ò\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u0012\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0000\u001a\f\u0010\u0018\u001a\u00020\u0017*\u00020\u0019H\u0000\u001a\u0016\u0010\u001a\u001a\u00020\u0017*\u0004\u0018\u00010\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0000\u001a\f\u0010\u001d\u001a\u00020\t*\u00020\u0015H\u0000\u001a\u0012\u0010\u001e\u001a\u00020\u001f*\u00020 2\u0006\u0010!\u001a\u00020\u0001\u001a2\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010$0#*\u00020\u000f2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020*0#H\u0000\u001a\f\u0010+\u001a\u00020\t*\u00020\u000fH\u0000\u001a&\u0010,\u001a\u0004\u0018\u00010-*\u00020.2\u0006\u0010/\u001a\u00020\u000f2\u000e\u00100\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001010#H\u0000\u001a\u000e\u00102\u001a\u0004\u0018\u000103*\u00020\u000fH\u0000\u001a\u000e\u00104\u001a\u0004\u0018\u00010\t*\u00020 H\u0000\u001a\u000e\u00105\u001a\u0004\u0018\u000103*\u00020\u000fH\u0000\u001a\n\u00106\u001a\u00020\u000f*\u00020\u000f\u001a\f\u00107\u001a\u00020\u000f*\u000208H\u0000\u001a\n\u00109\u001a\u00020\u000f*\u00020\u000f\u001a\f\u0010:\u001a\u00020\t*\u00020\u0015H\u0000\u001a\u0014\u0010;\u001a\u00020\u000e*\u00020<2\u0006\u0010=\u001a\u00020\tH\u0000\u001a\u000e\u0010>\u001a\u0004\u0018\u00010?*\u00020\u000fH\u0000\u001a\f\u0010@\u001a\u00020\u000f*\u00020<H\u0000\u001a\f\u0010A\u001a\u00020B*\u000208H\u0000\u001a \u0010C\u001a\u00020\u0015*\u00020\u00152\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u00020F\u0012\u0004\u0012\u00020\u00150EH\u0000\u001a\u0014\u0010C\u001a\u00020\u0015*\u00020\u00152\u0006\u0010G\u001a\u00020HH\u0000\u001a\u0014\u0010I\u001a\u0004\u0018\u00010\u0015*\u0002082\u0006\u0010J\u001a\u00020K\u001a\u0014\u0010L\u001a\u00020\u0017*\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\u0001\u001a\f\u0010M\u001a\u00020\u0017*\u00020\u000fH\u0000\u001a\f\u0010N\u001a\u00020\t*\u00020<H\u0000\u001a\u000e\u0010O\u001a\u00020\u0017*\u0004\u0018\u00010-H\u0000\u001a\f\u0010P\u001a\u00020\u0017*\u00020\u000fH\u0000\u001a\f\u0010Q\u001a\u00020\u0017*\u00020\u0015H\u0000\u001a\f\u0010R\u001a\u00020\u0017*\u00020<H\u0000\u001a\f\u0010S\u001a\u00020\u0017*\u00020\u0015H\u0000\u001a \u0010T\u001a\u000e\u0012\u0004\u0012\u00020V\u0012\u0004\u0012\u00020W0U*\u00020\u001c2\u0006\u0010X\u001a\u000208H\u0000\u001a\u000e\u0010Y\u001a\u00020\t*\u0004\u0018\u00010$H\u0000\u001a\u001e\u0010Z\u001a\b\u0012\u0004\u0012\u0002H\\0[\"\u0004\b\u0000\u0010\\*\b\u0012\u0004\u0012\u0002H\\0]H\u0000\u001a \u0010^\u001a\u0006\u0012\u0002\b\u00030[*\n\u0012\u0006\u0012\u0004\u0018\u00010$0#2\u0006\u0010_\u001a\u00020\u0015H\u0000\u001a\u0014\u0010`\u001a\u00020a*\u00020b2\u0006\u0010c\u001a\u00020dH\u0000\u001a\f\u0010e\u001a\u00020\t*\u00020*H\u0000\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003\"\u0011\u0010\u0006\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0003\"\u0018\u0010\b\u001a\u00020\t*\u00020\n8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u000f8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006f"}, d2 = {"compileTimeAnnotation", "Lorg/jetbrains/kotlin/name/FqName;", "getCompileTimeAnnotation", "()Lorg/jetbrains/kotlin/name/FqName;", "evaluateIntrinsicAnnotation", "getEvaluateIntrinsicAnnotation", "intrinsicConstEvaluationAnnotation", "getIntrinsicConstEvaluationAnnotation", "fqName", "", "Lorg/jetbrains/kotlin/ir/IrElement;", "getFqName", "(Lorg/jetbrains/kotlin/ir/IrElement;)Ljava/lang/String;", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getProperty", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getPrimitiveClass", "Ljava/lang/Class;", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "asObject", "", "accessesTopLevelOrObjectField", "Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "checkCast", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "fqNameWithNullability", "getAnnotation", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "annotation", "getArgsForMethodInvocation", "", "", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "methodType", "Ljava/lang/invoke/MethodType;", "args", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "getCapitalizedFileName", "getDefaultWithActualParameters", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "newParent", "actualParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "getDispatchReceiver", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "getEvaluateIntrinsicValue", "getExtensionReceiver", "getFirstNonInterfaceOverridden", "getFunctionThatContainsDefaults", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "getLastOverridden", "getOnlyName", "getOriginalPropertyByName", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "name", "getReceiver", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getSingleAbstractMethod", "getThisReceiver", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "getTypeIfReified", "getType", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "callStack", "Lorg/jetbrains/kotlin/ir/interpreter/stack/CallStack;", "getVarargType", "index", "", "hasAnnotation", "hasFunInterfaceParent", "internalName", "isAccessToNotNullableObject", "isAccessorOfPropertyWithBackingField", "isPrimitiveArray", "isSubclassOfThrowable", "isUnsignedArray", "loadReifiedTypeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KTypeState;", "expression", "specialToStringForJs", "toPrimitive", "Lorg/jetbrains/kotlin/ir/interpreter/state/Primitive;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "toPrimitiveStateArray", ModuleXmlParser.TYPE, "toState", "Lorg/jetbrains/kotlin/ir/interpreter/state/Common;", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "unsignedToString", "ir.interpreter"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    private static final FqName intrinsicConstEvaluationAnnotation = new FqName("kotlin.internal.IntrinsicConstEvaluation");
    private static final FqName compileTimeAnnotation = new FqName("kotlin.CompileTimeCalculation");
    private static final FqName evaluateIntrinsicAnnotation = new FqName("kotlin.EvaluateIntrinsic");

    /* JADX INFO: compiled from: Utils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[PrimitiveType.values().length];
            try {
                iArr[PrimitiveType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PrimitiveType.CHAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[PrimitiveType.BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[PrimitiveType.SHORT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[PrimitiveType.INT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[PrimitiveType.FLOAT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[PrimitiveType.LONG.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[PrimitiveType.DOUBLE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[SimpleTypeNullability.values().length];
            try {
                iArr2[SimpleTypeNullability.MARKED_NULLABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[SimpleTypeNullability.NOT_SPECIFIED.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[SimpleTypeNullability.DEFINITELY_NOT_NULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public static final FqName getIntrinsicConstEvaluationAnnotation() {
        return intrinsicConstEvaluationAnnotation;
    }

    public static final FqName getCompileTimeAnnotation() {
        return compileTimeAnnotation;
    }

    public static final FqName getEvaluateIntrinsicAnnotation() {
        return evaluateIntrinsicAnnotation;
    }

    public static final String getFqName(IrElement irElement) {
        FqName fqNameWhenAvailable;
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        String strAsString = null;
        IrDeclarationWithName irDeclarationWithName = irElement instanceof IrDeclarationWithName ? (IrDeclarationWithName) irElement : null;
        if (irDeclarationWithName != null && (fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irDeclarationWithName)) != null) {
            strAsString = fqNameWhenAvailable.asString();
        }
        return strAsString == null ? "" : strAsString;
    }

    public static final IrValueParameterSymbol getDispatchReceiver(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
            return dispatchReceiverParameter.getSymbol();
        }
        return null;
    }

    public static final IrValueParameterSymbol getExtensionReceiver(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrValueParameter extensionReceiverParameter = irFunction.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            return extensionReceiverParameter.getSymbol();
        }
        return null;
    }

    public static final IrSymbol getReceiver(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrValueParameterSymbol dispatchReceiver = getDispatchReceiver(irFunction);
        return dispatchReceiver != null ? dispatchReceiver : getExtensionReceiver(irFunction);
    }

    public static final IrValueSymbol getThisReceiver(IrFunctionAccessExpression irFunctionAccessExpression) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        IrValueParameter thisReceiver = IrUtilsKt.getParentAsClass(irFunctionAccessExpression.getSymbol().getOwner()).getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        return thisReceiver.getSymbol();
    }

    public static final <T> Primitive<T> toPrimitive(IrConst<T> irConst) {
        Intrinsics.checkNotNullParameter(irConst, "<this>");
        if (IrTypePredicatesKt.isByte(irConst.getType())) {
            T value = irConst.getValue();
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Number");
            return new Primitive<>(Byte.valueOf(((Number) value).byteValue()), irConst.getType());
        }
        if (!IrTypePredicatesKt.isShort(irConst.getType())) {
            return new Primitive<>(irConst.getValue(), irConst.getType());
        }
        T value2 = irConst.getValue();
        Intrinsics.checkNotNull(value2, "null cannot be cast to non-null type kotlin.Number");
        return new Primitive<>(Short.valueOf(((Number) value2).shortValue()), irConst.getType());
    }

    public static final boolean hasAnnotation(IrAnnotationContainer irAnnotationContainer, FqName annotation) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        if (irAnnotationContainer == null || irAnnotationContainer.getAnnotations().isEmpty()) {
            return false;
        }
        List<IrConstructorCall> annotations = irAnnotationContainer.getAnnotations();
        if ((annotations instanceof Collection) && annotations.isEmpty()) {
            return false;
        }
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(IrUtilsKt.getFqNameWhenAvailable(IrUtilsKt.getParentAsClass(((IrConstructorCall) it.next()).getSymbol().getOwner())), annotation)) {
                return true;
            }
        }
        return false;
    }

    public static final IrConstructorCall getAnnotation(IrAnnotationContainer irAnnotationContainer, FqName annotation) {
        Object next;
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Iterator<T> it = irAnnotationContainer.getAnnotations().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(IrUtilsKt.getFqNameWhenAvailable(IrUtilsKt.getParentAsClass(((IrConstructorCall) next).getSymbol().getOwner())), annotation));
        irConstructorCall = (IrConstructorCall) next;
        if (irConstructorCall == null) {
            IrDeclarationParent parent = ((IrFunction) irAnnotationContainer).getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
            for (IrConstructorCall irConstructorCall : ((IrClass) parent).getAnnotations()) {
                if (Intrinsics.areEqual(IrUtilsKt.getFqNameWhenAvailable(IrUtilsKt.getParentAsClass(irConstructorCall.getSymbol().getOwner())), annotation)) {
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        return irConstructorCall;
    }

    public static final String getEvaluateIntrinsicValue(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        if (irAnnotationContainer instanceof IrClass) {
            IrElement irElement = (IrElement) irAnnotationContainer;
            if (StringsKt.startsWith$default(getFqName(irElement), SuffixConstants.EXTENSION_java, false, 2, (Object) null)) {
                return getFqName(irElement);
            }
        }
        FqName fqName = evaluateIntrinsicAnnotation;
        if (!hasAnnotation(irAnnotationContainer, fqName)) {
            return null;
        }
        IrExpression valueArgument = getAnnotation(irAnnotationContainer, fqName).getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<*>");
        return String.valueOf(((IrConst) valueArgument).getValue());
    }

    public static /* synthetic */ Class getPrimitiveClass$default(IrType irType, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return getPrimitiveClass(irType, z);
    }

    public static final Class<?> getPrimitiveClass(IrType irType, boolean z) {
        Intrinsics.checkNotNullParameter(irType, "irType");
        PrimitiveType primitiveType = IrTypePredicatesKt.getPrimitiveType(irType);
        switch (primitiveType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[primitiveType.ordinal()]) {
            case 1:
                return z ? Boolean.class : Boolean.TYPE;
            case 2:
                return z ? Character.class : Character.TYPE;
            case 3:
                return z ? Byte.class : Byte.TYPE;
            case 4:
                return z ? Short.class : Short.TYPE;
            case 5:
                return z ? Integer.class : Integer.TYPE;
            case 6:
                return z ? Float.class : Float.TYPE;
            case 7:
                return z ? Long.class : Long.TYPE;
            case 8:
                return z ? Double.class : Double.TYPE;
            default:
                if (IrTypePredicatesKt.isString(irType)) {
                    return String.class;
                }
                return null;
        }
    }

    public static final IrFunction getFirstNonInterfaceOverridden(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (!(irFunction instanceof IrSimpleFunction)) {
            return irFunction;
        }
        for (Object obj : SequencesKt.flattenSequenceOfIterable(SequencesKt.generateSequence(CollectionsKt.listOf(irFunction), new Function1<List<? extends IrSimpleFunction>, List<? extends IrSimpleFunction>>() { // from class: org.jetbrains.kotlin.ir.interpreter.UtilsKt.getFirstNonInterfaceOverridden.1
            @Override // kotlin.jvm.functions.Function1
            public final List<IrSimpleFunction> invoke(List<? extends IrSimpleFunction> it) {
                List<IrSimpleFunctionSymbol> overriddenSymbols;
                Intrinsics.checkNotNullParameter(it, "it");
                IrSimpleFunction irSimpleFunction = (IrSimpleFunction) CollectionsKt.firstOrNull((List) it);
                if (irSimpleFunction == null || (overriddenSymbols = irSimpleFunction.getOverriddenSymbols()) == null) {
                    return null;
                }
                List<IrSimpleFunctionSymbol> list = overriddenSymbols;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it2 = list.iterator();
                while (it2.hasNext()) {
                    arrayList.add(((IrSimpleFunctionSymbol) it2.next()).getOwner());
                }
                return arrayList;
            }
        }))) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) obj;
            if (!irSimpleFunction.getIsFakeOverride()) {
                IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(irSimpleFunction);
                if ((parentClassOrNull != null ? parentClassOrNull.getKind() : null) != ClassKind.INTERFACE) {
                    return (IrFunction) obj;
                }
            }
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    public static final IrFunction getLastOverridden(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return !(irFunction instanceof IrSimpleFunction) ? irFunction : (IrFunction) SequencesKt.last(SequencesKt.flattenSequenceOfIterable(SequencesKt.generateSequence(CollectionsKt.listOf(irFunction), new Function1<List<? extends IrSimpleFunction>, List<? extends IrSimpleFunction>>() { // from class: org.jetbrains.kotlin.ir.interpreter.UtilsKt.getLastOverridden.1
            @Override // kotlin.jvm.functions.Function1
            public final List<IrSimpleFunction> invoke(List<? extends IrSimpleFunction> it) {
                List<IrSimpleFunctionSymbol> overriddenSymbols;
                Intrinsics.checkNotNullParameter(it, "it");
                IrSimpleFunction irSimpleFunction = (IrSimpleFunction) CollectionsKt.firstOrNull((List) it);
                if (irSimpleFunction == null || (overriddenSymbols = irSimpleFunction.getOverriddenSymbols()) == null) {
                    return null;
                }
                List<IrSimpleFunctionSymbol> list = overriddenSymbols;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it2 = list.iterator();
                while (it2.hasNext()) {
                    arrayList.add(((IrSimpleFunctionSymbol) it2.next()).getOwner());
                }
                return arrayList;
            }
        })));
    }

    public static final Primitive<?> toPrimitiveStateArray(List<? extends Object> list, IrType type) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        int i = 0;
        if (IrTypePredicatesKt.isByteArray(type)) {
            int size = list.size();
            byte[] bArr = new byte[size];
            while (i < size) {
                Object obj = list.get(i);
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
                bArr[i] = ((Number) obj).byteValue();
                i++;
            }
            return new Primitive<>(bArr, type);
        }
        if (IrTypePredicatesKt.isCharArray(type)) {
            int size2 = list.size();
            char[] cArr = new char[size2];
            while (i < size2) {
                Object obj2 = list.get(i);
                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Char");
                cArr[i] = ((Character) obj2).charValue();
                i++;
            }
            return new Primitive<>(cArr, type);
        }
        if (IrTypePredicatesKt.isShortArray(type)) {
            int size3 = list.size();
            short[] sArr = new short[size3];
            while (i < size3) {
                Object obj3 = list.get(i);
                Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Number");
                sArr[i] = ((Number) obj3).shortValue();
                i++;
            }
            return new Primitive<>(sArr, type);
        }
        if (IrTypePredicatesKt.isIntArray(type)) {
            int size4 = list.size();
            int[] iArr = new int[size4];
            while (i < size4) {
                Object obj4 = list.get(i);
                Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Number");
                iArr[i] = ((Number) obj4).intValue();
                i++;
            }
            return new Primitive<>(iArr, type);
        }
        if (IrTypePredicatesKt.isLongArray(type)) {
            int size5 = list.size();
            long[] jArr = new long[size5];
            while (i < size5) {
                Object obj5 = list.get(i);
                Intrinsics.checkNotNull(obj5, "null cannot be cast to non-null type kotlin.Number");
                jArr[i] = ((Number) obj5).longValue();
                i++;
            }
            return new Primitive<>(jArr, type);
        }
        if (IrTypePredicatesKt.isFloatArray(type)) {
            int size6 = list.size();
            float[] fArr = new float[size6];
            while (i < size6) {
                Object obj6 = list.get(i);
                Intrinsics.checkNotNull(obj6, "null cannot be cast to non-null type kotlin.Number");
                fArr[i] = ((Number) obj6).floatValue();
                i++;
            }
            return new Primitive<>(fArr, type);
        }
        if (IrTypePredicatesKt.isDoubleArray(type)) {
            int size7 = list.size();
            double[] dArr = new double[size7];
            while (i < size7) {
                Object obj7 = list.get(i);
                Intrinsics.checkNotNull(obj7, "null cannot be cast to non-null type kotlin.Number");
                dArr[i] = ((Number) obj7).doubleValue();
                i++;
            }
            return new Primitive<>(dArr, type);
        }
        if (!IrTypePredicatesKt.isBooleanArray(type)) {
            return new Primitive<>(list.toArray(new Object[0]), type);
        }
        int size8 = list.size();
        boolean[] zArr = new boolean[size8];
        while (i < size8) {
            Object obj8 = list.get(i);
            Intrinsics.checkNotNull(obj8, "null cannot be cast to non-null type kotlin.Boolean");
            zArr[i] = ((Boolean) obj8).booleanValue();
            i++;
        }
        return new Primitive<>(zArr, type);
    }

    public static final IrType getVarargType(IrFunctionAccessExpression irFunctionAccessExpression, int i) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        IrType varargElementType = irFunctionAccessExpression.getSymbol().getOwner().getValueParameters().get(i).getVarargElementType();
        if (varargElementType == null) {
            return null;
        }
        if (IrTypesKt.getClassOrNull(varargElementType) != null) {
            return irFunctionAccessExpression.getSymbol().getOwner().getValueParameters().get(i).getType();
        }
        IrType type = irFunctionAccessExpression.getSymbol().getOwner().getValueParameters().get(i).getType();
        IrSimpleType irSimpleType = type instanceof IrSimpleType ? (IrSimpleType) type : null;
        if (irSimpleType == null) {
            return null;
        }
        IrSimpleTypeBuilder builder = IrSimpleTypeImplKt.toBuilder(irSimpleType);
        IrSymbolOwner owner = IrTypesKt.getClassifierOrFail(varargElementType).getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrTypeParameter");
        IrType typeArgument = irFunctionAccessExpression.getTypeArgument(((IrTypeParameter) owner).getIndex());
        Intrinsics.checkNotNull(typeArgument);
        builder.setArguments(CollectionsKt.listOf(IrSimpleTypeImplKt.makeTypeProjection(typeArgument, Variance.OUT_VARIANCE)));
        return IrSimpleTypeImplKt.buildSimpleType(builder);
    }

    public static final String getCapitalizedFileName(IrFunction irFunction) {
        String name2;
        String strReplace$default;
        String strCapitalizeAsciiOnly;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrFile fileOrNull = IrUtilsKt.getFileOrNull(irFunction);
        return (fileOrNull == null || (name2 = IrDeclarationsKt.getName(fileOrNull)) == null || (strReplace$default = StringsKt.replace$default(name2, KotlinFileType.DOT_DEFAULT_EXTENSION, "Kt", false, 4, (Object) null)) == null || (strCapitalizeAsciiOnly = CapitalizeDecapitalizeKt.capitalizeAsciiOnly(strReplace$default)) == null) ? "<UNKNOWN>" : strCapitalizeAsciiOnly;
    }

    public static final boolean isSubclassOfThrowable(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Iterator it = SequencesKt.generateSequence(irClass, new Function1<IrClass, IrClass>() { // from class: org.jetbrains.kotlin.ir.interpreter.UtilsKt.isSubclassOfThrowable.1
            @Override // kotlin.jvm.functions.Function1
            public final IrClass invoke(IrClass irClass2) {
                Intrinsics.checkNotNullParameter(irClass2, "irClass");
                Object obj = null;
                if (IrTypePredicatesKt.isAny(IrUtilsKt.getDefaultType(irClass2))) {
                    return null;
                }
                List<IrType> superTypes = irClass2.getSuperTypes();
                ArrayList arrayList = new ArrayList();
                Iterator<T> it2 = superTypes.iterator();
                while (it2.hasNext()) {
                    IrClassSymbol classOrNull = IrTypesKt.getClassOrNull((IrType) it2.next());
                    IrClass owner = classOrNull != null ? classOrNull.getOwner() : null;
                    if (owner != null) {
                        arrayList.add(owner);
                    }
                }
                boolean z = false;
                Object obj2 = null;
                for (Object obj3 : arrayList) {
                    if (IrUtilsKt.isClass((IrClass) obj3)) {
                        if (z) {
                            return (IrClass) obj;
                        }
                        z = true;
                        obj2 = obj3;
                    }
                }
                if (z) {
                    obj = obj2;
                }
                return (IrClass) obj;
            }
        }).iterator();
        while (it.hasNext()) {
            if (IrTypeUtilsKt.isThrowable(IrUtilsKt.getDefaultType((IrClass) it.next()))) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isUnsignedArray(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (irType instanceof IrSimpleType) {
            IrSimpleType irSimpleType = (IrSimpleType) irType;
            if (irSimpleType.getClassifier() instanceof IrClassSymbol) {
                return SetsKt.setOf((Object[]) new String[]{"kotlin.UByteArray", "kotlin.UShortArray", "kotlin.UIntArray", "kotlin.ULongArray"}).contains(getFqName(irSimpleType.getClassifier().getOwner()));
            }
        }
        return false;
    }

    public static final boolean isPrimitiveArray(IrType irType) {
        FqName fqNameWhenAvailable;
        FqNameUnsafe unsafe;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClass irClass = IrTypesKt.getClass(irType);
        if (irClass == null || (fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irClass)) == null || (unsafe = fqNameWhenAvailable.toUnsafe()) == null) {
            return false;
        }
        return StandardNames.isPrimitiveArray(unsafe);
    }

    public static final String internalName(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        StringBuilder sb = new StringBuilder(irClass.getName().asString());
        for (IrDeclarationParent irDeclarationParent : SequencesKt.drop(SequencesKt.generateSequence(irClass instanceof IrDeclarationParent ? irClass : null, new Function1<IrDeclarationParent, IrDeclarationParent>() { // from class: org.jetbrains.kotlin.ir.interpreter.UtilsKt.internalName.1
            @Override // kotlin.jvm.functions.Function1
            public final IrDeclarationParent invoke(IrDeclarationParent it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDeclaration irDeclaration = it instanceof IrDeclaration ? (IrDeclaration) it : null;
                if (irDeclaration != null) {
                    return irDeclaration.getParent();
                }
                return null;
            }
        }), 1)) {
            if (irDeclarationParent instanceof IrClass) {
                sb.insert(0, ((IrClass) irDeclarationParent).getName().asString() + '$');
            } else if (irDeclarationParent instanceof IrPackageFragment) {
                String strAsString = ((IrPackageFragment) irDeclarationParent).getPackageFqName().asString();
                Intrinsics.checkNotNull(strAsString);
                if (strAsString.length() <= 0) {
                    strAsString = null;
                }
                if (strAsString != null) {
                    sb.insert(0, strAsString + '.');
                }
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static final boolean checkCast(IrFunction irFunction, IrInterpreterEnvironment environment) {
        IrType irType;
        State statePeekState;
        Intrinsics.checkNotNullParameter(environment, "environment");
        if (irFunction == null || !(IrTypesKt.getClassifierOrNull(irFunction.getReturnType()) instanceof IrTypeParameterSymbol)) {
            return true;
        }
        State stateLoadState = environment.getCallStack().loadState(irFunction.getSymbol());
        KTypeState kTypeState = stateLoadState instanceof KTypeState ? (KTypeState) stateLoadState : null;
        if (kTypeState == null || (irType = kTypeState.getIrType()) == null || (IrTypesKt.getClassifierOrFail(irType) instanceof IrTypeParameterSymbol) || (statePeekState = environment.getCallStack().peekState()) == null) {
            return true;
        }
        if ((!(statePeekState instanceof Primitive) || ((Primitive) statePeekState).getValue() != null) && !StateKt.isSubtypeOf(statePeekState, irType)) {
            String fqName = getFqName(environment.getCallStack().popState().getIrClass());
            environment.getCallStack().dropFrame();
            ExceptionUtilsKt.handleUserException(new ClassCastException(fqName + " cannot be cast to " + RenderIrElementKt.render$default(irType, (DumpIrTreeOptions) null, 1, (Object) null)), environment);
            return false;
        }
        return true;
    }

    public static final List<Object> getArgsForMethodInvocation(IrFunction irFunction, CallInterceptor callInterceptor, MethodType methodType, List<? extends State> args) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        Intrinsics.checkNotNullParameter(methodType, "methodType");
        Intrinsics.checkNotNullParameter(args, "args");
        List<Object> mutableList = CollectionsKt.toMutableList((Collection) ProxyKt.wrap(args, callInterceptor, irFunction, methodType));
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.lastOrNull((List) irFunction.getValueParameters());
        if ((irValueParameter != null ? irValueParameter.getVarargElementType() : null) != null) {
            Object objLast = CollectionsKt.last((List<? extends Object>) mutableList);
            mutableList.remove(mutableList.size() - 1);
            Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type kotlin.Array<out kotlin.Any?>");
            CollectionsKt.addAll(mutableList, (Object[]) objLast);
        }
        return mutableList;
    }

    public static final String fqNameWithNullability(IrType irType) {
        String strAsString;
        Name name2;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        FqName classFqName = IrTypesKt.getClassFqName(irType);
        if (classFqName == null || (strAsString = classFqName.toString()) == null) {
            IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
            IrSymbolOwner owner = classifierOrNull != null ? classifierOrNull.getOwner() : null;
            IrDeclarationWithName irDeclarationWithName = owner instanceof IrDeclarationWithName ? (IrDeclarationWithName) owner : null;
            strAsString = (irDeclarationWithName == null || (name2 = irDeclarationWithName.getName()) == null) ? null : name2.asString();
            if (strAsString == null) {
                strAsString = RenderIrElementKt.render$default(irType, (DumpIrTreeOptions) null, 1, (Object) null);
            }
        }
        Intrinsics.checkNotNull(strAsString);
        return strAsString + (((irType instanceof IrSimpleType) && ((IrSimpleType) irType).getNullability() == SimpleTypeNullability.MARKED_NULLABLE) ? TypeDescription.Generic.OfWildcardType.SYMBOL : "");
    }

    public static final String getOnlyName(IrType irType) {
        String str;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (!(irType instanceof IrSimpleType)) {
            return RenderIrElementKt.render$default(irType, (DumpIrTreeOptions) null, 1, (Object) null);
        }
        StringBuilder sb = new StringBuilder();
        IrSymbolOwner owner = IrTypesKt.getClassifierOrFail(irType).getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName");
        StringBuilder sbAppend = sb.append(((IrDeclarationWithName) owner).getName().asString());
        int i = WhenMappings.$EnumSwitchMapping$1[((IrSimpleType) irType).getNullability().ordinal()];
        if (i != 1) {
            str = "";
            if (i != 2) {
                if (i != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                if (IrTypesKt.getClassifierOrNull(irType) instanceof IrTypeParameterSymbol) {
                    str = " & Any";
                }
            }
        } else {
            str = TypeDescription.Generic.OfWildcardType.SYMBOL;
        }
        return sbAppend.append(str).toString();
    }

    public static final boolean accessesTopLevelOrObjectField(IrFieldAccessExpression irFieldAccessExpression) {
        IrType type;
        IrClassifierSymbol classifierOrNull;
        Intrinsics.checkNotNullParameter(irFieldAccessExpression, "<this>");
        if (irFieldAccessExpression.getReceiver() == null) {
            return true;
        }
        IrExpression receiver = irFieldAccessExpression.getReceiver();
        IrSymbolOwner owner = (receiver == null || (type = receiver.getType()) == null || (classifierOrNull = IrTypesKt.getClassifierOrNull(type)) == null) ? null : classifierOrNull.getOwner();
        IrClass irClass = owner instanceof IrClass ? (IrClass) owner : null;
        return irClass != null && IrUtilsKt.isObject(irClass);
    }

    public static final IrProperty getOriginalPropertyByName(IrClass irClass, String name2) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        IrProperty irProperty = null;
        boolean z = false;
        for (IrProperty irProperty2 : IrUtilsKt.getProperties(irClass)) {
            if (Intrinsics.areEqual(irProperty2.getName().asString(), name2)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irProperty = irProperty2;
            }
        }
        if (z) {
            IrSimpleFunction getter = irProperty.getGetter();
            Intrinsics.checkNotNull(getter);
            IrProperty property = getProperty(getLastOverridden(getter));
            Intrinsics.checkNotNull(property);
            return property;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    public static final IrFunction getFunctionThatContainsDefaults(IrFunctionAccessExpression irFunctionAccessExpression) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        IrFunction owner = irFunctionAccessExpression.getSymbol().getOwner();
        Iterator<Integer> it = RangesKt.until(0, irFunctionAccessExpression.getValueArgumentsCount()).iterator();
        while (it.hasNext()) {
            int iNextInt = ((IntIterator) it).nextInt();
            if (irFunctionAccessExpression.getValueArgument(iNextInt) == null) {
                IrFunction functionThatContainsDefaults$lookup = getFunctionThatContainsDefaults$lookup(owner.getValueParameters().get(iNextInt));
                return functionThatContainsDefaults$lookup == null ? owner : functionThatContainsDefaults$lookup;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    private static final IrFunction getFunctionThatContainsDefaults$lookup(IrValueParameter irValueParameter) {
        List<IrSimpleFunctionSymbol> overriddenSymbols;
        if (irValueParameter.getDefaultValue() != null) {
            IrDeclarationParent parent = irValueParameter.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFunction");
            IrFunction irFunction = (IrFunction) parent;
            if (irFunction != null) {
                return irFunction;
            }
        }
        IrDeclarationParent parent2 = irValueParameter.getParent();
        IrSimpleFunction irSimpleFunction = parent2 instanceof IrSimpleFunction ? (IrSimpleFunction) parent2 : null;
        if (irSimpleFunction != null && (overriddenSymbols = irSimpleFunction.getOverriddenSymbols()) != null) {
            List<IrSimpleFunctionSymbol> list = overriddenSymbols;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((IrSimpleFunctionSymbol) it.next()).getOwner().getValueParameters().get(irValueParameter.getIndex()));
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                IrFunction functionThatContainsDefaults$lookup = getFunctionThatContainsDefaults$lookup((IrValueParameter) it2.next());
                if (functionThatContainsDefaults$lookup != null) {
                    return functionThatContainsDefaults$lookup;
                }
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrExpression getDefaultWithActualParameters(IrValueParameter irValueParameter, final IrFunction newParent, final List<? extends IrValueDeclaration> actualParameters) {
        Intrinsics.checkNotNullParameter(irValueParameter, "<this>");
        Intrinsics.checkNotNullParameter(newParent, "newParent");
        Intrinsics.checkNotNullParameter(actualParameters, "actualParameters");
        IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
        DescriptorsRemapper descriptorsRemapper = null;
        Object[] objArr = 0;
        IrExpression expression = defaultValue != null ? defaultValue.getExpression() : null;
        if (expression instanceof IrConst) {
            return expression;
        }
        IrDeclarationParent parent = irValueParameter.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFunction");
        final IrFunction irFunction = (IrFunction) parent;
        IrElementTransformerVoid irElementTransformerVoid = new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.interpreter.UtilsKt$getDefaultWithActualParameters$transformer$1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetValue(IrGetValue expression2) {
                IrValueDeclaration irValueDeclaration;
                Intrinsics.checkNotNullParameter(expression2, "expression");
                IrValueDeclaration owner = expression2.getSymbol().getOwner();
                IrValueParameter irValueParameter2 = owner instanceof IrValueParameter ? (IrValueParameter) owner : null;
                if (irValueParameter2 != null && Intrinsics.areEqual(irValueParameter2.getParent(), irFunction)) {
                    if (irValueParameter2.getIndex() == -1) {
                        IrValueParameter dispatchReceiverParameter = newParent.getDispatchReceiverParameter();
                        if (dispatchReceiverParameter == null) {
                            dispatchReceiverParameter = newParent.getExtensionReceiverParameter();
                        }
                        irValueDeclaration = dispatchReceiverParameter;
                    } else {
                        irValueDeclaration = actualParameters.get(irValueParameter2.getIndex());
                    }
                    int startOffset = expression2.getStartOffset();
                    int endOffset = expression2.getEndOffset();
                    IrType type = expression2.getType();
                    Intrinsics.checkNotNull(irValueDeclaration);
                    return new IrGetValueImpl(startOffset, endOffset, type, irValueDeclaration.getSymbol(), null, 16, null);
                }
                return super.visitGetValue(expression2);
            }
        };
        if (expression == null) {
            return null;
        }
        IrExpression irExpression = expression;
        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(descriptorsRemapper, 1, objArr == true ? 1 : 0);
        IrVisitorsKt.acceptVoid(irExpression, deepCopySymbolRemapper);
        DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
        IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irExpression.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), newParent);
        if (irElementPatchDeclarationParents != null) {
            IrExpression irExpression2 = (IrExpression) irElementPatchDeclarationParents;
            if (irExpression2 != null) {
                return irExpression2.transform((IrElementTransformer<? super Object>) irElementTransformerVoid, (Object) null);
            }
            return null;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    public static final IrType getTypeIfReified(IrType irType, final CallStack callStack) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(callStack, "callStack");
        return getTypeIfReified(irType, new Function1<IrClassifierSymbol, IrType>() { // from class: org.jetbrains.kotlin.ir.interpreter.UtilsKt.getTypeIfReified.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrType invoke(IrClassifierSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                State stateLoadState = callStack.loadState(it);
                Intrinsics.checkNotNull(stateLoadState, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState");
                return ((KTypeState) stateLoadState).getIrType();
            }
        });
    }

    public static final IrType getTypeIfReified(IrType irType, Function1<? super IrClassifierSymbol, ? extends IrType> getType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(getType, "getType");
        if (!(irType instanceof IrSimpleType)) {
            return irType;
        }
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
        IrSymbolOwner owner = classifierOrNull != null ? classifierOrNull.getOwner() : null;
        if (owner instanceof IrTypeParameter) {
            IrTypeParameter irTypeParameter = (IrTypeParameter) owner;
            if (irTypeParameter.getIsReified()) {
                IrType irTypeInvoke = getType.invoke(irTypeParameter.getSymbol());
                Intrinsics.checkNotNull(irTypeInvoke, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                return IrTypesKt.mergeNullability((IrSimpleType) irTypeInvoke, irType);
            }
        }
        IrSimpleType irSimpleType = (IrSimpleType) irType;
        List<IrTypeArgument> arguments = irSimpleType.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        for (IrTypeArgument irTypeArgument : arguments) {
            IrType typeOrNull = IrTypesKt.getTypeOrNull(irTypeArgument);
            if (typeOrNull != null) {
                IrClassifierSymbol classifierOrNull2 = IrTypesKt.getClassifierOrNull(typeOrNull);
                IrSymbolOwner owner2 = classifierOrNull2 != null ? classifierOrNull2.getOwner() : null;
                if (!(owner2 instanceof IrTypeParameter) || ((IrTypeParameter) owner2).getIsReified()) {
                    Object typeIfReified = getTypeIfReified(typeOrNull, getType);
                    Intrinsics.checkNotNull(typeIfReified, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrTypeArgument");
                    irTypeArgument = (IrTypeArgument) typeIfReified;
                }
            }
            arrayList.add(irTypeArgument);
        }
        IrSimpleTypeBuilder builder = IrSimpleTypeImplKt.toBuilder(irSimpleType);
        builder.setArguments(arrayList);
        return IrSimpleTypeImplKt.buildSimpleType(builder);
    }

    public static final Map<IrTypeParameterSymbol, KTypeState> loadReifiedTypeArguments(final IrInterpreterEnvironment irInterpreterEnvironment, final IrFunctionAccessExpression expression) {
        Intrinsics.checkNotNullParameter(irInterpreterEnvironment, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        List<IrTypeParameter> typeParameters = expression.getSymbol().getOwner().getTypeParameters();
        ArrayList arrayList = new ArrayList();
        for (Object obj : typeParameters) {
            if (((IrTypeParameter) obj).getIsReified()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((IrTypeParameter) it.next()).getSymbol());
        }
        return org.jetbrains.kotlin.utils.CollectionsKt.keysToMap(arrayList3, new Function1<IrTypeParameterSymbol, KTypeState>() { // from class: org.jetbrains.kotlin.ir.interpreter.UtilsKt.loadReifiedTypeArguments.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final KTypeState invoke(IrTypeParameterSymbol it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                IrType typeArgument = expression.getTypeArgument(it2.getOwner().getIndex());
                Intrinsics.checkNotNull(typeArgument);
                return new KTypeState(UtilsKt.getTypeIfReified(typeArgument, irInterpreterEnvironment.getCallStack()), irInterpreterEnvironment.getKTypeClass$ir_interpreter().getOwner());
            }
        });
    }

    public static final boolean hasFunInterfaceParent(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrClass parentClassOrNull = IrUtilsKt.getParentClassOrNull(irFunction);
        return parentClassOrNull != null && parentClassOrNull.getIsFun();
    }

    public static final IrFunction getSingleAbstractMethod(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        List<IrDeclaration> declarations = irClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrSimpleFunction) {
                arrayList.add(obj);
            }
        }
        Object obj2 = null;
        boolean z = false;
        for (Object obj3 : arrayList) {
            if (((IrSimpleFunction) obj3).getModality() == Modality.ABSTRACT) {
                if (z) {
                    throw new IllegalArgumentException("Collection contains more than one matching element.");
                }
                z = true;
                obj2 = obj3;
            }
        }
        if (!z) {
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        return (IrFunction) obj2;
    }

    public static final boolean isAccessToNotNullableObject(IrExpression irExpression) {
        if (irExpression instanceof IrGetObjectValue) {
            if (!org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(irExpression.getType())) {
                return true;
            }
        } else if (irExpression instanceof IrGetValue) {
            IrValueDeclaration owner = ((IrGetValue) irExpression).getSymbol().getOwner();
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irExpression.getType());
            IrClass owner2 = classOrNull != null ? classOrNull.getOwner() : null;
            if (owner2 == null || !IrUtilsKt.isObject(owner2) || org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(irExpression.getType())) {
                return false;
            }
            if (Intrinsics.areEqual(owner.getOrigin(), IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE) || Intrinsics.areEqual(owner.getName().asString(), "<this>")) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isAccessorOfPropertyWithBackingField(IrFunction irFunction) {
        IrProperty owner;
        IrField backingField;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (irFunction instanceof IrSimpleFunction) {
            IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) irFunction).getCorrespondingPropertySymbol();
            if (((correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null || (backingField = owner.getBackingField()) == null) ? null : backingField.getInitializer()) != null) {
                return true;
            }
        }
        return false;
    }

    public static final String unsignedToString(State state) {
        Intrinsics.checkNotNullParameter(state, "<this>");
        Object objSingle = CollectionsKt.single(state.getFields().values());
        Intrinsics.checkNotNull(objSingle, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.Primitive<*>");
        Object value = ((Primitive) objSingle).getValue();
        if (value instanceof Byte) {
            return UByte.m2960toStringimpl(UByte.m2916constructorimpl(((Number) value).byteValue()));
        }
        if (value instanceof Short) {
            return UShort.m3223toStringimpl(UShort.m3179constructorimpl(((Number) value).shortValue()));
        }
        if (value instanceof Integer) {
            return Integer.toUnsignedString(UInt.m2993constructorimpl(((Number) value).intValue()));
        }
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Number");
        return Long.toUnsignedString(ULong.m3072constructorimpl(((Number) value).longValue()));
    }

    public static final String specialToStringForJs(Object obj) {
        if (obj instanceof Float) {
            Number number = (Number) obj;
            if (!Float.isInfinite(number.floatValue()) && Intrinsics.areEqual((float) Math.floor(number.floatValue()), (Float) obj)) {
                return String.valueOf((int) number.floatValue());
            }
        }
        if (obj instanceof Double) {
            Number number2 = (Number) obj;
            if (!Double.isInfinite(number2.doubleValue()) && Intrinsics.areEqual(Math.floor(number2.doubleValue()), (Double) obj)) {
                return String.valueOf((long) number2.doubleValue());
            }
        }
        return String.valueOf(obj);
    }

    public static final Common toState(IrEnumEntry irEnumEntry, IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(irEnumEntry, "<this>");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(irEnumEntry.getSymbol().getOwner());
        List<IrDeclaration> declarations = parentAsClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrEnumEntry) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        IrClass correspondingClass = irEnumEntry.getCorrespondingClass();
        if (correspondingClass != null) {
            parentAsClass = correspondingClass;
        }
        Common common = new Common(parentAsClass);
        if (!arrayList2.isEmpty()) {
            List listListOf = CollectionsKt.listOf((Object[]) new Primitive[]{new Primitive(irEnumEntry.getName().asString(), irBuiltIns.getStringType()), new Primitive(Integer.valueOf(arrayList2.indexOf(irEnumEntry)), irBuiltIns.getIntType())});
            List<IrDeclaration> declarations2 = irBuiltIns.getEnumClass().getOwner().getDeclarations();
            ArrayList arrayList3 = new ArrayList();
            for (Object obj2 : declarations2) {
                if (obj2 instanceof IrProperty) {
                    arrayList3.add(obj2);
                }
            }
            for (Pair pair : CollectionsKt.zip(arrayList3, listListOf)) {
                common.setField(((IrProperty) pair.component1()).getSymbol(), (Primitive) pair.component2());
            }
        }
        return common;
    }

    public static final IrProperty getProperty(IrFunction irFunction) {
        IrPropertySymbol correspondingPropertySymbol;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrSimpleFunction irSimpleFunction = irFunction instanceof IrSimpleFunction ? (IrSimpleFunction) irFunction : null;
        if (irSimpleFunction == null || (correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol()) == null) {
            return null;
        }
        return correspondingPropertySymbol.getOwner();
    }
}
