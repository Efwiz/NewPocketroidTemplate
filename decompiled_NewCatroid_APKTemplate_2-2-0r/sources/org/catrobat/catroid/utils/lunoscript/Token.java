package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/Token.class
  classes14.dex
 */
/* JADX INFO: compiled from: Token.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÆ\u0003J\t\u0010\u0017\u001a\u00020\bHÆ\u0003J\t\u0010\u0018\u001a\u00020\bHÆ\u0003J=\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00012\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\bHÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\bHÖ\u0001J\t\u0010\u001e\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/Token;", "", ModuleXmlParser.TYPE, "Lorg/catrobat/catroid/utils/lunoscript/TokenType;", "lexeme", "", "literal", Constant.LINE, "", "position", "(Lorg/catrobat/catroid/utils/lunoscript/TokenType;Ljava/lang/String;Ljava/lang/Object;II)V", "getLexeme", "()Ljava/lang/String;", "getLine", "()I", "getLiteral", "()Ljava/lang/Object;", "getPosition", "getType", "()Lorg/catrobat/catroid/utils/lunoscript/TokenType;", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Token {
    private final String lexeme;
    private final int line;
    private final Object literal;
    private final int position;
    private final TokenType type;

    public static /* synthetic */ Token copy$default(Token token, TokenType tokenType, String str, Object obj, int i, int i2, int i3, Object obj2) {
        if ((i3 & 1) != 0) {
            tokenType = token.type;
        }
        if ((i3 & 2) != 0) {
            str = token.lexeme;
        }
        String str2 = str;
        if ((i3 & 4) != 0) {
            obj = token.literal;
        }
        Object obj3 = obj;
        if ((i3 & 8) != 0) {
            i = token.line;
        }
        int i4 = i;
        if ((i3 & 16) != 0) {
            i2 = token.position;
        }
        return token.copy(tokenType, str2, obj3, i4, i2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final TokenType getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getLexeme() {
        return this.lexeme;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Object getLiteral() {
        return this.literal;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final int getPosition() {
        return this.position;
    }

    public final Token copy(TokenType type, String lexeme, Object literal, int line, int position) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(lexeme, "lexeme");
        return new Token(type, lexeme, literal, line, position);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Token)) {
            return false;
        }
        Token token = (Token) other;
        return this.type == token.type && Intrinsics.areEqual(this.lexeme, token.lexeme) && Intrinsics.areEqual(this.literal, token.literal) && this.line == token.line && this.position == token.position;
    }

    public int hashCode() {
        int iHashCode = ((this.type.hashCode() * 31) + this.lexeme.hashCode()) * 31;
        Object obj = this.literal;
        return ((((iHashCode + (obj == null ? 0 : obj.hashCode())) * 31) + Integer.hashCode(this.line)) * 31) + Integer.hashCode(this.position);
    }

    public String toString() {
        return "Token(type=" + this.type + ", lexeme=" + this.lexeme + ", literal=" + this.literal + ", line=" + this.line + ", position=" + this.position + Constant.AFTER_QUTO;
    }

    public Token(TokenType type, String lexeme, Object obj, int i, int i2) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(lexeme, "lexeme");
        this.type = type;
        this.lexeme = lexeme;
        this.literal = obj;
        this.line = i;
        this.position = i2;
    }

    public final TokenType getType() {
        return this.type;
    }

    public final String getLexeme() {
        return this.lexeme;
    }

    public final Object getLiteral() {
        return this.literal;
    }

    public final int getLine() {
        return this.line;
    }

    public final int getPosition() {
        return this.position;
    }
}
