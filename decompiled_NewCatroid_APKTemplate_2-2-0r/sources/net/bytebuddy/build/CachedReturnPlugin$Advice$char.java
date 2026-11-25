package net.bytebuddy.build;

import net.bytebuddy.asm.Advice;

/* JADX INFO: loaded from: classes12.dex */
class CachedReturnPlugin$Advice$char {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    protected static char enter(@CachedReturnPlugin.CacheField char c) {
        return c;
    }

    @Advice.OnMethodExit
    protected static void exit(@Advice.Return(readOnly = false) char c, @CachedReturnPlugin.CacheField char c2) {
    }

    private CachedReturnPlugin$Advice$char() {
        throw new UnsupportedOperationException("This class is merely an advice template and should not be instantiated");
    }
}
