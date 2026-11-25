package org.catrobat.paintroid.common;

import android.os.Environment;
import java.io.File;
import kotlin.Metadata;

/* JADX INFO: compiled from: Constants.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/catrobat/paintroid/common/Constants;", "", "()V", "DOWNLOADS_DIRECTORY", "Ljava/io/File;", "PICTURES_DIRECTORY", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class Constants {
    public static final Constants INSTANCE = new Constants();
    public static final File PICTURES_DIRECTORY = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_PICTURES);
    public static final File DOWNLOADS_DIRECTORY = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOWNLOADS);

    private Constants() {
    }
}
