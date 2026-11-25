package org.jetbrains.kotlin.com.intellij.openapi.extensions.impl;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Arrays;
import org.jetbrains.kotlin.analysis.providers.impl.ImplUtilsKt;
import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionNotApplicableException;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.LoadingOrder;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginAware;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;
import org.jetbrains.kotlin.com.intellij.util.xmlb.XmlSerializer;

/* JADX INFO: loaded from: classes3.dex */
class XmlExtensionAdapter extends ExtensionComponentAdapter {
    private static final Object NOT_APPLICABLE = new Object();
    private XmlElement extensionElement;
    private volatile Object extensionInstance;
    private boolean initializing;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 7 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 7 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "pluginDescriptor";
                break;
            case 2:
                objArr[0] = "order";
                break;
            case 3:
                objArr[0] = "implementationClassResolver";
                break;
            case 4:
            case 6:
                objArr[0] = "componentManager";
                break;
            case 5:
                objArr[0] = "aClass";
                break;
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/XmlExtensionAdapter";
                break;
            default:
                objArr[0] = "implementationClassName";
                break;
        }
        if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/XmlExtensionAdapter";
        } else {
            objArr[1] = "instantiateClass";
        }
        if (i == 4) {
            objArr[2] = "createInstance";
        } else if (i == 5 || i == 6) {
            objArr[2] = "instantiateClass";
        } else if (i != 7) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 7) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    XmlExtensionAdapter(String str, PluginDescriptor pluginDescriptor, String str2, LoadingOrder loadingOrder, XmlElement xmlElement, ImplementationClassResolver implementationClassResolver) {
        super(str, pluginDescriptor, str2, loadingOrder, implementationClassResolver);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (loadingOrder == null) {
            $$$reportNull$$$0(2);
        }
        if (implementationClassResolver == null) {
            $$$reportNull$$$0(3);
        }
        this.extensionElement = xmlElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionComponentAdapter
    final synchronized boolean isInstanceCreated() {
        return this.extensionInstance != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionComponentAdapter
    public <T> T createInstance(ComponentManager componentManager) {
        if (componentManager == null) {
            $$$reportNull$$$0(4);
        }
        T t = (T) this.extensionInstance;
        T t2 = null;
        if (t != null) {
            if (t == NOT_APPLICABLE) {
                return null;
            }
            return t;
        }
        synchronized (this) {
            Object obj = this.extensionInstance;
            if (obj != null) {
                if (obj != NOT_APPLICABLE) {
                    t2 = (T) obj;
                }
                return t2;
            }
            if (this.initializing) {
                throw componentManager.createError("Cyclic extension initialization: " + this, this.pluginDescriptor.getId());
            }
            try {
                try {
                    try {
                        try {
                            this.initializing = true;
                            T t3 = (T) instantiateClass(this.implementationClassResolver.resolveImplementationClass(componentManager, this), componentManager);
                            if (t3 instanceof PluginAware) {
                                ((PluginAware) t3).setPluginDescriptor(this.pluginDescriptor);
                            }
                            XmlElement xmlElement = this.extensionElement;
                            if (xmlElement != null) {
                                XmlSerializer.getBeanBinding(t3.getClass()).deserializeInto(t3, xmlElement);
                                this.extensionElement = null;
                            }
                            this.extensionInstance = t3;
                            this.initializing = false;
                            return t3;
                        } catch (Throwable th) {
                            this.initializing = false;
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw componentManager.createError("Cannot create extension (class=" + getAssignableToClassName() + Constant.AFTER_QUTO, th2, this.pluginDescriptor.getId(), null);
                    }
                } catch (ProcessCanceledException e) {
                    throw e;
                }
            } catch (ExtensionNotApplicableException unused) {
                this.extensionInstance = NOT_APPLICABLE;
                this.extensionElement = null;
                this.initializing = false;
                return null;
            }
        }
    }

    protected <T> T instantiateClass(Class<T> cls, ComponentManager componentManager) {
        if (cls == null) {
            $$$reportNull$$$0(5);
        }
        if (componentManager == null) {
            $$$reportNull$$$0(6);
        }
        T t = (T) componentManager.instantiateClass(cls, this.pluginDescriptor.getId());
        if (t == null) {
            $$$reportNull$$$0(7);
        }
        return t;
    }

    static final class SimpleConstructorInjectionAdapter extends XmlExtensionAdapter {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 6 || i == 7) ? 2 : 3];
            switch (i) {
                case 1:
                    objArr[0] = "pluginDescriptor";
                    break;
                case 2:
                    objArr[0] = "order";
                    break;
                case 3:
                    objArr[0] = "implementationClassResolver";
                    break;
                case 4:
                    objArr[0] = "aClass";
                    break;
                case 5:
                    objArr[0] = "componentManager";
                    break;
                case 6:
                case 7:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/XmlExtensionAdapter$SimpleConstructorInjectionAdapter";
                    break;
                default:
                    objArr[0] = "implementationClassName";
                    break;
            }
            if (i == 6 || i == 7) {
                objArr[1] = "instantiateClass";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/XmlExtensionAdapter$SimpleConstructorInjectionAdapter";
            }
            if (i == 4 || i == 5) {
                objArr[2] = "instantiateClass";
            } else if (i != 6 && i != 7) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 6 && i != 7) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        SimpleConstructorInjectionAdapter(String str, PluginDescriptor pluginDescriptor, String str2, LoadingOrder loadingOrder, XmlElement xmlElement, ImplementationClassResolver implementationClassResolver) {
            super(str, pluginDescriptor, str2, loadingOrder, xmlElement, implementationClassResolver);
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            if (pluginDescriptor == null) {
                $$$reportNull$$$0(1);
            }
            if (loadingOrder == null) {
                $$$reportNull$$$0(2);
            }
            if (implementationClassResolver == null) {
                $$$reportNull$$$0(3);
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.XmlExtensionAdapter
        protected <T> T instantiateClass(Class<T> cls, ComponentManager componentManager) {
            if (cls == null) {
                $$$reportNull$$$0(4);
            }
            if (componentManager == null) {
                $$$reportNull$$$0(5);
            }
            if (!cls.getName().equals(ImplUtilsKt.KOTLIN_JAVA_ELEMENT_FINDER_CLASS_NAME)) {
                try {
                    T t = (T) super.instantiateClass(cls, componentManager);
                    if (t == null) {
                        $$$reportNull$$$0(6);
                    }
                    return t;
                } catch (ExtensionNotApplicableException e) {
                    throw e;
                } catch (ProcessCanceledException e2) {
                    throw e2;
                } catch (RuntimeException e3) {
                    Throwable cause = e3.getCause();
                    if (!(cause instanceof NoSuchMethodException) && !(cause instanceof IllegalArgumentException)) {
                        throw e3;
                    }
                    ExtensionPointImpl.LOG.error("Cannot create extension without pico container (class=" + cls.getName() + ", constructors=" + Arrays.toString(cls.getDeclaredConstructors()) + "), please remove extra constructor parameters", e3);
                }
            }
            T t2 = (T) componentManager.instantiateClassWithConstructorInjection(cls, cls, this.pluginDescriptor.getId());
            if (t2 == null) {
                $$$reportNull$$$0(7);
            }
            return t2;
        }
    }
}
