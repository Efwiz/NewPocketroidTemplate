package org.jetbrains.kotlin.fir.resolve.transformers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirTypeResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0013\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0007HÆ\u0003J'\u0010\u0015\u001a\u00020\u00002\b\b\u0003\u0010\u0002\u001a\u00020\u00032\b\b\u0003\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00072\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0005HÖ\u0001R\u001c\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u000f\u0010\n\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/Foo;", "", "p1", "", "p2", "", "p3", "", "(ILjava/lang/String;Z)V", "getP1$annotations", "()V", "getP1", "()I", "getP2", "()Ljava/lang/String;", "getP3$annotations", "getP3", "()Z", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "toString", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Foo {
    private final int p1;
    private final String p2;
    private final boolean p3;

    public static /* synthetic */ Foo copy$default(Foo foo, int i, String str, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = foo.p1;
        }
        if ((i2 & 2) != 0) {
            str = foo.p2;
        }
        if ((i2 & 4) != 0) {
            z = foo.p3;
        }
        return foo.copy(i, str, z);
    }

    @Prop
    public static /* synthetic */ void getP1$annotations() {
    }

    @Both
    @NoTarget
    public static /* synthetic */ void getP3$annotations() {
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getP1() {
        return this.p1;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getP2() {
        return this.p2;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getP3() {
        return this.p3;
    }

    public final Foo copy(@Both @Param @NoTarget int p1, @Both @NoTarget String p2, boolean p3) {
        Intrinsics.checkNotNullParameter(p2, "p2");
        return new Foo(p1, p2, p3);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Foo)) {
            return false;
        }
        Foo foo = (Foo) other;
        return this.p1 == foo.p1 && Intrinsics.areEqual(this.p2, foo.p2) && this.p3 == foo.p3;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.p1) * 31) + this.p2.hashCode()) * 31) + Boolean.hashCode(this.p3);
    }

    public String toString() {
        return "Foo(p1=" + this.p1 + ", p2=" + this.p2 + ", p3=" + this.p3 + ')';
    }

    public Foo(@Both @Param @NoTarget int i, @Both @NoTarget String p2, boolean z) {
        Intrinsics.checkNotNullParameter(p2, "p2");
        this.p1 = i;
        this.p2 = p2;
        this.p3 = z;
    }

    public final int getP1() {
        return this.p1;
    }

    public final String getP2() {
        return this.p2;
    }

    public final boolean getP3() {
        return this.p3;
    }
}
