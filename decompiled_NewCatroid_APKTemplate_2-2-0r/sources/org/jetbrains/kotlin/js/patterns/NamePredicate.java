package org.jetbrains.kotlin.js.patterns;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: loaded from: classes5.dex */
public final class NamePredicate implements Predicate<Name> {
    private final Set<Name> validNames;
    public static final NamePredicate PRIMITIVE_NUMBERS = new NamePredicate((List<String>) CollectionsKt.map(PrimitiveType.NUMBER_TYPES, new Function1() { // from class: org.jetbrains.kotlin.js.patterns.NamePredicate$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return ((PrimitiveType) obj).getTypeName().asString();
        }
    }));
    public static final NamePredicate PRIMITIVE_NUMBERS_MAPPED_TO_PRIMITIVE_JS = new NamePredicate((List<String>) CollectionsKt.mapNotNull(PrimitiveType.NUMBER_TYPES, new Function1() { // from class: org.jetbrains.kotlin.js.patterns.NamePredicate$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return NamePredicate.lambda$static$1((PrimitiveType) obj);
        }
    }));
    public static final NamePredicate STRING = new NamePredicate(CommonClassNames.JAVA_LANG_STRING_SHORT);
    public static final NamePredicate NUMBER = new NamePredicate("Number");
    public static final NamePredicate BOOLEAN = new NamePredicate("Boolean");
    public static final NamePredicate CHAR = new NamePredicate(PrimitiveType.CHAR.getTypeName());
    public static final NamePredicate LONG = new NamePredicate(PrimitiveType.LONG.getTypeName());

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "validNames", "org/jetbrains/kotlin/js/patterns/NamePredicate", "<init>"));
    }

    static /* synthetic */ String lambda$static$1(PrimitiveType primitiveType) {
        if (primitiveType != PrimitiveType.LONG) {
            return primitiveType.getTypeName().asString();
        }
        return null;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public NamePredicate(String... strArr) {
        this((List<String>) Arrays.asList(strArr));
        if (strArr == null) {
            $$$reportNull$$$0(0);
        }
    }

    private NamePredicate(List<String> list) {
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        this.validNames = new HashSet();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            this.validNames.add(Name.guessByFirstCharacter(it.next()));
        }
    }

    public NamePredicate(Collection<Name> collection) {
        if (collection == null) {
            $$$reportNull$$$0(2);
        }
        HashSet hashSet = new HashSet();
        this.validNames = hashSet;
        hashSet.addAll(collection);
    }

    public NamePredicate(Name... nameArr) {
        if (nameArr == null) {
            $$$reportNull$$$0(3);
        }
        HashSet hashSet = new HashSet();
        this.validNames = hashSet;
        hashSet.addAll(Lists.newArrayList(nameArr));
    }

    @Override // java.util.function.Predicate
    public boolean test(Name name2) {
        return this.validNames.contains(name2);
    }
}
