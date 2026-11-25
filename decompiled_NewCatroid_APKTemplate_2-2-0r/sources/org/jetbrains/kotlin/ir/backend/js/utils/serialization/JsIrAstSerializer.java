package org.jetbrains.kotlin.ir.backend.js.utils.serialization;

import com.reandroid.apk.PathMap;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.ir.backend.js.export.TypeScriptFragment;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrIcClassModel;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrProgramFragment;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsCase;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsClass;
import org.jetbrains.kotlin.js.backend.ast.JsComment;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDebugger;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsDocComment;
import org.jetbrains.kotlin.js.backend.ast.JsDoubleLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsEmpty;
import org.jetbrains.kotlin.js.backend.ast.JsExport;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFor;
import org.jetbrains.kotlin.js.backend.ast.JsForIn;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsImport;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsLocation;
import org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource;
import org.jetbrains.kotlin.js.backend.ast.JsMultiLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPostfixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsRegExp;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsSingleLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsSuperRef;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsSwitchMember;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsThrow;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitor;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;
import org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata;
import org.jetbrains.kotlin.js.backend.ast.metadata.LocalAlias;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;

/* JADX INFO: compiled from: JsIrAstSerializer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000²\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0013J\u0014\u0010\u0014\u001a\u00020\u00002\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00130\u0016J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\nH\u0002J\u0010\u0010\u0019\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u0005H\u0002J\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ#\u0010\u001f\u001a\u00020\u001c*\u00020\u00072\u0006\u0010 \u001a\u00020!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001c0#H\u0082\bJ#\u0010$\u001a\u00020\u001c*\u00020\u00072\u0006\u0010 \u001a\u00020!2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001c0#H\u0082\bJ\u0014\u0010%\u001a\u00020\u001c*\u00020\u00072\u0006\u0010&\u001a\u00020'H\u0002J\u0014\u0010(\u001a\u00020\u001c*\u00020\u00072\u0006\u0010)\u001a\u00020*H\u0002J\u0014\u0010+\u001a\u00020\u001c*\u00020\u00072\u0006\u0010&\u001a\u00020,H\u0002J\u0014\u0010-\u001a\u00020\u001c*\u00020\u00072\u0006\u0010.\u001a\u00020/H\u0002J\u0014\u00100\u001a\u00020\u001c*\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0014\u00101\u001a\u00020\u001c*\u00020\u00072\u0006\u00102\u001a\u000203H\u0002J\u0014\u00104\u001a\u00020\u001c*\u00020\u00072\u0006\u00105\u001a\u000206H\u0002J\u0014\u00107\u001a\u00020\u001c*\u00020\u00072\u0006\u00108\u001a\u000209H\u0002J\u0014\u0010:\u001a\u00020\u001c*\u00020\u00072\u0006\u0010;\u001a\u00020<H\u0002J\u0014\u0010=\u001a\u00020\u001c*\u00020\u00072\u0006\u0010>\u001a\u00020?H\u0002J\u0014\u0010@\u001a\u00020\u001c*\u00020\u00072\u0006\u0010A\u001a\u00020BH\u0002J\u0014\u0010C\u001a\u00020\u001c*\u00020\u00072\u0006\u0010D\u001a\u00020EH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/serialization/JsIrAstSerializer;", "", "()V", "fileStack", "Ljava/util/Deque;", "", "fragmentSerializer", "Lorg/jetbrains/kotlin/ir/backend/js/utils/serialization/DataWriter;", "importedNames", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "nameMap", "", "", "nameSerializer", "stringMap", "stringSerializer", "append", "fragment", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "appendAll", "fragments", "", "internalizeName", "name", "internalizeString", "string", "saveTo", "", "rawOutput", "Ljava/io/OutputStream;", "withComments", "node", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "inner", "Lkotlin/Function0;", "withLocation", "writeBlock", "block", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "writeComment", "comment", "Lorg/jetbrains/kotlin/js/backend/ast/JsComment;", "writeCompositeBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "writeExpression", "expression", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "writeFragment", "writeFunction", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "writeImportedModule", "module", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "writeIrIcModel", "classModel", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrIcClassModel;", "writeLocalAlias", PathMap.NAME_alias, "Lorg/jetbrains/kotlin/js/backend/ast/metadata/LocalAlias;", "writeParameter", "parameter", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "writeStatement", "statement", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "writeVars", "vars", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JsIrAstSerializer {
    private final DataWriter fragmentSerializer = new DataWriter();
    private final DataWriter nameSerializer = new DataWriter();
    private final DataWriter stringSerializer = new DataWriter();
    private final Map<JsName, Integer> nameMap = new LinkedHashMap();
    private final Map<String, Integer> stringMap = new LinkedHashMap();
    private final Deque<String> fileStack = new ArrayDeque();
    private final Set<JsName> importedNames = new LinkedHashSet();

    public final JsIrAstSerializer appendAll(List<JsIrProgramFragment> fragments) throws IOException {
        Intrinsics.checkNotNullParameter(fragments, "fragments");
        this.fragmentSerializer.writeInt(fragments.size());
        Iterator<T> it = fragments.iterator();
        while (it.hasNext()) {
            append((JsIrProgramFragment) it.next());
        }
        return this;
    }

    public final JsIrAstSerializer append(JsIrProgramFragment fragment) throws IOException {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        Set<JsName> set = this.importedNames;
        Map<String, JsStatement> imports = fragment.getImports();
        ArrayList arrayList = new ArrayList(imports.size());
        for (Map.Entry<String, JsStatement> entry : imports.entrySet()) {
            JsName jsName = fragment.getNameBindings().get(entry.getKey());
            if (jsName == null) {
                throw new IllegalStateException(("No binding for tag " + entry.getKey()).toString());
            }
            arrayList.add(jsName);
        }
        CollectionsKt.addAll(set, arrayList);
        writeFragment(this.fragmentSerializer, fragment);
        return this;
    }

    public final void saveTo(OutputStream rawOutput) {
        Intrinsics.checkNotNullParameter(rawOutput, "rawOutput");
        DataOutputStream dataOutputStream = new DataOutputStream(rawOutput);
        try {
            DataOutputStream dataOutputStream2 = dataOutputStream;
            dataOutputStream2.writeInt(this.stringMap.size());
            this.stringSerializer.saveTo(dataOutputStream2);
            dataOutputStream2.writeInt(this.nameMap.size());
            this.nameSerializer.saveTo(dataOutputStream2);
            this.fragmentSerializer.saveTo(dataOutputStream2);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(dataOutputStream, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(dataOutputStream, th);
                throw th2;
            }
        }
    }

    private final void writeFragment(DataWriter dataWriter, JsIrProgramFragment jsIrProgramFragment) throws IOException {
        dataWriter.writeString(jsIrProgramFragment.getName());
        dataWriter.writeString(jsIrProgramFragment.getPackageFqn());
        List<JsImportedModule> importedModules = jsIrProgramFragment.getImportedModules();
        dataWriter.output.writeInt(importedModules.size());
        Iterator<T> it = importedModules.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            JsImportedModule jsImportedModule = (JsImportedModule) it.next();
            dataWriter.writeInt(internalizeString(jsImportedModule.getExternalName()));
            dataWriter.writeInt(internalizeName(jsImportedModule.getInternalName()));
            JsExpression plainReference = jsImportedModule.getPlainReference();
            dataWriter.output.writeBoolean(plainReference != null);
            if (plainReference != null) {
                writeExpression(dataWriter, plainReference);
            }
        }
        Set<Map.Entry<String, JsStatement>> setEntrySet = jsIrProgramFragment.getImports().entrySet();
        dataWriter.output.writeInt(setEntrySet.size());
        Iterator<T> it2 = setEntrySet.iterator();
        while (it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it2.next();
            String str = (String) entry.getKey();
            JsStatement jsStatement = (JsStatement) entry.getValue();
            dataWriter.writeInt(internalizeString(str));
            writeStatement(dataWriter, jsStatement);
        }
        writeCompositeBlock(dataWriter, jsIrProgramFragment.getDeclarations());
        writeCompositeBlock(dataWriter, jsIrProgramFragment.getInitializers());
        writeCompositeBlock(dataWriter, jsIrProgramFragment.getExports());
        writeCompositeBlock(dataWriter, jsIrProgramFragment.getPolyfills());
        Set<Map.Entry<String, JsName>> setEntrySet2 = jsIrProgramFragment.getNameBindings().entrySet();
        dataWriter.output.writeInt(setEntrySet2.size());
        Iterator<T> it3 = setEntrySet2.iterator();
        while (it3.hasNext()) {
            Map.Entry entry2 = (Map.Entry) it3.next();
            String str2 = (String) entry2.getKey();
            JsName jsName = (JsName) entry2.getValue();
            dataWriter.writeInt(internalizeString(str2));
            dataWriter.writeInt(internalizeName(jsName));
        }
        HashSet<String> optionalCrossModuleImports = jsIrProgramFragment.getOptionalCrossModuleImports();
        dataWriter.output.writeInt(optionalCrossModuleImports.size());
        Iterator<T> it4 = optionalCrossModuleImports.iterator();
        while (it4.hasNext()) {
            dataWriter.writeInt(internalizeString((String) it4.next()));
        }
        Set<Map.Entry<JsName, JsIrIcClassModel>> setEntrySet3 = jsIrProgramFragment.getClasses().entrySet();
        dataWriter.output.writeInt(setEntrySet3.size());
        Iterator<T> it5 = setEntrySet3.iterator();
        while (it5.hasNext()) {
            Map.Entry entry3 = (Map.Entry) it5.next();
            JsName jsName2 = (JsName) entry3.getKey();
            JsIrIcClassModel jsIrIcClassModel = (JsIrIcClassModel) entry3.getValue();
            dataWriter.writeInt(internalizeName(jsName2));
            writeIrIcModel(dataWriter, jsIrIcClassModel);
        }
        JsStatement testFunInvocation = jsIrProgramFragment.getTestFunInvocation();
        dataWriter.output.writeBoolean(testFunInvocation != null);
        if (testFunInvocation != null) {
            writeStatement(dataWriter, testFunInvocation);
        }
        JsStatement mainFunction = jsIrProgramFragment.getMainFunction();
        dataWriter.output.writeBoolean(mainFunction != null);
        if (mainFunction != null) {
            writeStatement(dataWriter, mainFunction);
        }
        String dts = jsIrProgramFragment.getDts();
        TypeScriptFragment typeScriptFragmentM7089boximpl = dts != null ? TypeScriptFragment.m7089boximpl(dts) : null;
        dataWriter.output.writeBoolean(typeScriptFragmentM7089boximpl != null);
        if (typeScriptFragmentM7089boximpl != null) {
            dataWriter.writeString(typeScriptFragmentM7089boximpl.m7095unboximpl());
        }
        JsName suiteFn = jsIrProgramFragment.getSuiteFn();
        dataWriter.output.writeBoolean(suiteFn != null);
        if (suiteFn != null) {
            dataWriter.writeInt(internalizeName(suiteFn));
        }
        Set<String> definitions = jsIrProgramFragment.getDefinitions();
        dataWriter.output.writeInt(definitions.size());
        Iterator<T> it6 = definitions.iterator();
        while (it6.hasNext()) {
            dataWriter.writeInt(internalizeString((String) it6.next()));
        }
    }

    private final void writeIrIcModel(DataWriter dataWriter, JsIrIcClassModel jsIrIcClassModel) throws IOException {
        List<JsName> superClasses = jsIrIcClassModel.getSuperClasses();
        dataWriter.output.writeInt(superClasses.size());
        Iterator<T> it = superClasses.iterator();
        while (it.hasNext()) {
            dataWriter.writeInt(internalizeName((JsName) it.next()));
        }
        writeCompositeBlock(dataWriter, jsIrIcClassModel.getPreDeclarationBlock());
        writeCompositeBlock(dataWriter, jsIrIcClassModel.getPostDeclarationBlock());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeStatement(final DataWriter dataWriter, final JsStatement jsStatement) throws IOException {
        JsVisitor jsVisitor = new JsVisitor() { // from class: org.jetbrains.kotlin.ir.backend.js.utils.serialization.JsIrAstSerializer$writeStatement$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitReturn(JsReturn x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(0);
                DataWriter dataWriter2 = dataWriter;
                JsExpression expression = x.getExpression();
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter3 = dataWriter;
                dataWriter2.output.writeBoolean(expression != null);
                if (expression != null) {
                    jsIrAstSerializer.writeExpression(dataWriter3, expression);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitThrow(JsThrow x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(1);
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter2 = dataWriter;
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                jsIrAstSerializer.writeExpression(dataWriter2, expression);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBreak(JsBreak x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(2);
                DataWriter dataWriter2 = dataWriter;
                JsNameRef label = x.getLabel();
                DataWriter dataWriter3 = dataWriter;
                JsIrAstSerializer jsIrAstSerializer = this;
                dataWriter2.output.writeBoolean(label != null);
                if (label != null) {
                    JsName name2 = label.getName();
                    Intrinsics.checkNotNull(name2);
                    dataWriter3.writeInt(jsIrAstSerializer.internalizeName(name2));
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitContinue(JsContinue x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(3);
                DataWriter dataWriter2 = dataWriter;
                JsNameRef label = x.getLabel();
                DataWriter dataWriter3 = dataWriter;
                JsIrAstSerializer jsIrAstSerializer = this;
                dataWriter2.output.writeBoolean(label != null);
                if (label != null) {
                    JsName name2 = label.getName();
                    Intrinsics.checkNotNull(name2);
                    dataWriter3.writeInt(jsIrAstSerializer.internalizeName(name2));
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitDebugger(JsDebugger x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(4);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitExpressionStatement(JsExpressionStatement x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(5);
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter2 = dataWriter;
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                jsIrAstSerializer.writeExpression(dataWriter2, expression);
                DataWriter dataWriter3 = dataWriter;
                String exportedTag = MetadataProperties.getExportedTag(x);
                DataWriter dataWriter4 = dataWriter;
                JsIrAstSerializer jsIrAstSerializer2 = this;
                dataWriter3.output.writeBoolean(exportedTag != null);
                if (exportedTag != null) {
                    dataWriter4.writeInt(jsIrAstSerializer2.internalizeString(exportedTag));
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitVars(JsVars x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(6);
                this.writeVars(dataWriter, x);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBlock(JsBlock x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                if (x instanceof JsCompositeBlock) {
                    dataWriter.writeByte(8);
                    this.writeCompositeBlock(dataWriter, (JsCompositeBlock) x);
                    return;
                }
                dataWriter.writeByte(7);
                DataWriter dataWriter2 = dataWriter;
                List<JsStatement> statements = x.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                List<JsStatement> list = statements;
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter3 = dataWriter;
                dataWriter2.output.writeInt(list.size());
                for (JsStatement jsStatement2 : list) {
                    Intrinsics.checkNotNull(jsStatement2);
                    jsIrAstSerializer.writeStatement(dataWriter3, jsStatement2);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitLabel(JsLabel x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(9);
                DataWriter dataWriter2 = dataWriter;
                JsIrAstSerializer jsIrAstSerializer = this;
                JsName name2 = x.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                dataWriter2.writeInt(jsIrAstSerializer.internalizeName(name2));
                JsIrAstSerializer jsIrAstSerializer2 = this;
                DataWriter dataWriter3 = dataWriter;
                JsStatement statement = x.getStatement();
                Intrinsics.checkNotNullExpressionValue(statement, "getStatement(...)");
                jsIrAstSerializer2.writeStatement(dataWriter3, statement);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitIf(JsIf x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(10);
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter2 = dataWriter;
                JsExpression ifExpression = x.getIfExpression();
                Intrinsics.checkNotNullExpressionValue(ifExpression, "getIfExpression(...)");
                jsIrAstSerializer.writeExpression(dataWriter2, ifExpression);
                JsIrAstSerializer jsIrAstSerializer2 = this;
                DataWriter dataWriter3 = dataWriter;
                JsStatement thenStatement = x.getThenStatement();
                Intrinsics.checkNotNullExpressionValue(thenStatement, "getThenStatement(...)");
                jsIrAstSerializer2.writeStatement(dataWriter3, thenStatement);
                DataWriter dataWriter4 = dataWriter;
                JsStatement elseStatement = x.getElseStatement();
                JsIrAstSerializer jsIrAstSerializer3 = this;
                DataWriter dataWriter5 = dataWriter;
                dataWriter4.output.writeBoolean(elseStatement != null);
                if (elseStatement != null) {
                    jsIrAstSerializer3.writeStatement(dataWriter5, elseStatement);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visit(JsSwitch x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(11);
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter2 = dataWriter;
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                jsIrAstSerializer.writeExpression(dataWriter2, expression);
                DataWriter dataWriter3 = dataWriter;
                List<JsSwitchMember> cases = x.getCases();
                Intrinsics.checkNotNullExpressionValue(cases, "getCases(...)");
                List<JsSwitchMember> list = cases;
                JsIrAstSerializer jsIrAstSerializer2 = this;
                DataWriter dataWriter4 = dataWriter;
                dataWriter3.output.writeInt(list.size());
                for (JsSwitchMember jsSwitchMember : list) {
                    Intrinsics.checkNotNull(jsSwitchMember);
                    Object source = jsSwitchMember.getSource();
                    if (!(source instanceof JsLocationWithSource)) {
                        source = null;
                    }
                    JsLocationWithSource jsLocationWithSource = (JsLocationWithSource) source;
                    JsLocation jsLocationAsSimpleLocation = jsLocationWithSource != null ? jsLocationWithSource.asSimpleLocation() : null;
                    dataWriter4.output.writeBoolean(jsLocationAsSimpleLocation != null);
                    if (jsLocationAsSimpleLocation != null) {
                        String str = (String) jsIrAstSerializer2.fileStack.peek();
                        String file = jsLocationAsSimpleLocation.getFile();
                        boolean zAreEqual = Intrinsics.areEqual(str, file);
                        dataWriter4.output.writeBoolean(!zAreEqual);
                        if (!zAreEqual) {
                            dataWriter4.writeInt(jsIrAstSerializer2.internalizeString(file));
                            jsIrAstSerializer2.fileStack.push(jsLocationAsSimpleLocation.getFile());
                        }
                        dataWriter4.writeInt(jsLocationAsSimpleLocation.getStartLine());
                        dataWriter4.writeInt(jsLocationAsSimpleLocation.getStartChar());
                        JsCase jsCase = jsSwitchMember instanceof JsCase ? (JsCase) jsSwitchMember : null;
                        dataWriter4.output.writeBoolean(jsCase != null);
                        if (jsCase != null) {
                            JsExpression caseExpression = jsCase.getCaseExpression();
                            Intrinsics.checkNotNullExpressionValue(caseExpression, "getCaseExpression(...)");
                            jsIrAstSerializer2.writeExpression(dataWriter4, caseExpression);
                        }
                        if (!zAreEqual) {
                            jsIrAstSerializer2.fileStack.pop();
                        }
                    }
                    if (jsLocationAsSimpleLocation == null) {
                        JsCase jsCase2 = jsSwitchMember instanceof JsCase ? (JsCase) jsSwitchMember : null;
                        dataWriter4.output.writeBoolean(jsCase2 != null);
                        if (jsCase2 != null) {
                            JsExpression caseExpression2 = jsCase2.getCaseExpression();
                            Intrinsics.checkNotNullExpressionValue(caseExpression2, "getCaseExpression(...)");
                            jsIrAstSerializer2.writeExpression(dataWriter4, caseExpression2);
                        }
                        Unit unit = Unit.INSTANCE;
                    }
                    List<JsStatement> statements = jsSwitchMember.getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                    List<JsStatement> list2 = statements;
                    dataWriter4.output.writeInt(list2.size());
                    for (JsStatement jsStatement2 : list2) {
                        Intrinsics.checkNotNull(jsStatement2);
                        jsIrAstSerializer2.writeStatement(dataWriter4, jsStatement2);
                    }
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitWhile(JsWhile x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(12);
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter2 = dataWriter;
                JsExpression condition = x.getCondition();
                Intrinsics.checkNotNullExpressionValue(condition, "getCondition(...)");
                jsIrAstSerializer.writeExpression(dataWriter2, condition);
                JsIrAstSerializer jsIrAstSerializer2 = this;
                DataWriter dataWriter3 = dataWriter;
                JsStatement body = x.getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                jsIrAstSerializer2.writeStatement(dataWriter3, body);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitDoWhile(JsDoWhile x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(13);
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter2 = dataWriter;
                JsExpression condition = x.getCondition();
                Intrinsics.checkNotNullExpressionValue(condition, "getCondition(...)");
                jsIrAstSerializer.writeExpression(dataWriter2, condition);
                JsIrAstSerializer jsIrAstSerializer2 = this;
                DataWriter dataWriter3 = dataWriter;
                JsStatement body = x.getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                jsIrAstSerializer2.writeStatement(dataWriter3, body);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFor(JsFor x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(14);
                DataWriter dataWriter2 = dataWriter;
                JsExpression condition = x.getCondition();
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter3 = dataWriter;
                dataWriter2.output.writeBoolean(condition != null);
                if (condition != null) {
                    jsIrAstSerializer.writeExpression(dataWriter3, condition);
                }
                DataWriter dataWriter4 = dataWriter;
                JsExpression incrementExpression = x.getIncrementExpression();
                JsIrAstSerializer jsIrAstSerializer2 = this;
                DataWriter dataWriter5 = dataWriter;
                dataWriter4.output.writeBoolean(incrementExpression != null);
                if (incrementExpression != null) {
                    jsIrAstSerializer2.writeExpression(dataWriter5, incrementExpression);
                }
                DataWriter dataWriter6 = dataWriter;
                JsStatement body = x.getBody();
                JsIrAstSerializer jsIrAstSerializer3 = this;
                DataWriter dataWriter7 = dataWriter;
                dataWriter6.output.writeBoolean(body != null);
                if (body != null) {
                    jsIrAstSerializer3.writeStatement(dataWriter7, body);
                }
                DataWriter dataWriter8 = dataWriter;
                JsVars initVars = x.getInitVars();
                JsIrAstSerializer jsIrAstSerializer4 = this;
                DataWriter dataWriter9 = dataWriter;
                dataWriter8.output.writeBoolean(initVars != null);
                if (initVars != null) {
                    jsIrAstSerializer4.writeVars(dataWriter9, initVars);
                }
                if (initVars != null) {
                    return;
                }
                DataWriter dataWriter10 = dataWriter;
                JsExpression initExpression = x.getInitExpression();
                JsIrAstSerializer jsIrAstSerializer5 = this;
                DataWriter dataWriter11 = dataWriter;
                dataWriter10.output.writeBoolean(initExpression != null);
                if (initExpression != null) {
                    jsIrAstSerializer5.writeExpression(dataWriter11, initExpression);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitForIn(JsForIn x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(15);
                DataWriter dataWriter2 = dataWriter;
                JsName iterVarName = x.getIterVarName();
                DataWriter dataWriter3 = dataWriter;
                JsIrAstSerializer jsIrAstSerializer = this;
                dataWriter2.output.writeBoolean(iterVarName != null);
                if (iterVarName != null) {
                    dataWriter3.writeInt(jsIrAstSerializer.internalizeName(iterVarName));
                }
                DataWriter dataWriter4 = dataWriter;
                JsExpression iterExpression = x.getIterExpression();
                JsIrAstSerializer jsIrAstSerializer2 = this;
                DataWriter dataWriter5 = dataWriter;
                dataWriter4.output.writeBoolean(iterExpression != null);
                if (iterExpression != null) {
                    jsIrAstSerializer2.writeExpression(dataWriter5, iterExpression);
                }
                JsIrAstSerializer jsIrAstSerializer3 = this;
                DataWriter dataWriter6 = dataWriter;
                JsExpression objectExpression = x.getObjectExpression();
                Intrinsics.checkNotNullExpressionValue(objectExpression, "getObjectExpression(...)");
                jsIrAstSerializer3.writeExpression(dataWriter6, objectExpression);
                JsIrAstSerializer jsIrAstSerializer4 = this;
                DataWriter dataWriter7 = dataWriter;
                JsStatement body = x.getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                jsIrAstSerializer4.writeStatement(dataWriter7, body);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitTry(JsTry x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(16);
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter2 = dataWriter;
                JsBlock tryBlock = x.getTryBlock();
                Intrinsics.checkNotNullExpressionValue(tryBlock, "getTryBlock(...)");
                jsIrAstSerializer.writeBlock(dataWriter2, tryBlock);
                DataWriter dataWriter3 = dataWriter;
                List<JsCatch> catches = x.getCatches();
                Intrinsics.checkNotNullExpressionValue(catches, "getCatches(...)");
                List<JsCatch> list = catches;
                DataWriter dataWriter4 = dataWriter;
                JsIrAstSerializer jsIrAstSerializer2 = this;
                dataWriter3.output.writeInt(list.size());
                for (JsCatch jsCatch : list) {
                    JsName name2 = jsCatch.getParameter().getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    dataWriter4.writeInt(jsIrAstSerializer2.internalizeName(name2));
                    JsBlock body = jsCatch.getBody();
                    Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                    jsIrAstSerializer2.writeBlock(dataWriter4, body);
                }
                DataWriter dataWriter5 = dataWriter;
                JsBlock finallyBlock = x.getFinallyBlock();
                JsIrAstSerializer jsIrAstSerializer3 = this;
                DataWriter dataWriter6 = dataWriter;
                dataWriter5.output.writeBoolean(finallyBlock != null);
                if (finallyBlock != null) {
                    jsIrAstSerializer3.writeBlock(dataWriter6, finallyBlock);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitExport(JsExport export) throws IOException {
                Intrinsics.checkNotNullParameter(export, "export");
                dataWriter.writeByte(21);
                JsExport.Subject subject = export.getSubject();
                if (subject instanceof JsExport.Subject.All) {
                    dataWriter.writeByte(0);
                } else if (subject instanceof JsExport.Subject.Elements) {
                    dataWriter.writeByte(1);
                    DataWriter dataWriter2 = dataWriter;
                    List<JsExport.Element> elements = ((JsExport.Subject.Elements) subject).getElements();
                    DataWriter dataWriter3 = dataWriter;
                    JsIrAstSerializer jsIrAstSerializer = this;
                    dataWriter2.output.writeInt(elements.size());
                    for (JsExport.Element element : elements) {
                        JsName name2 = element.getName().getName();
                        Intrinsics.checkNotNull(name2);
                        dataWriter3.writeInt(jsIrAstSerializer.internalizeName(name2));
                        JsName alias = element.getAlias();
                        dataWriter3.output.writeBoolean(alias != null);
                        if (alias != null) {
                            dataWriter3.writeInt(jsIrAstSerializer.internalizeName(alias));
                        }
                    }
                }
                DataWriter dataWriter4 = dataWriter;
                String fromModule = export.getFromModule();
                DataWriter dataWriter5 = dataWriter;
                dataWriter4.output.writeBoolean(fromModule != null);
                if (fromModule != null) {
                    dataWriter5.writeString(fromModule);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitImport(JsImport jsImport) throws IOException {
                Intrinsics.checkNotNullParameter(jsImport, "import");
                dataWriter.writeByte(20);
                dataWriter.writeString(jsImport.getModule());
                JsImport.Target target = jsImport.getTarget();
                if (target instanceof JsImport.Target.All) {
                    dataWriter.writeByte(0);
                    DataWriter dataWriter2 = dataWriter;
                    JsIrAstSerializer jsIrAstSerializer = this;
                    JsName name2 = ((JsImport.Target.All) target).getAlias().getName();
                    Intrinsics.checkNotNull(name2);
                    dataWriter2.writeInt(jsIrAstSerializer.internalizeName(name2));
                    return;
                }
                if (target instanceof JsImport.Target.Default) {
                    dataWriter.writeByte(2);
                    DataWriter dataWriter3 = dataWriter;
                    JsIrAstSerializer jsIrAstSerializer2 = this;
                    JsName name3 = ((JsImport.Target.Default) target).getName().getName();
                    Intrinsics.checkNotNull(name3);
                    dataWriter3.writeInt(jsIrAstSerializer2.internalizeName(name3));
                    return;
                }
                if (target instanceof JsImport.Target.Elements) {
                    dataWriter.writeByte(1);
                    DataWriter dataWriter4 = dataWriter;
                    List<JsImport.Element> elements = ((JsImport.Target.Elements) target).getElements();
                    DataWriter dataWriter5 = dataWriter;
                    JsIrAstSerializer jsIrAstSerializer3 = this;
                    dataWriter4.output.writeInt(elements.size());
                    for (JsImport.Element element : elements) {
                        dataWriter5.writeInt(jsIrAstSerializer3.internalizeName(element.getName()));
                        JsNameRef alias = element.getAlias();
                        dataWriter5.output.writeBoolean(alias != null);
                        if (alias != null) {
                            JsName name4 = alias.getName();
                            Intrinsics.checkNotNull(name4);
                            dataWriter5.writeInt(jsIrAstSerializer3.internalizeName(name4));
                        }
                    }
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitEmpty(JsEmpty x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(17);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitSingleLineComment(JsSingleLineComment comment) throws IOException {
                Intrinsics.checkNotNullParameter(comment, "comment");
                dataWriter.writeByte(18);
                dataWriter.writeString(comment.getText());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitMultiLineComment(JsMultiLineComment comment) throws IOException {
                Intrinsics.checkNotNullParameter(comment, "comment");
                dataWriter.writeByte(19);
                dataWriter.writeString(comment.getText());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            protected void visitElement(JsNode node) {
                Intrinsics.checkNotNullParameter(node, "node");
                throw new IllegalStateException(("Unknown statement type: " + Reflection.getOrCreateKotlinClass(jsStatement.getClass()).getQualifiedName()).toString());
            }
        };
        JsStatement jsStatement2 = jsStatement;
        Object source = jsStatement2.getSource();
        if (!(source instanceof JsLocationWithSource)) {
            source = null;
        }
        JsLocationWithSource jsLocationWithSource = (JsLocationWithSource) source;
        JsLocation jsLocationAsSimpleLocation = jsLocationWithSource != null ? jsLocationWithSource.asSimpleLocation() : null;
        dataWriter.output.writeBoolean(jsLocationAsSimpleLocation != null);
        if (jsLocationAsSimpleLocation != null) {
            String str = (String) this.fileStack.peek();
            String file = jsLocationAsSimpleLocation.getFile();
            boolean zAreEqual = Intrinsics.areEqual(str, file);
            dataWriter.output.writeBoolean(!zAreEqual);
            if (!zAreEqual) {
                dataWriter.writeInt(internalizeString(file));
                this.fileStack.push(jsLocationAsSimpleLocation.getFile());
            }
            dataWriter.writeInt(jsLocationAsSimpleLocation.getStartLine());
            dataWriter.writeInt(jsLocationAsSimpleLocation.getStartChar());
            jsStatement.accept(jsVisitor);
            if (!zAreEqual) {
                this.fileStack.pop();
            }
        }
        if (jsLocationAsSimpleLocation == null) {
            jsStatement.accept(jsVisitor);
            Unit unit = Unit.INSTANCE;
        }
        List<JsComment> commentsBeforeNode = jsStatement2.getCommentsBeforeNode();
        dataWriter.output.writeBoolean(commentsBeforeNode != null);
        if (commentsBeforeNode != null) {
            List<JsComment> list = commentsBeforeNode;
            dataWriter.output.writeInt(list.size());
            for (JsComment jsComment : list) {
                Intrinsics.checkNotNull(jsComment);
                writeComment(dataWriter, jsComment);
            }
        }
        List<JsComment> commentsAfterNode = jsStatement2.getCommentsAfterNode();
        dataWriter.output.writeBoolean(commentsAfterNode != null);
        if (commentsAfterNode != null) {
            List<JsComment> list2 = commentsAfterNode;
            dataWriter.output.writeInt(list2.size());
            for (JsComment jsComment2 : list2) {
                Intrinsics.checkNotNull(jsComment2);
                writeComment(dataWriter, jsComment2);
            }
        }
        Intrinsics.checkNotNull(jsStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata");
        dataWriter.writeBoolean(MetadataProperties.getSynthetic((HasMetadata) jsStatement));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeExpression(final DataWriter dataWriter, JsExpression jsExpression) throws IOException {
        JsVisitor jsVisitor = new JsVisitor() { // from class: org.jetbrains.kotlin.ir.backend.js.utils.serialization.JsIrAstSerializer$writeExpression$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitThis(JsThisRef x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(0);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitSuper(JsSuperRef x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(23);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitNull(JsNullLiteral x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(1);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBoolean(JsBooleanLiteral x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                if (x.getValue()) {
                    dataWriter.writeByte(2);
                } else {
                    dataWriter.writeByte(3);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitString(JsStringLiteral x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(4);
                DataWriter dataWriter2 = dataWriter;
                JsIrAstSerializer jsIrAstSerializer = this;
                String value = x.getValue();
                Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
                dataWriter2.writeInt(jsIrAstSerializer.internalizeString(value));
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitRegExp(JsRegExp x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(5);
                DataWriter dataWriter2 = dataWriter;
                JsIrAstSerializer jsIrAstSerializer = this;
                String pattern = x.getPattern();
                Intrinsics.checkNotNullExpressionValue(pattern, "getPattern(...)");
                dataWriter2.writeInt(jsIrAstSerializer.internalizeString(pattern));
                DataWriter dataWriter3 = dataWriter;
                String flags = x.getFlags();
                DataWriter dataWriter4 = dataWriter;
                JsIrAstSerializer jsIrAstSerializer2 = this;
                dataWriter3.output.writeBoolean(flags != null);
                if (flags != null) {
                    dataWriter4.writeInt(jsIrAstSerializer2.internalizeString(flags));
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitInt(JsIntLiteral x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(6);
                dataWriter.writeInt(x.value);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitDouble(JsDoubleLiteral x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(7);
                dataWriter.writeDouble(x.value);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitArray(JsArrayLiteral x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(8);
                DataWriter dataWriter2 = dataWriter;
                List<JsExpression> expressions = x.getExpressions();
                Intrinsics.checkNotNullExpressionValue(expressions, "getExpressions(...)");
                List<JsExpression> list = expressions;
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter3 = dataWriter;
                dataWriter2.output.writeInt(list.size());
                for (JsExpression jsExpression2 : list) {
                    Intrinsics.checkNotNull(jsExpression2);
                    jsIrAstSerializer.writeExpression(dataWriter3, jsExpression2);
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitObjectLiteral(JsObjectLiteral x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(9);
                DataWriter dataWriter2 = dataWriter;
                List<JsPropertyInitializer> propertyInitializers = x.getPropertyInitializers();
                Intrinsics.checkNotNullExpressionValue(propertyInitializers, "getPropertyInitializers(...)");
                List<JsPropertyInitializer> list = propertyInitializers;
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter3 = dataWriter;
                dataWriter2.output.writeInt(list.size());
                for (JsPropertyInitializer jsPropertyInitializer : list) {
                    JsExpression labelExpr = jsPropertyInitializer.getLabelExpr();
                    Intrinsics.checkNotNullExpressionValue(labelExpr, "getLabelExpr(...)");
                    jsIrAstSerializer.writeExpression(dataWriter3, labelExpr);
                    JsExpression valueExpr = jsPropertyInitializer.getValueExpr();
                    Intrinsics.checkNotNullExpressionValue(valueExpr, "getValueExpr(...)");
                    jsIrAstSerializer.writeExpression(dataWriter3, valueExpr);
                }
                dataWriter.writeBoolean(x.isMultiline());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFunction(JsFunction x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(10);
                this.writeFunction(dataWriter, x);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitClass(JsClass x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(22);
                DataWriter dataWriter2 = dataWriter;
                JsName name2 = x.getName();
                DataWriter dataWriter3 = dataWriter;
                JsIrAstSerializer jsIrAstSerializer = this;
                dataWriter2.output.writeBoolean(name2 != null);
                if (name2 != null) {
                    dataWriter3.writeInt(jsIrAstSerializer.internalizeName(name2));
                }
                DataWriter dataWriter4 = dataWriter;
                JsExpression baseClass = x.getBaseClass();
                JsIrAstSerializer jsIrAstSerializer2 = this;
                DataWriter dataWriter5 = dataWriter;
                dataWriter4.output.writeBoolean(baseClass != null);
                if (baseClass != null) {
                    jsIrAstSerializer2.writeExpression(dataWriter5, baseClass);
                }
                DataWriter dataWriter6 = dataWriter;
                JsFunction constructor = x.getConstructor();
                JsIrAstSerializer jsIrAstSerializer3 = this;
                DataWriter dataWriter7 = dataWriter;
                dataWriter6.output.writeBoolean(constructor != null);
                if (constructor != null) {
                    jsIrAstSerializer3.writeFunction(dataWriter7, constructor);
                }
                DataWriter dataWriter8 = dataWriter;
                List<JsFunction> members = x.getMembers();
                JsIrAstSerializer jsIrAstSerializer4 = this;
                DataWriter dataWriter9 = dataWriter;
                dataWriter8.output.writeInt(members.size());
                Iterator<T> it = members.iterator();
                while (it.hasNext()) {
                    jsIrAstSerializer4.writeFunction(dataWriter9, (JsFunction) it.next());
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitDocComment(JsDocComment comment) throws IOException {
                Intrinsics.checkNotNullParameter(comment, "comment");
                dataWriter.writeByte(11);
                DataWriter dataWriter2 = dataWriter;
                Set<Map.Entry<String, Object>> setEntrySet = comment.getTags().entrySet();
                DataWriter dataWriter3 = dataWriter;
                JsIrAstSerializer jsIrAstSerializer = this;
                dataWriter2.output.writeInt(setEntrySet.size());
                Iterator<T> it = setEntrySet.iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String str = (String) entry.getKey();
                    Object value = entry.getValue();
                    Intrinsics.checkNotNull(str);
                    dataWriter3.writeInt(jsIrAstSerializer.internalizeString(str));
                    JsNameRef jsNameRef = value instanceof JsNameRef ? (JsNameRef) value : null;
                    dataWriter3.output.writeBoolean(jsNameRef != null);
                    if (jsNameRef != null) {
                        jsIrAstSerializer.writeExpression(dataWriter3, jsNameRef);
                    }
                    if (jsNameRef == null) {
                        if (value instanceof String) {
                            dataWriter3.writeInt(jsIrAstSerializer.internalizeString((String) value));
                            Unit unit = Unit.INSTANCE;
                        } else {
                            throw new IllegalStateException(("Unsupported tag: " + Reflection.getOrCreateKotlinClass(value.getClass()).getQualifiedName()).toString());
                        }
                    }
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBinaryExpression(JsBinaryOperation x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(12);
                dataWriter.writeByte(x.getOperator().ordinal());
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter2 = dataWriter;
                JsExpression arg1 = x.getArg1();
                Intrinsics.checkNotNullExpressionValue(arg1, "getArg1(...)");
                jsIrAstSerializer.writeExpression(dataWriter2, arg1);
                JsIrAstSerializer jsIrAstSerializer2 = this;
                DataWriter dataWriter3 = dataWriter;
                JsExpression arg2 = x.getArg2();
                Intrinsics.checkNotNullExpressionValue(arg2, "getArg2(...)");
                jsIrAstSerializer2.writeExpression(dataWriter3, arg2);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitPrefixOperation(JsPrefixOperation x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(13);
                dataWriter.writeByte(x.getOperator().ordinal());
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter2 = dataWriter;
                JsExpression arg = x.getArg();
                Intrinsics.checkNotNullExpressionValue(arg, "getArg(...)");
                jsIrAstSerializer.writeExpression(dataWriter2, arg);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitPostfixOperation(JsPostfixOperation x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(14);
                dataWriter.writeByte(x.getOperator().ordinal());
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter2 = dataWriter;
                JsExpression arg = x.getArg();
                Intrinsics.checkNotNullExpressionValue(arg, "getArg(...)");
                jsIrAstSerializer.writeExpression(dataWriter2, arg);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitConditional(JsConditional x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(15);
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter2 = dataWriter;
                JsExpression testExpression = x.getTestExpression();
                Intrinsics.checkNotNullExpressionValue(testExpression, "getTestExpression(...)");
                jsIrAstSerializer.writeExpression(dataWriter2, testExpression);
                JsIrAstSerializer jsIrAstSerializer2 = this;
                DataWriter dataWriter3 = dataWriter;
                JsExpression thenExpression = x.getThenExpression();
                Intrinsics.checkNotNullExpressionValue(thenExpression, "getThenExpression(...)");
                jsIrAstSerializer2.writeExpression(dataWriter3, thenExpression);
                JsIrAstSerializer jsIrAstSerializer3 = this;
                DataWriter dataWriter4 = dataWriter;
                JsExpression elseExpression = x.getElseExpression();
                Intrinsics.checkNotNullExpressionValue(elseExpression, "getElseExpression(...)");
                jsIrAstSerializer3.writeExpression(dataWriter4, elseExpression);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitArrayAccess(JsArrayAccess x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(16);
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter2 = dataWriter;
                JsExpression arrayExpression = x.getArrayExpression();
                Intrinsics.checkNotNullExpressionValue(arrayExpression, "getArrayExpression(...)");
                jsIrAstSerializer.writeExpression(dataWriter2, arrayExpression);
                JsIrAstSerializer jsIrAstSerializer2 = this;
                DataWriter dataWriter3 = dataWriter;
                JsExpression indexExpression = x.getIndexExpression();
                Intrinsics.checkNotNullExpressionValue(indexExpression, "getIndexExpression(...)");
                jsIrAstSerializer2.writeExpression(dataWriter3, indexExpression);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitNameRef(JsNameRef nameRef) throws IOException {
                Intrinsics.checkNotNullParameter(nameRef, "nameRef");
                JsName name2 = nameRef.getName();
                JsExpression qualifier = nameRef.getQualifier();
                if (name2 != null) {
                    if (qualifier != null || Intrinsics.areEqual((Object) MetadataProperties.isInline(nameRef), (Object) true)) {
                        dataWriter.writeByte(17);
                        dataWriter.writeInt(this.internalizeName(name2));
                        DataWriter dataWriter2 = dataWriter;
                        JsIrAstSerializer jsIrAstSerializer = this;
                        dataWriter2.output.writeBoolean(qualifier != null);
                        if (qualifier != null) {
                            jsIrAstSerializer.writeExpression(dataWriter2, qualifier);
                        }
                        DataWriter dataWriter3 = dataWriter;
                        Boolean boolIsInline = MetadataProperties.isInline(nameRef);
                        DataWriter dataWriter4 = dataWriter;
                        dataWriter3.output.writeBoolean(boolIsInline != null);
                        if (boolIsInline != null) {
                            dataWriter4.writeBoolean(boolIsInline.booleanValue());
                            return;
                        }
                        return;
                    }
                    dataWriter.writeByte(18);
                    dataWriter.writeInt(this.internalizeName(name2));
                    return;
                }
                dataWriter.writeByte(19);
                DataWriter dataWriter5 = dataWriter;
                JsIrAstSerializer jsIrAstSerializer2 = this;
                String ident = nameRef.getIdent();
                Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
                dataWriter5.writeInt(jsIrAstSerializer2.internalizeString(ident));
                DataWriter dataWriter6 = dataWriter;
                JsIrAstSerializer jsIrAstSerializer3 = this;
                dataWriter6.output.writeBoolean(qualifier != null);
                if (qualifier != null) {
                    jsIrAstSerializer3.writeExpression(dataWriter6, qualifier);
                }
                DataWriter dataWriter7 = dataWriter;
                Boolean boolIsInline2 = MetadataProperties.isInline(nameRef);
                DataWriter dataWriter8 = dataWriter;
                dataWriter7.output.writeBoolean(boolIsInline2 != null);
                if (boolIsInline2 != null) {
                    dataWriter8.writeBoolean(boolIsInline2.booleanValue());
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitInvocation(JsInvocation invocation) throws IOException {
                Intrinsics.checkNotNullParameter(invocation, "invocation");
                dataWriter.writeByte(20);
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter2 = dataWriter;
                JsExpression qualifier = invocation.getQualifier();
                Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
                jsIrAstSerializer.writeExpression(dataWriter2, qualifier);
                DataWriter dataWriter3 = dataWriter;
                List<JsExpression> arguments = invocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                List<JsExpression> list = arguments;
                JsIrAstSerializer jsIrAstSerializer2 = this;
                DataWriter dataWriter4 = dataWriter;
                dataWriter3.output.writeInt(list.size());
                for (JsExpression jsExpression2 : list) {
                    Intrinsics.checkNotNull(jsExpression2);
                    jsIrAstSerializer2.writeExpression(dataWriter4, jsExpression2);
                }
                DataWriter dataWriter5 = dataWriter;
                Boolean boolIsInline = MetadataProperties.isInline(invocation);
                DataWriter dataWriter6 = dataWriter;
                dataWriter5.output.writeBoolean(boolIsInline != null);
                if (boolIsInline != null) {
                    dataWriter6.writeBoolean(boolIsInline.booleanValue());
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitNew(JsNew x) throws IOException {
                Intrinsics.checkNotNullParameter(x, "x");
                dataWriter.writeByte(21);
                JsIrAstSerializer jsIrAstSerializer = this;
                DataWriter dataWriter2 = dataWriter;
                JsExpression constructorExpression = x.getConstructorExpression();
                Intrinsics.checkNotNullExpressionValue(constructorExpression, "getConstructorExpression(...)");
                jsIrAstSerializer.writeExpression(dataWriter2, constructorExpression);
                DataWriter dataWriter3 = dataWriter;
                List<JsExpression> arguments = x.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                List<JsExpression> list = arguments;
                JsIrAstSerializer jsIrAstSerializer2 = this;
                DataWriter dataWriter4 = dataWriter;
                dataWriter3.output.writeInt(list.size());
                for (JsExpression jsExpression2 : list) {
                    Intrinsics.checkNotNull(jsExpression2);
                    jsIrAstSerializer2.writeExpression(dataWriter4, jsExpression2);
                }
            }
        };
        JsExpression jsExpression2 = jsExpression;
        Object source = jsExpression2.getSource();
        if (!(source instanceof JsLocationWithSource)) {
            source = null;
        }
        JsLocationWithSource jsLocationWithSource = (JsLocationWithSource) source;
        JsLocation jsLocationAsSimpleLocation = jsLocationWithSource != null ? jsLocationWithSource.asSimpleLocation() : null;
        dataWriter.output.writeBoolean(jsLocationAsSimpleLocation != null);
        if (jsLocationAsSimpleLocation != null) {
            String str = (String) this.fileStack.peek();
            String file = jsLocationAsSimpleLocation.getFile();
            boolean zAreEqual = Intrinsics.areEqual(str, file);
            dataWriter.output.writeBoolean(!zAreEqual);
            if (!zAreEqual) {
                dataWriter.writeInt(internalizeString(file));
                this.fileStack.push(jsLocationAsSimpleLocation.getFile());
            }
            dataWriter.writeInt(jsLocationAsSimpleLocation.getStartLine());
            dataWriter.writeInt(jsLocationAsSimpleLocation.getStartChar());
            jsExpression.accept(jsVisitor);
            if (!zAreEqual) {
                this.fileStack.pop();
            }
        }
        if (jsLocationAsSimpleLocation == null) {
            jsExpression.accept(jsVisitor);
            Unit unit = Unit.INSTANCE;
        }
        List<JsComment> commentsBeforeNode = jsExpression2.getCommentsBeforeNode();
        dataWriter.output.writeBoolean(commentsBeforeNode != null);
        if (commentsBeforeNode != null) {
            List<JsComment> list = commentsBeforeNode;
            dataWriter.output.writeInt(list.size());
            for (JsComment jsComment : list) {
                Intrinsics.checkNotNull(jsComment);
                writeComment(dataWriter, jsComment);
            }
        }
        List<JsComment> commentsAfterNode = jsExpression2.getCommentsAfterNode();
        dataWriter.output.writeBoolean(commentsAfterNode != null);
        if (commentsAfterNode != null) {
            List<JsComment> list2 = commentsAfterNode;
            dataWriter.output.writeInt(list2.size());
            for (JsComment jsComment2 : list2) {
                Intrinsics.checkNotNull(jsComment2);
                writeComment(dataWriter, jsComment2);
            }
        }
        JsExpression jsExpression3 = jsExpression;
        dataWriter.writeBoolean(MetadataProperties.getSynthetic(jsExpression3));
        dataWriter.writeByte(MetadataProperties.getSideEffects(jsExpression3).ordinal());
        JsImportedModule localAlias = MetadataProperties.getLocalAlias(jsExpression);
        dataWriter.output.writeBoolean(localAlias != null);
        if (localAlias != null) {
            writeImportedModule(dataWriter, localAlias);
        }
    }

    private final void writeImportedModule(DataWriter dataWriter, JsImportedModule jsImportedModule) throws IOException {
        dataWriter.writeInt(internalizeString(jsImportedModule.getExternalName()));
        dataWriter.writeInt(internalizeName(jsImportedModule.getInternalName()));
        JsExpression plainReference = jsImportedModule.getPlainReference();
        dataWriter.output.writeBoolean(plainReference != null);
        if (plainReference != null) {
            writeExpression(dataWriter, plainReference);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeFunction(DataWriter dataWriter, JsFunction jsFunction) throws IOException {
        JsBlock body = jsFunction.getBody();
        Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
        writeBlock(dataWriter, body);
        List<JsParameter> parameters = jsFunction.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<JsParameter> list = parameters;
        dataWriter.output.writeInt(list.size());
        for (JsParameter jsParameter : list) {
            Intrinsics.checkNotNull(jsParameter);
            writeParameter(dataWriter, jsParameter);
        }
        Set<JsFunction.Modifier> modifiers = jsFunction.getModifiers();
        Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
        Set<JsFunction.Modifier> set = modifiers;
        dataWriter.output.writeInt(set.size());
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            dataWriter.writeInt(((JsFunction.Modifier) it.next()).ordinal());
        }
        JsName name2 = jsFunction.getName();
        dataWriter.output.writeBoolean(name2 != null);
        if (name2 != null) {
            dataWriter.writeInt(internalizeName(name2));
        }
        dataWriter.writeBoolean(MetadataProperties.isLocal(jsFunction));
    }

    private final void writeParameter(DataWriter dataWriter, JsParameter jsParameter) throws IOException {
        JsName name2 = jsParameter.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        dataWriter.writeInt(internalizeName(name2));
        dataWriter.writeBoolean(MetadataProperties.getHasDefaultValue(jsParameter));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeCompositeBlock(DataWriter dataWriter, JsCompositeBlock jsCompositeBlock) throws IOException {
        List<JsStatement> statements = jsCompositeBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        List<JsStatement> list = statements;
        dataWriter.output.writeInt(list.size());
        for (JsStatement jsStatement : list) {
            Intrinsics.checkNotNull(jsStatement);
            writeStatement(dataWriter, jsStatement);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeBlock(DataWriter dataWriter, JsBlock jsBlock) throws IOException {
        dataWriter.writeBoolean(jsBlock instanceof JsCompositeBlock);
        List<JsStatement> statements = jsBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        List<JsStatement> list = statements;
        dataWriter.output.writeInt(list.size());
        for (JsStatement jsStatement : list) {
            Intrinsics.checkNotNull(jsStatement);
            writeStatement(dataWriter, jsStatement);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void writeVars(DataWriter dataWriter, JsVars jsVars) throws IOException {
        dataWriter.writeBoolean(jsVars.isMultiline());
        List<JsVars.JsVar> vars = jsVars.getVars();
        Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
        List<JsVars.JsVar> list = vars;
        dataWriter.output.writeInt(list.size());
        Iterator<T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            JsVars.JsVar jsVar = (JsVars.JsVar) it.next();
            Intrinsics.checkNotNull(jsVar);
            Object source = jsVar.getSource();
            if (!(source instanceof JsLocationWithSource)) {
                source = null;
            }
            JsLocationWithSource jsLocationWithSource = (JsLocationWithSource) source;
            JsLocation jsLocationAsSimpleLocation = jsLocationWithSource != null ? jsLocationWithSource.asSimpleLocation() : null;
            dataWriter.output.writeBoolean(jsLocationAsSimpleLocation != null);
            if (jsLocationAsSimpleLocation != null) {
                String str = (String) this.fileStack.peek();
                String file = jsLocationAsSimpleLocation.getFile();
                boolean zAreEqual = Intrinsics.areEqual(str, file);
                dataWriter.output.writeBoolean(!zAreEqual);
                if (!zAreEqual) {
                    dataWriter.writeInt(internalizeString(file));
                    this.fileStack.push(jsLocationAsSimpleLocation.getFile());
                }
                dataWriter.writeInt(jsLocationAsSimpleLocation.getStartLine());
                dataWriter.writeInt(jsLocationAsSimpleLocation.getStartChar());
                JsName name2 = jsVar.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                dataWriter.writeInt(internalizeName(name2));
                JsExpression initExpression = jsVar.getInitExpression();
                dataWriter.output.writeBoolean(initExpression != null);
                if (initExpression != null) {
                    writeExpression(dataWriter, initExpression);
                }
                if (!zAreEqual) {
                    this.fileStack.pop();
                }
            }
            if (jsLocationAsSimpleLocation == null) {
                JsName name3 = jsVar.getName();
                Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                dataWriter.writeInt(internalizeName(name3));
                JsExpression initExpression2 = jsVar.getInitExpression();
                dataWriter.output.writeBoolean(initExpression2 != null);
                if (initExpression2 != null) {
                    writeExpression(dataWriter, initExpression2);
                }
                Unit unit = Unit.INSTANCE;
            }
        }
        String exportedPackage = MetadataProperties.getExportedPackage(jsVars);
        dataWriter.output.writeBoolean(exportedPackage != null);
        if (exportedPackage != null) {
            dataWriter.writeInt(internalizeString(exportedPackage));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int internalizeName(JsName name2) throws IOException {
        Map<JsName, Integer> map = this.nameMap;
        Integer numValueOf = map.get(name2);
        if (numValueOf == null) {
            LocalAlias localAlias = MetadataProperties.getLocalAlias(name2);
            if (localAlias != null) {
                internalizeName(localAlias.getName());
            }
            DataWriter dataWriter = this.nameSerializer;
            String ident = name2.getIdent();
            Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
            dataWriter.writeInt(internalizeString(ident));
            dataWriter.writeBoolean(name2.isTemporary());
            LocalAlias localAlias2 = MetadataProperties.getLocalAlias(name2);
            dataWriter.output.writeBoolean(localAlias2 != null);
            if (localAlias2 != null) {
                writeLocalAlias(dataWriter, localAlias2);
            }
            dataWriter.writeBoolean(MetadataProperties.getImported(name2) && !this.importedNames.contains(name2));
            SpecialFunction specialFunction = MetadataProperties.getSpecialFunction(name2);
            dataWriter.output.writeBoolean(specialFunction != null);
            if (specialFunction != null) {
                dataWriter.writeByte(specialFunction.ordinal());
            }
            numValueOf = Integer.valueOf(this.nameMap.size());
            map.put(name2, numValueOf);
        }
        return numValueOf.intValue();
    }

    private final void writeLocalAlias(DataWriter dataWriter, LocalAlias localAlias) throws IOException {
        dataWriter.writeInt(internalizeName(localAlias.getName()));
        String tag = localAlias.getTag();
        dataWriter.output.writeBoolean(tag != null);
        if (tag != null) {
            dataWriter.writeInt(internalizeString(tag));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int internalizeString(String string) throws IOException {
        Map<String, Integer> map = this.stringMap;
        Integer numValueOf = map.get(string);
        if (numValueOf == null) {
            this.stringSerializer.writeString(string);
            numValueOf = Integer.valueOf(this.stringMap.size());
            map.put(string, numValueOf);
        }
        return numValueOf.intValue();
    }

    private final void writeComment(DataWriter dataWriter, JsComment jsComment) throws IOException {
        dataWriter.writeString(jsComment.getText());
        if (jsComment instanceof JsSingleLineComment) {
            dataWriter.writeBoolean(false);
        } else {
            if (!(jsComment instanceof JsMultiLineComment)) {
                throw new IllegalStateException(("Unknown type of comment " + jsComment.getClass().getName()).toString());
            }
            dataWriter.writeBoolean(true);
        }
    }
}
