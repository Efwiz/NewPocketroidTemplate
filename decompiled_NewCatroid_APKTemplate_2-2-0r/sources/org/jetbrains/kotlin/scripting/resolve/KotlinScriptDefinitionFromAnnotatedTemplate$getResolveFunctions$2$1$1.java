package org.jetbrains.kotlin.scripting.resolve;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KFunction;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: KotlinScriptDefinitionFromAnnotatedTemplate.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "it", "Lkotlin/reflect/KFunction;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class KotlinScriptDefinitionFromAnnotatedTemplate$getResolveFunctions$2$1$1 extends Lambda implements Function1<KFunction<?>, CharSequence> {
    public static final KotlinScriptDefinitionFromAnnotatedTemplate$getResolveFunctions$2$1$1 INSTANCE = new KotlinScriptDefinitionFromAnnotatedTemplate$getResolveFunctions$2$1$1();

    KotlinScriptDefinitionFromAnnotatedTemplate$getResolveFunctions$2$1$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final CharSequence invoke(KFunction<?> it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getName();
    }
}
