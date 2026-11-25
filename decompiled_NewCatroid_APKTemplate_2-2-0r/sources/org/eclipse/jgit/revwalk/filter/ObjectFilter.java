package org.eclipse.jgit.revwalk.filter;

import java.io.IOException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.revwalk.ObjectWalk;

/* JADX INFO: loaded from: classes13.dex */
public abstract class ObjectFilter {
    public static final ObjectFilter ALL = new AllFilter(null);

    public abstract boolean include(ObjectWalk objectWalk, AnyObjectId anyObjectId) throws IOException;

    private static final class AllFilter extends ObjectFilter {
        @Override // org.eclipse.jgit.revwalk.filter.ObjectFilter
        public boolean include(ObjectWalk objectWalk, AnyObjectId anyObjectId) {
            return true;
        }

        private AllFilter() {
        }

        /* synthetic */ AllFilter(AllFilter allFilter) {
            this();
        }
    }
}
