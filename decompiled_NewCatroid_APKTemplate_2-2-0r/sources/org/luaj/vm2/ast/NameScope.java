package org.luaj.vm2.ast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes6.dex */
public class NameScope {
    private static final Set LUA_KEYWORDS = new HashSet();
    public int functionNestingCount;
    public final Map namedVariables;
    public final NameScope outerScope;

    static {
        String[] strArr = {"and", PsiKeyword.BREAK, PsiKeyword.DO, PsiKeyword.ELSE, "elseif", "end", "false", PsiKeyword.FOR, "function", PsiKeyword.IF, "in", "local", "nil", "not", "or", "repeat", "return", "then", "true", "until", PsiKeyword.WHILE};
        for (int i = 0; i < 21; i++) {
            LUA_KEYWORDS.add(strArr[i]);
        }
    }

    public NameScope() {
        this.namedVariables = new HashMap();
        this.outerScope = null;
        this.functionNestingCount = 0;
    }

    public NameScope(NameScope nameScope) {
        this.namedVariables = new HashMap();
        this.outerScope = nameScope;
        this.functionNestingCount = nameScope != null ? nameScope.functionNestingCount : 0;
    }

    private void validateIsNotKeyword(String str) {
        if (LUA_KEYWORDS.contains(str)) {
            throw new IllegalArgumentException(new StringBuffer("name is a keyword: '").append(str).append(FormatHelper.QUOTE).toString());
        }
    }

    public Variable define(String str) throws IllegalStateException, IllegalArgumentException {
        validateIsNotKeyword(str);
        Variable variable = new Variable(str, this);
        this.namedVariables.put(str, variable);
        return variable;
    }

    public Variable find(String str) throws IllegalArgumentException {
        validateIsNotKeyword(str);
        for (NameScope nameScope = this; nameScope != null; nameScope = nameScope.outerScope) {
            if (nameScope.namedVariables.containsKey(str)) {
                return (Variable) nameScope.namedVariables.get(str);
            }
        }
        Variable variable = new Variable(str);
        this.namedVariables.put(str, variable);
        return variable;
    }
}
