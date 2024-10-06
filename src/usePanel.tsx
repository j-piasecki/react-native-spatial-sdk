import type { PanelConfig } from './specs/NativeSpatialPanelModule';
import NativePanelModule from './specs/NativeSpatialPanelModule';
import PanelNativeComponent from './specs/PanelNativeComponent';
import type {
  Position,
  Orientation,
  OrientationChangeEvent,
  PositionChangeEvent,
} from './types';
import React, { useMemo, useState, type PropsWithChildren } from 'react';
import {
  AppRegistry,
  StyleSheet,
  type NativeSyntheticEvent,
} from 'react-native';

const ENTRY_POINT_PREFIX = '_SpatialEntryPoint_';
let nextEntryPointId = 0;

interface PanelProps {
  position?: Position;
  orientation?: Orientation;

  positionRelativeToParent?: boolean;

  onPositionChange?: (event: NativeSyntheticEvent<PositionChangeEvent>) => void;
  onOrientationChange?: (
    event: NativeSyntheticEvent<OrientationChangeEvent>
  ) => void;
}

export function usePanel(content: React.ComponentType, config: PanelConfig) {
  const [panelId] = useState(() => {
    const entryPointName = ENTRY_POINT_PREFIX + nextEntryPointId++;
    // cleaned up on the native side
    const id = NativePanelModule.registerPanel(entryPointName, config);

    AppRegistry.registerComponent(entryPointName, () => content);

    return id;
  });

  const panelComponent = useMemo(() => {
    return React.forwardRef<any, PropsWithChildren<PanelProps>>(
      (props, ref) => {
        return (
          <PanelNativeComponent
            ref={ref}
            panelId={panelId}
            style={styles.panel}
            position={props.position}
            orientation={props.orientation}
            positionRelativeToParent={props.positionRelativeToParent}
            onPositionChange={props.onPositionChange}
            onOrientationChange={props.onOrientationChange}
            children={props.children}
          />
        );
      }
    );
  }, [panelId]);

  return panelComponent;
}

const styles = StyleSheet.create({
  panel: {
    position: 'absolute',
    width: 0,
    height: 0,
  },
});
