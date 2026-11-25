package com.android.tools.r8.internal;

import com.android.tools.r8.DataResourceConsumer;
import com.android.tools.r8.DataResourceProvider;
import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.ProgramResourceProvider;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3451yh {
    private final List a;

    public C3451yh(ArrayList arrayList) {
        this.a = arrayList;
    }

    public final ArrayList a() {
        ArrayList arrayList = new ArrayList();
        for (FeatureSplit featureSplit : this.a) {
            DataResourceConsumer dataResourceConsumer = featureSplit.getProgramConsumer().getDataResourceConsumer();
            if (dataResourceConsumer != null) {
                HashSet hashSet = new HashSet();
                Iterator<ProgramResourceProvider> it = featureSplit.getProgramResourceProviders().iterator();
                while (it.hasNext()) {
                    DataResourceProvider dataResourceProvider = it.next().getDataResourceProvider();
                    if (dataResourceProvider != null) {
                        hashSet.add(dataResourceProvider);
                    }
                }
                if (!hashSet.isEmpty()) {
                    arrayList.add(new C3381xh(hashSet, dataResourceConsumer));
                }
            }
        }
        return arrayList;
    }

    public final List b() {
        return this.a;
    }
}
