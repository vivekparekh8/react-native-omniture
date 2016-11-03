/**
 * @providesModule
 */
import ReactNative from 'react-native';

var RNOmniture = ReactNative.NativeModules.reactnativeomnitureapi

module.exports = {
  trackAction : (str, obj)  => {
    RNOmniture.trackAction(str, obj)
  }
}
