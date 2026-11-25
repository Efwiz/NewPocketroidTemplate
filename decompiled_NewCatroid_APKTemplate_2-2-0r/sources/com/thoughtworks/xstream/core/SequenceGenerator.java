package com.thoughtworks.xstream.core;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/SequenceGenerator.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class SequenceGenerator implements ReferenceByIdMarshaller.IDGenerator {
    private int counter;

    public SequenceGenerator(int i) {
        this.counter = i;
    }

    @Override // com.thoughtworks.xstream.core.ReferenceByIdMarshaller.IDGenerator
    public String next(Object obj) {
        int i = this.counter;
        this.counter = i + 1;
        return String.valueOf(i);
    }
}
