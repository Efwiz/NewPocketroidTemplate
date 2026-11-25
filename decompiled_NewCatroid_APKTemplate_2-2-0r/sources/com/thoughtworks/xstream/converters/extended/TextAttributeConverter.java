package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.reflection.AbstractAttributedCharacterIteratorAttributeConverter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/TextAttributeConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class TextAttributeConverter extends AbstractAttributedCharacterIteratorAttributeConverter {
    static /* synthetic */ Class class$java$awt$font$TextAttribute;

    /* JADX WARN: Illegal instructions before constructor call */
    public TextAttributeConverter() throws Throwable {
        Class clsClass$ = class$java$awt$font$TextAttribute;
        if (clsClass$ == null) {
            clsClass$ = class$("java.awt.font.TextAttribute");
            class$java$awt$font$TextAttribute = clsClass$;
        }
        super(clsClass$);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }
}
