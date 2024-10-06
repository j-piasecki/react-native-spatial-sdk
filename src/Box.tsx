import BoxNativeComponent from './specs/BoxNativeComponent';
import type { PositionChangeEvent, OrientationChangeEvent } from './types';
import type { PropsWithChildren } from 'react';
import React from 'react';
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

export const Box = React.forwardRef<any, PropsWithChildren<BoxProps>>(
  (props, ref) => {
    return <BoxNativeComponent {...props} ref={ref} />;
  }
);
