package androidx.lifecycle;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/lifecycle-livedata-core-2.6.1.aar:classes.jar:androidx/lifecycle/MutableLiveData.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public class MutableLiveData<T> extends LiveData<T> {
    public MutableLiveData(T t) {
        super(t);
    }

    public MutableLiveData() {
    }

    @Override // androidx.lifecycle.LiveData
    public void postValue(T t) {
        super.postValue(t);
    }

    @Override // androidx.lifecycle.LiveData
    public void setValue(T t) {
        super.setValue(t);
    }
}
