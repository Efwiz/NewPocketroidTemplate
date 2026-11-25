package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.KotlinClassMetadata;
import com.android.tools.r8.utils.InterfaceC3880d;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.apkeditor.Options;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import okhttp3.HttpUrl;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1821dA {
    static final /* synthetic */ boolean a = true;

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final StringBuilder sb, C3247vy c3247vy, String str) {
        AbstractC0936Ds.c(c3247vy, "<this>");
        a(str, "KmProperty", sb, ((C1025Gu) c3247vy.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k.b)).c(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda64
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C3539zy) obj2, (String) obj);
            }
        });
    }

    public static void b(final String str, String str2, StringBuilder sb, final Consumer consumer) {
        InterfaceC3880d interfaceC3880d = new InterfaceC3880d() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda50
            @Override // com.android.tools.r8.utils.InterfaceC3880d
            public final void a() {
                C1821dA.b(consumer, str);
            }
        };
        String str3 = "{" + com.android.tools.r8.utils.Y2.c;
        sb.append(str2);
        sb.append(str3);
        interfaceC3880d.a();
        sb.append(str + "}");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(final StringBuilder sb, C1119Jy c1119Jy, String str) {
        AbstractC0936Ds.c(c1119Jy, "<this>");
        a(str, "KmAnnotion", sb, ((C1800cv) c1119Jy.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n.b)).b(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda88
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a((C3321wx) obj2, sb, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(final StringBuilder sb, InterfaceC1877dy interfaceC1877dy, String str) {
        a(str, "KmTypeAlias", sb, (Collection) interfaceC1877dy.b().stream().sorted(Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda52
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0974Ey) obj).e();
            }
        })).collect(Collectors.toList()), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda53
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C0974Ey) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void e(final StringBuilder sb, C1283Px c1283Px, String str) {
        a(str, "KmConstructor", sb, (Collection) c1283Px.g().stream().sorted(Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda23
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1821dA.a((C1493Wx) obj);
            }
        })).collect(Collectors.toList()), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda24
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C1493Wx) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void f(final StringBuilder sb, C1283Px c1283Px, String str) {
        a(str, "KmType", sb, c1283Px.h(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda49
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C0942Dy) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void g(final StringBuilder sb, final C2815py c2815py, String str) {
        a(str, OverlayablePolicy.NAME_flags, sb, new StringBuilder().append(c2815py.f()).toString());
        a(str, "name", sb, c2815py.g());
        a(str, "receiverParameterType", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(sb, c2815py, (String) obj);
            }
        });
        a(str, "returnType", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.c(sb, c2815py, (String) obj);
            }
        });
        a(str, "typeParameters", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.d(sb, c2815py, (String) obj);
            }
        });
        a(str, "valueParameters", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.e(sb, c2815py, (String) obj);
            }
        });
        a(sb, c2815py.l(), str);
        final C1653ay c1653ayE = c2815py.e();
        if (c1653ayE == null) {
            a(str, "contract", sb, "null");
        } else {
            a(str, "contract", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1821dA.a(sb, c1653ayE, (String) obj);
                }
            });
        }
        a(str, "contextReceiverTypes", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.f(sb, c2815py, (String) obj);
            }
        });
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVarB = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.b(c2815py);
        a(str, "signature", sb, jVarB != null ? jVarB.a() : "null");
        a(str, "lambdaClassOriginName", sb, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c2815py));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void f(final StringBuilder sb, C2815py c2815py, String str) {
        a(str, "KmType", sb, c2815py.d(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda41
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C0942Dy) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(final StringBuilder sb, C1283Px c1283Px, String str) {
        a(str, "KmProperty", sb, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.c(c1283Px), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda35
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C3539zy) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void f(final StringBuilder sb, C3539zy c3539zy, String str) {
        a(str, "KmType", sb, c3539zy.c(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda72
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C0942Dy) obj2, (String) obj);
            }
        });
    }

    public static void a(C0633d0 c0633d0, PrintStream printStream, C2037fz c2037fz) {
        if (!a && c0633d0.b.a != c2037fz.a.t3) {
            throw new AssertionError();
        }
        try {
            printStream.println(a("#  ", C2888qz.a(c2037fz, c0633d0.b)));
        } catch (Throwable unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Consumer consumer, String str) {
        consumer.accept(str + "  ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(final StringBuilder sb, InterfaceC1877dy interfaceC1877dy, String str) {
        a(str, "KmProperty", sb, (Collection) interfaceC1877dy.a().stream().sorted(Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda60
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1821dA.a((C3539zy) obj);
            }
        })).collect(Collectors.toList()), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda61
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C3539zy) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(final StringBuilder sb, InterfaceC1877dy interfaceC1877dy, String str) {
        a(str, "KmFunction", sb, (Collection) interfaceC1877dy.c().stream().sorted(Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda19
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1821dA.a((C2815py) obj);
            }
        })).collect(Collectors.toList()), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda20
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C2815py) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(StringBuilder sb, C2815py c2815py, String str) {
        a(sb, c2815py.j(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void f(final StringBuilder sb, final C0974Ey c0974Ey, String str) {
        a(str, "annotations", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(sb, c0974Ey, (String) obj);
            }
        });
        a(str, "expandedType", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.c(sb, c0974Ey, (String) obj);
            }
        });
        a(str, OverlayablePolicy.NAME_flags, sb, new StringBuilder().append(c0974Ey.d()).toString());
        a(str, "name", sb, c0974Ey.e());
        a(str, "typeParameters", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.d(sb, c0974Ey, (String) obj);
            }
        });
        a(str, "underlyingType", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.e(sb, c0974Ey, (String) obj);
            }
        });
        a(sb, c0974Ey.g(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(StringBuilder sb, C3539zy c3539zy, String str) {
        a(sb, (List) c3539zy.k(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void e(StringBuilder sb, C2815py c2815py, String str) {
        b(sb, c2815py.k(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(final StringBuilder sb, final C0942Dy c0942Dy, final String str) {
        a(str, OverlayablePolicy.NAME_flags, sb, new StringBuilder().append(c0942Dy.e()).toString());
        a(str, "classifier", sb, c0942Dy.c.toString());
        a(str, "arguments", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda86
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(sb, c0942Dy, (String) obj);
            }
        });
        a(str, "abbreviatedType", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda87
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.a(str, sb, c0942Dy, (String) obj);
            }
        });
        a(str, "outerType", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda89
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(str, sb, c0942Dy, (String) obj);
            }
        });
        final C2742oy c2742oyF = c0942Dy.f();
        if (c2742oyF != null) {
            a(str, "flexibleTypeUpperBound", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda90
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1821dA.a(str, sb, c2742oyF, (String) obj);
                }
            });
        }
        a(str, Options.TYPE_RAW, sb, new StringBuilder().append(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.b(c0942Dy)).toString());
        a(str, "annotations", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda91
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.c(sb, c0942Dy, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void e(StringBuilder sb, C3539zy c3539zy, String str) {
        a(sb, c3539zy.j(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void e(StringBuilder sb, C0974Ey c0974Ey, String str) {
        a(sb, c0974Ey.e, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void e(final StringBuilder sb, C2120gy c2120gy, String str) {
        a(str, "KmEffectExpression", sb, c2120gy.g(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda37
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C2120gy) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(final StringBuilder sb, C1283Px c1283Px, String str) {
        a(str, "KmType", sb, c1283Px.p(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda96
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C0942Dy) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(StringBuilder sb, C1493Wx c1493Wx, String str) {
        b(sb, c1493Wx.d(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(StringBuilder sb, C2815py c2815py, String str) {
        a(sb, c2815py.h(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(StringBuilder sb, C3539zy c3539zy, String str) {
        a(sb, c3539zy.g(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(final StringBuilder sb, C0942Dy c0942Dy, String str) {
        a(str, "KmTypeProjection", sb, c0942Dy.d(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda59
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C1227Ny) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(String str, StringBuilder sb, C0942Dy c0942Dy, String str2) {
        a(sb, c0942Dy.g(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(final StringBuilder sb, final C2742oy c2742oy, String str) {
        a(str, "typeFlexibilityId", sb, c2742oy.b());
        a(str, ModuleXmlParser.TYPE, sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.a(sb, c2742oy, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(StringBuilder sb, C1283Px c1283Px, String str) {
        a(sb, c1283Px.l(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(StringBuilder sb, C1227Ny c1227Ny, String str) {
        a(sb, c1227Ny.c(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(final StringBuilder sb, final C1493Wx c1493Wx, String str) {
        a(str, OverlayablePolicy.NAME_flags, sb, new StringBuilder().append(c1493Wx.c()).toString());
        a(str, "valueParameters", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda80
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(sb, c1493Wx, (String) obj);
            }
        });
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVarA = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c1493Wx);
        a(str, "signature", sb, jVarA != null ? jVarA.a() : "null");
        a(sb, c1493Wx.e(), str);
    }

    private static void b(final StringBuilder sb, List list, String str) {
        a(str, "KmValueParameter", sb, list, new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C1284Py) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(StringBuilder sb, C1284Py c1284Py, String str) {
        a(sb, c1284Py.d(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(final StringBuilder sb, C1119Jy c1119Jy, String str) {
        a(str, "KmType", sb, c1119Jy.e(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C0942Dy) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(final StringBuilder sb, C0974Ey c0974Ey, String str) {
        a(str, "KmAnnotation", sb, c0974Ey.c(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda93
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a((C3321wx) obj2, sb, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C3321wx c3321wx, final StringBuilder sb, String str) {
        final Map<String, AbstractC3467yx> mapA = c3321wx.a();
        a(str, "{ key: String, value: KmAnnotationArgument<?> }", sb, mapA.keySet(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda94
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, mapA, (String) obj, (String) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(StringBuilder sb, C2815py c2815py, String str) {
        a(sb, c2815py.i(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void f(final StringBuilder sb, final C2120gy c2120gy, String str) {
        String string;
        a(str, OverlayablePolicy.NAME_flags, sb, new StringBuilder().append(c2120gy.f()).toString());
        if (c2120gy.h() == null) {
            string = "null";
        } else {
            string = new StringBuilder().append(c2120gy.h()).toString();
        }
        a(str, "foo", sb, string);
        a(str, "constantValue", sb, c2120gy.e() != null ? c2120gy.e().toString() : "null");
        a(str, "isInstanceType", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.c(sb, c2120gy, (String) obj);
            }
        });
        a(str, "andArguments", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda22
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.d(sb, c2120gy, (String) obj);
            }
        });
        a(str, "orArguments", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda33
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.e(sb, c2120gy, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(StringBuilder sb, C3539zy c3539zy, String str) {
        a(sb, c3539zy.h(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(final StringBuilder sb, C1653ay c1653ay, String str) {
        a(str, "KmEffect", sb, c1653ay.b(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda42
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C2036fy) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(final StringBuilder sb, C0942Dy c0942Dy, String str) {
        a(str, "KmAnnotion", sb, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c0942Dy), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda34
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a((C3321wx) obj2, sb, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(final StringBuilder sb, C2036fy c2036fy, String str) {
        a(str, "KmEffectExpression", sb, c2036fy.e(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda85
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C2120gy) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(final StringBuilder sb, final C1227Ny c1227Ny, String str) {
        a(str, ModuleXmlParser.TYPE, sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda65
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(sb, c1227Ny, (String) obj);
            }
        });
        if (c1227Ny.d() != null) {
            a(str, "variance", sb, c1227Ny.d().name());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void g(final StringBuilder sb, final C3539zy c3539zy, String str) {
        String strA;
        String strA2;
        String strA3;
        String strA4;
        a(str, OverlayablePolicy.NAME_flags, sb, new StringBuilder().append(c3539zy.d()).toString());
        a(str, "name", sb, c3539zy.f());
        a(str, "receiverParameterType", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda74
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(sb, c3539zy, (String) obj);
            }
        });
        a(str, "returnType", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda75
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.c(sb, c3539zy, (String) obj);
            }
        });
        a(str, "typeParameters", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda76
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.d(sb, c3539zy, (String) obj);
            }
        });
        a(str, "getterFlags", sb, new StringBuilder().append(c3539zy.e()).toString());
        a(str, "setterFlags", sb, new StringBuilder().append(c3539zy.i()).toString());
        a(str, "setterParameter", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda78
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.e(sb, c3539zy, (String) obj);
            }
        });
        a(sb, c3539zy.l(), str);
        a(str, "contextReceiverTypes", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda79
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.f(sb, c3539zy, (String) obj);
            }
        });
        a(str, "jvmFlags", sb, new StringBuilder().append(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.c(c3539zy)).toString());
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e eVarA = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c3539zy);
        String strA5 = "null";
        if (eVarA == null) {
            strA = "null";
        } else {
            strA = eVarA.a();
        }
        a(str, "fieldSignature", sb, strA);
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVarB = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.b(c3539zy);
        if (jVarB == null) {
            strA2 = "null";
        } else {
            strA2 = jVarB.a();
        }
        a(str, "getterSignature", sb, strA2);
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVarD = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.d(c3539zy);
        if (jVarD == null) {
            strA3 = "null";
        } else {
            strA3 = jVarD.a();
        }
        a(str, "setterSignature", sb, strA3);
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVarE = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.e(c3539zy);
        if (jVarE == null) {
            strA4 = "null";
        } else {
            strA4 = jVarE.a();
        }
        a(str, "syntheticMethodForAnnotations", sb, strA4);
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVarE2 = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.e(c3539zy);
        if (jVarE2 != null) {
            strA5 = jVarE2.a();
        }
        a(str, "syntheticMethodForDelegate", sb, strA5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(StringBuilder sb, C1284Py c1284Py, String str) {
        a(sb, c1284Py.e(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(StringBuilder sb, C0974Ey c0974Ey, String str) {
        a(sb, c0974Ey.f, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(final StringBuilder sb, List list, String str) {
        a(str, "KmVersionRequirement", sb, list, new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda47
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (String) obj, (C1439Uy) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(final StringBuilder sb, final C1653ay c1653ay, String str) {
        a(str, "effects", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda62
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(sb, c1653ay, (String) obj);
            }
        });
    }

    public static String a(String str, KotlinClassMetadata kotlinClassMetadata) {
        if (kotlinClassMetadata instanceof KotlinClassMetadata.Class) {
            return a((KotlinClassMetadata.Class) kotlinClassMetadata, str);
        }
        if (kotlinClassMetadata instanceof KotlinClassMetadata.FileFacade) {
            return a((KotlinClassMetadata.FileFacade) kotlinClassMetadata, str);
        }
        if (kotlinClassMetadata instanceof KotlinClassMetadata.a) {
            return a((KotlinClassMetadata.a) kotlinClassMetadata, str);
        }
        if (kotlinClassMetadata instanceof KotlinClassMetadata.MultiFileClassPart) {
            return a((KotlinClassMetadata.MultiFileClassPart) kotlinClassMetadata, str);
        }
        if (kotlinClassMetadata instanceof KotlinClassMetadata.SyntheticClass) {
            return a((KotlinClassMetadata.SyntheticClass) kotlinClassMetadata, str);
        }
        throw new C3405y10("An error would be thrown before in createKotlinInfo");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(final StringBuilder sb, final C2036fy c2036fy, String str) {
        a(str, ModuleXmlParser.TYPE, sb, c2036fy.g().toString());
        a(str, "invocationKind", sb, c2036fy.f() == null ? "null" : c2036fy.f().toString());
        a(str, "constructorArguments", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda83
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(sb, c2036fy, (String) obj);
            }
        });
        final C2120gy c2120gyD = c2036fy.d();
        if (c2120gyD == null) {
            a(str, "conclusion", sb, "null");
        } else {
            a(str, "conclusion", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda84
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1821dA.a(sb, c2120gyD, (String) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(final StringBuilder sb, final C1284Py c1284Py, String str) {
        a(str, OverlayablePolicy.NAME_flags, sb, new StringBuilder().append(c1284Py.b()).toString());
        a(str, "name", sb, c1284Py.c());
        a(str, ModuleXmlParser.TYPE, sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda38
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(sb, c1284Py, (String) obj);
            }
        });
        a(str, "varargElementType", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda39
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.c(sb, c1284Py, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(final StringBuilder sb, final C1119Jy c1119Jy, String str) {
        a(str, "id", sb, new StringBuilder().append(c1119Jy.c()).toString());
        a(str, OverlayablePolicy.NAME_flags, sb, new StringBuilder().append(c1119Jy.b()).toString());
        a(str, "name", sb, c1119Jy.d());
        a(str, "variance", sb, c1119Jy.f().name());
        a(str, "upperBounds", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda97
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(sb, c1119Jy, (String) obj);
            }
        });
        a(str, "extensions", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.c(sb, c1119Jy, (String) obj);
            }
        });
    }

    private static String a(final KotlinClassMetadata.Class r2, String str) {
        final StringBuilder sb = new StringBuilder(str);
        b(str, "Metadata.Class", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda81
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.a(sb, r2, (String) obj);
            }
        });
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, KotlinClassMetadata.Class r1, String str) {
        a(str, sb, r1.b());
    }

    private static String a(final KotlinClassMetadata.FileFacade fileFacade, String str) {
        final StringBuilder sb = new StringBuilder(str);
        b(str, "Metadata.FileFacade", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda92
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.a(sb, fileFacade, (String) obj);
            }
        });
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(StringBuilder sb, C2120gy c2120gy, String str) {
        a(sb, c2120gy.i(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(StringBuilder sb, C0974Ey c0974Ey, String str) {
        a(sb, c0974Ey.f(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(final StringBuilder sb, C2120gy c2120gy, String str) {
        a(str, "KmEffectExpression", sb, c2120gy.d(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda21
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C2120gy) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, KotlinClassMetadata.FileFacade fileFacade, String str) {
        a(str, sb, fileFacade.b());
    }

    private static String a(KotlinClassMetadata.a aVar, String str) {
        return str + "MetaData.MultiFileClassFacade(" + com.android.tools.r8.utils.Y2.a(", ", aVar.b()) + Constant.AFTER_QUTO;
    }

    private static String a(final KotlinClassMetadata.MultiFileClassPart multiFileClassPart, String str) {
        final StringBuilder sb = new StringBuilder(str);
        b(str, "Metadata.MultiFileClassPart", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda77
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.a(sb, multiFileClassPart, (String) obj);
            }
        });
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(StringBuilder sb, KotlinClassMetadata.MultiFileClassPart multiFileClassPart, String str) {
        a(str, "facadeClassName", sb, multiFileClassPart.a().xs());
        a(str, sb, multiFileClassPart.b());
    }

    private static String a(final KotlinClassMetadata.SyntheticClass syntheticClass, String str) {
        final StringBuilder sb = new StringBuilder(str);
        b(str, "Metadata.SyntheticClass", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda57
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.a(syntheticClass, sb, (String) obj);
            }
        });
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(KotlinClassMetadata.SyntheticClass syntheticClass, final StringBuilder sb, String str) {
        try {
            final C3095ty c3095tyC = syntheticClass.c();
            if (c3095tyC != null) {
                a(str, "function", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda67
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C1821dA.a(sb, c3095tyC, (String) obj);
                    }
                });
            } else {
                a(str, "function", sb, "null");
            }
        } catch (C1640an e) {
            a(str, "function", sb, e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, C3095ty c3095ty, String str) {
        a(sb, c3095ty.b, str);
    }

    private static void a(final String str, String str2, final StringBuilder sb, final Collection collection, final BiConsumer biConsumer) {
        if (collection.isEmpty()) {
            sb.append(str2).append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            return;
        }
        InterfaceC3880d interfaceC3880d = new InterfaceC3880d() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda56
            @Override // com.android.tools.r8.utils.InterfaceC3880d
            public final void a() {
                C1821dA.a(collection, sb, str, biConsumer);
            }
        };
        String str3 = "[" + com.android.tools.r8.utils.Y2.c;
        sb.append(str2);
        sb.append(str3);
        interfaceC3880d.a();
        sb.append(str + "]");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Collection collection, StringBuilder sb, String str, BiConsumer biConsumer) {
        for (Object obj : collection) {
            sb.append(str).append("  ");
            biConsumer.accept(str + "  ", obj);
            sb.append(com.android.tools.r8.utils.Y2.c);
        }
    }

    private static void a(final String str, String str2, StringBuilder sb, final Consumer consumer) {
        sb.append(str);
        InterfaceC3880d interfaceC3880d = new InterfaceC3880d() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.utils.InterfaceC3880d
            public final void a() {
                consumer.accept(str);
            }
        };
        String str3 = "," + com.android.tools.r8.utils.Y2.c;
        sb.append(str2);
        sb.append(": ");
        interfaceC3880d.a();
        sb.append(str3);
    }

    public static void a(String str, String str2, final StringBuilder sb, final String str3) {
        sb.append(str);
        InterfaceC3880d interfaceC3880d = new InterfaceC3880d() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda58
            @Override // com.android.tools.r8.utils.InterfaceC3880d
            public final void a() {
                sb.append(str3);
            }
        };
        String str4 = "," + com.android.tools.r8.utils.Y2.c;
        sb.append(str2);
        sb.append(": ");
        interfaceC3880d.a();
        sb.append(str4);
    }

    private static void a(final StringBuilder sb, final InterfaceC1877dy interfaceC1877dy, String str) {
        a(str, "functions", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda69
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(sb, interfaceC1877dy, (String) obj);
            }
        });
        a(str, "properties", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda70
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.c(sb, interfaceC1877dy, (String) obj);
            }
        });
        a(str, "typeAliases", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda71
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.d(sb, interfaceC1877dy, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(C2815py c2815py) {
        return com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.b(c2815py).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(C3539zy c3539zy) {
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVarB = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.b(c3539zy);
        if (jVarB != null) {
            return jVarB.a();
        }
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVarD = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.d(c3539zy);
        if (jVarD != null) {
            return jVarD.a();
        }
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.e eVarA = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c3539zy);
        if (eVarA != null) {
            return eVarA.a();
        }
        return c3539zy.f();
    }

    public static void a(String str, final StringBuilder sb, final C3247vy c3247vy) {
        a(sb, (InterfaceC1877dy) c3247vy, str);
        AbstractC0936Ds.c(c3247vy, "<this>");
        a(str, "moduleName", sb, ((C1025Gu) c3247vy.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.k.b)).d());
        a(str, "localDelegatedProperties", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.a(sb, c3247vy, (String) obj);
            }
        });
    }

    public static void a(String str, final StringBuilder sb, final C1283Px c1283Px) {
        a(str, OverlayablePolicy.NAME_flags, sb, new StringBuilder().append(c1283Px.j()).toString());
        a(str, "jvmFlags", sb, new StringBuilder().append(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.b(c1283Px)).toString());
        a(str, "name", sb, c1283Px.m());
        a(str, "typeParameters", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda26
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.a(sb, c1283Px, (String) obj);
            }
        });
        a(str, "superTypes", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda27
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(sb, c1283Px, (String) obj);
            }
        });
        if (c1283Px.k() != null) {
            a(str, "inlineClassUnderlyingPropertyName", sb, c1283Px.k());
        }
        if (c1283Px.l() != null) {
            a(str, "inlineClassUnderlyingType", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda28
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1821dA.c(sb, c1283Px, (String) obj);
                }
            });
        }
        String strF = c1283Px.f();
        a(str, "enumEntries", sb, "[" + com.android.tools.r8.utils.Y2.a(",", c1283Px.i()) + "]");
        if (strF == null) {
            strF = "null";
        }
        a(str, "companionObject", sb, strF);
        a(str, "sealedSubclasses", sb, "[" + com.android.tools.r8.utils.Y2.a(",", c1283Px.o()) + "]");
        a(str, "nestedClasses", sb, "[" + com.android.tools.r8.utils.Y2.a(",", c1283Px.n()) + "]");
        a(str, "anonymousObjectOriginName", sb, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c1283Px));
        a(str, "moduleName", sb, com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.d(c1283Px));
        a(str, "localDelegatedProperties", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda29
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.d(sb, c1283Px, (String) obj);
            }
        });
        a(sb, c1283Px.r(), str);
        a(str, "constructors", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda30
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.e(sb, c1283Px, (String) obj);
            }
        });
        a(str, "contextReceiverTypes", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda31
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.f(sb, c1283Px, (String) obj);
            }
        });
        a(sb, (InterfaceC1877dy) c1283Px, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, C1283Px c1283Px, String str) {
        a(sb, c1283Px.q(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(C1493Wx c1493Wx) {
        return com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.a(c1493Wx).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final StringBuilder sb, final C1493Wx c1493Wx, String str) {
        b(str, "KmConstructor", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda95
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.c(sb, c1493Wx, (String) obj);
            }
        });
    }

    public static void a(final StringBuilder sb, final C2815py c2815py, String str) {
        b(str, "KmFunction", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda44
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.g(sb, c2815py, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final StringBuilder sb, final C3539zy c3539zy, String str) {
        b(str, "KmProperty", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda82
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.g(sb, c3539zy, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final StringBuilder sb, final C0942Dy c0942Dy, String str) {
        if (c0942Dy == null) {
            sb.append("null");
        } else {
            b(str, "KmType", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda51
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1821dA.d(sb, c0942Dy, (String) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str, StringBuilder sb, C0942Dy c0942Dy, String str2) {
        a(sb, c0942Dy.c(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str, final StringBuilder sb, final C2742oy c2742oy, String str2) {
        b(str, "FlexibleTypeUpperBound", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda32
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(sb, c2742oy, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, C2742oy c2742oy, String str) {
        a(sb, c2742oy.a(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final StringBuilder sb, final C1227Ny c1227Ny, String str) {
        b(str, "KmTypeProjection", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda54
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.c(sb, c1227Ny, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final StringBuilder sb, final C1284Py c1284Py, String str) {
        if (c1284Py == null) {
            sb.append("null");
        } else {
            b(str, "KmValueParameter", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda43
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1821dA.d(sb, c1284Py, (String) obj);
                }
            });
        }
    }

    private static void a(final StringBuilder sb, List list, String str) {
        a(str, "KmTypeParameter", sb, list, new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda73
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1821dA.a(sb, (C1119Jy) obj2, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final StringBuilder sb, final C1119Jy c1119Jy, String str) {
        b(str, "KmTypeParameter", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda68
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.d(sb, c1119Jy, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final StringBuilder sb, final C0974Ey c0974Ey, String str) {
        b(str, "KmTypeAlias", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda55
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.f(sb, c0974Ey, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final C3321wx c3321wx, final StringBuilder sb, String str) {
        b(str, "KmAnnotation", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda40
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.a(sb, c3321wx, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final StringBuilder sb, final C3321wx c3321wx, String str) {
        a(str, "className", sb, c3321wx.b());
        a(str, "arguments", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda48
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.b(c3321wx, sb, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final StringBuilder sb, final Map map, String str, final String str2) {
        b(str, "", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda46
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.a(str2, sb, map, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final String str, final StringBuilder sb, final Map map, final String str2) {
        a(str2, str, sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda36
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.a(str2, sb, map, str, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str, StringBuilder sb, Map map, String str2, String str3) {
        a(sb, str, (AbstractC3467yx) map.get(str2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final StringBuilder sb, String str, AbstractC3467yx abstractC3467yx) {
        if (abstractC3467yx instanceof AbstractC3467yx.a) {
            a(str, "ArrayValue", sb, ((AbstractC3467yx.a) abstractC3467yx).a(), new BiConsumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda12
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C1821dA.a(sb, (String) obj, (AbstractC3467yx) obj2);
                }
            });
        } else {
            sb.append(abstractC3467yx.toString());
        }
    }

    private static void a(final StringBuilder sb, final ArrayList arrayList, String str) {
        a(str, "versionRequirements", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda25
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.c(sb, arrayList, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final StringBuilder sb, String str, final C1439Uy c1439Uy) {
        b(str, "KmVersionRequirement", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda45
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.a(sb, c1439Uy, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(StringBuilder sb, C1439Uy c1439Uy, String str) {
        String string;
        a(str, Namer.METADATA_CLASS_KIND, sb, c1439Uy.b.name());
        a(str, FirebaseAnalytics.Param.LEVEL, sb, c1439Uy.c.name());
        if (c1439Uy.b() == null) {
            string = "null";
        } else {
            string = c1439Uy.b().toString();
        }
        a(str, "errorCode", sb, string);
        a(str, CrashHianalyticsData.MESSAGE, sb, c1439Uy.c());
        C1392Ty c1392Ty = c1439Uy.f;
        if (c1392Ty != null) {
            a(str, "version", sb, c1392Ty.toString());
        } else {
            AbstractC0936Ds.a("version");
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final StringBuilder sb, final C1653ay c1653ay, String str) {
        b(str, "KmContract", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.c(sb, c1653ay, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final StringBuilder sb, final C2036fy c2036fy, String str) {
        b(str, "KmEffect", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda63
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.c(sb, c2036fy, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final StringBuilder sb, final C2120gy c2120gy, String str) {
        b(str, "KmEffectExpression", sb, new Consumer() { // from class: com.android.tools.r8.internal.dA$$ExternalSyntheticLambda66
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1821dA.f(sb, c2120gy, (String) obj);
            }
        });
    }
}
