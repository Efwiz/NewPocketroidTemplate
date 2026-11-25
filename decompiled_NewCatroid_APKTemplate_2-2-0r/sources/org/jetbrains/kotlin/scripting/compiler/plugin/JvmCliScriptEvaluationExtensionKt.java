package org.jetbrains.kotlin.scripting.compiler.plugin;

import java.lang.reflect.Method;
import kotlin.Metadata;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: JvmCliScriptEvaluationExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u0004\u0018\u00010\u0001H\u0002¨\u0006\u0002"}, d2 = {"getPlatformClassLoader", "Ljava/lang/ClassLoader;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmCliScriptEvaluationExtensionKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassLoader getPlatformClassLoader() {
        try {
            Method declaredMethod = ClassLoader.class.getDeclaredMethod("getPlatformClassLoader", new Class[0]);
            Object objInvoke = declaredMethod != null ? declaredMethod.invoke(null, new Object[0]) : null;
            if (objInvoke instanceof ClassLoader) {
                return (ClassLoader) objInvoke;
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }
}
