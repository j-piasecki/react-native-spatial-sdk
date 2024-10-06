import GrabbableNativeComponent from './specs/GrabbableNativeComponent';
import type { PropsWithChildren } from 'react';
import React from 'react';

export enum GrabbableType {
  Face = 'face',
  PivotY = 'pivot_y',
}

export interface GrabbableProps {
  enabled?: boolean;
  type?: GrabbableType;
}

export const Grabbable = React.forwardRef<
  any,
  PropsWithChildren<GrabbableProps>
>((props, ref) => {
  if (__DEV__) {
    try {
      React.Children.only(props.children);
    } catch (_error) {
      throw new Error('Grabbable must have exactly one child.');
    }
  }

  return <GrabbableNativeComponent {...props} ref={ref} />;
});
