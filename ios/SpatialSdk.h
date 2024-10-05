
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNSpatialSdkSpec.h"

@interface SpatialSdk : NSObject <NativeSpatialSdkSpec>
#else
#import <React/RCTBridgeModule.h>

@interface SpatialSdk : NSObject <RCTBridgeModule>
#endif

@end
