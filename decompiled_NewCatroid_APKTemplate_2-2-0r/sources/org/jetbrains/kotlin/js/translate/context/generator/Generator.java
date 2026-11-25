package org.jetbrains.kotlin.js.translate.context.generator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;

/* JADX INFO: loaded from: classes5.dex */
public class Generator<V> {
    private final Map<DeclarationDescriptor, V> values = new HashMap();
    private final List<Rule<V>> rules = Lists.newArrayList();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2) {
            objArr[0] = "descriptor";
        } else {
            objArr[0] = "rule";
        }
        objArr[1] = "org/jetbrains/kotlin/js/translate/context/generator/Generator";
        if (i == 1) {
            objArr[2] = "get";
        } else if (i != 2) {
            objArr[2] = "addRule";
        } else {
            objArr[2] = CompilerOptions.GENERATE;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected final void addRule(Rule<V> rule) {
        if (rule == null) {
            $$$reportNull$$$0(0);
        }
        this.rules.add(rule);
    }

    public V get(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        V v = this.values.get(declarationDescriptor);
        if (v != null) {
            return v;
        }
        V vGenerate = generate(declarationDescriptor);
        this.values.put(declarationDescriptor, vGenerate);
        return vGenerate;
    }

    private V generate(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        Iterator<Rule<V>> it = this.rules.iterator();
        while (it.hasNext()) {
            V vApply = it.next().apply(declarationDescriptor);
            if (vApply != null) {
                return vApply;
            }
        }
        return null;
    }
}
