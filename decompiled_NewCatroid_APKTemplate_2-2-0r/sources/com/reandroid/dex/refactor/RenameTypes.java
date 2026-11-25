package com.reandroid.dex.refactor;

import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.common.DexUtils;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.key.KeyPair;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.key.TypeKeyReference;
import com.reandroid.dex.model.DexClass;
import com.reandroid.dex.model.DexClassRepository;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.utils.ObjectsUtil;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class RenameTypes extends Rename<TypeKey, TypeKey> {
    public static final int DEFAULT_ARRAY_DEPTH = ObjectsUtil.of(3);
    private int arrayDepth = DEFAULT_ARRAY_DEPTH;
    private boolean renameSignatures = true;
    private boolean renameSource = true;
    private boolean noRenameSourceForNoPackageClass = true;
    private boolean fixAccessibility = true;
    private Set<String> renamedStrings = new HashSet();

    @Override // com.reandroid.dex.refactor.Rename
    public int apply(DexClassRepository dexClassRepository) {
        Map<String, String> mapBuildRenameMap = buildRenameMap();
        this.renamedStrings = new HashSet(mapBuildRenameMap.size());
        renameStringIds(dexClassRepository, mapBuildRenameMap);
        renameExternalTypeKeyReferences(dexClassRepository, mapBuildRenameMap);
        int size = this.renamedStrings.size();
        if (size != 0) {
            dexClassRepository.clearPoolMap();
        }
        fixAccessibility(dexClassRepository);
        this.renamedStrings.clear();
        this.renamedStrings = null;
        return size;
    }

    private void renameStringIds(DexClassRepository dexClassRepository, Map<String, String> map) {
        Iterator clonedItems = dexClassRepository.getClonedItems(SectionType.STRING_ID);
        while (clonedItems.hasNext()) {
            StringId stringId = (StringId) clonedItems.next();
            stringId.addUsageType(UsageMarker.USAGE_DEFINITION);
            String str = map.get(stringId.getString());
            if (str != null) {
                setString(stringId, str);
            } else {
                renameSignatures(map, stringId);
            }
        }
    }

    private void renameExternalTypeKeyReferences(DexClassRepository dexClassRepository, Map<String, String> map) {
        Iterator<TypeKeyReference> it = dexClassRepository.getExternalTypeKeyReferenceList().iterator();
        while (it.hasNext()) {
            renameExternalTypeKeyReference(it.next(), map);
        }
    }

    private void renameExternalTypeKeyReference(TypeKeyReference typeKeyReference, Map<String, String> map) {
        TypeKey typeKey = typeKeyReference.getTypeKey();
        if (typeKey == null) {
            return;
        }
        String str = map.get(typeKey.getTypeName());
        if (str == null) {
            str = map.get(typeKey.getSourceName());
        }
        TypeKey typeKey2 = TypeKey.parse(str);
        if (typeKey2 != null) {
            typeKeyReference.setTypeKey(typeKey2);
            this.renamedStrings.add(str);
        }
    }

    private void fixAccessibility(DexClassRepository dexClassRepository) {
        final Set<String> set = this.renamedStrings;
        if (!this.fixAccessibility || set == null || set.isEmpty()) {
            return;
        }
        Iterator<DexClass> dexClasses = dexClassRepository.getDexClasses(new Predicate() { // from class: com.reandroid.dex.refactor.RenameTypes$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return set.contains(((TypeKey) obj).getTypeName());
            }
        });
        while (dexClasses.hasNext()) {
            dexClasses.next().fixAccessibility();
        }
    }

    private void renameSignatures(Map<String, String> map, StringId stringId) {
        if (stringId.containsUsage(UsageMarker.USAGE_SIGNATURE_TYPE)) {
            String string = stringId.getString();
            if (string.indexOf(76) < 0) {
                return;
            }
            String[] strArrSplitSignatures = DexUtils.splitSignatures(string);
            int length = strArrSplitSignatures.length;
            boolean z = false;
            for (int i = 0; i < length; i++) {
                String str = map.get(strArrSplitSignatures[i]);
                if (str != null) {
                    strArrSplitSignatures[i] = str;
                    z = true;
                }
            }
            if (z) {
                StringBuilder sb = new StringBuilder();
                for (String str2 : strArrSplitSignatures) {
                    sb.append(str2);
                }
                setString(stringId, sb.toString());
            }
        }
    }

    private void setString(StringId stringId, String str) {
        stringId.setString(str);
        this.renamedStrings.add(str);
    }

    public void setArrayDepth(int i) {
        if (i < 0) {
            i = DEFAULT_ARRAY_DEPTH;
        }
        this.arrayDepth = i;
    }

    public void setRenameSignatures(boolean z) {
        this.renameSignatures = z;
    }

    public void setRenameSource(boolean z) {
        this.renameSource = z;
    }

    public void setNoRenameSourceForNoPackageClass(boolean z) {
        this.noRenameSourceForNoPackageClass = z;
    }

    public void setFixAccessibility(boolean z) {
        this.fixAccessibility = z;
    }

    private Map<String, String> buildRenameMap() {
        List<KeyPair<TypeKey, TypeKey>> listSortedList = sortedList();
        boolean z = this.renameSignatures;
        boolean z2 = this.renameSource;
        boolean z3 = this.noRenameSourceForNoPackageClass;
        int i = 1;
        int i2 = z ? 3 : 1;
        if (z2) {
            i2++;
        }
        int i3 = this.arrayDepth;
        if (i3 > 0) {
            i2 = i2 + i3 + 1;
        }
        HashMap map = new HashMap(listSortedList.size() * i2);
        int size = listSortedList.size();
        int i4 = this.arrayDepth + 1;
        int i5 = 0;
        while (i5 < size) {
            KeyPair<TypeKey, TypeKey> keyPair = listSortedList.get(i5);
            TypeKey typeKey = (TypeKey) keyPair.getFirst();
            TypeKey typeKey2 = (TypeKey) keyPair.getSecond();
            map.put(typeKey.getTypeName(), typeKey2.getTypeName());
            if (z) {
                map.put(typeKey.getTypeName().replace(';', '<'), typeKey2.getTypeName().replace(';', '<'));
                String typeName = typeKey.getTypeName();
                String typeName2 = typeKey2.getTypeName();
                map.put(typeName.substring(0, typeName.length() - 1), typeName2.substring(0, typeName2.length() - i));
            }
            int i6 = i;
            while (i6 < i4) {
                String arrayType = typeKey.getArrayType(i6);
                String arrayType2 = typeKey2.getArrayType(i6);
                map.put(arrayType, arrayType2);
                if (z && i6 == i) {
                    map.put(arrayType.replace(';', '<'), arrayType2.replace(';', '<'));
                }
                i6++;
                i = 1;
            }
            if (z2) {
                String typeName3 = typeKey.getTypeName();
                if (!z3 || typeName3.indexOf(47) > 0) {
                    map.put(typeKey.getSourceName(), typeKey2.getSourceName());
                }
            }
            i5++;
            i = 1;
        }
        return map;
    }

    private Map<String, String> buildSignatureRenameMap() {
        List<KeyPair<TypeKey, TypeKey>> listSortedList = sortedList();
        boolean z = this.renameSignatures;
        boolean z2 = this.renameSource;
        boolean z3 = this.noRenameSourceForNoPackageClass;
        int i = 1;
        int i2 = z ? 3 : 1;
        if (z2) {
            i2++;
        }
        int i3 = this.arrayDepth;
        if (i3 > 0) {
            i2 = i2 + i3 + 1;
        }
        HashMap map = new HashMap(listSortedList.size() * i2);
        int size = listSortedList.size();
        int i4 = this.arrayDepth + 1;
        int i5 = 0;
        while (i5 < size) {
            KeyPair<TypeKey, TypeKey> keyPair = listSortedList.get(i5);
            TypeKey typeKey = (TypeKey) keyPair.getFirst();
            TypeKey typeKey2 = (TypeKey) keyPair.getSecond();
            map.put(typeKey.getTypeName(), typeKey2.getTypeName());
            if (z) {
                map.put(typeKey.getTypeName().replace(';', '<'), typeKey2.getTypeName().replace(';', '<'));
                String typeName = typeKey.getTypeName();
                String typeName2 = typeKey2.getTypeName();
                map.put(typeName.substring(0, typeName.length() - 1), typeName2.substring(0, typeName2.length() - i));
            }
            int i6 = i;
            while (i6 < i4) {
                String arrayType = typeKey.getArrayType(i6);
                String arrayType2 = typeKey2.getArrayType(i6);
                map.put(arrayType, arrayType2);
                if (z && i6 == i) {
                    map.put(arrayType.replace(';', '<'), arrayType2.replace(';', '<'));
                }
                i6++;
                i = 1;
            }
            if (z2) {
                String typeName3 = typeKey.getTypeName();
                if (!z3 || typeName3.indexOf(47) > 0) {
                    map.put(typeKey.getSourceName(), typeKey2.getSourceName());
                }
            }
            i5++;
            i = 1;
        }
        return map;
    }
}
