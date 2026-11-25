package org.jetbrains.kotlin.codegen.state;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.archive.PathTree;
import com.reandroid.dex.sections.Marker;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ClassBuilderFactory;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;

/* JADX INFO: compiled from: GenerationState.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u001a=\u0010\t\u001a\u00020\n*\u00020\n2*\u0010\u000b\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u00060\f\"\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0006H\u0002¢\u0006\u0002\u0010\r\u001a;\u0010\t\u001a\u00020\n\"\u0004\b\u0000\u0010\u000e*\u00020\n2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u00102\u0018\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u00020\n0\u0012H\u0082\b\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"doNotGenerateInLightClassMode", "", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "GenerationStateEventCallback", "Lorg/jetbrains/kotlin/codegen/state/GenerationStateEventCallback;", "block", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "", "wrapWith", "Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", "wrappers", "", "(Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;[Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", ExifInterface.GPS_DIRECTION_TRUE, PathTree.NAME_elements, "", "wrapper", "Lkotlin/Function2;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class GenerationStateKt {
    private static final Set<JvmDeclarationOriginKind> doNotGenerateInLightClassMode = SetsKt.setOf((Object[]) new JvmDeclarationOriginKind[]{JvmDeclarationOriginKind.CLASS_MEMBER_DELEGATION_TO_DEFAULT_IMPL, JvmDeclarationOriginKind.BRIDGE, JvmDeclarationOriginKind.COLLECTION_STUB, JvmDeclarationOriginKind.AUGMENTED_BUILTIN_API});

    public static final GenerationStateEventCallback GenerationStateEventCallback(final Function1<? super GenerationState, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new GenerationStateEventCallback() { // from class: org.jetbrains.kotlin.codegen.state.GenerationStateKt.GenerationStateEventCallback.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(GenerationState generationState) {
                invoke2(generationState);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public void invoke2(GenerationState s) {
                Intrinsics.checkNotNullParameter(s, "s");
                block.invoke(s);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassBuilderFactory wrapWith(ClassBuilderFactory classBuilderFactory, Function1<? super ClassBuilderFactory, ? extends ClassBuilderFactory>... function1Arr) {
        for (Function1<? super ClassBuilderFactory, ? extends ClassBuilderFactory> function1 : function1Arr) {
            classBuilderFactory = function1.invoke(classBuilderFactory);
        }
        return classBuilderFactory;
    }
}
