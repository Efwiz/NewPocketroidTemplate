package com.reandroid.dex.model;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.common.AnnotationVisibility;
import com.reandroid.dex.common.DexUtils;
import com.reandroid.dex.data.AnnotationElement;
import com.reandroid.dex.data.AnnotationItem;
import com.reandroid.dex.data.AnnotationSet;
import com.reandroid.dex.data.ClassData;
import com.reandroid.dex.data.InstructionList;
import com.reandroid.dex.data.MethodDef;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.ins.Ins35c;
import com.reandroid.dex.ins.Opcode;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.value.ArrayValue;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.dex.value.TypeValue;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.FilterIterator;
import com.reandroid.utils.io.IOUtil;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class RClassParent extends DexClass {
    private static final String SIMPLE_NAME_PREFIX = "R";
    private final Map<String, RClass> mMembers;
    private final Set<PackageBlock> mPackageBlocks;

    public RClassParent(DexFile dexFile, ClassId classId) {
        super(dexFile, classId);
        this.mMembers = new HashMap();
        this.mPackageBlocks = new HashSet();
    }

    public RField getRField(int i) {
        return load(getEntry(i));
    }

    public boolean hasRField(int i) {
        return getEntry(i) != null;
    }

    private ResourceEntry getEntry(int i) {
        Iterator<PackageBlock> it = this.mPackageBlocks.iterator();
        while (it.hasNext()) {
            ResourceEntry resource = it.next().getResource(i);
            if (resource != null) {
                return resource;
            }
        }
        return null;
    }

    public void load(PackageBlock packageBlock) {
        this.mPackageBlocks.add(packageBlock);
    }

    private RField load(ResourceEntry resourceEntry) {
        if (resourceEntry == null || resourceEntry.isEmpty()) {
            return null;
        }
        return getOrCreateMember(resourceEntry.getType()).load(resourceEntry);
    }

    public RClass getOrCreateMember(String str) {
        TypeKey typeKeyCreateInnerClass = getKey().createInnerClass(str);
        RClass rClass = this.mMembers.get(typeKeyCreateInnerClass.getTypeName());
        if (rClass != null) {
            return rClass;
        }
        addMemberAnnotation(str);
        RClass rClass2 = new RClass(getDexFile(), getDexFile().getOrCreateClassId(typeKeyCreateInnerClass));
        this.mMembers.put(typeKeyCreateInnerClass.getTypeName(), rClass2);
        rClass2.initialize();
        return rClass2;
    }

    public void addMemberAnnotation(final String str) {
        ArrayValue orCreateMembersArray = getOrCreateMembersArray();
        Iterator<String> memberSimpleNames = getMemberSimpleNames();
        str.getClass();
        if (FilterIterator.of(memberSimpleNames, new Predicate() { // from class: com.reandroid.dex.model.RClassParent$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return str.equals((String) obj);
            }
        }).hasNext()) {
            return;
        }
        ((TypeValue) orCreateMembersArray.createNext(DexValueType.TYPE)).setItem(getKey().createInnerClass(str));
    }

    public Iterator<String> getMemberSimpleNames() {
        return ComputeIterator.of(getMemberNames(), new Function() { // from class: com.reandroid.dex.model.RClassParent$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return DexUtils.getSimpleInnerName((String) obj);
            }
        });
    }

    public Iterator<String> getMemberNames() {
        return ComputeIterator.of(getOrCreateMembersArray().iterator(TypeValue.class), new Function() { // from class: com.reandroid.dex.model.RClassParent$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2779lambda$getMemberNames$0$comreandroiddexmodelRClassParent((TypeValue) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getMemberNames$0$com-reandroid-dex-model-RClassParent, reason: not valid java name */
    /* synthetic */ String m2779lambda$getMemberNames$0$comreandroiddexmodelRClassParent(TypeValue typeValue) {
        return getKey().getTypeName();
    }

    private ArrayValue getOrCreateMembersArray() {
        AnnotationElement element = getOrCreateMemberAnnotation().getElement("value");
        DexValueBlock<?> value = element.getValue();
        if (value == null) {
            value = (ArrayValue) DexValueType.ARRAY.newInstance();
            element.setValue(value);
        }
        return (ArrayValue) value;
    }

    private AnnotationItem getOrCreateMemberAnnotation() {
        AnnotationItem orCreate = getOrCreateClassAnnotations().getOrCreate(TypeKey.DALVIK_MemberClass, "value");
        AnnotationElement element = orCreate.getElement("value");
        if (element.getValueType() == DexValueType.ARRAY) {
            return orCreate;
        }
        orCreate.setVisibility(AnnotationVisibility.SYSTEM);
        element.setValue((ArrayValue) DexValueType.ARRAY.newInstance());
        return orCreate;
    }

    private AnnotationSet getOrCreateClassAnnotations() {
        AnnotationSet classAnnotations = getClassAnnotations();
        return classAnnotations != null ? classAnnotations : getId().getOrCreateClassAnnotations();
    }

    private AnnotationSet getClassAnnotations() {
        return getId().getClassAnnotations();
    }

    public void initialize() {
        ClassId id2 = getId();
        id2.addAccessFlag(AccessFlag.PUBLIC);
        ClassData orCreateClassData = id2.getOrCreateClassData();
        MethodKey methodKey = new MethodKey(id2.getName(), "<init>", (String[]) null, ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        if (orCreateClassData.getMethod(methodKey) != null) {
            return;
        }
        MethodDef orCreateDirect = orCreateClassData.getOrCreateDirect(methodKey);
        orCreateDirect.addAccessFlag(AccessFlag.PUBLIC);
        orCreateDirect.addAccessFlag(AccessFlag.CONSTRUCTOR);
        InstructionList orCreateInstructionList = orCreateDirect.getOrCreateInstructionList();
        Ins35c ins35c = (Ins35c) orCreateInstructionList.createNext(Opcode.INVOKE_DIRECT);
        ins35c.setSectionIdKey(MethodKey.parse("Ljava/lang/Object;-><init>()V"));
        ins35c.setRegistersCount(1);
        ins35c.setRegister(0, 0);
        orCreateInstructionList.createNext(Opcode.RETURN_VOID);
    }

    static boolean isRParentClassName(ClassId classId) {
        if (classId != null) {
            return isRParentClassName(classId.getName());
        }
        return false;
    }

    static boolean isRParentClassName(String str) {
        if (str == null) {
            return false;
        }
        return SIMPLE_NAME_PREFIX.equals(DexUtils.getSimpleName(str));
    }

    public static void serializePublicXml(Collection<RField> collection, XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startDocument("utf-8", null);
        xmlSerializer.text("\n");
        xmlSerializer.startTag(null, PackageBlock.TAG_resources);
        ArrayCollection arrayCollection = new ArrayCollection();
        arrayCollection.addAll(collection);
        arrayCollection.sort(CompareUtil.getComparableComparator());
        Iterator<T> it = arrayCollection.iterator();
        while (it.hasNext()) {
            ((RField) it.next()).serializePublicXml(xmlSerializer);
        }
        xmlSerializer.text("\n");
        xmlSerializer.endTag(null, PackageBlock.TAG_resources);
        xmlSerializer.endDocument();
        xmlSerializer.flush();
        IOUtil.close(xmlSerializer);
    }
}
