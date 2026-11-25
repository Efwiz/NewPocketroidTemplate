package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: dumpKotlinLike.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b#\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001Bs\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0005¢\u0006\u0002\u0010\u0012J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\u0005HÆ\u0003J\t\u0010%\u001a\u00020\u0005HÆ\u0003J\t\u0010&\u001a\u00020\u0005HÆ\u0003J\t\u0010'\u001a\u00020\u0005HÆ\u0003J\t\u0010(\u001a\u00020\u0005HÆ\u0003J\t\u0010)\u001a\u00020\u0005HÆ\u0003J\t\u0010*\u001a\u00020\nHÆ\u0003J\t\u0010+\u001a\u00020\fHÆ\u0003J\t\u0010,\u001a\u00020\u000eHÆ\u0003J\t\u0010-\u001a\u00020\u0005HÆ\u0003Jw\u0010.\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u00052\b\b\u0002\u0010\u0011\u001a\u00020\u0005HÆ\u0001J\u0013\u0010/\u001a\u00020\u00052\b\u00100\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00101\u001a\u000202HÖ\u0001J\t\u00103\u001a\u000204HÖ\u0001R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001aR\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001aR\u0011\u0010\u0010\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u001aR\u0011\u0010\u0011\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001aR\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001a¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/KotlinLikeDumpOptions;", "", "customDumpStrategy", "Lorg/jetbrains/kotlin/ir/util/CustomKotlinLikeDumpStrategy;", "printRegionsPerFile", "", "printFileName", "printFilePath", "useNamedArguments", "labelPrintingStrategy", "Lorg/jetbrains/kotlin/ir/util/LabelPrintingStrategy;", "printFakeOverridesStrategy", "Lorg/jetbrains/kotlin/ir/util/FakeOverridesStrategy;", "bodyPrintingStrategy", "Lorg/jetbrains/kotlin/ir/util/BodyPrintingStrategy;", "printElseAsTrue", "printUnitReturnType", "stableOrder", "(Lorg/jetbrains/kotlin/ir/util/CustomKotlinLikeDumpStrategy;ZZZZLorg/jetbrains/kotlin/ir/util/LabelPrintingStrategy;Lorg/jetbrains/kotlin/ir/util/FakeOverridesStrategy;Lorg/jetbrains/kotlin/ir/util/BodyPrintingStrategy;ZZZ)V", "getBodyPrintingStrategy", "()Lorg/jetbrains/kotlin/ir/util/BodyPrintingStrategy;", "getCustomDumpStrategy", "()Lorg/jetbrains/kotlin/ir/util/CustomKotlinLikeDumpStrategy;", "getLabelPrintingStrategy", "()Lorg/jetbrains/kotlin/ir/util/LabelPrintingStrategy;", "getPrintElseAsTrue", "()Z", "getPrintFakeOverridesStrategy", "()Lorg/jetbrains/kotlin/ir/util/FakeOverridesStrategy;", "getPrintFileName", "getPrintFilePath", "getPrintRegionsPerFile", "getPrintUnitReturnType", "getStableOrder", "getUseNamedArguments", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class KotlinLikeDumpOptions {
    private final BodyPrintingStrategy bodyPrintingStrategy;
    private final CustomKotlinLikeDumpStrategy customDumpStrategy;
    private final LabelPrintingStrategy labelPrintingStrategy;
    private final boolean printElseAsTrue;
    private final FakeOverridesStrategy printFakeOverridesStrategy;
    private final boolean printFileName;
    private final boolean printFilePath;
    private final boolean printRegionsPerFile;
    private final boolean printUnitReturnType;
    private final boolean stableOrder;
    private final boolean useNamedArguments;

    public KotlinLikeDumpOptions() {
        this(null, false, false, false, false, null, null, null, false, false, false, 2047, null);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final CustomKotlinLikeDumpStrategy getCustomDumpStrategy() {
        return this.customDumpStrategy;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final boolean getPrintUnitReturnType() {
        return this.printUnitReturnType;
    }

    /* JADX INFO: renamed from: component11, reason: from getter */
    public final boolean getStableOrder() {
        return this.stableOrder;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getPrintRegionsPerFile() {
        return this.printRegionsPerFile;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getPrintFileName() {
        return this.printFileName;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getPrintFilePath() {
        return this.printFilePath;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final boolean getUseNamedArguments() {
        return this.useNamedArguments;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final LabelPrintingStrategy getLabelPrintingStrategy() {
        return this.labelPrintingStrategy;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final FakeOverridesStrategy getPrintFakeOverridesStrategy() {
        return this.printFakeOverridesStrategy;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final BodyPrintingStrategy getBodyPrintingStrategy() {
        return this.bodyPrintingStrategy;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final boolean getPrintElseAsTrue() {
        return this.printElseAsTrue;
    }

    public final KotlinLikeDumpOptions copy(CustomKotlinLikeDumpStrategy customDumpStrategy, boolean printRegionsPerFile, boolean printFileName, boolean printFilePath, boolean useNamedArguments, LabelPrintingStrategy labelPrintingStrategy, FakeOverridesStrategy printFakeOverridesStrategy, BodyPrintingStrategy bodyPrintingStrategy, boolean printElseAsTrue, boolean printUnitReturnType, boolean stableOrder) {
        Intrinsics.checkNotNullParameter(customDumpStrategy, "customDumpStrategy");
        Intrinsics.checkNotNullParameter(labelPrintingStrategy, "labelPrintingStrategy");
        Intrinsics.checkNotNullParameter(printFakeOverridesStrategy, "printFakeOverridesStrategy");
        Intrinsics.checkNotNullParameter(bodyPrintingStrategy, "bodyPrintingStrategy");
        return new KotlinLikeDumpOptions(customDumpStrategy, printRegionsPerFile, printFileName, printFilePath, useNamedArguments, labelPrintingStrategy, printFakeOverridesStrategy, bodyPrintingStrategy, printElseAsTrue, printUnitReturnType, stableOrder);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KotlinLikeDumpOptions)) {
            return false;
        }
        KotlinLikeDumpOptions kotlinLikeDumpOptions = (KotlinLikeDumpOptions) other;
        return Intrinsics.areEqual(this.customDumpStrategy, kotlinLikeDumpOptions.customDumpStrategy) && this.printRegionsPerFile == kotlinLikeDumpOptions.printRegionsPerFile && this.printFileName == kotlinLikeDumpOptions.printFileName && this.printFilePath == kotlinLikeDumpOptions.printFilePath && this.useNamedArguments == kotlinLikeDumpOptions.useNamedArguments && this.labelPrintingStrategy == kotlinLikeDumpOptions.labelPrintingStrategy && this.printFakeOverridesStrategy == kotlinLikeDumpOptions.printFakeOverridesStrategy && this.bodyPrintingStrategy == kotlinLikeDumpOptions.bodyPrintingStrategy && this.printElseAsTrue == kotlinLikeDumpOptions.printElseAsTrue && this.printUnitReturnType == kotlinLikeDumpOptions.printUnitReturnType && this.stableOrder == kotlinLikeDumpOptions.stableOrder;
    }

    public int hashCode() {
        return (((((((((((((((((((this.customDumpStrategy.hashCode() * 31) + Boolean.hashCode(this.printRegionsPerFile)) * 31) + Boolean.hashCode(this.printFileName)) * 31) + Boolean.hashCode(this.printFilePath)) * 31) + Boolean.hashCode(this.useNamedArguments)) * 31) + this.labelPrintingStrategy.hashCode()) * 31) + this.printFakeOverridesStrategy.hashCode()) * 31) + this.bodyPrintingStrategy.hashCode()) * 31) + Boolean.hashCode(this.printElseAsTrue)) * 31) + Boolean.hashCode(this.printUnitReturnType)) * 31) + Boolean.hashCode(this.stableOrder);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("KotlinLikeDumpOptions(customDumpStrategy=");
        sb.append(this.customDumpStrategy).append(", printRegionsPerFile=").append(this.printRegionsPerFile).append(", printFileName=").append(this.printFileName).append(", printFilePath=").append(this.printFilePath).append(", useNamedArguments=").append(this.useNamedArguments).append(", labelPrintingStrategy=").append(this.labelPrintingStrategy).append(", printFakeOverridesStrategy=").append(this.printFakeOverridesStrategy).append(", bodyPrintingStrategy=").append(this.bodyPrintingStrategy).append(", printElseAsTrue=").append(this.printElseAsTrue).append(", printUnitReturnType=").append(this.printUnitReturnType).append(", stableOrder=").append(this.stableOrder).append(')');
        return sb.toString();
    }

    public KotlinLikeDumpOptions(CustomKotlinLikeDumpStrategy customDumpStrategy, boolean z, boolean z2, boolean z3, boolean z4, LabelPrintingStrategy labelPrintingStrategy, FakeOverridesStrategy printFakeOverridesStrategy, BodyPrintingStrategy bodyPrintingStrategy, boolean z5, boolean z6, boolean z7) {
        Intrinsics.checkNotNullParameter(customDumpStrategy, "customDumpStrategy");
        Intrinsics.checkNotNullParameter(labelPrintingStrategy, "labelPrintingStrategy");
        Intrinsics.checkNotNullParameter(printFakeOverridesStrategy, "printFakeOverridesStrategy");
        Intrinsics.checkNotNullParameter(bodyPrintingStrategy, "bodyPrintingStrategy");
        this.customDumpStrategy = customDumpStrategy;
        this.printRegionsPerFile = z;
        this.printFileName = z2;
        this.printFilePath = z3;
        this.useNamedArguments = z4;
        this.labelPrintingStrategy = labelPrintingStrategy;
        this.printFakeOverridesStrategy = printFakeOverridesStrategy;
        this.bodyPrintingStrategy = bodyPrintingStrategy;
        this.printElseAsTrue = z5;
        this.printUnitReturnType = z6;
        this.stableOrder = z7;
    }

    public /* synthetic */ KotlinLikeDumpOptions(CustomKotlinLikeDumpStrategy customKotlinLikeDumpStrategy, boolean z, boolean z2, boolean z3, boolean z4, LabelPrintingStrategy labelPrintingStrategy, FakeOverridesStrategy fakeOverridesStrategy, BodyPrintingStrategy bodyPrintingStrategy, boolean z5, boolean z6, boolean z7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CustomKotlinLikeDumpStrategy.Default.INSTANCE : customKotlinLikeDumpStrategy, (i & 2) != 0 ? false : z, (i & 4) != 0 ? true : z2, (i & 8) == 0 ? z3 : true, (i & 16) != 0 ? false : z4, (i & 32) != 0 ? LabelPrintingStrategy.NEVER : labelPrintingStrategy, (i & 64) != 0 ? FakeOverridesStrategy.ALL : fakeOverridesStrategy, (i & 128) != 0 ? BodyPrintingStrategy.PRINT_BODIES : bodyPrintingStrategy, (i & 256) != 0 ? false : z5, (i & 512) != 0 ? false : z6, (i & 1024) == 0 ? z7 : false);
    }

    public final CustomKotlinLikeDumpStrategy getCustomDumpStrategy() {
        return this.customDumpStrategy;
    }

    public final boolean getPrintRegionsPerFile() {
        return this.printRegionsPerFile;
    }

    public final boolean getPrintFileName() {
        return this.printFileName;
    }

    public final boolean getPrintFilePath() {
        return this.printFilePath;
    }

    public final boolean getUseNamedArguments() {
        return this.useNamedArguments;
    }

    public final LabelPrintingStrategy getLabelPrintingStrategy() {
        return this.labelPrintingStrategy;
    }

    public final FakeOverridesStrategy getPrintFakeOverridesStrategy() {
        return this.printFakeOverridesStrategy;
    }

    public final BodyPrintingStrategy getBodyPrintingStrategy() {
        return this.bodyPrintingStrategy;
    }

    public final boolean getPrintElseAsTrue() {
        return this.printElseAsTrue;
    }

    public final boolean getPrintUnitReturnType() {
        return this.printUnitReturnType;
    }

    public final boolean getStableOrder() {
        return this.stableOrder;
    }
}
