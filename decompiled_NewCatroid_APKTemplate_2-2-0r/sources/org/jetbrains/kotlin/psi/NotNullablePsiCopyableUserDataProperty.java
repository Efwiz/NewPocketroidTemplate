package org.jetbrains.kotlin.psi;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: compiled from: userDataUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u00020\u0004B\u001b\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00028\u0001¢\u0006\u0002\u0010\bJ'\u0010\u000e\u001a\u00078\u0001¢\u0006\u0002\b\u000f2\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0012H\u0086\u0002¢\u0006\u0002\u0010\u0013J*\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u00122\u0006\u0010\u0016\u001a\u00028\u0001H\u0086\u0002¢\u0006\u0002\u0010\u0017R\u0013\u0010\u0007\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/psi/NotNullablePsiCopyableUserDataProperty;", "R", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", ExifInterface.GPS_DIRECTION_TRUE, "", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Key;", "defaultValue", "(Lcom/intellij/openapi/util/Key;Ljava/lang/Object;)V", "getDefaultValue", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getKey", "()Lcom/intellij/openapi/util/Key;", "getValue", "Lorg/jetbrains/annotations/Nullable;", "thisRef", "property", "Lkotlin/reflect/KProperty;", "(Lcom/intellij/psi/PsiElement;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "", "value", "(Lcom/intellij/psi/PsiElement;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NotNullablePsiCopyableUserDataProperty<R extends PsiElement, T> {
    private final T defaultValue;
    private final Key<T> key;

    public NotNullablePsiCopyableUserDataProperty(Key<T> key, T defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public final T getDefaultValue() {
        return this.defaultValue;
    }

    public final Key<T> getKey() {
        return this.key;
    }

    public final T getValue(R thisRef, KProperty<?> property) {
        Intrinsics.checkNotNullParameter(thisRef, "thisRef");
        Intrinsics.checkNotNullParameter(property, "property");
        T t = (T) thisRef.getCopyableUserData(this.key);
        if (t == null) {
            t = this.defaultValue;
        }
        Intrinsics.checkNotNull(t);
        return t;
    }

    public final void setValue(R thisRef, KProperty<?> property, T value) {
        Intrinsics.checkNotNullParameter(thisRef, "thisRef");
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(value, "value");
        Key<T> key = this.key;
        if (Intrinsics.areEqual(value, this.defaultValue)) {
            value = null;
        }
        thisRef.putCopyableUserData(key, value);
    }
}
