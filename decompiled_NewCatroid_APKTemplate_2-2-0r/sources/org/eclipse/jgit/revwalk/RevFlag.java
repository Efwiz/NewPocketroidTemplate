package org.eclipse.jgit.revwalk;

import java.text.MessageFormat;
import org.eclipse.jgit.internal.JGitText;

/* JADX INFO: loaded from: classes13.dex */
public class RevFlag {
    final int mask;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    final String f660name;
    final RevWalk walker;
    public static final RevFlag UNINTERESTING = new StaticRevFlag("UNINTERESTING", 4);
    public static final RevFlag SEEN = new StaticRevFlag("SEEN", 2);

    RevFlag(RevWalk revWalk, String str, int i) {
        this.walker = revWalk;
        this.f660name = str;
        this.mask = i;
    }

    public RevWalk getRevWalk() {
        return this.walker;
    }

    public String toString() {
        return this.f660name;
    }

    static class StaticRevFlag extends RevFlag {
        StaticRevFlag(String str, int i) {
            super(null, str, i);
        }

        @Override // org.eclipse.jgit.revwalk.RevFlag
        public RevWalk getRevWalk() {
            throw new UnsupportedOperationException(MessageFormat.format(JGitText.get().isAStaticFlagAndHasNorevWalkInstance, toString()));
        }
    }
}
