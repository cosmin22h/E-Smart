import {
  SELECT_CLIENT,
  SELECT_DEVICE,
  SIGN_IN,
  SIGN_OUT,
  UNSELECT_CLIENT,
  UNSELECT_DEVICE,
  SELECT_NOTIFICATION,
  UNSELECT_NOTIFICATION,
} from './types';

export const signIn = token => {
  return {
    type: SIGN_IN,
    payload: token,
  };
};

export const signOut = () => {
  return {
    type: SIGN_OUT,
  };
};

export const selectClient = client => {
  return {
    type: SELECT_CLIENT,
    payload: client,
  };
};

export const unselectClient = () => {
  return {
    type: UNSELECT_CLIENT,
  };
};

export const selectDevice = device => {
  return {
    type: SELECT_DEVICE,
    payload: device,
  };
};

export const unselectDevice = () => {
  return {
    type: UNSELECT_DEVICE,
  };
};

export const selectNotification = notification => {
  return {
    type: SELECT_NOTIFICATION,
    payload: notification,
  };
};

export const unselectNotification = () => {
  return {
    type: UNSELECT_NOTIFICATION,
  };
};
