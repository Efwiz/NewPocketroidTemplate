package org.eclipse.jgit.internal.transport.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: classes13.dex */
public final class FirstCommand {
    private final Set<String> capabilities;
    private final String line;

    public static FirstCommand fromLine(String str) {
        int iIndexOf = str.indexOf(0);
        if (iIndexOf < 0) {
            return new FirstCommand(str, Collections.emptySet());
        }
        return new FirstCommand(str.substring(0, iIndexOf), Collections.unmodifiableSet((Set) Arrays.asList(str.substring(iIndexOf + 1).split(" ")).stream().collect(Collectors.toSet())));
    }

    private FirstCommand(String str, Set<String> set) {
        this.line = str;
        this.capabilities = set;
    }

    public String getLine() {
        return this.line;
    }

    public Set<String> getCapabilities() {
        return this.capabilities;
    }
}
