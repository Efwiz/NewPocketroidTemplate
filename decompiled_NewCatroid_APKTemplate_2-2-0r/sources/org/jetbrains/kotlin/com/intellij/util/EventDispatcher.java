package org.jetbrains.kotlin.com.intellij.util;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.util.containers.DisposableWrapperList;
import org.jetbrains.kotlin.com.intellij.util.lang.CompoundRuntimeException;

/* JADX INFO: loaded from: classes3.dex */
public final class EventDispatcher<T extends EventListener> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) EventDispatcher.class);
    private final Class<T> myListenerClass;
    private final DisposableWrapperList<T> myListeners;
    private final Map<String, Object> myMethodReturnValues;
    private T myMulticaster;

    /* JADX WARN: Code duplicated, block: B:28:0x0051  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 10 || i == 11 || i == 20) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 10 || i == 11 || i == 20) ? 2 : 3];
        if (i == 2 || i == 3) {
            objArr[0] = "methodReturnValues";
        } else if (i != 7) {
            switch (i) {
                case 9:
                case 12:
                    objArr[0] = "listeners";
                    break;
                case 10:
                case 11:
                case 20:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/util/EventDispatcher";
                    break;
                case 13:
                    objArr[0] = "method";
                    break;
                case 14:
                    objArr[0] = "e";
                    break;
                case 15:
                    objArr[0] = "exceptions";
                    break;
                case 16:
                case 17:
                case 19:
                    objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                    break;
                case 18:
                    objArr[0] = "parentDisposable";
                    break;
                case 21:
                    objArr[0] = "disposable";
                    break;
                default:
                    objArr[0] = "listenerClass";
                    break;
            }
        } else {
            objArr[0] = "listeners";
        }
        if (i == 10) {
            objArr[1] = "createMulticaster";
        } else if (i == 11) {
            objArr[1] = "getMulticaster";
        } else if (i != 20) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/EventDispatcher";
        } else {
            objArr[1] = "getListeners";
        }
        switch (i) {
            case 3:
            case 4:
                objArr[2] = "assertNonVoidMethodReturnValuesAreDeclared";
                break;
            case 5:
                objArr[2] = "<init>";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = "createMulticaster";
                break;
            case 10:
            case 11:
            case 20:
                break;
            case 12:
            case 13:
                objArr[2] = "dispatchVoidMethod";
                break;
            case 14:
                objArr[2] = "handleException";
                break;
            case 15:
                objArr[2] = "throwExceptions";
                break;
            case 16:
            case 17:
            case 18:
                objArr[2] = "addListener";
                break;
            case 19:
                objArr[2] = "removeListener";
                break;
            case 21:
                objArr[2] = "neuterMultiCasterWhilePerformanceTestIsRunningUntil";
                break;
            default:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 10 && i != 11 && i != 20) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static <T extends EventListener> EventDispatcher<T> create(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(0);
        }
        return new EventDispatcher<>(cls, null);
    }

    private EventDispatcher(Class<T> cls, Map<String, Object> map) {
        if (cls == null) {
            $$$reportNull$$$0(5);
        }
        this.myListeners = new DisposableWrapperList<>();
        this.myListenerClass = cls;
        this.myMethodReturnValues = map;
    }

    static <T> T createMulticaster(Class<T> cls, final Map<String, Object> map, final Supplier<? extends Iterable<T>> supplier) {
        if (cls == null) {
            $$$reportNull$$$0(8);
        }
        if (supplier == null) {
            $$$reportNull$$$0(9);
        }
        LOG.assertTrue(cls.isInterface(), "listenerClass must be an interface");
        T t = (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new InvocationHandler() { // from class: org.jetbrains.kotlin.com.intellij.util.EventDispatcher$$ExternalSyntheticLambda0
            @Override // java.lang.reflect.InvocationHandler
            public final Object invoke(Object obj, Method method, Object[] objArr) {
                return EventDispatcher.lambda$createMulticaster$1(map, supplier, obj, method, objArr);
            }
        });
        if (t == null) {
            $$$reportNull$$$0(10);
        }
        return t;
    }

    static /* synthetic */ Object lambda$createMulticaster$1(Map map, Supplier supplier, Object obj, Method method, Object[] objArr) throws Throwable {
        String name2 = method.getName();
        if (method.getDeclaringClass().getName().equals(CommonClassNames.JAVA_LANG_OBJECT)) {
            return handleObjectMethod(obj, objArr, name2);
        }
        if (map != null && map.containsKey(name2)) {
            return map.get(name2);
        }
        dispatchVoidMethod((Iterable) supplier.get(), method, objArr);
        return null;
    }

    public static Object handleObjectMethod(Object obj, Object[] objArr, String str) {
        str.hashCode();
        switch (str) {
            case "toString":
                return "Multicaster";
            case "equals":
                return obj == objArr[0] ? Boolean.TRUE : Boolean.FALSE;
            case "hashCode":
                return Integer.valueOf(System.identityHashCode(obj));
            default:
                LOG.error("Incorrect Object's method invoked for proxy:" + str);
                return null;
        }
    }

    public T getMulticaster() {
        T t = this.myMulticaster;
        if (t == null) {
            t = (T) createMulticaster(this.myListenerClass, this.myMethodReturnValues, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.util.EventDispatcher$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m6703xc4cd89be();
                }
            });
            this.myMulticaster = t;
        }
        if (t == null) {
            $$$reportNull$$$0(11);
        }
        return t;
    }

    /* JADX INFO: renamed from: lambda$getMulticaster$2$org-jetbrains-kotlin-com-intellij-util-EventDispatcher, reason: not valid java name */
    /* synthetic */ Iterable m6703xc4cd89be() {
        return this.myListeners;
    }

    private static <T> void dispatchVoidMethod(Iterable<? extends T> iterable, Method method, Object[] objArr) {
        if (iterable == null) {
            $$$reportNull$$$0(12);
        }
        if (method == null) {
            $$$reportNull$$$0(13);
        }
        method.setAccessible(true);
        Iterator<? extends T> it = iterable.iterator();
        List<Throwable> listHandleException = null;
        while (it.hasNext()) {
            try {
                method.invoke(it.next(), objArr);
            } catch (Throwable th) {
                listHandleException = handleException(th, listHandleException);
            }
        }
        if (listHandleException != null) {
            throwExceptions(listHandleException);
        }
    }

    public static List<Throwable> handleException(Throwable th, List<Throwable> list) {
        Throwable cause;
        if (th == null) {
            $$$reportNull$$$0(14);
        }
        if ((th instanceof InvocationTargetException) && (cause = th.getCause()) != null) {
            if (cause instanceof AbstractMethodError) {
                return list;
            }
            th = cause;
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(th);
        return list;
    }

    public static void throwExceptions(List<? extends Throwable> list) {
        if (list == null) {
            $$$reportNull$$$0(15);
        }
        if (list.size() == 1) {
            ExceptionUtil.rethrow(list.get(0));
            return;
        }
        for (Throwable th : list) {
            if (th instanceof ProcessCanceledException) {
                throw ((ProcessCanceledException) th);
            }
        }
        throw new CompoundRuntimeException(list);
    }

    public void addListener(T t) {
        if (t == null) {
            $$$reportNull$$$0(16);
        }
        this.myListeners.add(t);
    }

    public void removeListener(T t) {
        if (t == null) {
            $$$reportNull$$$0(19);
        }
        this.myListeners.remove(t);
    }
}
