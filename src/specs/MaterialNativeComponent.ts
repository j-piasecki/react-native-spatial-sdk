import type { ViewProps, ColorValue } from 'react-native';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

export interface NativeProps extends ViewProps {
  color?: ColorValue;
}

export default codegenNativeComponent<NativeProps>('RNSpatialMaterialView');
