package net.bytebuddy.build;

import net.bytebuddy.asm.Advice;

/* JADX INFO: loaded from: classes12.dex */
class CachedReturnPlugin$Advice$long {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    protected static long enter(@CachedReturnPlugin.CacheField long j) {
        return j;
    }

    @Advice.OnMethodExit
    protected static void exit(@Advice.Return(readOnly = false) long j, @CachedReturnPlugin.CacheField long j2) {
    }

    private CachedReturnPlugin$Advice$long() {
        throw new UnsupportedOperationException("This class is merely an advice template and should not be instantiated");
    }
}
