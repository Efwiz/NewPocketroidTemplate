package io.github.rosemoe.sora.widget.snippet.variable;

import java.util.Random;
import java.util.UUID;

/* JADX INFO: loaded from: classes11.dex */
public class RandomBasedSnippetVariableResolver implements ISnippetVariableResolver {
    @Override // io.github.rosemoe.sora.widget.snippet.variable.ISnippetVariableResolver
    public String[] getResolvableNames() {
        return new String[]{"RANDOM", "RANDOM_HEX", "UUID"};
    }

    @Override // io.github.rosemoe.sora.widget.snippet.variable.ISnippetVariableResolver
    public String resolve(String str) {
        str.hashCode();
        switch (str) {
            case "RANDOM":
                return Integer.toString(new Random().nextInt());
            case "UUID":
                return UUID.randomUUID().toString();
            case "RANDOM_HEX":
                return Integer.toString(new Random().nextInt(), 16);
            default:
                throw new IllegalArgumentException("Unsupported variable name:" + str);
        }
    }
}
