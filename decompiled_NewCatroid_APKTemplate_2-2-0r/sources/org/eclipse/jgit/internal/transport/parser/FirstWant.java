package org.eclipse.jgit.internal.transport.parser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.JGitText;

/* JADX INFO: loaded from: classes13.dex */
public class FirstWant {
    private static final String AGENT_PREFIX = "agent=";

    @Nullable
    private final String agent;
    private final Set<String> capabilities;
    private final String line;

    public static FirstWant fromLine(String str) throws PackProtocolException {
        Set setEmptySet;
        String strSubstring = null;
        if (str.length() > 45) {
            String strSubstring2 = str.substring(45);
            if (!strSubstring2.startsWith(" ")) {
                throw new PackProtocolException(JGitText.get().wantNoSpaceWithCapabilities);
            }
            String strSubstring3 = strSubstring2.substring(1);
            HashSet hashSet = new HashSet();
            for (String str2 : strSubstring3.split(" ")) {
                if (str2.startsWith(AGENT_PREFIX)) {
                    strSubstring = str2.substring(AGENT_PREFIX.length());
                } else {
                    hashSet.add(str2);
                }
            }
            str = str.substring(0, 45);
            setEmptySet = Collections.unmodifiableSet(hashSet);
        } else {
            setEmptySet = Collections.emptySet();
        }
        return new FirstWant(str, setEmptySet, strSubstring);
    }

    private FirstWant(String str, Set<String> set, @Nullable String str2) {
        this.line = str;
        this.capabilities = set;
        this.agent = str2;
    }

    public String getLine() {
        return this.line;
    }

    public Set<String> getCapabilities() {
        return this.capabilities;
    }

    @Nullable
    public String getAgent() {
        return this.agent;
    }
}
