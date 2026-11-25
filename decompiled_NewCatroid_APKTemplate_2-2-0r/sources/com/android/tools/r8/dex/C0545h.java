package com.android.tools.r8.dex;

import com.android.tools.r8.DataDirectoryResource;
import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.DataResourceConsumer;
import com.android.tools.r8.DataResourceProvider;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.dex.h, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0545h implements DataResourceProvider.Visitor {
    final /* synthetic */ d0 a;
    final /* synthetic */ DataResourceConsumer b;
    final /* synthetic */ A1 c;
    final /* synthetic */ com.android.tools.r8.naming.K d;
    final /* synthetic */ Set e;

    C0545h(d0 d0Var, DataResourceConsumer dataResourceConsumer, A1 a1, com.android.tools.r8.naming.K k, HashSet hashSet) {
        this.a = d0Var;
        this.b = dataResourceConsumer;
        this.c = a1;
        this.d = k;
        this.e = hashSet;
    }

    @Override // com.android.tools.r8.DataResourceProvider.Visitor
    public final void visit(DataDirectoryResource dataDirectoryResource) {
        DataDirectoryResource dataDirectoryResourceA = this.a.a(dataDirectoryResource);
        if (dataDirectoryResourceA != null) {
            this.b.accept(dataDirectoryResourceA, this.c.e);
            this.c.e.a();
        }
    }

    @Override // com.android.tools.r8.DataResourceProvider.Visitor
    public final void visit(DataEntryResource dataEntryResource) {
        this.a.getClass();
        if (d0.c(dataEntryResource)) {
            return;
        }
        this.d.getClass();
        if (com.android.tools.r8.naming.K.a(dataEntryResource)) {
            return;
        }
        DataEntryResource dataEntryResourceB = this.a.b(dataEntryResource);
        if (this.e.add(dataEntryResourceB.getName())) {
            this.b.accept(dataEntryResourceB, this.c.e);
        } else {
            this.c.e.warning(new StringDiagnostic("Resource '" + dataEntryResource.getName() + "' already exists."));
        }
        this.c.e.a();
    }
}
