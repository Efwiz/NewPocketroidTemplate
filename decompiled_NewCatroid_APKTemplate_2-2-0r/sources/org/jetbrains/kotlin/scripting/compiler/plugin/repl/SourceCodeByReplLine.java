package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.SourceCode;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ReplCodeAnalyzer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J5\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/SourceCodeByReplLine;", "Lkotlin/script/experimental/api/SourceCode;", "text", "", "no", "", "name", "locationId", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "getLocationId", "()Ljava/lang/String;", "getName", "getNo", "()I", "getText", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SourceCodeByReplLine implements SourceCode {
    private final String locationId;
    private final String name;
    private final int no;
    private final String text;

    public static /* synthetic */ SourceCodeByReplLine copy$default(SourceCodeByReplLine sourceCodeByReplLine, String str, int i, String str2, String str3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = sourceCodeByReplLine.text;
        }
        if ((i2 & 2) != 0) {
            i = sourceCodeByReplLine.no;
        }
        if ((i2 & 4) != 0) {
            str2 = sourceCodeByReplLine.name;
        }
        if ((i2 & 8) != 0) {
            str3 = sourceCodeByReplLine.locationId;
        }
        return sourceCodeByReplLine.copy(str, i, str2, str3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getText() {
        return this.text;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getNo() {
        return this.no;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getLocationId() {
        return this.locationId;
    }

    public final SourceCodeByReplLine copy(String text, int no, String name2, String locationId) {
        Intrinsics.checkNotNullParameter(text, "text");
        return new SourceCodeByReplLine(text, no, name2, locationId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SourceCodeByReplLine)) {
            return false;
        }
        SourceCodeByReplLine sourceCodeByReplLine = (SourceCodeByReplLine) other;
        return Intrinsics.areEqual(this.text, sourceCodeByReplLine.text) && this.no == sourceCodeByReplLine.no && Intrinsics.areEqual(this.name, sourceCodeByReplLine.name) && Intrinsics.areEqual(this.locationId, sourceCodeByReplLine.locationId);
    }

    public int hashCode() {
        int iHashCode = ((this.text.hashCode() * 31) + Integer.hashCode(this.no)) * 31;
        String str = this.name;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.locationId;
        return iHashCode2 + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        return "SourceCodeByReplLine(text=" + this.text + ", no=" + this.no + ", name=" + this.name + ", locationId=" + this.locationId + ')';
    }

    public SourceCodeByReplLine(String text, int i, String str, String str2) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.text = text;
        this.no = i;
        this.name = str;
        this.locationId = str2;
    }

    public /* synthetic */ SourceCodeByReplLine(String str, int i, String str2, String str3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, (i2 & 4) != 0 ? null : str2, (i2 & 8) != 0 ? null : str3);
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getText() {
        return this.text;
    }

    public final int getNo() {
        return this.no;
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getName() {
        return this.name;
    }

    @Override // kotlin.script.experimental.api.SourceCode
    public String getLocationId() {
        return this.locationId;
    }
}
