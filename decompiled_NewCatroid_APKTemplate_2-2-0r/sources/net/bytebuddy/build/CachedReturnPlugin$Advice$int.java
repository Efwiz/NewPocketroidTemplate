package net.bytebuddy.build;

import net.bytebuddy.asm.Advice;

/* JADX INFO: loaded from: classes12.dex */
class CachedReturnPlugin$Advice$int {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    protected static int enter(@CachedReturnPlugin.CacheField int i) {
        return i;
    }

    @Advice.OnMethodExit
    protected static void exit(@Advice.Return(readOnly = false) int i, @CachedReturnPlugin.CacheField int i2) {
    }

    private CachedReturnPlugin$Advice$int() {
        throw new UnsupportedOperationException("This class is merely an advice template and should not be instantiated");
    }
}
