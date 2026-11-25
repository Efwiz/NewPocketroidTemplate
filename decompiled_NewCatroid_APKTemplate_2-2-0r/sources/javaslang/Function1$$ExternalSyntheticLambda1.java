package javaslang;

import java.io.Serializable;
import java.util.function.Function;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes11.dex */
public final /* synthetic */ class Function1$$ExternalSyntheticLambda1 implements Function1, Serializable {
    public final /* synthetic */ Function1 f$0;
    public final /* synthetic */ Function f$1;

    public /* synthetic */ Function1$$ExternalSyntheticLambda1(Function1 function1, Function function) {
        this.f$0 = function1;
        this.f$1 = function;
    }

    @Override // javaslang.Function1, java.util.function.Function
    public final Object apply(Object obj) {
        return this.f$0.apply(this.f$1.apply(obj));
    }
}
