import { SELECT_CLIENT, UNSELECT_CLIENT } from 'store/actions/types';

const INITIAL_STATE = {
  id: '',
  username: '',
  email: '',
  password: '',
  defaultUser: '',
  role: '',
  firstName: '',
  lastName: '',
  birthday: '',
  address: '',
  joinedDate: '',
  lastSession: null,
  disabled: '',
  noOfDevices: null,
};

const selectClientReducer = (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case SELECT_CLIENT:
      return {
        ...state,
        ...action.payload,
      };
    case UNSELECT_CLIENT:
      return { ...state, ...INITIAL_STATE };
    default:
      return state;
  }
};

export default selectClientReducer;
