package org.jetbrains.kotlin.daemon.common;

import com.android.dx.rop.code.RegisterSpec;
import kotlin.Metadata;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: FileSystemUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0002¨\u0006\u0003"}, d2 = {"orDefault", "", RegisterSpec.PREFIX, "daemon-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FileSystemUtilsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String orDefault(String str, String str2) {
        return (str == null || StringsKt.isBlank(str)) ? str2 : str;
    }
}
