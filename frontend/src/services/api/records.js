import { get } from 'services/http';

const BASE_ENDPOINT = '/records';

export const getReportRecords = (idClient, dateReport) => {
  return get(BASE_ENDPOINT, { idClient, dateReport });
};
