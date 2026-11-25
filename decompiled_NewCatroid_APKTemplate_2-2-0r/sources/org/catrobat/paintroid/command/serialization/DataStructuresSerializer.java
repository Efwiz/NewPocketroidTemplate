package org.catrobat.paintroid.command.serialization;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: DataStructuresSerializer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0005\u0003\u0004\u0005\u0006\u0007B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\b"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/DataStructuresSerializer;", "", "()V", "FloatArraySerializer", "PointFSerializer", "PointSerializer", "RectFSerializer", "StringArraySerializer", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DataStructuresSerializer {

    /* JADX INFO: compiled from: DataStructuresSerializer.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/DataStructuresSerializer$FloatArraySerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "array", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class FloatArraySerializer extends VersionSerializer<float[]> {
        public FloatArraySerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends float[]>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, float[] array) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(array, "array");
            output.writeInt(array.length);
            for (float f : array) {
                output.writeFloat(f);
            }
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public float[] read(Kryo kryo, Input input, Class<? extends float[]> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (float[]) super.handleVersions(this, kryo, input, type);
        }

        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public float[] readCurrentVersion(Kryo kryo, Input input, Class<? extends float[]> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            int i = input.readInt();
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < i; i2++) {
                arrayList.add(Float.valueOf(input.readFloat()));
            }
            return CollectionsKt.toFloatArray(arrayList);
        }
    }

    /* JADX INFO: compiled from: DataStructuresSerializer.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J9\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0014\u0010\f\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\rH\u0016¢\u0006\u0002\u0010\u000eJ9\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0014\u0010\f\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\rH\u0016¢\u0006\u0002\u0010\u000eJ+\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016¢\u0006\u0002\u0010\u0015¨\u0006\u0016"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/DataStructuresSerializer$StringArraySerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "", "", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "(Lcom/esotericsoftware/kryo/Kryo;Lcom/esotericsoftware/kryo/io/Input;Ljava/lang/Class;)[Ljava/lang/String;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "array", "(Lcom/esotericsoftware/kryo/Kryo;Lcom/esotericsoftware/kryo/io/Output;[Ljava/lang/String;)V", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class StringArraySerializer extends VersionSerializer<String[]> {
        public StringArraySerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends String[]>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, String[] array) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(array, "array");
            output.writeInt(array.length);
            for (String str : array) {
                output.writeString(str);
            }
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public String[] read(Kryo kryo, Input input, Class<? extends String[]> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (String[]) super.handleVersions(this, kryo, input, type);
        }

        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public String[] readCurrentVersion(Kryo kryo, Input input, Class<? extends String[]> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            int i = input.readInt();
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < i; i2++) {
                arrayList.add(input.readString());
            }
            Object[] array = arrayList.toArray(new String[0]);
            if (array != null) {
                return (String[]) array;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
    }

    /* JADX INFO: compiled from: DataStructuresSerializer.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/DataStructuresSerializer$RectFSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Landroid/graphics/RectF;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "rect", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class RectFSerializer extends VersionSerializer<RectF> {
        public RectFSerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends RectF>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, RectF rect) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(rect, "rect");
            output.writeFloat(rect.left);
            output.writeFloat(rect.top);
            output.writeFloat(rect.right);
            output.writeFloat(rect.bottom);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public RectF read(Kryo kryo, Input input, Class<? extends RectF> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (RectF) super.handleVersions(this, kryo, input, type);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public RectF readCurrentVersion(Kryo kryo, Input input, Class<? extends RectF> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return new RectF(input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat());
        }
    }

    /* JADX INFO: compiled from: DataStructuresSerializer.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/DataStructuresSerializer$PointFSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Landroid/graphics/PointF;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "rect", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class PointFSerializer extends VersionSerializer<PointF> {
        public PointFSerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends PointF>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, PointF rect) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(rect, "rect");
            output.writeFloat(rect.x);
            output.writeFloat(rect.y);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public PointF read(Kryo kryo, Input input, Class<? extends PointF> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (PointF) super.handleVersions(this, kryo, input, type);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public PointF readCurrentVersion(Kryo kryo, Input input, Class<? extends PointF> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return new PointF(input.readFloat(), input.readFloat());
        }
    }

    /* JADX INFO: compiled from: DataStructuresSerializer.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/DataStructuresSerializer$PointSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Landroid/graphics/Point;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "rect", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class PointSerializer extends VersionSerializer<Point> {
        public PointSerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Point>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Point rect) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(rect, "rect");
            output.writeInt(rect.x);
            output.writeInt(rect.y);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Point read(Kryo kryo, Input input, Class<? extends Point> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (Point) super.handleVersions(this, kryo, input, type);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public Point readCurrentVersion(Kryo kryo, Input input, Class<? extends Point> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return new Point(input.readInt(), input.readInt());
        }
    }
}
