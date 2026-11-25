package net.bytebuddy.build;

import net.bytebuddy.asm.Advice;

/* JADX INFO: loaded from: classes12.dex */
class CachedReturnPlugin$Advice$boolean {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    protected static boolean enter(@CachedReturnPlugin.CacheField boolean z) {
        return z;
    }

    @Advice.OnMethodExit
    protected static void exit(@Advice.Return(readOnly = false) boolean z, @CachedReturnPlugin.CacheField boolean z2) {
    }

    private CachedReturnPlugin$Advice$boolean() {
        throw new UnsupportedOperationException("This class is merely an advice template and should not be instantiated");
    }
}
