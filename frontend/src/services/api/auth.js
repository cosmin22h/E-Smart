import { post } from 'services/http';

const BASE_ENDPOINT = '/auth';

const endpoint = {
  login: '/login',
  logout: '/logout',
  register: '/register',
};

export const login = user => {
  return post(`${BASE_ENDPOINT}${endpoint.login}`, null, user);
};

export const logout = user => {
  return post(`${BASE_ENDPOINT}${endpoint.logout}`, null, user);
};

export const register = user => {
  return post(`${BASE_ENDPOINT}${endpoint.register}`, user);
};
