package org.jetbrains.kotlin.com.intellij.mock;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.diagnostic.ActivityCategory;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionsAreaImpl;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Conditions;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtilRt;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.messages.ListenerDescriptor;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBus;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBusOwner;
import org.jetbrains.kotlin.com.intellij.util.messages.impl.MessageBusFactoryImpl;
import org.jetbrains.kotlin.com.intellij.util.pico.DefaultPicoContainer;
import org.jetbrains.kotlin.org.picocontainer.MutablePicoContainer;
import org.jetbrains.kotlin.org.picocontainer.PicoContainer;

/* JADX INFO: loaded from: classes3.dex */
public class MockComponentManager extends UserDataHolderBase implements ComponentManager, MessageBusOwner {
    private final Map<Class<?>, Object> myComponents;
    private final Set<Object> myDisposableComponents;
    private boolean myDisposed;
    private final ExtensionsAreaImpl myExtensionArea;
    private final MessageBus myMessageBus;
    private final DefaultPicoContainer myPicoContainer;

    /* JADX WARN: Code duplicated, block: B:8:0x0011  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 30 && i != 31) {
            switch (i) {
                case 24:
                case 25:
                case 26:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 30 && i != 31) {
            switch (i) {
                case 24:
                case 25:
                case 26:
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
            case 1:
            case 24:
            case 25:
            case 26:
            case 30:
            case 31:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/mock/MockComponentManager";
                break;
            case 2:
                objArr[0] = "aClass";
                break;
            case 3:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 4:
            case 6:
            case 8:
            case 10:
                objArr[0] = "pluginId";
                break;
            case 5:
                objArr[0] = "error";
                break;
            case 7:
            case 9:
                objArr[0] = CrashHianalyticsData.MESSAGE;
                break;
            case 11:
            case 14:
            case 16:
                objArr[0] = "serviceInterface";
                break;
            case 12:
            case 13:
            case 15:
            case 17:
                objArr[0] = "serviceImplementation";
                break;
            case 18:
            default:
                objArr[0] = "parentDisposable";
                break;
            case 19:
            case 21:
                objArr[0] = "interfaceClass";
                break;
            case 20:
                objArr[0] = "instance";
                break;
            case 22:
                objArr[0] = "baseClass";
                break;
            case 23:
                objArr[0] = "serviceClass";
                break;
            case 27:
                objArr[0] = "descriptor";
                break;
            case 28:
                objArr[0] = "className";
                break;
            case 29:
                objArr[0] = "pluginDescriptor";
                break;
        }
        if (i == 1) {
            objArr[1] = "getExtensionArea";
        } else if (i == 30) {
            objArr[1] = "loadClass";
        } else if (i != 31) {
            switch (i) {
                case 24:
                    objArr[1] = "getPicoContainer";
                    break;
                case 25:
                    objArr[1] = "getMessageBus";
                    break;
                case 26:
                    objArr[1] = "getDisposed";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/mock/MockComponentManager";
                    break;
            }
        } else {
            objArr[1] = "getActivityCategory";
        }
        switch (i) {
            case 1:
            case 24:
            case 25:
            case 26:
            case 30:
            case 31:
                break;
            case 2:
            case 3:
            case 4:
                objArr[2] = "instantiateClassWithConstructorInjection";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = "createError";
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[2] = "registerService";
                break;
            case 19:
            case 20:
                objArr[2] = "addComponent";
                break;
            case 21:
                objArr[2] = "getComponent";
                break;
            case 22:
                objArr[2] = "getComponents";
                break;
            case 23:
                objArr[2] = "getService";
                break;
            case 27:
                objArr[2] = "createListener";
                break;
            case 28:
            case 29:
                objArr[2] = "loadClass";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 30 && i != 31) {
            switch (i) {
                case 24:
                case 25:
                case 26:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager
    public boolean isInjectionForExtensionSupported() {
        return false;
    }

    public MockComponentManager(PicoContainer picoContainer, Disposable disposable) {
        if (disposable == null) {
            $$$reportNull$$$0(0);
        }
        this.myMessageBus = MessageBusFactoryImpl.createRootBus(this);
        this.myComponents = new HashMap();
        this.myDisposableComponents = ContainerUtil.newConcurrentSet();
        DefaultPicoContainer defaultPicoContainer = new DefaultPicoContainer((DefaultPicoContainer) picoContainer) { // from class: org.jetbrains.kotlin.com.intellij.mock.MockComponentManager.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "componentKey", "org/jetbrains/kotlin/com/intellij/mock/MockComponentManager$1", "getComponentInstance"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.pico.DefaultPicoContainer
            public Object getComponentInstance(Object obj) {
                if (obj == null) {
                    $$$reportNull$$$0(0);
                }
                if (MockComponentManager.this.myDisposed) {
                    throw new IllegalStateException("Cannot get " + obj + " from already disposed " + this);
                }
                Object componentInstance = super.getComponentInstance(obj);
                MockComponentManager.this.registerComponentInDisposer(componentInstance);
                return componentInstance;
            }
        };
        this.myPicoContainer = defaultPicoContainer;
        defaultPicoContainer.registerComponentInstance(getClass(), this);
        this.myExtensionArea = new ExtensionsAreaImpl(this);
        Disposer.register(disposable, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager, org.jetbrains.kotlin.com.intellij.openapi.extensions.AreaInstance
    public ExtensionsAreaImpl getExtensionArea() {
        ExtensionsAreaImpl extensionsAreaImpl = this.myExtensionArea;
        if (extensionsAreaImpl == null) {
            $$$reportNull$$$0(1);
        }
        return extensionsAreaImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager
    public <T> T instantiateClassWithConstructorInjection(Class<T> cls, Object obj, PluginId pluginId) {
        if (cls == null) {
            $$$reportNull$$$0(2);
        }
        if (obj == null) {
            $$$reportNull$$$0(3);
        }
        if (pluginId == null) {
            $$$reportNull$$$0(4);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager
    public RuntimeException createError(Throwable th, PluginId pluginId) {
        if (th == null) {
            $$$reportNull$$$0(5);
        }
        if (pluginId == null) {
            $$$reportNull$$$0(6);
        }
        ExceptionUtilRt.rethrowUnchecked(th);
        return new RuntimeException(th);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager
    public RuntimeException createError(String str, PluginId pluginId) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        if (pluginId == null) {
            $$$reportNull$$$0(8);
        }
        return new RuntimeException(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager
    public RuntimeException createError(String str, Throwable th, PluginId pluginId, Map<String, String> map) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        if (pluginId == null) {
            $$$reportNull$$$0(10);
        }
        return new RuntimeException(str);
    }

    protected void registerComponentInDisposer(Object obj) {
        if (!(obj instanceof Disposable) || obj == this || (obj instanceof MessageBus) || !this.myDisposableComponents.add(obj)) {
            return;
        }
        Disposer.register(this, (Disposable) obj);
    }

    public <T> void registerService(Class<T> cls, Class<? extends T> cls2) {
        if (cls == null) {
            $$$reportNull$$$0(11);
        }
        if (cls2 == null) {
            $$$reportNull$$$0(12);
        }
        this.myPicoContainer.unregisterComponent(cls.getName());
        this.myPicoContainer.registerComponentImplementation(cls.getName(), cls2);
    }

    public <T> void registerService(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(13);
        }
        registerService((Class) cls, (Class) cls);
    }

    public <T> void registerService(Class<T> cls, T t) {
        if (cls == null) {
            $$$reportNull$$$0(14);
        }
        if (t == null) {
            $$$reportNull$$$0(15);
        }
        this.myPicoContainer.registerComponentInstance(cls.getName(), t);
        registerComponentInDisposer(t);
    }

    public <T> void registerService(Class<T> cls, T t, Disposable disposable) {
        if (cls == null) {
            $$$reportNull$$$0(16);
        }
        if (t == null) {
            $$$reportNull$$$0(17);
        }
        if (disposable == null) {
            $$$reportNull$$$0(18);
        }
        final String name2 = cls.getName();
        registerService(cls, t);
        Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.mock.MockComponentManager$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public final void dispose() {
                this.f$0.m6467x15966b5c(name2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$registerService$0$org-jetbrains-kotlin-com-intellij-mock-MockComponentManager, reason: not valid java name */
    /* synthetic */ void m6467x15966b5c(String str) {
        this.myPicoContainer.unregisterComponent(str);
    }

