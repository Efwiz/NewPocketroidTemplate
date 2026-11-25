package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.security.auth.Subject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/SubjectConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class SubjectConverter extends AbstractCollectionConverter {
    static /* synthetic */ Class class$javax$security$auth$Subject;

    protected void marshalPrivateCredentials(Set set, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
    }

    protected void marshalPublicCredentials(Set set, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
    }

    public SubjectConverter(Mapper mapper) {
        super(mapper);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        Class clsClass$ = class$javax$security$auth$Subject;
        if (clsClass$ == null) {
            clsClass$ = class$("javax.security.auth.Subject");
            class$javax$security$auth$Subject = clsClass$;
        }
        return cls == clsClass$;
    }

    @Override // com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        Subject subject = (Subject) obj;
        marshalPrincipals(subject.getPrincipals(), hierarchicalStreamWriter, marshallingContext);
        marshalPublicCredentials(subject.getPublicCredentials(), hierarchicalStreamWriter, marshallingContext);
        marshalPrivateCredentials(subject.getPrivateCredentials(), hierarchicalStreamWriter, marshallingContext);
        marshalReadOnly(subject.isReadOnly(), hierarchicalStreamWriter);
    }

    protected void marshalPrincipals(Set set, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        hierarchicalStreamWriter.startNode("principals");
        Iterator it = set.iterator();
        while (it.hasNext()) {
            writeCompleteItem(it.next(), marshallingContext, hierarchicalStreamWriter);
        }
        hierarchicalStreamWriter.endNode();
    }

    protected void marshalReadOnly(boolean z, HierarchicalStreamWriter hierarchicalStreamWriter) {
        hierarchicalStreamWriter.startNode("readOnly");
        hierarchicalStreamWriter.setValue(String.valueOf(z));
        hierarchicalStreamWriter.endNode();
    }

    @Override // com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter, com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return new Subject(unmarshalReadOnly(hierarchicalStreamReader), unmarshalPrincipals(hierarchicalStreamReader, unmarshallingContext), unmarshalPublicCredentials(hierarchicalStreamReader, unmarshallingContext), unmarshalPrivateCredentials(hierarchicalStreamReader, unmarshallingContext));
    }

    protected Set unmarshalPrincipals(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return populateSet(hierarchicalStreamReader, unmarshallingContext);
    }

    protected Set unmarshalPublicCredentials(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return Collections.EMPTY_SET;
    }

    protected Set unmarshalPrivateCredentials(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return Collections.EMPTY_SET;
    }

    protected boolean unmarshalReadOnly(HierarchicalStreamReader hierarchicalStreamReader) {
        hierarchicalStreamReader.moveDown();
        boolean z = Boolean.getBoolean(hierarchicalStreamReader.getValue());
        hierarchicalStreamReader.moveUp();
        return z;
    }

    protected Set populateSet(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        HashSet hashSet = new HashSet();
        hierarchicalStreamReader.moveDown();
        while (hierarchicalStreamReader.hasMoreChildren()) {
            hashSet.add(readCompleteItem(hierarchicalStreamReader, unmarshallingContext, hashSet));
        }
        hierarchicalStreamReader.moveUp();
        return hashSet;
    }
}
