package androidx.camera.core.internal;

import android.util.Log;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.UseCase;
import androidx.camera.core.VideoCapture;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class UseCaseOccupancy {
    private static final String TAG = "UseCaseOccupancy";

    private UseCaseOccupancy() {
    }

    public static boolean checkUseCaseLimitNotExceeded(List<UseCase> list) {
        int i = 0;
        int i2 = 0;
        for (UseCase useCase : list) {
            if (useCase instanceof ImageCapture) {
                i++;
            } else if (useCase instanceof VideoCapture) {
                i2++;
            }
        }
        if (i > 1) {
            Log.e(TAG, "Exceeded max simultaneously bound image capture use cases.");
            return false;
        }
        if (i2 <= 1) {
            return true;
        }
        Log.e(TAG, "Exceeded max simultaneously bound video capture use cases.");
        return false;
    }
}
