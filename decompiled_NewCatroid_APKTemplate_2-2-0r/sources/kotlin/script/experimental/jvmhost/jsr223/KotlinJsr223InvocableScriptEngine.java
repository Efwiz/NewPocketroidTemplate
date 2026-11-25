package kotlin.script.experimental.jvmhost.jsr223;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.script.Invocable;
import javax.script.ScriptException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.full.KClasses;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.cli.common.repl.InvokeWrapper;
import org.jetbrains.kotlin.cli.common.repl.ReplUtilKt;

/* JADX INFO: compiled from: KotlinJsr223InvocableScriptEngine.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\t\bf\u0018\u00002\u00020\u0001J)\u0010\u000f\u001a\u0004\u0018\u0001H\u0010\"\b\b\u0000\u0010\u0010*\u00020\u00042\u000e\u0010\u0011\u001a\n\u0012\u0004\u0012\u0002H\u0010\u0018\u00010\u0012H\u0016¢\u0006\u0002\u0010\u0013J3\u0010\u000f\u001a\u0004\u0018\u0001H\u0010\"\b\b\u0000\u0010\u0010*\u00020\u00042\b\u0010\u0014\u001a\u0004\u0018\u00010\u00042\u000e\u0010\u0011\u001a\n\u0012\u0004\u0012\u0002H\u0010\u0018\u00010\u0012H\u0016¢\u0006\u0002\u0010\u0015J1\u0010\u0016\u001a\u0004\u0018\u00010\u00042\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0016\u0010\u0019\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00040\u001a\"\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\u001bJ7\u0010\u001c\u001a\u0004\u0018\u00010\u00042\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0017\u001a\u00020\u00182\u0010\u0010\u0019\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00040\u001aH\u0002¢\u0006\u0002\u0010\u001eJ;\u0010\u001f\u001a\u0004\u0018\u00010\u00042\b\u0010\u0014\u001a\u0004\u0018\u00010\u00042\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0016\u0010\u0019\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00040\u001a\"\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010 J7\u0010!\u001a\u0004\u0018\u0001H\u0010\"\b\b\u0000\u0010\u0010*\u00020\u00042\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\u0010\u0011\u001a\n\u0012\u0004\u0012\u0002H\u0010\u0018\u00010\u0012H\u0002¢\u0006\u0002\u0010\"R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u0004\u0018\u00010\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006#À\u0006\u0001"}, d2 = {"Lkotlin/script/experimental/jvmhost/jsr223/KotlinJsr223InvocableScriptEngine;", "Ljavax/script/Invocable;", "backwardInstancesHistory", "Lkotlin/sequences/Sequence;", "", "getBackwardInstancesHistory", "()Lkotlin/sequences/Sequence;", "baseClassLoader", "Ljava/lang/ClassLoader;", "getBaseClassLoader", "()Ljava/lang/ClassLoader;", "invokeWrapper", "Lorg/jetbrains/kotlin/cli/common/repl/InvokeWrapper;", "getInvokeWrapper", "()Lorg/jetbrains/kotlin/cli/common/repl/InvokeWrapper;", "getInterface", ExifInterface.GPS_DIRECTION_TRUE, "clasz", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/Object;", "thiz", "(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;", "invokeFunction", "name", "", "args", "", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;", "invokeImpl", "possibleReceivers", "(Lkotlin/sequences/Sequence;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;", "invokeMethod", "(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;", "proxyInterface", "(Lkotlin/sequences/Sequence;Ljava/lang/Class;)Ljava/lang/Object;", "kotlin-scripting-jvm-host-unshaded"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinJsr223InvocableScriptEngine extends Invocable {
    Sequence<Object> getBackwardInstancesHistory();

    ClassLoader getBaseClassLoader();

    InvokeWrapper getInvokeWrapper();

    default Object invokeFunction(String name2, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (name2 == null) {
            throw new AssertionError("function name cannot be null");
        }
        return invokeImpl(getBackwardInstancesHistory(), name2, args);
    }

    default Object invokeMethod(Object thiz, String name2, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (name2 == null) {
            throw new NullPointerException("method name cannot be null");
        }
        if (thiz == null) {
            throw new IllegalArgumentException("cannot invoke method on the null object");
        }
        return invokeImpl(SequencesKt.sequenceOf(thiz), name2, args);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.script.ScriptException */
    private default Object invokeImpl(Sequence<? extends Object> possibleReceivers, final String name2, final Object[] args) throws NoSuchMethodException, ScriptException {
        Object objCallBy;
        Pair pair = (Pair) SequencesKt.firstOrNull(SequencesKt.filterNotNull(SequencesKt.mapNotNull(possibleReceivers, new Function1<Object, Pair<? extends KFunction<?>, ? extends Map<KParameter, ? extends Object>>>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223InvocableScriptEngine.invokeImpl.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Pair<? extends KFunction<?>, ? extends Map<KParameter, ? extends Object>> invoke(Object instance) {
                Intrinsics.checkNotNullParameter(instance, "instance");
                Collection<KFunction<?>> functions = KClasses.getFunctions(Reflection.getOrCreateKotlinClass(instance.getClass()));
                String str = name2;
                ArrayList arrayList = new ArrayList();
                for (Object obj : functions) {
                    if (Intrinsics.areEqual(((KFunction) obj).getName(), str)) {
                        arrayList.add(obj);
                    }
                }
                return KotlinJsr223InvocableScriptEngineKt.findMapping(arrayList, CollectionsKt.plus((Collection) CollectionsKt.listOf(instance), args));
            }
        })));
        if (pair != null) {
            final KFunction kFunction = (KFunction) pair.component1();
            final Map<KParameter, ? extends Object> map = (Map) pair.component2();
            try {
                if (getInvokeWrapper() != null) {
                    InvokeWrapper invokeWrapper = getInvokeWrapper();
                    Intrinsics.checkNotNull(invokeWrapper);
                    objCallBy = invokeWrapper.invoke(new Function0<Object>() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223InvocableScriptEngine$invokeImpl$res$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return kFunction.callBy(map);
                        }
                    });
                } else {
                    objCallBy = kFunction.callBy(map);
                }
                return Intrinsics.areEqual(kFunction.getReturnType().getClassifier(), Reflection.getOrCreateKotlinClass(Unit.class)) ? Unit.INSTANCE : objCallBy;
            } catch (Throwable th) {
                Throwable cause = th.getCause();
                Intrinsics.checkNotNull(cause);
                throw new ScriptException(ReplUtilKt.renderReplStackTrace(cause, kFunction.getName()));
            }
        }
        throw new NoSuchMethodException("no suitable function '" + name2 + "' found");
    }

    default <T> T getInterface(Class<T> clasz) {
        return (T) proxyInterface(getBackwardInstancesHistory(), clasz);
    }

    default <T> T getInterface(Object thiz, Class<T> clasz) {
        if (thiz == null) {
            throw new IllegalArgumentException("object cannot be null");
        }
        return (T) proxyInterface(SequencesKt.sequenceOf(thiz), clasz);
    }

    private default <T> T proxyInterface(final Sequence<? extends Object> possibleReceivers, Class<T> clasz) {
        if (clasz == null) {
            throw new IllegalArgumentException("class object cannot be null");
        }
        if (!clasz.isInterface()) {
            throw new IllegalArgumentException("expecting interface");
        }
        return (T) KClasses.safeCast(JvmClassMappingKt.getKotlinClass(clasz), Proxy.newProxyInstance(getBaseClassLoader(), new Class[]{clasz}, new InvocationHandler() { // from class: kotlin.script.experimental.jvmhost.jsr223.KotlinJsr223InvocableScriptEngine$$ExternalSyntheticLambda0
            @Override // java.lang.reflect.InvocationHandler
            public final Object invoke(Object obj, Method method, Object[] objArr) {
                return KotlinJsr223InvocableScriptEngine.proxyInterface$lambda$0(this.f$0, possibleReceivers, obj, method, objArr);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    static Object proxyInterface$lambda$0(KotlinJsr223InvocableScriptEngine this$0, Sequence possibleReceivers, Object obj, Method method, Object[] objArr) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(possibleReceivers, "$possibleReceivers");
        String name2 = method.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (objArr == null) {
            objArr = new Object[0];
        }
        return this$0.invokeImpl(possibleReceivers, name2, objArr);
    }
}
