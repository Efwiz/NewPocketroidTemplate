package org.jetbrains.kotlin.types;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.bytebuddy.implementation.MethodDelegation;

/* JADX INFO: compiled from: SmartcastStability.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/types/SmartcastStability;", "", "str", "", MediaTrack.ROLE_DESCRIPTION, "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "STABLE_VALUE", "PROPERTY_WITH_GETTER", "ALIEN_PUBLIC_PROPERTY", "CAPTURED_VARIABLE", "MUTABLE_PROPERTY", "DELEGATED_PROPERTY", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum SmartcastStability {
    STABLE_VALUE("stable val", null, 2, null),
    PROPERTY_WITH_GETTER("custom getter", "property that has open or custom getter"),
    ALIEN_PUBLIC_PROPERTY("alien public", "public API property declared in different module"),
    CAPTURED_VARIABLE("captured var", "local variable that is captured by a changing closure"),
    MUTABLE_PROPERTY("member", "mutable property that could have been changed by this time"),
    DELEGATED_PROPERTY(MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "delegated property that could have been changed by this time");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String description;
    private final String str;

    public static EnumEntries<SmartcastStability> getEntries() {
        return $ENTRIES;
    }

    SmartcastStability(String str, String str2) {
        this.str = str;
        this.description = str2;
    }

    /* synthetic */ SmartcastStability(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? str : str2);
    }

    public final String getDescription() {
        return this.description;
    }
}
