package org.jetbrains.kotlin.serialization.js.ast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.backend.ast.JsClassModel;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsLocation;
import org.jetbrains.kotlin.js.backend.ast.JsLocationWithEmbeddedSource;
import org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameBinding;
import org.jetbrains.kotlin.js.backend.ast.JsProgram;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsRootScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.ProtocolStringList;

/* JADX INFO: compiled from: JsAstDeserializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\f\u001a\u00020\u00122\u0006\u0010\u0010\u001a\u00020\u0013H\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0014H\u0002J\u001a\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0014R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/ast/JsAstDeserializer;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstDeserializerBase;", ConfigConstants.CONFIG_KEY_PROGRAM, "Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;", "sourceRoots", "", "Ljava/io/File;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsProgram;Ljava/lang/Iterable;)V", "scope", "Lorg/jetbrains/kotlin/js/backend/ast/JsRootScope;", "getScope", "()Lorg/jetbrains/kotlin/js/backend/ast/JsRootScope;", "deserialize", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "input", "Ljava/io/InputStream;", "proto", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Chunk;", "Lorg/jetbrains/kotlin/js/backend/ast/JsClassModel;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$ClassModel;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Fragment;", "embedSources", "Lorg/jetbrains/kotlin/js/backend/ast/JsLocationWithSource;", "deserializedLocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsLocation;", "file", "", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsAstDeserializer extends JsAstDeserializerBase {
    private final JsRootScope scope;
    private final Iterable<File> sourceRoots;

    /* JADX WARN: Multi-variable type inference failed */
    public JsAstDeserializer(JsProgram program, Iterable<? extends File> sourceRoots) {
        Intrinsics.checkNotNullParameter(program, "program");
        Intrinsics.checkNotNullParameter(sourceRoots, "sourceRoots");
        this.sourceRoots = sourceRoots;
        this.scope = new JsRootScope(program);
    }

    @Override // org.jetbrains.kotlin.serialization.js.ast.JsAstDeserializerBase
    public JsRootScope getScope() {
        return this.scope;
    }

    public final JsProgramFragment deserialize(InputStream input) throws IOException {
        Intrinsics.checkNotNullParameter(input, "input");
        CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(input);
        codedInputStreamNewInstance.setRecursionLimit(4096);
        JsAstProtoBuf.Chunk from = JsAstProtoBuf.Chunk.parseFrom(codedInputStreamNewInstance);
        Intrinsics.checkNotNullExpressionValue(from, "parseFrom(...)");
        return deserialize(from);
    }

    public final JsProgramFragment deserialize(JsAstProtoBuf.Chunk proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        List<String> stringTable = getStringTable();
        ProtocolStringList entryList = proto.getStringTable().getEntryList();
        Intrinsics.checkNotNullExpressionValue(entryList, "getEntryList(...)");
        CollectionsKt.addAll(stringTable, entryList);
        List<JsAstProtoBuf.Name> nameTable = getNameTable();
        List<JsAstProtoBuf.Name> entryList2 = proto.getNameTable().getEntryList();
        Intrinsics.checkNotNullExpressionValue(entryList2, "getEntryList(...)");
        CollectionsKt.addAll(nameTable, entryList2);
        List<JsName> nameCache = getNameCache();
        List<JsAstProtoBuf.Name> nameTable2 = getNameTable();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(nameTable2, 10));
        for (JsAstProtoBuf.Name name2 : nameTable2) {
            arrayList.add(null);
        }
        CollectionsKt.addAll(nameCache, arrayList);
        try {
            JsAstProtoBuf.Fragment fragment = proto.getFragment();
            Intrinsics.checkNotNullExpressionValue(fragment, "getFragment(...)");
            return deserialize(fragment);
        } finally {
            getStringTable().clear();
            getNameTable().clear();
            getNameCache().clear();
        }
    }

    private final JsProgramFragment deserialize(JsAstProtoBuf.Fragment proto) {
        JsExpression jsExpressionDeserialize;
        JsRootScope scope = getScope();
        String packageFqn = proto.getPackageFqn();
        Intrinsics.checkNotNullExpressionValue(packageFqn, "getPackageFqn(...)");
        JsProgramFragment jsProgramFragment = new JsProgramFragment(scope, packageFqn);
        List<JsImportedModule> importedModules = jsProgramFragment.getImportedModules();
        List<JsAstProtoBuf.ImportedModule> importedModuleList = proto.getImportedModuleList();
        Intrinsics.checkNotNullExpressionValue(importedModuleList, "getImportedModuleList(...)");
        List<JsAstProtoBuf.ImportedModule> list = importedModuleList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (JsAstProtoBuf.ImportedModule importedModule : list) {
            String strDeserializeString = deserializeString(importedModule.getExternalNameId());
            JsName jsNameDeserializeName = deserializeName(importedModule.getInternalNameId());
            if (importedModule.hasPlainReference()) {
                JsAstProtoBuf.Expression plainReference = importedModule.getPlainReference();
                Intrinsics.checkNotNullExpressionValue(plainReference, "getPlainReference(...)");
                jsExpressionDeserialize = deserialize(plainReference);
            } else {
                jsExpressionDeserialize = null;
            }
            arrayList.add(new JsImportedModule(strDeserializeString, jsNameDeserializeName, jsExpressionDeserialize, null, 8, null));
        }
        CollectionsKt.addAll(importedModules, arrayList);
        Map<String, JsExpression> imports = jsProgramFragment.getImports();
        List<JsAstProtoBuf.Import> importEntryList = proto.getImportEntryList();
        Intrinsics.checkNotNullExpressionValue(importEntryList, "getImportEntryList(...)");
        List<JsAstProtoBuf.Import> list2 = importEntryList;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
        for (JsAstProtoBuf.Import r3 : list2) {
            String strDeserializeString2 = deserializeString(r3.getSignatureId());
            JsAstProtoBuf.Expression expression = r3.getExpression();
            Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
            Pair pair = TuplesKt.to(strDeserializeString2, deserialize(expression));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        imports.putAll(linkedHashMap);
        if (proto.hasDeclarationBlock()) {
            List<JsStatement> statements = jsProgramFragment.getDeclarationBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            JsAstProtoBuf.CompositeBlock declarationBlock = proto.getDeclarationBlock();
            Intrinsics.checkNotNullExpressionValue(declarationBlock, "getDeclarationBlock(...)");
            List<JsStatement> statements2 = deserializeCompositeBlock(declarationBlock).getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            CollectionsKt.addAll(statements, statements2);
        }
        if (proto.hasInitializerBlock()) {
            List<JsStatement> statements3 = jsProgramFragment.getInitializerBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
            JsAstProtoBuf.CompositeBlock initializerBlock = proto.getInitializerBlock();
            Intrinsics.checkNotNullExpressionValue(initializerBlock, "getInitializerBlock(...)");
            List<JsStatement> statements4 = deserializeCompositeBlock(initializerBlock).getStatements();
            Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
            CollectionsKt.addAll(statements3, statements4);
        }
        if (proto.hasExportBlock()) {
            List<JsStatement> statements5 = jsProgramFragment.getExportBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements5, "getStatements(...)");
            JsAstProtoBuf.CompositeBlock exportBlock = proto.getExportBlock();
            Intrinsics.checkNotNullExpressionValue(exportBlock, "getExportBlock(...)");
            List<JsStatement> statements6 = deserializeCompositeBlock(exportBlock).getStatements();
            Intrinsics.checkNotNullExpressionValue(statements6, "getStatements(...)");
            CollectionsKt.addAll(statements5, statements6);
        }
        List<JsNameBinding> nameBindings = jsProgramFragment.getNameBindings();
        List<JsAstProtoBuf.NameBinding> nameBindingList = proto.getNameBindingList();
        Intrinsics.checkNotNullExpressionValue(nameBindingList, "getNameBindingList(...)");
        List<JsAstProtoBuf.NameBinding> list3 = nameBindingList;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        for (JsAstProtoBuf.NameBinding nameBinding : list3) {
            arrayList2.add(new JsNameBinding(deserializeString(nameBinding.getSignatureId()), deserializeName(nameBinding.getNameId())));
        }
        CollectionsKt.addAll(nameBindings, arrayList2);
        Map<JsName, JsClassModel> classes = jsProgramFragment.getClasses();
        List<JsAstProtoBuf.ClassModel> classModelList = proto.getClassModelList();
        Intrinsics.checkNotNullExpressionValue(classModelList, "getClassModelList(...)");
        List<JsAstProtoBuf.ClassModel> list4 = classModelList;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list4, 10)), 16));
        for (JsAstProtoBuf.ClassModel classModel : list4) {
            Intrinsics.checkNotNull(classModel);
            JsClassModel jsClassModelDeserialize = deserialize(classModel);
            Pair pair2 = TuplesKt.to(jsClassModelDeserialize.getName(), jsClassModelDeserialize);
            linkedHashMap2.put(pair2.getFirst(), pair2.getSecond());
        }
        classes.putAll(linkedHashMap2);
        List<JsAstProtoBuf.Expression> moduleExpressionList = proto.getModuleExpressionList();
        Intrinsics.checkNotNullExpressionValue(moduleExpressionList, "getModuleExpressionList(...)");
        List<JsAstProtoBuf.Expression> list5 = moduleExpressionList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list5, 10));
        for (JsAstProtoBuf.Expression expression2 : list5) {
            Intrinsics.checkNotNull(expression2);
            arrayList3.add(deserialize(expression2));
        }
        ArrayList arrayList4 = arrayList3;
        Map<String, JsExpression> inlineModuleMap = jsProgramFragment.getInlineModuleMap();
        List<JsAstProtoBuf.InlineModule> inlineModuleList = proto.getInlineModuleList();
        Intrinsics.checkNotNullExpressionValue(inlineModuleList, "getInlineModuleList(...)");
        List<JsAstProtoBuf.InlineModule> list6 = inlineModuleList;
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list6, 10)), 16));
        for (JsAstProtoBuf.InlineModule inlineModule : list6) {
            Pair pair3 = TuplesKt.to(deserializeString(inlineModule.getSignatureId()), arrayList4.get(inlineModule.getExpressionId()));
            linkedHashMap3.put(pair3.getFirst(), pair3.getSecond());
        }
        inlineModuleMap.putAll(linkedHashMap3);
        for (JsNameBinding jsNameBinding : jsProgramFragment.getNameBindings()) {
            if (jsProgramFragment.getImports().containsKey(jsNameBinding.getKey())) {
                MetadataProperties.setImported(jsNameBinding.getName(), true);
            }
        }
        if (proto.hasTestsInvocation()) {
            JsAstProtoBuf.Statement testsInvocation = proto.getTestsInvocation();
            Intrinsics.checkNotNullExpressionValue(testsInvocation, "getTestsInvocation(...)");
            jsProgramFragment.setTests(deserialize(testsInvocation));
        }
        if (proto.hasMainInvocation()) {
            JsAstProtoBuf.Statement mainInvocation = proto.getMainInvocation();
            Intrinsics.checkNotNullExpressionValue(mainInvocation, "getMainInvocation(...)");
            jsProgramFragment.setMainFunction(deserialize(mainInvocation));
        }
        List<JsAstProtoBuf.InlinedLocalDeclarations> inlinedLocalDeclarationsList = proto.getInlinedLocalDeclarationsList();
        Intrinsics.checkNotNullExpressionValue(inlinedLocalDeclarationsList, "getInlinedLocalDeclarationsList(...)");
        for (JsAstProtoBuf.InlinedLocalDeclarations inlinedLocalDeclarations : inlinedLocalDeclarationsList) {
            Map<String, JsCompositeBlock> inlinedLocalDeclarations2 = jsProgramFragment.getInlinedLocalDeclarations();
            String strDeserializeString3 = deserializeString(inlinedLocalDeclarations.getTag());
            JsAstProtoBuf.CompositeBlock block = inlinedLocalDeclarations.getBlock();
            Intrinsics.checkNotNullExpressionValue(block, "getBlock(...)");
            inlinedLocalDeclarations2.put(strDeserializeString3, deserializeCompositeBlock(block));
        }
        return jsProgramFragment;
    }

    private final JsClassModel deserialize(JsAstProtoBuf.ClassModel proto) {
        JsClassModel jsClassModel = new JsClassModel(deserializeName(proto.getNameId()), proto.hasSuperNameId() ? deserializeName(proto.getSuperNameId()) : null);
        List<Integer> interfaceNameIdList = proto.getInterfaceNameIdList();
        Intrinsics.checkNotNullExpressionValue(interfaceNameIdList, "getInterfaceNameIdList(...)");
        Set<JsName> interfaces = jsClassModel.getInterfaces();
        for (Integer num : interfaceNameIdList) {
            Intrinsics.checkNotNull(num);
            interfaces.add(deserializeName(num.intValue()));
        }
        if (proto.hasPostDeclarationBlock()) {
            List<JsStatement> statements = jsClassModel.getPostDeclarationBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            JsAstProtoBuf.CompositeBlock postDeclarationBlock = proto.getPostDeclarationBlock();
            Intrinsics.checkNotNullExpressionValue(postDeclarationBlock, "getPostDeclarationBlock(...)");
            List<JsStatement> statements2 = deserializeCompositeBlock(postDeclarationBlock).getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            CollectionsKt.addAll(statements, statements2);
        }
        return jsClassModel;
    }

    @Override // org.jetbrains.kotlin.serialization.js.ast.JsAstDeserializerBase
    protected JsLocationWithSource embedSources(JsLocation deserializedLocation, String file) {
        Object next;
        Intrinsics.checkNotNullParameter(deserializedLocation, "deserializedLocation");
        Intrinsics.checkNotNullParameter(file, "file");
        Iterable<File> iterable = this.sourceRoots;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        Iterator<File> it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(new File(it.next(), file));
        }
        Iterator it2 = arrayList.iterator();
        do {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
        } while (!((File) next).exists());
        final File file2 = (File) next;
        if (file2 != null) {
            return new JsLocationWithEmbeddedSource(deserializedLocation, null, new Function0<Reader>() { // from class: org.jetbrains.kotlin.serialization.js.ast.JsAstDeserializer.embedSources.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Reader invoke() {
                    return new InputStreamReader(new FileInputStream(file2), "UTF-8");
                }
            });
        }
        return null;
    }
}
