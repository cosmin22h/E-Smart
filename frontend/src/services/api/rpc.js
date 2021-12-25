import { post } from 'services/http';

const BASE_ENDPOINT = '/rpc';
const JSON_RPC_VERSION = '2.0';

const methods = {
  getHistory: {
    id: 1,
    method: 'getHistoricalConsumption',
  },
  getBaseline: {
    id: 2,
    method: 'getBaseline',
  },
  getBestProgram: {
    id: 3,
    method: 'getBestProgram',
  },
};

export const getHistoricalConsumption = (idClient, d) => {
  return post(BASE_ENDPOINT, {
    id: methods.getHistory.id,
    jsonrpc: JSON_RPC_VERSION,
    method: methods.getHistory.method,
    params: {
      idClient,
      d,
    },
  });
};

export const getBaseline = idClient => {
  return post(BASE_ENDPOINT, {
    id: methods.getBaseline.id,
    jsonrpc: JSON_RPC_VERSION,
    method: methods.getBaseline.method,
    params: {
      idClient,
    },
  });
};

export const getBestProgramToStartDevice = (idClient, idDevice, program) => {
  return post(BASE_ENDPOINT, {
    id: methods.getBestProgram.id,
    jsonrpc: JSON_RPC_VERSION,
    method: methods.getBestProgram.method,
    params: {
      idClient,
      idDevice,
      program,
    },
  });
};
