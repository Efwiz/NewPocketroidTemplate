package org.catrobat.catroid.content;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import kotlin.Pair;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.ParameterizedData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.actions.AddEditAction;
import org.catrobat.catroid.content.actions.AddFileToApkAction;
import org.catrobat.catroid.content.actions.AddHingeAction;
import org.catrobat.catroid.content.actions.AddItemToUserListAction;
import org.catrobat.catroid.content.actions.AddRadioAction;
import org.catrobat.catroid.content.actions.AddTextToBufferAction;
import org.catrobat.catroid.content.actions.AddToBufferAction;
import org.catrobat.catroid.content.actions.AdditiveParticleEffectAction;
import org.catrobat.catroid.content.actions.Apply3dForceAction;
import org.catrobat.catroid.content.actions.ApplyAngularImpulseAction;
import org.catrobat.catroid.content.actions.ApplyBufferLookAction;
import org.catrobat.catroid.content.actions.ApplyForceAction;
import org.catrobat.catroid.content.actions.ApplyImpulseAction;
import org.catrobat.catroid.content.actions.ApplyShaderToImageAction;
import org.catrobat.catroid.content.actions.ApplyTorqueAction;
import org.catrobat.catroid.content.actions.ArduinoSendDigitalValueAction;
import org.catrobat.catroid.content.actions.ArduinoSendPWMValueAction;
import org.catrobat.catroid.content.actions.AskAction;
import org.catrobat.catroid.content.actions.AskGPTAction;
import org.catrobat.catroid.content.actions.AskGemini2Action;
import org.catrobat.catroid.content.actions.AskGeminiAction;
import org.catrobat.catroid.content.actions.AskSpeechAction;
import org.catrobat.catroid.content.actions.AssertEqualsAction;
import org.catrobat.catroid.content.actions.AssertUserListAction;
import org.catrobat.catroid.content.actions.AsyncRepeatAction;
import org.catrobat.catroid.content.actions.AttachRaySensorAction;
import org.catrobat.catroid.content.actions.AttachSOAction;
import org.catrobat.catroid.content.actions.AttachToCameraAction;
import org.catrobat.catroid.content.actions.BakeByPrefixAction;
import org.catrobat.catroid.content.actions.Base64ToFileAction;
import org.catrobat.catroid.content.actions.BigAskAction;
import org.catrobat.catroid.content.actions.BindVmOutputAction;
import org.catrobat.catroid.content.actions.BroadcastAction;
import org.catrobat.catroid.content.actions.BroadcastWithParamsAction;
import org.catrobat.catroid.content.actions.BufferMaskAction;
import org.catrobat.catroid.content.actions.CameraBrickAction;
import org.catrobat.catroid.content.actions.CameraLookAtAction;
import org.catrobat.catroid.content.actions.CameraSettingsAction;
import org.catrobat.catroid.content.actions.CameraTouchControlAction;
import org.catrobat.catroid.content.actions.CameraTrackingAction;
import org.catrobat.catroid.content.actions.CastRayAction;
import org.catrobat.catroid.content.actions.ChangeBrightnessByNAction;
import org.catrobat.catroid.content.actions.ChangeColorByNAction;
import org.catrobat.catroid.content.actions.ChangeHeightAction;
import org.catrobat.catroid.content.actions.ChangeSizeByNAction;
import org.catrobat.catroid.content.actions.ChangeTempoByAction;
import org.catrobat.catroid.content.actions.ChangeTransparencyByNAction;
import org.catrobat.catroid.content.actions.ChangeVariableAction;
import org.catrobat.catroid.content.actions.ChangeVolumeByNAction;
import org.catrobat.catroid.content.actions.ChangeWidthAction;
import org.catrobat.catroid.content.actions.ChangeXByNAction;
import org.catrobat.catroid.content.actions.ChangeYByNAction;
import org.catrobat.catroid.content.actions.ChooseCameraAction;
import org.catrobat.catroid.content.actions.ChooseFileAction;
import org.catrobat.catroid.content.actions.ClearBackgroundAction;
import org.catrobat.catroid.content.actions.ClearGraphicEffectAction;
import org.catrobat.catroid.content.actions.ClearPythonEnvironmentAction;
import org.catrobat.catroid.content.actions.ClearSceneAction;
import org.catrobat.catroid.content.actions.ClearUserListAction;
import org.catrobat.catroid.content.actions.CloneAction;
import org.catrobat.catroid.content.actions.CloneAndNameAction;
import org.catrobat.catroid.content.actions.CloneObjectAction;
import org.catrobat.catroid.content.actions.ComeToFrontAction;
import org.catrobat.catroid.content.actions.CompileJavaToDexAction;
import org.catrobat.catroid.content.actions.ConfigureLightAction;
import org.catrobat.catroid.content.actions.ConfigureMaterialAction;
import org.catrobat.catroid.content.actions.ConfigureParticlesAction;
import org.catrobat.catroid.content.actions.ConnectServerAction;
import org.catrobat.catroid.content.actions.CopyLookAction;
import org.catrobat.catroid.content.actions.CopyProjectFileAction;
import org.catrobat.catroid.content.actions.CopyTextAction;
import org.catrobat.catroid.content.actions.Create3dObjectAction;
import org.catrobat.catroid.content.actions.CreateBufferAction;
import org.catrobat.catroid.content.actions.CreateButtonAction;
import org.catrobat.catroid.content.actions.CreateCubeAction;
import org.catrobat.catroid.content.actions.CreateDIalogAction;
import org.catrobat.catroid.content.actions.CreateDiskAction;
import org.catrobat.catroid.content.actions.CreateDistanceJointAction;
import org.catrobat.catroid.content.actions.CreateFloatAction;
import org.catrobat.catroid.content.actions.CreateGLViewAction;
import org.catrobat.catroid.content.actions.CreateGearJointAction;
import org.catrobat.catroid.content.actions.CreateImageViewAction;
import org.catrobat.catroid.content.actions.CreateParticlesAction;
import org.catrobat.catroid.content.actions.CreatePointJointAction;
import org.catrobat.catroid.content.actions.CreatePrismaticJointAction;
import org.catrobat.catroid.content.actions.CreatePulleyJointAction;
import org.catrobat.catroid.content.actions.CreateRevoluteJointAction;
import org.catrobat.catroid.content.actions.CreateScrollViewAction;
import org.catrobat.catroid.content.actions.CreateSliderAction;
import org.catrobat.catroid.content.actions.CreateSwitchAction;
import org.catrobat.catroid.content.actions.CreateTableAction;
import org.catrobat.catroid.content.actions.CreateTextFieldAction;
import org.catrobat.catroid.content.actions.CreateTextLabelAction;
import org.catrobat.catroid.content.actions.CreateVarAction;
import org.catrobat.catroid.content.actions.CreateVideoAction;
import org.catrobat.catroid.content.actions.CreateWebFileAction;
import org.catrobat.catroid.content.actions.CreateWebUrlAction;
import org.catrobat.catroid.content.actions.CreateWeldJointAction;
import org.catrobat.catroid.content.actions.CustomAction;
import org.catrobat.catroid.content.actions.CutLookAction;
import org.catrobat.catroid.content.actions.DelSquareAction;
import org.catrobat.catroid.content.actions.DeleteAllTablesAction;
import org.catrobat.catroid.content.actions.DeleteBaseAction;
import org.catrobat.catroid.content.actions.DeleteFilesAction;
import org.catrobat.catroid.content.actions.DeleteFloatAction;
import org.catrobat.catroid.content.actions.DeleteFromApkAction;
import org.catrobat.catroid.content.actions.DeleteItemOfUserListAction;
import org.catrobat.catroid.content.actions.DeleteLookAction;
import org.catrobat.catroid.content.actions.DeleteParticlesAction;
import org.catrobat.catroid.content.actions.DeleteTableAction;
import org.catrobat.catroid.content.actions.DeleteThisCloneAction;
import org.catrobat.catroid.content.actions.DeleteVarAction;
import org.catrobat.catroid.content.actions.DeleteWebAction;
import org.catrobat.catroid.content.actions.DestroyJointAction;
import org.catrobat.catroid.content.actions.DetachFromCameraAction;
import org.catrobat.catroid.content.actions.DisableBackgroundModeAction;
import org.catrobat.catroid.content.actions.DownloadDependencyAction;
import org.catrobat.catroid.content.actions.Ease3DPropertyAction;
import org.catrobat.catroid.content.actions.EasePropertyAction;
import org.catrobat.catroid.content.actions.EditLookAction;
import org.catrobat.catroid.content.actions.EnableBackgroundModeAction;
import org.catrobat.catroid.content.actions.EnablePbrRenderAction;
import org.catrobat.catroid.content.actions.EvalWebAction;
import org.catrobat.catroid.content.actions.EventAction;
import org.catrobat.catroid.content.actions.Export3dObjectToGlbAction;
import org.catrobat.catroid.content.actions.ExportProjectFileAction;
import org.catrobat.catroid.content.actions.ExtractFileAction;
import org.catrobat.catroid.content.actions.FadeParticleEffectAction;
import org.catrobat.catroid.content.actions.Fast2DApplyForceAction;
import org.catrobat.catroid.content.actions.Fast2DApplyImpulseAction;
import org.catrobat.catroid.content.actions.Fast2DCreateAction;
import org.catrobat.catroid.content.actions.Fast2DDeleteAction;
import org.catrobat.catroid.content.actions.Fast2DMakePhysicsAction;
import org.catrobat.catroid.content.actions.Fast2DSetAngularVelocityAction;
import org.catrobat.catroid.content.actions.Fast2DSetCameraAction;
import org.catrobat.catroid.content.actions.Fast2DSetCollisionFilterAction;
import org.catrobat.catroid.content.actions.Fast2DSetColorAction;
import org.catrobat.catroid.content.actions.Fast2DSetGravityAction;
import org.catrobat.catroid.content.actions.Fast2DSetPhysicsVelocityAction;
import org.catrobat.catroid.content.actions.Fast2DSetPositionAction;
import org.catrobat.catroid.content.actions.Fast2DSetRotationAction;
import org.catrobat.catroid.content.actions.Fast2DSetScaleAction;
import org.catrobat.catroid.content.actions.Fast2DSetTextureAction;
import org.catrobat.catroid.content.actions.Fast2DSetVelocityAction;
import org.catrobat.catroid.content.actions.Fast2DSetZIndexAction;
import org.catrobat.catroid.content.actions.FileUrlAction;
import org.catrobat.catroid.content.actions.FilesUrlAction;
import org.catrobat.catroid.content.actions.FinishStageAction;
import org.catrobat.catroid.content.actions.FlashAction;
import org.catrobat.catroid.content.actions.ForItemInUserListAction;
import org.catrobat.catroid.content.actions.ForVariableFromToAction;
import org.catrobat.catroid.content.actions.GenerateKeyAction;
import org.catrobat.catroid.content.actions.GetZipFileNamesAction;
import org.catrobat.catroid.content.actions.GlideToPhysicsAction;
import org.catrobat.catroid.content.actions.GoNStepsBackAction;
import org.catrobat.catroid.content.actions.GoToOtherSpritePositionAction;
import org.catrobat.catroid.content.actions.GoToRandomPositionAction;
import org.catrobat.catroid.content.actions.GoToTouchPositionAction;
import org.catrobat.catroid.content.actions.GrayscaleImgAction;
import org.catrobat.catroid.content.actions.HideText3Action;
import org.catrobat.catroid.content.actions.HideTextAction;
import org.catrobat.catroid.content.actions.HttpAttachFileAction;
import org.catrobat.catroid.content.actions.HttpBodyAction;
import org.catrobat.catroid.content.actions.HttpConfigAction;
import org.catrobat.catroid.content.actions.HttpCreateAction;
import org.catrobat.catroid.content.actions.HttpSaveFileAction;
import org.catrobat.catroid.content.actions.HttpSendAction;
import org.catrobat.catroid.content.actions.IfLogicAction;
import org.catrobat.catroid.content.actions.InsertItemIntoUserListAction;
import org.catrobat.catroid.content.actions.InsertTableAction;
import org.catrobat.catroid.content.actions.InstantAction;
import org.catrobat.catroid.content.actions.IntervalRepeatAction;
import org.catrobat.catroid.content.actions.KeyEventAction;
import org.catrobat.catroid.content.actions.KeyframeAnimationAction;
import org.catrobat.catroid.content.actions.LaunchProjectAction;
import org.catrobat.catroid.content.actions.LegoEv3MotorMoveAction;
import org.catrobat.catroid.content.actions.LegoEv3MotorStopAction;
import org.catrobat.catroid.content.actions.LegoEv3MotorTurnAngleAction;
import org.catrobat.catroid.content.actions.LegoEv3PlayToneAction;
import org.catrobat.catroid.content.actions.LegoEv3SetLedAction;
import org.catrobat.catroid.content.actions.LegoNxtMotorMoveAction;
import org.catrobat.catroid.content.actions.LegoNxtMotorStopAction;
import org.catrobat.catroid.content.actions.LegoNxtMotorTurnAngleAction;
import org.catrobat.catroid.content.actions.LegoNxtPlayToneAction;
import org.catrobat.catroid.content.actions.ListenMicroAction;
import org.catrobat.catroid.content.actions.ListenServerAction;
import org.catrobat.catroid.content.actions.LoadAndRunDexAction;
import org.catrobat.catroid.content.actions.LoadFromInternalStorageAction;
import org.catrobat.catroid.content.actions.LoadNNAction;
import org.catrobat.catroid.content.actions.LoadNativeModuleAction;
import org.catrobat.catroid.content.actions.LoadPythonLibraryAction;
import org.catrobat.catroid.content.actions.LoadSceneAction;
import org.catrobat.catroid.content.actions.LoadSceneAdditiveAction;
import org.catrobat.catroid.content.actions.LockMouseAction;
import org.catrobat.catroid.content.actions.LookFileAction;
import org.catrobat.catroid.content.actions.LookFromTableAction;
import org.catrobat.catroid.content.actions.LookRequestAction;
import org.catrobat.catroid.content.actions.LookToTableAction;
import org.catrobat.catroid.content.actions.LunoscriptAction;
import org.catrobat.catroid.content.actions.MLFileAction;
import org.catrobat.catroid.content.actions.MLStepAdamAction;
import org.catrobat.catroid.content.actions.MouseEventAction;
import org.catrobat.catroid.content.actions.MoveDownloadsAction;
import org.catrobat.catroid.content.actions.MoveFilesAction;
import org.catrobat.catroid.content.actions.MoveNStepsAction;
import org.catrobat.catroid.content.actions.MqttConnectAction;
import org.catrobat.catroid.content.actions.MqttDisconnectAction;
import org.catrobat.catroid.content.actions.MqttJoinRoomAction;
import org.catrobat.catroid.content.actions.MqttPublishAction;
import org.catrobat.catroid.content.actions.NativeLayerAction;
import org.catrobat.catroid.content.actions.NativeViewAnimateAction;
import org.catrobat.catroid.content.actions.NativeViewBindSpriteAction;
import org.catrobat.catroid.content.actions.NativeViewConfigAction;
import org.catrobat.catroid.content.actions.NativeViewControlAction;
import org.catrobat.catroid.content.actions.NativeViewListenerAction;
import org.catrobat.catroid.content.actions.NormalizeImgAction;
import org.catrobat.catroid.content.actions.NotificationAddButtonAction;
import org.catrobat.catroid.content.actions.NotificationCancelAction;
import org.catrobat.catroid.content.actions.NotificationCreateAction;
import org.catrobat.catroid.content.actions.NotificationShowAction;
import org.catrobat.catroid.content.actions.ObjectLookAtAction;
import org.catrobat.catroid.content.actions.ObjectLookAtCorrectAction;
import org.catrobat.catroid.content.actions.OpenAppAction;
import org.catrobat.catroid.content.actions.OpenFileAction;
import org.catrobat.catroid.content.actions.OpenFilesAction;
import org.catrobat.catroid.content.actions.OpenUrlAction;
import org.catrobat.catroid.content.actions.OrientationAction;
import org.catrobat.catroid.content.actions.PaintNewLookAction;
import org.catrobat.catroid.content.actions.ParameterizedAssertAction;
import org.catrobat.catroid.content.actions.PauseForBeatsAction;
import org.catrobat.catroid.content.actions.PauseVideoAction;
import org.catrobat.catroid.content.actions.PenDownAction;
import org.catrobat.catroid.content.actions.PenUpAction;
import org.catrobat.catroid.content.actions.PerformRayCastAction;
import org.catrobat.catroid.content.actions.PhiroMotorMoveBackwardAction;
import org.catrobat.catroid.content.actions.PhiroMotorMoveForwardAction;
import org.catrobat.catroid.content.actions.PhiroMotorStopAction;
import org.catrobat.catroid.content.actions.PhiroPlayToneAction;
import org.catrobat.catroid.content.actions.PhiroRGBLightAction;
import org.catrobat.catroid.content.actions.PhiroSensorAction;
import org.catrobat.catroid.content.actions.PhotoAction;
import org.catrobat.catroid.content.actions.PinToCameraAction;
import org.catrobat.catroid.content.actions.PlayAnimationAction;
import org.catrobat.catroid.content.actions.PlayDrumForBeatsAction;
import org.catrobat.catroid.content.actions.PlayGifAction;
import org.catrobat.catroid.content.actions.PlayNoteForBeatsAction;
import org.catrobat.catroid.content.actions.PlayPreparedSoundAction;
import org.catrobat.catroid.content.actions.PlaySoundAction;
import org.catrobat.catroid.content.actions.PlaySoundAtAction;
import org.catrobat.catroid.content.actions.PlaySoundAtPositionAction;
import org.catrobat.catroid.content.actions.PlaySpritesheetAction;
import org.catrobat.catroid.content.actions.PlayVideoAction;
import org.catrobat.catroid.content.actions.PointInDirectionAction;
import org.catrobat.catroid.content.actions.PointToAction;
import org.catrobat.catroid.content.actions.PostWebRequestAction;
import org.catrobat.catroid.content.actions.PredictNNAction;
import org.catrobat.catroid.content.actions.PrepareMusicAction;
import org.catrobat.catroid.content.actions.PrepareSoundAction;
import org.catrobat.catroid.content.actions.PrepareSoundAction2;
import org.catrobat.catroid.content.actions.PromoteLightAction;
import org.catrobat.catroid.content.actions.PtBackwardAction;
import org.catrobat.catroid.content.actions.PtCreateTensorAction;
import org.catrobat.catroid.content.actions.PtOpAction;
import org.catrobat.catroid.content.actions.PtReshapeAction;
import org.catrobat.catroid.content.actions.PtSetByIndexAction;
import org.catrobat.catroid.content.actions.PtSetTensorAction;
import org.catrobat.catroid.content.actions.PtSetTraining;
import org.catrobat.catroid.content.actions.PtStepAction;
import org.catrobat.catroid.content.actions.PutFloatAction;
import org.catrobat.catroid.content.actions.RaspiIfLogicAction;
import org.catrobat.catroid.content.actions.RaspiPwmAction;
import org.catrobat.catroid.content.actions.RaspiSendDigitalValueAction;
import org.catrobat.catroid.content.actions.ReadBaseAction;
import org.catrobat.catroid.content.actions.ReadFromFilesAction;
import org.catrobat.catroid.content.actions.ReadListFromDeviceAction;
import org.catrobat.catroid.content.actions.ReadVariableFromDeviceAction;
import org.catrobat.catroid.content.actions.ReadVariableFromFileAction;
import org.catrobat.catroid.content.actions.RegexAction;
import org.catrobat.catroid.content.actions.Remove3dObjectAction;
import org.catrobat.catroid.content.actions.RemoveFromBufferAction;
import org.catrobat.catroid.content.actions.RemoveJointAction;
import org.catrobat.catroid.content.actions.RemoveObjectsByPrefixAction;
import org.catrobat.catroid.content.actions.RemoveParentAction;
import org.catrobat.catroid.content.actions.RemovePbrLightAction;
import org.catrobat.catroid.content.actions.RepeatAction;
import org.catrobat.catroid.content.actions.RepeatParameterizedAction;
import org.catrobat.catroid.content.actions.RepeatUntilAction;
import org.catrobat.catroid.content.actions.Replace3DModelAction;
import org.catrobat.catroid.content.actions.ReplaceItemInUserListAction;
import org.catrobat.catroid.content.actions.ReportAction;
import org.catrobat.catroid.content.actions.ResetTimerAction;
import org.catrobat.catroid.content.actions.ResizeImgAction;
import org.catrobat.catroid.content.actions.ReturnToPreviousProjectAction;
import org.catrobat.catroid.content.actions.RotateCameraByAction;
import org.catrobat.catroid.content.actions.RunAsSpriteAction;
import org.catrobat.catroid.content.actions.RunChip8Action;
import org.catrobat.catroid.content.actions.RunJSAction;
import org.catrobat.catroid.content.actions.RunLuaAction;
import org.catrobat.catroid.content.actions.RunPythonScriptAction;
import org.catrobat.catroid.content.actions.RunShellAction;
import org.catrobat.catroid.content.actions.RunVMAction;
import org.catrobat.catroid.content.actions.RunVm2Action;
import org.catrobat.catroid.content.actions.RunningStitchAction;
import org.catrobat.catroid.content.actions.SaveBroadcastParamsAction;
import org.catrobat.catroid.content.actions.SaveBufferAction;
import org.catrobat.catroid.content.actions.SaveLookAction;
import org.catrobat.catroid.content.actions.SaveLookFilesAction;
import org.catrobat.catroid.content.actions.SavePlotAction;
import org.catrobat.catroid.content.actions.SaveToInternalStorageAction;
import org.catrobat.catroid.content.actions.SceneIdAction;
import org.catrobat.catroid.content.actions.SceneStartAction;
import org.catrobat.catroid.content.actions.SceneTransitionAction;
import org.catrobat.catroid.content.actions.ScreenShotAction;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.actions.SeekVideoAction;
import org.catrobat.catroid.content.actions.SendServerAction;
import org.catrobat.catroid.content.actions.SendVmInputAction;
import org.catrobat.catroid.content.actions.Set3DSoundMaxDistanceAction;
import org.catrobat.catroid.content.actions.Set3DSoundPositionAction;
import org.catrobat.catroid.content.actions.Set3dFrictionAction;
import org.catrobat.catroid.content.actions.Set3dGravityAction;
import org.catrobat.catroid.content.actions.Set3dPositionAction;
import org.catrobat.catroid.content.actions.Set3dRotationAction;
import org.catrobat.catroid.content.actions.Set3dScaleAction;
import org.catrobat.catroid.content.actions.Set3dVelocityAction;
import org.catrobat.catroid.content.actions.SetAIAction;
import org.catrobat.catroid.content.actions.SetActiveAction;
import org.catrobat.catroid.content.actions.SetAmbientLightAction;
import org.catrobat.catroid.content.actions.SetAnimationSpeedAction;
import org.catrobat.catroid.content.actions.SetAnisotropicFilterAction;
import org.catrobat.catroid.content.actions.SetBackgroundLightAction;
import org.catrobat.catroid.content.actions.SetBrightnessAction;
import org.catrobat.catroid.content.actions.SetBufferAutoUpdateAction;
import org.catrobat.catroid.content.actions.SetBufferCamera3DAction;
import org.catrobat.catroid.content.actions.SetBufferCameraAction;
import org.catrobat.catroid.content.actions.SetBufferEffectsAction;
import org.catrobat.catroid.content.actions.SetBufferModeAction;
import org.catrobat.catroid.content.actions.SetBufferOnlyAction;
import org.catrobat.catroid.content.actions.SetBufferShaderAction;
import org.catrobat.catroid.content.actions.SetBufferShaderUniformAction;
import org.catrobat.catroid.content.actions.SetCCDAction;
import org.catrobat.catroid.content.actions.SetCallbackAction;
import org.catrobat.catroid.content.actions.SetCameraFocusPointAction;
import org.catrobat.catroid.content.actions.SetCameraPosition2Action;
import org.catrobat.catroid.content.actions.SetCameraPositionAction;
import org.catrobat.catroid.content.actions.SetCameraRangeAction;
import org.catrobat.catroid.content.actions.SetCameraRotation2Action;
import org.catrobat.catroid.content.actions.SetCameraRotationAction;
import org.catrobat.catroid.content.actions.SetCameraZoomAction;
import org.catrobat.catroid.content.actions.SetColorAction;
import org.catrobat.catroid.content.actions.SetCornerOffsetsAction;
import org.catrobat.catroid.content.actions.SetDampingAction;
import org.catrobat.catroid.content.actions.SetDirectionalLigh2tAction;
import org.catrobat.catroid.content.actions.SetDirectionalLightAction;
import org.catrobat.catroid.content.actions.SetDnsAction;
import org.catrobat.catroid.content.actions.SetEmissiveAction;
import org.catrobat.catroid.content.actions.SetFogAction;
import org.catrobat.catroid.content.actions.SetFpsAction;
import org.catrobat.catroid.content.actions.SetFreeCameraAction;
import org.catrobat.catroid.content.actions.SetGeminiKeyAction;
import org.catrobat.catroid.content.actions.SetGlobalSoundVolumeAction;
import org.catrobat.catroid.content.actions.SetHeightAction;
import org.catrobat.catroid.content.actions.SetHingeMotorAction;
import org.catrobat.catroid.content.actions.SetHitboxAction;
import org.catrobat.catroid.content.actions.SetInstrumentAction;
import org.catrobat.catroid.content.actions.SetListeningLanguageAction;
import org.catrobat.catroid.content.actions.SetLookAction;
import org.catrobat.catroid.content.actions.SetLookByIndexAction;
import org.catrobat.catroid.content.actions.SetLookFilesAction;
import org.catrobat.catroid.content.actions.SetMainRenderLoopsAction;
import org.catrobat.catroid.content.actions.SetMaterialAction;
import org.catrobat.catroid.content.actions.SetMaxPointLightsAction;
import org.catrobat.catroid.content.actions.SetNativeParentAction;
import org.catrobat.catroid.content.actions.SetNegativeAction;
import org.catrobat.catroid.content.actions.SetNeutralButton;
import org.catrobat.catroid.content.actions.SetNextLookAction;
import org.catrobat.catroid.content.actions.SetNfcTagAction;
import org.catrobat.catroid.content.actions.SetObjectColorAction;
import org.catrobat.catroid.content.actions.SetObjectShaderAction;
import org.catrobat.catroid.content.actions.SetObjectShaderUniformAction;
import org.catrobat.catroid.content.actions.SetObjectTextureAction;
import org.catrobat.catroid.content.actions.SetParentAction;
import org.catrobat.catroid.content.actions.SetParticleColorAction;
import org.catrobat.catroid.content.actions.SetParticleEmissionAction;
import org.catrobat.catroid.content.actions.SetPenColorAction;
import org.catrobat.catroid.content.actions.SetPenSizeAction;
import org.catrobat.catroid.content.actions.SetPhysicsStateAction;
import org.catrobat.catroid.content.actions.SetPointLightAction;
import org.catrobat.catroid.content.actions.SetPositiveAction;
import org.catrobat.catroid.content.actions.SetPostProcessingAction;
import org.catrobat.catroid.content.actions.SetPostProcessingNewAction;
import org.catrobat.catroid.content.actions.SetPreviousLookAction;
import org.catrobat.catroid.content.actions.SetRenderResolutionAction;
import org.catrobat.catroid.content.actions.SetRestitutionAction;
import org.catrobat.catroid.content.actions.SetRotationLockAction;
import org.catrobat.catroid.content.actions.SetRotationStyleAction;
import org.catrobat.catroid.content.actions.SetSaveScenesAction;
import org.catrobat.catroid.content.actions.SetScreenShaderAction;
import org.catrobat.catroid.content.actions.SetShaderCodeAction;
import org.catrobat.catroid.content.actions.SetShaderUniformAction;
import org.catrobat.catroid.content.actions.SetShadowQualityAction;
import org.catrobat.catroid.content.actions.SetShadowsAction;
import org.catrobat.catroid.content.actions.SetSizeToAction;
import org.catrobat.catroid.content.actions.SetSkyColorAction;
import org.catrobat.catroid.content.actions.SetSkyboxAction;
import org.catrobat.catroid.content.actions.SetSoundInstancePitchAction;
import org.catrobat.catroid.content.actions.SetSoundInstanceVolumeAction;
import org.catrobat.catroid.content.actions.SetSoundVolumeAction;
import org.catrobat.catroid.content.actions.SetSpawnInvisibleAction;
import org.catrobat.catroid.content.actions.SetSpotLightAction;
import org.catrobat.catroid.content.actions.SetStopSoundsAction;
import org.catrobat.catroid.content.actions.SetTempoAction;
import org.catrobat.catroid.content.actions.SetTextAction;
import org.catrobat.catroid.content.actions.SetTextBufferOnlyAction;
import org.catrobat.catroid.content.actions.SetTextureTilingAction;
import org.catrobat.catroid.content.actions.SetThirdPersonCameraAction;
import org.catrobat.catroid.content.actions.SetThreadColorAction;
import org.catrobat.catroid.content.actions.SetTransparencyAction;
import org.catrobat.catroid.content.actions.SetVariableAction;
import org.catrobat.catroid.content.actions.SetVariableEasingAction;
import org.catrobat.catroid.content.actions.SetViewPositionAction;
import org.catrobat.catroid.content.actions.SetVisibleAction;
import org.catrobat.catroid.content.actions.SetVolumeToAction;
import org.catrobat.catroid.content.actions.SetWebAction;
import org.catrobat.catroid.content.actions.SetWidthAction;
import org.catrobat.catroid.content.actions.SetXAction;
import org.catrobat.catroid.content.actions.SetYAction;
import org.catrobat.catroid.content.actions.SewUpAction;
import org.catrobat.catroid.content.actions.ShaderAction;
import org.catrobat.catroid.content.actions.ShareAction;
import org.catrobat.catroid.content.actions.ShowDialogAction;
import org.catrobat.catroid.content.actions.ShowNotificationAction;
import org.catrobat.catroid.content.actions.ShowText2Action;
import org.catrobat.catroid.content.actions.ShowText3Action;
import org.catrobat.catroid.content.actions.ShowTextAction;
import org.catrobat.catroid.content.actions.ShowTextColorSizeAlignmentAction;
import org.catrobat.catroid.content.actions.ShowTextFontAction;
import org.catrobat.catroid.content.actions.ShowTextRotationAction;
import org.catrobat.catroid.content.actions.ShowToastAction;
import org.catrobat.catroid.content.actions.ShowVarFontAction;
import org.catrobat.catroid.content.actions.SignApkAction;
import org.catrobat.catroid.content.actions.SoundFileAction;
import org.catrobat.catroid.content.actions.SoundFilesAction;
import org.catrobat.catroid.content.actions.SpawnThreadAction;
import org.catrobat.catroid.content.actions.SpeakAction;
import org.catrobat.catroid.content.actions.SpeakAndWaitAction;
import org.catrobat.catroid.content.actions.SplitAction;
import org.catrobat.catroid.content.actions.SquareAction;
import org.catrobat.catroid.content.actions.StampAction;
import org.catrobat.catroid.content.actions.StartBufferRecordingAction;
import org.catrobat.catroid.content.actions.StartListeningAction;
import org.catrobat.catroid.content.actions.StartPlotAction;
import org.catrobat.catroid.content.actions.StartRecordingAction;
import org.catrobat.catroid.content.actions.StartServerAction;
import org.catrobat.catroid.content.actions.StitchAction;
import org.catrobat.catroid.content.actions.StopAllScriptsAction;
import org.catrobat.catroid.content.actions.StopAllSoundsAction;
import org.catrobat.catroid.content.actions.StopAnimationAction;
import org.catrobat.catroid.content.actions.StopBufferRecordingAction;
import org.catrobat.catroid.content.actions.StopGifAction;
import org.catrobat.catroid.content.actions.StopOtherScriptsAction;
import org.catrobat.catroid.content.actions.StopPlotAction;
import org.catrobat.catroid.content.actions.StopRecordingAction;
import org.catrobat.catroid.content.actions.StopRunningStitchAction;
import org.catrobat.catroid.content.actions.StopServerAction;
import org.catrobat.catroid.content.actions.StopSoundAction;
import org.catrobat.catroid.content.actions.StopSoundAction2;
import org.catrobat.catroid.content.actions.StopSpritesheetAction;
import org.catrobat.catroid.content.actions.StopThisScriptAction;
import org.catrobat.catroid.content.actions.StopThreadAction;
import org.catrobat.catroid.content.actions.StopVMAction;
import org.catrobat.catroid.content.actions.StoreCSVIntoUserListAction;
import org.catrobat.catroid.content.actions.StringToTableAction;
import org.catrobat.catroid.content.actions.TableToFloatAction;
import org.catrobat.catroid.content.actions.TapAtAction;
import org.catrobat.catroid.content.actions.TestAction;
import org.catrobat.catroid.content.actions.ThinkSayBubbleAction;
import org.catrobat.catroid.content.actions.ThreedAlignNormalAction;
import org.catrobat.catroid.content.actions.ThreedAttachObjectToBoneAction;
import org.catrobat.catroid.content.actions.ThreedBindBoneToObjectAction;
import org.catrobat.catroid.content.actions.ThreedCreateCylinderAction;
import org.catrobat.catroid.content.actions.ThreedCreateFixedConstraintAction;
import org.catrobat.catroid.content.actions.ToggleDisplayAction;
import org.catrobat.catroid.content.actions.TripleStitchAction;
import org.catrobat.catroid.content.actions.TryCatchFinallyAction;
import org.catrobat.catroid.content.actions.TurnLeftAction;
import org.catrobat.catroid.content.actions.TurnRightAction;
import org.catrobat.catroid.content.actions.UnloadNNAction;
import org.catrobat.catroid.content.actions.UnlockMouseAction;
import org.catrobat.catroid.content.actions.UnpinFromCameraAction;
import org.catrobat.catroid.content.actions.UnzipAction;
import org.catrobat.catroid.content.actions.UnzipProjectFilesAction;
import org.catrobat.catroid.content.actions.UpdateManifestAction;
import org.catrobat.catroid.content.actions.UploadFileAction;
import org.catrobat.catroid.content.actions.UserDefinedBrickAction;
import org.catrobat.catroid.content.actions.VibrateAction;
import org.catrobat.catroid.content.actions.VmRelativeMouseMoveAction;
import org.catrobat.catroid.content.actions.VmSetMonitorSizeAction;
import org.catrobat.catroid.content.actions.VoxelBuildAction;
import org.catrobat.catroid.content.actions.VoxelConfigAction;
import org.catrobat.catroid.content.actions.VoxelCreateWorldAction;
import org.catrobat.catroid.content.actions.VoxelDeleteAction;
import org.catrobat.catroid.content.actions.VoxelLoadStringAction;
import org.catrobat.catroid.content.actions.VoxelSetBlockAction;
import org.catrobat.catroid.content.actions.VoxelSetTransparentAction;
import org.catrobat.catroid.content.actions.WaitAction;
import org.catrobat.catroid.content.actions.WaitForBubbleBrickAction;
import org.catrobat.catroid.content.actions.WaitForSoundAction;
import org.catrobat.catroid.content.actions.WaitThreadAction;
import org.catrobat.catroid.content.actions.WaitTillIdleAction;
import org.catrobat.catroid.content.actions.WaitUntilAction;
import org.catrobat.catroid.content.actions.WebRequestAction;
import org.catrobat.catroid.content.actions.WriteBaseAction;
import org.catrobat.catroid.content.actions.WriteEmbroideryToFileAction;
import org.catrobat.catroid.content.actions.WriteToFilesAction;
import org.catrobat.catroid.content.actions.WriteUserDataOnDeviceAction;
import org.catrobat.catroid.content.actions.WriteVarToFileAction;
import org.catrobat.catroid.content.actions.ZigZagStitchAction;
import org.catrobat.catroid.content.actions.ZipAction;
import org.catrobat.catroid.content.actions.ZipProjectFilesAction;
import org.catrobat.catroid.content.actions.conditional.GlideToAction;
import org.catrobat.catroid.content.actions.conditional.IfOnEdgeBounceAction;
import org.catrobat.catroid.content.actions.hideStatusBarAction;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.LegoEv3MotorMoveBrick;
import org.catrobat.catroid.content.bricks.LegoEv3MotorStopBrick;
import org.catrobat.catroid.content.bricks.LegoEv3MotorTurnAngleBrick;
import org.catrobat.catroid.content.bricks.LegoEv3SetLedBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorMoveBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorStopBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorTurnAngleBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorMoveBackwardBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorMoveForwardBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorStopBrick;
import org.catrobat.catroid.content.bricks.PhiroPlayToneBrick;
import org.catrobat.catroid.content.bricks.PhiroRGBLightBrick;
import org.catrobat.catroid.content.bricks.brickspinner.PickableDrum;
import org.catrobat.catroid.content.bricks.brickspinner.PickableMusicalInstrument;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.DeviceListAccessor;
import org.catrobat.catroid.io.DeviceVariableAccessor;
import org.catrobat.catroid.libraries.CustomBrickDefinition;
import org.catrobat.catroid.physics.PhysicsLook;
import org.catrobat.catroid.physics.PhysicsObject;
import org.catrobat.catroid.stage.SpeechSynthesizer;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.userbrick.UserDefinedBrickInput;
import org.catrobat.catroid.utils.MobileServiceAvailability;
import org.catrobat.catroid.utils.ShowTextUtils;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/ActionFactory.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ActionFactory extends Actions {
    public EventAction createUserBrickAction(Sprite sprite, SequenceAction sequence, List<UserDefinedBrickInput> userDefinedBrickInputs, UUID userDefinedBrickID) {
        UserDefinedBrickAction userDefinedBrickAction = (UserDefinedBrickAction) action(UserDefinedBrickAction.class);
        userDefinedBrickAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        userDefinedBrickAction.setInputs(userDefinedBrickInputs);
        userDefinedBrickAction.setUserDefinedBrickID(userDefinedBrickID);
        userDefinedBrickAction.setSprite(sprite);
        userDefinedBrickAction.setWait(true);
        return userDefinedBrickAction;
    }

    public Action createRevoluteJointAction(Sprite sprite, SequenceAction sequence, Formula jointId, Formula spriteB, Formula anchorX, Formula anchorY) {
        CreateRevoluteJointAction createRevoluteJointAction = (CreateRevoluteJointAction) action(CreateRevoluteJointAction.class);
        createRevoluteJointAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createRevoluteJointAction.setJointId(jointId);
        createRevoluteJointAction.setSpriteBName(spriteB);
        createRevoluteJointAction.setAnchorX(anchorX);
        createRevoluteJointAction.setAnchorY(anchorY);
        return createRevoluteJointAction;
    }

    public Action createDestroyJointAction(Sprite sprite, SequenceAction sequence, Formula jointId) {
        DestroyJointAction destroyJointAction = (DestroyJointAction) action(DestroyJointAction.class);
        destroyJointAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        destroyJointAction.setJointId(jointId);
        return destroyJointAction;
    }

    public Action createDistanceJointAction(Sprite sprite, SequenceAction sequence, Formula jointId, Formula spriteB, Formula length, Formula frequency, Formula damping) {
        CreateDistanceJointAction createDistanceJointAction = (CreateDistanceJointAction) action(CreateDistanceJointAction.class);
        createDistanceJointAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createDistanceJointAction.setJointId(jointId);
        createDistanceJointAction.setSpriteBName(spriteB);
        createDistanceJointAction.setLength(length);
        createDistanceJointAction.setFrequency(frequency);
        createDistanceJointAction.setDamping(damping);
        return createDistanceJointAction;
    }

    public Action createWeldJointAction(Sprite sprite, SequenceAction sequence, Formula jointId, Formula spriteB, Formula anchorX, Formula anchorY) {
        CreateWeldJointAction createWeldJointAction = (CreateWeldJointAction) action(CreateWeldJointAction.class);
        createWeldJointAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createWeldJointAction.setJointId(jointId);
        createWeldJointAction.setSpriteBName(spriteB);
        createWeldJointAction.setAnchorX(anchorX);
        createWeldJointAction.setAnchorY(anchorY);
        return createWeldJointAction;
    }

    public Action createPulleyJointAction(Sprite sprite, SequenceAction sequence, Formula jointId, Formula spriteA, Formula spriteB, Formula gndAnchorAx, Formula gndAnchorAy, Formula gndAnchorBx, Formula gndAnchorBy, Formula ratio) {
        CreatePulleyJointAction createPulleyJointAction = (CreatePulleyJointAction) action(CreatePulleyJointAction.class);
        createPulleyJointAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createPulleyJointAction.setJointId(jointId);
        createPulleyJointAction.setSpriteAName(spriteA);
        createPulleyJointAction.setSpriteBName(spriteB);
        createPulleyJointAction.setGroundAnchorAx(gndAnchorAx);
        createPulleyJointAction.setGroundAnchorAy(gndAnchorAy);
        createPulleyJointAction.setGroundAnchorBx(gndAnchorBx);
        createPulleyJointAction.setGroundAnchorBy(gndAnchorBy);
        createPulleyJointAction.setRatio(ratio);
        return createPulleyJointAction;
    }

    public Action createGearJointAction(Sprite sprite, SequenceAction sequence, Formula jointId, Formula jointAId, Formula jointBId, Formula ratio) {
        CreateGearJointAction createGearJointAction = (CreateGearJointAction) action(CreateGearJointAction.class);
        createGearJointAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createGearJointAction.setJointId(jointId);
        createGearJointAction.setJointAId(jointAId);
        createGearJointAction.setJointBId(jointBId);
        createGearJointAction.setRatio(ratio);
        return createGearJointAction;
    }

    public Action createSetThirdPersonCameraAction(Sprite sprite, SequenceAction sequence, Formula objectId, Formula distance, Formula height, Formula pitch) {
        SetThirdPersonCameraAction setThirdPersonCameraAction = (SetThirdPersonCameraAction) action(SetThirdPersonCameraAction.class);
        setThirdPersonCameraAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setThirdPersonCameraAction.setObjectId(objectId);
        setThirdPersonCameraAction.setDistance(distance);
        setThirdPersonCameraAction.setHeight(height);
        setThirdPersonCameraAction.setPitch(pitch);
        return setThirdPersonCameraAction;
    }

    public Action createSetFreeCameraAction(Sprite sprite, SequenceAction sequence) {
        SetFreeCameraAction setFreeCameraAction = (SetFreeCameraAction) action(SetFreeCameraAction.class);
        setFreeCameraAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return setFreeCameraAction;
    }

    public Action createSaveToInternalStorageAction(Sprite sprite, SequenceAction sequence, Formula projectFileName, Formula internalPath) {
        SaveToInternalStorageAction saveToInternalStorageAction = (SaveToInternalStorageAction) action(SaveToInternalStorageAction.class);
        saveToInternalStorageAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        saveToInternalStorageAction.setProjectFileName(projectFileName);
        saveToInternalStorageAction.setInternalStoragePath(internalPath);
        return saveToInternalStorageAction;
    }

    public Action createLoadFromInternalStorageAction(Sprite sprite, SequenceAction sequence, Formula internalPath) {
        LoadFromInternalStorageAction loadFromInternalStorageAction = (LoadFromInternalStorageAction) action(LoadFromInternalStorageAction.class);
        loadFromInternalStorageAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        loadFromInternalStorageAction.setInternalStoragePath(internalPath);
        return loadFromInternalStorageAction;
    }

    public Action createPrismaticJointAction(Sprite sprite, SequenceAction sequence, Formula jointId, Formula spriteB, Formula anchorX, Formula anchorY, Formula axisX, Formula axisY) {
        CreatePrismaticJointAction createPrismaticJointAction = (CreatePrismaticJointAction) action(CreatePrismaticJointAction.class);
        createPrismaticJointAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createPrismaticJointAction.setJointId(jointId);
        createPrismaticJointAction.setSpriteBName(spriteB);
        createPrismaticJointAction.setAnchorX(anchorX);
        createPrismaticJointAction.setAnchorY(anchorY);
        createPrismaticJointAction.setAxisX(axisX);
        createPrismaticJointAction.setAxisY(axisY);
        return createPrismaticJointAction;
    }

    public Action createApplyForceAction(Sprite sprite, SequenceAction sequence, Formula forceX, Formula forceY) {
        ApplyForceAction applyForceAction = (ApplyForceAction) action(ApplyForceAction.class);
        applyForceAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        applyForceAction.setForceX(forceX);
        applyForceAction.setForceY(forceY);
        return applyForceAction;
    }

    public Action createReplace3DModelAction(Sprite sprite, SequenceAction sequence, Formula objectId, Formula modelPath) {
        Replace3DModelAction replace3DModelAction = (Replace3DModelAction) action(Replace3DModelAction.class);
        replace3DModelAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        replace3DModelAction.setObjectId(objectId);
        replace3DModelAction.setModelPath(modelPath);
        return replace3DModelAction;
    }

    public Action createApplyImpulseAction(Sprite sprite, SequenceAction sequence, Formula impulseX, Formula impulseY) {
        ApplyImpulseAction applyImpulseAction = (ApplyImpulseAction) action(ApplyImpulseAction.class);
        applyImpulseAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        applyImpulseAction.setImpulseX(impulseX);
        applyImpulseAction.setImpulseY(impulseY);
        return applyImpulseAction;
    }

    public Action createApplyTorqueAction(Sprite sprite, SequenceAction sequence, Formula torque) {
        ApplyTorqueAction applyTorqueAction = (ApplyTorqueAction) action(ApplyTorqueAction.class);
        applyTorqueAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        applyTorqueAction.setTorque(torque);
        return applyTorqueAction;
    }

    public Action createApplyAngularImpulseAction(Sprite sprite, SequenceAction sequence, Formula impulse) {
        ApplyAngularImpulseAction applyAngularImpulseAction = (ApplyAngularImpulseAction) action(ApplyAngularImpulseAction.class);
        applyAngularImpulseAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        applyAngularImpulseAction.setImpulse(impulse);
        return applyAngularImpulseAction;
    }

    public Action createSetDampingAction(Sprite sprite, SequenceAction sequence, Formula linear, Formula angular) {
        SetDampingAction setDampingAction = (SetDampingAction) action(SetDampingAction.class);
        setDampingAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setDampingAction.setLinearDamping(linear);
        setDampingAction.setAngularDamping(angular);
        return setDampingAction;
    }

    public Action createPerformRayCastaction(Sprite sprite, SequenceAction sequence, Formula rayId, Formula startX, Formula startY, Formula endX, Formula endY) {
        PerformRayCastAction performRayCastAction = (PerformRayCastAction) action(PerformRayCastAction.class);
        performRayCastAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        performRayCastAction.setRayId(rayId);
        performRayCastAction.setStartX(startX);
        performRayCastAction.setStartY(startY);
        performRayCastAction.setEndX(endX);
        performRayCastAction.setEndY(endY);
        return performRayCastAction;
    }

    public Action createLaunchProjectAction(Sprite sprite, SequenceAction sequence, Formula projectName) {
        LaunchProjectAction launchProjectAction = (LaunchProjectAction) action(LaunchProjectAction.class);
        launchProjectAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        launchProjectAction.setProjectNameFormula(projectName);
        return launchProjectAction;
    }

    public Action createReturnToPreviousProjectAction() {
        return action(ReturnToPreviousProjectAction.class);
    }

    public Action createTryCatchFinallyAction(ScriptSequenceAction trySequence, ScriptSequenceAction catchSequence, ScriptSequenceAction finallySequence, UserVariable errorVariable) {
        TryCatchFinallyAction tryCatchFinallyAction = (TryCatchFinallyAction) action(TryCatchFinallyAction.class);
        tryCatchFinallyAction.setTrySequence(trySequence);
        tryCatchFinallyAction.setCatchSequence(catchSequence);
        tryCatchFinallyAction.setFinallySequence(finallySequence);
        tryCatchFinallyAction.setErrorVariable(errorVariable);
        return tryCatchFinallyAction;
    }

    public Action createStartRecordingAction() {
        return action(StartRecordingAction.class);
    }

    public Action createStopRecordingAction(Sprite sprite, SequenceAction sequence, Formula fileName) {
        StopRecordingAction stopRecordingAction = (StopRecordingAction) action(StopRecordingAction.class);
        stopRecordingAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        stopRecordingAction.setFileNameFormula(fileName);
        return stopRecordingAction;
    }

    public Action createSetCameraPositionAction(Sprite sprite, SequenceAction sequence, Formula x, Formula y) {
        SetCameraPosition2Action setCameraPosition2Action = (SetCameraPosition2Action) action(SetCameraPosition2Action.class);
        setCameraPosition2Action.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setCameraPosition2Action.setXFormula(x);
        setCameraPosition2Action.setYFormula(y);
        return setCameraPosition2Action;
    }

    public Action createSetCameraZoomAction(Sprite sprite, SequenceAction sequence, Formula zoom) {
        SetCameraZoomAction setCameraZoomAction = (SetCameraZoomAction) action(SetCameraZoomAction.class);
        setCameraZoomAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setCameraZoomAction.setZoomFormula(zoom);
        return setCameraZoomAction;
    }

    public Action createSetCameraRotationAction(Sprite sprite, SequenceAction sequence, Formula rotation) {
        SetCameraRotation2Action setCameraRotation2Action = (SetCameraRotation2Action) action(SetCameraRotation2Action.class);
        setCameraRotation2Action.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setCameraRotation2Action.setRotationFormula(rotation);
        return setCameraRotation2Action;
    }

    public Action createPinToCameraAction(Sprite sprite, SequenceAction sequence) {
        PinToCameraAction pinToCameraAction = (PinToCameraAction) action(PinToCameraAction.class);
        pinToCameraAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return pinToCameraAction;
    }

    public Action createUnpinFromCameraAction(Sprite sprite, SequenceAction sequence) {
        UnpinFromCameraAction unpinFromCameraAction = (UnpinFromCameraAction) action(UnpinFromCameraAction.class);
        unpinFromCameraAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return unpinFromCameraAction;
    }

    public EventAction createBroadcastAction(String broadcastMessage, boolean wait) {
        BroadcastAction broadcastAction = (BroadcastAction) action(BroadcastAction.class);
        broadcastAction.setBroadcastMessage(broadcastMessage);
        broadcastAction.setWait(wait);
        return broadcastAction;
    }

    public Action RunShellAction(Sprite sprite, SequenceAction sequence, Formula command, UserVariable variable) {
        RunShellAction runShellAction = (RunShellAction) action(RunShellAction.class);
        runShellAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        runShellAction.setCommand(command);
        runShellAction.setUserVariable(variable);
        return runShellAction;
    }

    public Action createWaitAction(Sprite sprite, SequenceAction sequence, Formula delay) {
        WaitAction waitAction = (WaitAction) action(WaitAction.class);
        waitAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        waitAction.setDelay(delay);
        return waitAction;
    }

    public Action createCopyProjectFileAction(Sprite sprite, SequenceAction sequence, Formula sourceFileName, Formula newFileName) {
        CopyProjectFileAction copyProjectFileAction = (CopyProjectFileAction) action(CopyProjectFileAction.class);
        copyProjectFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        copyProjectFileAction.setSourceFileName(sourceFileName);
        copyProjectFileAction.setNewFileName(newFileName);
        return copyProjectFileAction;
    }

    public Action createSetSoundVolumeAction(Sprite sprite, SequenceAction sequence, SoundInfo sound, Formula volume) {
        SetSoundVolumeAction setSoundVolumeAction = (SetSoundVolumeAction) action(SetSoundVolumeAction.class);
        setSoundVolumeAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setSoundVolumeAction.setSound(sound);
        setSoundVolumeAction.setVolume(volume);
        return setSoundVolumeAction;
    }

    public Action createWaitForSoundAction(Sprite sprite, SequenceAction sequence, Formula delay, String soundFilePath) {
        WaitForSoundAction waitForSoundAction = (WaitForSoundAction) action(WaitForSoundAction.class);
        waitForSoundAction.setSoundFilePath(soundFilePath);
        waitForSoundAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        waitForSoundAction.setDelay(delay);
        return waitForSoundAction;
    }

    public Action createCreate3dObjectAction(Sprite sprite, ScriptSequenceAction sequence, Formula objectId, Formula modelPath) {
        Create3dObjectAction create3dObjectAction = (Create3dObjectAction) Actions.action(Create3dObjectAction.class);
        create3dObjectAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        create3dObjectAction.setObjectId(objectId);
        create3dObjectAction.setModelPath(modelPath);
        return create3dObjectAction;
    }

    public Action createSetFogAction(Sprite sprite, SequenceAction sequence, Formula r, Formula g, Formula b, Formula density) {
        SetFogAction setFogAction = (SetFogAction) Actions.action(SetFogAction.class);
        setFogAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        setFogAction.red = r;
        setFogAction.green = g;
        setFogAction.blue = b;
        setFogAction.density = density;
        return setFogAction;
    }

    public Action createCastRayAction(Sprite sprite, ScriptSequenceAction sequence, Formula rayName, Formula fromX, Formula fromY, Formula fromZ, Formula dirX, Formula dirY, Formula dirZ) {
        CastRayAction castRayAction = (CastRayAction) Actions.action(CastRayAction.class);
        castRayAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        castRayAction.rayName = rayName;
        castRayAction.fromX = fromX;
        castRayAction.fromY = fromY;
        castRayAction.fromZ = fromZ;
        castRayAction.dirX = dirX;
        castRayAction.dirY = dirY;
        castRayAction.dirZ = dirZ;
        return castRayAction;
    }

    public Action createSetSkyColorAction(Sprite sprite, ScriptSequenceAction sequence, Formula red, Formula green, Formula blue) {
        SetSkyColorAction setSkyColorAction = (SetSkyColorAction) Actions.action(SetSkyColorAction.class);
        setSkyColorAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        setSkyColorAction.red = red;
        setSkyColorAction.green = green;
        setSkyColorAction.blue = blue;
        return setSkyColorAction;
    }

    public Action createRemove3dObjectAction(Sprite sprite, ScriptSequenceAction sequence, Formula objectId) {
        Remove3dObjectAction remove3dObjectAction = (Remove3dObjectAction) Actions.action(Remove3dObjectAction.class);
        remove3dObjectAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        remove3dObjectAction.objectId = objectId;
        return remove3dObjectAction;
    }

    public Action createSet3dPositionAction(Sprite sprite, ScriptSequenceAction sequence, Formula objectId, Formula x, Formula y, Formula z) {
        Set3dPositionAction set3dPositionAction = (Set3dPositionAction) Actions.action(Set3dPositionAction.class);
        set3dPositionAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        set3dPositionAction.objectId = objectId;
        set3dPositionAction.xValue = x;
        set3dPositionAction.yValue = y;
        set3dPositionAction.zValue = z;
        return set3dPositionAction;
    }

    public Action set3dVelocity(Sprite sprite, ScriptSequenceAction sequence, Formula objectId, Formula x, Formula y, Formula z) {
        Set3dVelocityAction set3dVelocityAction = (Set3dVelocityAction) Actions.action(Set3dVelocityAction.class);
        set3dVelocityAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        set3dVelocityAction.objectId = objectId;
        set3dVelocityAction.xValue = x;
        set3dVelocityAction.yValue = y;
        set3dVelocityAction.zValue = z;
        return set3dVelocityAction;
    }

    public Action apply3dForce(Sprite sprite, ScriptSequenceAction sequence, Formula objectId, Formula x, Formula y, Formula z) {
        Apply3dForceAction apply3dForceAction = (Apply3dForceAction) Actions.action(Apply3dForceAction.class);
        apply3dForceAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        apply3dForceAction.objectId = objectId;
        apply3dForceAction.xValue = x;
        apply3dForceAction.yValue = y;
        apply3dForceAction.zValue = z;
        return apply3dForceAction;
    }

    public Action createSetObjectTextureAction(Sprite sprite, ScriptSequenceAction sequence, Formula objectId, Formula textureName) {
        SetObjectTextureAction setObjectTextureAction = (SetObjectTextureAction) Actions.action(SetObjectTextureAction.class);
        setObjectTextureAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        setObjectTextureAction.objectId = objectId;
        setObjectTextureAction.textureName = textureName;
        return setObjectTextureAction;
    }

    public Action createSetCameraRotationAction(Sprite sprite, ScriptSequenceAction sequence, Formula yaw, Formula pitch, Formula roll) {
        SetCameraRotationAction setCameraRotationAction = (SetCameraRotationAction) Actions.action(SetCameraRotationAction.class);
        setCameraRotationAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        setCameraRotationAction.yaw = yaw;
        setCameraRotationAction.pitch = pitch;
        setCameraRotationAction.roll = roll;
        return setCameraRotationAction;
    }

    public Action createCube(Sprite sprite, ScriptSequenceAction sequence, Formula name2) {
        CreateCubeAction createCubeAction = (CreateCubeAction) Actions.action(CreateCubeAction.class);
        createCubeAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        createCubeAction.objectId = name2;
        return createCubeAction;
    }

    public Action set3dGravity(Sprite sprite, ScriptSequenceAction sequence, Formula x, Formula y, Formula z) {
        Set3dGravityAction set3dGravityAction = (Set3dGravityAction) Actions.action(Set3dGravityAction.class);
        set3dGravityAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        set3dGravityAction.xValue = x;
        set3dGravityAction.yValue = y;
        set3dGravityAction.zValue = z;
        return set3dGravityAction;
    }

    public Action createSetObjectColorAction(Sprite sprite, ScriptSequenceAction sequence, Formula id2, Formula r, Formula g, Formula b) {
        SetObjectColorAction setObjectColorAction = (SetObjectColorAction) Actions.action(SetObjectColorAction.class);
        setObjectColorAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        setObjectColorAction.objectId = id2;
        setObjectColorAction.red = r;
        setObjectColorAction.green = g;
        setObjectColorAction.blue = b;
        return setObjectColorAction;
    }

    public Action createSetAmbientLightAction(Sprite sprite, ScriptSequenceAction sequence, Formula r, Formula g, Formula b) {
        SetAmbientLightAction setAmbientLightAction = (SetAmbientLightAction) Actions.action(SetAmbientLightAction.class);
        setAmbientLightAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        setAmbientLightAction.red = r;
        setAmbientLightAction.green = g;
        setAmbientLightAction.blue = b;
        return setAmbientLightAction;
    }

    public Action createSetDirectionalLightAction(Sprite sprite, ScriptSequenceAction sequence, Formula id2, Formula r, Formula g, Formula b, Formula dx, Formula dy, Formula dz) {
        SetDirectionalLightAction setDirectionalLightAction = (SetDirectionalLightAction) Actions.action(SetDirectionalLightAction.class);
        setDirectionalLightAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        setDirectionalLightAction.lightId = id2;
        setDirectionalLightAction.red = r;
        setDirectionalLightAction.green = g;
        setDirectionalLightAction.blue = b;
        setDirectionalLightAction.dirX = dx;
        setDirectionalLightAction.dirY = dy;
        setDirectionalLightAction.dirZ = dz;
        return setDirectionalLightAction;
    }

    public Action createSetSpotLightAction(Sprite sprite, ScriptSequenceAction sequence, Formula lightId, Formula posX, Formula posY, Formula posZ, Formula dirX, Formula dirY, Formula dirZ, Formula colorR, Formula colorG, Formula colorB, Formula intensity, Formula angle, Formula exponent, Formula range) {
        SetSpotLightAction setSpotLightAction = (SetSpotLightAction) Actions.action(SetSpotLightAction.class);
        setSpotLightAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setSpotLightAction.setLightId(lightId);
        setSpotLightAction.setPosX(posX);
        setSpotLightAction.setPosY(posY);
        setSpotLightAction.setPosZ(posZ);
        setSpotLightAction.setDirX(dirX);
        setSpotLightAction.setDirY(dirY);
        setSpotLightAction.setDirZ(dirZ);
        setSpotLightAction.setColorR(colorR);
        setSpotLightAction.setColorG(colorG);
        setSpotLightAction.setColorB(colorB);
        setSpotLightAction.setIntensity(intensity);
        setSpotLightAction.setAngle(angle);
        setSpotLightAction.setExponent(exponent);
        setSpotLightAction.setRange(range);
        return setSpotLightAction;
    }

    public Action createRemovePbrLightAction(Sprite sprite, ScriptSequenceAction sequence, Formula lightId) {
        RemovePbrLightAction removePbrLightAction = (RemovePbrLightAction) Actions.action(RemovePbrLightAction.class);
        removePbrLightAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        removePbrLightAction.setLightId(lightId);
        return removePbrLightAction;
    }

    public Action createUploadFileAction(Sprite sprite, SequenceAction sequence, Formula url, Formula filePath, int fileTypeSelection, Formula mimeType, int storageTypeSelection) {
        UploadFileAction uploadFileAction = (UploadFileAction) Actions.action(UploadFileAction.class);
        uploadFileAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        uploadFileAction.url = url;
        uploadFileAction.filePath = filePath;
        uploadFileAction.fileTypeSelection = fileTypeSelection;
        uploadFileAction.mimeType = mimeType;
        uploadFileAction.storageTypeSelection = storageTypeSelection;
        return uploadFileAction;
    }

    public Action createSetBackgroundLightAction(Sprite sprite, ScriptSequenceAction sequence, Formula intensity) {
        SetBackgroundLightAction setBackgroundLightAction = (SetBackgroundLightAction) Actions.action(SetBackgroundLightAction.class);
        setBackgroundLightAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setBackgroundLightAction.setIntensity(intensity);
        return setBackgroundLightAction;
    }

    public Action createSetCameraRangeAction(Sprite sprite, ScriptSequenceAction sequence, Formula near, Formula far) {
        SetCameraRangeAction setCameraRangeAction = (SetCameraRangeAction) Actions.action(SetCameraRangeAction.class);
        setCameraRangeAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setCameraRangeAction.setNear(near);
        setCameraRangeAction.setFar(far);
        return setCameraRangeAction;
    }

    public Action createSetShaderUniformAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula x, Formula y, Formula z) {
        SetShaderUniformAction setShaderUniformAction = (SetShaderUniformAction) Actions.action(SetShaderUniformAction.class);
        setShaderUniformAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setShaderUniformAction.setUniformName(name2);
        setShaderUniformAction.setValueX(x);
        setShaderUniformAction.setValueY(y);
        setShaderUniformAction.setValueZ(z);
        return setShaderUniformAction;
    }

    public Action createSetSkyboxAction(Sprite sprite, SequenceAction sequence, Formula fileName) {
        SetSkyboxAction setSkyboxAction = (SetSkyboxAction) action(SetSkyboxAction.class);
        setSkyboxAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setSkyboxAction.setFileName(fileName);
        return setSkyboxAction;
    }

    public Action createSetShaderCodeAction(Sprite sprite, SequenceAction sequence, Formula vertexCode, Formula fragmentCode) {
        SetShaderCodeAction setShaderCodeAction = (SetShaderCodeAction) Actions.action(SetShaderCodeAction.class);
        setShaderCodeAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setShaderCodeAction.setVertexCode(vertexCode);
        setShaderCodeAction.setFragmentCode(fragmentCode);
        return setShaderCodeAction;
    }

    public Action createSetCCDAction(Sprite sprite, SequenceAction sequence, Formula objectId, Formula enabled) {
        SetCCDAction setCCDAction = (SetCCDAction) Actions.action(SetCCDAction.class);
        setCCDAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setCCDAction.setObjectId(objectId);
        setCCDAction.setEnabled(enabled);
        return setCCDAction;
    }

    public Action createSetPhysicsStateAction(Sprite sprite, ScriptSequenceAction sequence, Formula objectId, int stateSelection, int shapeSelection, Formula mass) {
        SetPhysicsStateAction setPhysicsStateAction = (SetPhysicsStateAction) Actions.action(SetPhysicsStateAction.class);
        setPhysicsStateAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        setPhysicsStateAction.objectId = objectId;
        setPhysicsStateAction.stateSelection = stateSelection;
        setPhysicsStateAction.shapeSelection = shapeSelection;
        setPhysicsStateAction.mass = mass;
        return setPhysicsStateAction;
    }

    public Action createEnablePbrRenderAction(Sprite sprite, ScriptSequenceAction sequence, int renderState) {
        EnablePbrRenderAction enablePbrRenderAction = (EnablePbrRenderAction) Actions.action(EnablePbrRenderAction.class);
        enablePbrRenderAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        enablePbrRenderAction.setRenderState(renderState);
        return enablePbrRenderAction;
    }

    public Action createPlayAnimationAction(Sprite sprite, ScriptSequenceAction sequence, Formula objectId, Formula animationName, Formula loops, Formula speed, Formula transitionTime) {
        PlayAnimationAction playAnimationAction = (PlayAnimationAction) Actions.action(PlayAnimationAction.class);
        playAnimationAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        playAnimationAction.setObjectId(objectId);
        playAnimationAction.setAnimationName(animationName);
        playAnimationAction.setLoops(loops);
        playAnimationAction.setSpeed(speed);
        playAnimationAction.setTransitionTime(transitionTime);
        return playAnimationAction;
    }

    public Action createStopAnimationAction(Sprite sprite, ScriptSequenceAction sequence, Formula objectId) {
        StopAnimationAction stopAnimationAction = (StopAnimationAction) Actions.action(StopAnimationAction.class);
        stopAnimationAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        stopAnimationAction.setObjectId(objectId);
        return stopAnimationAction;
    }

    public Action createSetAnisotropicFilterAction(Sprite sprite, ScriptSequenceAction sequence, Formula objectId, Formula level) {
        SetAnisotropicFilterAction setAnisotropicFilterAction = (SetAnisotropicFilterAction) Actions.action(SetAnisotropicFilterAction.class);
        setAnisotropicFilterAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setAnisotropicFilterAction.setObjectId(objectId);
        setAnisotropicFilterAction.setLevel(level);
        return setAnisotropicFilterAction;
    }

    public Action createSetPointLightAction(Sprite sprite, ScriptSequenceAction sequence, Formula lightId, Formula posX, Formula posY, Formula posZ, Formula colorR, Formula colorG, Formula colorB, Formula intensity, Formula range) {
        SetPointLightAction setPointLightAction = (SetPointLightAction) Actions.action(SetPointLightAction.class);
        setPointLightAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setPointLightAction.setLightId(lightId);
        setPointLightAction.setPosX(posX);
        setPointLightAction.setPosY(posY);
        setPointLightAction.setPosZ(posZ);
        setPointLightAction.setColorR(colorR);
        setPointLightAction.setColorG(colorG);
        setPointLightAction.setColorB(colorB);
        setPointLightAction.setIntensity(intensity);
        setPointLightAction.setRange(range);
        return setPointLightAction;
    }

    public Action createSetDirectionalLight2Action(Sprite sprite, ScriptSequenceAction sequence, Formula dirX, Formula dirY, Formula dirZ, Formula intensity) {
        SetDirectionalLigh2tAction setDirectionalLigh2tAction = (SetDirectionalLigh2tAction) Actions.action(SetDirectionalLigh2tAction.class);
        setDirectionalLigh2tAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setDirectionalLigh2tAction.setDirX(dirX);
        setDirectionalLigh2tAction.setDirY(dirY);
        setDirectionalLigh2tAction.setDirZ(dirZ);
        setDirectionalLigh2tAction.setIntensity(intensity);
        return setDirectionalLigh2tAction;
    }

    public Action createSetCameraPositionAction(Sprite sprite, ScriptSequenceAction sequence, Formula x, Formula y, Formula z) {
        SetCameraPositionAction setCameraPositionAction = (SetCameraPositionAction) Actions.action(SetCameraPositionAction.class);
        setCameraPositionAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        setCameraPositionAction.xValue = x;
        setCameraPositionAction.yValue = y;
        setCameraPositionAction.zValue = z;
        return setCameraPositionAction;
    }

    public Action createCameraLookAtAction(Sprite sprite, ScriptSequenceAction sequence, Formula x, Formula y, Formula z) {
        CameraLookAtAction cameraLookAtAction = (CameraLookAtAction) Actions.action(CameraLookAtAction.class);
        cameraLookAtAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        cameraLookAtAction.xValue = x;
        cameraLookAtAction.yValue = y;
        cameraLookAtAction.zValue = z;
        return cameraLookAtAction;
    }

    public Action createSet3dRotationAction(Sprite sprite, ScriptSequenceAction sequence, Formula objectId, Formula yaw, Formula pitch, Formula roll) {
        Set3dRotationAction set3dRotationAction = (Set3dRotationAction) Actions.action(Set3dRotationAction.class);
        set3dRotationAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        set3dRotationAction.objectId = objectId;
        set3dRotationAction.yaw = yaw;
        set3dRotationAction.pitch = pitch;
        set3dRotationAction.roll = roll;
        return set3dRotationAction;
    }

    public Action createSet3dScaleAction(Sprite sprite, ScriptSequenceAction sequence, Formula objectId, Formula scaleX, Formula scaleY, Formula scaleZ) {
        Set3dScaleAction set3dScaleAction = (Set3dScaleAction) Actions.action(Set3dScaleAction.class);
        set3dScaleAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        set3dScaleAction.objectId = objectId;
        set3dScaleAction.scaleX = scaleX;
        set3dScaleAction.scaleY = scaleY;
        set3dScaleAction.scaleZ = scaleZ;
        return set3dScaleAction;
    }

    public Action createObjectLookAtAction(Sprite sprite, ScriptSequenceAction sequence, Formula objectId, Formula x, Formula y, Formula z) {
        ObjectLookAtAction objectLookAtAction = (ObjectLookAtAction) Actions.action(ObjectLookAtAction.class);
        objectLookAtAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        objectLookAtAction.objectId = objectId;
        objectLookAtAction.xValue = x;
        objectLookAtAction.yValue = y;
        objectLookAtAction.zValue = z;
        return objectLookAtAction;
    }

    public Action createObjectLookAtCorrectAction(Sprite sprite, ScriptSequenceAction sequence, Formula objectId, Formula x, Formula y, Formula z) {
        ObjectLookAtCorrectAction objectLookAtCorrectAction = (ObjectLookAtCorrectAction) Actions.action(ObjectLookAtCorrectAction.class);
        objectLookAtCorrectAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        objectLookAtCorrectAction.objectId = objectId;
        objectLookAtCorrectAction.xValue = x;
        objectLookAtCorrectAction.yValue = y;
        objectLookAtCorrectAction.zValue = z;
        return objectLookAtCorrectAction;
    }

    public Action createPlaySoundAtAction(Sprite sprite, SequenceAction sequence, Formula delay, SoundInfo sound) {
        PlaySoundAtAction playSoundAtAction = (PlaySoundAtAction) action(PlaySoundAtAction.class);
        Scope scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        playSoundAtAction.setSprite(sprite);
        playSoundAtAction.setSound(sound);
        playSoundAtAction.setScope(scope);
        playSoundAtAction.setOffset(delay);
        return playSoundAtAction;
    }

    public Action createWaitForBubbleBrickAction(Sprite sprite, SequenceAction sequence, Formula delay) {
        WaitForBubbleBrickAction waitForBubbleBrickAction = (WaitForBubbleBrickAction) Actions.action(WaitForBubbleBrickAction.class);
        waitForBubbleBrickAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        waitForBubbleBrickAction.setDelay(delay);
        return waitForBubbleBrickAction;
    }

    public Action createChangeBrightnessByNAction(Sprite sprite, SequenceAction sequence, Formula changeBrightness) {
        ChangeBrightnessByNAction changeBrightnessByNAction = (ChangeBrightnessByNAction) Actions.action(ChangeBrightnessByNAction.class);
        changeBrightnessByNAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        changeBrightnessByNAction.setBrightness(changeBrightness);
        return changeBrightnessByNAction;
    }

    public Action createChangeColorByNAction(Sprite sprite, SequenceAction sequence, Formula changeColor) {
        ChangeColorByNAction changeColorByNAction = (ChangeColorByNAction) Actions.action(ChangeColorByNAction.class);
        changeColorByNAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        changeColorByNAction.setColor(changeColor);
        return changeColorByNAction;
    }

    public Action createChangeTransparencyByNAction(Sprite sprite, SequenceAction sequence, Formula transparency) {
        ChangeTransparencyByNAction changeTransparencyByNAction = (ChangeTransparencyByNAction) Actions.action(ChangeTransparencyByNAction.class);
        changeTransparencyByNAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        changeTransparencyByNAction.setTransparency(transparency);
        return changeTransparencyByNAction;
    }

    public Action createChangeSizeByNAction(Sprite sprite, SequenceAction sequence, Formula size) {
        ChangeSizeByNAction changeSizeByNAction = (ChangeSizeByNAction) Actions.action(ChangeSizeByNAction.class);
        changeSizeByNAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        changeSizeByNAction.setSize(size);
        return changeSizeByNAction;
    }

    public Action createChangeVolumeByNAction(Sprite sprite, SequenceAction sequence, Formula volume) {
        ChangeVolumeByNAction changeVolumeByNAction = (ChangeVolumeByNAction) Actions.action(ChangeVolumeByNAction.class);
        changeVolumeByNAction.setVolume(volume);
        changeVolumeByNAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return changeVolumeByNAction;
    }

    public Action createChangeXByNAction(Sprite sprite, SequenceAction sequence, Formula xMovement) {
        ChangeXByNAction changeXByNAction = (ChangeXByNAction) Actions.action(ChangeXByNAction.class);
        changeXByNAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        changeXByNAction.setxMovement(xMovement);
        return changeXByNAction;
    }

    public Action createChangeYByNAction(Sprite sprite, SequenceAction sequence, Formula yMovement) {
        ChangeYByNAction changeYByNAction = (ChangeYByNAction) Actions.action(ChangeYByNAction.class);
        changeYByNAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        changeYByNAction.setyMovement(yMovement);
        return changeYByNAction;
    }

    public Action createSetRotationStyleAction(Sprite sprite, int mode) {
        SetRotationStyleAction setRotationStyleAction = (SetRotationStyleAction) Actions.action(SetRotationStyleAction.class);
        setRotationStyleAction.setRotationStyle(mode);
        setRotationStyleAction.setSprite(sprite);
        return setRotationStyleAction;
    }

    public Action createClearGraphicEffectAction(Sprite sprite) {
        ClearGraphicEffectAction clearGraphicEffectAction = (ClearGraphicEffectAction) Actions.action(ClearGraphicEffectAction.class);
        clearGraphicEffectAction.setSprite(sprite);
        return clearGraphicEffectAction;
    }

    public Action createComeToFrontAction(Sprite sprite) {
        ComeToFrontAction comeToFrontAction = (ComeToFrontAction) Actions.action(ComeToFrontAction.class);
        comeToFrontAction.setSprite(sprite);
        return comeToFrontAction;
    }

    public Action createGlideToAction(Sprite sprite, SequenceAction sequence, Formula x, Formula y, Formula duration) {
        GlideToAction glideToAction = (GlideToAction) Actions.action(GlideToAction.class);
        glideToAction.setPosition(x, y);
        glideToAction.setDuration(duration);
        glideToAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return glideToAction;
    }

    public Action createPlaceAtAction(Sprite sprite, SequenceAction sequence, Formula x, Formula y) {
        GlideToAction glideToAction = (GlideToAction) Actions.action(GlideToAction.class);
        glideToAction.setPosition(x, y);
        glideToAction.setDuration(0.0f);
        glideToAction.setInterpolation(null);
        glideToAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return glideToAction;
    }

    public Action createSquareAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula color, Formula x, Formula y, Formula w, Formula h, Formula trans, Formula rot, Formula borders) {
        SquareAction squareAction = (SquareAction) Actions.action(SquareAction.class);
        squareAction.setX(x);
        squareAction.setY(y);
        squareAction.setNam(name2);
        squareAction.setColor(color);
        squareAction.setW(w);
        squareAction.setH(h);
        squareAction.setTrans(trans);
        squareAction.setRot(rot);
        squareAction.setBord(borders);
        squareAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return squareAction;
    }

    public Action createDelSquareAction(Sprite sprite, SequenceAction sequence, Formula name2) {
        DelSquareAction delSquareAction = (DelSquareAction) Actions.action(DelSquareAction.class);
        delSquareAction.setNam(name2);
        delSquareAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return delSquareAction;
    }

    public Action createGoToAction(Sprite sprite, Sprite destinationSprite, int spinnerSelection) {
        switch (spinnerSelection) {
            case 80:
                GoToTouchPositionAction goToTouchPositionAction = (GoToTouchPositionAction) Actions.action(GoToTouchPositionAction.class);
                goToTouchPositionAction.setSprite(sprite);
                return goToTouchPositionAction;
            case 81:
                GoToRandomPositionAction goToRandomPositionAction = (GoToRandomPositionAction) Actions.action(GoToRandomPositionAction.class);
                goToRandomPositionAction.setSprite(sprite);
                return goToRandomPositionAction;
            case 82:
                GoToOtherSpritePositionAction goToOtherSpritePositionAction = (GoToOtherSpritePositionAction) Actions.action(GoToOtherSpritePositionAction.class);
                goToOtherSpritePositionAction.setSprite(sprite);
                goToOtherSpritePositionAction.setDestinationSprite(destinationSprite);
                return goToOtherSpritePositionAction;
            default:
                return null;
        }
    }

    public Action createGoNStepsBackAction(Sprite sprite, SequenceAction sequence, Formula steps) {
        GoNStepsBackAction goNStepsBackAction = (GoNStepsBackAction) Actions.action(GoNStepsBackAction.class);
        goNStepsBackAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        goNStepsBackAction.setSteps(steps);
        return goNStepsBackAction;
    }

    public Action createHideAction(Sprite sprite) {
        SetVisibleAction setVisibleAction = (SetVisibleAction) Actions.action(SetVisibleAction.class);
        setVisibleAction.setSprite(sprite);
        setVisibleAction.setVisible(false);
        return setVisibleAction;
    }

    public Action createIfOnEdgeBounceAction(Sprite sprite) {
        IfOnEdgeBounceAction ifOnEdgeBounceAction = (IfOnEdgeBounceAction) Actions.action(IfOnEdgeBounceAction.class);
        ifOnEdgeBounceAction.setSprite(sprite);
        return ifOnEdgeBounceAction;
    }

    public Action createLegoNxtMotorMoveAction(Sprite sprite, SequenceAction sequence, LegoNxtMotorMoveBrick.Motor motorEnum, Formula speed) {
        LegoNxtMotorMoveAction legoNxtMotorMoveAction = (LegoNxtMotorMoveAction) Actions.action(LegoNxtMotorMoveAction.class);
        legoNxtMotorMoveAction.setMotorEnum(motorEnum);
        legoNxtMotorMoveAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        legoNxtMotorMoveAction.setSpeed(speed);
        return legoNxtMotorMoveAction;
    }

    public Action createLegoNxtMotorStopAction(LegoNxtMotorStopBrick.Motor motorEnum) {
        LegoNxtMotorStopAction legoNxtMotorStopAction = (LegoNxtMotorStopAction) Actions.action(LegoNxtMotorStopAction.class);
        legoNxtMotorStopAction.setMotorEnum(motorEnum);
        return legoNxtMotorStopAction;
    }

    public Action createLegoNxtMotorTurnAngleAction(Sprite sprite, SequenceAction sequence, LegoNxtMotorTurnAngleBrick.Motor motorEnum, Formula degrees) {
        LegoNxtMotorTurnAngleAction legoNxtMotorTurnAngleAction = (LegoNxtMotorTurnAngleAction) Actions.action(LegoNxtMotorTurnAngleAction.class);
        legoNxtMotorTurnAngleAction.setMotorEnum(motorEnum);
        legoNxtMotorTurnAngleAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        legoNxtMotorTurnAngleAction.setDegrees(degrees);
        return legoNxtMotorTurnAngleAction;
    }

    public Action createLegoNxtPlayToneAction(Sprite sprite, SequenceAction sequence, Formula hertz, Formula durationInSeconds) {
        LegoNxtPlayToneAction legoNxtPlayToneAction = (LegoNxtPlayToneAction) Actions.action(LegoNxtPlayToneAction.class);
        legoNxtPlayToneAction.setHertz(hertz);
        legoNxtPlayToneAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        legoNxtPlayToneAction.setDurationInSeconds(durationInSeconds);
        return legoNxtPlayToneAction;
    }

    public Action createLegoEv3SingleMotorMoveAction(Sprite sprite, SequenceAction sequence, LegoEv3MotorMoveBrick.Motor motorEnum, Formula speed) {
        LegoEv3MotorMoveAction legoEv3MotorMoveAction = (LegoEv3MotorMoveAction) action(LegoEv3MotorMoveAction.class);
        legoEv3MotorMoveAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        legoEv3MotorMoveAction.setMotorEnum(motorEnum);
        legoEv3MotorMoveAction.setSpeed(speed);
        return legoEv3MotorMoveAction;
    }

    public Action createLegoEv3MotorStopAction(LegoEv3MotorStopBrick.Motor motorEnum) {
        LegoEv3MotorStopAction legoEv3MotorStopAction = (LegoEv3MotorStopAction) action(LegoEv3MotorStopAction.class);
        legoEv3MotorStopAction.setMotorEnum(motorEnum);
        return legoEv3MotorStopAction;
    }

    public Action createLegoEv3SetLedAction(LegoEv3SetLedBrick.LedStatus ledStatusEnum) {
        LegoEv3SetLedAction legoEv3SetLedAction = (LegoEv3SetLedAction) action(LegoEv3SetLedAction.class);
        legoEv3SetLedAction.setLedStatusEnum(ledStatusEnum);
        return legoEv3SetLedAction;
    }

    public Action createLegoEv3PlayToneAction(Sprite sprite, SequenceAction sequence, Formula hertz, Formula durationInSeconds, Formula volumeInPercent) {
        LegoEv3PlayToneAction legoEv3PlayToneAction = (LegoEv3PlayToneAction) action(LegoEv3PlayToneAction.class);
        legoEv3PlayToneAction.setHertz(hertz);
        legoEv3PlayToneAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        legoEv3PlayToneAction.setDurationInSeconds(durationInSeconds);
        legoEv3PlayToneAction.setVolumeInPercent(volumeInPercent);
        return legoEv3PlayToneAction;
    }

    public Action createLegoEv3MotorTurnAngleAction(Sprite sprite, SequenceAction sequence, LegoEv3MotorTurnAngleBrick.Motor motorEnum, Formula degrees) {
        LegoEv3MotorTurnAngleAction legoEv3MotorTurnAngleAction = (LegoEv3MotorTurnAngleAction) action(LegoEv3MotorTurnAngleAction.class);
        legoEv3MotorTurnAngleAction.setMotorEnum(motorEnum);
        legoEv3MotorTurnAngleAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        legoEv3MotorTurnAngleAction.setDegrees(degrees);
        return legoEv3MotorTurnAngleAction;
    }

    public Action createPhiroPlayToneActionAction(Sprite sprite, SequenceAction sequence, PhiroPlayToneBrick.Tone toneEnum, Formula duration) {
        PhiroPlayToneAction phiroPlayToneAction = (PhiroPlayToneAction) action(PhiroPlayToneAction.class);
        phiroPlayToneAction.setSelectedTone(toneEnum);
        phiroPlayToneAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        phiroPlayToneAction.setDurationInSeconds(duration);
        return phiroPlayToneAction;
    }

    public Action createPhiroMotorMoveForwardActionAction(Sprite sprite, SequenceAction sequence, PhiroMotorMoveForwardBrick.Motor motorEnum, Formula speed) {
        PhiroMotorMoveForwardAction phiroMotorMoveForwardAction = (PhiroMotorMoveForwardAction) action(PhiroMotorMoveForwardAction.class);
        phiroMotorMoveForwardAction.setMotorEnum(motorEnum);
        phiroMotorMoveForwardAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        phiroMotorMoveForwardAction.setSpeed(speed);
        return phiroMotorMoveForwardAction;
    }

    public Action createPhiroMotorMoveBackwardActionAction(Sprite sprite, SequenceAction sequence, PhiroMotorMoveBackwardBrick.Motor motorEnum, Formula speed) {
        PhiroMotorMoveBackwardAction phiroMotorMoveBackwardAction = (PhiroMotorMoveBackwardAction) action(PhiroMotorMoveBackwardAction.class);
        phiroMotorMoveBackwardAction.setMotorEnum(motorEnum);
        phiroMotorMoveBackwardAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        phiroMotorMoveBackwardAction.setSpeed(speed);
        return phiroMotorMoveBackwardAction;
    }

    public Action createPhiroRgbLedEyeActionAction(Sprite sprite, SequenceAction sequence, PhiroRGBLightBrick.Eye eye, Formula red, Formula green, Formula blue) {
        PhiroRGBLightAction phiroRGBLightAction = (PhiroRGBLightAction) action(PhiroRGBLightAction.class);
        phiroRGBLightAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        phiroRGBLightAction.setEyeEnum(eye);
        phiroRGBLightAction.setRed(red);
        phiroRGBLightAction.setGreen(green);
        phiroRGBLightAction.setBlue(blue);
        return phiroRGBLightAction;
    }

    public Action createPhiroSendSelectedSensorAction(Sprite sprite, SequenceAction sequence, int sensorNumber, Action ifAction, Action elseAction) {
        PhiroSensorAction phiroSensorAction = (PhiroSensorAction) action(PhiroSensorAction.class);
        phiroSensorAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        phiroSensorAction.setSensor(sensorNumber);
        phiroSensorAction.setIfAction(ifAction);
        phiroSensorAction.setElseAction(elseAction);
        return phiroSensorAction;
    }

    public Action createPhiroMotorStopActionAction(PhiroMotorStopBrick.Motor motorEnum) {
        PhiroMotorStopAction phiroMotorStopAction = (PhiroMotorStopAction) action(PhiroMotorStopAction.class);
        phiroMotorStopAction.setMotorEnum(motorEnum);
        return phiroMotorStopAction;
    }

    public Action createMoveNStepsAction(Sprite sprite, SequenceAction sequence, Formula steps) {
        MoveNStepsAction moveNStepsAction = (MoveNStepsAction) Actions.action(MoveNStepsAction.class);
        moveNStepsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        moveNStepsAction.setSteps(steps);
        return moveNStepsAction;
    }

    public Action createPenDownAction(Sprite sprite) {
        PenDownAction penDownAction = (PenDownAction) Actions.action(PenDownAction.class);
        penDownAction.setSprite(sprite);
        return penDownAction;
    }

    public Action createPenUpAction(Sprite sprite) {
        PenUpAction penUpAction = (PenUpAction) Actions.action(PenUpAction.class);
        penUpAction.setSprite(sprite);
        return penUpAction;
    }

    public Action createStartPlotAction(Sprite sprite) {
        StartPlotAction startPlotAction = (StartPlotAction) Actions.action(StartPlotAction.class);
        startPlotAction.setSprite(sprite);
        return startPlotAction;
    }

    public Action createStopPlotAction(Sprite sprite) {
        StopPlotAction stopPlotAction = (StopPlotAction) Actions.action(StopPlotAction.class);
        stopPlotAction.setSprite(sprite);
        return stopPlotAction;
    }

    public Action createSetPenSizeAction(Sprite sprite, SequenceAction sequence, Formula penSize) {
        SetPenSizeAction setPenSizeAction = (SetPenSizeAction) Actions.action(SetPenSizeAction.class);
        setPenSizeAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setPenSizeAction.setPenSize(penSize);
        return setPenSizeAction;
    }

    public Action createSetPenColorAction(Sprite sprite, SequenceAction sequence, Formula red, Formula green, Formula blue) {
        SetPenColorAction setPenColorAction = (SetPenColorAction) Actions.action(SetPenColorAction.class);
        setPenColorAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setPenColorAction.setRed(red);
        setPenColorAction.setGreen(green);
        setPenColorAction.setBlue(blue);
        return setPenColorAction;
    }

    public Action createClearBackgroundAction() {
        return Actions.action(ClearBackgroundAction.class);
    }

    public Action createSetCameraFocusPointAction(Sprite sprite, SequenceAction sequence, Formula horizontal, Formula vertical) {
        SetCameraFocusPointAction setCameraFocusPointAction = (SetCameraFocusPointAction) action(SetCameraFocusPointAction.class);
        setCameraFocusPointAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setCameraFocusPointAction.setSprite(sprite);
        setCameraFocusPointAction.setHorizontal(horizontal);
        setCameraFocusPointAction.setVertical(vertical);
        return setCameraFocusPointAction;
    }

    public Action createStampAction(Sprite sprite) {
        StampAction stampAction = (StampAction) Actions.action(StampAction.class);
        stampAction.setSprite(sprite);
        return stampAction;
    }

    public Action createPlaySoundAction(Sprite sprite, SoundInfo sound) {
        PlaySoundAction playSoundAction = (PlaySoundAction) Actions.action(PlaySoundAction.class);
        playSoundAction.setSprite(sprite);
        playSoundAction.setSound(sound);
        return playSoundAction;
    }

    public Action createStopSoundAction(Sprite sprite, SoundInfo sound) {
        StopSoundAction stopSoundAction = (StopSoundAction) Actions.action(StopSoundAction.class);
        stopSoundAction.setSprite(sprite);
        stopSoundAction.setSound(sound);
        return stopSoundAction;
    }

    public Action createPointInDirectionAction(Sprite sprite, SequenceAction sequence, Formula degrees) {
        PointInDirectionAction pointInDirectionAction = (PointInDirectionAction) Actions.action(PointInDirectionAction.class);
        pointInDirectionAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        pointInDirectionAction.setDegreesInUserInterfaceDimensionUnit(degrees);
        return pointInDirectionAction;
    }

    public Action createPointToAction(Sprite sprite, Sprite pointedSprite) {
        PointToAction pointToAction = (PointToAction) Actions.action(PointToAction.class);
        pointToAction.setSprite(sprite);
        pointToAction.setPointedSprite(pointedSprite);
        return pointToAction;
    }

    public Action createCloneAction(Sprite sprite) {
        CloneAction cloneAction = (CloneAction) Actions.action(CloneAction.class);
        cloneAction.setSprite(sprite);
        return cloneAction;
    }

    public Action createCloneAndNameAction(Sprite spriteToClone, Scope scope, Formula cloneName) {
        CloneAndNameAction cloneAndNameAction = (CloneAndNameAction) action(CloneAndNameAction.class);
        cloneAndNameAction.setScope(scope);
        cloneAndNameAction.setSprite(spriteToClone);
        cloneAndNameAction.setCloneName(cloneName);
        return cloneAndNameAction;
    }

    public Action createDeleteThisCloneAction(Sprite sprite) {
        DeleteThisCloneAction deleteThisCloneAction = (DeleteThisCloneAction) Actions.action(DeleteThisCloneAction.class);
        deleteThisCloneAction.setSprite(sprite);
        return deleteThisCloneAction;
    }

    public Action createSetBrightnessAction(Sprite sprite, SequenceAction sequence, Formula brightness) {
        SetBrightnessAction setBrightnessAction = (SetBrightnessAction) Actions.action(SetBrightnessAction.class);
        setBrightnessAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setBrightnessAction.setBrightness(brightness);
        return setBrightnessAction;
    }

    public Action createSetColorAction(Sprite sprite, SequenceAction sequence, Formula color) {
        SetColorAction setColorAction = (SetColorAction) Actions.action(SetColorAction.class);
        setColorAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setColorAction.setColor(color);
        return setColorAction;
    }

    public Action playVideoAction(Sprite sprite, SequenceAction sequence, Formula name2) {
        PlayVideoAction playVideoAction = (PlayVideoAction) Actions.action(PlayVideoAction.class);
        playVideoAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        playVideoAction.setName(name2);
        return playVideoAction;
    }

    public Action pauseVideoAction(Sprite sprite, SequenceAction sequence, Formula name2) {
        PauseVideoAction pauseVideoAction = (PauseVideoAction) Actions.action(PauseVideoAction.class);
        pauseVideoAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        pauseVideoAction.setName(name2);
        return pauseVideoAction;
    }

    public Action seekVideoAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula time) {
        SeekVideoAction seekVideoAction = (SeekVideoAction) Actions.action(SeekVideoAction.class);
        seekVideoAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        seekVideoAction.setName(name2);
        seekVideoAction.setTime(time);
        return seekVideoAction;
    }

    public Action createSetTransparencyAction(Sprite sprite, SequenceAction sequence, Formula transparency) {
        SetTransparencyAction setTransparencyAction = (SetTransparencyAction) Actions.action(SetTransparencyAction.class);
        setTransparencyAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setTransparencyAction.setTransparency(transparency);
        return setTransparencyAction;
    }

    public Action createSetLookAction(Sprite sprite, LookData lookData) {
        return createSetLookAction(sprite, lookData, false);
    }

    public Action createSetHitboxAction(Sprite sprite, SequenceAction sequence, LookData lookData) {
        SetHitboxAction setHitboxAction = (SetHitboxAction) Actions.action(SetHitboxAction.class);
        setHitboxAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setHitboxAction.setLookData(lookData);
        return setHitboxAction;
    }

    public Action createSetLookAction(Sprite sprite, LookData lookData, boolean wait) {
        SetLookAction setLookAction = (SetLookAction) Actions.action(SetLookAction.class);
        setLookAction.setSprite(sprite);
        setLookAction.setLookData(lookData);
        setLookAction.setWait(wait);
        return setLookAction;
    }

    public Action createSetLookByIndexAction(Sprite sprite, SequenceAction sequence, Formula formula) {
        SetLookByIndexAction setLookByIndexAction = (SetLookByIndexAction) Actions.action(SetLookByIndexAction.class);
        setLookByIndexAction.setSprite(sprite);
        setLookByIndexAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setLookByIndexAction.setFormula(formula);
        return setLookByIndexAction;
    }

    public Action createSetBackgroundAction(LookData lookData, boolean wait) {
        SetLookAction setLookAction = (SetLookAction) Actions.action(SetLookAction.class);
        setLookAction.setSprite(ProjectManager.getInstance().getCurrentlyPlayingScene().getBackgroundSprite());
        setLookAction.setLookData(lookData);
        setLookAction.setWait(wait);
        return setLookAction;
    }

    public Action createSetBackgroundByIndexAction(Sprite sprite, SequenceAction sequence, Formula formula, boolean wait) {
        SetLookByIndexAction setLookByIndexAction = (SetLookByIndexAction) Actions.action(SetLookByIndexAction.class);
        setLookByIndexAction.setSprite(ProjectManager.getInstance().getCurrentlyPlayingScene().getBackgroundSprite());
        setLookByIndexAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setLookByIndexAction.setFormula(formula);
        setLookByIndexAction.setWait(wait);
        return setLookByIndexAction;
    }

    public Action createSetNextLookAction(Sprite sprite, SequenceAction sequence) {
        SetNextLookAction setNextLookAction = (SetNextLookAction) Actions.action(SetNextLookAction.class);
        setNextLookAction.setSprite(sprite);
        setNextLookAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return setNextLookAction;
    }

    public Action createSetPreviousLookAction(Sprite sprite, SequenceAction sequence) {
        SetPreviousLookAction setPreviousLookAction = (SetPreviousLookAction) action(SetPreviousLookAction.class);
        setPreviousLookAction.setSprite(sprite);
        setPreviousLookAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return setPreviousLookAction;
    }

    public Action createDeleteLookAction(Sprite sprite) {
        DeleteLookAction deleteLookAction = (DeleteLookAction) Actions.action(DeleteLookAction.class);
        deleteLookAction.setSprite(sprite);
        return deleteLookAction;
    }

    public Action createSetSizeToAction(Sprite sprite, SequenceAction sequence, Formula size) {
        SetSizeToAction setSizeToAction = (SetSizeToAction) Actions.action(SetSizeToAction.class);
        setSizeToAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setSizeToAction.setSize(size);
        return setSizeToAction;
    }

    public Action createSetWidthAction(Sprite sprite, SequenceAction sequence, Formula size) {
        SetWidthAction setWidthAction = (SetWidthAction) Actions.action(SetWidthAction.class);
        setWidthAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setWidthAction.setSize(size);
        return setWidthAction;
    }

    public Action createChangeWidthAction(Sprite sprite, SequenceAction sequence, Formula size) {
        ChangeWidthAction changeWidthAction = (ChangeWidthAction) Actions.action(ChangeWidthAction.class);
        changeWidthAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        changeWidthAction.setSize(size);
        return changeWidthAction;
    }

    public Action createSetHeightAction(Sprite sprite, SequenceAction sequence, Formula size) {
        SetHeightAction setHeightAction = (SetHeightAction) Actions.action(SetHeightAction.class);
        setHeightAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setHeightAction.setSize(size);
        return setHeightAction;
    }

    public Action createChangeHeightAction(Sprite sprite, SequenceAction sequence, Formula size) {
        ChangeHeightAction changeHeightAction = (ChangeHeightAction) Actions.action(ChangeHeightAction.class);
        changeHeightAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        changeHeightAction.setSize(size);
        return changeHeightAction;
    }

    public Action createGlideToPhysicsAction(Sprite sprite, PhysicsLook physicsLook, SequenceAction sequence, Formula x, Formula y, float duration, float delta) {
        GlideToPhysicsAction glideToPhysicsAction = (GlideToPhysicsAction) Actions.action(GlideToPhysicsAction.class);
        glideToPhysicsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        glideToPhysicsAction.setPhysicsLook(physicsLook);
        glideToPhysicsAction.setPosition(x, y);
        glideToPhysicsAction.setDuration(duration);
        glideToPhysicsAction.act(delta);
        return glideToPhysicsAction;
    }

    public Action createSetVolumeToAction(Sprite sprite, SequenceAction sequence, Formula volume) {
        SetVolumeToAction setVolumeToAction = (SetVolumeToAction) Actions.action(SetVolumeToAction.class);
        setVolumeToAction.setVolume(volume);
        setVolumeToAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return setVolumeToAction;
    }

    public Action createSetXAction(Sprite sprite, SequenceAction sequence, Formula x) {
        SetXAction setXAction = (SetXAction) Actions.action(SetXAction.class);
        setXAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setXAction.setX(x);
        return setXAction;
    }

    public Action createSetYAction(Sprite sprite, SequenceAction sequence, Formula y) {
        SetYAction setYAction = (SetYAction) Actions.action(SetYAction.class);
        setYAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setYAction.setY(y);
        return setYAction;
    }

    public Action createShowAction(Sprite sprite) {
        SetVisibleAction setVisibleAction = (SetVisibleAction) Actions.action(SetVisibleAction.class);
        setVisibleAction.setSprite(sprite);
        setVisibleAction.setVisible(true);
        return setVisibleAction;
    }

    public Action createSpeakAction(Sprite sprite, SequenceAction sequence, Formula text) {
        SpeakAction speakAction = (SpeakAction) action(SpeakAction.class);
        speakAction.setSpeechSynthesizer(new SpeechSynthesizer(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence), text));
        speakAction.setMobileServiceAvailability((MobileServiceAvailability) KoinJavaComponent.get(MobileServiceAvailability.class));
        speakAction.setContext(StageActivity.activeStageActivity.get());
        return speakAction;
    }

    public Action createSpeakAndWaitAction(Sprite sprite, SequenceAction sequence, Formula text) {
        SpeakAndWaitAction speakAndWaitAction = (SpeakAndWaitAction) action(SpeakAndWaitAction.class);
        speakAndWaitAction.setSpeechSynthesizer(new SpeechSynthesizer(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence), text));
        speakAndWaitAction.setMobileServiceAvailability((MobileServiceAvailability) KoinJavaComponent.get(MobileServiceAvailability.class));
        speakAndWaitAction.setContext(StageActivity.activeStageActivity.get());
        return speakAndWaitAction;
    }

    public Action createStopAllSoundsAction() {
        return Actions.action(StopAllSoundsAction.class);
    }

    public Action createPauseForBeatsAction(Sprite sprite, SequenceAction sequence, Formula beats) {
        PauseForBeatsAction pauseForBeatsAction = (PauseForBeatsAction) action(PauseForBeatsAction.class);
        pauseForBeatsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        pauseForBeatsAction.setBeats(beats);
        return pauseForBeatsAction;
    }

    public Action createPlayNoteForBeatsAction(Sprite sprite, SequenceAction sequence, Formula note, Formula beats) {
        PlayNoteForBeatsAction playNoteForBeatsAction = (PlayNoteForBeatsAction) action(PlayNoteForBeatsAction.class);
        playNoteForBeatsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        playNoteForBeatsAction.setMidiValue(note);
        playNoteForBeatsAction.setBeats(beats);
        return playNoteForBeatsAction;
    }

    public Action createSetInstrumentAction(PickableMusicalInstrument instrument) {
        SetInstrumentAction setInstrumentAction = (SetInstrumentAction) action(SetInstrumentAction.class);
        setInstrumentAction.setInstrument(instrument);
        return setInstrumentAction;
    }

    public Action createSetTempoAction(Sprite sprite, SequenceAction sequence, Formula tempo) {
        SetTempoAction setTempoAction = (SetTempoAction) action(SetTempoAction.class);
        setTempoAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setTempoAction.setTempo(tempo);
        return setTempoAction;
    }

    public Action createChangeTempoAction(Sprite sprite, SequenceAction sequence, Formula tempo) {
        ChangeTempoByAction changeTempoByAction = (ChangeTempoByAction) action(ChangeTempoByAction.class);
        changeTempoByAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        changeTempoByAction.setTempo(tempo);
        return changeTempoByAction;
    }

    public Action createPlayDrumForBeatsAction(Sprite sprite, SequenceAction sequence, Formula beats, PickableDrum drum) {
        PlayDrumForBeatsAction playDrumForBeatsAction = (PlayDrumForBeatsAction) action(PlayDrumForBeatsAction.class);
        playDrumForBeatsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        playDrumForBeatsAction.setBeats(beats);
        playDrumForBeatsAction.setDrum(drum);
        return playDrumForBeatsAction;
    }

    public Action createTurnLeftAction(Sprite sprite, SequenceAction sequence, Formula degrees) {
        TurnLeftAction turnLeftAction = (TurnLeftAction) Actions.action(TurnLeftAction.class);
        turnLeftAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        turnLeftAction.setDegrees(degrees);
        return turnLeftAction;
    }

    public Action createTurnRightAction(Sprite sprite, SequenceAction sequence, Formula degrees) {
        TurnRightAction turnRightAction = (TurnRightAction) Actions.action(TurnRightAction.class);
        turnRightAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        turnRightAction.setDegrees(degrees);
        return turnRightAction;
    }

    public Action createChangeVariableAction(Sprite sprite, SequenceAction sequence, Formula variableFormula, UserVariable userVariable) {
        ChangeVariableAction changeVariableAction = (ChangeVariableAction) Actions.action(ChangeVariableAction.class);
        changeVariableAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        changeVariableAction.setChangeVariable(variableFormula);
        changeVariableAction.setUserVariable(userVariable);
        return changeVariableAction;
    }

    public Action createSetVariableAction(Sprite sprite, SequenceAction sequence, Formula variableFormula, UserVariable userVariable) {
        SetVariableAction setVariableAction = (SetVariableAction) Actions.action(SetVariableAction.class);
        setVariableAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setVariableAction.setChangeVariable(variableFormula);
        setVariableAction.setUserVariable(userVariable);
        return setVariableAction;
    }

    public Action createGetZipFileNamesAction(Sprite sprite, SequenceAction sequence, Formula zipFileName, UserVariable userVariable) {
        GetZipFileNamesAction getZipFileNamesAction = (GetZipFileNamesAction) action(GetZipFileNamesAction.class);
        getZipFileNamesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        getZipFileNamesAction.setZipFileName(zipFileName);
        getZipFileNamesAction.setUserVariable(userVariable);
        return getZipFileNamesAction;
    }

    public Action createTextfield(Sprite sprite, SequenceAction sequence, Formula name2, Formula def, Formula x, Formula y, Formula w, Formula h, Formula ts, Formula tc, Formula bc, Formula ht, Formula hc, Formula align, Formula pass, Formula corner, Formula len, Formula type, Formula font, UserVariable userVariable) {
        CreateTextFieldAction createTextFieldAction = (CreateTextFieldAction) Actions.action(CreateTextFieldAction.class);
        createTextFieldAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createTextFieldAction.setName(name2);
        createTextFieldAction.setInitialText(def);
        createTextFieldAction.setPosX(x);
        createTextFieldAction.setPosY(y);
        createTextFieldAction.setWidth(w);
        createTextFieldAction.setHeight(h);
        createTextFieldAction.setText_size(ts);
        createTextFieldAction.setText_color(tc);
        createTextFieldAction.setBg_color(bc);
        createTextFieldAction.setHint_text(ht);
        createTextFieldAction.setHint_color(hc);
        createTextFieldAction.setAlignment_f(align);
        createTextFieldAction.set_password(pass);
        createTextFieldAction.setCorner_radius(corner);
        createTextFieldAction.setMax_length(len);
        createTextFieldAction.setInput_type(type);
        createTextFieldAction.setFont_path(font);
        createTextFieldAction.setVariable(userVariable);
        return createTextFieldAction;
    }

    public Action createReadFromFilesAction(Sprite sprite, SequenceAction sequence, Formula value, UserVariable userVariable) {
        ReadFromFilesAction readFromFilesAction = (ReadFromFilesAction) Actions.action(ReadFromFilesAction.class);
        readFromFilesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        readFromFilesAction.setFileName(value);
        readFromFilesAction.setVariable(userVariable);
        return readFromFilesAction;
    }

    public Action createDeleteFilesAction(Sprite sprite, SequenceAction sequence, Formula value) {
        DeleteFilesAction deleteFilesAction = (DeleteFilesAction) Actions.action(DeleteFilesAction.class);
        deleteFilesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        deleteFilesAction.setFileName(value);
        return deleteFilesAction;
    }

    public Action createWriteToFilesAction(Sprite sprite, SequenceAction sequence, Formula value, UserVariable userVariable) {
        WriteToFilesAction writeToFilesAction = (WriteToFilesAction) Actions.action(WriteToFilesAction.class);
        writeToFilesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        writeToFilesAction.setFileName(value);
        writeToFilesAction.setVariable(userVariable);
        return writeToFilesAction;
    }

    public Action createPostWebRequestAction(Sprite sprite, SequenceAction sequence, Formula url, Formula header, Formula body, UserVariable userVariable) {
        PostWebRequestAction postWebRequestAction = (PostWebRequestAction) Actions.action(PostWebRequestAction.class);
        postWebRequestAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        postWebRequestAction.setRurl(url);
        postWebRequestAction.setHeader(header);
        postWebRequestAction.setBody(body);
        postWebRequestAction.setVariable(userVariable);
        return postWebRequestAction;
    }

    public Action createRunJS(Sprite sprite, SequenceAction sequence, Formula code, UserVariable userVariable) {
        RunJSAction runJSAction = (RunJSAction) Actions.action(RunJSAction.class);
        runJSAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        runJSAction.setRunScript(code);
        runJSAction.setUserVariable(userVariable);
        return runJSAction;
    }

    public Action createRunLua(Sprite sprite, SequenceAction sequence, Formula code, UserVariable userVariable) {
        RunLuaAction runLuaAction = (RunLuaAction) Actions.action(RunLuaAction.class);
        runLuaAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        runLuaAction.setCode(code);
        runLuaAction.setUserVariable(userVariable);
        return runLuaAction;
    }

    public Action createResizeImg(Sprite sprite, SequenceAction sequence, Formula file, Formula x, Formula y) {
        ResizeImgAction resizeImgAction = (ResizeImgAction) Actions.action(ResizeImgAction.class);
        resizeImgAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        resizeImgAction.setFile(file);
        resizeImgAction.setW(x);
        resizeImgAction.setH(y);
        return resizeImgAction;
    }

    public Action createGrayscaleImg(Sprite sprite, SequenceAction sequence, Formula file) {
        GrayscaleImgAction grayscaleImgAction = (GrayscaleImgAction) Actions.action(GrayscaleImgAction.class);
        grayscaleImgAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        grayscaleImgAction.setFile(file);
        return grayscaleImgAction;
    }

    public Action createNormalizeImg(Sprite sprite, SequenceAction sequence, Formula file, Formula r, Formula g, Formula b) {
        NormalizeImgAction normalizeImgAction = (NormalizeImgAction) Actions.action(NormalizeImgAction.class);
        normalizeImgAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        normalizeImgAction.setFile(file);
        normalizeImgAction.setR(r);
        normalizeImgAction.setG(g);
        normalizeImgAction.setB(b);
        return normalizeImgAction;
    }

    public Action createBindVmOutputAction(UserVariable variable) {
        BindVmOutputAction bindVmOutputAction = (BindVmOutputAction) action(BindVmOutputAction.class);
        bindVmOutputAction.setUserVariable(variable);
        return bindVmOutputAction;
    }

    public Action createSendVmInputAction(Sprite sprite, SequenceAction sequence, Formula inputText) {
        SendVmInputAction sendVmInputAction = (SendVmInputAction) action(SendVmInputAction.class);
        sendVmInputAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        sendVmInputAction.setInputText(inputText);
        return sendVmInputAction;
    }

    public Action createRunVm2Action(Sprite sprite, SequenceAction sequence, Formula arguments) {
        RunVm2Action runVm2Action = (RunVm2Action) action(RunVm2Action.class);
        runVm2Action.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        runVm2Action.setArguments(arguments);
        return runVm2Action;
    }

    public Action createAskGeminiAction(Sprite sprite, SequenceAction sequence, Formula question, UserVariable userVariable) {
        AskGeminiAction askGeminiAction = (AskGeminiAction) Actions.action(AskGeminiAction.class);
        askGeminiAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        askGeminiAction.setAsk(question);
        askGeminiAction.setVariable(userVariable);
        return askGeminiAction;
    }

    public Action createAskGemini2Action(Sprite sprite, SequenceAction sequence, Formula question, Formula model, UserVariable userVariable) {
        AskGemini2Action askGemini2Action = (AskGemini2Action) Actions.action(AskGemini2Action.class);
        askGemini2Action.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        askGemini2Action.setAsk(question);
        askGemini2Action.setModel(model);
        askGemini2Action.setVariable(userVariable);
        return askGemini2Action;
    }

    public Action createCustomAction(Sprite sprite, SequenceAction sequence, CustomBrickDefinition definition, List<Formula> parameterFormulas) {
        CustomAction customAction = (CustomAction) Actions.action(CustomAction.class);
        customAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        customAction.setDefinition(definition);
        customAction.setParameterFormulas(parameterFormulas);
        return customAction;
    }

    public Action createAskAction(Sprite sprite, SequenceAction sequence, Formula questionFormula, UserVariable answerVariable) {
        AskAction askAction = (AskAction) Actions.action(AskAction.class);
        askAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        askAction.setQuestionFormula(questionFormula);
        askAction.setAnswerVariable(answerVariable);
        return askAction;
    }

    public Action createBigAskAction(Sprite sprite, SequenceAction sequence, Formula questionFormula, Formula msg, Formula ok, Formula canel, Formula def, UserVariable answerVariable) {
        BigAskAction bigAskAction = (BigAskAction) Actions.action(BigAskAction.class);
        bigAskAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        bigAskAction.setQuestionFormula(questionFormula);
        bigAskAction.setMsg(msg);
        bigAskAction.setSumb(ok);
        bigAskAction.setCanel(canel);
        bigAskAction.setStandardText(def);
        bigAskAction.setAnswerVariable(answerVariable);
        return bigAskAction;
    }

    public Action createAskSpeechAction(Sprite sprite, SequenceAction sequence, Formula questionFormula, UserVariable answerVariable) {
        AskSpeechAction askSpeechAction = (AskSpeechAction) Actions.action(AskSpeechAction.class);
        askSpeechAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        askSpeechAction.setQuestionFormula(questionFormula);
        askSpeechAction.setAnswerVariable(answerVariable);
        return askSpeechAction;
    }

    public Action createDeleteItemOfUserListAction(Sprite sprite, SequenceAction sequence, Formula userListFormula, UserList userList) {
        DeleteItemOfUserListAction deleteItemOfUserListAction = (DeleteItemOfUserListAction) action(DeleteItemOfUserListAction.class);
        deleteItemOfUserListAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        deleteItemOfUserListAction.setFormulaIndexToDelete(userListFormula);
        deleteItemOfUserListAction.setUserList(userList);
        return deleteItemOfUserListAction;
    }

    public Action createClearUserListAction(UserList userList) {
        ClearUserListAction clearUserListAction = (ClearUserListAction) action(ClearUserListAction.class);
        clearUserListAction.setUserList(userList);
        return clearUserListAction;
    }

    public Action createAddItemToUserListAction(Sprite sprite, SequenceAction sequence, Formula userListFormula, UserList userList) {
        AddItemToUserListAction addItemToUserListAction = (AddItemToUserListAction) action(AddItemToUserListAction.class);
        addItemToUserListAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        addItemToUserListAction.setFormulaItemToAdd(userListFormula);
        addItemToUserListAction.setUserList(userList);
        return addItemToUserListAction;
    }

    public Action createRegexAction(Sprite sprite, SequenceAction sequence, Formula text, Formula regex, UserList userList) {
        RegexAction regexAction = (RegexAction) action(RegexAction.class);
        regexAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        regexAction.setText_f(text);
        regexAction.setRegex_f(regex);
        regexAction.setUserlist(userList);
        return regexAction;
    }

    public Action createShowToastAction(Sprite sprite, SequenceAction sequence, Formula toastFormula) {
        ShowToastAction showToastAction = (ShowToastAction) action(ShowToastAction.class);
        showToastAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        showToastAction.setToast(toastFormula);
        return showToastAction;
    }

    public Action createPhoto(Sprite sprite, SequenceAction sequence) {
        PhotoAction photoAction = (PhotoAction) action(PhotoAction.class);
        photoAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return photoAction;
    }

    public Action createShader(Sprite sprite, SequenceAction sequence, Formula code, Formula code2) {
        ShaderAction shaderAction = (ShaderAction) action(ShaderAction.class);
        shaderAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        shaderAction.setFormula(code);
        shaderAction.setVertex(code2);
        return shaderAction;
    }

    public Action createCutLook(Sprite sprite, SequenceAction sequence, Formula x1, Formula y1, Formula x2, Formula y2) {
        CutLookAction cutLookAction = (CutLookAction) action(CutLookAction.class);
        cutLookAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        cutLookAction.setX1(x1);
        cutLookAction.setY1(y1);
        cutLookAction.setX2(x2);
        cutLookAction.setY2(y2);
        return cutLookAction;
    }

    public Action createLoadNNAction(Sprite sprite, SequenceAction sequence, Formula file) {
        LoadNNAction loadNNAction = (LoadNNAction) action(LoadNNAction.class);
        loadNNAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        loadNNAction.setFile(file);
        return loadNNAction;
    }

    public Action createUnoadNNAction(Sprite sprite, SequenceAction sequence) {
        UnloadNNAction unloadNNAction = (UnloadNNAction) action(UnloadNNAction.class);
        unloadNNAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return unloadNNAction;
    }

    public Action createPredictNNAction(Sprite sprite, SequenceAction sequence, Formula input, UserVariable variable) {
        PredictNNAction predictNNAction = (PredictNNAction) action(PredictNNAction.class);
        predictNNAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        predictNNAction.setInput(input);
        predictNNAction.setVariable(variable);
        return predictNNAction;
    }

    public Action createLunoScriptAction(Sprite sprite, SequenceAction sequence, Formula code) {
        LunoscriptAction lunoscriptAction = (LunoscriptAction) action(LunoscriptAction.class);
        lunoscriptAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        lunoscriptAction.setCode(code);
        return lunoscriptAction;
    }

    public Action createHideStatusBarAction(Sprite sprite, SequenceAction sequence) {
        hideStatusBarAction hidestatusbaraction = (hideStatusBarAction) action(hideStatusBarAction.class);
        hidestatusbaraction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return hidestatusbaraction;
    }

    public Action createSetStopSoundsAction(Sprite sprite, SequenceAction sequence, Formula formula) {
        SetStopSoundsAction setStopSoundsAction = (SetStopSoundsAction) action(SetStopSoundsAction.class);
        setStopSoundsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setStopSoundsAction.setFormula(formula);
        return setStopSoundsAction;
    }

    public Action createSetSaveScenesAction(Sprite sprite, SequenceAction sequence, Formula formula) {
        SetSaveScenesAction setSaveScenesAction = (SetSaveScenesAction) action(SetSaveScenesAction.class);
        setSaveScenesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setSaveScenesAction.setFormula(formula);
        return setSaveScenesAction;
    }

    public Action createClearSceneAction(String sceneName, Sprite sprite) {
        ClearSceneAction clearSceneAction = (ClearSceneAction) action(ClearSceneAction.class);
        clearSceneAction.setScene(sceneName);
        clearSceneAction.setSprite(sprite);
        return clearSceneAction;
    }

    public Action createTestAction(Sprite sprite, SequenceAction sequence) {
        TestAction testAction = (TestAction) action(TestAction.class);
        testAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return testAction;
    }

    public Action createStringToTableAction(Sprite sprite, SequenceAction sequence, Formula str, Formula x, Formula y, Formula name2) {
        StringToTableAction stringToTableAction = (StringToTableAction) action(StringToTableAction.class);
        stringToTableAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        stringToTableAction.setStr(str);
        stringToTableAction.setXd(x);
        stringToTableAction.setYd(y);
        stringToTableAction.setName(name2);
        return stringToTableAction;
    }

    public Action createSetDnsAction(Sprite sprite, SequenceAction sequence, Formula toastFormula) {
        SetDnsAction setDnsAction = (SetDnsAction) action(SetDnsAction.class);
        setDnsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setDnsAction.setDns(toastFormula);
        return setDnsAction;
    }

    public Action createMoveFilesAction(Sprite sprite, SequenceAction sequence, Formula toastFormula) {
        MoveFilesAction moveFilesAction = (MoveFilesAction) action(MoveFilesAction.class);
        moveFilesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        moveFilesAction.setFileName(toastFormula);
        return moveFilesAction;
    }

    public Action createMoveDownloadsAction(Sprite sprite, SequenceAction sequence, Formula toastFormula) {
        MoveDownloadsAction moveDownloadsAction = (MoveDownloadsAction) action(MoveDownloadsAction.class);
        moveDownloadsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        moveDownloadsAction.setFileName(toastFormula);
        return moveDownloadsAction;
    }

    public Action createOrientationAction(Sprite sprite, SequenceAction sequence) {
        OrientationAction orientationAction = (OrientationAction) action(OrientationAction.class);
        orientationAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return orientationAction;
    }

    public Action createDialogAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula title, Formula message) {
        CreateDIalogAction createDIalogAction = (CreateDIalogAction) action(CreateDIalogAction.class);
        createDIalogAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createDIalogAction.setName(name2);
        createDIalogAction.setTitle(title);
        createDIalogAction.setMessage(message);
        return createDIalogAction;
    }

    public Action createPositiveAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula text) {
        SetPositiveAction setPositiveAction = (SetPositiveAction) action(SetPositiveAction.class);
        setPositiveAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setPositiveAction.setName(name2);
        setPositiveAction.setText(text);
        return setPositiveAction;
    }

    public Action createNegativeAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula text) {
        SetNegativeAction setNegativeAction = (SetNegativeAction) action(SetNegativeAction.class);
        setNegativeAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setNegativeAction.setName(name2);
        setNegativeAction.setText(text);
        return setNegativeAction;
    }

    public Action createNeutralAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula text) {
        SetNeutralButton setNeutralButton = (SetNeutralButton) action(SetNeutralButton.class);
        setNeutralButton.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setNeutralButton.setName(name2);
        setNeutralButton.setText(text);
        return setNeutralButton;
    }

    public Action createRunPythonScriptAction(Sprite sprite, SequenceAction sequence, Formula script, Formula variableName) {
        RunPythonScriptAction runPythonScriptAction = (RunPythonScriptAction) action(RunPythonScriptAction.class);
        runPythonScriptAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        runPythonScriptAction.setScript(script);
        runPythonScriptAction.setVariableName(variableName);
        return runPythonScriptAction;
    }

    public Action createLoadPythonLibraryAction(Sprite sprite, SequenceAction sequence, Formula fileName) {
        LoadPythonLibraryAction loadPythonLibraryAction = (LoadPythonLibraryAction) action(LoadPythonLibraryAction.class);
        loadPythonLibraryAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        loadPythonLibraryAction.setFileName(fileName);
        return loadPythonLibraryAction;
    }

    public Action createLoadNativeModuleAction(Sprite sprite, SequenceAction sequence, Formula fileName) {
        LoadNativeModuleAction loadNativeModuleAction = (LoadNativeModuleAction) action(LoadNativeModuleAction.class);
        loadNativeModuleAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        loadNativeModuleAction.setFileName(fileName);
        return loadNativeModuleAction;
    }

    public Action createClearPythonEnvironmentAction(Sprite sprite, SequenceAction sequence) {
        ClearPythonEnvironmentAction clearPythonEnvironmentAction = (ClearPythonEnvironmentAction) action(ClearPythonEnvironmentAction.class);
        clearPythonEnvironmentAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return clearPythonEnvironmentAction;
    }

    public Action createEditAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula text) {
        AddEditAction addEditAction = (AddEditAction) action(AddEditAction.class);
        addEditAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        addEditAction.setName(name2);
        addEditAction.setText(text);
        return addEditAction;
    }

    public Action createRadioAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula text) {
        AddRadioAction addRadioAction = (AddRadioAction) action(AddRadioAction.class);
        addRadioAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        addRadioAction.setName(name2);
        addRadioAction.setText(text);
        return addRadioAction;
    }

    public Action createShowDialogAction(Sprite sprite, SequenceAction sequence, Formula name2) {
        ShowDialogAction showDialogAction = (ShowDialogAction) action(ShowDialogAction.class);
        showDialogAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        showDialogAction.setName(name2);
        return showDialogAction;
    }

    public Action createSetCallbackAction(Sprite sprite, SequenceAction sequence, Formula name2, UserVariable button) {
        SetCallbackAction setCallbackAction = (SetCallbackAction) action(SetCallbackAction.class);
        setCallbackAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setCallbackAction.setName(name2);
        setCallbackAction.setVariable1(button);
        return setCallbackAction;
    }

    public Action createLookToTableAction(Sprite sprite, SequenceAction sequence, Formula r, Formula g, Formula b, Formula a) {
        LookToTableAction lookToTableAction = (LookToTableAction) action(LookToTableAction.class);
        lookToTableAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        lookToTableAction.setR_table(r);
        lookToTableAction.setG_table(g);
        lookToTableAction.setB_table(b);
        lookToTableAction.setA_table(a);
        return lookToTableAction;
    }

    public Action createLookFromTableAction(Sprite sprite, SequenceAction sequence, Formula r, Formula g, Formula b, Formula a) {
        LookFromTableAction lookFromTableAction = (LookFromTableAction) action(LookFromTableAction.class);
        lookFromTableAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        lookFromTableAction.setR_table(r);
        lookFromTableAction.setG_table(g);
        lookFromTableAction.setB_table(b);
        lookFromTableAction.setA_table(a);
        return lookFromTableAction;
    }

    public Action createOpenFileAction(Sprite sprite, SequenceAction sequence, Formula file) {
        OpenFileAction openFileAction = (OpenFileAction) action(OpenFileAction.class);
        openFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        openFileAction.setFile(file);
        return openFileAction;
    }

    public Action createOpenFilesAction(Sprite sprite, SequenceAction sequence, Formula file) {
        OpenFilesAction openFilesAction = (OpenFilesAction) action(OpenFilesAction.class);
        openFilesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        openFilesAction.setFile(file);
        return openFilesAction;
    }

    public Action createReadBaseAction(Sprite sprite, SequenceAction sequence, Formula base, Formula key, UserVariable variable) {
        ReadBaseAction readBaseAction = (ReadBaseAction) action(ReadBaseAction.class);
        readBaseAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        readBaseAction.setBase(base);
        readBaseAction.setKey(key);
        readBaseAction.setVariable(variable);
        return readBaseAction;
    }

    public Action createWriteBaseAction(Sprite sprite, SequenceAction sequence, Formula base, Formula key, Formula value) {
        WriteBaseAction writeBaseAction = (WriteBaseAction) action(WriteBaseAction.class);
        writeBaseAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        writeBaseAction.setBase(base);
        writeBaseAction.setKey(key);
        writeBaseAction.setValue(value);
        return writeBaseAction;
    }

    public Action createDeleteBaseAction(Sprite sprite, SequenceAction sequence, Formula base, Formula key) {
        DeleteBaseAction deleteBaseAction = (DeleteBaseAction) action(DeleteBaseAction.class);
        deleteBaseAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        deleteBaseAction.setBase(base);
        deleteBaseAction.setKey(key);
        return deleteBaseAction;
    }

    public Action createUnzipAction(Sprite sprite, SequenceAction sequence, Formula name2) {
        UnzipAction unzipAction = (UnzipAction) action(UnzipAction.class);
        unzipAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        unzipAction.setName(name2);
        return unzipAction;
    }

    public Action createZipAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula files) {
        ZipAction zipAction = (ZipAction) action(ZipAction.class);
        zipAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        zipAction.setName(name2);
        zipAction.setFiles(files);
        return zipAction;
    }

    public Action createHideText3Action(Sprite sprite, SequenceAction sequence, Formula name2, ShowTextUtils.AndroidStringProvider androidStringProvider) {
        HideText3Action hideText3Action = (HideText3Action) action(HideText3Action.class);
        hideText3Action.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        hideText3Action.setName(name2);
        hideText3Action.setSprite(sprite);
        hideText3Action.setAndroidStringProvider(androidStringProvider);
        return hideText3Action;
    }

    public Action createSplitAction(Sprite sprite, SequenceAction sequence, Formula string, Formula simbol, UserList userlist) {
        SplitAction splitAction = (SplitAction) action(SplitAction.class);
        splitAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        splitAction.setString(string);
        splitAction.setSimbol(simbol);
        splitAction.setUserList(userlist);
        return splitAction;
    }

    public Action createCreateTableAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula x, Formula y) {
        CreateTableAction createTableAction = (CreateTableAction) action(CreateTableAction.class);
        createTableAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createTableAction.setName(name2);
        createTableAction.setX(x);
        createTableAction.setY(y);
        return createTableAction;
    }

    public Action createFloat(Sprite sprite, SequenceAction sequence, Formula name2) {
        CreateFloatAction createFloatAction = (CreateFloatAction) action(CreateFloatAction.class);
        createFloatAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createFloatAction.setName(name2);
        return createFloatAction;
    }

    public Action createTableToFloat(Sprite sprite, SequenceAction sequence, Formula tname, Formula fname) {
        TableToFloatAction tableToFloatAction = (TableToFloatAction) action(TableToFloatAction.class);
        tableToFloatAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        tableToFloatAction.setT_name(tname);
        tableToFloatAction.setF_name(fname);
        return tableToFloatAction;
    }

    public Action createPutFloat(Sprite sprite, SequenceAction sequence, Formula name2, Formula value, Formula index) {
        PutFloatAction putFloatAction = (PutFloatAction) action(PutFloatAction.class);
        putFloatAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        putFloatAction.setF_name(name2);
        putFloatAction.setValue(value);
        putFloatAction.setIndex(index);
        return putFloatAction;
    }

    public Action createDeleteFloat(Sprite sprite, SequenceAction sequence, Formula name2, Formula index) {
        DeleteFloatAction deleteFloatAction = (DeleteFloatAction) action(DeleteFloatAction.class);
        deleteFloatAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        deleteFloatAction.setIndex(index);
        deleteFloatAction.setF_name(name2);
        return deleteFloatAction;
    }

    public Action createInsertTableAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula value, Formula x, Formula y) {
        InsertTableAction insertTableAction = (InsertTableAction) action(InsertTableAction.class);
        insertTableAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        insertTableAction.setName(name2);
        insertTableAction.setValue(value);
        insertTableAction.setX(x);
        insertTableAction.setY(y);
        return insertTableAction;
    }

    public Action createDeleteTableAction(Sprite sprite, SequenceAction sequence, Formula name2) {
        DeleteTableAction deleteTableAction = (DeleteTableAction) action(DeleteTableAction.class);
        deleteTableAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        deleteTableAction.setName(name2);
        return deleteTableAction;
    }

    public Action createDeleteAllTablesAction(Sprite sprite, SequenceAction sequence) {
        DeleteAllTablesAction deleteAllTablesAction = (DeleteAllTablesAction) action(DeleteAllTablesAction.class);
        deleteAllTablesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return deleteAllTablesAction;
    }

    public Action createSoundFileAction(Sprite sprite, SequenceAction sequence, Formula fileFormula) {
        SoundFileAction soundFileAction = (SoundFileAction) action(SoundFileAction.class);
        soundFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        soundFileAction.setName(fileFormula);
        return soundFileAction;
    }

    public Action createSoundFilesAction(Sprite sprite, SequenceAction sequence, Formula fileFormula) {
        SoundFilesAction soundFilesAction = (SoundFilesAction) action(SoundFilesAction.class);
        soundFilesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        soundFilesAction.setName(fileFormula);
        return soundFilesAction;
    }

    public Action createLookFileAction(Sprite sprite, SequenceAction sequence, Formula fileFormula) {
        LookFileAction lookFileAction = (LookFileAction) action(LookFileAction.class);
        lookFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        lookFileAction.setName(fileFormula);
        return lookFileAction;
    }

    public Action createSaveLookFilesAction(Sprite sprite, SequenceAction sequence, Formula fileFormula) {
        SaveLookFilesAction saveLookFilesAction = (SaveLookFilesAction) action(SaveLookFilesAction.class);
        saveLookFilesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        saveLookFilesAction.setName(fileFormula);
        return saveLookFilesAction;
    }

    public Action createSetLookFilesAction(Sprite sprite, SequenceAction sequence, Formula fileFormula) {
        SetLookFilesAction setLookFilesAction = (SetLookFilesAction) action(SetLookFilesAction.class);
        setLookFilesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setLookFilesAction.setName(fileFormula);
        return setLookFilesAction;
    }

    public Action createSaveLookAction(Sprite sprite, SequenceAction sequence, Formula nameFormula) {
        SaveLookAction saveLookAction = (SaveLookAction) action(SaveLookAction.class);
        saveLookAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        saveLookAction.setName(nameFormula);
        return saveLookAction;
    }

    public Action createFileUrlAction(Sprite sprite, SequenceAction sequence, Formula toastFormula, Formula nameFormula) {
        FileUrlAction fileUrlAction = (FileUrlAction) action(FileUrlAction.class);
        fileUrlAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        fileUrlAction.setUrl(toastFormula);
        fileUrlAction.setName(nameFormula);
        return fileUrlAction;
    }

    public Action createFilesUrlAction(Sprite sprite, SequenceAction sequence, Formula toastFormula, Formula nameFormula) {
        FilesUrlAction filesUrlAction = (FilesUrlAction) action(FilesUrlAction.class);
        filesUrlAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        filesUrlAction.setUrl(toastFormula);
        filesUrlAction.setName(nameFormula);
        return filesUrlAction;
    }

    public Action evalWebAction(Sprite sprite, SequenceAction sequence, Formula js, Formula name2) {
        EvalWebAction evalWebAction = (EvalWebAction) action(EvalWebAction.class);
        evalWebAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        evalWebAction.setCode(js);
        evalWebAction.setName(name2);
        return evalWebAction;
    }

    public Action setWebAction(Sprite sprite, SequenceAction sequence, Formula name2, UserVariable variable) {
        SetWebAction setWebAction = (SetWebAction) action(SetWebAction.class);
        setWebAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setWebAction.setUserVariable(variable);
        setWebAction.setName(name2);
        return setWebAction;
    }

    public Action createStartServerAction(Sprite sprite, SequenceAction sequence, Formula port) {
        StartServerAction startServerAction = (StartServerAction) action(StartServerAction.class);
        startServerAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        startServerAction.setPort(port);
        return startServerAction;
    }

    public Action createSendServerAction(Sprite sprite, SequenceAction sequence, Formula value) {
        SendServerAction sendServerAction = (SendServerAction) action(SendServerAction.class);
        sendServerAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        sendServerAction.setValue(value);
        return sendServerAction;
    }

    public Action createStopServerAction(Sprite sprite, SequenceAction sequence) {
        StopServerAction stopServerAction = (StopServerAction) action(StopServerAction.class);
        stopServerAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return stopServerAction;
    }

    public Action createListenServerAction(Sprite sprite, SequenceAction sequence, UserVariable variable) {
        ListenServerAction listenServerAction = (ListenServerAction) action(ListenServerAction.class);
        listenServerAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        listenServerAction.setVariable(variable);
        return listenServerAction;
    }

    public Action createConnectServerAction(Sprite sprite, SequenceAction sequence, Formula ip, Formula port) {
        ConnectServerAction connectServerAction = (ConnectServerAction) action(ConnectServerAction.class);
        connectServerAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        connectServerAction.setIp(ip);
        connectServerAction.setPort(port);
        return connectServerAction;
    }

    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".length())));
        }
        return sb.toString();
    }

    public Action createScreenShotAction(Sprite sprite, SequenceAction sequence) {
        ScreenShotAction screenShotAction = (ScreenShotAction) action(ScreenShotAction.class);
        screenShotAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        screenShotAction.setName(generateRandomString(10));
        return screenShotAction;
    }

    public Action createWebUrlAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula url, Formula x, Formula y, Formula width, Formula height) {
        CreateWebUrlAction createWebUrlAction = (CreateWebUrlAction) action(CreateWebUrlAction.class);
        createWebUrlAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createWebUrlAction.setName(name2);
        createWebUrlAction.setUrl(url);
        createWebUrlAction.setPosX(x);
        createWebUrlAction.setPosY(y);
        createWebUrlAction.setWidth(width);
        createWebUrlAction.setHeight(height);
        return createWebUrlAction;
    }

    public Action createWebFileAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula url, Formula x, Formula y, Formula width, Formula height) {
        CreateWebFileAction createWebFileAction = (CreateWebFileAction) action(CreateWebFileAction.class);
        createWebFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createWebFileAction.setName(name2);
        createWebFileAction.setFile(url);
        createWebFileAction.setPosX(x);
        createWebFileAction.setPosY(y);
        createWebFileAction.setWidth(width);
        createWebFileAction.setHeight(height);
        return createWebFileAction;
    }

    public Action videoAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula url, Formula x, Formula y, Formula width, Formula height, Formula loop, Formula control) {
        CreateVideoAction createVideoAction = (CreateVideoAction) action(CreateVideoAction.class);
        createVideoAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createVideoAction.setName(name2);
        createVideoAction.setFile(url);
        createVideoAction.setPosX(x);
        createVideoAction.setPosY(y);
        createVideoAction.setWidth(width);
        createVideoAction.setHeight(height);
        createVideoAction.setLoop(loop);
        createVideoAction.setControls(control);
        return createVideoAction;
    }

    public Action createDeleteWebAction(Sprite sprite, SequenceAction sequence, Formula name2) {
        DeleteWebAction deleteWebAction = (DeleteWebAction) action(DeleteWebAction.class);
        deleteWebAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        deleteWebAction.setName(name2);
        return deleteWebAction;
    }

    public Action createSetGeminiKey(Sprite sprite, SequenceAction sequence, Formula keyFormula) {
        SetGeminiKeyAction setGeminiKeyAction = (SetGeminiKeyAction) action(SetGeminiKeyAction.class);
        setGeminiKeyAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setGeminiKeyAction.setKey(keyFormula);
        return setGeminiKeyAction;
    }

    public Action createVarAction(Sprite sprite, SequenceAction sequence, Formula varname, Formula value) {
        CreateVarAction createVarAction = (CreateVarAction) action(CreateVarAction.class);
        createVarAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createVarAction.setVarname(varname);
        createVarAction.setValue(value);
        return createVarAction;
    }

    public Action createDeleteVarAction(Sprite sprite, SequenceAction sequence, Formula varname) {
        DeleteVarAction deleteVarAction = (DeleteVarAction) action(DeleteVarAction.class);
        deleteVarAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        deleteVarAction.setVarname(varname);
        return deleteVarAction;
    }

    public Action createDeleteVarsAction(Sprite sprite, SequenceAction sequence) {
        DeleteVarAction deleteVarAction = (DeleteVarAction) action(DeleteVarAction.class);
        deleteVarAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return deleteVarAction;
    }

    public Action createListenMicroAction(Sprite sprite, SequenceAction sequence, Formula time) {
        ListenMicroAction listenMicroAction = (ListenMicroAction) action(ListenMicroAction.class);
        listenMicroAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        listenMicroAction.setTime(time);
        return listenMicroAction;
    }

    public Action createCopyTextAction(Sprite sprite, SequenceAction sequence, Formula toastFormula) {
        CopyTextAction copyTextAction = (CopyTextAction) action(CopyTextAction.class);
        copyTextAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        copyTextAction.setText(toastFormula);
        return copyTextAction;
    }

    public Action createInsertItemIntoUserListAction(Sprite sprite, SequenceAction sequence, Formula userListFormulaIndexToInsert, Formula userListFormulaItemToInsert, UserList userList) {
        InsertItemIntoUserListAction insertItemIntoUserListAction = (InsertItemIntoUserListAction) action(InsertItemIntoUserListAction.class);
        insertItemIntoUserListAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        insertItemIntoUserListAction.setFormulaIndexToInsert(userListFormulaIndexToInsert);
        insertItemIntoUserListAction.setFormulaItemToInsert(userListFormulaItemToInsert);
        insertItemIntoUserListAction.setUserList(userList);
        return insertItemIntoUserListAction;
    }

    public Action createStoreCSVIntoUserListAction(Sprite sprite, SequenceAction sequence, Formula userListFormulaColumn, Formula userListFormulaCSV, UserList userList) {
        StoreCSVIntoUserListAction storeCSVIntoUserListAction = (StoreCSVIntoUserListAction) action(StoreCSVIntoUserListAction.class);
        storeCSVIntoUserListAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        storeCSVIntoUserListAction.setFormulaColumnToExtract(userListFormulaColumn);
        storeCSVIntoUserListAction.setFormulaCSVData(userListFormulaCSV);
        storeCSVIntoUserListAction.setUserList(userList);
        return storeCSVIntoUserListAction;
    }

    public Action createReplaceItemInUserListAction(Sprite sprite, SequenceAction sequence, Formula userListFormulaIndexToReplace, Formula userListFormulaItemToInsert, UserList userList) {
        ReplaceItemInUserListAction replaceItemInUserListAction = (ReplaceItemInUserListAction) action(ReplaceItemInUserListAction.class);
        replaceItemInUserListAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        replaceItemInUserListAction.setFormulaIndexToReplace(userListFormulaIndexToReplace);
        replaceItemInUserListAction.setFormulaItemToInsert(userListFormulaItemToInsert);
        replaceItemInUserListAction.setUserList(userList);
        return replaceItemInUserListAction;
    }

    public Action createResetTimerAction() {
        return Actions.action(ResetTimerAction.class);
    }

    public Action createThinkSayBubbleAction(Sprite sprite, SequenceAction sequence, ShowTextUtils.AndroidStringProvider androidStringProvider, Formula text, int type) {
        ThinkSayBubbleAction thinkSayBubbleAction = (ThinkSayBubbleAction) action(ThinkSayBubbleAction.class);
        thinkSayBubbleAction.setText(text);
        thinkSayBubbleAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        thinkSayBubbleAction.setAndroidStringProvider(androidStringProvider);
        thinkSayBubbleAction.setType(type);
        return thinkSayBubbleAction;
    }

    public Action createThinkSayForBubbleAction(Sprite sprite, SequenceAction sequence, ShowTextUtils.AndroidStringProvider androidStringProvider, Formula text, int type) {
        ThinkSayBubbleAction thinkSayBubbleAction = (ThinkSayBubbleAction) action(ThinkSayBubbleAction.class);
        thinkSayBubbleAction.setText(text);
        thinkSayBubbleAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        thinkSayBubbleAction.setAndroidStringProvider(androidStringProvider);
        thinkSayBubbleAction.setType(type);
        return thinkSayBubbleAction;
    }

    public Action createSceneTransitionAction(String sceneName, Sprite sprite) {
        SceneTransitionAction sceneTransitionAction = (SceneTransitionAction) action(SceneTransitionAction.class);
        sceneTransitionAction.setScene(sceneName);
        sceneTransitionAction.setSprite(sprite);
        return sceneTransitionAction;
    }

    public Action createSceneStartAction(String sceneName, Sprite sprite) {
        SceneStartAction sceneStartAction = (SceneStartAction) action(SceneStartAction.class);
        sceneStartAction.setScene(sceneName);
        sceneStartAction.setSprite(sprite);
        return sceneStartAction;
    }

    public Action createSceneIdAction(Integer sceneName, Sprite sprite) {
        SceneIdAction sceneIdAction = (SceneIdAction) action(SceneIdAction.class);
        sceneIdAction.setScene(sceneName);
        sceneIdAction.setSprite(sprite);
        return sceneIdAction;
    }

    public Action createIfLogicAction(Sprite sprite, SequenceAction sequence, Formula condition, Action ifAction, Action elseAction) {
        IfLogicAction ifLogicAction = (IfLogicAction) Actions.action(IfLogicAction.class);
        ifLogicAction.setIfAction(ifAction);
        ifLogicAction.setIfCondition(condition);
        ifLogicAction.setElseAction(elseAction);
        ifLogicAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return ifLogicAction;
    }

    public Action createRepeatAction(Sprite sprite, SequenceAction sequence, Formula count, Action repeatedAction, boolean isLoopDelay) {
        RepeatAction repeatAction = (RepeatAction) Actions.action(RepeatAction.class);
        repeatAction.setRepeatCount(count);
        repeatAction.setAction(repeatedAction);
        repeatAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        repeatAction.setLoopDelay(isLoopDelay);
        return repeatAction;
    }

    public Action createAsyncRepeatAction(Sprite sprite, SequenceAction sequence, Formula count, Action repeatedAction, boolean isLoopDelay) {
        AsyncRepeatAction asyncRepeatAction = (AsyncRepeatAction) Actions.action(AsyncRepeatAction.class);
        asyncRepeatAction.setRepeatCount(count);
        asyncRepeatAction.setAction(repeatedAction);
        asyncRepeatAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        asyncRepeatAction.setLoopDelay(isLoopDelay);
        return asyncRepeatAction;
    }

    public Action createIntervalRepeatAction(Sprite sprite, SequenceAction sequence, Formula count, Formula interval, Action loopBodyAction) {
        IntervalRepeatAction intervalRepeatAction = (IntervalRepeatAction) action(IntervalRepeatAction.class);
        intervalRepeatAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        intervalRepeatAction.setRepeatCount(count);
        intervalRepeatAction.setInterval(interval);
        intervalRepeatAction.setLoopBodyAction(loopBodyAction);
        return intervalRepeatAction;
    }

    public Action createPrepareSoundAction(Sprite sprite, SequenceAction sequence, Formula fileName, Formula cacheName) {
        PrepareSoundAction prepareSoundAction = (PrepareSoundAction) action(PrepareSoundAction.class);
        prepareSoundAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        prepareSoundAction.setFileNameFormula(fileName);
        prepareSoundAction.setCacheNameFormula(cacheName);
        return prepareSoundAction;
    }

    public Action createSetViewPositionAction(Sprite sprite, SequenceAction sequence, Formula viewId, Formula x, Formula y) {
        SetViewPositionAction setViewPositionAction = (SetViewPositionAction) action(SetViewPositionAction.class);
        setViewPositionAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setViewPositionAction.setViewIdFormula(viewId);
        setViewPositionAction.setXFormula(x);
        setViewPositionAction.setYFormula(y);
        return setViewPositionAction;
    }

    public Action createPlayPreparedSoundAction(Sprite sprite, SequenceAction sequence, Formula cacheName) {
        PlayPreparedSoundAction playPreparedSoundAction = (PlayPreparedSoundAction) action(PlayPreparedSoundAction.class);
        playPreparedSoundAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        playPreparedSoundAction.setCacheNameFormula(cacheName);
        return playPreparedSoundAction;
    }

    public Action createForVariableFromToAction(Sprite sprite, SequenceAction sequence, UserVariable controlVariable, Formula from, Formula to, Action repeatedAction, boolean isLoopDelay) {
        ForVariableFromToAction forVariableFromToAction = (ForVariableFromToAction) Actions.action(ForVariableFromToAction.class);
        forVariableFromToAction.setRange(from, to);
        forVariableFromToAction.setControlVariable(controlVariable);
        forVariableFromToAction.setAction(repeatedAction);
        forVariableFromToAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        forVariableFromToAction.setLoopDelay(isLoopDelay);
        return forVariableFromToAction;
    }

    public Action createForItemInUserListAction(UserList userList, UserVariable userVariable, Action repeatedAction, boolean isLoopDelay) {
        ForItemInUserListAction forItemInUserListAction = (ForItemInUserListAction) Actions.action(ForItemInUserListAction.class);
        forItemInUserListAction.setAction(repeatedAction);
        forItemInUserListAction.setUserList(userList);
        forItemInUserListAction.setCurrentItemVariable(userVariable);
        forItemInUserListAction.setLoopDelay(isLoopDelay);
        return forItemInUserListAction;
    }

    public Action createWaitUntilAction(Sprite sprite, SequenceAction sequence, Formula condition) {
        WaitUntilAction waitUntilAction = (WaitUntilAction) Actions.action(WaitUntilAction.class);
        waitUntilAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        waitUntilAction.setCondition(condition);
        return waitUntilAction;
    }

    public Action createRepeatUntilAction(Sprite sprite, SequenceAction sequence, Formula condition, Action repeatedAction, boolean isLoopDelay) {
        RepeatUntilAction repeatUntilAction = (RepeatUntilAction) action(RepeatUntilAction.class);
        repeatUntilAction.setRepeatCondition(condition);
        repeatUntilAction.setAction(repeatedAction);
        repeatUntilAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        repeatUntilAction.setLoopDelay(isLoopDelay);
        return repeatUntilAction;
    }

    public Action createDelayAction(Sprite sprite, SequenceAction sequence, Formula delay) {
        WaitAction waitAction = (WaitAction) Actions.action(WaitAction.class);
        waitAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        waitAction.setDelay(delay);
        return waitAction;
    }

    public Action createForeverAction(Sprite sprite, SequenceAction sequence, ScriptSequenceAction foreverSequence, boolean isLoopDelay) {
        RepeatAction repeatAction = (RepeatAction) Actions.action(RepeatAction.class);
        repeatAction.setForeverRepeat(true);
        repeatAction.setAction(foreverSequence);
        repeatAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        repeatAction.setLoopDelay(isLoopDelay);
        return repeatAction;
    }

    public static Action createStitchAction(Sprite sprite) {
        StitchAction stitchAction = (StitchAction) Actions.action(StitchAction.class);
        stitchAction.setSprite(sprite);
        return stitchAction;
    }

    public Action createRunningStitchAction(Sprite sprite, SequenceAction sequence, Formula length) {
        RunningStitchAction runningStitchAction = (RunningStitchAction) Actions.action(RunningStitchAction.class);
        runningStitchAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        runningStitchAction.setLength(length);
        return runningStitchAction;
    }

    public Action createTripleStitchAction(Sprite sprite, SequenceAction sequence, Formula steps) {
        TripleStitchAction tripleStitchAction = (TripleStitchAction) Actions.action(TripleStitchAction.class);
        tripleStitchAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        tripleStitchAction.setSteps(steps);
        return tripleStitchAction;
    }

    public Action createZigZagStitchAction(Sprite sprite, SequenceAction sequence, Formula length, Formula width) {
        ZigZagStitchAction zigZagStitchAction = (ZigZagStitchAction) Actions.action(ZigZagStitchAction.class);
        zigZagStitchAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        zigZagStitchAction.setLength(length);
        zigZagStitchAction.setWidth(width);
        return zigZagStitchAction;
    }

    public static Action createStopRunningStitchAction(Sprite sprite) {
        StopRunningStitchAction stopRunningStitchAction = (StopRunningStitchAction) Actions.action(StopRunningStitchAction.class);
        stopRunningStitchAction.setSprite(sprite);
        return stopRunningStitchAction;
    }

    public Action createWriteEmbroideryToFileAction(Sprite sprite, SequenceAction sequence, Formula fileName) {
        WriteEmbroideryToFileAction writeEmbroideryToFileAction = (WriteEmbroideryToFileAction) Actions.action(WriteEmbroideryToFileAction.class);
        writeEmbroideryToFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        writeEmbroideryToFileAction.setFormula(fileName);
        return writeEmbroideryToFileAction;
    }

    public Action createSavePlotAction(Sprite sprite, SequenceAction sequence, Formula fileName) {
        SavePlotAction savePlotAction = (SavePlotAction) Actions.action(SavePlotAction.class);
        savePlotAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        savePlotAction.setFormula(fileName);
        return savePlotAction;
    }

    public Action createSewUpAction(Sprite sprite) {
        SewUpAction sewUpAction = (SewUpAction) Actions.action(SewUpAction.class);
        sewUpAction.setSprite(sprite);
        return sewUpAction;
    }

    public Action createSetThreadColorAction(Sprite sprite, SequenceAction sequence, Formula color) {
        SetThreadColorAction setThreadColorAction = (SetThreadColorAction) Actions.action(SetThreadColorAction.class);
        Scope scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        setThreadColorAction.setSprite(sprite);
        setThreadColorAction.setScope(scope);
        setThreadColorAction.setColor(color);
        return setThreadColorAction;
    }

    public static Action createScriptSequenceAction(Script script) {
        return new ScriptSequenceAction(script);
    }

    public Action createSetBounceFactorAction(Sprite sprite, SequenceAction sequence, Formula bounceFactor) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createTurnRightSpeedAction(Sprite sprite, SequenceAction sequence, Formula degreesPerSecond) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createTurnLeftSpeedAction(Sprite sprite, SequenceAction sequence, Formula degreesPerSecond) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createSetVelocityAction(Sprite sprite, SequenceAction sequence, Formula velocityX, Formula velocityY) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createSetPhysicsObjectTypeAction(Sprite sprite, PhysicsObject.Type type) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createSetMassAction(Sprite sprite, SequenceAction sequence, Formula mass) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createSetGravityAction(Sprite sprite, SequenceAction sequence, Formula gravityX, Formula gravityY) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createSetFrictionAction(Sprite sprite, SequenceAction sequence, Formula friction) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createSetTextAction(Sprite sprite, SequenceAction sequence, Formula x, Formula y, Formula text) {
        SetTextAction setTextAction = (SetTextAction) action(SetTextAction.class);
        setTextAction.setPosition(x, y);
        setTextAction.setText(text);
        setTextAction.setDuration(5.0f);
        setTextAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return setTextAction;
    }

    public Action createSet3dFrictionAction(Sprite sprite, SequenceAction sequence, Formula id2, Formula fric) {
        Set3dFrictionAction set3dFrictionAction = (Set3dFrictionAction) action(Set3dFrictionAction.class);
        set3dFrictionAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        set3dFrictionAction.friction = fric;
        set3dFrictionAction.objectId = id2;
        return set3dFrictionAction;
    }

    public Action createCreateSphereAction(Sprite sprite, ScriptSequenceAction sequence, Formula objectId) {
        CreateSphereAction createSphereAction = (CreateSphereAction) Actions.action(CreateSphereAction.class);
        createSphereAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        createSphereAction.objectId = objectId;
        return createSphereAction;
    }

    public Action createSetRestitutionAction(Sprite sprite, ScriptSequenceAction sequence, Formula objectId, Formula restitution) {
        SetRestitutionAction setRestitutionAction = (SetRestitutionAction) Actions.action(SetRestitutionAction.class);
        setRestitutionAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        setRestitutionAction.objectId = objectId;
        setRestitutionAction.restitution = restitution;
        return setRestitutionAction;
    }

    public Action createShowVariableAction(Sprite sprite, SequenceAction sequence, Formula xPosition, Formula yPosition, UserVariable userVariable, ShowTextUtils.AndroidStringProvider androidStringProvider) {
        ShowTextAction showTextAction = (ShowTextAction) action(ShowTextAction.class);
        showTextAction.setPosition(xPosition, yPosition);
        showTextAction.setVariableToShow(userVariable);
        showTextAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        showTextAction.setAndroidStringProvider(androidStringProvider);
        return showTextAction;
    }

    public Action createShowVariableColorAndSizeAction(Sprite sprite, SequenceAction sequence, Formula xPosition, Formula yPosition, Formula relativeTextSize, Formula color, UserVariable userVariable, int alignment, ShowTextUtils.AndroidStringProvider androidStringProvider) {
        ShowTextColorSizeAlignmentAction showTextColorSizeAlignmentAction = (ShowTextColorSizeAlignmentAction) action(ShowTextColorSizeAlignmentAction.class);
        showTextColorSizeAlignmentAction.setPosition(xPosition, yPosition);
        showTextColorSizeAlignmentAction.setRelativeTextSize(relativeTextSize);
        showTextColorSizeAlignmentAction.setColor(color);
        showTextColorSizeAlignmentAction.setVariableToShow(userVariable);
        showTextColorSizeAlignmentAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        showTextColorSizeAlignmentAction.setAlignment(alignment);
        showTextColorSizeAlignmentAction.setAndroidStringProvider(androidStringProvider);
        return showTextColorSizeAlignmentAction;
    }

    public Action createShowVarFontAction(Sprite sprite, SequenceAction sequence, Formula file, Formula xPosition, Formula yPosition, Formula relativeTextSize, Formula color, UserVariable userVariable, int alignment, ShowTextUtils.AndroidStringProvider androidStringProvider) {
        ShowVarFontAction showVarFontAction = (ShowVarFontAction) action(ShowVarFontAction.class);
        showVarFontAction.setPosition(xPosition, yPosition);
        showVarFontAction.setRelativeTextSize(relativeTextSize);
        showVarFontAction.setColor(color);
        showVarFontAction.setVariableToShow(userVariable);
        showVarFontAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        showVarFontAction.setFile(file);
        showVarFontAction.setAlignment(alignment);
        showVarFontAction.setAndroidStringProvider(androidStringProvider);
        return showVarFontAction;
    }

    public Action createShowtext3Action(Sprite sprite, SequenceAction sequence, Formula name2, Formula text, Formula xPosition, Formula yPosition, Formula relativeTextSize, Formula color, int alignment, ShowTextUtils.AndroidStringProvider androidStringProvider) {
        ShowText3Action showText3Action = (ShowText3Action) action(ShowText3Action.class);
        showText3Action.setPosition(xPosition, yPosition);
        showText3Action.setRelativeTextSize(relativeTextSize);
        showText3Action.setColor(color);
        showText3Action.setName(name2);
        showText3Action.setText(text);
        showText3Action.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        showText3Action.setAlignment(alignment);
        showText3Action.setAndroidStringProvider(androidStringProvider);
        return showText3Action;
    }

    public Action createShowTextFontAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula text, Formula font, Formula xPosition, Formula yPosition, Formula relativeTextSize, Formula color, int alignment, ShowTextUtils.AndroidStringProvider androidStringProvider) {
        ShowTextFontAction showTextFontAction = (ShowTextFontAction) action(ShowTextFontAction.class);
        showTextFontAction.setPosition(xPosition, yPosition);
        showTextFontAction.setFile(font);
        showTextFontAction.setRelativeTextSize(relativeTextSize);
        showTextFontAction.setColor(color);
        showTextFontAction.setName(name2);
        showTextFontAction.setText(text);
        showTextFontAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        showTextFontAction.setAlignment(alignment);
        showTextFontAction.setAndroidStringProvider(androidStringProvider);
        return showTextFontAction;
    }

    public Action createShowTextRotationAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula text, Formula rotation, Formula font, Formula xPosition, Formula yPosition, Formula relativeTextSize, Formula color, int alignment, ShowTextUtils.AndroidStringProvider androidStringProvider) {
        ShowTextRotationAction showTextRotationAction = (ShowTextRotationAction) action(ShowTextRotationAction.class);
        showTextRotationAction.setPosition(xPosition, yPosition);
        showTextRotationAction.setFile(font);
        showTextRotationAction.setRelativeTextSize(relativeTextSize);
        showTextRotationAction.setColor(color);
        showTextRotationAction.setName(name2);
        showTextRotationAction.setText(text);
        showTextRotationAction.setRotation(rotation);
        showTextRotationAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        showTextRotationAction.setAlignment(alignment);
        showTextRotationAction.setAndroidStringProvider(androidStringProvider);
        return showTextRotationAction;
    }

    public Action createShowTextVar(Sprite sprite, SequenceAction sequence, Formula xPosition, Formula yPosition, Formula relativeTextSize, Formula color, Formula name2, Formula text, int alignment, ShowTextUtils.AndroidStringProvider androidStringProvider) {
        ShowText2Action showText2Action = (ShowText2Action) action(ShowText2Action.class);
        showText2Action.setPosition(xPosition, yPosition);
        showText2Action.setRelativeTextSize(relativeTextSize);
        showText2Action.setColor(color);
        showText2Action.setName(name2);
        showText2Action.setText(text);
        showText2Action.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        showText2Action.setAlignment(alignment);
        showText2Action.setAndroidStringProvider(androidStringProvider);
        return showText2Action;
    }

    public Action createHideVariableAction(Sprite sprite, UserVariable userVariable, ShowTextUtils.AndroidStringProvider androidStringProvider) {
        HideTextAction hideTextAction = (HideTextAction) action(HideTextAction.class);
        hideTextAction.setVariableToHide(userVariable);
        hideTextAction.setSprite(sprite);
        hideTextAction.setAndroidStringProvider(androidStringProvider);
        return hideTextAction;
    }

    public Action createFlashAction(boolean flashOn) {
        FlashAction flashAction = (FlashAction) action(FlashAction.class);
        flashAction.setFlashOn(flashOn);
        return flashAction;
    }

    public Action createVibrateAction(Sprite sprite, SequenceAction sequence, Formula duration) {
        VibrateAction vibrateAction = (VibrateAction) action(VibrateAction.class);
        vibrateAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        vibrateAction.setDuration(duration);
        return vibrateAction;
    }

    public Action createUpdateCameraPreviewAction(boolean turnOn) {
        CameraBrickAction cameraBrickAction = (CameraBrickAction) action(CameraBrickAction.class);
        cameraBrickAction.setActive(turnOn);
        return cameraBrickAction;
    }

    public Action nativeViewLayer(int layer) {
        NativeLayerAction nativeLayerAction = (NativeLayerAction) action(NativeLayerAction.class);
        nativeLayerAction.setLayer(layer);
        return nativeLayerAction;
    }

    public Action createFadeParticleEffectsAction(Sprite sprite, boolean turnOn) {
        FadeParticleEffectAction fadeParticleEffectAction = (FadeParticleEffectAction) action(FadeParticleEffectAction.class);
        fadeParticleEffectAction.setFadeIn(turnOn);
        fadeParticleEffectAction.setSprite(sprite);
        fadeParticleEffectAction.setBackgroundSprite(ProjectManager.getInstance().getCurrentlyPlayingScene().getBackgroundSprite());
        return fadeParticleEffectAction;
    }

    public Action createAdditiveParticleEffectsAction(Sprite sprite, boolean turnOn) {
        AdditiveParticleEffectAction additiveParticleEffectAction = (AdditiveParticleEffectAction) action(AdditiveParticleEffectAction.class);
        additiveParticleEffectAction.setFadeIn(turnOn);
        additiveParticleEffectAction.setSprite(sprite);
        return additiveParticleEffectAction;
    }

    public Action createSetParticleColorAction(Sprite sprite, Formula color, SequenceAction sequence) {
        SetParticleColorAction setParticleColorAction = (SetParticleColorAction) action(SetParticleColorAction.class);
        setParticleColorAction.setColor(color);
        setParticleColorAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return setParticleColorAction;
    }

    public Action createSetFrontCameraAction() {
        ChooseCameraAction chooseCameraAction = (ChooseCameraAction) action(ChooseCameraAction.class);
        chooseCameraAction.setFrontCamera();
        return chooseCameraAction;
    }

    public Action createChooseFileAction(Sprite sprite, SequenceAction sequence, Integer type, UserVariable variable) {
        ChooseFileAction chooseFileAction = (ChooseFileAction) action(ChooseFileAction.class);
        chooseFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        chooseFileAction.setFileType(type.intValue());
        chooseFileAction.setVariable(variable);
        return chooseFileAction;
    }

    public Action createSetBackCameraAction() {
        ChooseCameraAction chooseCameraAction = (ChooseCameraAction) action(ChooseCameraAction.class);
        chooseCameraAction.setBackCamera();
        return chooseCameraAction;
    }

    public Action createSendDigitalArduinoValueAction(Sprite sprite, SequenceAction sequence, Formula pinNumber, Formula pinValue) {
        ArduinoSendDigitalValueAction arduinoSendDigitalValueAction = (ArduinoSendDigitalValueAction) action(ArduinoSendDigitalValueAction.class);
        arduinoSendDigitalValueAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        arduinoSendDigitalValueAction.setPinNumber(pinNumber);
        arduinoSendDigitalValueAction.setPinValue(pinValue);
        return arduinoSendDigitalValueAction;
    }

    public Action createSendPWMArduinoValueAction(Sprite sprite, SequenceAction sequence, Formula pinNumber, Formula pinValue) {
        ArduinoSendPWMValueAction arduinoSendPWMValueAction = (ArduinoSendPWMValueAction) action(ArduinoSendPWMValueAction.class);
        arduinoSendPWMValueAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        arduinoSendPWMValueAction.setPinNumber(pinNumber);
        arduinoSendPWMValueAction.setPinValue(pinValue);
        return arduinoSendPWMValueAction;
    }

    public Action createSendDigitalRaspiValueAction(Sprite sprite, SequenceAction sequence, Formula pinNumber, Formula pinValue) {
        RaspiSendDigitalValueAction raspiSendDigitalValueAction = (RaspiSendDigitalValueAction) action(RaspiSendDigitalValueAction.class);
        raspiSendDigitalValueAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        raspiSendDigitalValueAction.setPinNumber(pinNumber);
        raspiSendDigitalValueAction.setPinValue(pinValue);
        return raspiSendDigitalValueAction;
    }

    public Action createSendRaspiPwmValueAction(Sprite sprite, SequenceAction sequence, Formula pinNumber, Formula pwmFrequency, Formula pwmPercentage) {
        RaspiPwmAction raspiPwmAction = (RaspiPwmAction) action(RaspiPwmAction.class);
        raspiPwmAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        raspiPwmAction.setPinNumberFormula(pinNumber);
        raspiPwmAction.setPwmFrequencyFormula(pwmFrequency);
        raspiPwmAction.setPwmPercentageFormula(pwmPercentage);
        return raspiPwmAction;
    }

    public Action createRaspiIfLogicActionAction(Sprite sprite, SequenceAction sequence, Formula pinNumber, Action ifAction, Action elseAction) {
        RaspiIfLogicAction raspiIfLogicAction = (RaspiIfLogicAction) action(RaspiIfLogicAction.class);
        raspiIfLogicAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        raspiIfLogicAction.setPinNumber(pinNumber);
        raspiIfLogicAction.setIfAction(ifAction);
        raspiIfLogicAction.setElseAction(elseAction);
        return raspiIfLogicAction;
    }

    public Action createStopScriptAction(int spinnerSelection, Script currentScript, Sprite sprite) {
        if (spinnerSelection == 0) {
            StopThisScriptAction stopThisScriptAction = (StopThisScriptAction) Actions.action(StopThisScriptAction.class);
            stopThisScriptAction.setCurrentScript(currentScript);
            return stopThisScriptAction;
        }
        if (spinnerSelection == 2) {
            StopOtherScriptsAction stopOtherScriptsAction = (StopOtherScriptsAction) Actions.action(StopOtherScriptsAction.class);
            stopOtherScriptsAction.setCurrentScript(currentScript);
            stopOtherScriptsAction.setSprite(sprite);
            return stopOtherScriptsAction;
        }
        return Actions.action(StopAllScriptsAction.class);
    }

    public Action createReportAction(Sprite sprite, SequenceAction sequence, Script currentScript, Formula reportFormula) {
        if (currentScript instanceof UserDefinedScript) {
            ReportAction reportAction = (ReportAction) Actions.action(ReportAction.class);
            reportAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
            reportAction.setCurrentScript(currentScript);
            reportAction.setReportFormula(reportFormula);
            return reportAction;
        }
        StopThisScriptAction stopThisScriptAction = (StopThisScriptAction) Actions.action(StopThisScriptAction.class);
        stopThisScriptAction.setCurrentScript(currentScript);
        return stopThisScriptAction;
    }

    public Action createSetNfcTagAction(Sprite sprite, SequenceAction sequence, Formula nfcNdefMessage, int nfcNdefSpinnerSelection) {
        SetNfcTagAction setNfcTagAction = (SetNfcTagAction) Actions.action(SetNfcTagAction.class);
        setNfcTagAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setNfcTagAction.setNfcTagNdefSpinnerSelection(nfcNdefSpinnerSelection);
        setNfcTagAction.setNfcNdefMessage(nfcNdefMessage);
        return setNfcTagAction;
    }

    public Action createAssertEqualsAction(Sprite sprite, SequenceAction sequence, Formula actual, Formula expected, String position) {
        AssertEqualsAction assertEqualsAction = (AssertEqualsAction) action(AssertEqualsAction.class);
        assertEqualsAction.setActualFormula(actual);
        assertEqualsAction.setExpectedFormula(expected);
        assertEqualsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        assertEqualsAction.setPosition(position);
        return assertEqualsAction;
    }

    public Action createAssertUserListsAction(Sprite sprite, SequenceAction sequence, UserList actual, UserList expected, String position) {
        AssertUserListAction assertUserListAction = (AssertUserListAction) action(AssertUserListAction.class);
        assertUserListAction.setActualUserList(actual);
        assertUserListAction.setExpectedUserList(expected);
        assertUserListAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        assertUserListAction.setPosition(position);
        return assertUserListAction;
    }

    public Action createRepeatParameterizedAction(Sprite sprite, ParameterizedData data, List<? extends Pair<UserList, UserVariable>> parameters, String position, Action repeatedAction, boolean isLoopDelay) {
        RepeatParameterizedAction repeatParameterizedAction = (RepeatParameterizedAction) action(RepeatParameterizedAction.class);
        repeatParameterizedAction.setParameterizedData(data);
        repeatParameterizedAction.setParameters(parameters);
        repeatParameterizedAction.setAction(repeatedAction);
        repeatParameterizedAction.setLoopDelay(isLoopDelay);
        repeatParameterizedAction.setSprite(sprite);
        repeatParameterizedAction.setPosition(position);
        return repeatParameterizedAction;
    }

    public Action createParameterizedAssertAction(Sprite sprite, SequenceAction sequence, Formula actual, UserList expected, ParameterizedData data, String position) {
        ParameterizedAssertAction parameterizedAssertAction = (ParameterizedAssertAction) action(ParameterizedAssertAction.class);
        parameterizedAssertAction.setActualFormula(actual);
        parameterizedAssertAction.setExpectedList(expected);
        parameterizedAssertAction.setParameterizedData(data);
        parameterizedAssertAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        parameterizedAssertAction.setPosition(position);
        return parameterizedAssertAction;
    }

    public Action createFinishStageAction(boolean silent) {
        FinishStageAction finishStageAction = (FinishStageAction) action(FinishStageAction.class);
        finishStageAction.setSilent(silent);
        return finishStageAction;
    }

    public Action createTapAtAction(Sprite sprite, SequenceAction sequence, Formula x, Formula y) {
        TapAtAction tapAtAction = (TapAtAction) Actions.action(TapAtAction.class);
        tapAtAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        tapAtAction.setStartX(x);
        tapAtAction.setStartY(y);
        return tapAtAction;
    }

    public Action createTapForAction(Sprite sprite, SequenceAction sequence, Formula x, Formula y, Formula duration) {
        TapAtAction tapAtAction = (TapAtAction) Actions.action(TapAtAction.class);
        tapAtAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        tapAtAction.setDurationFormula(duration);
        tapAtAction.setStartX(x);
        tapAtAction.setStartY(y);
        return tapAtAction;
    }

    public Action createTouchAndSlideAction(Sprite sprite, SequenceAction sequence, Formula x, Formula y, Formula xChange, Formula yChange, Formula duration) {
        TapAtAction tapAtAction = (TapAtAction) Actions.action(TapAtAction.class);
        tapAtAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        tapAtAction.setDurationFormula(duration);
        tapAtAction.setStartX(x);
        tapAtAction.setStartY(y);
        tapAtAction.setChangeX(xChange);
        tapAtAction.setChangeY(yChange);
        return tapAtAction;
    }

    public Action createWriteVariableOnDeviceAction(UserVariable userVariable) {
        WriteUserDataOnDeviceAction writeUserDataOnDeviceAction = (WriteUserDataOnDeviceAction) Actions.action(WriteUserDataOnDeviceAction.class);
        DeviceVariableAccessor deviceVariableAccessor = new DeviceVariableAccessor(ProjectManager.getInstance().getCurrentProject().getDirectory());
        writeUserDataOnDeviceAction.setUserData(userVariable);
        writeUserDataOnDeviceAction.setAccessor(deviceVariableAccessor);
        return writeUserDataOnDeviceAction;
    }

    public Action createWriteVariableToFileAction(Sprite sprite, SequenceAction sequence, Formula variableFormula, UserVariable userVariable) {
        WriteVarToFileAction writeVarToFileAction = (WriteVarToFileAction) action(WriteVarToFileAction.class);
        writeVarToFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        writeVarToFileAction.setUserVariable(userVariable);
        writeVarToFileAction.setFormula(variableFormula);
        return writeVarToFileAction;
    }

    public Action createReadVariableFromFileAction(Sprite sprite, SequenceAction sequence, Formula variableFormula, UserVariable userVariable, boolean deleteFile) {
        ReadVariableFromFileAction readVariableFromFileAction = (ReadVariableFromFileAction) Actions.action(ReadVariableFromFileAction.class);
        readVariableFromFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        readVariableFromFileAction.setUserVariable(userVariable);
        readVariableFromFileAction.setFormula(variableFormula);
        readVariableFromFileAction.setDeleteFile(deleteFile);
        return readVariableFromFileAction;
    }

    public Action createWriteListOnDeviceAction(UserList userList) {
        WriteUserDataOnDeviceAction writeUserDataOnDeviceAction = (WriteUserDataOnDeviceAction) Actions.action(WriteUserDataOnDeviceAction.class);
        DeviceListAccessor deviceListAccessor = new DeviceListAccessor(ProjectManager.getInstance().getCurrentProject().getDirectory());
        writeUserDataOnDeviceAction.setUserData(userList);
        writeUserDataOnDeviceAction.setAccessor(deviceListAccessor);
        return writeUserDataOnDeviceAction;
    }

    public Action createWaitTillIdleAction() {
        return action(WaitTillIdleAction.class);
    }

    public Action createReadVariableFromDeviceAction(UserVariable userVariable) {
        ReadVariableFromDeviceAction readVariableFromDeviceAction = (ReadVariableFromDeviceAction) Actions.action(ReadVariableFromDeviceAction.class);
        readVariableFromDeviceAction.setUserVariable(userVariable);
        return readVariableFromDeviceAction;
    }

    public Action createReadListFromDeviceAction(UserList userList) {
        ReadListFromDeviceAction readListFromDeviceAction = (ReadListFromDeviceAction) Actions.action(ReadListFromDeviceAction.class);
        readListFromDeviceAction.setUserList(userList);
        return readListFromDeviceAction;
    }

    public Action createWebRequestAction(Sprite sprite, SequenceAction sequence, Formula variableFormula, UserVariable userVariable) {
        WebRequestAction webRequestAction = (WebRequestAction) action(WebRequestAction.class);
        webRequestAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        webRequestAction.setFormula(variableFormula);
        webRequestAction.setUserVariable(userVariable);
        return webRequestAction;
    }

    public Action createAskGPTAction(Sprite sprite, SequenceAction sequence, Formula prompt, Formula system, UserVariable userVariable) {
        AskGPTAction askGPTAction = (AskGPTAction) action(AskGPTAction.class);
        askGPTAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        askGPTAction.setPformula(prompt);
        askGPTAction.setSformula(system);
        askGPTAction.setUserVariable(userVariable);
        return askGPTAction;
    }

    public Action createLookRequestAction(Sprite sprite, SequenceAction sequence, Formula variableFormula) {
        LookRequestAction lookRequestAction = (LookRequestAction) action(LookRequestAction.class);
        lookRequestAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        lookRequestAction.setFormula(variableFormula);
        return lookRequestAction;
    }

    public Action createOpenUrlAction(Sprite sprite, SequenceAction sequence, Formula variableFormula) {
        OpenUrlAction openUrlAction = (OpenUrlAction) action(OpenUrlAction.class);
        openUrlAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        openUrlAction.setFormula(variableFormula);
        return openUrlAction;
    }

    public Action createStartListeningAction(UserVariable userVariable) {
        if (((MobileServiceAvailability) KoinJavaComponent.get(MobileServiceAvailability.class)).isHmsAvailable(ProjectManager.getInstance().getApplicationContext())) {
            AskSpeechAction askSpeechAction = (AskSpeechAction) Actions.action(AskSpeechAction.class);
            askSpeechAction.setAnswerVariable(userVariable);
            return askSpeechAction;
        }
        StartListeningAction startListeningAction = (StartListeningAction) Actions.action(StartListeningAction.class);
        startListeningAction.setUserVariable(userVariable);
        return startListeningAction;
    }

    public Action createSetListeningLanguageAction(String listeningLanguageTag) {
        SetListeningLanguageAction setListeningLanguageAction = (SetListeningLanguageAction) action(SetListeningLanguageAction.class);
        setListeningLanguageAction.listeningLanguageTag = listeningLanguageTag;
        return setListeningLanguageAction;
    }

    public Action createPaintNewLookAction(Sprite sprite, SequenceAction sequence, Formula variableFormula, SetNextLookAction nextLookAction) {
        PaintNewLookAction paintNewLookAction = (PaintNewLookAction) action(PaintNewLookAction.class);
        paintNewLookAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        paintNewLookAction.setFormula(variableFormula);
        paintNewLookAction.nextLookAction(nextLookAction);
        return paintNewLookAction;
    }

    public Action createCopyLookAction(Sprite sprite, SequenceAction sequence, Formula variableFormula, SetNextLookAction nextLookAction) {
        CopyLookAction copyLookAction = (CopyLookAction) action(CopyLookAction.class);
        copyLookAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        copyLookAction.setFormula(variableFormula);
        copyLookAction.nextLookAction(nextLookAction);
        return copyLookAction;
    }

    public Action createEditLookAction(Sprite sprite, SequenceAction sequence, SetNextLookAction nextLookAction) {
        EditLookAction editLookAction = (EditLookAction) action(EditLookAction.class);
        editLookAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        editLookAction.nextLookAction(nextLookAction);
        return editLookAction;
    }

    public Action createExportProjectFileAction(Sprite sprite, ScriptSequenceAction sequence, Formula formulaWithBrickField) {
        ExportProjectFileAction exportProjectFileAction = (ExportProjectFileAction) action(ExportProjectFileAction.class);
        exportProjectFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        exportProjectFileAction.setProjectFileName(formulaWithBrickField);
        return exportProjectFileAction;
    }

    public Action createGLViewAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula x, Formula y, Formula width, Formula height) {
        CreateGLViewAction createGLViewAction = (CreateGLViewAction) action(CreateGLViewAction.class);
        createGLViewAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createGLViewAction.setViewName(name2);
        createGLViewAction.setX(x);
        createGLViewAction.setY(y);
        createGLViewAction.setWidth(width);
        createGLViewAction.setHeight(height);
        return createGLViewAction;
    }

    public Action createAttachSOAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula file) {
        AttachSOAction attachSOAction = (AttachSOAction) action(AttachSOAction.class);
        attachSOAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        attachSOAction.setViewName(name2);
        attachSOAction.setSoFileName(file);
        return attachSOAction;
    }

    public Action createDiskAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula size) {
        CreateDiskAction createDiskAction = (CreateDiskAction) action(CreateDiskAction.class);
        createDiskAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createDiskAction.setDiskName(name2);
        createDiskAction.setDiskSize(size);
        return createDiskAction;
    }

    public Action createRunVMAction(Sprite sprite, SequenceAction sequence, Formula memory, Formula cpu, Formula hda, Formula cdrom) {
        RunVMAction runVMAction = (RunVMAction) action(RunVMAction.class);
        runVMAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        runVMAction.setMemory(memory);
        runVMAction.setCpuCores(cpu);
        runVMAction.setHdaPath(hda);
        runVMAction.setCdromPath(cdrom);
        return runVMAction;
    }

    public Action createStopVMAction(Sprite sprite, SequenceAction sequence) {
        StopVMAction stopVMAction = (StopVMAction) action(StopVMAction.class);
        stopVMAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return stopVMAction;
    }

    public Action createToggleDisplayAction(Sprite sprite, SequenceAction sequence, Formula visible) {
        ToggleDisplayAction toggleDisplayAction = (ToggleDisplayAction) action(ToggleDisplayAction.class);
        toggleDisplayAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        toggleDisplayAction.setVisible(visible);
        return toggleDisplayAction;
    }

    public Action createMouseEventAction(Sprite sprite, SequenceAction sequence, Formula x, Formula y, Formula state) {
        MouseEventAction mouseEventAction = (MouseEventAction) action(MouseEventAction.class);
        mouseEventAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        mouseEventAction.setX(x);
        mouseEventAction.setY(y);
        mouseEventAction.setButtonState(state);
        return mouseEventAction;
    }

    public Action createKeyEventAction(Sprite sprite, SequenceAction sequence, Formula character, Formula isDown) {
        KeyEventAction keyEventAction = (KeyEventAction) action(KeyEventAction.class);
        keyEventAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        keyEventAction.setCharacter(character);
        keyEventAction.setDown(isDown);
        return keyEventAction;
    }

    public Action createLoadSceneAction(Sprite sprite, SequenceAction sequence, Formula fileName) {
        LoadSceneAction loadSceneAction = (LoadSceneAction) action(LoadSceneAction.class);
        loadSceneAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        loadSceneAction.setFileName(fileName);
        return loadSceneAction;
    }

    public Action createLockMouseAction() {
        return action(LockMouseAction.class);
    }

    public Action createUnlockMouseAction() {
        return action(UnlockMouseAction.class);
    }

    public Action createSetParentAction(Sprite sprite, SequenceAction sequence, Formula child, Formula parent) {
        SetParentAction setParentAction = (SetParentAction) action(SetParentAction.class);
        setParentAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setParentAction.setChildObject(child);
        setParentAction.setParentObject(parent);
        return setParentAction;
    }

    public Action createCreatePointJointAction(Sprite sprite, SequenceAction sequence, Formula constraintName, Formula objectA, Formula objectB, Formula pivotAX, Formula pivotAY, Formula pivotAZ, Formula pivotBX, Formula pivotBY, Formula pivotBZ) {
        CreatePointJointAction createPointJointAction = (CreatePointJointAction) action(CreatePointJointAction.class);
        createPointJointAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createPointJointAction.setConstraintName(constraintName);
        createPointJointAction.setObjectA(objectA);
        createPointJointAction.setObjectB(objectB);
        createPointJointAction.setPivotAX(pivotAX);
        createPointJointAction.setPivotAY(pivotAY);
        createPointJointAction.setPivotAZ(pivotAZ);
        createPointJointAction.setPivotBX(pivotBX);
        createPointJointAction.setPivotBY(pivotBY);
        createPointJointAction.setPivotBZ(pivotBZ);
        return createPointJointAction;
    }

    public Action createRemoveParentAction(Sprite sprite, SequenceAction sequence, Formula child) {
        RemoveParentAction removeParentAction = (RemoveParentAction) action(RemoveParentAction.class);
        removeParentAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        removeParentAction.setChildObject(child);
        return removeParentAction;
    }

    public Action createRemoveJointAction(Sprite sprite, SequenceAction sequence, Formula jointName) {
        RemoveJointAction removeJointAction = (RemoveJointAction) action(RemoveJointAction.class);
        removeJointAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        removeJointAction.setJointName(jointName);
        return removeJointAction;
    }

    public Action createSetActiveAction(Sprite sprite, SequenceAction sequence, Formula objectName, boolean activeState) {
        SetActiveAction setActiveAction = (SetActiveAction) action(SetActiveAction.class);
        setActiveAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setActiveAction.setObjectName(objectName);
        setActiveAction.setActiveState(activeState);
        return setActiveAction;
    }

    public Action createCloneObjectAction(Sprite sprite, SequenceAction sequence, Formula source, Formula newName) {
        CloneObjectAction cloneObjectAction = (CloneObjectAction) action(CloneObjectAction.class);
        cloneObjectAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        cloneObjectAction.setSourceObjectName(source);
        cloneObjectAction.setCloneObjectName(newName);
        return cloneObjectAction;
    }

    public Action createSetMaterialAction(Sprite sprite, SequenceAction sequence, Formula objectName, Formula r, Formula g, Formula b, Formula a, Formula metallic, Formula roughness, Formula colorTexture, Formula normalTexture, Formula mrTexture) {
        SetMaterialAction setMaterialAction = (SetMaterialAction) action(SetMaterialAction.class);
        setMaterialAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setMaterialAction.setObjectName(objectName);
        setMaterialAction.setColorR(r);
        setMaterialAction.setColorG(g);
        setMaterialAction.setColorB(b);
        setMaterialAction.setColorA(a);
        setMaterialAction.setMetallic(metallic);
        setMaterialAction.setRoughness(roughness);
        setMaterialAction.setBaseColorTexture(colorTexture);
        setMaterialAction.setNormalTexture(normalTexture);
        setMaterialAction.setMetallicRoughnessTexture(mrTexture);
        return setMaterialAction;
    }

    public Action createPlaySoundAtPositionAction(Sprite sprite, SequenceAction sequence, Formula soundName, Formula instanceName, Formula x, Formula y, Formula z, Formula volume, Formula pitch, boolean loop) {
        PlaySoundAtPositionAction playSoundAtPositionAction = (PlaySoundAtPositionAction) action(PlaySoundAtPositionAction.class);
        playSoundAtPositionAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        playSoundAtPositionAction.setSoundName(soundName);
        playSoundAtPositionAction.setInstanceName(instanceName);
        playSoundAtPositionAction.setPosX(x);
        playSoundAtPositionAction.setPosY(y);
        playSoundAtPositionAction.setPosZ(z);
        playSoundAtPositionAction.setVolume(volume);
        playSoundAtPositionAction.setPitch(pitch);
        playSoundAtPositionAction.setLoop(loop);
        return playSoundAtPositionAction;
    }

    public Action createPrepareSound2Action(Sprite sprite, SequenceAction sequence, Formula fileName, Formula soundName) {
        PrepareSoundAction2 prepareSoundAction2 = (PrepareSoundAction2) action(PrepareSoundAction2.class);
        prepareSoundAction2.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        prepareSoundAction2.setFileName(fileName);
        prepareSoundAction2.setSoundName(soundName);
        return prepareSoundAction2;
    }

    public Action createStopSoundAction2(Sprite sprite, SequenceAction sequence, Formula instanceName) {
        StopSoundAction2 stopSoundAction2 = (StopSoundAction2) action(StopSoundAction2.class);
        stopSoundAction2.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        stopSoundAction2.setInstanceName(instanceName);
        return stopSoundAction2;
    }

    public Action createSetGlobalSoundVolumeAction(Sprite sprite, SequenceAction sequence, Formula volume) {
        SetGlobalSoundVolumeAction setGlobalSoundVolumeAction = (SetGlobalSoundVolumeAction) action(SetGlobalSoundVolumeAction.class);
        setGlobalSoundVolumeAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setGlobalSoundVolumeAction.setVolume(volume);
        return setGlobalSoundVolumeAction;
    }

    public Action createPrepareMusicAction(Sprite sprite, SequenceAction sequence, Formula fileName, Formula soundName) {
        PrepareMusicAction prepareMusicAction = (PrepareMusicAction) action(PrepareMusicAction.class);
        prepareMusicAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        prepareMusicAction.setFileName(fileName);
        prepareMusicAction.setSoundName(soundName);
        return prepareMusicAction;
    }

    public Action createLoadSceneAdditiveAction(Sprite sprite, SequenceAction sequence, Formula fileName) {
        LoadSceneAdditiveAction loadSceneAdditiveAction = (LoadSceneAdditiveAction) action(LoadSceneAdditiveAction.class);
        loadSceneAdditiveAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        loadSceneAdditiveAction.setFileName(fileName);
        return loadSceneAdditiveAction;
    }

    public Action createSet3DSoundPositionAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula x, Formula y, Formula z) {
        Set3DSoundPositionAction set3DSoundPositionAction = (Set3DSoundPositionAction) action(Set3DSoundPositionAction.class);
        set3DSoundPositionAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        set3DSoundPositionAction.setSoundName(name2);
        set3DSoundPositionAction.setX(x);
        set3DSoundPositionAction.setY(y);
        set3DSoundPositionAction.setZ(z);
        return set3DSoundPositionAction;
    }

    public Action createVmRelativeMouseMoveAction(Sprite sprite, ScriptSequenceAction sequence, Formula dx, Formula dy, Formula mask) {
        VmRelativeMouseMoveAction vmRelativeMouseMoveAction = (VmRelativeMouseMoveAction) action(VmRelativeMouseMoveAction.class);
        vmRelativeMouseMoveAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        vmRelativeMouseMoveAction.setDxFormula(dx);
        vmRelativeMouseMoveAction.setDyFormula(dy);
        vmRelativeMouseMoveAction.setMaskFormula(mask);
        return vmRelativeMouseMoveAction;
    }

    public Action createVmSetMonitorSizeAction(Sprite sprite, ScriptSequenceAction sequence, Formula width, Formula height) {
        VmSetMonitorSizeAction vmSetMonitorSizeAction = (VmSetMonitorSizeAction) action(VmSetMonitorSizeAction.class);
        vmSetMonitorSizeAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        vmSetMonitorSizeAction.setWidthFormula(width);
        vmSetMonitorSizeAction.setHeightFormula(height);
        return vmSetMonitorSizeAction;
    }

    public Action createSetPostProcessingAction(Sprite sprite, ScriptSequenceAction sequence, int effectIdx, int paramIdx, Formula val) {
        SetPostProcessingAction setPostProcessingAction = (SetPostProcessingAction) action(SetPostProcessingAction.class);
        setPostProcessingAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setPostProcessingAction.setEffectIndex(effectIdx);
        setPostProcessingAction.setParamIndex(paramIdx);
        setPostProcessingAction.setValueFormula(val);
        return setPostProcessingAction;
    }

    public Action createParticlesAction(Sprite sprite, ScriptSequenceAction sequence, Formula particleId, Formula looping, Formula duration, Formula startLifetime, Formula startSpeed, Formula startSize, Formula gravityModifier, Formula maxParticles, Formula emissionRate, Formula coneAngle, Formula coneRadius, Formula startColor, Formula endColor, Formula endSize, Formula texturePath, Formula isAdditive, Formula startRotation, Formula rotationOverLifetime, Formula positionX, Formula positionY, Formula positionZ, Formula rotationPitch, Formula rotationYaw, Formula rotationRoll) {
        CreateParticlesAction createParticlesAction = (CreateParticlesAction) action(CreateParticlesAction.class);
        createParticlesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createParticlesAction.setParticleId(particleId);
        createParticlesAction.setLooping(looping);
        createParticlesAction.setDuration(duration);
        createParticlesAction.setStartLifetime(startLifetime);
        createParticlesAction.setStartSpeed(startSpeed);
        createParticlesAction.setStartSize(startSize);
        createParticlesAction.setGravityModifier(gravityModifier);
        createParticlesAction.setMaxParticles(maxParticles);
        createParticlesAction.setEmissionRate(emissionRate);
        createParticlesAction.setConeAngle(coneAngle);
        createParticlesAction.setConeRadius(coneRadius);
        createParticlesAction.setStartColor(startColor);
        createParticlesAction.setEndColor(endColor);
        createParticlesAction.setEndSize(endSize);
        createParticlesAction.setTexturePath(texturePath);
        createParticlesAction.setAdditive(isAdditive);
        createParticlesAction.setStartRotation(startRotation);
        createParticlesAction.setRotationOverLifetime(rotationOverLifetime);
        createParticlesAction.setPositionX(positionX);
        createParticlesAction.setPositionY(positionY);
        createParticlesAction.setPositionZ(positionZ);
        createParticlesAction.setRotationPitch(rotationPitch);
        createParticlesAction.setRotationYaw(rotationYaw);
        createParticlesAction.setRotationRoll(rotationRoll);
        return createParticlesAction;
    }

    public Action createDeleteParticlesAction(Sprite sprite, ScriptSequenceAction sequence, Formula particleId) {
        DeleteParticlesAction deleteParticlesAction = (DeleteParticlesAction) action(DeleteParticlesAction.class);
        deleteParticlesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        deleteParticlesAction.setParticleId(particleId);
        return deleteParticlesAction;
    }

    public Action createSetShadowsAction(Sprite sprite, SequenceAction sequence, Formula enabledFormula) {
        SetShadowsAction setShadowsAction = (SetShadowsAction) action(SetShadowsAction.class);
        setShadowsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setShadowsAction.setEnabledFormula(enabledFormula);
        return setShadowsAction;
    }

    public Action createSetVariableEasingAction(Sprite sprite, SequenceAction sequence, UserVariable variable, int typeIndex, Formula time, Formula duration, Formula start, Formula end) {
        SetVariableEasingAction setVariableEasingAction = (SetVariableEasingAction) action(SetVariableEasingAction.class);
        setVariableEasingAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setVariableEasingAction.setUserVariable(variable);
        setVariableEasingAction.setTypeIndex(typeIndex);
        setVariableEasingAction.setTimeFormula(time);
        setVariableEasingAction.setDurationFormula(duration);
        setVariableEasingAction.setStartFormula(start);
        setVariableEasingAction.setEndFormula(end);
        return setVariableEasingAction;
    }

    public Action createSetRotationLockAction(Sprite sprite, SequenceAction sequence, Formula objectId) {
        SetRotationLockAction setRotationLockAction = (SetRotationLockAction) action(SetRotationLockAction.class);
        setRotationLockAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setRotationLockAction.setObjectId(objectId);
        return setRotationLockAction;
    }

    public Action createSetSpawnInvisibleAction(Sprite sprite, SequenceAction sequence, Formula objectId) {
        SetSpawnInvisibleAction setSpawnInvisibleAction = (SetSpawnInvisibleAction) action(SetSpawnInvisibleAction.class);
        setSpawnInvisibleAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setSpawnInvisibleAction.setObjectId(objectId);
        return setSpawnInvisibleAction;
    }

    public Action createKeyframeAnimationAction(Sprite sprite, SequenceAction sequence, Formula objectId, int actionType, Formula time) {
        KeyframeAnimationAction keyframeAnimationAction = (KeyframeAnimationAction) action(KeyframeAnimationAction.class);
        keyframeAnimationAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        keyframeAnimationAction.setObjectId(objectId);
        keyframeAnimationAction.setActionType(actionType);
        keyframeAnimationAction.setTimeFormula(time);
        return keyframeAnimationAction;
    }

    public Action createPtCreateTensorAction(Sprite sprite, SequenceAction sequence, Formula formulaWithBrickField, Formula formulaWithBrickField1, Formula formulaWithBrickField2, Formula formulaWithBrickField3) {
        PtCreateTensorAction ptCreateTensorAction = (PtCreateTensorAction) action(PtCreateTensorAction.class);
        ptCreateTensorAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        ptCreateTensorAction.setNameFormula(formulaWithBrickField);
        ptCreateTensorAction.setShapeFormula(formulaWithBrickField1);
        ptCreateTensorAction.setValueFormula(formulaWithBrickField2);
        ptCreateTensorAction.setTrainableFormula(formulaWithBrickField3);
        return ptCreateTensorAction;
    }

    public Action createPtOpAction(Sprite sprite, ScriptSequenceAction sequence, Formula formulaWithBrickField, Formula formulaWithBrickField1, Formula formulaWithBrickField2, String op) {
        PtOpAction ptOpAction = (PtOpAction) action(PtOpAction.class);
        ptOpAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        ptOpAction.setResFormula(formulaWithBrickField);
        ptOpAction.setAFormula(formulaWithBrickField1);
        ptOpAction.setBFormula(formulaWithBrickField2);
        ptOpAction.setOpType(op);
        return ptOpAction;
    }

    public Action createPtBackwardAction(Sprite sprite, SequenceAction sequence, Formula lossName) {
        PtBackwardAction ptBackwardAction = (PtBackwardAction) action(PtBackwardAction.class);
        ptBackwardAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        ptBackwardAction.setLossNameFormula(lossName);
        return ptBackwardAction;
    }

    public Action createPtStepAction(Sprite sprite, SequenceAction sequence, Formula lr) {
        PtStepAction ptStepAction = (PtStepAction) action(PtStepAction.class);
        ptStepAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        ptStepAction.setLrFormula(lr);
        return ptStepAction;
    }

    public Action createPtSetTensorAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula data) {
        PtSetTensorAction ptSetTensorAction = (PtSetTensorAction) action(PtSetTensorAction.class);
        ptSetTensorAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        ptSetTensorAction.setNameFormula(name2);
        ptSetTensorAction.setDataFormula(data);
        return ptSetTensorAction;
    }

    public Action createPtSetByIndexAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula index, Formula value) {
        PtSetByIndexAction ptSetByIndexAction = (PtSetByIndexAction) action(PtSetByIndexAction.class);
        ptSetByIndexAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        ptSetByIndexAction.setNameFormula(name2);
        ptSetByIndexAction.setIndexFormula(index);
        ptSetByIndexAction.setValueFormula(value);
        return ptSetByIndexAction;
    }

    public Action createPtReshapeAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula shape) {
        PtReshapeAction ptReshapeAction = (PtReshapeAction) action(PtReshapeAction.class);
        ptReshapeAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        ptReshapeAction.setNameFormula(name2);
        ptReshapeAction.setShapeFormula(shape);
        return ptReshapeAction;
    }

    public Action createPtSetTrainingAction(Sprite sprite, ScriptSequenceAction sequence, Formula formulaWithBrickField) {
        PtSetTraining ptSetTraining = (PtSetTraining) action(PtSetTraining.class);
        ptSetTraining.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        ptSetTraining.setTrainingFormula(formulaWithBrickField);
        return ptSetTraining;
    }

    public Action createMLStepAdamAction(Sprite sprite, SequenceAction sequence, Formula lr) {
        MLStepAdamAction mLStepAdamAction = (MLStepAdamAction) action(MLStepAdamAction.class);
        mLStepAdamAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        mLStepAdamAction.setLr(lr);
        return mLStepAdamAction;
    }

    public Action createMLFileAction(Sprite sprite, SequenceAction sequence, Formula fileName, boolean isSave) {
        MLFileAction mLFileAction = new MLFileAction(isSave);
        mLFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        mLFileAction.setFileNameFormula(fileName);
        return mLFileAction;
    }

    public Action createGenerateKeyAction(Sprite sprite, SequenceAction sequence, Formula filename, Formula pass, Formula alias, Formula commonName) {
        GenerateKeyAction generateKeyAction = (GenerateKeyAction) action(GenerateKeyAction.class);
        generateKeyAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        generateKeyAction.setFilenameFormula(filename);
        generateKeyAction.setPasswordFormula(pass);
        generateKeyAction.setAliasFormula(alias);
        generateKeyAction.setCommonNameFormula(commonName);
        return generateKeyAction;
    }

    public Action createSignApkAction(Sprite sprite, SequenceAction sequence, Formula input, Formula output, Formula key, Formula pass, Formula alias) {
        SignApkAction signApkAction = (SignApkAction) action(SignApkAction.class);
        signApkAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        signApkAction.setInputApkFormula(input);
        signApkAction.setOutputApkFormula(output);
        signApkAction.setKeystoreFormula(key);
        signApkAction.setPasswordFormula(pass);
        signApkAction.setAliasFormula(alias);
        return signApkAction;
    }

    public Action createUpdateManifestAction(Sprite sprite, SequenceAction sequence, Formula apk, Formula pkg, Formula name2, Formula vCode, Formula vName, Formula minSdk, Formula targetSdk, Formula debug, Formula pAdd, Formula pRem) {
        UpdateManifestAction updateManifestAction = (UpdateManifestAction) action(UpdateManifestAction.class);
        updateManifestAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        updateManifestAction.setApkPathFormula(apk);
        updateManifestAction.setPackageNameFormula(pkg);
        updateManifestAction.setAppNameFormula(name2);
        updateManifestAction.setVersionCodeFormula(vCode);
        updateManifestAction.setVersionNameFormula(vName);
        updateManifestAction.setMinSdkFormula(minSdk);
        updateManifestAction.setTargetSdkFormula(targetSdk);
        updateManifestAction.setDebuggableFormula(debug);
        updateManifestAction.setPermsAddFormula(pAdd);
        updateManifestAction.setPermsRemoveFormula(pRem);
        return updateManifestAction;
    }

    public Action createExtractFileAction(Sprite sprite, SequenceAction sequence, Formula apk, Formula inner, Formula dest) {
        ExtractFileAction extractFileAction = (ExtractFileAction) action(ExtractFileAction.class);
        extractFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        extractFileAction.setApkPathFormula(apk);
        extractFileAction.setInnerPathFormula(inner);
        extractFileAction.setDestPathFormula(dest);
        return extractFileAction;
    }

    public Action createAddFileToApkAction(Sprite sprite, SequenceAction sequence, Formula apk, Formula src, Formula dest) {
        AddFileToApkAction addFileToApkAction = (AddFileToApkAction) action(AddFileToApkAction.class);
        addFileToApkAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        addFileToApkAction.setApkPathFormula(apk);
        addFileToApkAction.setSourcePathFormula(src);
        addFileToApkAction.setDestPathFormula(dest);
        return addFileToApkAction;
    }

    public Action createDeleteFromApkAction(Sprite sprite, SequenceAction sequence, Formula apk, Formula pattern) {
        DeleteFromApkAction deleteFromApkAction = (DeleteFromApkAction) action(DeleteFromApkAction.class);
        deleteFromApkAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        deleteFromApkAction.setApkPathFormula(apk);
        deleteFromApkAction.setDeletePatternFormula(pattern);
        return deleteFromApkAction;
    }

    public Action createThreedAlignNormalAction(Sprite sprite, ScriptSequenceAction sequence, Formula formulaWithBrickField, Formula formulaWithBrickField1, Formula formulaWithBrickField2, Formula formulaWithBrickField3) {
        ThreedAlignNormalAction threedAlignNormalAction = (ThreedAlignNormalAction) action(ThreedAlignNormalAction.class);
        threedAlignNormalAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        threedAlignNormalAction.setObjId(formulaWithBrickField);
        threedAlignNormalAction.setNx(formulaWithBrickField1);
        threedAlignNormalAction.setNy(formulaWithBrickField2);
        threedAlignNormalAction.setNz(formulaWithBrickField3);
        return threedAlignNormalAction;
    }

    public ThreedCreateCylinderAction createThreedCreateCylinderAction(Sprite sprite, ScriptSequenceAction sequence, Formula id2) {
        ThreedCreateCylinderAction threedCreateCylinderAction = (ThreedCreateCylinderAction) action(ThreedCreateCylinderAction.class);
        threedCreateCylinderAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        threedCreateCylinderAction.setObjectId(id2);
        return threedCreateCylinderAction;
    }

    public ThreedAttachObjectToBoneAction createThreedAttachObjectToBoneAction(Sprite sprite, ScriptSequenceAction sequence, Formula childId, Formula parentId, Formula boneName, Formula ox, Formula oy, Formula oz) {
        ThreedAttachObjectToBoneAction threedAttachObjectToBoneAction = (ThreedAttachObjectToBoneAction) action(ThreedAttachObjectToBoneAction.class);
        threedAttachObjectToBoneAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        threedAttachObjectToBoneAction.childId = childId;
        threedAttachObjectToBoneAction.parentId = parentId;
        threedAttachObjectToBoneAction.boneName = boneName;
        threedAttachObjectToBoneAction.offsetX = ox;
        threedAttachObjectToBoneAction.offsetY = oy;
        threedAttachObjectToBoneAction.offsetZ = oz;
        return threedAttachObjectToBoneAction;
    }

    public ThreedBindBoneToObjectAction createThreedBindBoneToObjectAction(Sprite sprite, ScriptSequenceAction sequence, Formula boneName, Formula modelId, Formula targetId) {
        ThreedBindBoneToObjectAction threedBindBoneToObjectAction = (ThreedBindBoneToObjectAction) action(ThreedBindBoneToObjectAction.class);
        threedBindBoneToObjectAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        threedBindBoneToObjectAction.boneName = boneName;
        threedBindBoneToObjectAction.modelId = modelId;
        threedBindBoneToObjectAction.targetId = targetId;
        return threedBindBoneToObjectAction;
    }

    public Action createRunChip8Action(Sprite sprite, SequenceAction sequence, Formula filename) {
        RunChip8Action runChip8Action = (RunChip8Action) action(RunChip8Action.class);
        runChip8Action.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        runChip8Action.setFilenameFormula(filename);
        return runChip8Action;
    }

    public Action createSetAIAction(Sprite sprite, ScriptSequenceAction sequence, Formula objId, Formula mode, Formula target, Formula speed, Formula stop, Formula range, Formula step, Formula avoid) {
        SetAIAction setAIAction = (SetAIAction) action(SetAIAction.class);
        setAIAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setAIAction.setObjectId(objId);
        setAIAction.setMode(mode);
        setAIAction.setTargetId(target);
        setAIAction.setSpeed(speed);
        setAIAction.setStopDist(stop);
        setAIAction.setRange(range);
        setAIAction.setStep(step);
        setAIAction.setAvoid(avoid);
        return setAIAction;
    }

    public Action createCameraTouchControlAction(Sprite sprite, ScriptSequenceAction sequence, Formula enabled, Formula sensitivity, Formula x, Formula y, Formula w, Formula h) {
        CameraTouchControlAction cameraTouchControlAction = (CameraTouchControlAction) action(CameraTouchControlAction.class);
        cameraTouchControlAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        cameraTouchControlAction.setEnabled(enabled);
        cameraTouchControlAction.setSensitivity(sensitivity);
        cameraTouchControlAction.setBlockUI(false);
        cameraTouchControlAction.setAreaX(x);
        cameraTouchControlAction.setAreaY(y);
        cameraTouchControlAction.setAreaW(w);
        cameraTouchControlAction.setAreaH(h);
        return cameraTouchControlAction;
    }

    public Action createSetTextureTilingAction(Sprite sprite, ScriptSequenceAction seq, Formula objId, Formula u, Formula v) {
        SetTextureTilingAction setTextureTilingAction = (SetTextureTilingAction) action(SetTextureTilingAction.class);
        setTextureTilingAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, seq));
        setTextureTilingAction.setObjectId(objId);
        setTextureTilingAction.setScaleU(u);
        setTextureTilingAction.setScaleV(v);
        return setTextureTilingAction;
    }

    public Action createSetSoundInstanceVolumeAction(Sprite sprite, ScriptSequenceAction seq, Formula name2, Formula vol) {
        SetSoundInstanceVolumeAction setSoundInstanceVolumeAction = (SetSoundInstanceVolumeAction) action(SetSoundInstanceVolumeAction.class);
        setSoundInstanceVolumeAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, seq));
        setSoundInstanceVolumeAction.setInstanceName(name2);
        setSoundInstanceVolumeAction.setVolume(vol);
        return setSoundInstanceVolumeAction;
    }

    public Action createSetSoundInstancePitchAction(Sprite sprite, ScriptSequenceAction seq, Formula name2, Formula pitch) {
        SetSoundInstancePitchAction setSoundInstancePitchAction = (SetSoundInstancePitchAction) action(SetSoundInstancePitchAction.class);
        setSoundInstancePitchAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, seq));
        setSoundInstancePitchAction.setInstanceName(name2);
        setSoundInstancePitchAction.setPitch(pitch);
        return setSoundInstancePitchAction;
    }

    public Action createSetParticleEmissionAction(Sprite sprite, ScriptSequenceAction seq, Formula objId, Formula rate) {
        SetParticleEmissionAction setParticleEmissionAction = (SetParticleEmissionAction) action(SetParticleEmissionAction.class);
        setParticleEmissionAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, seq));
        setParticleEmissionAction.setObjectId(objId);
        setParticleEmissionAction.setRate(rate);
        return setParticleEmissionAction;
    }

    public Action createSetAnimationSpeedAction(Sprite sprite, ScriptSequenceAction seq, Formula objId, Formula speed) {
        SetAnimationSpeedAction setAnimationSpeedAction = (SetAnimationSpeedAction) action(SetAnimationSpeedAction.class);
        setAnimationSpeedAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, seq));
        setAnimationSpeedAction.setObjectId(objId);
        setAnimationSpeedAction.setSpeed(speed);
        return setAnimationSpeedAction;
    }

    public Action createRotateCameraByAction(Sprite sprite, ScriptSequenceAction sequence, Formula pitch, Formula yaw, Formula roll) {
        RotateCameraByAction rotateCameraByAction = (RotateCameraByAction) action(RotateCameraByAction.class);
        rotateCameraByAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        rotateCameraByAction.setPitch(pitch);
        rotateCameraByAction.setYaw(yaw);
        rotateCameraByAction.setRoll(roll);
        return rotateCameraByAction;
    }

    public Action createCameraSettingsAction(Sprite sprite, ScriptSequenceAction sequence, Formula fov, Formula intensity, Formula duration) {
        CameraSettingsAction cameraSettingsAction = (CameraSettingsAction) action(CameraSettingsAction.class);
        cameraSettingsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        cameraSettingsAction.setFov(fov);
        cameraSettingsAction.setShakeIntensity(intensity);
        cameraSettingsAction.setShakeDuration(duration);
        return cameraSettingsAction;
    }

    public ThreedCreateFixedConstraintAction createThreedCreateFixedConstraintAction(Sprite sprite, ScriptSequenceAction sequence, Formula id2, Formula a, Formula b) {
        ThreedCreateFixedConstraintAction threedCreateFixedConstraintAction = (ThreedCreateFixedConstraintAction) action(ThreedCreateFixedConstraintAction.class);
        threedCreateFixedConstraintAction.scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence);
        threedCreateFixedConstraintAction.constraintId = id2;
        threedCreateFixedConstraintAction.objA = a;
        threedCreateFixedConstraintAction.objB = b;
        return threedCreateFixedConstraintAction;
    }

    public Action createAttachRaySensorAction(Sprite sprite, ScriptSequenceAction sequence, Formula rayName, Formula objectId, Formula offX, Formula offY, Formula offZ, Formula dirX, Formula dirY, Formula dirZ, Formula dist) {
        AttachRaySensorAction attachRaySensorAction = (AttachRaySensorAction) action(AttachRaySensorAction.class);
        attachRaySensorAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        attachRaySensorAction.setRayName(rayName);
        attachRaySensorAction.setObjectId(objectId);
        attachRaySensorAction.setOffX(offX);
        attachRaySensorAction.setOffY(offY);
        attachRaySensorAction.setOffZ(offZ);
        attachRaySensorAction.setDirX(dirX);
        attachRaySensorAction.setDirY(dirY);
        attachRaySensorAction.setDirZ(dirZ);
        attachRaySensorAction.setDist(dist);
        return attachRaySensorAction;
    }

    public Action createBakeByPrefixAction(Sprite sprite, ScriptSequenceAction sequence, Formula prefix, Formula resultName) {
        BakeByPrefixAction bakeByPrefixAction = (BakeByPrefixAction) action(BakeByPrefixAction.class);
        bakeByPrefixAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        bakeByPrefixAction.setPrefix(prefix);
        bakeByPrefixAction.setResultName(resultName);
        return bakeByPrefixAction;
    }

    public Action createSetPostProcessingNewAction(Sprite sprite, ScriptSequenceAction sequence, int effectIndex, int paramIndex, Formula valueFormula) {
        SetPostProcessingNewAction setPostProcessingNewAction = new SetPostProcessingNewAction();
        setPostProcessingNewAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setPostProcessingNewAction.setEffectIndex(effectIndex);
        setPostProcessingNewAction.setParamIndex(paramIndex);
        setPostProcessingNewAction.setValueFormula(valueFormula);
        return setPostProcessingNewAction;
    }

    public Action createSetShadowQualityAction(Sprite sprite, ScriptSequenceAction sequence, Formula size, Formula resolution) {
        SetShadowQualityAction setShadowQualityAction = new SetShadowQualityAction();
        setShadowQualityAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setShadowQualityAction.setSizeFormula(size);
        setShadowQualityAction.setResolutionFormula(resolution);
        return setShadowQualityAction;
    }

    public Action createAddHingeAction(Sprite sprite, ScriptSequenceAction sequence, Formula id2, Formula objA, Formula objB, Formula pax, Formula pay, Formula paz, Formula aax, Formula aay, Formula aaz, Formula pbx, Formula pby, Formula pbz, Formula abx, Formula aby, Formula abz) {
        AddHingeAction addHingeAction = (AddHingeAction) action(AddHingeAction.class);
        addHingeAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        addHingeAction.setConstraintId(id2);
        addHingeAction.setObjectA(objA);
        addHingeAction.setObjectB(objB);
        addHingeAction.setPivotAX(pax);
        addHingeAction.setPivotAY(pay);
        addHingeAction.setPivotAZ(paz);
        addHingeAction.setAxisAX(aax);
        addHingeAction.setAxisAY(aay);
        addHingeAction.setAxisAZ(aaz);
        addHingeAction.setPivotBX(pbx);
        addHingeAction.setPivotBY(pby);
        addHingeAction.setPivotBZ(pbz);
        addHingeAction.setAxisBX(abx);
        addHingeAction.setAxisBY(aby);
        addHingeAction.setAxisBZ(abz);
        return addHingeAction;
    }

    public Action createSetHingeMotorAction(Sprite sprite, ScriptSequenceAction sequence, Formula id2, Formula targetAngle, Formula maxForce) {
        SetHingeMotorAction setHingeMotorAction = (SetHingeMotorAction) action(SetHingeMotorAction.class);
        setHingeMotorAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setHingeMotorAction.setConstraintId(id2);
        setHingeMotorAction.setTargetAngle(targetAngle);
        setHingeMotorAction.setMaxForce(maxForce);
        return setHingeMotorAction;
    }

    public Action createFast2DCreateAction(Sprite sprite, SequenceAction sequence, Formula entityId) {
        Fast2DCreateAction fast2DCreateAction = (Fast2DCreateAction) action(Fast2DCreateAction.class);
        fast2DCreateAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        fast2DCreateAction.setEntityId(entityId);
        return fast2DCreateAction;
    }

    public Action createFast2DSetGravityAction(Sprite sprite, SequenceAction sequence, Formula gravityX, Formula gravityY) {
        Fast2DSetGravityAction fast2DSetGravityAction = (Fast2DSetGravityAction) action(Fast2DSetGravityAction.class);
        fast2DSetGravityAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        fast2DSetGravityAction.setGravityX(gravityX);
        fast2DSetGravityAction.setGravityY(gravityY);
        return fast2DSetGravityAction;
    }

    public Action createFast2DSetPositionAction(Sprite sprite, SequenceAction sequence, Formula entityId, Formula posX, Formula posY) {
        Fast2DSetPositionAction fast2DSetPositionAction = (Fast2DSetPositionAction) action(Fast2DSetPositionAction.class);
        fast2DSetPositionAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        fast2DSetPositionAction.setEntityId(entityId);
        fast2DSetPositionAction.setPosX(posX);
        fast2DSetPositionAction.setPosY(posY);
        return fast2DSetPositionAction;
    }

    public Action createFast2DSetRotationAction(Sprite s, SequenceAction seq, Formula id2, Formula angle) {
        Fast2DSetRotationAction fast2DSetRotationAction = (Fast2DSetRotationAction) action(Fast2DSetRotationAction.class);
        fast2DSetRotationAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), s, seq));
        fast2DSetRotationAction.setEntityId(id2);
        fast2DSetRotationAction.setAngle(angle);
        return fast2DSetRotationAction;
    }

    public Action createFast2DSetScaleAction(Sprite s, SequenceAction seq, Formula id2, Formula sx, Formula sy) {
        Fast2DSetScaleAction fast2DSetScaleAction = (Fast2DSetScaleAction) action(Fast2DSetScaleAction.class);
        fast2DSetScaleAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), s, seq));
        fast2DSetScaleAction.setEntityId(id2);
        fast2DSetScaleAction.setSx(sx);
        fast2DSetScaleAction.setSy(sy);
        return fast2DSetScaleAction;
    }

    public Action createFast2DSetTextureAction(Sprite s, SequenceAction seq, Formula id2, Formula file) {
        Fast2DSetTextureAction fast2DSetTextureAction = (Fast2DSetTextureAction) action(Fast2DSetTextureAction.class);
        fast2DSetTextureAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), s, seq));
        fast2DSetTextureAction.setEntityId(id2);
        fast2DSetTextureAction.setFileName(file);
        return fast2DSetTextureAction;
    }

    public Action createFast2DSetZIndexAction(Sprite s, SequenceAction seq, Formula id2, Formula z) {
        Fast2DSetZIndexAction fast2DSetZIndexAction = (Fast2DSetZIndexAction) action(Fast2DSetZIndexAction.class);
        fast2DSetZIndexAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), s, seq));
        fast2DSetZIndexAction.setEntityId(id2);
        fast2DSetZIndexAction.setZIndex(z);
        return fast2DSetZIndexAction;
    }

    public Action createFast2DDeleteAction(Sprite s, SequenceAction seq, Formula id2) {
        Fast2DDeleteAction fast2DDeleteAction = (Fast2DDeleteAction) action(Fast2DDeleteAction.class);
        fast2DDeleteAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), s, seq));
        fast2DDeleteAction.setEntityId(id2);
        return fast2DDeleteAction;
    }

    public Action createFast2DSetColorAction(Sprite sprite, ScriptSequenceAction sequence, Formula formulaWithBrickField, Formula formulaWithBrickField1, Formula formulaWithBrickField2, Formula formulaWithBrickField3, Formula formulaWithBrickField4) {
        Fast2DSetColorAction fast2DSetColorAction = (Fast2DSetColorAction) action(Fast2DSetColorAction.class);
        fast2DSetColorAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        fast2DSetColorAction.setEntityId(formulaWithBrickField);
        fast2DSetColorAction.setR(formulaWithBrickField1);
        fast2DSetColorAction.setG(formulaWithBrickField2);
        fast2DSetColorAction.setB(formulaWithBrickField3);
        fast2DSetColorAction.setA(formulaWithBrickField4);
        return fast2DSetColorAction;
    }

    public Action createFast2DSetCameraAction(Sprite sprite, ScriptSequenceAction sequence, Formula formulaWithBrickField, Formula formulaWithBrickField1, Formula formulaWithBrickField2) {
        Fast2DSetCameraAction fast2DSetCameraAction = (Fast2DSetCameraAction) action(Fast2DSetCameraAction.class);
        fast2DSetCameraAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        fast2DSetCameraAction.setPosX(formulaWithBrickField);
        fast2DSetCameraAction.setPosY(formulaWithBrickField1);
        fast2DSetCameraAction.setZoom(formulaWithBrickField2);
        return fast2DSetCameraAction;
    }

    public Action createFast2DSetVelocityAction(Sprite s, SequenceAction seq, Formula id2, Formula vx, Formula vy) {
        Fast2DSetVelocityAction fast2DSetVelocityAction = (Fast2DSetVelocityAction) action(Fast2DSetVelocityAction.class);
        fast2DSetVelocityAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), s, seq));
        fast2DSetVelocityAction.setEntityId(id2);
        fast2DSetVelocityAction.setVx(vx);
        fast2DSetVelocityAction.setVy(vy);
        return fast2DSetVelocityAction;
    }

    public Action createFast2DSetAngularVelocityAction(Sprite s, SequenceAction seq, Formula id2, Formula v) {
        Fast2DSetAngularVelocityAction fast2DSetAngularVelocityAction = (Fast2DSetAngularVelocityAction) action(Fast2DSetAngularVelocityAction.class);
        fast2DSetAngularVelocityAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), s, seq));
        fast2DSetAngularVelocityAction.setEntityId(id2);
        fast2DSetAngularVelocityAction.setAngVel(v);
        return fast2DSetAngularVelocityAction;
    }

    public Action createFast2DMakePhysicsAction(Sprite s, SequenceAction seq, Formula id2, Formula dyn, Formula shape, Formula den, Formula fric, Formula bnc) {
        Fast2DMakePhysicsAction fast2DMakePhysicsAction = (Fast2DMakePhysicsAction) action(Fast2DMakePhysicsAction.class);
        fast2DMakePhysicsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), s, seq));
        fast2DMakePhysicsAction.setEntityId(id2);
        fast2DMakePhysicsAction.setDyn(dyn);
        fast2DMakePhysicsAction.setShape(shape);
        fast2DMakePhysicsAction.setDen(den);
        fast2DMakePhysicsAction.setFric(fric);
        fast2DMakePhysicsAction.setBnc(bnc);
        return fast2DMakePhysicsAction;
    }

    public Action createFast2DApplyForceAction(Sprite s, SequenceAction seq, Formula id2, Formula fx, Formula fy) {
        Fast2DApplyForceAction fast2DApplyForceAction = (Fast2DApplyForceAction) action(Fast2DApplyForceAction.class);
        fast2DApplyForceAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), s, seq));
        fast2DApplyForceAction.setEntityId(id2);
        fast2DApplyForceAction.setFx(fx);
        fast2DApplyForceAction.setFy(fy);
        return fast2DApplyForceAction;
    }

    public Action createFast2DApplyImpulseAction(Sprite s, SequenceAction seq, Formula id2, Formula ix, Formula iy) {
        Fast2DApplyImpulseAction fast2DApplyImpulseAction = (Fast2DApplyImpulseAction) action(Fast2DApplyImpulseAction.class);
        fast2DApplyImpulseAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), s, seq));
        fast2DApplyImpulseAction.setEntityId(id2);
        fast2DApplyImpulseAction.setFx(ix);
        fast2DApplyImpulseAction.setFy(iy);
        return fast2DApplyImpulseAction;
    }

    public Action createFast2DSetPhysicsVelocityAction(Sprite s, SequenceAction seq, Formula id2, Formula vx, Formula vy) {
        Fast2DSetPhysicsVelocityAction fast2DSetPhysicsVelocityAction = (Fast2DSetPhysicsVelocityAction) action(Fast2DSetPhysicsVelocityAction.class);
        fast2DSetPhysicsVelocityAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), s, seq));
        fast2DSetPhysicsVelocityAction.setEntityId(id2);
        fast2DSetPhysicsVelocityAction.setFx(vx);
        fast2DSetPhysicsVelocityAction.setFy(vy);
        return fast2DSetPhysicsVelocityAction;
    }

    public Action createFast2DSetCollisionFilterAction(Sprite s, SequenceAction seq, Formula id2, Formula sensor, Formula group) {
        Fast2DSetCollisionFilterAction fast2DSetCollisionFilterAction = (Fast2DSetCollisionFilterAction) action(Fast2DSetCollisionFilterAction.class);
        fast2DSetCollisionFilterAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), s, seq));
        fast2DSetCollisionFilterAction.setEntityId(id2);
        fast2DSetCollisionFilterAction.setSensor(sensor);
        fast2DSetCollisionFilterAction.setGroup(group);
        return fast2DSetCollisionFilterAction;
    }

    public Action createBufferAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula w, Formula h) {
        CreateBufferAction createBufferAction = (CreateBufferAction) action(CreateBufferAction.class);
        createBufferAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createBufferAction.setNameFormula(name2);
        createBufferAction.setWidthFormula(w);
        createBufferAction.setHeightFormula(h);
        return createBufferAction;
    }

    public Action removeBufferAction(Sprite sprite, SequenceAction sequence, Formula name2) {
        RemoveFromBufferAction removeFromBufferAction = (RemoveFromBufferAction) action(RemoveFromBufferAction.class);
        removeFromBufferAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        removeFromBufferAction.setNameFormula(name2);
        return removeFromBufferAction;
    }

    public Action createAddToBufferAction(Sprite sprite, SequenceAction sequence, Formula name2) {
        AddToBufferAction addToBufferAction = (AddToBufferAction) action(AddToBufferAction.class);
        addToBufferAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        addToBufferAction.setNameFormula(name2);
        return addToBufferAction;
    }

    public Action createSetBufferCameraAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula x, Formula y, Formula zoom, Formula rot) {
        SetBufferCameraAction setBufferCameraAction = (SetBufferCameraAction) action(SetBufferCameraAction.class);
        setBufferCameraAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setBufferCameraAction.setNameFormula(name2);
        setBufferCameraAction.setXFormula(x);
        setBufferCameraAction.setYFormula(y);
        setBufferCameraAction.setZoomFormula(zoom);
        setBufferCameraAction.setRotFormula(rot);
        return setBufferCameraAction;
    }

    public Action createSaveBufferAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula file) {
        SaveBufferAction saveBufferAction = (SaveBufferAction) action(SaveBufferAction.class);
        saveBufferAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        saveBufferAction.setNameFormula(name2);
        saveBufferAction.setFileFormula(file);
        return saveBufferAction;
    }

    public Action createSetBufferAutoUpdateAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula state) {
        SetBufferAutoUpdateAction setBufferAutoUpdateAction = (SetBufferAutoUpdateAction) action(SetBufferAutoUpdateAction.class);
        setBufferAutoUpdateAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setBufferAutoUpdateAction.setNameFormula(name2);
        setBufferAutoUpdateAction.setStateFormula(state);
        return setBufferAutoUpdateAction;
    }

    public Action createApplyBufferLookAction(Sprite sprite, SequenceAction sequence, Formula name2) {
        ApplyBufferLookAction applyBufferLookAction = (ApplyBufferLookAction) action(ApplyBufferLookAction.class);
        applyBufferLookAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        applyBufferLookAction.setNameFormula(name2);
        return applyBufferLookAction;
    }

    public Action createSetBufferOnlyAction(Sprite sprite, SequenceAction sequence, Formula state) {
        SetBufferOnlyAction setBufferOnlyAction = (SetBufferOnlyAction) action(SetBufferOnlyAction.class);
        setBufferOnlyAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setBufferOnlyAction.setStateFormula(state);
        return setBufferOnlyAction;
    }

    public Action createSetBufferModeAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula r2d, Formula r3d) {
        SetBufferModeAction setBufferModeAction = (SetBufferModeAction) action(SetBufferModeAction.class);
        setBufferModeAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setBufferModeAction.setNameFormula(name2);
        setBufferModeAction.setRender2DFormula(r2d);
        setBufferModeAction.setRender3DFormula(r3d);
        return setBufferModeAction;
    }

    public Action createSetBufferCamera3DAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula x, Formula y, Formula z, Formula yaw, Formula pitch, Formula roll, Formula fov) {
        SetBufferCamera3DAction setBufferCamera3DAction = (SetBufferCamera3DAction) action(SetBufferCamera3DAction.class);
        setBufferCamera3DAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setBufferCamera3DAction.setNameFormula(name2);
        setBufferCamera3DAction.setXForm(x);
        setBufferCamera3DAction.setYForm(y);
        setBufferCamera3DAction.setZForm(z);
        setBufferCamera3DAction.setYawForm(yaw);
        setBufferCamera3DAction.setPitchForm(pitch);
        setBufferCamera3DAction.setRollForm(roll);
        setBufferCamera3DAction.setFovForm(fov);
        return setBufferCamera3DAction;
    }

    public Action createVoxelBuildAction(Sprite sprite, ScriptSequenceAction sequence, Formula worldId, Formula texture, Formula width, Formula height) {
        VoxelBuildAction voxelBuildAction = (VoxelBuildAction) action(VoxelBuildAction.class);
        voxelBuildAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        voxelBuildAction.setWorldId(worldId);
        voxelBuildAction.setTexture(texture);
        voxelBuildAction.setAtlasW(width);
        voxelBuildAction.setAtlasH(height);
        return voxelBuildAction;
    }

    public Action createVoxelLoadStringAction(Sprite sprite, ScriptSequenceAction sequence, Formula id2, Formula data, Formula dx, Formula dy, Formula dz) {
        VoxelLoadStringAction voxelLoadStringAction = (VoxelLoadStringAction) action(VoxelLoadStringAction.class);
        voxelLoadStringAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        voxelLoadStringAction.setWorldId(id2);
        voxelLoadStringAction.setDataStr(data);
        voxelLoadStringAction.setDelimX(dx);
        voxelLoadStringAction.setDelimY(dy);
        voxelLoadStringAction.setDelimZ(dz);
        return voxelLoadStringAction;
    }

    public Action createVoxelDeleteAction(Sprite sprite, ScriptSequenceAction sequence, Formula worldId) {
        VoxelDeleteAction voxelDeleteAction = (VoxelDeleteAction) action(VoxelDeleteAction.class);
        voxelDeleteAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        voxelDeleteAction.setWorldId(worldId);
        return voxelDeleteAction;
    }

    public Action createVoxelSetTransparentAction(Sprite sprite, ScriptSequenceAction sequence, Formula blockId, Formula isTransparent) {
        VoxelSetTransparentAction voxelSetTransparentAction = (VoxelSetTransparentAction) action(VoxelSetTransparentAction.class);
        voxelSetTransparentAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        voxelSetTransparentAction.setBlockId(blockId);
        voxelSetTransparentAction.setTransparent(isTransparent);
        return voxelSetTransparentAction;
    }

    public Action createVoxelCreateWorldAction(Sprite sprite, ScriptSequenceAction sequence, Formula worldId, Formula sx, Formula sy, Formula sz, Formula wx, Formula wy, Formula wz) {
        VoxelCreateWorldAction voxelCreateWorldAction = (VoxelCreateWorldAction) action(VoxelCreateWorldAction.class);
        voxelCreateWorldAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        voxelCreateWorldAction.setWorldId(worldId);
        voxelCreateWorldAction.setSizeX(sx);
        voxelCreateWorldAction.setSizeY(sy);
        voxelCreateWorldAction.setSizeZ(sz);
        voxelCreateWorldAction.setWorldX(wx);
        voxelCreateWorldAction.setWorldY(wy);
        voxelCreateWorldAction.setWorldZ(wz);
        return voxelCreateWorldAction;
    }

    public Action createVoxelConfigAction(Sprite sprite, ScriptSequenceAction sequence, Formula id2, Formula shape, Formula tx, Formula ty) {
        VoxelConfigAction voxelConfigAction = (VoxelConfigAction) action(VoxelConfigAction.class);
        voxelConfigAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        voxelConfigAction.setBlockId(id2);
        voxelConfigAction.setShape(shape);
        voxelConfigAction.setAtlasX(tx);
        voxelConfigAction.setAtlasY(ty);
        return voxelConfigAction;
    }

    public Action createVoxelSetBlockAction(Sprite sprite, ScriptSequenceAction sequence, Formula id2, Formula x, Formula y, Formula z, Formula type, Formula data) {
        VoxelSetBlockAction voxelSetBlockAction = (VoxelSetBlockAction) action(VoxelSetBlockAction.class);
        voxelSetBlockAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        voxelSetBlockAction.setWorldId(id2);
        voxelSetBlockAction.setX(x);
        voxelSetBlockAction.setY(y);
        voxelSetBlockAction.setZ(z);
        voxelSetBlockAction.setType(type);
        voxelSetBlockAction.setData(data);
        return voxelSetBlockAction;
    }

    public Action createAttachToCameraAction(Sprite sprite, SequenceAction sequence, Formula objectName) {
        AttachToCameraAction attachToCameraAction = (AttachToCameraAction) action(AttachToCameraAction.class);
        attachToCameraAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        attachToCameraAction.setObjectName(objectName);
        return attachToCameraAction;
    }

    public Action createDetachFromCameraAction(Sprite sprite, SequenceAction sequence, Formula objectName) {
        DetachFromCameraAction detachFromCameraAction = (DetachFromCameraAction) action(DetachFromCameraAction.class);
        detachFromCameraAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        detachFromCameraAction.setObjectName(objectName);
        return detachFromCameraAction;
    }

    public Action createSetMaxPointLightsAction(Sprite sprite, SequenceAction sequence, Formula maxLights) {
        SetMaxPointLightsAction setMaxPointLightsAction = (SetMaxPointLightsAction) action(SetMaxPointLightsAction.class);
        setMaxPointLightsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setMaxPointLightsAction.setMaxLights(maxLights);
        return setMaxPointLightsAction;
    }

    public Action createPromoteLightAction(Sprite sprite, SequenceAction sequence, Formula lightId) {
        PromoteLightAction promoteLightAction = (PromoteLightAction) action(PromoteLightAction.class);
        promoteLightAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        promoteLightAction.setLightId(lightId);
        return promoteLightAction;
    }

    public Action createApplyShaderToImageAction(Sprite sprite, SequenceAction sequence, Formula filename, Formula vsh, Formula fsh) {
        ApplyShaderToImageAction applyShaderToImageAction = (ApplyShaderToImageAction) action(ApplyShaderToImageAction.class);
        applyShaderToImageAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        applyShaderToImageAction.setFilename(filename);
        applyShaderToImageAction.setVertexCode(vsh);
        applyShaderToImageAction.setFragmentCode(fsh);
        return applyShaderToImageAction;
    }

    public Action createCameraTrackingAction(Sprite sprite, SequenceAction sequence, Formula id2, int mode, Formula px, Formula py, Formula pz, Formula yaw, Formula pitch, Formula roll) {
        CameraTrackingAction cameraTrackingAction = (CameraTrackingAction) action(CameraTrackingAction.class);
        cameraTrackingAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        cameraTrackingAction.setObjectId(id2);
        cameraTrackingAction.setMode(mode);
        cameraTrackingAction.setPx(px);
        cameraTrackingAction.setPy(py);
        cameraTrackingAction.setPz(pz);
        cameraTrackingAction.setRy(yaw);
        cameraTrackingAction.setRp(pitch);
        cameraTrackingAction.setRr(roll);
        return cameraTrackingAction;
    }

    public Action createSetScreenShaderAction(Sprite sprite, SequenceAction sequence, Formula vertex, Formula fragment) {
        SetScreenShaderAction setScreenShaderAction = (SetScreenShaderAction) action(SetScreenShaderAction.class);
        setScreenShaderAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setScreenShaderAction.setVertex(vertex);
        setScreenShaderAction.setFragment(fragment);
        return setScreenShaderAction;
    }

    public Action createSet3DSoundMaxDistanceAction(Sprite sprite, SequenceAction sequence, Formula instanceName, Formula distance) {
        Set3DSoundMaxDistanceAction set3DSoundMaxDistanceAction = (Set3DSoundMaxDistanceAction) action(Set3DSoundMaxDistanceAction.class);
        set3DSoundMaxDistanceAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        set3DSoundMaxDistanceAction.setInstanceName(instanceName);
        set3DSoundMaxDistanceAction.setDistance(distance);
        return set3DSoundMaxDistanceAction;
    }

    public Action createSetEmissiveAction(Sprite sprite, SequenceAction sequence, Formula objectName, Formula colorR, Formula colorG, Formula colorB, Formula colorA, Formula intensity, Formula texturePath) {
        SetEmissiveAction setEmissiveAction = (SetEmissiveAction) action(SetEmissiveAction.class);
        setEmissiveAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setEmissiveAction.setObjectName(objectName);
        setEmissiveAction.setColorR(colorR);
        setEmissiveAction.setColorG(colorG);
        setEmissiveAction.setColorB(colorB);
        setEmissiveAction.setColorA(colorA);
        setEmissiveAction.setIntensity(intensity);
        setEmissiveAction.setTexturePath(texturePath);
        return setEmissiveAction;
    }

    public Action createSetFpsAction(Sprite sprite, SequenceAction sequence, Formula fps) {
        SetFpsAction setFpsAction = (SetFpsAction) action(SetFpsAction.class);
        setFpsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setFpsAction.setFps(fps);
        return setFpsAction;
    }

    public Action createSetRenderResolutionAction(Sprite sprite, SequenceAction sequence, Formula scale, Formula mode) {
        SetRenderResolutionAction setRenderResolutionAction = (SetRenderResolutionAction) action(SetRenderResolutionAction.class);
        setRenderResolutionAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setRenderResolutionAction.setScale(scale);
        setRenderResolutionAction.setAspectMode(mode);
        return setRenderResolutionAction;
    }

    public Action createSetObjectShaderAction(Sprite sprite, SequenceAction sequence, Formula objectId, Formula vertexCode, Formula fragmentCode) {
        SetObjectShaderAction setObjectShaderAction = (SetObjectShaderAction) action(SetObjectShaderAction.class);
        setObjectShaderAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setObjectShaderAction.setObjectIdFormula(objectId);
        setObjectShaderAction.setVertexFormula(vertexCode);
        setObjectShaderAction.setFragmentFormula(fragmentCode);
        return setObjectShaderAction;
    }

    public Action createSetObjectShaderUniformAction(Sprite sprite, SequenceAction sequence, Formula objectId, Formula name2, Formula val1, Formula val2, Formula val3) {
        SetObjectShaderUniformAction setObjectShaderUniformAction = (SetObjectShaderUniformAction) action(SetObjectShaderUniformAction.class);
        setObjectShaderUniformAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setObjectShaderUniformAction.setObjectIdFormula(objectId);
        setObjectShaderUniformAction.setNameFormula(name2);
        setObjectShaderUniformAction.setVal1Formula(val1);
        setObjectShaderUniformAction.setVal2Formula(val2);
        setObjectShaderUniformAction.setVal3Formula(val3);
        return setObjectShaderUniformAction;
    }

    public Action createRemoveObjectsByPrefixAction(Sprite sprite, SequenceAction sequence, Formula prefix) {
        RemoveObjectsByPrefixAction removeObjectsByPrefixAction = (RemoveObjectsByPrefixAction) action(RemoveObjectsByPrefixAction.class);
        removeObjectsByPrefixAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        removeObjectsByPrefixAction.setPrefixFormula(prefix);
        return removeObjectsByPrefixAction;
    }

    public Action createRunAsSpriteAction(Sprite sprite, SequenceAction sequence, Script script, Formula spriteName, List<Brick> nestedBricks) {
        RunAsSpriteAction runAsSpriteAction = new RunAsSpriteAction(script, nestedBricks);
        runAsSpriteAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        runAsSpriteAction.setSpriteName(spriteName);
        return runAsSpriteAction;
    }

    public Action createInstantAction(Sprite sprite, SequenceAction sequence, Action instantSequence) {
        InstantAction instantAction = (InstantAction) action(InstantAction.class);
        instantAction.setAction(instantSequence);
        return instantAction;
    }

    public Action createEasePropertyAction(Sprite sprite, SequenceAction sequence, int propertyIndex, int typeIndex, Formula duration, Formula start, Formula end) {
        EasePropertyAction easePropertyAction = (EasePropertyAction) action(EasePropertyAction.class);
        easePropertyAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        easePropertyAction.setPropertyIndex(propertyIndex);
        easePropertyAction.setTypeIndex(typeIndex);
        easePropertyAction.setDurationFormula(duration);
        easePropertyAction.setStartFormula(start);
        easePropertyAction.setEndFormula(end);
        return easePropertyAction;
    }

    public Action createSpawnThreadAction(Sprite sprite, SequenceAction sequence, Formula id2, List<Brick> threadBricks, Script script) {
        SpawnThreadAction spawnThreadAction = (SpawnThreadAction) action(SpawnThreadAction.class);
        spawnThreadAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        spawnThreadAction.setThreadIdFormula(id2);
        spawnThreadAction.setThreadBricks(threadBricks);
        spawnThreadAction.setScript(script);
        return spawnThreadAction;
    }

    public Action createWaitThreadAction(Sprite sprite, SequenceAction sequence, Formula id2) {
        WaitThreadAction waitThreadAction = (WaitThreadAction) action(WaitThreadAction.class);
        waitThreadAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        waitThreadAction.setThreadIdFormula(id2);
        return waitThreadAction;
    }

    public Action createStopThreadAction(Sprite sprite, SequenceAction sequence, Formula id2) {
        StopThreadAction stopThreadAction = (StopThreadAction) action(StopThreadAction.class);
        stopThreadAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        stopThreadAction.setThreadIdFormula(id2);
        return stopThreadAction;
    }

    public Action createAddTextToBufferAction(Sprite sprite, SequenceAction sequence, Formula buffer, Formula text) {
        AddTextToBufferAction addTextToBufferAction = (AddTextToBufferAction) action(AddTextToBufferAction.class);
        addTextToBufferAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        addTextToBufferAction.setBufferName(buffer);
        addTextToBufferAction.setTextName(text);
        return addTextToBufferAction;
    }

    public Action createSetBufferShaderAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula vsh, Formula fsh) {
        SetBufferShaderAction setBufferShaderAction = (SetBufferShaderAction) action(SetBufferShaderAction.class);
        setBufferShaderAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setBufferShaderAction.setBufferName(name2);
        setBufferShaderAction.setVertexCode(vsh);
        setBufferShaderAction.setFragmentCode(fsh);
        return setBufferShaderAction;
    }

    public Action createSetBufferShaderUniformAction(Sprite sprite, SequenceAction sequence, Formula bufName, Formula uniName, Formula v1, Formula v2, Formula v3, int count) {
        SetBufferShaderUniformAction setBufferShaderUniformAction = (SetBufferShaderUniformAction) action(SetBufferShaderUniformAction.class);
        setBufferShaderUniformAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setBufferShaderUniformAction.setBufferName(bufName);
        setBufferShaderUniformAction.setUniformName(uniName);
        setBufferShaderUniformAction.setVal1(v1);
        setBufferShaderUniformAction.setVal2(v2);
        setBufferShaderUniformAction.setVal3(v3);
        setBufferShaderUniformAction.setParamCount(count);
        return setBufferShaderUniformAction;
    }

    public Action createSetMainRenderLoopsAction(Sprite sprite, SequenceAction sequence, Formula r2d, Formula rf2d, Formula r3d) {
        SetMainRenderLoopsAction setMainRenderLoopsAction = (SetMainRenderLoopsAction) action(SetMainRenderLoopsAction.class);
        setMainRenderLoopsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setMainRenderLoopsAction.setRender2D(r2d);
        setMainRenderLoopsAction.setRenderFast2D(rf2d);
        setMainRenderLoopsAction.setRender3D(r3d);
        return setMainRenderLoopsAction;
    }

    public Action createSetBufferEffectsAction(Sprite sprite, SequenceAction sequence, Formula name2, Formula vfx, Formula mip) {
        SetBufferEffectsAction setBufferEffectsAction = (SetBufferEffectsAction) action(SetBufferEffectsAction.class);
        setBufferEffectsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setBufferEffectsAction.setBufferName(name2);
        setBufferEffectsAction.setUseVfx(vfx);
        setBufferEffectsAction.setUseMipmapping(mip);
        return setBufferEffectsAction;
    }

    public Action createZipProjectFilesAction(Sprite sprite, SequenceAction sequence, Formula filesToZip, Formula zipFileName) {
        ZipProjectFilesAction zipProjectFilesAction = (ZipProjectFilesAction) action(ZipProjectFilesAction.class);
        zipProjectFilesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        zipProjectFilesAction.setFilesToZip(filesToZip);
        zipProjectFilesAction.setZipFileName(zipFileName);
        return zipProjectFilesAction;
    }

    public Action createUnzipProjectFilesAction(Sprite sprite, SequenceAction sequence, Formula zipFileName) {
        UnzipProjectFilesAction unzipProjectFilesAction = (UnzipProjectFilesAction) action(UnzipProjectFilesAction.class);
        unzipProjectFilesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        unzipProjectFilesAction.setZipFileName(zipFileName);
        return unzipProjectFilesAction;
    }

    public Action createHttpCreateAction(Sprite sprite, SequenceAction sequence, Formula requestId, Formula method, Formula url) {
        HttpCreateAction httpCreateAction = (HttpCreateAction) action(HttpCreateAction.class);
        httpCreateAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        httpCreateAction.setRequestId(requestId);
        httpCreateAction.setMethod(method);
        httpCreateAction.setUrl(url);
        return httpCreateAction;
    }

    public Action createHttpConfigAction(Sprite sprite, SequenceAction sequence, Formula requestId, int configTypeSelection, Formula key, Formula value) {
        HttpConfigAction httpConfigAction = (HttpConfigAction) action(HttpConfigAction.class);
        httpConfigAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        httpConfigAction.setRequestId(requestId);
        httpConfigAction.setConfigTypeSelection(configTypeSelection);
        httpConfigAction.setKey(key);
        httpConfigAction.setValue(value);
        return httpConfigAction;
    }

    public Action createHttpBodyAction(Sprite sprite, SequenceAction sequence, Formula requestId, Formula bodyText, Formula contentType) {
        HttpBodyAction httpBodyAction = (HttpBodyAction) action(HttpBodyAction.class);
        httpBodyAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        httpBodyAction.setRequestId(requestId);
        httpBodyAction.setBodyText(bodyText);
        httpBodyAction.setContentType(contentType);
        return httpBodyAction;
    }

    public Action createHttpAttachFileAction(Sprite sprite, SequenceAction sequence, Formula requestId, Formula fileName, Formula parameterName, Formula mimeType) {
        HttpAttachFileAction httpAttachFileAction = (HttpAttachFileAction) action(HttpAttachFileAction.class);
        httpAttachFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        httpAttachFileAction.setRequestId(requestId);
        httpAttachFileAction.setFileName(fileName);
        httpAttachFileAction.setParameterName(parameterName);
        httpAttachFileAction.setMimeType(mimeType);
        return httpAttachFileAction;
    }

    public Action createHttpSendAction(Sprite sprite, SequenceAction sequence, Formula requestId) {
        HttpSendAction httpSendAction = (HttpSendAction) action(HttpSendAction.class);
        httpSendAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        httpSendAction.setRequestId(requestId);
        return httpSendAction;
    }

    public Action createHttpSaveFileAction(Sprite sprite, SequenceAction sequence, Formula requestId, Formula destinationFileName) {
        HttpSaveFileAction httpSaveFileAction = (HttpSaveFileAction) action(HttpSaveFileAction.class);
        httpSaveFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        httpSaveFileAction.setRequestId(requestId);
        httpSaveFileAction.setDestinationFileName(destinationFileName);
        return httpSaveFileAction;
    }

    public Action createBufferMaskAction(Sprite sprite, SequenceAction sequence, Formula bufferName, int modeSelection) {
        BufferMaskAction bufferMaskAction = (BufferMaskAction) action(BufferMaskAction.class);
        bufferMaskAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        bufferMaskAction.setBufferName(bufferName);
        bufferMaskAction.setModeSelection(modeSelection);
        return bufferMaskAction;
    }

    public Action createSetNativeParentAction(Sprite sprite, ScriptSequenceAction sequence, Formula viewId, Formula parentId) {
        SetNativeParentAction setNativeParentAction = (SetNativeParentAction) action(SetNativeParentAction.class);
        setNativeParentAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setNativeParentAction.setViewId(viewId);
        setNativeParentAction.setParentId(parentId);
        return setNativeParentAction;
    }

    public Action createCreateScrollViewAction(Sprite sprite, ScriptSequenceAction sequence, int scrollMode, int overScrollMode, Formula viewId, Formula bgColor, Formula showBars, Formula padding, Formula x, Formula y, Formula width, Formula height) {
        CreateScrollViewAction createScrollViewAction = (CreateScrollViewAction) action(CreateScrollViewAction.class);
        createScrollViewAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createScrollViewAction.setScrollMode(scrollMode);
        createScrollViewAction.setOverScrollMode(overScrollMode);
        createScrollViewAction.setViewId(viewId);
        createScrollViewAction.setBgColor(bgColor);
        createScrollViewAction.setShowBars(showBars);
        createScrollViewAction.setPadding(padding);
        createScrollViewAction.setX(x);
        createScrollViewAction.setY(y);
        createScrollViewAction.setWidth(width);
        createScrollViewAction.setHeight(height);
        return createScrollViewAction;
    }

    public Action createCreateTextLabelAction(Sprite sprite, ScriptSequenceAction sequence, int formatMode, int alignMode, int scrollMode, Formula viewId, Formula text, Formula colorHex, Formula fontSize, Formula bgColor, Formula fontPath, Formula cornerRadius, Formula lineSpacing, Formula x, Formula y, Formula width, Formula height) {
        CreateTextLabelAction createTextLabelAction = (CreateTextLabelAction) action(CreateTextLabelAction.class);
        createTextLabelAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createTextLabelAction.setFormatMode(formatMode);
        createTextLabelAction.setAlignMode(alignMode);
        createTextLabelAction.setScrollMode(scrollMode);
        createTextLabelAction.setViewId(viewId);
        createTextLabelAction.setText(text);
        createTextLabelAction.setColorHex(colorHex);
        createTextLabelAction.setFontSize(fontSize);
        createTextLabelAction.setBgColor(bgColor);
        createTextLabelAction.setFontPath(fontPath);
        createTextLabelAction.setCornerRadius(cornerRadius);
        createTextLabelAction.setLineSpacing(lineSpacing);
        createTextLabelAction.setX(x);
        createTextLabelAction.setY(y);
        createTextLabelAction.setWidth(width);
        createTextLabelAction.setHeight(height);
        return createTextLabelAction;
    }

    public Action createNativeViewConfigAction(Sprite sprite, ScriptSequenceAction sequence, int propertySelection, Formula viewId, Formula value) {
        NativeViewConfigAction nativeViewConfigAction = (NativeViewConfigAction) action(NativeViewConfigAction.class);
        nativeViewConfigAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        nativeViewConfigAction.setPropertySelection(propertySelection);
        nativeViewConfigAction.setViewId(viewId);
        nativeViewConfigAction.setValue(value);
        return nativeViewConfigAction;
    }

    public Action createNativeViewControlAction(Sprite sprite, ScriptSequenceAction sequence, int commandSelection, Formula viewId, Formula parameter) {
        NativeViewControlAction nativeViewControlAction = (NativeViewControlAction) action(NativeViewControlAction.class);
        nativeViewControlAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        nativeViewControlAction.setCommandSelection(commandSelection);
        nativeViewControlAction.setViewId(viewId);
        nativeViewControlAction.setParameter(parameter);
        return nativeViewControlAction;
    }

    public Action createNativeViewListenerAction(Sprite sprite, ScriptSequenceAction sequence, int eventSelection, Formula viewId, UserVariable userVariable) {
        NativeViewListenerAction nativeViewListenerAction = (NativeViewListenerAction) action(NativeViewListenerAction.class);
        nativeViewListenerAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        nativeViewListenerAction.setEventSelection(eventSelection);
        nativeViewListenerAction.setViewId(viewId);
        nativeViewListenerAction.setUserVariable(userVariable);
        return nativeViewListenerAction;
    }

    public Action createCreateButtonAction(Sprite sprite, ScriptSequenceAction sequence, Formula viewId, Formula text, Formula colorHex, Formula fontSize, Formula bgColor, Formula cornerRadius, Formula x, Formula y, Formula width, Formula height) {
        CreateButtonAction createButtonAction = (CreateButtonAction) action(CreateButtonAction.class);
        createButtonAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createButtonAction.setViewId(viewId);
        createButtonAction.setText(text);
        createButtonAction.setColorHex(colorHex);
        createButtonAction.setFontSize(fontSize);
        createButtonAction.setBgColor(bgColor);
        createButtonAction.setCornerRadius(cornerRadius);
        createButtonAction.setX(x);
        createButtonAction.setY(y);
        createButtonAction.setWidth(width);
        createButtonAction.setHeight(height);
        return createButtonAction;
    }

    public Action createCreateSwitchAction(Sprite sprite, ScriptSequenceAction sequence, Formula viewId, Formula text, Formula defaultState, Formula x, Formula y, Formula width, Formula height) {
        CreateSwitchAction createSwitchAction = (CreateSwitchAction) action(CreateSwitchAction.class);
        createSwitchAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createSwitchAction.setViewId(viewId);
        createSwitchAction.setText(text);
        createSwitchAction.setDefaultState(defaultState);
        createSwitchAction.setX(x);
        createSwitchAction.setY(y);
        createSwitchAction.setWidth(width);
        createSwitchAction.setHeight(height);
        return createSwitchAction;
    }

    public Action createCreateImageViewAction(Sprite sprite, ScriptSequenceAction sequence, int scaleSelection, Formula viewId, Formula source, Formula cornerRadius, Formula x, Formula y, Formula width, Formula height) {
        CreateImageViewAction createImageViewAction = (CreateImageViewAction) action(CreateImageViewAction.class);
        createImageViewAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createImageViewAction.setScaleSelection(scaleSelection);
        createImageViewAction.setViewId(viewId);
        createImageViewAction.setSource(source);
        createImageViewAction.setCornerRadius(cornerRadius);
        createImageViewAction.setX(x);
        createImageViewAction.setY(y);
        createImageViewAction.setWidth(width);
        createImageViewAction.setHeight(height);
        return createImageViewAction;
    }

    public Action createCreateSliderAction(Sprite sprite, ScriptSequenceAction sequence, Formula viewId, Formula minValue, Formula maxValue, Formula currentValue, Formula x, Formula y, Formula width, Formula height) {
        CreateSliderAction createSliderAction = (CreateSliderAction) action(CreateSliderAction.class);
        createSliderAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        createSliderAction.setViewId(viewId);
        createSliderAction.setMinValue(minValue);
        createSliderAction.setMaxValue(maxValue);
        createSliderAction.setCurrentValue(currentValue);
        createSliderAction.setX(x);
        createSliderAction.setY(y);
        createSliderAction.setWidth(width);
        createSliderAction.setHeight(height);
        return createSliderAction;
    }

    public Action createNativeViewAnimateAction(Sprite sprite, ScriptSequenceAction sequence, int propertySelection, int easingSelection, Formula viewId, Formula value, Formula durationMs) {
        NativeViewAnimateAction nativeViewAnimateAction = (NativeViewAnimateAction) action(NativeViewAnimateAction.class);
        nativeViewAnimateAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        nativeViewAnimateAction.setPropertySelection(propertySelection);
        nativeViewAnimateAction.setEasingSelection(easingSelection);
        nativeViewAnimateAction.setViewId(viewId);
        nativeViewAnimateAction.setValue(value);
        nativeViewAnimateAction.setDurationMs(durationMs);
        return nativeViewAnimateAction;
    }

    public Action createNativeViewBindSpriteAction(Sprite sprite, ScriptSequenceAction sequence, int alignMode, Formula viewId, Formula spriteName, Formula offsetX, Formula offsetY) {
        NativeViewBindSpriteAction nativeViewBindSpriteAction = (NativeViewBindSpriteAction) action(NativeViewBindSpriteAction.class);
        nativeViewBindSpriteAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        nativeViewBindSpriteAction.setAlignMode(alignMode);
        nativeViewBindSpriteAction.setViewId(viewId);
        nativeViewBindSpriteAction.setSpriteName(spriteName);
        nativeViewBindSpriteAction.setOffsetX(offsetX);
        nativeViewBindSpriteAction.setOffsetY(offsetY);
        return nativeViewBindSpriteAction;
    }

    public Action createShowNotificationAction(Sprite sprite, SequenceAction sequence, Formula notificationId, Formula channelName, Formula title, Formula text, int importanceSelection, Formula largeIconFile, boolean isOngoing) {
        ShowNotificationAction showNotificationAction = (ShowNotificationAction) action(ShowNotificationAction.class);
        showNotificationAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        showNotificationAction.setNotificationId(notificationId);
        showNotificationAction.setChannelName(channelName);
        showNotificationAction.setTitle(title);
        showNotificationAction.setText(text);
        showNotificationAction.setImportanceSelection(importanceSelection);
        showNotificationAction.setLargeIconFile(largeIconFile);
        showNotificationAction.setOngoing(isOngoing);
        return showNotificationAction;
    }

    public Action createBroadcastWithParamsAction(Sprite sprite, SequenceAction sequence, Formula signalName, Formula params) {
        BroadcastWithParamsAction broadcastWithParamsAction = (BroadcastWithParamsAction) action(BroadcastWithParamsAction.class);
        broadcastWithParamsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        broadcastWithParamsAction.setSignalName(signalName);
        broadcastWithParamsAction.setParams(params);
        return broadcastWithParamsAction;
    }

    public Action createSaveBroadcastParamsAction(Sprite sprite, SequenceAction sequence, String signalName, UserVariable userVariable) {
        SaveBroadcastParamsAction saveBroadcastParamsAction = (SaveBroadcastParamsAction) action(SaveBroadcastParamsAction.class);
        saveBroadcastParamsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        saveBroadcastParamsAction.setSignalName(signalName);
        saveBroadcastParamsAction.setUserVariable(userVariable);
        return saveBroadcastParamsAction;
    }

    public Action createNotificationCreateAction(Sprite sprite, SequenceAction sequence, Formula notifId, Formula channelName, Formula title, Formula text, Formula largeIconFile, int importanceSelection, boolean isOngoing) {
        NotificationCreateAction notificationCreateAction = (NotificationCreateAction) action(NotificationCreateAction.class);
        notificationCreateAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        notificationCreateAction.setNotifId(notifId);
        notificationCreateAction.setChannelName(channelName);
        notificationCreateAction.setTitle(title);
        notificationCreateAction.setText(text);
        notificationCreateAction.setLargeIconFile(largeIconFile);
        notificationCreateAction.setImportanceSelection(importanceSelection);
        notificationCreateAction.setOngoing(isOngoing);
        return notificationCreateAction;
    }

    public Action createNotificationShowAction(Sprite sprite, SequenceAction sequence, Formula notifId, Formula delaySeconds) {
        NotificationShowAction notificationShowAction = (NotificationShowAction) action(NotificationShowAction.class);
        notificationShowAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        notificationShowAction.setNotifId(notifId);
        notificationShowAction.setDelaySeconds(delaySeconds);
        return notificationShowAction;
    }

    public Action createEnableBackgroundModeAction(Sprite sprite, SequenceAction sequence, Formula title, Formula text) {
        EnableBackgroundModeAction enableBackgroundModeAction = (EnableBackgroundModeAction) action(EnableBackgroundModeAction.class);
        enableBackgroundModeAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        enableBackgroundModeAction.setTitle(title);
        enableBackgroundModeAction.setText(text);
        return enableBackgroundModeAction;
    }

    public Action createDisableBackgroundModeAction(Sprite sprite, SequenceAction sequence) {
        DisableBackgroundModeAction disableBackgroundModeAction = (DisableBackgroundModeAction) action(DisableBackgroundModeAction.class);
        disableBackgroundModeAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return disableBackgroundModeAction;
    }

    public Action createNotificationAddButtonAction(Sprite sprite, SequenceAction sequence, Formula notifId, Formula actionId, Formula text, Formula iconFile, Formula hint, int behavior, boolean hasInput, boolean autoClose) {
        NotificationAddButtonAction notificationAddButtonAction = (NotificationAddButtonAction) action(NotificationAddButtonAction.class);
        notificationAddButtonAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        notificationAddButtonAction.setNotifId(notifId);
        notificationAddButtonAction.setActionId(actionId);
        notificationAddButtonAction.setText(text);
        notificationAddButtonAction.setIconFile(iconFile);
        notificationAddButtonAction.setHint(hint);
        notificationAddButtonAction.setBehavior(behavior);
        notificationAddButtonAction.setHasInput(hasInput);
        notificationAddButtonAction.setAutoClose(autoClose);
        return notificationAddButtonAction;
    }

    public Action createNotificationCancelAction(Sprite sprite, SequenceAction sequence, Formula notifId) {
        NotificationCancelAction notificationCancelAction = (NotificationCancelAction) action(NotificationCancelAction.class);
        notificationCancelAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        notificationCancelAction.setNotifId(notifId);
        return notificationCancelAction;
    }

    public Action createConfigureLightAction(Sprite sprite, SequenceAction sequence, Formula lightId, int configTypeSelection, Formula value) {
        ConfigureLightAction configureLightAction = (ConfigureLightAction) action(ConfigureLightAction.class);
        configureLightAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        configureLightAction.setLightId(lightId);
        configureLightAction.setConfigTypeSelection(configTypeSelection);
        configureLightAction.setValue(value);
        return configureLightAction;
    }

    public Action createConfigureMaterialAction(Sprite sprite, SequenceAction sequence, Formula objectId, int configTypeSelection, Formula value) {
        ConfigureMaterialAction configureMaterialAction = (ConfigureMaterialAction) action(ConfigureMaterialAction.class);
        configureMaterialAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        configureMaterialAction.setObjectId(objectId);
        configureMaterialAction.setConfigTypeSelection(configTypeSelection);
        configureMaterialAction.setValue(value);
        return configureMaterialAction;
    }

    public Action createConfigureParticlesAction(Sprite sprite, SequenceAction sequence, Formula particleId, int configTypeSelection, Formula value) {
        ConfigureParticlesAction configureParticlesAction = (ConfigureParticlesAction) action(ConfigureParticlesAction.class);
        configureParticlesAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        configureParticlesAction.setParticleId(particleId);
        configureParticlesAction.setConfigTypeSelection(configTypeSelection);
        configureParticlesAction.setValue(value);
        return configureParticlesAction;
    }

    public Action createSetTextBufferOnlyAction(Sprite sprite, SequenceAction sequence, Formula textName, Formula stateFormula) {
        SetTextBufferOnlyAction setTextBufferOnlyAction = (SetTextBufferOnlyAction) action(SetTextBufferOnlyAction.class);
        setTextBufferOnlyAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setTextBufferOnlyAction.setTextName(textName);
        setTextBufferOnlyAction.setStateFormula(stateFormula);
        return setTextBufferOnlyAction;
    }

    public Action createStartBufferRecordingAction(Sprite sprite, SequenceAction sequence, Formula bufferName, Formula fileName, Formula fpsFormula, Formula bitrateFormula) {
        StartBufferRecordingAction startBufferRecordingAction = (StartBufferRecordingAction) action(StartBufferRecordingAction.class);
        startBufferRecordingAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        startBufferRecordingAction.setBufferName(bufferName);
        startBufferRecordingAction.setFileName(fileName);
        startBufferRecordingAction.setFpsFormula(fpsFormula);
        startBufferRecordingAction.setBitrateFormula(bitrateFormula);
        return startBufferRecordingAction;
    }

    public Action createStopBufferRecordingAction(Sprite sprite, SequenceAction sequence) {
        StopBufferRecordingAction stopBufferRecordingAction = (StopBufferRecordingAction) action(StopBufferRecordingAction.class);
        stopBufferRecordingAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return stopBufferRecordingAction;
    }

    public Action createCompileJavaToDexAction(Sprite sprite, SequenceAction sequence, Formula srcFolder, Formula destDexFile, UserVariable errorVariable) {
        CompileJavaToDexAction compileJavaToDexAction = (CompileJavaToDexAction) action(CompileJavaToDexAction.class);
        compileJavaToDexAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        compileJavaToDexAction.setSrcFolder(srcFolder);
        compileJavaToDexAction.setDestDexFile(destDexFile);
        compileJavaToDexAction.setErrorVariable(errorVariable);
        return compileJavaToDexAction;
    }

    public Action createLoadAndRunDexAction(Sprite sprite, SequenceAction sequence, Formula dexPath, Formula className, Formula methodName, Formula methodArg) {
        LoadAndRunDexAction loadAndRunDexAction = (LoadAndRunDexAction) action(LoadAndRunDexAction.class);
        loadAndRunDexAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        loadAndRunDexAction.setDexPath(dexPath);
        loadAndRunDexAction.setClassName(className);
        loadAndRunDexAction.setMethodName(methodName);
        loadAndRunDexAction.setMethodArg(methodArg);
        return loadAndRunDexAction;
    }

    public Action createOpenAppAction(Sprite sprite, SequenceAction sequence, Formula packageName) {
        OpenAppAction openAppAction = (OpenAppAction) action(OpenAppAction.class);
        openAppAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        openAppAction.setPackageName(packageName);
        return openAppAction;
    }

    public Action createDownloadDependencyAction(Sprite sprite, SequenceAction sequence, Formula libraryId, Formula repositories, UserVariable resultVariable, boolean recursive) {
        DownloadDependencyAction downloadDependencyAction = (DownloadDependencyAction) action(DownloadDependencyAction.class);
        downloadDependencyAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        downloadDependencyAction.setLibraryId(libraryId);
        downloadDependencyAction.setRepositories(repositories);
        downloadDependencyAction.setResultVariable(resultVariable);
        downloadDependencyAction.setRecursive(recursive);
        return downloadDependencyAction;
    }

    public Action createExport3dObjectToGlbAction(Sprite sprite, SequenceAction sequence, Formula objectId, Formula destFileName) {
        Export3dObjectToGlbAction export3dObjectToGlbAction = (Export3dObjectToGlbAction) action(Export3dObjectToGlbAction.class);
        export3dObjectToGlbAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        export3dObjectToGlbAction.setObjectId(objectId);
        export3dObjectToGlbAction.setDestFileName(destFileName);
        return export3dObjectToGlbAction;
    }

    public Action createMqttConnectAction(Sprite sprite, SequenceAction sequence, Formula clientId, Formula host, Formula port) {
        MqttConnectAction mqttConnectAction = (MqttConnectAction) action(MqttConnectAction.class);
        mqttConnectAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        mqttConnectAction.setClientId(clientId);
        mqttConnectAction.setHost(host);
        mqttConnectAction.setPort(port);
        return mqttConnectAction;
    }

    public Action createMqttJoinRoomAction(Sprite sprite, SequenceAction sequence, Formula clientId, Formula roomId, Formula salt) {
        MqttJoinRoomAction mqttJoinRoomAction = (MqttJoinRoomAction) action(MqttJoinRoomAction.class);
        mqttJoinRoomAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        mqttJoinRoomAction.setClientId(clientId);
        mqttJoinRoomAction.setRoomId(roomId);
        mqttJoinRoomAction.setSalt(salt);
        return mqttJoinRoomAction;
    }

    public Action createMqttPublishAction(Sprite sprite, SequenceAction sequence, Formula clientId, Formula roomId, Formula salt, Formula message) {
        MqttPublishAction mqttPublishAction = (MqttPublishAction) action(MqttPublishAction.class);
        mqttPublishAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        mqttPublishAction.setClientId(clientId);
        mqttPublishAction.setRoomId(roomId);
        mqttPublishAction.setSalt(salt);
        mqttPublishAction.setMessage(message);
        return mqttPublishAction;
    }

    public Action createMqttDisconnectAction(Sprite sprite, SequenceAction sequence, Formula clientId) {
        MqttDisconnectAction mqttDisconnectAction = (MqttDisconnectAction) action(MqttDisconnectAction.class);
        mqttDisconnectAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        mqttDisconnectAction.setClientId(clientId);
        return mqttDisconnectAction;
    }

    public Action createEase3DPropertyAction(Sprite sprite, SequenceAction sequence, Formula objectId, int propertyIndex, int typeIndex, Formula duration, Formula start, Formula end) {
        Ease3DPropertyAction ease3DPropertyAction = (Ease3DPropertyAction) action(Ease3DPropertyAction.class);
        ease3DPropertyAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        ease3DPropertyAction.setObjectId(objectId);
        ease3DPropertyAction.setPropertyIndex(propertyIndex);
        ease3DPropertyAction.setTypeIndex(typeIndex);
        ease3DPropertyAction.setDurationFormula(duration);
        ease3DPropertyAction.setStartFormula(start);
        ease3DPropertyAction.setEndFormula(end);
        return ease3DPropertyAction;
    }

    public Action createBase64ToFileAction(Sprite sprite, SequenceAction sequence, Formula base64String, Formula destinationFileName) {
        Base64ToFileAction base64ToFileAction = (Base64ToFileAction) action(Base64ToFileAction.class);
        base64ToFileAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        base64ToFileAction.setBase64String(base64String);
        base64ToFileAction.setDestinationFileName(destinationFileName);
        return base64ToFileAction;
    }

    public Action createShareAction(Sprite sprite, SequenceAction sequence, Formula contentFormula) {
        ShareAction shareAction = (ShareAction) action(ShareAction.class);
        shareAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        shareAction.setContentFormula(contentFormula);
        return shareAction;
    }

    public Action createSetCornerOffsetsAction(Sprite sprite, SequenceAction sequence, Formula tlx, Formula tly, Formula trx, Formula tryFormula, Formula brx, Formula bry, Formula blx, Formula bly) {
        SetCornerOffsetsAction setCornerOffsetsAction = (SetCornerOffsetsAction) action(SetCornerOffsetsAction.class);
        setCornerOffsetsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setCornerOffsetsAction.setTlx(tlx);
        setCornerOffsetsAction.setTly(tly);
        setCornerOffsetsAction.setTrx(trx);
        setCornerOffsetsAction.setTryFormula(tryFormula);
        setCornerOffsetsAction.setBrx(brx);
        setCornerOffsetsAction.setBry(bry);
        setCornerOffsetsAction.setBlx(blx);
        setCornerOffsetsAction.setBly(bly);
        return setCornerOffsetsAction;
    }

    public Action createPlayGifAction(Sprite sprite, SequenceAction sequence, Formula gifFileName) {
        PlayGifAction playGifAction = (PlayGifAction) action(PlayGifAction.class);
        playGifAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        playGifAction.setGifFileName(gifFileName);
        return playGifAction;
    }

    public Action createStopGifAction(Sprite sprite, SequenceAction sequence) {
        StopGifAction stopGifAction = (StopGifAction) action(StopGifAction.class);
        stopGifAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return stopGifAction;
    }

    public Action createPlaySpritesheetAction(Sprite sprite, SequenceAction sequence, Formula rows, Formula cols, Formula selectedRow, Formula framesCount, Formula speed) {
        PlaySpritesheetAction playSpritesheetAction = (PlaySpritesheetAction) action(PlaySpritesheetAction.class);
        playSpritesheetAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        playSpritesheetAction.setRows(rows);
        playSpritesheetAction.setCols(cols);
        playSpritesheetAction.setSelectedRow(selectedRow);
        playSpritesheetAction.setFramesCount(framesCount);
        playSpritesheetAction.setSpeed(speed);
        return playSpritesheetAction;
    }

    public Action createStopSpritesheetAction(Sprite sprite, SequenceAction sequence) {
        StopSpritesheetAction stopSpritesheetAction = (StopSpritesheetAction) action(StopSpritesheetAction.class);
        stopSpritesheetAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        return stopSpritesheetAction;
    }
}
