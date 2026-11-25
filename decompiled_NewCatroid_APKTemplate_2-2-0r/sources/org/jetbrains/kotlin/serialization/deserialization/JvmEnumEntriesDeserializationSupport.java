package org.jetbrains.kotlin.serialization.deserialization;

import kotlin.Metadata;

/* JADX INFO: compiled from: EnumEntriesDeserializationSupport.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\r\u0010\u0003\u001a\u00020\u0004H\u0016¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/JvmEnumEntriesDeserializationSupport;", "Lorg/jetbrains/kotlin/serialization/deserialization/EnumEntriesDeserializationSupport;", "()V", "canSynthesizeEnumEntries", "", "()Ljava/lang/Boolean;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmEnumEntriesDeserializationSupport implements EnumEntriesDeserializationSupport {
    public static final JvmEnumEntriesDeserializationSupport INSTANCE = new JvmEnumEntriesDeserializationSupport();

    private JvmEnumEntriesDeserializationSupport() {
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.EnumEntriesDeserializationSupport
    public Boolean canSynthesizeEnumEntries() {
        return true;
    }
}
