package org.catrobat.paintroid.command.serialization;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.implementation.SmudgePathCommand;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: SmudgePathCommandSerializer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00132\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0013B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0014"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SmudgePathCommandSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/command/implementation/SmudgePathCommand;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "command", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class SmudgePathCommandSerializer extends VersionSerializer<SmudgePathCommand> {
    private static final int COMPRESSION_QUALITY = 100;

    public SmudgePathCommandSerializer(int i) {
        super(i);
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
        return read(kryo, input, (Class<? extends SmudgePathCommand>) cls);
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, SmudgePathCommand command) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(command, "command");
        command.getOriginalBitmap().compress(Bitmap.CompressFormat.PNG, 100, output);
        output.writeInt(command.getPointPath().size());
        Iterator<T> it = command.getPointPath().iterator();
        while (it.hasNext()) {
            kryo.writeObject(output, (PointF) it.next());
        }
        output.writeFloat(command.getMaxPressure());
        output.writeFloat(command.getMaxSize());
        output.writeFloat(command.getMinSize());
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public SmudgePathCommand read(Kryo kryo, Input input, Class<? extends SmudgePathCommand> type) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        return (SmudgePathCommand) super.handleVersions(this, kryo, input, type);
    }

    @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
    public SmudgePathCommand readCurrentVersion(Kryo kryo, Input input, Class<? extends SmudgePathCommand> type) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        Bitmap originalBitmap = BitmapFactory.decodeStream(input);
        ArrayList arrayList = new ArrayList();
        int i = input.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            Object object = kryo.readObject(input, PointF.class);
            Intrinsics.checkNotNullExpressionValue(object, "readObject(input, PointF::class.java)");
            arrayList.add(object);
        }
        float f = input.readFloat();
        float f2 = input.readFloat();
        float f3 = input.readFloat();
        Intrinsics.checkNotNullExpressionValue(originalBitmap, "originalBitmap");
        return new SmudgePathCommand(originalBitmap, arrayList, f, f2, f3);
    }
}
