package org.catrobat.catroid.ui;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/UiUtils.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class UiUtils {
    public static int getDrawableForItem(int itemId) {
        switch (itemId) {
            case R.string.backpack_add /* 2131886243 */:
                return R.drawable.personal_bag_24px;
            case R.string.brick_context_dialog_copy_brick /* 2131886411 */:
            case R.string.brick_context_dialog_copy_script /* 2131886412 */:
                return R.drawable.ic_content_copy;
            case R.string.brick_context_dialog_delete_brick /* 2131886414 */:
            case R.string.brick_context_dialog_delete_script /* 2131886416 */:
            case R.string.delete /* 2131887795 */:
                return R.drawable.ic_delete;
            case R.string.brick_context_dialog_formula_edit_brick /* 2131886418 */:
            case R.string.rename /* 2131889848 */:
                return R.drawable.ic_edit;
            case R.string.brick_context_dialog_help /* 2131886419 */:
                return R.drawable.ic_help_small;
            case R.string.from_library /* 2131888853 */:
                return R.drawable.ic_apps_small;
            case R.string.from_local /* 2131888854 */:
                return R.drawable.ic_library_add_small;
            case R.string.menu_rate_us /* 2131889130 */:
                return R.drawable.ic_star_rate;
            default:
                return R.drawable.ic_placeholder;
        }
    }

    private UiUtils() {
        throw new AssertionError("No.");
    }

    public static AppCompatActivity getActivityFromContextWrapper(Context context) {
        while ((context instanceof ContextWrapper) && !(context instanceof AppCompatActivity)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        }
        return null;
    }

    public static BitmapDrawable createCheckerboardDrawable(Context context, int tileSize) {
        int i = tileSize * 2;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        paint.setColor(-1);
        float f = tileSize;
        canvas.drawRect(0.0f, 0.0f, f, f, paint);
        float f2 = i;
        canvas.drawRect(f, f, f2, f2, paint);
        paint.setColor(-2302756);
        canvas.drawRect(0.0f, f, f, f2, paint);
        canvas.drawRect(f, 0.0f, f2, f, paint);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmapCreateBitmap);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        return bitmapDrawable;
    }

    public static AppCompatActivity getActivityFromView(View view) {
        return getActivityFromContextWrapper(view.getContext());
    }

    public static ArrayList<Integer> getDrawablesForItems(List<Integer> items) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        Iterator<Integer> it = items.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(getDrawableForItem(it.next().intValue())));
        }
        return arrayList;
    }

    public static PopupMenu createSettingsPopUpMenu(View view, Context context, int menuLayout, int[] hiddenOptionItems) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(menuLayout);
        if (hiddenOptionItems != null) {
            for (int i : hiddenOptionItems) {
                popupMenu.getMenu().findItem(i).setVisible(false);
            }
        }
        if (Build.VERSION.SDK_INT >= 29) {
            popupMenu.setForceShowIcon(true);
        }
        return popupMenu;
    }

    public static ArrayAdapter getAlertDialogAdapterForMenuIcons(List<Integer> options, final List<String> names, final Context context, final Activity activity) {
        final ArrayList<Integer> drawablesForItems = getDrawablesForItems(options);
        return new ArrayAdapter(context, R.layout.alert_dialog_layout, R.id.title_option_item, options) { // from class: org.catrobat.catroid.ui.UiUtils.1
            TextView item;

            @Override // android.widget.ArrayAdapter, android.widget.Adapter
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.alert_dialog_layout, parent, false);
                    TextView textView = (TextView) convertView.findViewById(R.id.title_option_item);
                    this.item = textView;
                    convertView.setTag(textView);
                } else {
                    this.item = (TextView) convertView.getTag();
                }
                this.item.setText((CharSequence) names.get(position));
                if (activity.getWindow().getDecorView().getLayoutDirection() == 0) {
                    this.item.setCompoundDrawablesWithIntrinsicBounds(((Integer) drawablesForItems.get(position)).intValue(), 0, 0, 0);
                } else {
                    this.item.setCompoundDrawablesWithIntrinsicBounds(0, 0, ((Integer) drawablesForItems.get(position)).intValue(), 0);
                }
                return convertView;
            }
        };
    }
}
