package org.jetbrains.kotlin.name;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CallableId.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 &2\u00020\u0001:\u0001&B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0017\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\tB\u001b\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\u000bB+\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\rJ\u0006\u0010\u0019\u001a\u00020\bJ\u0006\u0010\u001a\u001a\u00020\bJ\t\u0010\u001b\u001a\u00020\bHÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\bHÆ\u0003J\t\u0010\u001d\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\bHÂ\u0003J5\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\bHÆ\u0001J\u0013\u0010 \u001a\u00020\u00162\b\u0010!\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\"\u001a\u00020#HÖ\u0001J\b\u0010$\u001a\u00020%H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR$\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0010\u001a\u0004\u0018\u00010\u00038F@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\f\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0010\u0010\n\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/name/CallableId;", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "callableName", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/name/Name;)V", "packageName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/Name;)V", "pathToLocal", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/name/FqName;)V", "className", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/name/FqName;)V", "getCallableName", "()Lorg/jetbrains/kotlin/name/Name;", "<set-?>", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getClassName", "()Lorg/jetbrains/kotlin/name/FqName;", "isLocal", "", "()Z", "getPackageName", "asFqNameForDebugInfo", "asSingleFqName", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "Companion", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CallableId {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Name LOCAL_NAME;
    private static final FqName PACKAGE_FQ_NAME_FOR_LOCAL;
    private final Name callableName;
    private ClassId classId;
    private final FqName className;
    private final FqName packageName;
    private final FqName pathToLocal;

    public static /* synthetic */ CallableId copy$default(CallableId callableId, FqName fqName, FqName fqName2, Name name2, FqName fqName3, int i, Object obj) {
        if ((i & 1) != 0) {
            fqName = callableId.packageName;
        }
        if ((i & 2) != 0) {
            fqName2 = callableId.className;
        }
        if ((i & 4) != 0) {
            name2 = callableId.callableName;
        }
        if ((i & 8) != 0) {
            fqName3 = callableId.pathToLocal;
        }
        return callableId.copy(fqName, fqName2, name2, fqName3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FqName getPackageName() {
        return this.packageName;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final FqName getClassName() {
        return this.className;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Name getCallableName() {
        return this.callableName;
    }

    public final CallableId copy(FqName packageName, FqName className, Name callableName, FqName pathToLocal) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(callableName, "callableName");
        return new CallableId(packageName, className, callableName, pathToLocal);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CallableId)) {
            return false;
        }
        CallableId callableId = (CallableId) other;
        return Intrinsics.areEqual(this.packageName, callableId.packageName) && Intrinsics.areEqual(this.className, callableId.className) && Intrinsics.areEqual(this.callableName, callableId.callableName) && Intrinsics.areEqual(this.pathToLocal, callableId.pathToLocal);
    }

    public int hashCode() {
        int iHashCode = this.packageName.hashCode() * 31;
        FqName fqName = this.className;
        int iHashCode2 = (((iHashCode + (fqName == null ? 0 : fqName.hashCode())) * 31) + this.callableName.hashCode()) * 31;
        FqName fqName2 = this.pathToLocal;
        return iHashCode2 + (fqName2 != null ? fqName2.hashCode() : 0);
    }

    public CallableId(FqName packageName, FqName fqName, Name callableName, FqName fqName2) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(callableName, "callableName");
        this.packageName = packageName;
        this.className = fqName;
        this.callableName = callableName;
        this.pathToLocal = fqName2;
    }

    public /* synthetic */ CallableId(FqName fqName, FqName fqName2, Name name2, FqName fqName3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(fqName, fqName2, name2, (i & 8) != 0 ? null : fqName3);
    }

    public final FqName getPackageName() {
        return this.packageName;
    }

    public final FqName getClassName() {
        return this.className;
    }

    public final Name getCallableName() {
        return this.callableName;
    }

    /* JADX INFO: compiled from: CallableId.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/name/CallableId$Companion;", "", "()V", "LOCAL_NAME", "Lorg/jetbrains/kotlin/name/Name;", "PACKAGE_FQ_NAME_FOR_LOCAL", "Lorg/jetbrains/kotlin/name/FqName;", "getPACKAGE_FQ_NAME_FOR_LOCAL", "()Lorg/jetbrains/kotlin/name/FqName;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FqName getPACKAGE_FQ_NAME_FOR_LOCAL() {
            return CallableId.PACKAGE_FQ_NAME_FOR_LOCAL;
        }
    }

    static {
        Name name2 = SpecialNames.LOCAL;
        LOCAL_NAME = name2;
        FqName fqName = FqName.topLevel(name2);
        Intrinsics.checkNotNullExpressionValue(fqName, "topLevel(...)");
        PACKAGE_FQ_NAME_FOR_LOCAL = fqName;
    }

    public final boolean isLocal() {
        if (Intrinsics.areEqual(this.packageName, PACKAGE_FQ_NAME_FOR_LOCAL)) {
            return true;
        }
        ClassId classId = getClassId();
        return classId != null && classId.isLocal();
    }

    public final ClassId getClassId() {
        if (this.classId == null && this.className != null) {
            FqName fqName = this.packageName;
            this.classId = new ClassId(fqName, this.className, Intrinsics.areEqual(fqName, PACKAGE_FQ_NAME_FOR_LOCAL));
        }
        return this.classId;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public CallableId(ClassId classId, Name callableName) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(callableName, "callableName");
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        this(packageFqName, classId.getRelativeClassName(), callableName, null, 8, null);
        this.classId = classId;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CallableId(FqName packageName, Name callableName) {
        this(packageName, null, callableName, null, 8, null);
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(callableName, "callableName");
    }

    public /* synthetic */ CallableId(Name name2, FqName fqName, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(name2, (i & 2) != 0 ? null : fqName);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CallableId(Name callableName, FqName fqName) {
        this(PACKAGE_FQ_NAME_FOR_LOCAL, (FqName) null, callableName, fqName);
        Intrinsics.checkNotNullParameter(callableName, "callableName");
    }

    public final FqName asFqNameForDebugInfo() {
        FqName fqName = this.pathToLocal;
        if (fqName == null) {
            return asSingleFqName();
        }
        FqName fqNameChild = fqName.child(this.callableName);
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        return fqNameChild;
    }

    public final FqName asSingleFqName() {
        FqName fqNameAsSingleFqName;
        ClassId classId = getClassId();
        FqName fqNameChild = (classId == null || (fqNameAsSingleFqName = classId.asSingleFqName()) == null) ? null : fqNameAsSingleFqName.child(this.callableName);
        if (fqNameChild != null) {
            return fqNameChild;
        }
        FqName fqNameChild2 = this.packageName.child(this.callableName);
        Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
        return fqNameChild2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String strAsString = this.packageName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        sb.append(StringsKt.replace$default(strAsString, '.', '/', false, 4, (Object) null));
        sb.append("/");
        FqName fqName = this.className;
        if (fqName != null) {
            sb.append(fqName);
            sb.append(".");
        }
        sb.append(this.callableName);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
