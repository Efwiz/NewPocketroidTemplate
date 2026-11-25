package org.jetbrains.kotlin.psi;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;

/* JADX INFO: compiled from: userDataUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u00020\u0004B\u0013\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\u0002\u0010\u0007J)\u0010\n\u001a\t\u0018\u00018\u0001¢\u0006\u0002\b\u000b2\u0006\u0010\f\u001a\u00028\u00002\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0086\u0002¢\u0006\u0002\u0010\u000fJ,\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00028\u00002\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e2\b\u0010\u0012\u001a\u0004\u0018\u00018\u0001H\u0086\u0002¢\u0006\u0002\u0010\u0013R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/psi/UserDataProperty;", "R", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/UserDataHolder;", ExifInterface.GPS_DIRECTION_TRUE, "", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Key;", "(Lcom/intellij/openapi/util/Key;)V", "getKey", "()Lcom/intellij/openapi/util/Key;", "getValue", "Lorg/jetbrains/annotations/Nullable;", "thisRef", "desc", "Lkotlin/reflect/KProperty;", "(Lcom/intellij/openapi/util/UserDataHolder;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "", "value", "(Lcom/intellij/openapi/util/UserDataHolder;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UserDataProperty<R extends UserDataHolder, T> {
    private final Key<T> key;

    public UserDataProperty(Key<T> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.key = key;
    }

    public final Key<T> getKey() {
        return this.key;
    }

    public final T getValue(R thisRef, KProperty<?> desc) {
        Intrinsics.checkNotNullParameter(thisRef, "thisRef");
        Intrinsics.checkNotNullParameter(desc, "desc");
        return (T) thisRef.getUserData(this.key);
    }

    public final void setValue(R thisRef, KProperty<?> desc, T t) {
        Intrinsics.checkNotNullParameter(thisRef, "thisRef");
        Intrinsics.checkNotNullParameter(desc, "desc");
        thisRef.putUserData(this.key, t);
    }
}
