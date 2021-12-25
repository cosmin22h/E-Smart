import React from 'react';

import { Modal, Box, Typography, Button } from '@mui/material';

import { PRIMARY } from 'theme/colors';

const styleBox = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 450,
  backgroundColor: PRIMARY.light,
  color: '#fff',
  padding: '2rem',
  border: '2px solid #fff',
  boxShadow: 24,
};

const styleButtonsGroup = {
  marginTop: '1rem',
};

const ConfirmationModal = props => {
  const { title, bodyText, btnTextConfirm, open, onConfirm, onClose } = props;

  return (
    <Modal open={open} onClose={onClose}>
      <Box style={styleBox}>
        <Typography variant="h6" component="h2">
          {title}
        </Typography>
        <Typography>{bodyText}</Typography>
        <div style={styleButtonsGroup}>
          <Button color="error" variant="contained" onClick={onConfirm}>
            {btnTextConfirm}
          </Button>
          <Button
            variant="contained"
            onClick={onClose}
            style={{
              backgroundColor: '#ccc',
              color: '#000',
              marginLeft: '0.5rem',
            }}
          >
            Cancel
          </Button>
        </div>
      </Box>
    </Modal>
  );
};

export default ConfirmationModal;
