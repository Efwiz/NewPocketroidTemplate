package org.jetbrains.kotlin.resolve.checkers;

import com.google.android.gms.cast.MediaError;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: OptInDescription.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\b\u0018\u0000 \u001d2\u00020\u0001:\u0002\u001d\u001eB'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010\u0016\u001a\u00020\tHÆ\u0003J3\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\t2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u0007HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/OptInDescription;", "", "annotationFqName", "Lorg/jetbrains/kotlin/name/FqName;", "severity", "Lorg/jetbrains/kotlin/resolve/checkers/OptInDescription$Severity;", CrashHianalyticsData.MESSAGE, "", "subclassesOnly", "", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/resolve/checkers/OptInDescription$Severity;Ljava/lang/String;Z)V", "getAnnotationFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getMessage", "()Ljava/lang/String;", "getSeverity", "()Lorg/jetbrains/kotlin/resolve/checkers/OptInDescription$Severity;", "getSubclassesOnly", "()Z", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "Companion", "Severity", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class OptInDescription {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Severity DEFAULT_SEVERITY = Severity.ERROR;
    private static final Set<KotlinTarget> WRONG_TARGETS_FOR_MARKER = SetsKt.setOf((Object[]) new KotlinTarget[]{KotlinTarget.EXPRESSION, KotlinTarget.FILE, KotlinTarget.TYPE, KotlinTarget.TYPE_PARAMETER});
    private final FqName annotationFqName;
    private final String message;
    private final Severity severity;
    private final boolean subclassesOnly;

    /* JADX INFO: compiled from: OptInDescription.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/OptInDescription$Severity;", "", "(Ljava/lang/String;I)V", "WARNING", MediaError.ERROR_TYPE_ERROR, "FUTURE_ERROR", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Severity {
        WARNING,
        ERROR,
        FUTURE_ERROR;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Severity> getEntries() {
            return $ENTRIES;
        }
    }

    public static /* synthetic */ OptInDescription copy$default(OptInDescription optInDescription, FqName fqName, Severity severity, String str, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            fqName = optInDescription.annotationFqName;
        }
        if ((i & 2) != 0) {
            severity = optInDescription.severity;
        }
        if ((i & 4) != 0) {
            str = optInDescription.message;
        }
        if ((i & 8) != 0) {
            z = optInDescription.subclassesOnly;
        }
        return optInDescription.copy(fqName, severity, str, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FqName getAnnotationFqName() {
        return this.annotationFqName;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Severity getSeverity() {
        return this.severity;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getSubclassesOnly() {
        return this.subclassesOnly;
    }

    public final OptInDescription copy(FqName annotationFqName, Severity severity, String message, boolean subclassesOnly) {
        Intrinsics.checkNotNullParameter(annotationFqName, "annotationFqName");
        Intrinsics.checkNotNullParameter(severity, "severity");
        return new OptInDescription(annotationFqName, severity, message, subclassesOnly);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OptInDescription)) {
            return false;
        }
        OptInDescription optInDescription = (OptInDescription) other;
        return Intrinsics.areEqual(this.annotationFqName, optInDescription.annotationFqName) && this.severity == optInDescription.severity && Intrinsics.areEqual(this.message, optInDescription.message) && this.subclassesOnly == optInDescription.subclassesOnly;
    }

    public int hashCode() {
        int iHashCode = ((this.annotationFqName.hashCode() * 31) + this.severity.hashCode()) * 31;
        String str = this.message;
        return ((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + Boolean.hashCode(this.subclassesOnly);
    }

    public String toString() {
        return "OptInDescription(annotationFqName=" + this.annotationFqName + ", severity=" + this.severity + ", message=" + this.message + ", subclassesOnly=" + this.subclassesOnly + ')';
    }

    public OptInDescription(FqName annotationFqName, Severity severity, String str, boolean z) {
        Intrinsics.checkNotNullParameter(annotationFqName, "annotationFqName");
        Intrinsics.checkNotNullParameter(severity, "severity");
        this.annotationFqName = annotationFqName;
        this.severity = severity;
        this.message = str;
        this.subclassesOnly = z;
    }

    public final FqName getAnnotationFqName() {
        return this.annotationFqName;
    }

    public final Severity getSeverity() {
        return this.severity;
    }

    public final String getMessage() {
        return this.message;
    }

    public final boolean getSubclassesOnly() {
        return this.subclassesOnly;
    }

    /* JADX INFO: compiled from: OptInDescription.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/OptInDescription$Companion;", "", "()V", "DEFAULT_SEVERITY", "Lorg/jetbrains/kotlin/resolve/checkers/OptInDescription$Severity;", "getDEFAULT_SEVERITY", "()Lorg/jetbrains/kotlin/resolve/checkers/OptInDescription$Severity;", "WRONG_TARGETS_FOR_MARKER", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "getWRONG_TARGETS_FOR_MARKER", "()Ljava/util/Set;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Severity getDEFAULT_SEVERITY() {
            return OptInDescription.DEFAULT_SEVERITY;
        }

        public final Set<KotlinTarget> getWRONG_TARGETS_FOR_MARKER() {
            return OptInDescription.WRONG_TARGETS_FOR_MARKER;
        }
    }
}
