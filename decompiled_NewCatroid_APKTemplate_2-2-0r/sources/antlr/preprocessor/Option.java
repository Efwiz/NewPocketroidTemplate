package antlr.preprocessor;

import com.huawei.hms.framework.common.ContainerUtils;

/* JADX INFO: loaded from: classes.dex */
class Option {
    protected Grammar enclosingGrammar;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f59name;
    protected String rhs;

    public Option(String str, String str2, Grammar grammar) {
        this.f59name = str;
        this.rhs = str2;
        setEnclosingGrammar(grammar);
    }

    public Grammar getEnclosingGrammar() {
        return this.enclosingGrammar;
    }

    public String getName() {
        return this.f59name;
    }

    public String getRHS() {
        return this.rhs;
    }

    public void setEnclosingGrammar(Grammar grammar) {
        this.enclosingGrammar = grammar;
    }

    public void setName(String str) {
        this.f59name = str;
    }

    public void setRHS(String str) {
        this.rhs = str;
    }

    public String toString() {
        return new StringBuffer("\t").append(this.f59name).append(ContainerUtils.KEY_VALUE_DELIMITER).append(this.rhs).toString();
    }
}
