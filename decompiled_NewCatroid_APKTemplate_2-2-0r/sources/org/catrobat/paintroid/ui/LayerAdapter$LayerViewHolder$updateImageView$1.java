package org.catrobat.paintroid.ui;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import org.catrobat.paintroid.contract.LayerContracts;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: LayerAdapter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lkotlinx/coroutines/Job;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
@DebugMetadata(c = "org.catrobat.paintroid.ui.LayerAdapter$LayerViewHolder$updateImageView$1", f = "LayerAdapter.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class LayerAdapter$LayerViewHolder$updateImageView$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Job>, Object> {
    final /* synthetic */ LayerContracts.Layer $layer;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ LayerAdapter.LayerViewHolder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    LayerAdapter$LayerViewHolder$updateImageView$1(LayerAdapter.LayerViewHolder layerViewHolder, LayerContracts.Layer layer, Continuation<? super LayerAdapter$LayerViewHolder$updateImageView$1> continuation) {
        super(2, continuation);
        this.this$0 = layerViewHolder;
        this.$layer = layer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        LayerAdapter$LayerViewHolder$updateImageView$1 layerAdapter$LayerViewHolder$updateImageView$1 = new LayerAdapter$LayerViewHolder$updateImageView$1(this.this$0, this.$layer, continuation);
        layerAdapter$LayerViewHolder$updateImageView$1.L$0 = obj;
        return layerAdapter$LayerViewHolder$updateImageView$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Job> continuation) {
        return ((LayerAdapter$LayerViewHolder$updateImageView$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.ui.LayerAdapter$LayerViewHolder$updateImageView$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: LayerAdapter.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.ui.LayerAdapter$LayerViewHolder$updateImageView$1$1", f = "LayerAdapter.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ LayerContracts.Layer $layer;
        int label;
        final /* synthetic */ LayerAdapter.LayerViewHolder this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(LayerAdapter.LayerViewHolder layerViewHolder, LayerContracts.Layer layer, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = layerViewHolder;
            this.$layer = layer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.this$0, this.$layer, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
            this.this$0.imageView.setImageBitmap(this.this$0.resizeBitmap(this.$layer.getBitmap()));
            this.this$0.imageView.setAlpha(this.$layer.getOpacityPercentage() / 100);
            return Unit.INSTANCE;
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
        ResultKt.throwOnFailure(obj);
        return BuildersKt__Builders_commonKt.launch$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(this.this$0, this.$layer, null), 3, null);
    }
}
