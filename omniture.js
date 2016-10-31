/**
 * @providesModule
 */

var RNOmniture = require('react-native').NativeModules.omnitureImplementation

module.exports = {
  trackAction : function (str, obj) {
    return RNOmniture.trackAction(str, obj)
  }
}
