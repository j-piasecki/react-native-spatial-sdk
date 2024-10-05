/* eslint-disable react-native/no-inline-styles */
import React, { useState } from 'react';
import { StyleSheet, View, Text, Button } from 'react-native';
import { Grabbable, GrabbableType, usePanel } from 'react-native-spatial-sdk';

import type { Position, Orientation } from 'react-native-spatial-sdk';

function PanelView() {
  return (
    <View
      style={{ flex: 1, backgroundColor: 'red', borderRadius: 80, padding: 16 }}
    >
      <Text>Panel</Text>
    </View>
  );
}

function Content() {
  const Panel = usePanel(PanelView, { width: 1, height: 1 });
  const [anchored, setAnchored] = useState(false);
  const [position, _setPosition] = useState<Position>([2, 1.3, 2]);
  const [orientation, setOrientation] = useState<Orientation>([0, 20, 0]);

  return (
    <View style={styles.container}>
      <Button title="Toggle Anchored" onPress={() => setAnchored(!anchored)} />
      <Button
        title="Increase roll"
        onPress={() =>
          setOrientation([orientation[0], orientation[1], orientation[2] + 0.1])
        }
      />
      <Button
        title="Decrease roll"
        onPress={() =>
          setOrientation([orientation[0], orientation[1], orientation[2] - 0.1])
        }
      />
      <Grabbable enabled={!anchored} type={GrabbableType.PivotY}>
        <Panel position={position} orientation={orientation} />
      </Grabbable>
    </View>
  );
}

const STRICT_MODE = false;

export default function App() {
  if (STRICT_MODE) {
    return (
      <React.StrictMode>
        <Content />
      </React.StrictMode>
    );
  } else {
    return <Content />;
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
