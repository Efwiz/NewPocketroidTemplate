package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.huawei.agconnect.config.impl.Utils;
import java.util.function.Consumer;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class VP {
    public final C0759u2 A;
    public final C0759u2 B;
    public final C0737r2 C;
    public final C0737r2 D;
    public final C0684j2 E;
    public final C0684j2 F;
    public final C0684j2 G;
    private final C0722p1 a;
    public final C0767v2 b;
    public final C0767v2 c;
    public final C0767v2 d;
    public final C0767v2 e;
    public final C0767v2 f;
    public final C0767v2 g;
    public final C0767v2 h;
    public final C0767v2 i;
    public final C0767v2 j;
    public final C0767v2 k;
    public final C0767v2 l;
    public final C0767v2 m;
    public final C0767v2 n;
    public final C0767v2 o;
    public final QP p;
    public final TP q;
    public final RP r;
    public final SP s;
    public final UP t;
    public final C0759u2 u;
    public final C0759u2 v;
    public final C0759u2 w;
    public final C0759u2 x;
    public final C0759u2 y;
    public final C0759u2 z;

    /* JADX WARN: Multi-variable type inference failed */
    public VP(C0722p1 c0722p1) {
        this.a = c0722p1;
        this.b = c0722p1.e("Lcom/google/protobuf/Internal$EnumLite;");
        this.c = c0722p1.e("Lcom/google/protobuf/Internal$EnumLiteMap;");
        this.d = c0722p1.e("Lcom/google/protobuf/Internal$EnumVerifier;");
        this.e = c0722p1.e("Lcom/google/protobuf/GeneratedMessageLite$ExtendableMessage;");
        this.f = c0722p1.e("Lcom/google/protobuf/GeneratedMessageLite$ExtensionDescriptor;");
        this.g = c0722p1.e("Lcom/google/protobuf/ExtensionRegistryLite;");
        C0767v2 c0767v2E = c0722p1.e("Lcom/google/protobuf/GeneratedMessageLite$GeneratedExtension;");
        this.h = c0767v2E;
        C0767v2 c0767v2E2 = c0722p1.e("Lcom/google/protobuf/GeneratedMessageLite;");
        this.i = c0767v2E2;
        this.j = c0722p1.e("Lcom/google/protobuf/GeneratedMessageLite$Builder;");
        this.k = c0722p1.e("Lcom/google/protobuf/GeneratedMessageLite$ExtendableBuilder;");
        this.l = c0722p1.e("Lcom/google/protobuf/GeneratedMessageLite$ExtendableMessage;");
        C0767v2 c0767v2E3 = c0722p1.e("Lcom/google/protobuf/RawMessageInfo;");
        C0767v2 c0767v2E4 = c0722p1.e("Lcom/google/protobuf/MessageLite;");
        this.m = c0767v2E4;
        C0767v2 c0767v2E5 = c0722p1.e("Lcom/google/protobuf/GeneratedMessageLite$MethodToInvoke;");
        this.n = c0767v2E5;
        this.o = c0722p1.e("Lcom/google/protobuf/WireFormat$FieldType;");
        this.u = c0722p1.c(Utils.DEFAULT_NAME);
        this.v = c0722p1.c(JvmAbi.INSTANCE_FIELD);
        this.w = c0722p1.c("internalValueMap");
        C0759u2 c0759u2C = c0722p1.c("dynamicMethod");
        this.x = c0759u2C;
        this.y = c0722p1.c("findLiteExtensionByNumber");
        this.z = c0722p1.c("newBuilder");
        this.A = c0722p1.c("findValueByNumber");
        this.B = c0722p1.c("Lcom/google/protobuf/");
        C0767v2 c0767v2 = c0722p1.Z1;
        C0737r2 c0737r2B = c0722p1.b(c0767v2, c0767v2E5, c0767v2, c0767v2);
        this.C = c0737r2B;
        this.D = c0722p1.b(c0767v2E, c0767v2E4, c0722p1.A1);
        this.E = c0722p1.a(c0767v2E2, c0737r2B, c0759u2C);
        this.F = c0722p1.a(c0767v2E2, c0722p1.b(c0722p1.Z1, c0767v2E4, c0722p1.X1, c0722p1.c2), c0722p1.c("newMessageInfo"));
        this.G = c0722p1.a(c0767v2E3, c0722p1.b(c0722p1.D1, c0767v2E4, c0722p1.X1, c0722p1.c2), c0722p1.Y0);
        this.p = new QP(this, c0722p1);
        this.q = new TP(this, c0722p1);
        this.r = new RP(this, c0722p1);
        this.s = new SP(this, c0722p1);
        this.t = new UP(this, c0722p1);
    }

    public final void a(Consumer consumer) {
        QP qp = this.p;
        consumer.accept(qp.a);
        consumer.accept(qp.b);
        TP tp = this.q;
        consumer.accept(tp.a);
        consumer.accept(tp.b);
        consumer.accept(tp.c);
        consumer.accept(tp.d);
        consumer.accept(tp.e);
        consumer.accept(this.r.a);
        SP sp = this.s;
        consumer.accept(sp.a);
        consumer.accept(sp.b);
        this.t.getClass();
        consumer.accept(this.E);
        consumer.accept(this.F);
        consumer.accept(this.G);
    }

    public final com.android.tools.r8.graph.Y0 b(C0729q2 c0729q2) {
        return this.a.a(c0729q2.d, this.d, this.v);
    }

    public final boolean b(C0684j2 c0684j2) {
        if (c0684j2.h == this.D) {
            C0759u2 c0759u2 = c0684j2.f;
            C0759u2 c0759u3 = this.y;
            c0759u2.getClass();
            if (c0759u2.a(c0759u3.e) && c0684j2.e != this.g) {
                return true;
            }
        }
        return false;
    }

    public final com.android.tools.r8.graph.Y0 a(C0729q2 c0729q2) {
        C0722p1 c0722p1 = this.a;
        C0767v2 c0767v2 = c0729q2.d;
        return c0722p1.a(c0767v2, c0767v2, this.u);
    }

    public final boolean a(C0684j2 c0684j2) {
        return c0684j2.f == this.x && c0684j2.h == this.C;
    }

    public boolean a(com.android.tools.r8.graph.W0 w0) {
        return a(w0.getReference());
    }

    public final boolean a(com.android.tools.r8.graph.o5 o5Var) {
        return b(o5Var.getReference());
    }
}
