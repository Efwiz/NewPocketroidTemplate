package org.jetbrains.kotlin.js.inline.clean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameBinding;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;

/* JADX INFO: compiled from: removeUnusedImports.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006H\u0002\u001a\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"collectUsedImports", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "root", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "to", "", "removeUnusedImports", "", "fragment", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RemoveUnusedImportsKt {
    public static final void removeUnusedImports(JsProgramFragment fragment, JsBlock code) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        Intrinsics.checkNotNullParameter(code, "code");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        collectUsedImports(code, linkedHashSet);
        CollectionsKt.retainAll((List) fragment.getNameBindings(), (Function1) new Function1<JsNameBinding, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.RemoveUnusedImportsKt.removeUnusedImports.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(JsNameBinding it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!MetadataProperties.getImported(it.getName()) || linkedHashSet.contains(it.getName()));
            }
        });
        List<JsNameBinding> nameBindings = fragment.getNameBindings();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(nameBindings, 10));
        Iterator<T> it = nameBindings.iterator();
        while (it.hasNext()) {
            arrayList.add(((JsNameBinding) it.next()).getKey());
        }
        final Set set = CollectionsKt.toSet(arrayList);
        CollectionsKt.retainAll(fragment.getImports().entrySet(), new Function1<Map.Entry<String, JsExpression>, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.RemoveUnusedImportsKt.removeUnusedImports.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Map.Entry<String, JsExpression> entry) {
                Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                return Boolean.valueOf(set.contains(entry.getKey()));
            }
        });
    }

    private static final Set<JsName> collectUsedImports(JsNode jsNode, Set<JsName> set) {
        final UsedImportsCollector usedImportsCollector = new UsedImportsCollector(set);
        UsedImportsCollector usedImportsCollector2 = usedImportsCollector;
        jsNode.accept(usedImportsCollector2);
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        new NodeRemover(JsVars.class, new Function1<JsVars, Boolean>() { // from class: org.jetbrains.kotlin.js.inline.clean.RemoveUnusedImportsKt.collectUsedImports.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(JsVars statement) {
                Intrinsics.checkNotNullParameter(statement, "statement");
                boolean z = false;
                if (statement.getVars().size() == 1) {
                    JsName name2 = statement.getVars().get(0).getName();
                    Intrinsics.checkNotNull(name2);
                    if (MetadataProperties.getImported(name2) && !usedImportsCollector.getUsedImports().contains(name2)) {
                        z = true;
                    }
                    Set<JsName> set2 = linkedHashSet;
                    if (z) {
                        set2.add(name2);
                    }
                }
                return Boolean.valueOf(z);
            }
        }).accept(jsNode);
        for (JsVars.JsVar jsVar : usedImportsCollector.getPseudoImports()) {
            if (!linkedHashSet.contains(jsVar.getName())) {
                jsVar.getInitExpression().accept(usedImportsCollector2);
            }
        }
        return usedImportsCollector.getUsedImports();
    }
}
