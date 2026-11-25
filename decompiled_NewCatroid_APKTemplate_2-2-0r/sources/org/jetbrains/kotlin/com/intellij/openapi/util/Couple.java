package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
public class Couple<T> extends Pair<T, T> {
    private static final Couple<Object> EMPTY_COUPLE = new Couple<>(null, null);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/util/Couple", "getEmpty"));
    }

    public Couple(T t, T t2) {
        super(t, t2);
    }

    public static <T> Couple<T> of(T t, T t2) {
        return new Couple<>(t, t2);
    }

    public static <T> Couple<T> getEmpty() {
        Couple<T> couple = (Couple<T>) EMPTY_COUPLE;
        if (couple == null) {
            $$$reportNull$$$0(0);
        }
        return couple;
    }
}
