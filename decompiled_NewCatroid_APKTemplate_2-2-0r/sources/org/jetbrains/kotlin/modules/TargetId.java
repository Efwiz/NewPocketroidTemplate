package org.jetbrains.kotlin.modules;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: TargetId.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/modules/TargetId;", "Ljava/io/Serializable;", "name", "", ModuleXmlParser.TYPE, "(Ljava/lang/String;Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "getType", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class TargetId implements Serializable {
    private final String name;
    private final String type;

    public static /* synthetic */ TargetId copy$default(TargetId targetId, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = targetId.name;
        }
        if ((i & 2) != 0) {
            str2 = targetId.type;
        }
        return targetId.copy(str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getType() {
        return this.type;
    }

    public final TargetId copy(String name2, String type) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        return new TargetId(name2, type);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TargetId)) {
            return false;
        }
        TargetId targetId = (TargetId) other;
        return Intrinsics.areEqual(this.name, targetId.name) && Intrinsics.areEqual(this.type, targetId.type);
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + this.type.hashCode();
    }

    public String toString() {
        return "TargetId(name=" + this.name + ", type=" + this.type + ')';
    }

    public TargetId(String name2, String type) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        this.name = name2;
        this.type = type;
    }

    public final String getName() {
        return this.name;
    }

    public final String getType() {
        return this.type;
    }
}
