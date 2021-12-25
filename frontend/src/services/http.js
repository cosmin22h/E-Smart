import axios from './axios';

export const get = (endpoint, params, headers) => {
  return axios.get(endpoint, { params, headers });
};

export const post = (endpoint, body, headers) => {
  return axios.post(endpoint, body, { headers });
};

export const put = (endpoint, body, headers) => {
  return axios.put(endpoint, body, headers);
};

export const deletePost = (endpoint, params, headers) => {
  return axios.delete(endpoint, { params, headers });
};
