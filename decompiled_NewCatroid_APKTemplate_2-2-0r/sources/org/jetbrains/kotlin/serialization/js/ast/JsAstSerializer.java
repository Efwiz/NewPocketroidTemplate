package org.jetbrains.kotlin.serialization.js.ast;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.js.backend.ast.JsClassModel;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsLocation;
import org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameBinding;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;

/* JADX INFO: compiled from: JsAstSerializer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B;\u0012 \u0010\u0002\u001a\u001c\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0003\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\u0002\u0010\fJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0012\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u000e\u0010\u0013\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0004J\u0016\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u001aJ\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u0004H\u0002R(\u0010\u0002\u001a\u001c\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/ast/JsAstSerializer;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstSerializerBase;", "jsAstValidator", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "", "pathResolver", "Lkotlin/Function1;", "Ljava/io/File;", "", "(Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;)V", "extractLocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsLocation;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "node", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "serialize", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$ClassModel;", "classModel", "Lorg/jetbrains/kotlin/js/backend/ast/JsClassModel;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Chunk;", "fragment", "output", "Ljava/io/OutputStream;", "serializeFragment", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Fragment;", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsAstSerializer extends JsAstSerializerBase {
    private final Function2<JsProgramFragment, Set<? extends JsName>, Unit> jsAstValidator;
    private final Function1<File, String> pathResolver;

    /* JADX WARN: Multi-variable type inference failed */
    public JsAstSerializer(Function2<? super JsProgramFragment, ? super Set<? extends JsName>, Unit> function2, Function1<? super File, String> pathResolver) {
        Intrinsics.checkNotNullParameter(pathResolver, "pathResolver");
        this.jsAstValidator = function2;
        this.pathResolver = pathResolver;
    }

    public final void serialize(JsProgramFragment fragment, OutputStream output) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        Intrinsics.checkNotNullParameter(output, "output");
        List<JsNameBinding> nameBindings = fragment.getNameBindings();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (JsNameBinding jsNameBinding : nameBindings) {
            Pair pair = TuplesKt.to(jsNameBinding.getKey(), jsNameBinding.getName());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        getImportedNames().clear();
        Set<JsName> importedNames = getImportedNames();
        Map<String, JsExpression> imports = fragment.getImports();
        ArrayList arrayList = new ArrayList(imports.size());
        Iterator<Map.Entry<String, JsExpression>> it = imports.entrySet().iterator();
        while (it.hasNext()) {
            Object obj = linkedHashMap.get(it.next().getKey());
            Intrinsics.checkNotNull(obj);
            arrayList.add((JsName) obj);
        }
        CollectionsKt.addAll(importedNames, arrayList);
        serialize(fragment).writeTo(output);
    }

    public final JsAstProtoBuf.Chunk serialize(JsProgramFragment fragment) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        try {
            JsAstProtoBuf.Chunk.Builder builderNewBuilder = JsAstProtoBuf.Chunk.newBuilder();
            builderNewBuilder.setFragment(serializeFragment(fragment));
            builderNewBuilder.setNameTable(getNameTableBuilder().build());
            builderNewBuilder.setStringTable(getStringTableBuilder().build());
            JsAstProtoBuf.Chunk chunkBuild = builderNewBuilder.build();
            Intrinsics.checkNotNullExpressionValue(chunkBuild, "build(...)");
            return chunkBuild;
        } finally {
            getNameTableBuilder().clear();
            getStringTableBuilder().clear();
            getNameMap().clear();
            getStringMap().clear();
        }
    }

    private final JsAstProtoBuf.Fragment serializeFragment(JsProgramFragment fragment) {
        JsAstProtoBuf.Fragment.Builder builderNewBuilder = JsAstProtoBuf.Fragment.newBuilder();
        builderNewBuilder.setPackageFqn(fragment.getPackageFqn());
        for (JsImportedModule jsImportedModule : fragment.getImportedModules()) {
            JsAstProtoBuf.ImportedModule.Builder builderNewBuilder2 = JsAstProtoBuf.ImportedModule.newBuilder();
            builderNewBuilder2.setExternalNameId(serialize(jsImportedModule.getExternalName()));
            builderNewBuilder2.setInternalNameId(serialize(jsImportedModule.getInternalName()));
            JsExpression plainReference = jsImportedModule.getPlainReference();
            if (plainReference != null) {
                builderNewBuilder2.setPlainReference(serialize(plainReference));
            }
            builderNewBuilder.addImportedModule(builderNewBuilder2);
        }
        for (Map.Entry<String, JsExpression> entry : fragment.getImports().entrySet()) {
            String key = entry.getKey();
            JsExpression value = entry.getValue();
            JsAstProtoBuf.Import.Builder builderNewBuilder3 = JsAstProtoBuf.Import.newBuilder();
            builderNewBuilder3.setSignatureId(serialize(key));
            builderNewBuilder3.setExpression(serialize(value));
            builderNewBuilder.addImportEntry(builderNewBuilder3);
        }
        builderNewBuilder.setDeclarationBlock(serializeBlock(fragment.getDeclarationBlock()));
        builderNewBuilder.setInitializerBlock(serializeBlock(fragment.getInitializerBlock()));
        builderNewBuilder.setExportBlock(serializeBlock(fragment.getExportBlock()));
        for (JsNameBinding jsNameBinding : fragment.getNameBindings()) {
            JsAstProtoBuf.NameBinding.Builder builderNewBuilder4 = JsAstProtoBuf.NameBinding.newBuilder();
            builderNewBuilder4.setSignatureId(serialize(jsNameBinding.getKey()));
            builderNewBuilder4.setNameId(serialize(jsNameBinding.getName()));
            builderNewBuilder.addNameBinding(builderNewBuilder4);
        }
        Iterator<T> it = fragment.getClasses().values().iterator();
        while (it.hasNext()) {
            builderNewBuilder.addClassModel(serialize((JsClassModel) it.next()));
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<String, JsExpression> entry2 : fragment.getInlineModuleMap().entrySet()) {
            String key2 = entry2.getKey();
            JsExpression value2 = entry2.getValue();
            JsAstProtoBuf.InlineModule.Builder builderNewBuilder5 = JsAstProtoBuf.InlineModule.newBuilder();
            builderNewBuilder5.setSignatureId(serialize(key2));
            Object objValueOf = linkedHashMap.get(value2);
            if (objValueOf == null) {
                int moduleExpressionCount = builderNewBuilder.getModuleExpressionCount();
                builderNewBuilder.addModuleExpression(serialize(value2));
                objValueOf = Integer.valueOf(moduleExpressionCount);
                linkedHashMap.put(value2, objValueOf);
            }
            builderNewBuilder5.setExpressionId(((Number) objValueOf).intValue());
            builderNewBuilder.addInlineModule(builderNewBuilder5);
        }
        JsStatement tests = fragment.getTests();
        if (tests != null) {
            builderNewBuilder.setTestsInvocation(serialize(tests));
        }
        JsStatement mainFunction = fragment.getMainFunction();
        if (mainFunction != null) {
            builderNewBuilder.setMainInvocation(serialize(mainFunction));
        }
        for (Map.Entry<String, JsCompositeBlock> entry3 : fragment.getInlinedLocalDeclarations().entrySet()) {
            String key3 = entry3.getKey();
            JsCompositeBlock value3 = entry3.getValue();
            JsAstProtoBuf.InlinedLocalDeclarations.Builder builderNewBuilder6 = JsAstProtoBuf.InlinedLocalDeclarations.newBuilder();
            builderNewBuilder6.setTag(serialize(key3));
            builderNewBuilder6.setBlock(serializeBlock(value3));
            builderNewBuilder.addInlinedLocalDeclarations(builderNewBuilder6.build());
        }
        Function2<JsProgramFragment, Set<? extends JsName>, Unit> function2 = this.jsAstValidator;
        if (function2 != null) {
            function2.invoke(fragment, getNameMap().keySet());
        }
        JsAstProtoBuf.Fragment fragmentBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(fragmentBuild, "build(...)");
        return fragmentBuild;
    }

    private final JsAstProtoBuf.ClassModel serialize(JsClassModel classModel) {
        JsAstProtoBuf.ClassModel.Builder builderNewBuilder = JsAstProtoBuf.ClassModel.newBuilder();
        builderNewBuilder.setNameId(serialize(classModel.getName()));
        JsName superName = classModel.getSuperName();
        if (superName != null) {
            builderNewBuilder.setSuperNameId(serialize(superName));
        }
        Iterator<T> it = classModel.getInterfaces().iterator();
        while (it.hasNext()) {
            builderNewBuilder.addInterfaceNameId(serialize((JsName) it.next()));
        }
        List<JsStatement> statements = classModel.getPostDeclarationBlock().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        if (!statements.isEmpty()) {
            builderNewBuilder.setPostDeclarationBlock(serializeBlock(classModel.getPostDeclarationBlock()));
        }
        JsAstProtoBuf.ClassModel classModelBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(classModelBuild, "build(...)");
        return classModelBuild;
    }

    @Override // org.jetbrains.kotlin.serialization.js.ast.JsAstSerializerBase
    public JsLocation extractLocation(JsNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        Object source = node.getSource();
        if (source instanceof JsLocationWithSource) {
            return ((JsLocationWithSource) source).asSimpleLocation();
        }
        if (source instanceof PsiElement) {
            Intrinsics.checkNotNull(source);
            PsiElement psiElement = (PsiElement) source;
            if (!CallUtilKt.isFakePsiElement(psiElement)) {
                return extractLocation(psiElement);
            }
        }
        return null;
    }

    private final JsLocation extractLocation(PsiElement element) {
        PsiFile containingFile = element.getContainingFile();
        Document document = containingFile.getViewProvider().getDocument();
        Intrinsics.checkNotNull(document);
        String strInvoke = this.pathResolver.invoke(new File(containingFile.getViewProvider().getVirtualFile().getPath()));
        int startOffset = element.getNode().getStartOffset();
        int lineNumber = document.getLineNumber(startOffset);
        return new JsLocation(strInvoke, lineNumber, startOffset - document.getLineStartOffset(lineNumber), null, 8, null);
    }
}