    public <T> void addComponent(Class<T> cls, T t) {
        if (cls == null) {
            $$$reportNull$$$0(19);
        }
        if (t == null) {
            $$$reportNull$$$0(20);
        }
        this.myComponents.put(cls, t);
        registerComponentInDisposer(t);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager
    public <T> T getComponent(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(21);
        }
        T t = (T) this.myPicoContainer.getComponentInstance(cls);
        return t != null ? t : (T) this.myComponents.get(cls);
    }

    public <T> T[] getComponents(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(22);
        }
        throw new UnsupportedOperationException();
    }

    public <T> T getService(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(23);
        }
        T t = (T) this.myPicoContainer.getService(cls);
        registerComponentInDisposer(t);
        return t;
    }

    /* JADX INFO: renamed from: getPicoContainer, reason: merged with bridge method [inline-methods] */
    public final MutablePicoContainer m6468getPicoContainer() {
        DefaultPicoContainer defaultPicoContainer = this.myPicoContainer;
        if (defaultPicoContainer == null) {
            $$$reportNull$$$0(24);
        }
        return defaultPicoContainer;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager
    public MessageBus getMessageBus() {
        MessageBus messageBus = this.myMessageBus;
        if (messageBus == null) {
            $$$reportNull$$$0(25);
        }
        return messageBus;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager, org.jetbrains.kotlin.com.intellij.util.messages.MessageBusOwner
    public boolean isDisposed() {
        return this.myDisposed;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
        Disposer.dispose(this.myMessageBus);
        this.myDisposed = true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager
    public Condition<?> getDisposed() {
        Condition<?> conditionAlwaysFalse = Conditions.alwaysFalse();
        if (conditionAlwaysFalse == null) {
            $$$reportNull$$$0(26);
        }
        return conditionAlwaysFalse;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.messages.MessageBusOwner
    public Object createListener(ListenerDescriptor listenerDescriptor) {
        if (listenerDescriptor == null) {
            $$$reportNull$$$0(27);
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager
    public <T> Class<T> loadClass(String str, PluginDescriptor pluginDescriptor) throws ClassNotFoundException {
        if (str == null) {
            $$$reportNull$$$0(28);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(29);
        }
        Class<T> cls = (Class<T>) Class.forName(str);
        if (cls == null) {
            $$$reportNull$$$0(30);
        }
        return cls;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager
    public ActivityCategory getActivityCategory(boolean z) {
        ActivityCategory activityCategory = z ? ActivityCategory.APP_EXTENSION : ActivityCategory.APP_SERVICE;
        if (activityCategory == null) {
            $$$reportNull$$$0(31);
        }
        return activityCategory;
    }
}
