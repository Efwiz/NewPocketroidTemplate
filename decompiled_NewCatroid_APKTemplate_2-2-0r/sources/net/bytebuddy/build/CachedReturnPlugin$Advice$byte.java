package net.bytebuddy.build;

import net.bytebuddy.asm.Advice;

/* JADX INFO: loaded from: classes12.dex */
class CachedReturnPlugin$Advice$byte {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    protected static byte enter(@CachedReturnPlugin.CacheField byte b) {
        return b;
    }

    @Advice.OnMethodExit
    protected static void exit(@Advice.Return(readOnly = false) byte b, @CachedReturnPlugin.CacheField byte b2) {
    }

    private CachedReturnPlugin$Advice$byte() {
        throw new UnsupportedOperationException("This class is merely an advice template and should not be instantiated");
    }
}
