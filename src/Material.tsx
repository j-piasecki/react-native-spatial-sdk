import MaterialNativeComponent from './specs/MaterialNativeComponent';
import type { PropsWithChildren } from 'react';
import React from 'react';
import type { ColorValue } from 'react-native';

export interface MaterialProps {
  color?: ColorValue;
}

export const Material = React.forwardRef<any, PropsWithChildren<MaterialProps>>(
  (props, ref) => {
    if (__DEV__) {
      try {
        React.Children.only(props.children);
      } catch (_error) {
        throw new Error('Grabbable must have exactly one child.');
      }
    }

    return <MaterialNativeComponent {...props} ref={ref} />;
  }
);
