package org.jetbrains.kotlin.serialization.deserialization;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MetadataPartProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0001\u0006J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0004H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/MetadataPartProvider;", "", "findMetadataPackageParts", "", "", "packageFqName", "Empty", "deserialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface MetadataPartProvider {
    List<String> findMetadataPackageParts(String packageFqName);

    /* JADX INFO: compiled from: MetadataPartProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/MetadataPartProvider$Empty;", "Lorg/jetbrains/kotlin/serialization/deserialization/MetadataPartProvider;", "()V", "findMetadataPackageParts", "", "", "packageFqName", "deserialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty implements MetadataPartProvider {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
        }

        @Override // org.jetbrains.kotlin.serialization.deserialization.MetadataPartProvider
        public List<String> findMetadataPackageParts(String packageFqName) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            return CollectionsKt.emptyList();
        }
    }
}
