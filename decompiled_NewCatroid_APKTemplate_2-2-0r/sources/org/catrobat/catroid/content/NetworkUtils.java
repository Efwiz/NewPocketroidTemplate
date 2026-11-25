package org.catrobat.catroid.content;

import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import org.catrobat.catroid.CatroidApplication;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/NetworkUtils.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NetworkUtils {
    public static boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) CatroidApplication.getAppContext().getSystemService("connectivity");
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        return networkCapabilities != null && networkCapabilities.hasCapability(12);
    }
}
