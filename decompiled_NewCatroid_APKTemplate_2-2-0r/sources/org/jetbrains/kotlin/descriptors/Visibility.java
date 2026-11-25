package org.jetbrains.kotlin.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: Visibility.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0017\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0017\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0000H\u0016¢\u0006\u0002\u0010\u0011J\n\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0005H&J\b\u0010\u0015\u001a\u00020\u0000H\u0016J\u0006\u0010\u0016\u001a\u00020\u0003J\u0018\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0016R\u0014\u0010\u0007\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\t¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/Visibility;", "", "name", "", "isPublicAPI", "", "(Ljava/lang/String;Z)V", "externalDisplayName", "getExternalDisplayName", "()Ljava/lang/String;", "internalDisplayName", "getInternalDisplayName", "()Z", "getName", "compareTo", "", "visibility", "(Lorg/jetbrains/kotlin/descriptors/Visibility;)Ljava/lang/Integer;", "customEffectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "mustCheckInImports", "normalize", "toString", "visibleFromPackage", "fromPackage", "Lorg/jetbrains/kotlin/name/FqName;", "myPackage", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class Visibility {
    private final boolean isPublicAPI;
    private final String name;

    public EffectiveVisibility customEffectiveVisibility() {
        return null;
    }

    public abstract boolean mustCheckInImports();

    public Visibility normalize() {
        return this;
    }

    public boolean visibleFromPackage(FqName fromPackage, FqName myPackage) {
        Intrinsics.checkNotNullParameter(fromPackage, "fromPackage");
        Intrinsics.checkNotNullParameter(myPackage, "myPackage");
        return true;
    }

    protected Visibility(String name2, boolean z) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
        this.isPublicAPI = z;
    }

    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: isPublicAPI, reason: from getter */
    public final boolean getIsPublicAPI() {
        return this.isPublicAPI;
    }

    /* JADX INFO: renamed from: getInternalDisplayName, reason: from getter */
    public String getName() {
        return this.name;
    }

    public String getExternalDisplayName() {
        return getName();
    }

    public Integer compareTo(Visibility visibility) {
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        return Visibilities.INSTANCE.compareLocal$compiler_common(this, visibility);
    }

    public final String toString() {
        return getName();
    }
}
