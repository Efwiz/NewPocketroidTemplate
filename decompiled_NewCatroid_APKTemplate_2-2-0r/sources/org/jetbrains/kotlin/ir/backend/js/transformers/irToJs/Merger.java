package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.cast.MediaTrack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContextKt;
import org.jetbrains.kotlin.js.backend.ast.HasName;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExport;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsImport;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModuleKey;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsSingleLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.serialization.js.ModuleKind;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: Merger.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f¢\u0006\u0002\u0010\u000eJ\b\u0010\u0018\u001a\u00020\u0019H\u0002J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00110\u0007H\u0002J\b\u0010\u001b\u001a\u00020\u0019H\u0002J\u0006\u0010\u001c\u001a\u00020\u001dJ,\u0010\u001e\u001a\u00020\u00192\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\"0 2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$H\u0002J\u000e\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00110\u0007H\u0002J*\u0010'\u001a\u00020\u0019*\b\u0012\u0004\u0012\u00020\u00110\u00102\b\b\u0002\u0010(\u001a\u00020\u00032\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00110\u0007H\u0002J$\u0010'\u001a\u00020\u0019*\b\u0012\u0004\u0012\u00020\u00110\u00102\b\b\u0002\u0010(\u001a\u00020\u00032\u0006\u0010*\u001a\u00020$H\u0002J,\u0010+\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020!0 *\u00020\b2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020!0\u0013H\u0002J\u0012\u0010-\u001a\u00020\u0019*\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0002J\u0016\u0010.\u001a\u00020\u0011*\u00020\u00112\b\u0010/\u001a\u0004\u0018\u00010!H\u0002J/\u00100\u001a\u0002H1\"\b\b\u0000\u00101*\u000202*\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020!0 2\u0006\u00103\u001a\u0002H1H\u0002¢\u0006\u0002\u00104J \u00100\u001a\u00020!*\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020!0 2\u0006\u0010/\u001a\u00020!H\u0002J\u001c\u00105\u001a\u00020\u0019*\b\u0012\u0004\u0012\u00020\u00110\u00102\b\b\u0002\u00106\u001a\u00020\u0003H\u0002R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00110\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/Merger;", "", "moduleName", "", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "fragments", "", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "crossModuleReferences", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleReferences;", "generateRegionComments", "", "generateCallToMain", "(Ljava/lang/String;Lorg/jetbrains/kotlin/serialization/js/ModuleKind;Ljava/util/List;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleReferences;ZZ)V", "additionalExports", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "importStatements", "", "importedModulesMap", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModuleKey;", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "isEsModules", "assertSingleDefinition", "", "declareAndCallJsExporter", "linkJsNames", "merge", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "processClassModels", "classModelMap", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrIcClassModel;", "preDeclarationBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "postDeclarationBlock", "transitiveJsExport", "addWithComment", "regionDescription", "statements", "block", "buildRenames", "nameMap", "endRegion", "importStatementWithName", "name", "rename", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "rootNode", "(Ljava/util/Map;Lorg/jetbrains/kotlin/js/backend/ast/JsNode;)Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "startRegion", MediaTrack.ROLE_DESCRIPTION, "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Merger {
    private final List<JsStatement> additionalExports;
    private final CrossModuleReferences crossModuleReferences;
    private final List<JsIrProgramFragment> fragments;
    private final boolean generateCallToMain;
    private final boolean generateRegionComments;
    private final Map<String, JsStatement> importStatements;
    private final Map<JsImportedModuleKey, JsImportedModule> importedModulesMap;
    private final boolean isEsModules;
    private final ModuleKind moduleKind;
    private final String moduleName;

    public Merger(String moduleName, ModuleKind moduleKind, List<JsIrProgramFragment> fragments, CrossModuleReferences crossModuleReferences, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        Intrinsics.checkNotNullParameter(crossModuleReferences, "crossModuleReferences");
        this.moduleName = moduleName;
        this.moduleKind = moduleKind;
        this.fragments = fragments;
        this.crossModuleReferences = crossModuleReferences;
        this.generateRegionComments = z;
        this.generateCallToMain = z2;
        this.isEsModules = moduleKind == ModuleKind.ES;
        this.importStatements = new LinkedHashMap();
        this.importedModulesMap = new LinkedHashMap();
        this.additionalExports = new ArrayList();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void linkJsNames() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (JsIrProgramFragment jsIrProgramFragment : this.fragments) {
            Map<JsName, JsName> mapBuildRenames = buildRenames(jsIrProgramFragment, linkedHashMap);
            rename(mapBuildRenames, jsIrProgramFragment.getDeclarations());
            rename(mapBuildRenames, jsIrProgramFragment.getExports());
            Iterator<T> it = jsIrProgramFragment.getImports().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String str = (String) entry.getKey();
                JsStatement jsStatement = (JsStatement) entry.getValue();
                JsName jsName = linkedHashMap.get(str);
                if (jsName == null && !this.isEsModules) {
                    throw new IllegalStateException(("Missing name for declaration '" + str + '\'').toString());
                }
                this.importStatements.putIfAbsent(str, (JsStatement) rename(mapBuildRenames, importStatementWithName(jsStatement, jsName)));
            }
            Map mapPlus = MapsKt.plus(new LinkedHashMap(), jsIrProgramFragment.getClasses());
            jsIrProgramFragment.getClasses().clear();
            for (Map.Entry entry2 : mapPlus.entrySet()) {
                JsName jsName2 = (JsName) entry2.getKey();
                JsIrIcClassModel jsIrIcClassModel = (JsIrIcClassModel) entry2.getValue();
                Map<JsName, JsIrIcClassModel> classes = jsIrProgramFragment.getClasses();
                JsName jsNameRename = rename(mapBuildRenames, jsName2);
                List<JsName> superClasses = jsIrIcClassModel.getSuperClasses();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superClasses, 10));
                Iterator<T> it2 = superClasses.iterator();
                while (it2.hasNext()) {
                    arrayList.add(rename(mapBuildRenames, (JsName) it2.next()));
                }
                JsIrIcClassModel jsIrIcClassModel2 = new JsIrIcClassModel(arrayList);
                List<JsStatement> statements = jsIrIcClassModel2.getPreDeclarationBlock().getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                List<JsStatement> statements2 = jsIrIcClassModel.getPreDeclarationBlock().getStatements();
                Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                CollectionsKt.addAll(statements, statements2);
                List<JsStatement> statements3 = jsIrIcClassModel2.getPostDeclarationBlock().getStatements();
                Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
                List<JsStatement> statements4 = jsIrIcClassModel.getPostDeclarationBlock().getStatements();
                Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
                CollectionsKt.addAll(statements3, statements4);
                rename(mapBuildRenames, jsIrIcClassModel2.getPreDeclarationBlock());
                rename(mapBuildRenames, jsIrIcClassModel2.getPostDeclarationBlock());
                classes.put(jsNameRename, jsIrIcClassModel2);
            }
            rename(mapBuildRenames, jsIrProgramFragment.getInitializers());
            JsStatement mainFunction = jsIrProgramFragment.getMainFunction();
            if (mainFunction != null) {
            }
            JsStatement testFunInvocation = jsIrProgramFragment.getTestFunInvocation();
            if (testFunInvocation != null) {
            }
            JsName suiteFn = jsIrProgramFragment.getSuiteFn();
            if (suiteFn != null) {
                jsIrProgramFragment.setSuiteFn(rename(mapBuildRenames, suiteFn));
            }
        }
        for (Map.Entry<String, JsStatement> entry3 : this.crossModuleReferences.getJsImports().entrySet()) {
            String key = entry3.getKey();
            JsStatement value = entry3.getValue();
            JsName jsName3 = linkedHashMap.get(key);
            if (jsName3 == null) {
                throw new IllegalStateException(("Missing name for declaration '" + key + '\'').toString());
            }
            this.importStatements.putIfAbsent(key, JsIrProgramFragmentKt.renameImportedSymbolInternalName(value, jsName3));
        }
        if (this.crossModuleReferences.getExports().isEmpty()) {
            return;
        }
        JsName jsNameMakeInternalModuleName = ReservedJsNames.INSTANCE.makeInternalModuleName();
        if (!this.isEsModules) {
            JsStatement jsStatementMakeStmt = JsAstUtilsKt.jsAssignment(ReservedJsNames.INSTANCE.makeCrossModuleNameRef(jsNameMakeInternalModuleName), JsAstUtils.INSTANCE.or(ReservedJsNames.INSTANCE.makeCrossModuleNameRef(jsNameMakeInternalModuleName), new JsObjectLiteral())).makeStmt();
            Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
            this.additionalExports.add(jsStatementMakeStmt);
            Iterator<T> it3 = this.crossModuleReferences.getExports().entrySet().iterator();
            while (it3.hasNext()) {
                Map.Entry entry4 = (Map.Entry) it3.next();
                String str2 = (String) entry4.getKey();
                String str3 = (String) entry4.getValue();
                JsName jsName4 = linkedHashMap.get(str2);
                if (jsName4 == null) {
                    throw new IllegalStateException(("Missing name for declaration '" + str2 + '\'').toString());
                }
                this.additionalExports.add(JsAstUtilsKt.jsAssignment(new JsNameRef(str3, ReservedJsNames.INSTANCE.makeCrossModuleNameRef(ReservedJsNames.INSTANCE.makeInternalModuleName())), new JsNameRef(jsName4)).makeStmt());
            }
            return;
        }
        Set<Map.Entry<String, String>> setEntrySet = this.crossModuleReferences.getExports().entrySet();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10));
        Iterator<T> it4 = setEntrySet.iterator();
        while (it4.hasNext()) {
            Map.Entry entry5 = (Map.Entry) it4.next();
            String str4 = (String) entry5.getKey();
            String str5 = (String) entry5.getValue();
            JsName jsName5 = linkedHashMap.get(str4);
            if (jsName5 == null) {
                throw new IllegalStateException(("Missing name for declaration '" + str4 + '\'').toString());
            }
            JsNameRef jsNameRefMakeRef = jsName5.makeRef();
            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
            arrayList2.add(new JsExport.Element(jsNameRefMakeRef, new JsName(str5, false)));
        }
        this.additionalExports.add(new JsExport((JsExport.Subject) new JsExport.Subject.Elements(arrayList2), (String) null, 2, (DefaultConstructorMarker) (0 == true ? 1 : 0)));
    }

    private final Map<JsName, JsName> buildRenames(JsIrProgramFragment jsIrProgramFragment, Map<String, JsName> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (JsImportedModule jsImportedModule : jsIrProgramFragment.getImportedModules()) {
            Map<JsImportedModuleKey, JsImportedModule> map2 = this.importedModulesMap;
            JsImportedModuleKey key = jsImportedModule.getKey();
            JsImportedModule jsImportedModule2 = map2.get(key);
            if (jsImportedModule2 == null) {
                map2.put(key, jsImportedModule);
                jsImportedModule2 = jsImportedModule;
            }
            JsImportedModule jsImportedModule3 = jsImportedModule2;
            if (jsImportedModule3 != jsImportedModule) {
                linkedHashMap.put(jsImportedModule.getInternalName(), jsImportedModule3.getInternalName());
            }
        }
        Iterator<T> it = jsIrProgramFragment.getNameBindings().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String str = (String) entry.getKey();
            JsName jsName = (JsName) entry.getValue();
            JsName jsName2 = map.get(str);
            if (jsName2 == null) {
                map.put(str, jsName);
                jsName2 = jsName;
            }
            JsName jsName3 = jsName2;
            if (jsName3 != jsName) {
                linkedHashMap.put(jsName, jsName3);
            }
        }
        return linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JsName rename(Map<JsName, ? extends JsName> map, JsName jsName) {
        JsName jsName2 = map.get(jsName);
        if (jsName2 != null) {
            jsName = jsName2;
        }
        return jsName;
    }

    private final <T extends JsNode> T rename(final Map<JsName, ? extends JsName> map, T t) {
        t.accept(new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.Merger.rename.2
            @Override // org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitor
            protected void visitElement(JsNode node) {
                Intrinsics.checkNotNullParameter(node, "node");
                super.visitElement(node);
                if (node instanceof HasName) {
                    HasName hasName = (HasName) node;
                    JsName name2 = hasName.getName();
                    hasName.setName(name2 != null ? Merger.this.rename(map, name2) : null);
                }
            }
        });
        return t;
    }

    private final void assertSingleDefinition() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = this.fragments.iterator();
        while (it.hasNext()) {
            for (String str : ((JsIrProgramFragment) it.next()).getDefinitions()) {
                if (!linkedHashSet.add(str)) {
                    throw new IllegalStateException(("Clashing definitions with tag '" + str + '\'').toString());
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<JsStatement> declareAndCallJsExporter() {
        Sequence sequenceAsSequence;
        Sequence sequenceFlatMapIterable;
        Sequence sequenceDistinctBy;
        int i = 2;
        if (this.isEsModules) {
            List<JsIrProgramFragment> list = this.fragments;
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                List<JsStatement> statements = ((JsIrProgramFragment) it.next()).getExports().getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                CollectionsKt.addAll(arrayList, statements);
            }
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            for (Object obj : arrayList) {
                if (obj instanceof JsExport) {
                    arrayList2.add(obj);
                } else {
                    arrayList3.add(obj);
                }
            }
            Pair pair = new Pair(arrayList2, arrayList3);
            List list2 = (List) pair.component1();
            List list3 = (List) pair.component2();
            ArrayList arrayList4 = new ArrayList();
            ArrayList arrayList5 = new ArrayList();
            for (Object obj2 : list2) {
                if (((JsExport) obj2).getFromModule() == null) {
                    arrayList4.add(obj2);
                } else {
                    arrayList5.add(obj2);
                }
            }
            Pair pair2 = new Pair(arrayList4, arrayList5);
            List list4 = (List) pair2.component1();
            List list5 = (List) pair2.component2();
            JsExport jsExport = null;
            Object[] objArr = 0;
            Object[] objArr2 = 0;
            if (list4.isEmpty()) {
                list4 = null;
            }
            List list6 = (list4 == null || (sequenceAsSequence = CollectionsKt.asSequence(list4)) == null || (sequenceFlatMapIterable = SequencesKt.flatMapIterable(sequenceAsSequence, new Function1<JsExport, List<? extends JsExport.Element>>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.Merger$declareAndCallJsExporter$exportedElements$2
                @Override // kotlin.jvm.functions.Function1
                public final List<JsExport.Element> invoke(JsExport it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    JsExport.Subject subject = it2.getSubject();
                    Intrinsics.checkNotNull(subject, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsExport.Subject.Elements");
                    return ((JsExport.Subject.Elements) subject).getElements();
                }
            })) == null || (sequenceDistinctBy = SequencesKt.distinctBy(sequenceFlatMapIterable, new Function1<JsExport.Element, String>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.Merger$declareAndCallJsExporter$exportedElements$3
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(JsExport.Element it2) {
                    String ident;
                    Intrinsics.checkNotNullParameter(it2, "it");
                    JsName alias = it2.getAlias();
                    return (alias == null || (ident = alias.getIdent()) == null) ? it2.getName().getIdent() : ident;
                }
            })) == null) ? null : SequencesKt.toList(sequenceDistinctBy);
            if (list6 != null) {
                jsExport = new JsExport((JsExport.Subject) new JsExport.Subject.Elements(list6), (String) (objArr2 == true ? 1 : 0), i, (DefaultConstructorMarker) (objArr == true ? 1 : 0));
            }
            return CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) list3, (Iterable) CollectionsKt.listOfNotNull(jsExport)), (Iterable) list5);
        }
        List<JsIrProgramFragment> list7 = this.fragments;
        ArrayList arrayList6 = new ArrayList();
        Iterator<T> it2 = list7.iterator();
        while (it2.hasNext()) {
            List<JsStatement> statements2 = ((JsIrProgramFragment) it2.next()).getExports().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            CollectionsKt.addAll(arrayList6, statements2);
        }
        JsBlock jsBlock = new JsBlock(arrayList6);
        if (jsBlock.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        JsName jsNameMakeInternalModuleName = ReservedJsNames.INSTANCE.makeInternalModuleName();
        JsName jsNameMakeJsExporterName = ReservedJsNames.INSTANCE.makeJsExporterName();
        JsFunction jsFunction = new JsFunction(JsGenerationContextKt.getEmptyScope(), "js exporter function");
        jsFunction.setBody(jsBlock);
        jsFunction.setName(jsNameMakeJsExporterName);
        jsFunction.getParameters().add(new JsParameter(jsNameMakeInternalModuleName));
        List<JsStatement> listMutableListOf = CollectionsKt.mutableListOf(jsFunction.makeStmt(), new JsInvocation(jsNameMakeJsExporterName.makeRef(), jsNameMakeInternalModuleName.makeRef()).makeStmt());
        if (!this.generateCallToMain) {
            JsNameRef jsNameRef = new JsNameRef(jsNameMakeJsExporterName, jsNameMakeInternalModuleName.makeRef());
            JsNameRef jsNameRefMakeRef = jsNameMakeJsExporterName.makeRef();
            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
            listMutableListOf.add(JsAstUtilsKt.jsAssignment(jsNameRef, jsNameRefMakeRef).makeStmt());
        }
        return listMutableListOf;
    }

    private final List<JsStatement> transitiveJsExport() {
        if (this.isEsModules) {
            List<CrossModuleTransitiveExport> transitiveExportFrom = this.crossModuleReferences.getTransitiveExportFrom();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(transitiveExportFrom, 10));
            Iterator<T> it = transitiveExportFrom.iterator();
            while (it.hasNext()) {
                arrayList.add(new JsExport(JsExport.Subject.All.INSTANCE, JsIrProgramFragmentKt.getRequireEsmName((CrossModuleTransitiveExport) it.next())));
            }
            return arrayList;
        }
        JsName jsNameMakeInternalModuleName = ReservedJsNames.INSTANCE.makeInternalModuleName();
        JsName jsNameMakeJsExporterName = ReservedJsNames.INSTANCE.makeJsExporterName();
        List<CrossModuleTransitiveExport> transitiveExportFrom2 = this.crossModuleReferences.getTransitiveExportFrom();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(transitiveExportFrom2, 10));
        Iterator<T> it2 = transitiveExportFrom2.iterator();
        while (it2.hasNext()) {
            JsStatement jsStatementMakeStmt = new JsInvocation(new JsNameRef(jsNameMakeJsExporterName, ((CrossModuleTransitiveExport) it2.next()).getInternalName().makeRef()), jsNameMakeInternalModuleName.makeRef()).makeStmt();
            Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
            arrayList2.add(jsStatementMakeStmt);
        }
        return arrayList2;
    }

    public final JsProgram merge() {
        JsName suiteFn;
        JsStatement mainFunction;
        assertSingleDefinition();
        linkJsNames();
        ArrayList arrayList = new ArrayList();
        JsCompositeBlock jsCompositeBlock = new JsCompositeBlock();
        JsCompositeBlock jsCompositeBlock2 = new JsCompositeBlock();
        JsCompositeBlock jsCompositeBlock3 = new JsCompositeBlock();
        JsCompositeBlock jsCompositeBlock4 = jsCompositeBlock;
        addWithComment(arrayList, "block: pre-declaration", jsCompositeBlock4);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        JsCompositeBlock jsCompositeBlock5 = new JsCompositeBlock();
        for (JsIrProgramFragment jsIrProgramFragment : this.fragments) {
            List<JsStatement> statements = jsIrProgramFragment.getDeclarations().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            CollectionsKt.addAll(arrayList, statements);
            linkedHashMap.putAll(jsIrProgramFragment.getClasses());
            List<JsStatement> statements2 = jsCompositeBlock5.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            List<JsStatement> statements3 = jsIrProgramFragment.getInitializers().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
            CollectionsKt.addAll(statements2, statements3);
            List<JsStatement> statements4 = jsCompositeBlock3.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
            List<JsStatement> statements5 = jsIrProgramFragment.getPolyfills().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements5, "getStatements(...)");
            CollectionsKt.addAll(statements4, statements5);
        }
        processClassModels(linkedHashMap, jsCompositeBlock4, jsCompositeBlock2);
        List<JsStatement> statements6 = jsCompositeBlock2.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements6, "getStatements(...)");
        addWithComment(arrayList, "block: post-declaration", statements6);
        List<JsStatement> statements7 = jsCompositeBlock5.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements7, "getStatements(...)");
        addWithComment(arrayList, "block: init", statements7);
        List<JsIrProgramFragment> list = this.fragments;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (((JsIrProgramFragment) it.next()).getTestFunInvocation() != null) {
                    JsFunction jsFunction = new JsFunction(JsGenerationContextKt.getEmptyScope(), new JsBlock(), "root test fun");
                    Iterator<T> it2 = this.fragments.iterator();
                    do {
                        if (!it2.hasNext()) {
                            suiteFn = null;
                            break;
                        }
                        suiteFn = ((JsIrProgramFragment) it2.next()).getSuiteFn();
                    } while (suiteFn == null);
                    if (suiteFn == null) {
                        throw new NoSuchElementException("No element of the collection was transformed to a non-null value.");
                    }
                    JsNameRef jsNameRefMakeRef = suiteFn.makeRef();
                    Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
                    List<JsIrProgramFragment> list2 = this.fragments;
                    ArrayList<JsIrProgramFragment> arrayList2 = new ArrayList();
                    for (Object obj : list2) {
                        if (((JsIrProgramFragment) obj).getTestFunInvocation() != null) {
                            arrayList2.add(obj);
                        }
                    }
                    LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                    for (JsIrProgramFragment jsIrProgramFragment2 : arrayList2) {
                        String packageFqn = jsIrProgramFragment2.getPackageFqn();
                        Object obj2 = linkedHashMap2.get(packageFqn);
                        if (obj2 == null) {
                            obj2 = (List) new ArrayList();
                            linkedHashMap2.put(packageFqn, obj2);
                        }
                        ((List) obj2).add(jsIrProgramFragment2.getTestFunInvocation());
                    }
                    for (Map.Entry entry : linkedHashMap2.entrySet()) {
                        String str = (String) entry.getKey();
                        List list3 = (List) entry.getValue();
                        JsFunction jsFunction2 = new JsFunction(JsGenerationContextKt.getEmptyScope(), new JsBlock(), "test fun for " + str);
                        List<JsStatement> statements8 = jsFunction2.getBody().getStatements();
                        Intrinsics.checkNotNullExpressionValue(statements8, "getStatements(...)");
                        CollectionsKt.addAll(statements8, list3);
                        List<JsStatement> statements9 = jsFunction.getBody().getStatements();
                        Intrinsics.checkNotNullExpressionValue(statements9, "getStatements(...)");
                        statements9.add(new JsInvocation(jsNameRefMakeRef, new JsStringLiteral(str), new JsBooleanLiteral(false), jsFunction2).makeStmt());
                    }
                    startRegion(arrayList, "block: tests");
                    arrayList.add(new JsInvocation(jsFunction, new JsExpression[0]).makeStmt());
                    endRegion(arrayList);
                    break;
                }
            }
        }
        Iterator it3 = CollectionsKt.sortedWith(this.fragments, new Comparator() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.Merger$merge$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((JsIrProgramFragment) t).getPackageFqn(), ((JsIrProgramFragment) t2).getPackageFqn());
            }
        }).iterator();
        do {
            if (!it3.hasNext()) {
                mainFunction = null;
                break;
            }
            mainFunction = ((JsIrProgramFragment) it3.next()).getMainFunction();
        } while (mainFunction == null);
        List<? extends JsStatement> listPlus = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) declareAndCallJsExporter(), (Iterable) this.additionalExports), (Iterable) transitiveJsExport());
        List<JsImportedModule> listPlus2 = CollectionsKt.plus((Collection) CollectionsKt.toList(this.importedModulesMap.values()), (Iterable) this.crossModuleReferences.getImportedModules());
        List<? extends JsStatement> list4 = CollectionsKt.toList(this.importStatements.values());
        JsProgram jsProgram = new JsProgram();
        JsName jsNameMakeInternalModuleName = ReservedJsNames.INSTANCE.makeInternalModuleName();
        JsFunction jsFunction3 = new JsFunction(jsProgram.getRootScope(), new JsBlock(), "root function");
        List<JsParameter> parameters = jsFunction3.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        parameters.add(new JsParameter(jsNameMakeInternalModuleName));
        List<JsParameter> parameters2 = jsFunction3.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
        List<JsParameter> list5 = parameters2;
        List<JsImportedModule> list6 = listPlus2;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list6, 10));
        Iterator it4 = list6.iterator();
        while (it4.hasNext()) {
            arrayList3.add(new JsParameter(((JsImportedModule) it4.next()).getInternalName()));
        }
        CollectionsKt.addAll(list5, arrayList3);
        JsBlock body = jsFunction3.getBody();
        if (!this.isEsModules) {
            List<JsStatement> statements10 = body.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements10, "getStatements(...)");
            statements10.add(new JsStringLiteral("use strict").makeStmt());
        }
        List<JsStatement> statements11 = body.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements11, "getStatements(...)");
        addWithComment(statements11, "block: imports", list4);
        List<JsStatement> statements12 = body.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements12, "getStatements(...)");
        CollectionsKt.addAll(statements12, arrayList);
        List<JsStatement> statements13 = body.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements13, "getStatements(...)");
        addWithComment(statements13, "block: exports", listPlus);
        if (this.generateCallToMain && mainFunction != null) {
            List<JsStatement> statements14 = body.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements14, "getStatements(...)");
            statements14.add(mainFunction);
        }
        List<JsStatement> statements15 = body.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements15, "getStatements(...)");
        statements15.add(new JsReturn(jsNameMakeInternalModuleName.makeRef()));
        List<JsStatement> statements16 = jsCompositeBlock3.getStatements();
        Intrinsics.checkNotNull(statements16);
        List<JsStatement> list7 = !statements16.isEmpty() ? statements16 : null;
        if (list7 != null) {
            List<JsStatement> statements17 = jsProgram.getGlobalBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements17, "getStatements(...)");
            addWithComment(statements17, "block: polyfills", list7);
        }
        List<JsStatement> statements18 = jsProgram.getGlobalBlock().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements18, "getStatements(...)");
        CollectionsKt.addAll(statements18, ModuleWrapperTranslation.INSTANCE.wrap(this.moduleName, jsFunction3, listPlus2, jsProgram, this.moduleKind));
        return jsProgram;
    }

    private final void processClassModels(final Map<JsName, JsIrIcClassModel> classModelMap, final JsBlock preDeclarationBlock, final JsBlock postDeclarationBlock) {
        DFS.dfs(classModelMap.keySet(), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.Merger$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return Merger.processClassModels$lambda$41(classModelMap, (JsName) obj);
            }
        }, new DFS.AbstractNodeHandler<JsName, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.Merger$processClassModels$declarationHandler$1
            /* JADX INFO: renamed from: result, reason: collision with other method in class */
            public void m7202result() {
            }

            @Override // org.jetbrains.kotlin.utils.DFS.NodeHandler
            public /* bridge */ /* synthetic */ Object result() {
                m7202result();
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
            public void afterChildren(JsName current) {
                Intrinsics.checkNotNullParameter(current, "current");
                JsIrIcClassModel jsIrIcClassModel = classModelMap.get(current);
                if (jsIrIcClassModel != null) {
                    JsBlock jsBlock = preDeclarationBlock;
                    JsBlock jsBlock2 = postDeclarationBlock;
                    List<JsStatement> statements = jsBlock.getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                    List<JsStatement> statements2 = jsIrIcClassModel.getPreDeclarationBlock().getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                    CollectionsKt.addAll(statements, statements2);
                    List<JsStatement> statements3 = jsBlock2.getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
                    List<JsStatement> statements4 = jsIrIcClassModel.getPostDeclarationBlock().getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
                    CollectionsKt.addAll(statements3, statements4);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable processClassModels$lambda$41(Map classModelMap, JsName jsName) {
        List<JsName> listEmptyList;
        Intrinsics.checkNotNullParameter(classModelMap, "$classModelMap");
        JsIrIcClassModel jsIrIcClassModel = (JsIrIcClassModel) classModelMap.get(jsName);
        if (jsIrIcClassModel == null || (listEmptyList = jsIrIcClassModel.getSuperClasses()) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        return listEmptyList;
    }

    private final void startRegion(List<JsStatement> list, String str) {
        if (this.generateRegionComments) {
            list.add(new JsSingleLineComment("region " + str));
        }
    }

    private final void endRegion(List<JsStatement> list) {
        if (this.generateRegionComments) {
            list.add(new JsSingleLineComment("endregion"));
        }
    }

    private final void addWithComment(List<JsStatement> list, String str, JsBlock jsBlock) {
        startRegion(list, str);
        list.add(jsBlock);
        endRegion(list);
    }

    private final void addWithComment(List<JsStatement> list, String str, List<? extends JsStatement> list2) {
        if (list2.isEmpty()) {
            return;
        }
        startRegion(list, str);
        CollectionsKt.addAll(list, list2);
        endRegion(list);
    }

    private final JsStatement importStatementWithName(JsStatement jsStatement, JsName jsName) {
        JsImport.Target.Elements elements;
        if (jsName == null) {
            return jsStatement;
        }
        if (jsStatement instanceof JsVars) {
            List<JsVars.JsVar> vars = ((JsVars) jsStatement).getVars();
            Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
            return new JsVars(new JsVars.JsVar(jsName, ((JsVars.JsVar) CollectionsKt.single((List) vars)).getInitExpression()));
        }
        if (jsStatement instanceof JsImport) {
            JsImport jsImport = (JsImport) jsStatement;
            String module = jsImport.getModule();
            JsImport.Target target = jsImport.getTarget();
            if (target instanceof JsImport.Target.All) {
                JsNameRef jsNameRefMakeRef = jsName.makeRef();
                Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
                elements = new JsImport.Target.All(jsNameRefMakeRef);
            } else if (target instanceof JsImport.Target.Default) {
                JsNameRef jsNameRefMakeRef2 = jsName.makeRef();
                Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef2, "makeRef(...)");
                elements = new JsImport.Target.Default(jsNameRefMakeRef2);
            } else if (target instanceof JsImport.Target.Elements) {
                elements = new JsImport.Target.Elements(CollectionsKt.mutableListOf(new JsImport.Element(((JsImport.Element) CollectionsKt.single((List) jsImport.getElements())).getName(), jsName.makeRef())));
            } else {
                throw new NoWhenBranchMatchedException();
            }
            return new JsImport(module, elements);
        }
        if (!(jsStatement instanceof JsCompositeBlock)) {
            throw new IllegalStateException(("Unexpected import statement " + Reflection.getOrCreateKotlinClass(jsStatement.getClass()).getQualifiedName()).toString());
        }
        JsCompositeBlock jsCompositeBlock = (JsCompositeBlock) jsStatement;
        List<JsStatement> statements = jsCompositeBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        List listDropLast = CollectionsKt.dropLast(statements, 1);
        List<JsStatement> statements2 = jsCompositeBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
        Object objLast = CollectionsKt.last((List<? extends Object>) statements2);
        Intrinsics.checkNotNullExpressionValue(objLast, "last(...)");
        return new JsCompositeBlock((List<? extends JsStatement>) CollectionsKt.plus((Collection<? extends JsStatement>) listDropLast, importStatementWithName((JsStatement) objLast, jsName)));
    }
}
