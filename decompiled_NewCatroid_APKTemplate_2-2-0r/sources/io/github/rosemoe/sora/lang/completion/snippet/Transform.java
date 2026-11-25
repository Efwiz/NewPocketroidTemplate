package io.github.rosemoe.sora.lang.completion.snippet;

import java.util.List;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes11.dex */
public class Transform {
    public List<FormatString> format;
    public boolean globalMode;
    public Pattern regexp;

    public Pattern getRegexp() {
        return this.regexp;
    }

    public void setRegexp(Pattern pattern) {
        this.regexp = pattern;
    }

    public List<FormatString> getFormat() {
        return this.format;
    }

    public void setFormat(List<FormatString> list) {
        this.format = list;
    }
}
