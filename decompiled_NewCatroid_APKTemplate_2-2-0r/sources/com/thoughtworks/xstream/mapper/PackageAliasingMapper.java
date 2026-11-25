package com.thoughtworks.xstream.mapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/mapper/PackageAliasingMapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class PackageAliasingMapper extends MapperWrapper implements Serializable {
    private static final Comparator REVERSE = new Comparator() { // from class: com.thoughtworks.xstream.mapper.PackageAliasingMapper.1
        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return ((String) obj2).compareTo((String) obj);
        }
    };
    protected transient Map nameToPackage;
    private Map packageToName;

    public PackageAliasingMapper(Mapper mapper) {
        super(mapper);
        this.packageToName = new TreeMap(REVERSE);
        this.nameToPackage = new HashMap();
    }

    public void addPackageAlias(String str, String str2) {
        if (str.length() > 0 && str.charAt(str.length() - 1) != '.') {
            str = new StringBuffer().append(str).append('.').toString();
        }
        if (str2.length() > 0 && str2.charAt(str2.length() - 1) != '.') {
            str2 = new StringBuffer().append(str2).append('.').toString();
        }
        this.nameToPackage.put(str, str2);
        this.packageToName.put(str2, str);
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public String serializedClass(Class cls) {
        String name2 = cls.getName();
        int length = name2.length();
        while (true) {
            int iLastIndexOf = name2.lastIndexOf(46, length);
            String str = (String) this.packageToName.get(iLastIndexOf < 0 ? "" : name2.substring(0, iLastIndexOf + 1));
            if (str != null) {
                StringBuffer stringBufferAppend = new StringBuffer().append(str);
                if (iLastIndexOf >= 0) {
                    name2 = name2.substring(iLastIndexOf + 1);
                }
                return stringBufferAppend.append(name2).toString();
            }
            int i = iLastIndexOf - 1;
            if (iLastIndexOf < 0) {
                return super.serializedClass(cls);
            }
            length = i;
        }
    }

    @Override // com.thoughtworks.xstream.mapper.MapperWrapper, com.thoughtworks.xstream.mapper.Mapper
    public Class realClass(String str) {
        int length = str.length();
        while (true) {
            int iLastIndexOf = str.lastIndexOf(46, length);
            String str2 = (String) this.nameToPackage.get(iLastIndexOf < 0 ? "" : new StringBuffer().append(str.substring(0, iLastIndexOf)).append('.').toString());
            if (str2 != null) {
                StringBuffer stringBufferAppend = new StringBuffer().append(str2);
                if (iLastIndexOf >= 0) {
                    str = str.substring(iLastIndexOf + 1);
                }
                str = stringBufferAppend.append(str).toString();
                break;
            }
            int i = iLastIndexOf - 1;
            if (iLastIndexOf < 0) {
                break;
            }
            length = i;
        }
        return super.realClass(str);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(new HashMap(this.packageToName));
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        TreeMap treeMap = new TreeMap(REVERSE);
        this.packageToName = treeMap;
        treeMap.putAll((Map) objectInputStream.readObject());
        this.nameToPackage = new HashMap();
        for (Object obj : this.packageToName.keySet()) {
            this.nameToPackage.put(this.packageToName.get(obj), obj);
        }
    }
}
