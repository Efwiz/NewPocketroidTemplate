package org.catrobat.paintroid.colorpicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import com.google.android.material.button.MaterialButton;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: ColorPickerDialog.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
@DebugMetadata(c = "org.catrobat.paintroid.colorpicker.ColorPickerDialog$onCreateDialog$1$1", f = "ColorPickerDialog.kt", i = {}, l = {161}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class ColorPickerDialog$onCreateDialog$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ View $it;
    int label;
    final /* synthetic */ ColorPickerDialog this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ColorPickerDialog$onCreateDialog$1$1(ColorPickerDialog colorPickerDialog, View view, Continuation<? super ColorPickerDialog$onCreateDialog$1$1> continuation) {
        super(2, continuation);
        this.this$0 = colorPickerDialog;
        this.$it = view;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ColorPickerDialog$onCreateDialog$1$1(this.this$0, this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ColorPickerDialog$onCreateDialog$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ColorPickerPreviewActivity.Companion companion = ColorPickerPreviewActivity.INSTANCE;
            Bitmap bitmap = this.this$0.currentBitmap;
            if (bitmap == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentBitmap");
                bitmap = null;
            }
            companion.setPickableImage(bitmap);
            this.label = 1;
            if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass1(this.this$0, this.$it, null), this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: org.catrobat.paintroid.colorpicker.ColorPickerDialog$onCreateDialog$1$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: ColorPickerDialog.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "org.catrobat.paintroid.colorpicker.ColorPickerDialog$onCreateDialog$1$1$1", f = "ColorPickerDialog.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ View $it;
        int label;
        final /* synthetic */ ColorPickerDialog this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(ColorPickerDialog colorPickerDialog, View view, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = colorPickerDialog;
            this.$it = view;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.this$0, this.$it, continuation);
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
            MaterialButton materialButton = this.this$0.pipetteBtn;
            Bitmap bitmap = null;
            if (materialButton == null) {
                Intrinsics.throwUninitializedPropertyAccessException("pipetteBtn");
                materialButton = null;
            }
            materialButton.setEnabled(true);
            Intent intent = new Intent(this.$it.getContext(), (Class<?>) ColorPickerPreviewActivity.class);
            ColorPickerDialog colorPickerDialog = this.this$0;
            intent.putExtra(ColorPickerDialogKt.COLOR_EXTRA, colorPickerDialog.colorToApply);
            Bitmap bitmap2 = colorPickerDialog.currentBitmap;
            if (bitmap2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentBitmap");
                bitmap2 = null;
            }
            intent.putExtra(ColorPickerDialogKt.BITMAP_HEIGHT_EXTRA, bitmap2.getHeight());
            Bitmap bitmap3 = colorPickerDialog.currentBitmap;
            if (bitmap3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentBitmap");
            } else {
                bitmap = bitmap3;
            }
            intent.putExtra(ColorPickerDialogKt.BITMAP_WIDTH_EXTRA, bitmap.getWidth());
            try {
                this.this$0.startActivityForResult(intent, 1);
            } catch (IllegalStateException e) {
                Log.e(ColorPickerDialog.TAG, "onCreateDialog: " + e.getMessage());
            }
            return Unit.INSTANCE;
        }
    }
}
