package org.jetbrains.kotlin.com.intellij.patterns;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public final class ElementPatternCondition<T> {
    private final List<PatternCondition<? super T>> myConditions;
    private final InitialPatternCondition<T> myInitialCondition;

    public ElementPatternCondition(InitialPatternCondition<T> initialPatternCondition) {
        this.myInitialCondition = initialPatternCondition;
        this.myConditions = Collections.emptyList();
    }

    ElementPatternCondition(InitialPatternCondition<T> initialPatternCondition, List<PatternCondition<? super T>> list) {
        this.myInitialCondition = initialPatternCondition;
        this.myConditions = list;
    }

    private ElementPatternCondition(ElementPatternCondition<T> elementPatternCondition, PatternCondition<? super T> patternCondition) {
        this.myInitialCondition = elementPatternCondition.getInitialCondition();
        SmartList smartList = new SmartList((Collection) elementPatternCondition.getConditions());
        this.myConditions = smartList;
        smartList.add(patternCondition);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        append(sb, "");
        return sb.toString();
    }

    public void append(StringBuilder sb, String str) {
        this.myInitialCondition.append(sb, str);
        int size = this.myConditions.size();
        for (int i = 0; i < size; i++) {
            this.myConditions.get(i).append(sb.append(".\n").append(str), str);
        }
    }

    public List<PatternCondition<? super T>> getConditions() {
        return this.myConditions;
    }

    public InitialPatternCondition<T> getInitialCondition() {
        return this.myInitialCondition;
    }

    public ElementPatternCondition<T> append(PatternCondition<? super T> patternCondition) {
        return new ElementPatternCondition<>(this, patternCondition);
    }
}
