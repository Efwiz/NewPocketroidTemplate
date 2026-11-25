package org.jetbrains.kotlin.codegen.intrinsics;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.backend.jvm.lower.VarargLoweringKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.com.google.common.collect.UnmodifiableIterator;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class IntrinsicMethods {
    public static final String INTRINSICS_CLASS_NAME = "kotlin/jvm/internal/Intrinsics";
    private final IntrinsicsMap intrinsicsMap;
    private static final FqName KOTLIN_JVM = new FqName("kotlin.jvm");
    static final FqNameUnsafe RECEIVER_PARAMETER_FQ_NAME = new FqNameUnsafe(ExifInterface.GPS_DIRECTION_TRUE);
    private static final FqNameUnsafe KOTLIN_UINT = new FqNameUnsafe("kotlin.UInt");
    private static final FqNameUnsafe KOTLIN_ULONG = new FqNameUnsafe("kotlin.ULong");
    private static final IntrinsicMethod UNARY_MINUS = new UnaryMinus();
    private static final IntrinsicMethod UNARY_PLUS = new UnaryPlus();
    private static final IntrinsicMethod NUMBER_CAST = new NumberCast();
    private static final IntrinsicMethod INV = new Inv();
    private static final IntrinsicMethod RANGE_TO = new RangeTo();
    private static final IntrinsicMethod INC = new Increment(1);
    private static final IntrinsicMethod DEC = new Increment(-1);
    private static final IntrinsicMethod ARRAY_SIZE = new ArraySize();
    private static final IteratorNext ITERATOR_NEXT = new IteratorNext();
    private static final ArraySet ARRAY_SET = new ArraySet();
    private static final ArrayGet ARRAY_GET = new ArrayGet();
    private static final StringPlus STRING_PLUS = new StringPlus();
    private static final ToString TO_STRING = new ToString();
    private static final Clone CLONE = new Clone();
    private static final IntrinsicMethod ARRAY_ITERATOR = new ArrayIterator();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 3:
            case 6:
                objArr[0] = "methodName";
                break;
            case 2:
            case 5:
                objArr[0] = "classFqName";
                break;
            case 4:
            case 7:
                objArr[0] = "implementation";
                break;
            case 8:
                objArr[0] = "descriptor";
                break;
            default:
                objArr[0] = "arrayTypeFqName";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethods";
        switch (i) {
            case 1:
                objArr[2] = "declareBinaryOp";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[2] = "declareIntrinsicFunction";
                break;
            case 8:
                objArr[2] = "getIntrinsic";
                break;
            default:
                objArr[2] = "declareArrayMethods";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public IntrinsicMethods(JvmTarget jvmTarget) {
        this(false);
    }

    public IntrinsicMethods(boolean z) {
        IntrinsicsMap intrinsicsMap = new IntrinsicsMap();
        this.intrinsicsMap = intrinsicsMap;
        FqName fqName = KOTLIN_JVM;
        intrinsicsMap.registerIntrinsic(fqName, RECEIVER_PARAMETER_FQ_NAME, "javaClass", -1, JavaClassProperty.INSTANCE);
        intrinsicsMap.registerIntrinsic(fqName, StandardNames.FqNames.kClass, SuffixConstants.EXTENSION_java, -1, new KClassJavaProperty());
        intrinsicsMap.registerIntrinsic(fqName, StandardNames.FqNames.kClass, "javaObjectType", -1, new KClassJavaObjectTypeProperty());
        intrinsicsMap.registerIntrinsic(fqName, StandardNames.FqNames.kClass, "javaPrimitiveType", -1, new KClassJavaPrimitiveTypeProperty());
        intrinsicsMap.registerIntrinsic(StandardNames.FqNames.kCallable.toSafe(), null, "name", -1, new KCallableNameProperty());
        intrinsicsMap.registerIntrinsic(new FqName("kotlin.jvm.internal.unsafe"), null, "monitorEnter", 1, MonitorInstruction.MONITOR_ENTER);
        intrinsicsMap.registerIntrinsic(new FqName("kotlin.jvm.internal.unsafe"), null, "monitorExit", 1, MonitorInstruction.MONITOR_EXIT);
        intrinsicsMap.registerIntrinsic(fqName, StandardNames.FqNames.array, "isArrayOf", 0, new IsArrayOf());
        intrinsicsMap.registerIntrinsic(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, StandardNames.FqNames.kProperty0, "isInitialized", -1, LateinitIsInitialized.INSTANCE);
        intrinsicsMap.registerIntrinsic(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, null, VarargLoweringKt.ARRAY_OF_NAME, 1, new ArrayOf());
        intrinsicsMap.registerIntrinsic(new FqName("kotlin.collections"), new FqNameUnsafe("kotlin.collections.MutableMap"), FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, 2, new MutableMapSet());
        UnmodifiableIterator<Name> it = OperatorConventions.NUMBER_CONVERSIONS.asList().iterator();
        while (it.hasNext()) {
            String strAsString = it.next().asString();
            declareIntrinsicFunction(StandardNames.FqNames.number, strAsString, 0, NUMBER_CAST);
            Iterator<PrimitiveType> it2 = PrimitiveType.NUMBER_TYPES.iterator();
            while (it2.hasNext()) {
                declareIntrinsicFunction(it2.next().getTypeFqName(), strAsString, 0, NUMBER_CAST);
            }
        }
        Iterator<PrimitiveType> it3 = PrimitiveType.NUMBER_TYPES.iterator();
        while (it3.hasNext()) {
            FqName typeFqName = it3.next().getTypeFqName();
            IntrinsicMethod intrinsicMethod = UNARY_PLUS;
            declareIntrinsicFunction(typeFqName, "plus", 0, intrinsicMethod);
            declareIntrinsicFunction(typeFqName, "unaryPlus", 0, intrinsicMethod);
            IntrinsicMethod intrinsicMethod2 = UNARY_MINUS;
            declareIntrinsicFunction(typeFqName, "minus", 0, intrinsicMethod2);
            declareIntrinsicFunction(typeFqName, "unaryMinus", 0, intrinsicMethod2);
            declareIntrinsicFunction(typeFqName, "inv", 0, INV);
            declareIntrinsicFunction(typeFqName, "rangeTo", 1, RANGE_TO);
            declareIntrinsicFunction(typeFqName, "inc", 0, INC);
            declareIntrinsicFunction(typeFqName, "dec", 0, DEC);
        }
        HashCode hashCode = new HashCode();
        for (PrimitiveType primitiveType : PrimitiveType.values()) {
            FqName typeFqName2 = primitiveType.getTypeFqName();
            declareIntrinsicFunction(typeFqName2, Namer.EQUALS_METHOD_NAME, 1, new EqualsThrowingNpeForNullReceiver(AsmUtil.asmTypeByFqNameWithoutInnerClasses(JvmPrimitiveType.get(primitiveType).getWrapperFqName())));
            declareIntrinsicFunction(typeFqName2, "hashCode", 0, hashCode);
            declareIntrinsicFunction(typeFqName2, "toString", 0, TO_STRING);
            this.intrinsicsMap.registerIntrinsic(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, null, CapitalizeDecapitalizeKt.decapitalizeAsciiOnly(primitiveType.getArrayTypeName().asString()) + "Of", 1, new ArrayOf());
        }
        declareBinaryOp("plus", 96);
        declareBinaryOp("minus", 100);
        declareBinaryOp("times", 104);
        declareBinaryOp("div", 108);
        declareBinaryOp("mod", 112);
        declareBinaryOp("rem", 112);
        declareBinaryOp("shl", 120);
        declareBinaryOp("shr", 122);
        declareBinaryOp("ushr", 124);
        declareBinaryOp("and", 126);
        declareBinaryOp("or", 128);
        declareBinaryOp("xor", 130);
        declareIntrinsicFunction(StandardNames.FqNames._boolean, "not", 0, new Not());
        declareIntrinsicFunction(StandardNames.FqNames.string, "plus", 1, new Concat());
        declareIntrinsicFunction(StandardNames.FqNames.string, "get", 1, new StringGetChar());
        if (z) {
            this.intrinsicsMap.registerIntrinsic(StandardNames.TEXT_PACKAGE_FQ_NAME, StandardNames.FqNames.string, "trimMargin", 1, new TrimMargin());
            this.intrinsicsMap.registerIntrinsic(StandardNames.TEXT_PACKAGE_FQ_NAME, StandardNames.FqNames.string, "trimIndent", 0, new TrimIndent());
        }
        declareIntrinsicFunction(StandardNames.FqNames.cloneable, "clone", 0, CLONE);
        this.intrinsicsMap.registerIntrinsic(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, StandardNames.FqNames.any, "toString", 0, TO_STRING);
        this.intrinsicsMap.registerIntrinsic(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, StandardNames.FqNames.string, "plus", 1, STRING_PLUS);
        this.intrinsicsMap.registerIntrinsic(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, null, "arrayOfNulls", 1, new NewArray());
        for (PrimitiveType primitiveType2 : PrimitiveType.values()) {
            declareIntrinsicFunction(primitiveType2.getTypeFqName(), "compareTo", 1, new CompareTo());
            declareIntrinsicFunction(StandardNames.COLLECTIONS_PACKAGE_FQ_NAME.child(Name.identifier(primitiveType2.getTypeName().asString() + "Iterator")), ES6Iterator.NEXT_METHOD, 0, ITERATOR_NEXT);
        }
        declareArrayMethods();
        Java8UIntDivide java8UIntDivide = new Java8UIntDivide();
        IntrinsicsMap intrinsicsMap2 = this.intrinsicsMap;
        FqNameUnsafe fqNameUnsafe = KOTLIN_UINT;
        intrinsicsMap2.registerIntrinsic(fqNameUnsafe.toSafe(), null, "div", 1, java8UIntDivide);
        this.intrinsicsMap.registerIntrinsic(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, null, "uintDivide", 2, java8UIntDivide);
        Java8UIntRemainder java8UIntRemainder = new Java8UIntRemainder();
        this.intrinsicsMap.registerIntrinsic(fqNameUnsafe.toSafe(), null, "rem", 1, java8UIntRemainder);
        this.intrinsicsMap.registerIntrinsic(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, null, "uintRemainder", 2, java8UIntRemainder);
        Java8UIntCompare java8UIntCompare = new Java8UIntCompare();
        this.intrinsicsMap.registerIntrinsic(fqNameUnsafe.toSafe(), null, "compareTo", 1, java8UIntCompare);
        this.intrinsicsMap.registerIntrinsic(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, null, "uintCompare", 2, java8UIntCompare);
        this.intrinsicsMap.registerIntrinsic(fqNameUnsafe.toSafe(), null, "toString", 0, new Java8UIntToString());
        Java8ULongDivide java8ULongDivide = new Java8ULongDivide();
        IntrinsicsMap intrinsicsMap3 = this.intrinsicsMap;
        FqNameUnsafe fqNameUnsafe2 = KOTLIN_ULONG;
        intrinsicsMap3.registerIntrinsic(fqNameUnsafe2.toSafe(), null, "div", 1, java8ULongDivide);
        this.intrinsicsMap.registerIntrinsic(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, null, "ulongDivide", 2, java8ULongDivide);
        Java8ULongRemainder java8ULongRemainder = new Java8ULongRemainder();
        this.intrinsicsMap.registerIntrinsic(fqNameUnsafe2.toSafe(), null, "rem", 1, java8ULongRemainder);
        this.intrinsicsMap.registerIntrinsic(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, null, "ulongRemainder", 2, java8ULongRemainder);
        Java8ULongCompare java8ULongCompare = new Java8ULongCompare();
        this.intrinsicsMap.registerIntrinsic(fqNameUnsafe2.toSafe(), null, "compareTo", 1, java8ULongCompare);
        this.intrinsicsMap.registerIntrinsic(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, null, "ulongCompare", 2, java8ULongCompare);
        this.intrinsicsMap.registerIntrinsic(fqNameUnsafe2.toSafe(), null, "toString", 0, new Java8ULongToString());
    }

    private void declareArrayMethods() {
        for (JvmPrimitiveType jvmPrimitiveType : JvmPrimitiveType.values()) {
            declareArrayMethods(jvmPrimitiveType.getPrimitiveType().getArrayTypeFqName());
        }
        declareArrayMethods(StandardNames.FqNames.array.toSafe());
    }

    private void declareArrayMethods(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(0);
        }
        declareIntrinsicFunction(fqName, "size", -1, ARRAY_SIZE);
        declareIntrinsicFunction(fqName, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, 2, ARRAY_SET);
        declareIntrinsicFunction(fqName, "get", 1, ARRAY_GET);
        declareIntrinsicFunction(fqName, "clone", 0, CLONE);
        declareIntrinsicFunction(fqName, "iterator", 0, ARRAY_ITERATOR);
        declareIntrinsicFunction(fqName, "<init>", 2, ArrayConstructor.INSTANCE);
    }

    private void declareBinaryOp(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        BinaryOp binaryOp = new BinaryOp(i);
        for (PrimitiveType primitiveType : PrimitiveType.values()) {
            declareIntrinsicFunction(primitiveType.getTypeFqName(), str, 1, binaryOp);
        }
    }

    private void declareIntrinsicFunction(FqName fqName, String str, int i, IntrinsicMethod intrinsicMethod) {
        if (fqName == null) {
            $$$reportNull$$$0(2);
        }
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (intrinsicMethod == null) {
            $$$reportNull$$$0(4);
        }
        this.intrinsicsMap.registerIntrinsic(fqName, null, str, i, intrinsicMethod);
    }

    private void declareIntrinsicFunction(FqNameUnsafe fqNameUnsafe, String str, int i, IntrinsicMethod intrinsicMethod) {
        if (fqNameUnsafe == null) {
            $$$reportNull$$$0(5);
        }
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (intrinsicMethod == null) {
            $$$reportNull$$$0(7);
        }
        this.intrinsicsMap.registerIntrinsic(fqNameUnsafe.toSafe(), null, str, i, intrinsicMethod);
    }

    public IntrinsicMethod getIntrinsic(CallableMemberDescriptor callableMemberDescriptor) {
        if (callableMemberDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        return this.intrinsicsMap.getIntrinsic(callableMemberDescriptor);
    }
}
