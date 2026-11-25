package org.jetbrains.kotlin.fir.scopes;

import androidx.exifinterface.media.ExifInterface;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: compiled from: FirCompositeScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", ExifInterface.GPS_DIRECTION_TRUE, "it", "invoke", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class FirCompositeScope$processComposite$1<T> extends Lambda implements Function1<T, Unit> {
    final /* synthetic */ Function1<T, Unit> $processor;
    final /* synthetic */ Set<T> $unique;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FirCompositeScope$processComposite$1(Set<T> set, Function1<? super T, Unit> function1) {
        super(1);
        this.$unique = set;
        this.$processor = function1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
        invoke2(obj);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(T t) {
        if (this.$unique.add(t)) {
            this.$processor.invoke(t);
        }
    }
}
