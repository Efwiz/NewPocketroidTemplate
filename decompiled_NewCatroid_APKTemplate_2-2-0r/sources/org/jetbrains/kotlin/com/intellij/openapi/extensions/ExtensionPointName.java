package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionProcessingHelper;
import org.jetbrains.kotlin.com.intellij.util.ThreeState;

/* JADX INFO: loaded from: classes3.dex */
public final class ExtensionPointName<T> extends BaseExtensionPointName<T> {
    /* JADX WARN: Code duplicated, block: B:12:0x0020  */
    /* JADX WARN: Code duplicated, block: B:54:0x00af  */
    /* JADX WARN: Code duplicated, block: B:55:0x00b4  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 3 && i != 17 && i != 19 && i != 26 && i != 37 && i != 40) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 2 && i != 3 && i != 17 && i != 19 && i != 26 && i != 37 && i != 40) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
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
            case 3:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 17:
            case 19:
            case 26:
            case 37:
            case 40:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointName";
                break;
            case 4:
            case 20:
                objArr[0] = "consumer";
                break;
            case 5:
                objArr[0] = "predicate";
                break;
            case 6:
                objArr[0] = "processor";
                break;
            case 15:
            case 18:
                objArr[0] = "instanceOf";
                break;
            case 16:
                objArr[0] = "exactClass";
                break;
            case 21:
            case 22:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
            case 23:
            case 27:
            case 30:
            case 34:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 24:
            case 28:
            case 31:
            case 35:
            case 38:
                objArr[0] = "cacheId";
                break;
            case 25:
            case 29:
            case 32:
                objArr[0] = "keyMapper";
                break;
            case 33:
            case 36:
            case 39:
                objArr[0] = "valueMapper";
                break;
            default:
                objArr[0] = "name";
                break;
        }
        if (i == 2) {
            objArr[1] = "getExtensions";
        } else if (i == 3) {
            objArr[1] = "getExtensionList";
        } else if (i == 17) {
            objArr[1] = "findExtensionOrFail";
        } else if (i == 19) {
            objArr[1] = "getIterable";
        } else if (i == 26) {
            objArr[1] = "getByGroupingKey";
        } else if (i != 37 && i != 40) {
            switch (i) {
                case 7:
                case 8:
                    objArr[1] = "getExtensionsIfPointIsRegistered";
                    break;
                case 9:
                case 12:
                    objArr[1] = "extensions";
                    break;
                case 10:
                    objArr[1] = "getExtensionList";
                    break;
                case 11:
                    objArr[1] = "getExtensions";
                    break;
                case 13:
                case 14:
                    objArr[1] = "getPoint";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointName";
                    break;
            }
        } else {
            objArr[1] = "computeIfAbsent";
        }
        switch (i) {
            case 1:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            case 2:
            case 3:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 17:
            case 19:
            case 26:
            case 37:
            case 40:
                break;
            case 4:
                objArr[2] = "forEachExtensionSafe";
                break;
            case 5:
                objArr[2] = "findFirstSafe";
                break;
            case 6:
                objArr[2] = "computeSafeIfAny";
                break;
            case 15:
                objArr[2] = "findExtension";
                break;
            case 16:
                objArr[2] = "findExtensionOrFail";
                break;
            case 18:
                objArr[2] = "findFirstAssignableExtension";
                break;
            case 20:
                objArr[2] = "processWithPluginDescriptor";
                break;
            case 21:
                objArr[2] = "addExtensionPointListener";
                break;
            case 22:
                objArr[2] = "addChangeListener";
                break;
            case 23:
            case 24:
            case 25:
                objArr[2] = "getByGroupingKey";
                break;
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
                objArr[2] = "getByKey";
                break;
            case 34:
            case 35:
            case 36:
            case 38:
            case 39:
                objArr[2] = "computeIfAbsent";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 17 && i != 19 && i != 26 && i != 37 && i != 40) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExtensionPointName(String str) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    public static <T> ExtensionPointName<T> create(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return new ExtensionPointName<>(str);
    }

    public T[] getExtensions() {
        T[] extensions = getPointImpl(null).getExtensions();
        if (extensions == null) {
            $$$reportNull$$$0(2);
        }
        return extensions;
    }

    public List<T> getExtensionList() {
        List<T> extensionList = getPointImpl(null).getExtensionList();
        if (extensionList == null) {
            $$$reportNull$$$0(3);
        }
        return extensionList;
    }

    public void forEachExtensionSafe(Consumer<? super T> consumer) {
        if (consumer == null) {
            $$$reportNull$$$0(4);
        }
        ExtensionProcessingHelper.forEachExtensionSafe(getPointImpl(null), consumer);
    }

    public T findFirstSafe(Predicate<? super T> predicate) {
        if (predicate == null) {
            $$$reportNull$$$0(5);
        }
        return (T) ExtensionProcessingHelper.findFirstSafe(predicate, getPointImpl(null));
    }

    public List<T> getExtensionList(AreaInstance areaInstance) {
        List<T> extensionList = getPointImpl(areaInstance).getExtensionList();
        if (extensionList == null) {
            $$$reportNull$$$0(10);
        }
        return extensionList;
    }

    public T[] getExtensions(AreaInstance areaInstance) {
        T[] extensions = getPointImpl(areaInstance).getExtensions();
        if (extensions == null) {
            $$$reportNull$$$0(11);
        }
        return extensions;
    }

    @Deprecated
    public ExtensionPoint<T> getPoint(AreaInstance areaInstance) {
        ExtensionPointImpl<T> pointImpl = getPointImpl(areaInstance);
        if (pointImpl == null) {
            $$$reportNull$$$0(13);
        }
        return pointImpl;
    }

    public ExtensionPoint<T> getPoint() {
        ExtensionPointImpl<T> pointImpl = getPointImpl(null);
        if (pointImpl == null) {
            $$$reportNull$$$0(14);
        }
        return pointImpl;
    }

    public <V extends T> V findExtension(Class<V> cls) {
        if (cls == null) {
            $$$reportNull$$$0(15);
        }
        return (V) getPointImpl(null).findExtension(cls, false, ThreeState.UNSURE);
    }

    public Iterable<T> getIterable() {
        ExtensionPointImpl<T> pointImpl = getPointImpl(null);
        if (pointImpl == null) {
            $$$reportNull$$$0(19);
        }
        return pointImpl;
    }

    public void addExtensionPointListener(ExtensionPointListener<T> extensionPointListener, Disposable disposable) {
        if (extensionPointListener == null) {
            $$$reportNull$$$0(21);
        }
        getPointImpl(null).addExtensionPointListener(extensionPointListener, false, disposable);
    }

    public void addChangeListener(Runnable runnable, Disposable disposable) {
        if (runnable == null) {
            $$$reportNull$$$0(22);
        }
        getPointImpl(null).addChangeListener(runnable, disposable);
    }
}
