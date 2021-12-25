import { deletePost, get, post, put } from 'services/http';

const BASE_ENDPOINT = '/devices';

const endpoint = {
  clientDevices: '/client',
  device: '/device',
  addDevice: '/add',
  updateDevice: '/update',
  removeDevice: '/remove',
};

export const getDevices = (noOfPage, itemsPerPage) => {
  return get(BASE_ENDPOINT, { noOfPage, itemsPerPage });
};

export const getDevicesForClient = (idClient, noOfPage, itemsPerPage) => {
  return get(`${BASE_ENDPOINT}${endpoint.clientDevices}`, {
    idClient,
    noOfPage,
    itemsPerPage,
  });
};

export const getDevice = id => {
  return get(`${BASE_ENDPOINT}${endpoint.device}`, { id });
};

export const addDevice = newDevice => {
  return post(`${BASE_ENDPOINT}${endpoint.addDevice}`, newDevice);
};

export const updateDevice = device => {
  return put(`${BASE_ENDPOINT}${endpoint.updateDevice}`, device);
};

export const deleteDevice = id => {
  return deletePost(`${BASE_ENDPOINT}${endpoint.removeDevice}`, { id });
};
