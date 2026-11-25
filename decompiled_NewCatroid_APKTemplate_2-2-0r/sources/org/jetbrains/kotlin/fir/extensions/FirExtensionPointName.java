package org.jetbrains.kotlin.fir.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\t\u001a\u00020\u0005HÆ\u0003J\u0013\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "", "name", "", "(Ljava/lang/String;)V", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class FirExtensionPointName {
    private final Name name;

    public static /* synthetic */ FirExtensionPointName copy$default(FirExtensionPointName firExtensionPointName, Name name2, int i, Object obj) {
        if ((i & 1) != 0) {
            name2 = firExtensionPointName.name;
        }
        return firExtensionPointName.copy(name2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Name getName() {
        return this.name;
    }

    public final FirExtensionPointName copy(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return new FirExtensionPointName(name2);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof FirExtensionPointName) && Intrinsics.areEqual(this.name, ((FirExtensionPointName) other).name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return "FirExtensionPointName(name=" + this.name + ')';
    }

    public FirExtensionPointName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
    }

    public final Name getName() {
        return this.name;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public FirExtensionPointName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        this(nameIdentifier);
    }
}
