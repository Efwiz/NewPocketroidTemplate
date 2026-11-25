package org.catrobat.paintroid.command.serialization;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Paint;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.tools.implementation.DefaultToolPaint;
import org.catrobat.paintroid.tools.implementation.WatercolorTool;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: PaintSerializer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J(\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u000e\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u000eH\u0016J(\u0010\u000f\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u000e\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u000eH\u0016J6\u0010\u0010\u001a\u00020\u00022\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u000e\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u000eH\u0014J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0002H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/PaintSerializer;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Landroid/graphics/Paint;", "version", "", "activityContext", "Landroid/content/Context;", "(ILandroid/content/Context;)V", "read", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "input", "Lcom/esotericsoftware/kryo/io/Input;", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "readCurrentVersion", "readV1", "serializer", "write", "", "output", "Lcom/esotericsoftware/kryo/io/Output;", "paint", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class PaintSerializer extends VersionSerializer<Paint> {
    private final Context activityContext;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PaintSerializer(int i, Context activityContext) {
        super(i);
        Intrinsics.checkNotNullParameter(activityContext, "activityContext");
        this.activityContext = activityContext;
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public /* bridge */ /* synthetic */ Object read(Kryo kryo, Input input, Class cls) {
        return read(kryo, input, (Class<? extends Paint>) cls);
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public void write(Kryo kryo, Output output, Paint paint) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(output, "output");
        Intrinsics.checkNotNullParameter(paint, "paint");
        output.writeInt(paint.getColor());
        output.writeFloat(paint.getStrokeWidth());
        output.writeInt(paint.getStrokeCap().ordinal());
        output.writeBoolean(paint.isAntiAlias());
        output.writeInt(paint.getStyle().ordinal());
        output.writeInt(paint.getStrokeJoin().ordinal());
        output.writeBoolean(paint.getMaskFilter() != null);
        output.writeInt(paint.getAlpha());
    }

    @Override // com.esotericsoftware.kryo.Serializer
    public Paint read(Kryo kryo, Input input, Class<? extends Paint> type) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        return (Paint) super.handleVersions(this, kryo, input, type);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
    public Paint readV1(VersionSerializer<Paint> serializer, Kryo kryo, Input input, Class<? extends Paint> type) {
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        DefaultToolPaint defaultToolPaint = new DefaultToolPaint(this.activityContext);
        defaultToolPaint.setColor(input.readInt());
        defaultToolPaint.setStrokeWidth(input.readFloat());
        defaultToolPaint.setStrokeCap(Paint.Cap.values()[input.readInt()]);
        Paint bitmapPaint = defaultToolPaint.getBitmapPaint();
        bitmapPaint.setAntiAlias(input.readBoolean());
        bitmapPaint.setStyle(Paint.Style.values()[input.readInt()]);
        bitmapPaint.setStrokeJoin(Paint.Join.values()[input.readInt()]);
        return bitmapPaint;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.catrobat.paintroid.command.serialization.VersionSerializer
    public Paint readCurrentVersion(Kryo kryo, Input input, Class<? extends Paint> type) {
        Intrinsics.checkNotNullParameter(kryo, "kryo");
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        DefaultToolPaint defaultToolPaint = new DefaultToolPaint(this.activityContext);
        defaultToolPaint.setColor(input.readInt());
        defaultToolPaint.setStrokeWidth(input.readFloat());
        defaultToolPaint.setStrokeCap(Paint.Cap.values()[input.readInt()]);
        Paint bitmapPaint = defaultToolPaint.getBitmapPaint();
        bitmapPaint.setAntiAlias(input.readBoolean());
        bitmapPaint.setStyle(Paint.Style.values()[input.readInt()]);
        bitmapPaint.setStrokeJoin(Paint.Join.values()[input.readInt()]);
        boolean z = input.readBoolean();
        bitmapPaint.setAlpha(input.readInt());
        if (z) {
            bitmapPaint.setMaskFilter(new BlurMaskFilter(WatercolorTool.INSTANCE.calcRange(bitmapPaint.getAlpha()), BlurMaskFilter.Blur.INNER));
        }
        return bitmapPaint;
    }
}
