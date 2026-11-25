package com.android.tools.r8.utils;

import com.android.tools.r8.ClassFileResourceProvider;
import com.android.tools.r8.ProgramResource;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.T3;
import com.android.tools.r8.internal.C1367Tb;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class Y extends AbstractC3876c0 {
    private final com.android.tools.r8.graph.I b;
    private final ClassFileResourceProvider c;
    private final com.android.tools.r8.graph.M3 d;

    private Y(com.android.tools.r8.graph.I i, ClassFileResourceProvider classFileResourceProvider, com.android.tools.r8.graph.M3 m3) {
        super(i);
        this.b = i;
        this.c = classFileResourceProvider;
        this.d = m3;
    }

    @Override // com.android.tools.r8.utils.AbstractC3876c0
    public final void a(C0767v2 c0767v2, Consumer consumer) {
        String string = c0767v2.e.toString();
        ProgramResource programResource = this.c.getProgramResource(string);
        if (programResource != null) {
            try {
                new T3(this.d, consumer, this.b).a(programResource);
            } catch (ResourceException e) {
                throw new C1367Tb("Failed to load class: " + string, e);
            }
        }
    }

    public final String toString() {
        return "class-resource-provider(" + this.c.toString() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.utils.AbstractC3876c0
    public final Collection a() {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = this.c.getClassDescriptors().iterator();
        while (it.hasNext()) {
            arrayList.add(this.d.a.a.e(it.next()));
        }
        return arrayList;
    }
}
