package org.jetbrains.kotlin.com.intellij.util.pico;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.org.picocontainer.ComponentAdapter;
import org.jetbrains.kotlin.org.picocontainer.MutablePicoContainer;
import org.jetbrains.kotlin.org.picocontainer.PicoContainer;
import org.jetbrains.kotlin.org.picocontainer.PicoRegistrationException;
import org.jetbrains.kotlin.org.picocontainer.defaults.AmbiguousComponentResolutionException;

/* JADX INFO: loaded from: classes3.dex */
public class DefaultPicoContainer implements MutablePicoContainer {
    private final LinkedHashSetWrapper<ComponentAdapter> componentAdapters;
    private final Map<Object, ComponentAdapter> componentKeyToAdapter;
    private final DefaultPicoContainer parent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 5:
            case 6:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                i2 = 3;
                break;
            case 5:
            case 6:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 8:
            case 9:
            case 14:
            case 16:
                objArr[0] = "componentKey";
                break;
            case 3:
            case 4:
            case 12:
                objArr[0] = "componentType";
                break;
            case 5:
            case 6:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/pico/DefaultPicoContainer";
                break;
            case 7:
            case 13:
                objArr[0] = "componentAdapter";
                break;
            case 10:
                objArr[0] = "serviceClass";
                break;
            case 11:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 15:
                objArr[0] = "componentInstance";
                break;
            case 17:
                objArr[0] = "componentImplementation";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/pico/DefaultPicoContainer";
                break;
            case 5:
            case 6:
                objArr[1] = "getComponentAdaptersOfType";
                break;
            default:
                objArr[1] = "getComponentAdapters";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "getComponentAdapter";
                break;
            case 2:
                objArr[2] = "getFromCache";
                break;
            case 3:
                objArr[2] = "getComponentAdapterOfType";
                break;
            case 4:
                objArr[2] = "getComponentAdaptersOfType";
                break;
            case 7:
                objArr[2] = "registerComponent";
                break;
            case 8:
                objArr[2] = "unregisterComponent";
                break;
            case 9:
                objArr[2] = "getComponentInstance";
                break;
            case 10:
                objArr[2] = "getService";
                break;
            case 11:
                objArr[2] = "getServiceAdapter";
                break;
            case 12:
                objArr[2] = "getComponentInstanceOfType";
                break;
            case 13:
                objArr[2] = "getInstance";
                break;
            case 14:
            case 15:
                objArr[2] = "registerComponentInstance";
                break;
            case 16:
            case 17:
                objArr[2] = "registerComponentImplementation";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                throw new IllegalArgumentException(str2);
            case 5:
            case 6:
            default:
                throw new IllegalStateException(str2);
        }
    }

    public DefaultPicoContainer(DefaultPicoContainer defaultPicoContainer) {
        this.componentKeyToAdapter = new ConcurrentHashMap();
        this.componentAdapters = new LinkedHashSetWrapper<>();
        this.parent = defaultPicoContainer;
    }

    public DefaultPicoContainer() {
        this(null);
    }

    public final ComponentAdapter getComponentAdapter(Object obj) {
        DefaultPicoContainer defaultPicoContainer;
        if (obj == null) {
            $$$reportNull$$$0(1);
        }
        ComponentAdapter fromCache = getFromCache(obj);
        return (fromCache != null || (defaultPicoContainer = this.parent) == null) ? fromCache : defaultPicoContainer.getComponentAdapter(obj);
    }

    private ComponentAdapter getFromCache(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(2);
        }
        ComponentAdapter componentAdapter = this.componentKeyToAdapter.get(obj);
        if (componentAdapter != null) {
            return componentAdapter;
        }
        if (obj instanceof Class) {
            return this.componentKeyToAdapter.get(((Class) obj).getName());
        }
        return null;
    }

    public final ComponentAdapter getComponentAdapterOfType(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(3);
        }
        ComponentAdapter componentAdapter = getComponentAdapter(cls);
        if (componentAdapter != null) {
            return componentAdapter;
        }
        List<ComponentAdapter> componentAdaptersOfType = getComponentAdaptersOfType(cls);
        if (componentAdaptersOfType.size() == 1) {
            return componentAdaptersOfType.get(0);
        }
        if (componentAdaptersOfType.isEmpty()) {
            DefaultPicoContainer defaultPicoContainer = this.parent;
            if (defaultPicoContainer == null) {
                return null;
            }
            return defaultPicoContainer.getComponentAdapterOfType(cls);
        }
        int size = componentAdaptersOfType.size();
        Class[] clsArr = new Class[size];
        for (int i = 0; i < size; i++) {
            clsArr[i] = componentAdaptersOfType.get(i).getComponentImplementation();
        }
        throw new AmbiguousComponentResolutionException(cls, clsArr);
    }

    public final List<ComponentAdapter> getComponentAdaptersOfType(Class<?> cls) {
        Class<?> componentImplementation;
        if (cls == null) {
            $$$reportNull$$$0(4);
        }
        if (cls == String.class) {
            List<ComponentAdapter> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(5);
            }
            return listEmptyList;
        }
        ArrayList arrayList = new ArrayList();
        ComponentAdapter componentAdapter = this.componentKeyToAdapter.get(cls.getName());
        if (componentAdapter != null) {
            arrayList.add(componentAdapter);
        }
        for (ComponentAdapter componentAdapter2 : this.componentKeyToAdapter.values()) {
            if (!(componentAdapter2.getComponentKey() instanceof String) && (cls == (componentImplementation = componentAdapter2.getComponentImplementation()) || cls.isAssignableFrom(componentImplementation))) {
                arrayList.add(componentAdapter2);
            }
        }
        return arrayList;
    }

    public final ComponentAdapter registerComponent(ComponentAdapter componentAdapter) {
        if (componentAdapter == null) {
            $$$reportNull$$$0(7);
        }
        if (this.componentKeyToAdapter.putIfAbsent(componentAdapter.getComponentKey(), componentAdapter) != null) {
            throw new PicoRegistrationException("Key " + componentAdapter.getComponentKey() + " duplicated");
        }
        this.componentAdapters.add(componentAdapter);
        return componentAdapter;
    }

    @Override // org.jetbrains.kotlin.org.picocontainer.MutablePicoContainer
    public final ComponentAdapter unregisterComponent(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(8);
        }
        ComponentAdapter componentAdapterRemove = this.componentKeyToAdapter.remove(obj);
        if (componentAdapterRemove == null) {
            return null;
        }
        this.componentAdapters.remove(componentAdapterRemove);
        return componentAdapterRemove;
    }

    public Object getComponentInstance(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(9);
        }
        ComponentAdapter fromCache = getFromCache(obj);
        if (fromCache != null) {
            return fromCache.getComponentInstance(this);
        }
        DefaultPicoContainer defaultPicoContainer = this.parent;
        if (defaultPicoContainer == null) {
            return null;
        }
        return defaultPicoContainer.getComponentInstance(obj);
    }

    public final <T> T getService(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(10);
        }
        ComponentAdapter componentAdapter = this.componentKeyToAdapter.get(cls.getName());
        if (componentAdapter == null) {
            return null;
        }
        return (T) componentAdapter.getComponentInstance(this);
    }

    @Override // org.jetbrains.kotlin.org.picocontainer.MutablePicoContainer
    public final ComponentAdapter registerComponentInstance(Object obj, Object obj2) {
        if (obj == null) {
            $$$reportNull$$$0(14);
        }
        if (obj2 == null) {
            $$$reportNull$$$0(15);
        }
        return registerComponent(new InstanceComponentAdapter(obj, obj2));
    }

    @Override // org.jetbrains.kotlin.org.picocontainer.MutablePicoContainer
    public final ComponentAdapter registerComponentImplementation(Object obj, Class<?> cls) {
        if (obj == null) {
            $$$reportNull$$$0(16);
        }
        if (cls == null) {
            $$$reportNull$$$0(17);
        }
        return registerComponent(new CachingConstructorInjectionComponentAdapter(obj, cls));
    }

    public final DefaultPicoContainer getParent() {
        return this.parent;
    }

    private static final class LinkedHashSetWrapper<T> {
        private volatile Set<T> immutableSet;
        private final Object lock;
        private LinkedHashSet<T> synchronizedSet;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i != 1) {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/pico/DefaultPicoContainer$LinkedHashSetWrapper";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/pico/DefaultPicoContainer$LinkedHashSetWrapper";
            } else {
                objArr[1] = "getImmutableSet";
            }
            if (i != 1) {
                objArr[2] = ProductAction.ACTION_ADD;
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        private LinkedHashSetWrapper() {
            this.lock = new Object();
            this.synchronizedSet = new LinkedHashSet<>();
        }

        public void add(T t) {
            if (t == null) {
                $$$reportNull$$$0(0);
            }
            synchronized (this.lock) {
                if (!this.synchronizedSet.contains(t)) {
                    copySyncSetIfExposedAsImmutable().add(t);
                }
            }
        }

        private LinkedHashSet<T> copySyncSetIfExposedAsImmutable() {
            if (this.immutableSet != null) {
                this.immutableSet = null;
                this.synchronizedSet = new LinkedHashSet<>(this.synchronizedSet);
            }
            return this.synchronizedSet;
        }

        public void remove(T t) {
            synchronized (this.lock) {
                copySyncSetIfExposedAsImmutable().remove(t);
            }
        }
    }

    public final String toString() {
        DefaultPicoContainer defaultPicoContainer = this.parent;
        return "DefaultPicoContainer" + (defaultPicoContainer == null ? " (root)" : " (parent=" + defaultPicoContainer + Constant.AFTER_QUTO);
    }

    public static final class InstanceComponentAdapter implements ComponentAdapter {
        private final Object componentInstance;
        private final Object componentKey;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "componentKey";
            } else {
                objArr[0] = "componentInstance";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/pico/DefaultPicoContainer$InstanceComponentAdapter";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public InstanceComponentAdapter(Object obj, Object obj2) {
            if (obj == null) {
                $$$reportNull$$$0(0);
            }
            if (obj2 == null) {
                $$$reportNull$$$0(1);
            }
            this.componentKey = obj;
            this.componentInstance = obj2;
        }

        @Override // org.jetbrains.kotlin.org.picocontainer.ComponentAdapter
        public Object getComponentInstance(PicoContainer picoContainer) {
            return this.componentInstance;
        }

        @Override // org.jetbrains.kotlin.org.picocontainer.ComponentAdapter
        public Object getComponentKey() {
            return this.componentKey;
        }

        @Override // org.jetbrains.kotlin.org.picocontainer.ComponentAdapter
        public Class<?> getComponentImplementation() {
            return this.componentInstance.getClass();
        }

        public String toString() {
            return getClass().getName() + "[" + getComponentKey() + "]";
        }
    }
}
