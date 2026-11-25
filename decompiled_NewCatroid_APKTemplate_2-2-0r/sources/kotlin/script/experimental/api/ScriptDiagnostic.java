package kotlin.script.experimental.api;

import com.google.android.gms.cast.MediaError;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import io.netty.util.internal.StringUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: errorHandling.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\n\b\u0086\b\u0018\u0000 .2\u00020\u0001:\u0002./B7\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fBC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\u0010J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0007HÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u000fHÆ\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u000bHÆ\u0003JK\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bHÆ\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&HÖ\u0003J\t\u0010'\u001a\u00020\u0003HÖ\u0001J.\u0010(\u001a\u00020\u00052\b\b\u0002\u0010)\u001a\u00020$2\b\b\u0002\u0010*\u001a\u00020$2\b\b\u0002\u0010+\u001a\u00020$2\b\b\u0002\u0010,\u001a\u00020$J\b\u0010-\u001a\u00020\u0005H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\r\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0018¨\u00060"}, d2 = {"Lkotlin/script/experimental/api/ScriptDiagnostic;", "Ljava/io/Serializable;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", CrashHianalyticsData.MESSAGE, "", "severity", "Lkotlin/script/experimental/api/ScriptDiagnostic$Severity;", "locationWithId", "Lkotlin/script/experimental/api/SourceCode$LocationWithId;", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION, "", "(ILjava/lang/String;Lkotlin/script/experimental/api/ScriptDiagnostic$Severity;Lkotlin/script/experimental/api/SourceCode$LocationWithId;Ljava/lang/Throwable;)V", "sourcePath", FirebaseAnalytics.Param.LOCATION, "Lkotlin/script/experimental/api/SourceCode$Location;", "(ILjava/lang/String;Lkotlin/script/experimental/api/ScriptDiagnostic$Severity;Ljava/lang/String;Lkotlin/script/experimental/api/SourceCode$Location;Ljava/lang/Throwable;)V", "getCode", "()I", "getException", "()Ljava/lang/Throwable;", "getLocation", "()Lkotlin/script/experimental/api/SourceCode$Location;", "getMessage", "()Ljava/lang/String;", "getSeverity", "()Lkotlin/script/experimental/api/ScriptDiagnostic$Severity;", "getSourcePath", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "render", "withSeverity", "withLocation", "withException", "withStackTrace", "toString", "Companion", "Severity", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ScriptDiagnostic implements Serializable {
    public static final int incompleteCode = -3;
    private static final long serialVersionUID = 0;
    public static final int unspecifiedError = -1;
    public static final int unspecifiedException = -2;
    public static final int unspecifiedInfo = 0;
    private final int code;
    private final Throwable exception;
    private final SourceCode.Location location;
    private final String message;
    private final Severity severity;
    private final String sourcePath;

    /* JADX INFO: compiled from: errorHandling.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lkotlin/script/experimental/api/ScriptDiagnostic$Severity;", "", "(Ljava/lang/String;I)V", "DEBUG", "INFO", "WARNING", MediaError.ERROR_TYPE_ERROR, "FATAL", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Severity {
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        FATAL;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Severity> getEntries() {
            return $ENTRIES;
        }
    }

    public static /* synthetic */ ScriptDiagnostic copy$default(ScriptDiagnostic scriptDiagnostic, int i, String str, Severity severity, String str2, SourceCode.Location location, Throwable th, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = scriptDiagnostic.code;
        }
        if ((i2 & 2) != 0) {
            str = scriptDiagnostic.message;
        }
        String str3 = str;
        if ((i2 & 4) != 0) {
            severity = scriptDiagnostic.severity;
        }
        Severity severity2 = severity;
        if ((i2 & 8) != 0) {
            str2 = scriptDiagnostic.sourcePath;
        }
        String str4 = str2;
        if ((i2 & 16) != 0) {
            location = scriptDiagnostic.location;
        }
        SourceCode.Location location2 = location;
        if ((i2 & 32) != 0) {
            th = scriptDiagnostic.exception;
        }
        return scriptDiagnostic.copy(i, str3, severity2, str4, location2, th);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getCode() {
        return this.code;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Severity getSeverity() {
        return this.severity;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getSourcePath() {
        return this.sourcePath;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final SourceCode.Location getLocation() {
        return this.location;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final Throwable getException() {
        return this.exception;
    }

    public final ScriptDiagnostic copy(int code, String message, Severity severity, String sourcePath, SourceCode.Location location, Throwable exception) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(severity, "severity");
        return new ScriptDiagnostic(code, message, severity, sourcePath, location, exception);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScriptDiagnostic)) {
            return false;
        }
        ScriptDiagnostic scriptDiagnostic = (ScriptDiagnostic) other;
        return this.code == scriptDiagnostic.code && Intrinsics.areEqual(this.message, scriptDiagnostic.message) && this.severity == scriptDiagnostic.severity && Intrinsics.areEqual(this.sourcePath, scriptDiagnostic.sourcePath) && Intrinsics.areEqual(this.location, scriptDiagnostic.location) && Intrinsics.areEqual(this.exception, scriptDiagnostic.exception);
    }

    public int hashCode() {
        int iHashCode = ((((Integer.hashCode(this.code) * 31) + this.message.hashCode()) * 31) + this.severity.hashCode()) * 31;
        String str = this.sourcePath;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        SourceCode.Location location = this.location;
        int iHashCode3 = (iHashCode2 + (location == null ? 0 : location.hashCode())) * 31;
        Throwable th = this.exception;
        return iHashCode3 + (th != null ? th.hashCode() : 0);
    }

    public ScriptDiagnostic(int i, String message, Severity severity, String str, SourceCode.Location location, Throwable th) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(severity, "severity");
        this.code = i;
        this.message = message;
        this.severity = severity;
        this.sourcePath = str;
        this.location = location;
        this.exception = th;
    }

    public final int getCode() {
        return this.code;
    }

    public final String getMessage() {
        return this.message;
    }

    public /* synthetic */ ScriptDiagnostic(int i, String str, Severity severity, String str2, SourceCode.Location location, Throwable th, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, (i2 & 4) != 0 ? Severity.ERROR : severity, (i2 & 8) != 0 ? null : str2, (i2 & 16) != 0 ? null : location, (i2 & 32) != 0 ? null : th);
    }

    public final Severity getSeverity() {
        return this.severity;
    }

    public final String getSourcePath() {
        return this.sourcePath;
    }

    public final SourceCode.Location getLocation() {
        return this.location;
    }

    public final Throwable getException() {
        return this.exception;
    }

    public /* synthetic */ ScriptDiagnostic(int i, String str, Severity severity, SourceCode.LocationWithId locationWithId, Throwable th, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, (i2 & 4) != 0 ? Severity.ERROR : severity, locationWithId, (i2 & 16) != 0 ? null : th);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ScriptDiagnostic(int i, String message, Severity severity, SourceCode.LocationWithId locationWithId, Throwable th) {
        this(i, message, severity, locationWithId != null ? locationWithId.getCodeLocationId() : null, locationWithId != null ? locationWithId.getLocationInText() : null, th);
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(severity, "severity");
    }

    public String toString() {
        return render$default(this, false, false, false, false, 15, null);
    }

    public static /* synthetic */ String render$default(ScriptDiagnostic scriptDiagnostic, boolean z, boolean z2, boolean z3, boolean z4, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        if ((i & 4) != 0) {
            z3 = true;
        }
        if ((i & 8) != 0) {
            z4 = false;
        }
        return scriptDiagnostic.render(z, z2, z3, z4);
    }

    public final String render(boolean withSeverity, boolean withLocation, boolean withException, boolean withStackTrace) {
        StringBuilder sb = new StringBuilder();
        if (withSeverity) {
            sb.append(this.severity.name());
            sb.append(StringUtil.SPACE);
        }
        sb.append(this.message);
        if (withLocation && (this.sourcePath != null || this.location != null)) {
            sb.append(" (");
            String str = this.sourcePath;
            if (str != null) {
                sb.append(StringsKt.substringAfterLast$default(str, File.separatorChar, (String) null, 2, (Object) null));
            }
            SourceCode.Location location = this.location;
            if (location != null) {
                sb.append(':');
                sb.append(location.getStart().getLine());
                sb.append(':');
                sb.append(location.getStart().getCol());
            }
            sb.append(')');
        }
        if (withException && this.exception != null) {
            sb.append(": ");
            sb.append(this.exception);
            if (withStackTrace) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    ByteArrayOutputStream byteArrayOutputStream2 = byteArrayOutputStream;
                    PrintStream printStream = new PrintStream(byteArrayOutputStream2);
                    this.exception.printStackTrace(printStream);
                    printStream.flush();
                    sb.append("\n");
                    sb.append(byteArrayOutputStream2.toString());
                    CloseableKt.closeFinally(byteArrayOutputStream, null);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(byteArrayOutputStream, th);
                        throw th2;
                    }
                }
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
