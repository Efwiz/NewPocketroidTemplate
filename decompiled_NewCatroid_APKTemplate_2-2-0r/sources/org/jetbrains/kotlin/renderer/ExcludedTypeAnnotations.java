package org.jetbrains.kotlin.renderer;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: DescriptorRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/renderer/ExcludedTypeAnnotations;", "", "()V", "internalAnnotationsForResolve", "", "Lorg/jetbrains/kotlin/name/FqName;", "getInternalAnnotationsForResolve", "()Ljava/util/Set;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExcludedTypeAnnotations {
    public static final ExcludedTypeAnnotations INSTANCE = new ExcludedTypeAnnotations();
    private static final Set<FqName> internalAnnotationsForResolve = SetsKt.setOf((Object[]) new FqName[]{new FqName("kotlin.internal.NoInfer"), new FqName("kotlin.internal.Exact")});

    private ExcludedTypeAnnotations() {
    }

    public final Set<FqName> getInternalAnnotationsForResolve() {
        return internalAnnotationsForResolve;
    }
}
