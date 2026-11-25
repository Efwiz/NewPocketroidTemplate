package org.jetbrains.kotlin.serialization.deserialization;

import kotlin.Metadata;

/* JADX INFO: compiled from: EnumEntriesDeserializationSupport.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0001\u0005J\u000f\u0010\u0002\u001a\u0004\u0018\u00010\u0003H&¢\u0006\u0002\u0010\u0004ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/EnumEntriesDeserializationSupport;", "", "canSynthesizeEnumEntries", "", "()Ljava/lang/Boolean;", "Default", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface EnumEntriesDeserializationSupport {
    Boolean canSynthesizeEnumEntries();

    /* JADX INFO: compiled from: EnumEntriesDeserializationSupport.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/EnumEntriesDeserializationSupport$Default;", "Lorg/jetbrains/kotlin/serialization/deserialization/EnumEntriesDeserializationSupport;", "()V", "canSynthesizeEnumEntries", "", "()Ljava/lang/Boolean;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements EnumEntriesDeserializationSupport {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.serialization.deserialization.EnumEntriesDeserializationSupport
        public Boolean canSynthesizeEnumEntries() {
            return null;
        }

        private Default() {
        }
    }
}
