import { combineReducers } from 'redux';

import authReducer from './authReducer';
import selectClientReducer from './selectClientReducer';
import selectedDeviceReducer from './selectDeviceReducer';
import selectNotificationReducer from './selectNotificationReducer';

export default combineReducers({
  auth: authReducer,
  selectedClient: selectClientReducer,
  selectedDevice: selectedDeviceReducer,
  selectedNotification: selectNotificationReducer,
});
