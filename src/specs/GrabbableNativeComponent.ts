import type { ViewProps } from 'react-native';
import type {
  DirectEventHandler,
  WithDefault,
} from 'react-native/Libraries/Types/CodegenTypes';
import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';

type Type = 'face' | 'pivot_y';

type GrabStartEvent = Readonly<{}>;
type GrabEndEvent = Readonly<{}>;

export interface NativeProps extends ViewProps {
  onStart?: DirectEventHandler<GrabStartEvent>;
  onEnd?: DirectEventHandler<GrabEndEvent>;

  enabled?: boolean;
  type?: WithDefault<Type, 'face'>;
}

export default codegenNativeComponent<NativeProps>('RNSpatialGrabbableView');
