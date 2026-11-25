package org.jetbrains.kotlin.kotlinx.collections.immutable;

import java.util.Collection;
import kotlin.jvm.internal.markers.KMutableCollection;

/* JADX INFO: loaded from: classes5.dex */
public interface PersistentCollection extends ImmutableCollection {

    public interface Builder extends Collection, KMutableCollection {
    }
}
