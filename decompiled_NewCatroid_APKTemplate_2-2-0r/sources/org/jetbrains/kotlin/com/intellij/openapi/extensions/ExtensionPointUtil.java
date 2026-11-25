package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;

/* JADX INFO: loaded from: classes3.dex */
public final class ExtensionPointUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 10 || i == 13 || i == 16) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 10 || i == 13 || i == 16) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
                objArr[0] = "extensionPointName";
                break;
            case 2:
            case 10:
            case 13:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointUtil";
                break;
            case 3:
            case 5:
            case 7:
            case 11:
            case 14:
                objArr[0] = "extensionObject";
                break;
            case 6:
            case 8:
            case 12:
            case 15:
                objArr[0] = "extensionPoint";
                break;
            case 9:
                objArr[0] = "removePredicate";
                break;
            default:
                objArr[0] = "lazyValue";
                break;
        }
        if (i == 2) {
            objArr[1] = "dropLazyValueOnChange";
        } else if (i == 10) {
            objArr[1] = "createExtensionDisposable";
        } else if (i == 13) {
            objArr[1] = "createKeyedExtensionDisposable";
        } else if (i != 16) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointUtil";
        } else {
            objArr[1] = "createDisposable";
        }
        switch (i) {
            case 2:
            case 10:
            case 13:
            case 16:
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = "createExtensionDisposable";
                break;
            case 11:
            case 12:
                objArr[2] = "createKeyedExtensionDisposable";
                break;
            case 14:
            case 15:
                objArr[2] = "createDisposable";
                break;
            default:
                objArr[2] = "dropLazyValueOnChange";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 10 && i != 13 && i != 16) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ boolean lambda$createExtensionDisposable$0(Object obj, Object obj2) {
        return obj2 == obj;
    }

    public static <T> Disposable createExtensionDisposable(final T t, ExtensionPoint<T> extensionPoint) {
        if (t == null) {
            $$$reportNull$$$0(5);
        }
        if (extensionPoint == null) {
            $$$reportNull$$$0(6);
        }
        return createExtensionDisposable(t, extensionPoint, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ExtensionPointUtil.lambda$createExtensionDisposable$0(t, obj);
            }
        });
    }

    public static <T, U> Disposable createExtensionDisposable(T t, ExtensionPoint<U> extensionPoint, final Predicate<? super U> predicate) {
        if (t == null) {
            $$$reportNull$$$0(7);
        }
        if (extensionPoint == null) {
            $$$reportNull$$$0(8);
        }
        if (predicate == null) {
            $$$reportNull$$$0(9);
        }
        final Disposable disposableCreateDisposable = createDisposable(t, extensionPoint);
        extensionPoint.addExtensionPointListener(new ExtensionPointListener<U>() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointUtil.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "removedExtension";
                } else {
                    objArr[0] = "pluginDescriptor";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointUtil$1";
                objArr[2] = "extensionRemoved";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointListener
            public void extensionRemoved(U u, PluginDescriptor pluginDescriptor) {
                if (u == null) {
                    $$$reportNull$$$0(0);
                }
                if (pluginDescriptor == null) {
                    $$$reportNull$$$0(1);
                }
                if (predicate.test(u)) {
                    Disposer.dispose(disposableCreateDisposable);
                }
            }
        }, false, disposableCreateDisposable);
        if (disposableCreateDisposable == null) {
            $$$reportNull$$$0(10);
        }
        return disposableCreateDisposable;
    }

    private static <T> Disposable createDisposable(T t, ExtensionPoint<?> extensionPoint) {
        if (t == null) {
            $$$reportNull$$$0(14);
        }
        if (extensionPoint == null) {
            $$$reportNull$$$0(15);
        }
        Disposable disposableNewDisposable = Disposer.newDisposable("Disposable for [" + t + "]");
        Disposer.register(((ExtensionPointImpl) extensionPoint).getComponentManager(), disposableNewDisposable);
        if (disposableNewDisposable == null) {
            $$$reportNull$$$0(16);
        }
        return disposableNewDisposable;
    }
}
