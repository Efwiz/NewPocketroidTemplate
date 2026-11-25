package org.catrobat.catroid.plot;

import android.graphics.PointF;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/plot/SVGPlotGenerator.class
  classes14.dex
 */
/* JADX INFO: compiled from: SVGPlotGenerator.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0017\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\nH\u0002¢\u0006\u0002\u0010\u0010J\b\u0010\u0011\u001a\u00020\u000eH\u0002J/\u0010\u0012\u001a\u00020\u000e2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\n2\b\u0010\u0016\u001a\u0004\u0018\u00010\nH\u0002¢\u0006\u0002\u0010\u0017J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bR8\u0010\u0005\u001a,\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0018\u00010\u0006j\u001a\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b\u0018\u0001`\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u000bR\u0012\u0010\f\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u000b¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/plot/SVGPlotGenerator;", "", "plot", "Lorg/catrobat/catroid/plot/Plot;", "(Lorg/catrobat/catroid/plot/Plot;)V", "data", "Ljava/util/ArrayList;", "Landroid/graphics/PointF;", "Lkotlin/collections/ArrayList;", "height", "", "Ljava/lang/Float;", "width", "dotDecinalRound", "", "number", "(Ljava/lang/Float;)Ljava/lang/String;", "generateSVGContent", "generateSVGPath", Constant.LINE, "", "xAlignment", "yAlignment", "(Ljava/util/List;Ljava/lang/Float;Ljava/lang/Float;)Ljava/lang/String;", "writeToSVGFile", "", "targetFile", "Ljava/io/File;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SVGPlotGenerator {
    private final ArrayList<ArrayList<PointF>> data;
    private Float height;
    private Float width;

    public SVGPlotGenerator(Plot plot) {
        this.data = plot != null ? plot.data() : null;
        this.width = plot != null ? Float.valueOf(plot.getWidth()) : null;
        this.height = plot != null ? Float.valueOf(plot.getHeight()) : null;
    }

    private final String dotDecinalRound(Float number) {
        String str = String.format(Locale.ENGLISH, "%.2f", Arrays.copyOf(new Object[]{number}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    public final void writeToSVGFile(File targetFile) {
        Intrinsics.checkNotNullParameter(targetFile, "targetFile");
        FilesKt.writeText$default(targetFile, generateSVGContent(), null, 2, null);
    }

    private final String generateSVGPath(List<? extends PointF> line, Float xAlignment, Float yAlignment) {
        if (line.size() < 2) {
            return "";
        }
        float f = line.get(0).x;
        Intrinsics.checkNotNull(xAlignment);
        String strDotDecinalRound = dotDecinalRound(Float.valueOf(f - xAlignment.floatValue()));
        float f2 = line.get(0).y;
        Intrinsics.checkNotNull(yAlignment);
        String str = "<path fill=\"none\" style=\"stroke:rgb(0,0,0);stroke-width:1;stroke-linecap:round;stroke-opacity:1;\" d=\"M" + strDotDecinalRound + " " + dotDecinalRound(Float.valueOf(f2 - yAlignment.floatValue()));
        for (PointF pointF : line.subList(1, line.size())) {
            str = str + " L" + dotDecinalRound(Float.valueOf(pointF.x - xAlignment.floatValue())) + " " + dotDecinalRound(Float.valueOf(pointF.y - yAlignment.floatValue()));
        }
        return str + "\" />\n";
    }

    private final String generateSVGContent() {
        Float f = this.width;
        Float fValueOf = f != null ? Float.valueOf(f.floatValue() / (-2.0f)) : null;
        Float f2 = this.height;
        Float fValueOf2 = f2 != null ? Float.valueOf(f2.floatValue() / (-2.0f)) : null;
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<svg width=\"");
        sb.append(dotDecinalRound(this.width));
        sb.append("\" height=\"");
        sb.append(dotDecinalRound(this.height));
        sb.append("\" viewBox=\"");
        sb.append(dotDecinalRound(Float.valueOf(0.0f)));
        sb.append(" ");
        sb.append(dotDecinalRound(Float.valueOf(0.0f)));
        sb.append(" ");
        sb.append(dotDecinalRound(this.width));
        sb.append(" ");
        sb.append(dotDecinalRound(this.height));
        sb.append("\" style=\"background-color:#ffffff\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n<title>Plotter export</title>\n");
        ArrayList<ArrayList<PointF>> arrayList = this.data;
        if (arrayList != null) {
            for (ArrayList<PointF> arrayList2 : arrayList) {
                Intrinsics.checkNotNull(arrayList2);
                sb.append(generateSVGPath(arrayList2, fValueOf, fValueOf2));
            }
        }
        sb.append("\n</svg>");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
