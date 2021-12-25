import { put, get } from 'services/http';

const BASE_ENDPOINT = '/logs';

const endpoint = {
  all: '/all',
  read: '/read',
  noUnread: '/unread/number',
};

export const getNotifications = (idClient, noOfPage, itemsPerPage) => {
  return get(`${BASE_ENDPOINT}${endpoint.all}`, {
    idClient,
    noOfPage,
    itemsPerPage,
  });
};

export const getUnreadNo = idClient => {
  return get(`${BASE_ENDPOINT}${endpoint.noUnread}`, { idClient });
};

export const readNotification = id => {
  return put(`${BASE_ENDPOINT}${endpoint.read}/${id}`);
};
