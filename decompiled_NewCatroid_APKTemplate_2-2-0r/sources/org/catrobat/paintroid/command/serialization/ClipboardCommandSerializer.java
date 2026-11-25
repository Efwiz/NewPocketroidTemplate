package org.catrobat.paintroid.command.serialization;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.FileIO;
import org.catrobat.paintroid.command.implementation.ClipboardCommand;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: ClipboardCommandSerializer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00132\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0013B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0014"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/ClipboardCommandSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/command/implementation/ClipboardCommand;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "command", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ClipboardCommandSerializer extends VersionSerializer<ClipboardCommand> {
    private static final int COMPRESSION_QUALITY = 100;

    public ClipboardCommandSerializer(int i) {
        super(i);
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
        return read(kryo, input, (Class<? extends ClipboardCommand>) cls);
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, ClipboardCommand command) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(command, "command");
        File fileToStoredBitmap = command.getFileToStoredBitmap();
        Bitmap bitmapFromFile = fileToStoredBitmap == null ? null : FileIO.INSTANCE.getBitmapFromFile(fileToStoredBitmap);
        if (bitmapFromFile == null && (bitmapFromFile = command.getBitmap()) == null) {
            throw new KryoException();
        }
        bitmapFromFile.compress(Bitmap.CompressFormat.PNG, 100, output);
        kryo.writeObject(output, command.getCoordinates());
        output.writeFloat(command.getBoxWidth());
        output.writeFloat(command.getBoxHeight());
        output.writeFloat(command.getBoxRotation());
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public ClipboardCommand read(Kryo kryo, Input input, Class<? extends ClipboardCommand> type) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        return (ClipboardCommand) super.handleVersions(this, kryo, input, type);
    }

    @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
    public ClipboardCommand readCurrentVersion(Kryo kryo, Input input, Class<? extends ClipboardCommand> type) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        Point coordinates = (Point) kryo.readObject(input, Point.class);
        float f = input.readFloat();
        float f2 = input.readFloat();
        float f3 = input.readFloat();
        Intrinsics.checkNotNullExpressionValue(bitmap, "bitmap");
        Intrinsics.checkNotNullExpressionValue(coordinates, "coordinates");
        return new ClipboardCommand(bitmap, coordinates, f, f2, f3);
    }
}
