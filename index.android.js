/**
 * @providesModule
 */
import ReactNative from 'react-native';

var RNOmniture = ReactNative.NativeModules.omnitureImplementation

module.exports = {
  trackAction : (str, obj)  => {
    console.log("vivek",obj);
    RNOmniture.trackAction(str, obj)
  }
}
