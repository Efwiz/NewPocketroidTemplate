package org.jetbrains.kotlin.com.intellij.util;

import java.util.function.Supplier;

/* JADX INFO: loaded from: classes3.dex */
public final class DefaultBundleService {
    private static final DefaultBundleService INSTANCE = new DefaultBundleService();
    private static final ThreadLocal<Boolean> ourDefaultBundle = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.DefaultBundleService$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return DefaultBundleService.lambda$static$0();
        }
    });

    static /* synthetic */ Boolean lambda$static$0() {
        return false;
    }

    public static boolean isDefaultBundle() {
        return ourDefaultBundle.get().booleanValue();
    }
}
