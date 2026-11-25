package javaslang;

import java.io.Serializable;
import java.util.function.Function;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes11.dex */
public final /* synthetic */ class Function2$$ExternalSyntheticLambda0 implements Function2, Serializable {
    public final /* synthetic */ Function2 f$0;
    public final /* synthetic */ Function f$1;

    public /* synthetic */ Function2$$ExternalSyntheticLambda0(Function2 function2, Function function) {
        this.f$0 = function2;
        this.f$1 = function;
    }

    @Override // javaslang.Function2, java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        return this.f$1.apply(this.f$0.apply(obj, obj2));
    }
}
