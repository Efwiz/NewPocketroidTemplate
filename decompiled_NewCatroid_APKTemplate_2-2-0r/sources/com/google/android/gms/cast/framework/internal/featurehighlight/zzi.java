package com.google.android.gms.cast.framework.internal.featurehighlight;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.cast.framework.R;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzi {
    private final Rect zza = new Rect();
    private final int zzb;
    private final int zzc;
    private final int zzd;
    private final int zze;
    private final zzh zzf;

    zzi(zzh zzhVar) {
        this.zzf = zzhVar;
        Resources resources = zzhVar.getResources();
        this.zzb = resources.getDimensionPixelSize(R.dimen.cast_libraries_material_featurehighlight_inner_radius);
        this.zzc = resources.getDimensionPixelOffset(R.dimen.cast_libraries_material_featurehighlight_inner_margin);
        this.zzd = resources.getDimensionPixelSize(R.dimen.cast_libraries_material_featurehighlight_text_max_width);
        this.zze = resources.getDimensionPixelSize(R.dimen.cast_libraries_material_featurehighlight_text_horizontal_offset);
    }

    private final int zzb(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i5 = i4 - i;
        int i6 = i2 - i4;
        int i7 = i4 - (i3 / 2);
        int i8 = i5 <= i6 ? i7 + this.zze : i7 - this.zze;
        if (i8 - marginLayoutParams.leftMargin < i) {
            return i + marginLayoutParams.leftMargin;
        }
        return (i8 + i3) + marginLayoutParams.rightMargin > i2 ? (i2 - i3) - marginLayoutParams.rightMargin : i8;
    }

    private final void zzc(View view, int i, int i2) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(View.MeasureSpec.makeMeasureSpec(Math.min((i - marginLayoutParams.leftMargin) - marginLayoutParams.rightMargin, this.zzd), 1073741824), View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE));
    }

    final void zza(Rect rect, Rect rect2) {
        View viewZzb = this.zzf.zzb();
        if (rect.isEmpty() || rect2.isEmpty()) {
            viewZzb.layout(0, 0, 0, 0);
        } else {
            int iCenterY = rect.centerY();
            int iCenterX = rect.centerX();
            int iCenterY2 = rect2.centerY();
            int iHeight = rect.height();
            int i = this.zzb;
            int iMax = Math.max(i + i, iHeight) / 2;
            int i2 = iCenterY + iMax;
            int i3 = this.zzc;
            if (iCenterY < iCenterY2) {
                int i4 = i2 + i3;
                zzc(viewZzb, rect2.width(), rect2.bottom - i4);
                int iZzb = zzb(viewZzb, rect2.left, rect2.right, viewZzb.getMeasuredWidth(), iCenterX);
                viewZzb.layout(iZzb, i4, viewZzb.getMeasuredWidth() + iZzb, viewZzb.getMeasuredHeight() + i4);
            } else {
                int i5 = (iCenterY - iMax) - i3;
                zzc(viewZzb, rect2.width(), i5 - rect2.top);
                int iZzb2 = zzb(viewZzb, rect2.left, rect2.right, viewZzb.getMeasuredWidth(), iCenterX);
                viewZzb.layout(iZzb2, i5 - viewZzb.getMeasuredHeight(), viewZzb.getMeasuredWidth() + iZzb2, i5);
            }
        }
        this.zza.set(viewZzb.getLeft(), viewZzb.getTop(), viewZzb.getRight(), viewZzb.getBottom());
        zzh zzhVar = this.zzf;
        zzhVar.zzf().zzf(rect, this.zza);
        this.zzf.zzd().zzb(rect);
    }
}
