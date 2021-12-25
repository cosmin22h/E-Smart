import 'theme/style/style.scss';
import React from 'react';

import { Grid, Paper } from '@mui/material';

import DevicesList from 'components/lists/DevicesList';
import ClientsList from 'components/lists/ClientsList';

const AdminDashboardPage = props => {
  return (
    <Grid container spacing={3}>
      <Grid item xs={12}>
        <Paper className="paper">
          <ClientsList title="Clients" />
        </Paper>
      </Grid>
      <Grid item xs={12}>
        <Paper className="paper">
          <DevicesList title="Devices" />
        </Paper>
      </Grid>
    </Grid>
  );
};

export default AdminDashboardPage;
