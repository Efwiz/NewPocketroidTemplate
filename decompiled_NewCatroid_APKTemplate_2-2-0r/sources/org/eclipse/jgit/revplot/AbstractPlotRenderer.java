package org.eclipse.jgit.revplot;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revplot.PlotLane;
import org.eclipse.jgit.revwalk.RevFlag;

/* JADX INFO: loaded from: classes13.dex */
public abstract class AbstractPlotRenderer<TLane extends PlotLane, TColor> {
    private static final int LANE_WIDTH = 14;
    private static final int LEFT_PAD = 2;
    private static final int LINE_WIDTH = 2;

    protected abstract void drawBoundaryDot(int i, int i2, int i3, int i4);

    protected abstract void drawCommitDot(int i, int i2, int i3, int i4);

    protected abstract int drawLabel(int i, int i2, Ref ref);

    protected abstract void drawLine(TColor tcolor, int i, int i2, int i3, int i4, int i5);

    protected abstract void drawText(String str, int i, int i2);

    protected abstract TColor laneColor(TLane tlane);

    protected void paintCommit(PlotCommit<TLane> plotCommit, int i) {
        int i2;
        int i3;
        int iComputeDotSize = computeDotSize(i);
        PlotLane lane = plotCommit.getLane();
        int iLaneC = laneC(lane);
        TColor tcolorLaneColor = laneColor(lane);
        PlotLane[] plotLaneArr = plotCommit.passingLanes;
        int length = plotLaneArr.length;
        int iMax = iLaneC;
        int i4 = 0;
        while (i4 < length) {
            TColor tcolor = tcolorLaneColor;
            PlotLane plotLane = plotLaneArr[i4];
            int iLaneC2 = laneC(plotLane);
            drawLine(laneColor(plotLane), iLaneC2, 0, iLaneC2, i, 2);
            iMax = Math.max(iMax, iLaneC2);
            i4++;
            tcolorLaneColor = tcolor;
        }
        int i5 = (iLaneC - (iComputeDotSize / 2)) - 1;
        int i6 = (i - iComputeDotSize) / 2;
        if (plotCommit.getParentCount() > 0) {
            int i7 = iMax;
            int i8 = 14;
            drawLine(tcolorLaneColor, iLaneC, i, iLaneC, (i + iComputeDotSize) / 2, 2);
            PlotLane[] plotLaneArr2 = plotCommit.mergingLanes;
            int length2 = plotLaneArr2.length;
            int iMax2 = i7;
            int i9 = 0;
            while (i9 < length2) {
                PlotLane plotLane2 = plotLaneArr2[i9];
                TColor tcolorLaneColor2 = laneColor(plotLane2);
                int iLaneC3 = laneC(plotLane2);
                if (Math.abs(iLaneC - iLaneC3) > i8) {
                    int i10 = iLaneC < iLaneC3 ? iLaneC3 - 7 : iLaneC3 + 7;
                    int i11 = i / 2;
                    i3 = iLaneC3;
                    drawLine(tcolorLaneColor2, iLaneC, i11, i10, i11, 2);
                    drawLine(tcolorLaneColor2, i10, i11, i3, i, 2);
                } else {
                    i3 = iLaneC3;
                    drawLine(tcolorLaneColor2, iLaneC, i / 2, i3, i, 2);
                }
                iMax2 = Math.max(iMax2, i3);
                i9++;
                length2 = length2;
                plotLaneArr2 = plotLaneArr2;
                i8 = 14;
            }
            iMax = iMax2;
        }
        if (plotCommit.getChildCount() > 0) {
            PlotLane[] plotLaneArr3 = plotCommit.forkingOffLanes;
            int i12 = 0;
            for (int length3 = plotLaneArr3.length; i12 < length3; length3 = length3) {
                PlotLane plotLane3 = plotLaneArr3[i12];
                TColor tcolorLaneColor3 = laneColor(plotLane3);
                int iLaneC4 = laneC(plotLane3);
                if (Math.abs(iLaneC - iLaneC4) > 14) {
                    int i13 = iLaneC < iLaneC4 ? iLaneC4 - 7 : iLaneC4 + 7;
                    int i14 = i / 2;
                    i2 = iLaneC4;
                    drawLine(tcolorLaneColor3, iLaneC, i14, i13, i14, 2);
                    drawLine(tcolorLaneColor3, i13, i14, i2, 0, 2);
                } else {
                    i2 = iLaneC4;
                    drawLine(tcolorLaneColor3, iLaneC, i / 2, i2, 0, 2);
                }
                iMax = Math.max(iMax, i2);
                i12++;
                tcolorLaneColor = tcolorLaneColor;
            }
            if (plotCommit.getChildCount() - plotCommit.forkingOffLanes.length > 0) {
                drawLine(tcolorLaneColor, iLaneC, 0, iLaneC, i6, 2);
            }
            iMax = iMax;
        }
        if (plotCommit.has(RevFlag.UNINTERESTING)) {
            drawBoundaryDot(i5, i6, iComputeDotSize, iComputeDotSize);
        } else {
            drawCommitDot(i5, i6, iComputeDotSize, iComputeDotSize);
        }
        int iMax3 = Math.max(iMax + 7, i5 + iComputeDotSize) + 8;
        int length4 = plotCommit.refs.length;
        for (int i15 = 0; i15 < length4; i15++) {
            iMax3 += drawLabel(iMax3 + iComputeDotSize, i / 2, plotCommit.refs[i15]);
        }
        drawText(plotCommit.getShortMessage(), iMax3 + iComputeDotSize, i);
    }

    private static int computeDotSize(int i) {
        int iMin = (int) (Math.min(i, 14) * 0.5f);
        return iMin + (iMin & 1);
    }

    private static int laneX(PlotLane plotLane) {
        return ((plotLane != null ? plotLane.getPosition() : 0) * 14) + 2;
    }

    private static int laneC(PlotLane plotLane) {
        return laneX(plotLane) + 7;
    }
}
