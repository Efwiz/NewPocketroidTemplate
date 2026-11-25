package com.reandroid.apk;

import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.StagedAlias;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.value.ResConfig;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class TableBlockJsonBuilder {
    public TableBlock scanDirectory(File file) throws IOException {
        if (!file.isDirectory()) {
            throw new IOException("No such directory: " + file);
        }
        List<File> listListPackageDirectories = ApkUtil.listPackageDirectories(file);
        if (listListPackageDirectories.size() == 0) {
            throw new IOException("No package sub directory found in : " + file);
        }
        TableBlock tableBlock = new TableBlock();
        Iterator<File> it = listListPackageDirectories.iterator();
        while (it.hasNext()) {
            scanPackageDirectory(tableBlock, it.next());
        }
        tableBlock.sortPackages();
        tableBlock.refresh();
        return tableBlock;
    }

    private void scanPackageDirectory(TableBlock tableBlock, File file) throws IOException {
        File file2 = new File(file, PackageBlock.JSON_FILE_NAME);
        if (!file2.isFile()) {
            throw new IOException("Invalid package directory! Package file missing: " + file2);
        }
        JSONObject jSONObject = new JSONObject(file2);
        PackageBlock orCreate = tableBlock.getPackageArray().getOrCreate(jSONObject.getInt(PackageBlock.NAME_package_id));
        orCreate.setName(jSONObject.optString(PackageBlock.NAME_package_name));
        if (jSONObject.has(PackageBlock.NAME_staged_aliases)) {
            JSONArray jSONArray = jSONObject.getJSONArray(PackageBlock.NAME_staged_aliases);
            StagedAlias stagedAlias = new StagedAlias();
            stagedAlias.getStagedAliasEntryArray().fromJson(jSONArray);
            orCreate.getStagedAliasList().add(stagedAlias);
        }
        List<File> listListFiles = ApkUtil.listFiles(file, ApkUtil.JSON_FILE_EXTENSION);
        listListFiles.remove(file2);
        Iterator<File> it = listListFiles.iterator();
        while (it.hasNext()) {
            loadType(orCreate, it.next());
        }
        orCreate.sortTypes();
    }

    private void loadType(PackageBlock packageBlock, File file) throws IOException {
        JSONObject jSONObject = new JSONObject(file);
        ResConfig resConfig = new ResConfig();
        resConfig.fromJson(jSONObject.getJSONObject("config"));
        packageBlock.getSpecTypePairArray().getOrCreate((byte) (jSONObject.getInt("id") & 255), resConfig).fromJson(jSONObject);
    }
}
