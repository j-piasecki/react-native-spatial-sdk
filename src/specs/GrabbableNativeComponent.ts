import type { ViewProps } from 'react-native';
import type { WithDefault } from 'react-native/Libraries/Types/CodegenTypes';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

type Type = 'face' | 'pivot_y';

export interface NativeProps extends ViewProps {
  enabled?: boolean;
  type?: WithDefault<Type, 'face'>;
}

export default codegenNativeComponent<NativeProps>('RNSpatialGrabbableView');
