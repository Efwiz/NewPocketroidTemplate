package net.bytebuddy.build;

import net.bytebuddy.asm.Advice;

/* JADX INFO: loaded from: classes12.dex */
class CachedReturnPlugin$Advice$short {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    protected static short enter(@CachedReturnPlugin.CacheField short s) {
        return s;
    }

    @Advice.OnMethodExit
    protected static void exit(@Advice.Return(readOnly = false) short s, @CachedReturnPlugin.CacheField short s2) {
    }

    private CachedReturnPlugin$Advice$short() {
        throw new UnsupportedOperationException("This class is merely an advice template and should not be instantiated");
    }
}
