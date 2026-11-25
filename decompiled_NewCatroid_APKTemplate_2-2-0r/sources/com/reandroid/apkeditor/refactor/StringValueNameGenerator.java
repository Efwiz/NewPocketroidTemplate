package com.reandroid.apkeditor.refactor;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResTableEntry;
import com.reandroid.arsc.value.TableEntry;
import com.reandroid.arsc.value.ValueType;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes10.dex */
public class StringValueNameGenerator {
    private static final int MAX_NAME_LEN = 40;
    private static final String PATH_DATA_NAME = "vector_path_data";
    private static final String TYPE = "string";
    private final Set<String> mGeneratedNames = new HashSet();
    private final Set<Integer> mSkipIds = new HashSet();
    private final TableBlock tableBlock;
    private static final Pattern PATTERN_EN = Pattern.compile("^[A-Za-z]{2,15}(_[A-Za-z]{1,15})*[0-9]*$");
    private static final Pattern PATTERN_PATH = Pattern.compile("^M[0-9.]+[\\s,]+[0-9\\-ACLHMSVZaclhmsvz,.\\s]+$");
    private static final Pattern PATTERN_URL = Pattern.compile("^(https?://)(www\\.)?([^/]+)(/.*)?$");
    private static final Pattern PATTERN_END_NUMBER = Pattern.compile("^([^0-9]+)([0-9]+)\\s*([^a-zA-Z0-9]{0,2})\\s*$");
    private static final Pattern PATTERN_NUMBER = Pattern.compile("^[0-9]+$");

    public StringValueNameGenerator(TableBlock tableBlock) {
        this.tableBlock = tableBlock;
    }

    public void refactor() {
        Map<Integer, ResourceEntry> mapMapResourceEntries = mapResourceEntries();
        for (Map.Entry<Integer, String> entry : generate().entrySet()) {
            mapMapResourceEntries.get(entry.getKey()).setName(entry.getValue());
        }
    }

    private boolean isGenerated(ResourceEntry resourceEntry) {
        return RefactorUtil.generateUniqueName(resourceEntry.getType(), resourceEntry.getResourceId()).equals(resourceEntry.getName());
    }

    private Map<Integer, String> generate() {
        Entry englishOrDefault;
        String strGenerate;
        this.mGeneratedNames.clear();
        this.mSkipIds.clear();
        HashMap map = new HashMap();
        Set<Integer> set = this.mSkipIds;
        List<ResourceEntry> listListResources = listResources();
        for (ResourceEntry resourceEntry : listListResources) {
            if (!isGenerated(resourceEntry)) {
                set.add(Integer.valueOf(resourceEntry.getResourceId()));
            }
        }
        for (ResourceEntry resourceEntry2 : listListResources) {
            int resourceId = resourceEntry2.getResourceId();
            if (!map.containsKey(Integer.valueOf(resourceId)) && !set.contains(Integer.valueOf(resourceId)) && (englishOrDefault = getEnglishOrDefault(resourceEntry2)) != null && (strGenerate = generate(resourceId, ((ResTableEntry) englishOrDefault.getTableEntry()).getValue().getValueAsString())) != null) {
                map.put(Integer.valueOf(resourceId), strGenerate);
                this.mGeneratedNames.add(strGenerate);
            }
        }
        return map;
    }

    private Entry getEnglishOrDefault(ResourceEntry resourceEntry) {
        Entry entry = null;
        for (Entry entry2 : resourceEntry) {
            if (entry2 != null) {
                TableEntry<?, ?> tableEntry = entry2.getTableEntry();
                if ((tableEntry instanceof ResTableEntry) && ((ResTableEntry) tableEntry).getValue().getValueType() == ValueType.STRING) {
                    if (entry2.getResConfig().isDefault()) {
                        entry = entry2;
                    }
                    String language = entry2.getResConfig().getLanguage();
                    if (language != null && language.equals(LanguageCodeUtil.EN)) {
                        return entry2;
                    }
                }
            }
        }
        return entry;
    }

    private List<ResourceEntry> listResources() {
        return new ArrayList(mapResourceEntries().values());
    }

