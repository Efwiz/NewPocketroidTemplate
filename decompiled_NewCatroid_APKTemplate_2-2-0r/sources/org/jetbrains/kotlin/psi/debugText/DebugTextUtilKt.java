package org.jetbrains.kotlin.psi.debugText;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtElementImplStub;
import org.jetbrains.kotlin.psi.KtPackageDirective;

/* JADX INFO: compiled from: DebugTextUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a.\u0010\u0000\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0007H\u0002\u001a\n\u0010\t\u001a\u00020\u0007*\u00020\n¨\u0006\u000b"}, d2 = {"appendInn", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "target", "", "prefix", "", "suffix", "getDebugText", "Lorg/jetbrains/kotlin/psi/KtElement;", "frontend.common-psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DebugTextUtilKt {
    public static final String getDebugText(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        if (!(ktElement instanceof KtElementImplStub) || ((KtElementImplStub) ktElement).getStub() == 0) {
            String text = ktElement.getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            return text;
        }
        if (ktElement instanceof KtPackageDirective) {
            FqName fqName = ((KtPackageDirective) ktElement).getFqName();
            Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
            if (fqName.isRoot()) {
                return "";
            }
            return "package " + fqName.asString();
        }
        return (String) ktElement.accept(DebugTextBuildingVisitor.INSTANCE, Unit.INSTANCE);
    }

    static /* synthetic */ void appendInn$default(StringBuilder sb, Object obj, String str, String str2, int i, Object obj2) {
        if ((i & 2) != 0) {
            str = "";
        }
        if ((i & 4) != 0) {
            str2 = "";
        }
        appendInn(sb, obj, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void appendInn(StringBuilder sb, Object obj, String str, String str2) {
        if (obj == null) {
            return;
        }
        sb.append(str);
        sb.append(obj instanceof KtElement ? getDebugText((KtElement) obj) : obj.toString());
        sb.append(str2);
    }
}
