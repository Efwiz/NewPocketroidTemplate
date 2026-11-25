package org.jetbrains.kotlin.com.intellij.openapi.extensions.impl;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.diagnostic.StartUpMeasurer;
import org.jetbrains.kotlin.com.intellij.diagnostic.ThreadDumper;
import org.jetbrains.kotlin.com.intellij.ide.plugins.cl.PluginAwareClassLoader;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointAdapter;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointAndAreaListener;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointListener;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointPriorityListener;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.LoadingOrder;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.util.EmptyRunnable;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistryImpl;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.ThreeState;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ExtensionPointImpl<T> implements ExtensionPoint<T>, Iterable<T> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static Set<ExtensionPointImpl<?>> POINTS_IN_READONLY_MODE;
    private volatile List<ExtensionComponentAdapter> adapters;
    private volatile boolean adaptersAreSorted;
    private volatile List<T> cachedExtensions;
    private volatile T[] cachedExtensionsAsArray;
    private final String className;
    private final ComponentManager componentManager;
    private Class<T> extensionClass;
    private final boolean isDynamic;
    private final AtomicReference<ConcurrentMap<?, Map<?, ?>>> keyMapperToCacheRef;
    private ExtensionPointListener<T>[] listeners;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f715name;
    protected final PluginDescriptor pluginDescriptor;
    static final Logger LOG = Logger.getInstance((Class<?>) ExtensionPointImpl.class);
    private static final ArrayFactory<ExtensionPointListener<?>> LISTENER_ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl$$ExternalSyntheticLambda4
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return ExtensionPointImpl.lambda$static$0(i);
        }
    };
    private static Runnable CHECK_CANCELED = EmptyRunnable.getInstance();

    /* JADX WARN: Code duplicated, block: B:13:0x0022 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 4 && i != 5 && i != 6 && i != 14 && i != 23 && i != 61 && i != 66) {
            switch (i) {
                default:
                    switch (i) {
                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                        case 40:
                        case 41:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 27:
                case 28:
                case 29:
                case 30:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 4 && i != 5 && i != 6 && i != 14 && i != 23 && i != 61 && i != 66) {
            switch (i) {
                case 27:
                case 28:
                case 29:
                case 30:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                        case 40:
                        case 41:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "className";
                break;
            case 2:
            case 12:
            case 20:
            case 69:
                objArr[0] = "pluginDescriptor";
                break;
            case 3:
                objArr[0] = "componentManager";
                break;
            case 4:
            case 5:
            case 6:
            case 14:
            case 23:
            case 27:
            case 28:
            case 29:
            case 30:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 61:
            case 66:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/ExtensionPointImpl";
                break;
            case 7:
            case 9:
            case 11:
            case 15:
            case 18:
            case 25:
            case 52:
                objArr[0] = SchemaSymbols.ATTVAL_EXTENSION;
                break;
            case 8:
            case 16:
            case 19:
                objArr[0] = "order";
                break;
            case 10:
            case 13:
            case 17:
            case 48:
                objArr[0] = "parentDisposable";
                break;
            case 21:
            case 22:
            case 49:
                objArr[0] = "extensions";
                break;
            case 24:
            case 45:
            case 58:
                objArr[0] = "adapters";
                break;
            case 26:
            case 53:
                objArr[0] = "extensionClass";
                break;
            case 31:
            case 32:
            case 33:
            case 34:
                objArr[0] = "consumer";
                break;
            case 42:
            case 43:
            case 46:
            case 67:
                objArr[0] = "adapter";
                break;
            case 44:
                objArr[0] = "extensionClassForCheck";
                break;
            case 47:
                objArr[0] = "newList";
                break;
            case 50:
            case 59:
                objArr[0] = "listeners";
                break;
            case 51:
                objArr[0] = "filter";
                break;
            case 54:
            case 55:
                objArr[0] = "extensionClassFilter";
                break;
            case 56:
                objArr[0] = "priorityListenerCallbacks";
                break;
            case 57:
                objArr[0] = "listenerCallbacks";
                break;
            case 60:
            case 62:
            case 63:
            case 64:
            case 65:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
            case 68:
                objArr[0] = "extensionElements";
                break;
            case 70:
                objArr[0] = "oldArea";
                break;
            case 71:
            case 73:
                objArr[0] = "aClass";
                break;
            case 72:
                objArr[0] = "strictMatch";
                break;
            case 74:
                objArr[0] = "checkCanceled";
                break;
            case 75:
                objArr[0] = "trace";
                break;
            default:
                objArr[0] = "name";
                break;
        }
        if (i == 4) {
            objArr[1] = "getCacheMap";
        } else if (i == 5) {
            objArr[1] = "getName";
        } else if (i == 6) {
            objArr[1] = "getClassName";
        } else if (i == 14) {
            objArr[1] = "getPluginDescriptor";
        } else if (i == 23) {
            objArr[1] = "doRegisterExtensions";
        } else if (i == 61) {
            objArr[1] = "listenerArrayFactory";
        } else if (i != 66) {
            switch (i) {
                case 27:
                    objArr[1] = "getExtensionList";
                    break;
                case 28:
                    objArr[1] = "computeExtensionList";
                    break;
                case 29:
                    objArr[1] = "getExtensions";
                    break;
                case 30:
                    objArr[1] = "iterator";
                    break;
                default:
                    switch (i) {
                        case 35:
                            objArr[1] = "createIterator";
                            break;
                        case 36:
                            objArr[1] = "extensions";
                            break;
                        case 37:
                        case 38:
                        case 39:
                            objArr[1] = "getSortedAdapters";
                            break;
                        case 40:
                        case 41:
                            objArr[1] = "processAdapters";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/ExtensionPointImpl";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getExtensionClass";
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 14:
            case 23:
            case 27:
            case 28:
            case 29:
            case 30:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 61:
            case 66:
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
                objArr[2] = "registerExtension";
                break;
            case 18:
            case 19:
            case 20:
                objArr[2] = "doRegisterExtension";
                break;
            case 21:
            case 68:
            case 69:
                objArr[2] = "registerExtensions";
                break;
            case 22:
                objArr[2] = "doRegisterExtensions";
                break;
            case 24:
                objArr[2] = "findInsertionIndexForAnyOrder";
                break;
            case 25:
            case 26:
                objArr[2] = "checkExtensionType";
                break;
            case 31:
                objArr[2] = "processWithPluginDescriptor";
                break;
            case 32:
                objArr[2] = "processImplementations";
                break;
            case 33:
                objArr[2] = "checkImplementations";
                break;
            case 34:
                objArr[2] = "processIdentifiableImplementations";
                break;
            case 42:
            case 43:
            case 44:
            case 45:
                objArr[2] = "processAdapter";
                break;
            case 46:
                objArr[2] = "checkThatClassloaderIsActive";
                break;
            case 47:
            case 48:
                objArr[2] = "maskAll";
                break;
            case 49:
            case 50:
                objArr[2] = "doNotifyListeners";
                break;
            case 51:
            case 54:
            case 55:
            case 56:
            case 57:
                objArr[2] = "unregisterExtensions";
                break;
            case 52:
            case 53:
                objArr[2] = "unregisterExtension";
                break;
            case 58:
            case 59:
                objArr[2] = "notifyListeners";
                break;
            case 60:
            case 63:
                objArr[2] = "addExtensionPointListener";
                break;
            case 62:
                objArr[2] = "addListener";
                break;
            case 64:
                objArr[2] = "addChangeListener";
                break;
            case 65:
                objArr[2] = "removeExtensionPointListener";
                break;
            case 67:
                objArr[2] = "addExtensionAdapter";
                break;
            case 70:
                objArr[2] = "notifyAreaReplaced";
                break;
            case 71:
            case 72:
                objArr[2] = "findExtension";
                break;
            case 73:
                objArr[2] = "findExtensionByExactClass";
                break;
            case 74:
                objArr[2] = "setCheckCanceledAction";
                break;
            case 75:
                objArr[2] = "isInsideClassInitializer";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 6 && i != 14 && i != 23 && i != 61 && i != 66) {
            switch (i) {
                case 27:
                case 28:
                case 29:
                case 30:
                    break;
                default:
                    switch (i) {
                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                        case 40:
                        case 41:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    abstract ExtensionComponentAdapter createAdapter(ExtensionDescriptor extensionDescriptor, PluginDescriptor pluginDescriptor, ComponentManager componentManager);

    static /* synthetic */ ExtensionPointListener[] lambda$static$0(int i) {
        return i == 0 ? ExtensionPointListener.emptyArray() : new ExtensionPointListener[i];
    }

    ExtensionPointImpl(String str, String str2, PluginDescriptor pluginDescriptor, ComponentManager componentManager, Class<T> cls, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (str2 == null) {
            $$$reportNull$$$0(1);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        if (componentManager == null) {
            $$$reportNull$$$0(3);
        }
        this.adapters = Collections.emptyList();
        this.adaptersAreSorted = true;
        this.listeners = ExtensionPointListener.emptyArray();
        this.keyMapperToCacheRef = new AtomicReference<>();
        this.f715name = str;
        this.className = str2;
        this.pluginDescriptor = pluginDescriptor;
        this.componentManager = componentManager;
        this.extensionClass = cls;
        this.isDynamic = z;
    }

    public final String getName() {
        String str = this.f715name;
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return str;
    }

    public final String getClassName() {
        String str = this.className;
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint
    public final void registerExtension(T t, LoadingOrder loadingOrder) {
        if (t == null) {
            $$$reportNull$$$0(7);
        }
        if (loadingOrder == null) {
            $$$reportNull$$$0(8);
        }
        doRegisterExtension(t, loadingOrder, getPluginDescriptor(), null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint
    public final void registerExtension(T t, Disposable disposable) {
        if (t == null) {
            $$$reportNull$$$0(9);
        }
        if (disposable == null) {
            $$$reportNull$$$0(10);
        }
        registerExtension(t, getPluginDescriptor(), disposable);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint
    public final void registerExtension(T t, PluginDescriptor pluginDescriptor, Disposable disposable) {
        if (t == null) {
            $$$reportNull$$$0(11);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        if (disposable == null) {
            $$$reportNull$$$0(13);
        }
        doRegisterExtension(t, LoadingOrder.ANY, pluginDescriptor, disposable);
    }

    public final PluginDescriptor getPluginDescriptor() {
        PluginDescriptor pluginDescriptor = this.pluginDescriptor;
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        return pluginDescriptor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint
    public final void registerExtension(T t, LoadingOrder loadingOrder, Disposable disposable) {
        if (t == null) {
            $$$reportNull$$$0(15);
        }
        if (loadingOrder == null) {
            $$$reportNull$$$0(16);
        }
        if (disposable == null) {
            $$$reportNull$$$0(17);
        }
        doRegisterExtension(t, loadingOrder, getPluginDescriptor(), disposable);
    }

    public final ComponentManager getComponentManager() {
        return this.componentManager;
    }

    private synchronized void doRegisterExtension(T t, LoadingOrder loadingOrder, PluginDescriptor pluginDescriptor, Disposable disposable) {
        if (t == null) {
            try {
                $$$reportNull$$$0(18);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (loadingOrder == null) {
            $$$reportNull$$$0(19);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(20);
        }
        assertNotReadOnlyMode();
        checkExtensionType(t, getExtensionClass(), null);
        for (ExtensionComponentAdapter extensionComponentAdapter : this.adapters) {
            if ((extensionComponentAdapter instanceof ObjectComponentAdapter) && ((ObjectComponentAdapter) extensionComponentAdapter).componentInstance == t) {
                LOG.error("Extension was already added: " + t);
                return;
            }
        }
        final ObjectComponentAdapter objectComponentAdapter = new ObjectComponentAdapter(t, pluginDescriptor, loadingOrder);
        addExtensionAdapter(objectComponentAdapter);
        notifyListeners(false, Collections.singletonList(objectComponentAdapter), this.listeners);
        if (disposable != null) {
            Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
                public final void dispose() {
                    this.f$0.m6482x7cb8c262(objectComponentAdapter);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$doRegisterExtension$2$org-jetbrains-kotlin-com-intellij-openapi-extensions-impl-ExtensionPointImpl, reason: not valid java name */
    /* synthetic */ void m6482x7cb8c262(ObjectComponentAdapter objectComponentAdapter) {
        synchronized (this) {
            int iIndexOfIdentity = ContainerUtil.indexOfIdentity(this.adapters, objectComponentAdapter);
            if (iIndexOfIdentity < 0) {
                LOG.error("Extension to be removed not found: " + objectComponentAdapter.componentInstance);
            }
            ArrayList arrayList = new ArrayList(this.adapters);
            arrayList.remove(iIndexOfIdentity);
            this.adapters = arrayList;
            clearCache();
            notifyListeners(true, Collections.singletonList(objectComponentAdapter), this.listeners);
        }
    }

    private void checkExtensionType(T t, Class<T> cls, ExtensionComponentAdapter extensionComponentAdapter) {
        if (t == null) {
            $$$reportNull$$$0(25);
        }
        if (cls == null) {
            $$$reportNull$$$0(26);
        }
        if (cls.isInstance(t)) {
            return;
        }
        String str = "Extension " + t.getClass().getName() + " does not implement " + cls;
        if (extensionComponentAdapter == null) {
            throw new RuntimeException(str);
        }
        throw this.componentManager.createError(str + " (adapter=" + extensionComponentAdapter + Constant.AFTER_QUTO, null, extensionComponentAdapter.getPluginDescriptor().getId(), Collections.singletonMap("threadDump", ThreadDumper.dumpThreadsToString()));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint
    public final List<T> getExtensionList() {
        List<T> listComputeExtensionList = this.cachedExtensions;
        if (listComputeExtensionList == null) {
            listComputeExtensionList = computeExtensionList();
        }
        if (listComputeExtensionList == null) {
            $$$reportNull$$$0(27);
        }
        return listComputeExtensionList;
    }

    private synchronized List<T> computeExtensionList() {
        List<T> listEmptyList;
        listEmptyList = this.cachedExtensions;
        if (listEmptyList == null) {
            T[] tArrProcessAdapters = processAdapters();
            this.cachedExtensionsAsArray = tArrProcessAdapters;
            listEmptyList = tArrProcessAdapters.length == 0 ? Collections.emptyList() : ContainerUtil.immutableList(tArrProcessAdapters);
            this.cachedExtensions = listEmptyList;
        }
        if (listEmptyList == null) {
            $$$reportNull$$$0(28);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint
    public final T[] getExtensions() {
        T[] tArrProcessAdapters = this.cachedExtensionsAsArray;
        if (tArrProcessAdapters == null) {
            synchronized (this) {
                tArrProcessAdapters = this.cachedExtensionsAsArray;
                if (tArrProcessAdapters == null) {
                    tArrProcessAdapters = processAdapters();
                    this.cachedExtensionsAsArray = tArrProcessAdapters;
                    this.cachedExtensions = tArrProcessAdapters.length == 0 ? Collections.emptyList() : ContainerUtil.immutableList(tArrProcessAdapters);
                }
            }
        }
        if (tArrProcessAdapters.length != 0) {
            tArrProcessAdapters = (T[]) ((Object[]) tArrProcessAdapters.clone());
        }
        if (tArrProcessAdapters == null) {
            $$$reportNull$$$0(29);
        }
        return tArrProcessAdapters;
    }

    @Override // java.lang.Iterable
    public final Iterator<T> iterator() {
        List<T> list = this.cachedExtensions;
        Iterator<T> itCreateIterator = list == null ? createIterator() : list.iterator();
        if (itCreateIterator == null) {
            $$$reportNull$$$0(30);
        }
        return itCreateIterator;
    }

    private List<ExtensionComponentAdapter> getThreadSafeAdapterList(boolean z) {
        CHECK_CANCELED.run();
        if (!this.isDynamic && this.listeners.length > 0) {
            String str = "Listeners not allowed for extension point " + getName();
            if (z) {
                LOG.error(str);
            } else {
                LOG.warn(str);
                getExtensionList();
            }
        }
        return getSortedAdapters();
    }

    private Iterator<T> createIterator() {
        final List<ExtensionComponentAdapter> threadSafeAdapterList = getThreadSafeAdapterList(true);
        final int size = threadSafeAdapterList.size();
        if (size == 0) {
            Iterator<T> itEmptyIterator = Collections.emptyIterator();
            if (itEmptyIterator == null) {
                $$$reportNull$$$0(35);
            }
            return itEmptyIterator;
        }
        return new Iterator<T>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl.1
            private int currentIndex;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.currentIndex < size;
            }

            @Override // java.util.Iterator
            public T next() {
                do {
                    ExtensionPointImpl extensionPointImpl = ExtensionPointImpl.this;
                    List list = threadSafeAdapterList;
                    int i = this.currentIndex;
                    this.currentIndex = i + 1;
                    T t = (T) extensionPointImpl.processAdapter((ExtensionComponentAdapter) list.get(i));
                    if (t != null) {
                        return t;
                    }
                } while (hasNext());
                return null;
            }
        };
    }

    @Override // java.lang.Iterable
    public final Spliterator<T> spliterator() {
        return Spliterators.spliterator(iterator(), size(), 1281);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint
    public final int size() {
        List list = this.cachedExtensions;
        if (list == null) {
            list = this.adapters;
        }
        return list.size();
    }

    private List<ExtensionComponentAdapter> getSortedAdapters() {
        if (this.adaptersAreSorted) {
            List<ExtensionComponentAdapter> list = this.adapters;
            if (list == null) {
                $$$reportNull$$$0(37);
            }
            return list;
        }
        synchronized (this) {
            if (this.adaptersAreSorted) {
                List<ExtensionComponentAdapter> list2 = this.adapters;
                if (list2 == null) {
                    $$$reportNull$$$0(38);
                }
                return list2;
            }
            if (this.adapters.size() > 1) {
                ArrayList arrayList = new ArrayList(this.adapters);
                LoadingOrder.sort(arrayList);
                this.adapters = arrayList;
            }
            this.adaptersAreSorted = true;
            List<ExtensionComponentAdapter> list3 = this.adapters;
            if (list3 == null) {
                $$$reportNull$$$0(39);
            }
            return list3;
        }
    }

    private T[] processAdapters() {
        assertNotReadOnlyMode();
        CHECK_CANCELED.run();
        long currentTime = StartUpMeasurer.getCurrentTime();
        List<ExtensionComponentAdapter> sortedAdapters = getSortedAdapters();
        int size = sortedAdapters.size();
        Class<T> extensionClass = getExtensionClass();
        T[] tArr = (T[]) ArrayUtil.newArray(extensionClass, size);
        if (size == 0) {
            if (tArr == null) {
                $$$reportNull$$$0(40);
            }
            return tArr;
        }
        Set<T> setCreateSmallMemoryFootprintSet = this instanceof BeanExtensionPoint ? null : CollectionFactory.createSmallMemoryFootprintSet(size);
        ExtensionPointListener<T>[] extensionPointListenerArr = this.listeners;
        int i = 0;
        int i2 = 0;
        while (i2 < sortedAdapters.size()) {
            Set<T> set = setCreateSmallMemoryFootprintSet;
            Set<T> set2 = setCreateSmallMemoryFootprintSet;
            int i3 = i;
            T tProcessAdapter = processAdapter(sortedAdapters.get(i2), extensionPointListenerArr, tArr, set, extensionClass, sortedAdapters);
            if (tProcessAdapter != null) {
                i = i3 + 1;
                tArr[i3] = tProcessAdapter;
            } else {
                i = i3;
            }
            i2++;
            setCreateSmallMemoryFootprintSet = set2;
        }
        int i4 = i;
        if (i4 != tArr.length) {
            tArr = (T[]) Arrays.copyOf(tArr, i4);
        }
        T[] tArr2 = tArr;
        StartUpMeasurer.addCompletedActivity(currentTime, (Class<?>) extensionClass, this.componentManager.getActivityCategory(true), (String) null, StartUpMeasurer.MEASURE_THRESHOLD);
        if (tArr2 == null) {
            $$$reportNull$$$0(41);
        }
        return tArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:12:0x0012 A[DONT_GENERATE] */
    /* JADX WARN: Code duplicated, block: B:14:0x0014 A[Catch: all -> 0x003c, ProcessCanceledException -> 0x0044, TRY_ENTER, TryCatch #3 {ProcessCanceledException -> 0x0044, all -> 0x003c, blocks: (B:10:0x000c, B:14:0x0014, B:16:0x001c, B:18:0x0024), top: B:30:0x000c, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:16:0x001c A[Catch: all -> 0x003c, ProcessCanceledException -> 0x0044, TryCatch #3 {ProcessCanceledException -> 0x0044, all -> 0x003c, blocks: (B:10:0x000c, B:14:0x0014, B:16:0x001c, B:18:0x0024), top: B:30:0x000c, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:18:0x0024 A[Catch: all -> 0x003c, ProcessCanceledException -> 0x0044, TRY_LEAVE, TryCatch #3 {ProcessCanceledException -> 0x0044, all -> 0x003c, blocks: (B:10:0x000c, B:14:0x0014, B:16:0x001c, B:18:0x0024), top: B:30:0x000c, outer: #1 }] */
    /* JADX WARN: Instruction removed from duplicated block: B:12:0x0012, please report this as an issue */
    public synchronized T processAdapter(ExtensionComponentAdapter extensionComponentAdapter) {
        T t;
        Logger logger;
        if (extensionComponentAdapter == null) {
            $$$reportNull$$$0(42);
            try {
                if (!checkThatClassloaderIsActive(extensionComponentAdapter)) {
                    return null;
                }
                t = (T) extensionComponentAdapter.createInstance(this.componentManager);
                if (t == null) {
                    logger = LOG;
                    if (logger.isDebugEnabled()) {
                        logger.debug(extensionComponentAdapter + " not loaded because it reported that not applicable");
                    }
                }
                return t;
            } catch (ProcessCanceledException e) {
                throw e;
            } catch (Throwable th) {
                LOG.error(th);
                return null;
            }
        }
        if (!checkThatClassloaderIsActive(extensionComponentAdapter)) {
            return null;
        }
        t = (T) extensionComponentAdapter.createInstance(this.componentManager);
        if (t == null) {
            logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug(extensionComponentAdapter + " not loaded because it reported that not applicable");
            }
        }
        return t;
        throw th;
    }

    private T processAdapter(ExtensionComponentAdapter extensionComponentAdapter, ExtensionPointListener<T>[] extensionPointListenerArr, T[] tArr, Set<T> set, Class<T> cls, List<? extends ExtensionComponentAdapter> list) {
        if (extensionComponentAdapter == null) {
            $$$reportNull$$$0(43);
        }
        if (cls == null) {
            $$$reportNull$$$0(44);
        }
        if (list == null) {
            $$$reportNull$$$0(45);
        }
        try {
            if (!checkThatClassloaderIsActive(extensionComponentAdapter)) {
                return null;
            }
            boolean z = (extensionPointListenerArr == null || extensionPointListenerArr.length == 0 || extensionComponentAdapter.isInstanceCreated() || this.isDynamic) ? false : true;
            final T t = (T) extensionComponentAdapter.createInstance(this.componentManager);
            if (t == null) {
                Logger logger = LOG;
                if (logger.isDebugEnabled()) {
                    logger.debug(extensionComponentAdapter + " not loaded because it reported that not applicable");
                }
                return null;
            }
            if (set != null && !set.add(t)) {
                LOG.error("Duplicate extension found:\n                   " + t + ";\n  prev extension:  " + ContainerUtil.find(set, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl$$ExternalSyntheticLambda2
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                    public final boolean value(Object obj) {
                        return obj.equals(t);
                    }
                }) + ";\n  adapter:         " + extensionComponentAdapter + ";\n  extension class: " + cls + ";\n  result:          " + Arrays.asList(tArr) + ";\n  adapters:        " + list);
                return null;
            }
            checkExtensionType(t, cls, extensionComponentAdapter);
            if (z) {
                notifyListeners(false, Collections.singletonList(extensionComponentAdapter), extensionPointListenerArr);
            }
            return t;
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            LOG.error(th);
        }
    }

    private static boolean checkThatClassloaderIsActive(ExtensionComponentAdapter extensionComponentAdapter) {
        if (extensionComponentAdapter == null) {
            $$$reportNull$$$0(46);
        }
        Object pluginClassLoader = extensionComponentAdapter.getPluginDescriptor().get_pluginClassLoader();
        if (!(pluginClassLoader instanceof PluginAwareClassLoader) || ((PluginAwareClassLoader) pluginClassLoader).getState() == 1) {
            return true;
        }
        LOG.warn(extensionComponentAdapter + " not loaded because classloader is being unloaded");
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint
    public final void unregisterExtension(Class<? extends T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(53);
        }
        final String canonicalName = cls.getCanonicalName();
        if (unregisterExtensions(new BiPredicate() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return ExtensionPointImpl.lambda$unregisterExtension$9(canonicalName, (String) obj, (ExtensionComponentAdapter) obj2);
            }
        }, true)) {
            return;
        }
        LOG.warn("Extension to be removed not found: " + cls);
    }

    static /* synthetic */ boolean lambda$unregisterExtension$9(String str, String str2, ExtensionComponentAdapter extensionComponentAdapter) {
        return !str2.equals(str);
    }

    public final boolean unregisterExtensions(final BiPredicate<? super String, ? super ExtensionComponentAdapter> biPredicate, boolean z) {
        if (biPredicate == null) {
            $$$reportNull$$$0(54);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        boolean zUnregisterExtensions = unregisterExtensions(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                ExtensionComponentAdapter extensionComponentAdapter = (ExtensionComponentAdapter) obj;
                return biPredicate.test(extensionComponentAdapter.getAssignableToClassName(), extensionComponentAdapter);
            }
        }, z, arrayList2, arrayList);
        Iterator<Runnable> it = arrayList2.iterator();
        while (it.hasNext()) {
            it.next().run();
        }
        Iterator<Runnable> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            it2.next().run();
        }
        return zUnregisterExtensions;
    }

    final synchronized boolean unregisterExtensions(Predicate<? super ExtensionComponentAdapter> predicate, boolean z, List<Runnable> list, List<Runnable> list2) {
        if (predicate == null) {
            try {
                $$$reportNull$$$0(55);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (list == null) {
            $$$reportNull$$$0(56);
        }
        if (list2 == null) {
            $$$reportNull$$$0(57);
        }
        ExtensionPointListener<T>[] extensionPointListenerArr = this.listeners;
        List<ExtensionComponentAdapter> arrayList = this.adapters;
        final ArrayList arrayList2 = null;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ExtensionComponentAdapter extensionComponentAdapter = arrayList.get(size);
            if (!predicate.test(extensionComponentAdapter)) {
                if (arrayList == this.adapters) {
                    arrayList = new ArrayList(arrayList);
                }
                arrayList.remove(size);
                if (extensionPointListenerArr.length != 0) {
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                    }
                    arrayList2.add(extensionComponentAdapter);
                }
                if (z) {
                    break;
                }
            }
        }
        if (arrayList == this.adapters) {
            return false;
        }
        clearCache();
        this.adapters = arrayList;
        if (arrayList2 == null) {
            return true;
        }
        final List listFilter = ContainerUtil.filter(extensionPointListenerArr, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl$$ExternalSyntheticLambda7
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return ExtensionPointImpl.lambda$unregisterExtensions$11((ExtensionPointListener) obj);
            }
        });
        final List listFilter2 = ContainerUtil.filter(extensionPointListenerArr, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl$$ExternalSyntheticLambda8
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return ExtensionPointImpl.lambda$unregisterExtensions$12((ExtensionPointListener) obj);
            }
        });
        if (!listFilter.isEmpty()) {
            list.add(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m6484xb62e368b(arrayList2, listFilter);
                }
            });
        }
        if (!listFilter2.isEmpty()) {
            list2.add(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m6485xcf2f882a(arrayList2, listFilter2);
                }
            });
        }
        return true;
    }

    static /* synthetic */ boolean lambda$unregisterExtensions$11(ExtensionPointListener extensionPointListener) {
        return extensionPointListener instanceof ExtensionPointPriorityListener;
    }

    static /* synthetic */ boolean lambda$unregisterExtensions$12(ExtensionPointListener extensionPointListener) {
        return !(extensionPointListener instanceof ExtensionPointPriorityListener);
    }

    /* JADX INFO: renamed from: lambda$unregisterExtensions$13$org-jetbrains-kotlin-com-intellij-openapi-extensions-impl-ExtensionPointImpl, reason: not valid java name */
    /* synthetic */ void m6484xb62e368b(List list, List list2) {
        notifyListeners(true, list, (ExtensionPointListener[]) list2.toArray(ExtensionPointListener.emptyArray()));
    }

    /* JADX INFO: renamed from: lambda$unregisterExtensions$14$org-jetbrains-kotlin-com-intellij-openapi-extensions-impl-ExtensionPointImpl, reason: not valid java name */
    /* synthetic */ void m6485xcf2f882a(List list, List list2) {
        notifyListeners(true, list, (ExtensionPointListener[]) list2.toArray(ExtensionPointListener.emptyArray()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void notifyListeners(boolean z, List<? extends ExtensionComponentAdapter> list, ExtensionPointListener<T>[] extensionPointListenerArr) {
        if (list == null) {
            $$$reportNull$$$0(58);
        }
        if (extensionPointListenerArr == 0) {
            $$$reportNull$$$0(59);
        }
        for (ReferenceProvidersRegistryImpl.AnonymousClass1 anonymousClass1 : extensionPointListenerArr) {
            if (anonymousClass1 instanceof ExtensionPointAdapter) {
                try {
                    ((ExtensionPointAdapter) anonymousClass1).extensionListChanged();
                } catch (ProcessCanceledException e) {
                    throw e;
                } catch (Throwable th) {
                    LOG.error(th);
                }
            } else {
                for (ExtensionComponentAdapter extensionComponentAdapter : list) {
                    if (!z || extensionComponentAdapter.isInstanceCreated()) {
                        try {
                            Object objCreateInstance = extensionComponentAdapter.createInstance(this.componentManager);
                            if (objCreateInstance != null) {
                                if (z) {
                                    anonymousClass1.extensionRemoved(objCreateInstance, extensionComponentAdapter.getPluginDescriptor());
                                } else {
                                    anonymousClass1.extensionAdded(objCreateInstance, extensionComponentAdapter.getPluginDescriptor());
                                }
                            }
                        } catch (ProcessCanceledException e2) {
                            throw e2;
                        } catch (Throwable th2) {
                            LOG.error(th2);
                        }
                    }
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint
    public final void addExtensionPointListener(final ExtensionPointListener<T> extensionPointListener, boolean z, Disposable disposable) {
        if (extensionPointListener == null) {
            $$$reportNull$$$0(60);
        }
        if (addListener(extensionPointListener)) {
            if (z) {
                notifyListeners(false, getSortedAdapters(), new ExtensionPointListener[]{extensionPointListener});
            }
            if (disposable != null) {
                Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl$$ExternalSyntheticLambda1
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
                    public final void dispose() {
                        this.f$0.m6481xfe024b65(extensionPointListener);
                    }
                });
            }
        }
    }

    private static <T> ArrayFactory<ExtensionPointListener<T>> listenerArrayFactory() {
        ArrayFactory<ExtensionPointListener<T>> arrayFactory = (ArrayFactory<ExtensionPointListener<T>>) LISTENER_ARRAY_FACTORY;
        if (arrayFactory == null) {
            $$$reportNull$$$0(61);
        }
        return arrayFactory;
    }

    private synchronized boolean addListener(ExtensionPointListener<T> extensionPointListener) {
        if (extensionPointListener == null) {
            $$$reportNull$$$0(62);
        }
        ExtensionPointListener<T>[] extensionPointListenerArr = this.listeners;
        if (ArrayUtilRt.indexOf(extensionPointListenerArr, extensionPointListener, 0, extensionPointListenerArr.length) != -1) {
            return false;
        }
        if (extensionPointListener instanceof ExtensionPointPriorityListener) {
            this.listeners = (ExtensionPointListener[]) ArrayUtil.prepend(extensionPointListener, this.listeners, (ArrayFactory<? extends ExtensionPointListener<T>>) listenerArrayFactory());
        } else {
            this.listeners = (ExtensionPointListener[]) ArrayUtil.append(this.listeners, extensionPointListener, (ArrayFactory<? extends ExtensionPointListener<T>>) listenerArrayFactory());
        }
        return true;
    }

    public final void addChangeListener(final Runnable runnable, Disposable disposable) {
        if (runnable == null) {
            $$$reportNull$$$0(64);
        }
        final ExtensionPointAdapter<T> extensionPointAdapter = new ExtensionPointAdapter<T>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl.4
            @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointAdapter
            public void extensionListChanged() {
                runnable.run();
            }
        };
        this.listeners = (ExtensionPointListener[]) ArrayUtil.append((ExtensionPointAdapter<T>[]) this.listeners, extensionPointAdapter, (ArrayFactory<? extends ExtensionPointAdapter<T>>) listenerArrayFactory());
        if (disposable != null) {
            Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl$$ExternalSyntheticLambda5
                @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
                public final void dispose() {
                    this.f$0.m6480x8f011723(extensionPointAdapter);
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint
    /* JADX INFO: renamed from: removeExtensionPointListener, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final synchronized void m6481xfe024b65(ExtensionPointListener<T> extensionPointListener) {
        if (extensionPointListener == null) {
            $$$reportNull$$$0(65);
        }
        this.listeners = (ExtensionPointListener[]) ArrayUtil.remove(this.listeners, extensionPointListener, (ArrayFactory<? extends ExtensionPointListener<T>>) listenerArrayFactory());
    }

    public final Class<T> getExtensionClass() {
        Class<T> clsLoadClass = this.extensionClass;
        if (clsLoadClass == null) {
            try {
                clsLoadClass = this.componentManager.loadClass(this.className, this.pluginDescriptor);
                this.extensionClass = clsLoadClass;
            } catch (ClassNotFoundException e) {
                throw this.componentManager.createError(e, this.pluginDescriptor.getId());
            }
        }
        if (clsLoadClass == null) {
            $$$reportNull$$$0(66);
        }
        return clsLoadClass;
    }

    public final String toString() {
        return getName();
    }

    final synchronized void addExtensionAdapter(ExtensionComponentAdapter extensionComponentAdapter) {
        if (extensionComponentAdapter == null) {
            $$$reportNull$$$0(67);
        }
        ArrayList arrayList = new ArrayList(this.adapters.size() + 1);
        arrayList.addAll(this.adapters);
        arrayList.add(extensionComponentAdapter);
        this.adapters = arrayList;
        clearCache();
    }

    final void clearUserCache() {
        ConcurrentMap<?, Map<?, ?>> concurrentMap = this.keyMapperToCacheRef.get();
        if (concurrentMap != null) {
            concurrentMap.clear();
        }
    }

    private void clearCache() {
        this.cachedExtensions = null;
        this.cachedExtensionsAsArray = null;
        this.adaptersAreSorted = false;
        clearUserCache();
        assertNotReadOnlyMode();
    }

    private void assertNotReadOnlyMode() {
        if (isInReadOnlyMode()) {
            throw new IllegalStateException(this + " in a read-only mode and cannot be modified");
        }
    }

    public final synchronized void registerExtensions(List<ExtensionDescriptor> list, PluginDescriptor pluginDescriptor, List<Runnable> list2) {
        if (list == null) {
            try {
                $$$reportNull$$$0(68);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(69);
        }
        List arrayList = this.adapters;
        if (arrayList == Collections.emptyList()) {
            arrayList = new ArrayList(list.size());
            this.adapters = arrayList;
            this.adaptersAreSorted = false;
        } else {
            ((ArrayList) arrayList).ensureCapacity(arrayList.size() + list.size());
        }
        int size = arrayList.size();
        for (ExtensionDescriptor extensionDescriptor : list) {
            if (extensionDescriptor.os == null || this.componentManager.isSuitableForOs(extensionDescriptor.os)) {
                arrayList.add(createAdapter(extensionDescriptor, pluginDescriptor, this.componentManager));
            }
        }
        int size2 = arrayList.size();
        clearCache();
        final ExtensionPointListener<T>[] extensionPointListenerArr = this.listeners;
        if (list2 != null && extensionPointListenerArr.length != 0) {
            final List listEmptyList = Collections.emptyList();
            for (ExtensionPointListener<T> extensionPointListener : extensionPointListenerArr) {
                if (!(extensionPointListener instanceof ExtensionPointAdapter)) {
                    List listSubList = arrayList.subList(size, size2);
                    Set setNewSetFromMap = Collections.newSetFromMap(new IdentityHashMap(listSubList.size()));
                    setNewSetFromMap.addAll(listSubList);
                    listEmptyList = new ArrayList(setNewSetFromMap.size());
                    for (ExtensionComponentAdapter extensionComponentAdapter : getSortedAdapters()) {
                        if (setNewSetFromMap.contains(extensionComponentAdapter)) {
                            listEmptyList.add(extensionComponentAdapter);
                        }
                    }
                    break;
                }
            }
            list2.add(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m6483x81ef27ee(listEmptyList, extensionPointListenerArr);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$registerExtensions$17$org-jetbrains-kotlin-com-intellij-openapi-extensions-impl-ExtensionPointImpl, reason: not valid java name */
    /* synthetic */ void m6483x81ef27ee(List list, ExtensionPointListener[] extensionPointListenerArr) {
        notifyListeners(false, list, extensionPointListenerArr);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0014 A[Catch: all -> 0x001e, TRY_LEAVE, TryCatch #0 {, blocks: (B:5:0x0005, B:6:0x0008, B:8:0x000e, B:10:0x0014), top: B:17:0x0005 }] */
    /* JADX WARN: Code duplicated, block: B:21:0x0019 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:8:0x000e A[Catch: all -> 0x001e, TryCatch #0 {, blocks: (B:5:0x0005, B:6:0x0008, B:8:0x000e, B:10:0x0014), top: B:17:0x0005 }] */
    final synchronized void notifyAreaReplaced(ExtensionsArea extensionsArea) {
        int i;
        if (extensionsArea == null) {
            $$$reportNull$$$0(70);
            for (ExtensionPointListener<T> extensionPointListener : this.listeners) {
                if (extensionPointListener instanceof ExtensionPointAndAreaListener) {
                    ((ExtensionPointAndAreaListener) extensionPointListener).areaReplaced(extensionsArea);
                }
            }
        } else {
            while (i < r1) {
                if (extensionPointListener instanceof ExtensionPointAndAreaListener) {
                    ((ExtensionPointAndAreaListener) extensionPointListener).areaReplaced(extensionsArea);
                }
            }
        }
        throw th;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <V extends T> V findExtension(Class<V> cls, boolean z, ThreeState threeState) {
        if (cls == 0) {
            $$$reportNull$$$0(71);
        }
        if (threeState == null) {
            $$$reportNull$$$0(72);
        }
        if (threeState != ThreeState.NO) {
            T tFindExtensionByExactClass = findExtensionByExactClass(cls);
            if (tFindExtensionByExactClass != null) {
                return tFindExtensionByExactClass;
            }
            if (threeState == ThreeState.YES) {
                return null;
            }
        }
        List<T> list = this.cachedExtensions;
        if (list == null) {
            for (ExtensionComponentAdapter extensionComponentAdapter : getThreadSafeAdapterList(false)) {
                try {
                    if (cls.isAssignableFrom(extensionComponentAdapter.getImplementationClass(this.componentManager))) {
                        return processAdapter(extensionComponentAdapter);
                    }
                    continue;
                } catch (ClassNotFoundException e) {
                    this.componentManager.logError(e, extensionComponentAdapter.getPluginDescriptor().getId());
                }
            }
        } else {
            for (T t : list) {
                if (cls.isInstance(t)) {
                    return t;
                }
            }
        }
        if (!z) {
            return null;
        }
        String str = "cannot find extension implementation " + cls + "(epName=" + getName() + ", extensionCount=" + size();
        if (this.cachedExtensions != null) {
            str = str + ", cachedExtensions";
        }
        if (isInReadOnlyMode()) {
            str = str + ", point in read-only mode";
        }
        throw this.componentManager.createError(str + Constant.AFTER_QUTO, getPluginDescriptor().getId());
    }

    private T findExtensionByExactClass(Class<? extends T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(73);
        }
        List<T> list = this.cachedExtensions;
        if (list == null) {
            for (ExtensionComponentAdapter extensionComponentAdapter : getThreadSafeAdapterList(false)) {
                Object obj = extensionComponentAdapter.implementationClassOrName;
                if (obj instanceof String) {
                    if (obj.equals(cls.getName())) {
                        return processAdapter(extensionComponentAdapter);
                    }
                } else if (obj == cls) {
                    return processAdapter(extensionComponentAdapter);
                }
            }
            return null;
        }
        for (T t : list) {
            if (cls == t.getClass()) {
                return t;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ObjectComponentAdapter<T> extends ExtensionComponentAdapter {
        private final T componentInstance;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 3 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "pluginDescriptor";
            } else if (i == 2) {
                objArr[0] = "loadingOrder";
            } else if (i != 3) {
                objArr[0] = SchemaSymbols.ATTVAL_EXTENSION;
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/ExtensionPointImpl$ObjectComponentAdapter";
            }
            if (i != 3) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/ExtensionPointImpl$ObjectComponentAdapter";
            } else {
                objArr[1] = "createInstance";
            }
            if (i != 3) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 3) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionComponentAdapter
        boolean isInstanceCreated() {
            return true;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private ObjectComponentAdapter(final T t, PluginDescriptor pluginDescriptor, LoadingOrder loadingOrder) {
            super(t.getClass().getName(), pluginDescriptor, null, loadingOrder, new ImplementationClassResolver() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl$ObjectComponentAdapter$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ImplementationClassResolver
                public final Class resolveImplementationClass(ComponentManager componentManager, ExtensionComponentAdapter extensionComponentAdapter) {
                    return t.getClass();
                }
            });
            if (t == null) {
                $$$reportNull$$$0(0);
            }
            if (pluginDescriptor == null) {
                $$$reportNull$$$0(1);
            }
            if (loadingOrder == null) {
                $$$reportNull$$$0(2);
            }
            this.componentInstance = t;
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionComponentAdapter
        public <I> I createInstance(ComponentManager componentManager) {
            T t = this.componentInstance;
            if (t == null) {
                $$$reportNull$$$0(3);
            }
            return t;
        }
    }

    private synchronized boolean isInReadOnlyMode() {
        Set<ExtensionPointImpl<?>> set;
        set = POINTS_IN_READONLY_MODE;
        return set != null && set.contains(this);
    }
}
