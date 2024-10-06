/* eslint-disable react-native/no-inline-styles */
import React, { useState } from 'react';
import { Button, StyleSheet, Text, View } from 'react-native';
import {
  Box,
  Grabbable,
  GrabbableType,
  usePanel,
} from 'react-native-spatial-sdk';
import type { BoxProps, Orientation, Position } from 'react-native-spatial-sdk';

function PanelView() {
  return (
    <View
      style={{ flex: 1, backgroundColor: 'red', borderRadius: 24, padding: 16 }}
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
  const [boxesRelative, setBoxesRelative] = useState(false);
  const [boxes, setBoxes] = useState<BoxProps[]>([]);

  const positionUpdateTimeoutRef = React.useRef<ReturnType<
    typeof setTimeout
  > | null>(null);
  const orientationUpdateTimeoutRef = React.useRef<ReturnType<
    typeof setTimeout
  > | null>(null);

  return (
    <View style={styles.container}>
      <Button title="Toggle Anchored" onPress={() => setAnchored(!anchored)} />
      <Button
        title="Increase roll"
        onPress={() =>
          setOrientation([orientation[0], orientation[1], orientation[2] + 1])
        }
      />
      <Button
        title="Decrease roll"
        onPress={() =>
          setOrientation([orientation[0], orientation[1], orientation[2] - 1])
        }
      />

      <Button
        title="Spawn box"
        onPress={() =>
          setBoxes([
            ...boxes,
            {
              width: 0.2 + Math.random() * 0.6,
              height: 0.2 + Math.random() * 0.6,
              depth: 0.2 + Math.random() * 0.6,
            },
          ])
        }
      />

      <Button
        title="Toggle boxes relativity"
        onPress={() => setBoxesRelative(!boxesRelative)}
      />

      <Grabbable enabled={!anchored} type={GrabbableType.Face}>
        <Panel
          position={position}
          orientation={orientation}
          onPositionChange={(event) => {
            clearTimeout(positionUpdateTimeoutRef.current!);
            const data = event.nativeEvent;
            positionUpdateTimeoutRef.current = setTimeout(() => {
              _setPosition([data.x, data.y, data.z]);
            }, 50);
          }}
          onOrientationChange={(event) => {
            clearTimeout(orientationUpdateTimeoutRef.current!);
            const data = event.nativeEvent;
            orientationUpdateTimeoutRef.current = setTimeout(() => {
              setOrientation([data.pitch, data.yaw, data.roll]);
            }, 50);
          }}
        >
          {boxes.map((box, index) => (
            <Grabbable key={index} type={GrabbableType.PivotY}>
              <Box
                {...box}
                positionRelativeToParent={boxesRelative}
                onPositionChange={(e) => {
                  console.log('box position change', e.nativeEvent);
                }}
              />
            </Grabbable>
          ))}
        </Panel>
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
