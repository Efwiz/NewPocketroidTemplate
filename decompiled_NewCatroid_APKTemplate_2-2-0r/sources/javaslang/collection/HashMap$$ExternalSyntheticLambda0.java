package javaslang.collection;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes11.dex */
public final /* synthetic */ class HashMap$$ExternalSyntheticLambda0 implements Maps.OfEntries {
    public final /* synthetic */ HashMap f$0;

    public /* synthetic */ HashMap$$ExternalSyntheticLambda0(HashMap hashMap) {
        this.f$0 = hashMap;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return this.f$0.createFromEntries((Iterable) obj);
    }
}
