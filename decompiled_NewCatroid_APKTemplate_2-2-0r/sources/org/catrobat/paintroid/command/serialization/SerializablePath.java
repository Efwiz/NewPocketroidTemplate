package org.catrobat.paintroid.command.serialization;

import android.graphics.Path;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: SerializablePath.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0018\b\u0016\u0018\u00002\u00020\u0001:\f\u001d\u001e\u001f !\"#$%&'(B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B\u001f\b\u0016\u0012\u0016\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006¢\u0006\u0002\u0010\u0007B\u000f\b\u0016\u0012\u0006\u0010\b\u001a\u00020\u0000¢\u0006\u0002\u0010\tJ8\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0011H\u0016J\u0018\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u0011H\u0016J\u0018\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u0011H\u0016J(\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0011H\u0016J\b\u0010\u001c\u001a\u00020\u000fH\u0016R*\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u0007¨\u0006)"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializablePath;", "Landroid/graphics/Path;", "()V", "actions", "Ljava/util/ArrayList;", "Lorg/catrobat/paintroid/command/serialization/SerializablePath$SerializableAction;", "Lkotlin/collections/ArrayList;", "(Ljava/util/ArrayList;)V", "src", "(Lorg/catrobat/paintroid/command/serialization/SerializablePath;)V", "serializableActions", "getSerializableActions", "()Ljava/util/ArrayList;", "setSerializableActions", "cubicTo", "", "x1", "", "y1", "x2", "y2", "x3", "y3", "lineTo", "x", "y", "moveTo", "quadTo", "rewind", "Cube", "Line", "Move", "PathActionCubeSerializer", "PathActionLineSerializer", "PathActionMoveSerializer", "PathActionQuadSerializer", "PathActionRewindSerializer", "PathSerializer", "Quad", "Rewind", "SerializableAction", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class SerializablePath extends Path {
    private ArrayList<SerializableAction> serializableActions;

    /* JADX INFO: compiled from: SerializablePath.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializablePath$SerializableAction;", "", "perform", "", "path", "Landroid/graphics/Path;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface SerializableAction {
        void perform(Path path);
    }

    public final ArrayList<SerializableAction> getSerializableActions() {
        return this.serializableActions;
    }

    public final void setSerializableActions(ArrayList<SerializableAction> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.serializableActions = arrayList;
    }

    public SerializablePath() {
        this.serializableActions = new ArrayList<>();
    }

    public SerializablePath(ArrayList<SerializableAction> actions) {
        Intrinsics.checkNotNullParameter(actions, "actions");
        this.serializableActions = new ArrayList<>();
        Iterator<T> it = actions.iterator();
        while (it.hasNext()) {
            ((SerializableAction) it.next()).perform(this);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SerializablePath(SerializablePath src) {
        super(src);
        Intrinsics.checkNotNullParameter(src, "src");
        ArrayList<SerializableAction> arrayList = new ArrayList<>();
        this.serializableActions = arrayList;
        arrayList.addAll(src.serializableActions);
    }

    @Override // android.graphics.Path
    public void moveTo(float x, float y) {
        this.serializableActions.add(new Move(x, y));
        super.moveTo(x, y);
    }

    @Override // android.graphics.Path
    public void lineTo(float x, float y) {
        this.serializableActions.add(new Line(x, y));
        super.lineTo(x, y);
    }

    @Override // android.graphics.Path
    public void quadTo(float x1, float y1, float x2, float y2) {
        this.serializableActions.add(new Quad(x1, y1, x2, y2));
        super.quadTo(x1, y1, x2, y2);
    }

    @Override // android.graphics.Path
    public void cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) {
        this.serializableActions.add(new Cube(x1, y1, x2, y2, x3, y3));
        super.cubicTo(x1, y1, x2, y2, x3, y3);
    }

    @Override // android.graphics.Path
    public void rewind() {
        this.serializableActions.clear();
        super.rewind();
    }

    /* JADX INFO: compiled from: SerializablePath.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\r"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializablePath$Move;", "Lorg/catrobat/paintroid/command/serialization/SerializablePath$SerializableAction;", "x", "", "y", "(FF)V", "getX", "()F", "getY", "perform", "", "path", "Landroid/graphics/Path;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Move implements SerializableAction {
        private final float x;
        private final float y;

        public Move(float f, float f2) {
            this.x = f;
            this.y = f2;
        }

        public final float getX() {
            return this.x;
        }

        public final float getY() {
            return this.y;
        }

        @Override // org.catrobat.paintroid.command.serialization.SerializablePath.SerializableAction
        public void perform(Path path) {
            Intrinsics.checkNotNullParameter(path, "path");
            path.moveTo(this.x, this.y);
        }
    }

    /* JADX INFO: compiled from: SerializablePath.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\r"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializablePath$Line;", "Lorg/catrobat/paintroid/command/serialization/SerializablePath$SerializableAction;", "x", "", "y", "(FF)V", "getX", "()F", "getY", "perform", "", "path", "Landroid/graphics/Path;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Line implements SerializableAction {
        private final float x;
        private final float y;

        public Line(float f, float f2) {
            this.x = f;
            this.y = f2;
        }

        public final float getX() {
            return this.x;
        }

        public final float getY() {
            return this.y;
        }

        @Override // org.catrobat.paintroid.command.serialization.SerializablePath.SerializableAction
        public void perform(Path path) {
            Intrinsics.checkNotNullParameter(path, "path");
            path.lineTo(this.x, this.y);
        }
    }

    /* JADX INFO: compiled from: SerializablePath.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0011"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializablePath$Quad;", "Lorg/catrobat/paintroid/command/serialization/SerializablePath$SerializableAction;", "x1", "", "y1", "x2", "y2", "(FFFF)V", "getX1", "()F", "getX2", "getY1", "getY2", "perform", "", "path", "Landroid/graphics/Path;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Quad implements SerializableAction {
        private final float x1;
        private final float x2;
        private final float y1;
        private final float y2;

        public Quad(float f, float f2, float f3, float f4) {
            this.x1 = f;
            this.y1 = f2;
            this.x2 = f3;
            this.y2 = f4;
        }

        public final float getX1() {
            return this.x1;
        }

        public final float getX2() {
            return this.x2;
        }

        public final float getY1() {
            return this.y1;
        }

        public final float getY2() {
            return this.y2;
        }

        @Override // org.catrobat.paintroid.command.serialization.SerializablePath.SerializableAction
        public void perform(Path path) {
            Intrinsics.checkNotNullParameter(path, "path");
            path.quadTo(this.x1, this.y1, this.x2, this.y2);
        }
    }

    /* JADX INFO: compiled from: SerializablePath.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\tJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u0015"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializablePath$Cube;", "Lorg/catrobat/paintroid/command/serialization/SerializablePath$SerializableAction;", "x1", "", "y1", "x2", "y2", "x3", "y3", "(FFFFFF)V", "getX1", "()F", "getX2", "getX3", "getY1", "getY2", "getY3", "perform", "", "path", "Landroid/graphics/Path;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Cube implements SerializableAction {
        private final float x1;
        private final float x2;
        private final float x3;
        private final float y1;
        private final float y2;
        private final float y3;

        public Cube(float f, float f2, float f3, float f4, float f5, float f6) {
            this.x1 = f;
            this.y1 = f2;
            this.x2 = f3;
            this.y2 = f4;
            this.x3 = f5;
            this.y3 = f6;
        }

        public final float getX1() {
            return this.x1;
        }

        public final float getX2() {
            return this.x2;
        }

        public final float getX3() {
            return this.x3;
        }

        public final float getY1() {
            return this.y1;
        }

        public final float getY2() {
            return this.y2;
        }

        public final float getY3() {
            return this.y3;
        }

        @Override // org.catrobat.paintroid.command.serialization.SerializablePath.SerializableAction
        public void perform(Path path) {
            Intrinsics.checkNotNullParameter(path, "path");
            path.cubicTo(this.x1, this.y1, this.x2, this.y2, this.x3, this.y3);
        }
    }

    /* JADX INFO: compiled from: SerializablePath.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializablePath$Rewind;", "Lorg/catrobat/paintroid/command/serialization/SerializablePath$SerializableAction;", "()V", "perform", "", "path", "Landroid/graphics/Path;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Rewind implements SerializableAction {
        @Override // org.catrobat.paintroid.command.serialization.SerializablePath.SerializableAction
        public void perform(Path path) {
            Intrinsics.checkNotNullParameter(path, "path");
            path.rewind();
        }
    }

    /* JADX INFO: compiled from: SerializablePath.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializablePath$PathSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/command/serialization/SerializablePath;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "path", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class PathSerializer extends VersionSerializer<SerializablePath> {
        public PathSerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends SerializablePath>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, SerializablePath path) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(path, "path");
            output.writeInt(path.getSerializableActions().size());
            Iterator<T> it = path.getSerializableActions().iterator();
            while (it.hasNext()) {
                kryo.writeClassAndObject(output, (SerializableAction) it.next());
            }
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public SerializablePath read(Kryo kryo, Input input, Class<? extends SerializablePath> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (SerializablePath) super.handleVersions(this, kryo, input, type);
        }

        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public SerializablePath readCurrentVersion(Kryo kryo, Input input, Class<? extends SerializablePath> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            int i = input.readInt();
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < i; i2++) {
                Object classAndObject = kryo.readClassAndObject(input);
                if (classAndObject == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.command.serialization.SerializablePath.SerializableAction");
                }
                arrayList.add((SerializableAction) classAndObject);
            }
            return new SerializablePath((ArrayList<SerializableAction>) arrayList);
        }
    }

    /* JADX INFO: compiled from: SerializablePath.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializablePath$PathActionMoveSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/command/serialization/SerializablePath$Move;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "action", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class PathActionMoveSerializer extends VersionSerializer<Move> {
        public PathActionMoveSerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Move>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Move action) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(action, "action");
            output.writeFloat(action.getX());
            output.writeFloat(action.getY());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Move read(Kryo kryo, Input input, Class<? extends Move> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (Move) super.handleVersions(this, kryo, input, type);
        }

        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public Move readCurrentVersion(Kryo kryo, Input input, Class<? extends Move> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return new Move(input.readFloat(), input.readFloat());
        }
    }

    /* JADX INFO: compiled from: SerializablePath.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializablePath$PathActionLineSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/command/serialization/SerializablePath$Line;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "action", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class PathActionLineSerializer extends VersionSerializer<Line> {
        public PathActionLineSerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Line>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Line action) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(action, "action");
            output.writeFloat(action.getX());
            output.writeFloat(action.getY());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Line read(Kryo kryo, Input input, Class<? extends Line> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (Line) super.handleVersions(this, kryo, input, type);
        }

        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public Line readCurrentVersion(Kryo kryo, Input input, Class<? extends Line> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return new Line(input.readFloat(), input.readFloat());
        }
    }

    /* JADX INFO: compiled from: SerializablePath.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializablePath$PathActionQuadSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/command/serialization/SerializablePath$Quad;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "action", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class PathActionQuadSerializer extends VersionSerializer<Quad> {
        public PathActionQuadSerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Quad>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Quad action) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(action, "action");
            output.writeFloat(action.getX1());
            output.writeFloat(action.getY1());
            output.writeFloat(action.getX2());
            output.writeFloat(action.getY2());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Quad read(Kryo kryo, Input input, Class<? extends Quad> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (Quad) super.handleVersions(this, kryo, input, type);
        }

        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public Quad readCurrentVersion(Kryo kryo, Input input, Class<? extends Quad> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return new Quad(input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat());
        }
    }

    /* JADX INFO: compiled from: SerializablePath.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializablePath$PathActionCubeSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/command/serialization/SerializablePath$Cube;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "action", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class PathActionCubeSerializer extends VersionSerializer<Cube> {
        public PathActionCubeSerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Cube>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Cube action) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(action, "action");
            output.writeFloat(action.getX1());
            output.writeFloat(action.getY1());
            output.writeFloat(action.getX2());
            output.writeFloat(action.getY2());
            output.writeFloat(action.getX3());
            output.writeFloat(action.getY3());
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Cube read(Kryo kryo, Input input, Class<? extends Cube> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (Cube) super.handleVersions(this, kryo, input, type);
        }

        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public Cube readCurrentVersion(Kryo kryo, Input input, Class<? extends Cube> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return new Cube(input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat());
        }
    }

    /* JADX INFO: compiled from: SerializablePath.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J(\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J(\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\fH\u0016J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016¨\u0006\u0013"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/SerializablePath$PathActionRewindSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lorg/catrobat/paintroid/command/serialization/SerializablePath$Rewind;", "version", "", "(I)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "action", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class PathActionRewindSerializer extends VersionSerializer<Rewind> {
        @Override // com.esotericsoftware.kryo.Serializer
        public void write(Kryo kryo, Output output, Rewind action) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(action, "action");
        }

        public PathActionRewindSerializer(int i) {
            super(i);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
            return read(kryo, input, (Class<? extends Rewind>) cls);
        }

        @Override // com.esotericsoftware.kryo.Serializer
        public Rewind read(Kryo kryo, Input input, Class<? extends Rewind> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return (Rewind) super.handleVersions(this, kryo, input, type);
        }

        @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
        public Rewind readCurrentVersion(Kryo kryo, Input input, Class<? extends Rewind> type) {
            Intrinsics.checkNotNullParameter(kryo, "kryo");
            Intrinsics.checkNotNullParameter(input, "input");
            Intrinsics.checkNotNullParameter(type, "type");
            return new Rewind();
        }
    }
}
