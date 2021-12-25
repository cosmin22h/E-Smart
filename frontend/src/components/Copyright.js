import React from 'react';

import { Typography, Link } from '@mui/material';

const Copyright = props => {
  return (
    <Typography
      variant="body2"
      color="text.secondary"
      align="center"
      {...props}
    >
      {'Copyright © '}
      <Link color="inherit" href={`${process.env.REACT_APP_BASE_URL}`}>
        {process.env.REACT_APP_NAME}
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
};

export default Copyright;
