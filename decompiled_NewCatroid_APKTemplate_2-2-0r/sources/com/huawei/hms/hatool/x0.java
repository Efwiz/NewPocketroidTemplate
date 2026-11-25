package com.huawei.hms.hatool;

import android.content.Context;
import android.os.UserManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: loaded from: classes10.dex */
public class x0 {
    public static x0 c = new x0();
    public boolean a = false;
    public Context b = b.i();

    public static x0 b() {
        return c;
    }

    public boolean a() {
        if (!this.a) {
            Context context = this.b;
            if (context == null) {
                return false;
            }
            UserManager userManager = (UserManager) context.getSystemService(ConfigConstants.CONFIG_USER_SECTION);
            if (userManager != null) {
                this.a = userManager.isUserUnlocked();
            } else {
                this.a = false;
            }
        }
        return this.a;
    }
}
