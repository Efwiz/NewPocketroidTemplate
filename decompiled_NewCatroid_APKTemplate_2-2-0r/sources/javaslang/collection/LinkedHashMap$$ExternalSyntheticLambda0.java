package javaslang.collection;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes11.dex */
public final /* synthetic */ class LinkedHashMap$$ExternalSyntheticLambda0 implements Maps.OfEntries {
    public final /* synthetic */ LinkedHashMap f$0;

    public /* synthetic */ LinkedHashMap$$ExternalSyntheticLambda0(LinkedHashMap linkedHashMap) {
        this.f$0 = linkedHashMap;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return this.f$0.createFromEntries((Iterable) obj);
    }
}
