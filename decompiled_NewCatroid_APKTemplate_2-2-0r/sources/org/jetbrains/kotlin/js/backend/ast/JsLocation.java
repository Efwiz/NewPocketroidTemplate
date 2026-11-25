package org.jetbrains.kotlin.js.backend.ast;

import java.io.Reader;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JsLocation.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B+\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\bJ\b\u0010\u0018\u001a\u00020\u0000H\u0016J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003HÆ\u0003J3\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010!\u001a\u00020\u0005HÖ\u0001J\t\u0010\"\u001a\u00020\u0003HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\nR\u001c\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0006\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsLocation;", "Lorg/jetbrains/kotlin/js/backend/ast/JsLocationWithSource;", "file", "", "startLine", "", "startChar", "name", "(Ljava/lang/String;IILjava/lang/String;)V", "getFile", "()Ljava/lang/String;", "fileIdentity", "", "getFileIdentity", "()Ljava/lang/Object;", "getName", "sourceProvider", "Lkotlin/Function0;", "Ljava/io/Reader;", "getSourceProvider", "()Lkotlin/jvm/functions/Function0;", "getStartChar", "()I", "getStartLine", "asSimpleLocation", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JsLocation implements JsLocationWithSource {
    private final String file;
    private final String name;
    private final int startChar;
    private final int startLine;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JsLocation(String file, int i, int i2) {
        this(file, i, i2, null, 8, null);
        Intrinsics.checkNotNullParameter(file, "file");
    }

    public static /* synthetic */ JsLocation copy$default(JsLocation jsLocation, String str, int i, int i2, String str2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = jsLocation.file;
        }
        if ((i3 & 2) != 0) {
            i = jsLocation.startLine;
        }
        if ((i3 & 4) != 0) {
            i2 = jsLocation.startChar;
        }
        if ((i3 & 8) != 0) {
            str2 = jsLocation.name;
        }
        return jsLocation.copy(str, i, i2, str2);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource
    public JsLocation asSimpleLocation() {
        return this;
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getFile() {
        return this.file;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getStartLine() {
        return this.startLine;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getStartChar() {
        return this.startChar;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getName() {
        return this.name;
    }

    public final JsLocation copy(String file, int startLine, int startChar, String name2) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new JsLocation(file, startLine, startChar, name2);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JsLocation)) {
            return false;
        }
        JsLocation jsLocation = (JsLocation) other;
        return Intrinsics.areEqual(this.file, jsLocation.file) && this.startLine == jsLocation.startLine && this.startChar == jsLocation.startChar && Intrinsics.areEqual(this.name, jsLocation.name);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource
    public Object getFileIdentity() {
        return null;
    }

    public int hashCode() {
        int iHashCode = ((((this.file.hashCode() * 31) + Integer.hashCode(this.startLine)) * 31) + Integer.hashCode(this.startChar)) * 31;
        String str = this.name;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "JsLocation(file=" + this.file + ", startLine=" + this.startLine + ", startChar=" + this.startChar + ", name=" + this.name + ')';
    }

    public JsLocation(String file, int i, int i2, String str) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.file = file;
        this.startLine = i;
        this.startChar = i2;
        this.name = str;
    }

    public /* synthetic */ JsLocation(String str, int i, int i2, String str2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, i2, (i3 & 8) != 0 ? null : str2);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource
    public String getFile() {
        return this.file;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource
    public int getStartLine() {
        return this.startLine;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource
    public int getStartChar() {
        return this.startChar;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource
    public String getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource
    public Function0<Reader> getSourceProvider() {
        return new Function0() { // from class: org.jetbrains.kotlin.js.backend.ast.JsLocation$sourceProvider$1
            @Override // kotlin.jvm.functions.Function0
            public final Void invoke() {
                return null;
            }
        };
    }
}
