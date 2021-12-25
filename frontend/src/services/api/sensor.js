import { deletePost, post, put, get } from 'services/http';

const BASE_ENDPOINT = '/sensors';

const endpoint = {
  getDetails: '/all/details',
  addSensor: '/add',
  updateSensor: '/update',
  removeSensor: '/remove',
};

export const getDetails = () => {
  return get(`${BASE_ENDPOINT}${endpoint.getDetails}`);
};

export const addSensor = (idDevice, sensor) => {
  return post(`${BASE_ENDPOINT}${endpoint.addSensor}/${idDevice}`, sensor);
};

export const updateSensor = sensor => {
  return put(`${BASE_ENDPOINT}${endpoint.updateSensor}`, sensor);
};

export const deleteSensor = id => {
  return deletePost(`${BASE_ENDPOINT}${endpoint.removeSensor}`, { id });
};
