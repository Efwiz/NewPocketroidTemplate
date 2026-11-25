package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: dumpKotlinLike.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001aE\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u00022\u001d\u0010\u0004\u001a\u0019\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\b\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0082\b¢\u0006\u0002\u0010\u000b\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\f2\b\b\u0002\u0010\t\u001a\u00020\n\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\r\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u000e¨\u0006\u000f"}, d2 = {"dumpKotlinLike", "", ExifInterface.GPS_DIRECTION_TRUE, "target", "print", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/util/KotlinLikeDumper;", "", "Lkotlin/ExtensionFunctionType;", "options", "Lorg/jetbrains/kotlin/ir/util/KotlinLikeDumpOptions;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;Lorg/jetbrains/kotlin/ir/util/KotlinLikeDumpOptions;)Ljava/lang/String;", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DumpKotlinLikeKt {
    public static final String dumpKotlinLike(IrElement irElement, KotlinLikeDumpOptions options) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        StringBuilder sb = new StringBuilder();
        new KotlinLikeDumper(new Printer(sb, 1, "  "), options).printElement(irElement);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static final String dumpKotlinLike(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        KotlinLikeDumpOptions kotlinLikeDumpOptions = new KotlinLikeDumpOptions(null, false, false, false, false, null, null, null, false, false, false, 2047, null);
        StringBuilder sb = new StringBuilder();
        new KotlinLikeDumper(new Printer(sb, 1, "  "), kotlinLikeDumpOptions).printType(irType);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static final String dumpKotlinLike(IrTypeArgument irTypeArgument) {
        Intrinsics.checkNotNullParameter(irTypeArgument, "<this>");
        KotlinLikeDumpOptions kotlinLikeDumpOptions = new KotlinLikeDumpOptions(null, false, false, false, false, null, null, null, false, false, false, 2047, null);
        StringBuilder sb = new StringBuilder();
        new KotlinLikeDumper(new Printer(sb, 1, "  "), kotlinLikeDumpOptions).printTypeArgument(irTypeArgument);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
