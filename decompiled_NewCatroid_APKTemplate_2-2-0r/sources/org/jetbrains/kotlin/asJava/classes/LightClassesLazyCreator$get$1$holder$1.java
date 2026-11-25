package org.jetbrains.kotlin.asJava.classes;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiCachedValueImpl;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: compiled from: LightClassesLazyCreator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u0001H\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/com/intellij/psi/impl/PsiCachedValueImpl;", ExifInterface.GPS_DIRECTION_TRUE, JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class LightClassesLazyCreator$get$1$holder$1<T> extends Lambda implements Function0<PsiCachedValueImpl<T>> {
    final /* synthetic */ List<Object> $dependencies;
    final /* synthetic */ Function0<T> $initializer;
    final /* synthetic */ LightClassesLazyCreator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    LightClassesLazyCreator$get$1$holder$1(LightClassesLazyCreator lightClassesLazyCreator, Function0<? extends T> function0, List<? extends Object> list) {
        super(0);
        this.this$0 = lightClassesLazyCreator;
        this.$initializer = function0;
        this.$dependencies = list;
    }

    @Override // kotlin.jvm.functions.Function0
    public final PsiCachedValueImpl<T> invoke() {
        PsiManager psiManager = PsiManager.getInstance(this.this$0.project);
        final Function0<T> function0 = this.$initializer;
        final List<Object> list = this.$dependencies;
        return new PsiCachedValueImpl<>(psiManager, new CachedValueProvider() { // from class: org.jetbrains.kotlin.asJava.classes.LightClassesLazyCreator$get$1$holder$1$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return LightClassesLazyCreator$get$1$holder$1.invoke$lambda$0(function0, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CachedValueProvider.Result invoke$lambda$0(Function0 initializer, List dependencies) {
        Intrinsics.checkNotNullParameter(initializer, "$initializer");
        Intrinsics.checkNotNullParameter(dependencies, "$dependencies");
        return CachedValueProvider.Result.create(initializer.invoke(), dependencies);
    }
}
