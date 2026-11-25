package org.catrobat.catroid.io;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.UserDataHashMap;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/XStreamUserDataHashMapConverter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class XStreamUserDataHashMapConverter implements Converter {
    private static final String CATEGORY = "category";
    private static final String USERDATA = "userData";

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class type) {
        return type.equals(UserDataHashMap.class);
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object object, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        UserDataHashMap userDataHashMap = (UserDataHashMap) object;
        for (Brick.BrickData brickData : userDataHashMap.keySet()) {
            hierarchicalStreamWriter.startNode(USERDATA);
            hierarchicalStreamWriter.addAttribute(CATEGORY, brickData.toString());
            if (userDataHashMap.get(brickData) != null) {
                marshallingContext.convertAnother(userDataHashMap.get(brickData));
            }
            hierarchicalStreamWriter.endNode();
        }
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Object objConvertAnother;
        UserDataHashMap userDataHashMap = new UserDataHashMap();
        while (hierarchicalStreamReader.hasMoreChildren()) {
            hierarchicalStreamReader.moveDown();
            Brick.BrickData brickDataValueOf = Brick.BrickData.valueOf(hierarchicalStreamReader.getAttribute(CATEGORY));
            if (Brick.BrickData.isUserList(brickDataValueOf)) {
                objConvertAnother = unmarshallingContext.convertAnother(userDataHashMap, UserList.class);
            } else {
                objConvertAnother = unmarshallingContext.convertAnother(userDataHashMap, UserVariable.class);
            }
            hierarchicalStreamReader.moveUp();
            userDataHashMap.put(brickDataValueOf, (UserData) objConvertAnother);
        }
        return userDataHashMap;
    }
}
