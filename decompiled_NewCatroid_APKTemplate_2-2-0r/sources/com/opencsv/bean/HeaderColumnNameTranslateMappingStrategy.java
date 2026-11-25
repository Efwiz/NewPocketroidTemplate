package com.opencsv.bean;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class HeaderColumnNameTranslateMappingStrategy<T> extends HeaderNameBaseMappingStrategy<T> {
    private final Map<String, String> columnMapping = new HashMap();

    @Override // com.opencsv.bean.AbstractMappingStrategy
    public String getColumnName(int i) {
        String byPosition = this.headerIndex.getByPosition(i);
        return byPosition != null ? this.columnMapping.get(byPosition.toUpperCase()) : byPosition;
    }

    public Map<String, String> getColumnMapping() {
        return this.columnMapping;
    }

    public void setColumnMapping(Map<String, String> map) {
        this.columnMapping.clear();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.columnMapping.put(entry.getKey().toUpperCase(), entry.getValue());
        }
        if (getType() != null) {
            loadFieldMap();
        }
    }
}
