package org.jetbrains.kotlin.cli.common.messages;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CompilerMessageLocation.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0086\b\u0018\u0000  2\u00020\u0001:\u0001 B9\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003JG\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dHÖ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÖ\u0001J\b\u0010\u001f\u001a\u00020\u0003H\u0016R\u0014\u0010\u0006\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\b\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0016\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocationWithRange;", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", "path", "", Constant.LINE, "", "column", "lineEnd", "columnEnd", "lineContent", "(Ljava/lang/String;IIIILjava/lang/String;)V", "getColumn", "()I", "getColumnEnd", "getLine", "getLineContent", "()Ljava/lang/String;", "getLineEnd", "getPath", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CompilerMessageLocationWithRange implements CompilerMessageSourceLocation {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final long serialVersionUID = 8228357578L;
    private final int column;
    private final int columnEnd;
    private final int line;
    private final String lineContent;
    private final int lineEnd;
    private final String path;

    public /* synthetic */ CompilerMessageLocationWithRange(String str, int i, int i2, int i3, int i4, String str2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, i2, i3, i4, str2);
    }

    public static /* synthetic */ CompilerMessageLocationWithRange copy$default(CompilerMessageLocationWithRange compilerMessageLocationWithRange, String str, int i, int i2, int i3, int i4, String str2, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            str = compilerMessageLocationWithRange.path;
        }
        if ((i5 & 2) != 0) {
            i = compilerMessageLocationWithRange.line;
        }
        int i6 = i;
        if ((i5 & 4) != 0) {
            i2 = compilerMessageLocationWithRange.column;
        }
        int i7 = i2;
        if ((i5 & 8) != 0) {
            i3 = compilerMessageLocationWithRange.lineEnd;
        }
        int i8 = i3;
        if ((i5 & 16) != 0) {
            i4 = compilerMessageLocationWithRange.columnEnd;
        }
        int i9 = i4;
        if ((i5 & 32) != 0) {
            str2 = compilerMessageLocationWithRange.lineContent;
        }
        return compilerMessageLocationWithRange.copy(str, i6, i7, i8, i9, str2);
    }

    @JvmStatic
    public static final CompilerMessageLocationWithRange create(String str, int i, int i2, Integer num, Integer num2, String str2) {
        return INSTANCE.create(str, i, i2, num, num2, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getPath() {
        return this.path;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getColumn() {
        return this.column;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getLineEnd() {
        return this.lineEnd;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final int getColumnEnd() {
        return this.columnEnd;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getLineContent() {
        return this.lineContent;
    }

    public final CompilerMessageLocationWithRange copy(String path, int line, int column, int lineEnd, int columnEnd, String lineContent) {
        Intrinsics.checkNotNullParameter(path, "path");
        return new CompilerMessageLocationWithRange(path, line, column, lineEnd, columnEnd, lineContent);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CompilerMessageLocationWithRange)) {
            return false;
        }
        CompilerMessageLocationWithRange compilerMessageLocationWithRange = (CompilerMessageLocationWithRange) other;
        return Intrinsics.areEqual(this.path, compilerMessageLocationWithRange.path) && this.line == compilerMessageLocationWithRange.line && this.column == compilerMessageLocationWithRange.column && this.lineEnd == compilerMessageLocationWithRange.lineEnd && this.columnEnd == compilerMessageLocationWithRange.columnEnd && Intrinsics.areEqual(this.lineContent, compilerMessageLocationWithRange.lineContent);
    }

    public int hashCode() {
        int iHashCode = ((((((((this.path.hashCode() * 31) + Integer.hashCode(this.line)) * 31) + Integer.hashCode(this.column)) * 31) + Integer.hashCode(this.lineEnd)) * 31) + Integer.hashCode(this.columnEnd)) * 31;
        String str = this.lineContent;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    private CompilerMessageLocationWithRange(String str, int i, int i2, int i3, int i4, String str2) {
        this.path = str;
        this.line = i;
        this.column = i2;
        this.lineEnd = i3;
        this.columnEnd = i4;
        this.lineContent = str2;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
    public String getPath() {
        return this.path;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
    public int getLine() {
        return this.line;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
    public int getColumn() {
        return this.column;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
    public int getLineEnd() {
        return this.lineEnd;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
    public int getColumnEnd() {
        return this.columnEnd;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation
    public String getLineContent() {
        return this.lineContent;
    }

    public String toString() {
        return getPath() + ((getLine() == -1 && getColumn() == -1) ? "" : " (" + getLine() + ':' + getColumn() + ')');
    }

    /* JADX INFO: compiled from: CompilerMessageLocation.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JG\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\n2\b\u0010\r\u001a\u0004\u0018\u00010\n2\b\u0010\u000e\u001a\u0004\u0018\u00010\bH\u0007¢\u0006\u0002\u0010\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocationWithRange$Companion;", "", "()V", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocationWithRange;", "path", "", "lineStart", "", "columnStart", "lineEnd", "columnEnd", "lineContent", "(Ljava/lang/String;IILjava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;)Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocationWithRange;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final CompilerMessageLocationWithRange create(String path, int lineStart, int columnStart, Integer lineEnd, Integer columnEnd, String lineContent) {
            if (path == null) {
                return null;
            }
            return new CompilerMessageLocationWithRange(path, lineStart, columnStart, lineEnd != null ? lineEnd.intValue() : -1, columnEnd != null ? columnEnd.intValue() : -1, lineContent, null);
        }
    }
}
