package io.github.rosemoe.sora.widget.snippet.variable;

/* JADX INFO: loaded from: classes11.dex */
public abstract class FileBasedSnippetVariableResolver implements ISnippetVariableResolver {
    @Override // io.github.rosemoe.sora.widget.snippet.variable.ISnippetVariableResolver
    public String[] getResolvableNames() {
        return new String[]{"TM_FILENAME", "TM_FILENAME_BASE", "TM_DIRECTORY", "TM_FILEPATH", "RELATIVE_PATH"};
    }
}
