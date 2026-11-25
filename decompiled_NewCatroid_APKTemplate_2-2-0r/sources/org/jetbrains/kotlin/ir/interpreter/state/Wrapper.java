package org.jetbrains.kotlin.ir.interpreter.state;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.RangeUtilKt;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: Wrapper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000 ,2\u00020\u0001:\u0001,B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\"\u001a\u0004\u0018\u00010#2\u0006\u0010$\u001a\u00020%H\u0016J\u0012\u0010&\u001a\u0004\u0018\u00010'2\u0006\u0010(\u001a\u00020#H\u0002J\u0010\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010(\u001a\u00020#J\b\u0010+\u001a\u00020'H\u0016R$\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u0002`\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R.\u0010\u0012\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0018\u00010\u0013j\u0004\u0018\u0001`\u0014X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0016\u0010\u0019\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0000X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b \u0010!¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/Wrapper;", "Lorg/jetbrains/kotlin/ir/interpreter/state/Complex;", "value", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;)V", "fields", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Fields;", "getFields", "()Ljava/util/Map;", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "outerClass", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Field;", "getOuterClass", "()Lkotlin/Pair;", "setOuterClass", "(Lkotlin/Pair;)V", "receiverClass", "Ljava/lang/Class;", "superWrapperClass", "getSuperWrapperClass", "()Lorg/jetbrains/kotlin/ir/interpreter/state/Wrapper;", "setSuperWrapperClass", "(Lorg/jetbrains/kotlin/ir/interpreter/state/Wrapper;)V", "getValue", "()Ljava/lang/Object;", "getIrFunctionByIrCall", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "getJavaOriginalName", "", "irFunction", "getMethod", "Ljava/lang/invoke/MethodHandle;", "toString", "Companion", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Wrapper implements Complex {
    private final Map<IrSymbol, State> fields;
    private final IrClass irClass;
    private Pair<? extends IrSymbol, ? extends State> outerClass;
    private final Class<? extends Object> receiverClass;
    private Wrapper superWrapperClass;
    private final Object value;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Map<String, Object> companionObjectValue = MapsKt.mapOf(TuplesKt.to("kotlin.text.Regex$Companion", Regex.INSTANCE));
    private static final Set<String> intrinsicClasses = SetsKt.setOf((Object[]) new String[]{"kotlin.text.StringBuilder", "kotlin.Pair", "kotlin.collections.ArrayList", "kotlin.collections.HashMap", "kotlin.collections.LinkedHashMap", "kotlin.collections.HashSet", "kotlin.collections.LinkedHashSet", "kotlin.text.RegexOption", "kotlin.text.Regex", "kotlin.text.Regex.Companion", "kotlin.text.MatchGroup"});
    private static final Set<String> intrinsicJavaClasses = SetsKt.setOf((Object[]) new String[]{CommonClassNames.JAVA_LANG_STRING_BUILDER, CommonClassNames.JAVA_UTIL_ARRAY_LIST, CommonClassNames.JAVA_UTIL_LINKED_HASH_MAP, CommonClassNames.JAVA_UTIL_LINKED_HASH_SET, CommonClassNames.JAVA_LANG_EXCEPTION, "java.util.NoSuchElementException", CommonClassNames.JAVA_LANG_NULL_POINTER_EXCEPTION, "java.lang.IllegalArgumentException", "java.lang.ArithmeticException", "java.lang.UnsupportedOperationException"});
    private static final Map<String, String> intrinsicFunctionToHandler = MapsKt.mapOf(TuplesKt.to("Array.kotlin.collections.asList()", "kotlin.collections.ArraysKt"), TuplesKt.to("kotlin.collections.mutableListOf(Array)", "kotlin.collections.CollectionsKt"), TuplesKt.to("kotlin.collections.arrayListOf(Array)", "kotlin.collections.CollectionsKt"), TuplesKt.to("Char.kotlin.text.isWhitespace()", "kotlin.text.CharsKt"), TuplesKt.to("Array.kotlin.collections.toMutableList()", "kotlin.collections.ArraysKt"), TuplesKt.to("Array.kotlin.collections.copyToArrayOfAny(Boolean)", "kotlin.collections.CollectionsKt"));
    private static final Set<String> ranges = SetsKt.setOf((Object[]) new String[]{RangeUtilKt.CHAR_RANGE_FQN, RangeUtilKt.INT_RANGE_FQN, RangeUtilKt.LONG_RANGE_FQN});

    @Override // org.jetbrains.kotlin.ir.interpreter.state.Complex, org.jetbrains.kotlin.ir.interpreter.state.State
    public IrFunction getIrFunctionByIrCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Wrapper(Object value, IrClass irClass, IrInterpreterEnvironment environment) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(environment, "environment");
        this.value = value;
        this.irClass = irClass;
        this.fields = new LinkedHashMap();
        this.receiverClass = INSTANCE.getClass(IrUtilsKt.getDefaultType(getIrClass()), true);
        Class<?> cls = value.getClass();
        boolean z = false;
        Object obj = null;
        if (Intrinsics.areEqual(cls, HashMap.class)) {
            Class<?>[] declaredClasses = cls.getDeclaredClasses();
            Intrinsics.checkNotNullExpressionValue(declaredClasses, "getDeclaredClasses(...)");
            Class<?>[] clsArr = declaredClasses;
            int length = clsArr.length;
            int i = 0;
            boolean z2 = false;
            Class<?> cls2 = null;
            while (i < length) {
                Class<?> cls3 = clsArr[i];
                String name2 = cls3.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                Class<?>[] clsArr2 = clsArr;
                if (StringsKt.contains$default((CharSequence) name2, (CharSequence) "$Node", false, 2, (Object) null)) {
                    if (z2) {
                        throw new IllegalArgumentException("Array contains more than one matching element.");
                    }
                    cls2 = cls3;
                    z2 = true;
                }
                i++;
                clsArr = clsArr2;
            }
            if (z2) {
                Class<?> cls4 = cls2;
                List<IrType> superTypes = getIrClass().getSuperTypes();
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = superTypes.iterator();
                while (it.hasNext()) {
                    IrClassSymbol classOrNull = IrTypesKt.getClassOrNull((IrType) it.next());
                    IrClass owner = classOrNull != null ? classOrNull.getOwner() : null;
                    if (owner != null) {
                        arrayList.add(owner);
                    }
                }
                for (Object obj2 : arrayList) {
                    if (Intrinsics.areEqual(((IrClass) obj2).getName(), StandardNames.FqNames.mutableMap.shortName())) {
                        if (z) {
                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                        }
                        obj = obj2;
                        z = true;
                    }
                }
                if (z) {
                    Map<Class<?>, IrClass> javaClassToIrClass$ir_interpreter = environment.getJavaClassToIrClass$ir_interpreter();
                    List<IrDeclaration> declarations = ((IrClass) obj).getDeclarations();
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj3 : declarations) {
                        if (obj3 instanceof IrClass) {
                            arrayList2.add(obj3);
                        }
                    }
                    Pair pair = TuplesKt.to(cls4, CollectionsKt.single((List) arrayList2));
                    javaClassToIrClass$ir_interpreter.put(pair.getFirst(), pair.getSecond());
                } else {
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
            } else {
                throw new NoSuchElementException("Array contains no element matching the predicate.");
            }
        } else if (Intrinsics.areEqual(cls, LinkedHashMap.class)) {
            Class<?>[] declaredClasses2 = cls.getDeclaredClasses();
            Intrinsics.checkNotNullExpressionValue(declaredClasses2, "getDeclaredClasses(...)");
            Class<?>[] clsArr3 = declaredClasses2;
            int length2 = clsArr3.length;
            int i2 = 0;
            boolean z3 = false;
            Class<?> cls5 = null;
            while (i2 < length2) {
                Class<?> cls6 = clsArr3[i2];
                String name3 = cls6.getName();
                Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                Class<?>[] clsArr4 = clsArr3;
                int i3 = length2;
                if (StringsKt.contains$default((CharSequence) name3, (CharSequence) "$Entry", false, 2, (Object) null)) {
                    if (z3) {
                        throw new IllegalArgumentException("Array contains more than one matching element.");
                    }
                    cls5 = cls6;
                    z3 = true;
                }
                i2++;
                length2 = i3;
                clsArr3 = clsArr4;
            }
            if (z3) {
                Class<?> cls7 = cls5;
                List<IrType> superTypes2 = getIrClass().getSuperTypes();
                ArrayList arrayList3 = new ArrayList();
                Iterator<T> it2 = superTypes2.iterator();
                while (it2.hasNext()) {
                    IrClassSymbol classOrNull2 = IrTypesKt.getClassOrNull((IrType) it2.next());
                    IrClass owner2 = classOrNull2 != null ? classOrNull2.getOwner() : null;
                    if (owner2 != null) {
                        arrayList3.add(owner2);
                    }
                }
                for (Object obj4 : arrayList3) {
                    if (Intrinsics.areEqual(((IrClass) obj4).getName(), StandardNames.FqNames.mutableMap.shortName())) {
                        if (z) {
                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                        }
                        obj = obj4;
                        z = true;
                    }
                }
                if (z) {
                    Map<Class<?>, IrClass> javaClassToIrClass$ir_interpreter2 = environment.getJavaClassToIrClass$ir_interpreter();
                    List<IrDeclaration> declarations2 = ((IrClass) obj).getDeclarations();
                    ArrayList arrayList4 = new ArrayList();
                    for (Object obj5 : declarations2) {
                        if (obj5 instanceof IrClass) {
                            arrayList4.add(obj5);
                        }
                    }
                    Pair pair2 = TuplesKt.to(cls7, CollectionsKt.single((List) arrayList4));
                    javaClassToIrClass$ir_interpreter2.put(pair2.getFirst(), pair2.getSecond());
                } else {
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
            } else {
                throw new NoSuchElementException("Array contains no element matching the predicate.");
            }
        } else if (Intrinsics.areEqual(cls.getCanonicalName(), "java.util.Collections.SingletonMap")) {
            List<IrDeclaration> declarations3 = getIrClass().getDeclarations();
            ArrayList arrayList5 = new ArrayList();
            for (Object obj6 : declarations3) {
                if (obj6 instanceof IrClass) {
                    arrayList5.add(obj6);
                }
            }
            IrClass irClass2 = (IrClass) CollectionsKt.single((List) arrayList5);
            Map<Class<?>, IrClass> javaClassToIrClass$ir_interpreter3 = environment.getJavaClassToIrClass$ir_interpreter();
            Pair pair3 = TuplesKt.to(AbstractMap.SimpleEntry.class, irClass2);
            javaClassToIrClass$ir_interpreter3.put(pair3.getFirst(), pair3.getSecond());
            Map<Class<?>, IrClass> javaClassToIrClass$ir_interpreter4 = environment.getJavaClassToIrClass$ir_interpreter();
            Pair pair4 = TuplesKt.to(AbstractMap.SimpleImmutableEntry.class, irClass2);
            javaClassToIrClass$ir_interpreter4.put(pair4.getFirst(), pair4.getSecond());
        }
        IrClass irClass3 = environment.getJavaClassToIrClass$ir_interpreter().get(this.value.getClass());
        if (irClass3 == null || IrUtilsKt.isSubclassOf(getIrClass(), irClass3)) {
            environment.getJavaClassToIrClass$ir_interpreter().put(this.value.getClass(), getIrClass());
        }
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public IrClass getIrClass() {
        return this.irClass;
    }

    public final Object getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public Map<IrSymbol, State> getFields() {
        return this.fields;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.Complex
    public Wrapper getSuperWrapperClass() {
        return this.superWrapperClass;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.Complex
    public void setSuperWrapperClass(Wrapper wrapper) {
        this.superWrapperClass = wrapper;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.Complex
    public Pair<IrSymbol, State> getOuterClass() {
        return this.outerClass;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.Complex
    public void setOuterClass(Pair<? extends IrSymbol, ? extends State> pair) {
        this.outerClass = pair;
    }

    public final MethodHandle getMethod(IrFunction irFunction) {
        Name name2;
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        IrProperty property = UtilsKt.getProperty(irFunction);
        Object obj = null;
        String strAsString = (property == null || (name2 = property.getName()) == null) ? null : name2.asString();
        String[] strArr = new String[2];
        strArr[0] = strAsString;
        strArr[1] = "get" + (strAsString != null ? CapitalizeDecapitalizeKt.capitalizeAsciiOnly(strAsString) : null);
        loop0: for (Object obj2 : CollectionsKt.listOfNotNull((Object[]) strArr)) {
            String str = (String) obj2;
            Method[] methods = this.receiverClass.getMethods();
            Intrinsics.checkNotNullExpressionValue(methods, "getMethods(...)");
            for (Method method : methods) {
                if (Intrinsics.areEqual(method.getName(), str)) {
                    obj = obj2;
                    break loop0;
                }
            }
        }
        String string = (String) obj;
        String javaOriginalName = getJavaOriginalName(irFunction);
        if (javaOriginalName != null) {
            string = javaOriginalName;
        } else if (string == null) {
            string = irFunction.getName().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        }
        return MethodHandles.lookup().findVirtual(this.receiverClass, string, INSTANCE.getMethodType(irFunction));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:21:0x0042 A[ORIG_RETURN, RETURN] */
    private final String getJavaOriginalName(IrFunction irFunction) {
        switch (UtilsKt.getFqName(UtilsKt.getLastOverridden(irFunction))) {
            case "kotlin.CharSequence.get":
                return "charAt";
            case "kotlin.collections.Map.<get-keys>":
                return "keySet";
            case "kotlin.collections.Map.<get-entries>":
                return "entrySet";
            case "kotlin.collections.MutableList.removeAt":
                return ProductAction.ACTION_REMOVE;
            default:
                return null;
        }
    }

    public String toString() {
        return this.value.toString();
    }

    /* JADX INFO: compiled from: Wrapper.kt */
    @Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u000eJ\u000e\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0014J\u000e\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0018\u001a\u00020\u0014J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 J\u001c\u0010!\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\"*\u00020#2\u0006\u0010$\u001a\u00020\u001eH\u0002J\u0017\u0010%\u001a\u0006\u0012\u0002\b\u00030\"*\u0004\u0018\u00010&H\u0002¢\u0006\u0002\u0010'J\u000e\u0010(\u001a\u0004\u0018\u00010\u0005*\u00020\u0014H\u0002J\f\u0010)\u001a\u00020**\u00020\u0014H\u0002J\u0012\u0010+\u001a\b\u0012\u0004\u0012\u00020-0,*\u00020\u0014H\u0002J\u0016\u0010.\u001a\u00020\u0005*\u00020\u00142\b\b\u0002\u0010/\u001a\u00020\u0005H\u0002J\f\u00100\u001a\u00020\u001e*\u00020\u0014H\u0002J\f\u00101\u001a\u00020\u001e*\u00020\u0014H\u0002J\u0014\u00102\u001a\u00020\u001e*\u00020\u00142\u0006\u00103\u001a\u00020&H\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/Wrapper$Companion;", "", "()V", "companionObjectValue", "", "", "intrinsicClasses", "", "intrinsicFunctionToHandler", "intrinsicJavaClasses", "ranges", "getCompanionObject", "Lorg/jetbrains/kotlin/ir/interpreter/state/Wrapper;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "getConstructorMethod", "Ljava/lang/invoke/MethodHandle;", "irConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getEnumEntry", "irEnumClass", "getReflectionMethod", "irFunction", "getStaticGetter", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getStaticMethod", "mustBeHandledWithWrapper", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "getClass", "Ljava/lang/Class;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "asObject", "getCorrespondingFunction", "", "(Ljava/lang/Integer;)Ljava/lang/Class;", "getJvmClassName", "getMethodType", "Ljava/lang/invoke/MethodType;", "getOriginalOverriddenSymbols", "", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getSignature", "fqName", "isExtensionReceiverPrimitive", "isReturnTypePrimitiveAsObject", "isValueParameterPrimitiveAsObject", "index", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        static /* synthetic */ String getSignature$default(Companion companion, IrFunction irFunction, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = UtilsKt.getFqName(irFunction);
            }
            return companion.getSignature(irFunction, str);
        }

        private final String getSignature(IrFunction irFunction, String str) {
            String str2;
            IrType type;
            String onlyName;
            IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
            if (dispatchReceiverParameter == null) {
                dispatchReceiverParameter = irFunction.getExtensionReceiverParameter();
            }
            if (dispatchReceiverParameter == null || (type = dispatchReceiverParameter.getType()) == null || (onlyName = UtilsKt.getOnlyName(type)) == null || (str2 = onlyName + '.') == null) {
                str2 = "";
            }
            return CollectionsKt.joinToString$default(irFunction.getValueParameters(), null, str2 + str + '(', Constant.AFTER_QUTO, 0, null, new Function1<IrValueParameter, CharSequence>() { // from class: org.jetbrains.kotlin.ir.interpreter.state.Wrapper$Companion$getSignature$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(IrValueParameter it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return UtilsKt.getOnlyName(it.getType());
                }
            }, 25, null);
        }

        private final String getJvmClassName(IrFunction irFunction) {
            return (String) Wrapper.intrinsicFunctionToHandler.get(getSignature$default(this, irFunction, null, 1, null));
        }

        /* JADX WARN: Code duplicated, block: B:13:0x003b  */
        public final boolean mustBeHandledWithWrapper(IrDeclarationWithName declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            String fqName = UtilsKt.getFqName(declaration);
            if (declaration instanceof IrFunction) {
                return Wrapper.intrinsicFunctionToHandler.containsKey(getSignature((IrFunction) declaration, fqName));
            }
            if (Wrapper.ranges.contains(fqName)) {
                IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor((IrClass) declaration);
                if ((primaryConstructor != null ? primaryConstructor.getBody() : null) != null) {
                    if (!Wrapper.intrinsicClasses.contains(fqName)) {
                        return false;
                    }
                }
            } else if (!Wrapper.intrinsicClasses.contains(fqName) && !Wrapper.intrinsicJavaClasses.contains(fqName)) {
                return false;
            }
            return true;
        }

        public final MethodHandle getReflectionMethod(IrFunction irFunction) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
            String strAsString;
            Intrinsics.checkNotNullParameter(irFunction, "irFunction");
            IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            Class<? extends Object> cls = getClass(dispatchReceiverParameter.getType(), true);
            MethodType methodType = getMethodType(irFunction);
            if (irFunction instanceof IrSimpleFunction) {
                IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) irFunction).getCorrespondingPropertySymbol();
                IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
                if (Intrinsics.areEqual(owner != null ? owner.getGetter() : null, irFunction)) {
                    StringBuilder sb = new StringBuilder("get");
                    String strAsString2 = owner.getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                    strAsString = sb.append(CapitalizeDecapitalizeKt.capitalizeAsciiOnly(strAsString2)).toString();
                } else {
                    if (Intrinsics.areEqual(owner != null ? owner.getSetter() : null, irFunction)) {
                        StringBuilder sb2 = new StringBuilder(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX);
                        String strAsString3 = owner.getName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
                        strAsString = sb2.append(CapitalizeDecapitalizeKt.capitalizeAsciiOnly(strAsString3)).toString();
                    } else {
                        strAsString = irFunction.getName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    }
                }
            } else {
                strAsString = irFunction.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            }
            MethodHandle methodHandleFindVirtual = MethodHandles.lookup().findVirtual(cls, strAsString, methodType);
            Intrinsics.checkNotNullExpressionValue(methodHandleFindVirtual, "findVirtual(...)");
            return methodHandleFindVirtual;
        }

        public final Wrapper getCompanionObject(IrClass irClass, IrInterpreterEnvironment environment) {
            Intrinsics.checkNotNullParameter(irClass, "irClass");
            Intrinsics.checkNotNullParameter(environment, "environment");
            String strInternalName = UtilsKt.internalName(irClass);
            Object obj = Wrapper.companionObjectValue.get(strInternalName);
            if (obj == null) {
                throw new InternalError("Companion object " + strInternalName + " cannot be interpreted");
            }
            return new Wrapper(obj, irClass, environment);
        }

        public final MethodHandle getConstructorMethod(IrFunction irConstructor) throws ClassNotFoundException {
            Intrinsics.checkNotNullParameter(irConstructor, "irConstructor");
            String strInternalName = UtilsKt.internalName(IrUtilsKt.getParentAsClass(irConstructor));
            if (Intrinsics.areEqual(strInternalName, "kotlin.Char") || Intrinsics.areEqual(strInternalName, "kotlin.Long")) {
                return null;
            }
            return MethodHandles.lookup().findConstructor(getClass(irConstructor.getReturnType(), true), getMethodType(irConstructor));
        }

        public final MethodHandle getStaticMethod(IrFunction irFunction) throws ClassNotFoundException {
            Intrinsics.checkNotNullParameter(irFunction, "irFunction");
            String jvmClassName = getJvmClassName(irFunction);
            if (jvmClassName == null || jvmClassName.length() == 0) {
                return null;
            }
            return MethodHandles.lookup().findStatic(Class.forName(jvmClassName), irFunction.getName().asString(), getMethodType(irFunction));
        }

        public final MethodHandle getStaticGetter(IrField field) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
            Intrinsics.checkNotNullParameter(field, "field");
            Class<? extends Object> cls = getClass(IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(field)), true);
            IrType type = field.getType();
            MethodHandle methodHandleFindStaticGetter = MethodHandles.lookup().findStaticGetter(cls, field.getName().asString(), Wrapper.INSTANCE.getClass(type, IrTypeUtilsKt.isNullable(type)));
            Intrinsics.checkNotNullExpressionValue(methodHandleFindStaticGetter, "findStaticGetter(...)");
            return methodHandleFindStaticGetter;
        }

        public final MethodHandle getEnumEntry(IrClass irEnumClass) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
            Intrinsics.checkNotNullParameter(irEnumClass, "irEnumClass");
            Class<?> cls = Class.forName(UtilsKt.internalName(irEnumClass));
            MethodHandle methodHandleFindStatic = MethodHandles.lookup().findStatic(cls, StandardNames.ENUM_VALUE_OF.getIdentifier(), MethodType.methodType(cls, (Class<?>) String.class));
            Intrinsics.checkNotNullExpressionValue(methodHandleFindStatic, "findStatic(...)");
            return methodHandleFindStatic;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final MethodType getMethodType(IrFunction irFunction) throws ClassNotFoundException {
            IrType type;
            List<IrValueParameter> valueParameters = irFunction.getValueParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
            for (IrValueParameter irValueParameter : valueParameters) {
                arrayList.add(Wrapper.INSTANCE.getClass(irValueParameter.getType(), Wrapper.INSTANCE.isValueParameterPrimitiveAsObject(irFunction, irValueParameter.getIndex())));
            }
            ArrayList arrayList2 = arrayList;
            if (irFunction instanceof IrSimpleFunction) {
                Class<? extends Object> cls = getClass(irFunction.getReturnType(), isReturnTypePrimitiveAsObject(irFunction));
                IrValueParameter extensionReceiverParameter = irFunction.getExtensionReceiverParameter();
                MethodType methodType = MethodType.methodType(cls, (List<Class<?>>) CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull((extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null) ? null : getClass(type, isExtensionReceiverPrimitive(irFunction))), (Iterable) arrayList2));
                Intrinsics.checkNotNull(methodType);
                return methodType;
            }
            MethodType methodType2 = MethodType.methodType((Class<?>) Void.TYPE, arrayList2);
            Intrinsics.checkNotNull(methodType2);
            return methodType2;
        }

        private final Class<?> getCorrespondingFunction(Integer num) throws ClassNotFoundException {
            if (num == null || num.intValue() >= 23) {
                Class<?> cls = Class.forName("kotlin.jvm.functions.FunctionN");
                Intrinsics.checkNotNullExpressionValue(cls, "forName(...)");
                return cls;
            }
            Class<?> cls2 = Class.forName("kotlin.jvm.functions.Function" + num);
            Intrinsics.checkNotNullExpressionValue(cls2, "forName(...)");
            return cls2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public final Class<? extends Object> getClass(IrType irType, boolean z) throws ClassNotFoundException {
            String strRemovePrefix;
            IrClassSymbol classOrNull;
            IrClass owner;
            IrClassSymbol classOrNull2 = IrTypesKt.getClassOrNull(irType);
            Integer intOrNull = null;
            fqName = null;
            fqName = null;
            String fqName = null;
            intOrNull = null;
            IrClass owner2 = classOrNull2 != null ? classOrNull2.getOwner() : null;
            String fqName2 = owner2 != null ? UtilsKt.getFqName(owner2) : null;
            IrType irTypeMakeNotNull = IrTypesKt.makeNotNull(irType);
            if (IrTypePredicatesKt.isPrimitiveType$default(irTypeMakeNotNull, false, 1, null) || IrTypePredicatesKt.isString(irTypeMakeNotNull)) {
                Class primitiveClass = UtilsKt.getPrimitiveClass(irTypeMakeNotNull, z);
                Intrinsics.checkNotNull(primitiveClass);
                return primitiveClass;
            }
            if (IrTypePredicatesKt.isArray(irTypeMakeNotNull)) {
                Intrinsics.checkNotNull(irType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                IrType typeOrNull = IrTypesKt.getTypeOrNull((IrTypeArgument) CollectionsKt.single((List) ((IrSimpleType) irType).getArguments()));
                if (typeOrNull != null && (classOrNull = IrTypesKt.getClassOrNull(typeOrNull)) != null && (owner = classOrNull.getOwner()) != null) {
                    fqName = UtilsKt.getFqName(owner);
                }
                Class<? extends Object> cls = (fqName == null || Intrinsics.areEqual(fqName, "kotlin.Any")) ? Object[].class : Class.forName("[L" + fqName + ';');
                Intrinsics.checkNotNull(cls);
                return cls;
            }
            if (IrTypePredicatesKt.isNothing(irTypeMakeNotNull)) {
                return Void.class;
            }
            if (IrTypePredicatesKt.isAny(irTypeMakeNotNull)) {
                return Object.class;
            }
            if (IrTypePredicatesKt.isUnit(irTypeMakeNotNull)) {
                if (z) {
                    return Void.class;
                }
                Class<? extends Object> cls2 = Void.TYPE;
                Intrinsics.checkNotNull(cls2);
                return cls2;
            }
            if (IrTypePredicatesKt.isNumber(irTypeMakeNotNull)) {
                return Number.class;
            }
            if (IrTypePredicatesKt.isCharSequence(irTypeMakeNotNull)) {
                return CharSequence.class;
            }
            if (IrTypePredicatesKt.isComparable(irTypeMakeNotNull)) {
                return Comparable.class;
            }
            if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isThrowable(irTypeMakeNotNull)) {
                return Throwable.class;
            }
            if (IrTypePredicatesKt.isIterable(irTypeMakeNotNull)) {
                return Iterable.class;
            }
            if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isKFunction(irTypeMakeNotNull) || org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isKSuspendFunction(irTypeMakeNotNull)) {
                Class cls3 = Class.forName("kotlin.reflect.KFunction");
                Intrinsics.checkNotNullExpressionValue(cls3, "forName(...)");
                return cls3;
            }
            if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isFunction(irTypeMakeNotNull)) {
                if (fqName2 != null && (strRemovePrefix = StringsKt.removePrefix(fqName2, (CharSequence) "kotlin.Function")) != null) {
                    intOrNull = StringsKt.toIntOrNull(strRemovePrefix);
                }
                return getCorrespondingFunction(intOrNull);
            }
            if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isSuspendFunction(irTypeMakeNotNull)) {
                throw new IllegalStateException(("Interpretation of " + fqName2 + " is not supported").toString());
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.Enum")) {
                return Enum.class;
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.collections.Collection") || Intrinsics.areEqual(fqName2, "kotlin.collections.MutableCollection")) {
                return Collection.class;
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.collections.List") || Intrinsics.areEqual(fqName2, "kotlin.collections.MutableList")) {
                return List.class;
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.collections.Set") || Intrinsics.areEqual(fqName2, "kotlin.collections.MutableSet")) {
                return Set.class;
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.collections.Map") || Intrinsics.areEqual(fqName2, "kotlin.collections.MutableMap")) {
                return Map.class;
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.collections.ListIterator") || Intrinsics.areEqual(fqName2, "kotlin.collections.MutableListIterator")) {
                return ListIterator.class;
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.collections.Iterator") || Intrinsics.areEqual(fqName2, "kotlin.collections.MutableIterator")) {
                return Iterator.class;
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.collections.Map.Entry") || Intrinsics.areEqual(fqName2, "kotlin.collections.MutableMap.MutableEntry")) {
                return Map.Entry.class;
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.collections.ArrayList")) {
                return ArrayList.class;
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.collections.HashMap")) {
                return HashMap.class;
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.collections.HashSet")) {
                return HashSet.class;
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.collections.LinkedHashMap")) {
                return LinkedHashMap.class;
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.collections.LinkedHashSet")) {
                return LinkedHashSet.class;
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.text.StringBuilder")) {
                return StringBuilder.class;
            }
            if (Intrinsics.areEqual(fqName2, "kotlin.text.Appendable")) {
                return Appendable.class;
            }
            if (fqName2 == null) {
                return Object.class;
            }
            Class cls4 = Class.forName(UtilsKt.internalName(owner2));
            Intrinsics.checkNotNullExpressionValue(cls4, "forName(...)");
            return cls4;
        }

        private final List<IrFunctionSymbol> getOriginalOverriddenSymbols(IrFunction irFunction) {
            ArrayList arrayList = new ArrayList();
            if (irFunction instanceof IrSimpleFunction) {
                ListIterator listIterator = CollectionsKt.toMutableList((Collection) ((IrSimpleFunction) irFunction).getOverriddenSymbols()).listIterator();
                while (listIterator.hasNext()) {
                    IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) listIterator.next();
                    if (irSimpleFunctionSymbol.getOwner().getOverriddenSymbols().isEmpty()) {
                        arrayList.add(irSimpleFunctionSymbol);
                        listIterator.remove();
                    } else {
                        Iterator<T> it = irSimpleFunctionSymbol.getOwner().getOverriddenSymbols().iterator();
                        while (it.hasNext()) {
                            listIterator.add((IrSimpleFunctionSymbol) it.next());
                        }
                    }
                }
            }
            if (arrayList.isEmpty()) {
                arrayList.add(irFunction.getSymbol());
            }
            return arrayList;
        }

        private final boolean isExtensionReceiverPrimitive(IrFunction irFunction) {
            IrType type;
            IrValueParameter extensionReceiverParameter = irFunction.getExtensionReceiverParameter();
            return (extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null || IrTypePredicatesKt.isPrimitiveType$default(type, false, 1, null)) ? false : true;
        }

        private final boolean isReturnTypePrimitiveAsObject(IrFunction irFunction) {
            for (IrFunctionSymbol irFunctionSymbol : getOriginalOverriddenSymbols(irFunction)) {
                if (!org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isTypeParameter(irFunctionSymbol.getOwner().getReturnType()) && !IrTypeUtilsKt.isNullable(irFunctionSymbol.getOwner().getReturnType())) {
                    return false;
                }
            }
            return true;
        }

        private final boolean isValueParameterPrimitiveAsObject(IrFunction irFunction, int i) {
            for (IrFunctionSymbol irFunctionSymbol : getOriginalOverriddenSymbols(irFunction)) {
                if (!org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isTypeParameter(irFunctionSymbol.getOwner().getValueParameters().get(i).getType()) && !IrTypeUtilsKt.isNullable(irFunctionSymbol.getOwner().getValueParameters().get(i).getType())) {
                    return false;
                }
            }
            return true;
        }
    }
}
