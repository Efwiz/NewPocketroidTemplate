package org.eclipse.jgit.ignore;

import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.ignore.internal.PathMatcher;

/* JADX INFO: loaded from: classes13.dex */
public interface IMatcher {
    public static final IMatcher NO_MATCH = new IMatcher() { // from class: org.eclipse.jgit.ignore.IMatcher.1
        @Override // org.eclipse.jgit.ignore.IMatcher
        public boolean matches(String str, int i, int i2) {
            return false;
        }

        @Override // org.eclipse.jgit.ignore.IMatcher
        public boolean matches(String str, boolean z, boolean z2) {
            return false;
        }
    };

    boolean matches(String str, int i, int i2);

    boolean matches(String str, boolean z, boolean z2);

    static IMatcher createPathMatcher(String str, boolean z) throws InvalidPatternException {
        return PathMatcher.createPathMatcher(str, '/', z);
    }
}
