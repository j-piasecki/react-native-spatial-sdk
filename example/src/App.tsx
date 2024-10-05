import { StyleSheet, View, Text } from 'react-native';
import { usePanel } from 'react-native-spatial-sdk';

function PanelView() {
  return (
    <View style={{ flex: 1, backgroundColor: 'red' }}>
      <Text>Panel</Text>
    </View>
  );
}

export default function App() {
  const Panel = usePanel(PanelView, { width: 1, height: 1 });

  return (
    <View style={styles.container}>
      <Panel />
    </View>
  );
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
