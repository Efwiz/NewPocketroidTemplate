package org.apache.commons.collections.list;

import java.util.List;
import org.apache.commons.collections.functors.InstanceofPredicate;

/* JADX INFO: loaded from: classes12.dex */
public class TypedList {
    public static List decorate(List list, Class cls) {
        return new PredicatedList(list, InstanceofPredicate.getInstance(cls));
    }

    protected TypedList() {
    }
}
