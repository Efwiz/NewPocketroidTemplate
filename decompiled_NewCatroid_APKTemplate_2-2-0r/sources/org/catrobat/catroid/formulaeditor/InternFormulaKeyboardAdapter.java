package org.catrobat.catroid.formulaeditor;

import java.util.LinkedList;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/InternFormulaKeyboardAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class InternFormulaKeyboardAdapter {
    public static final int FORMULA_EDITOR_COLLIDE_RESOURCE_ID = 3;
    public static final int FORMULA_EDITOR_USER_DEFINED_BRICK_INPUT_RESOURCE_ID = 2;
    public static final int FORMULA_EDITOR_USER_LIST_RESOURCE_ID = 1;
    public static final int FORMULA_EDITOR_USER_VARIABLE_RESOURCE_ID = 0;
    public final int CUSTOM_JS_FUNCTION_RESOURCE_ID = -100;

    public List<InternToken> createInternTokenListByResourceId(int resource, String name2) {
        if (resource == 0 && !name2.isEmpty()) {
            return buildUserVariable(name2);
        }
        if (resource == 1 && !name2.isEmpty()) {
            return buildUserList(name2);
        }
        if (resource == 2 && !name2.isEmpty()) {
            return buildUserDefinedBrickInput(name2);
        }
        if (resource == 3 && !name2.isEmpty()) {
            return buildCollideWithFormula(name2);
        }
        if (resource == R.id.formula_editor_keyboard_string) {
            return buildString(name2);
        }
        switch (resource) {
            case -100:
                return buildCustomJsFunction(name2);
            case R.id.formula_editor_keyboard_decimal_mark /* 2131364460 */:
                return buildPeriod();
            case R.id.formula_editor_keyboard_divide /* 2131364462 */:
                return buildOperator(Operators.DIVIDE);
            case R.id.formula_editor_keyboard_plus /* 2131364470 */:
                return buildOperator(Operators.PLUS);
            case R.string.formula_3d_distance /* 2131888253 */:
                return buildDoubleParameterFunction(Functions.GET_3D_DISTANCE, InternTokenType.STRING, "object1", InternTokenType.STRING, "object2");
            case R.string.formula_3d_intersects /* 2131888255 */:
                return buildDoubleParameterFunction(Functions.OBJECT_INTERSECTS_OBJECT, InternTokenType.STRING, "player", InternTokenType.STRING, "trigger_zone");
            case R.string.formula_3d_touches /* 2131888267 */:
                return buildDoubleParameterFunction(Functions.OBJECT_TOUCHES_OBJECT, InternTokenType.STRING, "object1", InternTokenType.STRING, "object2");
            case R.string.formula_3d_velo_x /* 2131888269 */:
                return buildSingleParameterFunction(Functions.GET_3D_VELOCITY_X, InternTokenType.STRING, "myObject");
            case R.string.formula_3d_velo_y /* 2131888270 */:
                return buildSingleParameterFunction(Functions.GET_3D_VELOCITY_Y, InternTokenType.STRING, "myObject");
            case R.string.formula_3d_velo_z /* 2131888271 */:
                return buildSingleParameterFunction(Functions.GET_3D_VELOCITY_Z, InternTokenType.STRING, "myObject");
            case R.string.formula_cam_dir_x /* 2131888272 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.GET_CAMERA_DIR_X);
            case R.string.formula_cam_dir_y /* 2131888273 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.GET_CAMERA_DIR_Y);
            case R.string.formula_cam_dir_z /* 2131888274 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.GET_CAMERA_DIR_Z);
            case R.string.formula_cam_pos_x /* 2131888275 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.GET_CAMERA_POS_X);
            case R.string.formula_cam_pos_y /* 2131888276 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.GET_CAMERA_POS_Y);
            case R.string.formula_cam_pos_z /* 2131888277 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.GET_CAMERA_POS_Z);
            case R.string.formula_cam_rot_pitch /* 2131888278 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.GET_CAMERA_ROTATION_PITCH);
            case R.string.formula_cam_rot_roll /* 2131888279 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.GET_CAMERA_ROTATION_ROLL);
            case R.string.formula_cam_rot_yaw /* 2131888280 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.GET_CAMERA_ROTATION_YAW);
            case R.string.formula_camera_rotation /* 2131888281 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.CAMERA_ROTATION);
            case R.string.formula_camera_x /* 2131888282 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.CAMERA_X);
            case R.string.formula_camera_y /* 2131888283 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.CAMERA_Y);
            case R.string.formula_camera_zoom /* 2131888284 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.CAMERA_ZOOM);
            case R.string.formula_clipboard_text /* 2131888285 */:
                return buildSensor(Sensors.CLIPBOARD_TEXT);
            case R.string.formula_collision_list /* 2131888286 */:
                return buildSingleParameterFunction(Functions.COLLISION_LIST, InternTokenType.STRING, "myObject");
            case R.string.formula_delta /* 2131888287 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.DELTA);
            case R.string.formula_editor_function_abs /* 2131888323 */:
                return buildSingleParameterFunction(Functions.ABS, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_arcsin /* 2131888328 */:
                return buildSingleParameterFunction(Functions.ARCSIN, InternTokenType.NUMBER, "0.5");
            case R.string.formula_editor_function_clamp /* 2131888338 */:
                return buildTripleParameterFunction(Functions.CLAMP, InternTokenType.NUMBER, "-2", InternTokenType.NUMBER, "2", InternTokenType.NUMBER, "10");
            case R.string.formula_editor_function_collides_with_color /* 2131888340 */:
                return buildSingleParameterFunction(Functions.COLLIDES_WITH_COLOR, InternTokenType.STRING, "#ff0000");
            case R.string.formula_editor_function_collides_with_edge /* 2131888342 */:
                return buildObject(Sensors.COLLIDES_WITH_EDGE);
            case R.string.formula_editor_function_color_touches_color /* 2131888344 */:
                return buildDoubleParameterFunction(Functions.COLOR_TOUCHES_COLOR, InternTokenType.STRING, "#ff0000", InternTokenType.STRING, "#ff0000");
            case R.string.formula_editor_function_connect /* 2131888346 */:
                return buildDoubleParameterFunction(Functions.CONNECT, InternTokenType.STRING, "list_name", InternTokenType.STRING, ",");
            case R.string.formula_editor_function_contains /* 2131888348 */:
                return buildDoubleParameterFunction(Functions.CONTAINS, InternTokenType.USER_LIST, "list name", InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_contains_str /* 2131888350 */:
                return buildDoubleParameterFunction(Functions.CONTAINS_STR, InternTokenType.STRING, "bats are cutest!", InternTokenType.STRING, "cute");
            case R.string.formula_editor_function_cos /* 2131888352 */:
                return buildSingleParameterFunction(Functions.COS, InternTokenType.NUMBER, "360");
            case R.string.formula_editor_function_distan /* 2131888354 */:
                return buildFourParameterFunction(Functions.DISTAN, InternTokenType.NUMBER, "100", InternTokenType.NUMBER, "200", InternTokenType.NUMBER, "-100", InternTokenType.NUMBER, "-200");
            case R.string.formula_editor_function_distance /* 2131888356 */:
                return buildDoubleParameterFunction(Functions.DISTANCE, InternTokenType.STRING, "panda", InternTokenType.STRING, "lavanda");
            case R.string.formula_editor_function_exp /* 2131888358 */:
                return buildSingleParameterFunction(Functions.EXP, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_floatarray /* 2131888371 */:
                return buildSingleParameterFunction(Functions.FLOATARRAY, InternTokenType.STRING, "FloatArray");
            case R.string.formula_editor_function_floor /* 2131888373 */:
                return buildSingleParameterFunction(Functions.FLOOR, InternTokenType.NUMBER, "0.7");
            case R.string.formula_editor_function_get_id_of_detected_object /* 2131888375 */:
                return buildSingleParameterFunction(Functions.ID_OF_DETECTED_OBJECT, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_if_then_else /* 2131888376 */:
                return buildTripleParameterFunction(Functions.IF_THEN_ELSE, InternTokenType.FUNCTION_NAME, Functions.getFunctionByValue("FALSE").toString(), InternTokenType.NUMBER, "2", InternTokenType.NUMBER, "3");
            case R.string.formula_editor_function_index_of_current_touch /* 2131888378 */:
                return buildSingleParameterFunction(Functions.INDEX_CURRENT_TOUCH, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_index_of_item /* 2131888379 */:
                return buildDoubleParameterFunction(Functions.INDEX_OF_ITEM, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1, InternTokenType.USER_LIST, "list_name");
            case R.string.formula_editor_function_index_of_last_finger /* 2131888381 */:
                return buildSensor(Sensors.LAST_FINGER_INDEX);
            case R.string.formula_editor_function_is_finger_touching /* 2131888382 */:
                return buildSensor(Sensors.FINGER_TOUCHED);
            case R.string.formula_editor_function_is_mouse_button_down /* 2131888383 */:
                return buildSingleParameterFunction(Functions.IS_MOUSE_BUTTON_DOWN, InternTokenType.NUMBER, "0");
            case R.string.formula_editor_function_is_multi_finger_touching /* 2131888385 */:
                return buildSingleParameterFunction(Functions.MULTI_FINGER_TOUCHED, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_join /* 2131888386 */:
                return buildDoubleParameterFunction(Functions.JOIN, InternTokenType.STRING, "hello", InternTokenType.STRING, " world");
            case R.string.formula_editor_function_join3 /* 2131888387 */:
                return buildTripleParameterFunction(Functions.JOIN3, InternTokenType.STRING, "hello", InternTokenType.STRING, " world", InternTokenType.STRING, "!");
            case R.string.formula_editor_function_joinnumber /* 2131888390 */:
                return buildDoubleParameterFunction(Functions.JOINNUMBER, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_json_get /* 2131888392 */:
                return buildDoubleParameterFunction(Functions.JSON_GET, InternTokenType.STRING, "{\"id\": 1, \"data\": {\"value\": \"text\"}}", InternTokenType.STRING, "data.value");
            case R.string.formula_editor_function_json_is_valid /* 2131888394 */:
                return buildSingleParameterFunction(Functions.JSON_IS_VALID, InternTokenType.STRING, "{\"id\": 1}");
            case R.string.formula_editor_function_json_set /* 2131888396 */:
                return buildTripleParameterFunction(Functions.JSON_SET, InternTokenType.STRING, "{\"id\": 1}", InternTokenType.STRING, "id", InternTokenType.NUMBER, "2");
            case R.string.formula_editor_function_length /* 2131888398 */:
                return buildSingleParameterFunction(Functions.LENGTH, InternTokenType.STRING, "hello world");
            case R.string.formula_editor_function_letter /* 2131888400 */:
                return buildDoubleParameterFunction(Functions.LETTER, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1, InternTokenType.STRING, "hello world");
            case R.string.formula_editor_function_list_item /* 2131888402 */:
                return buildDoubleParameterFunction(Functions.LIST_ITEM, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1, InternTokenType.USER_LIST, "list name");
            case R.string.formula_editor_function_ln /* 2131888404 */:
                return buildSingleParameterFunction(Functions.LN, InternTokenType.NUMBER, "2.718281828459");
            case R.string.formula_editor_function_log /* 2131888406 */:
                return buildSingleParameterFunction(Functions.LOG, InternTokenType.NUMBER, "10");
            case R.string.formula_editor_function_lower /* 2131888408 */:
                return buildSingleParameterFunction(Functions.LOWER, InternTokenType.STRING, "SIGMA");
            case R.string.formula_editor_function_lua /* 2131888410 */:
                return buildSingleParameterFunction(Functions.LUA, InternTokenType.STRING, "return math.sqrt(25)");
            case R.string.formula_editor_function_max /* 2131888412 */:
                return buildDoubleParameterFunction(Functions.MAX, InternTokenType.NUMBER, "5", InternTokenType.NUMBER, "4");
            case R.string.formula_editor_function_min /* 2131888414 */:
                return buildDoubleParameterFunction(Functions.MIN, InternTokenType.NUMBER, "7", InternTokenType.NUMBER, "2");
            case R.string.formula_editor_function_mod /* 2131888416 */:
                return buildDoubleParameterFunction(Functions.MOD, InternTokenType.NUMBER, "3", InternTokenType.NUMBER, "2");
            case R.string.formula_editor_function_multi_finger_x /* 2131888418 */:
                return buildSingleParameterFunction(Functions.MULTI_FINGER_X, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_multi_finger_y /* 2131888419 */:
                return buildSingleParameterFunction(Functions.MULTI_FINGER_Y, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_number_of_current_touches /* 2131888421 */:
                return buildSensor(Sensors.NUMBER_CURRENT_TOUCHES);
            case R.string.formula_editor_function_number_of_items /* 2131888422 */:
                return buildSingleParameterFunction(Functions.NUMBER_OF_ITEMS, InternTokenType.USER_LIST, "list name");
            case R.string.formula_editor_function_object_with_id_visible /* 2131888424 */:
                return buildSingleParameterFunction(Functions.OBJECT_WITH_ID_VISIBLE, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_pi /* 2131888425 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.PI);
            case R.string.formula_editor_function_power /* 2131888428 */:
                return buildDoubleParameterFunction(Functions.POWER, InternTokenType.NUMBER, "2", InternTokenType.NUMBER, "3");
            case R.string.formula_editor_function_rand /* 2131888430 */:
                return buildDoubleParameterFunction(Functions.RAND, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1, InternTokenType.NUMBER, "6");
            case R.string.formula_editor_function_random_str /* 2131888432 */:
                return buildSingleParameterFunction(Functions.RANDOM_STR, InternTokenType.NUMBER, "15");
            case R.string.formula_editor_function_raspi_read_pin_value_digital /* 2131888434 */:
                return buildSingleParameterFunction(Functions.RASPIDIGITAL, InternTokenType.NUMBER, "0");
            case R.string.formula_editor_function_regex /* 2131888435 */:
                return buildDoubleParameterFunction(Functions.REGEX, InternTokenType.STRING, " an? ([^ .]+)", InternTokenType.STRING, "I am a panda.");
            case R.string.formula_editor_function_repeat /* 2131888440 */:
                return buildDoubleParameterFunction(Functions.REPEAT, InternTokenType.STRING, "повторение - признак безумия ", InternTokenType.NUMBER, "10");
            case R.string.formula_editor_function_replace /* 2131888442 */:
                return buildTripleParameterFunction(Functions.REPLACE, InternTokenType.STRING, "pandas love bamboo", InternTokenType.STRING, "love", InternTokenType.STRING, "eat");
            case R.string.formula_editor_function_reverse /* 2131888444 */:
                return buildSingleParameterFunction(Functions.REVERSE, InternTokenType.STRING, "olleH");
            case R.string.formula_editor_function_round /* 2131888446 */:
                return buildSingleParameterFunction(Functions.ROUND, InternTokenType.NUMBER, CompilerOptions.VERSION_1_6);
            case R.string.formula_editor_function_roundto /* 2131888448 */:
                return buildDoubleParameterFunction(Functions.ROUNDTO, InternTokenType.NUMBER, "123.456", InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_sin /* 2131888450 */:
                return buildSingleParameterFunction(Functions.SIN, InternTokenType.NUMBER, "90");
            case R.string.formula_editor_function_sqrt /* 2131888452 */:
                return buildSingleParameterFunction(Functions.SQRT, InternTokenType.NUMBER, "4");
            case R.string.formula_editor_function_subtext /* 2131888454 */:
                return buildTripleParameterFunction(Functions.SUBTEXT, InternTokenType.NUMBER, "3", InternTokenType.NUMBER, "5", InternTokenType.STRING, "hello world");
            case R.string.formula_editor_function_table_element /* 2131888456 */:
                return buildTripleParameterFunction(Functions.TABLE_ELEMENT, InternTokenType.STRING, "myTable", InternTokenType.NUMBER, "3", InternTokenType.NUMBER, "2");
            case R.string.formula_editor_function_table_join /* 2131888458 */:
                return buildTripleParameterFunction(Functions.TABLE_JOIN, InternTokenType.STRING, "myTable", InternTokenType.STRING, ",", InternTokenType.STRING, "\n");
            case R.string.formula_editor_function_table_x /* 2131888460 */:
                return buildSingleParameterFunction(Functions.TABLE_X, InternTokenType.STRING, "myTable");
            case R.string.formula_editor_function_table_y /* 2131888462 */:
                return buildSingleParameterFunction(Functions.TABLE_Y, InternTokenType.STRING, "myTable");
            case R.string.formula_editor_function_tan /* 2131888464 */:
                return buildSingleParameterFunction(Functions.TAN, InternTokenType.NUMBER, "45");
            case R.string.formula_editor_function_text_block_from_camera /* 2131888466 */:
                return buildSingleParameterFunction(Functions.TEXT_BLOCK_FROM_CAMERA, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_text_block_language_from_camera /* 2131888467 */:
                return buildSingleParameterFunction(Functions.TEXT_BLOCK_LANGUAGE_FROM_CAMERA, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_text_block_size /* 2131888469 */:
                return buildSingleParameterFunction(Functions.TEXT_BLOCK_SIZE, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_text_block_x /* 2131888470 */:
                return buildSingleParameterFunction(Functions.TEXT_BLOCK_X, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_text_block_y /* 2131888471 */:
                return buildSingleParameterFunction(Functions.TEXT_BLOCK_Y, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_function_to_dec /* 2131888472 */:
                return buildSingleParameterFunction(Functions.TO_DEC, InternTokenType.STRING, "AF");
            case R.string.formula_editor_function_to_hex /* 2131888474 */:
                return buildSingleParameterFunction(Functions.TO_HEX, InternTokenType.NUMBER, "175");
            case R.string.formula_editor_function_touched /* 2131888477 */:
                return buildObject(Sensors.COLLIDES_WITH_FINGER);
            case R.string.formula_editor_function_touches_object_by_name /* 2131888478 */:
                return buildSingleParameterFunction(Functions.TOUCHES_OBJECT_BY_NAME, InternTokenType.STRING, "object name");
            case R.string.formula_editor_function_true /* 2131888480 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.TRUE);
            case R.string.formula_editor_function_upper /* 2131888481 */:
                return buildSingleParameterFunction(Functions.UPPER, InternTokenType.STRING, "aboudna");
            case R.string.formula_editor_function_var /* 2131888483 */:
                return buildSingleParameterFunction(Functions.VAR, InternTokenType.STRING, "variable1");
            case R.string.formula_editor_function_var_name /* 2131888484 */:
                return buildSingleParameterFunction(Functions.VARNAME, InternTokenType.NUMBER, "0");
            case R.string.formula_editor_function_var_value /* 2131888487 */:
                return buildSingleParameterFunction(Functions.VARVALUE, InternTokenType.NUMBER, "0");
            case R.string.formula_editor_listening_language_sensor /* 2131888515 */:
                return buildSensor(Sensors.SPEECH_RECOGNITION_LANGUAGE);
            case R.string.formula_editor_logic_and /* 2131888517 */:
                return buildOperator(Operators.LOGICAL_AND);
            case R.string.formula_editor_logic_equal /* 2131888520 */:
                return buildOperator(Operators.EQUAL);
            case R.string.formula_editor_logic_greaterequal /* 2131888521 */:
                return buildOperator(Operators.GREATER_OR_EQUAL);
            case R.string.formula_editor_logic_greaterthan /* 2131888522 */:
                return buildOperator(Operators.GREATER_THAN);
            case R.string.formula_editor_logic_leserequal /* 2131888523 */:
                return buildOperator(Operators.SMALLER_OR_EQUAL);
            case R.string.formula_editor_logic_lesserthan /* 2131888524 */:
                return buildOperator(Operators.SMALLER_THAN);
            case R.string.formula_editor_logic_not /* 2131888525 */:
                return buildOperator(Operators.LOGICAL_NOT);
            case R.string.formula_editor_logic_notequal /* 2131888526 */:
                return buildOperator(Operators.NOT_EQUAL);
            case R.string.formula_editor_logic_or /* 2131888527 */:
                return buildOperator(Operators.LOGICAL_OR);
            case R.string.formula_editor_nfc_tag_id /* 2131888529 */:
                return buildSensor(Sensors.NFC_TAG_ID);
            case R.string.formula_editor_nfc_tag_message /* 2131888530 */:
                return buildSensor(Sensors.NFC_TAG_MESSAGE);
            case R.string.formula_editor_object_angular_velocity /* 2131888532 */:
                return buildObject(Sensors.OBJECT_ANGULAR_VELOCITY);
            case R.string.formula_editor_object_background_name /* 2131888533 */:
                return buildObject(Sensors.OBJECT_BACKGROUND_NAME);
            case R.string.formula_editor_object_background_number /* 2131888534 */:
                return buildObject(Sensors.OBJECT_BACKGROUND_NUMBER);
            case R.string.formula_editor_object_brightness /* 2131888535 */:
                return buildObject(Sensors.OBJECT_BRIGHTNESS);
            case R.string.formula_editor_object_color /* 2131888536 */:
                return buildObject(Sensors.OBJECT_COLOR);
            case R.string.formula_editor_object_distance_to /* 2131888537 */:
                return buildObject(Sensors.OBJECT_DISTANCE_TO);
            case R.string.formula_editor_object_height /* 2131888539 */:
                return buildObject(Sensors.OBJECT_HEIGHT);
            case R.string.formula_editor_object_layer /* 2131888540 */:
                return buildObject(Sensors.OBJECT_LAYER);
            case R.string.formula_editor_object_look_height /* 2131888542 */:
                return buildObject(Sensors.OBJECT_LOOK_HEIGHT);
            case R.string.formula_editor_object_look_name /* 2131888543 */:
                return buildObject(Sensors.OBJECT_LOOK_NAME);
            case R.string.formula_editor_object_look_number /* 2131888544 */:
                return buildObject(Sensors.OBJECT_LOOK_NUMBER);
            case R.string.formula_editor_object_look_width /* 2131888545 */:
                return buildObject(Sensors.OBJECT_LOOK_WIDTH);
            case R.string.formula_editor_object_number_of_backgrounds /* 2131888547 */:
            case R.string.formula_editor_object_number_of_looks /* 2131888548 */:
                return buildObject(Sensors.OBJECT_NUMBER_OF_LOOKS);
            case R.string.formula_editor_object_rotation /* 2131888549 */:
                return buildObject(Sensors.MOTION_DIRECTION);
            case R.string.formula_editor_object_rotation_look /* 2131888550 */:
                return buildObject(Sensors.LOOK_DIRECTION);
            case R.string.formula_editor_object_size /* 2131888551 */:
                return buildObject(Sensors.OBJECT_SIZE);
            case R.string.formula_editor_object_transparency /* 2131888552 */:
                return buildObject(Sensors.OBJECT_TRANSPARENCY);
            case R.string.formula_editor_object_width /* 2131888553 */:
                return buildObject(Sensors.OBJECT_WIDTH);
            case R.string.formula_editor_object_x /* 2131888554 */:
                return buildObject(Sensors.OBJECT_X);
            case R.string.formula_editor_object_x_velocity /* 2131888555 */:
                return buildObject(Sensors.OBJECT_X_VELOCITY);
            case R.string.formula_editor_object_y /* 2131888556 */:
                return buildObject(Sensors.OBJECT_Y);
            case R.string.formula_editor_object_y_velocity /* 2131888557 */:
                return buildObject(Sensors.OBJECT_Y_VELOCITY);
            case R.string.formula_editor_operator_power /* 2131888562 */:
                return buildOperator(Operators.POW);
            case R.string.formula_editor_phiro_sensor_bottom_left /* 2131888565 */:
                return buildSensor(Sensors.PHIRO_BOTTOM_LEFT);
            case R.string.formula_editor_phiro_sensor_bottom_right /* 2131888566 */:
                return buildSensor(Sensors.PHIRO_BOTTOM_RIGHT);
            case R.string.formula_editor_phiro_sensor_front_left /* 2131888567 */:
                return buildSensor(Sensors.PHIRO_FRONT_LEFT);
            case R.string.formula_editor_phiro_sensor_front_right /* 2131888568 */:
                return buildSensor(Sensors.PHIRO_FRONT_RIGHT);
            case R.string.formula_editor_phiro_sensor_side_left /* 2131888569 */:
                return buildSensor(Sensors.PHIRO_SIDE_LEFT);
            case R.string.formula_editor_phiro_sensor_side_right /* 2131888570 */:
                return buildSensor(Sensors.PHIRO_SIDE_RIGHT);
            case R.string.formula_editor_sensor_altitude /* 2131888572 */:
                return buildSensor(Sensors.ALTITUDE);
            case R.string.formula_editor_sensor_architecture /* 2131888573 */:
                return buildSensor(Sensors.ARCH);
            case R.string.formula_editor_sensor_battary /* 2131888574 */:
                return buildSensor(Sensors.BATTARY);
            case R.string.formula_editor_sensor_color_at_x_y /* 2131888575 */:
                return buildDoubleParameterFunction(Functions.COLOR_AT_XY, InternTokenType.NUMBER, "100", InternTokenType.NUMBER, "200");
            case R.string.formula_editor_sensor_color_equals_color /* 2131888577 */:
                return buildTripleParameterFunction(Functions.COLOR_EQUALS_COLOR, InternTokenType.STRING, "#ff0000", InternTokenType.STRING, "#fe0000", InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_editor_sensor_compass_direction /* 2131888579 */:
                return buildSensor(Sensors.COMPASS_DIRECTION);
            case R.string.formula_editor_sensor_date_day /* 2131888580 */:
                return buildSensor(Sensors.DATE_DAY);
            case R.string.formula_editor_sensor_date_month /* 2131888581 */:
                return buildSensor(Sensors.DATE_MONTH);
            case R.string.formula_editor_sensor_date_weekday /* 2131888582 */:
                return buildSensor(Sensors.DATE_WEEKDAY);
            case R.string.formula_editor_sensor_date_year /* 2131888583 */:
                return buildSensor(Sensors.DATE_YEAR);
            case R.string.formula_editor_sensor_drone_battery_status /* 2131888584 */:
                return buildSensor(Sensors.DRONE_BATTERY_STATUS);
            case R.string.formula_editor_sensor_drone_camera_ready /* 2131888585 */:
                return buildSensor(Sensors.DRONE_CAMERA_READY);
            case R.string.formula_editor_sensor_drone_emergency_state /* 2131888586 */:
                return buildSensor(Sensors.DRONE_EMERGENCY_STATE);
            case R.string.formula_editor_sensor_drone_flying /* 2131888587 */:
                return buildSensor(Sensors.DRONE_FLYING);
            case R.string.formula_editor_sensor_drone_initialized /* 2131888588 */:
                return buildSensor(Sensors.DRONE_INITIALIZED);
            case R.string.formula_editor_sensor_drone_num_frames /* 2131888589 */:
                return buildSensor(Sensors.DRONE_NUM_FRAMES);
            case R.string.formula_editor_sensor_drone_record_ready /* 2131888590 */:
                return buildSensor(Sensors.DRONE_RECORD_READY);
            case R.string.formula_editor_sensor_drone_recording /* 2131888591 */:
                return buildSensor(Sensors.DRONE_RECORDING);
            case R.string.formula_editor_sensor_drone_usb_active /* 2131888592 */:
                return buildSensor(Sensors.DRONE_USB_ACTIVE);
            case R.string.formula_editor_sensor_drone_usb_remaining_time /* 2131888593 */:
                return buildSensor(Sensors.DRONE_USB_REMAINING_TIME);
            case R.string.formula_editor_sensor_face_detected /* 2131888594 */:
                return buildSensor(Sensors.FACE_DETECTED);
            case R.string.formula_editor_sensor_face_size /* 2131888595 */:
                return buildSensor(Sensors.FACE_SIZE);
            case R.string.formula_editor_sensor_face_x_position /* 2131888596 */:
                return buildSensor(Sensors.FACE_X);
            case R.string.formula_editor_sensor_face_y_position /* 2131888597 */:
                return buildSensor(Sensors.FACE_Y);
            case R.string.formula_editor_sensor_fps /* 2131888598 */:
                return buildSensor(Sensors.FPS);
            case R.string.formula_editor_sensor_gamepad_a_pressed /* 2131888600 */:
                return buildSensor(Sensors.GAMEPAD_A_PRESSED);
            case R.string.formula_editor_sensor_gamepad_b_pressed /* 2131888601 */:
                return buildSensor(Sensors.GAMEPAD_B_PRESSED);
            case R.string.formula_editor_sensor_gamepad_down_pressed /* 2131888602 */:
                return buildSensor(Sensors.GAMEPAD_DOWN_PRESSED);
            case R.string.formula_editor_sensor_gamepad_left_pressed /* 2131888603 */:
                return buildSensor(Sensors.GAMEPAD_LEFT_PRESSED);
            case R.string.formula_editor_sensor_gamepad_right_pressed /* 2131888604 */:
                return buildSensor(Sensors.GAMEPAD_RIGHT_PRESSED);
            case R.string.formula_editor_sensor_gamepad_up_pressed /* 2131888605 */:
                return buildSensor(Sensors.GAMEPAD_UP_PRESSED);
            case R.string.formula_editor_sensor_head_top_x /* 2131888606 */:
                return buildSensor(Sensors.HEAD_TOP_X);
            case R.string.formula_editor_sensor_head_top_y /* 2131888607 */:
                return buildSensor(Sensors.HEAD_TOP_Y);
            case R.string.formula_editor_sensor_internet /* 2131888608 */:
                return buildSensor(Sensors.INTERNET);
            case R.string.formula_editor_sensor_ip /* 2131888609 */:
                return buildSensor(Sensors.IP);
            case R.string.formula_editor_sensor_latitude /* 2131888610 */:
                return buildSensor(Sensors.LATITUDE);
            case R.string.formula_editor_sensor_left_ankle_x /* 2131888611 */:
                return buildSensor(Sensors.LEFT_ANKLE_X);
            case R.string.formula_editor_sensor_left_ankle_y /* 2131888612 */:
                return buildSensor(Sensors.LEFT_ANKLE_Y);
            case R.string.formula_editor_sensor_left_ear_x /* 2131888613 */:
                return buildSensor(Sensors.LEFT_EAR_X);
            case R.string.formula_editor_sensor_left_ear_y /* 2131888614 */:
                return buildSensor(Sensors.LEFT_EAR_Y);
            case R.string.formula_editor_sensor_left_elbow_x /* 2131888615 */:
                return buildSensor(Sensors.LEFT_ELBOW_X);
            case R.string.formula_editor_sensor_left_elbow_y /* 2131888616 */:
                return buildSensor(Sensors.LEFT_ELBOW_Y);
            case R.string.formula_editor_sensor_left_eye_center_x /* 2131888617 */:
                return buildSensor(Sensors.LEFT_EYE_CENTER_X);
            case R.string.formula_editor_sensor_left_eye_center_y /* 2131888618 */:
                return buildSensor(Sensors.LEFT_EYE_CENTER_Y);
            case R.string.formula_editor_sensor_left_eye_inner_x /* 2131888619 */:
                return buildSensor(Sensors.LEFT_EYE_INNER_X);
            case R.string.formula_editor_sensor_left_eye_inner_y /* 2131888620 */:
                return buildSensor(Sensors.LEFT_EYE_INNER_Y);
            case R.string.formula_editor_sensor_left_eye_outer_x /* 2131888621 */:
                return buildSensor(Sensors.LEFT_EYE_OUTER_X);
            case R.string.formula_editor_sensor_left_eye_outer_y /* 2131888622 */:
                return buildSensor(Sensors.LEFT_EYE_OUTER_Y);
            case R.string.formula_editor_sensor_left_foot_index_x /* 2131888623 */:
                return buildSensor(Sensors.LEFT_FOOT_INDEX_X);
            case R.string.formula_editor_sensor_left_foot_index_y /* 2131888624 */:
                return buildSensor(Sensors.LEFT_FOOT_INDEX_Y);
            case R.string.formula_editor_sensor_left_heel_x /* 2131888625 */:
                return buildSensor(Sensors.LEFT_HEEL_X);
            case R.string.formula_editor_sensor_left_heel_y /* 2131888626 */:
                return buildSensor(Sensors.LEFT_HEEL_Y);
            case R.string.formula_editor_sensor_left_hip_x /* 2131888627 */:
                return buildSensor(Sensors.LEFT_HIP_X);
            case R.string.formula_editor_sensor_left_hip_y /* 2131888628 */:
                return buildSensor(Sensors.LEFT_HIP_Y);
            case R.string.formula_editor_sensor_left_index_knuckle_x /* 2131888629 */:
                return buildSensor(Sensors.LEFT_INDEX_X);
            case R.string.formula_editor_sensor_left_index_knuckle_y /* 2131888630 */:
                return buildSensor(Sensors.LEFT_INDEX_Y);
            case R.string.formula_editor_sensor_left_knee_x /* 2131888631 */:
                return buildSensor(Sensors.LEFT_KNEE_X);
            case R.string.formula_editor_sensor_left_knee_y /* 2131888632 */:
                return buildSensor(Sensors.LEFT_KNEE_Y);
            case R.string.formula_editor_sensor_left_pinky_knuckle_x /* 2131888633 */:
                return buildSensor(Sensors.LEFT_PINKY_X);
            case R.string.formula_editor_sensor_left_pinky_knuckle_y /* 2131888634 */:
                return buildSensor(Sensors.LEFT_PINKY_Y);
            case R.string.formula_editor_sensor_left_shoulder_x /* 2131888635 */:
                return buildSensor(Sensors.LEFT_SHOULDER_X);
            case R.string.formula_editor_sensor_left_shoulder_y /* 2131888636 */:
                return buildSensor(Sensors.LEFT_SHOULDER_Y);
            case R.string.formula_editor_sensor_left_thumb_knuckle_x /* 2131888637 */:
                return buildSensor(Sensors.LEFT_THUMB_X);
            case R.string.formula_editor_sensor_left_thumb_knuckle_y /* 2131888638 */:
                return buildSensor(Sensors.LEFT_THUMB_Y);
            case R.string.formula_editor_sensor_left_wrist_x /* 2131888639 */:
                return buildSensor(Sensors.LEFT_WRIST_X);
            case R.string.formula_editor_sensor_left_wrist_y /* 2131888640 */:
                return buildSensor(Sensors.LEFT_WRIST_Y);
            case R.string.formula_editor_sensor_lego_ev3_1 /* 2131888641 */:
                return buildSensor(Sensors.EV3_SENSOR_1);
            case R.string.formula_editor_sensor_lego_ev3_2 /* 2131888642 */:
                return buildSensor(Sensors.EV3_SENSOR_2);
            case R.string.formula_editor_sensor_lego_ev3_3 /* 2131888643 */:
                return buildSensor(Sensors.EV3_SENSOR_3);
            case R.string.formula_editor_sensor_lego_ev3_4 /* 2131888644 */:
                return buildSensor(Sensors.EV3_SENSOR_4);
            case R.string.formula_editor_sensor_lego_nxt_1 /* 2131888657 */:
                return buildSensor(Sensors.NXT_SENSOR_1);
            case R.string.formula_editor_sensor_lego_nxt_2 /* 2131888658 */:
                return buildSensor(Sensors.NXT_SENSOR_2);
            case R.string.formula_editor_sensor_lego_nxt_3 /* 2131888659 */:
                return buildSensor(Sensors.NXT_SENSOR_3);
            case R.string.formula_editor_sensor_lego_nxt_4 /* 2131888660 */:
                return buildSensor(Sensors.NXT_SENSOR_4);
            case R.string.formula_editor_sensor_location_accuracy /* 2131888666 */:
                return buildSensor(Sensors.LOCATION_ACCURACY);
            case R.string.formula_editor_sensor_longitude /* 2131888667 */:
                return buildSensor(Sensors.LONGITUDE);
            case R.string.formula_editor_sensor_loudness /* 2131888668 */:
                return buildSensor(Sensors.LOUDNESS);
            case R.string.formula_editor_sensor_micro /* 2131888669 */:
                return buildSensor(Sensors.MICRO);
            case R.string.formula_editor_sensor_micro_freq /* 2131888670 */:
                return buildSensor(Sensors.FREQ);
            case R.string.formula_editor_sensor_mouse_delta_x /* 2131888671 */:
                return buildSensor(Sensors.MOUSE_DELTA_X);
            case R.string.formula_editor_sensor_mouse_delta_y /* 2131888672 */:
                return buildSensor(Sensors.MOUSE_DELTA_Y);
            case R.string.formula_editor_sensor_mouse_scroll /* 2131888673 */:
                return buildSensor(Sensors.MOUSE_SCROLL);
            case R.string.formula_editor_sensor_mouse_x /* 2131888674 */:
                return buildSensor(Sensors.MOUSE_X);
            case R.string.formula_editor_sensor_mouse_y /* 2131888675 */:
                return buildSensor(Sensors.MOUSE_Y);
            case R.string.formula_editor_sensor_mouth_left_corner_x /* 2131888676 */:
                return buildSensor(Sensors.MOUTH_LEFT_CORNER_X);
            case R.string.formula_editor_sensor_mouth_left_corner_y /* 2131888677 */:
                return buildSensor(Sensors.MOUTH_LEFT_CORNER_Y);
            case R.string.formula_editor_sensor_mouth_right_corner_x /* 2131888678 */:
                return buildSensor(Sensors.MOUTH_RIGHT_CORNER_X);
            case R.string.formula_editor_sensor_mouth_right_corner_y /* 2131888679 */:
                return buildSensor(Sensors.MOUTH_RIGHT_CORNER_Y);
            case R.string.formula_editor_sensor_neck_x /* 2131888680 */:
                return buildSensor(Sensors.NECK_X);
            case R.string.formula_editor_sensor_neck_y /* 2131888681 */:
                return buildSensor(Sensors.NECK_Y);
            case R.string.formula_editor_sensor_nfc_size /* 2131888682 */:
                return buildSensor(Sensors.NFC_MEMORY_SIZE);
            case R.string.formula_editor_sensor_nfc_techs /* 2131888683 */:
                return buildSensor(Sensors.NFC_TECH_LIST);
            case R.string.formula_editor_sensor_nfc_type /* 2131888684 */:
                return buildSensor(Sensors.NFC_TAG_TYPE);
            case R.string.formula_editor_sensor_nfc_writable /* 2131888685 */:
                return buildSensor(Sensors.NFC_IS_WRITABLE);
            case R.string.formula_editor_sensor_nose_x /* 2131888686 */:
                return buildSensor(Sensors.NOSE_X);
            case R.string.formula_editor_sensor_nose_y /* 2131888687 */:
                return buildSensor(Sensors.NOSE_Y);
            case R.string.formula_editor_sensor_port /* 2131888688 */:
                return buildSensor(Sensors.PORT);
            case R.string.formula_editor_sensor_right_ankle_x /* 2131888689 */:
                return buildSensor(Sensors.RIGHT_ANKLE_X);
            case R.string.formula_editor_sensor_right_ankle_y /* 2131888690 */:
                return buildSensor(Sensors.RIGHT_ANKLE_Y);
            case R.string.formula_editor_sensor_right_ear_x /* 2131888691 */:
                return buildSensor(Sensors.RIGHT_EAR_X);
            case R.string.formula_editor_sensor_right_ear_y /* 2131888692 */:
                return buildSensor(Sensors.RIGHT_EAR_Y);
            case R.string.formula_editor_sensor_right_elbow_x /* 2131888693 */:
                return buildSensor(Sensors.RIGHT_ELBOW_X);
            case R.string.formula_editor_sensor_right_elbow_y /* 2131888694 */:
                return buildSensor(Sensors.RIGHT_ELBOW_Y);
            case R.string.formula_editor_sensor_right_eye_center_x /* 2131888695 */:
                return buildSensor(Sensors.RIGHT_EYE_CENTER_X);
            case R.string.formula_editor_sensor_right_eye_center_y /* 2131888696 */:
                return buildSensor(Sensors.RIGHT_EYE_CENTER_Y);
            case R.string.formula_editor_sensor_right_eye_inner_x /* 2131888697 */:
                return buildSensor(Sensors.RIGHT_EYE_INNER_X);
            case R.string.formula_editor_sensor_right_eye_inner_y /* 2131888698 */:
                return buildSensor(Sensors.RIGHT_EYE_INNER_Y);
            case R.string.formula_editor_sensor_right_eye_outer_x /* 2131888699 */:
                return buildSensor(Sensors.RIGHT_EYE_OUTER_X);
            case R.string.formula_editor_sensor_right_eye_outer_y /* 2131888700 */:
                return buildSensor(Sensors.RIGHT_EYE_OUTER_Y);
            case R.string.formula_editor_sensor_right_foot_index_x /* 2131888701 */:
                return buildSensor(Sensors.RIGHT_FOOT_INDEX_X);
            case R.string.formula_editor_sensor_right_foot_index_y /* 2131888702 */:
                return buildSensor(Sensors.RIGHT_FOOT_INDEX_Y);
            case R.string.formula_editor_sensor_right_heel_x /* 2131888703 */:
                return buildSensor(Sensors.RIGHT_HEEL_X);
            case R.string.formula_editor_sensor_right_heel_y /* 2131888704 */:
                return buildSensor(Sensors.RIGHT_HEEL_Y);
            case R.string.formula_editor_sensor_right_hip_x /* 2131888705 */:
                return buildSensor(Sensors.RIGHT_HIP_X);
            case R.string.formula_editor_sensor_right_hip_y /* 2131888706 */:
                return buildSensor(Sensors.RIGHT_HIP_Y);
            case R.string.formula_editor_sensor_right_index_knuckle_x /* 2131888707 */:
                return buildSensor(Sensors.RIGHT_INDEX_X);
            case R.string.formula_editor_sensor_right_index_knuckle_y /* 2131888708 */:
                return buildSensor(Sensors.RIGHT_INDEX_Y);
            case R.string.formula_editor_sensor_right_knee_x /* 2131888709 */:
                return buildSensor(Sensors.RIGHT_KNEE_X);
            case R.string.formula_editor_sensor_right_knee_y /* 2131888710 */:
                return buildSensor(Sensors.RIGHT_KNEE_Y);
            case R.string.formula_editor_sensor_right_pinky_knuckle_x /* 2131888711 */:
                return buildSensor(Sensors.RIGHT_PINKY_X);
            case R.string.formula_editor_sensor_right_pinky_knuckle_y /* 2131888712 */:
                return buildSensor(Sensors.RIGHT_PINKY_Y);
            case R.string.formula_editor_sensor_right_shoulder_x /* 2131888713 */:
                return buildSensor(Sensors.RIGHT_SHOULDER_X);
            case R.string.formula_editor_sensor_right_shoulder_y /* 2131888714 */:
                return buildSensor(Sensors.RIGHT_SHOULDER_Y);
            case R.string.formula_editor_sensor_right_thumb_knuckle_x /* 2131888715 */:
                return buildSensor(Sensors.RIGHT_THUMB_X);
            case R.string.formula_editor_sensor_right_thumb_knuckle_y /* 2131888716 */:
                return buildSensor(Sensors.RIGHT_THUMB_Y);
            case R.string.formula_editor_sensor_right_wrist_x /* 2131888717 */:
                return buildSensor(Sensors.RIGHT_WRIST_X);
            case R.string.formula_editor_sensor_right_wrist_y /* 2131888718 */:
                return buildSensor(Sensors.RIGHT_WRIST_Y);
            case R.string.formula_editor_sensor_second_face_detected /* 2131888719 */:
                return buildSensor(Sensors.SECOND_FACE_DETECTED);
            case R.string.formula_editor_sensor_second_face_size /* 2131888720 */:
                return buildSensor(Sensors.SECOND_FACE_SIZE);
            case R.string.formula_editor_sensor_second_face_x_position /* 2131888721 */:
                return buildSensor(Sensors.SECOND_FACE_X);
            case R.string.formula_editor_sensor_second_face_y_position /* 2131888722 */:
                return buildSensor(Sensors.SECOND_FACE_Y);
            case R.string.formula_editor_sensor_stage_height /* 2131888723 */:
                return buildSensor(Sensors.STAGE_HEIGHT);
            case R.string.formula_editor_sensor_stage_width /* 2131888724 */:
                return buildSensor(Sensors.STAGE_WIDTH);
            case R.string.formula_editor_sensor_text_blocks_number /* 2131888725 */:
                return buildObject(Sensors.TEXT_BLOCKS_NUMBER);
            case R.string.formula_editor_sensor_text_from_camera /* 2131888726 */:
                return buildObject(Sensors.TEXT_FROM_CAMERA);
            case R.string.formula_editor_sensor_time_hour /* 2131888727 */:
                return buildSensor(Sensors.TIME_HOUR);
            case R.string.formula_editor_sensor_time_minute /* 2131888728 */:
                return buildSensor(Sensors.TIME_MINUTE);
            case R.string.formula_editor_sensor_time_second /* 2131888729 */:
                return buildSensor(Sensors.TIME_SECOND);
            case R.string.formula_editor_sensor_timer /* 2131888730 */:
                return buildSensor(Sensors.TIMER);
            case R.string.formula_editor_sensor_user_language /* 2131888731 */:
                return buildSensor(Sensors.USER_LANGUAGE);
            case R.string.formula_editor_sensor_x_acceleration /* 2131888732 */:
                return buildSensor(Sensors.X_ACCELERATION);
            case R.string.formula_editor_sensor_x_inclination /* 2131888733 */:
                return buildSensor(Sensors.X_INCLINATION);
            case R.string.formula_editor_sensor_y_acceleration /* 2131888734 */:
                return buildSensor(Sensors.Y_ACCELERATION);
            case R.string.formula_editor_sensor_y_inclination /* 2131888735 */:
                return buildSensor(Sensors.Y_INCLINATION);
            case R.string.formula_editor_sensor_z_acceleration /* 2131888736 */:
                return buildSensor(Sensors.Z_ACCELERATION);
            case R.string.formula_f2d_alpha /* 2131888742 */:
                return buildSingleParameterFunction(Functions.F2D_ALPHA, InternTokenType.STRING, "id");
            case R.string.formula_f2d_b /* 2131888743 */:
                return buildSingleParameterFunction(Functions.F2D_COLOR_B, InternTokenType.STRING, "id");
            case R.string.formula_f2d_cam_x /* 2131888744 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.F2D_CAM_X);
            case R.string.formula_f2d_cam_y /* 2131888745 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.F2D_CAM_Y);
            case R.string.formula_f2d_cam_zoom /* 2131888746 */:
                return buildFunctionWithoutParametersAndBrackets(Functions.F2D_CAM_ZOOM);
            case R.string.formula_f2d_g /* 2131888747 */:
                return buildSingleParameterFunction(Functions.F2D_COLOR_G, InternTokenType.STRING, "id");
            case R.string.formula_f2d_is_touched /* 2131888749 */:
                return buildSingleParameterFunction(Functions.F2D_IS_TOUCHED, InternTokenType.STRING, "id");
            case R.string.formula_f2d_is_touched_index /* 2131888750 */:
                return buildDoubleParameterFunction(Functions.F2D_IS_TOUCHED_INDEX, InternTokenType.STRING, "id", InternTokenType.NUMBER, "0");
            case R.string.formula_f2d_r /* 2131888754 */:
                return buildSingleParameterFunction(Functions.F2D_COLOR_R, InternTokenType.STRING, "id");
            case R.string.formula_f2d_rotation /* 2131888755 */:
                return buildSingleParameterFunction(Functions.F2D_ROTATION, InternTokenType.STRING, "id");
            case R.string.formula_f2d_scale_x /* 2131888756 */:
                return buildSingleParameterFunction(Functions.F2D_SCALE_X, InternTokenType.STRING, "id");
            case R.string.formula_f2d_scale_y /* 2131888757 */:
                return buildSingleParameterFunction(Functions.F2D_SCALE_Y, InternTokenType.STRING, "id");
            case R.string.formula_f2d_texture /* 2131888758 */:
                return buildSingleParameterFunction(Functions.F2D_TEXTURE, InternTokenType.STRING, "id");
            case R.string.formula_f2d_x /* 2131888759 */:
                return buildSingleParameterFunction(Functions.F2D_X, InternTokenType.STRING, "id");
            case R.string.formula_f2d_y /* 2131888760 */:
                return buildSingleParameterFunction(Functions.F2D_Y, InternTokenType.STRING, "id");
            case R.string.formula_file_last_modified /* 2131888761 */:
                return buildSingleParameterFunction(Functions.FILE_LAST_MODIFIED, InternTokenType.STRING, "data.txt");
            case R.string.formula_file_md5 /* 2131888763 */:
                return buildSingleParameterFunction(Functions.FILE_MD5, InternTokenType.STRING, "image.png");
            case R.string.formula_file_sha256 /* 2131888765 */:
                return buildSingleParameterFunction(Functions.FILE_SHA256, InternTokenType.STRING, "image.png");
            case R.string.formula_file_to_base64 /* 2131888767 */:
                return buildSingleParameterFunction(Functions.FILE_TO_BASE64, InternTokenType.STRING, "image.png");
            case R.string.formula_get_object_name /* 2131888770 */:
                return buildObject(Sensors.OBJECT_NAME);
            case R.string.formula_http_response_code /* 2131888771 */:
                return buildSingleParameterFunction(Functions.HTTP_RESPONSE_CODE, InternTokenType.STRING, "my_request");
            case R.string.formula_http_response_header /* 2131888773 */:
                return buildDoubleParameterFunction(Functions.HTTP_RESPONSE_HEADER, InternTokenType.STRING, "my_request", InternTokenType.STRING, "Content-Type");
            case R.string.formula_http_response_text /* 2131888775 */:
                return buildSingleParameterFunction(Functions.HTTP_RESPONSE_TEXT, InternTokenType.STRING, "my_request");
            case R.string.formula_image_height /* 2131888777 */:
                return buildSingleParameterFunction(Functions.IMAGE_HEIGHT, InternTokenType.STRING, "picture.png");
            case R.string.formula_image_width /* 2131888779 */:
                return buildSingleParameterFunction(Functions.IMAGE_WIDTH, InternTokenType.STRING, "picture.png");
            case R.string.formula_intersect_list /* 2131888781 */:
                return buildSingleParameterFunction(Functions.INTERSECT_LIST, InternTokenType.STRING, "myObject");
            case R.string.formula_keyboard_height /* 2131888782 */:
                return buildSensor(Sensors.KEYBOARD_HEIGHT);
            case R.string.formula_md5 /* 2131888783 */:
                return buildSingleParameterFunction(Functions.MD5, InternTokenType.STRING, "text");
            case R.string.formula_media_duration /* 2131888785 */:
                return buildSingleParameterFunction(Functions.MEDIA_DURATION, InternTokenType.STRING, "music.mp3");
            case R.string.formula_mqtt_message /* 2131888787 */:
                return buildSingleParameterFunction(Functions.MQTT_MESSAGE, InternTokenType.STRING, "room1");
            case R.string.formula_notification_reply /* 2131888791 */:
                return buildSingleParameterFunction(Functions.NOTIFICATION_REPLY, InternTokenType.STRING, "my_action_id");
            case R.string.formula_pt_argmax /* 2131888793 */:
                return buildSingleParameterFunction(Functions.PT_ARGMAX, InternTokenType.STRING, "tensor");
            case R.string.formula_pt_dump /* 2131888795 */:
                return buildSingleParameterFunction(Functions.PT_DUMP, InternTokenType.STRING, "tensor");
            case R.string.formula_pt_shape /* 2131888797 */:
                return buildSingleParameterFunction(Functions.PT_SHAPE, InternTokenType.STRING, "tensor");
            case R.string.formula_pt_totalsize /* 2131888799 */:
                return buildSingleParameterFunction(Functions.PT_TOTALSIZE, InternTokenType.STRING, "tensor");
            case R.string.formula_pt_value /* 2131888801 */:
                return buildDoubleParameterFunction(Functions.PT_VALUE, InternTokenType.STRING, "tensor", InternTokenType.NUMBER, "0");
            case R.string.formula_pt_valuend /* 2131888803 */:
                return buildDoubleParameterFunction(Functions.PT_VALUEND, InternTokenType.STRING, "tensor", InternTokenType.STRING, "12,2");
            case R.string.formula_ray_did_hit /* 2131888805 */:
                return buildSingleParameterFunction(Functions.RAY_DID_HIT, InternTokenType.STRING, "ray");
            case R.string.formula_ray_did_hit2 /* 2131888806 */:
                return buildSingleParameterFunction(Functions.RAY_DID_HIT2, InternTokenType.STRING, "ray");
            case R.string.formula_ray_distance /* 2131888808 */:
                return buildSingleParameterFunction(Functions.GET_RAY_DISTANCE, InternTokenType.STRING, "ray");
            case R.string.formula_ray_hit_distance /* 2131888809 */:
                return buildSingleParameterFunction(Functions.RAY_HIT_DISTANCE, InternTokenType.STRING, "ray1");
            case R.string.formula_ray_hit_object /* 2131888811 */:
                return buildSingleParameterFunction(Functions.GET_RAY_HIT_OBJECT, InternTokenType.STRING, "ray");
            case R.string.formula_ray_hit_sprite_name /* 2131888812 */:
                return buildSingleParameterFunction(Functions.RAY_HIT_SPRITE_NAME, InternTokenType.STRING, "ray");
            case R.string.formula_ray_hit_x /* 2131888814 */:
                return buildSingleParameterFunction(Functions.GET_RAY_HIT_X, InternTokenType.STRING, "ray");
            case R.string.formula_ray_hit_x2 /* 2131888815 */:
                return buildSingleParameterFunction(Functions.RAY_HIT_X, InternTokenType.STRING, "ray");
            case R.string.formula_ray_hit_y /* 2131888817 */:
                return buildSingleParameterFunction(Functions.GET_RAY_HIT_Y, InternTokenType.STRING, "ray");
            case R.string.formula_ray_hit_y2 /* 2131888818 */:
                return buildSingleParameterFunction(Functions.RAY_HIT_Y, InternTokenType.STRING, "ray");
            case R.string.formula_ray_hit_z /* 2131888820 */:
                return buildSingleParameterFunction(Functions.GET_RAY_HIT_Z, InternTokenType.STRING, "ray");
            case R.string.formula_ray_normal_x /* 2131888821 */:
                return buildSingleParameterFunction(Functions.GET_RAY_HIT_NORMAL_X, InternTokenType.STRING, "ray");
            case R.string.formula_ray_normal_y /* 2131888822 */:
                return buildSingleParameterFunction(Functions.GET_RAY_HIT_NORMAL_Y, InternTokenType.STRING, "ray");
            case R.string.formula_ray_normal_z /* 2131888823 */:
                return buildSingleParameterFunction(Functions.GET_RAY_HIT_NORMAL_Z, InternTokenType.STRING, "ray");
            case R.string.formula_read_file /* 2131888825 */:
                return buildSingleParameterFunction(Functions.READ_FILE, InternTokenType.STRING, "data.txt");
            case R.string.formula_sha256 /* 2131888827 */:
                return buildSingleParameterFunction(Functions.SHA256, InternTokenType.STRING, "text");
            case R.string.formula_vector_angle /* 2131888830 */:
                return buildDoubleParameterFunction(Functions.GET_ANGLE, InternTokenType.NUMBER, "0.6", InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
            case R.string.formula_vector_dir_x /* 2131888833 */:
                return buildSingleParameterFunction(Functions.GET_DIRECTION_X, InternTokenType.NUMBER, "60");
            case R.string.formula_vector_dir_y /* 2131888834 */:
                return buildSingleParameterFunction(Functions.GET_DIRECTION_Y, InternTokenType.NUMBER, "60");
            case R.string.formula_voxel_get_data /* 2131888835 */:
                return buildFourParameterFunction(Functions.VOXEL_GET_DATA, InternTokenType.STRING, "chunk1", InternTokenType.NUMBER, "0", InternTokenType.NUMBER, "0", InternTokenType.NUMBER, "0");
            case R.string.formula_voxel_get_id /* 2131888837 */:
                return buildFourParameterFunction(Functions.VOXEL_GET_ID, InternTokenType.STRING, "chunk1", InternTokenType.NUMBER, "0", InternTokenType.NUMBER, "0", InternTokenType.NUMBER, "0");
            case R.string.is_video_playing /* 2131888999 */:
                return buildSingleParameterFunction(Functions.VIDEO_PLAYING, InternTokenType.STRING, "myVideoPlayer");
            case R.string.video_time /* 2131890168 */:
                return buildSingleParameterFunction(Functions.VIDEO_TIME, InternTokenType.STRING, "myVideoPlayer");
            case R.string.view_height /* 2131890170 */:
                return buildSingleParameterFunction(Functions.VIEW_HEIGHT, InternTokenType.STRING, "myView");
            case R.string.view_width /* 2131890172 */:
                return buildSingleParameterFunction(Functions.VIEW_WIDTH, InternTokenType.STRING, "myView");
            case R.string.view_x /* 2131890173 */:
                return buildSingleParameterFunction(Functions.VIEW_X, InternTokenType.STRING, "myView");
            case R.string.view_y /* 2131890174 */:
                return buildSingleParameterFunction(Functions.VIEW_Y, InternTokenType.STRING, "myView");
            default:
                switch (resource) {
                    case R.id.formula_editor_keyboard_0 /* 2131364444 */:
                        return buildNumber("0");
                    case R.id.formula_editor_keyboard_1 /* 2131364445 */:
                        return buildNumber(SchemaSymbols.ATTVAL_TRUE_1);
                    case R.id.formula_editor_keyboard_2 /* 2131364446 */:
                        return buildNumber("2");
                    case R.id.formula_editor_keyboard_3 /* 2131364447 */:
                        return buildNumber("3");
                    case R.id.formula_editor_keyboard_4 /* 2131364448 */:
                        return buildNumber("4");
                    case R.id.formula_editor_keyboard_5 /* 2131364449 */:
                        return buildNumber("5");
                    case R.id.formula_editor_keyboard_6 /* 2131364450 */:
                        return buildNumber("6");
                    case R.id.formula_editor_keyboard_7 /* 2131364451 */:
                        return buildNumber("7");
                    case R.id.formula_editor_keyboard_8 /* 2131364452 */:
                        return buildNumber("8");
                    case R.id.formula_editor_keyboard_9 /* 2131364453 */:
                        return buildNumber("9");
                    case R.id.formula_editor_keyboard_bracket_close /* 2131364454 */:
                        return buildBracketClose();
                    case R.id.formula_editor_keyboard_bracket_open /* 2131364455 */:
                        return buildBracketOpen();
                    default:
                        switch (resource) {
                            case R.id.formula_editor_keyboard_minus /* 2131364466 */:
                                return buildOperator(Operators.MINUS);
                            case R.id.formula_editor_keyboard_mult /* 2131364467 */:
                                return buildOperator(Operators.MULT);
                            default:
                                switch (resource) {
                                    case R.string.formula_3d_pos_x /* 2131888257 */:
                                        return buildSingleParameterFunction(Functions.GET_3D_POSITION_X, InternTokenType.STRING, "myObject");
                                    case R.string.formula_3d_pos_y /* 2131888258 */:
                                        return buildSingleParameterFunction(Functions.GET_3D_POSITION_Y, InternTokenType.STRING, "myObject");
                                    case R.string.formula_3d_pos_z /* 2131888259 */:
                                        return buildSingleParameterFunction(Functions.GET_3D_POSITION_Z, InternTokenType.STRING, "myObject");
                                    case R.string.formula_3d_rot_pitch /* 2131888260 */:
                                        return buildSingleParameterFunction(Functions.GET_3D_ROTATION_PITCH, InternTokenType.STRING, "myObject");
                                    case R.string.formula_3d_rot_roll /* 2131888261 */:
                                        return buildSingleParameterFunction(Functions.GET_3D_ROTATION_ROLL, InternTokenType.STRING, "myObject");
                                    case R.string.formula_3d_rot_yaw /* 2131888262 */:
                                        return buildSingleParameterFunction(Functions.GET_3D_ROTATION_YAW, InternTokenType.STRING, "myObject");
                                    case R.string.formula_3d_scale_x /* 2131888263 */:
                                        return buildSingleParameterFunction(Functions.GET_3D_SCALE_X, InternTokenType.STRING, "myObject");
                                    case R.string.formula_3d_scale_y /* 2131888264 */:
                                        return buildSingleParameterFunction(Functions.GET_3D_SCALE_Y, InternTokenType.STRING, "myObject");
                                    case R.string.formula_3d_scale_z /* 2131888265 */:
                                        return buildSingleParameterFunction(Functions.GET_3D_SCALE_Z, InternTokenType.STRING, "myObject");
                                    default:
                                        switch (resource) {
                                            case R.string.formula_editor_function_all_files /* 2131888325 */:
                                                return buildFunctionWithoutParametersAndBrackets(Functions.ALL_FILES);
                                            case R.string.formula_editor_function_arccos /* 2131888326 */:
                                                return buildSingleParameterFunction(Functions.ARCCOS, InternTokenType.NUMBER, "0");
                                            default:
                                                switch (resource) {
                                                    case R.string.formula_editor_function_arctan /* 2131888330 */:
                                                        return buildSingleParameterFunction(Functions.ARCTAN, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1);
                                                    case R.string.formula_editor_function_arctan2 /* 2131888331 */:
                                                        return buildDoubleParameterFunctionWithNegativeValues(Functions.ARCTAN2, true, InternTokenType.NUMBER, SchemaSymbols.ATTVAL_TRUE_1, false, InternTokenType.NUMBER, "0");
                                                    default:
                                                        switch (resource) {
                                                            case R.string.formula_editor_function_arduino_read_pin_value_analog /* 2131888334 */:
                                                                return buildSingleParameterFunction(Functions.ARDUINOANALOG, InternTokenType.NUMBER, "0");
                                                            case R.string.formula_editor_function_arduino_read_pin_value_digital /* 2131888335 */:
                                                                return buildSingleParameterFunction(Functions.ARDUINODIGITAL, InternTokenType.NUMBER, "0");
                                                            case R.string.formula_editor_function_ceil /* 2131888336 */:
                                                                return buildSingleParameterFunction(Functions.CEIL, InternTokenType.NUMBER, "0.3");
                                                            default:
                                                                switch (resource) {
                                                                    case R.string.formula_editor_function_false /* 2131888360 */:
                                                                        return buildFunctionWithoutParametersAndBrackets(Functions.FALSE);
                                                                    case R.string.formula_editor_function_file /* 2131888361 */:
                                                                        return buildSingleParameterFunction(Functions.FILE, InternTokenType.STRING, "variable.txt");
                                                                    default:
                                                                        switch (resource) {
                                                                            case R.string.formula_editor_function_file_size /* 2131888363 */:
                                                                                return buildSingleParameterFunction(Functions.FILE_SIZE, InternTokenType.STRING, "variable.txt");
                                                                            case R.string.formula_editor_function_files_path /* 2131888364 */:
                                                                                return buildFunctionWithoutParametersAndBrackets(Functions.FILES_PATH);
                                                                            case R.string.formula_editor_function_find /* 2131888365 */:
                                                                                return buildDoubleParameterFunction(Functions.FIND, InternTokenType.STRING, "list_name", InternTokenType.STRING, "256");
                                                                            default:
                                                                                switch (resource) {
                                                                                    case R.string.formula_editor_function_finger_x /* 2131888367 */:
                                                                                        return buildSensor(Sensors.FINGER_X);
                                                                                    case R.string.formula_editor_function_finger_y /* 2131888368 */:
                                                                                        return buildSensor(Sensors.FINGER_Y);
                                                                                    case R.string.formula_editor_function_flatten /* 2131888369 */:
                                                                                        return buildSingleParameterFunction(Functions.FLATTEN, InternTokenType.STRING, "list_name");
                                                                                    default:
                                                                                        return null;
                                                                                }
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
    }

    private List<InternToken> buildBracketOpen() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.BRACKET_OPEN));
        return linkedList;
    }

    private List<InternToken> buildBracketClose() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.BRACKET_CLOSE));
        return linkedList;
    }

    private List<InternToken> buildUserList(String userListName) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.USER_LIST, userListName));
        return linkedList;
    }

    private List<InternToken> buildUserVariable(String userVariableName) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.USER_VARIABLE, userVariableName));
        return linkedList;
    }

    private List<InternToken> buildUserDefinedBrickInput(String userDefinedBrickInput) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.USER_DEFINED_BRICK_INPUT, userDefinedBrickInput));
        return linkedList;
    }

    private List<InternToken> buildCollideWithFormula(String formula) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.COLLISION_FORMULA, formula));
        return linkedList;
    }

    private List<InternToken> buildPeriod() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.PERIOD));
        return linkedList;
    }

    private List<InternToken> buildNumber(String numberValue) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.NUMBER, numberValue));
        return linkedList;
    }

    private List<InternToken> buildObject(Sensors sensors) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.SENSOR, sensors.name()));
        return linkedList;
    }

    private List<InternToken> buildOperator(Operators operator) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.OPERATOR, operator.name()));
        return linkedList;
    }

    private List<InternToken> buildSensor(Sensors sensor) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.SENSOR, sensor.name()));
        return linkedList;
    }

    public List<InternToken> buildCustomJsFunction(String customFunctionName) {
        CustomFormula formulaByUniqueName = CustomFormulaManager.INSTANCE.getFormulaByUniqueName(customFunctionName);
        if (formulaByUniqueName == null) {
            return null;
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.FUNCTION_NAME, formulaByUniqueName.getUniqueName()));
        if (formulaByUniqueName.getParamCount() > 0) {
            linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN));
            for (int i = 0; i < formulaByUniqueName.getParamCount(); i++) {
                if (i > 0) {
                    linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETER_DELIMITER));
                }
                linkedList.add(new InternToken(formulaByUniqueName.getDefaultParamTypes().get(i), formulaByUniqueName.getDefaultParamValues().get(i)));
            }
            linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE));
        }
        return linkedList;
    }

    private List<InternToken> buildTripleParameterFunction(Functions function, InternTokenType firstParameter, String firstParameterNumberValue, InternTokenType secondParameter, String secondParameterNumberValue, InternTokenType thirdParameter, String thirdParameterNumberValue) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.FUNCTION_NAME, function.name()));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN));
        linkedList.add(new InternToken(firstParameter, firstParameterNumberValue));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETER_DELIMITER));
        linkedList.add(new InternToken(secondParameter, secondParameterNumberValue));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETER_DELIMITER));
        linkedList.add(new InternToken(thirdParameter, thirdParameterNumberValue));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE));
        return linkedList;
    }

    private List<InternToken> buildFourParameterFunction(Functions function, InternTokenType firstParameter, String firstParameterNumberValue, InternTokenType secondParameter, String secondParameterNumberValue, InternTokenType thirdParameter, String thirdParameterNumberValue, InternTokenType fourParameter, String fourParameterNumberValue) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.FUNCTION_NAME, function.name()));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN));
        linkedList.add(new InternToken(firstParameter, firstParameterNumberValue));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETER_DELIMITER));
        linkedList.add(new InternToken(secondParameter, secondParameterNumberValue));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETER_DELIMITER));
        linkedList.add(new InternToken(thirdParameter, thirdParameterNumberValue));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETER_DELIMITER));
        linkedList.add(new InternToken(fourParameter, fourParameterNumberValue));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE));
        return linkedList;
    }

    private List<InternToken> buildDoubleParameterFunction(Functions function, InternTokenType firstParameter, String firstParameterNumberValue, InternTokenType secondParameter, String secondParameterNumberValue) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.FUNCTION_NAME, function.name()));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN));
        linkedList.add(new InternToken(firstParameter, firstParameterNumberValue));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETER_DELIMITER));
        linkedList.add(new InternToken(secondParameter, secondParameterNumberValue));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE));
        return linkedList;
    }

    private List<InternToken> buildSingleParameterFunction(Functions function, InternTokenType firstParameter, String parameterNumberValue) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.FUNCTION_NAME, function.name()));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN));
        linkedList.add(new InternToken(firstParameter, parameterNumberValue));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE));
        return linkedList;
    }

    private List<InternToken> buildFunctionWithoutParametersAndBrackets(Functions function) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.FUNCTION_NAME, function.name()));
        return linkedList;
    }

    private List<InternToken> buildString(String myString) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.STRING, myString));
        return linkedList;
    }

    private List<InternToken> buildDoubleParameterFunctionWithNegativeValues(Functions function, boolean isFirstParameterNegative, InternTokenType firstParameter, String firstParameterNumberValue, boolean isSecondParameterNegative, InternTokenType secondParameter, String secondParameterNumberValue) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(new InternToken(InternTokenType.FUNCTION_NAME, function.name()));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_OPEN));
        if (isFirstParameterNegative) {
            linkedList.add(new InternToken(InternTokenType.OPERATOR, Operators.MINUS.name()));
        }
        linkedList.add(new InternToken(firstParameter, firstParameterNumberValue));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETER_DELIMITER));
        if (isSecondParameterNegative) {
            linkedList.add(new InternToken(InternTokenType.OPERATOR, Operators.MINUS.name()));
        }
        linkedList.add(new InternToken(secondParameter, secondParameterNumberValue));
        linkedList.add(new InternToken(InternTokenType.FUNCTION_PARAMETERS_BRACKET_CLOSE));
        return linkedList;
    }
}
