package org.jetbrains.kotlin.com.intellij.openapi.extensions.impl;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.mgsx.gltf.data.extensions.KHRLightsPunctual;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;

/* JADX INFO: loaded from: classes3.dex */
public final class ExtensionProcessingHelper {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 10 || i == 24 || i == 28 || i == 37 || i == 41) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 10 || i == 24 || i == 28 || i == 37 || i == 41) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "extensionConsumer";
                break;
            case 2:
                objArr[0] = "predicate";
                break;
            case 3:
            case 5:
            default:
                objArr[0] = "iterable";
                break;
            case 4:
                objArr[0] = "processor";
                break;
            case 6:
            case 11:
            case 16:
            case 20:
            case 25:
            case 29:
            case 36:
            case 40:
                objArr[0] = KHRLightsPunctual.GLTFLight.TYPE_POINT;
                break;
            case 7:
            case 13:
            case 18:
            case 22:
            case 26:
            case 30:
                objArr[0] = "cacheId";
                break;
            case 8:
            case 12:
            case 17:
            case 21:
            case 31:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 9:
            case 14:
            case 19:
            case 32:
            case 35:
            case 38:
                objArr[0] = "keyMapper";
                break;
            case 10:
            case 24:
            case 28:
            case 37:
            case 41:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/ExtensionProcessingHelper";
                break;
            case 15:
            case 33:
            case 39:
                objArr[0] = "valueMapper";
                break;
            case 23:
            case 27:
                objArr[0] = "valueProducer";
                break;
            case 34:
                objArr[0] = "keyMapperToCache";
                break;
        }
        if (i == 10) {
            objArr[1] = "getByGroupingKey";
        } else if (i == 24 || i == 28) {
            objArr[1] = "computeIfAbsent";
        } else if (i == 37) {
            objArr[1] = "buildCacheForGroupingKeyMapper";
        } else if (i != 41) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/impl/ExtensionProcessingHelper";
        } else {
            objArr[1] = "buildCacheForKeyMapper";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "findFirstSafe";
                break;
            case 4:
            case 5:
                objArr[2] = "computeSafeIfAny";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = "getByGroupingKey";
                break;
            case 10:
            case 24:
            case 28:
            case 37:
            case 41:
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[2] = "getByKey";
                break;
            case 20:
            case 21:
            case 22:
            case 23:
            case 25:
            case 26:
            case 27:
                objArr[2] = "computeIfAbsent";
                break;
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
                objArr[2] = "doGetByKey";
                break;
            case 35:
            case 36:
                objArr[2] = "buildCacheForGroupingKeyMapper";
                break;
            case 38:
            case 39:
            case 40:
                objArr[2] = "buildCacheForKeyMapper";
                break;
            default:
                objArr[2] = "forEachExtensionSafe";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 10 && i != 24 && i != 28 && i != 37 && i != 41) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static <T> void forEachExtensionSafe(Iterable<? extends T> iterable, Consumer<? super T> consumer) {
        T next;
        if (iterable == null) {
            $$$reportNull$$$0(0);
        }
        if (consumer == null) {
            $$$reportNull$$$0(1);
        }
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext() && (next = it.next()) != null) {
            try {
                consumer.accept(next);
            } catch (ProcessCanceledException e) {
                throw e;
            } catch (Throwable th) {
                ExtensionPointImpl.LOG.error(th);
            }
        }
    }

    public static <T> T findFirstSafe(final Predicate<? super T> predicate, Iterable<? extends T> iterable) {
        if (predicate == null) {
            $$$reportNull$$$0(2);
        }
        if (iterable == null) {
            $$$reportNull$$$0(3);
        }
        return (T) computeSafeIfAny(new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionProcessingHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ExtensionProcessingHelper.lambda$findFirstSafe$0(predicate, obj);
            }
        }, iterable);
    }

    static /* synthetic */ Object lambda$findFirstSafe$0(Predicate predicate, Object obj) {
        if (predicate.test(obj)) {
            return obj;
        }
        return null;
    }

    public static <T, R> R computeSafeIfAny(Function<? super T, ? extends R> function, Iterable<? extends T> iterable) {
        T next;
        if (function == null) {
            $$$reportNull$$$0(4);
        }
        if (iterable == null) {
            $$$reportNull$$$0(5);
        }
        Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext() && (next = it.next()) != null) {
            try {
                R rApply = function.apply(next);
                if (rApply != null) {
                    return rApply;
                }
            } catch (ProcessCanceledException e) {
                throw e;
            } catch (Exception e2) {
                ExtensionPointImpl.LOG.error((Throwable) e2);
            }
        }
        return null;
    }
}
