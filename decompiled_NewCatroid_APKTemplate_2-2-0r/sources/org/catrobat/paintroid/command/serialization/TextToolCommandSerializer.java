package org.catrobat.paintroid.command.serialization;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.util.Log;
import androidx.core.content.res.ResourcesCompat;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.command.implementation.TextToolCommand;
import org.catrobat.paintroid.tools.FontType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: TextToolCommandSerializer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J(\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u000e\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u000eH\u0016J(\u0010\u000f\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u000e\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u000eH\u0016J \u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0002H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/TextToolCommandSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/command/implementation/TextToolCommand;", "version", "", "activityContext", "Landroid/content/Context;", "(ILandroid/content/Context;)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "command", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class TextToolCommandSerializer extends VersionSerializer<TextToolCommand> {
    private final Context activityContext;

    /* JADX INFO: compiled from: TextToolCommandSerializer.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FontType.values().length];
            iArr[FontType.SANS_SERIF.ordinal()] = 1;
            iArr[FontType.SERIF.ordinal()] = 2;
            iArr[FontType.MONOSPACE.ordinal()] = 3;
            iArr[FontType.STC.ordinal()] = 4;
            iArr[FontType.DUBAI.ordinal()] = 5;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextToolCommandSerializer(int i, Context activityContext) {
        super(i);
        Intrinsics.checkNotNullParameter(activityContext, "activityContext");
        this.activityContext = activityContext;
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
        return read(kryo, input, (Class<? extends TextToolCommand>) cls);
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, TextToolCommand command) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(command, "command");
        kryo.writeObject(output, command.getMultilineText());
        kryo.writeObject(output, command.getTextPaint());
        output.writeFloat(command.getBoxOffset());
        output.writeFloat(command.getBoxWidth());
        output.writeFloat(command.getBoxHeight());
        kryo.writeObject(output, command.getToolPosition());
        output.writeFloat(command.getRotationAngle());
        kryo.writeObject(output, command.getTypeFaceInfo());
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public TextToolCommand read(Kryo kryo, Input input, Class<? extends TextToolCommand> type) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        return (TextToolCommand) super.handleVersions(this, kryo, input, type);
    }

    @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
    public TextToolCommand readCurrentVersion(Kryo kryo, Input input, Class<? extends TextToolCommand> type) {
        Typeface typefaceCreate;
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        String[] text = (String[]) kryo.readObject(input, String[].class);
        Paint paint = (Paint) kryo.readObject(input, Paint.class);
        float f = input.readFloat();
        float f2 = input.readFloat();
        float f3 = input.readFloat();
        PointF position = (PointF) kryo.readObject(input, PointF.class);
        float f4 = input.readFloat();
        SerializableTypeface typeFaceInfo = (SerializableTypeface) kryo.readObject(input, SerializableTypeface.class);
        paint.setFakeBoldText(typeFaceInfo.getBold());
        paint.setUnderlineText(typeFaceInfo.getUnderline());
        paint.setTextSize(typeFaceInfo.getTextSize());
        paint.setTextSkewX(typeFaceInfo.getTextSkewX());
        int i = typeFaceInfo.getItalic() ? 2 : 0;
        try {
            int i2 = WhenMappings.$EnumSwitchMapping$0[typeFaceInfo.getFont().ordinal()];
            if (i2 == 1) {
                typefaceCreate = Typeface.create(Typeface.SANS_SERIF, i);
            } else if (i2 == 2) {
                typefaceCreate = Typeface.create(Typeface.SERIF, i);
            } else if (i2 == 3) {
                typefaceCreate = Typeface.create(Typeface.MONOSPACE, i);
            } else if (i2 == 4) {
                typefaceCreate = ResourcesCompat.getFont(this.activityContext, R.font.stc_regular);
            } else {
                if (i2 != 5) {
                    throw new NoWhenBranchMatchedException();
                }
                typefaceCreate = ResourcesCompat.getFont(this.activityContext, R.font.dubai);
            }
        } catch (Exception unused) {
            Log.e("LoadImageAsync", "Typeface not supported on this mobile phone");
            typefaceCreate = Typeface.create(Typeface.SANS_SERIF, i);
        }
        paint.setTypeface(typefaceCreate);
        Intrinsics.checkNotNullExpressionValue(text, "text");
        Intrinsics.checkNotNullExpressionValue(paint, "paint");
        Intrinsics.checkNotNullExpressionValue(position, "position");
        Intrinsics.checkNotNullExpressionValue(typeFaceInfo, "typeFaceInfo");
        return new TextToolCommand(text, paint, f, f2, f3, position, f4, typeFaceInfo);
    }
}
