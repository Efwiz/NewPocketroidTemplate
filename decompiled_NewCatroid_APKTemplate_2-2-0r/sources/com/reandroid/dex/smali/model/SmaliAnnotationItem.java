package com.reandroid.dex.smali.model;

import com.reandroid.dex.common.AnnotationVisibility;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliParseException;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliAnnotationItem extends SmaliSet<SmaliAnnotationElement> implements SmaliRegion {
    private final SmaliDirective smaliDirective;
    private TypeKey type;
    private AnnotationVisibility visibility;

    public SmaliAnnotationItem(boolean z) {
        this.smaliDirective = z ? SmaliDirective.SUB_ANNOTATION : SmaliDirective.ANNOTATION;
        if (z) {
            return;
        }
        this.visibility = AnnotationVisibility.BUILD;
    }

    public SmaliAnnotationItem() {
        this(false);
    }

    public AnnotationVisibility getVisibility() {
        return this.visibility;
    }

    public void setVisibility(AnnotationVisibility annotationVisibility) {
        SmaliDirective smaliDirective = getSmaliDirective();
        if (smaliDirective == SmaliDirective.ANNOTATION && annotationVisibility == null) {
            throw new NullPointerException("Null annotation visibility");
        }
        if (smaliDirective == SmaliDirective.SUB_ANNOTATION && annotationVisibility != null) {
            throw new IllegalArgumentException("Can not set annotation visibility for: " + smaliDirective);
        }
        this.visibility = annotationVisibility;
    }

    public TypeKey getType() {
        return this.type;
    }

    public void setType(TypeKey typeKey) {
        this.type = typeKey;
    }

    @Override // com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return this.smaliDirective;
    }

    @Override // com.reandroid.dex.smali.model.SmaliSet, com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getSmaliDirective().append(smaliWriter);
        smaliWriter.appendOptional(getVisibility());
        smaliWriter.appendOptional(getType());
        smaliWriter.appendAllWithIndent(iterator());
        getSmaliDirective().appendEnd(smaliWriter);
    }

    @Override // com.reandroid.dex.smali.model.SmaliSet, com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
        smaliReader.skipWhitespacesOrComment();
        SmaliDirective smaliDirective = getSmaliDirective();
        SmaliParseException.expect(smaliReader, smaliDirective);
        if (smaliDirective == SmaliDirective.ANNOTATION) {
            AnnotationVisibility annotationVisibility = AnnotationVisibility.parse(smaliReader);
            if (annotationVisibility == null) {
                throw new SmaliParseException("Unrecognized annotation visibility", smaliReader);
            }
            setVisibility(annotationVisibility);
        }
        setType(TypeKey.read(smaliReader));
        while (parseNext(smaliReader) != null) {
            smaliReader.skipWhitespacesOrComment();
        }
        SmaliParseException.expect(smaliReader, getSmaliDirective(), true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.dex.smali.model.SmaliSet
    public SmaliAnnotationElement createNext(SmaliReader smaliReader) {
        smaliReader.skipWhitespacesOrComment();
        if (smaliReader.finished() || getSmaliDirective().isEnd(smaliReader)) {
            return null;
        }
        return new SmaliAnnotationElement();
    }
}
