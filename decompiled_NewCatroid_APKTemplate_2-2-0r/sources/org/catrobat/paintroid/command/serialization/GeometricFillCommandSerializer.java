package org.catrobat.paintroid.command.serialization;

import android.graphics.Paint;
import android.graphics.RectF;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.implementation.GeometricFillCommand;
import org.catrobat.paintroid.tools.drawable.HeartDrawable;
import org.catrobat.paintroid.tools.drawable.OvalDrawable;
import org.catrobat.paintroid.tools.drawable.RectangleDrawable;
import org.catrobat.paintroid.tools.drawable.ShapeDrawable;
import org.catrobat.paintroid.tools.drawable.StarDrawable;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: GeometricFillCommandSerializer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0004\u0013\u0014\u0015\u0016B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0017"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/GeometricFillCommandSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/command/implementation/GeometricFillCommand;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "command", "HeartDrawableSerializer", "OvalDrawableSerializer", "RectangleDrawableSerializer", "StarDrawableSerializer", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class GeometricFillCommandSerializer extends VersionSerializer<GeometricFillCommand> {
    public GeometricFillCommandSerializer(int i) {
        super(i);
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
        return read(kryo, input, (Class<? extends GeometricFillCommand>) cls);
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, GeometricFillCommand command) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(command, "command");
        kryo.writeClassAndObject(output, command.getShapeDrawable());
        output.writeInt(command.getPointX());
        output.writeInt(command.getPointY());
        kryo.writeObject(output, command.getBoxRect());
        output.writeFloat(command.getBoxRotation());
        kryo.writeObject(output, command.getPaint());
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public GeometricFillCommand read(Kryo kryo, Input input, Class<? extends GeometricFillCommand> type) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        return (GeometricFillCommand) super.handleVersions(this, kryo, input, type);
    }

    @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
    public GeometricFillCommand readCurrentVersion(Kryo kryo, Input input, Class<? extends GeometricFillCommand> type) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        Object classAndObject = kryo.readClassAndObject(input);
        if (classAndObject == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.tools.drawable.ShapeDrawable");
        }
        ShapeDrawable shapeDrawable = (ShapeDrawable) classAndObject;
        int i = input.readInt();
        int i2 = input.readInt();
        RectF rect = (RectF) kryo.readObject(input, RectF.class);
        float f = input.readFloat();
        Paint paint = (Paint) kryo.readObject(input, Paint.class);
        Intrinsics.checkNotNullExpressionValue(rect, "rect");
        Intrinsics.checkNotNullExpressionValue(paint, "paint");
        return new GeometricFillCommand(shapeDrawable, i, i2, rect, f, paint);
    }

    /* JADX INFO: compiled from: GeometricFillCommandSerializer.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/GeometricFillCommandSerializer$HeartDrawableSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/tools/drawable/HeartDrawable;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "command", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class HeartDrawableSerializer extends VersionSerializer<HeartDrawable> {
        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, HeartDrawable command) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(command, "command");
        }

        public HeartDrawableSerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends HeartDrawable>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public HeartDrawable read(Kryo kryo, Input input, Class<? extends HeartDrawable> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (HeartDrawable) super.handleVersions(this, kryo, input, type);
        }

        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public HeartDrawable readCurrentVersion(Kryo kryo, Input input, Class<? extends HeartDrawable> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return new HeartDrawable();
        }
    }

    /* JADX INFO: compiled from: GeometricFillCommandSerializer.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/GeometricFillCommandSerializer$OvalDrawableSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/tools/drawable/OvalDrawable;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "command", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class OvalDrawableSerializer extends VersionSerializer<OvalDrawable> {
        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, OvalDrawable command) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(command, "command");
        }

        public OvalDrawableSerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends OvalDrawable>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public OvalDrawable read(Kryo kryo, Input input, Class<? extends OvalDrawable> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (OvalDrawable) super.handleVersions(this, kryo, input, type);
        }

        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public OvalDrawable readCurrentVersion(Kryo kryo, Input input, Class<? extends OvalDrawable> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return new OvalDrawable();
        }
    }

    /* JADX INFO: compiled from: GeometricFillCommandSerializer.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/GeometricFillCommandSerializer$RectangleDrawableSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/tools/drawable/RectangleDrawable;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "command", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class RectangleDrawableSerializer extends VersionSerializer<RectangleDrawable> {
        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, RectangleDrawable command) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(command, "command");
        }

        public RectangleDrawableSerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends RectangleDrawable>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public RectangleDrawable read(Kryo kryo, Input input, Class<? extends RectangleDrawable> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (RectangleDrawable) super.handleVersions(this, kryo, input, type);
        }

        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public RectangleDrawable readCurrentVersion(Kryo kryo, Input input, Class<? extends RectangleDrawable> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return new RectangleDrawable();
        }
    }

    /* JADX INFO: compiled from: GeometricFillCommandSerializer.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/GeometricFillCommandSerializer$StarDrawableSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/tools/drawable/StarDrawable;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "command", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class StarDrawableSerializer extends VersionSerializer<StarDrawable> {
        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, StarDrawable command) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(command, "command");
        }

        public StarDrawableSerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends StarDrawable>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public StarDrawable read(Kryo kryo, Input input, Class<? extends StarDrawable> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (StarDrawable) super.handleVersions(this, kryo, input, type);
        }

        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public StarDrawable readCurrentVersion(Kryo kryo, Input input, Class<? extends StarDrawable> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return new StarDrawable();
        }
    }
}
