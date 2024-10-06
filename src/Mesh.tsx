import MeshNativeComponent from './specs/MeshNativeComponent';
import type { PositionChangeEvent, OrientationChangeEvent } from './types';
import type { PropsWithChildren } from 'react';
import React from 'react';
import type { NativeSyntheticEvent } from 'react-native';
import type { Position, Orientation } from 'react-native-spatial-sdk';

export interface MeshProps {
  mesh: string;
  position?: Position;
  orientation?: Orientation;
  positionRelativeToParent?: boolean;

  onPositionChange?: (event: NativeSyntheticEvent<PositionChangeEvent>) => void;
  onOrientationChange?: (
    event: NativeSyntheticEvent<OrientationChangeEvent>
  ) => void;
}

export const Mesh = React.forwardRef<any, PropsWithChildren<MeshProps>>(
  (props, ref) => {
    return <MeshNativeComponent {...props} ref={ref} />;
  }
);
