import { SELECT_DEVICE, UNSELECT_DEVICE } from 'store/actions/types';

const INITIAL_STATE = {};

const selectedDeviceReducer = (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case SELECT_DEVICE:
      return { ...state, ...action.payload };
    case UNSELECT_DEVICE:
      return { ...INITIAL_STATE };
    default:
      return state;
  }
};

export default selectedDeviceReducer;
