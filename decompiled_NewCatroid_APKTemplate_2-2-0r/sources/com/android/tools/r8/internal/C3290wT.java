package com.android.tools.r8.internal;

import com.android.tools.r8.naming.C3634k;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.retrace.RetraceClassElement;
import com.android.tools.r8.retrace.RetraceFrameElement;
import com.android.tools.r8.retrace.RetraceInvalidRewriteFrameDiagnostics;
import com.android.tools.r8.retrace.RetraceResult;
import com.android.tools.r8.retrace.RetraceStackTraceContext;
import com.android.tools.r8.retrace.RetracedClassMemberReference;
import com.android.tools.r8.retrace.RetracedClassReference;
import com.android.tools.r8.retrace.RetracedMethodReference;
import com.android.tools.r8.retrace.RetracedSourceFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3290wT implements RetraceFrameElement {
    private final UT a;
    private final C3437yT b;
    private final C2923rT c;
    private final List d;
    private final Optional e;
    private final OptionalInt f;
    private final YT g;

    C3290wT(C3437yT c3437yT, C2923rT c2923rT, UT ut, List list, Optional optional, OptionalInt optionalInt, YT yt) {
        this.a = ut;
        this.b = c3437yT;
        this.c = c2923rT;
        this.d = list;
        this.e = optional;
        this.f = optionalInt;
        this.g = yt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C3634k.b a(Object obj) {
        return ((C3359xT) obj).a;
    }

    @Override // com.android.tools.r8.retrace.RetraceFrameElement
    public final void forEach(Consumer consumer) {
        List list = this.d;
        if (list == null || list.isEmpty()) {
            consumer.accept(VT.a(this, this.a, 0));
            return;
        }
        consumer.accept(VT.a(this, this.a, 0));
        Iterator it = getOuterFrames().iterator();
        int i = 1;
        while (it.hasNext()) {
            consumer.accept(VT.a(this, (UT) it.next(), i));
            i++;
        }
    }

    @Override // com.android.tools.r8.retrace.RetraceFrameElement
    public final void forEachRewritten(Consumer consumer) {
        List listA;
        ET etA;
        CT ct = this.b.d;
        if (ct == null || (listA = com.android.tools.r8.utils.V1.a((Collection) this.d, new Function() { // from class: com.android.tools.r8.internal.wT$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3290wT.a((C3359xT) obj);
            }
        })) == null || listA.isEmpty()) {
            etA = ET.a();
        } else {
            DT dt = new DT();
            Iterator it = listA.iterator();
            while (it.hasNext()) {
                for (C2308jU c2308jU : ((C3634k.b) it.next()).j()) {
                    Iterator it2 = c2308jU.r().iterator();
                    do {
                        if (!it2.hasNext()) {
                            Iterator it3 = c2308jU.q().iterator();
                            while (it3.hasNext()) {
                                ((AbstractC2158hU) it3.next()).a(dt);
                            }
                            break;
                        }
                    } while (((AbstractC2004fU) it2.next()).a(ct));
                }
            }
            etA = dt.a();
        }
        int iB = etA.b();
        List list = this.d;
        boolean zK = false;
        if (iB > ((list == null || list.isEmpty()) ? 1 : this.d.size())) {
            this.g.a().warning(RetraceInvalidRewriteFrameDiagnostics.create(iB, this.a.a().toString()));
            iB = 0;
        }
        UT ut = this.a;
        int i = 0;
        for (UT ut2 : getOuterFrames()) {
            int i2 = iB - 1;
            if (iB <= 0) {
                consumer.accept(VT.a(this, ut, i));
                i++;
            }
            ut = ut2;
            iB = i2;
        }
        if (iB <= 0) {
            if (this.e.isPresent()) {
                zK = ((com.android.tools.r8.naming.Q) this.e.get()).g();
            } else {
                List list2 = this.d;
                if (list2 != null && !list2.isEmpty()) {
                    zK = ((C3359xT) com.android.tools.r8.utils.V1.b(this.d)).a.k();
                }
            }
            if (zK) {
                return;
            }
            consumer.accept(VT.a(this, ut, i));
        }
    }

    @Override // com.android.tools.r8.retrace.RetraceFrameElement
    public final RetraceClassElement getClassElement() {
        return this.c;
    }

    @Override // com.android.tools.r8.retrace.RetraceFrameElement
    public final List getOuterFrames() {
        if (this.d == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 1; i < this.d.size(); i++) {
            C3359xT c3359xT = (C3359xT) this.d.get(i);
            C3634k.b bVar = c3359xT.a;
            ClassReference classReference = this.c.a().getClassReference();
            boolean z = NT.b;
            arrayList.add(this.b.a(NT.a(bVar.b, classReference), c3359xT, this.f));
        }
        return arrayList;
    }

    @Override // com.android.tools.r8.retrace.RetraceElement
    public final RetraceResult getParentResult() {
        return this.b;
    }

    @Override // com.android.tools.r8.retrace.RetraceFrameElement
    public final RetraceStackTraceContext getRetraceStackTraceContext() {
        boolean zL;
        List list = this.d;
        if (list != null && !list.isEmpty() && this.f.isPresent()) {
            if (this.e.isPresent()) {
                zL = ((com.android.tools.r8.naming.Q) this.e.get()).j();
            } else {
                List list2 = this.d;
                zL = (list2 == null || list2.isEmpty()) ? false : ((C3359xT) com.android.tools.r8.utils.V1.b(this.d)).a.l();
            }
            if (zL) {
                return CT.b().a(this.f).a();
            }
        }
        return RetraceStackTraceContext.empty();
    }

    @Override // com.android.tools.r8.retrace.RetraceFrameElement
    public final RetracedSourceFile getSourceFile(RetracedClassMemberReference retracedClassMemberReference) {
        RetracedClassReference holderClass = retracedClassMemberReference.getHolderClass();
        YT yt = this.b.c;
        boolean z = NT.b;
        ClassReference classReference = holderClass.getClassReference();
        return new WT(classReference, yt.a(classReference));
    }

    @Override // com.android.tools.r8.retrace.RetraceFrameElement
    public final RetracedMethodReference getTopFrame() {
        return this.a;
    }

    @Override // com.android.tools.r8.retrace.RetraceElement
    public final boolean isCompilerSynthesized() {
        boolean zK;
        if (!getOuterFrames().isEmpty()) {
            return false;
        }
        if (this.e.isPresent()) {
            zK = ((com.android.tools.r8.naming.Q) this.e.get()).g();
        } else {
            List list = this.d;
            zK = (list == null || list.isEmpty()) ? false : ((C3359xT) com.android.tools.r8.utils.V1.b(this.d)).a.k();
        }
        return zK;
    }

    @Override // com.android.tools.r8.retrace.RetraceFrameElement
    public final boolean isUnknown() {
        return this.a.isUnknown();
    }

    @Override // com.android.tools.r8.retrace.RetraceFrameElement
    public final Stream stream() {
        Stream.Builder builder = Stream.builder();
        Objects.requireNonNull(builder);
        forEach(new wT$$ExternalSyntheticLambda0(builder));
        return builder.build();
    }

    @Override // com.android.tools.r8.retrace.RetraceFrameElement
    public final Stream streamRewritten(RetraceStackTraceContext retraceStackTraceContext) {
        Stream.Builder builder = Stream.builder();
        Objects.requireNonNull(builder);
        forEachRewritten(new wT$$ExternalSyntheticLambda0(builder));
        return builder.build();
    }
}
