import BoxNativeComponent from './specs/BoxNativeComponent';
import type { PositionChangeEvent, OrientationChangeEvent } from './types';
import type { PropsWithChildren } from 'react';
import type { NativeSyntheticEvent } from 'react-native';
import type { Position, Orientation } from 'react-native-spatial-sdk';

export interface BoxProps {
  width: number;
  height: number;
  depth: number;
  position?: Position;
  orientation?: Orientation;
  positionRelativeToParent?: boolean;

  onPositionChange?: (event: NativeSyntheticEvent<PositionChangeEvent>) => void;
  onOrientationChange?: (
    event: NativeSyntheticEvent<OrientationChangeEvent>
  ) => void;
}

export function Box(props: PropsWithChildren<BoxProps>) {
  return <BoxNativeComponent {...props} />;
}
