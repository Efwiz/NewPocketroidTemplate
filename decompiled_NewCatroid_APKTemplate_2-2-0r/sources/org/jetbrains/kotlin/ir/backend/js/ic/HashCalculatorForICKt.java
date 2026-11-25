package org.jetbrains.kotlin.ir.backend.js.ic;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.Hash128Bits;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CrossModuleImport;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CrossModuleReferences;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.CrossModuleTransitiveExport;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.CodedOutputStream;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: HashCalculatorForIC.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0000\u001a\u0011\u0010\u0004\u001a\u00020\u0005*\u00020\u0006H\u0000¢\u0006\u0002\u0010\u0007\u001a\u0014\u0010\b\u001a\u00020\t*\u00020\u00012\u0006\u0010\n\u001a\u00020\u000bH\u0000¨\u0006\f"}, d2 = {"readHash128BitsFromProtoStream", "Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "input", "Lorg/jetbrains/kotlin/protobuf/CodedInputStream;", "crossModuleReferencesHashForIC", "Lorg/jetbrains/kotlin/ir/backend/js/ic/ICHash;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleReferences;", "(Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleReferences;)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "toProtoStream", "", "out", "Lorg/jetbrains/kotlin/protobuf/CodedOutputStream;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class HashCalculatorForICKt {
    public static final void toProtoStream(Hash128Bits hash128Bits, CodedOutputStream out) throws IOException {
        Intrinsics.checkNotNullParameter(hash128Bits, "<this>");
        Intrinsics.checkNotNullParameter(out, "out");
        out.writeFixed64NoTag(hash128Bits.m6139getLowBytessVKNKU());
        out.writeFixed64NoTag(hash128Bits.m6138getHighBytessVKNKU());
    }

    public static final Hash128Bits readHash128BitsFromProtoStream(CodedInputStream input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return new Hash128Bits(ULong.m3072constructorimpl(input.readFixed64()), ULong.m3072constructorimpl(input.readFixed64()), null);
    }

    public static final Hash128Bits crossModuleReferencesHashForIC(CrossModuleReferences crossModuleReferences) {
        Intrinsics.checkNotNullParameter(crossModuleReferences, "<this>");
        HashCalculatorForIC hashCalculatorForIC = new HashCalculatorForIC();
        hashCalculatorForIC.update(crossModuleReferences.getModuleKind().ordinal());
        List<JsImportedModule> importedModules = crossModuleReferences.getImportedModules();
        hashCalculatorForIC.update(importedModules.size());
        Iterator<T> it = importedModules.iterator();
        while (true) {
            String str = "";
            if (!it.hasNext()) {
                break;
            }
            JsImportedModule jsImportedModule = (JsImportedModule) it.next();
            hashCalculatorForIC.update(jsImportedModule.getExternalName());
            String string = jsImportedModule.getInternalName().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            hashCalculatorForIC.update(string);
            String relativeRequirePath = jsImportedModule.getRelativeRequirePath();
            if (relativeRequirePath != null) {
                str = relativeRequirePath;
            }
            hashCalculatorForIC.update(str);
        }
        List<CrossModuleTransitiveExport> transitiveExportFrom = crossModuleReferences.getTransitiveExportFrom();
        hashCalculatorForIC.update(transitiveExportFrom.size());
        for (CrossModuleTransitiveExport crossModuleTransitiveExport : transitiveExportFrom) {
            String string2 = crossModuleTransitiveExport.getInternalName().toString();
            Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
            hashCalculatorForIC.update(string2);
            hashCalculatorForIC.update(crossModuleTransitiveExport.getExternalName());
        }
        List<String> listSorted = CollectionsKt.sorted(crossModuleReferences.getExports().keySet());
        hashCalculatorForIC.update(listSorted.size());
        for (String str2 : listSorted) {
            hashCalculatorForIC.update(str2);
            String str3 = crossModuleReferences.getExports().get(str2);
            Intrinsics.checkNotNull(str3);
            hashCalculatorForIC.update(str3);
        }
        List<String> listSorted2 = CollectionsKt.sorted(crossModuleReferences.getImports().keySet());
        hashCalculatorForIC.update(listSorted2.size());
        for (String str4 : listSorted2) {
            CrossModuleImport crossModuleImport = crossModuleReferences.getImports().get(str4);
            Intrinsics.checkNotNull(crossModuleImport);
            CrossModuleImport crossModuleImport2 = crossModuleImport;
            hashCalculatorForIC.update(str4);
            hashCalculatorForIC.update(crossModuleImport2.getExportedAs());
            if (crossModuleReferences.getModuleKind() == ModuleKind.ES) {
                String string3 = crossModuleImport2.getModuleExporter().getInternalName().toString();
                Intrinsics.checkNotNullExpressionValue(string3, "toString(...)");
                hashCalculatorForIC.update(string3);
                hashCalculatorForIC.update(crossModuleImport2.getModuleExporter().getExternalName());
                String relativeRequirePath2 = crossModuleImport2.getModuleExporter().getRelativeRequirePath();
                if (relativeRequirePath2 == null) {
                    relativeRequirePath2 = "";
                }
                hashCalculatorForIC.update(relativeRequirePath2);
            } else {
                String string4 = crossModuleImport2.getModuleExporter().getInternalName().toString();
                Intrinsics.checkNotNullExpressionValue(string4, "toString(...)");
                hashCalculatorForIC.update(string4);
            }
        }
        return hashCalculatorForIC.m7106finalizeAndGetHashYddAqs();
    }
}
