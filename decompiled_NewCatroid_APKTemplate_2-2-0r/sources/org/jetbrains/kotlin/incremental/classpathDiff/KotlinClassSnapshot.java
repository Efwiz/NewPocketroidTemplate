package org.jetbrains.kotlin.incremental.classpathDiff;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.incremental.KotlinClassInfo;

/* JADX INFO: compiled from: ClasspathSnapshot.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0003\u0007\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/KotlinClassSnapshot;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "()V", "classMemberLevelSnapshot", "Lorg/jetbrains/kotlin/incremental/KotlinClassInfo;", "getClassMemberLevelSnapshot", "()Lorg/jetbrains/kotlin/incremental/KotlinClassInfo;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/MultifileClassKotlinClassSnapshot;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/PackageFacadeKotlinClassSnapshot;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/RegularKotlinClassSnapshot;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinClassSnapshot extends AccessibleClassSnapshot {
    public /* synthetic */ KotlinClassSnapshot(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract KotlinClassInfo getClassMemberLevelSnapshot();

    private KotlinClassSnapshot() {
        super(null);
    }
}
