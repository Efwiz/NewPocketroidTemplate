package com.android.tools.r8.utils;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C2386kT$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.ED;
import com.android.tools.r8.internal.Q00$$ExternalSyntheticLambda1;
import com.android.tools.r8.internal.YT;
import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.retrace.RetraceClassElement;
import com.android.tools.r8.retrace.RetraceFieldElement;
import com.android.tools.r8.retrace.RetraceFieldResult;
import com.android.tools.r8.retrace.RetraceMethodElement;
import com.android.tools.r8.retrace.RetraceMethodResult;
import com.android.tools.r8.retrace.RetracedFieldReference;
import com.android.tools.r8.retrace.RetracedMethodReference;
import com.android.tools.r8.retrace.Retracer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class P2 {
    private static final P2 b = new P2(null);
    private final Retracer a;

    private P2(YT yt) {
        this.a = yt;
    }

    public static P2 a() {
        return b;
    }

    public final String b(C0767v2 c0767v2) {
        Q00$$ExternalSyntheticLambda1 q00$$ExternalSyntheticLambda1 = new Q00$$ExternalSyntheticLambda1();
        Function function = new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda17
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.b((RetraceClassElement) obj);
            }
        };
        Retracer retracer = this.a;
        return retracer == null ? (String) q00$$ExternalSyntheticLambda1.apply(c0767v2) : Y2.a(retracer.retraceClass(c0767v2.B0()).stream(), function);
    }

    public final String c(C0684j2 c0684j2) {
        return a(c0684j2, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0684j2) obj).r0();
            }
        }, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.b((RetracedMethodReference.KnownRetracedMethodReference) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.b((RetracedMethodReference) obj);
            }
        });
    }

    public final String d(com.android.tools.r8.graph.Y0 y0) {
        return a(y0, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda18
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.b((com.android.tools.r8.graph.Y0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda19
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.b((RetracedFieldReference.KnownRetracedFieldReference) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda20
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.b((RetracedFieldReference) obj);
            }
        });
    }

    public static P2 a(C3605b c3605b, O2 o2) {
        return c3605b == null ? b : new P2(YT.a(ED.a(c3605b), o2));
    }

    public final String c(com.android.tools.r8.graph.Y0 y0) {
        return a(y0, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.a((com.android.tools.r8.graph.Y0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.a((RetracedFieldReference.KnownRetracedFieldReference) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.a((RetracedFieldReference) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String b(RetraceClassElement retraceClassElement) {
        return retraceClassElement.getRetracedClass().getTypeName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String b(RetracedMethodReference.KnownRetracedMethodReference knownRetracedMethodReference) {
        return knownRetracedMethodReference.getMethodReference().toSourceString();
    }

    public final String a(C0767v2 c0767v2) {
        C2386kT$$ExternalSyntheticLambda0 c2386kT$$ExternalSyntheticLambda0 = new C2386kT$$ExternalSyntheticLambda0();
        Function function = new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda21
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.a((RetraceClassElement) obj);
            }
        };
        Retracer retracer = this.a;
        if (retracer == null) {
            return (String) c2386kT$$ExternalSyntheticLambda0.apply(c0767v2);
        }
        return Y2.a(retracer.retraceClass(c0767v2.B0()).stream(), function);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String b(RetracedMethodReference retracedMethodReference) {
        return retracedMethodReference.getHolderClass().getTypeName() + " " + retracedMethodReference.getMethodName();
    }

    public final String b(C0684j2 c0684j2) {
        return a(c0684j2, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.a((C0684j2) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.a((RetracedMethodReference.KnownRetracedMethodReference) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.a((RetracedMethodReference) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String b(com.android.tools.r8.graph.Y0 y0) {
        return y0.E0().toSourceString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String b(RetracedFieldReference.KnownRetracedFieldReference knownRetracedFieldReference) {
        return knownRetracedFieldReference.getFieldReference().toSourceString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(RetraceClassElement retraceClassElement) {
        return retraceClassElement.getRetracedClass().getDescriptor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String b(RetracedFieldReference retracedFieldReference) {
        return retracedFieldReference.getHolderClass().getDescriptor() + " " + retracedFieldReference.getFieldName();
    }

    private String a(C0684j2 c0684j2, Function function, final Function function2, final Function function3) {
        Retracer retracer = this.a;
        if (retracer == null) {
            return (String) function.apply(c0684j2);
        }
        RetraceMethodResult retraceMethodResultRetraceMethod = retracer.retraceMethod(c0684j2.E0());
        return Y2.a(retraceMethodResultRetraceMethod.stream(), new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.a(function3, function2, (RetraceMethodElement) obj);
            }
        });
    }

    public final String b(com.android.tools.r8.graph.I3 i3) {
        if (!(i3 instanceof AbstractC0744s2)) {
            return i3.r0();
        }
        return (String) ((AbstractC0744s2) i3).a(new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((C0767v2) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.d((com.android.tools.r8.graph.Y0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda15
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.c((C0684j2) obj);
            }
        });
    }

    public final boolean b() {
        return this == b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(Function function, Function function2, RetraceMethodElement retraceMethodElement) {
        if (retraceMethodElement.isUnknown()) {
            return (String) function.apply(retraceMethodElement.getRetracedMethod());
        }
        return (String) function2.apply(retraceMethodElement.getRetracedMethod().asKnown());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(C0684j2 c0684j2) {
        return c0684j2.E0().toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(RetracedMethodReference.KnownRetracedMethodReference knownRetracedMethodReference) {
        return knownRetracedMethodReference.getMethodReference().toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(RetracedMethodReference retracedMethodReference) {
        return retracedMethodReference.getHolderClass().getDescriptor() + retracedMethodReference.getMethodName();
    }

    private String a(com.android.tools.r8.graph.Y0 y0, Function function, final Function function2, final Function function3) {
        Retracer retracer = this.a;
        if (retracer == null) {
            return (String) function.apply(y0);
        }
        RetraceFieldResult retraceFieldResultRetraceField = retracer.retraceField(y0.E0());
        return Y2.a(retraceFieldResultRetraceField.stream(), new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return P2.a(function3, function2, (RetraceFieldElement) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(Function function, Function function2, RetraceFieldElement retraceFieldElement) {
        if (retraceFieldElement.isUnknown()) {
            return (String) function.apply(retraceFieldElement.getField());
        }
        return (String) function2.apply(retraceFieldElement.getField().asKnown());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(com.android.tools.r8.graph.Y0 y0) {
        return y0.E0().toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(RetracedFieldReference.KnownRetracedFieldReference knownRetracedFieldReference) {
        return knownRetracedFieldReference.getFieldReference().toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(RetracedFieldReference retracedFieldReference) {
        return retracedFieldReference.getHolderClass().getDescriptor() + retracedFieldReference.getFieldName();
    }

    public final String a(com.android.tools.r8.graph.I3 i3) {
        if (!(i3 instanceof AbstractC0744s2)) {
            return i3.toString();
        }
        return (String) ((AbstractC0744s2) i3).a(new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0767v2) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.c((com.android.tools.r8.graph.Y0) obj);
            }
        }, new Function() { // from class: com.android.tools.r8.utils.P2$$ExternalSyntheticLambda16
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((C0684j2) obj);
            }
        });
    }
}
