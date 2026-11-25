package net.bytebuddy.build;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

/* JADX INFO: loaded from: classes12.dex */
class CachedReturnPlugin$Advice$Object {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    protected static Object enter(@CachedReturnPlugin.CacheField Object obj) {
        return obj;
    }

    @Advice.OnMethodExit
    protected static void exit(@Advice.Return(readOnly = false, typing = Assigner.Typing.DYNAMIC) Object obj, @CachedReturnPlugin.CacheField Object obj2) {
    }

    private CachedReturnPlugin$Advice$Object() {
        throw new UnsupportedOperationException("This class is merely an advice template and should not be instantiated");
    }
}
