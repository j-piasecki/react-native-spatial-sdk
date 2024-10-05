import { useCallback, useState } from 'react';
import { AppRegistry, StyleSheet } from 'react-native';
import NativePanelModule from './specs/NativeSpatialPanelModule';
import type { PanelConfig } from './specs/NativeSpatialPanelModule';
import PanelNativeComponent from './specs/PanelNativeComponent';

const ENTRY_POINT_PREFIX = '_SpatialEntryPoint_';
let nextEntryPointId = 0;

export type Position = [x: number, y: number, z: number];
export type Orientation = [pitch: number, yaw: number, roll: number];

interface PanelProps {
  anchored?: boolean;
  position?: Position;
  orientation?: Orientation;
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
          anchored={props.anchored}
          position={props.position}
          orientation={props.orientation}
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
