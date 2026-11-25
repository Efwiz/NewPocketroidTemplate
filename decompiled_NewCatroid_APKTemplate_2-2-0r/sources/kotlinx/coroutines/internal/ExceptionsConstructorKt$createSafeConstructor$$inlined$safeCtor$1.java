package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: ExceptionsConstructor.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/internal/ExceptionsConstructorKt$createSafeConstructor$$inlined$safeCtor$1.class */
@Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0003\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003¨\u0006\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "e", "invoke", "kotlinx/coroutines/internal/ExceptionsConstructorKt$safeCtor$1"})
public final class ExceptionsConstructorKt$createSafeConstructor$$inlined$safeCtor$1 extends Lambda implements Function1<Throwable, Throwable> {
    final /* synthetic */ Constructor $constructor$inlined;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExceptionsConstructorKt$createSafeConstructor$$inlined$safeCtor$1(Constructor constructor) {
        super(1);
        this.$constructor$inlined = constructor;
    }

    @Override // kotlin.jvm.functions.Function1
    @Nullable
    public final Throwable invoke(@NotNull Throwable e) {
        Object objM2898constructorimpl;
        try {
            Result.Companion companion = Result.INSTANCE;
            Object objNewInstance = this.$constructor$inlined.newInstance(e.getMessage(), e);
            if (objNewInstance == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
            }
            objM2898constructorimpl = Result.m2898constructorimpl((Throwable) objNewInstance);
            Object obj = objM2898constructorimpl;
            return (Throwable) (Result.m2904isFailureimpl(obj) ? null : obj);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th));
        }
    }
}
