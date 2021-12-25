import React from 'react';

import { Box, IconButton, Typography } from '@mui/material';
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
import NavigateNextIcon from '@mui/icons-material/NavigateNext';

const Pagination = props => {
  const { currentPage, isNextPage, onPrevPage, onNextPage } = props;

  return (
    <Box
      style={{
        display: 'flex',
        justifyContent: 'center',
        marginTop: '1rem',
      }}
    >
      <IconButton
        size="small"
        disabled={currentPage === 1}
        onClick={onPrevPage}
      >
        <NavigateBeforeIcon />
      </IconButton>
      <IconButton
        size="small"
        onClick={e => e.preventDefault()}
        style={{ cursor: 'auto' }}
      >
        <Typography variant="h5">{currentPage}</Typography>
      </IconButton>
      <IconButton size="small" disabled={!isNextPage} onClick={onNextPage}>
        <NavigateNextIcon />
      </IconButton>
    </Box>
  );
};

export default Pagination;
