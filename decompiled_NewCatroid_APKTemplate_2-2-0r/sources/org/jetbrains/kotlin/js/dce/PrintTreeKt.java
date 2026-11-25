package org.jetbrains.kotlin.js.dce;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.backend.ast.JsLocation;
import org.jetbrains.kotlin.js.backend.ast.JsNode;

/* JADX INFO: compiled from: printTree.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a:\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u00060\u0003R\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t\u001a8\u0010\u0000\u001a\u00020\u00012\n\u0010\u000b\u001a\u00060\u0003R\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002¨\u0006\u0010"}, d2 = {"printTree", "", "root", "Lorg/jetbrains/kotlin/js/dce/Context$Node;", "Lorg/jetbrains/kotlin/js/dce/Context;", "consumer", "Lkotlin/Function1;", "", "printNestedMembers", "", "showLocations", "node", ConfigConstants.CONFIG_KEY_DEPTH, "", "settings", "Lorg/jetbrains/kotlin/js/dce/Settings;", "js.dce"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PrintTreeKt {
    public static /* synthetic */ void printTree$default(Context.Node node, Function1 function1, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        printTree(node, (Function1<? super String, Unit>) function1, z, z2);
    }

    public static final void printTree(Context.Node root, Function1<? super String, Unit> consumer, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(consumer, "consumer");
        printTree(root, consumer, 0, new Settings(z, z2));
    }

    private static final void printTree(Context.Node node, Function1<? super String, Unit> function1, int i, Settings settings) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbAppend = sb.append(StringsKt.repeat("  ", i));
        String memberName = node.getMemberName();
        if (memberName == null) {
            memberName = node.toString();
        }
        sbAppend.append(memberName);
        if (node.getReachable()) {
            sb.append(" (reachable");
            if (settings.getShowLocations()) {
                Set<JsNode> usedByAstNodes = node.getUsedByAstNodes();
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = usedByAstNodes.iterator();
                while (it.hasNext()) {
                    JsLocation jsLocationExtractLocation = UtilKt.extractLocation((JsNode) it.next());
                    if (jsLocationExtractLocation != null) {
                        arrayList.add(jsLocationExtractLocation);
                    }
                }
                ArrayList arrayList2 = arrayList;
                if (!arrayList2.isEmpty()) {
                    sb.append(" from ").append(CollectionsKt.joinToString$default(arrayList2, null, null, null, 0, null, new Function1<JsLocation, CharSequence>() { // from class: org.jetbrains.kotlin.js.dce.PrintTreeKt.printTree.1
                        @Override // kotlin.jvm.functions.Function1
                        public final CharSequence invoke(JsLocation it2) {
                            Intrinsics.checkNotNullParameter(it2, "it");
                            return UtilKt.asString(it2);
                        }
                    }, 31, null));
                }
            }
            sb.append(Constant.AFTER_QUTO);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        function1.invoke(string);
        Iterator it2 = CollectionsKt.sorted(node.getMemberNames()).iterator();
        while (it2.hasNext()) {
            Context.Node nodeMember = node.member((String) it2.next());
            if (nodeMember.getDeclarationReachable() && (!node.getReachable() || !nodeMember.getReachable() || settings.getPrintNestedMembers())) {
                printTree(nodeMember, function1, i + 1, settings);
            }
        }
    }
}
