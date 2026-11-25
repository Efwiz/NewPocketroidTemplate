package org.catrobat.catroid.utils.lunoscript;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoInvocationHandler.class
  classes14.dex
 */
/* JADX INFO: compiled from: NativeProxyFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u00012\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0002J;\u0010\n\u001a\u0004\u0018\u00010\u00012\b\b\u0001\u0010\u000b\u001a\u00020\f2\u0010\b\u0001\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000e2\u000e\b\u0001\u0010\u000f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0010H\u0007¢\u0006\u0002\u0010\u0011R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoInvocationHandler;", "", "lunoInstance", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoObject;", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "(Lorg/catrobat/catroid/utils/lunoscript/LunoValue$LunoObject;Lorg/catrobat/catroid/utils/lunoscript/Interpreter;)V", "getDefaultValueForType", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "intercept", "method", "Ljava/lang/reflect/Method;", "args", "", "superMethod", "Ljava/util/concurrent/Callable;", "(Ljava/lang/reflect/Method;[Ljava/lang/Object;Ljava/util/concurrent/Callable;)Ljava/lang/Object;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LunoInvocationHandler {
    private final Interpreter interpreter;
    private final LunoValue.LunoObject lunoInstance;

    public LunoInvocationHandler(LunoValue.LunoObject lunoInstance, Interpreter interpreter) {
        Intrinsics.checkNotNullParameter(lunoInstance, "lunoInstance");
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        this.lunoInstance = lunoInstance;
        this.interpreter = interpreter;
    }

    private final Object getDefaultValueForType(Class<?> type) {
        if (Intrinsics.areEqual(type, Void.TYPE)) {
            return null;
        }
        if (Intrinsics.areEqual(type, Boolean.TYPE)) {
            return false;
        }
        return type.isPrimitive() ? 0 : null;
    }

    @RuntimeType
    public final Object intercept(@Origin Method method, @AllArguments Object[] args, @SuperCall Callable<?> superMethod) {
        LunoValue.LunoFunction lunoFunctionFindMethod;
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(args, "args");
        LunoValue.LunoClass klass = this.lunoInstance.getKlass();
        if (klass != null) {
            String name2 = method.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            lunoFunctionFindMethod = klass.findMethod(name2);
        } else {
            lunoFunctionFindMethod = null;
        }
        if (lunoFunctionFindMethod == null) {
            if (superMethod != null) {
                return superMethod.call();
            }
            return null;
        }
        ArrayList arrayList = new ArrayList(args.length);
        for (Object obj : args) {
            arrayList.add(LunoValue.INSTANCE.fromKotlin(obj));
        }
        LunoValue lunoValueCall = new LunoValue.BoundMethod2(this.lunoInstance, lunoFunctionFindMethod).call(this.interpreter, arrayList, new Token(TokenType.EOF, "", null, -1, 0));
        if (Intrinsics.areEqual(method.getReturnType(), Void.TYPE)) {
            return null;
        }
        Interpreter interpreter = this.interpreter;
        Class<?> returnType = method.getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
        return interpreter.lunoValueToKotlin(lunoValueCall, returnType);
    }
}
