import type { ViewProps } from 'react-native';
import type { Int32 } from 'react-native/Libraries/Types/CodegenTypes';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

export interface NativeProps extends ViewProps {
  panelId: Int32;
  position?: number[];
  orientation?: number[];
}

export default codegenNativeComponent<NativeProps>('RNSpatialPanelView');
