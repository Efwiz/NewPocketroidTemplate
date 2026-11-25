package org.jetbrains.kotlin.codegen.state;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GenerationState.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00042\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0004ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0005À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/GenerationStateEventCallback;", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface GenerationStateEventCallback extends Function1<GenerationState, Unit> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: GenerationState.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/GenerationStateEventCallback$Companion;", "", "()V", "DO_NOTHING", "Lorg/jetbrains/kotlin/codegen/state/GenerationStateEventCallback;", "getDO_NOTHING", "()Lorg/jetbrains/kotlin/codegen/state/GenerationStateEventCallback;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final GenerationStateEventCallback DO_NOTHING = GenerationStateKt.GenerationStateEventCallback(new Function1<GenerationState, Unit>() { // from class: org.jetbrains.kotlin.codegen.state.GenerationStateEventCallback$Companion$DO_NOTHING$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(GenerationState it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(GenerationState generationState) {
                invoke2(generationState);
                return Unit.INSTANCE;
            }
        });

        private Companion() {
        }

        public final GenerationStateEventCallback getDO_NOTHING() {
            return DO_NOTHING;
        }
    }
}
