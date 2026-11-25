package org.catrobat.catroid.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/MarketingActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MarketingActivity extends AppCompatActivity {
    private static final String TAG = "MarketingActivity";

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final XmlHeader xmlHeader = ProjectManager.getInstance().getCurrentProject().getXmlHeader();
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_standalone_advertising);
        ((TextView) findViewById(R.id.title)).setText(ProjectManager.getInstance().getCurrentProject().getName());
        ((ImageView) findViewById(R.id.pocket_code_image)).setImageBitmap(scaleDrawable2Bitmap(xmlHeader.islandscapeMode()));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.googlePlayStore);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.huaweiAppGallery);
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.MarketingActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                try {
                    MarketingActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=org.catrobat.catroid")));
                } catch (ActivityNotFoundException unused) {
                    ToastUtil.showError(MarketingActivity.this, R.string.main_menu_play_store_not_installed);
                }
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.MarketingActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                try {
                    MarketingActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Constants.HUAWEI_APP_GALLERY_LINK)));
                } catch (ActivityNotFoundException unused) {
                    ToastUtil.showError(MarketingActivity.this, R.string.error_cannot_open_huawei_app_gallery);
                }
            }
        });
        TextView textView = (TextView) findViewById(R.id.website_link);
        TextView textView2 = (TextView) findViewById(R.id.playStore_link);
        textView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.MarketingActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String remixParentsUrlString = xmlHeader.getRemixParentsUrlString();
                if (remixParentsUrlString == null || remixParentsUrlString.length() == 0) {
                    Log.w(MarketingActivity.TAG, "Header of program contains not even one valid detail url!");
                    return;
                }
                List<String> listExtractRemixUrlsFromString = Utils.extractRemixUrlsFromString(remixParentsUrlString);
                if (listExtractRemixUrlsFromString.size() == 0) {
                    Log.w(MarketingActivity.TAG, "Header of program contains not even one valid detail url!");
                    return;
                }
                String str = listExtractRemixUrlsFromString.get(0);
                if (!remixParentsUrlString.trim().startsWith("http")) {
                    str = "https://t.me/NewCatroidDevs" + remixParentsUrlString;
                }
                Log.d(MarketingActivity.TAG, "Program detail url: " + str);
                MarketingActivity.this.startWebViewActivity(str);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.MarketingActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                try {
                    MarketingActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://search?q=Catrobat")));
                } catch (ActivityNotFoundException unused) {
                    ToastUtil.showError(MarketingActivity.this, R.string.main_menu_play_store_not_installed);
                }
            }
        });
    }

    private Bitmap scaleDrawable2Bitmap(boolean landscapeMode) {
        int width;
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.pocket_code);
        if (landscapeMode) {
            width = ScreenValues.currentScreenResolution.getHeight();
        } else {
            width = ScreenValues.currentScreenResolution.getWidth();
        }
        float f = width;
        int height = (int) (((double) bitmapDecodeResource.getHeight()) * ((double) (f / bitmapDecodeResource.getWidth())));
        Log.d("GSOC", "width: " + width + "  height: " + height + "   scaleFactor: " + ((int) (f / bitmapDecodeResource.getWidth())));
        return Bitmap.createScaledBitmap(bitmapDecodeResource, width, height, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startWebViewActivity(String url) {
        Intent intent = new Intent(this, (Class<?>) WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
