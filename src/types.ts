export type Position = [x: number, y: number, z: number];
export type Orientation = [pitch: number, yaw: number, roll: number];

export type PositionChangeEvent = {
  x: number;
  y: number;
  z: number;
};

export type OrientationChangeEvent = {
  pitch: number;
  yaw: number;
  roll: number;
};
