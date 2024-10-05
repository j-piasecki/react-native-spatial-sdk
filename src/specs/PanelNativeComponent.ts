import type { ViewProps } from 'react-native';
import type {
  DirectEventHandler,
  Double,
  Int32,
} from 'react-native/Libraries/Types/CodegenTypes';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

type PositionChangeEvent = Readonly<{
  x: Double;
  y: Double;
  z: Double;
}>;

type OrientationChangeEvent = Readonly<{
  pitch: Double;
  roll: Double;
  yaw: Double;
}>;

export interface NativeProps extends ViewProps {
  onPositionChange?: DirectEventHandler<PositionChangeEvent>;
  onOrientationChange?: DirectEventHandler<OrientationChangeEvent>;

  panelId: Int32;
  position?: number[];
  orientation?: number[];
}

export default codegenNativeComponent<NativeProps>('RNSpatialPanelView');
