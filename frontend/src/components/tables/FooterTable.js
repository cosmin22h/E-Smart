import React from 'react';

import { Button } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import ClearSharpIcon from '@mui/icons-material/ClearSharp';

const tableFooterStyle = {
  display: 'flex',
  flexDirection: 'row',
  marginTop: '1rem',
};

const FooterTable = props => {
  const {
    selectedItem,
    onAddItem,
    onCancelSelectItem,
    onDeleteItem,
    onEditItem,
  } = props;

  return (
    <div style={tableFooterStyle}>
      <div style={{ flex: '1' }}>
        {!selectedItem.id ? (
          <Button
            color="success"
            variant="contained"
            endIcon={<AddIcon />}
            onClick={onAddItem}
          >
            Add
          </Button>
        ) : (
          <Button
            color="primary"
            variant="contained"
            endIcon={<ClearSharpIcon />}
            onClick={onCancelSelectItem}
          >
            Cancel
          </Button>
        )}
      </div>
      {selectedItem.id ? (
        <React.Fragment>
          <div style={{ paddingRight: '0.5rem' }}>
            <Button
              color="warning"
              variant="outlined"
              endIcon={<EditIcon />}
              onClick={onEditItem}
            >
              Edit
            </Button>
          </div>
          <div>
            <Button
              color="error"
              variant="outlined"
              endIcon={<DeleteIcon />}
              onClick={onDeleteItem}
            >
              Delete
            </Button>
          </div>
        </React.Fragment>
      ) : null}
    </div>
  );
};

export default FooterTable;
