package org.catrobat.paintroid.tools.helper;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JavaFillAlgorithm.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0018\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u000f\n\u0002\u0010\u0007\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001GB\u0005¢\u0006\u0002\u0010\u0002J \u00101\u001a\u0002022\u0006\u00103\u001a\u00020(2\u0006\u00104\u001a\u00020\r2\u0006\u00105\u001a\u00020\u0018H\u0002J \u00106\u001a\u00020(2\u0006\u00104\u001a\u00020\r2\u0006\u00107\u001a\u00020\r2\u0006\u00108\u001a\u00020\u0018H\u0002J\u0018\u00109\u001a\u00020\r2\u0006\u00104\u001a\u00020\r2\u0006\u00107\u001a\u00020\rH\u0002J\u0018\u0010:\u001a\u00020\r2\u0006\u00104\u001a\u00020\r2\u0006\u00107\u001a\u00020\rH\u0002J\u0018\u0010;\u001a\u00020\u00182\u0006\u0010<\u001a\u00020\r2\u0006\u0010=\u001a\u00020\rH\u0002J\b\u0010>\u001a\u000202H\u0016J0\u0010?\u001a\u0002022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010,\u001a\u00020\r2\u0006\u0010@\u001a\u00020\r2\u0006\u0010A\u001a\u00020BH\u0016J\u0018\u0010C\u001a\u00020\u00182\u0006\u00104\u001a\u00020\r2\u0006\u00107\u001a\u00020\rH\u0002J\u0010\u0010D\u001a\u00020\r2\u0006\u0010E\u001a\u00020\rH\u0002J\u0018\u0010F\u001a\u00020\u00182\u0006\u00104\u001a\u00020\r2\u0006\u00107\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R$\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087.¢\u0006\u0014\n\u0000\u0012\u0004\b\u0007\u0010\u0002\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR$\u0010\f\u001a\u00020\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u000e\u0010\u0002\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R$\u0010\u0013\u001a\u00020\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0014\u0010\u0002\u001a\u0004\b\u0015\u0010\u0010\"\u0004\b\u0016\u0010\u0012R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aX\u0082.¢\u0006\u0004\n\u0002\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R,\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001a8\u0006@\u0006X\u0087.¢\u0006\u0016\n\u0002\u0010%\u0012\u0004\b \u0010\u0002\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\"\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'8\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b)\u0010\u0002\u001a\u0004\b*\u0010+R$\u0010,\u001a\u00020\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b-\u0010\u0002\u001a\u0004\b.\u0010\u0010\"\u0004\b/\u0010\u0012R\u000e\u00100\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006H"}, d2 = {"Lorg/catrobat/paintroid/tools/helper/JavaFillAlgorithm;", "Lorg/catrobat/paintroid/tools/helper/FillAlgorithm;", "()V", "bitmap", "Landroid/graphics/Bitmap;", "clickedPixel", "Landroid/graphics/Point;", "getClickedPixel$annotations", "getClickedPixel", "()Landroid/graphics/Point;", "setClickedPixel", "(Landroid/graphics/Point;)V", "colorToBeReplaced", "", "getColorToBeReplaced$annotations", "getColorToBeReplaced", "()I", "setColorToBeReplaced", "(I)V", "colorToleranceThresholdSquared", "getColorToleranceThresholdSquared$annotations", "getColorToleranceThresholdSquared", "setColorToleranceThresholdSquared", "considerTolerance", "", "filledPixels", "", "", "[[Z", "height", "pixels", "", "getPixels$annotations", "getPixels", "()[[I", "setPixels", "([[I)V", "[[I", "ranges", "Ljava/util/Queue;", "Lorg/catrobat/paintroid/tools/helper/JavaFillAlgorithm$Range;", "getRanges$annotations", "getRanges", "()Ljava/util/Queue;", "targetColor", "getTargetColor$annotations", "getTargetColor", "setTargetColor", "width", "checkRangeAndGenerateNewRanges", "", "range", "row", "directionUp", "generateRangeAndReplaceColor", "col", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "getEndIndex", "getStartIndex", "isPixelWithinColorTolerance", "pixel", "referenceColor", "performFilling", "setParameters", "replacementColor", "colorToleranceThreshold", "", "shouldCellBeFilled", "square", "x", "validateAndAssign", "Range", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class JavaFillAlgorithm implements FillAlgorithm {
    private Bitmap bitmap;
    public Point clickedPixel;
    private int colorToBeReplaced;
    private int colorToleranceThresholdSquared;
    private boolean considerTolerance;
    private boolean[][] filledPixels;
    private int height;
    public int[][] pixels;
    private final Queue<Range> ranges = new LinkedList();
    private int targetColor;
    private int width;

    public static /* synthetic */ void getClickedPixel$annotations() {
    }

    public static /* synthetic */ void getColorToBeReplaced$annotations() {
    }

    public static /* synthetic */ void getColorToleranceThresholdSquared$annotations() {
    }

    public static /* synthetic */ void getPixels$annotations() {
    }

    public static /* synthetic */ void getRanges$annotations() {
    }

    public static /* synthetic */ void getTargetColor$annotations() {
    }

    private final int square(int x) {
        return x * x;
    }

    public final int[][] getPixels() {
        int[][] iArr = this.pixels;
        if (iArr != null) {
            return iArr;
        }
        Intrinsics.throwUninitializedPropertyAccessException("pixels");
        return null;
    }

    public final void setPixels(int[][] iArr) {
        Intrinsics.checkNotNullParameter(iArr, "<set-?>");
        this.pixels = iArr;
    }

    public final Point getClickedPixel() {
        Point point = this.clickedPixel;
        if (point != null) {
            return point;
        }
        Intrinsics.throwUninitializedPropertyAccessException("clickedPixel");
        return null;
    }

    public final void setClickedPixel(Point point) {
        Intrinsics.checkNotNullParameter(point, "<set-?>");
        this.clickedPixel = point;
    }

    public final Queue<Range> getRanges() {
        return this.ranges;
    }

    public final int getTargetColor() {
        return this.targetColor;
    }

    public final void setTargetColor(int i) {
        this.targetColor = i;
    }

    public final int getColorToBeReplaced() {
        return this.colorToBeReplaced;
    }

    public final void setColorToBeReplaced(int i) {
        this.colorToBeReplaced = i;
    }

    public final int getColorToleranceThresholdSquared() {
        return this.colorToleranceThresholdSquared;
    }

    public final void setColorToleranceThresholdSquared(int i) {
        this.colorToleranceThresholdSquared = i;
    }

    @Override // org.catrobat.paintroid.tools.helper.FillAlgorithm
    public void setParameters(Bitmap bitmap, Point clickedPixel, int targetColor, int replacementColor, float colorToleranceThreshold) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(clickedPixel, "clickedPixel");
        this.bitmap = bitmap;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        int height = bitmap.getHeight();
        int[][] iArr = new int[height][];
        for (int i = 0; i < height; i++) {
            iArr[i] = new int[bitmap.getWidth()];
        }
        setPixels(iArr);
        int i2 = this.height;
        if (i2 > 0) {
            int i3 = 0;
            while (true) {
                int i4 = i3 + 1;
                Bitmap bitmap2 = this.bitmap;
                if (bitmap2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("bitmap");
                    bitmap2 = null;
                }
                int[] iArr2 = getPixels()[i3];
                int i5 = this.width;
                bitmap2.getPixels(iArr2, 0, i5, 0, i3, i5, 1);
                if (i4 >= i2) {
                    break;
                } else {
                    i3 = i4;
                }
            }
        }
        int height2 = bitmap.getHeight();
        boolean[][] zArr = new boolean[height2][];
        for (int i6 = 0; i6 < height2; i6++) {
            zArr[i6] = new boolean[bitmap.getWidth()];
        }
        this.filledPixels = zArr;
        setClickedPixel(clickedPixel);
        this.targetColor = targetColor;
        this.colorToBeReplaced = replacementColor;
        this.colorToleranceThresholdSquared = square((int) colorToleranceThreshold);
        this.considerTolerance = colorToleranceThreshold > 0.0f;
    }

    @Override // org.catrobat.paintroid.tools.helper.FillAlgorithm
    public void performFilling() {
        Range rangePoll;
        Range rangeGenerateRangeAndReplaceColor = generateRangeAndReplaceColor(getClickedPixel().y, getClickedPixel().x, true);
        this.ranges.add(rangeGenerateRangeAndReplaceColor);
        this.ranges.add(new Range(rangeGenerateRangeAndReplaceColor.getLine(), rangeGenerateRangeAndReplaceColor.getStart(), rangeGenerateRangeAndReplaceColor.getEnd(), false));
        while (!this.ranges.isEmpty() && (rangePoll = this.ranges.poll()) != null) {
            boolean direction = rangePoll.getDirection();
            int line = rangePoll.getLine() + (direction ? -1 : 1);
            if (line >= 0 && line < this.height) {
                checkRangeAndGenerateNewRanges(rangePoll, line, direction);
            }
        }
    }

    private final boolean shouldCellBeFilled(int row, int col) {
        boolean[][] zArr = this.filledPixels;
        if (zArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("filledPixels");
            zArr = null;
        }
        return !zArr[row][col] && (getPixels()[row][col] == this.colorToBeReplaced || (this.considerTolerance && isPixelWithinColorTolerance(getPixels()[row][col], this.colorToBeReplaced)));
    }

    private final boolean validateAndAssign(int row, int col) {
        if (!shouldCellBeFilled(row, col)) {
            return false;
        }
        getPixels()[row][col] = this.targetColor;
        boolean[][] zArr = this.filledPixels;
        if (zArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("filledPixels");
            zArr = null;
        }
        zArr[row][col] = true;
        return true;
    }

    private final int getStartIndex(int row, int col) {
        Integer next;
        Iterator<Integer> it = RangesKt.downTo(col, 0).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (validateAndAssign(row, next.intValue()));
        Integer num = next;
        return (num == null ? -1 : num.intValue()) + 1;
    }

    private final int getEndIndex(int row, int col) {
        Integer next;
        Iterator<Integer> it = RangesKt.until(col, this.width).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (validateAndAssign(row, next.intValue()));
        Integer num = next;
        return (num == null ? this.width : num.intValue()) - 1;
    }

    private final Range generateRangeAndReplaceColor(int row, int col, boolean direction) {
        Range range = new Range(0, 0, 0, false, 15, null);
        getPixels()[row][col] = this.targetColor;
        boolean[][] zArr = this.filledPixels;
        Bitmap bitmap = null;
        if (zArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("filledPixels");
            zArr = null;
        }
        zArr[row][col] = true;
        int startIndex = getStartIndex(row, col - 1);
        int endIndex = getEndIndex(row, col + 1);
        range.setLine(row);
        range.setEnd(endIndex);
        range.setStart(startIndex);
        range.setDirection(direction);
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bitmap");
        } else {
            bitmap = bitmap2;
        }
        bitmap.setPixels(getPixels()[row], startIndex, this.width, startIndex, row, (endIndex - startIndex) + 1, 1);
        return range;
    }

    private final void checkRangeAndGenerateNewRanges(Range range, int row, boolean directionUp) {
        int start = range.getStart();
        while (start <= range.getEnd()) {
            if (shouldCellBeFilled(row, start)) {
                Range rangeGenerateRangeAndReplaceColor = generateRangeAndReplaceColor(row, start, directionUp);
                this.ranges.add(rangeGenerateRangeAndReplaceColor);
                if (rangeGenerateRangeAndReplaceColor.getStart() <= range.getStart() - 2) {
                    this.ranges.add(new Range(row, rangeGenerateRangeAndReplaceColor.getStart(), range.getStart() - 2, !directionUp));
                }
                if (rangeGenerateRangeAndReplaceColor.getEnd() >= range.getEnd() + 2) {
                    this.ranges.add(new Range(row, range.getEnd() + 2, rangeGenerateRangeAndReplaceColor.getEnd(), !directionUp));
                }
                if (rangeGenerateRangeAndReplaceColor.getEnd() >= range.getEnd() - 1) {
                    return;
                } else {
                    start = rangeGenerateRangeAndReplaceColor.getEnd() + 1;
                }
            }
            start++;
        }
    }

    private final boolean isPixelWithinColorTolerance(int pixel, int referenceColor) {
        return ((square(Color.red(pixel) - Color.red(referenceColor)) + square(Color.green(pixel) - Color.green(referenceColor))) + square(Color.blue(pixel) - Color.blue(referenceColor))) + square(Color.alpha(pixel) - Color.alpha(referenceColor)) <= this.colorToleranceThresholdSquared;
    }

    /* JADX INFO: compiled from: JavaFillAlgorithm.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0007HÆ\u0003J1\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u00072\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010¨\u0006\u001f"}, d2 = {"Lorg/catrobat/paintroid/tools/helper/JavaFillAlgorithm$Range;", "", Constant.LINE, "", "start", "end", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "", "(IIIZ)V", "getDirection", "()Z", "setDirection", "(Z)V", "getEnd", "()I", "setEnd", "(I)V", "getLine", "setLine", "getStart", "setStart", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "toString", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final /* data */ class Range {
        private boolean direction;
        private int end;
        private int line;
        private int start;

        public Range() {
            this(0, 0, 0, false, 15, null);
        }

        public static /* synthetic */ Range copy$default(Range range, int i, int i2, int i3, boolean z, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                i = range.line;
            }
            if ((i4 & 2) != 0) {
                i2 = range.start;
            }
            if ((i4 & 4) != 0) {
                i3 = range.end;
            }
            if ((i4 & 8) != 0) {
                z = range.direction;
            }
            return range.copy(i, i2, i3, z);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final int getLine() {
            return this.line;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getStart() {
            return this.start;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final int getEnd() {
            return this.end;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final boolean getDirection() {
            return this.direction;
        }

        public final Range copy(int line, int start, int end, boolean direction) {
            return new Range(line, start, end, direction);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Range)) {
                return false;
            }
            Range range = (Range) other;
            return this.line == range.line && this.start == range.start && this.end == range.end && this.direction == range.direction;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v7, types: [int] */
        /* JADX WARN: Type inference failed for: r1v5, types: [int] */
        /* JADX WARN: Type inference failed for: r1v6 */
        /* JADX WARN: Type inference failed for: r1v7 */
        public int hashCode() {
            int iHashCode = ((((Integer.hashCode(this.line) * 31) + Integer.hashCode(this.start)) * 31) + Integer.hashCode(this.end)) * 31;
            boolean z = this.direction;
            ?? r1 = z;
            if (z) {
                r1 = 1;
            }
            return iHashCode + r1;
        }

        public String toString() {
            return "Range(line=" + this.line + ", start=" + this.start + ", end=" + this.end + ", direction=" + this.direction + ')';
        }

        public Range(int i, int i2, int i3, boolean z) {
            this.line = i;
            this.start = i2;
            this.end = i3;
            this.direction = z;
        }

        public /* synthetic */ Range(int i, int i2, int i3, boolean z, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this((i4 & 1) != 0 ? 0 : i, (i4 & 2) != 0 ? 0 : i2, (i4 & 4) != 0 ? 0 : i3, (i4 & 8) != 0 ? false : z);
        }

        public final int getLine() {
            return this.line;
        }

        public final void setLine(int i) {
            this.line = i;
        }

        public final int getStart() {
            return this.start;
        }

        public final void setStart(int i) {
            this.start = i;
        }

        public final int getEnd() {
            return this.end;
        }

        public final void setEnd(int i) {
            this.end = i;
        }

        public final boolean getDirection() {
            return this.direction;
        }

        public final void setDirection(boolean z) {
            this.direction = z;
        }
    }
}
