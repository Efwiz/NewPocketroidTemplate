package antlr.preprocessor;

import antlr.collections.impl.IndexedVector;
import java.util.Enumeration;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes.dex */
class Rule {
    protected String args;
    protected boolean bang = false;
    protected String block;
    protected Grammar enclosingGrammar;
    protected String initAction;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f60name;
    protected IndexedVector options;
    protected String returnValue;
    protected String throwsSpec;
    protected String visibility;

    public Rule(String str, String str2, IndexedVector indexedVector, Grammar grammar) {
        this.f60name = str;
        this.block = str2;
        this.options = indexedVector;
        setEnclosingGrammar(grammar);
    }

    public String getArgs() {
        return this.args;
    }

    public boolean getBang() {
        return this.bang;
    }

    public String getName() {
        return this.f60name;
    }

    public String getReturnValue() {
        return this.returnValue;
    }

    public String getVisibility() {
        return this.visibility;
    }

    public boolean narrowerVisibility(Rule rule) {
        if (this.visibility.equals("public")) {
            return !rule.equals("public");
        }
        if (this.visibility.equals("protected")) {
            return rule.equals("private");
        }
        this.visibility.equals("private");
        return false;
    }

    public boolean sameSignature(Rule rule) {
        boolean zEquals = this.f60name.equals(rule.getName());
        String str = this.args;
        boolean zEquals2 = str != null ? str.equals(rule.getArgs()) : true;
        String str2 = this.returnValue;
        return zEquals && zEquals2 && (str2 != null ? str2.equals(rule.getReturnValue()) : true);
    }

    public void setArgs(String str) {
        this.args = str;
    }

    public void setBang() {
        this.bang = true;
    }

    public void setEnclosingGrammar(Grammar grammar) {
        this.enclosingGrammar = grammar;
    }

    public void setInitAction(String str) {
        this.initAction = str;
    }

    public void setOptions(IndexedVector indexedVector) {
        this.options = indexedVector;
    }

    public void setReturnValue(String str) {
        this.returnValue = str;
    }

    public void setThrowsSpec(String str) {
        this.throwsSpec = str;
    }

    public void setVisibility(String str) {
        this.visibility = str;
    }

    public String toString() {
        String string = this.returnValue == null ? "" : new StringBuffer("returns ").append(this.returnValue).toString();
        String str = this.args;
        if (str == null) {
            str = "";
        }
        String string2 = new StringBuffer().append(new StringBuffer("").append(this.visibility != null ? new StringBuffer().append(this.visibility).append(" ").toString() : "").toString()).append(this.f60name).append(getBang() ? "!" : "").append(str).append(" ").append(string).append(this.throwsSpec).toString();
        if (this.options != null) {
            String string3 = new StringBuffer().append(string2).append(System.getProperty(SystemProperties.LINE_SEPARATOR)).append("options {").append(System.getProperty(SystemProperties.LINE_SEPARATOR)).toString();
            Enumeration enumerationElements = this.options.elements();
            while (enumerationElements.hasMoreElements()) {
                string3 = new StringBuffer().append(string3).append((Option) enumerationElements.nextElement()).append(System.getProperty(SystemProperties.LINE_SEPARATOR)).toString();
            }
            string2 = new StringBuffer().append(string3).append("}").append(System.getProperty(SystemProperties.LINE_SEPARATOR)).toString();
        }
        if (this.initAction != null) {
            string2 = new StringBuffer().append(string2).append(this.initAction).append(System.getProperty(SystemProperties.LINE_SEPARATOR)).toString();
        }
        return new StringBuffer().append(string2).append(this.block).toString();
    }
}
