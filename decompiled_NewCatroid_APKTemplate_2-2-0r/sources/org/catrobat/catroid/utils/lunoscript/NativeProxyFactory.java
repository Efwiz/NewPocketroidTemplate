package org.catrobat.catroid.utils.lunoscript;

import android.content.Context;
import java.util.Arrays;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.android.AndroidClassLoadingStrategy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ByteArrayClassLoader;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/NativeProxyFactory.class
  classes14.dex
 */
/* JADX INFO: compiled from: NativeProxyFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J@\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0014\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00010\r¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/NativeProxyFactory;", "", "()V", "createProxy", "lunoInstance", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoObject;", "nativeSuperclass", "Ljava/lang/Class;", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "context", "Landroid/content/Context;", "bakedMethods", "", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeProxyFactory {
    public static final NativeProxyFactory INSTANCE = new NativeProxyFactory();

    private NativeProxyFactory() {
    }

    public final Object createProxy(LunoValue.LunoObject lunoInstance, Class<?> nativeSuperclass, Interpreter interpreter, Context context, Map<String, ? extends Object> bakedMethods) {
        String message;
        Object objNewInstance;
        FixedValue.AssignerConfigurable assignerConfigurableValue;
        Intrinsics.checkNotNullParameter(lunoInstance, "lunoInstance");
        Intrinsics.checkNotNullParameter(nativeSuperclass, "nativeSuperclass");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bakedMethods, "bakedMethods");
        try {
            LunoInvocationHandler lunoInvocationHandler = new LunoInvocationHandler(lunoInstance, interpreter);
            AndroidClassLoadingStrategy.Wrapping wrapping = new AndroidClassLoadingStrategy.Wrapping(context.getDir(ByteArrayClassLoader.URL_SCHEMA, 0));
            DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition receiverTypeDefinitionIntercept = new ByteBuddy().subclass((Class) nativeSuperclass).constructor(ElementMatchers.any()).intercept(SuperMethodCall.INSTANCE);
            String[] strArr = (String[]) bakedMethods.keySet().toArray(new String[0]);
            for (Map.Entry<String, ? extends Object> entry : bakedMethods.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    assignerConfigurableValue = FixedValue.nullValue();
                } else {
                    assignerConfigurableValue = FixedValue.value(value);
                }
                receiverTypeDefinitionIntercept = receiverTypeDefinitionIntercept.method(ElementMatchers.named(key)).intercept(assignerConfigurableValue);
            }
            Class loaded = receiverTypeDefinitionIntercept.method(ElementMatchers.isMethod().and(ElementMatchers.not(ElementMatchers.isStatic())).and(ElementMatchers.not(ElementMatchers.isFinalizer())).and(ElementMatchers.not(ElementMatchers.isPrivate())).and(ElementMatchers.not(ElementMatchers.namedOneOf((String[]) Arrays.copyOf(strArr, strArr.length))))).intercept(MethodDelegation.to(lunoInvocationHandler)).make().load(NativeProxyFactory.class.getClassLoader(), wrapping).getLoaded();
            try {
                objNewInstance = loaded.getDeclaredConstructor(Context.class).newInstance(context);
            } catch (NoSuchMethodException unused) {
                objNewInstance = loaded.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            Intrinsics.checkNotNull(objNewInstance);
            return objNewInstance;
        } catch (Exception e) {
            String simpleName = nativeSuperclass.getSimpleName();
            Throwable cause = e.getCause();
            if (cause == null || (message = cause.getMessage()) == null) {
                message = e.getMessage();
            }
            throw new LunoRuntimeError("Failed to create native proxy for '" + simpleName + "': " + message, -1, e);
        }
    }
}
