package net.bytebuddy.build;

import net.bytebuddy.asm.Advice;

/* JADX INFO: loaded from: classes12.dex */
class CachedReturnPlugin$Advice$double {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    protected static double enter(@CachedReturnPlugin.CacheField double d) {
        return d;
    }

    @Advice.OnMethodExit
    protected static void exit(@Advice.Return(readOnly = false) double d, @CachedReturnPlugin.CacheField double d2) {
    }

    private CachedReturnPlugin$Advice$double() {
        throw new UnsupportedOperationException("This class is merely an advice template and should not be instantiated");
    }
}
