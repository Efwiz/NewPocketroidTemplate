package org.jetbrains.kotlin.fir.declarations.utils;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: declarationAttributes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/utils/DanglingTypeConstraint;", "", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/KtSourceElement;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DanglingTypeConstraint {
    private final Name name;
    private final KtSourceElement source;

    public static /* synthetic */ DanglingTypeConstraint copy$default(DanglingTypeConstraint danglingTypeConstraint, Name name2, KtSourceElement ktSourceElement, int i, Object obj) {
        if ((i & 1) != 0) {
            name2 = danglingTypeConstraint.name;
        }
        if ((i & 2) != 0) {
            ktSourceElement = danglingTypeConstraint.source;
        }
        return danglingTypeConstraint.copy(name2, ktSourceElement);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Name getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final KtSourceElement getSource() {
        return this.source;
    }

    public final DanglingTypeConstraint copy(Name name2, KtSourceElement source) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(source, "source");
        return new DanglingTypeConstraint(name2, source);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DanglingTypeConstraint)) {
            return false;
        }
        DanglingTypeConstraint danglingTypeConstraint = (DanglingTypeConstraint) other;
        return Intrinsics.areEqual(this.name, danglingTypeConstraint.name) && Intrinsics.areEqual(this.source, danglingTypeConstraint.source);
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + this.source.hashCode();
    }

    public String toString() {
        return "DanglingTypeConstraint(name=" + this.name + ", source=" + this.source + ')';
    }

    public DanglingTypeConstraint(Name name2, KtSourceElement source) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(source, "source");
        this.name = name2;
        this.source = source;
    }

    public final Name getName() {
        return this.name;
    }

    public final KtSourceElement getSource() {
        return this.source;
    }
}
