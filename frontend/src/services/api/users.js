import { deletePost, get, put } from 'services/http';

const BASE_ENDPOINT = '/users';

const endpoint = {
  getUsers: '',
  deleteUser: '/remove',
  updateAdmin: '/update',
  getClients: '/clients',
  getAdmin: '/admin',
  getClient: '/client',
  updateClient: '/client/update',
  changePassword: '/change-password',
};

export const getUsers = (noOfPage, itemsPerPage) => {
  return get(`${BASE_ENDPOINT}${endpoint.getUsers}`, {
    noOfPage,
    itemsPerPage,
  });
};

export const getClients = (noOfPage, itemsPerPage) => {
  return get(`${BASE_ENDPOINT}${endpoint.getClients}`, {
    noOfPage,
    itemsPerPage,
  });
};

export const getAdmin = id => {
  return get(`${BASE_ENDPOINT}${endpoint.getAdmin}`, { id });
};

export const getClient = id => {
  return get(`${BASE_ENDPOINT}${endpoint.getClient}`, { id });
};

export const updateAdmin = admin => {
  return put(`${BASE_ENDPOINT}${endpoint.updateAdmin}`, admin);
};

export const updateClient = client => {
  return put(`${BASE_ENDPOINT}${endpoint.updateClient}`, client);
};

export const changePassword = changePasswordBody => {
  return put(`${BASE_ENDPOINT}${endpoint.changePassword}`, changePasswordBody);
};

export const deleteUser = id => {
  return deletePost(`${BASE_ENDPOINT}${endpoint.deleteUser}/${id}`);
};
