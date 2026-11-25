package org.jetbrains.kotlin.ir.backend.js;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.StageController;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: WholeWorldStageController.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0016J)\u0010\u0013\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u00142\u0006\u0010\u0015\u001a\u00020\u00042\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00140\u0017H\u0016¢\u0006\u0002\u0010\u0018J!\u0010\u0019\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u00142\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00140\u0017H\u0016¢\u0006\u0002\u0010\u001bR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/WholeWorldStageController;", "Lorg/jetbrains/kotlin/ir/declarations/StageController;", "()V", "currentDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getCurrentDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "setCurrentDeclaration", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)V", "currentStage", "", "getCurrentStage", "()I", "setCurrentStage", "(I)V", "index", "createSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "parentSignature", "restrictTo", ExifInterface.GPS_DIRECTION_TRUE, "declaration", "fn", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withInitialIr", "block", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WholeWorldStageController extends StageController {
    private IrDeclaration currentDeclaration;
    private int currentStage;
    private int index;

    public WholeWorldStageController() {
        super(0, 1, null);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.StageController
    public int getCurrentStage() {
        return this.currentStage;
    }

    public void setCurrentStage(int i) {
        this.currentStage = i;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.StageController
    public IrDeclaration getCurrentDeclaration() {
        return this.currentDeclaration;
    }

    public void setCurrentDeclaration(IrDeclaration irDeclaration) {
        this.currentDeclaration = irDeclaration;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.StageController
    public <T> T restrictTo(IrDeclaration declaration, Function0<? extends T> fn) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(fn, "fn");
        IrDeclaration currentDeclaration = getCurrentDeclaration();
        int i = this.index;
        setCurrentDeclaration(declaration);
        this.index = 0;
        try {
            return fn.invoke();
        } finally {
            setCurrentDeclaration(currentDeclaration);
            this.index = i;
        }
    }

    @Override // org.jetbrains.kotlin.ir.declarations.StageController
    public <T> T withInitialIr(Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        int currentStage = getCurrentStage();
        setCurrentStage(0);
        IrDeclaration currentDeclaration = getCurrentDeclaration();
        setCurrentDeclaration(null);
        try {
            return block.invoke();
        } finally {
            setCurrentStage(currentStage);
            setCurrentDeclaration(currentDeclaration);
        }
    }

    @Override // org.jetbrains.kotlin.ir.declarations.StageController
    public IdSignature createSignature(IdSignature parentSignature) {
        Intrinsics.checkNotNullParameter(parentSignature, "parentSignature");
        int currentStage = getCurrentStage();
        int i = this.index;
        this.index = i + 1;
        return new IdSignature.LoweredDeclarationSignature(parentSignature, currentStage, i);
    }
}
