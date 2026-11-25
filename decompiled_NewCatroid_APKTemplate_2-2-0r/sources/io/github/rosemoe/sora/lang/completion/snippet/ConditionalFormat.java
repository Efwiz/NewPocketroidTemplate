package io.github.rosemoe.sora.lang.completion.snippet;

/* JADX INFO: loaded from: classes11.dex */
public class ConditionalFormat implements FormatString {
    public String elseValue;
    public int group;
    public String ifValue;
    public String shorthand;

    public int getGroup() {
        return this.group;
    }

    public void setGroup(int i) {
        this.group = i;
    }

    public String getIfValue() {
        return this.ifValue;
    }

    public void setIfValue(String str) {
        this.ifValue = str;
    }

    public String getElseValue() {
        return this.elseValue;
    }

    public void setElseValue(String str) {
        this.elseValue = str;
    }

    public void setShorthand(String str) {
        this.shorthand = str;
    }

    public String getShorthand() {
        return this.shorthand;
    }
}
