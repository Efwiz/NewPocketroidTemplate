package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SmartExtensionPoint<Extension, V> {
    private volatile List<V> myCache;
    private final Collection<V> myExplicitExtensions;
    private volatile ExtensionPoint<Extension> myExtensionPoint;
    private final ExtensionPointAndAreaListener<Extension> myExtensionPointAndAreaListener;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = SchemaSymbols.ATTVAL_EXTENSION;
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/SmartExtensionPoint";
        } else {
            objArr[0] = "explicitExtensions";
        }
        if (i == 3 || i == 4 || i == 5) {
            objArr[1] = "getExtensions";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/SmartExtensionPoint";
        }
        if (i == 1) {
            objArr[2] = "addExplicitExtension";
        } else if (i == 2) {
            objArr[2] = "removeExplicitExtension";
        } else if (i != 3 && i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected abstract V getExtension(Extension extension);

    protected abstract ExtensionPoint<Extension> getExtensionPoint();

    protected SmartExtensionPoint(Collection<V> collection) {
        if (collection == null) {
            $$$reportNull$$$0(0);
        }
        this.myExplicitExtensions = collection;
        this.myExtensionPointAndAreaListener = new ExtensionPointAdapter<Extension>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.SmartExtensionPoint.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "oldArea", "org/jetbrains/kotlin/com/intellij/openapi/extensions/SmartExtensionPoint$1", "areaReplaced"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointAndAreaListener
            public void areaReplaced(ExtensionsArea extensionsArea) {
                if (extensionsArea == null) {
                    $$$reportNull$$$0(0);
                }
                dropCache();
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointAdapter
            public void extensionListChanged() {
                dropCache();
            }

            private void dropCache() {
                if (SmartExtensionPoint.this.myCache == null) {
                    return;
                }
                synchronized (SmartExtensionPoint.this.myExplicitExtensions) {
                    if (SmartExtensionPoint.this.myCache != null) {
                        SmartExtensionPoint.this.myCache = null;
                        ExtensionPoint extensionPoint = SmartExtensionPoint.this.myExtensionPoint;
                        if (extensionPoint != null) {
                            extensionPoint.m6481xfe024b65(this);
                            SmartExtensionPoint.this.myExtensionPoint = null;
                        }
                    }
                }
            }
        };
    }

    public final void addExplicitExtension(V v) {
        if (v == null) {
            $$$reportNull$$$0(1);
        }
        synchronized (this.myExplicitExtensions) {
            this.myExplicitExtensions.add(v);
            this.myCache = null;
        }
    }

    public final void removeExplicitExtension(V v) {
        if (v == null) {
            $$$reportNull$$$0(2);
        }
        synchronized (this.myExplicitExtensions) {
            this.myExplicitExtensions.remove(v);
            this.myCache = null;
        }
    }

    public final List<V> getExtensions() {
        List<V> list = this.myCache;
        if (list != null) {
            if (list == null) {
                $$$reportNull$$$0(3);
            }
            return list;
        }
        ExtensionPoint<Extension> extensionPoint = this.myExtensionPoint;
        if (extensionPoint == null) {
            extensionPoint = getExtensionPoint();
            this.myExtensionPoint = extensionPoint;
        }
        List listMapNotNull = ContainerUtil.mapNotNull(extensionPoint.getExtensionList(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.SmartExtensionPoint$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.getExtension(obj);
            }
        });
        synchronized (this.myExplicitExtensions) {
            List<V> list2 = this.myCache;
            if (list2 != null) {
                if (list2 == null) {
                    $$$reportNull$$$0(4);
                }
                return list2;
            }
            extensionPoint.addExtensionPointListener(this.myExtensionPointAndAreaListener, false, null);
            ArrayList arrayList = new ArrayList(this.myExplicitExtensions.size() + listMapNotNull.size());
            arrayList.addAll(this.myExplicitExtensions);
            arrayList.addAll(listMapNotNull);
            this.myCache = arrayList;
            return arrayList;
        }
    }
}
