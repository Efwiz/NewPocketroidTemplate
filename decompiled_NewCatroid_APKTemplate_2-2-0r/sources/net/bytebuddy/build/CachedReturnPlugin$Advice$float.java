package net.bytebuddy.build;

import net.bytebuddy.asm.Advice;

/* JADX INFO: loaded from: classes12.dex */
class CachedReturnPlugin$Advice$float {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    protected static float enter(@CachedReturnPlugin.CacheField float f) {
        return f;
    }

    @Advice.OnMethodExit
    protected static void exit(@Advice.Return(readOnly = false) float f, @CachedReturnPlugin.CacheField float f2) {
    }

    private CachedReturnPlugin$Advice$float() {
        throw new UnsupportedOperationException("This class is merely an advice template and should not be instantiated");
    }
}
