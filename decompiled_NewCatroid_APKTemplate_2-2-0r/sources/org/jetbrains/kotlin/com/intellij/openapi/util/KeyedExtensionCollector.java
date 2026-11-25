package org.jetbrains.kotlin.com.intellij.openapi.util;

import com.android.tools.r8.internal.NN$$ExternalSyntheticLambda0;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;
import net.mgsx.gltf.data.extensions.KHRLightsPunctual;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionNotApplicableException;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointAndAreaListener;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointPriorityListener;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.Extensions;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class KeyedExtensionCollector<T, KeyT> implements ModificationTracker {
    private static final Logger LOG = Logger.getInstance((Class<?>) KeyedExtensionCollector.class);
    private final ConcurrentMap<String, List<T>> myCache;
    protected final AtomicBoolean myEpListenerAdded;
    private final String myEpName;
    private Map<String, List<T>> myExplicitExtensions;
    protected final String myLock;
    private final SimpleModificationTracker myTracker;

    /* JADX WARN: Code duplicated, block: B:39:0x007d  */
    /* JADX WARN: Code duplicated, block: B:9:0x0016  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 11 && i != 13 && i != 23 && i != 25) {
            switch (i) {
                case 17:
                case 18:
                case 19:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 11 && i != 13 && i != 23 && i != 25) {
            switch (i) {
                case 17:
                case 18:
                case 19:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 2:
                objArr[0] = KHRLightsPunctual.GLTFLight.TYPE_POINT;
                break;
            case 3:
            case 5:
            case 8:
            case 10:
            case 12:
            case 14:
            case 16:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 4:
            case 6:
            case 9:
                objArr[0] = "t";
                break;
            case 7:
                objArr[0] = "parentDisposable";
                break;
            case 11:
            case 13:
            case 17:
            case 18:
            case 19:
            case 23:
            case 25:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/KeyedExtensionCollector";
                break;
            case 15:
                objArr[0] = "stringKey";
                break;
            case 20:
            case 24:
                objArr[0] = "isMyBean";
                break;
            case 21:
                objArr[0] = "extensions";
                break;
            case 22:
                objArr[0] = UserMetadata.KEYDATA_FILENAME;
                break;
            default:
                objArr[0] = "epName";
                break;
        }
        if (i == 11) {
            objArr[1] = "keyToString";
        } else if (i == 13) {
            objArr[1] = "forKey";
        } else if (i == 23) {
            objArr[1] = "buildExtensions";
        } else if (i != 25) {
            switch (i) {
                case 17:
                    objArr[1] = "buildExtensions";
                    break;
                case 18:
                case 19:
                    objArr[1] = "getExtensions";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/KeyedExtensionCollector";
                    break;
            }
        } else {
            objArr[1] = "getName";
        }
        switch (i) {
            case 2:
                objArr[2] = "addExtensionPointListener";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[2] = "addExplicitExtension";
                break;
            case 8:
            case 9:
                objArr[2] = "removeExplicitExtension";
                break;
            case 10:
                objArr[2] = "keyToString";
                break;
            case 11:
            case 13:
            case 17:
            case 18:
            case 19:
            case 23:
            case 25:
                break;
            case 12:
                objArr[2] = "forKey";
                break;
            case 14:
                objArr[2] = "findSingle";
                break;
            case 15:
            case 16:
            case 22:
                objArr[2] = "buildExtensions";
                break;
            case 20:
            case 21:
                objArr[2] = "buildExtensionsFromExtensionPoint";
                break;
            case 24:
                objArr[2] = "buildExtensionsFromExplicitRegistration";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 11 && i != 13 && i != 23 && i != 25) {
            switch (i) {
                case 17:
                case 18:
                case 19:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KeyedExtensionCollector(ExtensionPointName<? extends KeyedLazyInstance<T>> extensionPointName) {
        this(extensionPointName.getName());
        if (extensionPointName == null) {
            $$$reportNull$$$0(0);
        }
    }

    public KeyedExtensionCollector(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        this.myExplicitExtensions = Collections.emptyMap();
        this.myCache = new ConcurrentHashMap();
        this.myTracker = new SimpleModificationTracker();
        this.myEpListenerAdded = new AtomicBoolean();
        this.myEpName = str;
        this.myLock = "lock for KeyedExtensionCollector " + str;
    }

    public void clearCache() {
        this.myCache.clear();
        this.myTracker.incModificationCount();
    }

    private void addExtensionPointListener(ExtensionPoint<KeyedLazyInstance<T>> extensionPoint) {
        if (extensionPoint == null) {
            $$$reportNull$$$0(2);
        }
        if (this.myEpListenerAdded.compareAndSet(false, true)) {
            extensionPoint.addExtensionPointListener(new MyExtensionPointListener(), false, null);
        }
    }

    protected void invalidateCacheForExtension(String str) {
        if (str != null) {
            this.myCache.remove(str);
        }
        this.myTracker.incModificationCount();
    }

    public void addExplicitExtension(KeyT keyt, T t) {
        if (keyt == null) {
            $$$reportNull$$$0(3);
        }
        if (t == null) {
            $$$reportNull$$$0(4);
        }
        synchronized (this.myLock) {
            String strKeyToString = keyToString(keyt);
            if (this.myExplicitExtensions == Collections.emptyMap()) {
                this.myExplicitExtensions = new HashMap();
            }
            this.myExplicitExtensions.computeIfAbsent(strKeyToString, new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return KeyedExtensionCollector.lambda$addExplicitExtension$0((String) obj);
                }
            }).add(t);
            invalidateCacheForExtension(strKeyToString);
        }
    }

    static /* synthetic */ List lambda$addExplicitExtension$0(String str) {
        return new SmartList();
    }

    public void addExplicitExtension(final KeyT keyt, final T t, Disposable disposable) {
        if (keyt == null) {
            $$$reportNull$$$0(5);
        }
        if (t == null) {
            $$$reportNull$$$0(6);
        }
        if (disposable == null) {
            $$$reportNull$$$0(7);
        }
        addExplicitExtension(keyt, t);
        Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public final void dispose() {
                this.f$0.m6501x67ad86cc(keyt, t);
            }
        });
    }

    /* JADX INFO: renamed from: removeExplicitExtension, reason: merged with bridge method [inline-methods] */
    public void m6501x67ad86cc(KeyT keyt, T t) {
        if (keyt == null) {
            $$$reportNull$$$0(8);
        }
        if (t == null) {
            $$$reportNull$$$0(9);
        }
        synchronized (this.myLock) {
            String strKeyToString = keyToString(keyt);
            List<T> list = this.myExplicitExtensions.get(strKeyToString);
            if (list != null) {
                list.remove(t);
                if (list.isEmpty()) {
                    this.myExplicitExtensions.remove(strKeyToString);
                }
            }
            invalidateCacheForExtension(strKeyToString);
        }
    }

    protected String keyToString(KeyT keyt) {
        if (keyt == null) {
            $$$reportNull$$$0(10);
        }
        String string = keyt.toString();
        if (string == null) {
            $$$reportNull$$$0(11);
        }
        return string;
    }

    public List<T> forKey(KeyT keyt) {
        if (keyt == null) {
            $$$reportNull$$$0(12);
        }
        String strKeyToString = keyToString(keyt);
        List<T> list = this.myCache.get(strKeyToString);
        if (list == null) {
            list = (List) ConcurrencyUtil.cacheOrGet(this.myCache, strKeyToString, ContainerUtil.immutableCopy(buildExtensions(strKeyToString, keyt)));
        }
        if (list == null) {
            $$$reportNull$$$0(13);
        }
        return list;
    }

    public T findSingle(KeyT keyt) {
        if (keyt == null) {
            $$$reportNull$$$0(14);
        }
        List<T> listForKey = forKey(keyt);
        if (listForKey.isEmpty()) {
            return null;
        }
        return listForKey.get(0);
    }

    protected List<T> buildExtensions(final String str, KeyT keyt) {
        List<T> listNotNullize;
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        if (keyt == null) {
            $$$reportNull$$$0(16);
        }
        List<KeyedLazyInstance<T>> extensions = getExtensions();
        synchronized (this.myLock) {
            List<T> list = this.myExplicitExtensions.get(str);
            listNotNullize = ContainerUtil.notNullize(buildExtensionsFromExtensionPoint(list != null ? new ArrayList(list) : null, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return str.equals(((KeyedLazyInstance) obj).getKey());
                }
            }, extensions));
        }
        if (listNotNullize == null) {
            $$$reportNull$$$0(17);
        }
        return listNotNullize;
    }

    protected final List<KeyedLazyInstance<T>> getExtensions() {
        ExtensionPoint<KeyedLazyInstance<T>> point = getPoint();
        if (point == null) {
            List<KeyedLazyInstance<T>> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(18);
            }
            return listEmptyList;
        }
        addExtensionPointListener(point);
        List<KeyedLazyInstance<T>> extensionList = point.getExtensionList();
        if (extensionList == null) {
            $$$reportNull$$$0(19);
        }
        return extensionList;
    }

    final List<T> buildExtensionsFromExtensionPoint(List<T> list, Predicate<? super KeyedLazyInstance<T>> predicate, List<? extends KeyedLazyInstance<T>> list2) {
        if (predicate == null) {
            $$$reportNull$$$0(20);
        }
        if (list2 == null) {
            $$$reportNull$$$0(21);
        }
        for (KeyedLazyInstance<T> keyedLazyInstance : list2) {
            if (predicate.test(keyedLazyInstance)) {
                try {
                    T keyedLazyInstance2 = keyedLazyInstance.getInstance();
                    if (list == null) {
                        list = new SmartList();
                    }
                    list.add(keyedLazyInstance2);
                } catch (ProcessCanceledException e) {
                    throw e;
                } catch (Exception e2) {
                    e = e2;
                    LOG.error(e);
                } catch (LinkageError e3) {
                    e = e3;
                    LOG.error(e);
                } catch (ExtensionNotApplicableException unused) {
                }
            }
        }
        return list;
    }

    protected List<T> buildExtensions(final Set<String> set) {
        List<T> listNotNullize;
        if (set == null) {
            $$$reportNull$$$0(22);
        }
        List<KeyedLazyInstance<T>> extensions = getExtensions();
        synchronized (this.myLock) {
            Objects.requireNonNull(set);
            listNotNullize = ContainerUtil.notNullize(buildExtensionsFromExtensionPoint(buildExtensionsFromExplicitRegistration(null, new NN$$ExternalSyntheticLambda0(set)), new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return set.contains(((KeyedLazyInstance) obj).getKey());
                }
            }, extensions));
        }
        if (listNotNullize == null) {
            $$$reportNull$$$0(23);
        }
        return listNotNullize;
    }

    protected List<T> buildExtensionsFromExplicitRegistration(List<T> list, Predicate<? super String> predicate) {
        if (predicate == null) {
            $$$reportNull$$$0(24);
        }
        for (Map.Entry<String, List<T>> entry : this.myExplicitExtensions.entrySet()) {
            if (predicate.test(entry.getKey())) {
                List<T> value = entry.getValue();
                if (list == null) {
                    list = new ArrayList<>(value);
                } else {
                    list.addAll(value);
                }
            }
        }
        return list;
    }

    public final ExtensionPoint<KeyedLazyInstance<T>> getPoint() {
        return Extensions.getRootArea().getExtensionPointIfRegistered(this.myEpName);
    }

    public boolean hasAnyExtensions() {
        synchronized (this.myLock) {
            boolean z = true;
            if (!this.myExplicitExtensions.isEmpty()) {
                return true;
            }
            ExtensionPoint<KeyedLazyInstance<T>> point = getPoint();
            if (point == null || point.size() == 0) {
                z = false;
            }
            return z;
        }
    }

    public String getName() {
        String str = this.myEpName;
        if (str == null) {
            $$$reportNull$$$0(25);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
    public long getModificationCount() {
        return this.myTracker.getModificationCount();
    }

    protected void ensureValuesLoaded() {
        ExtensionPoint<KeyedLazyInstance<T>> point = getPoint();
        if (point != null) {
            Iterator<KeyedLazyInstance<T>> it = point.getExtensionList().iterator();
            while (it.hasNext()) {
                it.next().getInstance();
            }
        }
    }

    private final class MyExtensionPointListener implements ExtensionPointAndAreaListener<KeyedLazyInstance<T>>, ExtensionPointPriorityListener {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1 || i == 3) {
                objArr[0] = "pluginDescriptor";
            } else if (i != 4) {
                objArr[0] = "bean";
            } else {
                objArr[0] = "area";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/KeyedExtensionCollector$MyExtensionPointListener";
            if (i == 2 || i == 3) {
                objArr[2] = "extensionRemoved";
            } else if (i != 4) {
                objArr[2] = "extensionAdded";
            } else {
                objArr[2] = "areaReplaced";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private MyExtensionPointListener() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointListener
        public void extensionAdded(KeyedLazyInstance<T> keyedLazyInstance, PluginDescriptor pluginDescriptor) {
            if (keyedLazyInstance == null) {
                $$$reportNull$$$0(0);
            }
            if (pluginDescriptor == null) {
                $$$reportNull$$$0(1);
            }
            synchronized (KeyedExtensionCollector.this.myLock) {
                if (keyedLazyInstance.getKey() == null) {
                    throw new PluginException("No key specified for extension of class " + keyedLazyInstance.getInstance().getClass(), pluginDescriptor.getId());
                }
                KeyedExtensionCollector.this.invalidateCacheForExtension(keyedLazyInstance.getKey());
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointListener
        public void extensionRemoved(KeyedLazyInstance<T> keyedLazyInstance, PluginDescriptor pluginDescriptor) {
            if (keyedLazyInstance == null) {
                $$$reportNull$$$0(2);
            }
            if (pluginDescriptor == null) {
                $$$reportNull$$$0(3);
            }
            synchronized (KeyedExtensionCollector.this.myLock) {
                KeyedExtensionCollector.this.invalidateCacheForExtension(keyedLazyInstance.getKey());
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointAndAreaListener
        public void areaReplaced(ExtensionsArea extensionsArea) {
            if (extensionsArea == null) {
                $$$reportNull$$$0(4);
            }
            KeyedExtensionCollector.this.myCache.clear();
            KeyedExtensionCollector.this.myTracker.incModificationCount();
        }
    }
}
