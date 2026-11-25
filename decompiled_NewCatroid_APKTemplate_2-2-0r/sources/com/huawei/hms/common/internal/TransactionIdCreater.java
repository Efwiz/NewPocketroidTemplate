package com.huawei.hms.common.internal;

import com.huawei.hms.utils.StringUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import kotlin.time.DurationKt;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: loaded from: classes10.dex */
public class TransactionIdCreater {
    public static String getId(String str, String str2) {
        return StringUtil.addByteForNum(str, 9, ExternalAnnotationProvider.NULLABLE) + StringUtil.addByteForNum(str2, 6, ExternalAnnotationProvider.NULLABLE) + new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.ENGLISH).format(new Date()) + String.format(Locale.ENGLISH, "%06d", Integer.valueOf(new Random().nextInt(DurationKt.NANOS_IN_MILLIS)));
    }
}
