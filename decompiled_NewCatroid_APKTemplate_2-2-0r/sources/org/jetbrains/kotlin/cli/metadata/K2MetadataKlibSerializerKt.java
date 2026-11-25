package org.jetbrains.kotlin.cli.metadata;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.DefaultBuiltIns;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.library.metadata.KlibMetadataFactories;
import org.jetbrains.kotlin.library.metadata.NullFlexibleTypeDeserializer;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: K2MetadataKlibSerializer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0002"}, d2 = {"MetadataFactories", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataFactories;", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class K2MetadataKlibSerializerKt {
    private static final KlibMetadataFactories MetadataFactories = new KlibMetadataFactories(new Function1<StorageManager, KotlinBuiltIns>() { // from class: org.jetbrains.kotlin.cli.metadata.K2MetadataKlibSerializerKt$MetadataFactories$1
        @Override // kotlin.jvm.functions.Function1
        public final KotlinBuiltIns invoke(StorageManager it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return DefaultBuiltIns.Companion.getInstance();
        }
    }, NullFlexibleTypeDeserializer.INSTANCE);
}
