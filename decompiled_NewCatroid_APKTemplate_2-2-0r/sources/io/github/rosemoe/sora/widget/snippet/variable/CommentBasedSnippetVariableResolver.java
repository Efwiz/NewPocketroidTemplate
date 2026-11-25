package io.github.rosemoe.sora.widget.snippet.variable;

/* JADX INFO: loaded from: classes11.dex */
public class CommentBasedSnippetVariableResolver implements ISnippetVariableResolver {
    private String[] commentTokens;

    public CommentBasedSnippetVariableResolver() {
        this(null);
    }

    public CommentBasedSnippetVariableResolver(String[] strArr) {
        setCommentTokens(strArr);
    }

    public void setCommentTokens(String[] strArr) {
        this.commentTokens = strArr;
    }

    public String[] getCommentTokens() {
        return this.commentTokens;
    }

    @Override // io.github.rosemoe.sora.widget.snippet.variable.ISnippetVariableResolver
    public String[] getResolvableNames() {
        return new String[]{"LINE_COMMENT", "BLOCK_COMMENT_START", "BLOCK_COMMENT_END"};
    }

    @Override // io.github.rosemoe.sora.widget.snippet.variable.ISnippetVariableResolver
    public String resolve(String str) {
        String[] strArr = this.commentTokens;
        if (strArr == null || strArr.length != 3) {
            throw new IllegalStateException("language comment style is not configured properly");
        }
        str.hashCode();
        switch (str) {
            case "LINE_COMMENT":
                return this.commentTokens[0];
            case "BLOCK_COMMENT_START":
                return this.commentTokens[1];
            case "BLOCK_COMMENT_END":
                return this.commentTokens[2];
            default:
                throw new IllegalArgumentException("Unsupported variable name:" + str);
        }
    }
}
