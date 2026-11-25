package org.jetbrains.kotlin.backend.jvm.intrinsics;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.jvm.JvmSymbols;
import org.jetbrains.kotlin.backend.jvm.lower.VarargLoweringKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;
import org.jetbrains.org.objectweb.asm.Type;
import org.mozilla.javascript.ES6Iterator;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrIntrinsicMethods.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 <2\u00020\u0001:\u0002<=B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001a\u0010 \u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\"0!H\u0002J*\u0010 \u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\"0!2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u001cH\u0002J2\u0010&\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\"0!2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010*\u001a\u00020$H\u0002J*\u0010+\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\"0!2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u000fH\u0002J*\u0010,\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\"0!2\u0006\u0010-\u001a\u00020(2\u0006\u0010.\u001a\u00020/H\u0002J\u0010\u00100\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001b\u001a\u000201J\u001a\u00102\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u0002030\"0!H\u0002J\u001a\u00104\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\"0!H\u0002J6\u00105\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u0002060\"0!2\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u0002080\r2\u0006\u00109\u001a\u00020:H\u0002J*\u0010;\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\"0!2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u000fH\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0018\u0010\u001b\u001a\u00020\u001c*\u00020\u001d8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f¨\u0006>"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IrIntrinsicMethods;", "", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbols", "Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;)V", "anyFqn", "Lorg/jetbrains/kotlin/name/FqName;", "arrayFqn", "cloneableFqn", "intFqn", "intrinsicsMap", "", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IrIntrinsicMethods$Key;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "kClassFqn", "kotlinEnumsFqn", "kotlinFqn", "kotlinJvmFqn", "kotlinJvmInternalUnsafeFqn", "kotlinReflectFqn", "stringFqn", "getSymbols", "()Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getSymbol", "(Lorg/jetbrains/kotlin/builtins/PrimitiveType;)Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "arrayMethods", "", "Lkotlin/Pair;", "elementClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "arrayClass", "binaryFunForPrimitives", "name", "", "intrinsic", "parameter", "binaryFunForPrimitivesAcrossPrimitives", "binaryOp", "methodName", "opcode", "", "getIntrinsic", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "intrinsicsThatShouldHaveBeenLowered", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicShouldHaveBeenLowered;", "numberConversionMethods", "primitiveComparisonIntrinsics", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/PrimitiveComparison;", "typeToIrFun", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "operator", "Lorg/jetbrains/kotlin/lexer/KtSingleValueToken;", "unaryFunForPrimitives", "Companion", "Key", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrIntrinsicMethods {
    private final FqName anyFqn;
    private final FqName arrayFqn;
    private final FqName cloneableFqn;
    private final FqName intFqn;
    private final Map<Key, IntrinsicMethod> intrinsicsMap;
    private final IrBuiltIns irBuiltIns;
    private final FqName kClassFqn;
    private final FqName kotlinEnumsFqn;
    private final FqName kotlinFqn;
    private final FqName kotlinJvmFqn;
    private final FqName kotlinJvmInternalUnsafeFqn;
    private final FqName kotlinReflectFqn;
    private final FqName stringFqn;
    private final JvmSymbols symbols;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Increment INC = new Increment(1);
    private static final Increment DEC = new Increment(-1);
    private static final ExplicitEquals EXPLICIT_EQUALS = new ExplicitEquals();

    /* JADX INFO: compiled from: IrIntrinsicMethods.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class EntriesMappings {
        public static final /* synthetic */ kotlin.enums.EnumEntries<PrimitiveType> entries$0 = EnumEntriesKt.enumEntries(PrimitiveType.values());
    }

    public IrIntrinsicMethods(IrBuiltIns irBuiltIns, JvmSymbols symbols) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(symbols, "symbols");
        this.irBuiltIns = irBuiltIns;
        this.symbols = symbols;
        FqName fqName = StandardNames.BUILT_INS_PACKAGE_FQ_NAME;
        this.kotlinFqn = fqName;
        FqName fqName2 = new FqName("kotlin.jvm");
        this.kotlinJvmFqn = fqName2;
        FqName fqName3 = new FqName("kotlin.jvm.internal.unsafe");
        this.kotlinJvmInternalUnsafeFqn = fqName3;
        FqName fqName4 = StandardNames.KOTLIN_REFLECT_FQ_NAME;
        this.kotlinReflectFqn = fqName4;
        FqName fqName5 = new FqName("kotlin.enums");
        this.kotlinEnumsFqn = fqName5;
        FqName safe = StandardNames.FqNames.any.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe, "toSafe(...)");
        this.anyFqn = safe;
        FqName safe2 = StandardNames.FqNames.array.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe2, "toSafe(...)");
        this.arrayFqn = safe2;
        FqName safe3 = StandardNames.FqNames.cloneable.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe3, "toSafe(...)");
        this.cloneableFqn = safe3;
        FqName safe4 = StandardNames.FqNames._int.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe4, "toSafe(...)");
        this.intFqn = safe4;
        FqName safe5 = StandardNames.FqNames.kClass.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe5, "toSafe(...)");
        this.kClassFqn = safe5;
        FqName safe6 = StandardNames.FqNames.string.toSafe();
        Intrinsics.checkNotNullExpressionValue(safe6, "toSafe(...)");
        this.stringFqn = safe6;
        Companion companion = INSTANCE;
        Key key = companion.toKey(irBuiltIns.getEqeqSymbol());
        Intrinsics.checkNotNull(key);
        KtSingleValueToken EQEQ = KtTokens.EQEQ;
        Intrinsics.checkNotNullExpressionValue(EQEQ, "EQEQ");
        Key key2 = companion.toKey(irBuiltIns.getEqeqeqSymbol());
        Intrinsics.checkNotNull(key2);
        KtSingleValueToken EQEQEQ = KtTokens.EQEQEQ;
        Intrinsics.checkNotNullExpressionValue(EQEQEQ, "EQEQEQ");
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = irBuiltIns.getIeee754equalsFunByOperandType().get(irBuiltIns.getFloatClass());
        Intrinsics.checkNotNull(irSimpleFunctionSymbol);
        Key key3 = companion.toKey(irSimpleFunctionSymbol);
        Intrinsics.checkNotNull(key3);
        Type FLOAT_TYPE = Type.FLOAT_TYPE;
        Intrinsics.checkNotNullExpressionValue(FLOAT_TYPE, "FLOAT_TYPE");
        IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = irBuiltIns.getIeee754equalsFunByOperandType().get(irBuiltIns.getDoubleClass());
        Intrinsics.checkNotNull(irSimpleFunctionSymbol2);
        Key key4 = companion.toKey(irSimpleFunctionSymbol2);
        Intrinsics.checkNotNull(key4);
        Type DOUBLE_TYPE = Type.DOUBLE_TYPE;
        Intrinsics.checkNotNullExpressionValue(DOUBLE_TYPE, "DOUBLE_TYPE");
        Key key5 = companion.toKey(irBuiltIns.getBooleanNotSymbol());
        Intrinsics.checkNotNull(key5);
        Key key6 = companion.toKey(irBuiltIns.getNoWhenBranchMatchedExceptionSymbol());
        Intrinsics.checkNotNull(key6);
        Key key7 = companion.toKey(irBuiltIns.getIllegalArgumentExceptionSymbol());
        Intrinsics.checkNotNull(key7);
        Key key8 = companion.toKey(irBuiltIns.getCheckNotNullSymbol());
        Intrinsics.checkNotNull(key8);
        Key key9 = companion.toKey(irBuiltIns.getAndandSymbol());
        Intrinsics.checkNotNull(key9);
        Key key10 = companion.toKey(irBuiltIns.getOrorSymbol());
        Intrinsics.checkNotNull(key10);
        Key key11 = companion.toKey(irBuiltIns.getDataClassArrayMemberHashCodeSymbol());
        Intrinsics.checkNotNull(key11);
        Key key12 = companion.toKey(irBuiltIns.getDataClassArrayMemberToStringSymbol());
        Intrinsics.checkNotNull(key12);
        Key key13 = companion.toKey(symbols.getSingleArgumentInlineFunction());
        Intrinsics.checkNotNull(key13);
        Key key14 = companion.toKey(symbols.getUnsafeCoerceIntrinsic());
        Intrinsics.checkNotNull(key14);
        Key key15 = companion.toKey(symbols.getSignatureStringIntrinsic());
        Intrinsics.checkNotNull(key15);
        Key key16 = companion.toKey(symbols.getThrowNullPointerException());
        Intrinsics.checkNotNull(key16);
        Type objectType = Type.getObjectType("java/lang/NullPointerException");
        Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
        Key key17 = companion.toKey(symbols.getThrowTypeCastException());
        Intrinsics.checkNotNull(key17);
        Type objectType2 = Type.getObjectType("kotlin/TypeCastException");
        Intrinsics.checkNotNullExpressionValue(objectType2, "getObjectType(...)");
        Key key18 = companion.toKey(symbols.getThrowUnsupportedOperationException());
        Intrinsics.checkNotNull(key18);
        Type objectType3 = Type.getObjectType("java/lang/UnsupportedOperationException");
        Intrinsics.checkNotNullExpressionValue(objectType3, "getObjectType(...)");
        Key key19 = companion.toKey(symbols.getThrowIllegalAccessException());
        Intrinsics.checkNotNull(key19);
        Type objectType4 = Type.getObjectType("java/lang/IllegalAccessException");
        Intrinsics.checkNotNullExpressionValue(objectType4, "getObjectType(...)");
        Key key20 = companion.toKey(symbols.getThrowKotlinNothingValueException());
        Intrinsics.checkNotNull(key20);
        Key key21 = companion.toKey(symbols.getJvmIndyIntrinsic());
        Intrinsics.checkNotNull(key21);
        Key key22 = companion.toKey(symbols.getJvmDebuggerInvokeSpecialIntrinsic());
        Intrinsics.checkNotNull(key22);
        Key key23 = companion.toKey(symbols.getIntPostfixIncrDecr());
        Intrinsics.checkNotNull(key23);
        Key key24 = companion.toKey(symbols.getIntPrefixIncrDecr());
        Intrinsics.checkNotNull(key24);
        List listPlus = CollectionsKt.plus((Collection<? extends Pair>) CollectionsKt.plus((Collection<? extends Pair>) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(new Key(fqName2, new FqName(ExifInterface.GPS_DIRECTION_TRUE), "<get-javaClass>", CollectionsKt.emptyList()), JavaClassProperty.INSTANCE), TuplesKt.to(new Key(fqName2, safe5, "<get-javaObjectType>", CollectionsKt.emptyList()), GetJavaObjectType.INSTANCE), TuplesKt.to(new Key(fqName2, safe5, "<get-javaPrimitiveType>", CollectionsKt.emptyList()), GetJavaPrimitiveType.INSTANCE), TuplesKt.to(new Key(fqName2, safe5, "<get-java>", CollectionsKt.emptyList()), KClassJavaProperty.INSTANCE), TuplesKt.to(new Key(fqName3, null, "monitorEnter", CollectionsKt.listOf(safe)), MonitorInstruction.MONITOR_ENTER), TuplesKt.to(new Key(fqName3, null, "monitorExit", CollectionsKt.listOf(safe)), MonitorInstruction.MONITOR_EXIT), TuplesKt.to(new Key(fqName2, safe2, "isArrayOf", CollectionsKt.emptyList()), IsArrayOf.INSTANCE), TuplesKt.to(new Key(fqName, null, "arrayOfNulls", CollectionsKt.listOf(safe4)), NewArray.INSTANCE), TuplesKt.to(new Key(safe3, null, "clone", CollectionsKt.emptyList()), Clone.INSTANCE), TuplesKt.to(new Key(fqName, null, "enumValues", CollectionsKt.emptyList()), EnumValues.INSTANCE), TuplesKt.to(new Key(fqName, null, "enumValueOf", CollectionsKt.listOf(safe6)), EnumValueOf.INSTANCE), TuplesKt.to(new Key(fqName5, null, "enumEntries", CollectionsKt.emptyList()), EnumEntries.INSTANCE), TuplesKt.to(new Key(fqName, safe6, "plus", CollectionsKt.listOf(safe)), StringPlus.INSTANCE), TuplesKt.to(new Key(fqName4, null, "typeOf", CollectionsKt.emptyList()), TypeOf.INSTANCE), TuplesKt.to(key, new Equals(EQEQ)), TuplesKt.to(key2, new Equals(EQEQEQ)), TuplesKt.to(key3, new Ieee754Equals(FLOAT_TYPE)), TuplesKt.to(key4, new Ieee754Equals(DOUBLE_TYPE)), TuplesKt.to(key5, Not.INSTANCE), TuplesKt.to(key6, IrNoWhenBranchMatchedException.INSTANCE), TuplesKt.to(key7, IrIllegalArgumentException.INSTANCE), TuplesKt.to(key8, IrCheckNotNull.INSTANCE), TuplesKt.to(key9, AndAnd.INSTANCE), TuplesKt.to(key10, OrOr.INSTANCE), TuplesKt.to(key11, IrDataClassArrayMemberHashCode.INSTANCE), TuplesKt.to(key12, IrDataClassArrayMemberToString.INSTANCE), TuplesKt.to(key13, SingleArgumentInlineFunctionIntrinsic.INSTANCE), TuplesKt.to(key14, UnsafeCoerce.INSTANCE), TuplesKt.to(key15, SignatureString.INSTANCE), TuplesKt.to(key16, new ThrowException(objectType)), TuplesKt.to(key17, new ThrowException(objectType2)), TuplesKt.to(key18, new ThrowException(objectType3)), TuplesKt.to(key19, new ThrowException(objectType4)), TuplesKt.to(key20, ThrowKotlinNothingValueException.INSTANCE), TuplesKt.to(key21, JvmInvokeDynamic.INSTANCE), TuplesKt.to(key22, JvmDebuggerInvokeSpecial.INSTANCE), TuplesKt.to(key23, new IntIncr(false)), TuplesKt.to(key24, new IntIncr(true))}), (Iterable) numberConversionMethods()), (Iterable) unaryFunForPrimitives("plus", UnaryPlus.INSTANCE)), (Iterable) unaryFunForPrimitives("unaryPlus", UnaryPlus.INSTANCE)), (Iterable) unaryFunForPrimitives("minus", UnaryMinus.INSTANCE)), (Iterable) unaryFunForPrimitives("unaryMinus", UnaryMinus.INSTANCE)), (Iterable) unaryFunForPrimitives("inv", Inv.INSTANCE)), (Iterable) unaryFunForPrimitives("inc", INC)), (Iterable) unaryFunForPrimitives("dec", DEC)), (Iterable) unaryFunForPrimitives("hashCode", HashCode.INSTANCE)), (Iterable) binaryFunForPrimitives(Namer.EQUALS_METHOD_NAME, EXPLICIT_EQUALS, irBuiltIns.getAnyClass())), (Iterable) binaryFunForPrimitivesAcrossPrimitives("rangeTo", RangeTo.INSTANCE)), (Iterable) binaryFunForPrimitivesAcrossPrimitives("rangeUntil", RangeUntil.INSTANCE)), (Iterable) binaryOp("plus", 96)), (Iterable) binaryOp("minus", 100)), (Iterable) binaryOp("times", 104)), (Iterable) binaryOp("div", 108)), (Iterable) binaryOp("mod", 112)), (Iterable) binaryOp("rem", 112)), (Iterable) binaryOp("shl", 120)), (Iterable) binaryOp("shr", 122)), (Iterable) binaryOp("ushr", 124)), (Iterable) binaryOp("and", 126)), (Iterable) binaryOp("or", 128)), (Iterable) binaryOp("xor", 130)), (Iterable) binaryFunForPrimitivesAcrossPrimitives("compareTo", CompareTo.INSTANCE)), companion.createKeyMapping(Not.INSTANCE, irBuiltIns.getBooleanClass(), "not", new IrClassifierSymbol[0])), companion.createKeyMapping(StringGetChar.INSTANCE, irBuiltIns.getStringClass(), "get", irBuiltIns.getIntClass()));
        Collection<IrClassSymbol> collectionValues = symbols.getPrimitiveIteratorsByType().values();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionValues, 10));
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            arrayList.add(INSTANCE.createKeyMapping(IteratorNext.INSTANCE, (IrClassSymbol) it.next(), ES6Iterator.NEXT_METHOD, new IrClassifierSymbol[0]));
        }
        List listPlus2 = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) listPlus, (Iterable) arrayList), (Iterable) arrayMethods());
        Map<IrClassifierSymbol, IrSimpleFunctionSymbol> lessFunByOperandType = this.irBuiltIns.getLessFunByOperandType();
        KtSingleValueToken LT = KtTokens.LT;
        Intrinsics.checkNotNullExpressionValue(LT, "LT");
        List listPlus3 = CollectionsKt.plus((Collection) listPlus2, (Iterable) primitiveComparisonIntrinsics(lessFunByOperandType, LT));
        Map<IrClassifierSymbol, IrSimpleFunctionSymbol> lessOrEqualFunByOperandType = this.irBuiltIns.getLessOrEqualFunByOperandType();
        KtSingleValueToken LTEQ = KtTokens.LTEQ;
        Intrinsics.checkNotNullExpressionValue(LTEQ, "LTEQ");
        List listPlus4 = CollectionsKt.plus((Collection) listPlus3, (Iterable) primitiveComparisonIntrinsics(lessOrEqualFunByOperandType, LTEQ));
        Map<IrClassifierSymbol, IrSimpleFunctionSymbol> greaterFunByOperandType = this.irBuiltIns.getGreaterFunByOperandType();
        KtSingleValueToken GT = KtTokens.GT;
        Intrinsics.checkNotNullExpressionValue(GT, "GT");
        List listPlus5 = CollectionsKt.plus((Collection) listPlus4, (Iterable) primitiveComparisonIntrinsics(greaterFunByOperandType, GT));
        Map<IrClassifierSymbol, IrSimpleFunctionSymbol> greaterOrEqualFunByOperandType = this.irBuiltIns.getGreaterOrEqualFunByOperandType();
        KtSingleValueToken GTEQ = KtTokens.GTEQ;
        Intrinsics.checkNotNullExpressionValue(GTEQ, "GTEQ");
        this.intrinsicsMap = MapsKt.toMap(CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) listPlus5, (Iterable) primitiveComparisonIntrinsics(greaterOrEqualFunByOperandType, GTEQ)), (Iterable) intrinsicsThatShouldHaveBeenLowered()));
    }

    public final IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    public final JvmSymbols getSymbols() {
        return this.symbols;
    }

    private final List<Pair<Key, IntrinsicShouldHaveBeenLowered>> intrinsicsThatShouldHaveBeenLowered() {
        Map<PrimitiveType, IrClassSymbol> primitiveTypesToPrimitiveArrays = this.symbols.getPrimitiveTypesToPrimitiveArrays();
        ArrayList arrayList = new ArrayList(primitiveTypesToPrimitiveArrays.size());
        Iterator<Map.Entry<PrimitiveType, IrClassSymbol>> it = primitiveTypesToPrimitiveArrays.entrySet().iterator();
        while (it.hasNext()) {
            IrClassSymbol value = it.next().getValue();
            String strAsString = value.getOwner().getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            arrayList.add(new Key(this.kotlinFqn, null, CapitalizeDecapitalizeKt.decapitalizeAsciiOnly(strAsString) + "Of", CollectionsKt.listOf(IrUtilsKt.getFqNameWhenAvailable(value.getOwner()))));
        }
        List listPlus = CollectionsKt.plus((Collection) arrayList, (Iterable) CollectionsKt.listOf((Object[]) new Key[]{new Key(this.kotlinFqn, this.anyFqn, "toString", CollectionsKt.emptyList()), new Key(this.kotlinFqn, null, VarargLoweringKt.ARRAY_OF_NAME, CollectionsKt.listOf(this.arrayFqn)), new Key(this.stringFqn, null, "plus", CollectionsKt.listOf(this.anyFqn))}));
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPlus, 10));
        Iterator it2 = listPlus.iterator();
        while (it2.hasNext()) {
            arrayList2.add(TuplesKt.to((Key) it2.next(), IntrinsicShouldHaveBeenLowered.INSTANCE));
        }
        return arrayList2;
    }

    private final IrClassSymbol getSymbol(PrimitiveType primitiveType) {
        IrType irType = this.irBuiltIns.getPrimitiveTypeToIrType().get(primitiveType);
        Intrinsics.checkNotNull(irType);
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        Intrinsics.checkNotNull(classOrNull);
        return classOrNull;
    }

    public final IntrinsicMethod getIntrinsic(IrFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return this.intrinsicsMap.get(INSTANCE.toKey(symbol));
    }

    private final List<Pair<Key, IntrinsicMethod>> unaryFunForPrimitives(String name2, IntrinsicMethod intrinsic) {
        kotlin.enums.EnumEntries<PrimitiveType> enumEntries = EntriesMappings.entries$0;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(enumEntries, 10));
        Iterator<PrimitiveType> it = enumEntries.iterator();
        while (it.hasNext()) {
            arrayList.add(INSTANCE.createKeyMapping(intrinsic, getSymbol(it.next()), name2, new IrClassifierSymbol[0]));
        }
        return arrayList;
    }

    private final List<Pair<Key, IntrinsicMethod>> binaryFunForPrimitivesAcrossPrimitives(String name2, IntrinsicMethod intrinsic) {
        kotlin.enums.EnumEntries<PrimitiveType> enumEntries = EntriesMappings.entries$0;
        ArrayList arrayList = new ArrayList();
        Iterator<PrimitiveType> it = enumEntries.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, binaryFunForPrimitives(name2, intrinsic, getSymbol(it.next())));
        }
        return arrayList;
    }

    private final List<Pair<Key, IntrinsicMethod>> binaryFunForPrimitives(String name2, IntrinsicMethod intrinsic, IrClassifierSymbol parameter) {
        kotlin.enums.EnumEntries<PrimitiveType> enumEntries = EntriesMappings.entries$0;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(enumEntries, 10));
        Iterator<PrimitiveType> it = enumEntries.iterator();
        while (it.hasNext()) {
            arrayList.add(INSTANCE.createKeyMapping(intrinsic, getSymbol(it.next()), name2, parameter));
        }
        return arrayList;
    }

    private final List<Pair<Key, IntrinsicMethod>> binaryOp(String methodName, int opcode) {
        return binaryFunForPrimitivesAcrossPrimitives(methodName, new BinaryOp(opcode));
    }

    private final List<Pair<Key, IntrinsicMethod>> numberConversionMethods() {
        Set<PrimitiveType> set = PrimitiveType.NUMBER_TYPES;
        ArrayList arrayList = new ArrayList();
        for (PrimitiveType primitiveType : set) {
            ImmutableSet<Name> NUMBER_CONVERSIONS = OperatorConventions.NUMBER_CONVERSIONS;
            Intrinsics.checkNotNullExpressionValue(NUMBER_CONVERSIONS, "NUMBER_CONVERSIONS");
            ImmutableSet<Name> immutableSet = NUMBER_CONVERSIONS;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(immutableSet, 10));
            for (Name name2 : immutableSet) {
                Companion companion = INSTANCE;
                NumberCast numberCast = NumberCast.INSTANCE;
                IrClassSymbol symbol = getSymbol(primitiveType);
                String strAsString = name2.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                arrayList2.add(companion.createKeyMapping(numberCast, symbol, strAsString, new IrClassifierSymbol[0]));
            }
            CollectionsKt.addAll(arrayList, arrayList2);
        }
        return arrayList;
    }

    private final List<Pair<Key, IntrinsicMethod>> arrayMethods() {
        Map<IrClassSymbol, PrimitiveType> primitiveArraysToPrimitiveTypes = this.symbols.getPrimitiveArraysToPrimitiveTypes();
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<IrClassSymbol, PrimitiveType> entry : primitiveArraysToPrimitiveTypes.entrySet()) {
            CollectionsKt.addAll(arrayList, arrayMethods(getSymbol(entry.getValue()), entry.getKey()));
        }
        return CollectionsKt.plus((Collection) arrayList, (Iterable) arrayMethods(((IrTypeParameter) CollectionsKt.single((List) this.symbols.getArray().getOwner().getTypeParameters())).getSymbol(), this.symbols.getArray()));
    }

    private final List<Pair<Key, IntrinsicMethod>> arrayMethods(IrClassifierSymbol elementClass, IrClassSymbol arrayClass) {
        Companion companion = INSTANCE;
        return CollectionsKt.listOf((Object[]) new Pair[]{companion.createKeyMapping(ArraySize.INSTANCE, arrayClass, "<get-size>", new IrClassifierSymbol[0]), companion.createKeyMapping(NewArray.INSTANCE, arrayClass, "<init>", this.irBuiltIns.getIntClass()), companion.createKeyMapping(ArraySet.INSTANCE, arrayClass, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, this.irBuiltIns.getIntClass(), elementClass), companion.createKeyMapping(ArrayGet.INSTANCE, arrayClass, "get", this.irBuiltIns.getIntClass()), companion.createKeyMapping(Clone.INSTANCE, arrayClass, "clone", new IrClassifierSymbol[0]), companion.createKeyMapping(ArrayIterator.INSTANCE, arrayClass, "iterator", new IrClassifierSymbol[0])});
    }

    private final List<Pair<Key, PrimitiveComparison>> primitiveComparisonIntrinsics(Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol> typeToIrFun, KtSingleValueToken operator) {
        Pair pair;
        kotlin.enums.EnumEntries<PrimitiveType> enumEntries = EntriesMappings.entries$0;
        ArrayList arrayList = new ArrayList();
        for (PrimitiveType primitiveType : enumEntries) {
            IrType irType = this.irBuiltIns.getPrimitiveTypeToIrType().get(primitiveType);
            Intrinsics.checkNotNull(irType);
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = typeToIrFun.get(IrTypesKt.getClassifierOrFail(irType));
            if (irSimpleFunctionSymbol == null) {
                pair = null;
            } else {
                Key key = INSTANCE.toKey(irSimpleFunctionSymbol);
                Intrinsics.checkNotNull(key);
                pair = TuplesKt.to(key, new PrimitiveComparison(primitiveType, operator));
            }
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        return arrayList;
    }

    /* JADX INFO: compiled from: IrIntrinsicMethods.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u000e\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\b¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0006HÆ\u0003J\u0011\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\bHÆ\u0003J;\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\bHÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0019\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IrIntrinsicMethods$Key;", "", "owner", "Lorg/jetbrains/kotlin/name/FqName;", "receiverParameterTypeName", "name", "", "valueParameterTypeNames", "", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/FqName;Ljava/lang/String;Ljava/util/List;)V", "getName", "()Ljava/lang/String;", "getOwner", "()Lorg/jetbrains/kotlin/name/FqName;", "getReceiverParameterTypeName", "getValueParameterTypeNames", "()Ljava/util/List;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Key {
        private final String name;
        private final FqName owner;
        private final FqName receiverParameterTypeName;
        private final List<FqName> valueParameterTypeNames;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Key copy$default(Key key, FqName fqName, FqName fqName2, String str, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                fqName = key.owner;
            }
            if ((i & 2) != 0) {
                fqName2 = key.receiverParameterTypeName;
            }
            if ((i & 4) != 0) {
                str = key.name;
            }
            if ((i & 8) != 0) {
                list = key.valueParameterTypeNames;
            }
            return key.copy(fqName, fqName2, str, list);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FqName getOwner() {
            return this.owner;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final FqName getReceiverParameterTypeName() {
            return this.receiverParameterTypeName;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final String getName() {
            return this.name;
        }

        public final List<FqName> component4() {
            return this.valueParameterTypeNames;
        }

        public final Key copy(FqName owner, FqName receiverParameterTypeName, String name2, List<FqName> valueParameterTypeNames) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(valueParameterTypeNames, "valueParameterTypeNames");
            return new Key(owner, receiverParameterTypeName, name2, valueParameterTypeNames);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Key)) {
                return false;
            }
            Key key = (Key) other;
            return Intrinsics.areEqual(this.owner, key.owner) && Intrinsics.areEqual(this.receiverParameterTypeName, key.receiverParameterTypeName) && Intrinsics.areEqual(this.name, key.name) && Intrinsics.areEqual(this.valueParameterTypeNames, key.valueParameterTypeNames);
        }

        public int hashCode() {
            int iHashCode = this.owner.hashCode() * 31;
            FqName fqName = this.receiverParameterTypeName;
            return ((((iHashCode + (fqName == null ? 0 : fqName.hashCode())) * 31) + this.name.hashCode()) * 31) + this.valueParameterTypeNames.hashCode();
        }

        public String toString() {
            return "Key(owner=" + this.owner + ", receiverParameterTypeName=" + this.receiverParameterTypeName + ", name=" + this.name + ", valueParameterTypeNames=" + this.valueParameterTypeNames + ')';
        }

        public Key(FqName owner, FqName fqName, String name2, List<FqName> valueParameterTypeNames) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(valueParameterTypeNames, "valueParameterTypeNames");
            this.owner = owner;
            this.receiverParameterTypeName = fqName;
            this.name = name2;
            this.valueParameterTypeNames = valueParameterTypeNames;
        }

        public final String getName() {
            return this.name;
        }

        public final FqName getOwner() {
            return this.owner;
        }

        public final FqName getReceiverParameterTypeName() {
            return this.receiverParameterTypeName;
        }

        public final List<FqName> getValueParameterTypeNames() {
            return this.valueParameterTypeNames;
        }
    }

    /* JADX INFO: compiled from: IrIntrinsicMethods.kt */
    @Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JE\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u0011\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130\u0012\"\u00020\u0013H\u0002¢\u0006\u0002\u0010\u0014J\u0014\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\u0014\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0013H\u0002J\u000e\u0010\u0019\u001a\u0004\u0018\u00010\n*\u00020\u001aH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IrIntrinsicMethods$Companion;", "", "()V", "DEC", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/Increment;", "EXPLICIT_EQUALS", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/ExplicitEquals;", "INC", "createKeyMapping", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IrIntrinsicMethods$Key;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "intrinsic", "klass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "name", "", "args", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "(Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Ljava/lang/String;[Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;)Lkotlin/Pair;", "getParameterFqName", "Lorg/jetbrains/kotlin/name/FqName;", "parameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "toKey", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Key toKey(IrFunctionSymbol irFunctionSymbol) {
            FqName packageFqName;
            IrDeclarationParent parent = irFunctionSymbol.getOwner().getParent();
            boolean z = parent instanceof IrClass;
            if (z && IrUtilsKt.isFileClass((IrDeclaration) parent)) {
                IrDeclarationParent parent2 = ((IrClass) parent).getParent();
                Intrinsics.checkNotNull(parent2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrPackageFragment");
                packageFqName = ((IrPackageFragment) parent2).getPackageFqName();
            } else if (z) {
                packageFqName = IrUtilsKt.getFqNameWhenAvailable((IrDeclarationWithName) parent);
                if (packageFqName == null) {
                    return null;
                }
            } else {
                if (!(parent instanceof IrPackageFragment)) {
                    return null;
                }
                packageFqName = ((IrPackageFragment) parent).getPackageFqName();
            }
            FqName parameterFqName = getParameterFqName(irFunctionSymbol.getOwner().getExtensionReceiverParameter());
            String strAsString = irFunctionSymbol.getOwner().getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            List<IrValueParameter> valueParameters = irFunctionSymbol.getOwner().getValueParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
            Iterator<T> it = valueParameters.iterator();
            while (it.hasNext()) {
                arrayList.add(getParameterFqName((IrValueParameter) it.next()));
            }
            return new Key(packageFqName, parameterFqName, strAsString, arrayList);
        }

        private final FqName getParameterFqName(IrValueParameter parameter) {
            IrType type;
            return getParameterFqName((parameter == null || (type = parameter.getType()) == null) ? null : IrTypesKt.getClassifierOrNull(type));
        }

        private final FqName getParameterFqName(IrClassifierSymbol parameter) {
            IrSymbolOwner owner;
            if (parameter == null || (owner = parameter.getOwner()) == null) {
                return null;
            }
            if (owner instanceof IrClass) {
                return IrUtilsKt.getFqNameWhenAvailable((IrDeclarationWithName) owner);
            }
            if (owner instanceof IrTypeParameter) {
                return new FqName(((IrTypeParameter) owner).getName().asString());
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Pair<Key, IntrinsicMethod> createKeyMapping(IntrinsicMethod intrinsic, IrClassSymbol klass, String name2, IrClassifierSymbol... args) {
            FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(klass.getOwner());
            Intrinsics.checkNotNull(fqNameWhenAvailable);
            ArrayList arrayList = new ArrayList(args.length);
            for (IrClassifierSymbol irClassifierSymbol : args) {
                arrayList.add(IrIntrinsicMethods.INSTANCE.getParameterFqName(irClassifierSymbol));
            }
            return TuplesKt.to(new Key(fqNameWhenAvailable, null, name2, arrayList), intrinsic);
        }
    }
}
