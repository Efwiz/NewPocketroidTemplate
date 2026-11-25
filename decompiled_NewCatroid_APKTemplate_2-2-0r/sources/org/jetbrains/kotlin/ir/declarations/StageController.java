package org.jetbrains.kotlin.ir.declarations;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: PersistentApi.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\fH\u0016J)\u0010\u000e\u001a\u0002H\u000f\"\u0004\b\u0000\u0010\u000f2\u0006\u0010\u0010\u001a\u00020\u00062\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u0012H\u0016¢\u0006\u0002\u0010\u0013J!\u0010\u0014\u001a\u0002H\u000f\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u0012H\u0016¢\u0006\u0002\u0010\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/StageController;", "", "currentStage", "", "(I)V", "currentDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getCurrentDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getCurrentStage", "()I", "createSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "parentSignature", "restrictTo", ExifInterface.GPS_DIRECTION_TRUE, "declaration", "fn", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withInitialIr", "block", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class StageController {
    private final int currentStage;

    public StageController() {
        this(0, 1, null);
    }

    public IdSignature createSignature(IdSignature parentSignature) {
        Intrinsics.checkNotNullParameter(parentSignature, "parentSignature");
        return null;
    }

    public IrDeclaration getCurrentDeclaration() {
        return null;
    }

    public StageController(int i) {
        this.currentStage = i;
    }

    public /* synthetic */ StageController(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i);
    }

    public int getCurrentStage() {
        return this.currentStage;
    }

    public <T> T restrictTo(IrDeclaration declaration, Function0<? extends T> fn) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(fn, "fn");
        return fn.invoke();
    }

    public <T> T withInitialIr(Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return block.invoke();
    }
}
