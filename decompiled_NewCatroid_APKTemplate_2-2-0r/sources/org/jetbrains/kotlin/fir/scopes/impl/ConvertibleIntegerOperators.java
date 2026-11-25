package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ConvertibleIntegerOperators.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004*\b\u0012\u0004\u0012\u00020\u000e0\rH\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/ConvertibleIntegerOperators;", "", "()V", "binaryOperatorsNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getBinaryOperatorsNames", "()Ljava/util/Set;", "binaryOperatorsWithSignedArgument", "getBinaryOperatorsWithSignedArgument", "unaryOperatorNames", "getUnaryOperatorNames", "toNameSet", "", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConvertibleIntegerOperators {
    public static final ConvertibleIntegerOperators INSTANCE;
    private static final Set<Name> binaryOperatorsNames;
    private static final Set<Name> binaryOperatorsWithSignedArgument;
    private static final Set<Name> unaryOperatorNames;

    private ConvertibleIntegerOperators() {
    }

    public final Set<Name> getBinaryOperatorsNames() {
        return binaryOperatorsNames;
    }

    static {
        ConvertibleIntegerOperators convertibleIntegerOperators = new ConvertibleIntegerOperators();
        INSTANCE = convertibleIntegerOperators;
        binaryOperatorsNames = convertibleIntegerOperators.toNameSet(CollectionsKt.listOf((Object[]) new String[]{"plus", "minus", "times", "div", "rem", "and", "or", "xor", "shl", "shr", "ushr"}));
        unaryOperatorNames = convertibleIntegerOperators.toNameSet(CollectionsKt.listOf((Object[]) new String[]{"inv", "unaryPlus", "unaryMinus"}));
        binaryOperatorsWithSignedArgument = convertibleIntegerOperators.toNameSet(CollectionsKt.listOf((Object[]) new String[]{"shl", "shr", "ushr"}));
    }

    public final Set<Name> getUnaryOperatorNames() {
        return unaryOperatorNames;
    }

    public final Set<Name> getBinaryOperatorsWithSignedArgument() {
        return binaryOperatorsWithSignedArgument;
    }

    private final Set<Name> toNameSet(List<String> list) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(Name.identifier((String) it.next()));
        }
        return linkedHashSet;
    }
}
