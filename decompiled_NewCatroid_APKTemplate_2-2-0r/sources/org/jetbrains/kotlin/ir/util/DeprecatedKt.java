package org.jetbrains.kotlin.ir.util;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrFile;

/* JADX INFO: compiled from: Deprecated.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006H\u0007\u001a.\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0007\u001a\u001c\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0010\u001a\u00020\u0006H\u0007\u001a \u0010\u0011\u001a\u00020\u0012*\u00020\u00132\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006H\u0007\u001a\u001e\u0010\u0014\u001a\u00020\u0012*\u00020\u00152\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0016"}, d2 = {"DumpIrTreeVisitor", "Lorg/jetbrains/kotlin/ir/util/DumpIrTreeVisitor;", "out", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "normalizeNames", "", "stableOrder", "DumpTreeFromSourceLineVisitor", "Lorg/jetbrains/kotlin/ir/util/DumpTreeFromSourceLineVisitor;", "fileEntry", "Lorg/jetbrains/kotlin/ir/IrFileEntry;", "lineNumber", "", "RenderIrElementVisitor", "Lorg/jetbrains/kotlin/ir/util/RenderIrElementVisitor;", "verboseErrorTypes", ArchiveStreamFactory.DUMP, "", "Lorg/jetbrains/kotlin/ir/IrElement;", "dumpTreesFromLineNumber", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeprecatedKt {
    public static /* synthetic */ String dump$default(IrElement irElement, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = false;
        }
        return dump(irElement, z, z2);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use the overload with DumpIrTreeOptions instead.", replaceWith = @ReplaceWith(expression = "dump(DumpIrTreeOptions(normalizeNames = normalizeNames, stableOrder = stableOrder))", imports = {"org.jetbrains.kotlin.ir.util.DumpIrTreeOptions"}))
    public static final String dump(IrElement irElement, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        return DumpIrTreeKt.dump(irElement, new DumpIrTreeOptions(z, z2, false, false, false, false, 60, null));
    }

    public static /* synthetic */ String dumpTreesFromLineNumber$default(IrFile irFile, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        return dumpTreesFromLineNumber(irFile, i, z);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use the overload with DumpIrTreeOptions instead.", replaceWith = @ReplaceWith(expression = "dumpTreesFromLineNumber(lineNumber, DumpIrTreeOptions(normalizeNames = normalizeNames))", imports = {"org.jetbrains.kotlin.ir.util.DumpIrTreeOptions"}))
    public static final String dumpTreesFromLineNumber(IrFile irFile, int i, boolean z) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        return DumpIrTreeKt.dumpTreesFromLineNumber(irFile, i, new DumpIrTreeOptions(z, false, false, false, false, false, 62, null));
    }

    public static /* synthetic */ DumpTreeFromSourceLineVisitor DumpTreeFromSourceLineVisitor$default(IrFileEntry irFileEntry, int i, Appendable appendable, boolean z, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z = false;
        }
        return DumpTreeFromSourceLineVisitor(irFileEntry, i, appendable, z);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use the overload with DumpIrTreeOptions instead.", replaceWith = @ReplaceWith(expression = "DumpTreeFromSourceLineVisitor(fileEntry, lineNumber, out, DumpIrTreeOptions(normalizeNames = normalizeNames))", imports = {"org.jetbrains.kotlin.ir.util.DumpIrTreeOptions"}))
    public static final DumpTreeFromSourceLineVisitor DumpTreeFromSourceLineVisitor(IrFileEntry fileEntry, int i, Appendable out, boolean z) {
        Intrinsics.checkNotNullParameter(fileEntry, "fileEntry");
        Intrinsics.checkNotNullParameter(out, "out");
        return new DumpTreeFromSourceLineVisitor(fileEntry, i, out, new DumpIrTreeOptions(z, false, false, false, false, false, 62, null));
    }

    public static /* synthetic */ DumpIrTreeVisitor DumpIrTreeVisitor$default(Appendable appendable, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        return DumpIrTreeVisitor(appendable, z, z2);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use the overload with DumpIrTreeOptions instead.", replaceWith = @ReplaceWith(expression = "DumpIrTreeVisitor(out, DumpIrTreeOptions(normalizeNames = normalizeNames, stableOrder = stableOrder))", imports = {"org.jetbrains.kotlin.ir.util.DumpIrTreeOptions"}))
    public static final DumpIrTreeVisitor DumpIrTreeVisitor(Appendable out, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(out, "out");
        return new DumpIrTreeVisitor(out, new DumpIrTreeOptions(z, z2, false, false, false, false, 60, null));
    }

    public static /* synthetic */ RenderIrElementVisitor RenderIrElementVisitor$default(boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        return RenderIrElementVisitor(z, z2);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use the overload with DumpIrTreeOptions instead.", replaceWith = @ReplaceWith(expression = "RenderIrElementVisitor(DumpIrTreeOptions(normalizeNames = normalizeNames, stableOrder = !verboseErrorTypes))", imports = {"org.jetbrains.kotlin.ir.util.DumpIrTreeOptions"}))
    public static final RenderIrElementVisitor RenderIrElementVisitor(boolean z, boolean z2) {
        return new RenderIrElementVisitor(new DumpIrTreeOptions(z, false, z2, false, false, false, 58, null));
    }
}
