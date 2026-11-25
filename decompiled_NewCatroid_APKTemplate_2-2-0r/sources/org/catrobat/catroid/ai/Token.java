package org.catrobat.catroid.ai;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/Token.class
  classes9.dex
 */
/* JADX INFO: compiled from: KoveParser.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0007HÆ\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u0007HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001b"}, d2 = {"Lorg/catrobat/catroid/ai/Token;", "", ModuleXmlParser.TYPE, "Lorg/catrobat/catroid/ai/TokenType;", "value", "", Constant.LINE, "", "column", "(Lorg/catrobat/catroid/ai/TokenType;Ljava/lang/String;II)V", "getColumn", "()I", "getLine", "getType", "()Lorg/catrobat/catroid/ai/TokenType;", "getValue", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Token {
    private final int column;
    private final int line;
    private final TokenType type;
    private final String value;

    public static /* synthetic */ Token copy$default(Token token, TokenType tokenType, String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            tokenType = token.type;
        }
        if ((i3 & 2) != 0) {
            str = token.value;
        }
        if ((i3 & 4) != 0) {
            i = token.line;
        }
        if ((i3 & 8) != 0) {
            i2 = token.column;
        }
        return token.copy(tokenType, str, i, i2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final TokenType getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getValue() {
        return this.value;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getColumn() {
        return this.column;
    }

    public final Token copy(TokenType type, String value, int line, int column) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(value, "value");
        return new Token(type, value, line, column);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Token)) {
            return false;
        }
        Token token = (Token) other;
        return this.type == token.type && Intrinsics.areEqual(this.value, token.value) && this.line == token.line && this.column == token.column;
    }

    public int hashCode() {
        return (((((this.type.hashCode() * 31) + this.value.hashCode()) * 31) + Integer.hashCode(this.line)) * 31) + Integer.hashCode(this.column);
    }

    public String toString() {
        return "Token(type=" + this.type + ", value=" + this.value + ", line=" + this.line + ", column=" + this.column + Constant.AFTER_QUTO;
    }

    public Token(TokenType type, String value, int i, int i2) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(value, "value");
        this.type = type;
        this.value = value;
        this.line = i;
        this.column = i2;
    }

    public final int getColumn() {
        return this.column;
    }

    public final int getLine() {
        return this.line;
    }

    public final TokenType getType() {
        return this.type;
    }

    public final String getValue() {
        return this.value;
    }
}
