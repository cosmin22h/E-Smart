import React from 'react';

import { Box, Paper, TextField, Button } from '@mui/material';

import useInput from 'utils/hooks/useInput';
import history from 'utils/history';
import Title from 'components/Title';
import ClosingAlert from 'components/alerts/ClosingAlert';

const AdminForm = props => {
  const { defaultAdmin, onSubmit, error, onCloseAlert } = props;

  const [admin, onAdminChange] = useInput(defaultAdmin);

  const handleOnSubmit = event => {
    event.preventDefault();
    onSubmit(admin);
  };

  return (
    <Paper sx={{ p: 2 }}>
      <div
        style={{
          display: 'flex',
          flexDirection: 'row',
          justifyContent: 'space-between',
        }}
      >
        <Title>{`Hello, ${defaultAdmin.username}!`}</Title>
        <Button
          variant="outlined"
          onClick={() => history.push(`/admin/change-password/${admin.id}`)}
        >
          Change Password
        </Button>
      </div>
      {error ? (
        <ClosingAlert severity="error" message={error} onClose={onCloseAlert} />
      ) : null}
      <Box component="form" sx={{ mt: 2 }} onSubmit={handleOnSubmit}>
        <TextField
          id="username"
          name="username"
          type="text"
          label="Username"
          value={admin.username}
          required
          fullWidth
          onChange={onAdminChange}
        />
        <TextField
          id="email"
          name="email"
          type="email"
          label="Email"
          value={admin.email}
          required
          fullWidth
          onChange={onAdminChange}
          sx={{ mt: 2 }}
        />
        <Button type="submit" fullWidth variant="contained" sx={{ mt: 2 }}>
          UPDATE
        </Button>
      </Box>
    </Paper>
  );
};

export default AdminForm;
