import { useEffect, useState } from 'react';
import { AppRegistry } from 'react-native';
import NativePanelModule from './specs/NativeSpatialPanelModule';
import type { PanelConfig } from './specs/NativeSpatialPanelModule';
import PanelNativeComponent from './specs/PanelNativeComponent';

const ENTRY_POINT_PREFIX = '_SpatialEntryPoint_';
let nextEntryPointId = 0;

export function usePanel(content: React.ComponentType, config: PanelConfig) {
  const [panelId] = useState(() => {
    const entryPointName = ENTRY_POINT_PREFIX + nextEntryPointId++;
    const id = NativePanelModule.registerPanel(entryPointName, config);

    AppRegistry.registerComponent(entryPointName, () => content);

    return id;
  });

  useEffect(() => {
    return () => {
      NativePanelModule.unregisterPanel(panelId);
    };
  }, [panelId]);

  return () => {
    return (
      <PanelNativeComponent
        panelId={panelId}
        style={{ width: 100, height: 100, backgroundColor: 'red' }}
      />
    );
  };
}
