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
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0086\b\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB)\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J3\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÖ\u0001J\b\u0010\u0019\u001a\u00020\u0003H\u0016R\u0014\u0010\u0006\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation;", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", "path", "", Constant.LINE, "", "column", "lineContent", "(Ljava/lang/String;IILjava/lang/String;)V", "getColumn", "()I", "getLine", "getLineContent", "()Ljava/lang/String;", "getPath", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CompilerMessageLocation implements CompilerMessageSourceLocation {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final long serialVersionUID = 8228357578L;
    private final int column;
    private final int line;
    private final String lineContent;
    private final String path;

    public /* synthetic */ CompilerMessageLocation(String str, int i, int i2, String str2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, i2, str2);
    }

    public static /* synthetic */ CompilerMessageLocation copy$default(CompilerMessageLocation compilerMessageLocation, String str, int i, int i2, String str2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = compilerMessageLocation.path;
        }
        if ((i3 & 2) != 0) {
            i = compilerMessageLocation.line;
        }
        if ((i3 & 4) != 0) {
            i2 = compilerMessageLocation.column;
        }
        if ((i3 & 8) != 0) {
            str2 = compilerMessageLocation.lineContent;
        }
        return compilerMessageLocation.copy(str, i, i2, str2);
    }

    @JvmStatic
    public static final CompilerMessageLocation create(String str) {
        return INSTANCE.create(str);
    }

    @JvmStatic
    public static final CompilerMessageLocation create(String str, int i, int i2, String str2) {
        return INSTANCE.create(str, i, i2, str2);
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
    public final String getLineContent() {
        return this.lineContent;
    }

    public final CompilerMessageLocation copy(String path, int line, int column, String lineContent) {
        Intrinsics.checkNotNullParameter(path, "path");
        return new CompilerMessageLocation(path, line, column, lineContent);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CompilerMessageLocation)) {
            return false;
        }
        CompilerMessageLocation compilerMessageLocation = (CompilerMessageLocation) other;
        return Intrinsics.areEqual(this.path, compilerMessageLocation.path) && this.line == compilerMessageLocation.line && this.column == compilerMessageLocation.column && Intrinsics.areEqual(this.lineContent, compilerMessageLocation.lineContent);
    }

    public int hashCode() {
        int iHashCode = ((((this.path.hashCode() * 31) + Integer.hashCode(this.line)) * 31) + Integer.hashCode(this.column)) * 31;
        String str = this.lineContent;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    private CompilerMessageLocation(String str, int i, int i2, String str2) {
        this.path = str;
        this.line = i;
        this.column = i2;
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
    public String getLineContent() {
        return this.lineContent;
    }

    public String toString() {
        return getPath() + ((getLine() == -1 && getColumn() == -1) ? "" : " (" + getLine() + ':' + getColumn() + ')');
    }

    /* JADX INFO: compiled from: CompilerMessageLocation.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0007J.\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation$Companion;", "", "()V", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation;", "path", "", Constant.LINE, "", "column", "lineContent", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final CompilerMessageLocation create(String path) {
            return create(path, -1, -1, null);
        }

        @JvmStatic
        public final CompilerMessageLocation create(String path, int line, int column, String lineContent) {
            if (path == null) {
                return null;
            }
            return new CompilerMessageLocation(path, line, column, lineContent, null);
        }
    }
}
