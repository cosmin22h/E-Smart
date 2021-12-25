import axios from 'axios';

export default axios.create({
  baseURL: process.env.REACT_APP_API_URL,
  headers: {
    post: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Headers':
        'Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With',
    },
  },
});
