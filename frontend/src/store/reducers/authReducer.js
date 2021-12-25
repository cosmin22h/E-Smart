import { SIGN_IN, SIGN_OUT } from 'store/actions/types';

const INITIAL_STATE = {
  role: null,
  id: null,
  idSession: null,
};

const authReducer = (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case SIGN_IN:
      return {
        ...state,
        ...action.payload,
      };
    case SIGN_OUT:
      return { ...state, ...INITIAL_STATE };
    default:
      return state;
  }
};

export default authReducer;
