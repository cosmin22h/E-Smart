import React from 'react';

import { Alert, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

const ClosingAlert = props => {
  const { severity, message, onClose } = props;

  return (
    <Alert
      severity={severity}
      variant="filled"
      action={
        <IconButton
          aria-label="close"
          color="inherit"
          size="small"
          onClick={onClose}
        >
          <CloseIcon fontSize="inherit" />
        </IconButton>
      }
      style={{ width: '100%', marginTop: '1rem' }}
    >
      {message}
    </Alert>
  );
};

export default ClosingAlert;
