package org.jetbrains.kotlin.cli.common.repl;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.script.Invocable;
import javax.script.ScriptException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.full.KClasses;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: KotlinJsr223JvmInvocableScriptEngine.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J)\u0010\u0006\u001a\u0004\u0018\u0001H\u0007\"\b\b\u0000\u0010\u0007*\u00020\b2\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\nH\u0016¢\u0006\u0002\u0010\u000bJ3\u0010\u0006\u001a\u0004\u0018\u0001H\u0007\"\b\b\u0000\u0010\u0007*\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\b2\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\nH\u0016¢\u0006\u0002\u0010\rJ1\u0010\u000e\u001a\u0004\u0018\u00010\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0016\u0010\u0011\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\b0\u0012\"\u0004\u0018\u00010\bH\u0016¢\u0006\u0002\u0010\u0013J7\u0010\u0014\u001a\u0004\u0018\u00010\b2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010\u000f\u001a\u00020\u00102\u0010\u0010\u0011\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\b0\u0012H\u0002¢\u0006\u0002\u0010\u0018J;\u0010\u0019\u001a\u0004\u0018\u00010\b2\b\u0010\f\u001a\u0004\u0018\u00010\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0016\u0010\u0011\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\b0\u0012\"\u0004\u0018\u00010\bH\u0016¢\u0006\u0002\u0010\u001aJ&\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\f\u0010\u001c\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\bH\u0002J3\u0010\u001f\u001a\u0004\u0018\u0001H\u0007\"\b\b\u0000\u0010\u0007*\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\b2\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\nH\u0002¢\u0006\u0002\u0010\rR\u0016\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006 À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/KotlinJsr223JvmInvocableScriptEngine;", "Ljavax/script/Invocable;", "state", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "getState", "()Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "getInterface", ExifInterface.GPS_DIRECTION_TRUE, "", "clasz", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/Object;", "thiz", "(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;", "invokeFunction", "name", "", "args", "", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;", "invokeImpl", "prioritizedCallOrder", "", "Lorg/jetbrains/kotlin/cli/common/repl/EvalClassWithInstanceAndLoader;", "(Ljava/util/List;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;", "invokeMethod", "(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;", "prioritizedHistory", "receiverClass", "Lkotlin/reflect/KClass;", "receiverInstance", "proxyInterface", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinJsr223JvmInvocableScriptEngine extends Invocable {
    IReplStageState<?> getState();

    private default List<EvalClassWithInstanceAndLoader> prioritizedHistory(KClass<?> receiverClass, Object receiverInstance) {
        Object next;
        IReplStageHistory<EvalClassWithInstanceAndLoader> history = ((GenericReplEvaluatorState) getState().asState(GenericReplEvaluatorState.class)).getHistory();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(history, 10));
        Iterator<EvalClassWithInstanceAndLoader> it = history.iterator();
        while (it.hasNext()) {
            arrayList.add((EvalClassWithInstanceAndLoader) ((ReplHistoryRecord) it.next()).getItem());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (((EvalClassWithInstanceAndLoader) obj).getInstance() != null) {
                arrayList2.add(obj);
            }
        }
        List<EvalClassWithInstanceAndLoader> listEnsureNotEmpty = ReplUtilKt.ensureNotEmpty(CollectionsKt.reversed(arrayList2), "no script ");
        if (receiverInstance == null) {
            return listEnsureNotEmpty;
        }
        if (receiverClass == null) {
            receiverClass = Reflection.getOrCreateKotlinClass(receiverInstance.getClass());
        }
        List<EvalClassWithInstanceAndLoader> list = listEnsureNotEmpty;
        Iterator<T> it2 = list.iterator();
        do {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
        } while (!Intrinsics.areEqual(((EvalClassWithInstanceAndLoader) next).getInstance(), receiverInstance));
        EvalClassWithInstanceAndLoader evalClassWithInstanceAndLoader = (EvalClassWithInstanceAndLoader) next;
        if (evalClassWithInstanceAndLoader == null) {
            ClassLoader classLoader = JvmClassMappingKt.getJavaClass((KClass) receiverClass).getClassLoader();
            Intrinsics.checkNotNullExpressionValue(classLoader, "getClassLoader(...)");
            evalClassWithInstanceAndLoader = new EvalClassWithInstanceAndLoader(receiverClass, receiverInstance, classLoader, ((EvalClassWithInstanceAndLoader) CollectionsKt.first((List) listEnsureNotEmpty)).getInvokeWrapper());
        }
        List listListOf = CollectionsKt.listOf(evalClassWithInstanceAndLoader);
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : list) {
            if (!Intrinsics.areEqual((EvalClassWithInstanceAndLoader) obj2, evalClassWithInstanceAndLoader)) {
                arrayList3.add(obj2);
            }
        }
        return CollectionsKt.plus((Collection) listListOf, (Iterable) arrayList3);
    }

    default Object invokeFunction(String name2, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (name2 == null) {
            throw new NullPointerException("function name cannot be null");
        }
        return invokeImpl(prioritizedHistory(null, null), name2, args);
    }

    default Object invokeMethod(Object thiz, String name2, Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        if (name2 == null) {
            throw new NullPointerException("method name cannot be null");
        }
        if (thiz == null) {
            throw new IllegalArgumentException("cannot invoke method on the null object");
        }
        return invokeImpl(prioritizedHistory(Reflection.getOrCreateKotlinClass(thiz.getClass()), thiz), name2, args);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.script.ScriptException */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v5, types: [kotlin.reflect.KFunction] */
    /* JADX WARN: Type inference failed for: r3v6, types: [kotlin.reflect.KFunction] */
    private default Object invokeImpl(List<EvalClassWithInstanceAndLoader> prioritizedCallOrder, final String name2, final Object[] args) throws NoSuchMethodException, ScriptException {
        Object objCallBy;
        Triple triple = (Triple) SequencesKt.firstOrNull(SequencesKt.filterNotNull(SequencesKt.map(CollectionsKt.asSequence(prioritizedCallOrder), new Function1<EvalClassWithInstanceAndLoader, Triple<? extends KFunction<?>, ? extends Map<KParameter, ? extends Object>, ? extends InvokeWrapper>>() { // from class: org.jetbrains.kotlin.cli.common.repl.KotlinJsr223JvmInvocableScriptEngine.invokeImpl.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Triple<KFunction<?>, Map<KParameter, Object>, InvokeWrapper> invoke(EvalClassWithInstanceAndLoader evalClassWithInstanceAndLoader) {
                Intrinsics.checkNotNullParameter(evalClassWithInstanceAndLoader, "<name for destructuring parameter 0>");
                KClass<?> kClassComponent1 = evalClassWithInstanceAndLoader.component1();
                Object evalClassWithInstanceAndLoader2 = evalClassWithInstanceAndLoader.getInstance();
                InvokeWrapper invokeWrapper = evalClassWithInstanceAndLoader.getInvokeWrapper();
                Collection<KFunction<?>> functions = KClasses.getFunctions(kClassComponent1);
                String str = name2;
                ArrayList arrayList = new ArrayList();
                for (Object obj : functions) {
                    if (Intrinsics.areEqual(((KFunction) obj).getName(), str)) {
                        arrayList.add(obj);
                    }
                }
                Pair pairFindMapping = KotlinJsr223JvmInvocableScriptEngineKt.findMapping(arrayList, CollectionsKt.plus((Collection) CollectionsKt.listOf(evalClassWithInstanceAndLoader2), args));
                if (pairFindMapping != null) {
                    return new Triple<>(pairFindMapping.getFirst(), pairFindMapping.getSecond(), invokeWrapper);
                }
                return null;
            }
        })));
        if (triple != null) {
            final ?? AreEqual = (KFunction) triple.component1();
            final Map map = (Map) triple.component2();
            InvokeWrapper invokeWrapper = (InvokeWrapper) triple.component3();
            try {
                if (invokeWrapper != null) {
                    objCallBy = invokeWrapper.invoke(new Function0<Object>() { // from class: org.jetbrains.kotlin.cli.common.repl.KotlinJsr223JvmInvocableScriptEngine$invokeImpl$res$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return AreEqual.callBy(map);
                        }
                    });
                } else {
                    objCallBy = AreEqual.callBy(map);
                }
                AreEqual = Intrinsics.areEqual(AreEqual.getReturnType().getClassifier(), Reflection.getOrCreateKotlinClass(Unit.class));
                return AreEqual != 0 ? Unit.INSTANCE : objCallBy;
            } catch (Throwable th) {
                Throwable cause = th.getCause();
                Intrinsics.checkNotNull(cause);
                throw new ScriptException(ReplUtilKt.renderReplStackTrace(cause, AreEqual.getName()));
            }
        }
        throw new NoSuchMethodException("no suitable function '" + name2 + "' found");
    }

    default <T> T getInterface(Class<T> clasz) {
        return (T) proxyInterface(null, clasz);
    }

    default <T> T getInterface(Object thiz, Class<T> clasz) {
        if (thiz == null) {
            throw new IllegalArgumentException("object cannot be null");
        }
        return (T) proxyInterface(thiz, clasz);
    }

    private default <T> T proxyInterface(Object thiz, Class<T> clasz) {
        Class<?> cls;
        if (getState().getHistory().size() == 0) {
            throw new IllegalStateException("no script");
        }
        final List<EvalClassWithInstanceAndLoader> listPrioritizedHistory = prioritizedHistory((thiz == null || (cls = thiz.getClass()) == null) ? null : JvmClassMappingKt.getKotlinClass(cls), thiz);
        if (clasz == null) {
            throw new IllegalArgumentException("class object cannot be null");
        }
        if (!clasz.isInterface()) {
            throw new IllegalArgumentException("expecting interface");
        }
        return (T) KClasses.safeCast(JvmClassMappingKt.getKotlinClass(clasz), Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{clasz}, new InvocationHandler() { // from class: org.jetbrains.kotlin.cli.common.repl.KotlinJsr223JvmInvocableScriptEngine$$ExternalSyntheticLambda0
            @Override // java.lang.reflect.InvocationHandler
            public final Object invoke(Object obj, Method method, Object[] objArr) {
                return KotlinJsr223JvmInvocableScriptEngine.proxyInterface$lambda$5(this.f$0, listPrioritizedHistory, obj, method, objArr);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    static Object proxyInterface$lambda$5(KotlinJsr223JvmInvocableScriptEngine this$0, List priority, Object obj, Method method, Object[] objArr) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(priority, "$priority");
        String name2 = method.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (objArr == null) {
            objArr = new Object[0];
        }
        return this$0.invokeImpl(priority, name2, objArr);
    }
}
