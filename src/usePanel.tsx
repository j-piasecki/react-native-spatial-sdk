import type { PanelConfig } from './specs/NativeSpatialPanelModule';
import NativePanelModule from './specs/NativeSpatialPanelModule';
import PanelNativeComponent from './specs/PanelNativeComponent';
import { useCallback, useState } from 'react';
import {
  AppRegistry,
  StyleSheet,
  type NativeSyntheticEvent,
} from 'react-native';

const ENTRY_POINT_PREFIX = '_SpatialEntryPoint_';
let nextEntryPointId = 0;

export type Position = [x: number, y: number, z: number];
export type Orientation = [pitch: number, yaw: number, roll: number];

type PositionChangeEvent = {
  x: number;
  y: number;
  z: number;
};

type OrientationChangeEvent = {
  pitch: number;
  yaw: number;
  roll: number;
};

interface PanelProps {
  position?: Position;
  orientation?: Orientation;

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

  const panelComponent = useCallback(
    (props: PanelProps) => {
      return (
        <PanelNativeComponent
          panelId={panelId}
          style={styles.panel}
          position={props.position}
          orientation={props.orientation}
          onPositionChange={props.onPositionChange}
          onOrientationChange={props.onOrientationChange}
        />
      );
    },
    [panelId]
  );

  return panelComponent;
}

const styles = StyleSheet.create({
  panel: {
    position: 'absolute',
    width: 0,
    height: 0,
  },
});
