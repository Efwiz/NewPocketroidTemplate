package org.catrobat.paintroid.command.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.paintroid.tools.FontType;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: SerializableTypeface.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0015\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\"B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0019\u001a\u00020\tHÆ\u0003J\t\u0010\u001a\u001a\u00020\tHÆ\u0003JE\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\tHÆ\u0001J\u0013\u0010\u001c\u001a\u00020\u00052\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001J\t\u0010 \u001a\u00020!HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\r¨\u0006#"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializableTypeface;", "", "font", "Lorg/catrobat/paintroid/tools/FontType;", "bold", "", "underline", "italic", StageActivity.STYLE_TEXT_SIZE, "", "textSkewX", "(Lorg/catrobat/paintroid/tools/FontType;ZZZFF)V", "getBold", "()Z", "getFont", "()Lorg/catrobat/paintroid/tools/FontType;", "getItalic", "getTextSize", "()F", "getTextSkewX", "getUnderline", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "TypefaceSerializer", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final /* data */ class SerializableTypeface {
    private final boolean bold;
    private final FontType font;
    private final boolean italic;
    private final float textSize;
    private final float textSkewX;
    private final boolean underline;

    public static /* synthetic */ SerializableTypeface copy$default(SerializableTypeface serializableTypeface, FontType fontType, boolean z, boolean z2, boolean z3, float f, float f2, int i, Object obj) {
        if ((i & 1) != 0) {
            fontType = serializableTypeface.font;
        }
        if ((i & 2) != 0) {
            z = serializableTypeface.bold;
        }
        boolean z4 = z;
        if ((i & 4) != 0) {
            z2 = serializableTypeface.underline;
        }
        boolean z5 = z2;
        if ((i & 8) != 0) {
            z3 = serializableTypeface.italic;
        }
        boolean z6 = z3;
        if ((i & 16) != 0) {
            f = serializableTypeface.textSize;
        }
        float f3 = f;
        if ((i & 32) != 0) {
            f2 = serializableTypeface.textSkewX;
        }
        return serializableTypeface.copy(fontType, z4, z5, z6, f3, f2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FontType getFont() {
        return this.font;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getBold() {
        return this.bold;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getUnderline() {
        return this.underline;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getItalic() {
        return this.italic;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final float getTextSize() {
        return this.textSize;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final float getTextSkewX() {
        return this.textSkewX;
    }

    public final SerializableTypeface copy(FontType font, boolean bold, boolean underline, boolean italic, float textSize, float textSkewX) {
        Intrinsics.checkNotNullParameter(font, "font");
        return new SerializableTypeface(font, bold, underline, italic, textSize, textSkewX);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SerializableTypeface)) {
            return false;
        }
        SerializableTypeface serializableTypeface = (SerializableTypeface) other;
        return this.font == serializableTypeface.font && this.bold == serializableTypeface.bold && this.underline == serializableTypeface.underline && this.italic == serializableTypeface.italic && Intrinsics.areEqual((Object) Float.valueOf(this.textSize), (Object) Float.valueOf(serializableTypeface.textSize)) && Intrinsics.areEqual((Object) Float.valueOf(this.textSkewX), (Object) Float.valueOf(serializableTypeface.textSkewX));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [int] */
    /* JADX WARN: Type inference failed for: r0v5, types: [int] */
    /* JADX WARN: Type inference failed for: r0v7, types: [int] */
    /* JADX WARN: Type inference failed for: r1v1, types: [int] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v3, types: [int] */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [int] */
    /* JADX WARN: Type inference failed for: r2v2 */
    public int hashCode() {
        int iHashCode = this.font.hashCode() * 31;
        boolean z = this.bold;
        ?? r1 = z;
        if (z) {
            r1 = 1;
        }
        int i = (iHashCode + r1) * 31;
        boolean z2 = this.underline;
        ?? r2 = z2;
        if (z2) {
            r2 = 1;
        }
        int i2 = (i + r2) * 31;
        boolean z3 = this.italic;
        return ((((i2 + (z3 ? 1 : z3)) * 31) + Float.hashCode(this.textSize)) * 31) + Float.hashCode(this.textSkewX);
    }

    public String toString() {
        return "SerializableTypeface(font=" + this.font + ", bold=" + this.bold + ", underline=" + this.underline + ", italic=" + this.italic + ", textSize=" + this.textSize + ", textSkewX=" + this.textSkewX + ')';
    }

    public SerializableTypeface(FontType font, boolean z, boolean z2, boolean z3, float f, float f2) {
        Intrinsics.checkNotNullParameter(font, "font");
        this.font = font;
        this.bold = z;
        this.underline = z2;
        this.italic = z3;
        this.textSize = f;
        this.textSkewX = f2;
    }

    public final boolean getBold() {
        return this.bold;
    }

    public final FontType getFont() {
        return this.font;
    }

    public final boolean getItalic() {
        return this.italic;
    }

    public final float getTextSize() {
        return this.textSize;
    }

    public final float getTextSkewX() {
        return this.textSkewX;
    }

    public final boolean getUnderline() {
        return this.underline;
    }

    /* JADX INFO: compiled from: SerializableTypeface.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializableTypeface$TypefaceSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/command/serialization/SerializableTypeface;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "typeface", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class TypefaceSerializer extends VersionSerializer<SerializableTypeface> {
        public TypefaceSerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends SerializableTypeface>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, SerializableTypeface typeface) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(typeface, "typeface");
            output.writeString(typeface.getFont().name());
            output.writeBoolean(typeface.getBold());
            output.writeBoolean(typeface.getUnderline());
            output.writeBoolean(typeface.getItalic());
            output.writeFloat(typeface.getTextSize());
            output.writeFloat(typeface.getTextSkewX());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public SerializableTypeface read(Kryo kryo, Input input, Class<? extends SerializableTypeface> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (SerializableTypeface) super.handleVersions(this, kryo, input, type);
        }

        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public SerializableTypeface readCurrentVersion(Kryo kryo, Input input, Class<? extends SerializableTypeface> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            String string = input.readString();
            Intrinsics.checkNotNullExpressionValue(string, "readString()");
            return new SerializableTypeface(FontType.valueOf(string), input.readBoolean(), input.readBoolean(), input.readBoolean(), input.readFloat(), input.readFloat());
        }
    }
}