    private Map<Integer, ResourceEntry> mapResourceEntries() {
        HashMap map = new HashMap();
        Iterator<PackageBlock> it = this.tableBlock.listPackages().iterator();
        while (it.hasNext()) {
            SpecTypePair specTypePair = it.next().getSpecTypePair("string");
            if (specTypePair != null) {
                Iterator<ResourceEntry> resources = specTypePair.getResources();
                while (resources.hasNext()) {
                    ResourceEntry next = resources.next();
                    if (!next.isEmpty()) {
                        map.put(Integer.valueOf(next.getResourceId()), next);
                    }
                }
            }
        }
        return map;
    }

    private String generate(int i, String str) {
        String strGenerateEnglish;
        if (str == null || (strGenerateEnglish = generateEnglish(str)) == null) {
            return null;
        }
        if (!this.mGeneratedNames.contains(strGenerateEnglish)) {
            return strGenerateEnglish;
        }
        String str2 = strGenerateEnglish + "_" + String.format("%04x", Integer.valueOf(i & 65535));
        if (!this.mGeneratedNames.contains(str2)) {
            return str2;
        }
        for (int i2 = 0; i2 < 10; i2++) {
            String str3 = str2 + "_" + i2;
            if (!this.mGeneratedNames.contains(str3)) {
                return str3;
            }
        }
        return null;
    }

    private String generateEnglish(String str) {
        String pathDataName = getPathDataName(str);
        if (pathDataName == null) {
            pathDataName = getUrlName(str);
        }
        return pathDataName == null ? getDefaultName(str) : pathDataName;
    }

    private String getPathDataName(String str) {
        if (PATTERN_PATH.matcher(str).find()) {
            return PATH_DATA_NAME;
        }
        return null;
    }

    private String getUrlName(String str) {
        Matcher matcher = PATTERN_URL.matcher(str);
        if (!matcher.find()) {
            return null;
        }
        StringBuilder sb = new StringBuilder("url_");
        String strReplace = matcher.group(3).replace('.', '/');
        String strGroup = matcher.group(4);
        if (strGroup != null) {
            strReplace = strReplace + strGroup.replace(MangleConstant.Q_MARK, '/').replace('=', '/');
        }
        int i = 0;
        boolean z = false;
        for (String str2 : strReplace.split("/")) {
            if (isAToZName(str2)) {
                int length = str2.length() + i;
                if (length > 40) {
                    if (z) {
                        break;
                    }
                } else {
                    if (z) {
                        sb.append('_');
                    }
                    sb.append(str2);
                    z = true;
                    i = length;
                }
            }
        }
        if (z) {
            return sb.toString();
        }
        return null;
    }

    private String getDefaultName(String str) {
        String[] strArrSplit = replaceNonAZ(str.replaceAll(FormatHelper.QUOTE, "").replaceAll("%([0-9]+\\$)?s", " STR ").replaceAll("%([0-9]+\\$)?d", " NUM ").replaceAll("&amp;", " and ").replaceAll("&(lt|gt);", " ")).split("[\\s]+");
        StringBuilder sb = new StringBuilder();
        int length = strArrSplit.length;
        int i = 0;
        boolean z = false;
        for (int i2 = 0; i2 < length; i2++) {
            String lowerCase = strArrSplit[i2];
            if (isAToZName(lowerCase) || (i2 + 1 >= length && isNumber(lowerCase) && z)) {
                int length2 = lowerCase.length();
                if (i + length2 > 40) {
                    if (z) {
                        break;
                    }
                } else {
                    if (z) {
                        sb.append('_');
                        i++;
                    } else {
                        lowerCase = lowerCase.toLowerCase();
                    }
                    sb.append(lowerCase);
                    i += length2;
                    z = true;
                }
            }
        }
        if (!z || i < 3) {
            return null;
        }
        return sb.toString();
    }

    private String replaceNonAZ(String str) {
        String strGroup;
        Matcher matcher = PATTERN_END_NUMBER.matcher(str);
        if (matcher.find()) {
            str = matcher.group(1);
            strGroup = matcher.group(2);
        } else {
            strGroup = null;
        }
        String strReplaceAll = str.replaceAll("[^A-Za-z]+", " ");
        StringBuilder sb = new StringBuilder();
        sb.append(strReplaceAll);
        if (strGroup != null) {
            sb.append(StringUtil.SPACE);
            sb.append(strGroup);
        }
        sb.append(StringUtil.SPACE);
        return sb.toString();
    }

    private static boolean isAToZName(String str) {
        return PATTERN_EN.matcher(str).find();
    }

    private static boolean isNumber(String str) {
        return PATTERN_NUMBER.matcher(str).find();
    }
}
