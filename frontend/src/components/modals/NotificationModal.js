import React from 'react';

import { Modal, Box, Typography, Button } from '@mui/material';

import { WARNING, BACKGROUND } from 'theme/colors';

const styleBox = {
  display: 'flex',
  flexDirection: 'column',
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 550,
  backgroundColor: BACKGROUND,
  color: '#000',
  padding: '1rem',
  border: `1.5px solid ${WARNING.main}`,
  boxShadow: 24,
};

const NotificationModal = props => {
  const { title, bodyText, btnText, open, onClose } = props;

  return (
    <Modal open={open} onClose={onClose}>
      <Box style={styleBox}>
        <Typography variant="h6" component="h2" sx={{ mb: 2 }}>
          {title}
        </Typography>
        {bodyText}
        <Button variant="outlined" onClick={onClose} sx={{ mt: 2 }}>
          {btnText}
        </Button>
      </Box>
    </Modal>
  );
};

export default NotificationModal;
