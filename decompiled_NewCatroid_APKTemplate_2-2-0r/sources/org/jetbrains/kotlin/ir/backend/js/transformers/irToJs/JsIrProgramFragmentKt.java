package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.js.backend.ast.JsImport;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;

/* JADX INFO: compiled from: JsIrProgramFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0007\u001a\u00020\b*\u00020\t\u001a\u0012\u0010\n\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\f\u001a\u00020\r\"\u001d\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\b\u0012\u0004\u0012\u00020\u00010\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001b\u0010\u0005\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006\u000e"}, d2 = {"exportFragment", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "", "getExportFragment", "(Ljava/util/List;)Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "mainFragment", "getMainFragment", "getRequireEsmName", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleTransitiveExport;", "renameImportedSymbolInternalName", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "newName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsIrProgramFragmentKt {
    public static final String getRequireEsmName(CrossModuleTransitiveExport crossModuleTransitiveExport) {
        Intrinsics.checkNotNullParameter(crossModuleTransitiveExport, "<this>");
        return crossModuleTransitiveExport.getExternalName() + ".mjs";
    }

    public static final JsStatement renameImportedSymbolInternalName(JsStatement jsStatement, JsName newName) {
        Intrinsics.checkNotNullParameter(jsStatement, "<this>");
        Intrinsics.checkNotNullParameter(newName, "newName");
        if (jsStatement instanceof JsImport) {
            JsImport jsImport = (JsImport) jsStatement;
            String module = jsImport.getModule();
            JsImport.Target target = jsImport.getTarget();
            Intrinsics.checkNotNull(target, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsImport.Target.Elements");
            return new JsImport(module, new JsImport.Element(((JsImport.Element) CollectionsKt.single((List) ((JsImport.Target.Elements) target).getElements())).getName(), newName.makeRef()));
        }
        if (!(jsStatement instanceof JsVars)) {
            throw new IllegalStateException(("Unexpected cross-module import statement " + Reflection.getOrCreateKotlinClass(jsStatement.getClass()).getQualifiedName()).toString());
        }
        List<JsVars.JsVar> vars = ((JsVars) jsStatement).getVars();
        Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
        return new JsVars(new JsVars.JsVar(newName, ((JsVars.JsVar) CollectionsKt.single((List) vars)).getInitExpression()));
    }

    public static final JsIrProgramFragment getMainFragment(List<JsIrProgramFragment> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return (JsIrProgramFragment) CollectionsKt.first((List) list);
    }

    public static final JsIrProgramFragment getExportFragment(List<JsIrProgramFragment> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return (JsIrProgramFragment) CollectionsKt.getOrNull(list, 1);
    }
}
