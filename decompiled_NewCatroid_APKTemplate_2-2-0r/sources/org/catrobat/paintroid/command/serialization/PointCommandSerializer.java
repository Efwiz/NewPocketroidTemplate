package org.catrobat.paintroid.command.serialization;

import android.graphics.Paint;
import android.graphics.PointF;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.implementation.PointCommand;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: PointCommandSerializer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/PointCommandSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/command/implementation/PointCommand;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "command", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class PointCommandSerializer extends VersionSerializer<PointCommand> {
    public PointCommandSerializer(int i) {
        super(i);
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
        return read(kryo, input, (Class<? extends PointCommand>) cls);
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, PointCommand command) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(command, "command");
        kryo.writeObject(output, command.getPoint());
        kryo.writeObject(output, command.getPaint());
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public PointCommand read(Kryo kryo, Input input, Class<? extends PointCommand> type) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        return (PointCommand) super.handleVersions(this, kryo, input, type);
    }

    @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
    public PointCommand readCurrentVersion(Kryo kryo, Input input, Class<? extends PointCommand> type) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        PointF point = (PointF) kryo.readObject(input, PointF.class);
        Paint paint = (Paint) kryo.readObject(input, Paint.class);
        Intrinsics.checkNotNullExpressionValue(paint, "paint");
        Intrinsics.checkNotNullExpressionValue(point, "point");
        return new PointCommand(paint, point);
    }
}
