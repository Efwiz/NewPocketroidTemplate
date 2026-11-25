package org.jetbrains.kotlin.com.intellij.util.containers;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface Convertor<Src, Dst> {
    public static final IntoSelf SELF = new IntoSelf();

    public static class IntoSelf<Src> implements Convertor<Src, Src> {
        @Override // org.jetbrains.kotlin.com.intellij.util.containers.Convertor
        public Src convert(Src src) {
            return src;
        }
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/util/containers/Convertor", "self"));
    }

    Dst convert(Src src);

    static <T> Convertor<T, T> self() {
        IntoSelf intoSelf = SELF;
        if (intoSelf == null) {
            $$$reportNull$$$0(0);
        }
        return intoSelf;
    }
}
