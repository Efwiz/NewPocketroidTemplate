package com.android.tools.r8.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class V6 extends C2757p8 {
    static final /* synthetic */ boolean s = true;
    private HashSet j = new HashSet();
    private ArrayList k = new ArrayList();
    private HashSet l = new HashSet();
    private HashSet m = new HashSet();
    private ArrayList n = null;
    private MR o = null;
    private boolean p = false;
    private StringBuilder q = null;
    private final AbstractC2178hm r = AbstractC2178hm.e().a("Z", "booleanType").a("B", "byteType").a("C", "charType").a("D", "doubleType").a("F", "floatType").a("I", "intType").a("J", "longType").a(ExifInterface.LATITUDE_SOUTH, "shortType").a(ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "voidType").a("[Z", "booleanArrayType").a("[B", "byteArrayType").a("[C", "charArrayType").a("[D", "doubleArrayType").a("[F", "floatArrayType").a("[I", "intArrayType").a("[J", "longArrayType").a("[S", "shortArrayType").a(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "objectType").a("Ljava/lang/Class;", "classType").a("Ljava/lang/Throwable;", "throwableType").a("Ljava/lang/String;", "stringType").a("Ljava/lang/Character;", "boxedCharType").a("Ljava/lang/CharSequence;", "charSequenceType").a("Ljava/lang/StringBuilder;", "stringBuilderType").a("Ljava/lang/AutoCloseable;", "autoCloseableType").a();

    private String b(EnumC1812d20 enumC1812d20) {
        return d("ValueType") + "." + enumC1812d20.name();
    }

    private String c(String str) {
        return a(str, (List) AbstractC1639am.a("cf", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean e(String str) {
        return str.contains(".");
    }

    private static String f(String str) {
        return "\"" + str + "\"";
    }

    private String i() {
        return a("FrameType", (List) AbstractC1639am.a("cf", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, TypedValues.AttributesType.S_FRAME));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(C2051g8 c2051g8) {
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void d() {
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void g() {
        a("CfReturnVoid", new String[0]);
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void h() {
        a("CfArrayLength", new String[0]);
    }

    public Set<String> j() {
        return this.j;
    }

    public List<String> k() {
        ArrayList arrayList = new ArrayList(this.j);
        arrayList.sort(new Comparator() { // from class: com.android.tools.r8.internal.V6$$ExternalSyntheticLambda3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((String) obj).compareTo((String) obj2);
            }
        });
        return arrayList;
    }

    public List<String> l() {
        return this.k;
    }

    public Set<String> m() {
        return this.m;
    }

    private String b(EnumC2523mE enumC2523mE) {
        return d("MemberType") + "." + enumC2523mE.name();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String c(M7 m7) {
        return CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME + this.o.a(m7);
    }

    private String d(String str) {
        return a(str, (List) AbstractC1639am.a(KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY));
    }

    public void a(String str, C0753u c0753u) {
        if (!this.l.add(str)) {
            throw new IllegalStateException("Invalid attempt to visit the same method twice: " + str);
        }
        this.o = new MR();
        this.n = new ArrayList();
        this.p = false;
        this.q = new StringBuilder().append("public static ").append(a("CfCode", Collections.singletonList("graph"))).append(" ").append(str).append("(").append(a("DexItemFactory", Collections.singletonList("graph"))).append(" factory, ").append(a("DexMethod", Collections.singletonList("graph"))).append(" method) {");
        for (B7 b7 : c0753u.I0()) {
            if (b7 instanceof M7) {
                M7 m7 = (M7) b7;
                this.o.a(this.n.size(), m7);
                this.n.add(m7);
                this.q.append("CfLabel ").append(c(m7)).append(" = new " + c("CfLabel") + "();");
            }
        }
        this.q.append("return new " + a("CfCode", Collections.singletonList("graph")) + "(").append("method.holder,").append(c0753u.L0()).append(",").append(c0753u.K0()).append(",").append(a("ImmutableList", AbstractC1639am.a("com", "google", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR, "collect"))).append(".of(");
        Iterator<B7> it = c0753u.I0().iterator();
        while (it.hasNext()) {
            it.next().a(this);
        }
        this.q.append("),").append(a("ImmutableList", AbstractC1639am.a("com", "google", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR, "collect"))).append(".of(");
        this.p = false;
        for (W8 w8 : c0753u.Q0()) {
            a("CfTryCatch", c(w8.a), c(w8.b), a("ImmutableList", AbstractC1639am.a("com", "google", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR, "collect")) + ".of(" + ((String) w8.c.stream().map(new Function() { // from class: com.android.tools.r8.internal.V6$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.b((C0767v2) obj);
                }
            }).collect(Collectors.joining(", "))) + Constant.AFTER_QUTO, a("ImmutableList", AbstractC1639am.a("com", "google", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR, "collect")) + ".of(" + ((String) w8.d.stream().map(new Function() { // from class: com.android.tools.r8.internal.V6$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.c((M7) obj);
                }
            }).collect(Collectors.joining(", "))) + Constant.AFTER_QUTO);
        }
        this.q.append("),").append(a("ImmutableList", AbstractC1639am.a("com", "google", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR, "collect"))).append(".of());").append("}");
        this.k.add(this.q.toString());
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void e() {
        a("CfThrow", new String[0]);
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void f() {
        a("CfConstNull", new String[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String b(C0767v2 c0767v2) {
        String strB1 = c0767v2.b1();
        String str = (String) this.r.get(strB1);
        if (str != null) {
            return "factory." + str;
        }
        this.m.add(strB1);
        return "factory.createType(" + f(strB1) + Constant.AFTER_QUTO;
    }

    private String c(C2284j7 c2284j7) {
        String strA = com.android.tools.r8.utils.Y2.a(",", c2284j7.Z(), new Function() { // from class: com.android.tools.r8.internal.V6$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((InterfaceC3054tM) obj);
            }
        });
        return "new " + a("ArrayDeque", AbstractC1639am.a(SuffixConstants.EXTENSION_java, "util")) + "<>(" + a("Arrays", AbstractC1639am.a(SuffixConstants.EXTENSION_java, "util")) + ".asList(" + strA + "))";
    }

    private String b(C0684j2 c0684j2) {
        String strB = b(c0684j2.e);
        C0737r2 c0737r2 = c0684j2.h;
        StringBuilder sbAppend = new StringBuilder("factory.createProto(").append(b(c0737r2.e));
        for (C0767v2 c0767v2 : c0737r2.f.a) {
            sbAppend.append(", ").append(b(c0767v2));
        }
        return "factory.createMethod(" + strB + ", " + sbAppend.append(Constant.AFTER_QUTO).toString() + ", " + a(c0684j2.f) + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void b(M7 m7) {
        if (this.p) {
            this.q.append(",");
        }
        this.p = true;
        this.q.append(c(m7));
    }

    private String b(C2284j7 c2284j7) {
        String strA = com.android.tools.r8.utils.Y2.a(",", c2284j7.W().keySet());
        String strA2 = com.android.tools.r8.utils.Y2.a(",", c2284j7.W().values(), new Function() { // from class: com.android.tools.r8.internal.V6$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((InterfaceC1348Si) obj);
            }
        });
        return "new " + a("Int2ObjectAVLTreeMap", AbstractC1639am.a("it", "unimi", "dsi", "fastutil", "ints")) + "<>(new int[] {" + strA + "},new " + i() + "[] { " + strA2 + " })";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String b(InterfaceC1348Si interfaceC1348Si) {
        if (interfaceC1348Si.q()) {
            return i() + ".oneWord()";
        }
        if (interfaceC1348Si.k()) {
            return i() + ".twoWord()";
        }
        if (interfaceC1348Si.A()) {
            return i() + ".uninitializedThis()";
        }
        if (interfaceC1348Si.y()) {
            return i() + ".uninitializedNew(new " + c("CfLabel") + "())";
        }
        if (interfaceC1348Si.isPrimitive()) {
            if (interfaceC1348Si.n()) {
                return i() + "." + interfaceC1348Si.p().U().getTypeName() + "HighType()";
            }
            return i() + "." + interfaceC1348Si.asPrimitive().getTypeName() + "Type()";
        }
        boolean z = s;
        if (!z && !interfaceC1348Si.M()) {
            throw new AssertionError();
        }
        if (!z && interfaceC1348Si.G()) {
            throw new AssertionError("Unexpected InitializedNonNullReferenceTypeWithInterfaces in CfFrame");
        }
        if (interfaceC1348Si.l()) {
            return i() + ".nullType()";
        }
        if (!z && !interfaceC1348Si.N()) {
            throw new AssertionError();
        }
        return i() + ".initializedNonNullReference(" + b(interfaceC1348Si.j().T()) + Constant.AFTER_QUTO;
    }

    private static String a(long j) {
        return ((j < -2147483648L || 2147483647L < j) ? new StringBuilder().append(j).append("L") : new StringBuilder().append(j)).toString();
    }

    private String a(String str, AbstractC1639am abstractC1639am) {
        boolean z = s;
        if (!z && str.contains(".")) {
            throw new AssertionError();
        }
        if (!z && !abstractC1639am.stream().noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.V6$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return V6.e((String) obj);
            }
        })) {
            throw new AssertionError();
        }
        this.j.add(String.join(".", abstractC1639am) + "." + str);
        return str;
    }

    private String a(String str, List list) {
        return a(str, AbstractC1639am.g().a((Iterable) AbstractC1639am.a("com", "android", "tools", "r8")).a((Iterable) list).a());
    }

    private String a(EnumC2527mI enumC2527mI) {
        return d("NumericType") + "." + enumC2527mI.name();
    }

    private String a(EnumC1076Il enumC1076Il) {
        return d("IfType") + "." + enumC1076Il.name();
    }

    private String a(EnumC3423yF enumC3423yF) {
        return d("MonitorType") + "." + enumC3423yF.name();
    }

    private static String a(C0759u2 c0759u2) {
        return "factory.createString(" + f(c0759u2.toString()) + Constant.AFTER_QUTO;
    }

    private String a(com.android.tools.r8.graph.Y0 y0) {
        return "factory.createField(" + b(y0.e) + ", " + b(y0.h) + ", " + a(y0.f) + Constant.AFTER_QUTO;
    }

    private void a(String str, String... strArr) {
        if (this.p) {
            this.q.append(",");
        }
        this.p = true;
        this.q.append("new ").append(c(str));
        com.android.tools.r8.utils.Y2.a(this.q, Arrays.asList(strArr), ", ", com.android.tools.r8.utils.Y2.a.PARENS);
    }

    private void a(int i, EnumC1812d20 enumC1812d20, String str) {
        a(str, b(enumC1812d20), new StringBuilder().append(i).toString());
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(D8 d8) {
        a("CfStackInstruction", c("CfStackInstruction") + ".Opcode." + d8.T().name());
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(C1818d7 c1818d7) {
        a("CfConstNumber", a(c1818d7.X()), b(c1818d7.Y()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(X6 x6) {
        a("CfConstClass", b(x6.getType()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(Y6 y6) {
        throw new Z00(y6.getClass().getSimpleName());
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(C3109u8 c3109u8) {
        a("CfReturn", b(c3109u8.V()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(R7 r7) {
        a("CfMonitor", a(r7.T()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(C3481z6 c3481z6) {
        a("CfArithmeticBinop", c("CfArithmeticBinop") + ".Opcode." + c3481z6.U().name(), a(c3481z6.V()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(U6 u6) {
        a("CfCmp", d("Cmp") + ".Bias." + u6.U().name(), a(u6.V()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(Q7 q7) {
        a("CfLogicalBinop", c("CfLogicalBinop") + ".Opcode." + q7.U().name(), a(q7.V()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(U7 u7) {
        a("CfNeg", a(u7.U()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(C1591a8 c1591a8) {
        a("CfNumberConversion", a(c1591a8.U()), a(c1591a8.V()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(C1891e7 c1891e7) {
        a("CfConstString", a(c1891e7.T()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(C2134h7 c2134h7) {
        throw new Z00(c2134h7.getClass().getSimpleName());
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(B6 b6) {
        a("CfArrayLoad", b(b6.T()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(D6 d6) {
        a("CfArrayStore", b(d6.T()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(I7 i7) {
        a("CfInvoke", Integer.toString(i7.U()), b(i7.T()), Boolean.toString(i7.V()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(J7 j7) {
        throw new Z00(j7.getClass().getSimpleName());
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(C2284j7 c2284j7) {
        if (c2284j7.W().isEmpty()) {
            if (c2284j7.Z().isEmpty()) {
                a(c("CfFrame"), new String[0]);
                return;
            } else {
                a(c("CfFrame"), c(c2284j7));
                return;
            }
        }
        if (c2284j7.Z().isEmpty()) {
            a(c("CfFrame"), b(c2284j7));
        } else {
            a(c("CfFrame"), b(c2284j7), c(c2284j7));
        }
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(A7 a7) {
        a("CfInstanceOf", b(a7.getType()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(M6 m6) {
        a("CfCheckCast", b(m6.getType()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(AbstractC2206i7 abstractC2206i7) {
        switch (abstractC2206i7.U()) {
            case 178:
                a("CfStaticFieldRead", a(abstractC2206i7.getField()));
                return;
            case 179:
                a("CfStaticFieldWrite", a(abstractC2206i7.getField()));
                return;
            case 180:
                a("CfInstanceFieldRead", a(abstractC2206i7.getField()));
                return;
            case 181:
                a("CfInstanceFieldWrite", a(abstractC2206i7.getField()));
                return;
            default:
                throw new C3405y10();
        }
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(V7 v7) {
        a("CfNew", b(v7.getType()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(W7 w7) {
        a("CfNewArray", b(w7.getType()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(S7 s7) {
        throw new Z00(s7.getClass().getSimpleName());
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(C2899r7 c2899r7) {
        a("CfGoto", c(c2899r7.A()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(C3039t7 c3039t7) {
        a("CfIf", a(c3039t7.U()), b(c3039t7.V()), c(c3039t7.A()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(C3185v7 c3185v7) {
        a("CfIfCmp", a(c3185v7.U()), b(c3185v7.V()), c(c3185v7.A()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(U8 u8) {
        throw new Z00(u8.getClass().getSimpleName());
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(O7 o7) {
        a(o7.U(), o7.V(), "CfLoad");
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(R8 r8) {
        a(r8.T(), r8.V(), "CfStore");
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(C3260w7 c3260w7) {
        a("CfIinc", Integer.toString(c3260w7.U()), Integer.toString(c3260w7.T()));
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(Z6 z6) {
        throw new Z00(z6.getClass().getSimpleName());
    }

    @Override // com.android.tools.r8.internal.C2757p8
    public final void a(C1590a7 c1590a7) {
        throw new Z00(c1590a7.getClass().getSimpleName());
    }
}
