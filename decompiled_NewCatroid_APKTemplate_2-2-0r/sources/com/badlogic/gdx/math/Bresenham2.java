package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/math/Bresenham2.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Bresenham2 {
    private final Array<GridPoint2> points = new Array<>();
    private final Pool<GridPoint2> pool = new Pool<GridPoint2>() { // from class: com.badlogic.gdx.math.Bresenham2.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.badlogic.gdx.utils.Pool
        public GridPoint2 newObject() {
            return new GridPoint2();
        }
    };

    public Array<GridPoint2> line(GridPoint2 gridPoint2, GridPoint2 gridPoint3) {
        return line(gridPoint2.x, gridPoint2.y, gridPoint3.x, gridPoint3.y);
    }

    public Array<GridPoint2> line(int i, int i2, int i3, int i4) {
        this.pool.freeAll(this.points);
        this.points.clear();
        return line(i, i2, i3, i4, this.pool, this.points);
    }

    public Array<GridPoint2> line(int i, int i2, int i3, int i4, Pool<GridPoint2> pool, Array<GridPoint2> array) {
        int i5;
        int i6 = i3 - i;
        int i7 = i4 - i2;
        int i8 = i6 < 0 ? -1 : i6 > 0 ? 1 : 0;
        int i9 = i8;
        int i10 = i7 < 0 ? -1 : i7 > 0 ? 1 : 0;
        int iAbs = Math.abs(i6);
        int iAbs2 = Math.abs(i7);
        if (iAbs < iAbs2) {
            iAbs = Math.abs(i7);
            iAbs2 = Math.abs(i6);
            i5 = i7 >= 0 ? i7 > 0 ? 1 : 0 : -1;
            i9 = 0;
        } else {
            i5 = 0;
        }
        int i11 = iAbs2 << 1;
        int i12 = iAbs << 1;
        int i13 = 0;
        for (int i14 = 0; i14 <= iAbs; i14++) {
            GridPoint2 gridPoint2Obtain = pool.obtain();
            gridPoint2Obtain.set(i, i2);
            array.add(gridPoint2Obtain);
            i13 += i11;
            if (i13 > iAbs) {
                i13 -= i12;
                i += i8;
                i2 += i10;
            } else {
                i += i9;
                i2 += i5;
            }
        }
        return array;
    }
}
