package org.jetbrains.kotlin.backend.common;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KMutableProperty0;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;

/* JADX INFO: compiled from: Mappings.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a/\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0005¢\u0006\u0002\u0010\u0006\u001aA\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0007*\u00020\b\"\u0004\b\u0001\u0010\u0001*\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\u00010\t2\u0006\u0010\n\u001a\u0002H\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0005¢\u0006\u0002\u0010\u000b¨\u0006\f"}, d2 = {"getOrPut", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "Lkotlin/reflect/KMutableProperty0;", "fn", "Lkotlin/Function0;", "(Lkotlin/reflect/KMutableProperty0;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "K", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MappingsKt {
    public static final <V> V getOrPut(KMutableProperty0<V> kMutableProperty0, Function0<? extends V> fn) {
        Intrinsics.checkNotNullParameter(kMutableProperty0, "<this>");
        Intrinsics.checkNotNullParameter(fn, "fn");
        V v = kMutableProperty0.get();
        if (v != null) {
            return v;
        }
        V vInvoke = fn.invoke();
        kMutableProperty0.set(vInvoke);
        return vInvoke;
    }

    public static final <K extends IrDeclaration, V> V getOrPut(Mapping.Delegate<K, V> delegate, K key, Function0<? extends V> fn) {
        Intrinsics.checkNotNullParameter(delegate, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(fn, "fn");
        V v = delegate.get(key);
        if (v != null) {
            return v;
        }
        V vInvoke = fn.invoke();
        delegate.set(key, vInvoke);
        return vInvoke;
    }
}
