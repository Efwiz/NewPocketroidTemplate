package com.thoughtworks.xstream.mapper;

import com.thoughtworks.xstream.core.util.FastField;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/ElementIgnoringMapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ElementIgnoringMapper extends MapperWrapper {
    static /* synthetic */ Class class$java$lang$Object;
    protected final Set fieldsToOmit;
    protected final Set unknownElementsToIgnore;

    public ElementIgnoringMapper(Mapper mapper) {
        super(mapper);
        this.fieldsToOmit = new HashSet();
        this.unknownElementsToIgnore = new LinkedHashSet();
    }

    public void addElementsToIgnore(Pattern pattern) {
        this.unknownElementsToIgnore.add(pattern);
    }

    public void omitField(Class cls, String str) {
        this.fieldsToOmit.add(key(cls, str));
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public boolean shouldSerializeMember(Class cls, String str) throws Throwable {
        if (this.fieldsToOmit.contains(key(cls, str))) {
            return false;
        }
        Class clsClass$ = class$java$lang$Object;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_LANG_OBJECT);
            class$java$lang$Object = clsClass$;
        }
        if (cls == clsClass$ && isIgnoredElement(str)) {
            return false;
        }
        return super.shouldSerializeMember(cls, str);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public boolean isIgnoredElement(String str) {
        if (!this.unknownElementsToIgnore.isEmpty()) {
            Iterator it = this.unknownElementsToIgnore.iterator();
            while (it.hasNext()) {
                if (((Pattern) it.next()).matcher(str).matches()) {
                    return true;
                }
            }
        }
        return super.isIgnoredElement(str);
    }

    private Object key(Class cls, String str) {
        return new FastField(cls, str);
    }
}
