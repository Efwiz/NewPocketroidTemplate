package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: ArrayMapOwner.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00022\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0004J\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u0010J;\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u0002H\u00130\u0012\"\b\b\u0002\u0010\u0013*\u00028\u00012\u0006\u0010\u0014\u001a\u00020\t2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u0001H\u0013¢\u0006\u0002\u0010\u0016JK\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u0002H\u00130\u0012\"\b\b\u0002\u0010\u0013*\u00028\u0001\"\b\b\u0003\u0010\u0017*\u00028\u00002\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00170\u00192\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u0001H\u0013¢\u0006\u0002\u0010\u001aJ.\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0002\b\u00030\u001c\"\b\b\u0002\u0010\u0017*\u00028\u00002\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0019J:\u0010\u001d\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u0002H\u00130\u001c\"\b\b\u0002\u0010\u0013*\u00028\u0001\"\b\b\u0003\u0010\u0017*\u00028\u00002\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0019J\u000e\u0010\u001e\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\tJ\u001e\u0010\u001e\u001a\u00020\n\"\b\b\u0002\u0010\u0013*\u00028\u00002\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00130\u0019J4\u0010\u001f\u001a\u00020\n*\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b2\u0006\u0010 \u001a\u00020\t2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\"H&R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\f8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/util/TypeRegistry;", "K", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "()V", "idCounter", "Ljava/util/concurrent/atomic/AtomicInteger;", "idPerType", "Ljava/util/concurrent/ConcurrentHashMap;", "", "", "indices", "", "getIndices", "()Ljava/util/Collection;", "allValuesThreadUnsafeForRendering", "", "generateAccessor", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", ExifInterface.GPS_DIRECTION_TRUE, "keyQualifiedName", "default", "(Ljava/lang/String;Ljava/lang/Object;)Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "KK", "kClass", "Lkotlin/reflect/KClass;", "(Lkotlin/reflect/KClass;Ljava/lang/Object;)Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "generateAnyNullableAccessor", "Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", "generateNullableAccessor", "getId", "customComputeIfAbsent", ASTExpr.DEFAULT_MAP_KEY_NAME, "compute", "Lkotlin/Function1;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TypeRegistry<K, V> {
    private final ConcurrentHashMap<String, Integer> idPerType = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    public abstract int customComputeIfAbsent(ConcurrentHashMap<String, Integer> concurrentHashMap, String str, Function1<? super String, Integer> function1);

    public static /* synthetic */ ArrayMapAccessor generateAccessor$default(TypeRegistry typeRegistry, KClass kClass, Object obj, int i, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: generateAccessor");
        }
        if ((i & 2) != 0) {
            obj = null;
        }
        return typeRegistry.generateAccessor(kClass, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T extends V, KK extends K> ArrayMapAccessor<K, V, T> generateAccessor(KClass<KK> kClass, T t) {
        Intrinsics.checkNotNullParameter(kClass, "kClass");
        String qualifiedName = kClass.getQualifiedName();
        Intrinsics.checkNotNull(qualifiedName);
        return new ArrayMapAccessor<>(qualifiedName, getId(kClass), t);
    }

    public static /* synthetic */ ArrayMapAccessor generateAccessor$default(TypeRegistry typeRegistry, String str, Object obj, int i, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: generateAccessor");
        }
        if ((i & 2) != 0) {
            obj = null;
        }
        return typeRegistry.generateAccessor(str, obj);
    }

    public final <T extends V> ArrayMapAccessor<K, V, T> generateAccessor(String keyQualifiedName, T t) {
        Intrinsics.checkNotNullParameter(keyQualifiedName, "keyQualifiedName");
        return new ArrayMapAccessor<>(keyQualifiedName, getId(keyQualifiedName), t);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T extends V, KK extends K> NullableArrayMapAccessor<K, V, T> generateNullableAccessor(KClass<KK> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "kClass");
        return new NullableArrayMapAccessor<>(getId(kClass));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <KK extends K> NullableArrayMapAccessor<K, V, ?> generateAnyNullableAccessor(KClass<KK> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "kClass");
        return new NullableArrayMapAccessor<>(getId(kClass));
    }

    public final <T extends K> int getId(KClass<T> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "kClass");
        String qualifiedName = kClass.getQualifiedName();
        Intrinsics.checkNotNull(qualifiedName);
        return getId(qualifiedName);
    }

    public final int getId(String keyQualifiedName) {
        Intrinsics.checkNotNullParameter(keyQualifiedName, "keyQualifiedName");
        return customComputeIfAbsent(this.idPerType, keyQualifiedName, new Function1<String, Integer>(this) { // from class: org.jetbrains.kotlin.util.TypeRegistry.getId.1
            final /* synthetic */ TypeRegistry<K, V> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final Integer invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Integer.valueOf(((TypeRegistry) this.this$0).idCounter.getAndIncrement());
            }
        });
    }

    public final Map<String, Integer> allValuesThreadUnsafeForRendering() {
        return this.idPerType;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final Collection<Integer> getIndices() {
        Collection<Integer> collectionValues = this.idPerType.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        return collectionValues;
    }
}
