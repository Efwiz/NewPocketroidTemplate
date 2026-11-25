package org.eclipse.jgit.transport;

import java.util.Map;
import org.eclipse.jgit.lib.Ref;

/* JADX INFO: loaded from: classes13.dex */
public interface RefFilter {
    public static final RefFilter DEFAULT = new RefFilter() { // from class: org.eclipse.jgit.transport.RefFilter$$ExternalSyntheticLambda0
        @Override // org.eclipse.jgit.transport.RefFilter
        public final Map filter(Map map) {
            return RefFilter.lambda$0(map);
        }
    };

    static /* synthetic */ Map lambda$0(Map map) {
        return map;
    }

    Map<String, Ref> filter(Map<String, Ref> map);
}
