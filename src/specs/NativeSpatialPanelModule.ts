import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';
import type { Int32 } from 'react-native/Libraries/Types/CodegenTypes';

export interface PanelConfig {
  width: number;
  height: number;
}

export interface Spec extends TurboModule {
  registerPanel(entryPoint: string, config: PanelConfig): Int32;
  unregisterPanel(panelId: Int32): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('RNSpatialPanelModule');
