package org.jetbrains.kotlin.serialization.deserialization;

import kotlin.Metadata;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: LocalClassifierTypeSettings.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0001\u0006R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/LocalClassifierTypeSettings;", "", "replacementTypeForLocalClassifiers", "Lorg/jetbrains/kotlin/types/SimpleType;", "getReplacementTypeForLocalClassifiers", "()Lorg/jetbrains/kotlin/types/SimpleType;", "Default", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LocalClassifierTypeSettings {
    SimpleType getReplacementTypeForLocalClassifiers();

    /* JADX INFO: compiled from: LocalClassifierTypeSettings.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/LocalClassifierTypeSettings$Default;", "Lorg/jetbrains/kotlin/serialization/deserialization/LocalClassifierTypeSettings;", "()V", "replacementTypeForLocalClassifiers", "Lorg/jetbrains/kotlin/types/SimpleType;", "getReplacementTypeForLocalClassifiers", "()Lorg/jetbrains/kotlin/types/SimpleType;", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements LocalClassifierTypeSettings {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.serialization.deserialization.LocalClassifierTypeSettings
        public SimpleType getReplacementTypeForLocalClassifiers() {
            return null;
        }

        private Default() {
        }
    }
}
