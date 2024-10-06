import ScaleNativeComponent from './specs/ScaleNativeComponent';
import type { PropsWithChildren } from 'react';
import React from 'react';

export type ScaleValue = [number, number, number] | number;

export interface ScaleProps {
  scale: ScaleValue;
}

export const Scale = React.forwardRef<any, PropsWithChildren<ScaleProps>>(
  (props, ref) => {
    if (__DEV__) {
      try {
        React.Children.only(props.children);
      } catch (_error) {
        throw new Error('Grabbable must have exactly one child.');
      }
    }

    const scale =
      typeof props.scale === 'number'
        ? [props.scale, props.scale, props.scale]
        : props.scale;

    return (
      <ScaleNativeComponent scale={scale} ref={ref} children={props.children} />
    );
  }
);
