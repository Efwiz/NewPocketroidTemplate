package javaslang;

import java.util.function.Supplier;

/* JADX INFO: loaded from: classes11.dex */
@FunctionalInterface
public interface Function0<R> extends Supplier<R>, InterfaceC0221<R> {
    public static final long serialVersionUID = 1;

    R apply();

    @Override // java.util.function.Supplier
    default R get() {
        return apply();
    }
}
