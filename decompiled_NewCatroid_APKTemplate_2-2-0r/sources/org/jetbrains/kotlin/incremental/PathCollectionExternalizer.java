package org.jetbrains.kotlin.incremental;

import gnu.trove.THashSet;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;
import org.jetbrains.kotlin.incremental.storage.CollectionExternalizer;
import org.jetbrains.kotlin.incremental.storage.PathStringDescriptor;

/* JADX INFO: compiled from: IncrementalJvmCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/incremental/PathCollectionExternalizer;", "Lorg/jetbrains/kotlin/incremental/storage/CollectionExternalizer;", "", "()V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PathCollectionExternalizer extends CollectionExternalizer<String> {
    public static final PathCollectionExternalizer INSTANCE = new PathCollectionExternalizer();

    private PathCollectionExternalizer() {
        super(PathStringDescriptor.INSTANCE, new Function0<Collection<String>>() { // from class: org.jetbrains.kotlin.incremental.PathCollectionExternalizer.1
            @Override // kotlin.jvm.functions.Function0
            public final Collection<String> invoke() {
                return new THashSet(CollectionFactory.createFilePathSet());
            }
        });
    }
}
