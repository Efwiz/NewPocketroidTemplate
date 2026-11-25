package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0899Cj extends AbstractC0859Bj {
    private C1785ci b = C1785ci.b();
    private boolean c;

    protected AbstractC0899Cj() {
    }

    protected final void a(AbstractC0958Ej abstractC0958Ej) {
        if (!this.c) {
            this.b = this.b.clone();
            this.c = true;
        }
        this.b.a(abstractC0958Ej.a);
    }

    static C1785ci a(AbstractC0899Cj abstractC0899Cj) {
        abstractC0899Cj.b.f();
        abstractC0899Cj.c = false;
        return abstractC0899Cj.b;
    }

    public final void a(C1012Gj c1012Gj, AbstractC1045Hj abstractC1045Hj) {
        if (c1012Gj.a == d()) {
            if (!this.c) {
                this.b = this.b.clone();
                this.c = true;
            }
            this.b.a(c1012Gj.d, c1012Gj.b(abstractC1045Hj));
            return;
        }
        throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void a(C1012Gj c1012Gj, Serializable serializable) {
        Object objB;
        if (c1012Gj.a == d()) {
            if (!this.c) {
                this.b = this.b.clone();
                this.c = true;
            }
            C1785ci c1785ci = this.b;
            C0985Fj c0985Fj = c1012Gj.d;
            if (c0985Fj.c) {
                if (c0985Fj.b.a() == M20.i) {
                    objB = serializable;
                    ArrayList arrayList = new ArrayList();
                    Iterator it = ((List) serializable).iterator();
                    while (it.hasNext()) {
                        arrayList.add(c1012Gj.b(it.next()));
                    }
                    objB = arrayList;
                }
            } else {
                objB = c1012Gj.b(serializable);
            }
            objB = serializable;
            c1785ci.c(c0985Fj, objB);
            return;
        }
        throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
    }
}
