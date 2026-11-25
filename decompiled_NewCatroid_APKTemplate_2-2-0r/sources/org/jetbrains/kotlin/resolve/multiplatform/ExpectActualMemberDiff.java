package org.jetbrains.kotlin.resolve.multiplatform;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ExpectActualMemberDiff.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0001*\u0006\b\u0001\u0010\u0002 \u00012\u00020\u0003:\u0001\u001bB\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00028\u0001¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J\u000e\u0010\u0010\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\nJ\u000e\u0010\u0011\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\nJ8\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00028\u00002\b\b\u0002\u0010\u0007\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u0013J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0003HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0007\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualMemberDiff;", "M", "C", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualMemberDiff$Kind;", "actualMember", "expectClass", "(Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualMemberDiff$Kind;Ljava/lang/Object;Ljava/lang/Object;)V", "getActualMember", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getExpectClass", "getKind", "()Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualMemberDiff$Kind;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualMemberDiff$Kind;Ljava/lang/Object;Ljava/lang/Object;)Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualMemberDiff;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", Namer.CLASS_KIND_ENUM, "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ExpectActualMemberDiff<M, C> {
    private final M actualMember;
    private final C expectClass;
    private final Kind kind;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ExpectActualMemberDiff copy$default(ExpectActualMemberDiff expectActualMemberDiff, Kind kind, Object obj, Object obj2, int i, Object obj3) {
        if ((i & 1) != 0) {
            kind = expectActualMemberDiff.kind;
        }
        if ((i & 2) != 0) {
            obj = expectActualMemberDiff.actualMember;
        }
        if ((i & 4) != 0) {
            obj2 = expectActualMemberDiff.expectClass;
        }
        return expectActualMemberDiff.copy(kind, obj, obj2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Kind getKind() {
        return this.kind;
    }

    public final M component2() {
        return this.actualMember;
    }

    public final C component3() {
        return this.expectClass;
    }

    public final ExpectActualMemberDiff<M, C> copy(Kind kind, M actualMember, C expectClass) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        return new ExpectActualMemberDiff<>(kind, actualMember, expectClass);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExpectActualMemberDiff)) {
            return false;
        }
        ExpectActualMemberDiff expectActualMemberDiff = (ExpectActualMemberDiff) other;
        return this.kind == expectActualMemberDiff.kind && Intrinsics.areEqual(this.actualMember, expectActualMemberDiff.actualMember) && Intrinsics.areEqual(this.expectClass, expectActualMemberDiff.expectClass);
    }

    public int hashCode() {
        int iHashCode = this.kind.hashCode() * 31;
        M m = this.actualMember;
        int iHashCode2 = (iHashCode + (m == null ? 0 : m.hashCode())) * 31;
        C c = this.expectClass;
        return iHashCode2 + (c != null ? c.hashCode() : 0);
    }

    public String toString() {
        return "ExpectActualMemberDiff(kind=" + this.kind + ", actualMember=" + this.actualMember + ", expectClass=" + this.expectClass + ')';
    }

    public ExpectActualMemberDiff(Kind kind, M m, C c) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.kind = kind;
        this.actualMember = m;
        this.expectClass = c;
    }

    public final M getActualMember() {
        return this.actualMember;
    }

    public final C getExpectClass() {
        return this.expectClass;
    }

    public final Kind getKind() {
        return this.kind;
    }

    /* JADX INFO: compiled from: ExpectActualMemberDiff.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualMemberDiff$Kind;", "", "rawMessage", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getRawMessage", "()Ljava/lang/String;", "ReturnTypeChangedInOverride", "ModalityChangedInOverride", "VisibilityChangedInOverride", "SetterVisibilityChangedInOverride", "ParameterNameChangedInOverride", "PropertyKindChangedInOverride", "LateinitChangedInOverride", "VarargChangedInOverride", "TypeParameterNamesChangedInOverride", "Unknown", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Kind {
        ReturnTypeChangedInOverride("{0}: the return type of this member must be the same in the expect class and the actual class. This error happens because the expect class ''{1}'' is non-final"),
        ModalityChangedInOverride("{0}: the modality of this member must be the same in the expect class and the actual class. This error happens because the expect class ''{1}'' is non-final"),
        VisibilityChangedInOverride("{0}: the visibility of this member must be the same in the expect class and the actual class. This error happens because the expect class ''{1}'' is non-final"),
        SetterVisibilityChangedInOverride("{0}: the setter visibility of this member must be the same in the expect class and the actual class. This error happens because the expect class ''{1}'' is non-final"),
        ParameterNameChangedInOverride("{0}: the parameter names of this member must be the same in the expect class and the actual class. This error happens because the expect class ''{1}'' is non-final"),
        PropertyKindChangedInOverride("{0}: the property kind (val vs var) of this member must be the same in the expect class and the actual class. This error happens because the expect class ''{1}'' is non-final"),
        LateinitChangedInOverride("{0}: the property modifiers (lateinit) of this member must be the same in the expect class and the actual class. This error happens because the expect class ''{1}'' is non-final"),
        VarargChangedInOverride("{0}: the parameter modifiers (vararg) of this member must be the same in the expect class and the actual class. This error happens because the expect class ''{1}'' is non-final"),
        TypeParameterNamesChangedInOverride("{0}: the type parameter names of this member must be the same in the expect class and the actual class. This error happens because the expect class ''{1}'' is non-final"),
        Unknown("{0}: normally, this error should never happen. Please report to https://kotl.in/issue. This error happens because the expect class ''{1}'' is non-final");

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final String rawMessage;

        public static EnumEntries<Kind> getEntries() {
            return $ENTRIES;
        }

        Kind(String str) {
            this.rawMessage = str;
        }

        public final String getRawMessage() {
            return this.rawMessage;
        }
    }
}
