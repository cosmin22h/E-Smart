import {
  SELECT_NOTIFICATION,
  UNSELECT_NOTIFICATION,
} from 'store/actions/types';

const INITIAL_STATE = {};

const selectNotificationReducer = (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case SELECT_NOTIFICATION:
      return { ...state, ...action.payload };
    case UNSELECT_NOTIFICATION:
      return { ...INITIAL_STATE };
    default:
      return state;
  }
};

export default selectNotificationReducer;
