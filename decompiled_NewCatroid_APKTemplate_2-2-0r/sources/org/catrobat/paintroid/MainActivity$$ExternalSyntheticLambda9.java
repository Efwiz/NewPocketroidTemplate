package org.catrobat.paintroid;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes14.dex */
public final /* synthetic */ class MainActivity$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ MainActivity f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ String[] f$2;
    public final /* synthetic */ int[] f$3;

    public /* synthetic */ MainActivity$$ExternalSyntheticLambda9(MainActivity mainActivity, int i, String[] strArr, int[] iArr) {
        this.f$0 = mainActivity;
        this.f$1 = i;
        this.f$2 = strArr;
        this.f$3 = iArr;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MainActivity.m5878onRequestPermissionsResult$lambda17(this.f$0, this.f$1, this.f$2, this.f$3);
    }
}
