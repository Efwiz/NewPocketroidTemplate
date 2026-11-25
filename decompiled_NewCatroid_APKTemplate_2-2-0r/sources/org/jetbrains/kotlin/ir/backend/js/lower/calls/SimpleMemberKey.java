package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: CallsLoweringUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/SimpleMemberKey;", "", "klass", "Lorg/jetbrains/kotlin/ir/types/IrType;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/name/Name;)V", "getKlass", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SimpleMemberKey {
    private final IrType klass;
    private final Name name;

    public static /* synthetic */ SimpleMemberKey copy$default(SimpleMemberKey simpleMemberKey, IrType irType, Name name2, int i, Object obj) {
        if ((i & 1) != 0) {
            irType = simpleMemberKey.klass;
        }
        if ((i & 2) != 0) {
            name2 = simpleMemberKey.name;
        }
        return simpleMemberKey.copy(irType, name2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final IrType getKlass() {
        return this.klass;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Name getName() {
        return this.name;
    }

    public final SimpleMemberKey copy(IrType klass, Name name2) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(name2, "name");
        return new SimpleMemberKey(klass, name2);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SimpleMemberKey)) {
            return false;
        }
        SimpleMemberKey simpleMemberKey = (SimpleMemberKey) other;
        return Intrinsics.areEqual(this.klass, simpleMemberKey.klass) && Intrinsics.areEqual(this.name, simpleMemberKey.name);
    }

    public int hashCode() {
        return (this.klass.hashCode() * 31) + this.name.hashCode();
    }

    public String toString() {
        return "SimpleMemberKey(klass=" + this.klass + ", name=" + this.name + ')';
    }

    public SimpleMemberKey(IrType klass, Name name2) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.klass = klass;
        this.name = name2;
    }

    public final IrType getKlass() {
        return this.klass;
    }

    public final Name getName() {
        return this.name;
    }
}
