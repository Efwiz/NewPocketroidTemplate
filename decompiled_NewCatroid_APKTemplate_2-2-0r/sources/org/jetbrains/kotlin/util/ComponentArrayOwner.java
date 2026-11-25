package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: ComponentArrayOwner.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0004B\u0005¢\u0006\u0002\u0010\u0005J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000bH\u0002J\u001e\u0010\u000f\u001a\u00028\u00012\u000e\u0010\u0010\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u0011H\u0084\u0002¢\u0006\u0002\u0010\u0012J\u001d\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00028\u0001H\u0004¢\u0006\u0002\u0010\u0016R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/util/ComponentArrayOwner;", "K", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/util/AbstractArrayMapOwner;", "()V", "arrayMap", "Lorg/jetbrains/kotlin/util/ArrayMap;", "getArrayMap", "()Lorg/jetbrains/kotlin/util/ArrayMap;", "createDiagnosticMessage", "", "id", "", "keyQualifiedName", "get", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "(Lkotlin/reflect/KClass;)Ljava/lang/Object;", "registerComponent", "", "value", "(Ljava/lang/String;Ljava/lang/Object;)V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ComponentArrayOwner<K, V> extends AbstractArrayMapOwner<K, V> {
    private final ArrayMap<V> arrayMap = new ArrayMapImpl();

    @Override // org.jetbrains.kotlin.util.AbstractArrayMapOwner
    protected final ArrayMap<V> getArrayMap() {
        return this.arrayMap;
    }

    @Override // org.jetbrains.kotlin.util.AbstractArrayMapOwner
    protected final void registerComponent(String keyQualifiedName, V value) {
        Intrinsics.checkNotNullParameter(keyQualifiedName, "keyQualifiedName");
        Intrinsics.checkNotNullParameter(value, "value");
        int id2 = getTypeRegistry().getId(keyQualifiedName);
        try {
            this.arrayMap.set(id2, value);
        } catch (Exception e) {
            throw new RuntimeException(createDiagnosticMessage(id2, keyQualifiedName), e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final V get(KClass<? extends K> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        int id2 = getTypeRegistry().getId(key);
        V v = this.arrayMap.get(id2);
        if (v != null) {
            return v;
        }
        throw new IllegalStateException(("No '" + key + "'(" + id2 + ") component in array: " + this).toString());
    }

    private final String createDiagnosticMessage(int id2, String keyQualifiedName) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbAppend = sb.append("Error occurred during registration of component in array");
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
        StringBuilder sbAppend2 = sb.append("Currently registered");
        Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend2.append('\n'), "append(...)");
        StringBuilder sbAppend3 = sb.append("  " + id2 + ": " + keyQualifiedName);
        Intrinsics.checkNotNullExpressionValue(sbAppend3, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend3.append('\n'), "append(...)");
        StringBuilder sbAppend4 = sb.append("Registrar:");
        Intrinsics.checkNotNullExpressionValue(sbAppend4, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend4.append('\n'), "append(...)");
        for (Map.Entry<String, Integer> entry : getTypeRegistry().allValuesThreadUnsafeForRendering().entrySet()) {
            StringBuilder sbAppend5 = sb.append("  " + entry.getValue().intValue() + ": " + entry.getKey());
            Intrinsics.checkNotNullExpressionValue(sbAppend5, "append(...)");
            Intrinsics.checkNotNullExpressionValue(sbAppend5.append('\n'), "append(...)");
        }
        StringBuilder sbAppend6 = sb.append("Array map:");
        Intrinsics.checkNotNullExpressionValue(sbAppend6, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend6.append('\n'), "append(...)");
        int size = this.arrayMap.getSize();
        for (int i = 0; i < size; i++) {
            Object orCreateKotlinClass = this.arrayMap.get(i);
            if (orCreateKotlinClass != null) {
                orCreateKotlinClass = Reflection.getOrCreateKotlinClass(orCreateKotlinClass.getClass());
            }
            StringBuilder sbAppend7 = sb.append("  " + i + ": " + orCreateKotlinClass);
            Intrinsics.checkNotNullExpressionValue(sbAppend7, "append(...)");
            Intrinsics.checkNotNullExpressionValue(sbAppend7.append('\n'), "append(...)");
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
