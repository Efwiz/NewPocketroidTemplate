package org.jetbrains.kotlin.ir.interpreter;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.DurationKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.platform.TargetPlatform;

/* JADX INFO: compiled from: IrInterpreterConfiguration.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u001a\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BM\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\b¢\u0006\u0002\u0010\fJ\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001a\u001a\u00020\bHÆ\u0003J\t\u0010\u001b\u001a\u00020\bHÆ\u0003J\t\u0010\u001c\u001a\u00020\bHÆ\u0003J\t\u0010\u001d\u001a\u00020\bHÆ\u0003JQ\u0010\u001e\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\bHÆ\u0001J\u0013\u0010\u001f\u001a\u00020\b2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010!\u001a\u00020\u0005HÖ\u0001J\t\u0010\"\u001a\u00020#HÖ\u0001R\u0011\u0010\n\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u000b\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000e¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterConfiguration;", "", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "maxStack", "", "maxCommands", "createNonCompileTimeObjects", "", "printOnlyExceptionMessage", "collapseStackTraceFromJDK", "inlineConstVal", "(Lorg/jetbrains/kotlin/platform/TargetPlatform;IIZZZZ)V", "getCollapseStackTraceFromJDK", "()Z", "getCreateNonCompileTimeObjects", "getInlineConstVal", "getMaxCommands", "()I", "getMaxStack", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPrintOnlyExceptionMessage", "component1", "component2", "component3", "component4", "component5", "component6", "component7", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "toString", "", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class IrInterpreterConfiguration {
    private final boolean collapseStackTraceFromJDK;
    private final boolean createNonCompileTimeObjects;
    private final boolean inlineConstVal;
    private final int maxCommands;
    private final int maxStack;
    private final TargetPlatform platform;
    private final boolean printOnlyExceptionMessage;

    public IrInterpreterConfiguration() {
        this(null, 0, 0, false, false, false, false, 127, null);
    }

    public static /* synthetic */ IrInterpreterConfiguration copy$default(IrInterpreterConfiguration irInterpreterConfiguration, TargetPlatform targetPlatform, int i, int i2, boolean z, boolean z2, boolean z3, boolean z4, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            targetPlatform = irInterpreterConfiguration.platform;
        }
        if ((i3 & 2) != 0) {
            i = irInterpreterConfiguration.maxStack;
        }
        int i4 = i;
        if ((i3 & 4) != 0) {
            i2 = irInterpreterConfiguration.maxCommands;
        }
        int i5 = i2;
        if ((i3 & 8) != 0) {
            z = irInterpreterConfiguration.createNonCompileTimeObjects;
        }
        boolean z5 = z;
        if ((i3 & 16) != 0) {
            z2 = irInterpreterConfiguration.printOnlyExceptionMessage;
        }
        boolean z6 = z2;
        if ((i3 & 32) != 0) {
            z3 = irInterpreterConfiguration.collapseStackTraceFromJDK;
        }
        boolean z7 = z3;
        if ((i3 & 64) != 0) {
            z4 = irInterpreterConfiguration.inlineConstVal;
        }
        return irInterpreterConfiguration.copy(targetPlatform, i4, i5, z5, z6, z7, z4);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final TargetPlatform getPlatform() {
        return this.platform;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getMaxStack() {
        return this.maxStack;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getMaxCommands() {
        return this.maxCommands;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getCreateNonCompileTimeObjects() {
        return this.createNonCompileTimeObjects;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final boolean getPrintOnlyExceptionMessage() {
        return this.printOnlyExceptionMessage;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final boolean getCollapseStackTraceFromJDK() {
        return this.collapseStackTraceFromJDK;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final boolean getInlineConstVal() {
        return this.inlineConstVal;
    }

    public final IrInterpreterConfiguration copy(TargetPlatform platform, int maxStack, int maxCommands, boolean createNonCompileTimeObjects, boolean printOnlyExceptionMessage, boolean collapseStackTraceFromJDK, boolean inlineConstVal) {
        return new IrInterpreterConfiguration(platform, maxStack, maxCommands, createNonCompileTimeObjects, printOnlyExceptionMessage, collapseStackTraceFromJDK, inlineConstVal);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IrInterpreterConfiguration)) {
            return false;
        }
        IrInterpreterConfiguration irInterpreterConfiguration = (IrInterpreterConfiguration) other;
        return Intrinsics.areEqual(this.platform, irInterpreterConfiguration.platform) && this.maxStack == irInterpreterConfiguration.maxStack && this.maxCommands == irInterpreterConfiguration.maxCommands && this.createNonCompileTimeObjects == irInterpreterConfiguration.createNonCompileTimeObjects && this.printOnlyExceptionMessage == irInterpreterConfiguration.printOnlyExceptionMessage && this.collapseStackTraceFromJDK == irInterpreterConfiguration.collapseStackTraceFromJDK && this.inlineConstVal == irInterpreterConfiguration.inlineConstVal;
    }

    public int hashCode() {
        TargetPlatform targetPlatform = this.platform;
        return ((((((((((((targetPlatform == null ? 0 : targetPlatform.hashCode()) * 31) + Integer.hashCode(this.maxStack)) * 31) + Integer.hashCode(this.maxCommands)) * 31) + Boolean.hashCode(this.createNonCompileTimeObjects)) * 31) + Boolean.hashCode(this.printOnlyExceptionMessage)) * 31) + Boolean.hashCode(this.collapseStackTraceFromJDK)) * 31) + Boolean.hashCode(this.inlineConstVal);
    }

    public String toString() {
        return "IrInterpreterConfiguration(platform=" + this.platform + ", maxStack=" + this.maxStack + ", maxCommands=" + this.maxCommands + ", createNonCompileTimeObjects=" + this.createNonCompileTimeObjects + ", printOnlyExceptionMessage=" + this.printOnlyExceptionMessage + ", collapseStackTraceFromJDK=" + this.collapseStackTraceFromJDK + ", inlineConstVal=" + this.inlineConstVal + ')';
    }

    public IrInterpreterConfiguration(TargetPlatform targetPlatform, int i, int i2, boolean z, boolean z2, boolean z3, boolean z4) {
        this.platform = targetPlatform;
        this.maxStack = i;
        this.maxCommands = i2;
        this.createNonCompileTimeObjects = z;
        this.printOnlyExceptionMessage = z2;
        this.collapseStackTraceFromJDK = z3;
        this.inlineConstVal = z4;
    }

    public /* synthetic */ IrInterpreterConfiguration(TargetPlatform targetPlatform, int i, int i2, boolean z, boolean z2, boolean z3, boolean z4, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? null : targetPlatform, (i3 & 2) != 0 ? 10000 : i, (i3 & 4) != 0 ? DurationKt.NANOS_IN_MILLIS : i2, (i3 & 8) != 0 ? false : z, (i3 & 16) != 0 ? false : z2, (i3 & 32) != 0 ? true : z3, (i3 & 64) != 0 ? true : z4);
    }

    public final TargetPlatform getPlatform() {
        return this.platform;
    }

    public final int getMaxStack() {
        return this.maxStack;
    }

    public final int getMaxCommands() {
        return this.maxCommands;
    }

    public final boolean getCreateNonCompileTimeObjects() {
        return this.createNonCompileTimeObjects;
    }

    public final boolean getPrintOnlyExceptionMessage() {
        return this.printOnlyExceptionMessage;
    }

    public final boolean getCollapseStackTraceFromJDK() {
        return this.collapseStackTraceFromJDK;
    }

    public final boolean getInlineConstVal() {
        return this.inlineConstVal;
    }
}
