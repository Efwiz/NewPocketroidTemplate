package org.jetbrains.kotlin.ir.interpreter;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.UnsignedType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.constant.BooleanValue;
import org.jetbrains.kotlin.constant.ByteValue;
import org.jetbrains.kotlin.constant.CharValue;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.constant.DoubleValue;
import org.jetbrains.kotlin.constant.FloatValue;
import org.jetbrains.kotlin.constant.IntValue;
import org.jetbrains.kotlin.constant.LongValue;
import org.jetbrains.kotlin.constant.NullValue;
import org.jetbrains.kotlin.constant.ShortValue;
import org.jetbrains.kotlin.constant.StringValue;
import org.jetbrains.kotlin.constant.UByteValue;
import org.jetbrains.kotlin.constant.UIntValue;
import org.jetbrains.kotlin.constant.ULongValue;
import org.jetbrains.kotlin.constant.UShortValue;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.impl.IrFactoryImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrVariableImpl;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockBodyImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrElseBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrEnumConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrIfThenElseImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrVariableSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IrTreeBuildUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Â\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0000\u001a,\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0000\u001a\u001c\u0010\u0012\u001a\u00020\u0013*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0000\u001a\u0018\u0010\u0018\u001a\u00020\u0019*\u00020\u001a2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u001bH\u0000\u001a\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u000fH\u0000\u001a\u0018\u0010 \u001a\u00020!*\u00020\"2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$H\u0000\u001a\f\u0010 \u001a\u00020%*\u00020\u001aH\u0000\u001a\f\u0010&\u001a\u00020'*\u00020\u0007H\u0000\u001a\f\u0010(\u001a\u00020)*\u00020*H\u0000\u001a\u0014\u0010+\u001a\u00020,*\u00020\u001a2\u0006\u0010-\u001a\u00020%H\u0000\u001a\f\u0010.\u001a\u00020/*\u00020*H\u0000\u001a\u0014\u00100\u001a\u00020\u001d*\u00020\u00142\u0006\u00101\u001a\u00020\u000fH\u0000\u001a\u001c\u00102\u001a\u00020\u0019*\u00020\u00142\u0006\u00103\u001a\u00020%2\u0006\u00104\u001a\u00020%H\u0000\u001a$\u00105\u001a\u000206*\u00020\u00142\u0006\u00107\u001a\u00020%2\u0006\u00108\u001a\u00020%2\u0006\u00109\u001a\u00020%H\u0000\u001a\u0016\u0010:\u001a\u00020\u0016*\u00020\u00162\b\b\u0002\u0010;\u001a\u00020<H\u0000\u001a\u0014\u0010=\u001a\u0006\u0012\u0002\b\u00030>*\u0006\u0012\u0002\b\u00030?H\u0000\u001a.\u0010@\u001a\u0006\u0012\u0002\b\u00030?*\u0004\u0018\u00010A2\u0006\u0010\u001f\u001a\u00020\u000f2\b\b\u0002\u0010B\u001a\u00020C2\b\b\u0002\u0010D\u001a\u00020CH\u0007\u001a\u0012\u0010E\u001a\u00020F*\b\u0012\u0004\u0012\u00020H0GH\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003¨\u0006I"}, d2 = {"TEMP_CLASS_FOR_INTERPRETER", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "getTEMP_CLASS_FOR_INTERPRETER", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "TEMP_FUNCTION_FOR_INTERPRETER", "getTEMP_FUNCTION_FOR_INTERPRETER", "createTempClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "name", "Lorg/jetbrains/kotlin/name/Name;", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "createTempFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "copyArgs", "", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "into", "createCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "createConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "irType", "createGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "receiver", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "createGetObject", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "createGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "createReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "value", "createTempVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "emptyArrayConstructor", "arrayType", "irEquals", "arg1", "arg2", "irIfNullThenElse", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "nullableArg", "ifTrue", "ifFalse", "shallowCopy", "copyTypeArguments", "", "toConstantValue", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "toIrConst", "", "startOffset", "", "endOffset", "wrapWithBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "ir.interpreter"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrTreeBuildUtilsKt {
    private static final IrDeclarationOriginImpl TEMP_CLASS_FOR_INTERPRETER = new IrDeclarationOriginImpl() { // from class: org.jetbrains.kotlin.ir.interpreter.IrTreeBuildUtilsKt$TEMP_CLASS_FOR_INTERPRETER$1
    };
    private static final IrDeclarationOriginImpl TEMP_FUNCTION_FOR_INTERPRETER = new IrDeclarationOriginImpl() { // from class: org.jetbrains.kotlin.ir.interpreter.IrTreeBuildUtilsKt$TEMP_FUNCTION_FOR_INTERPRETER$1
    };

    /* JADX INFO: compiled from: IrTreeBuildUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[UnsignedType.values().length];
            try {
                iArr[UnsignedType.UBYTE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[UnsignedType.USHORT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[UnsignedType.UINT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[UnsignedType.ULONG.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[PrimitiveType.values().length];
            try {
                iArr2[PrimitiveType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[PrimitiveType.CHAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[PrimitiveType.BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[PrimitiveType.SHORT.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[PrimitiveType.INT.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[PrimitiveType.FLOAT.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[PrimitiveType.LONG.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[PrimitiveType.DOUBLE.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public static final IrDeclarationOriginImpl getTEMP_CLASS_FOR_INTERPRETER() {
        return TEMP_CLASS_FOR_INTERPRETER;
    }

    public static final IrDeclarationOriginImpl getTEMP_FUNCTION_FOR_INTERPRETER() {
        return TEMP_FUNCTION_FOR_INTERPRETER;
    }

    public static /* synthetic */ IrConst toIrConst$default(Object obj, IrType irType, int i, int i2, int i3, Object obj2) {
        if ((i3 & 2) != 0) {
            i = -2;
        }
        if ((i3 & 4) != 0) {
            i2 = -2;
        }
        return toIrConst(obj, irType, i, i2);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Please migrate to `org.jetbrains.kotlin.ir.util.toIrConst`")
    public static final /* synthetic */ IrConst toIrConst(Object obj, IrType irType, int i, int i2) {
        Intrinsics.checkNotNullParameter(irType, "irType");
        return IrUtilsKt.toIrConst(obj, irType, i, i2);
    }

    public static /* synthetic */ IrCall createCall$default(IrFunction irFunction, IrStatementOrigin irStatementOrigin, int i, Object obj) {
        if ((i & 1) != 0) {
            irStatementOrigin = null;
        }
        return createCall(irFunction, irStatementOrigin);
    }

    public static final IrCall createCall(IrFunction irFunction, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return new IrCallImpl(-2, -2, irFunction.getReturnType(), ((IrSimpleFunction) irFunction).getSymbol(), irFunction.getTypeParameters().size(), irFunction.getValueParameters().size(), irStatementOrigin, null, 128, null);
    }

    public static /* synthetic */ IrConstructorCall createConstructorCall$default(IrConstructor irConstructor, IrType irType, int i, Object obj) {
        if ((i & 1) != 0) {
            irType = irConstructor.getReturnType();
        }
        return createConstructorCall(irConstructor, irType);
    }

    public static final IrConstructorCall createConstructorCall(IrConstructor irConstructor, IrType irType) {
        Intrinsics.checkNotNullParameter(irConstructor, "<this>");
        Intrinsics.checkNotNullParameter(irType, "irType");
        return IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, -2, -2, irType, irConstructor.getSymbol(), null, 16, null);
    }

    public static final IrGetValue createGetValue(IrValueDeclaration irValueDeclaration) {
        Intrinsics.checkNotNullParameter(irValueDeclaration, "<this>");
        return new IrGetValueImpl(-2, -2, irValueDeclaration.getType(), irValueDeclaration.getSymbol(), null, 16, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrVariable createTempVariable(IrValueDeclaration irValueDeclaration) {
        Intrinsics.checkNotNullParameter(irValueDeclaration, "<this>");
        return new IrVariableImpl(-2, -2, IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE, new IrVariableSymbolImpl(null, 1, 0 == true ? 1 : 0), irValueDeclaration.getName(), irValueDeclaration.getType(), false, false, false);
    }

    public static final IrGetObjectValue createGetObject(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return new IrGetObjectValueImpl(-2, -2, IrUtilsKt.getDefaultType(irClass), irClass.getSymbol());
    }

    public static final IrReturn createReturn(IrFunction irFunction, IrExpression value) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        return new IrReturnImpl(-2, -2, irFunction.getReturnType(), irFunction.getSymbol(), value);
    }

    public static /* synthetic */ IrSimpleFunction createTempFunction$default(Name name2, IrType irType, IrDeclarationOrigin irDeclarationOrigin, DescriptorVisibility PUBLIC, int i, Object obj) {
        if ((i & 4) != 0) {
            irDeclarationOrigin = TEMP_FUNCTION_FOR_INTERPRETER;
        }
        if ((i & 8) != 0) {
            PUBLIC = DescriptorVisibilities.PUBLIC;
            Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        }
        return createTempFunction(name2, irType, irDeclarationOrigin, PUBLIC);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrSimpleFunction createTempFunction(Name name2, IrType type, IrDeclarationOrigin origin, DescriptorVisibility visibility) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        return IrFactory.DefaultImpls.createSimpleFunction$default(IrFactoryImpl.INSTANCE, -2, -2, origin, name2, visibility, false, false, type, Modality.FINAL, new IrSimpleFunctionSymbolImpl(null, 1, 0 == true ? 1 : 0), false, false, true, false, false, null, false, 98304, null);
    }

    public static /* synthetic */ IrClass createTempClass$default(Name name2, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 2) != 0) {
            irDeclarationOrigin = TEMP_CLASS_FOR_INTERPRETER;
        }
        return createTempClass(name2, irDeclarationOrigin);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrClass createTempClass(Name name2, IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(origin, "origin");
        IrFactoryImpl irFactoryImpl = IrFactoryImpl.INSTANCE;
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        return IrFactory.DefaultImpls.createClass$default((IrFactory) irFactoryImpl, -2, -2, origin, name2, PRIVATE, (IrClassSymbol) new IrClassSymbolImpl(null, 1, 0 == true ? 1 : 0), ClassKind.CLASS, Modality.FINAL, false, false, false, false, false, false, false, (SourceElement) null, 65280, (Object) null);
    }

    public static final IrExpression createGetField(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrProperty property = UtilsKt.getProperty(irFunction);
        Intrinsics.checkNotNull(property);
        IrField backingField = property.getBackingField();
        Intrinsics.checkNotNull(backingField);
        IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
        if (dispatchReceiverParameter == null) {
            dispatchReceiverParameter = irFunction.getExtensionReceiverParameter();
        }
        return createGetField(backingField, dispatchReceiverParameter);
    }

    public static /* synthetic */ IrGetField createGetField$default(IrField irField, IrValueParameter irValueParameter, int i, Object obj) {
        if ((i & 1) != 0) {
            irValueParameter = null;
        }
        return createGetField(irField, irValueParameter);
    }

    public static final IrGetField createGetField(IrField irField, IrValueParameter irValueParameter) {
        Intrinsics.checkNotNullParameter(irField, "<this>");
        return new IrGetFieldImpl(-2, -2, irField.getSymbol(), irField.getType(), irValueParameter != null ? createGetValue(irValueParameter) : null, null, null, 96, null);
    }

    public static final IrBlockBody wrapWithBlockBody(List<? extends IrStatement> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return new IrBlockBodyImpl(-2, -2, list);
    }

    public static /* synthetic */ IrFunctionAccessExpression shallowCopy$default(IrFunctionAccessExpression irFunctionAccessExpression, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return shallowCopy(irFunctionAccessExpression, z);
    }

    public static final IrFunctionAccessExpression shallowCopy(IrFunctionAccessExpression irFunctionAccessExpression, boolean z) {
        IrEnumConstructorCallImpl irEnumConstructorCallImpl;
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        if (irFunctionAccessExpression instanceof IrCall) {
            irEnumConstructorCallImpl = createCall$default(((IrCall) irFunctionAccessExpression).getSymbol().getOwner(), null, 1, null);
        } else if (irFunctionAccessExpression instanceof IrConstructorCall) {
            irEnumConstructorCallImpl = createConstructorCall$default(((IrConstructorCall) irFunctionAccessExpression).getSymbol().getOwner(), null, 1, null);
        } else if (irFunctionAccessExpression instanceof IrDelegatingConstructorCall) {
            irEnumConstructorCallImpl = IrDelegatingConstructorCallImpl.Companion.fromSymbolOwner$default(IrDelegatingConstructorCallImpl.INSTANCE, -2, -2, irFunctionAccessExpression.getType(), ((IrDelegatingConstructorCall) irFunctionAccessExpression).getSymbol(), 0, 0, 48, null);
        } else if (irFunctionAccessExpression instanceof IrEnumConstructorCall) {
            irEnumConstructorCallImpl = new IrEnumConstructorCallImpl(-2, -2, irFunctionAccessExpression.getType(), ((IrEnumConstructorCall) irFunctionAccessExpression).getSymbol(), irFunctionAccessExpression.getTypeArgumentsCount(), irFunctionAccessExpression.getValueArgumentsCount());
        } else {
            throw new NotImplementedError("An operation is not implemented: " + ("Expression " + irFunctionAccessExpression + " cannot be copied"));
        }
        if (z) {
            Iterator<Integer> it = RangesKt.until(0, irFunctionAccessExpression.getTypeArgumentsCount()).iterator();
            while (it.hasNext()) {
                int iNextInt = ((IntIterator) it).nextInt();
                irEnumConstructorCallImpl.putTypeArgument(iNextInt, irFunctionAccessExpression.getTypeArgument(iNextInt));
            }
        }
        return irEnumConstructorCallImpl;
    }

    public static final void copyArgs(IrBuiltIns irBuiltIns, IrFunctionAccessExpression from, IrFunctionAccessExpression into) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(into, "into");
        into.setDispatchReceiver(from.getDispatchReceiver());
        into.setExtensionReceiver(from.getExtensionReceiver());
        int i = 0;
        IntRange intRangeUntil = RangesKt.until(0, from.getValueArgumentsCount());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
        Iterator<Integer> it = intRangeUntil.iterator();
        while (it.hasNext()) {
            arrayList.add(from.getValueArgument(((IntIterator) it).nextInt()));
        }
        for (Object obj : arrayList) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrConstImpl irConstImplConstNull = (IrExpression) obj;
            if (irConstImplConstNull == null) {
                irConstImplConstNull = IrConstImpl.INSTANCE.constNull(-2, -2, irBuiltIns.getAnyNType());
            }
            into.putValueArgument(i, irConstImplConstNull);
            i = i2;
        }
    }

    public static final IrCall irEquals(IrBuiltIns irBuiltIns, IrExpression arg1, IrExpression arg2) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "<this>");
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(arg2, "arg2");
        IrCall irCallCreateCall = createCall(irBuiltIns.getEqeqSymbol().getOwner(), IrStatementOrigin.EQEQ.INSTANCE);
        irCallCreateCall.putValueArgument(0, arg1);
        irCallCreateCall.putValueArgument(1, arg2);
        return irCallCreateCall;
    }

    public static final IrWhen irIfNullThenElse(IrBuiltIns irBuiltIns, IrExpression nullableArg, IrExpression ifTrue, IrExpression ifFalse) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "<this>");
        Intrinsics.checkNotNullParameter(nullableArg, "nullableArg");
        Intrinsics.checkNotNullParameter(ifTrue, "ifTrue");
        Intrinsics.checkNotNullParameter(ifFalse, "ifFalse");
        IrBranchImpl irBranchImpl = new IrBranchImpl(irEquals(irBuiltIns, nullableArg, IrConstImpl.INSTANCE.constNull(-2, -2, irBuiltIns.getAnyNType())), ifTrue);
        IrElseBranchImpl irElseBranchImpl = new IrElseBranchImpl(IrConstImpl.INSTANCE.constTrue(-2, -2, irBuiltIns.getBooleanType()), ifFalse);
        IrIfThenElseImpl irIfThenElseImpl = new IrIfThenElseImpl(-2, -2, ifTrue.getType(), null, 8, null);
        CollectionsKt.addAll(irIfThenElseImpl.getBranches(), CollectionsKt.listOf((Object[]) new IrBranch[]{irBranchImpl, irElseBranchImpl}));
        return irIfThenElseImpl;
    }

    public static final IrConstructorCall emptyArrayConstructor(IrBuiltIns irBuiltIns, IrType arrayType) {
        IrConstructor next;
        Intrinsics.checkNotNullParameter(irBuiltIns, "<this>");
        Intrinsics.checkNotNullParameter(arrayType, "arrayType");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(arrayType);
        Intrinsics.checkNotNull(classOrNull);
        IrClass owner = classOrNull.getOwner();
        Iterator<IrConstructor> it = IrUtilsKt.getConstructors(owner).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (next.getValueParameters().size() != 1);
        IrConstructor irConstructor = next;
        if (irConstructor == null) {
            irConstructor = (IrConstructor) SequencesKt.first(IrUtilsKt.getConstructors(owner));
        }
        IrConstructorCall irConstructorCallCreateConstructorCall = createConstructorCall(irConstructor, arrayType);
        irConstructorCallCreateConstructorCall.putValueArgument(0, IrUtilsKt.toIrConst$default(0, irBuiltIns.getIntType(), 0, 0, 6, null));
        if (irConstructor.getValueParameters().size() == 2) {
            Name nameIdentifier = Name.identifier("TempForVararg");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            IrSimpleFunction irSimpleFunctionCreateTempFunction$default = createTempFunction$default(nameIdentifier, irBuiltIns.getAnyType(), null, null, 12, null);
            irSimpleFunctionCreateTempFunction$default.setParent(owner);
            irConstructorCallCreateConstructorCall.putValueArgument(1, new IrFunctionExpressionImpl(-2, -2, irConstructor.getValueParameters().get(1).getType(), irSimpleFunctionCreateTempFunction$default, IrStatementOrigin.LAMBDA.INSTANCE));
            IrTypeArgument irTypeArgument = (IrTypeArgument) CollectionsKt.singleOrNull((List) ((IrSimpleType) arrayType).getArguments());
            irConstructorCallCreateConstructorCall.putTypeArgument(0, irTypeArgument != null ? IrTypesKt.getTypeOrNull(irTypeArgument) : null);
        }
        return irConstructorCallCreateConstructorCall;
    }

    public static final ConstantValue<?> toConstantValue(IrConst<?> irConst) {
        Intrinsics.checkNotNullParameter(irConst, "<this>");
        if (irConst.getValue() == null) {
            return NullValue.INSTANCE;
        }
        IrType irTypeRemoveAnnotations = IrTypesKt.removeAnnotations(IrTypesKt.makeNotNull(irConst.getType()));
        PrimitiveType primitiveType = IrTypePredicatesKt.getPrimitiveType(irConst.getType());
        switch (primitiveType == null ? -1 : WhenMappings.$EnumSwitchMapping$1[primitiveType.ordinal()]) {
            case -1:
                UnsignedType unsignedType = IrTypePredicatesKt.getUnsignedType(irTypeRemoveAnnotations);
                int i = unsignedType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[unsignedType.ordinal()];
                if (i == -1) {
                    if (!IrTypePredicatesKt.isString(irTypeRemoveAnnotations)) {
                        throw new IllegalStateException(("Cannot convert IrConst " + RenderIrElementKt.render$default(irConst, (DumpIrTreeOptions) null, 1, (Object) null) + " to ConstantValue").toString());
                    }
                    Object value = irConst.getValue();
                    Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.String");
                    return new StringValue((String) value);
                }
                if (i == 1) {
                    Object value2 = irConst.getValue();
                    Intrinsics.checkNotNull(value2, "null cannot be cast to non-null type kotlin.Number");
                    return new UByteValue(((Number) value2).byteValue());
                }
                if (i == 2) {
                    Object value3 = irConst.getValue();
                    Intrinsics.checkNotNull(value3, "null cannot be cast to non-null type kotlin.Number");
                    return new UShortValue(((Number) value3).shortValue());
                }
                if (i == 3) {
                    Object value4 = irConst.getValue();
                    Intrinsics.checkNotNull(value4, "null cannot be cast to non-null type kotlin.Number");
                    return new UIntValue(((Number) value4).intValue());
                }
                if (i != 4) {
                    throw new NoWhenBranchMatchedException();
                }
                Object value5 = irConst.getValue();
                Intrinsics.checkNotNull(value5, "null cannot be cast to non-null type kotlin.Number");
                return new ULongValue(((Number) value5).longValue());
            case 0:
            default:
                throw new NoWhenBranchMatchedException();
            case 1:
                Object value6 = irConst.getValue();
                Intrinsics.checkNotNull(value6, "null cannot be cast to non-null type kotlin.Boolean");
                return new BooleanValue(((Boolean) value6).booleanValue());
            case 2:
                Object value7 = irConst.getValue();
                Intrinsics.checkNotNull(value7, "null cannot be cast to non-null type kotlin.Char");
                return new CharValue(((Character) value7).charValue());
            case 3:
                Object value8 = irConst.getValue();
                Intrinsics.checkNotNull(value8, "null cannot be cast to non-null type kotlin.Number");
                return new ByteValue(((Number) value8).byteValue());
            case 4:
                Object value9 = irConst.getValue();
                Intrinsics.checkNotNull(value9, "null cannot be cast to non-null type kotlin.Number");
                return new ShortValue(((Number) value9).shortValue());
            case 5:
                Object value10 = irConst.getValue();
                Intrinsics.checkNotNull(value10, "null cannot be cast to non-null type kotlin.Number");
                return new IntValue(((Number) value10).intValue());
            case 6:
                Object value11 = irConst.getValue();
                Intrinsics.checkNotNull(value11, "null cannot be cast to non-null type kotlin.Number");
                return new FloatValue(((Number) value11).floatValue());
            case 7:
                Object value12 = irConst.getValue();
                Intrinsics.checkNotNull(value12, "null cannot be cast to non-null type kotlin.Number");
                return new LongValue(((Number) value12).longValue());
            case 8:
                Object value13 = irConst.getValue();
                Intrinsics.checkNotNull(value13, "null cannot be cast to non-null type kotlin.Number");
                return new DoubleValue(((Number) value13).doubleValue());
        }
    }
}
