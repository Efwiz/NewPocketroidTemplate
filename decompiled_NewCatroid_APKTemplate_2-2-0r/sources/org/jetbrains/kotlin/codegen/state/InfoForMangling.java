package org.jetbrains.kotlin.codegen.state;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqNameUnsafe;

/* JADX INFO: compiled from: inlineClassManglingUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/InfoForMangling;", "", "fqName", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "isValue", "", "isNullable", "(Lorg/jetbrains/kotlin/name/FqNameUnsafe;ZZ)V", "getFqName", "()Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "()Z", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InfoForMangling {
    private final FqNameUnsafe fqName;
    private final boolean isNullable;
    private final boolean isValue;

    public InfoForMangling(FqNameUnsafe fqName, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        this.fqName = fqName;
        this.isValue = z;
        this.isNullable = z2;
    }

    public final FqNameUnsafe getFqName() {
        return this.fqName;
    }

    /* JADX INFO: renamed from: isValue, reason: from getter */
    public final boolean getIsValue() {
        return this.isValue;
    }

    /* JADX INFO: renamed from: isNullable, reason: from getter */
    public final boolean getIsNullable() {
        return this.isNullable;
    }
}
