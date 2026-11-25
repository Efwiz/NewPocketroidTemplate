package com.android.tools.r8.utils;

import com.android.tools.r8.DataDirectoryResource;
import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.DataResourceProvider;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.internal.V5;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/* JADX INFO: renamed from: com.android.tools.r8.utils.h, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3901h implements DataResourceProvider.Visitor {
    final /* synthetic */ Set a;
    final /* synthetic */ Set b;

    C3901h(TreeSet treeSet, TreeSet treeSet2) {
        this.a = treeSet;
        this.b = treeSet2;
    }

    @Override // com.android.tools.r8.DataResourceProvider.Visitor
    public final void visit(DataDirectoryResource dataDirectoryResource) {
        this.a.add(DataDirectoryResource.fromName(dataDirectoryResource.getName(), dataDirectoryResource.getOrigin()));
    }

    @Override // com.android.tools.r8.DataResourceProvider.Visitor
    public final void visit(DataEntryResource dataEntryResource) {
        try {
            this.b.add(DataEntryResource.fromBytes(V5.a(dataEntryResource.getByteStream()), dataEntryResource.getName(), dataEntryResource.getOrigin()));
        } catch (ResourceException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
