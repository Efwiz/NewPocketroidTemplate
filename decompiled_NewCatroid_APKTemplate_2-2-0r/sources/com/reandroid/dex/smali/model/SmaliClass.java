package com.reandroid.dex.smali.model;

import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.common.Modifier;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.key.TypeListKey;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliClass extends SmaliDef {
    private final SmaliFieldSet fields;
    private final SmaliInterfaceSet interfaces;
    private final SmaliMethodSet methods;
    private StringKey sourceFile;
    private TypeKey superClass;

    public SmaliClass() {
        SmaliInterfaceSet smaliInterfaceSet = new SmaliInterfaceSet();
        this.interfaces = smaliInterfaceSet;
        SmaliFieldSet smaliFieldSet = new SmaliFieldSet();
        this.fields = smaliFieldSet;
        SmaliMethodSet smaliMethodSet = new SmaliMethodSet();
        this.methods = smaliMethodSet;
        smaliInterfaceSet.setParent(this);
        smaliFieldSet.setParent(this);
        smaliMethodSet.setParent(this);
    }

    @Override // com.reandroid.dex.smali.model.SmaliDef
    public TypeKey getKey() {
        return TypeKey.create(getName());
    }

    public void setKey(TypeKey typeKey) {
        setName(typeKey != null ? typeKey.getTypeName() : null);
    }

    public TypeKey getSuperClass() {
        return this.superClass;
    }

    public void setSuperClass(TypeKey typeKey) {
        this.superClass = typeKey;
    }

    public StringKey getSourceFile() {
        return this.sourceFile;
    }

    public void setSourceFile(StringKey stringKey) {
        this.sourceFile = stringKey;
    }

    public String getSourceFileName() {
        StringKey sourceFile = getSourceFile();
        if (sourceFile != null) {
            return sourceFile.getString();
        }
        return null;
    }

    public SmaliInterfaceSet getInterfaces() {
        return this.interfaces;
    }

    public TypeListKey getInterfacesKey() {
        return getInterfaces().getKey();
    }

    public boolean hasClassData() {
        return (this.fields.isEmpty() && this.methods.isEmpty()) ? false : true;
    }

    public Iterator<SmaliField> getStaticFields() {
        return this.fields.getStaticFields();
    }

    public Iterator<SmaliField> getInstanceFields() {
        return this.fields.getInstanceFields();
    }

    public Iterator<SmaliMethod> getDirectMethods() {
        return this.methods.getDirectMethods();
    }

    public Iterator<SmaliMethod> getVirtualMethods() {
        return this.methods.getVirtualMethods();
    }

    public SmaliMethod getStaticConstructor() {
        Iterator<SmaliMethod> directMethods = getDirectMethods();
        while (directMethods.hasNext()) {
            SmaliMethod next = directMethods.next();
            if (next.isConstructor() && next.isStatic() && MethodKey.CONSTRUCTOR_STATIC.equalsIgnoreDeclaring(next.getKey())) {
                return next;
            }
        }
        return null;
    }

    void fixUninitializedFinalFields() {
        Iterator<SmaliField> staticFields = getStaticFields();
        while (staticFields.hasNext()) {
            staticFields.next().fixUninitializedFinalValue();
        }
    }

    @Override // com.reandroid.dex.smali.model.SmaliDef, com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.CLASS;
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getSmaliDirective().append(smaliWriter);
        Modifier.append(smaliWriter, getAccessFlags());
        smaliWriter.appendOptional(getKey());
        smaliWriter.newLine();
        SmaliDirective.SUPER.append(smaliWriter);
        smaliWriter.appendOptional(getSuperClass());
        StringKey sourceFile = getSourceFile();
        if (sourceFile != null) {
            smaliWriter.newLine();
            SmaliDirective.SOURCE.append(smaliWriter);
            sourceFile.append(smaliWriter);
        }
        getInterfaces().append(smaliWriter);
        if (hasAnnotation()) {
            smaliWriter.newLine(2);
            smaliWriter.appendComment("annotations");
            smaliWriter.appendAllWithDoubleNewLine(getAnnotation().iterator());
        }
        this.fields.append(smaliWriter);
        this.methods.append(smaliWriter);
    }

    @Override // com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespacesOrComment();
        SmaliParseException.expect(smaliReader, SmaliDirective.CLASS);
        setAccessFlags(AccessFlag.parse(smaliReader));
        setKey(TypeKey.read(smaliReader));
        while (parseNext(smaliReader)) {
            smaliReader.skipWhitespacesOrComment();
        }
        smaliReader.skipWhitespacesOrComment();
        fixUninitializedFinalFields();
    }

    private boolean parseNext(SmaliReader smaliReader) throws IOException {
        if (smaliReader.finished()) {
            return false;
        }
        smaliReader.skipWhitespacesOrComment();
        SmaliDirective smaliDirective = SmaliDirective.parse(smaliReader, false);
        if (smaliDirective == SmaliDirective.CLASS) {
            return false;
        }
        if (smaliDirective == SmaliDirective.SUPER) {
            parseSuper(smaliReader);
            return true;
        }
        if (smaliDirective == SmaliDirective.SOURCE) {
            parseSource(smaliReader);
            return true;
        }
        if (smaliDirective == SmaliDirective.ANNOTATION) {
            getOrCreateAnnotation().parse(smaliReader);
            return true;
        }
        if (smaliDirective == SmaliDirective.FIELD) {
            this.fields.parse(smaliReader);
            return true;
        }
        if (smaliDirective == SmaliDirective.METHOD) {
            this.methods.parse(smaliReader);
            return true;
        }
        if (smaliDirective != SmaliDirective.IMPLEMENTS) {
            return false;
        }
        this.interfaces.parse(smaliReader);
        return true;
    }

    private void parseSuper(SmaliReader smaliReader) throws IOException {
        SmaliParseException.expect(smaliReader, SmaliDirective.SUPER);
        setSuperClass(TypeKey.read(smaliReader));
    }

    private void parseSource(SmaliReader smaliReader) throws IOException {
        SmaliParseException.expect(smaliReader, SmaliDirective.SOURCE);
        smaliReader.skipSpaces();
        setSourceFile(StringKey.read(smaliReader));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SmaliField parseField(SmaliReader smaliReader) throws IOException {
        return (SmaliField) this.fields.parseNext(smaliReader);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SmaliMethod parseMethod(SmaliReader smaliReader) throws IOException {
        return (SmaliMethod) this.methods.parseNext(smaliReader);
    }

    @Override // com.reandroid.dex.smali.model.Smali
    public String toDebugString() {
        return "class = " + getKey();
    }
}
