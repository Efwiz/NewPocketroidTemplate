package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.io.AbstractReader;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.path.Path;
import com.thoughtworks.xstream.io.path.PathTracker;
import com.thoughtworks.xstream.io.path.PathTrackingReader;
import com.thoughtworks.xstream.mapper.Mapper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/ReferenceByXPathUnmarshaller.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ReferenceByXPathUnmarshaller extends AbstractReferenceUnmarshaller {
    protected boolean isNameEncoding;
    private PathTracker pathTracker;

    public ReferenceByXPathUnmarshaller(Object obj, HierarchicalStreamReader hierarchicalStreamReader, ConverterLookup converterLookup, Mapper mapper) {
        super(obj, hierarchicalStreamReader, converterLookup, mapper);
        this.pathTracker = new PathTracker();
        this.reader = new PathTrackingReader(hierarchicalStreamReader, this.pathTracker);
        this.isNameEncoding = hierarchicalStreamReader.underlyingReader() instanceof AbstractReader;
    }

    @Override // com.thoughtworks.xstream.core.AbstractReferenceUnmarshaller
    protected Object getReferenceKey(String str) {
        Path path = new Path(this.isNameEncoding ? ((AbstractReader) this.reader.underlyingReader()).decodeNode(str) : str);
        return str.charAt(0) != '/' ? this.pathTracker.getPath().apply(path) : path;
    }

    @Override // com.thoughtworks.xstream.core.AbstractReferenceUnmarshaller
    protected Object getCurrentReferenceKey() {
        return this.pathTracker.getPath();
    }
}
